package org.algorithms.search;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class NumSquares {
    public int numSquares(int n){
        //优化
        int[] dp = new int[n+1];
        dp[0] = 0;
        for(int i=1;i<=n;i++){
            int min = Integer.MAX_VALUE;
            for(int j=1;j*j<=i;j++){
                min = Math.min(min,dp[i-j*j]);
            }
            dp[i] = min+1;
        }
        return dp[n];


        /*
        int[] dp = new int[n];
        for(int i=0;i<n;i++){
            int ans = (int)Math.pow(i+1,2);
            if(ans<=n)
                dp[ans-1] = 1;
        }
        for(int i=1;i<n;i++){
            if(dp[i]!=0)
                continue;
            Stack<Integer> squares = new Stack<>();
            for(int k=1;k<=i+1;k++){
                if(Math.pow(k,2)<=i+1){
                    squares.add((int)Math.pow(k,2));
                }
            }
            int min = Integer.MAX_VALUE;
            while(!squares.isEmpty()){
                int num = squares.pop();
                int sub = i+1-num;
                if((dp[sub-1]+1)<min)
                    min = dp[sub-1]+1;
            }
            dp[i] = min;
        }
        return dp[n-1];
         */
    }

    @Test
    public void test(){
        System.out.println(numSquares(12));
    }
}
