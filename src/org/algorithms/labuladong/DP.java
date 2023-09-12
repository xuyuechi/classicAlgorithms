package org.algorithms.labuladong;

import org.junit.Test;

import java.util.*;

//悟剑篇 4.1 动态规划核心原理 4.2 经典动态规划 4.3 背包问题 4.4 用动态规划玩游戏
public class DP {
    @Test
    public void test2(){
        ArrayList<String> objects = new ArrayList<>();
        objects.add("cats");
        objects.add("dog");
        objects.add("and");
        objects.add("sand");
        objects.add("cat");
        objects.add("bitch");
        wordBreak("catsandog",objects);
    }

    private HashMap<String,Boolean> cache;

    public boolean wordBreak(String s, List<String> wordDict) {
        cache = new HashMap<>();
        return recursiveWordBreak(new StringBuilder(),wordDict,s);
    }

    public boolean recursiveWordBreak(StringBuilder chosen,List<String> wordDict,String s){
        if(chosen.toString().equals(s)){
            return true;
        }
        if(chosen.length()>s.length())
            return false;
        if(cache.containsKey(chosen.toString()))
            return cache.get(chosen.toString());
        for(int i=0;i<wordDict.size();i++){
            chosen.append(wordDict.get(i));
            boolean res = recursiveWordBreak(chosen,wordDict,s);
            if(!res){
                cache.put(chosen.toString(),false);
            }
            else
                return true;
            chosen.delete(chosen.length()-wordDict.get(i).length(),chosen.length());
        }
        return false;
    }

    //leetcode 221
    public int maximalSquare(char[][] matrix) {
        int height = matrix.length;
        int width = matrix[0].length;
        int[][] dp = new int[height][width];
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                if(i>0 && j>0 && matrix[i][j] == '1'){
                    dp[i][j] = Math.min(dp[i-1][j],Math.min(dp[i][j-1],dp[i-1][j-1]))+1;
                }
                else if(matrix[i][j] == '1')
                    dp[i][j] = 1;
                else
                    dp[i][j] = 0;
            }
        }
        int max = Integer.MIN_VALUE;
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                max = Math.max(max,dp[i][j]);
            }
        }
        return max*max;
    }

    //leetcode 63
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if(obstacleGrid[0][0]==1 || obstacleGrid[obstacleGrid.length-1][obstacleGrid[0].length-1] == 1)
            return 0;
        int height = obstacleGrid.length;
        int width = obstacleGrid[0].length;
        int[][] dp = new int[height][width];
        dp[0][0] = 1;
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                if(i==0 && j==0)
                    continue;
                if(i>0 && j>0 && obstacleGrid[i-1][j]!=1 && obstacleGrid[i][j-1]!=1)
                    dp[i][j] = dp[i-1][j] + dp[i][j-1];
                else if((i == 0 || obstacleGrid[i - 1][j] == 1) && (j>0 && obstacleGrid[i][j-1]!=1))
                    dp[i][j] = dp[i][j-1];
                else if((j==0 || obstacleGrid[i][j-1]==1) && (i>0 && obstacleGrid[i-1][j]!=1))
                    dp[i][j] = dp[i-1][j];
                else
                    dp[i][j] = 0;
            }
        }
        return dp[height-1][width-1];
    }

    //leetcode 740
    public int deleteAndEarn(int[] nums) {
        if(nums.length==1){
            return nums[0];
        }
        Arrays.sort(nums);
        int[] sum = new int[nums[nums.length-1]+1];
        for(int i=0;i<nums.length;i++){
            sum[nums[i]]+=nums[i];
        }
        int[] dp = new int[sum.length];
        dp[0] = 0;
        dp[1] = sum[1];
        for(int i=2;i<sum.length;i++){
            dp[i] = Math.max(dp[i-1],dp[i-2] + sum[i]);
        }
        return dp[sum.length-1];
    }

    //leetcode 746
    public int minCostClimbingStairs(int[] cost) {
        int len = cost.length;
        int[] dp = new int[len+1];
        dp[0] = 0;
        dp[1] = 0;
        for(int i=2;i<=len;i++){
            dp[i] = Math.min(dp[i-1] + cost[i-1],dp[i-2] + cost[i-2]);
        }
        return dp[len];
    }

    //leetcode 1137
    public int tribonacci(int n) {
        if(n==0)
            return 0;
        if(n==1 || n==2)
            return 1;
        int[] dp = new int[n+1];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 1;
        for(int i=3;i<=n;i++){
            dp[i] = dp[i-1] + dp[i-2] + dp[i-3];
        }
        return dp[n];
    }

    //leetcode 174
    private int[][] memoHP;
    public int calculateMinimumHP(int[][] dungeon) {
        int height = dungeon.length;
        int width = dungeon[0].length;
        memoHP = new int[height][width];
        for(int i=0;i<height;i++)
            Arrays.fill(memoHP[i],-1);
        return dpHP(0,0,dungeon,height,width);
    }
    public int dpHP(int i,int j,int[][] grid,int height,int width){
        if(i >= height || j >= width)
            return Integer.MAX_VALUE;
        if(i==height-1 && j==width-1)
            return grid[i][j] <= 0 ? -grid[i][j] + 1:1;
        if(memoHP[i][j] != -1)
            return memoHP[i][j];
        int res = Math.min(dpHP(i+1,j,grid,height,width),dpHP(i,j+1,grid,height,width)) - grid[i][j];
        memoHP[i][j] = res <= 0 ? 1:res;
        return memoHP[i][j];
    }

    private int[][] memory;
    //leetcode 887
    public int superEggDrop(int k, int n) {
        memory = new int[k+1][n+1];
        for(int i=0;i<=k;i++){
            Arrays.fill(memory[i],-1);
        }
        return eggRecursion(k,n);
    }

    public int eggRecursion(int eggs,int floors){
        if(eggs==1)
            return floors;
        if(floors == 0)
            return 0;
        if(memory[eggs][floors]!=-1)
            return memory[eggs][floors];
        int res = Integer.MAX_VALUE;
        for(int i=1;i<=floors;i++){
            res = Math.min(res,Math.max(eggRecursion(eggs,floors-i),eggRecursion(eggs-1,i-1))+1);
        }
        memory[eggs][floors] = res;
        return res;
    }

    //leetcode 64
    public int minPathSum(int[][] grid) {
        int width = grid[0].length;
        int height = grid.length;
        int[][] dp = new int[height+1][width+1];
        for(int i=0;i<=height;i++){
            dp[i][0] = 100000;
        }
        for(int i=0;i<=width;i++){
            dp[0][i] = 100000;
        }
        dp[1][0] = 0;
        dp[0][1] = 0;
        for(int i=1;i<=height;i++){
            for(int j=1;j<=width;j++){
                dp[i][j] = Math.min(dp[i-1][j] + grid[i-1][j-1],dp[i][j-1]+grid[i-1][j-1]);
            }
        }
        return dp[height][width];
    }

    //leetcode 877
    public boolean stoneGame(int[] piles) {
        return PredictTheWinner(piles);
    }

    //leetcode 486
    class Player{
        public int fir;
        public int sec;
        public Player(){}
        public Player(int f,int s){
            fir = f;
            sec = s;
        }
    }
    public boolean PredictTheWinner(int[] nums) {
        int len = nums.length;
        Player[][] dp = new Player[len][len];
        for(int i=0;i<len;i++)
            for(int j=0;j<len;j++)
                dp[i][j] = new Player();
        for(int k=0;k<len;k++){
            dp[k][k].fir = nums[k];
            dp[k][k].sec = 0;
        }
        for(int i=len-2;i>=0;i--){
            for(int j=i+1;j<len;j++){
                if(nums[i]+dp[i+1][j].sec>nums[j]+dp[i][j-1].sec){
                    dp[i][j].fir = nums[i]+dp[i+1][j].sec;
                    dp[i][j].sec = dp[i+1][j].fir;
                }
                else{
                    dp[i][j].fir = nums[j] + dp[i][j-1].sec;
                    dp[i][j].sec = dp[i][j-1].fir;
                }
            }
        }
        return dp[0][len-1].fir - dp[0][len-1].sec >=0;
    }

    //leetcode 188
    public int maxProfit6(int k,int[] prices) {
        int days = prices.length;
        int[][][] dp = new int[days + 1][k + 1][2];
        for (int p = 0; p <= k; p++) {
            dp[0][p][0] = 0;
            dp[0][p][1] = Integer.MIN_VALUE;
        }
        for (int i = 1; i <= days; i++) {
            dp[i][0][1] = Integer.MIN_VALUE;
            dp[i][0][0] = 0;
        }
        for (int i = 1; i <= days; i++) {
            for (int p = 1; p <= k; p++) {
                dp[i][p][0] = Math.max(dp[i - 1][p][0], dp[i - 1][p][1] + prices[i - 1]);
                dp[i][p][1] = Math.max(dp[i - 1][p][1], dp[i - 1][p - 1][0] - prices[i - 1]);
            }
        }
        return dp[days][k][0];
    }

    //leetcode 123
    public int maxProfit5(int[] prices) {
        int days = prices.length;
        int k = 2;
        int[][][] dp = new int[days + 1][k + 1][2];
        for (int p = 0; p <= k; p++) {
            dp[0][p][0] = 0;
            dp[0][p][1] = Integer.MIN_VALUE;
        }
        for (int i = 1; i <= days; i++) {
            dp[i][0][1] = Integer.MIN_VALUE;
            dp[i][0][0] = 0;
        }
        for (int i = 1; i <= days; i++) {
            for (int p = 1; p <= k; p++) {
                dp[i][p][0] = Math.max(dp[i - 1][p][0], dp[i - 1][p][1] + prices[i - 1]);
                dp[i][p][1] = Math.max(dp[i - 1][p][1], dp[i - 1][p - 1][0] - prices[i - 1]);
            }
        }
        return dp[days][k][0];
    }

    //leetcode 714
    public int maxProfit4(int[] prices,int fee){
        int days = prices.length;
        //int k = days - 1;
        int[][] dp = new int[days + 1][2];
        dp[0][0] = 0;
        dp[0][1] = Integer.MIN_VALUE;
        for (int i = 1; i <= days; i++) {
            dp[i][1] = Integer.MIN_VALUE;
            dp[i][0] = 0;
        }
        for (int i = 1; i <= days; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i - 1]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i - 1]-fee);
        }
        return dp[days][0];
    }

    //leetcode 309
    public int maxProfit3(int[] prices){
        int days = prices.length;
        int[][][] dp = new int[days + 1][2][2]; //0:冷冻期，1:非冷冻期
        for (int p = 0; p < 2; p++) {
            dp[0][p][0] = 0;
            dp[0][p][1] = Integer.MIN_VALUE;
        }
        for (int i = 1; i <= days; i++) {
            dp[i][0][1] = Integer.MIN_VALUE;
        }
        for (int i = 1; i <= days; i++) {
            dp[i][1][0] = Math.max(dp[i-1][1][0],dp[i-1][0][0]);
            dp[i][1][1] = Math.max(dp[i-1][1][0]-prices[i-1],dp[i-1][1][1]);
            dp[i][0][0] = dp[i-1][1][1]+prices[i-1];
        }
        return Math.max(dp[days][1][0],dp[days][0][0]);
    }

    //leetcode 122
    public int maxProfit2(int[] prices) {
        int days = prices.length;
        int k = days - 1;
        int[][] dp = new int[days + 1][2];
        dp[0][0] = 0;
        dp[0][1] = Integer.MIN_VALUE;
        for (int i = 1; i <= days; i++) {
            dp[i][1] = Integer.MIN_VALUE;
            dp[i][0] = 0;
        }
        for (int i = 1; i <= days; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i - 1]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i - 1]);
        }
        return dp[days][0];
    }

    //leetcode 121
    public int maxProfit(int[] prices) {
        int days = prices.length;
        int k = 1;
        int[][][] dp = new int[days + 1][k + 1][2];
        for (int p = 0; p <= k; p++) {
            dp[0][p][0] = 0;
            dp[0][p][1] = Integer.MIN_VALUE;
        }
        for (int i = 1; i <= days; i++) {
            dp[i][0][1] = Integer.MIN_VALUE;
            dp[i][0][0] = 0;
        }
        for (int i = 1; i <= days; i++) {
            for (int p = 1; p <= k; p++) {
                dp[i][p][0] = Math.max(dp[i - 1][p][0], dp[i - 1][p][1] + prices[i - 1]);
                dp[i][p][1] = Math.max(dp[i - 1][p][1], dp[i - 1][p - 1][0] - prices[i - 1]);
            }
        }
        return dp[days][k][0];
    }

    //leetcode 518
    public int change2(int amount, int[] coins) {
        int coinTypes = coins.length;
        int[][] dp = new int[coinTypes + 1][amount + 1];
        for (int i = 0; i <= coinTypes; i++) {
            dp[i][0] = 1;
        }
        for (int j = 1; j <= amount; j++) {
            dp[0][j] = 0;
        }
        for (int i = 1; i <= coinTypes; i++) {
            for (int j = 1; j <= amount; j++) {
                if (j - coins[i - 1] >= 0)
                    dp[i][j] = dp[i - 1][j] + dp[i][j - coins[i - 1]];
                else
                    dp[i][j] = dp[i - 1][j];
            }
        }
        return dp[coinTypes][amount];
    }

    //leetcode 416
    public boolean canPartition(int[] nums) {
        int total = Arrays.stream(nums).sum();
        if (total % 2 != 0)
            return false;
        int bagSize = total / 2;
        int numSize = nums.length;
        boolean[][] dp = new boolean[numSize + 1][bagSize + 1];
        for (int i = 0; i <= numSize; i++)
            dp[i][0] = true;
        for (int j = 1; j <= bagSize; j++)
            dp[0][j] = false;
        for (int i = 1; i <= numSize; i++) {
            for (int j = 1; j <= bagSize; j++) {
                if (j - nums[i - 1] >= 0)
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i - 1]];
                else
                    dp[i][j] = dp[i - 1][j];
            }
        }
        return dp[numSize][bagSize];
    }

    private HashMap<String, Boolean> mem;

    //leetcode 10
    public boolean isMatch(String s, String p) {
        mem = new HashMap<>();
        return dp10(s, 0, p, 0);
    }

    public boolean dp10(String s, int i, String p, int j) {
        if (i == s.length()) {
            if ((p.length() - j) % 2 != 0)
                return false;
            for (; j + 1 < p.length(); j += 2)
                if (p.charAt(j + 1) != '*')
                    return false;
            return true;
        }
        if (j == p.length())
            return false;
        if (mem.containsKey(i + "" + j))
            return mem.get(i + "" + j);
        boolean res;
        if (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.') {
            if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
                res = dp10(s, i, p, j + 2) || dp10(s, i + 1, p, j);
            } else
                res = dp10(s, i + 1, p, j + 1);
        } else {
            if (j + 1 < p.length() && p.charAt(j + 1) == '*')
                res = dp10(s, i, p, j + 2);
            else
                res = false;
        }
        mem.put(i + "" + j, res);
        return res;
    }

    //leetcode 712
    public int minimumDeleteSum(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];
        dp[0][0] = 0;
        dp[1][0] = s1.charAt(0);
        for (int i = 2; i <= len1; i++) {
            dp[i][0] = s1.charAt(i - 1) + dp[i - 1][0];
        }
        dp[0][1] = s2.charAt(0);
        for (int j = 2; j <= len2; j++) {
            dp[0][j] = s2.charAt(j - 1) + dp[0][j - 1];
        }
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1];
                else {
                    dp[i][j] = Math.min(dp[i - 1][j] + s1.charAt(i - 1), dp[i][j - 1] + s2.charAt(j - 1));
                }
            }
        }
        return dp[len1][len2];
    }

    //leetcode 583
    public int minDistance583(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();
        int lcs = longestCommonSubsequence(word1, word2);
        return (len1 - lcs) + (len2 - lcs);
    }

    //leetcode 1143
    public int longestCommonSubsequence(String text1, String text2) {
        int len1 = text1.length();
        int len2 = text2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];
        for (int i = 0; i <= len1; i++) {
            dp[i][0] = 0;
        }
        for (int j = 0; j <= len2; j++) {
            dp[0][j] = 0;
        }
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[len1][len2];
    }

    //leetcode 72 最小编辑距离
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i < (m + 1); i++) {
            dp[i][0] = i;
        }
        for (int i = 0; i < (n + 1); i++) {
            dp[0][i] = i;
        }
        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                    continue;
                }
                dp[i][j] = Math.min(dp[i - 1][j - 1] + 1, Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1));
            }
        }
        return dp[m][n];
    }

    //leetcode 354
    public int maxEnvelopes(int[][] envelopes) {
        int envs = envelopes.length;
        int[] dp = new int[envs];
        Arrays.fill(dp, 1);
        Arrays.sort(envelopes, new Comparator<int[]>() {
            @Override
            public int compare(int[] ints, int[] t1) {
                return ints[0] == t1[0] ? t1[1] - ints[1] : ints[0] - t1[0];
            }
        });
        int[] heights = new int[envs];
        for (int i = 0; i < envs; i++) {
            heights[i] = envelopes[i][1];
        }
        return advancedLengthOfLIS(heights);
    }

    public int advancedLengthOfLIS(int[] nums) {
        int[] top = new int[nums.length + 1];
        int piles = 0;
        for (int i = 0; i < nums.length; i++) {
            int poker = nums[i];
            int left = 0, right = piles;
            while (left <= right) {
                int mid = (left + right) / 2;
                if (top[mid] > poker) {
                    right = mid - 1;
                } else if (top[mid] < poker) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            if (left > piles) piles++;
            top[left] = poker;
        }
        return piles;
    }

    //leetcode 300
    public int lengthOfLIS(int[] nums) {
        int len = nums.length;
        int[] dp = new int[len];
        Arrays.fill(dp, 1);
        for (int i = 1; i < nums.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (nums[j] < nums[i])
                    dp[i] = Math.max(dp[i], dp[j] + 1);
            }
        }
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < len; i++) {
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    //leetcode 931
    public int minFallingPathSum(int[][] matrix) {
        int n = matrix.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            dp[0][i] = matrix[0][i];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j == 0)
                    dp[i][j] = Math.min(dp[i - 1][j] + matrix[i][j], dp[i - 1][j + 1] + matrix[i][j]);
                else if (j == n - 1)
                    dp[i][j] = Math.min(dp[i - 1][j] + matrix[i][j], dp[i - 1][j - 1] + matrix[i][j]);
                else
                    dp[i][j] = Math.min(dp[i - 1][j] + matrix[i][j], Math.min(dp[i - 1][j - 1] + matrix[i][j], dp[i - 1][j + 1] + matrix[i][j]));
            }
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            if (dp[n - 1][i] < min)
                min = dp[n - 1][i];
        }
        return -1;
    }

    private int[] memo;

    //leetcode 322
    public int coinChangeUpToDown(int[] coins, int amount) {
        memo = new int[amount + 1];
        Arrays.fill(memory, -888);
        memo[0] = 0;
        return recursionToCoins(coins, amount);
    }

    public int recursionToCoins(int[] coins, int amount) {
        if (amount == 0) return 0;
        if (amount < 0) return -1;
        if (memo[amount] != -888)
            return memo[amount];
        int res = Integer.MAX_VALUE;
        for (int j = 0; j < coins.length; j++) {
            int subProblem = recursionToCoins(coins, amount - coins[j]);
            if (subProblem == -1)
                continue;
            res = Math.min(res, subProblem + 1);
        }
        memo[amount] = res == Integer.MAX_VALUE ? -1 : res;
        return memo[amount];
    }

    //leetcode 322
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            for (int j = 0; j < coins.length; j++) {
                if (i - coins[j] >= 0)
                    dp[i] = Math.min(dp[i - coins[j]] + 1, dp[i]);
            }
        }
        return dp[amount] == amount + 1 ? -1 : dp[amount];
    }

    //leetcode 509
    public int dpFib(int n) {
        if (n == 0 || n == 1)
            return n;
        int[] memo = new int[n + 1];
        memo[0] = 0;
        memo[1] = 1;
        for (int i = 2; i <= n; i++) {
            memo[i] = memo[i - 1] + memo[i - 2];
        }
        return memo[n];
    }

    //leetcode 509
    public int advancedFib(int n) {
        int[] memo = new int[n + 1];
        return cal(n, memo);
    }

    public int cal(int i, int[] memo) {
        if (i == 0 || i == 1)
            return i;
        else {
            if (memo[i] != 0)
                return memo[i];
            else
                return memo[i] = cal(i - 1, memo) + cal(i - 2, memo);
        }
    }

    //leetcode 509
    public int fib(int n) {
        if (n == 0 || n == 1)
            return n;
        else
            return fib(n - 1) + fib(n - 2);
    }
}
