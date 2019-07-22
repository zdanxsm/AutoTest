package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserInfoCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetUserInfoCaseTest {

    @Test(dependsOnGroups = "loginTrue",description = "获得用户信息接口测试")
    public void getUserInfoCaseTest() throws IOException {
        SqlSession sqlSession = DatabaseUtil.getSqlSession();
        GetUserInfoCase getUserInfoCase = sqlSession.selectOne("getUserInfoCase", 1);
        System.out.println(getUserInfoCase.toString());
        System.out.println(TestConfig.getUserInfoUrl);


        //发送请求获取结果
        JSONArray resultJson = getUserInfoJsonResult(getUserInfoCase);
        JSONArray resultJsonArray = new JSONArray(resultJson.getString(0));
        //用测试数据getUserListCase，在用户表查询出用户列表
        User userExpected = sqlSession.selectOne(getUserInfoCase.getExpected(), getUserInfoCase);
        List<User> arrayListExpected = new ArrayList<>();
        arrayListExpected.add(userExpected);
        JSONArray jsonArrayExpected = new JSONArray(arrayListExpected);

        //验证预期和实际结果
        Assert.assertEquals(resultJsonArray.toString(),jsonArrayExpected.toString());
    }

    private JSONArray getUserInfoJsonResult(GetUserInfoCase getUserInfoCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserInfoUrl);
        //设置请求参数
        JSONObject param = new JSONObject();
        param.put("id",getUserInfoCase.getUserId());
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        //设置请求头
        post.setHeader("content-type","application/json");
        //设置cookies
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.cookieStore);
        //执行请求
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        List resultList = Arrays.asList(result);
        return new JSONArray(resultList);
    }
}
