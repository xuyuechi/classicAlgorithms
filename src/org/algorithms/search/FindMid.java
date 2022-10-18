package org.algorithms.search;

import org.junit.Test;

public class FindMid {
    public int lomutoPartition(int[] A,int l,int r){
        int p = A[l];
        int s = l;
        for (int i = l+1; i <= r; i++) {
            if (A[i] < p) {
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

    public int quickSelect(int[] A,int k,int l,int r){
        //快速选择求中值，运用递归与Lomuto划分
        int s = lomutoPartition(A,l,r);
        if(s==(l+k-1))
            return A[s];
        else if(s>(l+k-1)){
            int result = quickSelect(A,k,l,s-1);
            return result;
        }
        else {
            int result = quickSelect(A, l + k - 1 - s, s + 1, r);
            return result;
        }
    }

    @Test
    public void testForFindMid(){
        int[] B = new int[]{3,8,5,4,7,2,1,6,9,10,0};
        System.out.println(quickSelect(B,B.length/2+1,0,B.length-1));
    }
}
