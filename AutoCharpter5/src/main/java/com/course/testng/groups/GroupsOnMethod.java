package com.course.testng.groups;

import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

public class GroupsOnMethod {

    @Test(groups = "sever")
    public void test1(){
        System.out.println("这是服务端组测试1");
    }

    @Test(groups = "sever")
    public void test2(){
        System.out.println("这是服务端组测试2");
    }

    @Test(groups = "client")
    public void test3(){
        System.out.println("这是客户端组测试3");
    }

    @Test(groups = "client")
    public void test4(){
        System.out.println("这是客户端组测试4");
    }

    @BeforeGroups("sever")
    public void beforeGroups(){
        System.out.println("beforeGroups run..");
    }

    @AfterGroups("sever")
    public void afterGroups(){
        System.out.println("afterGroups run..");
    }

}
