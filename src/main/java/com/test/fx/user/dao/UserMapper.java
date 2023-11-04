package com.test.fx.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.fx.user.model.UserModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface UserMapper extends BaseMapper<UserModel> {
    List<UserModel> getList(Map<String, String> map);
}
