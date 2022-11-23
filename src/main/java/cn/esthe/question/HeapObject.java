package cn.esthe.question;

import java.util.ArrayList;
import java.util.List;

public class HeapObject {
    static class OOMObject {
    }

    static int a = 0;

    public static void addStack() {
        a++;
        for (int i = 0; i < a; i++) {
            String str = new String();
        }
        addStack();
    }

    public static void main(String[] args) {
//        List<OOMObject> list =new ArrayList<>();
//        while (true){
//            list.add(new OOMObject());
//        }
        try {
            addStack();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(a);
        }

    }
}
