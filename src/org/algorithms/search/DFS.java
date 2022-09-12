package org.algorithms.search;

/**
 * Depth First Search 深度优先搜索
 * 算法思想：蛮力法
 *
 * 伪代码：
 * 实现给定图的深度优先查找遍历
 * 输入：图G=&lt;V,E&gt;
 * 输出：图G的顶点，按照被DFS遍历访问到的先后次序
 *
 * dfs(G):
 * count ← count+1;mark v with count
 * for each vertex w in V adjacent to v do
 *      if w is marked with 0
 *          dfs(w)
 */
public class DFS {
    public static int[][] graph;
    public static int[] visited;
    public static int count = 0;
    public static int[] startDFS(int[][] g){
        graph = g;
        visited = new int[g.length];
        for (int i = 0; i < visited.length; i++) {
            visited[i] = 0;
        }
        dfs(0);
        return visited;
    }
    public static void dfs(int start){
        count++;
        visited[start] = count;
        for(int i=0;i< visited.length;i++){
            if(graph[start][i] == 1){
                if(visited[i] == 0)
                    dfs(i);
            }
        }
    }
}
