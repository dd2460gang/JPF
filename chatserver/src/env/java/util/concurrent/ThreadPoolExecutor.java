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
    private volatile int maximumPoolSize;
    private volatile RejectedExecutionHandler handler;
    public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			      long keepAliveTime, TimeUnit unit,
			      BlockingQueue<Runnable> workQueue,
			      RejectedExecutionHandler handler) {

        this.maximumPoolSize = maximumPoolSize;
        this.handler = handler;
    }

    public void shutdown() { } // stub

    public void execute(Runnable r) {
        if(launchedThreads<maximumPoolSize){
            new Thread(r).start();
            launchedThreads++;
        } else handler.rejectedExecution(r, this);
    }
}
