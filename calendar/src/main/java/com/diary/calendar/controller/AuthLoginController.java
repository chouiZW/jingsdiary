package com.diary.calendar.controller;

import com.diary.calendar.constant.BusinessConstant;
import com.diary.calendar.constant.ResultCode;
import com.diary.calendar.dto.LoginDTO;
import com.diary.calendar.service.AuthService;
import com.diary.calendar.vo.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("auth")
public class AuthLoginController {

    @Autowired
    private AuthService authService;

    @PostMapping("login")
    public Result<?> login(@RequestBody LoginDTO loginDTO){
        if(Objects.isNull(loginDTO) || Objects.isNull(loginDTO.getLoginType())){
            return Result.fail(ResultCode.BAD_REQUEST, "登录类型错误");
        }

        if(BusinessConstant.LOGIN_TYPE_WECHAT.equals(loginDTO.getLoginType())) {
            return Result.success(authService.loginByWechat(loginDTO));
        } else if (BusinessConstant.LOGIN_TYPE_WEB.equals(loginDTO.getLoginType())){
            return Result.success(authService.loginByWeb(loginDTO));
        } else {
            return Result.fail(ResultCode.BAD_REQUEST, "登录类型错误");
        }
    }

    @PostMapping("register")
    public Result<?> register(@RequestBody LoginDTO loginDTO){
        if(Objects.isNull(loginDTO) || Objects.isNull(loginDTO.getLoginType())){
            return Result.fail(ResultCode.BAD_REQUEST, "注册类型错误");
        }

        if(BusinessConstant.LOGIN_TYPE_WEB.equals(loginDTO.getLoginType())){
            return Result.success(authService.registerByWeb(loginDTO));
        } else {
            return Result.fail(ResultCode.BAD_REQUEST, "注册类型错误");
        }
    }

    @PostMapping("/logout")
    public Result<?> logout(HttpServletRequest request){
        if(authService.logout(request)){
            return Result.success(null);
        } else {
            return Result.fail(ResultCode.SYSTEM_ERROR, "登出失败");
        }
    }
}
