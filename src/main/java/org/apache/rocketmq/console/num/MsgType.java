package org.apache.rocketmq.console.num;

import com.xiangshang360.middleware.sdk.serial.IntEnumInter;

public enum MsgType implements IntEnumInter<MsgType> {

    /**
     * 死信
     */
    DEAD_LETTER(1,"死信"),
    /**
     * 事务消息
     */
    TRANS_LETTER(2,"事务消息");


    private int val;
    private String desc;

    MsgType(int val, String desc) {
        this.val=val;
        this.desc=desc;
    }


    @Override
    public int intValue() {
        return this.val;
    }
    @Override
    public String toString(){
        return this.desc;
    }
}
