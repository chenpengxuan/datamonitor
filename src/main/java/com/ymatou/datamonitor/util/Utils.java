/*
 *
 *  (C) Copyright 2017 Ymatou (http://www.ymatou.com/).
 *  All rights reserved.
 *
 */

package com.ymatou.datamonitor.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author luoshiqian 2017/3/9 13:08
 */
public class Utils {
    /**
     * 返回字符串形式的异常完整堆栈
     * @param throwable
     * @return 空字符串<tt>""</tt>，如果输入是<tt>null</tt>
     */
    public static String getStackTrace(Throwable throwable) {
        if (throwable == null) {
            return "";
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}
