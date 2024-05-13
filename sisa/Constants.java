package com.example.sisa;

import android.os.Build;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Constants {

    public static String getDate() {
        String date = null;

        DateTimeFormatter dtf = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        }
        LocalDateTime now = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            now = LocalDateTime.now();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date = dtf.format(now);
        }

        return date;
    }
}
