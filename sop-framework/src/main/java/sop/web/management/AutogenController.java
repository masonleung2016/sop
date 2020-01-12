package sop.web.management;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import sop.persistence.beans.Autogen;
import sop.persistence.beans.Currency;
import sop.services.AutogenServiceMgr;
import sop.vo.AutogenVo;
import sop.vo.CompanyVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:42
 * @Package: sop.web.management
 */

@Controller("management.autogenController")
@RequestMapping(value = "/management/autogen")
//@SessionAttributes({"currentAutogen"})
public class AutogenController extends BaseController {

    @Autowired
    private AutogenServiceMgr autogenMgr;

    @RequestMapping("/list")
    public String list(BaseConditionVO vo, Model model) {
        List<AutogenVo> autogenVoList = autogenMgr.getAutogenListByCondition(vo);
        autogenMgr.getAutogenListNum(vo);
        model.addAttribute("totalCount", vo.getTotalCount());
        model.addAttribute("pageSize", vo.getPageSize());
        model.addAttribute("autogenVoList", autogenVoList);
        model.addAttribute("vo", vo);
        return "/management/autogen/list";
    }

    @RequestMapping("/add")
    public String add(Model model, HttpServletRequest request) {
        List<CompanyVo> autogenCompanyList = autogenMgr.getAllCompany();
        model.addAttribute("autogenCompanyList", autogenCompanyList);
        return "/management/autogen/add";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insert(Autogen autogen, HttpServletRequest request) {
        Checker checker = autogenMgr.checkAutogen(autogen, false);
        if (checker.isSuccess()) {
            User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
            if (currentUser != null) {
                autogen.setCrtUsr(currentUser.getUsrId());
                autogen.setModUsr(currentUser.getUsrId());
            }
            try {
                if (autogenMgr.addAutogen(autogen)) {
                    return ajaxDoneSuccess(getMessage("msg.operation.success"));
                } else {
                    return ajaxDoneError(getMessage("msg.operation.failure"));
                }
            } catch (ServiceException e) {
                return ajaxDoneError(e.getMessage());
            }
        } else {
            return ajaxDoneError(checker.getReturnStr());
        }
    }

    @RequestMapping("/edit/{key}")
    public String edit(@PathVariable("key") String key, Model model) {
        //String key1 = Base64.decode(key, "utf-8");
        List<CompanyVo> autogenCompanyList = autogenMgr.getAllCompany();
        model.addAttribute("autogenCompanyList", autogenCompanyList);
        String[] keys = StringUtils.split(key, "|");
        Autogen autogen = autogenMgr.getAutogen(keys[0], keys[1]);
        model.addAttribute("autogen", autogen);
        return "/management/autogen/edit";
    }

    @RequestMapping("/update")
    public ModelAndView update(Autogen autogen, HttpServletRequest request) {
        Checker checker = autogenMgr.checkAutogen(autogen, true);
        if (checker.isSuccess()) {
            try {
                User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
                if (currentUser != null) {
                    autogen.setCrtUsr(currentUser.getUsrId());
                    autogen.setModUsr(currentUser.getUsrId());
                }
                autogenMgr.updateAutogen(autogen);
            } catch (ServiceException e) {
                e.printStackTrace();
                return ajaxDoneError(e.getMessage());
            }
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } else {
            return ajaxDoneError(checker.getReturnStr());
        }
    }

    @RequestMapping("/delete/{key}")
    public ModelAndView delete(@PathVariable("key") String key) {
        try {
            //String key1 = Base64.decode(key, "utf-8");
            String[] keys = StringUtils.split(key, "|");
            if (autogenMgr.deleteAutogen(keys[0], keys[1])) {
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
