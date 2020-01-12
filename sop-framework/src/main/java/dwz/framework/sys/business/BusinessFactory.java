package dwz.framework.sys.business;

import org.springframework.util.Assert;

import dwz.framework.spring.SpringContextHolder;

/**
 * @Author: LCF
 * @Date: 2020/1/8 16:56
 * @Package: dwz.framework.sys.business
 */

public class BusinessFactory {

    private static BusinessFactory bf;

    private BusinessFactory() {

    }

    public static BusinessFactory getInstance() {
        if (bf == null) {
            synchronized (BusinessFactory.class) {
                bf = new BusinessFactory();
            }
        }
        return bf;
    }

    @SuppressWarnings("unchecked")
    public <T extends BusinessObjectServiceMgr> T getService(String serviceName) {
        Assert.hasText(serviceName);
        return (T) SpringContextHolder.getBean(serviceName);
    }

}
