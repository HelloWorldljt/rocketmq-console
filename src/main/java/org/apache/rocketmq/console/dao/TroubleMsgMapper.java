package org.apache.rocketmq.console.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.rocketmq.console.model.TroubleMsg;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * trouble msg mapper
 *
 * @author duanz
 */
@Mapper
public interface TroubleMsgMapper {
    /**
     * 插入
     *
     * @param troubleMsg
     */
    void insert(TroubleMsg troubleMsg);

    /**
     * 批量插入
     *
     * @param msgList
     */
    void batchInsert(@Param("list") List<TroubleMsg> msgList);

    /**
     * 查询是否已经存在
     *
     * @param msgId
     * @param topic
     * @return
     */
    int queryIfExist(@Param("msgId") String msgId, @Param("topic") String topic);

    /**
     * 查询死信消息
     * @param topic
     * @param msgId
     * @param status
     * @return
     */
    List<TroubleMsg> queryTroubleMsg(@Param("topic") String topic, @Param("msgId") String msgId, @Param("status") Integer status);
}
