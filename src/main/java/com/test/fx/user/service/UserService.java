package com.test.fx.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.test.fx.user.model.UserModel;

import java.util.Map;

//
public interface UserService extends IService<UserModel> {

    public UserModel GetUser(Map<String,String> user) throws Exception;

}
