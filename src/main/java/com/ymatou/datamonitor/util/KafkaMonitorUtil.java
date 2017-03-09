/*
 *
 * (C) Copyright 2017 Ymatou (http://www.ymatou.com/). All rights reserved.
 *
 */

package com.ymatou.datamonitor.util;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.ymatou.datamonitor.config.BizConfig;
import com.ymatou.datamonitor.model.vo.ConsumerTopicPartition;
import com.ymatou.datamonitor.model.vo.KafkaAlertEventVo;
import com.ymatou.datamonitor.service.IntegrationService;

/**
 * @author luoshiqian 2017/3/9 12:24
 */
@Component
public class KafkaMonitorUtil {

    public static final Map<String, KafkaAlertEventVo> ALERT_EVENT_MAP = Maps.newConcurrentMap();
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaMonitorUtil.class);
    public static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE =
            Executors.newSingleThreadScheduledExecutor();

    @Autowired
    private IntegrationService integrationService;
    @Autowired
    private BizConfig bizConfig;


    @PostConstruct
    public void init() {
        SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(() -> {

            try {
                StringBuilder sb = new StringBuilder();
                boolean isSendPhone = ALERT_EVENT_MAP.values().stream()
                        .map(KafkaAlertEventVo::getEvent)
                        .flatMap(consumerGroupStatus -> consumerGroupStatus.getPartitions().stream())
                        .anyMatch(consumerTopicPartition -> consumerTopicPartition.getEnd().getLag() > 0);
                ALERT_EVENT_MAP.values().stream().forEach(kafkaAlertEventVo -> {
                    String cluster = kafkaAlertEventVo.getCluster();
                    String group = kafkaAlertEventVo.getGroup();
                    Map<String, Long> map = kafkaAlertEventVo.getEvent().getPartitions().stream()
                            .collect(Collectors.groupingBy(ConsumerTopicPartition::getTopic,
                                    Collectors.summingLong(value -> value.getEnd().getLag())));

                    sb.append("cluster:").append(cluster);
                    sb.append(";group:").append(group);

                    for (Map.Entry<String,Long> stringLongEntry :map.entrySet()) {
                        sb.append(";topic:").append(stringLongEntry.getKey()).append("|lag:")
                                .append(stringLongEntry.getValue());
                    }

                    sb.append("\n");
                });
                String title = sb.toString();
                if (StringUtils.isNotBlank(title)) {
                    title = "kafka监控出现堆积:" + title;
                    String emailContent = title + " <br/><br/><br/>" + JSON.toJSONString(ALERT_EVENT_MAP);
                    integrationService.sendHtmlEmail(bizConfig.getKafkaMonitorEmails(), title, emailContent);
                    if(isSendPhone){
                        integrationService.sendMessage(bizConfig.getKafkaMonitorPhones(), title);
                    }
                    LOGGER.info(emailContent);
                }
            } catch (Exception e) {
                LOGGER.error("exception", e);
                integrationService.sendMessage(bizConfig.getKafkaMonitorPhones(), "kafka monitor timer error ");
                integrationService.sendHtmlEmail(bizConfig.getKafkaMonitorEmails(), "kafka monitor timer error ",
                        Utils.getStackTrace(e));
            } finally {
                ALERT_EVENT_MAP.clear();
            }

        }, 0L, 30L, TimeUnit.SECONDS);
    }

    @PreDestroy
    public void destroy() {
        SCHEDULED_EXECUTOR_SERVICE.shutdownNow();
    }
}
