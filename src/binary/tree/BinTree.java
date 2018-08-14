package binary.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉树
 * Created by jiang on 18/8/14.
 */
public class BinTree {
    private BinTree lChild;
    private BinTree rChild;
    private BinTree root;
    private Object data;
    private List<BinTree> datas;

    public BinTree(BinTree lChild, BinTree rChild, Object data) {
        super();
        this.lChild = lChild;
        this.rChild = rChild;
        this.data = data;
    }

    public BinTree() {
    }

    public BinTree(Object data) {
        this(null, null, data);
    }

    public void createTree(Object[] objs) {
        datas = new ArrayList<BinTree>();
        for (Object object : objs) {
            datas.add(new BinTree(object));
        }
        root = datas.get(0);
        for (int i = 0; i < objs.length / 2; i++) {
            datas.get(i).lChild = datas.get(i * 2 + 1);
            if (i * 2 + 2 < datas.size()) {
                datas.get(i).rChild = datas.get(i * 2 + 2);
            }
        }
    }

    //先序排列
    public void preorder(BinTree root) {
        if (root != null) {
            visit(root.getData());
            preorder(root.lChild);
            preorder(root.rChild);
        }
    }
    //中序排列
    public void inorder(BinTree root){
        if (root!=null){
            inorder(root.lChild);
            visit(root.getData());
            inorder(root.rChild);
        }
    }
    //后序排列
    public void afterorder(BinTree root){
        if (root!=null) {
            afterorder(root.lChild);
            afterorder(root.rChild);
            visit(root.getData());
        }
    }

    private void visit(Object obj){
        System.out.print(obj+" ");
    }
    public Object getData(){
        return data;
    }
    public BinTree getRoot(){
        return root;
    }

    public static void main(String[] args) {
        BinTree binTree = new BinTree();
        Object[] objs = {0,1,2,3,4,5,6,7};
        binTree.createTree(objs);

        System.out.println("first:");
        binTree.preorder(binTree.getRoot());
        System.out.println();
        System.out.println("centre:");
        binTree.inorder(binTree.getRoot());
        System.out.println();
        System.out.println("after:");
        binTree.afterorder(binTree.getRoot());
    }
}
