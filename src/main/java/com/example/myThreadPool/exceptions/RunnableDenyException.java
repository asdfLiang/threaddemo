package com.example.myThreadPool.exceptions;

/**
 * @author liangzj9624@163.com
 * @date 2020/8/23 下午11:43
 */
public class RunnableDenyException extends RuntimeException {
    public RunnableDenyException(String message) {
        super(message);
    }
}
