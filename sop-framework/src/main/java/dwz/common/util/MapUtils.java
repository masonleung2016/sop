package dwz.common.util;

import java.util.Map;

/**
 * @Author: LCF
 * @Date: 2020/1/8 16:03
 * @Package: dwz.common.util
 */

public class MapUtils {
    @SuppressWarnings("all")
    public static void putIfNull(Map map, Object key, Object defaultValue) {
        if (key == null)
            throw new IllegalArgumentException("key must be not null");
        if (map == null)
            throw new IllegalArgumentException("map must be not null");
        if (map.get(key) == null) {
            map.put(key, defaultValue);
        }
    }
}
