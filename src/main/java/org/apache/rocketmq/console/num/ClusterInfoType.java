package org.apache.rocketmq.console.num;

import org.apache.rocketmq.console.support.IntEnum;

/**
 * 异常信息类型：1 broker集群 2 consumer实例 3 消息积压 4 namesrv集群
 *
 * @author lijiangtao
 */
public enum ClusterInfoType implements IntEnum {

    BROKER_CLUSTOR(1,"broker集群"),
    consumer_instance(2,"consumer"),
    MESSAGE_BACKLOG(3,"消息积压"),
    NAMESRV_CLUSTOR(4,"namesrv集群"),
    ;

    private int code;
    private String desc;
    ClusterInfoType(int code,String desc){
        this.code = code;
        this.desc = desc;
    }


    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String toString(){
        return this.desc;
    }

}
