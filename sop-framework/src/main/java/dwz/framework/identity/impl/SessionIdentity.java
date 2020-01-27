package dwz.framework.identity.impl;

import java.io.Serializable;

import dwz.framework.identity.Identity;

/**
 * @Author: LCF
 * @Date: 2020/1/8 16:40
 * @Package: dwz.framework.identity.impl
 */

public class SessionIdentity implements Identity {

    private Serializable accessToken = null;

    public SessionIdentity(Serializable accessToken) {
        this.accessToken = accessToken;
    }

    public Serializable getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(Serializable accessToken) {
        this.accessToken = accessToken;
    }
}
