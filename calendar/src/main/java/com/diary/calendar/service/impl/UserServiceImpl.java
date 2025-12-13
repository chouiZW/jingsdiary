package com.diary.calendar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diary.calendar.dto.UserDTO;
import com.diary.calendar.entity.UserEntity;
import com.diary.calendar.mapper.UserMapper;
import com.diary.calendar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Autowired
    private UserMapper userMapper; // 你的用户数据访问接口

    @Override
    public UserEntity findOrCreateUserByOpenid(String openid) {
        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserEntity::getOpenid, openid);
        // 1. 根据openid查询用户
        UserEntity user = userMapper.selectOne(wrapper);

        // 2. 如果用户不存在，则创建新用户
        if (user == null) {
            user = new UserEntity();
            user.setOpenid(openid);
            user.setName("用户" + UUID.randomUUID());
            // 可以设置一些默认用户信息，如默认昵称、注册时间等
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            userMapper.insert(user);
        }
        // 3. 返回用户对象 (无论是新创建的还是已存在的)
        return user;
    }

    @Override
    public UserEntity updateUser(UserDTO userDTO) {
        return null;
    }

    @Override
    public boolean deleteUser(UserDTO userDTO) {
        return false;
    }
}
