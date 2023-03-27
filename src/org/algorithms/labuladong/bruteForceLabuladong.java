package org.algorithms.labuladong;

import org.junit.Test;

import java.util.*;

//霸剑篇 3.1 DFS/回溯
public class bruteForceLabuladong {

    private boolean[][] visited;

    private int[][] dirs = new int[][]{{0,1},{1,0},{0,-1},{-1,0}};

    //leetcode 1905
    public int countSubIslands(int[][] grid1, int[][] grid2) {
        int m = grid1.length;
        int n = grid1[0].length;
        int count = 0;
        visited = new boolean[m][n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(!visited[i][j] && grid2[i][j] == 1){
                    if(checkSub(grid1,grid2,i,j,m,n))
                        count++;
                }
            }
        }
        return count;
    }

    public boolean checkSub(int [][] grid1,int[][] grid2,int i,int j,int m,int n){
        if(i<0 || i>=m || j<0 || j>=n)
            return true;
        if(visited[i][j])
            return true;
        if(grid2[i][j] == 0)
            return true;
        visited[i][j] = true;
        boolean flag = true;
        if(grid2[i][j] == 1 && grid1[i][j] == 0)
            flag = false;
        for(int[] dir:dirs){
            int nextI = dir[0] + i;
            int nextJ = dir[1] + j;
            flag &= checkSub(grid1, grid2, nextI, nextJ, m, n);
        }
        return flag;
    }

    //leetcode 695
    public int maxAreaOfIsland(int[][] grid) {
        return islandUsingFloodFill(grid);
    }

    //leetcode 1020
    int islandUsingFloodFill(int[][] grid){
        int m = grid.length,n = grid[0].length;
        int count=0;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(grid[i][j] == 1){
                    count=Math.max(count,dfs1020(grid,i,j));
                }
            }
        }
        return count;
    }

    //leetcode 1020
    public int numEnclaves(int[][] grid) {
        return closedIslandUsingFloodFill1020(grid);
    }

    //leetcode 1020
    int closedIslandUsingFloodFill1020(int[][] grid){
        int m = grid.length,n = grid[0].length;
        for(int j=0;j<n;j++){
            dfs1020(grid,0,j);
            dfs1020(grid,m-1,j);
        }
        for(int i=0;i<m;i++){
            dfs1020(grid,i,0);
            dfs1020(grid,i,n-1);
        }
        int count=0;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(grid[i][j] == 1){
                    count+=dfs1020(grid,i,j);
                }
            }
        }
        return count;
    }

    //leetcode 1254 labuladong version
    int closedIslandUsingFloodFill(int[][] grid){
        int m = grid.length,n = grid[0].length;
        for(int j=0;j<n;j++){
            dfs(grid,0,j);
            dfs(grid,m-1,j);
        }
        for(int i=0;i<m;i++){
            dfs(grid,i,0);
            dfs(grid,i,n-1);
        }
        int count=0;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(grid[i][j] == 0){
                    count++;
                    dfs(grid,i,j);
                }
            }
        }
        return count;
    }

    int dfs1020(int[][] grid,int i,int j){
        int m = grid.length,n = grid[0].length;
        if(i < 0 || j < 0 || i>= m || j>= n)
            return 0;
        if(grid[i][j] == 0)
            return 0;
        grid[i][j] = 0;

        int down = dfs1020(grid,i+1,j);
        int right = dfs1020(grid,i,j+1);
        int up = dfs1020(grid,i-1,j);
        int left = dfs1020(grid,i,j-1);
        return left + right + up + down + 1;
    }
    void dfs(int[][] grid,int i,int j){
        int m = grid.length,n = grid[0].length;
        if(i < 0 || j < 0 || i>= m || j>= n)
            return;
        if(grid[i][j] == 1)
            return;
        grid[i][j] = 1;
        dfs(grid,i+1,j);
        dfs(grid,i,j+1);
        dfs(grid,i-1,j);
        dfs(grid,i,j-1);

    }

    //leetcode 1254
    public int closedIslandAI(int[][] grid) {
        int width = grid.length;
        int length = grid[0].length;
        visited = new boolean[width][length];
        dirs = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int count = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if (grid[i][j] == 0 && !visited[i][j]) {
                    if (searchToFindCloseIslandsAI(i, j, width, length, grid)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public boolean searchToFindCloseIslandsAI(int i, int j, int width, int length, int[][] grids) {

        if (i < 0 || i >= width || j < 0 || j >= length)
            return false;
        if (grids[i][j] == 1)
            return true;
        if (visited[i][j])
            return true;
        visited[i][j] = true;
        boolean flag = true;
        for (int k = 0; k < 4; k++) {
            int nextI = i + dirs[k][0];
            int nextJ = j + dirs[k][1];

            flag &= searchToFindCloseIslandsAI(nextI, nextJ, width, length, grids);
        }
        return flag;
    }

    //leetcode 1254
    public int closedIslandMe(int[][] grid) {
        int width = grid.length;
        int length = grid[0].length;
        visited = new boolean[width][length];
        dirs = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int count = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if (grid[i][j] == 0 && !visited[i][j]) {
                    if (searchToFindCloseIslandsMe(i, j, width, length, grid)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public boolean searchToFindCloseIslandsMe(int i, int j, int width, int length, int[][] grids) {

        if (i < 0 || i >= width || j < 0 || j >= length)
            return false;
        if (grids[i][j] == 1)
            return true;
        if (visited[i][j])
            return true;
        visited[i][j] = true;
        for (int k = 0; k < 4; k++) {
            int nextI = i + dirs[k][0];
            int nextJ = j + dirs[k][1];

            if(!searchToFindCloseIslandsMe(nextI, nextJ, width, length, grids))
                return false;
        }
        return true;
    }

    //leetcode 200
    public int numIslands(char[][] grid) {
        int width = grid.length;
        int length = grid[0].length;
        visited = new boolean[width][length];
        int count = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if (grid[i][j] == '1' && !visited[i][j]) {
                    count++;
                    searchToFindIslands(i, j, width, length, grid);
                }
            }
        }
        return count;
    }

    public void searchToFindIslands(int i, int j, int width, int length, char[][] grids) {

        if (i < 0 || i >= width || j < 0 || j >= length)
            return;
        if (visited[i][j])
            return;
        if (grids[i][j] != '1')
            return;
        visited[i][j] = true;
        for (int k = 0; k < 4; k++) {
            int nextI = i + dirs[k][0];
            int nextJ = j + dirs[k][1];
            searchToFindIslands(nextI, nextJ, width, length, grids);
        }
    }

    public LinkedList<List<Integer>> res;

    //leetcode 39
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        res = new LinkedList<>();
        Arrays.sort(candidates);
        int len = candidates.length;
        for (int i = 0; i < len; i++) {
            int temp = candidates[i];
            candidates[i] = candidates[len - i - 1];
            candidates[len - i - 1] = temp;
        }
        backTrack39(candidates, 0, 0, target, new LinkedList<>());
        return res;
    }

    public void backTrack39(int[] nums, int start, int sum, int target, LinkedList<Integer> track) {
        if (sum > target)
            return;
        if (sum == target) {
            res.add(new LinkedList<>(track));
            return;
        }
        for (int i = start; i < nums.length; i++) {
            if (nums[i] > target)
                continue;
            track.addLast(nums[i]);
            sum += nums[i];
            backTrack39(nums, i, sum, target, track);
            track.pollLast();
            sum -= nums[i];
        }
    }

    //leetcode 47
    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        res = new LinkedList<>();
        backTrackToPermute(new LinkedList<>(), nums.length, nums, new boolean[nums.length]);
        return res;
    }

    public void backTrackToPermute(LinkedList<Integer> track, int size, int[] nums, boolean[] used) {
        if (track.size() == size) {
            res.add(new LinkedList<Integer>(track));
            return;
        }
        HashSet<Integer> layer = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (layer.contains(nums[i]) || used[i])
                continue;
            track.addLast(nums[i]);
            used[i] = true;
            layer.add(nums[i]);
            backTrackToPermute(track, size, nums, used);
            used[i] = false;
            track.pollLast();
        }
    }

    //leetcode 40
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        res = new LinkedList<>();
        backTrack40(0, candidates, target, new LinkedList<>(), 0);
        return res;
    }

    public void backTrack40(int start, int[] nums, int target, LinkedList<Integer> track, int sum) {
        if (sum == target) {
            res.add(new LinkedList<>(track));
            return;
        }
        if (sum > target)
            return;
        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1])
                continue;
            track.add(nums[i]);
            sum += nums[i];
            backTrack40(i + 1, nums, target, track, sum);
            track.pollLast();
            sum -= nums[i];
        }
    }

    //leetcode 90
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        res = new LinkedList<>();
        backTrack(0, nums, new LinkedList<>());
        return res;
    }

    public void backTrack(int start, int[] nums, LinkedList<Integer> track) {
        res.add(new LinkedList<>(track));
        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1])
                continue;
            track.add(nums[i]);
            backTrack(i + 1, nums, track);
            track.pollLast();
        }
    }

    private int[] buckets;

    HashMap<Integer, Boolean> memo;

    //leetcode 698
    public boolean canPartitionKSubsets(int[] nums, int k) {
        if (k > nums.length)
            return false;
        buckets = new int[k];
        int sum = Arrays.stream(nums).sum();
        if (sum % k != 0)
            return false;
        memo = new HashMap<>();
        int target = sum / k;
        return advancedBacktrack(k, 0, nums, 0, 0, target);
    }

    public boolean advancedBacktrack(int k, int bucket, int[] nums, int start, int used, int target) {
        if (k == 0) {
            return true;
        }
        if (bucket == target) {
            boolean res = advancedBacktrack(k - 1, 0, nums, 0, used, target);
            memo.put(used, res);
            return res;
        }
        if (memo.containsKey(used))
            return memo.get(used);
        for (int i = start; i < nums.length; i++) {
            if (((used >> i) & 1) == 1)
                continue;
            if (nums[i] + bucket > target)
                continue;
            used |= (1 << i);
            bucket += nums[i];
            if (advancedBacktrack(k, bucket, nums, i + 1, used, target)) {
                return true;
            }
            used ^= (1 << i);
            bucket -= nums[i];
        }
        return false;
    }

    public boolean backtrack(int[] nums, int index, int target) {
        if (index == nums.length) {
            for (int i = 0; i < buckets.length; i++)
                if (buckets[i] != target)
                    return false;
            return true;
        }
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] + nums[index] > target)
                continue;
            buckets[i] += nums[index];
            if (backtrack(nums, index + 1, target)) {
                return true;
            }
            buckets[i] -= nums[index];
        }
        return false;
    }
}
