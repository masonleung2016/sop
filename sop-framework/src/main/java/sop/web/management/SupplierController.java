package sop.web.management;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dwz.framework.config.Constants;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.user.User;
import dwz.persistence.BaseConditionVO;
import dwz.web.BaseController;
import sop.persistence.beans.Supplier;
import sop.services.SupplierServiceMgr;

/**
 * @Author: LCF
 * @Date: 2020/1/9 13:00
 * @Package: sop.web.management
 */

@Controller("management.supplierController")
@RequestMapping(value = "/management/supplier")
public class SupplierController extends BaseController {

    @Autowired
    private SupplierServiceMgr supplierMgr;

    @RequestMapping("/list")
    public String index(BaseConditionVO vo, Model model) {
        List<Supplier> supplierList = supplierMgr.searchSupplier(vo);
        Integer totalCount = supplierMgr.searchSupplierNum(vo);

        vo.setTotalCount(totalCount);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageSize", vo.getPageSize());
        model.addAttribute("vo", vo);
        model.addAttribute("supplierList", supplierList);
        return "/management/supplier/list";
    }

    @RequestMapping("/add")
    public String add(Model model) {
        Supplier supplier = new Supplier();
        model.addAttribute("supplier", supplier);
        return "/management/supplier/add";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insert(Supplier supplier, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        supplier.setCrtUsr(user.getUsrId());
        try {
            supplierMgr.addSupplier(supplier);
        } catch (ServiceException e) {
            e.printStackTrace();
            return ajaxDoneError("code 重复");
        }
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }

    @RequestMapping("/edit/{code}")
    public String edit(@PathVariable("code") String code, Model model) {
        Supplier supplier = supplierMgr.getSupplierByCode(code);
        model.addAttribute("supplier", supplier);
        return "/management/supplier/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(Supplier supplier, HttpServletRequest request) {
        dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
        supplier.setModUsr(currentUser.getUsrId());

        try {
            supplierMgr.updateSupplier(supplier);
        } catch (ServiceException e) {
            e.printStackTrace();
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }

    @RequestMapping("/delete/{code}")
    public ModelAndView delete(@PathVariable("code") String code, HttpServletRequest request) {

//TODO: no use,不确定是物理删除，还是逻辑删除
//			try {
//				supplierMgr.deleteSupplier(code);
//			} catch (ServiceException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				return ajaxDoneError(e.getMessage());
//			}
//			return ajaxDoneSuccess(getMessage("msg.operation.success"));
//	}
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }
}
