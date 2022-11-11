package com.example.myapplication.utils;

import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public abstract class LogUtil {
    public static final String TAG="HMS_TAG";
    private static final HiLogLabel LABEL = new HiLogLabel(HiLog.LOG_APP, 0x00201, TAG);
    public static boolean flag=false;

    public static void debug(String msg){
        if(!flag){
            HiLog.debug(LABEL, msg);
        }
    }
    public static void error(String msg){
        if(!flag){
            HiLog.error(LABEL, msg);
        }
    }
    public static void  warn(String msg){
        if(!flag){
            HiLog.warn(LABEL, msg);

        }
    }
    public static void info(String msg){
        if(!flag){
            HiLog.info(LABEL, msg);
        }
    }

    public  static  void  fatal(String msg){
        if(!flag){
            HiLog.fatal(LABEL, msg);
        }
    }
}
