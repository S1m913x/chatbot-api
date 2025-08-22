package org.yjys.api.application.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.yjys.api.domain.ai.IDeepSeek;
import org.yjys.api.domain.zsxq.IZsxqApi;
import org.yjys.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import org.yjys.api.domain.zsxq.model.vo.Question;
import org.yjys.api.domain.zsxq.model.vo.Topics;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

@EnableScheduling
@Configuration
public class ChatBotSchedule {
    private static final Logger logger = LoggerFactory.getLogger(ChatBotSchedule.class);

    @Value("${chatbot-api.groupId}")
    private String groupId;

    @Value("${chatbot-api.cookie}")
    private String cookie;

    @Resource
    private IZsxqApi zsxqApi;

    @Resource
    private IDeepSeek deepSeek;

    @Scheduled(cron = "0/5 * * * * ?")
    public void run()  {
        if (new Random().nextBoolean()) {
            logger.info("随机打烊中");
            return;
        }

        try {
            UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates = zsxqApi.queryUnAnsweredQuestionsTopicId(groupId, cookie);
            List<Topics> topics = unAnsweredQuestionsAggregates.getResp_data().getTopics();
            if (topics == null || topics.isEmpty()) {
                logger.info("本次检测未查询到待回答问题");
                return;
            }

            Topics topic = topics.get(0);
            String answer = deepSeek.doDeepSeek(topic.getQuestion().getText().trim());

            boolean status = zsxqApi.answer(groupId, cookie, topic.getTopic_id(), answer, false);
            logger.info("编号: {} 问题: {} 回答: {} 状态: {}", topic.getTopic_id(), topic.getQuestion().getText(), answer, status);
        } catch (Exception e) {
            logger.error("自动回答问题异常", e);
        }



    }
}
