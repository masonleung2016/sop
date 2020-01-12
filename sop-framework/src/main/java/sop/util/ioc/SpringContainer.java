package sop.util.ioc;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:18
 * @Package: sop.util.ioc
 */

public class SpringContainer implements ApplicationContextAware {
    private static final Object mutex = new Object();
    private static volatile SpringContainer springC;
    private static ApplicationContext appCtx;

    /**
     * Create instance of Spring Container should be called by
     * Spring framework only.
     *
     * @return New instance of SpringContainer
     */
    public static SpringContainer createInstance() {
        if (springC == null) {
            synchronized (mutex) {
                springC = new SpringContainer();
            }
        }
        return springC;
    }

    /**
     * @return Init instance of SpringContainer
     */
    public static SpringContainer getContainer() {
        return createInstance();
    }

    /**
     * Check to seek contain bean
     *
     * @param id Bean id
     * @return true if contain bean
     */
    public boolean containBean(String id) {
        return getAppCtx().containsBean(id);
    }

    /**
     * Return the bean contained with container
     *
     * @param id Reference to the bean
     * @return Bean instance
     */
    public Object getBean(String id) {
        return getAppCtx().getBean(id);
    }

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        appCtx = applicationContext;
    }

    /**
     * @return the value for appCtx
     */
    public ApplicationContext getAppCtx() {
        return appCtx;
    }
}
