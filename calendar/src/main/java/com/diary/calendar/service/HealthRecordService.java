package com.diary.calendar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.diary.calendar.dto.CalendarBaseDTO;
import com.diary.calendar.dto.HealthRecordDTO;
import com.diary.calendar.entity.HealthRecordEntity;
import com.diary.calendar.vo.HealthRecordVO;

public interface HealthRecordService extends IService<HealthRecordEntity> {
    
    HealthRecordVO getHealthRecordByDate(CalendarBaseDTO calendarBaseDTO);

    boolean addOrUpdateHealthRecord(HealthRecordDTO healthRecordDTO);

}
