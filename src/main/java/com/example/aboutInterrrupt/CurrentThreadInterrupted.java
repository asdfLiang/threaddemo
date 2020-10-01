package com.example.aboutInterrrupt;

import java.util.concurrent.TimeUnit;

/**
 * @author liangzj9624@163.com
 * @date 2020/8/22 下午7:30
 */
public class CurrentThreadInterrupted {
    public static void main(String[] args) {
        // 判断当前线程是否被中断
        System.out.println("Main thread is interrupted? " + Thread.interrupted());
//        System.out.println("Main thread is interrupted? " + Thread.currentThread().isInterrupted());
        // 中断当前线程
        Thread.currentThread().interrupt();
        // 判断当前线程是否已经被中断
        /*为什么不用Thread.interrupted()？Thread.interrupted()会清除*/
        System.out.println("Main thread is interrupted? " + Thread.currentThread().isInterrupted());

        try {
            // 当前线程执行可中断方法
            TimeUnit.MINUTES.sleep(1);
        } catch (InterruptedException e) {
            // 捕获中断信号
            System.out.println("I will be interrupted still." + Thread.currentThread().isInterrupted());
        }
    }
}
