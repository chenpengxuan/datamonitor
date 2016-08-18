/*
 *
 *  (C) Copyright 2016 Ymatou (http://www.ymatou.com/).
 *  All rights reserved.
 *
 */

package com.ymatou.datamonitor.contoller;

import com.ymatou.datamonitor.model.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymatou.datamonitor.util.CurrentUserUtil;
import com.ymatou.datamonitor.util.WapperUtil;


@Controller
@RequestMapping("")
public class IndexController {

    private final static Logger logger = LoggerFactory.getLogger(IndexController.class);
//
//    @Autowired
//    private UserService userService;

    @RequestMapping("/")
    public String index(){

        return "forward:/login.html";
    }

    @RequestMapping("/getCurrentUser")
    @ResponseBody
    public Object getCurrentUser(){
        User user = CurrentUserUtil.getCurrentUser();
        user.setPassword("");
        return WapperUtil.success(CurrentUserUtil.getCurrentUser());
    }

}
