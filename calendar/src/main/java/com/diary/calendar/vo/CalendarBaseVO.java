package com.diary.calendar.vo;

import java.util.Date;

import lombok.Data;

@Data
public class CalendarBaseVO {
    private Integer calendarId;
    private Date targetDate;
    private String targetYear;
    private String targetMonth;
    private String targetWeek;
    private Float weight;
    private String mark;
}
