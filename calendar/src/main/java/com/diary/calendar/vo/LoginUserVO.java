package com.diary.calendar.vo;

import lombok.Data;

@Data
public class LoginUserVO {
    private String token;
    private String name;
    private byte[] avatar;
    private String phone;
}
