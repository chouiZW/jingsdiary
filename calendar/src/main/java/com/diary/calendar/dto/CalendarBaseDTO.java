package com.diary.calendar.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class CalendarBaseDTO {
    private Integer userId;
    private Date targetDate;
    private List<Date> targetDays;
    private String targetYear;
    private String targetMonth;
    private String targetWeek;
    private Float weight;
    private String mark;
}
