package dwz.framework.timer;

import java.util.Collection;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:11
 * @Package: dwz.framework.timer
 */

public interface TaskParse {

    Collection<TaskUnit> getTaskUnits();

    TaskUnit getTaskUnit(String name);
}
