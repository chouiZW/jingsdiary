package com.diary.calendar.service.impl;

import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diary.calendar.dto.CalendarBaseDTO;
import com.diary.calendar.dto.HealthRecordDTO;
import com.diary.calendar.entity.HealthRecordEntity;
import com.diary.calendar.entity.UserEntity;
import com.diary.calendar.mapper.HealthRecordMapper;
import com.diary.calendar.service.HealthRecordService;
import com.diary.calendar.util.EntityToVoConverter;
import com.diary.calendar.util.UserContext;
import com.diary.calendar.vo.HealthRecordVO;

@Service
public class HealthRecordServiceImpl extends ServiceImpl<HealthRecordMapper, HealthRecordEntity>
        implements HealthRecordService {

    @Autowired
    private HealthRecordMapper healthRecordMapper;

    @Override
    public HealthRecordVO getHealthRecordByDate(CalendarBaseDTO calendarBaseDTO) {
        Integer userId = UserContext.getCurrentUserId();
        LambdaQueryWrapper<HealthRecordEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HealthRecordEntity::getUserId, calendarBaseDTO.getUserId())
                .eq(HealthRecordEntity::getTargetDate, calendarBaseDTO.getTargetDate());
        HealthRecordEntity entity = healthRecordMapper.selectOne(wrapper);
        // 获取当天之前的最新一条健康记录，用于记录体重差值
        HealthRecordEntity previousRecord = healthRecordMapper.selectOne(
                new LambdaQueryWrapper<HealthRecordEntity>()
                        .eq(HealthRecordEntity::getUserId, userId)
                        .lt(HealthRecordEntity::getTargetDate, calendarBaseDTO.getTargetDate())
                        .orderByDesc(HealthRecordEntity::getTargetDate)
                        .last("LIMIT 1"));
        if (entity == null) {
            return null;
        } else {
            HealthRecordVO resultvo = EntityToVoConverter.convertSingle(entity, HealthRecordVO.class);
            if (Objects.nonNull(previousRecord) && Objects.nonNull(previousRecord.getWeight())
                    && Objects.nonNull(entity.getWeight())) {
                Float weightDiff = entity.getWeight() - previousRecord.getWeight();
                resultvo.setWeightDiff(weightDiff);
            }
            return resultvo;
        }
    }

    @Override
    public boolean addOrUpdateHealthRecord(HealthRecordDTO healthRecordDTO) {
        Integer userId = UserContext.getCurrentUserId();
        HealthRecordEntity healthRecordEntity = EntityToVoConverter.convertSingle(healthRecordDTO,
                HealthRecordEntity.class);
                healthRecordEntity.setUserId(userId);
        LambdaQueryWrapper<HealthRecordEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HealthRecordEntity::getUserId, userId)
        .eq(HealthRecordEntity::getTargetDate, healthRecordDTO.getTargetDate());
        HealthRecordEntity existingRecord = healthRecordMapper.selectOne(wrapper);
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
