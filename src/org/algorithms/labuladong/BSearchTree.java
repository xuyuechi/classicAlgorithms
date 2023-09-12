package org.algorithms.labuladong;

import org.algorithms.tree.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

//仗剑篇 2.2二叉搜索树
public class BSearchTree {

    private int pos = -1;
    //leetcode 33 *
    public int search(int[] nums, int target) {
        int len = nums.length;
        if(len==0)
            return -1;
        if(len==1)
            return nums[0] == target?0:-1;
        int end = -1;
        for(int i=0;i<len-1;i++){
            if(nums[i]>nums[i+1])
                end = i;
        }
        if(target<nums[0] || end == -1)
            binarySearch(nums,end+1,len,target);
        else
            binarySearch(nums,0,end+1,target);
        return pos;
    }

    public void binarySearch(int[] nums,int i,int j,int target){
        if(i>=j)
            return;
        int mid = (i+j)/2;
        if(target<nums[mid])
            binarySearch(nums,i,mid,target);
        else if(target>nums[mid])
            binarySearch(nums,mid+1,j,target);
        else{
            pos = mid;
        }
    }

    private int maxSum = 0;

    //leetcode 1373
    public int maxSumBST(TreeNode root){
        traverseSubTree(root);
        return maxSum;
    }

    public int[] traverseSubTree(TreeNode root){
        if(root == null)
            return new int[]{1,Integer.MAX_VALUE,Integer.MIN_VALUE,0};
        int[] left = traverseSubTree(root.left);// [0]记录是不是BST，是就1，不是就0; [1]记录以root为根的二叉树所有节点中的最小值
        //[2]记录以root为根的二叉树所有节点中的最大值 [3]记录以root为根的二叉树的所有节点值的和
        int[] right = traverseSubTree(root.right);
        if(left[0] == 0 || right[0] == 0)
            return new int[]{0,0,0,0};
        if(left[2] >= root.val || right[1] <= root.val)
            return new int[]{0,0,0,0};
        int[] res = new int[4];
        res[0] = 1;
        res[1] = Math.min(left[1],root.val);
        res[2] = Math.max(right[2],root.val);
        res[3] = left[3] + right[3] + root.val;
        maxSum = Math.max(maxSum,res[3]);
        return res;
    }

    //leetcode 95
    public List<TreeNode> generateTrees(int n){
        return traverseGenerate(1,n);
    }

    public List<TreeNode> traverseGenerate(int low,int high){
        List<TreeNode> res = new LinkedList<>();
        if(low > high){
            res.add(null);
            return res;
        }
        for(int i=low;i<=high;i++){
            List<TreeNode> leftList = traverseGenerate(low,i-1);
            List<TreeNode> rightList = traverseGenerate(i+1,high);
            for(TreeNode left:leftList){
                for(TreeNode right:rightList){
                    TreeNode root = new TreeNode(i);
                    root.left = left;
                    root.right = right;
                    res.add(root);
                }
            }
        }
        return res;
    }


    private int[][] memo;

    //leetcode 96
    public int advancedNumTrees(int n){
        memo = new int[n][n];
        return advancedNumTreeCounts(0,n-1);
    }

    public int advancedNumTreeCounts(int low,int high){
        if(low > high)
            return 1;
        if(memo[low][high] != 0)
            return memo[low][high];
        int total = 0;
        for(int i=low;i<=high;i++){
            total += advancedNumTreeCounts(low,i-1) * advancedNumTreeCounts(i+1,high);
        }
        memo[low][high] = total;
        return total;
    }

    //leetcode 96
    public int numTrees(int n){
        ArrayList<Integer> integers = new ArrayList<>(n);
        for(int i=1;i<=n;i++)
            integers.add(i);
        return backTraverse(integers);
    }

    public int backTraverse(List<Integer> choices){
        if(choices.isEmpty())
            return 1;
        int total = 0;
        for(Integer choice:choices){
            TreeNode root = new TreeNode(choice);
            int leftNum = backTraverse(choices.stream().filter(integer -> integer<root.val).collect(Collectors.toList()));
            int rightNum = backTraverse(choices.stream().filter(integer -> integer<root.val).collect(Collectors.toList()));
            total += (leftNum*rightNum);
        }
        return total;
    }

    //leetcode 450
    public TreeNode deleteNode(TreeNode root,int key){
        if(root == null)
            return null;
        if(root.val == key){
           if(root.left == null) return root.right;
           if(root.right == null) return root.left;
           TreeNode minNode = getMin(root.right);
           root.right = deleteNode(root.right,minNode.val);
           minNode.left = root.left;
           minNode.right = root.right;
           root = minNode;
        }
        else if(root.val > key)
            root.left = deleteNode(root.left,key);
        else
            root.right = deleteNode(root.right,key);
        return root;
    }

    public TreeNode getMin(TreeNode node){
        while(node.left!=null) node = node.left;
        return node;
    }

    //leetcode 701
    public TreeNode insertIntoBST(TreeNode root,int val){
        if(root==null){
            return new TreeNode(val);
        }
        insertBST(root, val);
        return root;
    }

    public void insertBST(TreeNode root,int val){
        if(root.left == null && val < root.val){
            root.left = new TreeNode(val);
        }
        else if(root.right == null && val > root.val){
            root.right = new TreeNode(val);
        }
        else if(root.val > val)
            insertBST(root.left,val);
        else if(root.val < val)
            insertBST(root.right,val);
    }

    //leetcode 700
    public TreeNode searchBST(TreeNode root,int val){
        if(root == null)
            return null;
        if(root.val<val)
            return searchBST(root.right,val);
        else if(root.val>val)
            return searchBST(root.left,val);
        else
            return root;
    }

    //leetcode 98
    public boolean isValidBST(TreeNode root){
        return isValidBST(root,null,null);
    }

    public boolean isValidBST(TreeNode root,TreeNode min,TreeNode max){
        if(root==null)
            return true;
        if(min!=null && root.val <= min.val)return false;
        if(max!=null && root.val >= max.val)return false;
        return isValidBST(root.left,min,root) && isValidBST(root.right,root,max);
    }
    private int sum;
    //leetcode 538
    public TreeNode convertBST(TreeNode root){
        if(root == null)
            return null;
        TreeNode right = convertBST(root.right);
        sum += root.val;
        TreeNode newRoot = new TreeNode(sum);
        newRoot.right = right;
        TreeNode left = convertBST(root.left);
        newRoot.left = left;
        return newRoot;
    }

    private int res;
    private int rank;

    //leetcode 230
    public int kthSmallest(TreeNode root, int k){
        traverse(root,k);
        return res;
    }

    public void traverse(TreeNode root,int k){
        if(root==null)
            return;
        traverse(root.left,k);
        rank++;
        if(rank == k){
            res = root.val;
            return;
        }
        traverse(root.right,k);
    }
}
