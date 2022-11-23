package cn.esthe.other;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Arrays;

public class TenSort {
    public static void main(String[] args) {
        int[] nums = new int[]{31, 3, 2, 6, 9, 5};
//        insertionSort(nums);
//      shellSort(nums);
//      choiceSort(nums);
      //  bubbleSort(nums);

//        for (int i = 0; i < nums.length; i++) {
//            System.out.print(nums[i] + " ");
//        }
        try {
            LocalDateTime time = LocalDateTime.now();
            Thread.sleep(1000);
            System.out.println(time);
            LocalDateTime now = LocalDateTime.now();
            System.out.println(now);
            System.out.println(time.isBefore(now));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 冒泡排序
     *
     * @param arr
     */
    public static void bubbleSort(int[] arr) {
        if (arr.length == 0) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }

            }
        }
    }

    /**
     * 选择排序
     */
    public static void choiceSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int index = i;
            for (int j = i; j < arr.length; j++) {
                if (arr[index] > arr[j]) {
                    index = j;
                }
            }
            int temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;
        }
    }

    /**
     * 插入排序  基本思想：拿index+1位置的值与前面的值进行比较
     *
     * @param arr
     * @return
     */
    public static void insertionSort(int[] arr) {
        if (arr.length == 0) {
            return;
        }
        for (int i = 0; i < arr.length - 1; i++) {
            // 当前需要比较的值
            int cur = arr[i + 1];
            // 前一个值
            int preIndex = i;
            while (preIndex >= 0 && cur < arr[preIndex]) {
                // 如果前一个值比当前值小，则前一个值往后移动一位
                arr[preIndex + 1] = arr[preIndex];
                preIndex--;
            }
            arr[preIndex + 1] = cur;
        }
    }

    /**
     * 希尔排序
     *
     * @param arr
     * @return
     */
    public static void shellSort(int[] arr) {
        if (arr.length == 0) {
            return;
        }
        int temp;
        int gap = arr.length / 2;
        while (gap > 0) {
            for (int i = gap; i < arr.length; i++) {
                temp = arr[i];
                int preIndex = i - gap;
                while (preIndex >= 0 && arr[preIndex] > temp) {
                    arr[preIndex + gap] = arr[preIndex];
                    preIndex -= gap;
                }
                arr[preIndex + gap] = temp;
            }
            gap /= 2;
        }
    }

    /**
     * 归并排序 递归+排序  将两段排序好的数组合到一起
     */
    public static int[] mergeSort(int[] arr){
        // 递归终止条件
        if(arr.length<2){
            return arr;
        }
        int[] left= Arrays.copyOfRange(arr,0,arr.length/2);
        int[] right=Arrays.copyOfRange(arr,arr.length+1,arr.length);
        return merge(mergeSort(left),mergeSort(right));
    }

    /**
     * 归并排序 递归+排序  将两段排序好的数组合到一起
     */
    public static int[] merge(int[] left,int[] right){

        return null;
    }



}
