package com.diary.calendar.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.diary.calendar.dto.CalendarBaseDTO;
import com.diary.calendar.entity.CalendarBaseEntity;
import com.diary.calendar.vo.CalendarBaseVO;

public interface CalendarBaseService extends IService<CalendarBaseEntity> {

    List<CalendarBaseVO> getCalendarListByMonth(CalendarBaseDTO calendarBaseDTO);

    boolean updateMarks(CalendarBaseDTO calendarBaseDTO);

}
