package com.diary.calendar.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diary.calendar.dto.CalendarBaseDTO;
import com.diary.calendar.dto.HealthRecordDTO;
import com.diary.calendar.service.HealthRecordService;
import com.diary.calendar.vo.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/healthRecord")
public class HealthRecordController {

    @Autowired
    private HealthRecordService service;

    @PostMapping("getHealthRecordByDate")
    public Result<?> getHealthRecordByDate(@RequestBody CalendarBaseDTO calendarBaseDTO) {
        return Result.success(service.getHealthRecordByDate(calendarBaseDTO));
    }

    @PostMapping("addOrUpdateHealthRecord")
    public Result<?> addOrUpdateHealthRecord(@RequestBody HealthRecordDTO healthRecordDTO) {
        return Result.success(service.addOrUpdateHealthRecord(healthRecordDTO));
    }
}
