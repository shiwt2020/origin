package cn.esthe.jvm;

public class MultiProcess {
    public static volatile  int race = 0;

    public static void increase() {
        race++;
    }

    public static final int THREAD_COUNT = 20;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        Thread[] threads = new Thread[THREAD_COUNT];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        increase();
                    }

                }
            });
            threads[i].start();
        }
        System.out.println("111111111");

        while (Thread.activeCount()>2){
            Thread.yield();
        }

        long end = System.currentTimeMillis();

        System.out.println("共耗时："+(end-start));
        System.out.println(race);
    }
}
