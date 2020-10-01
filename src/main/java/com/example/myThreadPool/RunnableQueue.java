package com.example.myThreadPool;

/**
 * 任务队列，主要用于缓存提交到线程池中的任务
 *
 * @author liangzj9624@163.com
 * @date 2020/8/23 下午11:30
 */
public interface RunnableQueue {

    /**
     * 当有新任务时首先会offer到队列中
     *
     * @param runnable
     */
    void offer(Runnable runnable);

    /**
     * 工作线程通过take方法获取Runnable
     *
     * @return
     */
    Runnable take() throws InterruptedException;

    /**
     * 获取任务队列中任务的数量
     *
     * @return
     */
    int size();
}
