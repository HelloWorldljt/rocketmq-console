package org.apache.rocketmq.console.num;

import org.apache.rocketmq.console.support.IntEnum;

/**
 * 1 待处理 2 已处理
 * @author lijiangtao
 */
public enum TroubleClusterStatus implements IntEnum {

    UNHANDLE(1,"待处理"),
    HANDLED(2,"已处理"),

    ;

    private int code;
    private String desc;
    TroubleClusterStatus(int code, String desc){
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
