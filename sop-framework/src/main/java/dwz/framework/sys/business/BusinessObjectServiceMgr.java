package dwz.framework.sys.business;

import java.util.List;
import java.util.Set;

import dwz.cache.CacheManager;

/**
 * @Author: LCF
 * @Date: 2020/1/8 16:57
 * @Package: dwz.framework.sys.business
 */

public interface BusinessObjectServiceMgr {

    /**
     * 获得当前配置的MemcacheManager
     *
     * @return
     */
    CacheManager getMemCacheManager();

    /**
     * 设定业务对象到Cache
     *
     * @param object
     */
    <T extends BusinessObject> void setInCache(T t);

    /**
     * 从Cache中删除业务对象
     *
     * @param object
     */
    <T extends BusinessObject> void deleteFromCache(T t);

    /**
     * 从Cache中获取业务对象
     *
     * @param clazz
     * @param id
     * @return
     */
    <T extends BusinessObject> T getFromCache(Class<T> clazz, java.io.Serializable id);

    /**
     * 过滤掉New BusinessObject
     *
     * @param ts
     * @return
     */
    <T extends BusinessObject> Set<T> filterNewBusinessObject(List<T> ts);

    /**
     * 过滤掉Not New BusinessObject
     *
     * @param ts
     * @return
     */
    <T extends BusinessObject> Set<T> filterNotNewBusinessObject(List<T> ts);

    /**
     * 获取业务对象ID集合
     *
     * @param bos
     * @return
     */
    <T extends BusinessObject> List<java.io.Serializable> businessObject2Ids(List<T> bos);

}
