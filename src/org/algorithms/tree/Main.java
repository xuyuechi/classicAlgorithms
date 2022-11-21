package org.algorithms.tree;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = scanner.nextInt();
        int[] data = new int[count];
        for(int i=0;i<count;i++){
            data[i] = scanner.nextInt();
        }
        scanner.close();
        System.out.println(dpSubArray(data));
    }
    public static int dpSubArray(int[] nums){
        int size = nums.length;
        int[] dp = new int[size];
        dp[0] = nums[0];
        for(int i=1;i<size;i++){
            dp[i] = Math.max(dp[i-1] + nums[i],nums[i]);
        }
        int max = Integer.MIN_VALUE;
        for(int i=0;i<size;i++){
            if(dp[i]>max)
                max = dp[i];
        }
        return max;
    }
}
