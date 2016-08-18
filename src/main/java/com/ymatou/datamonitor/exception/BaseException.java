/*
 *
 *  (C) Copyright 2016 Ymatou (http://www.ymatou.com/).
 *  All rights reserved.
 *
 */

package com.ymatou.datamonitor.exception;

public class BaseException extends Exception {

    public BaseException(){
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause){
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

}
