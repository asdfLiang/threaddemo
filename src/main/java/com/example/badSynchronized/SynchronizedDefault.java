package com.example.badSynchronized;

import java.util.concurrent.TimeUnit;

/**
 * @author liangzj9624@163.com
 * @date 2020/8/23 下午3:58
 */
public class SynchronizedDefault {

    public synchronized void synchronizedMethod() {
        try {
            TimeUnit.HOURS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedDefault synchronizedDefault = new SynchronizedDefault();
        Thread t1 = new Thread(synchronizedDefault::synchronizedMethod, "T1");
        t1.start();

        TimeUnit.MILLISECONDS.sleep(2);
        Thread t2 = new Thread(synchronizedDefault::synchronizedMethod, "T2");
        t2.start();
        t2.interrupt();
        System.out.println(t2.isInterrupted());
        System.out.println(t2.getState());
    }

}
