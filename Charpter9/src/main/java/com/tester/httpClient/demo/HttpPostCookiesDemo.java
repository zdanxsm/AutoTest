package com.tester.httpClient.demo;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class HttpPostCookiesDemo {

    private String testHostUrl;
    private ResourceBundle bundle;
    private CookieStore cookieStore;

    @BeforeTest
    public void beforeTest(){
        bundle = ResourceBundle.getBundle("application",Locale.CHINA);
        testHostUrl = bundle.getString("test.url");

    }

    @Test
    public void testGetCookies() throws IOException {
        String testUrl = this.testHostUrl + bundle.getString("getCookies.uri");
        HttpGet httpGet = new HttpGet(testUrl);
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        HttpResponse response = defaultHttpClient.execute(httpGet);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");

        this.cookieStore = defaultHttpClient.getCookieStore();
    }

    @Test(dependsOnMethods = {"testGetCookies"})
    public void testCookiePostRequest() throws IOException {
        String testUrl = this.testHostUrl + bundle.getString("postWithCookies");
        HttpPost httpPost = new HttpPost(testUrl);
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        //设置请求cookies
        defaultHttpClient.setCookieStore(this.cookieStore);
        //设置请求头
        httpPost.setHeader("content-type","application/json");
        //设置请求参数(json格式参数)
        JSONObject param = new JSONObject();
        param.put("name","zhangsan");
        param.put("age","18");
        StringEntity stringEntity = new StringEntity(param.toString(), "utf-8");
        httpPost.setEntity(stringEntity);

        /*
        // 创建请求参数param格式
        List<NameValuePair> list = new LinkedList<>();
        BasicNameValuePair param1 = new BasicNameValuePair("name", "root");
        BasicNameValuePair param2 = new BasicNameValuePair("password", "123456");
        list.add(param1);
        list.add(param2);
        // 使用URL实体转换工具
        UrlEncodedFormEntity entityParam = new UrlEncodedFormEntity(list, "UTF-8");
        httpPost.setEntity(entityParam);
        * */

        //获取返回状态码
        HttpResponse response = defaultHttpClient.execute(httpPost);
        System.out.println("the response is: " + response);
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        if (statusCode == 200){
            //处理返回结果
            String result = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println("the result is: " + result);
            JSONObject jsonResult = new JSONObject(result);
            String success = (String) jsonResult.get("success");
            String status = (String) jsonResult.get("status");
            Assert.assertEquals("success",success);
            Assert.assertEquals("1",status);
        }


    }
}
