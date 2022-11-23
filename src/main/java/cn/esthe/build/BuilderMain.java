package cn.esthe.build;

public class BuilderMain {
    public static void main(String[] args) {
        ConcreteBuilder<DataRecord> builder =new ConcreteBuilder<DataRecord>();
        DataRecord build = (DataRecord)builder.builder()
                .id("123")
                .name("xiaoming")
                .video("video")
                .vt("vt")
                .build(new DataRecord());

        System.out.println("111");
    }
}
