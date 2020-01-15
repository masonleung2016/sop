package dwz.adapter.web.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dwz.common.util.ServerInfo;
import dwz.framework.identity.Validator;

/**
 * @Author: LCF
 * @Date: 2020/1/8 11:32
 * @Package: dwz.adapter.web.filter
 */

public class SessionValidateFilter implements Filter {

    private static final Log log = LogFactory.getLog(SessionValidateFilter.class);
    private static final String PARAM_URI = "uri";
    private static final String DEBUG = "debug";
    private static final String LOGIN_URL = "loginUrl";
    private static final String BACK_TO_URL = "backToUrl";
    private String[] unsecureUris = null;
    private boolean debug = false;

    private String loginUrl;

    private FilterConfig config = null;

    public void destroy() {
        this.unsecureUris = null;
        this.config = null;
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;

//		java.util.Locale _mlc =  org.springframework.web.servlet.support.RequestContextUtils.getLocale(request);
//		String lcstr = _mlc.toString();
//		if(lcstr.equals("en_US")){
//
//		}else if(lcstr.equals("zh_CN")){
//
//		}else{
//			lcstr = "zh_CN";
//		}
//		req.setAttribute("lcstr", lcstr);

        Validator validator = Validator.getInstance();

//		MessageSource messageSource = (MessageSource)SpringContainer.getBean("messageSource");

        validator.init(request.getSession());

        String queryString = request.getQueryString();
        String requestUri = request.getRequestURI();
        // if ("/".equals(requestUri)) requestUri = "/index!index.do";

        System.out.println("url: " + requestUri + (queryString != null ? "?" + queryString : ""));
        for (String unsecureUri : this.unsecureUris) {
            String unsecureQuery = null;
            String uri = unsecureUri;
            boolean unsecure = false;

            if (unsecureUri.indexOf("?") >= 0) {
                uri = unsecureUri.substring(0, unsecureUri.indexOf("?"));
                unsecureQuery = unsecureUri.substring(unsecureUri.indexOf("?") + 1);
                unsecure = requestUri.startsWith(uri) && queryString.indexOf(unsecureQuery) >= 0;
            } else {
                unsecure = requestUri.startsWith(uri);
            }

            if (unsecure) {
                if (debug) {
                    this.config.getServletContext().log("uri " + requestUri + " will not be protected.");
                }

                if (!validator.validate()) {
                    validator.cancel();
                } else {
                    validator.confirm();
                }

                chain.doFilter(req, res);

                validator.clear();
                return;
            }
        }

        log.debug("validate authentication.");
        if (!validator.validate()) {
            validator.cancel();

            HttpServletResponse response = (HttpServletResponse) res;
            StringBuffer sb = request.getRequestURL();
            String query = request.getQueryString();
            if (query != null && !"".equals(query)) {
                sb.append('?').append(query);
            }

            String backToUrl = sb.toString();
            System.out.println("backToUrl = " + backToUrl);
            if (ServerInfo.isAjax(request) || request.getParameter("ajax") != null) {
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                out.println("{\"statusCode\":\"301\", \"message\":null}");
            } else {
                // request.setAttribute("statusCode", 301); // for iframeDone
                // callback
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + this.loginUrl + java.net.URLEncoder.encode(backToUrl, "UTF-8")));
            }
            return;
        }

        validator.confirm();

        log.debug("validate authentication finished, the authentication has permission to enter this uri.");
        chain.doFilter(req, res);
        validator.clear();

    }

    public void init(FilterConfig config) throws ServletException {
        this.config = config;
        String uri = config.getInitParameter(PARAM_URI);
        if (uri != null) {
            this.unsecureUris = uri.split(",");
            for (int i = 0; i < this.unsecureUris.length; i++) {
                if (this.unsecureUris[i] == null || "".equals(this.unsecureUris[i].trim())) {
                    continue;
                }

                this.unsecureUris[i] = this.unsecureUris[i].trim();
            }
        }

        this.debug = Boolean.parseBoolean(config.getInitParameter(DEBUG));

        if (config.getInitParameter(LOGIN_URL) != null) {
            StringBuffer sb = new StringBuffer(config.getInitParameter(LOGIN_URL));
            if (sb.indexOf("?") >= 0) {
                sb.append("&");
            } else {
                sb.append("?");
            }
            sb.append(BACK_TO_URL).append("=");
            this.loginUrl = sb.toString();
        }
    }
}
