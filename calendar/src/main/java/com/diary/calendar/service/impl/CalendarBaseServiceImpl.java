package com.diary.calendar.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diary.calendar.constant.ResultCode;
import com.diary.calendar.dto.CalendarBaseDTO;
import com.diary.calendar.entity.CalendarBaseEntity;
import com.diary.calendar.entity.UserEntity;
import com.diary.calendar.exception.ServiceException;
import com.diary.calendar.mapper.CalendarBaseMapper;
import com.diary.calendar.service.CalendarBaseService;
import com.diary.calendar.util.EntityToVoConverter;
import com.diary.calendar.util.UserContext;
import com.diary.calendar.vo.CalendarBaseVO;

@Service
public class CalendarBaseServiceImpl extends ServiceImpl<CalendarBaseMapper, CalendarBaseEntity> implements CalendarBaseService {

    @Autowired
    private CalendarBaseMapper dailyBasisMapper;

    @Override
    public List<CalendarBaseVO> getCalendarListByMonth(CalendarBaseDTO calendarBaseDTO) {
        Integer userid = UserContext.getCurrentUserId();
        if(Objects.isNull(calendarBaseDTO) || Objects.isNull(userid) ||
           Objects.isNull(calendarBaseDTO.getTargetYear()) || Objects.isNull(calendarBaseDTO.getTargetMonth())) {
            throw new ServiceException(ResultCode.BAD_REQUEST, "参数错误");
        }
        calendarBaseDTO.setUserId(userid);
        return dailyBasisMapper.getCalendarListWithWeight(calendarBaseDTO);
    }

    @Override
    public boolean updateMarks(CalendarBaseDTO calendarBaseDTO) {

        Integer userId = UserContext.getCurrentUserId();
        if(Objects.isNull(calendarBaseDTO) || Objects.isNull(userId) ||
           Objects.isNull(calendarBaseDTO.getTargetDays()) || calendarBaseDTO.getTargetDays().isEmpty() ||
           Objects.isNull(calendarBaseDTO.getMark()) ) {
            throw new ServiceException(ResultCode.BAD_REQUEST, "参数错误");
        }

        LambdaQueryWrapper<CalendarBaseEntity> wrapper;
        for (Date day: calendarBaseDTO.getTargetDays()) {
            CalendarBaseEntity dailyBase = new CalendarBaseEntity();
            dailyBase.setTargetDate(day);
            dailyBase.setUserId(userId);
            dailyBase.setMark(calendarBaseDTO.getMark());
            dailyBase.setUpdateTime(new Date());
            populateDateInfo(dailyBase);
            wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(CalendarBaseEntity::getTargetDate, day)
                    .eq(CalendarBaseEntity::getUserId, userId);
            int res = dailyBasisMapper.update(dailyBase, wrapper);
            if(res == 0){
                dailyBase.setCreateTime(new Date());
                dailyBase.setUpdateTime(new Date());
                res = dailyBasisMapper.insert(dailyBase);
            }
            if(res == 0) {
                throw new RuntimeException("update error");
            }
        }
        return true;
    }

    // 根据日期获取当天所在的年份，月份，周的信息
    private void populateDateInfo(CalendarBaseEntity entity) {
        Date date = entity.getTargetDate();
        String yearStr = String.format("%tY", date);
        String monthStr = String.format("%tY-%tm", date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        int weekOfMonth = ((dayOfMonth - 1) / 7) + 1;
        String weekStr = String.format("%tY-W%d", date, weekOfMonth);
        entity.setTargetYear(yearStr);
        entity.setTargetMonth(monthStr);
        entity.setTargetWeek(weekStr);
    }
}
