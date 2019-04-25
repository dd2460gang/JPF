package env.java.util.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/** ThreadPoolExecutor stub that either launches a thread or rejects it right
    away. Does not manage a thread pool or use a thread of its own, to be
    lightweight enough for JPF. */

public class ThreadPoolExecutor {
    public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			      long keepAliveTime, TimeUnit unit,
			      BlockingQueue<Runnable> workQueue,
			      RejectedExecutionHandler handler) {
      // TODO: fill in for exercise
    }

    public void shutdown() { } // stub

    public void execute(Runnable r) { // TODO: fill in for exercise
    }
}
