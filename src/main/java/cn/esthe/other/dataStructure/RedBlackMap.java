package cn.esthe.other.dataStructure;

import cn.esthe.entity.Person;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * 红黑树定义
 * 1,根结点是黑色
 * 2,结点是红色或者黑色
 * 3,叶子结点都是黑色（叶子是null节点）
 * 4,如果一个结点是红的，则该结点的两个儿子结点都是黑色的
 * 5,对每个结点，从该节点到其子孙结点的所有路径上包含相同数目的黑节点
 *
 * @param <K> key
 * @param <V> value
 */
public class RedBlackMap<K, V> {
    public static void main(String[] args) {
        Comparable<Person> p = new Person("xiaoming");
        comparableClassFor(p);

        Person a = new Person("hhh");
        Person b = new Person("hhh");
        System.out.println(a == b);
        System.out.println(System.identityHashCode(a));
        System.out.println(System.identityHashCode(b));

    }

    static int compareComparables(Class<?> kc, Object k, Object x) {
        return (x == null || x.getClass() != kc
                ? 0 : ((Comparable) k).compareTo(x));
    }

    static Class<?> comparableClassFor(Object x) {
        if (x instanceof Comparable) {
            Class<?> c;
            Type[] ts, as;
            Type t;
            ParameterizedType p;
            if ((c = x.getClass()) == String.class) // bypass checks
                return c;
            if ((ts = c.getGenericInterfaces()) != null) {
                for (int i = 0; i < ts.length; ++i) {
                    if (((t = ts[i]) instanceof ParameterizedType) &&
                            ((p = (ParameterizedType) t).getRawType() == Comparable.class) &&
                            (as = p.getActualTypeArguments()) != null &&
                            as.length == 1 &&
                            as[0] == c) // type arg is c
                        return c;
                }
            }
        }
        return null;
    }

    static final class TreeNode<K, V> extends RedBlackMap.Node<K, V> {
        TreeNode<K, V> parent;
        TreeNode<K, V> left;
        TreeNode<K, V> right;
        TreeNode<K, V> prev;
        boolean red;

        TreeNode(int hash, K key, V val, Node<K, V> next) {
            super(hash, key, val, next);
        }

        public TreeNode<K, V> putTreeVal(RedBlackMap<K, V> map, int h, K k, V v) {
            // 获取root节点，遍历root节点
            TreeNode<K, V> root = (parent != null) ? root() : this;
            boolean searched = false;
            Class<?> kc = null;

            for (TreeNode<K, V> p = root; ; ) {
                int dir, ph;
                K pk;
                // 先判断dir的指向，是向左插入还是向右插入
                if ((ph = p.hash) > h) {
                    dir = -1;
                } else if (ph < h) {
                    dir = 1;
                } else if ((pk = p.key) == k || (k != null && k.equals(pk))) {
                    return p;
                }
                // 判断key是否实现了Comparable接口，
                // 决定是否能够基于compareTo比较，
                // 如果仍未比较出来则需要tieBreakOrder方法进行比较
                // 解决hash冲突
                else if ((kc == null && (kc = comparableClassFor(k)) == null) ||
                        ((dir = compareComparables(kc, k, pk)) == 0)) {
                    if (!searched) {
                        searched = true;
                        TreeNode<K, V> q, ch;
                        // 说明从左结点或者右结点能找到该元素，直接返回
                        if (((ch = p.left) != null && (q = ch.find(h, k, kc)) != null) ||
                                ((ch = p.right) != null && (q = ch.find(h, k, kc)) != null)) {
                            return q;
                        }
                    }
                    //在此处比较根据内存地址计算的hash值，
                    // 每次运行是内存地址都不一样，因此值也不一致，
                    // 如果值未空 则返回0
                    dir = (System.identityHashCode(k) <= System.identityHashCode(pk) ? -1 : 1);
                }

                TreeNode<K, V> xp = p;
                if ((p = (dir <= 0) ? p.left : p.right) == null) {
                    Node<K, V> xpn = xp.next;
                    TreeNode<K, V> x = new TreeNode<>(h, k, v, xpn);
                    if (dir < 0) {
                        xp.left = x;
                    } else {
                        xp.right = x;
                    }
                    xp.next = x;
                    x.parent = x.prev = xp;
                    if (xpn != null) {
                        ((TreeNode<K, V>) xpn).prev = x;
                    }

                    balanceInsertion(root, x);
                }
                return null;
            }
        }

        TreeNode<K, V> balanceInsertion(TreeNode<K, V> root, TreeNode<K, V> x) {
            x.red = true;
            for (TreeNode<K, V> xp, xpp, xppl, xppr; ; ) {
                // 如果该结点未root结点，直接返回
                if (((xp = x.parent) == null)) {
                    x.red = false;
                    return x;
                } else if (!xp.red || (xpp = xp.parent) == null) {
                    return root;
                }

                // 从左结点插入
                if (xp == (xppl = xpp.left)) {
                    //xp的父结点不为null且为红色结点，且xp的右结点不为null
                    // 即 左右结点都不为null
                    // 左右结点不为空时，不用左旋或者右旋
                    if ((xppr = xpp.right) != null && xppr.red) {
                        // 将xpp的左右结点 都变为黑色
                        xppr.red = false;
                        xp.red = false; //等价于xppl=false

                        // 将xp的父节点变为红色
                        xpp.red = true;

                        // 下一次循环
                        x = xpp;
                    } else {
                        //从右结点开始插入
                        if (x == xp.right) {
                            rotateLeft(root, x = xp);
                        }
                    }

                }

            }
        }

        static <K, V> TreeNode<K, V> rotateLeft(TreeNode<K, V> root, TreeNode<K, V> p) {
            TreeNode<K, V> r, pp, rl;
            if (p != null && (r = p.right) != null) {
                // 开换 用rl标记，将r的左子树变为p的右子树
                if ((rl = p.right = r.left) != null) {
                    rl.parent = p;
                }

                if ((pp = r.parent = p.parent) == null) {
                    (root = r).red = false;
                } else if (pp.left == p) {
                    pp.left = r;
                }else {
                    pp.right=r;
                }
                r.left=p;
                p.parent=r;
            }
            return null;
        }

        // 查找元素所在位置
        final TreeNode<K, V> find(int h, Object k, Class<?> kc) {
            TreeNode<K, V> p = this;
            do {
                int ph, dir;
                K pk;
                TreeNode<K, V> pl = p.left;
                TreeNode<K, V> pr = p.right;
                TreeNode<K, V> q;
                if ((ph = p.hash) > h) {
                    p = pl;
                } else if (ph < h) {
                    p = pr;
                } else if ((pk = p.key) == k || ((k != null) && k.equals(pk))) {
                    return p;
                } else if (pl == null) {
                    p = pr;
                } else if (pr == null) {
                    p = pl;
                    // 实现Comparable接口，用compare方法比较
                } else if ((kc != null || (kc = comparableClassFor(k)) != null) &&
                        (dir = compareComparables(kc, k, pk)) != 0) {
                    p = (dir < 0) ? pl : pr;
                } else if ((q = pr.find(h, k, kc)) != null) {
                    return q;
                } else {
                    p = pl;
                }
            } while (p != null);
            return null;
        }

        public TreeNode<K, V> root() {
            for (TreeNode<K, V> r = this, p; ; ) {
                if ((p = r.parent) == null) {
                    return r;
                }
                r = p;
            }
        }


    }


    //定义node节点，数组对象
    private static class Node<K, V> {
        final int hash;
        Node<K, V> next;
        final K key;
        V value;

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        // 根据hash值去判定左插 还是右查
        public Node(int hash, K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.hash = hash;
            this.next = next;
        }

        /**
         * ==      基本数据类型,比较的是值;引用数据类型,比较的是内存地址
         * equal   未重写equal方法，相当于== 重写equal，比较的是对象内容
         *
         * @param o
         * @return
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?, ?> that = (Node<?, ?>) o;
            return hash == that.hash && Objects.equals(next, that.next);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }
}

