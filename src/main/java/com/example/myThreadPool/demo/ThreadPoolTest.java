package com.example.myThreadPool.demo;

import com.example.myThreadPool.ThreadPool;

import java.util.concurrent.TimeUnit;

/**
 * @author liangzj9624@163.com
 * @date 2020/8/30 上午11:39
 */
public class ThreadPoolTest {
    public static void main(String[] args) throws InterruptedException {
        final ThreadPool threadPool = new BaseThreadPool(2, 6, 4, 1000);

        for (int i = 0; i < 20; i++) {
            threadPool.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(10);
                    System.out.println(Thread.currentThread().getName() + " is running and done.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        for (;;) {
            System.out.println("getActiveCount: " + threadPool.getActiveCount());
            System.out.println("getQueueSize: " + threadPool.getQueueSize());
            System.out.println("getCoreSize: " + threadPool.getCoreSize());
            System.out.println("getMaxSize: " + threadPool.getMaxSize());
            System.out.println("==================================================");
            TimeUnit.SECONDS.sleep(5);
        }
    }
}
