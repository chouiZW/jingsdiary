package com.diary.calendar.util;

import com.diary.calendar.entity.UserEntity;

public class UserContext {
    private static final ThreadLocal<UserEntity> CURRENT_USER_ID = new ThreadLocal<>();

    public static void setCurrentUser(UserEntity user) {
        CURRENT_USER_ID.set(user);
    }

    public static UserEntity getCurrentUser() {
        return CURRENT_USER_ID.get();
    }

    public static void clear() {
        CURRENT_USER_ID.remove();
    }
}