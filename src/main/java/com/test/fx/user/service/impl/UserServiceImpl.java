package com.test.fx.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.fx.user.dao.UserMapper;
import com.test.fx.user.model.UserModel;
import com.test.fx.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper,UserModel> implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    @Transactional
    public UserModel GetUser(Map<String,String> user){
        UserModel userModel = new UserModel();
        userModel.setUserName(user.get("username"));
        userModel.setUserPassword(user.get("password"));
        QueryWrapper<UserModel> queryWrapper = new QueryWrapper<>(userModel);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public String saveUser(UserModel user) {
        return null;
    }

    @Override
    public String deleteUser(UserModel user) {
        return null;
    }

}
