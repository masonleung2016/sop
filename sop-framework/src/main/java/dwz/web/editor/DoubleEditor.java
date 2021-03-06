package dwz.web.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:37
 * @Package: dwz.web.editor
 */

public class DoubleEditor extends PropertyEditorSupport {
    
    @Override
    public String getAsText() {

        return getValue().toString();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.equals(""))
            text = "0";
        if (!StringUtils.hasText(text)) {
            setValue(null);
        } else {
            setValue(Double.parseDouble(text));
            // 这句话是最重要的，他的目的是通过传入参数的类型来匹配相应的databind
        }
    }
}
