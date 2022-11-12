package org.algorithms.tree;

import org.junit.Test;

import java.util.*;

public class LongestSubstring {
    public int longestSubstring(String s, int k) {
        HashMap<Character,Integer> map = new HashMap<>();
        for(int i=0;i<s.length();i++){
            Character key = s.charAt(i);
            if(!map.containsKey(key)){
                map.put(key,1);
            }
            else{
                Integer count = map.get(key);
                count++;
                map.put(key,count);
            }
        }
        Set<Map.Entry<Character, Integer>> entries = map.entrySet();
        int min = 0;
        boolean flag = false;
        for(Map.Entry<Character,Integer> entry:entries){
            if(entry.getValue()<k){
                flag = true;
                String[] split = s.split("" + entry.getKey());
                for(String sub:split){
                    int length = longestSubstring(sub, k);
                    if(length>min)
                        min=length;
                }
            }
        }
        if(flag)
            return min;
        else
            return s.length();

    }
    public int longestSubstring2(String s, int k) {
        if (s.length() < k) return 0;
        HashMap<Character, Integer> counter = new HashMap();
        for (int i = 0; i < s.length(); i++) {
            counter.put(s.charAt(i), counter.getOrDefault(s.charAt(i), 0) + 1);
        }
        for (char c : counter.keySet()) {
            if (counter.get(c) < k) {
                int res = 0;
                for (String t : s.split(String.valueOf(c))) {
                    res = Math.max(res, longestSubstring2(t, k));
                }
                return res;
            }
        }
        return s.length();
    }

    @Test
    public void test() {
        String a = "ababbc";
        System.out.println(longestSubstring2("aaaaaaaaaaaabcdefghijklmnopqrstuvwzyz"
                , 3));
    }
}
