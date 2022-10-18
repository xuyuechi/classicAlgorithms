package org.algorithms.tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String s = null;
        try {
            s = bf.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] s1 = s.split(" ");
        List<Integer> data = new ArrayList<>();
        for(String s2:s1){
            data.add(Integer.parseInt(s2));
        }
        TreeNode root = buildTree(data);
        StringBuffer sb = preorder(root,new StringBuffer());
        System.out.println(sb.toString().trim());
    }
    public static TreeNode buildTree(List<Integer> list){
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

    public static StringBuffer preorder(TreeNode node,StringBuffer sb){
        sb.append(node.getValue()+" ");
        if(node.getLeft()==null && node.getRight()==null){
            return sb;
        }
        if(node.getLeft() == null){
            sb.append("null ");
        }else{
            preorder(node.getLeft(),sb);
        }
        if(node.getRight() == null){
            sb.append("null ");
        }else{
            preorder(node.getRight(),sb);
        }
        return sb;
    }
    public static class TreeNode {
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
