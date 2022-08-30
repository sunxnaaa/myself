import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.test.fx.user.dao.UserMapper;
import com.test.fx.user.model.UserModel;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class test {
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
}
