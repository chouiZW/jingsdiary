package com.diary.calendar.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private Integer loginType;
    private String code;
    private String account;
    private String password;
}
