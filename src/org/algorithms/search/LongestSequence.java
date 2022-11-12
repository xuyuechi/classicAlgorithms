package org.algorithms.search;

import org.junit.Test;

import java.util.Arrays;

public class LongestSequence {
    public int longestCommonSequence(String text1, String text2) {
        int len1 = text1.length();
        int len2 = text2.length();
        int[][] dp = new int[len1+1][len2+1];
        for(int i=0;i<=len1;i++){
            dp[i][0] = 0;
        }
        for(int i=0;i<=len2;i++){
            dp[0][i] = 0;
        }
        for(int i=1;i<=len1;i++){
            for(int j=1;j<=len2;j++){
                if(text1.charAt(i-1)==text2.charAt(j-1))
                    dp[i][j] = dp[i-1][j-1]+1;
                else
                    dp[i][j] = Math.max(dp[i][j-1],dp[i-1][j]);
            }
        }
        return dp[len1][len2];
    }

    @Test
    public void test() {
        System.out.println(longestCommonSequence("ace", "abcde"));
    }
}

/*
if(work.charAt(0) == ref.charAt(p)){
                dp[0] = 1;
                p++;
            }
            else
                dp[0] = 0;
            int originalP = p;
            for (int i = 1; i < work.length() && p<ref.length(); i++) {
                if (work.charAt(i) == ref.charAt(p)) {
                    dp[i] = dp[i - 1]+1;
                    p++;
                    if(p==ref.length())
                        break;
                }else{
                    dp[i] = dp[i-1];
                }
            }
            for(int i:dp){
                System.out.print(i+",");
                if(i>max)
                    max = i;
            }
            System.out.println();
            p = originalP;
            p++;
 */
/*
int pRef = 0;
        int pWork = 0;

pRef = originalP;
            while(pWork<work.length()) {
                if (ref.charAt(pRef) == work.charAt(pWork) && pRef<ref.length()) {
                    if (pWork == 0) {
                        dp[0] = 1;
                    } else {
                        dp[pWork] = dp[pWork - 1] + 1;
                    }
                    pWork++;
                    pRef++;
                }else{
                    dp[pWork] = dp[pWork-1];
                    pWork++;
                }
            }
            for(int i:dp){
                System.out.print(i+",");
                if(i>max)
                    max = i;
            }
            System.out.println();
            pWork = 0;
            originalP++;
 */
/*
String ref;
        String work;
        if (text1.length() > text2.length()) {
            ref = text2;
            work = text1;
        } else {
            ref = text1;
            work = text2;
        }
        int[] dp = new int[work.length()];
        int max = Integer.MIN_VALUE;
        int originalP = 0;
        while (originalP < ref.length()) {
            if (work.charAt(0) == ref.charAt(originalP)) {
                dp[0] = 1;
                originalP++;
            } else
                dp[0] = 0;
            int p = originalP;
            while(p<ref.length()) {
                for (int i = 1; i < work.length() && p < ref.length(); i++) {
                    if (work.charAt(i) == ref.charAt(p)) {
                        dp[i] = dp[i - 1] + 1;
                        p++;
                    } else {
                        if(dp[i-1]>0)
                            dp[i] = dp[i - 1];
                    }
                }
                p++;
            }
            for (int v : dp) {
                System.out.print(v + ",");
                if (v > max)
                    max = v;
            }
            System.out.println();
            originalP++;
        }
        return max;
 */