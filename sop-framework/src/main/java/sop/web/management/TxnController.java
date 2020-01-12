package sop.web.management;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import dwz.framework.config.Constants;
import dwz.framework.sys.exception.ServiceException;
import dwz.framework.user.User;
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import dwz.web.BaseController;
import it.sauronsoftware.base64.Base64;
import sop.persistence.beans.Txn;
import sop.services.TxnServiceMgr;
import sop.vo.TxnVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 13:01
 * @Package: sop.web.management
 */

@Controller("management.txnController")
@RequestMapping(value = "/management/txn")
//@SessionAttributes({"currentTxn"})
public class TxnController extends BaseController {

    @Autowired
    private TxnServiceMgr txnMgr;

    @RequestMapping("/list")
    public String list(BaseConditionVO vo, Model model) {
        List<TxnVo> txnVoList = txnMgr.getTxnListByCondition(vo);
        txnMgr.getTxnListNum(vo);
        model.addAttribute("totalCount", vo.getTotalCount());
        model.addAttribute("pageSize", vo.getPageSize());
        model.addAttribute("txnVoList", txnVoList);
        model.addAttribute("vo", vo);
        return "/management/txn/list";
    }

    @RequestMapping("/add")
    public String add(Model model, HttpServletRequest request) {
        model.addAttribute("currentTxn", new Txn());
        return "/management/txn/add";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insert(Txn txn, HttpServletRequest request) {
        Checker checker = txnMgr.checkTxn(txn);
        if (checker.isSuccess()) {
            User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
            if (currentUser != null) {
                txn.setCrtUsr(currentUser.getUsrId());
                txn.setModUsr(currentUser.getUsrId());
            }
            try {
                if (txnMgr.addTxn(txn)) {
                    return ajaxDoneSuccess(getMessage("msg.operation.success"));
                } else {
                    return ajaxDoneError(getMessage("msg.operation.failure"));
                }
            } catch (ServiceException e) {
                e.printStackTrace();
                return ajaxDoneError(e.getMessage());
            }
        } else {
            return ajaxDoneError(checker.getReturnStr());
        }
    }

    @RequestMapping("/edit/{fkTxtCode}")
    public ModelAndView edit(@PathVariable("fkTxtCode") String fkTxtCode, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/management/txn/edit");
        Txn currentTxn = txnMgr.getTxnByFkTxtCode(fkTxtCode);
        if (currentTxn != null) {
            mv.addObject("currentTxn", currentTxn);
        } else {
            return ajaxDoneError("No record found!");
        }

        return mv;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@ModelAttribute("currentTxn") Txn currentTxn, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        if (currentUser != null) {
            currentTxn.setModUsr(currentUser.getUsrId());
        }
        try {
            if (txnMgr.updateTxn(currentTxn)) {
                return ajaxDoneSuccess(getMessage("msg.operation.success"));
            } else {
                return ajaxDoneError(getMessage("msg.operation.failure"));
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            return ajaxDoneError(e.getMessage());
        }
    }

//	@RequestMapping(value = "/clearcurrentuom", method = RequestMethod.POST)
//	public ModelAndView clearCurrentUom(HttpServletRequest request) {
//		request.getSession().removeAttribute("currentUom");
//		return ajaxDoneSuccess(getMessage("msg.operation.success"));
//	}

    @RequestMapping("/delete/{fkTxtCode}")
    public ModelAndView delete(@PathVariable("fkTxtCode") String fkTxtCode, Model model, HttpServletRequest request) {
        //fkTxtCode = Base64.decode(fkTxtCode, "utf-8");
        if (txnMgr.deleteTxn(fkTxtCode)) {
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } else {
            return ajaxDoneError(getMessage("msg.operation.failure"));
        }
    }
}
