/*
 *
 *  (C) Copyright 2016 Ymatou (http://www.ymatou.com/).
 *  All rights reserved.
 *
 */

package com.ymatou.datamonitor.exception;

import com.alibaba.fastjson.JSON;
import com.ymatou.datamonitor.util.ResponseStatusEnum;
import com.ymatou.datamonitor.util.WapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MySimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {

    private final static String HEADER_STRING = "X-Requested-With";
    private final static String AJAX_HEADER = "XMLHttpRequest";
    private final static String JSON_CONTENT_TYPE = "application/json;charset=UTF-8";
    private final static int HTTP_STATUS_OK = 200;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        boolean isAjax = AJAX_HEADER.equals(request.getHeader(HEADER_STRING));

        logger.error("exception :",ex);

        if (isAjax) {

//            if (ex instanceof BaseException) {
//                responseString(response, JSON.toJSONString(WapperUtil.error(ResponseStatusEnum.ERROR, ex.getMessage())));
//            } else if (ex instanceof BaseRunTimeException) {
//                responseString(response, JSON.toJSONString(WapperUtil.error(ResponseStatusEnum.ERROR, ex.getMessage())));
//            } else {
                responseString(response, JSON.toJSONString(WapperUtil.error(ResponseStatusEnum.ERROR, ex.getMessage())));
//            }

            return new ModelAndView();
        }

        return super.doResolveException(request, response, handler, ex);
    }

    private void responseString(HttpServletResponse response, String jsonpInfo) {

        try {
            response.getWriter().write(jsonpInfo);

            response.setContentType(JSON_CONTENT_TYPE);
            response.setStatus(HTTP_STATUS_OK);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
