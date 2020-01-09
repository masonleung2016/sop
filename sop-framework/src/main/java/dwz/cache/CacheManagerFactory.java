package dwz.cache;

import dwz.cache.memcache.MemcacheCacheManager;

/**
 * <strong>CacheManagerFactory</strong><br>
 * <br>
 * <strong>Create on : 2012-2-15<br></strong>
 * <p>
 * <strong>Copyright (C) Ecointel Software Co.,Ltd.<br></strong>
 * <p>
 *
 * @author peng.shi peng.shi@ecointel.com.cn<br>
 * @version <strong>ecointel-epp v1.0.0</strong><br>
 */

public class CacheManagerFactory {

    private static CacheManagerFactory cmf = null;

    private CacheManagerFactory() {
    }

    /**
     * 获取CacheManagerFactory实例
     *
     * @return
     */
    public static CacheManagerFactory getInstance() {
        if (cmf == null) {
            cmf = new CacheManagerFactory();
        }
        return cmf;
    }

    /**
     * 获取MemcachedManager
     *
     * @return
     */
    public CacheManager getMemCacheManager() {
        return new MemcacheCacheManager();
    }

}
