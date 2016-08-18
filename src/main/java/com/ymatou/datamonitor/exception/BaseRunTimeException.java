/*
 *
 *  (C) Copyright 2016 Ymatou (http://www.ymatou.com/).
 *  All rights reserved.
 *
 */

package com.ymatou.datamonitor.exception;

public class BaseRunTimeException extends RuntimeException {

    public BaseRunTimeException(){
        super();
    }

    public BaseRunTimeException(String message) {
        super(message);
    }

    public BaseRunTimeException(String message, Throwable cause){
        super(message, cause);
    }

    public BaseRunTimeException(Throwable cause) {
        super(cause);
    }

}
