package cn.esthe.other.dataStructure;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
//    public static void main(String[] args) {
//
////        单向链表
////        LoopListNode<String> loopListNode = new LoopListNode<>();
////        loopListNode.add("aa");
////        loopListNode.add("bb");
////        loopListNode.add("cc");
////        loopListNode.add("dd");
////        loopListNode.add("ee");
////        loopListNode.get(10);
////        for (int i = 0; i < loopListNode.getSize(); i++) {
////            String s = loopListNode.get(i);
////            System.out.println(s);
////        }
////        loopListNode.remove(loopListNode.getSize()-1);
////        System.out.println("====================");
////        for (int i = 0; i < loopListNode.getSize(); i++) {
////            String s = loopListNode.get(i);
////            System.out.println(s);
////        }
//
////        avl树
////        AvlTree<Integer>  avlTree=new AvlTree<>();
////        avlTree.insert(1);
////        avlTree.insert(2);
////        avlTree.insert(3);
////        avlTree.insert(4);
////        avlTree.insert(5);
////        avlTree.insert(6);
////        avlTree.insert(7);
////        avlTree.insert(8);
////        avlTree.insert(9);
////        avlTree.remove(6);
////        avlTree.printTree();
//
////        int a=1;
////        add(a=2);
//        filter(new Result(null,null));
//
//
//    }
//
//    public static void filter(Result request){
//        Result result1 =new Result("1","code");
//        Result result2 =new Result("2","ssss");
//        Result result3 =new Result("3","code");
//        List<Result> list =new ArrayList<>();
//        list.add(result1);
//        list.add(result2);
//        list.add(result3);
//        Stream<Result> resultStream = list.parallelStream();
//
//        List<Result> code = list.stream()
//                .filter(x ->StringUtils.isEmpty(request.getSuccess()) || request.getSuccess().equals(x.getSuccess()))
//                .filter(y-> StringUtils.isEmpty(request.getMessage()) || request.getMessage().equals(y.getMessage()))
//                .collect(Collectors.toList());
//        System.out.println(code.size());
//
//
//
//    }
//
//    static void add(int b){
//        System.out.println(b);
//    }

    public static void main(String[] args) {
        List<String> streamList =new ArrayList<>();
        streamList.add("hello");
        streamList.add("world");
        streamList.add("stream");
        streamList.add("test");

        List<String> hello = streamList.stream().filter(x -> x.equals("hello")).collect(Collectors.toList());
        System.out.println("111");
    }

}
