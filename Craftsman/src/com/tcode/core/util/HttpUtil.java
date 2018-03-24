package com.tcode.core.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class HttpUtil {

    private static final CloseableHttpClient httpclient = HttpClients.createDefault();
    private static Logger log = Logger.getLogger("ILog");
    
    /**
     * 发送HttpPost请求，参数为obj
     * @param url
     * @param obj
     * @return
     */
    public static JSONObject sendPost(String url, Object obj) {
        
        CloseableHttpResponse response = null;
        JSONObject result = null;
        try {
        	HttpPost httppost = new HttpPost(url);
        	httppost.addHeader("Authorization", "Basic YWRtaW46c2VjcmV0");
            httppost.addHeader("Accept", "application/json");
            httppost.setHeader("Content-Type", "application/json;charset=UTF-8");
        	String jsonstr = JSON.toJSONString(obj);
        	log.warn("I_SEND:" + jsonstr);
        	//StringEntity se = new StringEntity(jsonstr);
        	//se.setContentType("text/json;charset=GBK");
        	//se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=GBK"));
        	HttpEntity ey = new ByteArrayEntity(jsonstr.getBytes("UTF-8"));
        	httppost.setEntity(ey);
        	
            response = httpclient.execute(httppost);
            int code = response.getStatusLine().getStatusCode();
            if(code == 200){
            	String entity = EntityUtils.toString(response.getEntity());
            	result = JSONObject.parseObject(entity);
            	log.warn("I_RECE:" + result);
            } else
            	log.error("I_ERROR:" + response);
        } catch (IOException e) {
        	log.error(Utils.getErrorMessage(e));
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发送不带参数的HttpPost请求
     * @param url
     * @return
     */
    public static String sendPost(String url) {
        HttpPost httppost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httppost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity entity = response.getEntity();
        String result = null;
        try {
            result = EntityUtils.toString(entity);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
