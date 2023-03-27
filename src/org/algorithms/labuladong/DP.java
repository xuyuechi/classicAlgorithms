package org.algorithms.labuladong;

import java.util.Arrays;

//悟剑篇 4.1 动态规划核心原理
public class DP {

    private int[] memo;

    //leetcode 322
    public int coinChangeUpToDown(int[] coins,int amount) {
        memo = new int[amount+1];
        Arrays.fill(memo,-888);
        memo[0] = 0;
        return recursionToCoins(coins,amount);
    }

    public int recursionToCoins(int[] coins,int amount){
        if(amount == 0)return 0;
        if(amount < 0) return -1;
        if(memo[amount]!=-888)
            return memo[amount];
        int res = Integer.MAX_VALUE;
        for(int j=0;j<coins.length;j++){
            int subProblem = recursionToCoins(coins,amount-coins[j]);
            if(subProblem==-1)
                continue;
            res = Math.min(res,subProblem+1);
        }
        memo[amount] = res==Integer.MAX_VALUE?-1:res;
        return memo[amount];
    }

    //leetcode 322
    public int coinChange(int[] coins,int amount){
        int[] dp = new int[amount+1];
        Arrays.fill(dp,amount+1);
        dp[0] = 0;
        for(int i=1;i<=amount;i++){
            for(int j=0;j<coins.length;j++){
                if(i-coins[j]>=0)
                    dp[i] = Math.min(dp[i-coins[j]]+1,dp[i]);
            }
        }
        return dp[amount] == amount+1?-1:dp[amount];
    }

    //leetcode 509
    public int dpFib(int n){
        if(n==0||n==1)
            return n;
        int[] memo = new int[n+1];
        memo[0] = 0;
        memo[1] = 1;
        for(int i=2;i<=n;i++){
            memo[i] = memo[i-1] + memo[i-2];
        }
        return memo[n];
    }

    //leetcode 509
    public int advancedFib(int n){
        int[] memo = new int[n+1];
        return cal(n,memo);
    }

    public int cal(int i,int[] memo){
        if(i==0 || i==1)
            return i;
        else{
            if(memo[i]!=0)
                return memo[i];
            else
                return memo[i] = cal(i-1,memo) + cal(i-2,memo);
        }
    }

    //leetcode 509
    public int fib(int n){
        if(n==0 || n==1)
            return n;
        else
            return fib(n-1) + fib(n-2);
    }
}
