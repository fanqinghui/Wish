package com.foundation.common.http;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.foundation.common.utils.Security;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 基于 httpclient 4.x版本的 httpClient工具类
 * Created by fqh on 2015/12/14.
 */
public class HttpClientUtils {

    private static PoolingHttpClientConnectionManager connManager = null;
    private static final CloseableHttpClient httpClient;
    public static final String CHARSET = "UTF-8";
    public static final Integer CONNECTTIMEOUT = 60000000;
    public static final Integer SOCKETTIMEOUT = 3000000;

    static {
        //超时时间设置为600000毫秒，socket链接超时时间设置为30000毫秒
        RequestConfig config = RequestConfig.custom().setConnectTimeout(CONNECTTIMEOUT).setSocketTimeout(SOCKETTIMEOUT).build();
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        //httpClient=HttpConnectionManager.getHttpClient();
    }

    public static String doGet(String url, Map<String, String> params) {
        return doGet(url, params, CHARSET);
    }

    public static String doPost(String url, Map<String, String> params) {
        return doPost(url, params, CHARSET);
    }

    public static CloseableHttpClient getHttpClient(){
        //httpClient.getHttpConnectionManager().getParams().setTcpNoDelay(true);
        return httpClient;
    }
    /**
     * HTTP Get 获取内容
     *
     * @param url     请求的url地址 ?之前的地址
     * @param params  请求的参数
     * @param charset 编码格式
     * @return 页面内容
     */
    public static String doGet(String url, Map<String, String> params, String charset) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            }
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * HTTP Post 获取内容
     *
     * @param url     请求的url地址 ?之前的地址
     * @param params  请求的参数
     * @param charset 编码格式
     * @return 页面内容
     */
    public static String doPost(String url, Map<String, String> params, String charset) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<>(params.size());
                //去掉NameValuePair转换，这样就可以传递Map<String,Object>
                /*pairs = new ArrayList<NameValuePair>(params.size());*/
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(url);
            if (pairs != null && pairs.size() > 0) {
                httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET));
            }
            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //利用post方式上传zip压缩文件
   /* public static String upLoadZip(String url, Map<String, String> params, InputStream input,String zipFileName) throws Exception {
        HttpPost post = new HttpPost(url);
        InputStream inputStream = new FileInputStream(zipFileName);
        //File file = new File(imageFileName);
        String message = "This is a multipart post";
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
      *//*  builder.addBinaryBody
                ("upfile", file, ContentType.DEFAULT_BINARY, imageFileName);*//*
        builder.addBinaryBody("upstream", inputStream, ContentType.create("application/zip"), zipFileName);
        builder.addTextBody("text", message, ContentType.TEXT_PLAIN);

        HttpEntity entity = builder.build();
        post.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(post);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            post.abort();
            throw new RuntimeException("HttpClient,error status code :" + statusCode);
        }
        HttpEntity resEntity = response.getEntity();
        String result = null;
        if (resEntity != null) {
            result = EntityUtils.toString(entity, "utf-8");
        }
        EntityUtils.consume(entity);
        response.close();
        return result;
    }

    //利用post方式上传Img
    public static String upLoadImg(String url, Map<String, String> params, InputStream input,String imageFileName) throws Exception {
        HttpPost post = new HttpPost(url);
        InputStream inputStream = new FileInputStream(imageFileName);
        String message = "This is a multipart post";
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addBinaryBody("upstream", inputStream, ContentType.create("application/octet-stream"), imageFileName);
        builder.addTextBody("text", message, ContentType.TEXT_PLAIN);

        HttpEntity entity = builder.build();
        post.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(post);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            post.abort();
            throw new RuntimeException("HttpClient,error status code :" + statusCode);
        }
        HttpEntity resEntity = response.getEntity();
        String result = null;
        if (resEntity != null) {
            result = EntityUtils.toString(entity, "utf-8");
        }
        EntityUtils.consume(entity);
        response.close();
        return result;
    }*/


    public static void main(String[] args) {
       // String getData = doGet("http://localhost:8888/msapp/v1/init", null);
       // System.out.println("----------------------分割线1-----------------------");
       // System.out.println(getData);
        System.out.println("----------------------分割线2-----------------------");
        Map<String,String> paramsMaps= Maps.newHashMap();
        paramsMaps.put("tenderId","1019");
        paramsMaps.put("key", Security.md5("11"+"13"));
        paramsMaps.put("salt1","11");
        paramsMaps.put("salt2","12");
        paramsMaps.put("salt3","13");
        paramsMaps.put("salt4","14");
        String postData = doPost("http://10.0.9.253:8080/appservice/queryTenderDetail.action", paramsMaps);
        System.out.println("----------------------分割线2-----------------------");
        System.out.println(postData);
        System.out.println("----------------------分割线2-----------------------");
    }

}
