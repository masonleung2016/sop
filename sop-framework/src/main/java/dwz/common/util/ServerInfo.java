package dwz.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: LCF
 * @Date: 2020/1/8 16:12
 * @Package: dwz.common.util
 */

public class ServerInfo {
    public static boolean isAjax(HttpServletRequest request) {
        if (request != null
                && "XMLHttpRequest".equalsIgnoreCase(request
                .getHeader("X-Requested-With")))
            return true;
        return false;
    }
}
