/*
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 */
package com.ymatou.datamonitor.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ymatou.datamonitor.model.StatusEnum;
import com.ymatou.datamonitor.model.vo.ExecLogVo;
import com.ymatou.datamonitor.service.ExecLogService;
import com.ymatou.datamonitor.util.WapperUtil;

/**
 * 
 * @author qianmin 2016年8月22日 下午3:20:58
 *
 */
@RestController
@RequestMapping("/execLog")
public class ExecLogController {
    
    @Autowired
    private ExecLogService execLogService;
    
    @RequestMapping(path = "/list", method = RequestMethod.POST,
            consumes="application/json",produces="application/json")
    public Object list(ExecLogVo execLogVo, Pageable pageable){
        
        execLogVo.setStatus(StatusEnum.ENABLE.name());
        Page<ExecLogVo> execLogPage = execLogService.listExecLogVo(execLogVo, pageable);

        return WapperUtil.success(execLogPage);
    }
}
