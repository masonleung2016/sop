package dwz.common.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @Author: LCF
 * @Date: 2020/1/8 16:14
 * @Package: dwz.common.util
 */

public class TemplateUtil {

    @SuppressWarnings("rawtypes")
    public static String template2String(String templateContent, Map map) {
        Assert.hasText(templateContent);
        if (map == null) {
            map = new HashMap();
        }

        Template t = null;
        try {
            Configuration config = new Configuration();
            t = new Template("", new StringReader(templateContent), config);
            StringWriter writer = new StringWriter();
            t.process(map, writer);
            return writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (TemplateException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
