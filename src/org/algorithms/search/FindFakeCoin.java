package org.algorithms.search;

import org.junit.Test;

import java.util.Arrays;
import java.util.Scanner;

public class FindFakeCoin {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int[] coins = new int[n];
        for(int i=0;i<n;i++){
            coins[i] = scan.nextInt();
        }
        System.out.println(findFakeCoin(coins));
    }

    public static int findFakeCoin(int[] coins){
        int size = coins.length;
        int min = Integer.MAX_VALUE;
        int index = 0;
        for(int i=0;i<coins.length;i++){
            if(coins[i]<min){
                min = coins[i];
                index = i;
            }
        }
        return index+1;
    }
}
