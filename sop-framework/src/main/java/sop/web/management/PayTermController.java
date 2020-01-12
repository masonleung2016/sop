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
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import dwz.web.BaseController;
import it.sauronsoftware.base64.Base64;
import sop.persistence.beans.PayTerm;
import sop.services.PayTermServiceMgr;
import sop.vo.PayTermVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:55
 * @Package: sop.web.management
 */


@Controller("management.payTermController")
@RequestMapping(value = "/management/payTerm")
public class PayTermController extends BaseController {

    @Autowired
    private PayTermServiceMgr payTermMgr;

    @RequestMapping("/list")
    public String index(BaseConditionVO vo, Model model) {
        List<PayTermVo> payTermList = payTermMgr.searchPayTerm(vo);
        Integer totalCount = payTermMgr.searchPayTermNum(vo);
        vo.setTotalCount(totalCount);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageSize", vo.getPageSize());
        model.addAttribute("vo", vo);
        model.addAttribute("payTermList", payTermList);
        return "/management/payterm/list";
    }

    //	@RequestMapping(value = "/save", method = RequestMethod.POST)
//	public ModelAndView save(PayTerm payTerm) {
//		try {
//			if(payTerm.getId() == null){
//				//save add payTerm
//				payTermMgr.addPayTerm(payTerm);
//			}else{
//				//edit payTerm
//				payTermMgr.updatePayTerm(payTerm);
//			}
//		} catch (ServiceException e) {
//			return ajaxDoneError(e.getMessage());
//		}
//		return ajaxDoneSuccess(getMessage("msg.operation.success"));
//	}
    @RequestMapping("/add")
    public String add() {
        return "/management/payterm/add";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insert(PayTerm payTerm, HttpServletRequest request) {
        Checker checker = payTermMgr.checkPaytm(payTerm);
        if (checker.isSuccess()) {
            Checker checkerDecimal = payTermMgr.checkDecimal(payTerm);
            if (checkerDecimal.isSuccess()) {
                User user = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
                if (user != null) {
                    payTerm.setCrtUsr(user.getUsrId());
                    payTerm.setModUsr(user.getUsrId());
                } else {
                    payTerm.setCrtUsr("test");
                    payTerm.setModUsr("test");
                }
                try {
                    payTermMgr.addPayTerm(payTerm);
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

    @RequestMapping("/edit/{payCode}")
    public String edit(@PathVariable("payCode") String payCode, Model model) {
        payCode = Base64.decode(payCode, "utf-8");
        PayTerm payTerm = payTermMgr.getPayTerm(payCode);
        model.addAttribute("payTerm", payTerm);
        return "/management/payterm/edit";
    }

    @RequestMapping("/delete/{payCode}")
    public ModelAndView delete(@PathVariable("payCode") String payCode, Model model) throws ServiceException {
        payCode = Base64.decode(payCode, "utf-8");
        payTermMgr.deletePaymentTerm(payCode);
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }

    @RequestMapping("/update")
    public ModelAndView update(PayTerm payTerm, HttpServletRequest request) {
        Checker checkerDecimal = payTermMgr.checkDecimal(payTerm);
        if (checkerDecimal.isSuccess()) {
            User user = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
            if (user != null) {
                payTerm.setModUsr(user.getUsrId());
            } else {
                payTerm.setModUsr("test");
            }
            try {
                payTermMgr.updatePayTerm(payTerm);
            } catch (ServiceException e) {
                e.printStackTrace();
                return ajaxDoneError(e.getMessage());
            }
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } else {
            return ajaxDoneError(checkerDecimal.getReturnStr());
        }
    }
//	@RequestMapping("/edit/{id}")
//	@ResponseBody
//	public HashMap<String, Object> edit(@PathVariable("id") int id, Model model) {
//		HashMap<String, Object> result = new HashMap<String, Object>();
//		PayTerm payTerm = payTermMgr.getPayTerm(id);
//		result.put("payTerm", payTerm);
//		return result;
//	}
}
