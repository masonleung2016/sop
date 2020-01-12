package dwz.dal.common.sequence;

import java.util.UUID;

/**
 * @Author: LCF
 * @Date: 2020/1/8 16:20
 * @Package: dwz.dal.common.sequence
 */

public class UUIDGenerator {

    public static String nextId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
