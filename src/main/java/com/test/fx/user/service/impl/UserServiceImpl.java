package com.test.fx.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.fx.user.dao.UserMapper;
import com.test.fx.user.model.UserModel;
import com.test.fx.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper,UserModel> implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public UserModel GetUser(Map<String,String> user){
        UserModel userModel = new UserModel();
        userModel.setUserName(user.get("username"));
        userModel.setUserPassword(user.get("password"));
        QueryWrapper<UserModel> queryWrapper = new QueryWrapper<>(userModel);
        return userMapper.selectOne(queryWrapper);
    }
}
