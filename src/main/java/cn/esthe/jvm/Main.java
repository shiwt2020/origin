package cn.esthe.jvm;

import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = new ClassLoader() {
            @Override
            public Class <?> loadClass(String name) throws ClassNotFoundException {
                try {
                    System.out.println("name="+name);
                    Class <?> loadedClass = findLoadedClass(name);
                    System.out.println("loadedClass="+loadedClass);
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    System.out.println("fileName=" + fileName);
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    Class <?> aClass = defineClass(name, b, 0, b.length);
                    System.out.println("=====aClass="+aClass);
                    return aClass;
                } catch (IOException e) {
                    throw new ClassNotFoundException();
                }
            }
        };
        Object obj = classLoader.loadClass("cn.esthe.jvm.Main").newInstance();
//        System.out.println(obj.getClass());
//        System.out.println(obj instanceof cn.esthe.jvm.Main);
    }
}
