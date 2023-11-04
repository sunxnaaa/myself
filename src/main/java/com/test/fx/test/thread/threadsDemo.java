package com.test.fx.test.thread;
/**
 * @Description 多线程学习
 * @author sunxn
 * @date 2022/11/14
 */
public class threadsDemo extends Thread{

    private String title;
    public threadsDemo(String title){
        this.title = title;
    }
    public void run(){
        for (int i=0;i<10;i++){
            System.out.println(this.title + "运行，i = " + i);
        }
    }
    public static class MyThreadDemo{
        public static void main(String[] args) {

            new threadsDemo("线程A").run();


            new threadsDemo("线程A").start();
            new threadsDemo("线程B").start();
            new threadsDemo("线程C").start();


        }
    }

}
