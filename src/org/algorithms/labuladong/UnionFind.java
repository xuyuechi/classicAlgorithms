package org.algorithms.labuladong;

import org.junit.Test;

import java.util.HashSet;

public class UnionFind {
    public class UF{
        private int count;
        private int[] parent;

        public UF(int n){
            this.count = n;
            parent = new int[n];
            for(int i=0;i<n;i++){
                parent[i] = i;
            }
        }

        public void union(int p,int q){
            int rootP = find(p);
            int rootQ = find(q);

            if(rootP==rootQ)
                return;
            parent[rootQ] = rootP;
            count--;
        }

        public boolean isConnected(int p,int q){
            int rootP = find(p);
            int rootQ = find(q);
            return rootP == rootQ;
        }

        public int find(int x){
            if(parent[x]!=x)
                parent[x] = find(parent[x]);
            return parent[x];
        }

        public int count(){
            return count;
        }
    }

    //leetcode 130
    public void solve(char[][] board){
        int height = board.length;
        int width = board[0].length;
        int size = height*width+1;
        UF uf = new UF(size);
        int dummy = height*width;
        uf.parent[size-1] = dummy;
        for(int i=0;i<height;i++){
            if(board[i][0] == 'O')
                uf.union(width*i,dummy);
            if(board[i][width-1] == 'O')
                uf.union((i+1)*width-1,dummy);
        }
        for(int i=0;i<width;i++){
            if(board[0][i] == 'O')
                uf.union(i,dummy);
            if(board[height-1][i] == 'O')
                uf.union((height-1)*width+i,dummy);
        }
        //方向数组direction是上下左右搜索的常用手法
        int[][] d = new int[][]{{1,0},{0,1},{0,-1},{-1,0}};
        for(int i=1;i<height-1;i++){
            for(int j=1;j<width-1;j++){
                if(board[i][j] == 'O')
                    for(int k=0;k<4;k++){
                        int x = i + d[k][0];
                        int y = j + d[k][1];
                        if(board[x][y] == 'O')
                            uf.union(i*width+j,x*width+y);
                    }
            }
        }
        for(int i=1;i<height-1;i++)
            for(int j=1;j<width-1;j++)
                if(!uf.isConnected(i*width+j,dummy)){
                    board[i][j] = 'X';
                }
    }

    //leetcode 990
    public boolean equationsPossible(String[] equations){
        UF uf = new UF(26);
        for(String s:equations) {
            if (s.contains("==")) {
                int a = s.charAt(0) - 97;
                int b = s.charAt(3) - 97;
                uf.union(a, b);
            }
        }
        for(String s:equations){
            if(s.contains("!=")){
                int a = s.charAt(0)-97;
                int b = s.charAt(3)-97;
                if(uf.isConnected(a,b))
                    return false;
            }
        }
        return true;
    }

    @Test
    public void testForString(){
        System.out.println(equationsPossible(new String[]{"c==c","b==d","x!=z"}));
    }
}
