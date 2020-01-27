package dwz.framework.context;

import dwz.framework.user.User;

/**
 * @Author: LCF
 * @Date: 2020/1/8 16:38
 * @Package: dwz.framework.context
 */

public class DefaultAppContext implements AppContext {

    private User user = null;

    public DefaultAppContext() {
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
