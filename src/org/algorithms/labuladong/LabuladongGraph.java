package org.algorithms.labuladong;

import org.junit.Test;

import java.util.*;

//仗剑篇 2.3 图论
public class LabuladongGraph {

    //leetcode 1584
    public int minCostConnectPointsUsingPrim(int[][] points) {
        List<int[]>[] graph = buildGraph(points);
        return prim(graph);
    }

    public int prim(List<int[]>[] graph){
        int len = graph.length;
        PriorityQueue<int[]> edges = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] ints, int[] t1) {
                return ints[2] - t1[2];
            }
        });
        int min = 0;
        boolean[] inMST = new boolean[len];
        inMST[0] = true;
        cut(graph,edges,0,inMST);
        while(!edges.isEmpty()){
            int[] poll = edges.poll();
            if(inMST[poll[1]])
                continue;
            min += poll[2];
            inMST[poll[1]] = true;
            cut(graph,edges,poll[1],inMST);
        }
        return min;
    }

    public void cut(List<int[]>[] graph,PriorityQueue<int[]> edges,int point,boolean[] inMST){
        List<int[]> edge = graph[point];
        for(int[] e:edge){
            if(inMST[e[1]])
                continue;
            edges.add(e);
        }

    }

    public List<int[]>[] buildGraph(int[][] points){
        LinkedList<int[]>[] graph = new LinkedList[points.length];
        for(int i=0;i<points.length;i++){
            graph[i] = new LinkedList<>();
        }
        for(int i=0;i<points.length-1;i++){
            for(int j=i+1;j<points.length;j++){
                int[] pA = points[i];
                int[] pB = points[j];
                graph[i].add(new int[]{i,j,Math.abs(pA[0]-pB[0])+Math.abs(pA[1]-pB[1])});
                graph[j].add(new int[]{j,i,Math.abs(pA[0]-pB[0])+Math.abs(pA[1]-pB[1])});
            }
        }
        return graph;
    }

    class ProbState{
        int id;
        double prob;
        public ProbState(int id,double prob){
            this.id = id;
            this.prob = prob;
        }
    }

    //leetcode 1514
    public double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {
        double[] dist = new double[n];
        Arrays.fill(dist,0);
        PriorityQueue<ProbState> pq = new PriorityQueue<>(new Comparator<ProbState>() {
            @Override
            public int compare(ProbState probState, ProbState t1) {
                return Double.compare(t1.prob,probState.prob);
            }
        });
        pq.offer(new ProbState(start,1.0));
        while(!pq.isEmpty()){
            ProbState root = pq.poll();
            int curId = root.id;
            double curProb = root.prob;
            if(curProb < dist[curId])
                continue;
            dist[curId] = curProb;
            if(curId == end)
                return curProb;
            LinkedList<double[]> adjs = adjs(edges, curId, succProb);
            for(double[] adj:adjs){
                int nextId = (int)adj[0];
                double nextProb = adj[1];
                if(nextProb * curProb > dist[nextId])
                    pq.offer(new ProbState(nextId,nextProb * curProb));
            }
        }
        return 0;
    }

    public LinkedList<double[]> adjs(int[][] edges,int id,double[] succProb){
        LinkedList<double[]> neighbor = new LinkedList<>();
        for(int i=0;i<edges.length;i++){
            if(edges[i][0] == id || edges[i][1] == id){
                double[] neigh = new double[2];
                neigh[0] = edges[i][0] == id?edges[i][1]:edges[i][0];
                neigh[1] = succProb[i];
                neighbor.add(neigh);
            }
        }
        return neighbor;
    }

    private int[][] times;

    @Test
    public void test(){
        minimumEffortPath(new int[][]{{4,3,4,10,5,5,9,2},{10,8,2,10,9,7,5,6},{5,8,10,10,10,7,4,2},{5,1,3,1,1,3,1,9},{6,4,10,6,10,9,4,6}});
    }

    //leetcode 1631
    public int minimumEffortPath(int[][] heights){
        int row = heights.length;
        int col = heights[0].length;
        List<Integer[]>[] graph = new List[row*col];
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                List<int[]> neigh = neighbors(i,j,row,col);
                for(int[] n:neigh){
                    if(graph[i*col+j]==null)
                        graph[i*col+j] = new LinkedList<>();
                    graph[i*col+j].add(new Integer[]{n[0]*col+n[1],Math.abs(heights[i][j] - heights[n[0]][n[1]])});
                }
            }
        }
        return classicDijkstra(graph,0,row*col-1);
    }

    public List<int[]> neighbors(int x,int y,int row,int col){
        int[][] dirs = new int[][]{{0,1},{0,-1},{-1,0},{1,0}};
        List<int[]> neighbors = new LinkedList<>();
        for(int[] dir:dirs){
            int nx = x + dir[0];
            int ny = y + dir[1];
            if((nx<0||nx>=row) || (ny<0||ny>=col))
                continue;
            neighbors.add(new int[]{nx,ny});
        }
        return neighbors;
    }

    public int classicDijkstra(List<Integer[]>[] graph,int start,int endId){ //graph的索引：起点编号，graph[x][0]：终点编号，graph[x][1]：权重
        int[] dist = new int[graph.length];
        Arrays.fill(dist,Integer.MAX_VALUE);
        PriorityQueue<State> pq = new PriorityQueue<>(new Comparator<State>() {
            @Override
            public int compare(State state, State t1) {
                return state.distFromStart - t1.distFromStart;
            }
        });
        dist[start] = 0;
        pq.offer(new State(start,0));
        while(!pq.isEmpty()){
            State root = pq.poll();
            int curId = root.id;
            int curDistFromStart = root.distFromStart;
            if(curId == endId)
                return curDistFromStart;
            if(curDistFromStart > dist[curId])
                continue;
            dist[curId] = curDistFromStart;
            List<Integer[]> adjs = graph[curId];
            for(Integer[] adj:adjs){
                int nextId = adj[0];
                int weight = adj[1];
                //int nextDistFromStart = weight + curDistFromStart;
                int nextDistFromStart = Math.max(curDistFromStart,weight);
                if(nextDistFromStart < dist[nextId]){
                    pq.offer(new State(nextId,nextDistFromStart));
                }

            }
        }
        return Integer.MAX_VALUE;
    }

    //leetcode 743
    public int networkDelayTime(int[][] times, int n, int k) {
        this.times = times;
        int[] dist = new int[n+1];
        Arrays.fill(dist,1000);
        PriorityQueue<State> pq = new PriorityQueue<>(new Comparator<State>() {
            @Override
            public int compare(State state, State t1) {
                return state.distFromStart - t1.distFromStart;
            }
        });
        pq.offer(new State(k,0));
        dist[k] = 0;
        while(!pq.isEmpty()){
            State node = pq.poll();
            int curId = node.id;
            int curDistFromStart = node.distFromStart;
            if(curDistFromStart > dist[node.id])
                continue;
            dist[node.id] = curDistFromStart;
            List<Integer> adjs = adj(curId);
            for(int nextId:adjs){
                int nextDistFromStart = weightFor743(curId,nextId) + dist[curId];
                if(nextDistFromStart<dist[nextId]){
                    //dist[nextId] = nextDistFromStart;
                    pq.offer(new State(nextId,nextDistFromStart));
                }
            }
        }
        int max = Integer.MIN_VALUE;
        for(int i=1;i<dist.length;i++){
            max = Math.max(max,dist[i]);
        }
        return max==1000?-1:max;
    }

    class State{
        int id;
        int distFromStart;

        public State(int id,int dist){
            this.id = id;
            this.distFromStart = dist;
        }
    }
    public int weightFor743(int u,int v){
        for(int p=0;p<times.length;p++){
            if(times[p][0] == u && times[p][1] == v)
                return times[p][2];
        }
        return 101;
    }

    public List<Integer> adj(int u){
        LinkedList<Integer> children = new LinkedList<>();
        for(int p=0;p<times.length;p++){
            if(times[p][0] == u)
                children.add(times[p][1]);
        }
        return children;
    }

    @Test
    public void testFor743(){
        System.out.println(networkDelayTime(new int[][]{{2,1,1},{2,3,1},{3,4,1}},4,2));
    }
    class UF {
        private int[] nodes;
        private int count;

        public UF(int n){
            nodes = new int[n];
            for(int i=0;i<n;i++){
                nodes[i] = i;
            }
            count = n;
        }

        public int count(){
            return count;
        }

        public void union(int i,int j){
            int rootA = find(i);
            int rootB = find(j);
            if(rootA == rootB)
                return;
            nodes[rootA] = rootB;
            count--;
        }

        public boolean connected(int i,int j){
            return find(i) == find(j);
        }

        public int find(int i){
            while(nodes[i] != i){
                nodes[i] = nodes[nodes[i]];
                i = nodes[i];
            }
            return i;
        }
    }

    //leetcode 1584
    public int minCostConnectPoints(int[][] points){
        int cost = 0;
        LinkedList<int[]> edges = new LinkedList<>();
        UF uf = new UF(points.length);
        for(int i=0;i<points.length-1;i++){
            for(int j=i+1;j<points.length;j++){
                int[] pointA = points[i];
                int[] pointB = points[j];
                int[] edge = new int[]{i,j,Math.abs(pointA[0]-pointB[0])+Math.abs(pointA[1]-pointB[1])};
                edges.add(edge);
            }
        }
        edges.sort(new Comparator<int[]>() {
            @Override
            public int compare(int[] ints, int[] t1) {
                return ints[2] - t1[2];
            }
        });
        while(!edges.isEmpty()){
            int[] edge = edges.pollFirst();
            if(uf.connected(edge[0],edge[1]))
                continue;
            else{
                uf.union(edge[0],edge[1]);
                cost += edge[2];
            }
        }
        return cost;
    }

    private boolean[] color;

    private boolean result785;

    //leetcode 785
    public boolean isBipartite(int[][] graph){
        color = new boolean[graph.length];
        visited = new boolean[graph.length];
        Arrays.fill(visited,false);
        result785 = true;
        for(int i=0;i< graph.length;i++){
            if(!visited[i])
                traverseToPaint(graph,i,true);
        }
        return result785;
    }

    //leetcode 886
    public boolean possibleBipartition(int n,int[][] dislikes){
        color = new boolean[n+1];
        visited = new boolean[n+1];
        Arrays.fill(visited,false);
        result785 = true;
        List<Integer>[] graph = new List[n+1];
        for(int i=1;i<=n;i++){
            graph[i] = new LinkedList<Integer>();
        }
        for(int[] d:dislikes){
            graph[d[0]].add(d[1]);
            graph[d[1]].add(d[0]);
        }
        for(int i=1;i<=n;i++){
            if(!visited[i])
                advancedTraverseToGroup(graph,i);
        }
        return result785;
    }

    public void advancedTraverseToGroup(List<Integer>[] graph,int root){
        if(!result785)
            return;
        visited[root] = true;
        for(int w:graph[root]){
            if(!visited[w]){
                color[w] = !color[root];
                advancedTraverseToGroup(graph,w);
            }else{
                if(color[w] == color[root]){
                    result785 = false;
                    return;
                }
            }
        }
    }

    @Test
    public void testForAdvancedGroup(){
        possibleBipartition(3,new int[][]{{1,2},{1,3},{2,3}});
    }

    public void traverseToGroup(List<Integer>[] graph,int root,boolean parentColor){
        if(!result785)
            return;
        if(!visited[root]){
            visited[root] = true;
            color[root] = !parentColor;
            List<Integer> list = graph[root];
            for(int i:list){
                traverseToGroup(graph,i,color[root]);
            }
        }else{
            result785 = !(parentColor == color[root]);
            return;
        }
    }

    public void traverseToPaint(int[][] graph,int root,boolean parentColor){
        if(!result785)
            return;
        if(!visited[root]){
            visited[root] = true;
            color[root] = !parentColor;
            for(int i:graph[root]){
                traverseToPaint(graph,i,color[root]);
            }
        }else{
            result785 = !(parentColor == color[root]);
            return;
        }
    }

    @Test
    public void testForColor(){
        isBipartite(new int[][]{{1,3},{0,2},{1,3},{0,2}});
    }

    private List<Integer>[] courseMap;

    private boolean hasCycle;

    private boolean[] onPath;

    private boolean[] visited;

    private LinkedList<Integer> postorder;

    //leetcode 210
    public int[] findOrder(int numCourses,int[][] prerequisites){
        int[] result = new int[numCourses];
        postorder = new LinkedList<>();
        if(!canFinish(numCourses,prerequisites))
            return new int[]{};
        else{
            Collections.reverse(postorder);
            for (int i=0;i<numCourses;i++) {
                result[i] = postorder.get(i);
            }
        }
        return result;
    }

    //leetcode 207
    public boolean canFinish(int numCourses,int[][] prerequisites){
        hasCycle = false;
        onPath = new boolean[numCourses];
        visited = new boolean[numCourses];
        courseMap = new LinkedList[numCourses];
        for(int i=0;i<numCourses;i++){
            courseMap[i] = new LinkedList<>();
        }
        for(int[] requisite:prerequisites){
            courseMap[requisite[1]].add(requisite[0]);
        }
        for(int i=0;i<numCourses;i++){
            traverseToFindCycle(i);
        }
        return !hasCycle;
    }

    public void traverseToFindCycle(int root){
        if(onPath[root]){
            hasCycle = true;
        }
        if(hasCycle || visited[root])
            return;
        List<Integer> post = courseMap[root];
        visited[root] = true;
        onPath[root] = true;
        for(Integer p:post){
            traverseToFindCycle(p);
        }
        postorder.add(root);
        onPath[root] = false;
    }
    private LinkedList<List<Integer>> result;

    private LinkedList<Integer> path;

    private int[][] graph;

    //leetcode 797
    public List<List<Integer>> allPathsSourceTarget(int[][] graph){
        result = new LinkedList<>();
        path = new LinkedList<>();
        this.graph = graph;
        traverseGraph(0);
        return result;
    }

    public void traverseGraph(int root){
        path.add(root);
        if(root == graph.length-1){
            result.add(new LinkedList<>(path));
            path.removeLast();
            return;
        }
        for(int i:graph[root]){
            traverseGraph(i);
        }
        path.removeLast();
    }
}
