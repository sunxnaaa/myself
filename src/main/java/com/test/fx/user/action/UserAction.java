package com.test.fx.user.action;

import com.test.fx.user.model.UserModel;
import com.test.fx.user.service.UserService;
import com.test.fx.util.AxiosVo;
import com.test.fx.util.JwtUtil;
import com.test.fx.util.ResultCodeEnum;
import com.test.fx.util.ToolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/test/fx/user")
public class UserAction {
    @Autowired
    private UserService userService;

    @RequestMapping("validUser")
    public AxiosVo validUser(@RequestBody Map<String,String> user){
        AxiosVo axiosVo = new AxiosVo();
        UserModel userModel = null;
        try {
            userModel = userService.GetUser(user);
        } catch (Exception e) {
            axiosVo.setCode(ResultCodeEnum.UNKNOWN_REASON.getCode());
            axiosVo.setSuccess(ResultCodeEnum.UNKNOWN_REASON.getSuccess());
            axiosVo.setMsg(ResultCodeEnum.UNKNOWN_REASON.getMessage());
            e.printStackTrace();
        }
        if (!ToolUtils.isNull(userModel)){
            axiosVo.setCode(ResultCodeEnum.SUCCESS.getCode());
            axiosVo.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
            axiosVo.setMsg(ResultCodeEnum.SUCCESS.getMessage());
        }else {
            axiosVo.setCode(ResultCodeEnum.USER_NAME_REPEAT.getCode());
            axiosVo.setSuccess(ResultCodeEnum.USER_NAME_REPEAT.getSuccess());
            axiosVo.setMsg(ResultCodeEnum.USER_NAME_REPEAT.getMessage());
        }
        return axiosVo ;
    }
}
