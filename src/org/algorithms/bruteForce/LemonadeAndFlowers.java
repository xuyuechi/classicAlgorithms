package org.algorithms.bruteForce;

import org.junit.Test;

import java.util.LinkedList;

public class LemonadeAndFlowers {
    //leetcode 860
    public boolean lemonadeChange(int[] bills){
        LinkedList<Integer> mine = new LinkedList<>();
        for(int i=0;i< bills.length;i++){
            if(bills[i]==5)
                mine.add(5);
            else if(bills[i]==10) {
                boolean result = mine.remove((Integer) 5);
                if(!result)
                    return false;
                mine.add(10);
            }
            else if(bills[i]==20){
                int five = 0;
                int ten = 0;
                for(Integer j:mine){
                    if(j==5)
                        five++;
                    if(j==10)
                        ten++;
                }
                if(five>=1 && ten>=1){
                    mine.remove((Integer) 5);
                    mine.remove((Integer) 10);
                }
                else if(five>=3){
                    mine.remove((Integer) 5);
                    mine.remove((Integer) 5);
                    mine.remove((Integer) 5);
                }
                else
                    return false;
            }
            else
                return false;
        }
        return true;
    }
    //leetcode 605
    public boolean canPlaceFlowers(int[] flowerbed,int n){
        if(n==0)
            return true;
        if(flowerbed.length==1)
            return flowerbed[0]==0 && n<=1;
        int size = flowerbed.length;
        int[] work = new int[size+2];
        work[0] = 0;
        work[size+1] = 0;
        System.arraycopy(flowerbed, 0, work, 1, flowerbed.length);
        for(int i=1;i<work.length-1;i++){
            if(work[i-1]==0&&work[i]==0&&work[i+1]==0){
                n--;
                work[i] = 1;
            }
        }
        return n <= 0;
    }
    @Test
    public void test(){
        System.out.println(canPlaceFlowers(new int[]{0,0,0,0,1},2));
    }
}
