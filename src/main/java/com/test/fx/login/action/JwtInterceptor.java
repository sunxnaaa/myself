package com.test.fx.login.action;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.test.fx.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * @Description拦截器
 * @author sunxn
 * @date 2022/11/14
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        String uri = request.getRequestURI();
        System.out.println("uri: " + uri);
        String headToken = request.getHeader("X-Token");
        if(StringUtils.isEmpty(headToken)) {
            Map<String, Object> map = new HashMap<>();
            map.put("code", 50008);
            map.put("message", "token");
            ErrorResponse(response, map);
            return false;
        }
        try {
            Map<String, Claim> map1 = JwtUtil.verifyToken(headToken);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> map = new HashMap<>();
            map.put("code", 50014);
            map.put("message", "Invalid Authorization header " + e.getLocalizedMessage());
            ErrorResponse(response, map);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }

    // 被拦截的请求响应
    private void ErrorResponse(HttpServletResponse response, Map<String, Object> result){
        OutputStream out = null;

        JSONObject object = new JSONObject();
        object.put("result", result);

        try{
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            out = response.getOutputStream();
            out.write(object.toString().getBytes());
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try {
                if (out != null) {
                    out.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
