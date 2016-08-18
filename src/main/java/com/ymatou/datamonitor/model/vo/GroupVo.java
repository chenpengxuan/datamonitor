/*
 *
 *  (C) Copyright 2016 Ymatou (http://www.ymatou.com/).
 *  All rights reserved.
 *
 */

package com.ymatou.datamonitor.model.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luoshiqian 2016/7/28 18:12
 */
public class GroupVo {

    private Long id;
    private String name;
    private List<FunctionVo> functions = new ArrayList<>();

    public GroupVo(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public GroupVo() {
    }

    public List<FunctionVo> getFunctions() {
        return functions;
    }

    public void setFunctions(List<FunctionVo> functions) {
        this.functions = functions;
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
