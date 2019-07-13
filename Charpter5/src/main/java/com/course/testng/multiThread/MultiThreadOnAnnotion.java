package com.course.testng.multiThread;

import org.testng.annotations.Test;

import static java.lang.Thread.currentThread;

public class MultiThreadOnAnnotion {

    @Test(invocationCount = 5 ,threadPoolSize = 2)
    public void test(){
        System.out.println(1);
        System.out.printf("Thread Id : %s%n",currentThread().getId());
    }

}
