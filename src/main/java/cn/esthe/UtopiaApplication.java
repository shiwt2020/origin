package cn.esthe;

import cn.esthe.test.Dog;
import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

//@EnableCaching
//@SpringBootApplication
public class UtopiaApplication {
    public static void main(String[] args) {
//        for (int i = 0; i < 100; i++) {
//            int num = i;
//            new Thread(() -> {
//                Dog dog = new Dog();
//                Dog.LinkInfo linkInfo = dog.getLinkInfo();
//                if(linkInfo==null){
//                    linkInfo =new Dog.LinkInfo();
//                    linkInfo.setName(num+"");
//                }
//                linkInfo.setName(num+"");
//                System.out.println(linkInfo.getName());
//            }).start();
//        }
//        String str="12434";
//        System.out.println(str.substring(0,2));
//        System.out.println(str);
//        List <String> list=new ArrayList<>();
//        list.add("aaa");
//        list.add("bbb");
//        list.add("ccc");
//        List <String> aaa = list.stream().filter(x -> x.equals("aaa")).collect(Collectors.toList());
//
//        Long aa=null;
//
//        String s = String.valueOf(aa);
//        System.out.println(s);
//
//        System.out.println();
//
//        System.out.println("111");


        // 密码长度不少于8位且至少包含大写字母、小写字母、数字和特殊符号中的四种
        //   String password = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{14,20}$";
            String password = "(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[\\W_]).{14,20}";
        // 密码长度8-20位且至少包含大写字母、小写字母、数字或特殊符号中的任意三种
//        String password = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[~!@#$%^&*])[\\da-zA-Z~!@#$%^&*]{14,20}$";


        String password1 = "ABCDEFGHIG";  //全部大写
        String password2 = "ABCDE01234!@#$%"; //符合要求密码任意三种
        String password3 = "!@#$%^&*ab1cAe!@#$%"; //符合要求密码任意三种
        String password4 = "*~ABCDEabcde01234"; //符合要求密码任意三种
        String password5 = "123456aaaaAaaaaa!@"; //符合要求密码任意三种
        String password6 = "Xiaoming12345*"; //符合要求密码任意三种 和 符合全部的四种

        System.out.println(password1.matches(password) + "1长度"+password1.length());
        System.out.println(password2.matches(password) + "2长度"+password2.length());
        System.out.println(password3.matches(password) + "3长度"+password3.length());
        System.out.println(password4.matches(password) + "4长度"+password4.length());
        System.out.println(password5.matches(password) + "5长度"+password5.length());
        System.out.println(password6.matches(password) + "6长度"+password6.length());

        List <Integer> integers = Lists.asList(0, 1, new Integer[0]);
        System.out.println(integers);

        List <String> list = new ArrayList <>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        list.add("ddd");
        list.add("eee");

//        list.forEach(x->{
//            if(x.equals("aaa")){
//                return;
//            }
//            System.out.println(x);
//        });
//        BigDecimal bi=new BigDecimal(0.001);
//        int i = bi.compareTo(new BigDecimal(0.1));
//        System.out.println(i);
//
//
//        String timeStr1= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        String timeStr2=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
//        System.out.println("当前时间为:"+timeStr1);
//        System.out.println("当前时间为:"+timeStr2);


    }


}

