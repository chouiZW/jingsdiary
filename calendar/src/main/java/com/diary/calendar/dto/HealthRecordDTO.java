package com.diary.calendar.dto;

import java.util.Date;

import lombok.Data;

@Data
public class HealthRecordDTO {

    private Date targetDate;
    private Float weight;
    private String cookbook;
    private Integer calorie;
    private String exerciseDetail;
}
