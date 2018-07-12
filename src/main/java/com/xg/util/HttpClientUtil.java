package com.xg.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http tool
 *
 * @author guzhen
 * @date 2018/7/12
 */
public class HttpClientUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);


  private static final CloseableHttpClient HTTP_CLIENT;

  static {
    PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    cm.setMaxTotal(100);
    cm.setDefaultMaxPerRoute(20);
    cm.setDefaultMaxPerRoute(50);
    HTTP_CLIENT = HttpClients.custom().setConnectionManager(cm).build();
  }

  public static String getJson(String url) {
    CloseableHttpResponse response = null;
    String result = "";
    try {
      HttpGet httpGet = new HttpGet(url);
      RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000).setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
      httpGet.setConfig(requestConfig);
      httpGet.addHeader("Content-type", "application/json; charset=utf-8");
      httpGet.setHeader("Accept", "application/json");
      response = HTTP_CLIENT.execute(httpGet);
      result= EntityUtils.toString(response.getEntity(),Charset.forName("UTF-8"));
    } catch (IOException e) {
      LOGGER.error(e.getMessage(),e);
    } finally {
      HttpClientUtils.closeQuietly(response);
    }
    return result;
  }

  public static String postJson(String url, String jsonString) {
    CloseableHttpResponse response = null;
    String result = "";
    try {
      HttpPost httpPost = new HttpPost(url);
      RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000).setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
      httpPost.setConfig(requestConfig);
      httpPost.setConfig(requestConfig);
      httpPost.addHeader("Content-type", "application/json; charset=utf-8");
      httpPost.setHeader("Accept", "application/json");
      httpPost.setEntity(new StringEntity(jsonString, Charset.forName("UTF-8")));
      response = HTTP_CLIENT.execute(httpPost);
      result = EntityUtils.toString(response.getEntity(),Charset.forName("UTF-8"));
    } catch (IOException e) {
      LOGGER.error(e.getMessage(),e);
    } finally {
      HttpClientUtils.closeQuietly(response);
    }
    return result;
  }


}
