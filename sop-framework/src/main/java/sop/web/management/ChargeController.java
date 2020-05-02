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
import sop.persistence.beans.Charge;
import sop.services.ChargeServiceMgr;
import sop.vo.ChargeVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:43
 * @Package: sop.web.management
 */

@Controller("management.chargeController")
@RequestMapping(value = "/management/charge")
//@SessionAttributes({"currentCharge"})
public class ChargeController extends BaseController {

    @Autowired
    private ChargeServiceMgr chargeMgr;

    @RequestMapping("/list")
    public String list(BaseConditionVO vo, Model model) {
        
        List<ChargeVo> chargeVoList = chargeMgr.getChargeListByCondition(vo);
        
        chargeMgr.getChargeListNum(vo);
        
        model.addAttribute("totalCount", vo.getTotalCount());
        
        model.addAttribute("pageSize", vo.getPageSize());
        
        model.addAttribute("chargeVoList", chargeVoList);
        
        model.addAttribute("vo", vo);
        
        return "/management/charge/list";
    }

    @RequestMapping("/add")
    public String add(Model model, HttpServletRequest request) {
        
        return "/management/charge/add";
        
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insert(Charge charge, HttpServletRequest request) {
        
        Checker checker = chargeMgr.checkCharge(charge);
        
        if (checker.isSuccess()) {
            
            Checker checkerDecimal = chargeMgr.checkChargeDecimal(charge);
            
            if (checkerDecimal.isSuccess()) {
                
                User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
                
                if (currentUser != null) {
                    
                    charge.setCrtUsr(currentUser.getUsrId());
                    
                    charge.setModUsr(currentUser.getUsrId());
                    
                }
                try {
                    
                    if (chargeMgr.addCharge(charge)) {
                        
                        return ajaxDoneSuccess(getMessage("msg.operation.success"));
                        
                    } else {
                        
                        return ajaxDoneError(getMessage("msg.operation.failure"));
                        
                    }
                    
                } catch (ServiceException e) {
                    
                    e.printStackTrace();
                    
                    return ajaxDoneError(e.getMessage());
                    
                }
                
            } else {
                
                return ajaxDoneError(checkerDecimal.getReturnStr());
                
            }
            
        } else {
            
            return ajaxDoneError(checker.getReturnStr());
            
        }
    }

    @RequestMapping("/edit/{fkChgCode}")
    public String edit(@PathVariable("fkChgCode") String fkChgCode, Model model, HttpServletRequest request) {
        
        fkChgCode = Base64.decode(fkChgCode, "utf-8");
        
        Charge currentCharge = chargeMgr.getChargeByFkChgCode(fkChgCode);
        
        model.addAttribute("currentCharge", currentCharge);

        return "/management/charge/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@ModelAttribute("currentCharge") Charge currentCharge, HttpServletRequest request) {
        
        Checker checker = chargeMgr.checkChargeDecimal(currentCharge);
        
        if (checker.isSuccess()) {
            
            User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
            
            if (currentUser != null) {
                
                currentCharge.setModUsr(currentUser.getUsrId());
                
            }
            
            try {
                
                if (chargeMgr.updateCharge(currentCharge)) {
                    
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

    @RequestMapping("/delete/{fkChgCode}")
    public ModelAndView delete(@PathVariable("fkChgCode") String fkChgCode, HttpServletRequest request) {
        
        fkChgCode = Base64.decode(fkChgCode, "utf-8");
        
        try {
            
            if (chargeMgr.deleteCharge(fkChgCode)) {
                
                return ajaxDoneSuccess(getMessage("msg.operation.success"));
                
            } else {
                
                return ajaxDoneError(getMessage("msg.operation.failure"));
                
            }
            
        } catch (ServiceException e) {
            
            return ajaxDoneError(e.toString());
            
        }
    }
//	@RequestMapping(value = "/clearcurrentuom", method = RequestMethod.POST)
//	public ModelAndView clearCurrentUom(HttpServletRequest request) {
//		request.getSession().removeAttribute("currentUom");
//		return ajaxDoneSuccess(getMessage("msg.operation.success"));
//	}
}
