package org.algorithms.labuladong;

import org.junit.Test;

import java.util.*;

public class BackTrack {

    public int countArr = 0;

    //leetcode 526
    public int countArrangement(int n){
        boolean[] used = new boolean[n];
        Arrays.fill(used,false);
        backTrackCount(new LinkedList<>(),used);
        return countArr;
    }

    public void backTrackCount(LinkedList<Integer> track,boolean[] used){
        int size = track.size();
        if(size>0){
            if(track.get(size-1) % size!=0 && size % track.get(size-1)!=0)
                return;
        }
        if(track.size() == used.length){
            countArr++;
            return;
        }
        for(int i=0;i<used.length;i++){
            if(used[i])
                continue;
            track.add(i+1);
            used[i] = true;
            backTrackCount(track,used);
            track.removeLast();
            used[i] = false;
        }
    }

    @Test
    public void testForCount(){
        System.out.println(countArrangement(2));
    }

    List<String> result17 = new LinkedList<>();

    //leetcode 17
    public List<String> letterCombinations(String digits){
        if(digits.length()==0)
            return result17;
        HashMap<Character,char[]> keyboard = new HashMap<>();
        keyboard.put('2',new char[]{'a','b','c'});
        keyboard.put('3',new char[]{'d','e','f'});
        keyboard.put('4',new char[]{'g','h','i'});
        keyboard.put('5',new char[]{'j','k','l'});
        keyboard.put('6',new char[]{'m','n','o'});
        keyboard.put('7',new char[]{'p','q','r','s'});
        keyboard.put('8',new char[]{'t','u','v'});
        keyboard.put('9',new char[]{'w','x','y','z'});
        int size = digits.length();
        backTrackCombine(0,size,digits,keyboard,new StringBuilder());
        return result17;
    }

    public void backTrackCombine(int index,int size,String digits,HashMap<Character,char[]> keyboard,StringBuilder track){
        if(track.length()==size){
            result17.add(track.toString());
            return;
        }
        char[] chars = keyboard.get(digits.charAt(index));
        for(int i=0;i<chars.length;i++){
            track.append(chars[i]);
            backTrackCombine(index+1,size,digits,keyboard,track);
            track.deleteCharAt(track.length()-1);
        }
    }

    public int totalCount;

    //leetcode 494
    public int findTargetSumWays(int[] nums,int target){
        backTrackTargetSum(0,0,target,nums);
        return totalCount;
    }

    public void backTrackTargetSum(int index,int sum,int target,int[] nums){
        if(index==nums.length){
            if(sum == target)
                totalCount++;
            return;
        }

        sum+=nums[index];
        backTrackTargetSum(index+1,sum,target,nums);
        sum-=nums[index];

        sum-=nums[index];
        backTrackTargetSum(index+1,sum,target,nums);
        sum+=nums[index];
    }

    //leetcode 131
//    List<List<String>> result131 = new LinkedList<>();
//    public List<List<String>> partition(String s){
//        StringBuilder str = new StringBuilder(s);
//    }
//
//    public void backTrackForPartition(boolean[] cuts,List<StringBuilder> part){
//        for(int i=0;i<cuts.length;i++){
//            if(cuts[i])
//
//        }
//    }
//
//    public boolean isPalindrome(List<StringBuilder> sb){
//        for(StringBuilder s:sb) {
//            StringBuilder bs = new StringBuilder(s);
//            if (bs.reverse().toString().equals(s.toString()))
//                return true;
//            else
//                return false;
//        }
//    }

    List<List<Integer>> result78 = new LinkedList<>();

    //leetcode 78
    public List<List<Integer>> subsets(int[] nums){
        LinkedList<Integer> track = new LinkedList<>();
        backTrackSubsets(0,track,nums);
        return result78;
    }

    public void backTrackSubsets(int start,LinkedList<Integer> track,int[] nums){
        result78.add(new LinkedList<>(track));
        for(int i=start;i<nums.length;i++){
            track.add(nums[i]);
            backTrackSubsets(i+1,track,nums);
            track.removeLast();
        }
    }

    @Test
    public void testForSubsets(){
        System.out.println(subsetXORSum(new int[]{1,3}));
    }


    //leetcode 1863
    public int subsetXORSum(int[] nums){
        subsets(nums);
        int res = 0;
        for(List<Integer> r:result78){
            if(r.size()==0)
                continue;
            int xor = 0;
            for(int i=0;i<r.size();i++){
                xor = r.get(i) ^ xor;
            }
            res += xor;
        }
        return res;
    }

    List<List<Integer>> result77 = new LinkedList<>();
    //leetcode 77
    public List<List<Integer>> combine(int n,int k){
        LinkedList<Integer> track = new LinkedList<>();
        backTrackCombine(1,k,n,track);
        return result77;
    }

    public void backTrackCombine(int start,int k,int num,LinkedList<Integer> track){
        if(track.size()==k){
            result77.add(new LinkedList<>(track));
            return;
        }

        for(int i=start;i<=num;i++){
            track.add(i);
            backTrackCombine(i+1,k,num,track);
            track.removeLast();
        }
    }

    List<String> answerForPar = new LinkedList<>();

    //leetcode 22
    public List<String> generateParenthesis(int n) {
        LinkedList<String> res = new LinkedList<>();
        backTrackForParenthesis(res, n, n, 0);
        return answerForPar;
    }

    public void backTrackForParenthesis(LinkedList<String> res, int left, int right, int leftCanUsed) {
        if (left == 0 && right == 0) {
            StringBuilder sb = new StringBuilder();
            for (String s : res) {
                sb.append(s);
            }
            answerForPar.add(sb.toString());
        }

        if (left > 0) {
            res.addLast("(");
            backTrackForParenthesis(res, left-1, right, leftCanUsed+1);
            res.removeLast();
        }
        if (right > 0 && leftCanUsed > 0) {
            res.add(")");
            backTrackForParenthesis(res, left, right-1, leftCanUsed-1);
            res.removeLast();
        }
    }

    @Test
    public void testForPar(){
        System.out.println(generateParenthesis(1));
    }

    public List<List<String>> res = new LinkedList<>();

    //leetcode 51
    public List<List<String>> solveNQueens(int n) {
        char[][] board = new char[n][n];
        for (char[] b : board) {
            Arrays.fill(b, '.');
        }
        backTrackQueens(board, 0, n);
        return res;
    }

    public void backTrackQueens(char[][] board, int row, int n) {
        if (row == n) {
            LinkedList<String> r = new LinkedList<>();
            for (int i = 0; i < n; i++) {
                String s = new String(board[i]);
                r.add(s);
            }
            res.add(r);
            return;
        }

        for (int i = 0; i < n; i++) {
            if (isVaild(board, row, i, n)) {
                board[row][i] = 'Q';
                row++;
                backTrackQueens(board, row, n);
                row--;
                board[row][i] = '.';
            }
        }
    }

    public boolean isVaild(char[][] board, int row, int col, int n) {
        //检查同一列
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 'Q')
                return false;
        }

        //检查左上方
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q')
                return false;
        }

        //检查右上方
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (board[i][j] == 'Q')
                return false;
        }
        return true;
    }

    @Test
    public void testForQueens() {
        List<List<String>> lists = solveNQueens(1);
        for (List list : lists) {
            System.out.println(list);
        }
    }


    public List<List<Integer>> result = new LinkedList<>();

    //leetcode 46
    public List<List<Integer>> permute(int[] nums) {
        LinkedList<Integer> track = new LinkedList<>();
        boolean[] used = new boolean[nums.length];
        backTrackNums(nums, track, used);
        return result;
    }

    public void backTrackNums(int[] nums, LinkedList<Integer> track, boolean[] used) {
        if (track.size() == nums.length) {
            result.add(new LinkedList<>(track));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i])
                continue;
            track.add(nums[i]);
            used[i] = true;
            backTrackNums(nums, track, used);
            track.removeLast();
            used[i] = false;
        }
    }
}
