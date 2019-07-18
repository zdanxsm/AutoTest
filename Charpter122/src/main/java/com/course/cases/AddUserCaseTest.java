package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.AddUserCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddUserCaseTest {

    @Test(dependsOnGroups = "loginTrue",description = "增加用户的测试")
    public void addUserCaseTest() throws IOException {
        SqlSession sqlSession = DatabaseUtil.getSqlSession();
        AddUserCase addUserCase = sqlSession.selectOne("addUserCase", 1);
        System.out.println(addUserCase.toString());
        System.out.println(TestConfig.addUserUrl);

        //发请求，获取结果
        String result = getResult(addUserCase);
        //用测试数据去用户表查数据，并打印数据
        User user = sqlSession.selectOne("addUser", addUserCase);
        System.out.println(user.toString());

        //验证返回结果
        Assert.assertEquals(addUserCase.getExpected(),result);
    }

    private String getResult(AddUserCase addUserCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.addUserUrl);
        //1设置请求参数
        JSONObject param = new JSONObject();
        param.put("userName",addUserCase.getUserName());
        param.put("password",addUserCase.getPassword());
        param.put("age",addUserCase.getAge());
        param.put("sex",addUserCase.getSex());
        param.put("permission",addUserCase.getPermission());
        param.put("isDelete",addUserCase.getIsDelete());
        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        post.setEntity(entity);
        //2设置头信息
        post.setHeader("content-type","application/json");
        //3设置cookie信息
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.cookieStore);
        //4发请求，并获得response结果
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        return result;
    }
}
