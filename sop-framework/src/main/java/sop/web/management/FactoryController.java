package sop.web.management;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dwz.framework.sys.exception.ServiceException;
import dwz.framework.vo.Checker;
import dwz.web.BaseController;
import it.sauronsoftware.base64.Base64;
import sop.persistence.beans.Factory;
import sop.services.FactoryServiceMgr;
import sop.vo.FactoryComboVo;
import sop.vo.FactoryConditionVo;
import sop.vo.FactoryVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:47
 * @Package: sop.web.management
 */

@Controller("management.factoryController")
@RequestMapping(value = "/management/factory")
public class FactoryController extends BaseController {

    @Autowired
    private FactoryServiceMgr factoryMgr;

    @RequestMapping("/list")
    public String index(FactoryConditionVo vo, Model model) {
        
        List<FactoryVo> factoryList = factoryMgr.searchFactory(vo);
        
        Integer totalCount = factoryMgr.searchFactoryNum(vo);
        
        vo.setTotalCount(totalCount);
        
        model.addAttribute("totalCount", totalCount);
        
        model.addAttribute("pageSize", vo.getPageSize());
        
        model.addAttribute("vo", vo);
        
        model.addAttribute("factoryList", factoryList);
        
        return "/management/factory/list";
    }

    @RequestMapping("/edit/{suCode}")
    public String edit(@PathVariable("suCode") String suCode, Model model, HttpServletRequest request) {
        suCode = Base64.decode(suCode, "utf-8");
        FactoryComboVo factoryComboVo = factoryMgr.getFactoryComboVo();
        Factory currentFactory = factoryMgr.getFactoryBySuCode(suCode);
        model.addAttribute("factoryComboVo", factoryComboVo);
        model.addAttribute("currentFactory", currentFactory);
        return "/management/factory/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(Factory factory, HttpServletRequest request) {
        Checker checker = factoryMgr.checkDecimal(factory);
        if (checker.isSuccess()) {
            try {
                dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
                if (currentUser != null) {
                    factory.setModUsr(currentUser.getUsrId());
                } else {
                    factory.setModUsr("test");
                }
                factoryMgr.updateFactory(factory);
            } catch (ServiceException e) {
                e.printStackTrace();
                return ajaxDoneError(e.getMessage());
            }
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } else {
            return ajaxDoneError(checker.getReturnStr());
        }
    }

    @RequestMapping("/add")
    public String add(Model model) {
        FactoryComboVo factoryComboVo = factoryMgr.getFactoryComboVo();
        model.addAttribute("factoryComboVo", factoryComboVo);
        return "/management/factory/add";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insert(Factory factory, HttpServletRequest request) {
        Checker checker = factoryMgr.checkFactory(factory);
        if (checker.isSuccess()) {
            Checker checkDecimal = factoryMgr.checkDecimal(factory);
            if (checkDecimal.isSuccess()) {
                try {
                    dwz.framework.user.User currentUser = (dwz.framework.user.User) request.getSession().getAttribute(dwz.framework.config.Constants.AUTHENTICATION_KEY);
                    if (currentUser != null) {
                        factory.setCrtUsr(currentUser.getUsrId());
                        factory.setModUsr(currentUser.getUsrId());
                    } else {
                        factory.setCrtUsr("test");
                        factory.setModUsr("test");
                    }
                    factoryMgr.addFactory(factory);
                } catch (ServiceException e) {
                    e.printStackTrace();
                    return ajaxDoneError(e.getMessage());
                }
                return ajaxDoneSuccess(getMessage("msg.operation.success"));
            } else {
                return ajaxDoneError(checkDecimal.getReturnStr());
            }
        } else {
            return ajaxDoneError(checker.getReturnStr());
        }
    }

    @RequestMapping("/delete/{fkSuCode}")
    public ModelAndView delete(@PathVariable("fkSuCode") String fkSuCode, HttpServletRequest request) {
        fkSuCode = Base64.decode(fkSuCode, "utf-8");
        try {
            if (factoryMgr.deleteFactory(fkSuCode)) {
                return ajaxDoneSuccess(getMessage("msg.operation.success"));
            } else {
                return ajaxDoneError(getMessage("msg.operation.failure"));
            }
        } catch (ServiceException e) {
            return ajaxDoneError(e.toString());
        }
    }

    @RequestMapping(value = "/getLatestCode", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getTemplatesByFkItemMaster(@RequestParam(value = "getCodeByPrefix") String getCodeByPrefix, Model model) {
        Map<String, Object> result = new HashMap<String, Object>();
        String factoryCode = factoryMgr.getLatestFactoryCodeByPrefix(getCodeByPrefix);
        result.put("factoryCode", factoryCode);
        return result;
    }

}
