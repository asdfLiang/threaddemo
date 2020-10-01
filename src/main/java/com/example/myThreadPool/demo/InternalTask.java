package com.example.myThreadPool.demo;

import com.example.myThreadPool.RunnableQueue;

/**
 * 用于从任务队列中获取runnable，并运行
 *
 * @author liangzj9624@163.com
 * @date 2020/8/30 上午8:01
 */
public class InternalTask implements Runnable {

    private final RunnableQueue runnableQueue;

    private volatile boolean running = true;

    public InternalTask(RunnableQueue runnableQueue) {
        this.runnableQueue = runnableQueue;
    }

    @Override
    public void run() {
        // 如果当前线程为running并且没有被中断，则其将不断地从queue中获取runnable然后执行run方法
        while (running && !Thread.currentThread().isInterrupted()) {
            try {
                Runnable task = runnableQueue.take();
                task.run();
            } catch (Exception e) {
                running = false;
                break;
            }
        }
    }

    public void stop() {
        this.running = false;
    }
}
