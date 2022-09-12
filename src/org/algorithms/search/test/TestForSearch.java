package org.algorithms.search.test;

import org.algorithms.search.BFS;
import org.algorithms.search.DFS;
import org.junit.Test;

import java.util.Arrays;

public class TestForSearch {
    @Test
    public void testForBFS() {
        int[] visited = BFS.bfs(generateGraph());
        System.out.println(Arrays.toString(visited));
    }

    @Test
    public void testForDFS() {
        int[] visited = DFS.startDFS(generateGraph());
        System.out.println(Arrays.toString(visited));
    }

    private int[][] generateGraph() {
        int[][] graph = new int[6][6];
        for (int i = 0; i < graph.length; i++) {
            for (int k = 0; k < graph.length; k++) {
                graph[i][k] = 0;
            }
        }
        graph[0][3] = 1;
        graph[0][2] = 1;
        graph[0][4] = 1;
        graph[1][4] = 1;
        graph[1][5] = 1;
        graph[2][0] = 1;
        graph[2][3] = 1;
        graph[2][5] = 1;
        graph[3][0] = 1;
        graph[3][2] = 1;
        graph[4][0] = 1;
        graph[4][5] = 1;
        graph[4][1] = 1;
        graph[5][2] = 1;
        graph[5][4] = 1;
        graph[5][1] = 1;
        return graph;
    }
}
