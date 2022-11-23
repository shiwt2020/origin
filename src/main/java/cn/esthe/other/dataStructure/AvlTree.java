package cn.esthe.other.dataStructure;

/**
 * avl树 --左右子树的高度差不能超过1
 * <? extends E>  E的子类
 * <? super E>  E的父类
 *
 * @param <E>
 */
public class AvlTree<E extends Comparable<? super E>> {

    private AvlTreeNode<E> root;

    private static class AvlTreeNode<E> {
        //要插入的元素
        E ele;
        // 左节点
        AvlTreeNode<E> left;
        // 右节点
        AvlTreeNode<E> right;
        //树的高度，从该节点到树叶节点的最大长度
        int height;

        public AvlTreeNode(E ele, AvlTreeNode<E> right, AvlTreeNode<E> left) {
            this.ele = ele;
            this.right = right;
            this.left = left;
        }
    }

    //定义树的绝对高度
    private final static int ALLOWED_IMBALANCE = 1;

    // 插入元素
    public void insert(E e) {
        root = insert(e, root);
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void printTree() {
        if (isEmpty())
            System.out.println("Empty tree");
        else
            printTree(root);
    }

    private void printTree(AvlTreeNode<E> t) {
        if (t != null) {
            printTree(t.left);
            System.out.println(t.ele);
            printTree(t.right);
        }
    }

    // 益处元素
    public void remove(E e) {
        //需遍历，然后平衡树的结构、
        root = remove(e, root);
    }

    //删除节点
    private AvlTreeNode<E> remove(E e, AvlTreeNode<E> node) {
        //根节点为null,说明avl树无元素，直接返回null
        if (node == null) {
            return null;
        }
        int compare = e.compareTo(node.ele);
        if (compare > 0) {
            node.right = remove(e, node.right);
        } else if (compare < 0) {
            node.left = remove(e, node.left);
        } else if (node.left != null && node.right != null) { //删除节点有两个children
            // 这里是我抄的书本，作者设计的很牛，代码如下
            // 首先获取最小节点，将最小节点赋值给当前节点
            node.ele = findMin(node.right).ele;
            //将右children节点中的ele删除
            node.right = remove(node.ele, node.right);
        } else { // 删除的节点仅有一个children
            node = node.left == null ? node.right : node.left;
        }
        return balance(node);
    }

    private AvlTreeNode<E> findMin(AvlTreeNode<E> node) {
        if (node == null) {
            return null;
        }

        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * 插入元素
     * 分为4种情况：左左，右右，左右，右左
     * 1,从左儿子的左节点插入数据 -- 右旋
     * 2,从右儿子的右节点插入数据 -- 左旋
     * 3,从左儿子的右节点插入数据 -- 左旋，右旋
     * 4,从右儿子的左节点插入数据 -- 右旋，左旋
     * <p>
     * 思路：以递归的形式插入数据，然后调整树的结构
     */
    private AvlTreeNode<E> insert(E e, AvlTreeNode<E> node) {
        //插入第一个元素时，根节点为null
        if (node == null)
            return new AvlTreeNode<>(e, null, null);

        //比较插入的数据
        int i = e.compareTo(node.ele);
        if (i > 0) {
            node.right = insert(e, node.right);
        } else if (i < 0) {
            node.left = insert(e, node.left);
        } else {
            // 元素相同，不做处理
        }
        return balance(node);
    }

    /**
     * 用于平衡树的结构
     * 基础知识：相对的儿子节点变为相对的root节点，左旋，即是逆时针旋转；右旋，即是顺时针旋
     *
     * @param node 根据节点信息平衡树结构
     * @return
     */
    private AvlTreeNode<E> balance(AvlTreeNode<E> node) {
        // 删除：当节点为叶子节点时，直接返回null
        if(node==null){
            return null;
        }

        //首先判断当前节点的左节点与右节点的差值大于1
        if (height(node.left) - height(node.right) > ALLOWED_IMBALANCE) {
            // 从左儿子的左节点插入数据，进行单旋转便可
            if (height(node.left.left) >= height(node.left.right)) {
                node = singRightRotate(node);
            } else {
                node = doubleLeftRotate(node);
            }
        } else if (height(node.right) - height(node.left) > ALLOWED_IMBALANCE) {
            // 从右儿子的右节点插入数据,进行单旋转便可
            if (height(node.right.right) >= height(node.right.left)) {
                node = singLeftRotate(node);
            } else {
                node = doubleRightRotate(node);
            }
        }
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        return node;
    }


    // 右旋：从左儿子的左节点插入数据,只有k1、k2两个节点的高度存在变动，
    private AvlTreeNode<E> singRightRotate(AvlTreeNode<E> k2) {
        AvlTreeNode<E> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        return k1;
    }

    // 左旋：从右儿子的右节点插入数据,只有k1、k2两个节点的高度存在变动
    private AvlTreeNode<E> singLeftRotate(AvlTreeNode<E> k1) {
        AvlTreeNode<E> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        return k2;
    }

    //3,从左儿子的右节点插入数据
    private AvlTreeNode<E> doubleLeftRotate(AvlTreeNode<E> node) {
        //先左旋：即node.left.right
        node.left = singLeftRotate(node.left);

        //再右旋：即node.left.right
        return singRightRotate(node);
    }

    // 4,从右儿子的左节点插入数据
    private AvlTreeNode<E> doubleRightRotate(AvlTreeNode<E> node) {
        //先右旋
        node.right = singRightRotate(node.right);
        return singLeftRotate(node);
    }

    //获取当前节点树的高度
    private int height(AvlTreeNode<E> node) {
        return node == null ? -1 : node.height;
    }

}
