import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.test.fx.sunApplication;
import com.test.fx.user.dao.UserMapper;
import com.test.fx.user.model.UserModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;


public class a {
    @Autowired
    private UserMapper Mapper;
    @Test
    public void testJunit5(){

        UserModel model = new UserModel();
        model.setUserName("sunxn");
        model.setUserPassword("123456");
        try {
            QueryWrapper<UserModel> queryWrapper = new QueryWrapper<>(model);
            UserModel userModel = Mapper.selectOne(queryWrapper);
            System.out.println(userModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testJunit6(){
        Map<Integer,String> map = new HashMap<>();
        List<String> arrayList = new ArrayList<>();
        List<String> linkedList = new LinkedList<>();
        for(int i = 0;i<9000000;i++){
            arrayList.add(i+"==");
            map.put(i,i+"==");
            linkedList.add(i+"==");
        }

        /*-------------Areraylist计时 -------------------*/
        long startTime =  System.currentTimeMillis();
        long endTime = 0;
        for (String li:arrayList ) {

        }
        endTime =  System.currentTimeMillis();
        long usedTime = endTime-startTime;
        System.out.println("Areraylist计时==== " + usedTime);

        /*-------------linkListt计时 -------------------*/
        long startTime3 =  System.currentTimeMillis();
        long endTime3 = 0;
        for (String li:linkedList ) {

        }
        endTime3 =  System.currentTimeMillis();
        long usedTime3 = endTime3-startTime3;
        System.out.println("linkedList计时==== " + usedTime3);

        /*-------HashMap-- entrySet计时--------*/
        long startTime1 =  System.currentTimeMillis();
        long endTime1 = 0;
        for (Map.Entry<Integer, String> entry : map.entrySet()) {

        }
        endTime1 =  System.currentTimeMillis();
        long usedTime1 = endTime1-startTime1;
        System.out.println("HashMap-- entrySet --计时=="+usedTime1);

        /*---------HashMap----forkey计时------------*/
        long startTime2=  System.currentTimeMillis();
        long endTime2 = 0;
        for (Integer key : map.keySet()) {
        }
        endTime2 =  System.currentTimeMillis();
        long usedTime2 = endTime2-startTime2;
        System.out.println("HashMap-- forkey --计时=="+usedTime2);



    }

}
