package edu.ua.cs.cs495.caladrius.android;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

// This file just use for testing code

public class TestFile {

    public static void main(String[] args) {
//        System.out.println("Hello, this is Java");
//        System.out.println(getMonthForInt(10));
//        String date = getBackupFolderName();
//        System.out.println(date.substring(0, date.length()-5) + "th" +
//                date.substring(date.length()-5, date.length()));

        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();
        int millis = now.get(ChronoField.MILLI_OF_SECOND); // Note: no direct getter available.

        System.out.printf("%s %02d %02d ", getMonthForInt(month), day, year);
    }

    static String getMonthForInt(int m) {
        List<String> monthStr = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        return monthStr.get(m);
    }

    public static String getBackupFolderName() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
        return sdf.format(date);
    }
}
