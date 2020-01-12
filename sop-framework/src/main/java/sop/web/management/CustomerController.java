package sop.web.management;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import dwz.framework.config.Constants;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.user.User;
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import dwz.web.BaseController;
import it.sauronsoftware.base64.Base64;
import sop.persistence.beans.CustAttn;
import sop.persistence.beans.Customer;
import sop.services.CustomerServiceMgr;
import sop.vo.CustomerComboVo;
import sop.vo.CustomerVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:46
 * @Package: sop.web.management
 */

@Controller("management.customerController")
@RequestMapping(value = "/management/customer")
@SessionAttributes({"customer"})
public class CustomerController extends BaseController {

    @Autowired
    private CustomerServiceMgr customerMgr;

    @RequestMapping("/list")
    public String index(BaseConditionVO vo, Model model) {
        List<CustomerVo> customerList = customerMgr.searchCustomer(vo);
        Integer totalCount = customerMgr.searchCustomerNum(vo);

        vo.setTotalCount(totalCount);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageSize", vo.getPageSize());
        model.addAttribute("vo", vo);
        model.addAttribute("customerList", customerList);
        return "/management/customer/list";
    }

    @RequestMapping("/add")
    public String add(Model model) {
        CustomerComboVo customerComboVo = customerMgr.getCustomerComboVo();
        model.addAttribute("customerComboVo", customerComboVo);
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "/management/customer/add";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insert(@ModelAttribute("customer") Customer customer, HttpServletRequest request) {
        Checker checker = customerMgr.checkCustomer(customer);
        if (checker.isSuccess()) {
            Checker checkerDecimal = customerMgr.checkDecimal(customer);
            if (checker.isSuccess()) {
                try {
                    User user = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
                    if (user != null) {
                        customer.setCrtUsr(user.getUsrId());
                        customer.setModUsr(user.getUsrId());
                    }
                    customerMgr.addCustomer(customer);
                } catch (ServiceException e) {
                    e.printStackTrace();
                    return ajaxDoneError("Code 重复");
                }
                return ajaxDoneSuccess(getMessage("msg.operation.success"));
            } else {
                return ajaxDoneError(checkerDecimal.getReturnStr());
            }
        } else {
            return ajaxDoneError(checker.getReturnStr());
        }
    }

    @RequestMapping("/edit/{fkCuCode}")
    public String edit(@PathVariable("fkCuCode") String fkCuCode, Model model) {
        fkCuCode = Base64.decode(fkCuCode, "utf-8");
        CustomerComboVo customerComboVo = customerMgr.getCustomerComboVo();
        Customer customer = customerMgr.getCustomerByCode(fkCuCode);
        model.addAttribute("customerComboVo", customerComboVo);
        model.addAttribute("customer", customer);
        return "/management/customer/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@ModelAttribute("customer") Customer customer, HttpServletRequest request) {
        Checker checker = customerMgr.checkDecimal(customer);
        if (checker.isSuccess()) {
            try {
                dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
                if (currentUser != null) {
                    customer.setModUsr(currentUser.getUsrId());
                } else {
                    customer.setModUsr("test");
                }
                customerMgr.updateCustomer(customer);
            } catch (ServiceException e) {
                e.printStackTrace();
                return ajaxDoneError(e.getMessage());
            }
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } else {
            return ajaxDoneError(checker.getReturnStr());
        }
    }

    @RequestMapping("/delete/{fkCuCode}")
    public ModelAndView delete(@PathVariable("fkCuCode") String fkCuCode, HttpServletRequest request) {
        fkCuCode = Base64.decode(fkCuCode, "utf-8");
        try {
            if (customerMgr.deleteCustomer(fkCuCode)) {
                return ajaxDoneSuccess(getMessage("msg.operation.success"));
            } else {
                return ajaxDoneError(getMessage("msg.operation.failure"));
            }
        } catch (ServiceException e) {
            return ajaxDoneError(e.toString());
        }
    }

    @RequestMapping("/addattn")
    public String addAttn(Model model, HttpServletRequest request) {
        return "/management/customer/custaddattn";
    }

    @RequestMapping(value = "/insertattn", method = RequestMethod.POST)
    public ModelAndView insertAttn(CustAttn custAttn, @ModelAttribute("customer") Customer customer, HttpServletRequest request) {
        Checker checker = customerMgr.checkCustAttn(custAttn, customer);
        if (checker.isSuccess()) {
            dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
            if (currentUser != null) {
                customer.setModUsr(currentUser.getUsrId());
            } else {
                customer.setModUsr("test");
            }
            Map<String, CustAttn> custAttns = customer.getCustAttns();
            if (custAttns == null) custAttns = new LinkedHashMap<String, CustAttn>();
            custAttn.setCuCode(customer.getCuCode());
            custAttns.put(custAttn.getEncodeAttention(), custAttn);
            customer.setCustAttns(custAttns);
            request.getSession().setAttribute("customer", customer);
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } else {
            return ajaxDoneError(checker.getReturnStr());
        }
    }

    @RequestMapping("/refleshattns")
    public String refleshAttns(@ModelAttribute("customer") Customer customer, Model model, HttpServletRequest request) {
        model.addAttribute("customer", customer);
        return "/management/customer/custattntable";
    }

    @RequestMapping(value = "/deletecustattn", method = RequestMethod.GET)
    public String deleteCustAttn(HttpServletRequest request, @RequestParam(value = "fkAttnNo") String fkAttnNo, @ModelAttribute("customer") Customer customer, Model model) {
        Map<String, CustAttn> custAttns = customerMgr.removeCustAttn(fkAttnNo, customer.getCustAttns());
        customer.setCustAttns(custAttns);
        request.getSession().setAttribute("customer", customer);
        return "/management/customer/custattntable";
    }
}
