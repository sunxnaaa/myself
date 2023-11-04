package com.test.fx.system.action;

import com.test.fx.util.AxiosVo;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description 系统controller
 * @author sunxn
 * @date 2022/12/12
 */
public class systemAction {
    /**
     * @Description 用户查询
     * @author sunxn
     * @date 2022/10/20
     */
    @RequestMapping("getSystemInfo")
    public AxiosVo getSystemInfo(){
        AxiosVo axiosVo = new AxiosVo();
        try {
            System.getenv();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return axiosVo ;
    }
}
