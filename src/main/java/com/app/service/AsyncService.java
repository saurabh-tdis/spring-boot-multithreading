package com.app.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

/**
 * @Author saurabh vaish
 * @Date 02-07-2022
 */

@Transactional
@Service
@Log4j2
public class AsyncService {


    @Async
    public void sleep() throws InterruptedException {
        log.info("sleep async start");

        sleepTime(5000L);

        log.info("sleep async ends");
    }

    private void sleepTime(Long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    public void sleepWithFutures() throws InterruptedException {

        StopWatch sp = new StopWatch();
        sp.start();

        CompletableFuture<Void> future1 = CompletableFuture.runAsync(()->sleepTime(3000L));
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(()->sleepTime(5000L));
        CompletableFuture<Void> future3 = CompletableFuture.runAsync(()->sleepTime(8000L));

        CompletableFuture.allOf(future1,future2,future3).join();
        sp.stop();

        System.out.println("time taken == "+sp.getTotalTimeMillis());

    }


    @Async
    public CompletableFuture<Long> sleepWithFuturesReturning() throws InterruptedException {


        StopWatch sp = new StopWatch();
        sp.start();

        CompletableFuture<Long> future1 = CompletableFuture.supplyAsync(()->{sleepTime(3000L);return 3000L;});
        CompletableFuture<Long> future2 = CompletableFuture.supplyAsync(()->{sleepTime(5000L);return 5000L;});
        CompletableFuture<Long> future3 = CompletableFuture.supplyAsync(()->{sleepTime(7000L);return 7000L;});

        CompletableFuture.allOf(future1, future2, future3).join(); // join will wait for all futures to get complete then rest of code will execute

        // combining result
        Optional<CompletableFuture<Long>> opValue = Optional.of(CompletableFuture.completedFuture(0L));
        opValue = Stream.of(future1, future2, future3)
                .reduce((f1, f2) -> f1.thenCombine(f2, (d1, d2) -> d1 + d2));

        sp.stop();

        System.out.println("time taken == "+sp.getTotalTimeMillis());

        CompletableFuture<Long> value = opValue.orElse(CompletableFuture.completedFuture(0L));

        value.thenAccept(s-> System.out.println("total result in service ="+s));

        return value;

    }


    @Async
    public void runAsync(TaskExecutor executor,Runnable runnable){
        log.info("thread in async service class async method "+Thread.currentThread().getName());
        executor.execute(runnable);
    }

    @Async
    public void runAsync(Runnable runnable){
        log.info("thread in async service class async method "+Thread.currentThread().getName());
        runnable.run();
    }

}
