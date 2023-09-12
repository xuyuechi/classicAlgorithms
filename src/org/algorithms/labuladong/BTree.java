package org.algorithms.labuladong;

import org.algorithms.tree.TreeNode;
import org.junit.Test;

import java.util.*;

//仗剑篇 2.1二叉树
public class BTree {

    @Test
    public void testZig(){
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        zigzagLevelOrder(root);
    }

    //leetcode 103
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        LinkedList<List<Integer>> result = new LinkedList<>();
        if(root==null)
            return result;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        boolean leftToRight = true;
        while(!queue.isEmpty()){
            int size = queue.size();
            LinkedList<Integer> temp = new LinkedList<>();
            for(int i=0;i<size;i++){
                TreeNode poll = queue.poll();
                if(leftToRight)
                    temp.addLast(poll.val);
                else
                    temp.addFirst(poll.val);
                if(poll.left!=null)
                    queue.offer(poll.left);
                if(poll.right!=null)
                    queue.offer(poll.right);
            }
            result.add(temp);
            leftToRight = !leftToRight;
        }
        return result;
    }

//    public List<Integer> countSmaller(int[] nums){
//
//    }

    private static int[] temp;

    //leetcode 912
    public int[] sortArray(int[] nums){
        temp = new int[nums.length];
        mergeSort(nums,0,nums.length-1);
        return nums;
    }

    public void mergeSort(int[] nums,int low,int high){
        if(low == high)
            return;
        int mid = low + (high - low)/2;
        mergeSort(nums,low,mid);
        mergeSort(nums,mid+1,high);
        merge(nums,low,mid,high);
    }

    public void merge(int[] nums,int low,int mid,int high){
        for(int i=low;i<=high;i++){
            temp[i] = nums[i];
        }
        int i = low,j = mid+1;
        for(int p=low;p<=high;p++){
            if(i == mid+1)
                nums[p] = temp[j++];
            else if(j == high+1)
                nums[p] = temp[i++];
            else if(temp[i]<temp[j])
                nums[p] = temp[i++];
            else
                nums[p] = temp[j++];
        }
    }

    private static final String SEP = ",";
    private static final String NULL = "#";
    private static StringBuilder sb;

    private HashMap<String,Integer> memo;

    private LinkedList<TreeNode> res;

    //leetcode 652
    public List<TreeNode> findDuplicateSubtrees(TreeNode root){
        memo = new HashMap<>();
        res = new LinkedList<>();
        traverseToFindDuplicate(root);
        return res;
    }

    public String traverseToFindDuplicate(TreeNode root){
        if(root == null)
            return NULL;
        String left = traverseToFindDuplicate(root.left);
        String right = traverseToFindDuplicate(root.right);
        StringBuilder sb = new StringBuilder();
        sb.append(root.val).append(SEP);
        sb.append(left);
        sb.append(right);
        String key = sb.toString();
        if(memo.containsKey(key))
            memo.put(key,memo.get(key)+1);
        else
            memo.put(key,1);

        if(memo.get(key) == 2)
            res.add(root);
        return sb.toString();
    }

    //leetcode 297
    public String serialize(TreeNode root){
        sb = new StringBuilder();
        traverse(root);
        return sb.toString().substring(0,sb.length()-1);
    }

    public void traverse(TreeNode root){
        if(root == null){
            sb.append(NULL);
            sb.append(SEP);
            return;
        }
        sb.append(root.val);
        sb.append(SEP);
        traverse(root.left);
        traverse(root.right);
    }

    private int index = 0;

    public TreeNode deserialize(String data){
        TreeNode root = traverseBuildTree(data.split(SEP));
        return root;
    }

    public TreeNode traverseBuildTree(String[] data){
        if(data[index].equals("#")){
            return null;
        }
        TreeNode root = new TreeNode(Integer.parseInt(data[index]));
        index++;
        root.left = traverseBuildTree(data);
        index++;
        root.right = traverseBuildTree(data);
        return root;
    }

    //leetcode 889
    public TreeNode constructFromPrePost(int[] preorder,int[] postorder){
        return buildByPrePost(preorder,0,preorder.length-1,postorder,0,postorder.length-1);
    }

    public TreeNode buildByPrePost(int[] preorder,int preLow,int preHigh,int[] postorder,int postLow,int postHigh){
        if(preLow > preHigh)
            return null;
        if(preLow == preHigh)
            return new TreeNode(preorder[preLow]);
        TreeNode root = new TreeNode(preorder[preLow]);
        int leftRoot = preorder[preLow+1];
        int leftRootIndex = -1;
        for (int i = postLow; i <= postHigh; i++) {
            if(leftRoot == postorder[i])
                leftRootIndex = i;
        }
        int leftSize = leftRootIndex - postLow;
        root.left = buildByPrePost(preorder,preLow+1,preLow + leftSize+1,postorder,postLow,postLow+leftSize);
        root.right = buildByPrePost(preorder,preLow+1+leftSize+1,preHigh,postorder,postLow+leftSize+1,postHigh-1);
        return root;
    }


    //leetcode 106
    public TreeNode buildTreePostorder(int[] inorder, int[] postorder) {
        return buildByMidAndPost(postorder,postorder.length-1,inorder,0,inorder.length-1);
    }

    public TreeNode buildByMidAndPost(int[] postorder, int postHigh, int[] inorder, int inLow, int inHigh) {
        if (inLow > inHigh)
            return null;
        TreeNode root = new TreeNode(postorder[postHigh]);
        int rootIndex = -1;
        for (int i = inLow; i <= inHigh; i++) {
            if (root.val == inorder[i])
                rootIndex = i;
        }
        int rightSize = inHigh - rootIndex;
        root.left = buildByMidAndPost(postorder, postHigh - rightSize - 1, inorder, inLow, inHigh - rightSize - 1);
        root.right = buildByMidAndPost(postorder, postHigh - 1, inorder, rootIndex+1, inHigh);
        return root;
    }

    //leetcode 105
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildByPreAndMid(preorder, 0, inorder, 0, inorder.length - 1);
    }

    public TreeNode buildByPreAndMid(int[] preorder, int preLow, int[] inorder, int inLow, int inHigh) {
        if (inLow > inHigh)
            return null;
        TreeNode root = new TreeNode(preorder[preLow]);
        int rootIndex = -1;
        for (int i = inLow; i <= inHigh; i++) {
            if (root.val == inorder[i]) {
                rootIndex = i;
            }
        }

        int leftSize = rootIndex - inLow;

        root.left = buildByPreAndMid(preorder, preLow + 1, inorder, inLow, rootIndex - 1);
        root.right = buildByPreAndMid(preorder, preLow + leftSize + 1, inorder, rootIndex + 1, inHigh);
        return root;
    }

    //leetcode 654
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return buildTree(nums, 0, nums.length - 1);
    }

    public TreeNode buildTree(int[] nums, int low, int high) {
        if (low > high)
            return null;
        int max = Integer.MIN_VALUE;
        int index = -1;
        for (int i = low; i <= high; i++) {
            if (nums[i] > max) {
                max = nums[i];
                index = i;
            }
        }
        TreeNode root = new TreeNode(max);
        root.left = buildTree(nums, low, index - 1);
        root.right = buildTree(nums, index + 1, high);
        return root;
    }

    //leetcode 114
    public TreeNode flatten(TreeNode root) {
        if (root == null)
            return null;
        TreeNode leftRoot = flatten(root.left);
        TreeNode rightRoot = flatten(root.right);
        if (leftRoot == null) {
            return root;
        }
        TreeNode p = leftRoot;
        while (p.right != null) {
            p = p.right;
        }
        p.right = rightRoot;
        root.left = null;
        root.right = leftRoot;
        return root;
    }

    //leetcode 116
    public NodeNext connect(NodeNext root) {
        if (root == null)
            return null;
        if (root.left != null) {
            root.left.next = root.right;
        }
        conn(root.left);
        conn(root.right);
        return root;
    }


    public void conn(NodeNext root) {
        if (root == null)
            return;
        if (root.left != null) {
            root.left.next = root.right;
            if (root.next != null)
                root.right.next = root.next.left;
        }
        conn(root.left);
        conn(root.right);
    }

    //leetcode 226
    public TreeNode invertTree(TreeNode root) {
        if (root == null)
            return null;
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        invertTree(root.left);
        invertTree(root.right);
        return root;
    }

    //leetcode 559
    public int maxDepth(MultiNode root) {
        if (root == null)
            return 0;
        int childDepth = 0;
        for (MultiNode child : root.children) {
            childDepth = Math.max(maxDepth(child), childDepth);
        }
        return childDepth + 1;
    }

    public int biggestLen = 0;

    //leetcode 102
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null)
            return new LinkedList<>();
        LinkedList<List<Integer>> ans = new LinkedList<>();
        Queue<TreeNode> work = new LinkedList<>();
        Queue<TreeNode> nextLevel = new LinkedList<>();
        work.add(root);
        while (!work.isEmpty()) {
            nextLevel.clear();
            LinkedList<Integer> level = new LinkedList<>();
            while (!work.isEmpty()) {
                TreeNode parent = work.poll();
                level.add(parent.val);
                if (parent.left != null)
                    nextLevel.add(parent.left);
                if (parent.right != null)
                    nextLevel.add(parent.right);
            }
            ans.add(level);
            work.addAll(nextLevel);
        }
        return ans;
    }

    //leetcode 543
    public int diameterOfBinaryTree(TreeNode root) {
        dofB(root);
        return biggestLen;
    }

    public int dofB(TreeNode root) {
        if (root == null)
            return 0;
        int leftLen = dofB(root.left);
        int rightLen = dofB(root.right);
        int totalLen = leftLen + rightLen;
        if (totalLen > biggestLen)
            biggestLen = totalLen;
        return Math.max(leftLen, rightLen) + 1;
    }

    //leetcode 104
    public int maxDepth(TreeNode root) {
        if (root == null)
            return 0;
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        return Math.max(leftDepth, rightDepth) + 1;
    }

    //leetcode 144
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new LinkedList<>();
        if (root == null) {
            return res;
        }
        res.add(root.val);
        res.addAll(preorderTraversal(root.left));
        res.addAll(preorderTraversal(root.right));
        return res;
    }
}
