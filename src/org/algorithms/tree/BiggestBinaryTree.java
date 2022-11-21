package org.algorithms.tree;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class BiggestBinaryTree {
    public TreeNode buildTree(List<Integer> list){
        TreeNode root = new TreeNode();
        Integer max = Collections.max(list);
        root.setValue(max);
        List<Integer> leftList = list.subList(0, list.indexOf(max));
        List<Integer> rightList = list.subList(list.indexOf(max)+1, list.size());
        if(leftList.size()>0)
            root.setLeft(buildTree(leftList));
        if(rightList.size()>0)
            root.setRight(buildTree(rightList));
        return root;
    }

    public void preorder(TreeNode node){
        System.out.print(node.value+" ");
        if(node.getLeft()==null && node.getRight()==null)
            return;
        if(node.getLeft()!=null)
            preorder(node.getLeft());
        else
            System.out.print("null ");
        if(node.getRight()!=null)
            preorder(node.getRight());
        else
            System.out.print("null ");
    }
    public class TreeNode {
        private int value;
        private TreeNode left;
        private TreeNode right;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public TreeNode getLeft() {
            return left;
        }

        public void setLeft(TreeNode left) {
            this.left = left;
        }

        public TreeNode getRight() {
            return right;
        }

        public void setRight(TreeNode right) {
            this.right = right;
        }

        public TreeNode(int value, TreeNode left, TreeNode right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public TreeNode() {
        }
    }
}
