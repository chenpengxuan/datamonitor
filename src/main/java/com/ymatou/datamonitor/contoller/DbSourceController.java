/*
 *
 *  (C) Copyright 2016 Ymatou (http://www.ymatou.com/).
 *  All rights reserved.
 *
 */
package com.ymatou.datamonitor.contoller;

import com.ymatou.datamonitor.model.DataSourceEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ymatou.datamonitor.service.MonitorService;
import com.ymatou.datamonitor.util.WapperUtil;

/**
 * 
 * @author qianmin 2016年8月18日 下午2:41:41
 *
 */
@RestController
@RequestMapping("/dbSource")
public class DbSourceController {
    
    private static final Logger logger = LoggerFactory.getLogger(DbSourceController.class);
    
    @Autowired
    private MonitorService monitorService;
    
    @RequestMapping(path = "/getAll")
    public Object getAll(){

        return WapperUtil.success(DataSourceEnum.values());
    }
    

}
