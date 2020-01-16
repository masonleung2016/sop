package dwz.cache.memcache;

/**
 * <strong>MemcachedClientSocketPoolConfig</strong><br>
 * SocketIO Pool的配置<br>
 * <strong>Create on : 2011-12-11<br></strong>
 * <p>
 * <strong>Copyright (C) Ecointel Software Co.,Ltd.<br></strong>
 * <p>
 *
 * @author peng.shi peng.shi@ecointel.com.cn<br>
 * @version <strong>Ecointel v1.0.0</strong><br>
 */

public class MemcachedClientSocketPoolConfig {
    private boolean failover = true;
    private int initConn = 10;
    private int minConn = 5;
    private int maxConn = 250;
    /**
     * 这个参数很重要，检查Pool,对于连接池有维护的作用,ms作为单位
     */
    private int maintSleep = 1000 * 3;
    private boolean nagle = false;
    /**
     * Socket TimeOut配置
     */
    private int socketTo = 3000;
    /**
     * socket在处理前是否需要作心跳交验
     */
    private boolean aliveCheck = true;
    /**
     * max idle time in ms
     */
    private int maxIdle = 3 * 1000;

    /**
     * 分布的memcached服务器的列表字段，用逗号分割，服务器地址加端口号
     */
    private String[] servers;
    /**
     * 是否需要设置这些服务器的权重
     */
    private Integer[] weights;

    public boolean isFailover() {
        return failover;
    }

    public void setFailover(boolean failover) {
        this.failover = failover;
    }

    public int getInitConn() {
        return initConn;
    }

    public void setInitConn(int initConn) {
        this.initConn = initConn;
    }

    public int getMinConn() {
        return minConn;
    }

    public void setMinConn(int minConn) {
        this.minConn = minConn;
    }

    public int getMaxConn() {
        return maxConn;
    }

    public void setMaxConn(int maxConn) {
        this.maxConn = maxConn;
    }

    public int getMaintSleep() {
        return maintSleep;
    }

    public void setMaintSleep(int maintSleep) {
        this.maintSleep = maintSleep;
    }

    public boolean isNagle() {
        return nagle;
    }

    public void setNagle(boolean nagle) {
        this.nagle = nagle;
    }

    public int getSocketTo() {
        return socketTo;
    }

    public void setSocketTo(int socketTo) {
        this.socketTo = socketTo;
    }

    public boolean isAliveCheck() {
        return aliveCheck;
    }

    public void setAliveCheck(boolean aliveCheck) {
        this.aliveCheck = aliveCheck;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public String[] getServers() {
        return servers;
    }

    public void setServers(String[] servers) {
        this.servers = servers;
    }

    public Integer[] getWeights() {
        return weights;
    }

    public void setWeights(Integer[] weights) {
        this.weights = weights;
    }
}
