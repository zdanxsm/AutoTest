package com.course.testng;

import org.testng.annotations.*;

public class BasicAnnotation {

    @Test
    public void testCase1(){
        System.out.println("这是测试用例1");
    }

    @Test
    public void testCase2(){
        System.out.println("这是测试用例2");

    }

    @BeforeMethod
    public void beforeMethod(){
        System.out.println("beforeMethod,运行");
    }

    @AfterMethod
    public void afterMethod(){
        System.out.println("afterMethod,运行");
    }

    @BeforeTest
    public void beforeTest(){
        System.out.println("beforeTest,运行");
    }

    @AfterTest
    public void afterTest(){
        System.out.println("afterTest,运行");
    }

    @BeforeClass
    public void beforeClass(){
        System.out.println("beforeClass,运行在类前");
    }

    @AfterClass
    public void afterClass(){
        System.out.println("afterClass,运行在类后");
    }


}
