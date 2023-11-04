package com.test.fx.login.action;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.test.fx.user.dao.UserMapper;
import com.test.fx.user.model.UserModel;
import com.test.fx.util.AxiosVo;
import com.test.fx.util.MailUtil;
import com.test.fx.util.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 孙晓宁
 * @version 1.0
 * @date 2021/4/16 13:52
 */
@RestController
@RequestMapping("/email")
public class EmailController {

    @RequestMapping("sendIdCode")
    public String AjaxSendIdCode(String mail, HttpServletResponse response, HttpServletRequest request)
            throws IOException, AddressException, MessagingException {
        response.setCharacterEncoding("utf-8");
        int idcode = (int) (Math.random()*100000);
        String text = Integer.toString(idcode);
        request.getSession().setAttribute("idcode", text);
        MailUtil mailUtil = new MailUtil();
        mailUtil.sendMail(mail, text);
        return "success";
    }


    @RequestMapping("regist")
    public AxiosVo regist(UserModel user, String idcode, HttpServletRequest request){
        AxiosVo axiosVo = new AxiosVo();
        QueryWrapper<UserModel> wrapper = new QueryWrapper<>();
        wrapper.eq("username",user.getUserName());
        if(user.getUserName()!= null){
            if(!request.getSession().getAttribute("idcode").equals(idcode)){
                axiosVo.setCode(ResultCodeEnum.SUCCESS.getCode());
            }
        }
        return axiosVo;
    }

}
