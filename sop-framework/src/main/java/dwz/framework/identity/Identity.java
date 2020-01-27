package dwz.framework.identity;

import java.io.Serializable;

/**
 * @Author: LCF
 * @Date: 2020/1/8 16:40
 * @Package: dwz.framework.identity.impl
 */

/**
 * @pdOid a64765cd-61d6-4709-baaa-e6ca5d5593de
 */
public interface Identity {

    /**
     * @pdOid dc335790-0351-46c6-b1a5-b937ee997389
     */
    public Serializable getAccessToken();

    /**
     * @param token
     * @pdOid 6934fe46-ebee-414f-a403-b2834c3086b2
     */
    public void setAccessToken(Serializable accessToken);
}
