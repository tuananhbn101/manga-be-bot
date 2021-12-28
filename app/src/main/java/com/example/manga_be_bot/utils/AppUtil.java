package com.example.manga_be_bot.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUtil {
    public static String millisecondToDate(long time, String format) {
        Date now = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(now);
    }
}
