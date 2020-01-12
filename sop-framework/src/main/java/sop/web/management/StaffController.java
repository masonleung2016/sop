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
import sop.persistence.beans.Staff;
import sop.services.StaffServiceMgr;
import sop.vo.StaffVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 12:59
 * @Package: sop.web.management
 */

@Controller("management.staffController")
@RequestMapping(value = "/management/staff")
public class StaffController extends BaseController {

    @Autowired
    private StaffServiceMgr staffMgr;

    @RequestMapping("/list")
    public String index(BaseConditionVO vo, Model model, HttpServletRequest request) {
        List<StaffVo> staffVoList = staffMgr.getStaffListByCondition(vo);
        staffMgr.getStaffListNum(vo);
        model.addAttribute("totalCount", vo.getTotalCount());
        model.addAttribute("pageSize", vo.getPageSize());
        model.addAttribute("staffVoList", staffVoList);
        model.addAttribute("vo", vo);
        return "/management/staff/list";
    }

    @RequestMapping("/edit/{fkSfCode}")
    public String edit(@PathVariable("fkSfCode") String fkSfCode, Model model, HttpServletRequest request) {
        fkSfCode = Base64.decode(fkSfCode, "utf-8");
        Staff currentStaff = staffMgr.getStaffById(fkSfCode);
        model.addAttribute("currentStaff", currentStaff);
        return "/management/staff/edit";
    }

    @RequestMapping("/delete/{fkSfCode}")
    public ModelAndView delete(@PathVariable("fkSfCode") String fkSfCode, Model model, HttpServletRequest request) {
        fkSfCode = Base64.decode(fkSfCode, "utf-8");
        if (staffMgr.deleteStaff(fkSfCode)) {
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } else {
            return ajaxDoneError(getMessage("msg.operation.failure"));
        }
    }

    @RequestMapping("/add")
    public String newStaff(Model model, HttpServletRequest request) {
        return "/management/staff/add";
    }

//	@RequestMapping(value = "/save", method = RequestMethod.POST)
//	public ModelAndView save(Staff currentStaff) {
//		if(currentStaff.isEditMode()){
//			if(staffMgr.updateStaff(currentStaff)){
//				return ajaxDoneSuccess(getMessage("msg.operation.success"));
//			}else{
//				return ajaxDoneError(getMessage("msg.operation.failure"));
//			}
//		}else{
//			Checker checker = staffMgr.checkStaff(currentStaff);
//			if(checker != null){
//				if(checker.isSuccess()){
//					if(staffMgr.insertStaff(currentStaff)){
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
    public ModelAndView update(@ModelAttribute("currentStaff") Staff currentStaff, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        if (currentUser != null) {
            currentStaff.setModUsr(currentUser.getUsrId());
        }
        if (staffMgr.updateStaff(currentStaff)) {
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } else {
            return ajaxDoneError(getMessage("msg.operation.failure"));
        }
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insert(Staff currentStaff, HttpServletRequest request) {
        Checker checker = staffMgr.checkStaff(currentStaff);
        if (checker.isSuccess()) {
            User currentUser = (User) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
            if (currentUser != null) {
                currentStaff.setCrtUsr(currentUser.getUsrId());
                currentStaff.setModUsr(currentUser.getUsrId());
            } else {
                currentStaff.setCrtUsr("test");
                currentStaff.setModUsr("test");
            }
            if (staffMgr.insertStaff(currentStaff)) {
                return ajaxDoneSuccess(getMessage("msg.operation.success"));
            } else {
                return ajaxDoneError(getMessage("msg.operation.failure"));
            }
        } else {
            return ajaxDoneError(checker.getReturnStr());
        }
    }
}
