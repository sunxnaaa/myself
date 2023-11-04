import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class test1 {
    @Test
    public void testJunit6(){
        double a = 0.135;
        double b = 3;
        BigDecimal b1 = new BigDecimal(Double.toString(a));
        BigDecimal b2 = new BigDecimal(Double.toString(b));
        System.out.println(this.mul2(b1,b2));
    }
    public double mul2(BigDecimal b1,BigDecimal b2){
        BigDecimal b3 = b1.multiply(b2);
        double v = b3.setScale(2, RoundingMode.HALF_UP).doubleValue();
        return v;
    }
}
