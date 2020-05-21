package org.apache.rocketmq.console.model;

import org.apache.rocketmq.console.num.MsgType;
import org.apache.rocketmq.console.num.TroubleMsgStatus;

import java.util.Date;

/**
 * 异常消息
 * @author duanz
 */
public class TroubleMsg {

    private Integer id;
    private String msgId;
    private MsgType type;
    private String topic;
    private String msgProperty;
    private String msgBody;
    private Date sendTime;
    private TroubleMsgStatus status;

    private Date createTime;
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public MsgType getType() {
        return type;
    }

    public void setType(MsgType type) {
        this.type = type;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public TroubleMsgStatus getStatus() {
        return status;
    }

    public void setStatus(TroubleMsgStatus status) {
        this.status = status;
    }

    public String getMsgProperty() {
        return msgProperty;
    }

    public void setMsgProperty(String msgProperty) {
        this.msgProperty = msgProperty;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "TroubleMsg{" +
                "id=" + id +
                ", msgId='" + msgId + '\'' +
                ", type=" + type +
                ", topic='" + topic + '\'' +
                ", msgProperty='" + msgProperty + '\'' +
                ", msgBody='" + msgBody + '\'' +
                ", sendTime=" + sendTime +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
