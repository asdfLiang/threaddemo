package com.example.aboutAsync;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author liangzj9624@163.com
 * @date 2020/2/17 下午8:51
 */
public class FutureDemo {

    private static final ExecutorService executorService = new ThreadPoolExecutor(
            30, 30, 0L,
            TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(),
            new ThreadFactoryBuilder().setNameFormat("plan-exec-%d").build());

    public static void main(String[] args) {

        executorService.submit(() -> {
            CompletableFuture<String> completableFuture = new CompletableFuture<>();
            try {
                String s = completableFuture.get(6, TimeUnit.SECONDS);
                System.out.println(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        });

    }
}
