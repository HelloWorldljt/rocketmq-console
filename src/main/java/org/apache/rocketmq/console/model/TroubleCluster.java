package org.apache.rocketmq.console.model;

import org.apache.rocketmq.console.num.ClusterInfoType;
import org.apache.rocketmq.console.num.TroubleClusterStatus;

import java.util.Date;

/**
 * rocketmq集群问题监控
 * @author lijiangtao
 */
public class TroubleCluster {

    private Integer id;

    /**
    * 异常信息类型：1 broker集群 2 consumer实例 3 消息挤压 4 namesrv集群 
    */
    private ClusterInfoType type;

    /**
    * 异常信息json描述
    */
    private String details;

    /**
    * 问题处理状态：1 待处理 2 已处理
    */
    private TroubleClusterStatus status;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 备注
    */
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ClusterInfoType getType() {
        return type;
    }

    public void setType(ClusterInfoType type) {
        this.type = type;
    }

    public TroubleClusterStatus getStatus() {
        return status;
    }

    public void setStatus(TroubleClusterStatus status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}