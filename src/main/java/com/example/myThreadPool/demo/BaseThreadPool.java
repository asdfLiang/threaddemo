package com.example.myThreadPool.demo;

import com.example.myThreadPool.DenyPolicy;
import com.example.myThreadPool.RunnableQueue;
import com.example.myThreadPool.ThreadFactory;
import com.example.myThreadPool.ThreadPool;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liangzj9624@163.com
 * @date 2020/8/30 上午8:41
 */
public class BaseThreadPool extends Thread implements ThreadPool {
    // 初始化线程数
    private final int initSize;

    // 线程池最大线程数量
    private final int maxSize;

    // 线程池核心线程数量
    private final int coreSize;

    // 当前活跃的线程数量
    private int activeCount;

    // 创建线程所需的线程工厂
    private final ThreadFactory threadFactory;

    // 创建线程所需的工厂
    private final RunnableQueue runnableQueue;

    // 线程池是否已经被shutdown
    private volatile boolean isShutDown = false;

    // 工作线程队列
    private final Queue<ThreadTask> threadQueue = new ArrayDeque<>();

    private static final DenyPolicy DEFAULT_DENY_POLICY = new DenyPolicy.DiscardDenyPolicy();

    private static final ThreadFactory DEFAULT_THREAD_FACTORY = new DefaultThreadFactory();

    private final long keepActiveTime;

    private final TimeUnit timeUnit;

    public BaseThreadPool(int initSize, int maxSize, int coreSize, int queueSize) {
        this(initSize, maxSize, coreSize, DEFAULT_THREAD_FACTORY,
                queueSize, DEFAULT_DENY_POLICY, 10L, TimeUnit.SECONDS);
    }

    public BaseThreadPool(int initSize, int maxSize, int coreSize,
                          ThreadFactory threadFactory, int queueSize,
                          DenyPolicy denyPolicy, long keepActiveTime, TimeUnit timeUnit) {
        this.initSize = initSize;
        this.maxSize = maxSize;
        this.coreSize = coreSize;
        this.activeCount = 0;
        this.threadFactory = threadFactory;
        this.runnableQueue = new LinkedRunnableQueue(queueSize, denyPolicy, this);
        this.keepActiveTime = keepActiveTime;
        this.timeUnit = timeUnit;
        this.init();
    }

    private void init() {
        start();
        for (int i = 0; i < initSize; i++) {
            new Thread();
        }
    }

    private void newThread() {
        InternalTask internalTask = new InternalTask(runnableQueue);
        Thread thread = this.threadFactory.createThread(internalTask);

        ThreadTask threadTask = new ThreadTask(thread, internalTask);
        threadQueue.offer(threadTask);
        this.activeCount++;
        thread.start();
    }

    private void removeThread() {
        ThreadTask threadTask = threadQueue.remove();
        threadTask.internalTask.stop();
        this.activeCount--;
    }

    @Override
    public void run() {
        while (!isShutDown && !isInterrupted()) {

            try {
                timeUnit.sleep(keepActiveTime);
            } catch (InterruptedException e) {
                isShutDown = true;
                break;
            }

            synchronized (this) {
                if (isShutDown) {
                    break;
                }
                // 当前队列中有任务尚未处理，并且activeCount < coreSize则继续扩容
                if (runnableQueue.size() > 0 && activeCount < coreSize) {
                    for (int i = initSize; i < coreSize; i++) {
                        newThread();
                    }
                    // 不想让线程扩容直接达到maxSize
                    continue;
                }

                if (runnableQueue.size() > 0 && activeCount < maxSize) {
                    for (int i = coreSize; i < maxSize; i++) {
                        newThread();
                    }
                }

                // 如果队列中没有任务，则需要回收，回收至coreSize即可
                if (runnableQueue.size() == 0 && activeCount > coreSize) {
                    removeThread();
                }

            }

        }
    }

    @Override
    public void execute(Runnable runnable) {
        if (this.isShutDown) {
            throw new IllegalStateException("The thread is destroy");
        }
        this.runnableQueue.offer(runnable);
    }

    @Override
    public void shutdown() {
        synchronized (this) {
            if (isShutDown) {
                return;
            }

            threadQueue.forEach(threadTask -> {
                threadTask.internalTask.stop();
                threadTask.thread.interrupt();
            });

            this.interrupt();
        }
    }

    @Override
    public int getInitSize() {
        if (this.isShutDown) {
            throw new IllegalStateException("The thread is destroy");
        }
        return this.initSize;
    }

    @Override
    public int getCoreSize() {
        if (this.isShutDown) {
            throw new IllegalStateException("The thread is destroy");
        }
        return this.coreSize;
    }

    @Override
    public int getMaxSize() {
        if (this.isShutDown) {
            throw new IllegalStateException("The thread is destroy");
        }
        return this.maxSize;
    }

    @Override
    public int getQueueSize() {
        if (this.isShutDown) {
            throw new IllegalStateException("The thread is destroy");
        }
        return this.runnableQueue.size();
    }

    @Override
    public int getActiveCount() {
        synchronized (this) {
            return this.activeCount;
        }
    }

    @Override
    public boolean isShutdown() {
        return isShutDown;
    }

    private static class ThreadTask {
        Thread thread;
        InternalTask internalTask;

        public ThreadTask(Thread thread, InternalTask internalTask) {
            this.thread = thread;
            this.internalTask = internalTask;
        }
    }

    private static class DefaultThreadFactory implements ThreadFactory {

        private static final AtomicInteger GROUP_COUNTER = new AtomicInteger(1);

        private static final ThreadGroup group = new ThreadGroup("MyThreadPool-"
                + GROUP_COUNTER.getAndDecrement());

        private static final AtomicInteger COUNTER = new AtomicInteger(0);

        @Override
        public Thread createThread(Runnable runnable) {
            return new Thread(group, runnable, "thread-pool-" + COUNTER.getAndDecrement());
        }
    }
}
