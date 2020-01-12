package sop.filegen.generator.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;


/**
 * @Author: LCF
 * @Date: 2020/1/8 18:00
 * @Package: sop.filegen.generator.impl
 */

public class MailMergeUtil {

    protected static final Logger logger = LoggerFactory.getLogger(MailMergeUtil.class);

    public static FieldsMetadata getFieldsMetadata(Object object) throws Exception {
        FieldsMetadata metadata = new FieldsMetadata();
        if (!(object instanceof Map)) {
            object = ObjectToMap(object);
        }
        Map map = (Map) object;
        metadata = getFieldsMetadataByMap(map);
        printMetadataLog(metadata);
        return metadata;
    }

    public static FieldsMetadata getFieldsMetadataByMap(Map<String, Object> keyAndValue) throws Exception {
        FieldsMetadata metadata = new FieldsMetadata();
        Set<String> keys = keyAndValue.keySet();
        for (String key : keys) {
            logger.debug("key:" + key);
            Object object = keyAndValue.get(key);
            if (object instanceof List) {
                List list = (List) object;
                if (list != null && list.size() > 0) {
                    Object obj = list.get(0);
                    if (!(object instanceof Map)) {
                        object = ObjectToMap(object);
                    }
                    Map<String, Object> map = (Map<String, Object>) object;
                    Set<String> names = map.keySet();
                    for (String name : names) {
                        metadata.addFieldAsList(key + "." + name);
                    }

                }
            }
        }

        return metadata;
    }


    private static FieldsMetadata getFieldsMetadataByMap(FieldsMetadata metadata, String objName, Map<String, Object> map) {
        Set<String> keys = map.keySet();
        for (String key : keys) {
            metadata.addFieldAsList(objName + "." + key);
        }
        return metadata;
    }

    private static FieldsMetadata getFieldsMetadataByObj(FieldsMetadata metadata, String objName, Object object) throws Exception {
        Class<?> clz = object.getClass();
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            metadata.addFieldAsList(objName + "." + field.getName());
        }
        FieldsMetadata metadata2 = getFieldsMetadata(object);
        metadata = mergeFieldsMetadata(metadata, metadata2);

        return metadata;
    }

    public static FieldsMetadata mergeFieldsMetadata(FieldsMetadata metadata1, FieldsMetadata metadata2) {
        Collection<String> c = metadata2.getFieldsAsList();
        for (String fieldName : c) {
            metadata1.addFieldAsList(fieldName);
        }
        return metadata1;
    }

    public static Map<String, Object> ObjectToMap(Object object) throws Exception {
        if (object instanceof Map) {
            Map map = (Map) object;
            return map;
        }

        Map<String, Object> keyAndValue = new HashMap<String, Object>();
        Class<?> clz = object.getClass();
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
            Object obj = m.invoke(object);
            keyAndValue.put(field.getName(), obj);
        }
        return keyAndValue;
    }

    // set the first char Upper、
    private static String getMethodName(String fildeName) {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

    private static void printMetadataLog(FieldsMetadata metadata) {
        logger.debug("============================");
        Collection<String> c = metadata.getFieldsAsList();
        for (String name : c) {
            logger.debug("metadata:" + name);
        }
    }

    public static void main(String[] args) {
        TestDTO dto = new TestDTO();
        TestDTO dto2 = new TestDTO();
        TestDTO dto3 = new TestDTO();
        dto.getDtoList().add(dto2);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key1", "v");
        map.put("key2", "v");
        map.put("key3", "v");
        map.put("key4", "v");
        map.put("key5", "v");
        dto2.getMapList().add(map);
        dto2.getDtoList().add(dto3);

        dto.getMapList().add(map);
        FieldsMetadata metadata = null;
        try {
            metadata = MailMergeUtil.getFieldsMetadata(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        printMetadataLog(metadata);
    }
}


