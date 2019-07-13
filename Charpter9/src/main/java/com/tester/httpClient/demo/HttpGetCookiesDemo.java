package com.tester.httpClient.demo;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class HttpGetCookiesDemo {
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
        List<Cookie> cookies = this.cookieStore.getCookies();
        for (Cookie cookie: cookies){
            String cookieName = cookie.getName();
            String cookieValue = cookie.getValue();
            System.out.println("cookieName = "+cookieName+"  cookieValue = "+cookieValue);
        }
    }

    @Test(dependsOnMethods = {"testGetCookies"})
    public void testCookieGetRequest() throws IOException, URISyntaxException {
        /*
        *由于GET请求的参数都是拼装在URL地址后方，所以我们要构建一个URL，带参数
        */

        String testUrl = this.testHostUrl + bundle.getString("getWithCookies");
        URIBuilder uriBuilder = new URIBuilder(testUrl);
        LinkedList<NameValuePair> list = new LinkedList<>();
        BasicNameValuePair param1 = new BasicNameValuePair("name", "zhangsan");
        BasicNameValuePair param2 = new BasicNameValuePair("age", "18");
        list.add(param1);
        list.add(param2);

        //转化参数
        String params = EntityUtils.toString(new UrlEncodedFormEntity(list,Consts.UTF_8));
        System.out.println(params);
        //创建HttpGet请求
        HttpGet httpGet = new HttpGet(testUrl+"?"+params);
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();

        //设置请求cookies
        defaultHttpClient.setCookieStore(this.cookieStore);

        //设置请求头
        httpGet.setHeader("content-type","application/json");

        //执行get请求
        HttpResponse response = defaultHttpClient.execute(httpGet);
        //获取请求结果
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("responseCode = " + statusCode);

        if (statusCode == 200){
            String result = EntityUtils.toString(response.getEntity(),"utf-8");
            System.out.println(result);
        }

    }

}
