/*
 *
 *  (C) Copyright 2016 Ymatou (http://www.ymatou.com/).
 *  All rights reserved.
 *
 */

package com.ymatou.datamonitor.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ymatou.common.mybatis.annotation.MyBatisDao;
import com.ymatou.datamonitor.model.pojo.User;


@MyBatisDao
public interface UserMapper {

    List<User> findByUser(@Param("user") User user);

    Page<User> findByUser(@Param("user") User user, @Param("pageable") Pageable pageable);

}
