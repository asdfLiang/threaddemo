package com.example;

import java.util.concurrent.TimeUnit;

/**
 * @author liangzj9624@163.com
 * @date 2020/8/22 下午10:19
 */
public class Mutex {

    private static final Object MUTEX = new Object();

    public void accessResource() {
        synchronized (MUTEX) {
            try {
                TimeUnit.MINUTES.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        final Mutex mutex = new Mutex();
        for (int i = 0; i < 5; i++) {
            new Thread(mutex::accessResource).start();
        }
    }
}
