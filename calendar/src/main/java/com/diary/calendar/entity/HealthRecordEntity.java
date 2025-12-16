package com.diary.calendar.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("calendar_health_record")
public class HealthRecordEntity {
    @TableId
    private Integer id;
    @TableField("user_id")
    private Integer userId;
    @TableField("target_date")
    private Date targetDate;
    @TableField("weight")
    private Float weight;
    @TableField("weight_diff")
    private Float weightDiff;
    @TableField("breakfast")
    private String breakfast;
    @TableField("lunch")
    private String lunch; 
    @TableField("dinner")
    private String dinner;
    @TableField("snack")
    private String snack;
    @TableField("calorie")
    private Integer calorie;
    @TableField("exercise_detail")
    private String exerciseDetail;
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

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getWeightDiff() {
        return weightDiff;
    }

    public void setWeightDiff(Float weightDiff) {
        this.weightDiff = weightDiff;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getLunch() {
        return lunch;
    }

    public void setLunch(String lunch) {
        this.lunch = lunch;
    }

    public String getDinner() {
        return dinner;
    }

    public void setDinner(String dinner) {
        this.dinner = dinner;
    }

    public String getSnack() {
        return snack;
    }

    public void setSnack(String snack) {
        this.snack = snack;
    }

    public Integer getCalorie() {
        return calorie;
    }

    public void setCalorie(Integer calorie) {
        this.calorie = calorie;
    }

    public String getExerciseDetail() {
        return exerciseDetail;
    }

    public void setExerciseDetail(String exerciseDetail) {
        this.exerciseDetail = exerciseDetail;
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
