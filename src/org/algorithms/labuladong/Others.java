package org.algorithms.labuladong;

import org.algorithms.sort.ListNode;
import org.algorithms.tree.TreeNode;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

//朴剑篇 其他
public class Others {

    //银泰 1
    public TreeNode deleteNode (TreeNode root, int key) {
        if(root == null)
            return null;
        if(key>root.val)
            root.right = deleteNode(root.right,key);
        if(key<root.val)
            root.left = deleteNode(root.left,key);
        if(key==root.val){
            if(root.left == null){
                root = root.right;
            }
            else if(root.right == null){
                root = root.left;
            }
            else{
                TreeNode maxLeft = findMaxLeft(root.right);
                maxLeft.left = root.left;
                root = root.right;
            }
        }
        return root;
    }

    public TreeNode findMaxLeft(TreeNode root){
        if(root == null)
            return null;
        if(root.left == null)
            return root;
        else
            return findMaxLeft(root.left);
    }


    @Test
    public void test3(){
        isSubsequence("axc","ahbdgc");
    }

    //leetcode 392
    public boolean isSubsequence(String s, String t) {
        char[] sArray = s.toCharArray();
        char[] tArray = t.toCharArray();
        HashMap<Character,ArrayList<Integer>> indexOft = new HashMap<>();
        for(int i=0;i<tArray.length;i++){
            if(!indexOft.containsKey(tArray[i])){
                indexOft.put(tArray[i],new ArrayList<>());
            }
            indexOft.get(tArray[i]).add(i);
        }
        return check(0,-1,indexOft,sArray);
    }

    public boolean check(int launch,int start,HashMap<Character,ArrayList<Integer>> indexOft,char[] s){
        if(launch>=s.length)
            return true;
        if(!indexOft.containsKey(s[launch]))
            return false;
        ArrayList<Integer> indexes = indexOft.get(s[launch]);
        for(Integer in:indexes){
            if(in>start)
                return check(launch+1,in,indexOft,s);
        }
        return false;
    }

    //leetcode 1559
    public boolean containsCycle(char[][] grid) {
        return true;
    }

    public void dfs(char[][] grid,boolean[][] visited,LinkedList<Integer> track,int row,int col){
        if(row<0 || row >= grid.length || col<0 || col >= grid[0].length)
            return;
        visited[row][col] = true;
        track.add(row*grid[0].length+col);
        int[][] dir = new int[][]{{1,0},{0,1},{-1,0},{0,-1}};
        for(int i=0;i<4;i++){
            int nextRow = row + dir[i][0];
            int nextCol = col + dir[i][1];

        }
    }

    //leetcode 1540
    public boolean canConvertString(String s, String t, int k) {
        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();
        int len = s.length();
        int len2 = t.length();
        if(len != len2)
            return false;
        int[] count = new int[26];
        for(int i=0;i<len;i++){
            int g = tChars[i] - sChars[i];
            if(g<0)
                g +=26;
            count[g]++;
        }
        for(int i=1;i<=25;i++){
            if(count[i] == 0)
                continue;
            if(i+(count[i]-1)*26>k)
                return false;
        }
        return true;
    }

    //leetcode 347
    public int[] topKFrequentAdvanced(int[] nums,int k){
        HashMap<Integer,Integer> counter = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            counter.put(nums[i],counter.getOrDefault(nums[i],0)+1);
        }
        ArrayList<Integer>[] buckets = new ArrayList[nums.length+1];
        for(Map.Entry<Integer,Integer> entry: counter.entrySet()){
            if(buckets[entry.getValue()]==null)
                buckets[entry.getValue()] = new ArrayList<>();
            buckets[entry.getValue()].add(entry.getKey());
        }
        int[] ans = new int[k];
        for(int i=nums.length-1;i>=0;i--){
            ArrayList<Integer> keys = buckets[i];
            if(keys == null)
                continue;
            for(Integer key:keys){
                ans[--k] = key;
                if(k==0)
                    return ans;
            }
        }
        return null;
    }

    //leetcode 347
    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer,Integer> counter = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            counter.put(nums[i],counter.getOrDefault(nums[i],0)+1);
        }
        List<Map.Entry<Integer,Integer>> list = new ArrayList<>(counter.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> integerIntegerEntry, Map.Entry<Integer, Integer> t1) {
                return integerIntegerEntry.getValue().compareTo(t1.getValue());
            }
        });
        int[] ans = new int[k];
        int j=0;
        for(int i=list.size()-1;i>=list.size()-k;i--){
            ans[j++] = list.get(i).getKey();
        }
        return ans;
    }

    //leetcode 74
    public boolean searchMatrix(int[][] matrix, int target) {
        return binarySearch(0,matrix.length-1,matrix,target);
    }

    public boolean binarySearch(int left,int right,int[][] matrix,int target){
        if(left>right)
            return false;
        int mid = left + (right-left)/2;
        if((target >= matrix[mid][0] && target <= matrix[mid][matrix[mid].length-1]) || left == right){
            for(int i=0;i<matrix[mid].length;i++){
                if(matrix[mid][i] == target)
                    return true;
            }
            return false;
        }
        else if(target > matrix[mid][matrix[mid].length-1] && mid < matrix.length-1){
            return binarySearch(mid+1,right,matrix,target);
        }
        else if(target < matrix[mid][0] && mid > 0){
            return binarySearch(left,mid-1,matrix,target);
        }
        else
            return false;
    }

    //leetcode 120
    public int minimumTotalAdvanced(List<List<Integer>> triangle){
        int min = Integer.MAX_VALUE;
        int row = triangle.size();
        int column = triangle.get(row-1).size();
        int[][] dp = new int[row][column];
        dp[0][0] = triangle.get(0).get(0);
        for(int i=1;i<row;i++){
            dp[i][0] = dp[i-1][0] + triangle.get(i).get(0);
        }
        for(int i=1;i<row;i++){
            for(int j=1;j<triangle.get(i).size();j++){
                if(triangle.get(i-1).size()<=j)
                    dp[i][j] = dp[i-1][j-1] + triangle.get(i).get(j);
                else
                    dp[i][j] = Math.min(dp[i-1][j],dp[i-1][j-1]) + triangle.get(i).get(j);
            }
        }
        for(int j=0;j<column;j++){
            min = Math.min(min,dp[row-1][j]);
        }
        return min;
    }

    @Test
    public void test2(){
        ArrayList<List<Integer>> list = new ArrayList<>();
        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        ArrayList<Integer> list3 = new ArrayList<>();
        ArrayList<Integer> list4 = new ArrayList<>();

        list1.add(-1);
        list2.add(2);
        list2.add(3);
        list3.add(1);
        list3.add(-1);
        list3.add(-3);
        list4.add(4);
        list4.add(1);
        list4.add(8);
        list4.add(3);

        list.add(list1);
        list.add(list2);
        list.add(list3);

        minimumTotal(list);
    }
    private int min = Integer.MAX_VALUE;

    //leetcode 120
    public int minimumTotal(List<List<Integer>> triangle) {
        int minimum = Integer.MAX_VALUE;
        int lastLayer = triangle.size()-1;
        int[][] memo = new int[triangle.size()][triangle.get(lastLayer).size()];
        for(int[] m:memo){
            Arrays.fill(m,Integer.MAX_VALUE);
        }
        dp(triangle,0,0,memo,0);
        for(int i=0;i<triangle.get(lastLayer).size();i++){
            minimum = Math.min(memo[lastLayer][i],minimum);
        }
        return minimum;
    }

    public void dp(List<List<Integer>> triangle,int startLayer,int startPoint,int[][] cache,int sum){
        if(startLayer>=triangle.size() || startPoint==-1 || startPoint>=triangle.get(startLayer).size())
            return;
        List<Integer> list = triangle.get(startLayer);
        int value1 = sum+list.get(startPoint);

        if(cache[startLayer][startPoint]>value1) {
            cache[startLayer][startPoint] = value1;
            dp(triangle, startLayer + 1, startPoint, cache, value1);
            dp(triangle,startLayer+1,startPoint+1,cache,value1);
        }

    }

    //leetcode 910
    public int smallestRangeIIAdvanced(int[] nums,int k){
        int min = Integer.MAX_VALUE;
        Arrays.sort(nums);
        for(int i=0;i<nums.length;i++){
            min = Math.min(Math.max(nums[i]+k,nums[nums.length-1]-k)-Math.min(nums[0]+k,nums[(i+1)>= nums.length?i:i+1]-k),min);
        }
        return min;
    }
    //private int min = Integer.MAX_VALUE;

    //leetcode 910
    public int smallestRangeII(int[] nums, int k) {
        dp(nums,k,0);
        return min;
    }

    public void dp(int[] nums,int k,int start){
        if(start>=nums.length){
            min = Math.min(min,gap(nums));
            return;
        }
        nums[start]+=k;
        dp(nums,k,start+1);
        nums[start]-=k;

        nums[start]-=k;
        dp(nums,k,start+1);
        nums[start]+=k;
    }

    public int gap(int[] nums){
        int b = Integer.MIN_VALUE;
        int s = Integer.MAX_VALUE;
        for(int i=0;i<nums.length;i++){
            b = Math.max(b,nums[i]);
            s = Math.min(s,nums[i]);
        }
        return b-s;
    }

    private int[] memo;

    //leetcode 2500
    public int deleteGreatestValue(int[][] grid) {
        int maxTimes = grid[0].length;
        int answer = 0;
        for(int i=0;i<grid.length;i++){
            Arrays.sort(grid[i]);
        }
        for(int j=0;j<maxTimes;j++){
            int toAdd = Integer.MIN_VALUE;
            for(int i=0;i<grid.length;i++){
                toAdd = Math.max(toAdd,grid[i][maxTimes-j-1]);
            }
            answer += toAdd;
        }
        return answer;
    }

    //leetcode 55
    public boolean canJumpAdvanced(int[] nums){
        int k = 0;
        for(int i=0;i< nums.length;i++){
            if(i>k)
                return false;
            else
                k = Math.max(k,i+nums[i]);
        }
        return true;
    }

    //leetcode 55
    public boolean canJump(int[] nums) {
        memo = new int[nums.length];
        return dfs(nums,0,nums.length-1);
    }

    public boolean dfs(int[] nums,int start,int target){
        if(target == start){
            memo[start] = 1;
            return true;
        }
        if(start>target || nums[start] == 0){
            memo[start] = -1;
            return false;
        }
        if(memo[start]!=0)
            return memo[start]==1;
        boolean result = false;
        for(int i=0;i<nums[start];i++){
            result |= dfs(nums,start+i+1, target);
            memo[start+i+1] = result?1:-1;
            if(result)
                break;
        }
        return result;
    }

    //leetcode 45
    public int jump(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp,Integer.MAX_VALUE);
        dp[0] = 0;
        for(int i=1;i<nums.length;i++){
            for(int j=1;i+j<= nums.length && j<=nums[i-1];j++){
                dp[i+j-1] = Math.min(dp[i+j-1],dp[i-1]+1);
            }
        }
        return dp[nums.length-1];
    }

    //leetcode 617
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        TreeNode newRoot = null;
        if(root1==null && root2==null)
            return newRoot;
        else if(root1==null && root2!=null){
            newRoot = root2;
            return newRoot;
        }
        else if(root1!=null && root2==null){
            newRoot = root1;
            return newRoot;
        }
        else
            newRoot = new TreeNode(root1.val+root2.val);
        newRoot.left = mergeTrees(root1.left,root2.left);
        newRoot.right = mergeTrees(root1.right,root2.right);
        return newRoot;
    }


    //leetcode 100
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p==null && q==null)
            return true;
        if(p==null || q==null)
            return false;
        if(p.val!=q.val)
            return false;
        else
            return isSameTree(p.left,q.left) && isSameTree(p.right,q.right);
    }

    //leetcode 66
    public int[] plusOne(int[] digits) {
        boolean forward = true;
        for(int i=digits.length-1;i>=0;i--){
            if(forward){
                digits[i]++;
                forward = false;
            }
            if(digits[i] == 10){
                forward = true;
                digits[i] = 0;
            }
            if(i==0 && forward){
                int[] newDigit = new int[digits.length+1];
                System.arraycopy(digits,0,newDigit,1,digits.length);
                newDigit[0] = 1;
                return newDigit;
            }
        }
        return digits;
    }

    //leetcode 35
    public int searchInsert(int[] nums, int target) {
        return binarySearch(0,nums.length-1,nums,target);
    }

    public int binarySearch(int left,int right,int[] nums,int target){
        if(left>right)
            return left;
        int mid = left + (right-left)/2;
        if(target == nums[mid])
            return mid;
        if(target > nums[mid])
            return binarySearch(mid+1,right,nums,target);
        else
            return binarySearch(left,mid-1,nums,target);

    }

    //leetcode 421 代码随想录
    public int findMaximumXOR(int[] nums) {
        return 0;
    }

    //leetcode 101
    public boolean isSymmetric(TreeNode root) {
        return checkSymm(root.left,root.right);
    }

    public boolean checkSymm(TreeNode left,TreeNode right){
        if(left == null && right == null)
            return true;
        if(left == null || right == null)
            return false;
        if(left.val != right.val)
            return false;
        return checkSymm(left.left,right.right) && checkSymm(left.right,right.left);
    }

    //leetcode 43
    public String multiply(String num1, String num2) {
        return null;
    }

    //leetcode 165
    public int compareVersion(String version1, String version2) {
        String[] ver1 = version1.split("\\.");
        String[] ver2 = version2.split("\\.");
        int len = Math.max(ver1.length,ver2.length);
        int[] v1 = new int[len];
        int[] v2 = new int[len];
        for(int i=0;i<len;i++){
            if(ver1.length>i)
                v1[i] = Integer.parseInt(ver1[i]);
            else
                v1[i] = 0;
            if(ver2.length>i)
                v2[i] = Integer.parseInt(ver2[i]);
            else
                v2[i] = 0;
        }
        for(int i=0;i<len;i++){
            if(v1[i]>v2[i])
                return 1;
            else if(v1[i]<v2[i])
                return -1;
        }
        return 0;
    }

    //leetcode 155
    class MinStack {
        private Stack<Integer> main;
        private Stack<Integer> deputy;

        public MinStack() {
            main = new Stack<>();
            deputy = new Stack<>();
        }

        public void push(int val) {
            main.push(val);
            if(deputy.size()>0 && val > getMin()){
                deputy.push(getMin());
            }
            else{
                deputy.push(val);
            }
        }

        public void pop() {
            main.pop();
            deputy.pop();
        }

        public int top() {
            return main.peek();
        }

        public int getMin() {
            return deputy.peek();
        }
    }

    //leetcode 剑指22
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode pre = head,p = head;
        for(int i=0;i<k-1;i++){
            pre = pre.next;
        }
        while(pre.next!=null){
            pre = pre.next;
            p = p.next;
        }
        return p;
    }

    //leetcode 8 *
    public int myAtoi(String s){
        String str = s.trim();
        if(str.length()==0)
            return 0;
        StringBuilder sb = new StringBuilder();
        boolean negative = false;
        int start = 0;
        if(str.charAt(0) == '-'){
            negative = true;
            start = 1;
        }
        else if(str.charAt(0) == '+'){
            negative = false;
            start = 1;
        }
        else if(str.charAt(0)<48 || str.charAt(0)>57){
            return 0;
        }
        int pov = start;
        char ch;
        while(pov<str.length() && (ch = str.charAt(pov))>=48 && ch<=57){
            sb.append(ch);
            pov++;
        }
        String ansStr = sb.toString().replaceFirst("^0+", "");
        if(ansStr.length()==0)
            return 0;
        if(ansStr.length()>10)
            return negative?Integer.MIN_VALUE:Integer.MAX_VALUE;
        Long value = Long.parseLong(ansStr);
        if(value>2147483647L && !negative)
            return Integer.MAX_VALUE;
        else if(value>2147483648L && negative)
            return Integer.MIN_VALUE;
        if(value == 2147483648L && negative)
            return Integer.MIN_VALUE;
        Integer realValue = Integer.parseInt(ansStr);
        return negative?(-1)*(realValue):(realValue);
    }

    //leetcode 2 *
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode p1 = l1, p2 = l2;
        ListNode dummy = new ListNode();
        ListNode pointer = dummy;
        boolean forward = false;
        while (p1 != null && p2 != null) {
            pointer.next = new ListNode();
            pointer = pointer.next;
            int total = (p1.val + p2.val + (forward ? 1 : 0))%10;
            pointer.val = total;
            if (p1.val + p2.val + (forward?1:0) >= 10)
                forward = true;
            else
                forward = false;
            p1 = p1.next;
            p2 = p2.next;
        }
        if(p1 == null && p2 == null && forward){
            pointer.next = new ListNode(1);
            return dummy.next;
        }
        if(p1 == null && p2 == null)
            return dummy.next;
        pointer.next = p1==null?p2:p1;
        pointer = pointer.next;
        while (forward && pointer.next != null) {
            pointer.val = pointer.val + 1;
            if (pointer.val >= 10) {
                pointer.val = 0;
                forward = true;
            } else {
                forward = false;
            }
            pointer = pointer.next;
        }
        if (forward) {
            pointer.val = pointer.val + 1;
            if (pointer.val >= 10) {
                pointer.val = 0;
                pointer.next = new ListNode(1);
            }
        }
        return dummy.next;
    }

    //leetcode 31 *
    public void nextPermutation(int[] nums) {
        if (nums.length < 2)
            return;
        int len = nums.length;
        int left = len - 1;
        int right = len - 1;
        for (int p = len - 1; p >= 1; p--) {
            if (nums[p] > nums[p - 1]) {
                left = p - 1;
                right = p;
                break;
            }
        }
        int toSwap = -1;
        for (int k = len - 1; k >= right; k--) {
            if (nums[k] > nums[left]) {
                toSwap = k;
                break;
            }
        }
        if (toSwap == -1) {
            reverse(nums, 0, len - 1);
            return;
        }
        swap(nums, left, toSwap);
        reverse(nums, right, len - 1);
    }

    public void reverse(int[] nums, int i, int j) {
        if (i >= j)
            return;
        for (int n = 0; n < (j - i + 1) / 2; n++) {
            swap(nums, i + n, j - n);
        }
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    class Foo {

        public Semaphore semaphore1;
        public Semaphore semaphore2;

        public Foo() {
            semaphore1 = new Semaphore(0);
            semaphore2 = new Semaphore(0);
        }

        public void first(Runnable printFirst) throws InterruptedException {
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            semaphore1.release();
        }

        public void second(Runnable printSecond) throws InterruptedException {
            semaphore1.acquire();
            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            semaphore2.release();
        }

        public void third(Runnable printThird) throws InterruptedException {
            semaphore2.acquire();
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
        }
    }

    //leetcode 93
    private List<String> result;

    public List<String> restoreIpAddresses(String s) {
        LinkedList<Integer> buckets = new LinkedList<>();
        result = new LinkedList<>();
        backTrack(buckets, 0, 0, s.length(), s);
        return result;
    }

    public void backTrack(List<Integer> buckets, int bucket, int start, int end, String str) {
        if (bucket == 4 && start == end) {
            StringBuilder r = new StringBuilder();
            for (Integer integer : buckets) {
                r.append(integer);
                r.append(".");
            }
            r.deleteCharAt(r.length() - 1);
            result.add(r.toString());
            return;
        } else if (bucket == 4 || start == end) {
            return;
        }

        if (str.charAt(start) == '0') {
            buckets.add(0);
            backTrack(buckets, bucket + 1, start + 1, end, str);
            buckets.remove(bucket);
            return;
        }

        int addr = 0;
        for (int i = start; i < end; i++) {
            addr = addr * 10 + str.charAt(i) - '0';
            if (addr > 0 && addr <= 255) {
                buckets.add(addr);
                backTrack(buckets, bucket + 1, i + 1, end, str);
                buckets.remove(bucket);
            } else
                break;
        }
    }

    //leetcode 986
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        int i = 0;
        int j = 0;
        LinkedList<int[]> result = new LinkedList<>();
        while (i < firstList.length && j < secondList.length) {
            int[] g1 = firstList[i];
            int[] g2 = secondList[j];
            if (g1[1] >= g2[0] && g1[0] <= g2[1]) {
                result.add(new int[]{Math.max(g1[0], g2[0]), Math.min(g1[1], g2[1])});
            }
            if (g1[1] > g2[1])
                j++;
            else
                i++;
        }
        int[][] res = new int[result.size()][2];
        for (int p = 0; p < result.size(); p++) {
            res[p][0] = result.get(p)[0];
            res[p][1] = result.get(p)[1];
        }
        return res;
    }

    @Test
    public void test() {
        System.out.println(myAtoi(" "));
    }

    //leetcode 56
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] ints, int[] t1) {
                if (ints[0] == t1[0])
                    return t1[1] - ints[1];
                return ints[0] - t1[0];
            }
        });
        LinkedList<int[]> result = new LinkedList<>();
        result.add(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            int[] gap1 = result.pollLast();
            int[] gap2 = intervals[i];
            if (gap1[1] >= gap2[0] && gap1[1] < gap2[1]) {
                result.add(new int[]{gap1[0], gap2[1]});
                continue;
            } else if (gap1[0] <= gap2[0] && gap1[1] >= gap2[1]) {
                result.add(gap1);
            } else {
                result.add(gap1);
                result.add(gap2);
            }
        }
        int[][] res = new int[result.size()][2];
        for (int i = 0; i < result.size(); i++) {
            res[i][0] = result.get(i)[0];
            res[i][1] = result.get(i)[1];
        }
        return res;
    }

    //leetcode 1288
    public int removeCoveredIntervals(int[][] intervals) {
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] ints, int[] t1) {
                if (ints[0] == t1[0])
                    return t1[1] - ints[1];
                return ints[0] - t1[0];
            }
        });
        int toDelete = 0;
        int left = intervals[0][0];
        int right = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            int[] intv = intervals[i];
            if (left <= intv[0] && right >= intv[1])
                toDelete++;
            if (right >= intv[0] && right < intv[1])
                right = intv[1];
            if (right < intv[0]) {
                left = intv[0];
                right = intv[1];
            }
        }
        return intervals.length - toDelete;
    }

    //求(10,limit)范围内的水仙花数
    //求可累积股数的买股票两次的最大收益

    //leetcode 49 *
    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, LinkedList<String>> map = new HashMap<>();
        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String s = Arrays.toString(chars);
            if (map.containsKey(s))
                map.get(s).add(str);
            else {
                map.put(s, new LinkedList<>());
                map.get(s).add(str);
            }
        }
        return new LinkedList<>(map.values());
    }

    //leetcode 18
    public List<List<Integer>> fourSum(int[] nums, int target) {
        LinkedList<List<Integer>> result = new LinkedList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 3; i++) {
            List<List<Integer>> lists = threeSum(nums, target - nums[i], i + 1);
            for (List<Integer> list : lists)
                list.add(nums[i]);
            result.addAll(lists);
            while (i < nums.length - 4 && nums[i] == nums[i + 1]) i++;
        }
        return result;
    }

    //leetcode 15
    public List<List<Integer>> threeSum(int[] nums, long target, int start) {
        LinkedList<List<Integer>> result = new LinkedList<>();
        //Arrays.sort(nums);
        for (int i = start; i < nums.length - 2; i++) {
            LinkedList<List<Integer>> lists = advancedTwoSum(nums, target - nums[i], i + 1);
            for (List<Integer> list : lists)
                list.add(nums[i]);
            result.addAll(lists);
            while (i < nums.length - 3 && nums[i] == nums[i + 1]) i++;
        }
        return result;
    }

    public LinkedList<List<Integer>> advancedTwoSum(int[] nums, long target, int start) {
        int low = start, high = nums.length - 1;
        LinkedList<List<Integer>> result = new LinkedList<>();
        while (low < high) {
            int left = nums[low], right = nums[high];
            long sum = left + right;
            if (sum < target) {
                while (low < high && nums[low] == left) low++;
            } else if (sum > target) {
                while (low < high && nums[high] == right) high--;
            } else {
                LinkedList<Integer> res = new LinkedList<>();
                res.add(left);
                res.add(right);
                result.add(res);
                while (low < high && nums[low] == left) low++;
                while (low < high && nums[high] == right) high--;
            }
        }
        return result;
    }

    //leetcode 1
    public int[] twoSum(int[] nums, int target) {
        int[] originalNums = Arrays.copyOf(nums, nums.length);
        Arrays.sort(nums);
        int p = 0;
        int q = nums.length - 1;
        while (p < q) {
            if (nums[p] + nums[q] > target) {
                q--;
            } else if (nums[p] + nums[q] < target) {
                p++;
            } else {
                int[] res = new int[2];
                boolean flag1 = false;
                boolean flag2 = false;
                for (int i = 0; i < originalNums.length; i++) {
                    if (originalNums[i] == nums[p] && !flag1) {
                        res[0] = i;
                        flag1 = true;
                        continue;
                    }
                    if (originalNums[i] == nums[q] && !flag2) {
                        res[1] = i;
                        flag2 = true;
                    }
                }
                return res;
            }
        }
        return null;
    }
}
