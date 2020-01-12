package dwz.plugins;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:35
 * @Package: dwz.plugins
 */

public class StringToDateConverter implements Converter<String, Date> {

    public Date convert(String source) {
        DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateTimeFormat.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
