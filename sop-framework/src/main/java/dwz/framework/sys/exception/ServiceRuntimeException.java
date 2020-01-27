package dwz.framework.sys.exception;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:00
 * @Package: dwz.framework.sys.exception
 */
public class ServiceRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -6879298762342455L;

    public ServiceRuntimeException() {
        super();
    }

    public ServiceRuntimeException(String message) {
        super(message);
    }

    public ServiceRuntimeException(Throwable cause) {
        super(cause);
    }

    public ServiceRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 出于性能考虑， 复写此方法。
     */
    public Throwable fillInStackTrace() {
        return this;
    }
}
