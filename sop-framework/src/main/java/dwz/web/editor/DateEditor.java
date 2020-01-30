package dwz.web.editor;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:36
 * @Package: dwz.web.editor
 */

public class DateEditor extends PropertyEditorSupport {
    
    @Override
    public String getAsText() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Object obj = getValue();
        if (obj == null) {
            return null;
        } else {
            return sdf.format((Date) obj);
        }
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        if (!StringUtils.hasText(text)) {
            setValue(null);
        } else {
            setValue(DateUtil.string2Date(text, "yyyy-MM-dd"));
        }
    }
}
