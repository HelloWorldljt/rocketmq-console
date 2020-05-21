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

import com.xiangshang.elasticjob.lite.starter.job.AbstractSimpleJob;
import io.elasticjob.lite.api.ShardingContext;
import org.apache.rocketmq.console.model.ConsumerMonitorConfig;
import org.apache.rocketmq.console.model.GroupConsumeInfo;
import org.apache.rocketmq.console.service.ConsumerService;
import org.apache.rocketmq.console.service.MonitorService;
import org.apache.rocketmq.console.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 默认监控task
 * 暂时不用
 * @author duanz
 */
@Component
public class MonitorTask extends AbstractSimpleJob {
    private Logger logger = LoggerFactory.getLogger(MonitorTask.class);

    @Resource
    private MonitorService monitorService;

    @Resource
    private ConsumerService consumerService;

    @Override
    public void doTask(ShardingContext content) {
        for (Map.Entry<String, ConsumerMonitorConfig> configEntry : monitorService.queryConsumerMonitorConfig().entrySet()) {
            GroupConsumeInfo consumeInfo = consumerService.queryGroup(configEntry.getKey());
            if (consumeInfo.getCount() < configEntry.getValue().getMinCount() || consumeInfo.getDiffTotal() > configEntry.getValue().getMaxDiffTotal()) {
                // notify the alert system
                logger.info("op=look consumeInfo {}", JsonUtil.obj2String(consumeInfo));
            }
        }
    }
}
