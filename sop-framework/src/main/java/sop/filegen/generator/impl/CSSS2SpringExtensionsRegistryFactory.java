package sop.filegen.generator.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ListableBeanFactory;

import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.extensions.SpringExtensionsRegistryFactory;
import sop.util.ioc.SpringContainer;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:46
 * @Package: sop.filegen.generator.impl
 */

public class CSSS2SpringExtensionsRegistryFactory extends SpringExtensionsRegistryFactory {
    private Logger logger = LoggerFactory.getLogger(CSSS2SpringExtensionsRegistryFactory.class);

    //private static FileSystemXmlApplicationContext ctx = null;
    @Override
    protected ListableBeanFactory getBeanFactory(String registryId, JRPropertiesMap properties) {
		/*if(ctx == null){
			String resourceProp = DefaultExtensionsRegistry.PROPERTY_REGISTRY_PREFIX + registryId + PROPERTY_SUFFIX_SPRING_BEANS_RESOURCE;
			String resource = properties.getProperty(resourceProp);
			if (resource == null)
			{
				throw new JRRuntimeException("No Spring resource property set");
			}

			//URL resourceLocation = JRLoader.getResource(resource);
			String resourceLocation = "file:"+ System.getProperty("rrr.config.path")+"/springConfig/"+resource;
			if (resourceLocation == null)
			{
				throw new JRRuntimeException("Could not find Spring resource "
						+ resource + " for extensions registry " + registryId);
			}

			logger.info("Creating Spring beans factory for extensions registry " + registryId + " using "+ resourceLocation);
			ctx = new FileSystemXmlApplicationContext(resourceLocation);
		}

		return ctx;*/
        properties.setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");
        return SpringContainer.getContainer().getAppCtx();
    }
}
