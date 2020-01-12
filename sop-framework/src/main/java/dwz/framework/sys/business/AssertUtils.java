package dwz.framework.sys.business;

/**
 * @Author: LCF
 * @Date: 2020/1/8 16:55
 * @Package: dwz.framework.sys.business
 */

public class AssertUtils {

    /**
     * Assert为非新业务对象
     *
     * @param businessObject
     */
    public static void notNewBusinessObject(BusinessObject businessObject) {
        if (businessObject.isNew()) {
            throw new java.lang.IllegalArgumentException();
        }
    }

    /**
     * Assert为新业务对象
     *
     * @param businessObject
     */
    public static void newBusinessObject(BusinessObject businessObject) {
        if (!businessObject.isNew()) {
            throw new java.lang.IllegalArgumentException();
        }
    }

}
