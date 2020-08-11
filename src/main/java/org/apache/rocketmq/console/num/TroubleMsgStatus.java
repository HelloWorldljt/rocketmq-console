package org.apache.rocketmq.console.num;

/**
 * 异常消息状态
 * @author duanz
 */
public enum TroubleMsgStatus{
    /**
     * 未处理
     */
    TO_DO(1,"未处理"),
    /**
     * 已处理
     */
    DONE(2,"已处理");

    private int val;
    private String desc;

    TroubleMsgStatus(int val, String desc) {
        this.val=val;
        this.desc=desc;
    }

    public int intValue() {
        return this.val;
    }

    @Override
    public String toString() {
        return desc;
    }
}
