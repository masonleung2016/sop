package dwz.framework.sys.business;

import java.io.Serializable;

/**
 * @Author: LCF
 * @Date: 2020/1/8 16:57
 * @Package: dwz.framework.sys.business
 */

public interface BusinessObject extends Serializable {

    Serializable getId();

    boolean isNew();

    String getCacheKey();
}
