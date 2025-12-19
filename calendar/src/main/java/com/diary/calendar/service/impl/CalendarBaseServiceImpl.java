package com.diary.calendar.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diary.calendar.constant.ResultCode;
import com.diary.calendar.dto.CalendarBaseDTO;
import com.diary.calendar.entity.CalendarBaseEntity;
import com.diary.calendar.exception.ServiceException;
import com.diary.calendar.mapper.CalendarBaseMapper;
import com.diary.calendar.service.CalendarBaseService;
import com.diary.calendar.util.DateUtils;
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
        entity.setTargetYear(DateUtils.getYearFromDate(date));
        entity.setTargetMonth(DateUtils.getMonthFromDate(date));
        entity.setTargetWeek(DateUtils.getWeekFromDate(date));
    }
}
