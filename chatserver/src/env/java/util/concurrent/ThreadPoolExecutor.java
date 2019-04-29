package env.java.util.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/** ThreadPoolExecutor stub that either launches a thread or rejects it right
    away. Does not manage a thread pool or use a thread of its own, to be
    lightweight enough for JPF. */

public class ThreadPoolExecutor {
    private volatile int launchedThreads;
    //private volatile int corePoolSize;
    private volatile int maximumPoolSize;
    //private final BlockingQueue<Runnable> workQueue;
    //private volatile long keepAliveTime;
    //private volatile ThreadFactory threadFactory;
    private volatile RejectedExecutionHandler handler;
    public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			      long keepAliveTime, TimeUnit unit,
			      BlockingQueue<Runnable> workQueue,
			      RejectedExecutionHandler handler) {
        /*if (corePoolSize < 0 ||
                maximumPoolSize <= 0 ||
                maximumPoolSize < corePoolSize ||
                keepAliveTime < 0)
            throw new IllegalArgumentException();*/
        if (maximumPoolSize <=0) throw  new IllegalArgumentException();
        /*if (workQueue == null || threadFactory == null || handler == null)
            throw new NullPointerException();*/
        if(handler == null) throw new NullPointerException();
        this.maximumPoolSize = maximumPoolSize;
        this.handler = handler;
        //this.corePoolSize = corePoolSize;
        //this.workQueue = workQueue;
        //this.keepAliveTime = unit.toNanos(keepAliveTime);
        //this.threadFactory = threadFactory;

    }

    public void shutdown() { } // stub

    public void execute(Runnable r) {
        //Keep track of number of threads launched
            //Nr active threads: use the nr of launched threads as approximation (upper bound)
        //Launch thread if not max number of threads reached
        if(launchedThreads<maximumPoolSize){
            r.run();
            launchedThreads++;
        }else{
            handler.rejectedExecution(r, this);
        }
        //Reject thread if max nr reached by calling RejectedExecutionHandler
            //Rejected case handled directly, do not launch new thread for this.
    }

}
