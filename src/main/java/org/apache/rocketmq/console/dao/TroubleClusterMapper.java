package org.apache.rocketmq.console.dao;

import org.apache.rocketmq.console.model.TroubleCluster;

public interface TroubleClusterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TroubleCluster record);

    int insertSelective(TroubleCluster record);

    TroubleCluster selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TroubleCluster record);

    int updateByPrimaryKey(TroubleCluster record);
}