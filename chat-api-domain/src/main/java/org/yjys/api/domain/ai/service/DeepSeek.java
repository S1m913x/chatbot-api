package org.yjys.api.domain.ai.service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.yjys.api.domain.ai.IDeepSeek;
import org.yjys.api.domain.ai.model.aggregates.AIAnswer;
import org.yjys.api.domain.ai.model.vo.Choices;
import org.yjys.api.domain.zsxq.IZsxqApi;

import java.io.IOException;
import java.util.List;

@Service
public class DeepSeek implements IDeepSeek {

    private Logger logger = LoggerFactory.getLogger(DeepSeek.class);

    @Value("${chatbot-api.deepSeekKey}")
    private String deepSeekKey;

    @Override
    public String doDeepSeek(String question) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.deepseek.com/chat/completions");
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer " + deepSeekKey);

        String paramJson = "{\"model\": \"deepseek-chat\",\"messages\": [ {\"role\": \"system\", \"content\": \"You are a helpful assistant.\"}, {\"role\": \"user\", \"content\": \"" +
                question +
                "\"}],\"stream\": false}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("application/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            AIAnswer aiAnswer = JSON.parseObject(jsonStr, AIAnswer.class);
            StringBuilder answers = new StringBuilder();
            List<Choices> choices = aiAnswer.getChoices();
            for (Choices choice : choices) {
                answers.append(choice.getMessage().getContent());
            }
            return answers.toString();
        } else {
            throw new RuntimeException("doDeepSeek Err code is" + response.getStatusLine().getReasonPhrase());
        }
    }
}
