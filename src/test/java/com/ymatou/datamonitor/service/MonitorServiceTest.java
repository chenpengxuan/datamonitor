/*
 *
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 *
 */

package com.ymatou.datamonitor.service;

import com.ymatou.datamonitor.BaseTest;
import com.ymatou.datamonitor.model.pojo.Monitor;
import com.ymatou.datamonitor.model.vo.MonitorVo;
import com.ymatou.datamonitor.util.Converter;
import com.ymatou.datamonitor.util.JpaEntityUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author luoshiqian 2016/8/18 19:09
 */
public class MonitorServiceTest extends BaseTest{

    @Autowired
    MonitorService monitorService;
    @Autowired
    JpaEntityUtil jpaEntityUtil;

    @Test
    public void testRunNow(){

        Long id = 1L;
        Monitor monitor = monitorService.findById(id);
        MonitorVo monitorVo = Converter.convert(monitor,MonitorVo.class);

        jpaEntityUtil.supportJpaEntity(o -> {
            monitorService.runNow(monitorVo);
            return null;
        });


    }

}
