package com.diary.calendar.vo;

import lombok.Data;

@Data
public class HealthRecordVO {

    private Integer calendarId;
    private Float weight;
    private Float weightDiff;
    private String cookbook;
    private Integer calorie;
    private String exerciseDetail;
}
