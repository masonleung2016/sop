package dwz.common.component.queue;

/**
 * 入队方式
 *
 * @Author: LCF
 * @Date: 2020/1/8 15:13
 * @Package: dwz.common.component.queue
 */

public interface PutMethod {

    public boolean allowPut(BaseQueue<Object> queue, Object o);

}
