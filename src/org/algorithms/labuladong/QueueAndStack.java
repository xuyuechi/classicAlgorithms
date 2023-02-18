package org.algorithms.labuladong;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

//学剑篇 1.3队列/栈
public class QueueAndStack {

    //leetcode 20
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '{' || s.charAt(i) == '(' || s.charAt(i) == '[')
                stack.push(s.charAt(i));
            else if (!stack.isEmpty()) {
                if (s.charAt(i) == '}' && stack.peek() == '{')
                    stack.pop();
                else if (s.charAt(i) == ']' && stack.peek() == '[')
                    stack.pop();
                else if (s.charAt(i) == ')' && stack.peek() == '(')
                    stack.pop();
                else
                    return false;
            } else
                return false;
        }
        if (stack.isEmpty())
            return true;
        else
            return false;
    }

    //leetcode 921
    public int minAddToMakeValid(String s) {
        int insert = 0, need = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                need++;
            } else if (need > 0) {
                need--;
            } else {
                insert++;
            }
        }
        return insert + need;
    }

    //leetcode 1541
    public int minInsertions(String s) {
        int insert = 0, need = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '('){
                need += 2;
                if(need % 2 == 1){
                    insert++;
                    need--;
                }
            }
            else {
                need--;
                if (need == -1) {
                    need = 1;
                    insert++;
                }
            }
        }
        return need + insert;
    }

    //leetcode 496
    public int[] nextGreaterElement(int[] nums1,int[] nums2){
        HashMap<Integer,Integer> fx = new HashMap<>();
        Stack<Integer> work = new Stack<>();
        for(int i=nums2.length-1;i>=0;i--){
            while(!work.isEmpty() && work.peek()<=nums2[i]){
                work.pop();
            }
            fx.put(nums2[i],work.isEmpty()?-1:work.peek());
            work.push(nums2[i]);
        }
        int[] ans = new int[nums1.length];
        for(int j=0;j<nums1.length;j++){
            ans[j] = fx.get(nums1[j]);
        }
        return ans;
    }

    //leetcode 739
    public int[] dailyTemperatures(int[] temperatures){
        Stack<Integer> work = new Stack<>();
        int[] ans = new int[temperatures.length];
        for(int i=temperatures.length-1;i>=0;i--){
            while(!work.isEmpty() && temperatures[work.peek()]<=temperatures[i]){
                work.pop();
            }
            ans[i] = work.isEmpty()?0:work.peek()-i;
            work.push(i);
        }
        return ans;
    }

    //leetcode 503
    public int[] nextGreaterElements(int[] nums){
        Stack<Integer> work = new Stack<>();
        int[] ans = new int[nums.length];
        for(int i=nums.length*2-1;i>=0;i--){
            while(!work.isEmpty() && work.peek()<=nums[i % nums.length]){
                work.pop();
            }
            ans[i%nums.length] = work.isEmpty()?-1:work.peek();
            work.push(nums[i% nums.length]);
        }
        return ans;
    }

    //leetcode 172 baidu.com
    public int trailingZeroes(int n){
        int count = 0;
        for(int i=1;i<=n;i++){
            int k = i;
            while(k%5 == 0){
                k = k/5;
                count++;
            }
        }
        return count;
    }

    //leetcode 239
    class MonotonicQueue{
        LinkedList<Integer> maxq = new LinkedList<>();
        public void push(int n){
            while(!maxq.isEmpty() && maxq.getLast()<n){
                maxq.pollLast();
            }
            maxq.addLast(n);
        }

        public int max(){
            return maxq.getFirst();
        }

        public void pop(int n){
            if(maxq.getFirst() == n)
                maxq.pollFirst();
        }
    }

    //leetcode 239
    public int[] maxSlidingWindow(int[] nums,int k){
        MonotonicQueue mq = new MonotonicQueue();
        int[] ans = new int[nums.length-k+1];
        for(int i=0;i<nums.length;i++){
            if(i < k-1){
                mq.push(nums[i]);
                continue;
            }
            mq.push(nums[i]);
            ans[i-k+1] = mq.max();
            mq.pop(nums[i-k+1]);
        }
        return ans;
    }

    //leetcode 316
    public String removeDuplicateLetters(String s){
        Stack<Character> stk = new Stack<>();
        boolean[] exist = new boolean[256];
        int[] count = new int[256];
        for(int i=0;i<s.length();i++){
            count[s.charAt(i)-97]++;
        }
        for(int i=0;i<s.length();i++){
            count[s.charAt(i)-97]--;
            if(!exist[s.charAt(i)-97]){
                while(!stk.isEmpty() && count[stk.peek()-97]>0 && stk.peek()>s.charAt(i)){
                    exist[stk.peek()-97] = false;
                    stk.pop();
                }
                stk.push(s.charAt(i));
                exist[s.charAt(i)-97] = true;
            }
        }
        StringBuilder sb = new StringBuilder();
        while(!stk.isEmpty()){
            sb.append(stk.pop());
        }
        return sb.reverse().toString();
    }

    @Test
    public void testFor316(){
        System.out.println(removeDuplicateLetters("bcabc"));
    }
}
