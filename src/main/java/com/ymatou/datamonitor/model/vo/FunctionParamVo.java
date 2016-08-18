/*
 * (C) Copyright 2016 Ymatou (http://www.ymatou.com/). All rights reserved.
 */
package com.ymatou.datamonitor.model.vo;

/**
 * 
 * @author qianmin 2016年8月2日 上午11:50:08
 *
 */
public class FunctionParamVo {
    
    private Long id;
    private Long functionId;
    private String name;
    private String paramName;
    private String type;
    private String format;
    private boolean isArray;
    private String description;
    private String defaultValue;
    private Object value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFunctionId() {
        return functionId;
    }
    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getFormat() {
        return format;
    }
    public void setFormat(String format) {
        this.format = format;
    }
    public boolean isArray() {
        return isArray;
    }
    public void setArray(boolean isArray) {
        this.isArray = isArray;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDefaultValue() {
        return defaultValue;
    }
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
    public Object getValue() {
        if(value == null){
            value = defaultValue;
        }
        return value;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
