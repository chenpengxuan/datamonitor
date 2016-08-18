/*
 *
 *  (C) Copyright 2016 Ymatou (http://www.ymatou.com/).
 *  All rights reserved.
 *
 */

package com.ymatou.datamonitor.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface BaseService<T>{

    void save(T t);

    void deleteById(Long id);

    void delete(T t);

    T findById(Long id);

    void saveAll(Iterable<T> entities);

    Page<T> findByPage(Pageable pageable);

    List<T> findAll();

}
