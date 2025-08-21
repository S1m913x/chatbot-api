package org.yjys.api.test;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.junit.Test;

import java.io.IOException;

public class ApiTest {
    @Test
    public void query_unanswered_query() throws IOException, ParseException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/28882252581181/topics?scope=unanswered_questions&count=20");
        get.addHeader("cookie", "zsxq_access_token=12D658F8-A6C1-40C4-869C-227DB4C9FD1C_9F87A41B1A4E8920; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22415422884182228%22%2C%22first_id%22%3A%22198aca84c3d14c2-082a7db7a8e92c8-16525636-1892970-198aca84c3e261d%22%2C%22props%22%3A%7B%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTk4YWNhODRjM2QxNGMyLTA4MmE3ZGI3YThlOTJjOC0xNjUyNTYzNi0xODkyOTcwLTE5OGFjYTg0YzNlMjYxZCIsIiRpZGVudGl0eV9sb2dpbl9pZCI6IjQxNTQyMjg4NDE4MjIyOCJ9%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22415422884182228%22%7D%7D; abtest_env=product");
        get.addHeader("content-type", "application/json; charset=UTF-8");

        CloseableHttpResponse response = httpClient.execute(get);
        if(response.getCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else  {
            System.out.println(response.getCode());
        }
    }

    @Test
    public void answer() throws IOException, ParseException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/2852112411881481/answer");
        post.addHeader("cookie", "zsxq_access_token=12D658F8-A6C1-40C4-869C-227DB4C9FD1C_9F87A41B1A4E8920; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22415422884182228%22%2C%22first_id%22%3A%22198aca84c3d14c2-082a7db7a8e92c8-16525636-1892970-198aca84c3e261d%22%2C%22props%22%3A%7B%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTk4YWNhODRjM2QxNGMyLTA4MmE3ZGI3YThlOTJjOC0xNjUyNTYzNi0xODkyOTcwLTE5OGFjYTg0YzNlMjYxZCIsIiRpZGVudGl0eV9sb2dpbl9pZCI6IjQxNTQyMjg4NDE4MjIyOCJ9%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22415422884182228%22%7D%7D; abtest_env=product");
        post.addHeader("content-type", "application/json; charset=UTF-8");

        String paramJson = "{\"req_data\":{\"text\":\"自己去百度\\n\",\"image_ids\":[],\"silenced\":false}}";
        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if(response.getCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else  {
            System.out.println(response.getCode());
        }

    }

}
