package com.diary.calendar.service;

import com.diary.calendar.dto.LoginDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    String loginByWechat(LoginDTO loginDTO);
    String loginByWeb(LoginDTO loginDTO);
    boolean registerByWeb(LoginDTO loginDTO);
    boolean logout(HttpServletRequest request);
}
