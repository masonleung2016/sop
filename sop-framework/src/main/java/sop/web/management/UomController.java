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
import sop.persistence.beans.Uom;
import sop.services.UomServiceMgr;
import sop.vo.UomVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 13:01
 * @Package: sop.web.management
 */

@Controller("management.uomController")
@RequestMapping(value = "/management/uom")
//@SessionAttributes({"currentUom"})
public class UomController extends BaseController {

    @Autowired
    private UomServiceMgr uomMgr;

    @RequestMapping("/list")
    public String list(BaseConditionVO vo, Model model) {
        List<UomVo> uomVoList = uomMgr.getUomListByCondition(vo);
        uomMgr.getUomListNum(vo);
        model.addAttribute("totalCount", vo.getTotalCount());
        model.addAttribute("pageSize", vo.getPageSize());
        model.addAttribute("uomVoList", uomVoList);
        model.addAttribute("vo", vo);
        return "/management/uom/list";
    }

    @RequestMapping("/add")
    public String add(Model model) {
        return "/management/uom/add";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insert(Uom uom, HttpServletRequest request) {
        Checker checker = uomMgr.checkUom(uom);
        if (checker.isSuccess()) {
            User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
            if (currentUser != null) {
                uom.setCrtUsr(currentUser.getUsrId());
                uom.setModUsr(currentUser.getUsrId());
            }
            try {
                uomMgr.addUom(uom);
                return ajaxDoneSuccess(getMessage("msg.operation.success"));
            } catch (ServiceException e) {
                e.printStackTrace();
                return ajaxDoneError(e.getMessage());
            }
        } else {
            return ajaxDoneError(checker.getReturnStr());
        }
    }

    @RequestMapping("/edit/{fkuCode}")
    public String edit(@PathVariable("fkuCode") String fkuCode, Model model, HttpServletRequest request) {

        Uom currentUom = uomMgr.getUomByFkuCode(fkuCode);
        model.addAttribute("currentUom", currentUom);

        return "/management/uom/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@ModelAttribute("currentUom") Uom currentUom, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        if (currentUser != null) {
            currentUom.setModUsr(currentUser.getUsrId());
        }
        try {
            if (uomMgr.updateUom(currentUom)) {
                return ajaxDoneSuccess(getMessage("msg.operation.success"));
            } else {
                return ajaxDoneError(getMessage("msg.operation.failure"));
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            return ajaxDoneError(e.getMessage());
        }
    }

    @RequestMapping(value = "/clearcurrentuom", method = RequestMethod.POST)
    public ModelAndView clearCurrentUom(HttpServletRequest request) {
        request.getSession().removeAttribute("currentUom");
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }

    @RequestMapping("/delete/{fkuCode}")
    public ModelAndView delete(@PathVariable("fkuCode") String fkuCode, HttpServletRequest request) {
        try {
            if (uomMgr.deleteUom(fkuCode)) {
                return ajaxDoneSuccess(getMessage("msg.operation.success"));
            } else {
                return ajaxDoneError(getMessage("msg.operation.failure"));
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            return ajaxDoneError(e.toString());
        }
    }
}
