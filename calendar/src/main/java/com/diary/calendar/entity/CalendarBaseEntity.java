package com.diary.calendar.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("calendar_base")
public class CalendarBaseEntity {
    @TableId
    private Integer id;
    @TableField("user_id")
    private Integer userId;
    @TableField("target_date")
    private Date targetDate;
    @TableField("target_year")
    private String targetYear;
    @TableField("target_month")
    private String targetMonth;
    @TableField("target_week")
    private String targetWeek;
    @TableField("mark")
    private String mark;
    @TableField("column1")
    private String column1;
    @TableField("column2")
    private String column2;
    @TableField("column3")
    private String column3;
    @TableField("column4")
    private String column4;
    @TableField("deleted")
    private String deleted;
    @TableField("create_by")
    private Integer createBy;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_by")
    private Integer updateBy;
    @TableField("update_time")
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public String getTargetYear() {
        return targetYear;
    }

    public void setTargetYear(String targetYear) {
        this.targetYear = targetYear;
    }

    public String getTargetMonth() {
        return targetMonth;
    }

    public void setTargetMonth(String targetMonth) {
        this.targetMonth = targetMonth;
    }

    public String getTargetWeek() {
        return targetWeek;
    }

    public void setTargetWeek(String targetWeek) {
        this.targetWeek = targetWeek;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getColumn1() {
        return column1;
    }

    public void setColumn1(String column1) {
        this.column1 = column1;
    }

    public String getColumn2() {
        return column2;
    }

    public void setColumn2(String column2) {
        this.column2 = column2;
    }

    public String getColumn3() {
        return column3;
    }

    public void setColumn3(String column3) {
        this.column3 = column3;
    }

    public String getColumn4() {
        return column4;
    }

    public void setColumn4(String column4) {
        this.column4 = column4;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
