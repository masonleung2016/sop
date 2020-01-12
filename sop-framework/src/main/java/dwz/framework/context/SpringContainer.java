package dwz.framework.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @Author: LCF
 * @Date: 2020/1/8 16:38
 * @Package: dwz.framework.context
 */

public class SpringContainer implements ApplicationContextAware {

    private static ApplicationContext ctx;

    public static Object getBean(String beanName) {
        return ctx.getBean(beanName);
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx)
            throws BeansException {
        SpringContainer.ctx = ctx;
    }

}
