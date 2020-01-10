package dwz.common.util;

/**
 * @Author: LCF
 * @Date: 2020/1/8 15:56
 * @Package: dwz.common.util
 */
public class EnumUtils {
    public static boolean isDefined(Enum<?>[] ems, String emStr) {
        for (Enum<?> em : ems) {
            if (em.toString().equals(emStr)) return true;
        }
        return false;
    }
}
