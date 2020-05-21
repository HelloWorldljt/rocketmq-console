package org.apache.rocketmq.console.task;

import com.xiangshang.elasticjob.lite.starter.annotation.ElasticSimpleJob;
import com.xiangshang.elasticjob.lite.starter.job.AbstractSimpleJob;
import com.xiangshang360.middleware.sdk.util.DateUtils;
import io.elasticjob.lite.api.ShardingContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.MixAll;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.common.protocol.body.TopicList;
import org.apache.rocketmq.console.config.Params;
import org.apache.rocketmq.console.model.MessageView;
import org.apache.rocketmq.console.model.request.TopicConfigInfo;
import org.apache.rocketmq.console.num.MsgType;
import org.apache.rocketmq.console.service.MessageService;
import org.apache.rocketmq.console.service.TopicService;
import org.apache.rocketmq.console.service.TroubleMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 收集失败的死信消息
 *
 * @author duanz
 */
@ElasticSimpleJob(appName = "${spring.application.name}",
        jobClass = TroubleMsgCollectTask.class,
        cron = "0 0/5 * * * ?",
        description = "收集失败的死信消息/事务消息")
@Service
public class TroubleMsgCollectTask extends AbstractSimpleJob {
    private Logger logger = LoggerFactory.getLogger(TroubleMsgCollectTask.class);

    @Resource
    private TopicService topicService;

    @Resource
    private MessageService messageService;

    @Resource
    private TroubleMsgService troubleMsgService;

    @Resource
    private Params params;

    /**
     * 收集死信数据
     */

    public void collectDeadLetter() {

        logger.info("collectDeadLetter start");

        TopicList topicList = topicService.fetchAllTopicList();

        for (String topic : topicList.getTopicList()) {
            if (topic.startsWith(MixAll.DLQ_GROUP_TOPIC_PREFIX)) {
                List<TopicConfigInfo> topicConfigs = topicService.examineTopicConfig(topic);

                for (TopicConfigInfo topicConfigInfo : topicConfigs) {
                    //如果只有写，修改成读写
                    if (topicConfigInfo.getPerm() == 2) {
                        topicConfigInfo.setPerm(6);
                        topicService.createOrUpdate(topicConfigInfo);
                    }

                    try {

                        saveMessage(topic);
                    } catch (Throwable e) {
                        logger.error("collectDeadLetter error", e);
                    }
                }

            }
        }
        logger.info("collectDeadLetter end");

    }


    public void collectTransMsg() {
        logger.info("collectTransMsg start");
        Date endDate = new Date();
        Date startDate = DateUtils.addMinutes(endDate, -10);
        List<MessageView> messageViews = messageService.queryMessageByTopic(MixAll.RMQ_SYS_TRANS_HALF_TOPIC, startDate.getTime(), endDate.getTime(), true);
        List<MessageView> toSaveMsg = new ArrayList<>();
        for (MessageView messageView : messageViews) {

            String checkTimes = messageView.getProperties().get(MessageConst.PROPERTY_TRANSACTION_CHECK_TIMES);
            if (!StringUtils.isEmpty(checkTimes)) {
                int checkTime = Integer.parseInt(checkTimes);
                logger.info("checkTime:{},paramsCheckTime:{}",checkTime,params.getTransMaxCheckTimes());
                if (checkTime > params.getTransMaxCheckTimes()) {
                    toSaveMsg.add(messageView);
                }
            }

        }
        troubleMsgService.batchSaveDeadMsg(toSaveMsg, MsgType.TRANS_LETTER);
        logger.info("collectTransMsg end");

    }

    public void saveMessage(String topic) {

        Date endDate = new Date();
        Date startDate = DateUtils.addMinutes(endDate, -10);
        saveMessage(topic, startDate, endDate);
    }

    public void saveMessage(String topic, Date startDate, Date endDate) {
        try {
            logger.info("saveMessage topic:{}", topic);
            List<MessageView> messageViews = messageService.queryMessageByTopic(topic, startDate.getTime(), endDate.getTime(), true);
            logger.info("saveMessage topic:{},deadCount:{}", topic, messageViews.size());
            troubleMsgService.batchSaveDeadMsg(messageViews, MsgType.DEAD_LETTER);
        } catch (Exception e) {
            logger.error("save deadLetter error", e);
        }

    }


    @Override
    public void doTask(ShardingContext content) {
        collectDeadLetter();
        collectTransMsg();
    }
}
