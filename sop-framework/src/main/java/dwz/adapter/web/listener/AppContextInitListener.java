package dwz.adapter.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @Author: LCF
 * @Date: 2020/1/8 11:37
 * @Package: dwz.adapter.web.listener
 */

public class AppContextInitListener implements ServletContextListener,
        HttpSessionListener {

    private static final String ETC_FILE = "etc_file";

    public AppContextInitListener() {
    }

    public void contextDestroyed(ServletContextEvent event) {
    }

    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        String configFile = context.getInitParameter(ETC_FILE);
    }

    public void sessionCreated(HttpSessionEvent event) {
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        session.invalidate();
    }
}
