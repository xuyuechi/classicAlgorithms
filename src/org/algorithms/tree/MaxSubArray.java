package org.algorithms.tree;

import org.junit.Test;

public class MaxSubArray {
    public int maxSubArray(int[] nums){
        return advancedMaxSubArray(nums,0,nums.length-1);
    }
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
        System.out.println(maxSubArray(new int[]{-2,1,-3,4,-1,2,1,-5,4}));
    }
}
