package org.apache.rocketmq.console.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 系统参数
 * @author duanz
 * @date 2019-05-23
 */
@Service
public class Params {
    @Value("${params.trans-max-check-times}")
    private Integer transMaxCheckTimes;
    @Value("${params.batch-insert-size}")
    private Integer batchInsertSize;


    public Integer getTransMaxCheckTimes() {
        return transMaxCheckTimes;
    }

    public void setTransMaxCheckTimes(Integer transMaxCheckTimes) {
        this.transMaxCheckTimes = transMaxCheckTimes;
    }

    public Integer getBatchInsertSize() {
        return batchInsertSize;
    }

    public void setBatchInsertSize(Integer batchInsertSize) {
        this.batchInsertSize = batchInsertSize;
    }
}
