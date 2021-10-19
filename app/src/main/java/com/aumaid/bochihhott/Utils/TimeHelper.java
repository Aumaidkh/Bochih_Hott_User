/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * BochihHott and BochihHott Partner are properties of Murtaza Khursheed.
 */

package com.aumaid.bochihhott.Utils;

public class TimeHelper {

    public static String getTimeStamp(){
        long millis = System.currentTimeMillis();
        java.util.Date date=new java.util.Date(millis);
        return date.toString();
    }
}
