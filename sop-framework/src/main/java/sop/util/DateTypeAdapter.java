package sop.util;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * @Author: LCF
 * @Date: 2020/1/9 11:21
 * @Package: sop.util
 */

public class DateTypeAdapter implements JsonSerializer<Date>,
        JsonDeserializer<Date> {
    public static final String JSON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public JsonElement serialize(Date src, Type arg1,
                                 JsonSerializationContext arg2) {
        DateFormat format = new SimpleDateFormat(JSON_DATE_FORMAT);
        String dateFormatAsString = format.format(new Date(src.getTime()));
        return new JsonPrimitive(dateFormatAsString);
    }

    public Date deserialize(JsonElement json, Type typeOfT,
                            JsonDeserializationContext context) throws JsonParseException {
        DateFormat format = new SimpleDateFormat(JSON_DATE_FORMAT);
        if (!(json instanceof JsonPrimitive)) {
            throw new JsonParseException("The date should be a string value");
        }

        try {
            Date date = format.parse(json.getAsString());
            return date;
        } catch (ParseException e) {
            throw new JsonParseException(e);
        }
    }


}
