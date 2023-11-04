package com.test.fx.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.test.fx.user.model.UserModel;

import java.util.Map;

//
public interface UserService extends IService<UserModel> {

    /**
     * @Description 用户查询
     * @author sunxn
     * @date 2022/10/20
     */
    public UserModel GetUser(Map<String,String> user) throws Exception;

    /**
     * @Description 保存
     * @author sunxn
     * @date 2022/10/20
     */
    public String saveUser(UserModel user);

    /**
     * @Description 删除
     * @author sunxn
     * @date 2022/10/20
     */
    public String deleteUser(UserModel user);
}
