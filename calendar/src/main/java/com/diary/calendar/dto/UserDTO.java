package com.diary.calendar.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String account;
    private String password;
    private String openid;
    private String name;
    private byte[] avatar;
    private String phone;
    private String column1;
    private String column2;
    private String column3;
    private String column4;
}
