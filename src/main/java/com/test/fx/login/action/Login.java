package com.test.fx.login.action;

import com.test.fx.user.model.UserModel;
import com.test.fx.user.service.UserService;
import com.test.fx.util.AxiosVo;
import com.test.fx.util.JwtUtil;
import com.test.fx.util.ResultCodeEnum;
import com.test.fx.util.ToolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/test/fx/login")
public class Login {
    @Autowired
    private UserService userService;

    @RequestMapping("userLogin")
    public AxiosVo userLogin(@RequestBody Map<String,String> user, HttpServletRequest request){
        AxiosVo axiosVo = new AxiosVo();
        String token;
        try {
            UserModel userModel = userService.GetUser(user);
            if (!ToolUtils.isNull(userModel)){
                try {
                    token = JwtUtil.createToken(user.get("username"));
                    axiosVo.setCode(ResultCodeEnum.SUCCESS.getCode());
                    axiosVo.setSuccess(true);
                    axiosVo.setMsg(ResultCodeEnum.SUCCESS.getMessage());
                    axiosVo.setToken(token);
                    HttpSession session = request.getSession();
                    session.setAttribute("user",userModel);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("生成token失败");
                }
            }else {
                axiosVo.setCode(ResultCodeEnum.USER_NAME_REPEAT.getCode());
                axiosVo.setSuccess(ResultCodeEnum.USER_NAME_REPEAT.getSuccess());
                axiosVo.setMsg(ResultCodeEnum.USER_NAME_REPEAT.getMessage());
            }
        } catch (Exception e) {
            axiosVo.setCode(ResultCodeEnum.USER_NAME_REPEAT.getCode());
            axiosVo.setSuccess(ResultCodeEnum.UNKNOWN_REASON.getSuccess());
            axiosVo.setMsg(e.toString());
            e.printStackTrace();
        }
        return axiosVo;
    }


    @RequestMapping("userVerify")
    public AxiosVo userVerify(HttpServletRequest request){
        AxiosVo axiosVo = new AxiosVo();
        axiosVo.setCode(20000);
        HttpSession session = request.getSession();
        UserModel userModel = (UserModel) session.getAttribute("user");
        UserModel model = userService.getById(userModel.getId());
        axiosVo.setObj(model);
        return axiosVo ;
    }

    @RequestMapping("userLoginOut")
    public AxiosVo userLoginOut( HttpServletRequest request){
        AxiosVo axiosVo = new AxiosVo();
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        axiosVo.setCode(20000);
        return axiosVo;
    }

}
