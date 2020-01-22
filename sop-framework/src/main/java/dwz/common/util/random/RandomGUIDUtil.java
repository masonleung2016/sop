package dwz.common.util.random;

/**
 * Created on 2007-1-11
 * <p>
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class RandomGUIDUtil {

    /**
     * 产生唯一的随机字符串
     *
     * @return
     */
    
    public static String generateKey() {
        return new RandomGUID(true).toString();
    }
}
