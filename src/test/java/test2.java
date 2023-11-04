import org.junit.Test;

import java.math.BigDecimal;
import java.util.Scanner;

public class test2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextLine()) { // 注意 while 处理多个 case
            double a = in.nextDouble();
            int b = (int)(a);
            String str[] = new String[10];//数字
            String str1[] = new String[13];//位数
            String str2[] = new String[13];//单位
            String end = null;
            StringBuilder str3 = new StringBuilder();
            str[0] = "零";
            str[1] = "壹";
            str[2] = "贰";
            str[3] = "叁";
            str[4] = "肆";
            str[5] = "伍";
            str[6] = "陆";
            str[7] = "柒";
            str[8] = "捌";
            str[9] = "玖";
            str1[0] = "拾";
            str1[1] = "佰";
            str1[2] = "仟";
            str1[3] = "";
            str1[4] = "拾";
            str1[5] = "佰";
            str1[6] = "仟";
            str1[7] = "";
            str1[8] = "拾";
            str1[9] = "百";
            str1[10] = "仟";
            str2[0] = "元";
            str2[1] = "角";
            str2[2] = "分";
            str2[3] = "整";
            for (int i = 0; i < 13; i++) {
                if (b / 10 == 0 ) {
                    int f = b % 10;
                    if (f == 1) {
                        str3.insert(0, str1[i - 1]);
                    } else if(f == 0){
                        str3.insert(0, str[f]);
                    }else {
                        str3.insert(0, str1[i - 1]);
                        str3.insert(0, str[f]);
                    }
                    str3.append( "元");
                    end = str3.toString();
                    break;
                } else {
                    {
                        int f = b % 10;
                        if (i == 4) {
                            str3.insert(0, "万");
                            str3.insert(0, str[f]);
                        } else if (i == 7) {
                            str3.insert(0, "亿");
                            str3.insert(0, str[f]);
                        } else {
                            str3.insert(0, str[f]);
                            str3.insert(0, str1[i]);
                        }
                        end = str3.toString().replaceAll("零", "零零");
                    }
                }
                b = b / 10;
            }
            str3.setLength(0);
            BigDecimal bd = BigDecimal.valueOf(a);
            BigDecimal bf = new BigDecimal((int)a);
            bd = bd.subtract(bf);
            double g = bd.doubleValue();
            if (g == 0) {
                str3.append("整");
            } else {
                int h = (int)(g * 100);
                if (h % 10 != 0) {
                    str3.insert(0, "分");
                    str3.insert(0, str[h % 10]);
                }
                if (h / 10 > 0) {
                    h = h / 10;
                    str3.insert(0, "角");
                    str3.insert(0, str[h % 10]);
                }
            }
            str3.insert(0, end);
            str3.insert(0, "人民币");
            System.out.println(str3.toString());
        }
    }
    @Test
    public void testJunit5(){

    }
}
