package com.example.aboutAsync;

import java.util.concurrent.*;

/**
 * @author liangzj9624@163.com
 * @date 2020/8/22 上午12:29
 */
public class DecisionDemo {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(5);

    private static final Integer SYNC = 0;
    private static final Integer ASYNC = 1;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            decisionAndPackage(SYNC, ASYNC);
        }
    }

    private static void decisionAndPackage(Integer howDecision, Integer howPackage)
            throws ExecutionException, InterruptedException {
        SopDecision sopDecision = new SopDecision();
        if (SYNC.equals(howDecision)) {
            String s = sopDecision.syncDecision();
            if (SYNC.equals(howPackage)) {
                sopDecision.syncPackage(s);
            } else {
                FutureTask<String> future = new FutureTask(() -> sopDecision.asyncPackage(s));
                executorService.execute(future);

            }
        } else {

        }
    }
}
