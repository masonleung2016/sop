package dwz.cache.memcache;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import dwz.cache.CacheManager;
import dwz.cache.memcache.client.ErrorHandler;
import dwz.cache.memcache.client.MemcachedClient;
import dwz.cache.memcache.client.SockIOPool;

/**
 * <strong>Title : MemcacheCacheManager<br></strong>
 * <strong>Description : </strong>
 * 1. 读取配置文件 /memcached.xml
 * 2. 初始化SockIOPool.
 * 3. 初始化MemcacheClient.
 * <br>
 * <strong>Create on : 2011-12-9<br></strong>
 * <p>
 * <strong>Copyright (C) Ecointel Software Co.,Ltd.<br></strong>
 * <p>
 *
 * @author peng.shi peng.shi@ecointellects.com<br>
 * @version <strong>Ecointel v1.0.0</strong><br>
 * <br>
 * <strong>修改历史:</strong><br>
 * 修改人		修改日期		修改描述<br>
 * -------------------------------------------<br>
 * <br>
 * <br>
 */

public class MemcacheCacheManager implements CacheManager {
    private static Logger logger = LoggerFactory.getLogger(MemcacheCacheManager.class);

    private String MEMCACHED_CONFIG = "/memcached.xml";

    private MemcachedClientConfig memcachedClientConfig;
    private MemcachedClientSocketPoolConfig memcachedClientSocketPoolConfig;
    private SockIOPool sockIOPool;
    private MemcachedClient memcachedClient;

    public MemcacheCacheManager() {
        init();
    }

    @SuppressWarnings("unchecked")
    protected void init() {
        try {
            InputStream is = this.getClass().getResourceAsStream(MEMCACHED_CONFIG);
            SAXReader sr = new SAXReader();
            Document doc = sr.read(is);
            Element memcachedElement = doc.getRootElement();
            Element sockIOPoolElement = memcachedElement.element("SockIOPool");
            Assert.notNull(sockIOPoolElement);

            this.memcachedClientConfig = new MemcachedClientConfig();
            this.memcachedClientSocketPoolConfig = new MemcachedClientSocketPoolConfig();

            String strFailover = sockIOPoolElement.attributeValue("failover");
            if (!StringUtils.isEmpty(strFailover) && !StringUtils.isBlank(strFailover)) {
                this.memcachedClientSocketPoolConfig.setFailover(Boolean.parseBoolean(strFailover));
            }
            String strInitConn = sockIOPoolElement.attributeValue("initConn");
            if (!StringUtils.isEmpty(strInitConn) && !StringUtils.isBlank(strInitConn)) {
                this.memcachedClientSocketPoolConfig.setInitConn(Integer.parseInt(strInitConn));
            }
            String strMinConn = sockIOPoolElement.attributeValue("minConn");
            if (!StringUtils.isEmpty(strMinConn) && !StringUtils.isBlank(strMinConn)) {
                this.memcachedClientSocketPoolConfig.setMinConn(Integer.parseInt(strMinConn));
            }
            String strMaxConn = sockIOPoolElement.attributeValue("maxConn");
            if (!StringUtils.isEmpty(strMaxConn) && !StringUtils.isBlank(strMaxConn)) {
                this.memcachedClientSocketPoolConfig.setMaxConn(Integer.parseInt(strMaxConn));
            }
            String strMaintSleep = sockIOPoolElement.attributeValue("maintSleep");
            if (!StringUtils.isEmpty(strMaintSleep) && !StringUtils.isBlank(strMaintSleep)) {
                this.memcachedClientSocketPoolConfig.setMaintSleep(Integer.parseInt(strMaintSleep));
            }
            String strNagle = sockIOPoolElement.attributeValue("nagle");
            if (!StringUtils.isEmpty(strNagle) && !StringUtils.isBlank(strNagle)) {
                this.memcachedClientSocketPoolConfig.setNagle(Boolean.parseBoolean(strNagle));
            }
            String strSocketTo = sockIOPoolElement.attributeValue("socketTo");
            if (!StringUtils.isEmpty(strSocketTo) && !StringUtils.isBlank(strSocketTo)) {
                this.memcachedClientSocketPoolConfig.setSocketTo(Integer.parseInt(strSocketTo));
            }
            String strAliveCheck = sockIOPoolElement.attributeValue("aliveCheck");
            if (!StringUtils.isEmpty(strAliveCheck) && !StringUtils.isBlank(strAliveCheck)) {
                this.memcachedClientSocketPoolConfig.setAliveCheck(Boolean.parseBoolean(strAliveCheck));
            }
            String strMaxIdle = sockIOPoolElement.attributeValue("maxIdle");
            if (!StringUtils.isEmpty(strMaxIdle) && !StringUtils.isBlank(strMaxIdle)) {
                this.memcachedClientSocketPoolConfig.setMaxIdle(Integer.parseInt(strMaxIdle));
            }
            List<Element> serversElements = sockIOPoolElement.elements("Server");
            if (CollectionUtils.isEmpty(serversElements)) {
                throw new ExceptionInInitializerError();
            }
            String[] servers = new String[serversElements.size()];
            Integer[] weights = new Integer[serversElements.size()];
            for (int i = 0; i < serversElements.size(); i++) {
                String strIp = serversElements.get(i).attributeValue("ip");
                if (!StringUtils.isEmpty(strIp) && !StringUtils.isBlank(strIp)) {
                    servers[i] = strIp;
                    this.memcachedClientSocketPoolConfig.setServers(servers);
                } else {
                    throw new ExceptionInInitializerError();
                }
                String strWeight = serversElements.get(i).attributeValue("weight");
                if (!StringUtils.isEmpty(strWeight) && !StringUtils.isBlank(strWeight)) {
                    weights[i] = Integer.parseInt(strWeight);
                    this.memcachedClientSocketPoolConfig.setWeights(weights);
                } else {
                    throw new ExceptionInInitializerError();
                }
            }

            Element memcachedClientElement = memcachedElement.element("MemcachedClient");
            if (memcachedClientElement != null) {
                String strCompressEnable = memcachedClientElement.attributeValue("compressEnable");
                if (!StringUtils.isEmpty(strCompressEnable) && !StringUtils.isBlank(strCompressEnable)) {
                    this.memcachedClientConfig.setCompressEnable(Boolean.parseBoolean(strCompressEnable));
                }
                String strDefaultEncoding = memcachedClientElement.attributeValue("defaultEncoding");
                if (!StringUtils.isEmpty(strDefaultEncoding) && !StringUtils.isBlank(strDefaultEncoding)) {
                    this.memcachedClientConfig.setDefaultEncoding(strDefaultEncoding);
                }
                String strErrorHandler = memcachedClientElement.attributeValue("errorHandler");
                if (!StringUtils.isEmpty(strErrorHandler) && !StringUtils.isBlank(strErrorHandler)) {
                    this.memcachedClientConfig.setErrorHandler(strErrorHandler);
                }
                String strPrimitiveAsString = memcachedClientElement.attributeValue("primitiveAsString");
                if (!StringUtils.isEmpty(strPrimitiveAsString) && !StringUtils.isBlank(strPrimitiveAsString)) {
                    this.memcachedClientConfig.setPrimitiveAsString(Boolean.parseBoolean(strPrimitiveAsString));
                }
                String strCompressThreshold = memcachedClientElement.attributeValue("compressThreshold");
                if (!StringUtils.isEmpty(strCompressThreshold) && !StringUtils.isBlank(strCompressThreshold)) {
                    this.memcachedClientConfig.setCompressThreshold(Long.parseLong(strCompressThreshold));
                }
            } else {
                throw new ExceptionInInitializerError();
            }
            is.close();

            if (logger.isDebugEnabled()) {
                StringBuffer poolSB = new StringBuffer();
                poolSB.append("failover:" + this.memcachedClientSocketPoolConfig.isFailover() + "\n");
                poolSB.append("initConn:" + this.memcachedClientSocketPoolConfig.getInitConn() + "\n");
                poolSB.append("minConn:" + this.memcachedClientSocketPoolConfig.getMinConn() + "\n");
                poolSB.append("maxConn:" + this.memcachedClientSocketPoolConfig.getMaxConn() + "\n");
                poolSB.append("maintSleep:" + this.memcachedClientSocketPoolConfig.getMaintSleep() + "\n");
                poolSB.append("nagle:" + this.memcachedClientSocketPoolConfig.isNagle());
                poolSB.append("socketTo:" + this.memcachedClientSocketPoolConfig.getSocketTo());
                poolSB.append("aliveCheck:" + this.memcachedClientSocketPoolConfig.isAliveCheck());
                poolSB.append("maxIdle:" + this.memcachedClientSocketPoolConfig.getMaxIdle());
                for (int i = 0; i < this.memcachedClientSocketPoolConfig.getServers().length; i++) {
                    poolSB.append("server:" + memcachedClientSocketPoolConfig.getServers()[i] + "weight : " + memcachedClientSocketPoolConfig.getWeights()[i]);
                }

                logger.debug(poolSB.toString());

                StringBuffer clientSB = new StringBuffer();
                clientSB.append("compressEnable:" + memcachedClientConfig.isCompressEnable() + "\n");
                clientSB.append("defaultEncoding:" + memcachedClientConfig.getDefaultEncoding() + "\n");
                clientSB.append("errorHandler:" + memcachedClientConfig.getErrorHandler() + "\n");
                clientSB.append("primitiveAsString:" + memcachedClientConfig.isPrimitiveAsString() + "\n");
                clientSB.append("compressThreshold:" + memcachedClientConfig.getCompressThreshold() + "\n");

                logger.debug(clientSB.toString());
            }

            //init SockIOPool
            this.sockIOPool = SockIOPool.getInstance();
            sockIOPool.setFailover(this.memcachedClientSocketPoolConfig.isFailover());
            sockIOPool.setInitConn(this.memcachedClientSocketPoolConfig.getInitConn());
            sockIOPool.setMinConn(this.memcachedClientSocketPoolConfig.getMinConn());
            sockIOPool.setMaxConn(this.memcachedClientSocketPoolConfig.getMaxConn());
            sockIOPool.setMaintSleep(this.memcachedClientSocketPoolConfig.getMaintSleep());
            sockIOPool.setNagle(this.memcachedClientSocketPoolConfig.isNagle());
            sockIOPool.setAliveCheck(this.memcachedClientSocketPoolConfig.isAliveCheck());
            sockIOPool.setMaxIdle(this.memcachedClientSocketPoolConfig.getMaxIdle());
            sockIOPool.setServers(this.memcachedClientSocketPoolConfig.getServers());
            sockIOPool.setWeights(this.memcachedClientSocketPoolConfig.getWeights());

            //init MemcachedClient
            sockIOPool.initialize();
            this.memcachedClient = new MemcachedClient();
            this.memcachedClient.setCompressEnable(this.memcachedClientConfig.isCompressEnable());
            this.memcachedClient.setCompressThreshold(this.memcachedClientConfig.getCompressThreshold());
            this.memcachedClient.setDefaultEncoding(this.memcachedClientConfig.getDefaultEncoding());
            this.memcachedClient.setErrorHandler((ErrorHandler) Class.forName(this.memcachedClientConfig.getErrorHandler()).newInstance());
            this.memcachedClient.setPrimitiveAsString(this.memcachedClientConfig.isPrimitiveAsString());
            if (logger.isDebugEnabled()) {
                logger.debug("SockIOPool and MemcachedClient Initial Success.");
            }
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    @Override
    public boolean keyExists(String key) {
        return this.memcachedClient.keyExists(key);
    }

    @Override
    public boolean delete(String key) {
        return this.memcachedClient.delete(key);
    }

    @Override
    public boolean delete(String key, Date expiry) {
        return this.memcachedClient.delete(key, expiry);
    }

    @Override
    public boolean delete(String key, Integer hashCode, Date expiry) {
        return this.memcachedClient.delete(key, hashCode, expiry);
    }

    @Override
    public boolean set(String key, Object value) {
        return this.memcachedClient.set(key, value);
    }

    @Override
    public boolean set(String key, Object value, Integer hashCode) {
        return this.memcachedClient.set(key, value, hashCode);
    }

    @Override
    public boolean set(String key, Object value, Date expiry) {
        return this.memcachedClient.set(key, value, expiry);
    }

    @Override
    public boolean set(String key, Object value, Date expiry, Integer hashCode) {
        return this.memcachedClient.set(key, value, expiry, hashCode);
    }

    @Override
    public boolean add(String key, Object value) {
        return this.memcachedClient.add(key, value);
    }

    @Override
    public boolean add(String key, Object value, Integer hashCode) {
        return this.memcachedClient.add(key, value, hashCode);
    }

    @Override
    public boolean add(String key, Object value, Date expiry) {
        return this.memcachedClient.add(key, value, expiry);
    }

    @Override
    public boolean add(String key, Object value, Date expiry, Integer hashCode) {
        return this.memcachedClient.add(key, value, expiry, hashCode);
    }

    @Override
    public boolean replace(String key, Object value) {
        return this.memcachedClient.replace(key, value);
    }

    @Override
    public boolean replace(String key, Object value, Integer hashCode) {
        return this.memcachedClient.replace(key, value, hashCode);
    }

    @Override
    public boolean replace(String key, Object value, Date expiry) {
        return this.memcachedClient.replace(key, value, expiry);
    }

    @Override
    public boolean replace(String key, Object value, Date expiry, Integer hashCode) {
        return this.memcachedClient.replace(key, value, expiry, hashCode);
    }

    @Override
    public boolean storeCounter(String key, long counter) {
        return this.memcachedClient.storeCounter(key, counter);
    }

    @Override
    public boolean storeCounter(String key, Long counter) {
        return this.memcachedClient.storeCounter(key, counter);
    }

    @Override
    public boolean storeCounter(String key, Long counter, Integer hashCode) {
        return this.memcachedClient.storeCounter(key, counter, hashCode);
    }

    @Override
    public long getCounter(String key) {
        return this.memcachedClient.getCounter(key);
    }

    @Override
    public long getCounter(String key, Integer hashCode) {
        return this.memcachedClient.getCounter(key, hashCode);
    }

    @Override
    public long addOrIncr(String key) {
        return this.memcachedClient.addOrIncr(key);
    }

    @Override
    public long addOrIncr(String key, long inc) {
        return this.memcachedClient.addOrIncr(key, inc);
    }

    @Override
    public long addOrIncr(String key, long inc, Integer hashCode) {
        return this.memcachedClient.addOrIncr(key, inc, hashCode);
    }

    @Override
    public long addOrDecr(String key) {
        return this.memcachedClient.addOrDecr(key);
    }

    @Override
    public long addOrDecr(String key, long inc) {
        return this.memcachedClient.addOrDecr(key, inc);
    }

    @Override
    public long addOrDecr(String key, long inc, Integer hashCode) {
        return this.memcachedClient.addOrDecr(key, inc, hashCode);
    }

    @Override
    public long incr(String key) {
        return this.memcachedClient.incr(key);
    }

    @Override
    public long incr(String key, long inc) {
        return this.memcachedClient.incr(key, inc);
    }

    @Override
    public long incr(String key, long inc, Integer hashCode) {
        return this.memcachedClient.incr(key, inc, hashCode);
    }

    @Override
    public long decr(String key) {
        return this.memcachedClient.decr(key);
    }

    @Override
    public long decr(String key, long inc) {
        return this.memcachedClient.decr(key, inc);
    }

    @Override
    public long decr(String key, long inc, Integer hashCode) {
        return this.memcachedClient.decr(key, inc, hashCode);
    }

    @Override
    public Object get(String key) {
        return this.memcachedClient.get(key);
    }

    @Override
    public Object get(String key, Integer hashCode) {
        return this.memcachedClient.get(key, hashCode);
    }

    @Override
    public Object get(String key, Integer hashCode, boolean asString) {
        return this.memcachedClient.get(key, hashCode, asString);
    }

    @Override
    public Object[] getMultiArray(String[] keys) {
        return this.memcachedClient.getMultiArray(keys);
    }

    @Override
    public Object[] getMultiArray(String[] keys, Integer[] hashCodes) {
        return this.memcachedClient.getMultiArray(keys, hashCodes);
    }

    @Override
    public Object[] getMultiArray(String[] keys, Integer[] hashCodes, boolean asString) {
        return this.memcachedClient.getMultiArray(keys, hashCodes, asString);
    }

    @Override
    public Map<String, Object> getMulti(String[] keys) {
        return this.memcachedClient.getMulti(keys);
    }

    @Override
    public Map<String, Object> getMulti(String[] keys, Integer[] hashCodes) {
        return this.memcachedClient.getMulti(keys, hashCodes);
    }

    @Override
    public Map<String, Object> getMulti(String[] keys, Integer[] hashCodes, boolean asString) {
        return this.memcachedClient.getMulti(keys, hashCodes, asString);
    }

    @Override
    public boolean flushAll() {
        return this.memcachedClient.flushAll();
    }

    @Override
    public boolean flushAll(String[] servers) {
        return this.memcachedClient.flushAll(servers);
    }

    @Override
    public Map stats() {
        return this.memcachedClient.stats();
    }

    @Override
    public Map stats(String[] servers) {
        return this.memcachedClient.stats(servers);
    }

    @Override
    public Map statsItems() {
        return this.memcachedClient.statsItems();
    }

    @Override
    public Map statsItems(String[] servers) {
        return this.memcachedClient.statsItems(servers);
    }

    @Override
    public Map statsSlabs() {
        return this.memcachedClient.statsSlabs();
    }

    @Override
    public Map statsSlabs(String[] servers) {
        return this.memcachedClient.statsSlabs(servers);
    }

    @Override
    public Map statsCacheDump(int slabNumber, int limit) {
        return this.memcachedClient.statsCacheDump(slabNumber, limit);
    }

    @Override
    public Map statsCacheDump(String[] servers, int slabNumber, int limit) {
        return this.memcachedClient.statsCacheDump(servers, slabNumber, limit);
    }

}
