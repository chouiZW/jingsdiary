package com.diary.calendar.util;

import com.diary.calendar.entity.UserEntity;

public class UserContext {
    private static final ThreadLocal<Integer> CURRENT_USER_ID = new ThreadLocal<>();

    public static void setCurrentUserId(Integer userId) {
        CURRENT_USER_ID.set(userId);
    }

    public static Integer getCurrentUserId() {
        return CURRENT_USER_ID.get();
    }

    public static void clear() {
        CURRENT_USER_ID.remove();
    }
}