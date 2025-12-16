package com.diary.calendar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.diary.calendar.constant.ResultCode;
import com.diary.calendar.dto.LoginDTO;
import com.diary.calendar.entity.UserEntity;
import com.diary.calendar.exception.ServiceException;
import com.diary.calendar.service.AuthService;
import com.diary.calendar.service.UserService;
import com.diary.calendar.util.EntityToVoConverter;
import com.diary.calendar.util.WechatMiniLoginUtil;
import com.diary.calendar.vo.LoginUserVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthServiceImpl implements AuthService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private WechatMiniLoginUtil wechatMiniLoginUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    @Override
    public LoginUserVO loginByWechat(LoginDTO loginDTO) {

        String code = loginDTO.getCode();
        // 6. 返回Token和用户信息给前端
        try{
            // 1. 校验code
            if (code == null || code.isEmpty()) {
                throw new ServiceException(ResultCode.BAD_REQUEST, "Code cannot be null or empty");
            }

            String openid = wechatMiniLoginUtil.getOpenidByCode(code);
            // session_key 在此示例中未使用，但实际应妥善保管，可用于解密手机号等敏感信息。
            // String sessionKey = (String) wechatResponse.getBody().get("session_key");

            // 4. 业务逻辑：查找或创建用户
            UserEntity user = userService.findOrCreateUserByOpenid(openid);

            // 5. 生成Token并存储到Redis
            String token = tokenService.generateAndStoreToken(user.getId());
            // ... (这里应将 token 与 user.getId() 关联并存储，例如存入Redis，并设置过期时间)
            LoginUserVO vo = EntityToVoConverter.convertSingle(user, LoginUserVO.class);
            vo.setToken(token);
            // 6. 返回token
            return vo;
        } catch (Exception e) {
            log.error("微信登录失败: {}", e.getMessage());
            throw new ServiceException(ResultCode.SYSTEM_ERROR, "登录异常");
        }
    }

    @Override
    public LoginUserVO loginByWeb(LoginDTO loginDTO) {
        String account = loginDTO.getAccount();
        String password = loginDTO.getPassword();
        if (!StringUtils.hasLength(account) || !StringUtils.hasLength(password)) {
            throw new ServiceException(ResultCode.BAD_REQUEST, "账号或密码不能为空");
        }
        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserEntity::getAccount, account).eq(UserEntity::getPassword, password);
        UserEntity user = userService.getOne(wrapper);
        if(Objects.isNull(user) ){
            throw new ServiceException(ResultCode.UNAUTHORIZED, "账号或密码错误");
        }
        String token = tokenService.generateAndStoreToken(user.getId());
        LoginUserVO vo = EntityToVoConverter.convertSingle(user, LoginUserVO.class);
        vo.setToken(token);
        return vo;
    }

    @Override
    public boolean logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            tokenService.deleteToken(token);
        }
        return true;
    }

    @Override
    public boolean registerByWeb(LoginDTO loginDTO) {
        String account = loginDTO.getAccount();
        String password = loginDTO.getPassword();
        if (!StringUtils.hasLength(account) || !StringUtils.hasLength(password)) {
            throw new ServiceException(ResultCode.BAD_REQUEST, "账号或密码不能为空");
        }
        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserEntity::getAccount, account);
        UserEntity existingUser = userService.getOne(wrapper);
        if(existingUser != null){
            throw new ServiceException(ResultCode.BAD_REQUEST, "账号已存在");
        }
        UserEntity newUser = new UserEntity();
        newUser.setAccount(account);
        newUser.setPassword(password);
        return userService.save(newUser);
    }
}
