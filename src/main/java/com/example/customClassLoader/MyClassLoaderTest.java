package com.example.customClassLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author liangzj9624@163.com
 * @date 2020/8/31 上午7:45
 */
public class MyClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException,
            IllegalAccessException, InstantiationException,
            NoSuchMethodException, InvocationTargetException {
        MyClassLoader classLoader = new MyClassLoader();

        Class<?> aClass = classLoader.loadClass("com.example.customClassLoader.HelloWorld");

        //
        System.out.println(aClass.getClassLoader());

        //
        Object helloWorld = aClass.newInstance();
        System.out.println(helloWorld);
        Method welcomeMethod = aClass.getMethod("welcome");

        Object invokeResult = welcomeMethod.invoke(helloWorld);
        System.out.println("Result: " + invokeResult);
    }

}
