package com.example.aboutInterrrupt;

import java.util.concurrent.TimeUnit;

/**
 * @author liangzj9624@163.com
 * @date 2020/8/22 下午5:28
 */
public class ThreadIsInterrupted {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.MINUTES.sleep(1);
                } catch (InterruptedException e) {
                    System.out.println("i am be interrupted ? " + Thread.currentThread().isInterrupted());
                }
            }
        });

        thread.setDaemon(true);
        thread.start();

        TimeUnit.MILLISECONDS.sleep(2);
        System.out.println("thread be interrupted ? " + thread.isInterrupted());
        thread.interrupt();
        System.out.println("thread be interrupted ? " + thread.isInterrupted());
    }
}
