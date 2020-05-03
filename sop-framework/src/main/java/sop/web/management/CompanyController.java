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
import dwz.framework.user.User;
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import dwz.web.BaseController;
import it.sauronsoftware.base64.Base64;
import sop.persistence.beans.Company;
import sop.services.CompanyServiceMgr;
import sop.vo.CompanyVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:44
 * @Package: sop.web.management
 */

@Controller("management.companyController")
@RequestMapping(value = "/management/company")
//@SessionAttributes({"currentCom"})
public class CompanyController extends BaseController {

    @Autowired
    private CompanyServiceMgr comMgr;

    @RequestMapping("/list")
    public String index(BaseConditionVO vo, Model model, HttpServletRequest request) {
        List<CompanyVo> companyVoList = comMgr.getComListByCondition(vo);
        comMgr.getComListNum(vo);
        model.addAttribute("totalCount", vo.getTotalCount());
        model.addAttribute("pageSize", vo.getPageSize());
        model.addAttribute("companyVoList", companyVoList);
        model.addAttribute("vo", vo);
        return "/management/company/list";
    }

    @RequestMapping("/edit/{fkCoCode}")
    public String edit(@PathVariable("fkCoCode") String fkCoCode, Model model, HttpServletRequest request) {
        fkCoCode = Base64.decode(fkCoCode, "utf-8");
        Company currentCom = comMgr.getCompanyById(fkCoCode);
        model.addAttribute("currentCom", currentCom);
        return "/management/company/edit";
    }

    @RequestMapping("/delete/{fkCoCode}")
    public ModelAndView delete(@PathVariable("fkCoCode") String fkCoCode, Model model, HttpServletRequest request) {
        fkCoCode = Base64.decode(fkCoCode, "utf-8");
        if (comMgr.deleteCompany(fkCoCode)) {
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } else {
            return ajaxDoneError(getMessage("msg.operation.failure"));
        }
    }

    @RequestMapping("/add")
    public String newCom(Model model, HttpServletRequest request) {
        return "/management/company/add";
    }

//	@RequestMapping(value = "/save", method = RequestMethod.POST)
//	public ModelAndView save(Company currentCom) {
//		if(currentCom.isEditMode()){
//			if(comMgr.updateCompany(currentCom)){
//				return ajaxDoneSuccess(getMessage("msg.operation.success"));
//			}else{
//				return ajaxDoneError(getMessage("msg.operation.failure"));
//			}
//		}else{
//			Checker checker = comMgr.checkCompany(currentCom);
//			if(checker != null){
//				if(checker.isSuccess()){
//					if(comMgr.insertCompany(currentCom)){
//						return ajaxDoneSuccess(getMessage("msg.operation.success"));
//					}else{
//						return ajaxDoneError(getMessage("msg.operation.failure"));
//					}
//				}else{
//					return ajaxDoneError(checker.getReturnStr());
//				}
//			}else{
//				return ajaxDoneError(getMessage("msg.operation.failure"));
//			}
//		}
//	}

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@ModelAttribute("currentCom") Company currentCom, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        if (currentUser != null) {
            currentCom.setModUsr(currentUser.getUsrId());
        }
        if (comMgr.updateCompany(currentCom)) {
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } else {
            return ajaxDoneError(getMessage("msg.operation.failure"));
        }
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insert(Company currentCom, HttpServletRequest request) {
        Checker checker = comMgr.checkCompany(currentCom);
        if (checker.isSuccess()) {
            User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
            if (currentUser != null) {
                currentCom.setCrtUsr(currentUser.getUsrId());
                currentCom.setModUsr(currentUser.getUsrId());
            }
            if (comMgr.insertCompany(currentCom)) {
                return ajaxDoneSuccess(getMessage("msg.operation.success"));
            } else {
                return ajaxDoneError(getMessage("msg.operation.failure"));
            }
        } else {
            return ajaxDoneError(checker.getReturnStr());
        }
    }
}
