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
import org.springframework.web.servlet.ModelAndView;

import dwz.framework.config.Constants;
import dwz.framework.user.User;
import dwz.framework.vo.Checker;
import dwz.persistence.BaseConditionVO;
import dwz.web.BaseController;
import it.sauronsoftware.base64.Base64;
import sop.persistence.beans.GlInterface;
import sop.services.GlInterfaceServiceMgr;
import sop.vo.GlInterfaceVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:48
 * @Package: sop.web.management
 */

@Controller("management.glinterfaceController")
@RequestMapping(value = "/management/glinterface")
public class GlInterfaceController extends BaseController {

    @Autowired
    private GlInterfaceServiceMgr glinterfaceMgr;

    @RequestMapping("/list")
    public String index(BaseConditionVO vo, Model model, HttpServletRequest request) {
        List<GlInterfaceVo> glinterfaceVoList = glinterfaceMgr.getGlInterfaceListByCondition(vo);
        glinterfaceMgr.getGlInterfaceListNum(vo);
        model.addAttribute("totalCount", vo.getTotalCount());
        model.addAttribute("pageSize", vo.getPageSize());
        model.addAttribute("glinterfaceVoList", glinterfaceVoList);
        model.addAttribute("vo", vo);
        return "/management/glinterface/list";
    }

    @RequestMapping("/edit/{fkGlInfCode}")
    public String edit(@PathVariable("fkGlInfCode") String fkGlInfCode, Model model, HttpServletRequest request) {
        fkGlInfCode = Base64.decode(fkGlInfCode, "utf-8");
        GlInterface currentGlInterface = glinterfaceMgr.getfkGlInfCodeById(fkGlInfCode);
        model.addAttribute("currentGlInterface", currentGlInterface);
        return "/management/glinterface/edit";
    }

    @RequestMapping("/delete/{fkGlInfCode}")
    public ModelAndView delete(@PathVariable("fkGlInfCode") String fkGlInfCode, Model model, HttpServletRequest request) {
        fkGlInfCode = Base64.decode(fkGlInfCode, "utf-8");
        if (glinterfaceMgr.deleteGlInterface(fkGlInfCode)) {
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } else {
            return ajaxDoneError(getMessage("msg.operation.failure"));
        }
    }

    @RequestMapping("/add")
    public String newGlInterface(Model model, HttpServletRequest request) {
        return "/management/glinterface/add";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@ModelAttribute("currentGlInterface") GlInterface currentGlInterface, HttpServletRequest request) {
        Checker checkerDecimal = glinterfaceMgr.checkGlInterfaceDecimal(currentGlInterface);
        if (checkerDecimal.isSuccess()) {
            User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
            if (currentUser != null) {
                currentGlInterface.setModUsr(currentUser.getUsrId());
            }
            if (glinterfaceMgr.updateGlInterface(currentGlInterface)) {
                return ajaxDoneSuccess(getMessage("msg.operation.success"));
            } else {
                return ajaxDoneError(getMessage("msg.operation.failure"));
            }
        } else {
            return ajaxDoneError(checkerDecimal.getReturnStr());
        }
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insert(GlInterface currentGlInterface, HttpServletRequest request) {
        Checker checker = glinterfaceMgr.checkGlInterface(currentGlInterface);
        if (checker.isSuccess()) {
            Checker checkerDecimal = glinterfaceMgr.checkGlInterfaceDecimal(currentGlInterface);
            if (checkerDecimal.isSuccess()) {
                User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
                if (currentUser != null) {
                    currentGlInterface.setCoCode(currentUser.getCoCode());
                    currentGlInterface.setCrtUsr(currentUser.getUsrId());
                    currentGlInterface.setModUsr(currentUser.getUsrId());
                } else {
                    currentGlInterface.setCoCode("RRR");
                    currentGlInterface.setCrtUsr("test");
                    currentGlInterface.setModUsr("test");
                }
                if (glinterfaceMgr.insertGlInterface(currentGlInterface)) {
                    return ajaxDoneSuccess(getMessage("msg.operation.success"));
                } else {
                    return ajaxDoneError(getMessage("msg.operation.failure"));
                }
            } else {
                return ajaxDoneError(checkerDecimal.getReturnStr());
            }
        } else {
            return ajaxDoneError(checker.getReturnStr());
        }
    }
}
