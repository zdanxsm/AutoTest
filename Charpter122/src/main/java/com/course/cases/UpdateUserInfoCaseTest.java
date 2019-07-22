package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserInfoCase;
import com.course.model.UpdateUserInfoCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class UpdateUserInfoCaseTest {
    @Test(dependsOnGroups = "loginTrue",description = "更新用户信息接口测试")
    public void updateUserInfoCaseTest() throws IOException {
        SqlSession sqlSession = DatabaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = sqlSession.selectOne("updateUserInfoCase", 1);
        System.out.println(updateUserInfoCase.toString());
        System.out.println(TestConfig.updateUserInfoUrl);

        //发请求，获取结果
        int result = getResult(updateUserInfoCase);
        //用测试数据去用户表查询，获得实际更新后的数据
        User user = sqlSession.selectOne(updateUserInfoCase.getExpected(), updateUserInfoCase);
        //验证预期和实际结果
        Assert.assertNotNull(user);
        Assert.assertNotNull(result);
        Assert.assertNotEquals(result,0);

    }



    @Test(dependsOnGroups = "loginTrue",description = "删除用户接口测试")
    public void deleteUserTest() throws IOException {
        SqlSession sqlSession = DatabaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = sqlSession.selectOne("updateUserInfoCase", 2);
        System.out.println(updateUserInfoCase.toString());
        System.out.println(TestConfig.updateUserInfoUrl);


        //发请求，获取结果
        int result = getResult(updateUserInfoCase);
        //用测试数据去用户表查询，获得实际更新后的数据
        User user = sqlSession.selectOne(updateUserInfoCase.getExpected(), updateUserInfoCase);
        //验证预期和实际结果
        Assert.assertNotNull(user);
        Assert.assertNotNull(result);
        Assert.assertNotEquals(result,0);
    }

    private int getResult(UpdateUserInfoCase updateUserInfoCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.updateUserInfoUrl);
        //设置请求参数
        JSONObject param = new JSONObject();
        param.put("id",updateUserInfoCase.getUserId());
        param.put("userName",updateUserInfoCase.getUserName());
        param.put("age",updateUserInfoCase.getAge());
        param.put("sex",updateUserInfoCase.getSex());
        param.put("permission",updateUserInfoCase.getPermission());
        param.put("isDelete",updateUserInfoCase.getIsDelete());
        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        post.setEntity(entity);

        //设置请求头
        post.setHeader("content-type","application/json");
        //设置请求cookie
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.cookieStore);
        //执行请求
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");

        return Integer.parseInt(result);
    }
}
