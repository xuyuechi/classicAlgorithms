package org.algorithms.search;

import org.junit.Test;

//leetcode 322
public class SumCoins {
    public int coinChange(int[] coins, int amount){
        int[] table = new int[amount+1];
        table[0] = 0;
        for(int i=1;i<=amount;i++){
            int j=0;
            int temp = Integer.MAX_VALUE;
            while(j<coins.length){
                if(i>=coins[j]) {
                    if(table[i-coins[j]]!=-1)
                        temp = Math.min(table[i - coins[j]], temp);
                }
                j++;
            }
            if(temp==Integer.MAX_VALUE)
                table[i] = -1;
            else
                table[i] = temp+1;
        }
        return table[amount];
    }

    @Test
    public void test(){
        System.out.println(coinChange(new int[]{186,419,83,408},6249));
    }
}
