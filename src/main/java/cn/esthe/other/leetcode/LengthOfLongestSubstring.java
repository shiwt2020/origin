package cn.esthe.other.leetcode;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个字符串 s ,请你找出其中不含有重复字符的最长子串的长度
 * 示例1:
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc",所以其长度为 3
 * <p>
 * 示例 2:
 * 输入: s = "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b",所以其长度为 1
 * <p>
 * 示例 3:
 * 输入: s = "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是"wke",所以其长度为 3
 * 请注意,你的答案必须是 子串 的长度,"pwke"是一个子序列,不是子串
 * <p>
 * 提示
 * 0 <= s.length <= 5 * 104
 * s由英文字母,数字符号和空格组成
 */

public class LengthOfLongestSubstring {
    public static void main(String[] args) {
        Integer num=10;
        String str="10";
        System.out.println(str.equals(num));

    }

    public static int getLength(String s) {
        if (s.length() == 0)
            return 0;

        int left = 0;
        int max = 0;
        Map <Character,Integer> map =new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            if(map.containsKey(c)){
                Integer cur = map.get(c)+1;
                left=Math.max(max,cur);
            }
            map.put(c,i);
            max=Math.max(max,i-left+1);
        }
        return max;
    }
}
