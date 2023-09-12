package org.algorithms.sort;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class QuickSort {
    public static int hoarePartition(int[] arr,int left,int right){
        int pivot = arr[left];
        while(left<right){
            while(arr[right]>=pivot && left<right)right--;
            arr[left]=arr[right];
            while(arr[left]<=pivot && left<right)left++;
            arr[right]=arr[left];
        }
        arr[left] = pivot;
        return left;
    }
    public static void quickSort(int[] A,int l,int r){
        if(l<r){
            int s=hoarePartition(A,l,r);
            quickSort(A,l,s-1);
            quickSort(A,s+1,r);
        }
    }

    @Test
    public void test(){
        //hoarePartition(new int[]{2,1},0,1);
//        String abc = "xxx\\".replaceAll("\\\\", "abc");
//        System.out.println(abc);

        String s = "xxx\\";
        System.out.println(s);
        String abc = s.replaceAll("\\\\", "abc");
        System.out.println(abc);
    }
}
