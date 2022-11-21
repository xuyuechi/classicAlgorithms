package org.algorithms.tree;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MajorityElement {
    public int majorityElement(int[] nums) {
        Map<Integer,Integer> overall = new HashMap<>();
        for(int p:nums){
            if(!overall.containsKey(p)){
                overall.put(p,0);
            }else{
                int count = overall.get(p);
                overall.put(p,++count);
            }
        }
        int max = -1;
        Integer value = null;
        for(Map.Entry e:overall.entrySet()){
            if((int)e.getValue()>max){
                max = (int) e.getValue();
                value = (int)e.getKey();
            }
        }
        return value;
    }

    public int advancedGetMajority(int[] nums,int begin,int end){
        if(begin==end)
            return nums[begin];
        int middle = (begin+end)/2;
        int leftNum = advancedGetMajority(nums,begin,middle);
        int rightNum = advancedGetMajority(nums,middle+1,end);
        int leftCount = getCount(nums,begin,middle,leftNum);
        int rightCount = getCount(nums,middle+1,end,rightNum);
        if(leftCount>rightCount)
            return leftNum;
        else
            return rightNum;
    }
    public int getCount(int[]nums,int begin,int end,int num){
        int count=0;
        for(int i=begin;i<=end;i++){
            if(nums[i]==num)
                count++;
        }
        return count;
    }
}
