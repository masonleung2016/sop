package dwz.common.util.security.springsecurity;

import java.util.LinkedHashMap;

/**
 * @Author: LCF
 * @Date: 2020/1/8 15:40
 * @Package: dwz.common.util.security.springsecurity
 */

public interface ResourceDetailsService {

    /**
     * 返回带顺序的URL-授权关系Map, key为受保护的URL, value为能访问该URL的授权名称列表,以','分隔.
     */
    
    public LinkedHashMap<String, String> getRequestMap() throws Exception; //NOSONAR
}
