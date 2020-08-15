package org.apache.rocketmq.console.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.console.config.Params;
import org.apache.rocketmq.console.dao.TroubleMsgMapper;
import org.apache.rocketmq.console.model.MessageView;
import org.apache.rocketmq.console.model.TroubleMsg;
import org.apache.rocketmq.console.num.MsgType;
import org.apache.rocketmq.console.num.TroubleMsgStatus;
import org.apache.rocketmq.console.task.TroubleMsgCollectTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class TroubleMsgService {
    @Resource
    private TroubleMsgMapper troubleMsgMapper;
    @Resource
    private Params params;
    private Logger logger = LoggerFactory.getLogger(TroubleMsgCollectTask.class);

    public void batchSaveDeadMsg(List<MessageView> messageViews,MsgType msgType){
        List<MessageView> toSaveMessages = new ArrayList<>();
        List<TroubleMsg> troubleMsgs=new ArrayList<>();
        Map<String,Integer> listCache = new HashMap<>(messageViews.size());
        for(MessageView messageView: messageViews){
            String msgId = messageView.getMsgId();
            String topic = messageView.getTopic();
            if(msgType==MsgType.TRANS_LETTER){
               topic= messageView.getProperties().get(MessageConst.PROPERTY_REAL_TOPIC);
            }
            int count =troubleMsgMapper.queryIfExist(msgId,topic);
            if(count==0&&!alreadyInWaitList(messageView,listCache)){
                toSaveMessages.add(messageView);
            }
        }

        if(toSaveMessages.size()>0){

            for(MessageView messageView:toSaveMessages){
                TroubleMsg msg= new TroubleMsg();
                msg.setCreateTime(new Date());
                msg.setMsgBody(messageView.getMessageBody());
                msg.setMsgProperty(JSON.toJSONString(messageView.getProperties()) );
                msg.setSendTime(new Date(messageView.getBornTimestamp()) );
                msg.setStatus(TroubleMsgStatus.TO_DO);
                msg.setType(msgType);
                msg.setMsgId(messageView.getMsgId());

                if(msgType==MsgType.DEAD_LETTER){

                    msg.setTopic(messageView.getTopic());
                }
                if(msgType==MsgType.TRANS_LETTER){
                   msg.setTopic(messageView.getProperties().get(MessageConst.PROPERTY_REAL_TOPIC));
                }
                troubleMsgs.add(msg);
            }
        }

        if(troubleMsgs.size()>0){
            logger.info("batchInsert start,count:"+troubleMsgs.size());
            /**
             * 分页插入
             */
            for(List<TroubleMsg> subList: Lists.partition(troubleMsgs,params.getBatchInsertSize())){
                troubleMsgMapper.batchInsert(subList);
            }
            logger.info("batchInsert end,count:"+troubleMsgs.size());
        }
    }

    private boolean alreadyInWaitList(MessageView messageView,Map<String,Integer> map) {

        if(map.get(messageView.getMsgId())==null){
            map.put(messageView.getMsgId(),1);
            return false;
        }
        return true;
    }


    /**
     * 查询死信消息
     * @param topic
     * @param msgId
     * @param status
     * @return
     */
    public List<TroubleMsg> queryTroubleMsg(String topic, String msgId, Integer status) {
        return troubleMsgMapper.queryTroubleMsg(topic,msgId,status);
    }
}
