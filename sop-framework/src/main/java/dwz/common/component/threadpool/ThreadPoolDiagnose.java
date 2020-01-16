package dwz.common.component.threadpool;

import dwz.common.component.threadpool.ThreadPool.ThreadDiagnose;

/**
 * 诊断信息接口
 *
 * @Author: LCF
 * @Date: 2020/1/8 15:17
 * @Package: dwz.common.component.threadpool
 */

public interface ThreadPoolDiagnose {

    public boolean running();

    public int maxsize();

    public int size();

    public ThreadDiagnose[] getDiagnose();

}
