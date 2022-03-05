package com.app.controller;


import com.app.service.AppService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
public class AppController {

    @Autowired
    private AppService appService;


    @GetMapping("/getMsgCom")
    public CompletableFuture<String> getMessageComp() throws Exception , ExecutionException {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        CompletableFuture<String> future1 = appService.getData1();
        CompletableFuture<String> future2 = appService.getData2();
        CompletableFuture<String> future3 = appService.getData3();
        CompletableFuture<String> future4 = appService.getData4();

        CompletableFuture.allOf(future1, future2, future3).join();

        CompletableFuture<String> all = future1.thenCombine(future2, (s1, s2) -> s1 + "\n" + s2)
                .thenCombine(future3, (s1, s2) -> s1 + "\n" + s2).thenCombine(future4, (s1, s2) -> s1 + "\n" + s2);

        stopWatch.stop();

        System.out.println("Time taken to get all data : " + stopWatch.getTotalTimeMillis());

        return  all;



//        return future1.get() + " " + future2.get() + " " + future3.get();
    }

    @GetMapping("/getMsgStr")
    public String getMessageStr() throws Exception , ExecutionException {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        CompletableFuture<String> future1 = appService.getData1();
        CompletableFuture<String> future2 = appService.getData2();
        CompletableFuture<String> future3 = appService.getData3();
        CompletableFuture<String> future4 = appService.getData4();

        CompletableFuture.allOf(future1, future2, future3).join();

        String all = future1.get() + "\n" + future2.get() + "\n" + future3.get() + "\n" + future4.get();

        stopWatch.stop();

        System.out.println("Time taken to get all data : " + stopWatch.getTotalTimeMillis());

        System.out.println(Thread.activeCount());

        return  all;
//        return  Thread.activeCount()+"";

    }

}
