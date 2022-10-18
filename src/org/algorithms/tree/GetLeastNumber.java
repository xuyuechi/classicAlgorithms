package org.algorithms.tree;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class GetLeastNumber {
    public int[] getLeastNumbers(int[] arr,int k){
        int[] res = new int[k];
        for(int i=0;i<k;i++){
            res[i] = quickSelect(arr,0,arr.length-1,i+1);
        }
        return res;
    }
    public int quickSelect(int[] A,int l,int r,int k){
        int s = lomutoPartition(A,l,r);
        if(s==l+k-1)
            return A[s];
        else if(s>l+k-1) return quickSelect(A,l,s-1,k);
        else return quickSelect(A,s+1,r,l+k-1-s);
    }
    public int lomutoPartition(int[] A,int l,int r){
        int p=A[l];
        int s=l;
        for(int i=l+1;i<=r;i++){
            if(A[i]<p){
                s++;
                int temp = A[s];
                A[s] = A[i];
                A[i] = temp;
            }
        }
        int temp = A[l];
        A[l] = A[s];
        A[s] = temp;
        return s;
    }
    @Test
    public void test(){
        System.out.println(Arrays.toString(getLeastNumbers(new int[]{3,2,1},2)));
    }
}
