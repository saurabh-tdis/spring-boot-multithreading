package com.app.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/***
 *
 *  ***** For multithreading with Async method should always return CompletableFuture<T> or void
 *
 *
 */

@Service
@Log4j2
public class AppService {

    @Async("taskExecutor1")
    public CompletableFuture<String> getData1() throws InterruptedException {
        log.info("getData1 method called == "+Thread.currentThread().getName());
        Thread.sleep(2000);
        log.info("getData1 method finished == "+Thread.currentThread().getName());
        return CompletableFuture.completedFuture("message from data 1 == "+Thread.currentThread().getName());
    }

    @Async("taskExecutor1")
    public CompletableFuture<String> getData2() throws InterruptedException {
        log.info("getData2 method called == "+Thread.currentThread().getName());
        Thread.sleep(2000);
        log.info("getData2 method finished == "+Thread.currentThread().getName());
        return CompletableFuture.completedFuture("message from data 2 == "+Thread.currentThread().getName());
    }

    @Async("taskExecutor2")
    public CompletableFuture<String> getData3() throws InterruptedException {
        log.info("getData3 method called == "+Thread.currentThread().getName());
        Thread.sleep(2000);
        log.info("getData3 method finished == "+Thread.currentThread().getName());
        return CompletableFuture.completedFuture("message from data 4  == "+Thread.currentThread().getName());
    }

    @Async("taskExecutor2")
    public CompletableFuture<String> getData4() throws InterruptedException {
        log.info("getData4 method called == "+Thread.currentThread().getName());
        Thread.sleep(2000);
        log.info("getData4 method finished == "+Thread.currentThread().getName());
        return CompletableFuture.completedFuture("message from data 4 == "+Thread.currentThread().getName());
    }

    @Async
    public CompletableFuture<String> getData5() throws InterruptedException {
        log.info("getData5 method called == "+Thread.currentThread().getName());
        Thread.sleep(2000);
        log.info("getData5 method finished == "+Thread.currentThread().getName());
        return CompletableFuture.completedFuture("message from data 5 == "+Thread.currentThread().getName());
    }

}
