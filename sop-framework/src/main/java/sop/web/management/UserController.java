package sop.web.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dwz.framework.sys.exception.ServiceException;
import dwz.framework.user.User;
import dwz.framework.user.UserServiceMgr;
import dwz.persistence.BaseConditionVO;
import dwz.web.BaseController;
import it.sauronsoftware.base64.Base64;
import sop.persistence.beans.SysUser;
import sop.vo.CustomerVo;

/**
 * @Author: LCF
 * @Date: 2020/1/9 13:02
 * @Package: sop.web.management
 */

@Controller
@RequestMapping(value = "/management/user")
public class UserController extends BaseController {

    @Autowired
    private UserServiceMgr userMgr;


    @RequestMapping("/list")
    public ModelAndView list(BaseConditionVO vo) {

        ModelAndView mv = new ModelAndView("/management/user/list");

        List<SysUser> userList = userMgr.searchUser(vo);
        Integer totalCount = userMgr.searchUserNum(vo);

        vo.setTotalCount(totalCount);
        mv.addObject("totalCount", totalCount);
        mv.addObject("pageSize", vo.getPageSize());
        mv.addObject("vo", vo);
        mv.addObject("userList", userList);

        return mv;

    }

    @RequestMapping("/add")
    public ModelAndView add() {
        ModelAndView mv = new ModelAndView("/management/user/add");
        mv.addObject("coCodes", this.userMgr.getCoCodes());
        mv.addObject("user", new SysUser());

        return mv;
    }

    @RequestMapping("/edit/{uname}/{company}")
    public ModelAndView edit(@PathVariable("uname") String uname, @PathVariable("company") String company) {
        User user = userMgr.getUserByUsernameAndCocode(uname, company);

        ModelAndView mv = new ModelAndView("/management/user/edit");
        mv.addObject("user", user.getSysUser());
        mv.addObject("coCodes", this.userMgr.getCoCodes());

        return mv;
    }

    @RequestMapping("/insert")
    @ResponseBody
    public ModelAndView insert(SysUser user) {
        try {
            userMgr.insertUsr(user);
        } catch (Exception e) {
            return ajaxDoneError(e.getMessage());
        }

        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }

    @RequestMapping("/update")
    @ResponseBody
    public ModelAndView update(SysUser user) {
        try {
            userMgr.updUser(user);
        } catch (Exception e) {
            return ajaxDoneError(e.getMessage());
        }

        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }

    @RequestMapping("/delete/{uname}/{company}")
    @ResponseBody
    public ModelAndView delete(@PathVariable("uname") String uname, @PathVariable("company") String company) {
        try {
            User user = userMgr.getUserByUsernameAndCocode(uname, company);
            if (user != null)
                userMgr.delUser(user.getSysUser());
        } catch (Exception e) {
            return ajaxDoneError(e.getMessage());
        }

        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }

}
