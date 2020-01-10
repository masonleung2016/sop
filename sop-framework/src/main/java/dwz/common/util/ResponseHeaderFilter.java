package dwz.common.util;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: LCF
 * @Date: 2020/1/8 16:09
 * @Package: dwz.common.util
 * <p>
 * 为Response设置Expires等Header的Filter.
 * <p>
 * eg.在web.xml中设置
 * <filter>
 * <filter-name>expiresHeaderFilter</filter-name>
 * <filter-class>org.springside.modules.web.ResponseHeaderFilter</filter-class>
 * <init-param>
 * <param-name>Cache-Control</param-name>
 * <param-value>public, max-age=31536000</param-value>
 * </init-param>
 * </filter>
 * <filter-mapping>
 * <filter-name>expiresHeaderFilter</filter-name>
 * <url-pattern>/img/*</url-pattern>
 * </filter-mapping>
 */

public class ResponseHeaderFilter implements Filter {

    private FilterConfig fc;

    /**
     * 设置Filter在web.xml中定义的所有参数到Response.
     */
    @SuppressWarnings("unchecked")
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        HttpServletResponse response = (HttpServletResponse) res;

        // set the provided HTTP response parameters
        for (Enumeration<Object> e = fc.getInitParameterNames(); e.hasMoreElements(); ) {
            String headerName = (String) e.nextElement();
            response.addHeader(headerName, fc.getInitParameter(headerName));
        }

        // pass the request/response on
        chain.doFilter(req, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig filterConfig) {
        this.fc = filterConfig;
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }
}

