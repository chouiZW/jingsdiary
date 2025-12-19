package com.diary.calendar.service.impl;

import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diary.calendar.dto.CalendarBaseDTO;
import com.diary.calendar.dto.HealthRecordDTO;
import com.diary.calendar.entity.CalendarBaseEntity;
import com.diary.calendar.entity.HealthRecordEntity;
import com.diary.calendar.mapper.CalendarBaseMapper;
import com.diary.calendar.mapper.HealthRecordMapper;
import com.diary.calendar.service.HealthRecordService;
import com.diary.calendar.util.DateUtils;
import com.diary.calendar.util.EntityToVoConverter;
import com.diary.calendar.util.UserContext;
import com.diary.calendar.vo.HealthRecordVO;

@Service
public class HealthRecordServiceImpl extends ServiceImpl<HealthRecordMapper, HealthRecordEntity>
        implements HealthRecordService {

    @Autowired
    private HealthRecordMapper healthRecordMapper;

    @Autowired
    private CalendarBaseMapper calendarBaseMapper;

    @Override
    public HealthRecordVO getHealthRecordByDate(CalendarBaseDTO calendarBaseDTO) {
        Integer userId = UserContext.getCurrentUserId();
        LambdaQueryWrapper<HealthRecordEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HealthRecordEntity::getUserId, userId)
                .eq(HealthRecordEntity::getTargetDate, calendarBaseDTO.getTargetDate());
        HealthRecordEntity entity = healthRecordMapper.selectOne(wrapper);
        if (entity == null) {
            return null;
        } else {
            HealthRecordVO resultvo = EntityToVoConverter.convertSingle(entity, HealthRecordVO.class);
            return resultvo;
        }
    }

    @Transactional
    @Override
    public boolean addOrUpdateHealthRecord(HealthRecordDTO healthRecordDTO) {
        Integer userId = UserContext.getCurrentUserId();
        HealthRecordEntity healthRecordEntity = EntityToVoConverter.convertSingle(healthRecordDTO,
                HealthRecordEntity.class);
        healthRecordEntity.setUserId(userId);
        // 查询calendarBase表中是否存在该日期的记录，如果没有则新增，有则不做处理
        CalendarBaseEntity calendarBaseEntity = calendarBaseMapper.selectOne(
                new LambdaQueryWrapper<CalendarBaseEntity>()
                        .eq(CalendarBaseEntity::getUserId, userId)
                        .eq(CalendarBaseEntity::getTargetDate, healthRecordDTO.getTargetDate()));
        if (Objects.isNull(calendarBaseEntity)) {
            CalendarBaseEntity newCalendarBase = new CalendarBaseEntity();
            Date targetDate = healthRecordDTO.getTargetDate();
            newCalendarBase.setUserId(userId);
            newCalendarBase.setTargetDate(targetDate);
            newCalendarBase.setTargetYear(DateUtils.getYearFromDate(targetDate));
            newCalendarBase.setTargetMonth(DateUtils.getMonthFromDate(targetDate));
            newCalendarBase.setTargetWeek(DateUtils.getWeekFromDate(targetDate));
            newCalendarBase.setCreateBy(userId);
            newCalendarBase.setCreateTime(new Date());
            calendarBaseMapper.insert(newCalendarBase);
        }
        LambdaQueryWrapper<HealthRecordEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HealthRecordEntity::getUserId, userId)
                .eq(HealthRecordEntity::getTargetDate, healthRecordDTO.getTargetDate());
        HealthRecordEntity existingRecord = healthRecordMapper.selectOne(wrapper);

        HealthRecordEntity preRecord = healthRecordMapper.selectOne(
                new LambdaQueryWrapper<HealthRecordEntity>()
                        .eq(HealthRecordEntity::getUserId, userId)
                        .lt(HealthRecordEntity::getTargetDate, healthRecordDTO.getTargetDate())
                        .orderByDesc(HealthRecordEntity::getTargetDate)
                        .last("LIMIT 1"));

        HealthRecordEntity nextRecord = healthRecordMapper.selectOne(
                new LambdaQueryWrapper<HealthRecordEntity>()
                        .eq(HealthRecordEntity::getUserId, userId)
                        .gt(HealthRecordEntity::getTargetDate, healthRecordDTO.getTargetDate())
                        .orderByAsc(HealthRecordEntity::getTargetDate)
                        .last("LIMIT 1"));

        // 计算体重差值并更新前后记录
        if (Objects.nonNull(preRecord) && Objects.nonNull(healthRecordEntity.getWeight())
                && Objects.nonNull(preRecord.getWeight())) {
            Float weightDiff = healthRecordEntity.getWeight() - preRecord.getWeight();
            healthRecordEntity.setWeightDiff(weightDiff);
        } else {
            healthRecordEntity.setWeightDiff(null);
        }

        if (Objects.nonNull(nextRecord) && Objects.nonNull(healthRecordEntity.getWeight())
                && Objects.nonNull(nextRecord.getWeight())) {
            Float nextWeightDiff = nextRecord.getWeight() - healthRecordEntity.getWeight();
            nextRecord.setWeightDiff(nextWeightDiff);
            healthRecordMapper.updateById(nextRecord);
        }

        if (existingRecord != null) {
            // 更新操作
            healthRecordEntity.setId(existingRecord.getId());
            int updateResult = healthRecordMapper.updateById(healthRecordEntity);
            return updateResult > 0;
        } else {
            healthRecordEntity.setCreateBy(userId);
            healthRecordEntity.setCreateTime(new Date());
            // 新增操作
            int insertResult = healthRecordMapper.insert(healthRecordEntity);
            return insertResult > 0;
        }
    }

}
