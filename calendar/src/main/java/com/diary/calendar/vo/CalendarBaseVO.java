package com.diary.calendar.vo;

import java.util.Date;

import lombok.Data;

@Data
public class CalendarBaseVO {
    private Date targetDate;
    private String targetYear;
    private String targetMonth;
    private String targetWeek;
    private Float weight;
    private Float weightDiff;
    private String breakfast;
    private String lunch;
    private String dinner;
    private String snack;
    private Integer calorie;
    private String mark;
    private String exerciseDetail;
}
