/*
 *
 *  (C) Copyright 2016 Ymatou (http://www.ymatou.com/).
 *  All rights reserved.
 *
 */

package com.ymatou.datamonitor.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ymatou.datamonitor.model.pojo.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

    User findByUsernameAndPassword(String username,String password);
    
    User findByUsername(String name);
}