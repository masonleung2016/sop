package dwz.common.component.threadpool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 简单线程池
 *
 * @Author: LCF
 * @Date: 2020/1/8 15:16
 * @Package: dwz.common.component.threadpool
 */
public class ThreadPool implements ThreadPoolDiagnose {

    private static int num = 0;
    /**
     * log信息
     */
    private Log log = LogFactory.getLog(getClass());
    /**
     * 线程池大小.
     */
    private int maxsize;
    /**
     * 线程名前缀.
     */
    private String thread_prefix;
    /**
     * 线程池是否启动.
     */
    private boolean running;
    /**
     * 底层实现.
     */
    private ThreadPoolExecutor threadPool = null;
    /**
     * 工作队列
     */
    private BlockingQueue<Runnable> workQueue = new SynchronousQueue<Runnable>();
    /**
     * 存储当前正在运行的任务.
     */
    private Object lock = new Object();
    /**
     * 诊断信息集合 Runnable->ThreadDiagnose
     */
    private Map<Runnable, ThreadDiagnose> diagnoseMap = new HashMap<Runnable, ThreadDiagnose>();

    public ThreadPool(String thread_prefix) {
        this.thread_prefix = thread_prefix;

        if (log.isDebugEnabled()) {
            log.debug("New ThreadPool given: coresize=" + maxsize
                    + ", maxsize=" + maxsize + ", thread_prefix="
                    + thread_prefix);
        }
    }

    public ThreadPool(int maxsize, String thread_prefix) {
        if (maxsize < 0) {
            throw new IllegalArgumentException("maxsize < 0");
        }
        this.maxsize = maxsize;
        this.thread_prefix = thread_prefix;

        if (log.isDebugEnabled()) {
            log.debug("New ThreadPool given: coresize=" + maxsize
                    + ", maxsize=" + maxsize + ", thread_prefix="
                    + thread_prefix);
        }
    }

    private static synchronized int next() {
        return ++num;
    }

    /**
     * 启动.
     */
    public synchronized void start() {
        if (!running) {
            if (log.isDebugEnabled()) {
                log.debug("ThreadPool.start() starting");
            }

            long waitTime = 30 * 60 * 1000L; //30min, waiting pool
            threadPool = new ThreadPoolExecutor(maxsize, maxsize, waitTime,
                    TimeUnit.MILLISECONDS, workQueue);
            threadPool.setThreadFactory(new ThreadFactory() {
                public Thread newThread(Runnable command) {
                    Thread t = new Thread(command);
                    t.setName(thread_prefix + "_" + next());
                    //设置成守护线程.
                    t.setDaemon(true);
                    if (t.getPriority() != Thread.NORM_PRIORITY) {
                        t.setPriority(Thread.NORM_PRIORITY);
                    }
                    return t;
                }
            });
            running = true;
        } else {
            if (log.isDebugEnabled()) {
                log.debug("ThreadPool.start() is already running");
            }
        }
    }

    /**
     * 停止.
     */
    public synchronized void stop() {
        if (running) {
            if (log.isDebugEnabled()) {
                log.debug("ThreadPool.stop() stopping");
            }

            threadPool.shutdownNow();
            threadPool = null;
            running = false;
            synchronized (lock) {
                diagnoseMap.clear();
            }

        } else {
            if (log.isDebugEnabled()) {
                log.debug("ThreadPool.stop() is already stop");
            }
        }
    }

    /**
     * 执行任务.
     *
     * @param torun 要执行的任务.
     * @throws Exception
     */
    public void execute(final Runnable torun) throws Exception {
        if (running && torun != null) {
            Runnable wapper = new Runnable() {
                public void run() {
                    //1. to recode
                    ThreadDiagnose diagnose = new ThreadDiagnose();
                    diagnose.className = torun.getClass().getName();
                    diagnose.starttime = System.currentTimeMillis();
                    diagnose.torun = torun;
                    synchronized (lock) {
                        diagnoseMap.put(this, diagnose);
                    }
                    try {
                        //2. to execute the exact work.
                        torun.run();
                    } finally {
                        //3. clear
                        synchronized (lock) {
                            diagnoseMap.remove(this);
                        }
                        if (log.isDebugEnabled()) {
                            log
                                    .debug("ThreadPool.execute:torun="
                                            + diagnose.className
                                            + ", Elapsed Time="
                                            + (System.currentTimeMillis() - diagnose.starttime)
                                            + "(ms)");
                        }
                    }
                }
            };
            threadPool.execute(wapper);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("ThreadPool.execute() error:" + running + "=" + running + ", Runable=" + torun);
            }
            throw new Exception("not running");
        }
    }

    /**
     * 是否在运行.
     *
     * @return
     */
    public boolean running() {
        return running;
    }

    /**
     * 最大大小.
     *
     * @return
     */
    public int maxsize() {
        return maxsize;
    }

    /**
     * 当前大小.
     *
     * @return
     */
    public int size() {
        if (threadPool != null) {
            return threadPool.getPoolSize();
        }
        return 0;
    }

    /**
     * 获取线程池诊断信息
     *
     * @return
     */
    public ThreadDiagnose[] getDiagnose() {
        List<ThreadDiagnose> list = new ArrayList<ThreadDiagnose>();
        synchronized (lock) {
            Iterator<ThreadDiagnose> it = diagnoseMap.values().iterator();
            while (it.hasNext()) {
                ThreadDiagnose diagnose = (it.next()).copyOne();
                list.add(diagnose);
            }
        }
        return list.toArray(new ThreadDiagnose[0]);
    }

    /**
     * 诊断信息对象
     */
    public static class ThreadDiagnose {
        /**
         * 正在运行的任务的类名
         */
        public String className;
        /**
         * 开始时间
         */
        public long starttime;
        /**
         * 要执行的任务
         */
        public Runnable torun;

        private ThreadDiagnose copyOne() {
            ThreadDiagnose diagnose = new ThreadDiagnose();
            diagnose.className = className;
            diagnose.starttime = starttime;
            diagnose.torun = torun;
            return diagnose;
        }
    }
}
