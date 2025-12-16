package com.diary.calendar.mapper;

import com.diary.calendar.dto.CalendarBaseDTO;
import com.diary.calendar.vo.CalendarBaseVO;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diary.calendar.entity.CalendarBaseEntity;

import java.util.List;

@Repository
public interface CalendarBaseMapper extends BaseMapper<CalendarBaseEntity> {

    List<CalendarBaseVO> getCalendarListWithWeight(CalendarBaseDTO dto);
}
