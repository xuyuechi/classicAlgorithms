package org.algorithms.tree;

import java.util.Collections;
import java.util.List;

public class BiggestBinaryTree {
    public TreeNode buildTree(List<Integer> list){
        TreeNode root = new TreeNode();
        Integer max = Collections.max(list);
        root.val = max;
        List<Integer> leftList = list.subList(0, list.indexOf(max));
        List<Integer> rightList = list.subList(list.indexOf(max)+1, list.size());
        if(leftList.size()>0)
            root.left = buildTree(leftList);
        if(rightList.size()>0)
            root.right = buildTree(rightList);
        return root;
    }

    public void preorder(TreeNode node){
        System.out.print(node.val +" ");
        if(node.left==null && node.right==null)
            return;
        if(node.left!=null)
            preorder(node.left);
        else
            System.out.print("null ");
        if(node.right!=null)
            preorder(node.right);
        else
            System.out.print("null ");
    }

}
