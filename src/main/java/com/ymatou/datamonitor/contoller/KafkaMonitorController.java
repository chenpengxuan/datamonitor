/*
 *
 * (C) Copyright 2017 Ymatou (http://www.ymatou.com/). All rights reserved.
 *
 */

package com.ymatou.datamonitor.contoller;

import static com.ymatou.datamonitor.util.KafkaMonitorUtil.ALERT_EVENT_MAP;

import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ymatou.datamonitor.config.BizConfig;
import com.ymatou.datamonitor.model.vo.KafkaAlertEventVo;
import com.ymatou.datamonitor.service.IntegrationService;
import com.ymatou.datamonitor.util.Utils;


@RestController
@RequestMapping("/kafka")
public class KafkaMonitorController {

    private final static Logger logger = LoggerFactory.getLogger(KafkaMonitorController.class);

    private final Charset CHARSET_UTF_8 = Charset.forName("utf-8");

    @Autowired
    private IntegrationService integrationService;
    @Autowired
    private BizConfig bizConfig;

    /**
     * 接收数据格式 { "api_key" : "", "app" : "burrow", "block" : false, "events" : [{ "id" :
      "4246a66a-a067-4cec-b021-299549f7ab58", "cluster" : "local", "group" : "test2", "event" : {
     * "severity" : "ERR", "tier" : "STG", "cluster" : "local", "group" : "test2", "start" :
     * "Mar 09, 2017 11:47:26 UTC", "complete" : true, "partitions" : [{ "topic" :
     * "messagebus.testjava_kafka_hello2", "partition" : 0, "status" : "STOP", "start" : { "offset"
     * : 10452, "timestamp" : 1489030692447, "lag" : 1113, "max_offset" : 10452 }, "end" : {
     * "offset" : 10469, "timestamp" : 1489030857684, "lag" : 2295, "max_offset" : 10469 } } ] } }]
     * }
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/alert", method = RequestMethod.POST)
    public String alert(HttpServletRequest request) throws Exception {
        String data = getDataFromRequest(request);
        logger.info("alert={}", data);
        try {
            TypeReference<List<KafkaAlertEventVo>> typeReference = new TypeReference<List<KafkaAlertEventVo>>() {};
            List<KafkaAlertEventVo> kafkaAlertEventVoList =
                    JSON.parseObject(JSON.parseObject(data).getJSONArray("events").toJSONString(), typeReference);

            if (!CollectionUtils.isEmpty(kafkaAlertEventVoList)) {
                kafkaAlertEventVoList.stream()
                        .forEach(
                                kafkaAlertEventVo -> ALERT_EVENT_MAP.put(kafkaAlertEventVo.getId(), kafkaAlertEventVo));
            }
        } catch (Exception e) {
            logger.error("exception", e);
            integrationService.sendMessage(bizConfig.getKafkaMonitorPhones(), "kafka monitor alert error " + data);
            integrationService.sendHtmlEmail(bizConfig.getKafkaMonitorEmails(), "kafka monitor alert error " + data,
                    data + "<br/>" + Utils.getStackTrace(e));
        }
        return "success";
    }


    /**
     * 接收数据格式 { "api_key" : "", "app" : "burrow", "block" : false, "ids" :
     * [{"id":"ed849a1c-a394-4560-b154-0e63743eaa54", "cluster" : "local", "group" :
     * "messagebus.dispatch.k.product_mongoservice_crud_c1"}] }
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String delete(HttpServletRequest request) throws Exception {
        String data = getDataFromRequest(request);
        logger.info("delete={}", data);

        try {
            TypeReference<List<KafkaAlertEventVo>> typeReference = new TypeReference<List<KafkaAlertEventVo>>() {};
            List<KafkaAlertEventVo> kafkaAlertEventVoList =
                    JSON.parseObject(JSON.parseObject(data).getJSONArray("ids").toJSONString(), typeReference);

            if (!CollectionUtils.isEmpty(kafkaAlertEventVoList)) {
                kafkaAlertEventVoList.forEach(o -> {
                    String cluster = o.getCluster();
                    String group = o.getGroup();
                    logger.info("cluster :{},group:{} recovered", cluster, group);
                    String title = "kafka 监控 cluster:" + cluster + "; group:" + group + " recovered";

                    integrationService.sendMessage(bizConfig.getKafkaMonitorPhones(), title);
                    integrationService.sendHtmlEmail(bizConfig.getKafkaMonitorEmails(), title, title);
                });
            }
        } catch (Exception e) {
            logger.error("data:{},exception", data, e);
            integrationService.sendMessage(bizConfig.getKafkaMonitorPhones(), "kafka monitor delete error " + data);
            integrationService.sendHtmlEmail(bizConfig.getKafkaMonitorEmails(), "kafka monitor delete error " + data,
                    data + "<br/>" + Utils.getStackTrace(e));
        }
        return "success";
    }


    private String getDataFromRequest(HttpServletRequest request) throws Exception {
        return StreamUtils.copyToString(request.getInputStream(), CHARSET_UTF_8);
    }
}
