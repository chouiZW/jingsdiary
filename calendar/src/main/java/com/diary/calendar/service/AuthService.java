package com.diary.calendar.service;

import com.diary.calendar.dto.LoginDTO;
import com.diary.calendar.vo.LoginUserVO;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    LoginUserVO loginByWechat(LoginDTO loginDTO);
    LoginUserVO loginByWeb(LoginDTO loginDTO);
    boolean registerByWeb(LoginDTO loginDTO);
    boolean logout(HttpServletRequest request);
}
