package org.algorithms.labuladong;

import org.algorithms.sort.ListNode;
import org.algorithms.tree.TreeNode;
import org.junit.Test;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Semaphore;

//朴剑篇 其他
public class Others {

    //广发研2
    public String mul37 (String str1, String str2) {
        return to37(toInt(str1)*toInt(str2));
    }

    public int toInt(String str){
        int num = 0;
        int add = 0;
        for(int i=str.length()-1;i>=0;i--){
            char c = str.charAt(i);
            if(c>='0' && c<='9'){
                add = c - '0';
            }
            if(c>='A' && c<='Z'){
                add = c-'A' + 10;
            }
            if(c=='$'){
                add = 36;
            }
            if(i==str.length()-1){
                num += add;
            }
            else{
                num += Math.pow(37,str.length()-i-1)*add;
            }
        }
        return num;
    }

    public String to37(int num){
        StringBuilder sb = new StringBuilder();
        while(num>0){
            int n = num%37;
            if(n<=9){
                sb.append(n);
            }
            else if(n<=35){
                sb.append((char) ('A'+(n-10)));
            }
            else {
                sb.append('$');
            }
            num = num/37;
        }
        return sb.reverse().toString();
    }

    //广发研1
    public String getTypeOfMobilePhoneNumber (String mobilePhoneNumber) {
        String cm = "ChinaMobile";
        String cu = "ChinaUnicom";
        String ct = "ChinaTelecom";
        String cb = "ChinaBroadNet";
        String illegal = "Invalid";
        if(mobilePhoneNumber.length()>14 || mobilePhoneNumber.length()<11)
            return illegal;
        String prefix = mobilePhoneNumber.substring(0,mobilePhoneNumber.indexOf('1'));
        if(prefix.length()!=0 && !(prefix.equals("860")||prefix.equals("0"))){
            return illegal;
        }
        mobilePhoneNumber = mobilePhoneNumber.substring(mobilePhoneNumber.indexOf('1'));
        String firstStr = mobilePhoneNumber.substring(0,3);
        Integer firstNum = Integer.parseInt(firstStr);
        if(firstNum<=139 && firstNum>=135){
            return cm;
        }
        if(firstNum==147){
            return cm;
        }
        if(firstNum<=159 && firstNum>=157){
            return cm;
        }
        if(firstNum<=152 && firstNum>=150){
            return cm;
        }
        if(firstNum==188 || firstNum==187 || firstNum==182){
            return cm;
        }
        if(firstNum==134 && mobilePhoneNumber.charAt(3)!='9'){
            return cm;
        }

        if(firstNum==133 || firstNum==153 || firstNum==189 || firstNum==180){
            return ct;
        }
        if(mobilePhoneNumber.startsWith("1349")){
            return ct;
        }

        if(firstNum<=132 && firstNum>=130){
            return cu;
        }
        if(firstNum==145){
            return cu;
        }
        if(firstNum==156 || firstNum==155){
            return cu;
        }
        if(firstNum==186 || firstNum==185){
            return cu;
        }

        if(firstNum==190 || firstNum==197 || firstNum==196 || firstNum==192){
            return cb;
        }

        return illegal;
    }

    //360_1
    public boolean transfer(String s,String t){
        if(t.length()<s.length() || (t.length()==s.length() && !s.equals(t)))
            return false;
        int i=0,j=0;
        while(i<s.length() && j<t.length() && s.charAt(i)==t.charAt(j)){
            j++;
            int originalI = i;
            if(j<t.length() && s.charAt(i)!=t.charAt(j)){
                i++;
                while(i<s.length()-1 && s.charAt(i-1)==s.charAt(i))i++;
                for(int k=j-2;k>=j-(i-originalI);k--){
                    if(t.charAt(k)!=t.charAt(k+1))
                        return false;
                }
            }
        }
        if(j>=t.length()-1 && i>=s.length()-1){
            return true;
        }
        return false;
    }
    @Test
    public void test5(){
        System.out.println(mul37("100","1"));
    }

    //去哪儿3
    public double percent (int N, int a, int b) {
        if(a+N-1<b)
            return 1;
        int[] dp = new int[a+N];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= a + N - 1; i++) {
            int groups = 0;
            for (int j = i - 1; j >= 0 && j>=i-N; j--) {
                groups += dp[j];
            }
            if(i<=N)
                groups++;
            dp[i] = groups;
        }
        double fail = 0;
        for (int i = b; i <= a + N - 1; i++) {
            fail += dp[i];
        }
        double success = 0;
        for (int i = a; i < b; i++) {
            success += dp[i];
        }
        System.out.println(success);
        System.out.println(fail);
        return success / (fail+success);
    }

    private int minWay = Integer.MAX_VALUE;

    //去哪儿2
    public int minPath (int[][] paths) {
        dfs(0,0,0,paths.length-1,paths[0].length-1,paths);
        return minWay;
    }

    public void dfs(int x,int y,int value,int xBound,int yBound,int[][] grid){
        value += grid[x][y];
        if(x==xBound && y==yBound){
            minWay = Math.min(minWay,value);
            return;
        }
        if(x<xBound){
            dfs(x+1,y,value,xBound,yBound,grid);
        }
        if(y<yBound){
            dfs(x,y+1,value,xBound,yBound,grid);
        }
    }



    //去哪儿1
    public String maxSubsequenceAndReverse (String string1, String string2) {
        int len1 = string1.length();
        int len2 = string2.length();
        StringBuilder[][] dp = new StringBuilder[len1+1][len2+1];

        for(int i=0;i<=len1;i++){
            dp[i][0] = new StringBuilder();
        }

        for(int j=0;j<=len2;j++){
            dp[0][j] = new StringBuilder();
        }

        for(int i=1;i<=len1;i++){
            for(int j=1;j<=len2;j++){
                if(string1.charAt(i-1)==string2.charAt(j-1)){
                    dp[i][j] = new StringBuilder(dp[i-1][j-1]);
                    dp[i][j].append(string1.charAt(i-1));
                }
                else{
                    dp[i][j] = new StringBuilder(dp[i-1][j].length()>dp[i][j-1].length()?dp[i-1][j]:dp[i][j-1]);
                }
            }
        }
        return dp[len1][len2].reverse().toString();
    }

    //奇安信2
    public boolean CheckGameResource (int[] sequence) {
        int len = sequence.length;
        int[] anti_sequence = new int[len];
        for(int i=0;i<len;i++){
            anti_sequence[i] = sequence[i]==1?0:1;
        }
        int[] anti_sum = new int[len];
        int[] sum = new int[len];
        anti_sum[0] = anti_sequence[0];
        sum[0] = sequence[0];
        for(int i=1;i<len;i++){
            anti_sum[i] = anti_sum[i-1]+anti_sequence[i];
            sum[i] = sum[i-1]+sequence[i];
        }
        if(sum[len-1]!=anti_sum[len-1])
            return false;
        for(int i=0;i<len;i++){
            if(sum[i]<anti_sum[i])
                return false;
        }
        return true;
    }

    //奇安信1
    public int maximizeDonations (int[] donations) {
        if(donations.length==0)
            return 0;
        if(donations.length==1)
            return donations[0];
        if(donations.length==2)
            return Math.max(donations[0],donations[1]);
        int[] dp1 = new int[donations.length];
        int[] dp2 = new int[donations.length];

        dp1[0] = donations[0];
        dp1[1] = dp1[0];
        for(int i=2;i<donations.length-1;i++){
            dp1[i] = Math.max(dp1[i-1],dp1[i-2]+donations[i]);
        }
        dp1[donations.length-1] = dp1[donations.length-2];

        dp2[0] = donations[donations.length-1];
        dp2[1] = donations[1] + dp2[0];
        for(int i=2;i<donations.length-2;i++){
            dp2[i] = Math.max(dp2[i-1],dp2[i-2]+donations[i]);
        }
        dp2[donations.length-2] = dp2[donations.length-3];
        return Math.max(dp1[donations.length-1],dp2[donations.length-2]);
    }

    int maxArea = 0;
    int maxCategory = 0;

    //多抓鱼5
    public int categoryOfMaxWarehouseArea (int[][] grid) {
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[0].length;j++){
                dfs(i,j,grid,0,grid[i][j],new boolean[grid.length][grid[0].length]);
            }
        }
        return maxCategory;
    }

    public void dfs(int x,int y,int[][] grid,int size,int category,boolean[][] chosen){
        chosen[x][y] = true;
        size++;
        if(size>maxArea){
            maxArea = size;
            maxCategory = category;
        }
        if(x<grid.length-1 && grid[x+1][y] == category && !chosen[x+1][y]){
            dfs(x+1,y,grid,size,category,chosen);
        }
        if(y<grid[0].length-1 && grid[x][y+1] == category && !chosen[x][y+1]){
            dfs(x,y+1,grid,size,category,chosen);
        }
        if(x>0 && grid[x-1][y] == category && !chosen[x-1][y]){
            dfs(x-1,y,grid,size,category,chosen);
        }
        if(y>0 && grid[x][y-1] == category && !chosen[x][y-1]){
            dfs(x,y-1,grid,size,category,chosen);
        }
    }

    //多抓鱼4
    public int[] numberOfShelves (int N) {
        int nums = (N+1)*N/2;
        int[][] tri = new int[N][N];
        int i=2;
        int dir = 1;
        int nowX = 0;
        int nowY = 0;
        tri[nowX][nowY] = 1;
        while(i<=nums){
            if(dir==1){
                nowX++;
                tri[nowX][nowY] = i;
                if(nowX==N-1 || tri[nowX+1][nowY]!=0){
                    dir = 2;
                    i++;
                    continue;
                }
            }
            if(dir==2){
                nowX--;
                nowY++;
                tri[nowX][nowY] = i;
                if(nowX == 0 || tri[nowX-1][nowY+1]!=0) {
                    i++;
                    dir = 3;
                    continue;
                }
            }
            if(dir == 3){
                nowY--;
                tri[nowX][nowY] = i;
                if(nowY==0 || tri[nowX][nowY-1]!=0)
                    dir = 1;
            }
            i++;
        }
        int[] result = new int[nums];
        int p = 0;
        for(int k=0;k<N;k++){
            for(int j=0;j<N-k;j++){
                result[p] = tri[k][j];
                p++;
            }
        }
        return result;
    }

    //多抓鱼3
    public int max_consistent_book_size (int[][] books) {
        Arrays.sort(books, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if(o1[0]!=o2[0])
                    return o1[0] - o2[0];
                else
                    return o1[1] - o2[1];
            }
        });

        int max = Integer.MIN_VALUE;
        int left = 0;
        int right = 1;
        while(left<right && right<books.length){
            right++;
            if(books[right-1][1]<books[right-2][1]){
                int count = 0;
                int ground = books[right-2][1];
                for(int i=right;i<books.length;i++){
                    if(books[i][0] == books[right-1][0] && books[i][1] >= ground){
                        count++;
                        ground = books[i][1];
                    }
                }
                max = Math.max(max,right-left + count);
            }
            left = right-1;
            max = Math.max(right-left,max);
        }
        return max;
    }

    //多抓鱼2
    public int purchase (int[] nums, int budget) {
        long total = 0;
        for(int i=0;i<nums.length;i++){
            if(nums[i]>budget)
                continue;
            else{
                for(int j=i+1;j<nums.length;j++){
                    if(nums[j]<=budget-nums[i])
                        total++;
                }
            }
        }
        return (int) (total % 1000000007L);
    }

    //多抓鱼1
    public String decompress(String compressed_str) {
        String[] array = new String[compressed_str.length()];
        for(int i=0;i<array.length;i++){
            array[i] = String.valueOf(compressed_str.charAt(i));
        }
        Stack<Integer> left = new Stack<>();
        Stack<Integer> right = new Stack<>();
        for (int i = 0; i < compressed_str.length(); i++) {
            if (compressed_str.charAt(i) == '(')
                left.push(i);
        }

        while (left.size() != 0) {
            Integer i = left.pop();
            Integer j = 0;
            for(int k=i+1;k<array.length;k++){
                if(array[k]!=null && array[k].equals(")")) {
                    j = k;
                    break;
                }
            }
            int times = compressed_str.charAt(j + 1) - '0';
            String str = "";
            String sub = "";
            for(int p = i+1;p<j;p++){
                if(array[p]!=null)
                    sub += array[p];
            }
            for (int k = 0; k < times; k++) {
                str = str+sub;
            }
            array[i] = str;
            for(int p=i+1;p<=j+1;p++){
                array[p] = null;
            }
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<array.length;i++){
            if(array[i]!=null){
                sb.append(array[i]);
            }
        }
        return sb.toString();
    }

    //leetcode 383
    public boolean canConstruct(String ransomNote, String magazine) {
        HashMap<Character, Integer> count = new HashMap<>();
        for (int i = 0; i < magazine.length(); i++) {
            count.put(magazine.charAt(i), count.getOrDefault(magazine.charAt(i), 0) + 1);
        }
        for (int i = 0; i < ransomNote.length(); i++) {
            Character c = ransomNote.charAt(i);
            if (!count.containsKey(c) || count.get(c) == 0)
                return false;
            else
                count.put(c, count.get(c) - 1);
        }
        return true;
    }

    //leetcode 湍流数组
    public int maxTurbulenceSize(int[] arr) {
        if (arr.length == 1 || (arr.length == 2 && arr[1] == arr[0]))
            return 1;
        int left = 0;
        int right = 2;
        boolean dir = arr[left] > arr[right - 1] && arr[left] != arr[right - 1];
        int max = 2;
        while (left < right && right < arr.length) {
            right++;
            boolean d = arr[right - 2] > arr[right - 1] && arr[right - 2] != arr[right - 1];
            if (d != dir) {
                dir = d;
                max = Math.max(right - left, max);
            } else {
                left = right - 2;
                dir = d;
            }
        }
        return max;
    }

    private List<List<Integer>> result;

    //leetcode 216
    public List<List<Integer>> combinationSum3(int k, int n) {
        result = new LinkedList<>();
        backTrack(new LinkedList<>(), 1, k, n);
        return result;
    }

    public void backTrack(LinkedList<Integer> list, int now, int k, int n) {
        if (k == 0) {
            int total = 0;
            for (int i = 0; i < list.size(); i++) {
                total += list.get(i);
            }
            if (total == n)
                result.add(new LinkedList<>(list));
            return;
        }
        for (int i = now; i <= 9; i++) {
            if (i > n)
                return;
            list.add(i);
            backTrack(list, i + 1, k - 1, n);
            list.pollLast();
        }
    }

    //leetcode 56
    public int[][] merge2(int[][] intervals) {
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        ArrayList<int[]> ans = new ArrayList<>();
        for (int i = 0; i < intervals.length; i++) {
            int a = intervals[i][0];
            int b = intervals[i][1];
            while (i < intervals.length - 1 && intervals[i + 1][0] <= b) {
                b = Math.max(intervals[i + 1][1], b);
                i++;
            }
            ans.add(new int[]{a, b});
        }
        int[][] result = new int[ans.size()][2];
        for (int i = 0; i < ans.size(); i++) {
            result[i][0] = ans.get(i)[0];
            result[i][1] = ans.get(i)[1];
        }
        return result;
    }

    //喜马拉雅2
    public int removeDuplicates(int[] nums) {
        int temp = nums[0];
        int len = nums.length;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == temp) {
                len--;
            } else {
                temp = nums[i];
            }
        }
        return len;
    }

    //喜马拉雅1
    public int maxAbsoluteSum(int[] nums) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            for (int j = 1; j <= nums.length; j++) {
                max = Math.max(max, absSum(nums, i, j));
            }
        }
        return max;
    }

    public int absSum(int[] arr, int left, int right) {
        int value = 0;
        for (int i = left; i < right; i++) {
            value += arr[i];
        }
        return Math.abs(value);
    }

    //天翼云3
    public int countSubstring(String a, String b) {
        int count = 0;
        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < a.length(); i++) {
            for (int j = 0; j <= a.length(); j++) {
                if (i >= j)
                    continue;
                String substring = a.substring(i, j);
                if (b.contains(substring) && !set.contains(substring)) {
                    count++;
                    set.add(substring);
                }
            }
        }
        return count;
    }

    @Test
    public void test4() {
        //以下哪几行会报错？为什么?可自行验证
        float a = (float) 5.2e2;
        double b2 = 4.27e-2f;
        int b3 = (int) 4_4_0.1e2;

        System.out.println(a);
        System.out.println(b2);
        System.out.println(b3);
    }

    private int min = Integer.MAX_VALUE;

    private int[] memo;

    //天翼云2
    public int count2(int h) {
        memo = new int[h + 5];
        Arrays.fill(memo, Integer.MAX_VALUE);
        backTrack(0, true, 0, h);
        return min;
    }

    public void backTrack(int now, boolean status, int times, int h) {
        if (now >= h - 1) {
            min = Math.min(min, times);
            return;
        }
        if (times < memo[now]) {
            memo[now] = times;
        } else {
            return;
        }
        if (status)
            backTrack(now + 2, false, times + 1, h);
        backTrack(now + 1, true, times + 1, h);
    }


    public int count(int h) {
        int[][] dp = new int[2][h + 5];
        Arrays.fill(dp[1], 1);//1可以跳，0不可以
        dp[0][0] = 1;
        dp[1][0] = 1;
        dp[0][1] = 1;
        dp[1][1] = 0;
        dp[0][2] = 1;
        dp[1][2] = 0;
        for (int i = 3; i < h + 5; i++) {
            if (dp[0][i - 1] > dp[0][i - 2] && dp[1][i - 2] == 1) {
                dp[0][i] = dp[0][i - 2] + 1;
                dp[1][i] = 0;
            } else if (dp[0][i - 1] == dp[0][i - 2]) {
                dp[0][i] = dp[0][i - 1] + 1;
                dp[1][i] = 1;
            } else {
                dp[0][i] = dp[0][i - 1] + 1;
                dp[1][i] = 1;
            }
        }
        int min = Integer.MAX_VALUE;
        for (int i = h - 1; i <= h + 4; i++) {
            min = Math.min(min, dp[0][i]);
        }
        return min;
    }

    //天翼云1
    public int myCount(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || (c >= 'A' && c <= 'Z' && c != 'A' && c != 'E' && c != 'I' && c != 'O' && c != 'U'))
                count++;
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(f(2));
    }

    public static int f(int value) {
        try {
            return (1 / 0) * value * value;
        } catch (RuntimeException e) {
            return 55;
        } finally {
            return 4;
        }
    }

    //银泰 1
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null)
            return null;
        if (key > root.val)
            root.right = deleteNode(root.right, key);
        if (key < root.val)
            root.left = deleteNode(root.left, key);
        if (key == root.val) {
            if (root.left == null) {
                root = root.right;
            } else if (root.right == null) {
                root = root.left;
            } else {
                TreeNode maxLeft = findMaxLeft(root.right);
                maxLeft.left = root.left;
                root = root.right;
            }
        }
        return root;
    }

    public TreeNode findMaxLeft(TreeNode root) {
        if (root == null)
            return null;
        if (root.left == null)
            return root;
        else
            return findMaxLeft(root.left);
    }


    @Test
    public void test3() {
        System.out.println(Arrays.toString(numberOfShelves(3)));
    }

    public int testExp() {
        int x = 0;
        try {
            throw new IOException();
        } catch (IOException e) {
            return x += 1;
        } finally {
            return x += 2;
        }
    }

    //leetcode 392
    public boolean isSubsequence(String s, String t) {
        char[] sArray = s.toCharArray();
        char[] tArray = t.toCharArray();
        HashMap<Character, ArrayList<Integer>> indexOft = new HashMap<>();
        for (int i = 0; i < tArray.length; i++) {
            if (!indexOft.containsKey(tArray[i])) {
                indexOft.put(tArray[i], new ArrayList<>());
            }
            indexOft.get(tArray[i]).add(i);
        }
        return check(0, -1, indexOft, sArray);
    }

    public boolean check(int launch, int start, HashMap<Character, ArrayList<Integer>> indexOft, char[] s) {
        if (launch >= s.length)
            return true;
        if (!indexOft.containsKey(s[launch]))
            return false;
        ArrayList<Integer> indexes = indexOft.get(s[launch]);
        for (Integer in : indexes) {
            if (in > start)
                return check(launch + 1, in, indexOft, s);
        }
        return false;
    }

    //leetcode 1559
    public boolean containsCycle(char[][] grid) {
        return true;
    }

    public void dfs(char[][] grid, boolean[][] visited, LinkedList<Integer> track, int row, int col) {
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length)
            return;
        visited[row][col] = true;
        track.add(row * grid[0].length + col);
        int[][] dir = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        for (int i = 0; i < 4; i++) {
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
        if (len != len2)
            return false;
        int[] count = new int[26];
        for (int i = 0; i < len; i++) {
            int g = tChars[i] - sChars[i];
            if (g < 0)
                g += 26;
            count[g]++;
        }
        for (int i = 1; i <= 25; i++) {
            if (count[i] == 0)
                continue;
            if (i + (count[i] - 1) * 26 > k)
                return false;
        }
        return true;
    }

    //leetcode 347
    public int[] topKFrequentAdvanced(int[] nums, int k) {
        HashMap<Integer, Integer> counter = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            counter.put(nums[i], counter.getOrDefault(nums[i], 0) + 1);
        }
        ArrayList<Integer>[] buckets = new ArrayList[nums.length + 1];
        for (Map.Entry<Integer, Integer> entry : counter.entrySet()) {
            if (buckets[entry.getValue()] == null)
                buckets[entry.getValue()] = new ArrayList<>();
            buckets[entry.getValue()].add(entry.getKey());
        }
        int[] ans = new int[k];
        for (int i = nums.length - 1; i >= 0; i--) {
            ArrayList<Integer> keys = buckets[i];
            if (keys == null)
                continue;
            for (Integer key : keys) {
                ans[--k] = key;
                if (k == 0)
                    return ans;
            }
        }
        return null;
    }

    //leetcode 347
    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> counter = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            counter.put(nums[i], counter.getOrDefault(nums[i], 0) + 1);
        }
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(counter.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> integerIntegerEntry, Map.Entry<Integer, Integer> t1) {
                return integerIntegerEntry.getValue().compareTo(t1.getValue());
            }
        });
        int[] ans = new int[k];
        int j = 0;
        for (int i = list.size() - 1; i >= list.size() - k; i--) {
            ans[j++] = list.get(i).getKey();
        }
        return ans;
    }

    //leetcode 74
    public boolean searchMatrix(int[][] matrix, int target) {
        return binarySearch(0, matrix.length - 1, matrix, target);
    }

    public boolean binarySearch(int left, int right, int[][] matrix, int target) {
        if (left > right)
            return false;
        int mid = left + (right - left) / 2;
        if ((target >= matrix[mid][0] && target <= matrix[mid][matrix[mid].length - 1]) || left == right) {
            for (int i = 0; i < matrix[mid].length; i++) {
                if (matrix[mid][i] == target)
                    return true;
            }
            return false;
        } else if (target > matrix[mid][matrix[mid].length - 1] && mid < matrix.length - 1) {
            return binarySearch(mid + 1, right, matrix, target);
        } else if (target < matrix[mid][0] && mid > 0) {
            return binarySearch(left, mid - 1, matrix, target);
        } else
            return false;
    }

    //leetcode 120
    public int minimumTotalAdvanced(List<List<Integer>> triangle) {
        int min = Integer.MAX_VALUE;
        int row = triangle.size();
        int column = triangle.get(row - 1).size();
        int[][] dp = new int[row][column];
        dp[0][0] = triangle.get(0).get(0);
        for (int i = 1; i < row; i++) {
            dp[i][0] = dp[i - 1][0] + triangle.get(i).get(0);
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < triangle.get(i).size(); j++) {
                if (triangle.get(i - 1).size() <= j)
                    dp[i][j] = dp[i - 1][j - 1] + triangle.get(i).get(j);
                else
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i - 1][j - 1]) + triangle.get(i).get(j);
            }
        }
        for (int j = 0; j < column; j++) {
            min = Math.min(min, dp[row - 1][j]);
        }
        return min;
    }

    @Test
    public void test2() {
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

    private int min2 = Integer.MAX_VALUE;

    //leetcode 120
    public int minimumTotal(List<List<Integer>> triangle) {
        int minimum = Integer.MAX_VALUE;
        int lastLayer = triangle.size() - 1;
        int[][] memo = new int[triangle.size()][triangle.get(lastLayer).size()];
        for (int[] m : memo) {
            Arrays.fill(m, Integer.MAX_VALUE);
        }
        dp(triangle, 0, 0, memo, 0);
        for (int i = 0; i < triangle.get(lastLayer).size(); i++) {
            minimum = Math.min(memo[lastLayer][i], minimum);
        }
        return minimum;
    }

    public void dp(List<List<Integer>> triangle, int startLayer, int startPoint, int[][] cache, int sum) {
        if (startLayer >= triangle.size() || startPoint == -1 || startPoint >= triangle.get(startLayer).size())
            return;
        List<Integer> list = triangle.get(startLayer);
        int value1 = sum + list.get(startPoint);

        if (cache[startLayer][startPoint] > value1) {
            cache[startLayer][startPoint] = value1;
            dp(triangle, startLayer + 1, startPoint, cache, value1);
            dp(triangle, startLayer + 1, startPoint + 1, cache, value1);
        }

    }

    //leetcode 910
    public int smallestRangeIIAdvanced(int[] nums, int k) {
        int min = Integer.MAX_VALUE;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            min = Math.min(Math.max(nums[i] + k, nums[nums.length - 1] - k) - Math.min(nums[0] + k, nums[(i + 1) >= nums.length ? i : i + 1] - k), min);
        }
        return min;
    }
    //private int min = Integer.MAX_VALUE;

    //leetcode 910
    public int smallestRangeII(int[] nums, int k) {
        dp(nums, k, 0);
        return min;
    }

    public void dp(int[] nums, int k, int start) {
        if (start >= nums.length) {
            min = Math.min(min, gap(nums));
            return;
        }
        nums[start] += k;
        dp(nums, k, start + 1);
        nums[start] -= k;

        nums[start] -= k;
        dp(nums, k, start + 1);
        nums[start] += k;
    }

    public int gap(int[] nums) {
        int b = Integer.MIN_VALUE;
        int s = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            b = Math.max(b, nums[i]);
            s = Math.min(s, nums[i]);
        }
        return b - s;
    }

    private int[] memo2;

    //leetcode 2500
    public int deleteGreatestValue(int[][] grid) {
        int maxTimes = grid[0].length;
        int answer = 0;
        for (int i = 0; i < grid.length; i++) {
            Arrays.sort(grid[i]);
        }
        for (int j = 0; j < maxTimes; j++) {
            int toAdd = Integer.MIN_VALUE;
            for (int i = 0; i < grid.length; i++) {
                toAdd = Math.max(toAdd, grid[i][maxTimes - j - 1]);
            }
            answer += toAdd;
        }
        return answer;
    }

    //leetcode 55
    public boolean canJumpAdvanced(int[] nums) {
        int k = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > k)
                return false;
            else
                k = Math.max(k, i + nums[i]);
        }
        return true;
    }

    //leetcode 55
    public boolean canJump(int[] nums) {
        memo = new int[nums.length];
        return dfs(nums, 0, nums.length - 1);
    }

    public boolean dfs(int[] nums, int start, int target) {
        if (target == start) {
            memo[start] = 1;
            return true;
        }
        if (start > target || nums[start] == 0) {
            memo[start] = -1;
            return false;
        }
        if (memo[start] != 0)
            return memo[start] == 1;
        boolean result = false;
        for (int i = 0; i < nums[start]; i++) {
            result |= dfs(nums, start + i + 1, target);
            memo[start + i + 1] = result ? 1 : -1;
            if (result)
                break;
        }
        return result;
    }

    //leetcode 45
    public int jump(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 1; i < nums.length; i++) {
            for (int j = 1; i + j <= nums.length && j <= nums[i - 1]; j++) {
                dp[i + j - 1] = Math.min(dp[i + j - 1], dp[i - 1] + 1);
            }
        }
        return dp[nums.length - 1];
    }

    //leetcode 617
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        TreeNode newRoot = null;
        if (root1 == null && root2 == null)
            return newRoot;
        else if (root1 == null && root2 != null) {
            newRoot = root2;
            return newRoot;
        } else if (root1 != null && root2 == null) {
            newRoot = root1;
            return newRoot;
        } else
            newRoot = new TreeNode(root1.val + root2.val);
        newRoot.left = mergeTrees(root1.left, root2.left);
        newRoot.right = mergeTrees(root1.right, root2.right);
        return newRoot;
    }


    //leetcode 100
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null)
            return true;
        if (p == null || q == null)
            return false;
        if (p.val != q.val)
            return false;
        else
            return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    //leetcode 66
    public int[] plusOne(int[] digits) {
        boolean forward = true;
        for (int i = digits.length - 1; i >= 0; i--) {
            if (forward) {
                digits[i]++;
                forward = false;
            }
            if (digits[i] == 10) {
                forward = true;
                digits[i] = 0;
            }
            if (i == 0 && forward) {
                int[] newDigit = new int[digits.length + 1];
                System.arraycopy(digits, 0, newDigit, 1, digits.length);
                newDigit[0] = 1;
                return newDigit;
            }
        }
        return digits;
    }

    //leetcode 35
    public int searchInsert(int[] nums, int target) {
        return binarySearch(0, nums.length - 1, nums, target);
    }

    public int binarySearch(int left, int right, int[] nums, int target) {
        if (left > right)
            return left;
        int mid = left + (right - left) / 2;
        if (target == nums[mid])
            return mid;
        if (target > nums[mid])
            return binarySearch(mid + 1, right, nums, target);
        else
            return binarySearch(left, mid - 1, nums, target);

    }

    //leetcode 421 代码随想录
    public int findMaximumXOR(int[] nums) {
        return 0;
    }

    //leetcode 101
    public boolean isSymmetric(TreeNode root) {
        return checkSymm(root.left, root.right);
    }

    public boolean checkSymm(TreeNode left, TreeNode right) {
        if (left == null && right == null)
            return true;
        if (left == null || right == null)
            return false;
        if (left.val != right.val)
            return false;
        return checkSymm(left.left, right.right) && checkSymm(left.right, right.left);
    }

    //leetcode 43
    public String multiply(String num1, String num2) {
        return null;
    }

    //leetcode 165
    public int compareVersion(String version1, String version2) {
        String[] ver1 = version1.split("\\.");
        String[] ver2 = version2.split("\\.");
        int len = Math.max(ver1.length, ver2.length);
        int[] v1 = new int[len];
        int[] v2 = new int[len];
        for (int i = 0; i < len; i++) {
            if (ver1.length > i)
                v1[i] = Integer.parseInt(ver1[i]);
            else
                v1[i] = 0;
            if (ver2.length > i)
                v2[i] = Integer.parseInt(ver2[i]);
            else
                v2[i] = 0;
        }
        for (int i = 0; i < len; i++) {
            if (v1[i] > v2[i])
                return 1;
            else if (v1[i] < v2[i])
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
            if (deputy.size() > 0 && val > getMin()) {
                deputy.push(getMin());
            } else {
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
        ListNode pre = head, p = head;
        for (int i = 0; i < k - 1; i++) {
            pre = pre.next;
        }
        while (pre.next != null) {
            pre = pre.next;
            p = p.next;
        }
        return p;
    }

    //leetcode 8 *
    public int myAtoi(String s) {
        String str = s.trim();
        if (str.length() == 0)
            return 0;
        StringBuilder sb = new StringBuilder();
        boolean negative = false;
        int start = 0;
        if (str.charAt(0) == '-') {
            negative = true;
            start = 1;
        } else if (str.charAt(0) == '+') {
            negative = false;
            start = 1;
        } else if (str.charAt(0) < 48 || str.charAt(0) > 57) {
            return 0;
        }
        int pov = start;
        char ch;
        while (pov < str.length() && (ch = str.charAt(pov)) >= 48 && ch <= 57) {
            sb.append(ch);
            pov++;
        }
        String ansStr = sb.toString().replaceFirst("^0+", "");
        if (ansStr.length() == 0)
            return 0;
        if (ansStr.length() > 10)
            return negative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        Long value = Long.parseLong(ansStr);
        if (value > 2147483647L && !negative)
            return Integer.MAX_VALUE;
        else if (value > 2147483648L && negative)
            return Integer.MIN_VALUE;
        if (value == 2147483648L && negative)
            return Integer.MIN_VALUE;
        Integer realValue = Integer.parseInt(ansStr);
        return negative ? (-1) * (realValue) : (realValue);
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
            int total = (p1.val + p2.val + (forward ? 1 : 0)) % 10;
            pointer.val = total;
            if (p1.val + p2.val + (forward ? 1 : 0) >= 10)
                forward = true;
            else
                forward = false;
            p1 = p1.next;
            p2 = p2.next;
        }
        if (p1 == null && p2 == null && forward) {
            pointer.next = new ListNode(1);
            return dummy.next;
        }
        if (p1 == null && p2 == null)
            return dummy.next;
        pointer.next = p1 == null ? p2 : p1;
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
    private List<String> result2;

    public List<String> restoreIpAddresses(String s) {
        LinkedList<Integer> buckets = new LinkedList<>();
        result2 = new LinkedList<>();
        backTrack(buckets, 0, 0, s.length(), s);
        return result2;
    }

    public void backTrack(List<Integer> buckets, int bucket, int start, int end, String str) {
        if (bucket == 4 && start == end) {
            StringBuilder r = new StringBuilder();
            for (Integer integer : buckets) {
                r.append(integer);
                r.append(".");
            }
            r.deleteCharAt(r.length() - 1);
            result2.add(r.toString());
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

    //1996 time limit exceeded
    public int numberOfWeakCharacters2(int[][] properties) {
        int count = 0;
        Arrays.sort(properties, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if(o1[0]!=o2[0])
                    return o1[0] - o2[0];
                else
                    return o1[1] - o2[1];
            }
        });
        for(int i=0;i<properties.length-1;i++){
            for(int j=i+1;j<properties.length;j++){
                if(properties[i][0] < properties[j][0] && properties[i][1] < properties[j][1]){
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    //1996
    public int numberOfWeakCharacters(int[][] properties) {
        int count = 0;
        Arrays.sort(properties, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if(o1[0]==o2[0])
                    return o1[1] - o2[1];
                else
                    return o2[0] - o1[0];
            }
        });
        int maxOne = 0;

        for(int i=0;i<properties.length;i++){
            if(properties[i][1] < maxOne){
                count++;
            } else {
                maxOne = properties[i][1];
            }
        }
        return count;
    }

    @Test
    public void testNumberOfWeakCharacters(){
        int[][] properties = {{1,1},{1,2},{2,1},{2,2}};
        System.out.println(numberOfWeakCharacters(properties));
    }

    @Test
    public void testMatrixBlockSum(){
        int[][] mat = new int[][]{{1,2,3},{4,5,6},{7,8,9}};
        matrixBlockSum(mat,1);
    }

    //1314
    public int[][] matrixBlockSum(int[][] mat, int k) {
        int[][] presum = buildPreSumMatrix(mat);
        int[][] answer = new int[mat.length][mat[0].length];
        for(int i=0;i<mat.length;i++){
            for(int j=0;j<mat[0].length;j++){
                int upleftX = Math.max(i - k, 0);
                int upleftY = Math.max(j - k,0);
                int downRightX = Math.min(i+k,mat.length-1);
                int downRightY = Math.min(j+k,mat[0].length-1);
                answer[i][j] = presum[downRightX+1][downRightY+1] - presum[upleftX][downRightY+1] - presum[downRightX+1][upleftY] + presum[upleftX][upleftY];
            }
        }
        return answer;
    }

    public int[][] buildPreSumMatrix(int[][] mat){
        int[][] presum = new int[mat.length+1][mat[0].length+1];
        for(int i=0;i<presum.length;i++){
            for(int j=0;j<presum[0].length;j++){
                if(i == 0 || j == 0){
                    presum[i][j] = 0;
                    continue;
                }
                presum[i][j] = mat[i-1][j-1] + presum[i][j-1] + presum[i-1][j] - presum[i-1][j-1];
            }
        }
        return presum;
    }

    @Test
    public void testMaxSumRangeQuery(){
        int[][] requests = new int[][]{{1,3},{0,1}};
        int[] nums = new int[]{1,2,3,4,5};
        System.out.println(maxSumRangeQuery(nums,requests));
    }

    //1589
    public int maxSumRangeQuery(int[] nums, int[][] requests) {
        int[] timesDif = new int[nums.length];
        long ans = 0;
        Arrays.fill(timesDif,0);
        for(int i=0;i<requests.length;i++){
            int start = requests[i][0];
            int end = requests[i][1];
            timesDif[start] += 1;
            if(end <= nums.length-2){
                timesDif[end+1] -= 1;
            }
        }
        int[] times = new int[nums.length];
        times[0] = timesDif[0];
        for(int i=1;i<timesDif.length;i++){
            times[i] = times[i-1] + timesDif[i];
        }
        Arrays.sort(nums);
        Arrays.sort(times);
        for(int i=0;i<times.length;i++){
            ans += nums[i] * times[i];
        }
        return (int) (ans % 1000000007L);
    }

    @Test
    public void testMaxSatisfied(){
        int[] customers = new int[]{1,0,1,2,1,1,7,5};
        int[] grumpy = new int[]{0,1,0,1,0,1,0,1};
        System.out.println(maxSatisfied(customers,grumpy,3));
    }

    //1052
    public int maxSatisfied(int[] customers, int[] grumpy, int minutes) {
        int maxIncrease = 0;
        int increase = 0;
        for(int i=0;i<customers.length-minutes+1;i++){
            for(int j=i;j<i+minutes;j++){
                if(grumpy[j] == 1){
                    increase += customers[j];
                }
            }
            maxIncrease = Math.max(maxIncrease,increase);
            increase = 0;
        }
        int total = 0;
        for(int i=0;i<customers.length;i++){
            if(grumpy[i] == 1){
                continue;
            }
            total += customers[i];
        }
        return total + maxIncrease;
    }

    @Test
    public void testIsHappy(){
        longestConsecutive(new int[]{0,3,7,2,5,8,4,6,0,1});
    }

    //202
    public boolean isHappy(int n) {
        if(n == 1)
            return true;
        if(n < 10)
            return false;
        if(n % 10 == 0)
            return true;
        HashSet<Integer> showed = new HashSet<>();
        while(true){
            List<Integer> integers = separateIntoDigits(n);
            int sum = 0;
            for(Integer i:integers){
                sum += i*i;
            }
            if(sum == 1){
                return true;
            }
            if(showed.contains(sum)){
                return false;
            } else {
                showed.add(sum);
                n = sum;
            }
        }
    }

    public List<Integer> separateIntoDigits(int n){
        List<Integer> digits = new ArrayList<>();
        while(n != 0){
            digits.add(n % 10);
            n /= 10;
        }
        return digits;
    }

    //258
    public int addDigits(int num) {
        while(num / 10 != 0){
            int sum = 0;
            while(num != 0){
                sum += num % 10;
                num /= 10;
            }
            if(sum / 10 == 0){
                return sum;
            } else {
                num = sum;
            }
        }
        return num;
    }

    //203
    public ListNode removeElements(ListNode head, int val) {
        if(head.val == val){
            return removeElements(head.next,val);
        } else {
            head.next = removeElements(head.next,val);
            return head;
        }
    }

    //328
    public ListNode oddEvenList(ListNode head) {
        if(head == null || head.next == null)
            return head;
        ListNode oddHead = new ListNode();
        ListNode oddPointer = oddHead;
        ListNode evenHead = new ListNode();
        evenHead.next = head;
        ListNode evenPointer = evenHead.next;
        ListNode p = head.next;
        boolean flag = true;
        while(p!=null){
            if(flag) {
                oddPointer.next = p;
                oddPointer = p;
            } else {
                evenPointer.next = p;
                evenPointer = p;
            }
            p = p.next;
            flag = !flag;
            oddPointer.next = null;
            evenPointer.next = null;
        }
        evenPointer.next = oddHead.next;
        return evenHead.next;
    }

    //234
    /*
    public boolean isPalindrome(ListNode head) {

    }
     */

    //128
    public int longestConsecutive(int[] nums) {
        if(nums.length == 0){
            return 0;
        }
        if(nums.length == 1){
            return 1;
        }
        Arrays.sort(nums);
        System.out.println(nums);
        int maxLen = 1;
        int len = 1;
        for(int i=1;i<nums.length;i++){
            if(nums[i] == nums[i-1]){
                continue;
            }
            if(nums[i] == nums[i-1] + 1){
                len++;
            }
            if(nums[i] > nums[i-1] + 1){
                maxLen = Math.max(len,maxLen);
                len = 1;
            }
        }
        maxLen = Math.max(len,maxLen);
        return maxLen;
    }

    //136
    public int singleNumber(int[] nums) {
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            if(map.containsKey(nums[i])) {
                map.put(nums[i], map.get(nums[i]) + 1);
            } else {
                map.put(nums[i],1);
            }
        }
        for(Map.Entry<Integer,Integer> entry:map.entrySet()){
            if(entry.getValue() == 1){
                return entry.getKey();
            }
        }
        return -1;
    }

}
