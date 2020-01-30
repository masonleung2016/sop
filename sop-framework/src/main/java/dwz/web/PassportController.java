package dwz.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dwz.framework.config.Constants;
import dwz.framework.user.User;
import dwz.framework.user.UserServiceMgr;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:41
 * @Package: dwz.web
 */

@Controller
@RequestMapping(value = "/passport")
public class PassportController extends BaseController {

    @Autowired
    private UserServiceMgr userMgr;

//	@RequestMapping("/register")
//	public ModelAndView register(User user){
//		try {
//			userMgr.register(user);
//		} catch (ServiceException e) {
//			return ajaxDoneError(e.getMessage());
//		}
//
//		return ajaxDoneSuccess(getMessage("msg.operation.success"));
//	}

//	@RequestMapping("/verify/{verifyCode}")
//	public String verify(@PathVariable("verifyCode") String verifyCode, Model model) {
//		try {
//			userMgr.verify(verifyCode);
//		} catch (ServiceException e) {
//			model.addAttribute("statusCode", 300);
//			model.addAttribute("message", e.getMessage());
//		}
//		return "/alert";
//	}

//	@RequestMapping("/sendVerifyEmail/{username}")
//	public ModelAndView sendVerifyEmail(@PathVariable("username") String username, Model model) {
//		try {
//			User user = userMgr.getUserByUsername(username);
//			userMgr.sendVerifyEmail(user);
//		} catch (ServiceException e) {
//			ajaxDoneError(e.getMessage());
//		}
//		return ajaxDoneSuccess(getMessage("msg.operation.success"));
//	}

    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("login");
        mv.addObject("coCodes", this.userMgr.getCoCodes());

        String coCode = request.getParameter("coCode");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

//		try {
//			if (isSkipVC() || this.verifyValidationCode(getValidationCode())) {
//				loginOk = userMgr.hasMatchUser(username, password);
//			} else {
//				setStatusCode(300);
//				setMessage(this.getText("msg.validation.code.match"));
//			}
//
//		} catch (AuthenticationException e) {
//			setStatusCode(300);
//			setMessage(e.getLocalizedMessage());
//		}

        if ((username == null || username.trim().equals("")) && (password == null || password.trim().equals(""))) {
            mv.addObject("error", getMessage("ivf.login.inputusernamepassword"));
            return mv;
        } else if ((username == null || username.trim().equals("")) && (password != null && !password.trim().equals(""))) {
            mv.addObject("error", getMessage("ivf.login.inputusername"));
            return mv;
        } else if ((username != null && !username.trim().equals("")) && (password == null || password.trim().equals(""))) {
            mv.addObject("username", username);
            mv.addObject("error", getMessage("ivf.login.inputpassword"));
            return mv;
        } else {
//			帐号和密码都有
            User user = userMgr.getUserByUsernameAndCocode(username, coCode);
            if (user != null && password != null && password.equals(user.getUsrPass())) {
                //登录成功
                request.getSession().setAttribute(Constants.AUTHENTICATION_KEY, user);

                //request.getSession().setAttribute("user", user);
                ModelAndView indexMv = new ModelAndView("forward:/management/index");

                //set session scope "hospital name" for print page
//				indexMv.addObject("authorizationVo", authorizationVo);

                return indexMv;
//				return new ModelAndView("forward:/management/index", "", "");
            } else {
                //用户名或者是密码错误
                mv.addObject("username", username);
                mv.addObject("error", getMessage("ivf.login.wrongusernameorpassword"));
                return mv;
            }
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
//		ModelAndView logoutMv = new ModelAndView("logout");
        ModelAndView logoutMv = new ModelAndView("login");

        request.getSession().removeAttribute(Constants.AUTHENTICATION_KEY);
        request.getSession().removeAttribute("user");
        request.getSession().removeAttribute("hospitalName");
        request.getSession().invalidate();//注销
        String locale = (String) request.getParameter("locale");
//		logoutMv.addObject("locale", locale);
        return "redirect:/login" + "?locale=" + locale;
    }
}
