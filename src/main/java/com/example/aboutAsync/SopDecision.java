package com.example.aboutAsync;

import java.util.Random;

/**
 * @author liangzj9624@163.com
 * @date 2020/8/22 上午12:23
 */
public class SopDecision {

    public String syncDecision() {
        return "I am sync decision";
    }

    public String asyncDecision() {
        Random random = new Random(10);
        int i = random.nextInt(5);
        try {
            Thread.sleep(i);
            return "I am async decision, I sleep" + i + " second";
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "sorry, I am running error";
    }

    public void syncPackage(String howDecision) {
        System.out.println(howDecision + ", I am sync package");
    }

    public String asyncPackage(String howDecision) {
        Random random = new Random(10000);
        int i = random.nextInt(10);
        System.out.println(i * 1000);
        try {
            Thread.sleep(i);
            return howDecision + ", I am async package, I sleep " + i + " second";
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "sorry, I am running error";
    }
}
