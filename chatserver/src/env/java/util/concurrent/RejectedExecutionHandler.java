package env.java.util.concurrent;

/** Mock for RejectedExecutionHandler. */

public interface RejectedExecutionHandler {
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor);
}
