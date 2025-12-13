package com.diary.calendar.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    // 插入时填充
    @Override
    public void insertFill(MetaObject metaObject) {
        // 填充 createTime（字段名要和实体类一致）
        strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        // 填充 updateTime
        strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }

    // 更新时填充
    @Override
    public void updateFill(MetaObject metaObject) {
        // 只填充 updateTime
        strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }
}