package com.course.server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@Api(value = "/",description = "这是我全部的GET方法")
public class MyGetMethod {

    @RequestMapping(value = "/getCookies",method = RequestMethod.GET)
    @ApiOperation(value = "直接请求，获得cookies",httpMethod = "GET")
    public String getCookies(HttpServletResponse response){
        Cookie cookie = new Cookie("login", "true");
        response.addCookie(cookie);
        return "you have get the cookies";
    }

    /**
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/get/withCookies", method = RequestMethod.GET)
    @ApiOperation(value = "需要传递cookies信息材才能访问的接口",httpMethod = "GET")
    public String getWithCookies(HttpServletRequest request){

        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)){
            return "you need take the cookies for me";
        }
        for (Cookie cookie:cookies){
            if (cookie.getName().equals("login") && cookie.getValue().equals("true")){
                return "you have take the cookie for me";
            }
        }
        return "you take the cookies was't right";

    }

    /**
     *带参数，第一种方式
     * @param start
     * @param end
     * @return
     */
    @RequestMapping(value = "/get/with/param",method = RequestMethod.GET)
    @ApiOperation(value = "第一种带参数的get请求接口",httpMethod = "GET")
    public Map<String,Integer> getListFirst(@RequestParam Integer start,@RequestParam Integer end){

        Map<String,Integer> myList = new HashMap<>();
        myList.put("衣服",50);
        myList.put("鞋子",20);
        return myList;
    }

    /**
     * 带参数，第2种方式
     * @param start
     * @param end
     * @return
     */
    @RequestMapping(value = "/get/with/param/{start}/{end}")
    @ApiOperation(value = "第二种带参数的get请求接口",httpMethod = "GET")
    public Map<String,Integer> getListSecond(@PathVariable Integer start,@PathVariable Integer end){

        Map<String,Integer> myList = new HashMap<>();
        myList.put("衣服",50);
        myList.put("鞋子",20);
        myList.put("鞋子2",30);
        return myList;
    }




}
