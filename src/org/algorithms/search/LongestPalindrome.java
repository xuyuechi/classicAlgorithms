package org.algorithms.search;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LongestPalindrome {

    //leetcode 118 杨辉三角
    public List<List<Integer>> generate(int numRows){
        List<List<Integer>> ans = new LinkedList<>();
        List<Integer> init = new LinkedList<>();
        init.add(1);
        ans.add(init);
        List<Integer> lastOne = init;
        for(int i=2;i<=numRows;i++){
            List<Integer> thisOne = new LinkedList<>();
            thisOne.add(1);
            for(int j=1;j<i-1;j++){
                Integer left = lastOne.get(j - 1);
                Integer right = lastOne.get(j);
                thisOne.add(left+right);
            }
            thisOne.add(1);
            lastOne = thisOne;
            ans.add(thisOne);
        }
        return ans;
    }
    @Test
    public void testFor118(){
        List<List<Integer>> generate = generate(30);
        for(List<Integer> i:generate){
            System.out.println(i);
        }
    }

    //leetcode 121
    public int maxProfit(int[] prices){
        if(prices.length==0)
            return 0;
        int[] dp = new int[prices.length];
        dp[0] = 0;
        int minPrice = prices[0];
        for(int i=1;i<prices.length;i++){
            if(prices[i]<minPrice){
                minPrice = prices[i];
            }
            dp[i] = Math.max(prices[i]-minPrice,dp[i-1]);
        }
        System.out.println(Arrays.toString(dp));
        return dp[prices.length-1];
    }

    @Test
    public void testForProfit(){
        System.out.println(maxProfit(new int[]{7,1,5,3,6,4}));
    }

    //leetcode 343
    public int integerBreak(int n){
        int[] dp = new int[n+1];
        dp[0] = -1;
        dp[1] = -1;
        dp[2] = 1;
        for(int i=3;i<=n;i++){
            int max = Integer.MIN_VALUE;
            for(int j=i-1;j>=2;j--){
                int multi = Math.max((i-j)*j,(i-j)*dp[j]);
                if(multi>max)
                    max = multi;
            }
            dp[i] = max;
        }
        return dp[n];
    }
    @Test
    public void testForInteger(){
        System.out.println(integerBreak(58));
        System.out.println(Integer.MAX_VALUE);
    }

    public String longestPalindrome(String s){
        int len = s.length();
        int[][] work = new int[len][len];
        for(int i=0;i<len;i++){
            work[i][i] = 1;
        }
        for(int i=1;i<len;i++) {
            if (s.charAt(i - 1) == s.charAt(i)) {
                work[i - 1][i] = 2;
            } else {
                work[i - 1][i] = -1;
            }
        }
        for(int k=3;k<=len;k++){
            for(int i=0;i<=len-k;i++){
                if(work[i+1][i+k-2] != -1 && s.charAt(i)==s.charAt(i+k-1)){
                    work[i][i+k-1] = k;
                }else{
                    work[i][i+k-1] = -1;
                }
            }
        }
        int max = 0;
        int end = -1;
        int begin = -1;
        for(int i=0;i<len;i++){
            for(int j=i;j<len;j++){
                if(work[i][j]>max){
                    max = work[i][j];
                    end = j;
                    begin = i;
                }
            }
        }
        if(begin==-1 && end==-1)
            return "";
        else
            return s.substring(begin,end+1);
    }

    @Test
    public void test(){
        System.out.println(longestPalindrome("abcbe"));
    }
}
