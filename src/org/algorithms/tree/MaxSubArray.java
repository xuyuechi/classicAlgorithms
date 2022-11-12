package org.algorithms.tree;

import org.junit.Test;

public class MaxSubArray {
    public int maxSubArray(int[] nums){
        return advancedMaxSubArray(nums,0,nums.length-1);
    }
    //动态规划
    public int dpSubArray(int[] nums){
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
    public int sumAllSubArray(int[] nums){
        int size = nums.length;
        int[][] work = new int[size][size];
        for(int i=0;i<size;i++){
            work[i][i] = nums[i];
        }
        for(int k=2;k<=size;k++){
            for(int i=0;i<=size-k;i++){
                    work[i][i+k-1] = work[i][i+k-2] + nums[i+k-1];
            }
        }
        int max = Integer.MIN_VALUE;
        for(int i=0;i<size;i++){
            for(int j=i;j<size;j++){
                if(work[i][j]>max){
                    max = work[i][j];
                }
            }
        }
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                System.out.print(work[i][j] + ",");
            }
            System.out.println();
        }
        return max;
    }

    //分治法
    public int advancedMaxSubArray(int[] nums,int begin,int end){
        if(begin==end)
            return nums[begin];
        int sum = 0;
        for(int i=begin;i<=end;i++){
            sum+=nums[i];
        }
        int mid = (begin+end)/2;
        int centerSum = countCenterSum(nums,begin,end,mid);
        int leftSum = advancedMaxSubArray(nums,begin,mid);
        int rightSum = advancedMaxSubArray(nums,mid+1,end);
        int a = Math.max(centerSum,sum);
        int b = Math.max(leftSum,rightSum);
        return Math.max(a,b);
    }
    public int countCenterSum(int[] nums,int begin,int end,int mid){
        int leftCenterSum = Integer.MIN_VALUE;
        int rightCenterSum = Integer.MIN_VALUE;
        int tempSum = 0;
        for(int i=mid;i>=begin;i--){
            tempSum += nums[i];
            if(tempSum>leftCenterSum)
                leftCenterSum = tempSum;
        }
        tempSum = 0;
        for(int i=mid+1;i<=end;i++){
            tempSum += nums[i];
            if(tempSum>rightCenterSum)
                rightCenterSum = tempSum;
        }
        return leftCenterSum+rightCenterSum;
    }

    @Test
    public void test(){
        System.out.println(dpSubArray(new int[]{5,4,-1,7,8}));
    }
}
