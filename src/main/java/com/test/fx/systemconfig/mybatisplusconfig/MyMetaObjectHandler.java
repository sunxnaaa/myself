package com.test.fx.systemconfig.mybatisplusconfig;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.test.fx.user.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * @Description
 * @author sunxn
 * @date 2022/9/30
 */

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        UserModel userModel = (UserModel) session.getAttribute("user");
        this.strictInsertFill(metaObject, "createDate", () -> LocalDateTime.now(), LocalDateTime.class);
        this.strictInsertFill(metaObject, "createUser", String.class, userModel.getUserName());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        UserModel userModel = (UserModel) session.getAttribute("user");
        this.strictInsertFill(metaObject, "updateDate", () -> LocalDateTime.now(), LocalDateTime.class);
        this.strictInsertFill(metaObject, "updateUser", String.class, userModel.getUserName());
    }
}