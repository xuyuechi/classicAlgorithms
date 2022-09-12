package org.algorithms.search;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Breadth First Search 广度优先搜索
 * 算法思想：蛮力法
 * <p>
 * 伪代码：
 * 实现给定图的广度优先查找遍历
 * 输入：图G=&lt;V,E&gt;
 * 输出：图G的顶点，按照被BFS遍历访问到的先后次序
 * <p>
 * bfs(G):
 * count ← count+1; mark v with count and initialize a queue with v
 * while the queue is not empty do
 *      remove the front vertex from the queue
 *      for each vertex w in V adjacent to the front vertex do
 *          if w is marked with 0
 *              count ← count+1;
 *              mark w with count
 *              add w to the queue
 */
public class BFS {
    //默认vertex中的顶点顺序与graph的行下标（列下标）顺序对应
    public static int[] bfs(int[][] g) {
        int count = 0;
        int[] visited = new int[g.length];
        for (int i = 0; i < visited.length; i++) {
            visited[i] = 0;
        }
        int node = 0;

        count++;
        visited[node] = count;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            int next = queue.remove();
            for (int i = 0; i < g.length; i++) {
                if (g[next][i] == 1) {
                    if (visited[i] == 0) {
                        count++;
                        visited[i] = count;
                        queue.add(i);
                    }
                }
            }
        }
        return visited;
    }
}
