import org.junit.Test;

import java.util.*;


public class gatherTest {

    public static void main(String[] args) {
        String a = "a";
        StringBuilder Str = new StringBuilder("a");
        String b = Str.toString();
        System.out.println(a.equals(b));

    }
    @Test
    public void testJunit6(){
        Map<Integer,String> map = new HashMap<>();
        List<String> arrayList = new ArrayList<>();
        List<String> linkedList = new LinkedList<>();
        long startTime4 =  System.currentTimeMillis();
        long endTime4 = 0;
        for(int i = 0;i<9000000;i++){
            arrayList.add(i+"==");
        }
        endTime4 =  System.currentTimeMillis();
        long usedTime4 = endTime4-startTime4;
        System.out.println("arrayList-- add --计时=="+usedTime4);



        long startTime5 =  System.currentTimeMillis();
        long endTime5 = 0;
        for(int i = 0;i<9000000;i++){
            map.put(i,i+"==");
        }
        endTime5 =  System.currentTimeMillis();
        long usedTime5 = endTime5-startTime5;
        System.out.println("HashMap-- put --计时=="+usedTime5);



        long startTime6 =  System.currentTimeMillis();
        long endTime6 = 0;
        for(int i = 0;i<9000000;i++){
            linkedList.add(i+"==");
        }
        endTime6 =  System.currentTimeMillis();
        long usedTime6 = endTime6-startTime6;
        System.out.println("linkedList-- add --计时=="+usedTime6);
       /* *//*-------------Areraylist计时 -------------------*//*
        long startTime =  System.currentTimeMillis();
        long endTime = 0;
        for (String li:arrayList ) {

        }
        endTime =  System.currentTimeMillis();
        long usedTime = endTime-startTime;
        System.out.println("Areraylist计时==== " + usedTime);

        *//*-------------linkListt计时 -------------------*//*
        long startTime3 =  System.currentTimeMillis();
        long endTime3 = 0;
        for (String li:linkedList ) {

        }
        endTime3 =  System.currentTimeMillis();
        long usedTime3 = endTime3-startTime3;
        System.out.println("linkedList计时==== " + usedTime3);

        *//*-------HashMap-- entrySet计时--------*//*
        long startTime1 =  System.currentTimeMillis();
        long endTime1 = 0;
        for (Map.Entry<Integer, String> entry : map.entrySet()) {

        }
        endTime1 =  System.currentTimeMillis();
        long usedTime1 = endTime1-startTime1;
        System.out.println("HashMap-- entrySet --计时=="+usedTime1);

        *//*---------HashMap----forkey计时------------*//*
        long startTime2=  System.currentTimeMillis();
        long endTime2 = 0;
        for (Integer key : map.keySet()) {
        }
        endTime2 =  System.currentTimeMillis();
        long usedTime2 = endTime2-startTime2;
        System.out.println("HashMap-- forkey --计时=="+usedTime2);*/

    }

}
