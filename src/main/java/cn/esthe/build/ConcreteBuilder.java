package cn.esthe.build;

import org.apache.poi.ss.formula.functions.T;

import java.lang.reflect.Field;

public class ConcreteBuilder<T extends BaseBean> {

    private String id;
    private String name;

    private String vt;
    private String video;



    public T build(T t) {
        Class <? extends BaseBean> beanClazz = t.getClass();
        Class <? extends ConcreteBuilder> clazz = this.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                String name = field.getName();
                field.setAccessible(true);
                Field value = clazz.getDeclaredField(name);
                Field father = beanClazz.getSuperclass().getField(name);
                if(father!=null){
                    father.set(name,value.get(t));
                }
                Field beanField = beanClazz.getField(name);
                if(field!=null){
                    beanField.set(name,value.get(t));
                }
            } catch (NoSuchFieldException |IllegalAccessException e) {
                System.out.println("beanClazz not such value...");
            }
        }
        return t;
    }

    public static ConcreteBuilder builder(){
        return new ConcreteBuilder();
    }

    public String getId() {
        return id;
    }

    public ConcreteBuilder id(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ConcreteBuilder name(String name) {
        this.name = name;
        return this;
    }

    public String getVt() {
        return vt;
    }

    public ConcreteBuilder vt(String vt) {
        this.vt = vt;
        return this;
    }

    public String getVideo() {
        return video;
    }

    public ConcreteBuilder video(String video) {
        this.video = video;
        return this;
    }
}
