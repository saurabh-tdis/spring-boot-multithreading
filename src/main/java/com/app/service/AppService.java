package com.app.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/***
 *
 * @Link = https://www.linkedin.com/pulse/asynchronous-calls-spring-boot-using-async-annotation-omar-ismail
 *
 *  ***** For multithreading with Async method should always return CompletableFuture<T> or void
 *
 * There are few rules which we should remember while using this annotation.
 *
 * 1.  @Async annotation must be on the public method.
 * 2.  Spring use a proxy for this annotation and it must be public for the proxy to work.
 * 2.  Calling the async method from within the same class. It won’t work (Method calling like this will bypass proxy).
 * 4.  Method with a return type should be CompletableFuture or Future.
 *
 */

//@Transactional
@Service
@Log4j2
public class AppService {

    @Autowired
    AsyncService asyncService;

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

    // this will not work as sleep method will not get considered async
    // for this to work either call directly sleep from controller
    // or create another class for this async method
    // as spring generated proxy class so when we call async method from any service class , it will get consider as normal one
    // so best option to gen a class then call async method from their
    public void sleepAsync() throws InterruptedException {
//        sleep(); // wont work from here
        asyncService.sleep(); // now it will get call as async
    }

//    @Transactional
//    @Async
//    public void sleep() throws InterruptedException {
//        log.info("sleep async start");
//
//        Thread.sleep(5000);
//
//        log.info("sleep async ends");
//    }


    public void sleepAsyncWithFutures() throws InterruptedException {
//        sleep(); // wont work from here
        asyncService.sleepWithFutures(); // now it will get call as async
    }

    public CompletableFuture<Long> sleepAsyncWithFuturesReturning() throws InterruptedException {
//        sleep(); // wont work from here
        return asyncService.sleepWithFuturesReturning(); // now it will get call as async
    }

}
