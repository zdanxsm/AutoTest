package com.course.server;

import com.course.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(value = "/", description = "这是所有的POST请求")
@RequestMapping("/v1")
public class MyPostMethod {

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value = "login and set cookies 接口",httpMethod = "POST")
    public String login(@RequestParam(value = "userName",required = true) String userNme,
                        @RequestParam(value = "password",required = true) String passWord,
                        HttpServletResponse response){
        if (userNme.equals("zhangsan") && passWord.equals("123456")){
            Cookie cookie = new Cookie("login","true");
            response.addCookie(cookie);
            return "congratulations for login";
        }
        return "your userName or password is'n right";
    }


    @RequestMapping(value = "/getUerList",method = RequestMethod.POST)
    @ApiOperation(value = "获得你传递的cookies并给你返回一个userList",httpMethod = "POST")
    public String getUerList(HttpServletRequest request, @RequestBody User user){
        Cookie[] cookies = request.getCookies();
        User user1;
        for (Cookie cookie:cookies){
            if (cookie.getName().equals("login")
                    && cookie.getValue().equals("true")
                    && "zhangsan".equals(user.getAdminName())
                    && "123456".equals(user.getAdminPassword())){
                user1 = new User();
                user1.setName("lisi");
                user1.setAge("20");
                user1.setSex("man");
                return user1.toString();
            }
        }
        return "参数不合法";
    }




}