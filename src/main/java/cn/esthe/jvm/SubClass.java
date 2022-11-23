package cn.esthe.jvm;

public class SubClass extends SuperClass{
    static {
        System.out.println("subClass is init");
    }

    public static void main(String[] args)throws Exception {
        {

        }
        SubClass sb=new SubClass();
        System.out.println("111");
    }

    public static void add(Integer num) throws Exception {
        if(num==1){
            throw new  Exception();
        }

    }

}
