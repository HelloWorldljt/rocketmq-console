package org.apache.rocketmq.console.controller.swagger;

import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.rocketmq.console.task.TroubleMsgCollectTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;

/**
 * @author duanz
 * @date 2019-05-22
 */
@RestController("/messageCollect")
public class MessageCollectTaskController {


    @Autowired
    TroubleMsgCollectTask troubleMsgCollectTask;

    @ApiOperation(value = "collect")
    @RequestMapping(value = "/collect",method = RequestMethod.POST)
    public void collect(@RequestParam String topic, @RequestParam String startDate,@RequestParam String endDate) throws ParseException {
       Date start= DateUtils.parseDate(startDate);
       Date end = DateUtils.parseDate(endDate);
        troubleMsgCollectTask.saveMessage(topic,start,end);
    }
    @ApiOperation(value = "collect")
    @RequestMapping(value = "/taskTrigger",method = RequestMethod.POST)
    public void taskTrigger(){
        troubleMsgCollectTask.doTask();
    }
}
