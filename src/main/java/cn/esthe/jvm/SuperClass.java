package cn.esthe.jvm;

public class SuperClass {
    static {
        System.out.println("superClass is init");
    }
    public static int value=1;


    public void show(){
        System.out.println("show方法");
    }
}
