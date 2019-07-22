package com.course.controller;

import com.course.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@Log4j
@RestController
@RequestMapping("/v1")
@Api(value = "v1",description = "用户管理系统接口")
public class UserManager {

    @Autowired
    private SqlSessionTemplate template;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value = "登录接口",httpMethod = "POST")
    public boolean login(HttpServletResponse response, @RequestBody User user){
        int i = template.selectOne("login", user);
        Cookie cookie = new Cookie("login1", "true1");
        response.addCookie(cookie);
        log.info("查询到的结果是" + i);
        if (i == 1){
            log.info("登录的用户是" + user.getUserName());
            return true;
        }
        return false;

    }


    /*@RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value = "登录接口",httpMethod = "POST")
    public User login(HttpServletResponse response, @RequestBody User user){
        User user1 = template.selectOne("login", user);
        Cookie cookie = new Cookie("login", "true");
        response.addCookie(cookie);
        if (!Objects.isNull(user1)){
            log.info("登录的用户是" + user.getUserName());
            return user1;

        }
        return null;

    }*/

    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    @ApiOperation(value = "增加用户接口",httpMethod = "POST")
    public boolean addUser(HttpServletRequest request,@RequestBody User user){
        Boolean isCookie = verifyCookies(request);
        int result = 0;
        if (!isCookie){
            log.info("cookie不正确，不能增加用户");
            return false;
        }
        result = template.update("addUser", user);
        if (result > 0){
            log.info("用户增加成功,增加的用户数是：" + result);
            return true;
        }
        log.info("用户增加不成功");
        return false;

    }

    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    @ApiOperation(value = "获取用户列表接口",httpMethod = "POST")
    public List<User> getUserInfo(HttpServletRequest request,@RequestBody User user){

        Boolean isCookie = verifyCookies(request);
        if (!isCookie){
            log.info("cookie不正确，不能获取用户");
            return null;
        }
        List<User> users = template.selectList("getUserInfo", user);
        log.info("获取的用户数量是：" + users.size());
        return users;
    }

    @RequestMapping(value = "/updateUserInfo",method = RequestMethod.POST)
    @ApiOperation(value = "更新/删除用户接口",httpMethod = "POST")
    public int updateUser(HttpServletRequest request,@RequestBody User user){
        Boolean isCookie = verifyCookies(request);
        int result = 0;
        if (!isCookie){
            log.info("cookie不正确，不能获取用户");
            return result;
        }
        result = template.update("updateUserInfo", user);
        log.info("更新/删除用户成功，更新的条目数是：" + result);
        return result;

    }



    private Boolean verifyCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)) {
            log.info("cookies为空");
            return false;
        }
        for (Cookie cookie:cookies){
            if (cookie.getName().equals("login1") && cookie.getValue().equals("true1")){
                log.info("cookies验证通过");
                return true;
            }
        }
        return false;
    }


}
