package com.diary.calendar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.diary.calendar.dto.UserDTO;
import com.diary.calendar.entity.UserEntity;

public interface UserService extends IService<UserEntity> {
    UserEntity findOrCreateUserByOpenid(String openid);
    UserEntity updateUser(UserDTO userDTO);
    boolean deleteUser(UserDTO userDTO);
}
