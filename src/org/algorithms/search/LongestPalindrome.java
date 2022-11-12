package org.algorithms.search;

import org.junit.Test;

import java.util.Arrays;

public class LongestPalindrome {
    public String longestPalindrome(String s){
        int len = s.length();
        int[][] work = new int[len][len];
        for(int i=0;i<len;i++){
            work[i][i] = 1;
        }
        for(int i=1;i<len;i++) {
            if (s.charAt(i - 1) == s.charAt(i)) {
                work[i - 1][i] = 2;
            } else {
                work[i - 1][i] = -1;
            }
        }
        for(int k=3;k<=len;k++){
            for(int i=0;i<=len-k;i++){
                if(work[i+1][i+k-2] != -1 && s.charAt(i)==s.charAt(i+k-1)){
                    work[i][i+k-1] = k;
                }else{
                    work[i][i+k-1] = -1;
                }
            }
        }
        int max = 0;
        int end = -1;
        int begin = -1;
        for(int i=0;i<len;i++){
            for(int j=i;j<len;j++){
                if(work[i][j]>max){
                    max = work[i][j];
                    end = j;
                    begin = i;
                }
            }
        }
        if(begin==-1 && end==-1)
            return "";
        else
            return s.substring(begin,end+1);
    }

    @Test
    public void test(){
        System.out.println(longestPalindrome("abcbe"));
    }
}
