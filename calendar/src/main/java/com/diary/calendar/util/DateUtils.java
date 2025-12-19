package com.diary.calendar.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {
    // 小程序时间戳（毫秒）转 LocalDate（默认北京时间）
    public static LocalDate timestampToLocalDate(Long timestamp) {
        if (timestamp == null) return null;
        // 时间戳 → Instant → LocalDate（按北京时间时区转换）
        return Instant.ofEpochMilli(timestamp)
                .atZone(ZoneId.of("Asia/Shanghai"))
                .toLocalDate();
    }

    // LocalDate 转 小程序时间戳（毫秒，默认取当日 00:00:00 的时间戳）
    public static Long localDateToTimestamp(LocalDate localDate) {
        if (localDate == null) return null;
        // LocalDate → Instant → 毫秒时间戳
        return localDate.atStartOfDay(ZoneId.of("Asia/Shanghai"))
                .toInstant()
                .toEpochMilli();
    }
    //根据传入的Date获取当前的年(字符串类型)
    public static String getYearFromDate(Date date) {
        Instant instant = date.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.of("Asia/Shanghai")).toLocalDate();
        return String.valueOf(localDate.getYear());
    }
    //根据传入的Date获取当前的月(字符串)
    public static String getMonthFromDate(Date date) {
        Instant instant = date.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.of("Asia/Shanghai")).toLocalDate();
        int monthValue = localDate.getMonthValue();
        return String.valueOf(monthValue);
    }
    //根据传入的Date类型获取当前是今年的第几周(字符串)
    public static String getWeekFromDate(Date date) {
        Instant instant = date.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.of("Asia/Shanghai")).toLocalDate();
        int weekOfYear = localDate.get(java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        return String.valueOf(weekOfYear);
    }
}