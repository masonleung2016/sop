package dwz.dal;

import java.io.Serializable;

import org.springframework.util.Assert;

/**
 * @Author: LCF
 * @Date: 2020/1/8 16:35
 * @Package: dwz.dal
 */

public class DalUtils {

    /**
     * 判断是否DO对象是否存在
     *
     * @param mapper
     * @param key
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static boolean isExist(BaseMapper mapper, Serializable key) {
        Assert.notNull(mapper);
        Assert.notNull(key);
        Object o = mapper.load(key);
        if (o == null) {
            return false;
        }
        return true;
    }

}
