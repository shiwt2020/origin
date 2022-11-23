package cn.esthe;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
////        int sum = in.nextInt();
////        if (sum == 0) {
////            return;
////        }
////        // 获取从键盘输入的数字
////        int[] arr = new int[sum];
////        int index = 0;
////        while (sum > 0) {
////            int num = in.nextInt();
////            arr[index] = num;
////            index++;
////            sum--;
////        }

        // 排序去重操作
//        int[] result = new int[]{};
        int[] arr=new int[]{1,3,6,0,9};
        for (int k = 0; k < arr.length; k++) {
            for (int i = k + 1; i < arr.length; i++) {
                int temp=arr[k];
                if (arr[i] < temp) {
                    arr[k]=arr[i];
                    arr[i]=temp;
                }
            }
        }
        System.out.println("1");
    }

}
