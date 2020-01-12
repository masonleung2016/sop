package dwz.web;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import dwz.common.util.ServerInfo;
import dwz.web.editor.DateEditor;
import dwz.web.editor.DoubleEditor;
import dwz.web.editor.IntegerEditor;
import dwz.web.editor.LongEditor;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:38
 * @Package: dwz.web.editor
 */

public abstract class BaseController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ResourceBundleMessageSource _res;

//	@Autowired
//	protected SpringContextHolder _contextHolder;

    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws Exception {

        binder.registerCustomEditor(int.class, new IntegerEditor());
        binder.registerCustomEditor(long.class, new LongEditor());
        binder.registerCustomEditor(double.class, new DoubleEditor());
        binder.registerCustomEditor(Date.class, new DateEditor());
    }

    protected ModelAndView ajaxDone(int statusCode, String message, String forwardUrl) {
        ModelAndView mav = new ModelAndView("ajaxDone");
        mav.addObject("statusCode", statusCode);
        mav.addObject("message", message);
        mav.addObject("forwardUrl", forwardUrl);
        return mav;
    }

    protected ModelAndView ajaxDoneSuccess(String message) {
        return ajaxDone(200, message, "");
    }

    protected ModelAndView ajaxDoneError(String message) {
        return ajaxDone(300, message, "");
    }

    protected String getMessage(String code) {
        return this.getMessage(code, new Object[]{});
    }

    protected String getMessage(String code, Object arg0) {
        return getMessage(code, new Object[]{arg0});
    }

    protected String getMessage(String code, Object arg0, Object arg1) {
        return getMessage(code, new Object[]{arg0, arg1});
    }

    protected String getMessage(String code, Object arg0, Object arg1,
                                Object arg2) {
        return getMessage(code, new Object[]{arg0, arg1, arg2});
    }

    protected String getMessage(String code, Object arg0, Object arg1,
                                Object arg2, Object arg3) {
        return getMessage(code, new Object[]{arg0, arg1, arg2, arg3});
    }

    protected String getMessage(String code, Object[] args) {
        //HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        Locale locale = localeResolver.resolveLocale(request);

        return _res.getMessage(code, args, locale);
    }


    @ExceptionHandler(Exception.class)
    public ModelAndView exception(Exception e, HttpServletRequest request) {
        e.printStackTrace();

        request.setAttribute("exception", e);

        if (ServerInfo.isAjax(request) || request.getParameter("ajax") != null) {
            return ajaxDoneError(e.getMessage());
        }

        ModelAndView mav = new ModelAndView("error");
        mav.addObject("statusCode", 300);
        mav.addObject("message", e.getMessage());

        return mav;
    }

    public Map<String, Object> stringToMap(String str) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        ObjectMapper picMapper = new ObjectMapper();
        try {
            map = picMapper.readValue(str, new TypeReference<LinkedHashMap<String, Object>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
