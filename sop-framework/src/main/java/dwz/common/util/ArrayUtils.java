package dwz.common.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: LCF
 * @Date: 2020/1/8 15:49
 * @Package: dwz.common.util
 */

public class ArrayUtils {

    private ArrayUtils() {
    }

    /**
     * 将一个array转为根据keys转为map
     *
     * @param array
     * @param keys
     * @return
     */
    @SuppressWarnings("all")
    public static Map toMap(Object[] array, String... keys) {
        if (array == null) return new HashMap();
        Map m = new LinkedHashMap();
        for (int i = 0; i < keys.length; i++) {
            if (array.length == i) {
                break;
            }
            m.put(keys[i], array[i]);
        }
        return m;
    }

}
