package com.example.myThreadPool.demo;

import com.example.myThreadPool.DenyPolicy;
import com.example.myThreadPool.RunnableQueue;
import com.example.myThreadPool.ThreadPool;

import java.util.LinkedList;

/**
 * 任务队列
 *
 * @author liangzj9624@163.com
 * @date 2020/8/30 上午8:25
 */
public class LinkedRunnableQueue implements RunnableQueue {

    // 任务队列的最大容量
    private final int limit;

    // 任务队列的任务若已经满了，则需要执行策略
    private final DenyPolicy denyPolicy;

    // 存放任务的队列
    private final LinkedList<Runnable> runnableList = new LinkedList<>();

    private final ThreadPool threadPool;

    public LinkedRunnableQueue(int limit, DenyPolicy denyPolicy, ThreadPool threadPool) {
        this.limit = limit;
        this.denyPolicy = denyPolicy;
        this.threadPool = threadPool;
    }

    @Override
    public void offer(Runnable runnable) {
        synchronized (runnableList) {
            // 任务超过队列最大值，执行策略
            if (runnableList.size() >= limit) {
                denyPolicy.reject(runnable, threadPool);
            } else {
                runnableList.addLast(runnable);
                runnableList.notify();
            }
        }
    }

    @Override
    public Runnable take() throws InterruptedException {
        while (runnableList.isEmpty()) {
            try {
                runnableList.wait();
            } catch (InterruptedException e) {
                throw e;
            }
        }
        return runnableList.removeFirst();
    }

    @Override
    public int size() {
        synchronized (runnableList) {
            return runnableList.size();
        }
    }
}
