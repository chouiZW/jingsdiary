package com.diary.calendar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diary.calendar.dto.CalendarBaseDTO;
import com.diary.calendar.service.CalendarBaseService;
import com.diary.calendar.vo.Result;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    @Autowired
    private CalendarBaseService service;

    @PostMapping("/getDailyInfoList")
    public Result<?> getDailyInfoList(@RequestBody CalendarBaseDTO calendarBaseDTO){
        return Result.success(service.getCalendarListByMonth(calendarBaseDTO));
    }

    @PostMapping("/updateMarks")
    public Result<?> updateMarks(@RequestBody CalendarBaseDTO calendarBaseDTO){
        return Result.success(service.updateMarks(calendarBaseDTO));
    }
}
