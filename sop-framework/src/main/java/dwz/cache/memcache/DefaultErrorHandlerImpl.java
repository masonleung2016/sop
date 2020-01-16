package dwz.cache.memcache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dwz.cache.memcache.client.ErrorHandler;
import dwz.cache.memcache.client.MemcachedClient;

/**
 * <strong>DefaultErrorHandlerImpl</strong><br>
 * 默认Memecached ErrorHandler 实现<br>
 * <strong>Create on : 2011-12-12<br></strong>
 * <p>
 * <strong>Copyright (C) Ecointel Software Co.,Ltd.<br></strong>
 * <p>
 *
 * @author peng.shi peng.shi@ecointel.com.cn<br>
 * @version <strong>Ecointel v1.0.0</strong><br>
 */

public class DefaultErrorHandlerImpl implements ErrorHandler {

    private static final Log Logger = LogFactory.getLog(DefaultErrorHandlerImpl.class);

    @Override
    public void handleErrorOnInit(MemcachedClient client, Throwable error) {
        Logger.error("ErrorOnInit", error);
    }

    @Override
    public void handleErrorOnGet(MemcachedClient client, Throwable error, String cacheKey) {
        Logger.error(new StringBuilder("ErrorOnGet, cacheKey: ").append(cacheKey).toString(), error);
    }

    @Override
    public void handleErrorOnGet(MemcachedClient client, Throwable error, String[] cacheKeys) {
        Logger.error(new StringBuilder("ErrorOnGet, cacheKey: ").append(cacheKeys).toString(), error);
    }

    @Override
    public void handleErrorOnSet(MemcachedClient client, Throwable error, String cacheKey) {
        Logger.error(new StringBuilder("ErrorOnSet, cacheKey: ").append(cacheKey).toString(), error);
    }

    @Override
    public void handleErrorOnDelete(MemcachedClient client, Throwable error, String cacheKey) {
        Logger.error(new StringBuilder("ErrorOnDelete, cacheKey: ").append(cacheKey).toString(), error);
    }

    @Override
    public void handleErrorOnFlush(MemcachedClient client, Throwable error) {
        Logger.error("ErrorOnFlush", error);
    }

    @Override
    public void handleErrorOnStats(MemcachedClient client, Throwable error) {
        Logger.error("ErrorOnStats", error);
    }
}
