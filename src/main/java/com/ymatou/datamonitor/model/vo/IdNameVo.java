/*
 *
 *  (C) Copyright 2016 Ymatou (http://www.ymatou.com/).
 *  All rights reserved.
 *
 */

package com.ymatou.datamonitor.model.vo;

/**
 * @author luoshiqian 2016/7/28 18:12
 */
public class IdNameVo {

    private Long id;
    private String name;

    public IdNameVo(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public IdNameVo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
