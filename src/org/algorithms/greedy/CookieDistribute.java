package org.algorithms.greedy;

import org.algorithms.tree.TreeNode;
import org.junit.Test;

import java.util.*;

public class CookieDistribute {

    //leetcode 409
    public int longestPalindrome(String s){
        HashMap<Character,Integer> map = new HashMap<>();
        for(int i=0;i<s.length();i++){
            if(map.containsKey(s.charAt(i))){
                int value = map.get(s.charAt(i));
                map.put(s.charAt(i),++value);
            }else {
                map.put(s.charAt(i),1);
            }
        }
        Set<Map.Entry<Character, Integer>> entries = map.entrySet();
        int count = 0;
        int biggestEven = 0;
        for(Map.Entry<Character,Integer> entry:entries){
            int value = entry.getValue();
            if(value%2==0)
                count+=value;
            else {
                if(biggestEven<value){
                    biggestEven = value;
                }
            }
        }
        boolean flag = true;
        for(Map.Entry<Character,Integer> entry:entries){
            int value = entry.getValue();
            if(value%2!=0){
                if(value==biggestEven&&flag){
                    count+=value;
                    flag = false;
                }
                else
                    count+=(value-1);
            }
        }
        return count;
    }

    //leetcode 1827
    public int minOperations(int[] nums){
        int ops = 0;
        for(int i=1;i<nums.length;i++){
            if(nums[i]>nums[i-1])
                continue;
            ops+=nums[i-1]-nums[i]+1;
            nums[i] = nums[i-1] + 1;
        }
        return ops;
    }

    @Test
    public void testForRob3(){
        TreeNode single = new TreeNode(5,null,null);
        System.out.println(rob3(single));
    }

    //leetcode 337
    public int rob3(TreeNode root){
        HashMap<TreeNode,Integer> work = new HashMap<>();
        postOrder(root,work);
        return Math.max(root.val,work.get(root));
    }

    public void postOrder(TreeNode root,HashMap<TreeNode,Integer> map){
        if(root == null)
            return;
        else{
            postOrder(root.left,map);
            postOrder(root.right,map);
            if(root.left == null && root.right == null){
                map.put(root,0);
            }
            else if(root.left == null){
                root.val = Math.max(root.val +map.get(root.right),root.right.val);
                map.put(root,root.right.val);
            }
            else if(root.right == null){
                root.val = Math.max(root.val +map.get(root.left),root.left.val);
                map.put(root,root.left.val);

            }else{
                root.val = Math.max(root.val +map.get(root.right)+ map.get(root.left),root.left.val+root.right.val);
                map.put(root,root.left.val+root.right.val);
            }
        }
    }

    //leetcode 213
    public int rob2(int[] nums){
        int size = nums.length;
        if(size==1)
            return nums[0];
        if(size==2)
            return Math.max(nums[0],nums[1]);
        if(size==3)
            return Math.max(Math.max(nums[0],nums[1]),nums[2]);
        int[] dp = new int[size];
        dp[0] = nums[0];
        dp[1] = dp[0];
        for(int i=2;i<size-1;i++){
            dp[i] = Math.max(dp[i-1],nums[i]+dp[i-2]);
        }
        dp[size-1] = dp[size-2];
        int beginWith0 = dp[size-1];
        Arrays.fill(dp,0);
        dp[size-1] = nums[size-1];
        dp[0] = nums[size-1];
        for(int i=1;i<size-2;i++){
            dp[i] = Math.max(dp[i-1],nums[i]+dp[(i-2+size)%size]);
        }
        dp[size-2] = dp[size-3];
        dp[size-1] = dp[size-2];
        int beginWithEnd = dp[size-1];
        Arrays.fill(dp,0);
        dp[1] = nums[1];
        dp[2] = Math.max(dp[1],nums[2]);
        for(int i=3;i<size;i++){
            dp[i] = Math.max(dp[i-1],nums[i]+dp[i-2]);
        }
        int beginWithNothing = dp[size-1];
        return Math.max(Math.max(beginWith0,beginWithEnd),beginWithNothing);
    }

    @Test
    public void testForRob2(){
        System.out.println(rob2(new int[]{2,7,7,7,4}));
    }

    //leetcode 365
    public int gcd(int a,int b){
        if(b==0)
            return a;
        else
            return gcd(b,a%b);
    }

    public boolean canMeasureWater(int jug1Capacity,int jug2Capacity,int targetCapacity){
        int num = gcd(jug1Capacity,jug2Capacity);
        if(targetCapacity%num==0 && targetCapacity<=jug1Capacity+jug2Capacity)
            return true;
        else
            return false;
    }

    @Test
    public void testForMeasureWater(){
        System.out.println(canMeasureWater(1,1,3));
    }

    //leetcode 198
    public int rob(int[] nums){
        int size = nums.length;
        if(size<2)
            return nums[0];
        nums[1] = Math.max(nums[0],nums[1]);
        for(int i=2;i<size;i++){
            nums[i] = Math.max(nums[i-1],nums[i]+nums[i-2]);
        }
        return nums[size-1];
    }

    @Test
    public void testForRob(){
        System.out.println(rob(new int[]{2,7,9,3,1}));
    }

    //leetcode 1732
    public int largestAltitude(int[] gain){
        for(int i=1;i< gain.length;i++){
            gain[i] = gain[i] + gain[i-1];
        }
        int biggest = Integer.MIN_VALUE;
        for(int i=0;i< gain.length;i++){
            biggest = Math.max(biggest,gain[i]);
        }
        if(biggest<0)
            biggest = 0;
        return biggest;
    }

    @Test
    public void testForAltitude(){
        System.out.println(largestAltitude(new int[]{-5,1,5,0,-7}));
    }

    //leetcode 11
    public int maxArea(int[] height){
        int len = height.length;
        int left = 0;
        int right = len-1;
        int max = Integer.MIN_VALUE;
        while(left<right){
            int lower = Math.min(height[left],height[right]);
            int area = lower*(right-left);
            if(area>max)
                max = area;
            if(height[left]<height[right])
                left++;
            else
                right--;
        }
        return max;
    }

    @Test
    public void testForMaxArea(){
        System.out.println(maxArea(new int[]{1,1}));
    }

    //leetcode 402
    public String removeKdigits(String num,int k){
        Deque<Character> deque = new LinkedList<Character>();
        int len = num.length();
        deque.add(num.charAt(0));
        for(int i=1;i<len;i++){
            while(k>0 && !deque.isEmpty() && num.charAt(i) < deque.peekLast()){
                deque.pollLast();
                k--;
            }
            deque.addLast(num.charAt(i));
        }
        for(int j=0;j<k;j++){
            deque.pollLast();
        }
        int size = deque.size();
        for(int m=0;m<size;m++){
            if(deque.peekFirst() == '0')
                deque.pollFirst();
        }
        StringBuilder sb = new StringBuilder();
        while(!deque.isEmpty()){
            sb.append(deque.pollFirst());
        }
        String s = sb.toString();
        if(s.equals(""))
            return "0";
        else
            return s;
    }

    @Test
    public void testForRemove() {
        System.out.println(removeKdigits("10001", 1));
    }
    /*
    public String removeKdigits(String num, int k) {
        int beforeSize = num.length();
        int afterSize = beforeSize - k;
        if(afterSize<=0)
            return "0";
        ArrayList<Integer> del = new ArrayList<>();
        for (int i = 0; i < afterSize && k > 0; i++) {
            int range = beforeSize - afterSize + i;
            int max = Integer.MIN_VALUE;
            int tag = -1;
            for (int j = 0; j <= range; j++) {
                int c = Integer.parseInt(String.valueOf(num.charAt(j)));
                if (c > max && !del.contains(j)) {
                    max = c;
                    tag = j;
                }
            }
            if (max > 0) {
                k--;
                del.add(tag);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < beforeSize; i++) {
            if (!del.contains(i))
                sb.append(num.charAt(i));
        }
        return String.valueOf(Integer.parseInt(sb.toString()));
    }
    */


    //leetcode 976
    public int largestPerimeter(int[] nums) {
        int size = nums.length;
        Arrays.sort(nums);
        for (int i = size - 1; i >= 2; i--) {
            if (nums[i - 2] + nums[i - 1] > nums[i])
                return nums[i] + nums[i - 1] + nums[i - 2];
        }
        return 0;
    }

    @Test
    public void testForTriangle() {
        System.out.println(largestPerimeter(new int[]{5, 7, 4, 55, 9, 8, 6}));
    }

    //leetcode 1323
    public int maximum69Number(int num) {
        Stack<Integer> digits = new Stack<>();
        int i;
        while ((i = num % 10) != 0) {
            num /= 10;
            digits.add(i);
        }
        int stackLength = digits.size();
        int ans = 0;
        boolean notDo = false;
        while (!digits.isEmpty()) {
            int dig = digits.pop();
            if (dig == 6 && !notDo) {
                dig = 9;
                ans += dig * Math.pow(10, stackLength - 1);
                notDo = true;
            } else {
                ans += dig * Math.pow(10, stackLength - 1);
            }
            stackLength--;
        }
        return ans;
    }

    @Test
    public void testFor69Number() {
        System.out.println(maximum69Number(9));
    }


    //leetcode 455
    public int findContentChildren(int[] g, int[] s) {
        if (s.length == 0)
            return 0;
        int count = 0;
        for (int i = 0; i < g.length; i++) {
            int index = findSuit(g[i], s);
            if (index != -1) {
                s[index] = 0;
                count++;
            }
        }
        return count;
    }

    @Test
    public void testForCookie() {
        System.out.println(findContentChildren(new int[]{6, 2, 10, 7, 7}, new int[]{1, 2, 8, 9}));
    }

    public int findSuit(int a, int[] s) {
        int closest = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < s.length; i++) {
            if (s[i] > 0 && s[i] - a < closest && s[i] - a >= 0) {
                closest = s[i] - a;
                index = i;
            }
        }
        return index;
    }
}
