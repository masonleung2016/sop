package dwz.framework.timer;

import java.util.Date;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:12
 * @Package: dwz.framework.timer
 */

public interface TaskUnit {

    String getName();

    Runnable getTask();

    Class<?> getTaskClass();

    int getPriority();

    Date getStartTime();

    long getPeriod();

    long getDelay();

    boolean isRunnable();

}
