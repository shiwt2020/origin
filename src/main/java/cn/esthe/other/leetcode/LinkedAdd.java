package cn.esthe.other.leetcode;

import javax.xml.soap.Node;
import java.util.Objects;

public class LinkedAdd {
    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node12 = new Node(2);
        node1.next = node12;

        Node nn1 = new Node(5);
        Node nn2 = new Node(6);
        nn1.next = nn2;

        Node add = add(node1, nn2);

        System.out.println(add);
    }

    public static Node add(Node first, Node sec) {
        int carry = 0;
        Node pre = new Node(0);
        Node cur = pre;
        while (first != null || sec != null) {
            // 循环中止条件
            int x = first == null ? 0 : first.val;
            int y = sec == null ? 0 : sec.val;
            int sum = x + y + carry;

            // 当前值
            int val = sum % 10;
            carry = sum / 10;

            cur = new Node(val);
            cur = cur.next;

            // 怎么能那么愚蠢？？？？？？
            if (first != null) {
                first = first.next;
            }
            if (sec != null) {
                sec = sec.next;
            }

            if(carry==1){
                cur.next=new Node(carry);
            }
        }
        return pre.next;
    }

    /**
     * 单向链表
     */
    private static class Node {
        int val;
        Node next;

        public Node(int val) {
            this.val = val;
        }
    }
}
