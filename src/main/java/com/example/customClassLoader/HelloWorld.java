package com.example.customClassLoader;

/**
 * @author liangzj9624@163.com
 * @date 2020/8/31 上午7:44
 */
public class HelloWorld {

    static {
        System.out.println("Hello World Class is Initialized.");
    }

    public String welcome() {
        return "Hello World";
    }
}
