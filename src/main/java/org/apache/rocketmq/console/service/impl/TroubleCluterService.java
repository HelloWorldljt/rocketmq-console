package org.apache.rocketmq.console.service.impl;

import org.apache.rocketmq.console.dao.TroubleCluterMapper;
import org.apache.rocketmq.console.model.TroubleCluster;
import org.apache.rocketmq.console.num.ClusterInfoType;
import org.apache.rocketmq.console.num.TroubleClusterStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class TroubleCluterService{

    @Resource
    private TroubleCluterMapper troubleCluterMapper;

    
    public int deleteByPrimaryKey(Integer id) {
        return troubleCluterMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(TroubleCluster record) {
        return troubleCluterMapper.insert(record);
    }

    
    public int insertSelective(TroubleCluster record) {
        return troubleCluterMapper.insertSelective(record);
    }

    
    public TroubleCluster selectByPrimaryKey(Integer id) {
        return troubleCluterMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(TroubleCluster record) {
        return troubleCluterMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(TroubleCluster record) {
        return troubleCluterMapper.updateByPrimaryKey(record);
    }

    public void insert(ClusterInfoType type,String jsonDetails, String remark) {
        TroubleCluster troubleCluster = new TroubleCluster();
        troubleCluster.setType(type);
        troubleCluster.setDetails(jsonDetails);
        troubleCluster.setStatus(TroubleClusterStatus.UNHANDLE);
        troubleCluster.setCreateTime(new Date());
        troubleCluster.setRemark(remark);
        int insert = troubleCluterMapper.insert(troubleCluster);
        Assert.isTrue(insert == 1,"trouble cluster info insert fail");
    }
}
