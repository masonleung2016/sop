package dwz.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dwz.framework.config.Constants;
import dwz.framework.user.UserServiceMgr;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:39
 * @Package: dwz.web
 */

@Controller
public class IndexController extends BaseController {
    // @RequestMapping("") public String index() { return "/index"; }
    // 前台的首页不用。

    @Autowired
    private UserServiceMgr usrMgr;

    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request, Model model) {
        if (request.getSession().getAttribute(Constants.AUTHENTICATION_KEY) != null) {
            ModelAndView mv = new ModelAndView("forward:/management/index");
            return mv;
        } else {
            ModelAndView mv = new ModelAndView("/login");
            mv.addObject("coCodes", usrMgr.getCoCodes());
            return mv;
        }
    }

}
