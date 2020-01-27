package dwz.framework.context;

/**
 * @Author: LCF
 * @Date: 2020/1/8 16:37
 * @Package: dwz.framework.context
 */

public class AppContextHolder {

    private static final InheritableThreadLocal<AppContext> holder = new InheritableThreadLocal<AppContext>() {

        protected AppContext initialValue() {
            return null;
        }
    };

    public static AppContext getContext() {
        return holder.get();
    }

    public static void setContext(AppContext context) {
        holder.set(context);
    }
}
