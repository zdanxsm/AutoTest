package com.course.controller;

import com.course.model.User11;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Log4j
@RestController
@RequestMapping("/v1")
@Api(value = "v1",description = "there are all of my mybatis 接口")
public class Demo {

    @Autowired
    private SqlSessionTemplate template;

    @RequestMapping(value = "/getUserCount",method = RequestMethod.GET)
    @ApiOperation(value = "获得用户数的接口",httpMethod = "GET")
    public int getUserCount(){
        return template.selectOne("getUserCount");

    }

    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    @ApiOperation(value = "增加用户接口",httpMethod = "POST")
    public int addUser(@RequestBody User11 user){
        int addUser = template.insert("addUser", user);
        return addUser;
    }

    /*@RequestMapping(value = "/addUser",method = RequestMethod.POST)
    @ApiOperation(value = "增加用户接口",httpMethod = "POST")
    public int addUser(){
        User user = new User();
        user.setId(23);
        user.setName("qqq");
        user.setAge(10);
        user.setSex("男");
        int addUser = template.insert("addUser", user);
        return addUser;
    }*/


    @RequestMapping(value = "/deleteUser",method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除数据",httpMethod = "POST")
    public int deleteUser(@RequestParam int id){
        int deleteUser = template.delete("deleteUser", id);
        return deleteUser;
    }

    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    @ApiOperation(value = "根据传递的参数更新数据接口",httpMethod = "POST")
    public int updateUser(@RequestBody User11 user){
        int updateUser = template.update("updateUser", user);
        return updateUser;
    }

}
