package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserListCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import com.course.utils.JsonPrintOut;
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
import java.util.Arrays;
import java.util.List;

public class GetUserListCaseTest {

    @Test(dependsOnGroups = "loginTrue",description = "获取用户列表接口的测试")
    public void getUserListCaseTest() throws IOException {
        SqlSession sqlSession = DatabaseUtil.getSqlSession();
        GetUserListCase getUserListCase = sqlSession.selectOne("getUserListCase",1);
        System.out.println(getUserListCase.toString());
        System.out.println(TestConfig.getUserListUrl);

        //发送请求获取结果
        JSONArray resultJson = getUserInfoJsonResult(getUserListCase);
        JSONArray resultJsonArray = new JSONArray(resultJson.getString(0));
        System.out.println(resultJsonArray);
        //用测试数据getUserListCase，在用户表查询出用户列表
        List<User> userListExpect = sqlSession.selectList(getUserListCase.getExpected(), getUserListCase);
        /*for (User user:userListExpect){
            System.out.println("查库获取的用户列表是：" + user.toString());
        }*/
        JSONArray userListJsonExpect = new JSONArray(userListExpect);
        System.out.println(userListJsonExpect);
        //验证返回结果
        Assert.assertEquals(userListJsonExpect.length(),resultJsonArray.length());
        for (int i = 0; i < resultJsonArray.length(); i++) {
            JSONObject expect = (JSONObject) userListJsonExpect.get(i);
            JSONObject actual = (JSONObject) resultJsonArray.get(i);
            Assert.assertEquals(expect.toString(),actual.toString());
        }

    }

    private JSONArray getUserInfoJsonResult(GetUserListCase getUserListCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserInfoUrl);
        //设置请求参数
        JSONObject param = new JSONObject();
        param.put("userName",getUserListCase.getUserName());
        param.put("age",getUserListCase.getAge());
        param.put("sex",getUserListCase.getSex());
        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        post.setEntity(entity);

        //设置请求头
        post.setHeader("content-type","application/json");
        //设置请求的携带的cookie
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.cookieStore);
        //执行请求
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        List resultList = Arrays.asList(result);
        JSONArray jsonArray = new JSONArray(resultList);

        return jsonArray;
    }
}
