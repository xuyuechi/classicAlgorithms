package org.algorithms.sort;

/**
 * InsertionSort 插入排序
 * 算法思想：减治法
 *
 * 伪代码：
 * 用插入排序对给定数组排序
 * 输入：n个可排序元素构成的一个数组A[0..n-1]
 * 输出：非降序排列的数组A[0..n-1]
 * for i←1 to n-1 do
 *      v ← A[i]
 *      j ← i-1
 *      while j>=0 and A[j] > v do
 *          A[j+1] ← A[j]
 *          j ← j-1
 *          A[j+1] ← v
 */
public class InsertionSort{
    public static int[] sort(int[] A){
        for(int i=1;i<=A.length-1;i++){
            int v = A[i];
            int j = i-1;
            while(j>=0 && A[j]>v){
                A[j+1]=A[j];
                j=j-1;
                A[j+1]=v;
            }
        }
        return A;
    }
}
