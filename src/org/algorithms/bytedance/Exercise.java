package org.algorithms.bytedance;

import org.algorithms.sort.ListNode;
import org.algorithms.tree.TreeNode;
import org.junit.Test;

import java.sql.DriverManager;
import java.util.*;
import java.util.stream.Collectors;

public class Exercise {

    @Test
    public void test(){
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        swapPairs(head);
    }

    private LinkedList<List<Integer>> result;

    //leetcode 113
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        result = new LinkedList<>();
        if(root==null)
            return result;

        traverse(new LinkedList<>(),targetSum,root,0);
        return result;
    }

    public void traverse(LinkedList<Integer> path,int targetSum,TreeNode root,int nowSum){
        path.addLast(root.val);
        int sum = nowSum+root.val;
        if(sum==targetSum && path.size()!=0 && root.left==null && root.right==null){
            result.add(new LinkedList<>(path));
            path.pollLast();
            return;
        }
        if(root.left!=null)
            traverse(path,targetSum,root.left,sum);
        if(root.right!=null)
            traverse(path,targetSum,root.right,sum);
        path.pollLast();
    }


    //leetcode 24
    public ListNode swapPairs(ListNode p){
        if(p==null || p.next==null)
            return p;
        ListNode q = p.next;
        ListNode next = p.next.next;
        p.next.next = p;
        p.next = swapPairs(next);
        return q;
    }

    //leetcode 394
    public String decodeString(String s) {
        LinkedList<Character> stack = new LinkedList<>();
        int index = 0;
        while(index<s.length()){
            if(s.charAt(index)==']'){
                StringBuilder sb = new StringBuilder();
                Character c = stack.pop();
                while(c!='['){
                    sb.append(c);
                    c = stack.pop();
                }
                int num = toDigits(stack);
                String str = sb.reverse().toString();
                for(int i=0;i<num;i++){
                    for(int j=0;j<str.length();j++){
                        stack.push(str.charAt(j));
                    }
                }
            }
            else{
                stack.push(s.charAt(index));
            }
            index++;
        }
        StringBuilder result = new StringBuilder();
        for(int i=stack.size()-1;i>=0;i--){
            result.append(stack.get(i));
        }
        return result.toString();
    }

    public int toDigits(LinkedList<Character> stack){
        StringBuilder sb = new StringBuilder();
        while((!stack.isEmpty()) && Character.isDigit(stack.peekFirst())){
            sb.append(stack.pop());
        }
        return Integer.parseInt(sb.reverse().toString());
    }

    public boolean undone(LinkedList<Character> list){
        if(list.size()==0)
            return true;
        for(Character c:list){
            if(c=='[')
                return true;
        }
        return false;
    }


    //wrong
    public int lengthOfLongestSubstring(String s) {
        int len = 0;
        int left = 0;
        int right = 0;
        if(s.length()==0)
            return 0;
        int maxLen = 0;
        HashMap<Character,Integer> count = new HashMap<>();
        LinkedList<Integer> list = new LinkedList<>();
        while(left<right && right<s.length()){
            right++;
            Character add = s.charAt(right-1);
            while(left<right && count.getOrDefault(add,0)>0){
                Character remove = s.charAt(left);
                count.put(remove,count.get(remove)-1);
                left++;
            }
            count.put(add,1);
            maxLen = Math.max(maxLen,right-left);
        }
        return maxLen;
    }

    @Test
    public void optionalTest(){
        Optional<String> nonEmptyOptional = Optional.of("nonEmpty");
        Optional<String> empty = Optional.empty();
        Optional<String> nullable = Optional.ofNullable(null);
        System.out.println("nonEmptyOptional is Present? "+nonEmptyOptional.isPresent());
        System.out.println("emptyOptional is Present? "+empty.isPresent());
        System.out.println("nullable is Present? "+nullable.isPresent());
        System.out.println("nonEmptyOptional value: "+nonEmptyOptional.get());
        System.out.println("nullable default value: "+nullable.orElse("default"));
    }

    @Test
    public void testFor1(){
        ArrayList<Object> list = null;
        for(Object o:list){
            System.out.println(o);
        }
    }

    @Test
    public void testFor2(){
        ArrayList<Object> list = null;
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
    }


}
