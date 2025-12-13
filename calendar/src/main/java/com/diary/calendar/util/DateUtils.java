package com.diary.calendar.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

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
}