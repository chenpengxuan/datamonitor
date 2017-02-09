/*
 *
 *  (C) Copyright 2016 Ymatou (http://www.ymatou.com/).
 *  All rights reserved.
 *
 */

package com.ymatou.datamonitor.contoller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ymatou.datamonitor.model.pojo.User;
import com.ymatou.datamonitor.service.UserService;
import com.ymatou.datamonitor.util.CipherUtil;
import com.ymatou.datamonitor.util.SessionUtil;
import com.ymatou.datamonitor.util.WapperUtil;


@RestController
@RequestMapping("")
public class LoginController {

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;


    @RequestMapping("/auth")
    public Object auth(String username, String password){

        String errorMessage = "未知错误！";


        if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)){

            // 得到Subject及创建用户名/密码身份验证Token(即用户身份/凭证)
            Subject currentUser = SecurityUtils.getSubject();

            // 如果用户已经登录
            if (currentUser.isAuthenticated()) {
                return WapperUtil.success("该用户已经登录！");
            }
            // 登录Token验证
            String md5Password = CipherUtil.encryptMD5(password);
            UsernamePasswordToken token = new UsernamePasswordToken(username, password, true);

            try {
                currentUser.login(token);

                // 判断用户是否已经认证
                if(currentUser.isAuthenticated()) {

                    // 获取已认证用户User
                    User user = userService.getUser(username);
                    
                    if(user == null){
                        user = new User();
                        user.setUsername(username);
                        user.setPassword(md5Password);
                        userService.save(user);
                    }

                    // 增加用户的相关数据进入Session
                    addUserInfoToSession(user);
                    return WapperUtil.success("登录成功");
                }
            } catch (AuthenticationException e) { // 登录失败
                errorMessage = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
                logger.info("登录失败",e);
            } catch (Exception e) {
                errorMessage = "未知错误！";
                logger.info("登录失败",e);
            }
        }else{
            errorMessage = "用户名或密码为空！";
        }

        return WapperUtil.error(errorMessage);
    }


    /**
     * 增加用户的信息到session中
     */
    private void addUserInfoToSession(User user){

        // 设置用户的信息到session中
        SessionUtil.put(SessionUtil.SESSION_KEY_USER_ID, user.getId());
        SessionUtil.put(SessionUtil.SESSION_KEY_USER, user);

    }

    @RequestMapping("/logout")
    public Object logout(){

        Subject subject = SecurityUtils.getSubject();


        if(subject.isAuthenticated()){
            subject.logout();

            WapperUtil.success();
        }

        return WapperUtil.error("您还未登录！");
    }
    
    @RequestMapping("/version")
    public String version() {
        return "2017-02-08";
    }
    
    @RequestMapping("/warmup")
    public String status() {
        return "ok";
    }
}
