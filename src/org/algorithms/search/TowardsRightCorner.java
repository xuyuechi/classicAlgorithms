package org.algorithms.search;

import org.junit.Test;

public class TowardsRightCorner {
    //leetcode 62
    public int uniquePaths(int m,int n){
        int[][] paths = new int[m][n];
        paths[0][0] = 1;
        for(int i=1;i<m;i++){
            paths[i][0] = 1;
        }
        for(int j=1;j<n;j++){
            paths[0][j] = 1;
        }
        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                paths[i][j] = paths[i-1][j]+paths[i][j-1];
            }
        }
        return paths[m-1][n-1];
    }

    //leetcode 70
    public int climbStairs(int n){
        int[] paths = new int[n];
        paths[0] = 1;
        if(n==1)
            return paths[0];
        paths[1] = 2;
        for(int i=2;i<n;i++){
            paths[i] = paths[i-1]+paths[i-2];
        }
        return paths[n-1];
    }
    @Test
    public void test(){
        System.out.println(climbStairs(5));
    }
}
