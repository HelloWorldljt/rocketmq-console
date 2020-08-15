package org.apache.rocketmq.console.num;


import org.apache.rocketmq.console.support.IntEnum;

/**
 * 死信类型
 * @author lijiangtao
 */

public enum MsgType implements IntEnum {

    /**
     * 死信
     */
    DEAD_LETTER(1,"死信"),
    /**
     * 事务消息
     */
    TRANS_LETTER(2,"事务消息");


    private int code;
    private String desc;

    MsgType(int code, String desc) {
        this.code=code;
        this.desc=desc;
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
