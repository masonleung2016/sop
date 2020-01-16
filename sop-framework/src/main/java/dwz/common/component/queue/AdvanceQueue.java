package dwz.common.component.queue;

/**
 * 队列高级行为
 * (此行为仅暴露给业务中实际操作入队和出队行为的用户)
 *
 * @Author: LCF
 * @Date: 2020/1/8 15:09
 * @Package: dwz.common.component.queue
 */

public interface AdvanceQueue<E> {

    public void putInStrategy(E obj) throws Exception;

    public void putInStrategy(E obj, long maxlimit) throws Exception;

    public Object getInStrategy() throws Exception;

    public Object getInStrategy(long maxlimit) throws Exception;

}
