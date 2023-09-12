package org.algorithms.labuladong;

import org.algorithms.tree.TreeNode;
import org.junit.Test;

import java.util.*;

//霸剑篇 3.2 BFS
public class BFS {

    public String toS(int[][] board){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<2;i++){
            for(int j=0;j<3;j++)
                sb.append(board[i][j]);
        }
        return sb.toString();
    }
    private HashSet<String> visited;
    //leetcode 773
    public int slidingPuzzle(int[][] board) {
        Queue<String> queue = new LinkedList<>();
        visited = new HashSet<>();
        queue.offer(toS(board));
        int step = 0;
        while(!queue.isEmpty()){
            int size = queue.size();
            for(int i=0;i<size;i++){
                String b = queue.poll();
                if(visited.contains(b))
                    continue;
                if(check(b))
                    return step;
                visited.add(b);
                queue.addAll(move(b));
            }
            step++;
        }
        return -1;
    }

    public LinkedList<String> move(String b){
        int[][] board = new int[2][3];
        for(int i=0;i<2;i++){
            for(int j=0;j<3;j++){
                board[i][j] = Integer.parseInt(String.valueOf(b.charAt(i*3+j)));
            }
        }
        int zeroI = 0;
        int zeroJ = 0;
        for(int i=0;i<2;i++){
            for(int j=0;j<3;j++){
                if(board[i][j] == 0){
                    zeroI = i;
                    zeroJ = j;
                }
            }
        }
        LinkedList<String> results = new LinkedList<>();
        int[][] dir = new int[][]{{0,1},{1,0},{-1,0},{0,-1}};
        for(int []d:dir){
            int nextI = zeroI+d[0];
            int nextJ = zeroJ+d[1];
            if(nextI < 0 || nextI > 1 || nextJ < 0 || nextJ>2)
                continue;
            int temp = board[nextI][nextJ];
            board[nextI][nextJ] = board[zeroI][zeroJ];
            board[zeroI][zeroJ] = temp;
            String tempBoard = toS(board);
            if(!visited.contains(tempBoard))
                results.add(tempBoard);
            temp = board[zeroI][zeroJ];
            board[zeroI][zeroJ] = board[nextI][nextJ];
            board[nextI][nextJ] = temp;
        }
        return results;
    }

    public boolean check(String b){
        if(b.equals("123450"))
            return true;
        else
            return false;
    }

    //leetcode 752
    public int advancedOpenLock(String[] deadends, String target) {
        HashSet<String> visited = new HashSet<>();
        HashSet<String> dead = new HashSet<>();
        dead.addAll(Arrays.asList(deadends));
        HashSet<String> q1 = new HashSet<>();
        HashSet<String> q2 = new HashSet<>();
        q1.add("0000");
        q2.add(target);
        int step = 0;
        while(!q1.isEmpty() && !q2.isEmpty()){
            HashSet<String> temp = new HashSet<>();
            for(String cur:q1){
                if(dead.contains(cur))
                    continue;
                if(q2.contains(cur))
                    return step;
                visited.add(cur);
                for(int j=0;j<4;j++){
                    String m = minusOne(cur, j);
                    if(!visited.contains(m))
                        temp.add(m);
                    String p = plusOne(cur,j);
                    if(!visited.contains(p))
                        temp.add(p);
                }
            }
            step++;
            q1 = q2;
            q2 = temp;
        }
        return -1;
    }

    //leetcode 752
    public int openLock(String[] deadends, String target) {
        HashSet<String> visited = new HashSet<>();
        HashSet<String> dead = new HashSet<>();
        dead.addAll(Arrays.asList(deadends));
        Queue<String> queue = new LinkedList<>();
        queue.offer("0000");
        int step = 0;
        while(!queue.isEmpty()){
            int size = queue.size();
            for(int i=0;i<size;i++){
                String poll = queue.poll();
                if(poll.equals(target))
                    return step;
                if(dead.contains(poll))
                    continue;
                visited.add(poll);
                for(int j=0;j<4;j++){
                    String m = minusOne(poll, j);
                    if(!visited.contains(m))
                        queue.offer(m);
                    String p = plusOne(poll,j);
                    if(!visited.contains(p))
                        queue.offer(p);
                }
            }
            step++;
        }
        return -1;
    }

    public String minusOne(String s,int j){
        char[] ch = s.toCharArray();
        if(ch[j]=='0')
            ch[j] = '9';
        else
            ch[j] -= 1;
        return new String(ch);
    }

    public String plusOne(String s,int j){
        char[] ch = s.toCharArray();
        if(ch[j]=='9')
            ch[j] = '0';
        else
            ch[j] += 1;
        return new String(ch);
    }

    //leetcode 111
    public int minDepth(TreeNode root) {
        if (root == null)
            return 0;
        Queue<TreeNode> queue = new LinkedList<>();
        int step = 1;
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                if (poll.left == null && poll.right == null)
                    return step;

                if (poll.left != null)
                    queue.offer(poll.left);
                if (poll.right != null)
                    queue.offer(poll.right);
            }
            step++;
        }
        return -1;
    }
}
