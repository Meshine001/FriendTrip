package com.xjtu.friendtrip.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.MediaStore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by Meshine on 16/5/26.
 */
public class CommonUtil {

    /**
     * 获取本地时间
     * @return
     */
    public static String getCurrentTimeStr(){
        SimpleDateFormat    formatter    =   new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date    curDate    =   new Date(System.currentTimeMillis());//获取当前时间
        String    str    =    formatter.format(curDate);
        return str;
    }


    private static String[] Colors = {
            "#F44336",
            "#E91E63",
            "#9C27B0",
            "#2196F3",
            "#03A9F4",
            "#00BCD4",
            "#009688",
            "#4CAF50",
            "#8BC34A",
            "#CDDC39",
            "#FFEB3B",
            "#FFC107",
            "#FF9800",
            "#FF5722",
            "#795548",
            "#9E9E9E",
            "#607D8B"};
    public static int getRandomColor() {
        Random random = new Random();
        int p = random.nextInt(Colors.length);
        return Color.parseColor(Colors[p]);
    }
}
