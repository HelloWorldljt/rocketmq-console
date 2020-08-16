/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.rocketmq.console.task;

import com.google.common.collect.Maps;
import org.apache.rocketmq.common.protocol.body.ClusterInfo;
import org.apache.rocketmq.common.protocol.route.BrokerData;
import org.apache.rocketmq.console.config.RMQConfigure;
import org.apache.rocketmq.console.model.ConsumerMonitorConfig;
import org.apache.rocketmq.console.model.GroupConsumeInfo;
import org.apache.rocketmq.console.service.ConsumerService;
import org.apache.rocketmq.console.service.MonitorService;
import org.apache.rocketmq.console.service.impl.TroubleCluterService;
import org.apache.rocketmq.console.util.JsonUtil;
import org.apache.rocketmq.tools.admin.MQAdminExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 默认监控task
 * 监控内容：
 * 针对topic：监控消息堆积数量
 *           消费者实例数量
 *
 *  broker集群：broker集群数量
 *
 *  namesrv集群：namesrv集群数量
 *
 *
 * @author lijiangtao
 */
@Component
public class MonitorTask{
    private Logger logger = LoggerFactory.getLogger(MonitorTask.class);

    @Resource
    private MonitorService monitorService;
    @Resource
    private ConsumerService consumerService;
    @Resource
    private MQAdminExt mqAdminExt;
    @Resource
    private RMQConfigure rmqConfigure;
    @Resource
    private TroubleCluterService troubleCluterService;

    /**
     * 监控消息对接数量和consumer 实例数量
     */
    @Scheduled(cron = "0 30 * * * ?")
    public void doTask() {
        for (Map.Entry<String, ConsumerMonitorConfig> configEntry : monitorService.queryConsumerMonitorConfig().entrySet()) {
            GroupConsumeInfo consumeInfo = consumerService.queryGroup(configEntry.getKey());
            if (consumeInfo.getCount() < configEntry.getValue().getMinCount() || consumeInfo.getDiffTotal() > configEntry.getValue().getMaxDiffTotal()) {
                // notify the alert system
                logger.info("op=look consumeInfo {}", JsonUtil.obj2String(consumeInfo));
            }
        }
    }

    /**
     * 监控：
     * mq中broker 健康状态：数量
     * commitLogDiskRatio consumeQueueDiskRatio
     * remainHowManyDataToFlush
     */
    String brokerRemarkTemplate = "broker存活数量：%s,预警配置数量：%s";
    @Scheduled(cron = "0 * * * * ?")
    public void  brokerInfoMonitor(){
        Map<String, Object> resultMap = Maps.newHashMap();
        try {
            ClusterInfo clusterInfo = mqAdminExt.examineBrokerClusterInfo();
            logger.info("op=look_clusterInfo {}", JsonUtil.obj2String(clusterInfo));
            //TODO 监控标准配置
            HashMap<String, Set<String>> clusterAddrTable = clusterInfo.getClusterAddrTable();

            //broker 集群实例统计（包括主从实例数量）
            int liveBrokerNum = 0;
            HashMap<String, BrokerData> brokerAddrTable = clusterInfo.getBrokerAddrTable();
            for (String brokerAddr : brokerAddrTable.keySet()) {
                BrokerData brokerData = brokerAddrTable.get(brokerAddr);
                liveBrokerNum += brokerData.getBrokerAddrs().size();
            }

            if(liveBrokerNum < rmqConfigure.getMonitorBrokerNums()){
                String remark = String.format(brokerRemarkTemplate, liveBrokerNum, rmqConfigure.getMonitorBrokerNums());
                logger.warn(remark);
                //插入数据库，预警系统扫描预警
                troubleCluterService.insert(JsonUtil.obj2String(brokerAddrTable),remark);
            }


        } catch (Exception e) {
            logger.error(" op=look broker info error ",e);
        }
    }


}
