package cn.esthe.other.dataStructure;

/**
 * 循环链表实现LinkedList集合
 *
 * @param <E> 泛型
 */
public class LoopListNode<E> {

    //用于记录头节点
    transient Node<E> head;

    //用于记录尾节点
    transient Node<E> rear;

    //记录该链表的大小
    private int size = 0;

    //获取集合大小
    public int getSize() {
        return size;
    }

    //从头部添加元素
    private void firstNode(E e) {
        Node<E> node = new Node<>(e, null);
        if (head == null) {
            rear = node;
        }
        head = node;
        node.next = head;
        size++;
    }

    //从尾部添加元素
    private void lastNode(E e) {
        Node<E> node = new Node<>(e, null);
        if (rear == null) {
            rear =node;
            head = node;
        }
        //将新加入如的最后一个节点 指向 头节点
        node.next = rear.next;
        rear.next = node;
        rear = node;
        size++;
    }

    //根据索引添加元素
    public void add(int index, E e) {
        checkPositionIndex(index);
        if (size == 0) {
            firstNode(e);
        } else if (index == size - 1) {
            lastNode(e);
        } else {
            Node<E> node = new Node<>(e, null);
            //获取要插入索引的前一个元素
            Node<E> preNode = getNode(index - 1);
            node.next = preNode.next;
            preNode.next = node;
            size++;
        }
    }

    private void checkPositionIndex(int index){
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    //获取集合元素
    public E get(int index) {
        checkPositionIndex(index);
        if(index==0){
            return head.ele;
        }
        return getNode(index).ele;
    }

    //根据索引删除元素
    public void remove(int index) {
        checkPositionIndex(index);
        if (size == 1) { //如果数组元素为1,使得头尾节点为空
            head = null;
            rear = null;
        }  else if (index == size - 1) { //删除尾节点
            //获取尾节点的前一个元素
            Node<E> node = getNode(index - 1);
            node.next = head;
            rear = node;
        } else if (index == 0) {
            Node<E> ele = head.next;
            //将head节点 指向 首节点的next
            head.next = ele.next;
            //将原先的head.next置为null,便于垃圾回收
            ele = null;
        }else {
            //获取要删除的前一个元素
            Node<E> preNode = getNode(index - 1);
            Node<E> curNode =preNode.next;
            preNode.next=curNode.next;
        }
        size--;
    }


    /**
     * 根据index获取节点信息，因为构建的是循环链表，所以只能从头开始遍历
     *
     * @param index
     * @return Node<E>
     */
    private Node<E> getNode(int index) {
        Node<E> node=head;
        for (int i = 0; i < index; i++)
            node= node.next;
        return node;
    }

    public void add(E e) {
        lastNode(e);
    }

    private class Node<E> {
        private E ele;
        private Node next;
        public Node() {
            this(null, null);
        }
        public Node(E data, Node next) {
            this.ele = data;
            this.next = next;
        }
    }
}
