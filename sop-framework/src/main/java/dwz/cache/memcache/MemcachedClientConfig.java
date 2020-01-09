package dwz.cache.memcache;

/**
 * <strong>MemcachedClientConfig</strong><br>
 * Memcache的客户端配置<br>
 * <strong>Create on : 2011-12-12<br></strong>
 * <p>
 * <strong>Copyright (C) Ecointel Software Co.,Ltd.<br></strong>
 * <p>
 *
 * @author peng.shi peng.shi@ecointel.com.cn<br>
 * @version <strong>Ecointel v1.0.0</strong><br>
 */

public class MemcachedClientConfig {
    /**
     * 是否需要压缩
     */
    private boolean compressEnable = true;
    /**
     * 默认编码方式UTF-8
     */
    private String defaultEncoding = "utf-8";
    /**
     * 处理错误的类名，需要全路径
     */
    private String errorHandler = "cn.com.ecointel.memcache.DefaultErrorHandlerImpl";

    /**
     * 默认Key类型为String
     */
    private boolean primitiveAsString = true;

    /**
     * 超过这个值启动压缩
     */
    private long compressThreshold = 32 * 1024;

    public long getCompressThreshold() {
        return compressThreshold;
    }

    public void setCompressThreshold(long compressThreshold) {
        this.compressThreshold = compressThreshold;
    }

    public boolean isPrimitiveAsString() {
        return primitiveAsString;
    }

    public void setPrimitiveAsString(boolean primitiveAsString) {
        this.primitiveAsString = primitiveAsString;
    }

    public boolean isCompressEnable() {
        return compressEnable;
    }

    public void setCompressEnable(boolean compressEnable) {
        this.compressEnable = compressEnable;
    }

    public String getDefaultEncoding() {
        return defaultEncoding;
    }

    public void setDefaultEncoding(String defaultEncoding) {
        this.defaultEncoding = defaultEncoding;
    }

    public String getErrorHandler() {
        return errorHandler;
    }

    public void setErrorHandler(String errorHandler) {
        this.errorHandler = errorHandler;
    }

}
