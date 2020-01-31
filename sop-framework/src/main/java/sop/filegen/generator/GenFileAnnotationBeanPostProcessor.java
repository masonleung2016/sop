package sop.filegen.generator;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

import sop.filegen.GenFile;
import sop.filegen.GenFileFunction;

/**
 * @Author: LCF
 * @Date: 2020/1/8 18:02
 * @Package: sop.filegen.generator
 */

public class GenFileAnnotationBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {
    private Map<String, Object> genFileMap = new HashMap<String, Object>();

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public Map<String, Object> getGenFileMap() {
        return genFileMap;
    }

    @Override
    public boolean postProcessAfterInstantiation(final Object bean, String beanName) throws BeansException {
        if (bean instanceof GenFileFunction) {
            GenFile cfg = bean.getClass().getAnnotation(GenFile.class);
            if (cfg != null) {
                String templateId = cfg.value();
                genFileMap.put(templateId, bean);
                logger.info("Putting GenFileFunction implementation bean to genFileMap. [" + templateId + ", " + beanName + "]");
            }
        }
        return true;
    }
}
