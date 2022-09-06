package org.algorithms.sort.test;
import org.algorithms.sort.InsertionSort;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class TestForSort {
    @Test
    public void test(){
        Random random = new Random();
        int size;
        while((size=random.nextInt(1000))<=0){}
        int[] A = new int[size];
        for(int k=0;k<size;k++){
            A[k] = random.nextInt(100000);
        }
        System.out.println("Array size is "+size);
        System.out.println("Array before sorting: "+ Arrays.toString(A));
        A = InsertionSort.sort(A);
        System.out.println("Array after sorting: "+ Arrays.toString(A));
    }
}
