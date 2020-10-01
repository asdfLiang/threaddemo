package com.example.myThreadPool;

/**
 * @author liangzj9624@163.com
 * @date 2020/8/23 下午11:24
 */
public interface ThreadPool {

    // 提交任务到线程池
    void execute(Runnable runnable);

    // 关闭线程池
    void shutdown();

    // 获取线程池的初始大小
    int getInitSize();

    int getCoreSize();

    // 获取线程池的最大线程数
    int getMaxSize();

    // 获取线程池中用于缓存任务队列的大小
    int getQueueSize();

    // 获取线程池中活跃线程的数量
    int getActiveCount();

    // 查看线程池是否已经被销毁
    boolean isShutdown();

}
