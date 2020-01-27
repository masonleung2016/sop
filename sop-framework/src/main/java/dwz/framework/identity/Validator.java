package dwz.framework.identity;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dwz.framework.config.Constants;
import dwz.framework.context.AppContext;
import dwz.framework.context.AppContextHolder;
import dwz.framework.context.DefaultAppContext;
import dwz.framework.identity.impl.SessionIdentity;
import dwz.framework.user.User;

/**
 * @Author: LCF
 * @Date: 2020/1/8 16:42
 * @Package: dwz.framework.identity
 */

public class Validator implements IdentityProvider {

    private static final Log log = LogFactory.getLog(Validator.class);

    private static ThreadLocal<Validator> validatorHolder = new ThreadLocal<Validator>() {

        protected Validator initialValue() {
            return new Validator();
        }
    };

    private HttpSession session = null;

    private User user = null;

    private Validator() {
    }

    public static Validator getInstance() {
        return validatorHolder.get();
    }

    public void init(HttpSession session) {
        this.session = session;
    }

    public boolean validate() {
        log.debug("will validate session.");
        if (session == null) {
            log.warn("the session is null.");
            return false;
        }

        boolean expired = false;

        try {
            this.user = (User) session
                    .getAttribute(Constants.AUTHENTICATION_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (this.user == null) {
            expired = true;
        }
        if (!expired) {
            if (log.isDebugEnabled()) {
                log.debug("validating session successfully.");
            }

            log.debug("validate account successfully.");

            return true;
        }

        log.debug("validating session failed.");

        return false;
    }

    public void confirm() {
        if (this.user == null) {
            throw new IllegalArgumentException("authentication is null.");
        }

        AppContext context = AppContextHolder.getContext();
        if (context == null) {
            context = new DefaultAppContext();
            AppContextHolder.setContext(context);
        }
        context.setUser(user);
    }

    public void cancel() {
        this.session = null;
        this.user = null;
        AppContext context = AppContextHolder.getContext();
        if (context != null) {
            context.setUser(null);
        }
    }

    public void clear() {
        this.session = null;
        this.user = null;
        AppContext context = AppContextHolder.getContext();
        if (context != null) {
            context.setUser(null);
        }
    }

    public Identity createIdentity(String identityString) {
        if (identityString == null) {
            return null;
        }
        return new SessionIdentity(identityString);
    }
}
