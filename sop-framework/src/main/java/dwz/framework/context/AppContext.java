package dwz.framework.context;

import dwz.framework.user.User;

/**
 * @Author: LCF
 * @Date: 2020/1/8 16:37
 * @Package: dwz.framework.context
 */
public interface AppContext {

    User getUser();

    void setUser(User user);

}
