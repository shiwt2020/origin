package cn.esthe.other.leetcode;

import java.util.HashMap;
import java.util.Map;

// 给定一个整数数组nums和一个整数目标值target，请你在该数组中找出和为目标值target的那两个整数，并返回它们的数组下标。

//    示例 1：
//        输入：nums = [2,7,11,15], target = 9
//        输出：[0,1]
//        解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1]
//
//    示例 2：
//        输入：nums = [3,2,4], target = 6
//        输出：[1,2]
//
//    示例 3：
//        输入：nums = [3,3], target = 6
//        输出：[0,1]

public class TwoNumberAdd {

    public static int[] twoSum(int[] nums, int target) {
        Map <Integer, Integer> map = new HashMap <>();
        for (int i = 0; i < nums.length; i++) {
            if (map.get(target - nums[i]) != null) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("can't find this result");
    }

    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int[] ints = twoSum(nums, 200);
        System.out.println(ints);
    }
}
