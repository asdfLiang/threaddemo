package com.example.myThreadPool;

/**
 * 创建线程的工厂
 *
 * @author liangzj9624@163.com
 * @date 2020/8/23 下午11:33
 */
public interface ThreadFactory {

    Thread createThread(Runnable runnable);

}
