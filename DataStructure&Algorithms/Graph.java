import java.util.*;

public class Graph {
    
    public static class Edge{
        int v = 0;
        int w = 0;

        public Edge(int v,int w){
            this.v = v;
            this.w = w;
        }
    }

    static int N = 7;
    @SuppressWarnings("unchecked")
    static ArrayList<Edge>[] graph = new ArrayList[N];

    public static void addEdge(int u,int v,int w){
        graph[u].add(new Edge(v,w));
        graph[v].add(new Edge(u,w));
    }

    public static void display(){
        for(int i=0;i<N;i++){
            System.out.print(i + " ->");
            for(Edge e:graph[i])
                System.out.print(" (" + e.v + "," + e.w + "),");
            System.out.println();
        }
    }

    public static int searchVtx(int u,int v){
        for(int i=0;i<graph[u].size();i++){
            Edge e = graph[u].get(i);
            if(e.v==v) return i;
        }
        return -1;
    }

    public static int searchEdge(int u,int v){
        int idx = -1;
        for(Edge e:graph[u]){
            idx++;
            if(e.v==v) break;
        }
        return idx;
    }

    public static void removeEdge(int u,int v){
        int l1 = searchVtx(u,v);
        graph[u].remove(l1);

        int l2 = searchVtx(v, u);
        graph[u].remove(l2);
    }

    public static void removeVtx(int u){
        for(int i=graph[u].size()-1;i>=0;i--){
            Edge e = graph[u].get(i);
            removeEdge(u, e.v);
        }
    }

    //HasPath - Undirected Graph
    private static boolean hasPath(int src,int dest,boolean[] vis){
        if(src==dest) return true;

        boolean res = false;
        vis[src] = true;
        for(Edge e:graph[src]){
            if(!vis[e.v])
                res = res || hasPath(e.v,dest,vis);
            if(res) break;
        }
        vis[src] = false;
        return res;
    }
    public static boolean hasPath(int src,int dest){
        if(src==dest) return true;
        boolean[] vis = new boolean[N];
        return hasPath(src,dest,vis);
    }

    //Return no. of paths and also print it - undirected graph
    private static int printAllPaths(int src,int dest,boolean[] vis,String path){
        if(src==dest){
            System.out.println(path+src);
            return 1;
        }

        int count = 0;
        vis[src] = true;
        for(Edge e:graph[src]){
            if(!vis[e.v])
            count += printAllPaths(e.v,dest,vis,path+src);
        }
        vis[src] = false;
        return count;

    }
    public static int printAllPaths(int src,int dest){
        boolean[] vis = new boolean[N];
        return printAllPaths(src,dest,vis,"");
    }

    //Heavy Path - print path and weight
    public class weightPathPair{
        int weight;
        String path;
        weightPathPair(int weight,String path){
            this.weight = weight;
            this.path = path;
        }
    }
    private weightPathPair heavyPath(int src,int dest,boolean[] vis){
        if(src==dest){
            return new weightPathPair(0,dest+"");
        }

        weightPathPair ans = new weightPathPair(-(int)1e8,"");
        vis[src] = true;

        for(Edge e:graph[src]){
            if(!vis[e.v]){
                weightPathPair recAns = heavyPath(e.v,dest,vis);
                if(recAns.weight!= -(int)1e8 && recAns.weight+e.w>ans.weight){
                    ans.weight = recAns.weight+e.w;
                    ans.path = src + " " + recAns.path;
                }
            }
        }

        vis[src] = false;
        return ans;
    }
    public weightPathPair heavyPath(int src,int dest){
        boolean[] vis = new boolean[N];
        return heavyPath(src,dest,vis);
    }

    private weightPathPair lightPath(int src,int dest,boolean[] vis){
        if(src==dest){
            return new weightPathPair(0,dest+"");
        }

        weightPathPair ans = new weightPathPair((int)1e8,"");
        vis[src] = true;

        for(Edge e:graph[src]){
            if(!vis[e.v]){
                weightPathPair recAns = lightPath(e.v, dest, vis);
                if(recAns.weight!=(int)1e8 && recAns.weight+e.w < ans.weight){
                    ans.weight = recAns.weight + e.w;
                    ans.path = src + " " + recAns.path;
                }
            }
        }
        vis[src] = false;
        return ans;
    }
    public weightPathPair lightPath(int src,int dest){
        boolean[] vis = new boolean[N];
        return lightPath(src,dest,vis);
    }

    //Hamiltonian Path and Cycle
    /*The following function returns the no. of hamiltonian paths and prints the path informing whether 
    it is cycle or path */
    private int hamiltonianPathAndCycle(int src,int osrc,int n,boolean[] vis,String psf){
        if(n==N-1){
            int idx = searchEdge(src,osrc);
            if(idx!=-1){
                System.out.println("Cycle: " + psf + src);
                return 0;
            }else{
                System.out.println("Path: " + psf + src);
                return 1;
            }
        }

        int count = 0;
        vis[src] = true;
        for(Edge e:graph[src]){
            if(!vis[e.v])
                count += hamiltonianPathAndCycle(e.v,osrc,n+1,vis,psf+src+" ");
        }
        vis[src] = false;
        return count;
    }
    public int hamiltonianPathAndCycle(int src){
        boolean[] vis = new boolean[N];
        return hamiltonianPathAndCycle(src,src,0,vis,"");
    }

    //Get Connected Components
    //This function returns the no. of connected components and prints the vertices of each component
    private void getConnectedComponents(int src,boolean[] vis){
        vis[src] = true;
        System.out.print(src+" ");
        for(Edge e:graph[src]){
            if(!vis[e.v])
                getConnectedComponents(e.v,vis);
        }
    }
    public int getConnectedComponents(){
        boolean[] vis = new boolean[N];
        int count = 0;
        for(int i=0;i<N;i++){
            if(!vis[i]){
                if(i!=0) System.out.println();
                getConnectedComponents(i,vis);
                count++;
            }
        }
        return count;
    }

    //Leetcode 200
    private void numIslands(int i,int j,int n,int m,int[][] dir,char[][] grid){
        grid[i][j]='0';

        for(int d=0;d<4;d++){
            int x = i + dir[d][0];
            int y = j + dir[d][1];
            if(x>=0 && y>=0 && x<n && y<m && grid[x][y]=='1')
                numIslands(x,y,n,m,dir,grid);
        }
    }
    public int numIslands(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int count = 0;
        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]=='1'){
                    numIslands(i,j,n,m,dir,grid);
                    count++;
                }
            }
        }
        return count;
    }

    //Leetcode 695
    private int maxAreaOfIsland(int i,int j,int n,int m,int[][] dir,int[][] grid){
        grid[i][j] = 0;
        int area = 1;
        for(int d=0;d<4;d++){
            int x = i + dir[d][0];
            int y = j + dir[d][1];

            if(x>=0 && y>=0 && x<n && y<m && grid[x][y]==1)
                area += maxAreaOfIsland(x,y,n,m,dir,grid);
        }
        return area;
    }
    public int maxAreaOfIsland(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        int maxArea = 0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]==1){
                    maxArea = Math.max(maxArea,maxAreaOfIsland(i,j,n,m,dir,grid));
                }
            }
        }
        return maxArea;
    }

    //Leetcode 463
    public int islandPerimeter(int[][] grid) {
        int countOnes = 0;
        int countNbrs = 0;

        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        int x,y;

        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[0].length;j++){
                if(grid[i][j]==1){
                    countOnes++;
                    for(int d=0;d<4;d++){
                        x = i + dir[d][0];
                        y = j + dir[d][1];
                        if(x>=0 && y>=0 && x<grid.length && y<grid[0].length && grid[x][y]==1) countNbrs++;
                    }
                }
            }
        }

        return 4*countOnes - countNbrs;
    }

    //Leetcode 130
    private void boardDFS(int i,int j,int n,int m,int[][] dir,char[][] board){
        board[i][j] = '#';
        for(int d=0;d<4;d++){
            int x = i + dir[d][0];
            int y = j + dir[d][1];
            if(x>=0 && y>=0 && x<n && y<m && board[x][y]=='O')
                boardDFS(x,y,n,m,dir,board);
        }
    }
    public void solve(char[][] board) {
        int n = board.length;
        int m = board[0].length;

        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(i==0 || j==0 || i==n-1 || j==m-1){
                    if(board[i][j]=='O')
                    boardDFS(i,j,n,m,dir,board);
                }
            }
        }

        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(board[i][j]=='O') board[i][j] = 'X';
                else if(board[i][j]=='#') board[i][j] = 'O';
            }
        }
    }

    //BFS

    //BFS - Cycle
    public static void BFS_Cycle(int src,int dest){
        boolean[] vis = new boolean[N];

        Queue<Integer> q = new LinkedList<>();
        q.add(src);
        int level = 0;
        int atLevel = -1;
        boolean isCycle = false;

        while(q.size()>0){
            int size = q.size();
            System.out.print("Level " + level + " : ");
            while(size-->0){
                int vtx = q.remove();
                if(vis[vtx]){
                    isCycle = true;
                    continue;
                }

                System.out.print(vtx+" ");

                if(vtx==dest) atLevel = level;

                vis[vtx] = true;
                for(Edge e:graph[vtx]){
                    if(!vis[e.v]) q.add(e.v);
                }
            }
            level++;
            System.out.println();
        }

        System.out.println(dest+" present at "+atLevel);
        System.out.println("isCycle: " +Boolean.valueOf(isCycle));
    }

    //BFS - Shortest Path - Traverse and Print - Without Cycle
    public static void BFS_ShortestPath(int src,int dest){
        boolean[] vis = new boolean[N];
        int[] dist = new int[N];
        Queue<Integer> q = new LinkedList<>();
        q.add(src);
        vis[src] = true;
        int level = 0;
        int atLevel = -1;

        while(q.size()>0){
            int size = q.size();
            while(size-->0){
                int vtx = q.remove();

                if(vtx==dest) atLevel = level;

                dist[vtx] = level;

                for(Edge e:graph[vtx]){
                    if(!vis[e.v]){
                        q.add(e.v);
                        vis[e.v] = true;
                    }
                }
            }
            level++;
        }

        System.out.println(atLevel);
    }

    public void BFS_printShortestPath(int src,boolean[] vis){
        Queue<Integer> q = new LinkedList<>();
        q.add(src);
        int dest = 6;
        vis[src] = true;
        int level = 0;
        int atLevel = -1;
        int[] par = new int[N];
        Arrays.fill(par,-1);

        while(q.size()>0){
            int size = q.size();
            while(size-->0){
                int vtx = q.remove();

                for(Edge e:graph[vtx]){
                    if(!vis[e.v]){
                        vis[e.v] = true;
                        q.add(e.v);
                        par[e.v] = vtx;
                    }

                    if(atLevel==-1 && e.v==dest){
                        atLevel = level+1;
                    }
                }
            }
            level++;
        }

        System.out.println(dest+" present at "+atLevel);

        int idx = dest;
        while(idx!=-1){
            System.out.print(idx+"->");
            idx = par[idx];
        }
    }

    //To traverse through all components of the graph
    public void BFS(){
        boolean[] vis = new boolean[N];
        int components = 0;
        for(int i=0;i<N;i++){
            if(!vis[i]){
                components++;
                BFS_printShortestPath(i,vis);
            }
        }
        System.out.println(components);
    }

    //Leetcode 1091
    public int shortestPathBinaryMatrix(int[][] grid) {
        int n = grid.length;
        if(grid[0][0]==1 || grid[n-1][n-1]==1) return -1;

        Queue<Integer> q = new LinkedList<>();
        q.add(0*n+0);
        grid[0][0] = 1;
        int[][] dir = {{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1},{-1,0},{-1,1}};
        int len = 1;

        while(!q.isEmpty()){
            int size = q.size();
            while(size-->0){
                int idx = q.remove();
                int r = idx/n;
                int c = idx%n;
                if(r==n-1 && c==n-1) return len;

                for(int d=0;d<dir.length;d++){
                    int x = r + dir[d][0];
                    int y = c + dir[d][1];
                    if(x>=0 && y>=0 && x<n && y<n && grid[x][y]==0){
                        q.add(x*n+y);
                        grid[x][y] = 1;
                    }
                }
            }
            len++;
        }

        return -1;
    }

    //Leetcode 785
    //Method 1 - BFS
    private boolean isBipartiteBFS(int i,int[][] graph,int[] markedColour){
        Queue<Integer> q = new LinkedList<>();
        q.add(i);
        int colour = 0;
        while(q.size()!=0){
            int size = q.size();
            while(size-->0){
                int vtx = q.remove();
                if(markedColour[vtx]!=-1){
                    if(markedColour[vtx]!=colour) return false;
                    continue;
                }
                markedColour[vtx] = colour;
                for(int v:graph[vtx]){
                    if(markedColour[v]==-1) q.add(v);
                }
            }
            colour = (colour+1)%2;
        }
        return true;
    }
    public boolean isBipartiteBFS(int[][] graph) {
        int n = graph.length;
        int[] markedColour = new int[n];
        Arrays.fill(markedColour,-1);
        boolean ans = false;
        for(int i=0;i<n;i++){
            if(markedColour[i]==-1)
                ans = isBipartiteBFS(i,graph,markedColour);
            if(!ans) return ans;
        }
        return ans;
    }

    //Method 2 - DFS
    private boolean isBipartite(int u,int currVal,int[] vis,int[][] graph){
        vis[u] = currVal;
        int nextVal = (currVal+1)%2;

        for(int v:graph[u]){
            if(vis[v]==-1 && (!isBipartite(v,nextVal,vis,graph))) return false;
            if(vis[v]!=nextVal) return false;
        }

        return true;
    }
    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] vis = new int[n];
        Arrays.fill(vis,-1);
        
        for(int i=0;i<n;i++){
            if(vis[i]==-1) 
                if(!isBipartite(i,0,vis,graph)) return false;
            
        }

        return true;
    }

    //Leetcode 994
    public int orangesRotting(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        Queue<Integer> q = new LinkedList<>();
        int oranges = 0;
        int o = 0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]==1||grid[i][j]==2){
                    oranges++;
                    if(grid[i][j]==2) {
                        q.add(i*m+j);
                        o++;
                    }
                }
            }
        }
        if(o==oranges) return 0;
        if(o==0){
            if(oranges>0) return -1;
        }

        int time = -1;
        int rottenOranges = 0;
        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        while(!q.isEmpty()){
            int size = q.size();
            while(size-->0){
                int vtx = q.remove();
                int r = vtx/m;
                int c = vtx%m;
                rottenOranges++;
                for(int d=0;d<4;d++){
                    int x = r + dir[d][0];
                    int y = c + dir[d][1];

                    if(x>=0 && y>=0 && x<n && y<m && grid[x][y]==1){
                        q.add(x*m+y);
                        grid[x][y] = 2; 
                    }
                }
            }
            time++;
        }

        if(oranges==rottenOranges){
            return (time!=-1) ? time : 0;
        }
        else return -1;
    }

    //Leetcode 286 - Locked
    //Lintcode 663
    public void wallsAndGates(int[][] rooms) {
        int n = rooms.length;
        int m = rooms[0].length;

        Queue<Integer> q = new LinkedList<>();

        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(rooms[i][j]==0) q.add(i*m+j);
            }
        }

        int level = 0;
        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};

        while(!q.isEmpty()){
            int size = q.size();
            while(size-->0){
                int idx = q.remove();
                int r = idx/m;
                int c = idx%m;

                for(int d=0;d<4;d++){
                    int x = r + dir[d][0];
                    int y = c + dir[d][1];

                    if(x>=0 && y>=0 && x<n && y<m && rooms[x][y]==Integer.MAX_VALUE){
                        rooms[x][y] = level + 1;
                        q.add(x*m+y);
                    }
                }
            }
            level++;
        }
    }

    //Topological Sort
    private static void TOPO_DFS(int u,boolean[] vis,ArrayList<Integer> ans){
        vis[u] = true;
        for(Edge e:graph[u]){
            if(!vis[e.v]) TOPO_DFS(e.v,vis,ans);
        }
        ans.add(u);

    }
    public static void TOPO_DFS(){
        ArrayList<Integer> ans = new ArrayList<>();
        boolean[] vis = new boolean[N];
        for(int i=0;i<N;i++){
            if(!vis[i]) TOPO_DFS(i,vis,ans);
        }
    }

    //Kahn's Algo
    public static void KahnsAlgo(){
        int[] indegree = new int[N];
        ArrayList<Integer> ans = new ArrayList<>();
        Queue<Integer> q = new LinkedList<>();

        for(int i=0;i<N;i++){
            for(Edge e:graph[i]) indegree[e.v]++;
        }

        for(int i=0;i<N;i++){
            if(indegree[i]==0) q.add(i);
        }

        while(q.size()>0){
            int vtx = q.remove();
            ans.add(vtx);
            for(Edge e:graph[vtx]){
                if(--indegree[e.v]==0) q.add(e.v);
            }
        }

        if(ans.size()==N) System.out.println(ans);
        else System.out.println("Cycle");
    }

    //Leetcode 207 - Kahn's Algo - BFS
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] indegree = new int[numCourses];
        Queue<Integer> q = new LinkedList<>();
        int n = 0;
        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] graph = new ArrayList[numCourses];
        for(int i=0;i<numCourses;i++) graph[i] = new ArrayList<>();

        for(int i=0;i<prerequisites.length;i++){
            graph[prerequisites[i][0]].add(prerequisites[i][1]);
            indegree[prerequisites[i][1]]++;
        }

        for(int i=0;i<numCourses;i++){
            if(indegree[i]==0) q.add(i);
        }

        while(!q.isEmpty()){
            int vtx = q.remove();
            n++;
            for(int u:graph[vtx]){
                if(--indegree[u]==0) q.add(u);
            }
        }

        return n==numCourses;
    }

    //Leetcode 210 - Kahn's Algo - BFS
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] indegree = new int[numCourses];
        Queue<Integer> q = new LinkedList<>();
        int[] ans = new int[numCourses];
        int idx = numCourses;
        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] graph = new ArrayList[numCourses];
        for(int i=0;i<numCourses;i++) graph[i] = new ArrayList<>();

        for(int i=0;i<prerequisites.length;i++){
            graph[prerequisites[i][0]].add(prerequisites[i][1]);
            indegree[prerequisites[i][1]]++;
        }

        for(int i=0;i<numCourses;i++){
            if(indegree[i]==0) q.add(i);
        }

        while(!q.isEmpty()){
            int vtx = q.remove();
            ans[--idx] = vtx;
            for(int u:graph[vtx]){
                if(--indegree[u]==0) q.add(u);
            }
        }

        return (idx==0) ? ans : new int[]{};
    }

    //Leetcode 310
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> ans = new LinkedList<>();
        if(n <= 2){
            for(int i = 0; i < n; i++) ans.add(i);
            return ans;
        }
        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] graph = new ArrayList[n];
        for(int i=0;i<n;i++) graph[i] = new ArrayList<>();
        int[] indegree = new int[n];
        for(int[] edge:edges){
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
            indegree[edge[0]]++;
            indegree[edge[1]]++;
        }

        Queue<Integer> q = new LinkedList<>();
        for(int i=0;i<n;i++){
            if(indegree[i]==1) q.add(i);
        }
        int size;
        while(!q.isEmpty()){
            size = q.size();
            for(int i=0;i<size;i++){
                int vtx = q.remove();
                if(n>2){
                    for(int v:graph[vtx]){
                        if(--indegree[v]==1) q.add(v);
                    }
                }else{
                    ans.add(vtx);
                }
            }
            n -= size;
        }

        return ans;
    } 

    //Topo DFS Cycle - To find if a cycle is present
    //0 - unvisited | 1 - same path visited | 2 - diff path visited
    private static boolean Topo_DFS(int src,int[] vis,ArrayList<Integer> ans){
        vis[src] = 1;
        for(Edge e:graph[src]){
            if(vis[e.v]==0){
                if(Topo_DFS(e.v,vis,ans)) return true;
            }else if(vis[e.v]==1) return true;
        }
        vis[src] = 2;
        ans.add(src);
        return false;
    }
    public static boolean Topo_DFS(){
        ArrayList<Integer> ans = new ArrayList<>();
        int[] vis = new int[N];
        boolean isCycle = false;
        for(int i=0;i<N;i++){
            if(vis[i]==0){
                if(Topo_DFS(i,vis,ans)){
                    isCycle = true;
                    break;
                }
            }
        }
        if(!isCycle) System.out.println(ans);
        return isCycle;

    }

    //Leetcode 207 - Topo DFS
    private boolean canFinish_TopoDFS(int u,int[] vis,List<List<Integer>> graph){
        vis[u] = 1;
        for(int v:graph.get(u)){
            if(vis[v]==0 && !canFinish_TopoDFS(v,vis,graph)) return false;
            else if(vis[v]==1) return false; 
        }
        vis[u] = 2;
        return true;
    }
    public boolean canFinish_TopoDFS(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0;i<numCourses;i++) graph.add(new ArrayList<>());

        for(int[] edge:prerequisites){
            graph.get(edge[0]).add(edge[1]);
        }
        int[] vis = new int[numCourses];
        for(int i=0;i<numCourses;i++){
            if(vis[i]==0 && !canFinish_TopoDFS(i,vis,graph)) return false;
        }

        return true;
    }

    //Leetcode 210 - Topo DFS
    private boolean findOrder(int u,int[] vis,int[] ans,int[] idx,List<List<Integer>> graph){
        vis[u] = 1;

        for(int v:graph.get(u)){
            if(vis[v]==0 && !findOrder(v,vis,ans,idx,graph)) return false;
            else if(vis[v]==1) return false;
        }
        ans[idx[0]++] = u;
        vis[u] = 2;
        return true;
    }
    public int[] findOrder_TopoDFS(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0;i<numCourses;i++) graph.add(new ArrayList<>());

        for(int[] edge:prerequisites){
            graph.get(edge[0]).add(edge[1]);
        }

        int[] vis = new int[numCourses];
        int[] ans = new int[numCourses];
        int[] idx = new int[1];

        for(int i=0;i<numCourses;i++){
            if(vis[i]==0 && !findOrder(i,vis,ans,idx,graph)) return new int[]{}; 
        }

        return ans;
    }
    

    //Leetcode 2115
    //Method 1 - Kahn's Algo BFS
    public List<String> findAllRecipes01(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        HashMap<String,List<String>> graph = new HashMap<>();
        int n = recipes.length;
        HashMap<String,Integer> indegree = new HashMap<>();

        for(int i=0;i<n;i++){
            indegree.put(recipes[i],0);
            for(String ing:ingredients.get(i)){
                if(!graph.containsKey(ing)){
                    graph.put(ing,new ArrayList<>());
                }
                graph.get(ing).add(recipes[i]);
                indegree.put(recipes[i],indegree.get(recipes[i])+1);
            }
        }

        Queue<String> q = new LinkedList<>();
        for(String supply:supplies) q.add(supply);
        List<String> ans = new ArrayList<>();
        while(!q.isEmpty()){
            String a = q.remove();
            if(graph.containsKey(a)){
                for(String v:graph.get(a)){
                    int x = indegree.get(v)-1;
                    if(x==0){
                        q.add(v);
                        indegree.remove(v);
                        ans.add(v);
                    }
                    indegree.put(v,x);
                }
            }
        }
        return ans;

    }

    //Method 2 - TOPO DFS
    private boolean findAllRecipes(String recipe,HashMap<String,List<String>> graph,HashMap<String,Integer> vis,HashSet<String> recipeSet,List<String> ans){
        vis.put(recipe,1);
        for(String v : graph.get(recipe)){
            if(graph.containsKey(v)){
                if(!vis.containsKey(v)){
                    if(!findAllRecipes(v,graph,vis,recipeSet,ans)) return false;
                }else if(vis.get(v)==1) return false;
            }
            else return false;
        }
        vis.put(recipe,2);
        if(recipeSet.contains(recipe)) ans.add(recipe);
        return true;
    }

    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        HashMap<String,List<String>> graph = new HashMap<>();
        int n = recipes.length;
        HashSet<String> recipeSet = new HashSet<>();

        for(int i=0;i<n;i++){
            recipeSet.add(recipes[i]);
            graph.put(recipes[i],new ArrayList<>(ingredients.get(i)));
        }
        for(String ing:supplies){
            graph.put(ing,new ArrayList<>());
        }

        HashMap<String,Integer> vis = new HashMap<>();
        List<String> ans = new ArrayList<>();
        for(int i=0;i<n;i++){
            if(!vis.containsKey(recipes[i])){
                findAllRecipes(recipes[i],graph,vis,recipeSet,ans);
            }
        }
        return ans;
    }

    //Leetcode 2392
    //Method 1
    private boolean topoDFS(int i,List<Integer>[] graph,int[] vis,List<Integer> ans){
        vis[i] = 1;
        for(int nbr:graph[i]){
            if(vis[nbr]==0){
                if(!topoDFS(nbr,graph,vis,ans)) return false;
            }else if(vis[nbr]==1) return false;
        }
        vis[i] = 2;
        ans.add(i);
        return true;
    }
    private List<Integer> topo(int n,int[][] conditions){
        @SuppressWarnings("unchecked")
        List<Integer>[] graph = new ArrayList[n+1];
        for(int i=0;i<=n;i++) graph[i] = new ArrayList<>();

        for(int[] con:conditions){
            graph[con[1]].add(con[0]);
        }

        int[] vis = new int[n+1];
        List<Integer> ans = new ArrayList<>();
        for(int i=1;i<=n;i++){
            if(vis[i]==0){
                if(!topoDFS(i,graph,vis,ans)) return new ArrayList<>();
            }
        }
        
        return ans;
    }
    public int[][] buildMatrix_DFS(int k, int[][] rowConditions, int[][] colConditions) {
        List<Integer> topoRow = topo(k,rowConditions);
        if(topoRow.isEmpty()) return new int[][]{};

        List<Integer> topoCol = topo(k,colConditions);
        if(topoCol.isEmpty()) return new int[][]{};

        int[][] matrix = new int[k][k];
        int[] nodeToRowIdx = new int[k+1];
        for(int i=0;i<k;i++){
            nodeToRowIdx[topoRow.get(i)] = i;
        }
        for(int j=0;j<k;j++){
            int node = topoCol.get(j);
            int i = nodeToRowIdx[node];
            matrix[i][j] = node;
        }
        return matrix;
    }

    //Method 2
    private List<Integer> topoBFS(int n,int[][] conditions){
        @SuppressWarnings("unchecked")
        List<Integer>[] graph = new ArrayList[n+1];
        for(int i=0;i<=n;i++) graph[i] = new ArrayList<>();
        int[] indegree = new int[n+1];
        for(int[] con:conditions){
            graph[con[0]].add(con[1]);
            indegree[con[1]]++;
        }

        Queue<Integer> q = new LinkedList<>();
        for(int i=1;i<=n;i++){
            if(indegree[i]==0) q.add(i); 
        }

        List<Integer> ans = new ArrayList<>();
        int count = 0;
        while(!q.isEmpty()){
            int i = q.remove();
            ans.add(i);
            count++;
            for(int nbr:graph[i]){
                if(--indegree[nbr]==0) q.add(nbr);
            }
        }
        
        return (count==n) ? ans : new ArrayList<>();
    }
    public int[][] buildMatrix_BFS(int k, int[][] rowConditions, int[][] colConditions) {
        List<Integer> topoRow = topoBFS(k,rowConditions);
        if(topoRow.isEmpty()) return new int[][]{};

        List<Integer> topoCol = topoBFS(k,colConditions);
        if(topoCol.isEmpty()) return new int[][]{};

        int[][] matrix = new int[k][k];
        int[] nodeToRowIdx = new int[k+1];
        for(int i=0;i<k;i++){
            nodeToRowIdx[topoRow.get(i)] = i;
        }
        for(int j=0;j<k;j++){
            int node = topoCol.get(j);
            int i = nodeToRowIdx[node];
            matrix[i][j] = node;
        }
        return matrix;
    }

    //Leetcode 269 - Locked
    //Lintcode 892
    public String alienOrder(String[] words) {
        if(words==null || words.length==0) return "";

        HashMap<Character,HashSet<Character>> graph = new HashMap<>();
        HashMap<Character,Integer> indegree = new HashMap<>();
        int n = words.length;
        for (String s: words) {
            for (char c: s.toCharArray()) {
                indegree.put(c, 0);
            }
        }

        for(int i=0;i<n-1;i++){
            int k = Math.min(words[i].length(),words[i+1].length());
            int j = 0;
            String s1 = words[i];
            String s2 = words[i+1];
            while(j<k){
                if(s1.charAt(j)!=s2.charAt(j)){
                    char ch1 = s1.charAt(j);
                    char ch2 = s2.charAt(j);
                    if(!graph.containsKey(ch1)) graph.put(ch1,new HashSet<>());
                    if(!graph.get(ch1).contains(ch2)){
                        graph.get(ch1).add(ch2);
                        indegree.put(ch2,indegree.get(ch2)+1);
                    }
                    break;
                }
                j++;
            }
            if(j==k && s1.length()>s2.length()) return "";
        }

        //In leetcode 269 do not use a priority queue, instead use queue
        PriorityQueue<Character> pq = new PriorityQueue<>();
        for(char ch:indegree.keySet()){
            if(indegree.get(ch)==0) pq.add(ch);
        }
        StringBuilder sb = new StringBuilder();

        int count = 0;
        while(!pq.isEmpty()){
            char ch = pq.remove();
            sb.append(ch);
            count++;
            if(graph.containsKey(ch)){
                for(char nbr:graph.get(ch)){
                    int a = indegree.get(nbr)-1;
                    if(a==0){
                        pq.add(nbr);
                    }
                    indegree.put(nbr,a);
                }
            }
        }

        return (count==indegree.size()) ? sb.toString() : "";
    }

    //Leetcode 802
    //Method 1 - Topo BFS
    public List<Integer> eventualSafeNodes_TopoBFS(int[][] graph) {
        int n = graph.length;
        @SuppressWarnings("unchecked")
        List<Integer>[] newGraph = new ArrayList[n];
        for(int i=0;i<n;i++) newGraph[i] = new ArrayList<>();
        int[] indegree = new int[n];

        Queue<Integer> q = new LinkedList<>();

        for(int i=0;i<n;i++){
            if(graph[i].length==0) q.add(i);
            else{
                for(int ele:graph[i]){
                newGraph[ele].add(i);
                indegree[i]++;
                }
            }
        }

        List<Integer> ans = new ArrayList<>();
        while(!q.isEmpty()){
            int vtx = q.remove();
            ans.add(vtx);
            for(int nbr:newGraph[vtx]){
                if(--indegree[nbr]==0) q.add(nbr);
            }
        }

        Collections.sort(ans);
        return ans;
    }

    //Method 2 - Topo DFS
    private boolean eventualSafeNodes(int src,int[] vis,List<Integer> ans,int[][] graph){
        vis[src] = 1;
        
        for(int nbr:graph[src]){
            if(vis[nbr]==0 && !eventualSafeNodes(nbr,vis,ans,graph)) return false;
            else if(vis[nbr]==1) return false;
        }
        vis[src] = 2;
        return true;
    }
    public List<Integer> eventualSafeNodes(int[][] graph) {
        int n = graph.length;
        int[] vis = new int[n];
        List<Integer> ans = new ArrayList<>();

        for(int i=0;i<n;i++){
            if(vis[i]==0){
                eventualSafeNodes(i,vis,ans,graph);
                if(vis[i]==2) ans.add(i);
            }else if(vis[i]==2) ans.add(i);
        }
        
        return ans;
    }

    //Leetcode 329
    public int longestIncreasingPath(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] indegree = new int[n][m];
        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                for(int d=0;d<4;d++){
                    int r = i + dir[d][0];
                    int c = j + dir[d][1];

                    if(r>=0 && c>=0 && r<n && c<m && matrix[i][j]>matrix[r][c])
                        indegree[i][j]++;
                }
            }
        }

        Queue<Integer> q = new LinkedList<Integer>();
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(indegree[i][j]==0) q.add(i*m+j);
            }
        }

        int level = 0;
        while(!q.isEmpty()){
            int size = q.size();
            while(size-->0){
                int idx = q.remove();
                int r = idx/m;
                int c = idx%m;
                for(int d=0;d<4;d++){
                    int x = r + dir[d][0];
                    int y = c + dir[d][1];
                    if(x>=0 && y>=0 && x<n && y<m && matrix[r][c]<matrix[x][y]){
                        if(--indegree[x][y]==0) q.add(x*m+y);
                    }
                }
            }
            level++;
        }

        return level;
    }

    //Leetcode 851
    private int loudAndRich(int i,int[] quiet,int[] ans,HashMap<Integer,List<Integer>> hm){
        ans[i] = i;
        for(int nbr:hm.get(i)){
           int res = (ans[nbr]==-1) ? loudAndRich(nbr,quiet,ans,hm) : ans[nbr];
           if(quiet[res] < quiet[ans[i]]) ans[i] = res;
        }
        return ans[i];
    }
    public int[] loudAndRich(int[][] richer, int[] quiet) {
        int n = quiet.length;
        HashMap<Integer,List<Integer>> hm = new HashMap<>();
        for(int i=0;i<n;i++) hm.put(i,new ArrayList<>());

        for(int[] rich:richer){
            hm.get(rich[1]).add(rich[0]);
        }
        int[] ans = new int[n];
        Arrays.fill(ans,-1);
        for(int i=0;i<n;i++){
            if(ans[i]==-1)
                loudAndRich(i,quiet,ans,hm);
        }
        return ans;
    }

    //Kosaraju's Algorithm
    private void dfs1(int src,boolean[] vis,ArrayList<Integer> list,ArrayList<ArrayList<Integer>> adj){
        vis[src] = true;
        for(int v:adj.get(src)){
            if(!vis[v])
                dfs1(v,vis,list,adj);
        }
        list.add(src);
    }
    private void dfs2(int src,boolean[] vis,ArrayList<Integer> arr,ArrayList<ArrayList<Integer>> graph){
        vis[src] = true;
        arr.add(src);
        for(int v:graph.get(src)){
            if(!vis[v])
                dfs2(v,vis,arr,graph);
        }
    }
    public ArrayList<ArrayList<Integer>> kosaraju(int V, ArrayList<ArrayList<Integer>> adj)
    {
        ArrayList<Integer> list = new ArrayList<>();
        boolean[] vis = new boolean[V];
        for(int i=0;i<V;i++){
            if(!vis[i]){
                dfs1(i,vis,list,adj);
            }
        }
        
        // 2nd step
        ArrayList<ArrayList<Integer>> reverseGraph = new ArrayList<>();
        for(int i=0;i<V;i++){
            reverseGraph.add(new ArrayList<Integer>());
        }
        
        for(int i=0;i<V;i++){
            for(int v:adj.get(i)) reverseGraph.get(v).add(i);
        }
        
        Arrays.fill(vis,false);
        int countSCC = 0;
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        for(int i=V-1;i>=0;i--){
            if(!vis[list.get(i)]){
                ArrayList<Integer> res = new ArrayList<>();
                dfs2(list.get(i),vis,res,reverseGraph);
                ans.add(res);
                countSCC++;
            }
        }

        System.out.println("No. of SCCs : " + countSCC);
        return ans;
    }

    //Union-Find Algorithm
    class UnionFindAlgo{
        int[] par;
        int[] size;
        private int findPar(int u){
            return (u==par[u]) ? u : (par[u] = findPar(par[u]));
        }
        private void merge(int p1,int p2){
            if(size[p1]<size[p2]){
                par[p1] = par[p2];
                size[p2] += size[p1];
            }
            else{
                par[p2] = par[p1];
                size[p1] += size[p2];
            }
        }
        public void UnionFind(int N,int[][] edges){
            @SuppressWarnings("unchecked")
            ArrayList<Edge>[] graph = new ArrayList[N];
            for(int i=0;i<N;i++) graph[i] = new ArrayList<>();

            //edges - u - edges[0], v - edges[1], w - edges[2];
            par = new int[N];
            size = new int[N];
            boolean cycle = false;
            for(int i=0;i<N;i++){
                par[i] = i;
                size[i] = 1;
            }

            for(int[] edge:edges){
                int u = edge[0];
                int v = edge[1];
                int w = edge[2];
                int p1 = findPar(u);
                int p2 = findPar(v);
                if(p1!=p2){
                    merge(p1,p2);
                    addEdge(u,v,w);
                }else cycle = true;
            }

            for(int i=0;i<N;i++){
                System.out.print(i + " -> ");
                for(Edge e:graph[i])
                    System.out.print("(" + e.v +"," + e.w + "), ");
                System.out.println();
            }
            System.out.println(cycle);
        }
    }

    //Leetcode 684
    //Method 1 - Use path compression and size
    class Solution1_RedundantConnection{
        private int findPar(int u,int[] par){
            return (par[u]==u) ? u : (par[u] = findPar(par[u],par));
        }
        private void merge(int p1,int p2,int[] par,int[] size){
            if(size[p1]>size[p2]){
                par[p2] = par[p1];
                size[p1] += size[p2];
            }else{
                par[p1] = par[p2];
                size[p2] += size[p1];
            }
        }
        public int[] findRedundantConnection(int[][] edges) {
            int n = edges.length;
            int[] par = new int[n+1];
            int[] size = new int[n+1];
            for(int i=0;i<=n;i++){
                par[i] = i;
                size[i] = 1;
            }
            for(int[] edge:edges){
                int u = edge[0];
                int v = edge[1];
                int p1 = findPar(u,par);
                int p2 = findPar(v,par);
                if(p1!=p2){
                    merge(p1,p2,par,size);
                }else return edge;
            }
            return new int[0];
        }
    }

    //Method 2 - Using path compression only
    class Solution2_RedundantConnection {
        private int findPar(int u,int[] par){
            return (par[u]==u) ? u : (par[u] = findPar(par[u],par));
        }
        public int[] findRedundantConnection(int[][] edges) {
            int n = edges.length;
            int[] par = new int[n+1];
    
            for(int i=0;i<=n;i++){
                par[i] = i;
            }
            for(int[] edge:edges){
                int u = edge[0];
                int v = edge[1];
                int p1 = findPar(u,par);
                int p2 = findPar(v,par);
                if(p1!=p2){
                    par[p1] = par[p2];
                }else return edge;
            }
            return new int[0];
        }
    }

    //Leetcode 1061
    private int findPar(int u,int[] par){
        return (par[u]==u) ? u : (par[u] = findPar(par[u],par));
    }
    public String smallestEquivalentString(String s1, String s2, String baseStr) {
        int[] par = new int[26];
        for(int i=0;i<26;i++) par[i] = i;

        for(int i=0;i<s1.length();i++){
            char ch1 = s1.charAt(i);
            char ch2 = s2.charAt(i);
            int p1 = findPar(ch1-'a',par);
            int p2 = findPar(ch2-'a',par);

            par[p1] = par[p2] = Math.min(p1,p2);
        }

        String ans = "";
        for(int i=0;i<baseStr.length();i++){
            ans += (char)(findPar(baseStr.charAt(i)-'a',par) + 'a');
        }
        return ans;
    }

    //Leetcode 839
    private boolean isSimilar(String s1,String s2){
        int count = 0;
        for(int i=0;i<s1.length();i++){
            if(s1.charAt(i)!=s2.charAt(i) && ++count>2) return false;
        }
        return true;
    }
    public int numSimilarGroups(String[] strs) {
        int n = strs.length;
        int[] par = new int[n];
        for(int i=0;i<n;i++) par[i] = i;

        int count = n;
        for(int i=0;i<n;i++){
            int p1 = findPar(i,par);
            for(int j=i+1;j<n;j++){
                int p2 = findPar(j,par);
                if(p1!=p2){
                    if(isSimilar(strs[i],strs[j])){
                        par[p2] = par[p1];
                        count--;
                    }
                }
            }
        }
        return count;
    }

    //Lintcode 434
    class Point {
        int x;
        int y;
        Point() { x = 0; y = 0; }
        Point(int a, int b) { x = a; y = b; }
    } 
    public List<Integer> numIslands2(int n, int m, Point[] operators) {
        if(operators == null) return new ArrayList<>();
        int[] par = new int[n*m];
        Arrays.fill(par,-1);
        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        int count = 0;
        List<Integer> ans = new ArrayList<>();

        for(Point point:operators){
            int r = point.x;
            int c = point.y;

            if(par[r*m+c]==-1){
                par[r*m+c] = r*m+c;
                count++;

                for(int d=0;d<4;d++){
                    int nx = r + dir[d][0];
                    int ny = c + dir[d][1];

                    if(nx>=0 && ny>=0 && nx<n && ny<m && par[nx*m+ny]!=-1){
                        int p1 = findPar(r*m+c,par);
                        int p2 = findPar(nx*m+ny,par);
                        if(p1!=p2){
                            count--;
                            par[p1] = par[p2];
                        }
                    }
                }
            }
            ans.add(count);
        }

        return ans;
    }

    //Leetcode 305 - Locked
    public List<Integer> numIslands2(int n,int m,int[][] positions){
        int[] par = new int[n*m];
        Arrays.fill(par,-1);
        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        int count = 0;
        List<Integer> ans = new ArrayList<>();

        for(int[] position:positions){
            int r = position[0];
            int c = position[1];

            if(par[r*m+c]==-1){
                count++;
                par[r*m+c] = r*m+c;

                for(int d=0;d<4;d++){
                    int x = r + dir[d][0];
                    int y = c + dir[d][1];

                    if(x>=0 && y>=0 && x<n && y<m && par[x*m+y]!=-1){
                        int p1 = findPar(r*m+c,par);
                        int p2 = findPar(x*m+y,par);

                        if(p1!=p2){
                            count--;
                            par[p1] = p2;
                        }
                    }
                }
            }
            ans.add(count);
        }

        return ans;
    }

    //Leetcode 200 - Using Union-Find Algo
    public int numIslands_UF(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[] par = new int[n*m];
        for(int i=0;i<n*m;i++) par[i] = i;
        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        int count = 0;

        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]=='1'){
                    count++;

                    for(int d=0;d<2;d++){
                        int x = i + dir[d][0];
                        int y = j + dir[d][1];

                        if(x>=0 && y>=0 && x<n && y<m && grid[x][y]=='1'){
                            int p1 = findPar(i*m+j,par);
                            int p2 = findPar(x*m+y,par);

                            if(p1!=p2){
                                count--;
                                par[p2] = p1;
                            }
                        }
                    }
                }
            }
        }

        return count;
    }

    //Leetcode 695 - Using Union Find Algo
    public int maxAreaOfIsland_UF(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        int[] par = new int[n*m];
        for(int i=0;i<n*m;i++) par[i] = i;
        int maxArea = 0;
        int[][] dir = {{0,1},{1,0}};

        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]==1){
                    maxArea = Math.max(maxArea,grid[i][j]);
                    for(int d=0;d<2;d++){
                        int r = i + dir[d][0];
                        int c = j + dir[d][1];

                        if(r>=0 && c>=0 && r<n && c<m && grid[r][c]==1){
                            int p1 = findPar(i*m+j,par);
                            int p2 = findPar(r*m+c,par);

                            if(p1!=p2){
                                par[p2] = par[p1];
                                grid[p1/m][p1%m] += grid[p2/m][p2%m];
                                maxArea = Math.max(maxArea,grid[p1/m][p1%m]);
                            }
                        }
                    }
                }
            }
        }

        return maxArea;
    }

    //Kruskal MST
    class KruskalMST{
        int[] par;
        int[] size;
        private int findPar(int u){
            return (u==par[u]) ? u : (par[u] = findPar(par[u]));
        }
        private void merge(int p1,int p2){
            if(size[p1]>size[p2]){
                par[p2] = par[p1];
                size[p1] += size[p2];
            }else{
                par[p1] = par[p2];
                size[p2] += size[p1];
            }
        }
        private void UnionFind(int N,int[][] edges){
            @SuppressWarnings("unchecked")
            ArrayList<Edge>[] graph = new ArrayList[N];
            for(int i=0;i<N;i++) graph[i] = new ArrayList<>();

            par = new int[N];
            size = new int[N];
            for(int i=0;i<N;i++){
                par[i] = i;
                size[i] = 1;
            }

            for(int[] edge:edges){
                int u = edge[0];
                int v = edge[1];
                int w = edge[2];

                int p1 = findPar(u);
                int p2 = findPar(v);
                if(p1!=p2){
                    merge(p1,p2);
                    addEdge(u,v,w);
                }
            }

            for(int i=0;i<N;i++){
                System.out.print(i + " -> ");
                for(Edge e:graph[i])
                    System.out.print("(" + e.v +"," + e.w + "), ");
                System.out.println();
            }
        }
        public void solve(int N,int[][] edges){
            Arrays.sort(edges,(a,b) -> {
                return a[2] - b[2];
            });
            UnionFind(N,edges);
        }
    }

    //Leetcode 1168
    //https://leetcode.ca/2019-02-10-1168-Optimize-Water-Distribution-in-a-Village/
    class OptimizeWaterDistribution_Solution{
        private int findPar(int u,int[] par){
            return (u==par[u]) ? u : (par[u] = findPar(par[u],par));
        }
        private void merge(int p1,int p2,int[] size,int[] par){
            if(size[p1]>size[p2]){
                par[p2] = p1;
                size[p1] += size[p2];
            }else{
                par[p1] = p2;
                size[p2] += size[p1];
            }
        }
        public int minCostToSupplyWater(int n,int[] wells,int[][] pipes){
            ArrayList<int[]> graph = new ArrayList<>();

            for(int i=0;i<n;i++){
                graph.add(new int[]{0,i+1,wells[i]});
            }

            for(int[] pipe:pipes) graph.add(pipe);
            Collections.sort(graph, (a,b) -> {
                return a[2] - b[2];
            });

            int[] par = new int[n+1];
            int[] size = new int[n+1];;

            for(int i=0;i<=n;i++){
                par[i] = i;
                size[i] = 1;
            }

            int cost = 0;
            for(int[] edge:graph){
                int u = edge[0];
                int v = edge[1];
                int w = edge[2];

                int p1 = findPar(u,par);
                int p2 = findPar(v,par);

                if(p1!=p2){
                    merge(p1,p2,size,par);
                    cost += w;
                }
            }

            return cost;
        }
    }

    //https://www.hackerrank.com/challenges/journey-to-the-moon/problem
    private void merge(int p1,int p2,int[] par,int[] size){
        if(size[p1]>size[p2]){
            par[p2] = p1;
            size[p1] += size[p2];
        }else{
            par[p1] = p2;
            size[p2] += size[p1];
        }
    }
    public long journeyToMoon(int n, List<List<Integer>> astronaut) {
        int[] par = new int[n];
        int[] size = new int[n];
        for(int i=0;i<n;i++){
            par[i] = i;
            size[i] = 1;
        }
        
        for(List<Integer> group: astronaut){
            int a1 = group.get(0);
            int a2 = group.get(1);
            
            int p1 = findPar(a1,par);
            int p2 = findPar(a2,par);
            
            if(p1!=p2){
                merge(p1,p2,par,size);
            }
        }
        
        long ans = 0;
        long sum = 0;
        
        for(int i=0;i<n;i++){
            if(par[i]==i){
                ans += sum*size[i];
                sum += size[i];
            }
        }
        return ans;

    }

    //https://www.hackerearth.com/practice/algorithms/graphs/minimum-spanning-tree/practice-problems/algorithm/mr-president/
    public int mrPresident(int n,long k,int[][] edges){
        Arrays.sort(edges,(a,b) -> {
            return a[2] - b[2];
        });
        int[] par = new int[n+1];
        for(int i=1;i<=n;i++) par[i] = i;

        long maintenanceCost = 0;
        ArrayList<Integer> MST = new ArrayList<>();
        for(int[] edge:edges){
            int p1 = findPar(edge[0],par);
            int p2 = findPar(edge[1],par);

            if(p1!=p2){
                par[p1] = p2;
                MST.add(edge[2]);
                maintenanceCost += edge[2];
                n--;
            }
        }

        if(n>1) return -1;
        else if(maintenanceCost <= k) return 0;
        int transform = 0;
        for(int i=MST.size()-1;i>=0;i--){
            maintenanceCost = maintenanceCost - MST.get(i) + 1;
            transform++;
            if(maintenanceCost<=k) return transform;
        }

        return (maintenanceCost <= k) ? transform : -1;

    }

    //Leetcode 1489
    private int getMSTWeight(int n,int[][] edges,int[] firstEdge,int deletedEdge){
        int weight = 0;
        int[] par = new int[n];
        for(int i=1;i<n;i++) par[i] = i;

        if(firstEdge.length==4){
            int p1 = findPar(firstEdge[0],par);
            int p2 = findPar(firstEdge[1],par);
            par[p1] = p2;
            weight += firstEdge[2];
        }

        for(int[] edge:edges){
            if(edge[3]==deletedEdge) continue;
            int u = edge[0];
            int v = edge[1];
            int p1 = findPar(u,par);
            int p2 = findPar(v,par);
            if(p1!=p2){
                par[p1] = p2;
                weight += edge[2];
            }
        }

        int root = findPar(0,par);
        for(int i=0;i<n;i++){
            if(findPar(i,par)!=root) return (int)1e8;
        }

        return weight;
    }
    public List<List<Integer>> findCriticalAndPseudoCriticalEdges(int n, int[][] edges) {
        List<Integer> criticalEdges = new ArrayList<>();
        List<Integer> pseudoCriticalEdges = new ArrayList<>();

        for(int i=0;i<edges.length;i++){
            edges[i] = new int[]{edges[i][0],edges[i][1],edges[i][2],i};
        }

        Arrays.sort(edges, (a,b) -> {
            return a[2] - b[2];
        });

        int mstWeight = getMSTWeight(n,edges,new int[]{},-1);

        for(int[] edge:edges){
            int idx = edge[3];

            if(getMSTWeight(n,edges,new int[]{},idx)>mstWeight) 
                criticalEdges.add(idx);
                /*If a edge not included in ST(spanning tree) doesn't affect weight (meaning same mst weight as decrease of mst weight not possible),
                 * then either that edge not included in any MSTs or if included in a ST will either increase weight of Spanning Tree or be equal to it,
                 * So next line of, (if else condition) is  for checking if inclusion of this weight leads to same MST weight. as the above statement 
                 * has already proved that there is one mst which doesn't include this edge.
                  */
            
            else if(getMSTWeight(n,edges,edge,-1)==mstWeight)
                pseudoCriticalEdges.add(idx);
        }

        List<List<Integer>> ans = new ArrayList<>();
        ans.add(criticalEdges);
        ans.add(pseudoCriticalEdges);
        return ans;
    }

    //Leetcode 815
    public int numBusesToDestination(int[][] routes, int source, int target) {
        if(source==target) return 0;

        int busRoutes = routes.length;
        HashSet<Integer> busStands = new HashSet<>();
        HashMap<Integer,ArrayList<Integer>> busStandToBusRoutes = new HashMap<>();
        boolean[] visBusRoutes = new boolean[busRoutes];

        for(int i=0;i<busRoutes;i++){
            for(int stand:routes[i]){
                busStandToBusRoutes.putIfAbsent(stand,new ArrayList<>());
                busStandToBusRoutes.get(stand).add(i);
            }
        }

        Queue<Integer> q = new LinkedList<>();
        q.add(source);
        int buses = 0;
        int size;
        while(!q.isEmpty()){
            size = q.size();
            while(size-->0){
                int stand = q.remove();
                if(busStands.contains(stand)) continue;
                busStands.add(stand);
                if(busStandToBusRoutes.containsKey(stand)){
                    for(int route:busStandToBusRoutes.get(stand)){
                        if(!visBusRoutes[route]){
                            for(int i:routes[route]){
                                if(i==target) return buses+1;
                                if(!busStands.contains(i)) q.add(i);
                            }
                            visBusRoutes[route] = true;
                        }
                    }
                }
            }
            buses++;
        }
        return -1;
    }

    //Leetcode 547
    //Method 1 - DFS
    private void findCircleNum(int node,int n,int[][] isConnected,boolean[]vis){
        vis[node] = true;
        for(int i=0;i<n;i++){
            if(isConnected[node][i]==1 && !vis[i]){
                findCircleNum(i,n,isConnected,vis);
            }
        }
    }
    public int findCircleNum01(int[][] isConnected) {
        int n = isConnected.length;
        boolean[] vis = new boolean[n];
        int ans = 0;
        for(int i=0;i<n;i++){
            if(!vis[i]){
                ans++;
                findCircleNum(i,n,isConnected,vis);
            }
        }
        return ans;
    }

    //Method 2 - Union Find
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;

        int[] par = new int[n];
        for(int i=0;i<n;i++) par[i] = i;
        int ans = n;
        for(int i=0;i<n;i++){
            for(int j=i+1;j<n;j++){
                if(isConnected[i][j]==1){
                    int p1 = findPar(i,par);
                    int p2 = findPar(j,par);
                    if(p1!=p2){
                        par[p2] = p1;
                        ans--;
                    }
                }
            }
        }

        return ans;
    }

    //Leetcode 1020
    private void numEnclavesDFS(int r,int c,int n,int m,int[][] dir,int[][] grid){
        grid[r][c] = 0;

        for(int d=0;d<dir.length;d++){
            int x = r + dir[d][0];
            int y = c + dir[d][1];
            if(x>=0 && y>=0 && x<n && y<m && grid[x][y]==1){
                numEnclavesDFS(x,y,n,m,dir,grid);
            }
        }
    }
    public int numEnclaves(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        for(int i=0;i<n;i++){
            if(grid[i][0]==1) numEnclavesDFS(i,0,n,m,dir,grid);
            if(grid[i][m-1]==1) numEnclavesDFS(i,m-1,n,m,dir,grid);
        }

        for(int i=0;i<m;i++){
            if(grid[0][i]==1) numEnclavesDFS(0,i,n,m,dir,grid);
            if(grid[n-1][i]==1) numEnclavesDFS(n-1,i,n,m,dir,grid);
        }

        int enclaves = 0;
        for(int i=1;i<n-1;i++){
            for(int j=1;j<m-1;j++){
                enclaves += grid[i][j];
            }
        }
        return enclaves;
    }

    //Leetcode 685
    private boolean checkCycle(int u,int v,int[] par2){
        int p1 = findPar(u,par2);
        int p2 = findPar(v,par2);
        if(p1==p2){
            return true;
        }
        par2[p2] = p1;
        return false;
    }
    public int[] findRedundantDirectedConnection(int[][] edges) {
        int n = edges.length;
        int[] par1 = new int[n+1];
        int[] par2 = new int[n+1];
        for(int i=1;i<=n;i++) {
            par1[i] = i;
            par2[i] = i;
        }

        int cycle=-1,conflict=-1;
        for(int i=0;i<n;i++){
            int u = edges[i][0];
            int v = edges[i][1];
            if(par1[v]!=v){
                conflict = i;
            }else{
                par1[v] = u;
                if(checkCycle(u,v,par2)){
                    cycle = i;
                }
            }
        }
        if(conflict==-1){
            return edges[cycle];
        }
        int v = edges[conflict][1];
        if(cycle!=-1){
            return new int[]{par1[v],v};
        }
        return edges[conflict];
    }

    //Leetcode 765
    public int minSwapsCouples(int[] row) {
        int n = row.length;
        int couples = row.length/2;
        int[] par = new int[couples];
        for(int i=0;i<couples;i++) par[i] = i;

        for(int i=0;i<n;i+=2){
            int u = row[i]/2;
            int v = row[i+1]/2;

            int p1 = findPar(u,par);
            int p2 = findPar(v,par);

            if(p1!=p2){
                par[p1] = p2;
            }
        }

        int swaps = couples;
        for(int i=0;i<couples;i++){
            if(par[i]==i){
                swaps--;
            }
        }
        return swaps;
    }

    //Leetcode 721
    private String find(String u,HashMap<String,String> id){
        if(u!=id.get(u)){
            id.put(u,find(id.get(u),id));
        }
        return id.get(u);
    }

    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        HashMap<String,String> emailToName = new HashMap<>();
        HashMap<String,String> id = new HashMap<>();
        HashMap<String,TreeSet<String>> idEmailToEmails = new HashMap<>();

        for(List<String> account:accounts){
            int n = account.size();
            for(int i=1;i<n;i++){
                String email = account.get(i);
                id.putIfAbsent(email,email);
                emailToName.putIfAbsent(account.get(i),account.get(0));
            }
        }

        for(List<String> account:accounts){
            for(int i=2;i<account.size();i++){
                String e1 = account.get(i);
                String e2 = account.get(i-1);
                String p1 = find(e1,id);
                String p2 = find(e2,id);
                id.put(p1,p2);
            }
        }

        for(List<String> account:accounts){
            for(int i=1;i<account.size();i++){
                String email = account.get(i);
                String currId = find(account.get(i),id);
                idEmailToEmails.putIfAbsent(currId,new TreeSet<>());
                idEmailToEmails.get(currId).add(email);
            }
        }

        List<List<String>> ans = new ArrayList<>();
        for(String idEmail:idEmailToEmails.keySet()){
            List<String> emails = new ArrayList<>(idEmailToEmails.get(idEmail));
            String name = emailToName.get(idEmail);
            emails.add(0,name);
            ans.add(emails);
        }

        return ans;
    }

    //Leetcode 694 - Locked
    //Lintcode 860
    private String numberOfDistinctIslands(int i,int j,int n,int m,int[][] dir,char[] dirs,int[][] grid){
        grid[i][j] = 0;
        String shape = "";
        for(int d=0;d<4;d++){
            int r = i + dir[d][0];
            int c = j + dir[d][1];
            if(r>=0 && c>=0 && r<n && c<m && grid[r][c]==1){
                shape += dirs[d];
                shape += numberOfDistinctIslands(r,c,n,m,dir,dirs,grid);
                shape += 'b';
            }
        }
        return shape;
    }
    public int numberofDistinctIslands(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        HashSet<String> shapeSet = new HashSet<>();
        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        char[] dirs = {'r','d','l','u'};

        String shape = "";
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]==1){
                    shape += numberOfDistinctIslands(i,j,n,m,dir,dirs,grid);
                    shapeSet.add(shape);
                    shape = "";
                }
            }
        }
        return shapeSet.size();
    }

    //Leetcode 296 - Locked
    //Lintcode 912
    private int minTotalDistance(ArrayList<Integer> list){
        int i = 0;
        int j = list.size()-1;
        int dist = 0;
        while(i<j){
            dist += list.get(j--) - list.get(i++);
        }

        return dist;
    }
    public int minTotalDistance(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        ArrayList<Integer> I = new ArrayList<>();
        ArrayList<Integer> J = new ArrayList<>();
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(grid[i][j]==1){
                    I.add(i);
                    J.add(j);
                }
            }
        }
        Collections.sort(J);
        return minTotalDistance(I) + minTotalDistance(J);
    }

    //Prim's Algorithm

    class PrimsPair{
        int vtx = 0;
        int par = 0;
        int wt = 0;
        protected PrimsPair(int vtx,int par,int wt){
            this.vtx = vtx;
            this.par = par;
            this.wt = wt;
        }
    }

    public void addEdge(ArrayList<Edge>[] graph,int u,int v,int w){
        graph[u].add(new Edge(v,w));
        graph[v].add(new Edge(u,w));
    }

    public void display(int N,ArrayList<Edge>[] graph){

        for(int i=0;i<N;i++){
            System.out.print(i + " -> ");
            for(Edge e:graph[i]){
                System.out.print("(" + e.v + ", " + e.w + ") ");
            }
            System.out.println();
        }
    }

    //Basic - Method 1
    public void primsAlgo_01(int N,ArrayList<Edge>[] graph){
        boolean[] vis = new boolean[N];

        PriorityQueue<PrimsPair> pq = new PriorityQueue<>((a,b) -> {
            return a.wt - b.wt;
        });

        pq.add(new PrimsPair(0,-1,0));

        @SuppressWarnings("unchecked")
        ArrayList<Edge>[] MST = new ArrayList[N];
        for(int i=0;i<N;i++) MST[i] = new ArrayList<>();

        int numberOfEdges = 0;

        // while (que.size() != 0) { // for disconnected graph and more generic way
        // while (que.size() != 0 && NumberOfEdges < N - 1) { // for disconnected graph
        while(numberOfEdges<N-1){
            PrimsPair p = pq.remove();
            if(vis[p.vtx]) continue;

            while(p.par!=-1){
                addEdge(MST,p.vtx,p.par,p.wt);
                numberOfEdges++;
            }

            vis[p.vtx] = true;
            for(Edge e:graph[p.vtx]){
                if(!vis[e.v]){
                    pq.add(new PrimsPair(e.v,p.vtx,e.w));
                }
            }
        }
        display(N,MST);

    }

    //Method 2
    public void primsAlgo_02(int N,ArrayList<Edge>[] graph){
        boolean[] vis = new boolean[N];
        int[] dis = new int[N];
        Arrays.fill(dis,(int)1e9);

        PriorityQueue<PrimsPair> pq = new PriorityQueue<>((a,b) -> {
            return a.wt - b.wt;
        });

        @SuppressWarnings("unchecked")
        ArrayList<Edge>[] MST = new ArrayList[N];
        for(int i=0;i<N;i++) MST[i] = new ArrayList<>();

        int n = 0;
        pq.add(new PrimsPair(0,-1,0));

        while(n<N-1){
            PrimsPair p = pq.remove();
            if(vis[p.vtx]) continue;

            if(p.par!=-1){
                addEdge(MST,p.par,p.vtx,p.wt);
                n++;
            }

            vis[p.vtx] = true;
            for(Edge e:graph[p.vtx]){
                if(!vis[e.v] && e.w<dis[e.v]){
                    dis[e.v] = e.w;
                    pq.add(new PrimsPair(e.v,p.vtx,e.w));
                }
            }
        }

        display(N,MST);
    }

    protected class DijkstraPair{
        int vtx;
        int wt;
        int wsf;
        int par;
        DijkstraPair(int vtx,int wt,int wsf,int par){
            this.vtx = vtx;
            this.wt = wt;
            this.wsf = wsf;
            this.par = par;
        }
    }

    //Method 1 - Basic
    public void dijkstraAlgo_01(int src,int N,ArrayList<Edge>[] graph){
        boolean[] vis = new boolean[N];
        PriorityQueue<DijkstraPair> pq = new PriorityQueue<>((a,b) -> {
            return a.wsf - b.wsf;
        });
        @SuppressWarnings("unchecked")
        ArrayList<Edge>[] dijkstraPath = new ArrayList[N];
        for(int i=0;i<N;i++) dijkstraPath[i] = new ArrayList<>();

        int n = 0;
        pq.add(new DijkstraPair(src,0,0,-1));
        while(n<N-1){
            DijkstraPair p = pq.remove();
            if(vis[p.vtx]) continue;

            if(p.vtx!=src){
                addEdge(dijkstraPath,p.vtx,p.par,p.wt);
                n++;
            }

            vis[p.vtx] = true;
            for(Edge e:graph[p.vtx]){
                if(!vis[e.v]){
                    pq.add(new DijkstraPair(e.v,e.w,p.wsf+e.w,p.vtx));
                }
            }
        }
        display(N,dijkstraPath);
    }

    //Method 2 - Better
    protected class dijkstraPair{
        int vtx;
        int wt;
        int wsf;
        dijkstraPair(int vtx,int wt,int wsf){
            this.vtx = vtx;
            this.wt = wt;
            this.wsf = wsf;
        }
    }
    public void dijkstraAlgo_02(int src,int N,ArrayList<Edge>[] graph){
        PriorityQueue<dijkstraPair> pq = new PriorityQueue<>((a,b) ->{
            return a.wsf - b.wsf;
        });

        pq.add(new dijkstraPair(src,0,0));
        int n = 0;

        @SuppressWarnings("unchecked")
        ArrayList<Edge>[] dijkstraPath = new ArrayList[N];
        boolean[] vis = new boolean[N];
        int[] dis = new int[N];
        int[] par = new int[N];

        Arrays.fill(dis,(int)1e8);
        Arrays.fill(par,-1);
        dis[src] = 0;
        while(n<N-1){
            dijkstraPair p = pq.remove();
            if(vis[p.vtx]) continue;

            if(par[p.vtx]!=-1){
                addEdge(dijkstraPath,p.vtx,par[p.vtx],p.wt);
                n++;
            }

            vis[p.vtx] = true;
            for(Edge e:graph[p.vtx]){
                if(!vis[e.v] && e.w + p.wsf < dis[e.v]){
                    dis[e.v] = e.w + p.wsf;
                    par[e.v] = p.vtx;
                    pq.add(new dijkstraPair(e.v,e.w,p.wsf+e.w));
                }
            }
        }
        display(N,dijkstraPath);
    }

    //Leetcode 743
    public int networkDelayTime(int[][] times, int n, int k) {
        @SuppressWarnings("unchecked")
        ArrayList<int[]>[] graph = new ArrayList[n+1];
        for(int i=1;i<=n;i++){
            graph[i] = new ArrayList<>();
        }
        for(int[] edge:times){
            graph[edge[0]].add(new int[] {edge[1],edge[2]});
        }

        int numberOfEdges = 0;
        boolean[] vis = new boolean[n+1];
        int[] dis = new int[n+1];
        Arrays.fill(dis,(int)1e9);

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> {
            return a[1] - b[1];
        });
        pq.add(new int[]{k,0});
        int ans = 0;

        while(pq.size()!=0){
            int[] edge = pq.remove();
            if(vis[edge[0]]) continue;

            if(edge[0]!=k) numberOfEdges++;
            ans = Math.max(ans,edge[1]);
            vis[edge[0]] = true;
            for(int[] e:graph[edge[0]]){
                if(!vis[e[0]] && e[1] + edge[1] < dis[e[0]]){
                    dis[e[0]] = e[1] + edge[1];
                    pq.add(new int[]{e[0],dis[e[0]]});
                }
            }
        }

        if(numberOfEdges!=n-1) return -1;
        else return ans;
    }

    //Leetcode 1631
    public int minimumEffortPath(int[][] heights) {
        int n = heights.length;
        int m = heights[0].length;

        int[][] dist = new int[n][m];
        for(int[] d:dist) Arrays.fill(d,(int)1e8);
        dist[0][0] = 0;

        boolean[][] vis = new boolean[n][m];
        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> {
            return a[1] - b[1];
        });

        pq.add(new int[]{0,0});

        while(!pq.isEmpty()){
            int[] vtx = pq.remove();
            int u = vtx[0];
            int r = u/m;
            int c = u%m;
            if(vis[r][c]) continue;
            
            vis[r][c] = true;
            if(r==n-1 && c==m-1) break;
            for(int d=0;d<4;d++){
                int i = r + dir[d][0];
                int j = c + dir[d][1];
                if(i>=0 && j>=0 && i<n && j<m && !vis[i][j]){
                    int max = Math.max(vtx[1],Math.abs(heights[r][c]-heights[i][j]));
                    if(max < dist[i][j]){
                        dist[i][j] = max;
                        pq.add(new int[]{i*m+j,max});
                    }
                }
            }

        }
        return dist[n-1][m-1];

    }

    //Leetcode 1584
    //Method 1 - Using Kruskal - 891 ms
    public int minCostConnectPoints01(int[][] points) {
        int n = points.length;
        
        List<List<Integer>> edges = new ArrayList<>();
        int w;
        for(int i=0;i<n;i++){
            for(int j=i+1;j<n;j++){
                w = Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
                List<Integer> edge = new ArrayList<>();
                edge.add(i);
                edge.add(j);
                edge.add(w);
                edges.add(edge);
            }
        }

        Collections.sort(edges,(a,b) -> {
            return a.get(2) - b.get(2);
        });

        int[] par = new int[n];
        for(int i=1;i<n;i++) par[i] = i;
        w = 0;
        for(List<Integer> edge:edges){
            int u = edge.get(0);
            int v = edge.get(1);
            int p1 = findPar(u,par);
            int p2 = findPar(v,par);
            if(p1!=p2){
                par[p1] = p2;
                w += edge.get(2);
            }
        }
        return w;
    }

    //Method 2 - Prims - Method 1 - 132 ms
    public int minCostConnectPoints02(int[][] points) {
        int n = points.length;
        @SuppressWarnings("unchecked")
        List<int[]> [] graph = new ArrayList[n];
        for(int i=0;i<n;i++) graph[i] = new ArrayList<>();
        int w;
        for(int i=0;i<n;i++){
            for(int j=i+1;j<n;j++){
                w = Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
                graph[i].add(new int[]{j,w});
                graph[j].add(new int[]{i,w});
            }
        }

        w = 0;
        int numberOfEdges = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> {
            return a[2] - b[2];
        });
        boolean[] vis = new boolean[n];
        pq.add(new int[]{0,-1,0});
        while(numberOfEdges<n-1){
            int[] vtx = pq.remove();
            if(vis[vtx[0]]) continue;

            if(vtx[1]!=-1){
                w += vtx[2];
                numberOfEdges++;
            }

            vis[vtx[0]] = true;
            for(int[] e:graph[vtx[0]]){
                if(!vis[e[0]]){
                    pq.add(new int[]{e[0],vtx[0],e[1]});
                }
            }

        }
        return w;
    }

    //Method 3 - Prims - Method 2 - 87ms
    public int minCostConnectPoints(int[][] points) {
        int n = points.length;
        @SuppressWarnings("unchecked")
        List<int[]> [] graph = new ArrayList[n];
        for(int i=0;i<n;i++) graph[i] = new ArrayList<>();
        int w;
        for(int i=0;i<n;i++){
            for(int j=i+1;j<n;j++){
                w = Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
                graph[i].add(new int[]{j,w});
                graph[j].add(new int[]{i,w});
            }
        }

        w = 0;
        int numberOfEdges = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> {
            return a[2] - b[2];
        });
        boolean[] vis = new boolean[n];
        int[] dis = new int[n];
        Arrays.fill(dis,(int)1e9);
        pq.add(new int[]{0,-1,0});
        while(numberOfEdges<n-1){
            int[] vtx = pq.remove();
            if(vis[vtx[0]]) continue;

            if(vtx[1]!=-1){
                w += vtx[2];
                numberOfEdges++;
            }

            vis[vtx[0]] = true;
            for(int[] e:graph[vtx[0]]){
                if(!vis[e[0]] && e[1]<dis[e[0]]){
                    dis[e[0]] = e[1];
                    pq.add(new int[]{e[0],vtx[0],e[1]});
                }
            }

        }
        return w;
    }

    //Leetcode 1135 - Locked
    //Lintcode 3672
    protected class PrimsTuple{
        int vtx;
        int par;
        int w;
        PrimsTuple(int vtx,int par,int w){
            this.vtx = vtx;
            this.par = par;
            this.w = w;
        }
    }
    public int minimumCost(int n, int[][] connections) {
        if(n==1) return 0;
        @SuppressWarnings("unchecked")
        List<Edge>[] graph = new ArrayList[n+1];
        for(int i=0;i<=n;i++) graph[i] = new ArrayList<>();

        for(int[] edge:connections){
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];
            graph[u].add(new Edge(v,w));
            graph[v].add(new Edge(u,w));
        }

        PriorityQueue<PrimsTuple> pq = new PriorityQueue<>((a,b) -> {
            return a.w - b.w;
        });

        pq.add(new PrimsTuple(1,-1,0));
        boolean[] vis = new boolean[n+1];
        int[] dist = new int[n+1];
        Arrays.fill(dist,(int)1e9);
        int cost = 0;
        int edges = 0;
        while(!pq.isEmpty()){
            PrimsTuple u = pq.remove();
            if(vis[u.vtx]) continue;

            if(u.par!=-1) edges++;
            cost += u.w;

            vis[u.vtx] = true;
            if(edges==n-1) break;
            for(Edge e:graph[u.vtx]){
                if(!vis[e.v] && e.w<dist[e.v]){
                    dist[e.v] = e.w;
                    pq.add(new PrimsTuple(e.v,u.vtx,e.w));
                }
            }
        }

        return (edges==n-1) ? cost : -1;
    }

    
    //Leetcode 787
    //Method 1 - Using Dijkstra Algorithm - Time Limit Exceeded
    public int findCheapestPrice_01(int n, int[][] flights, int src, int dst, int k) {
        @SuppressWarnings("unchecked")
        ArrayList<int[]>[] graph = new ArrayList[n];
        for(int i=0;i<n;i++) graph[i] = new ArrayList<>();
        
        for(int[] flight:flights){
            graph[flight[0]].add(new int[]{flight[1],flight[2]});
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> {
            return a[1] - b[1];
        });
        pq.add(new int[]{src,0,0});

        while(pq.size()!=0){
            int[] vtx = pq.remove();
            
            if(vtx[0]==dst) return vtx[1];
            if(vtx[2] >= k+1) continue;

            for(int[] edge:graph[vtx[0]]){
                pq.add(new int[]{edge[0],edge[1]+vtx[1],vtx[2]+1});
            }
        }

        return -1;
    }

    //Articulation Point and Bridges
    class ArticulationPointAndBridges{
        static int[] dis, low, AP;
        static boolean[] vis, isAP;
        static int time=0, noOfCallsFromRoot=0;
        private static void dfs_APB(int src,int par,int n,ArrayList<Integer>[] graph){
            vis[src] = true;
            dis[src] = low[src] = time++;

            for(int nbr:graph[src]){
                if(!vis[nbr]){
                    if(par==-1) noOfCallsFromRoot++;
                    dfs_APB(nbr,src,n,graph);

                    if(dis[src] <= low[nbr]){
                        AP[src]++;
                        isAP[src] = true;
                    }

                    if(dis[src] < low[nbr]){
                        System.out.println("AP Edge: "+ src + " -> "+nbr);
                    }

                    low[src] = Math.min(low[src],low[nbr]);
                }else if(nbr!=par){
                    low[src] = Math.min(low[src],dis[nbr]);
                }
            }
        }
        public static void APB(int n,ArrayList<Integer>[] graph){
            dis = new int[n];
            low = new int[n];
            AP = new int[n];
            vis = new boolean[n];
            isAP = new boolean[n];

            for(int i=0;i<n;i++){
                if(!vis[i]){
                    dfs_APB(i,-1,n,graph);
                    if(noOfCallsFromRoot==1){
                        AP[i] = 0;
                        isAP[i] = false;
                    }
                    noOfCallsFromRoot = 0;
                }
            }

            int countOfAP = 0;
            for(int i=0;i<N;i++){
                if(isAP[i]){
                    countOfAP++;
                    System.out.println("AP: "+ i + " @ "+ "Increase in no. of components: "+ AP[i]);
                }
            }
            System.out.println(countOfAP);
        }
    }

    //Leetcode 1192
    class Solution_CriticalConnectionsInANetwork{
        int[] dis,low;
        boolean[] vis;
        int time;
        private void dfs(int src,int par,List<List<Integer>> ans,List<List<Integer>> graph){
            dis[src]  = low[src] = time++;
            vis[src] = true;
    
            for(int nbr:graph.get(src)){
                if(!vis[nbr]){
                    dfs(nbr,src,ans,graph);
    
                    if(dis[src] < low[nbr]){
                        ans.add(Arrays.asList(src,nbr));
                    }
    
                    low[src] = Math.min(low[src],low[nbr]);
                }else if(nbr!=par){
                    low[src] = Math.min(low[src],dis[nbr]);
                }
            }
        }
        public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
            List<List<Integer>> graph = new ArrayList<>();
            for(int i=0;i<n;i++) graph.add(new ArrayList<>());
    
            for(List<Integer> connection:connections){
                graph.get(connection.get(0)).add(connection.get(1));
                graph.get(connection.get(1)).add(connection.get(0));
            }
    
            dis = new int[n];
            low = new int[n];
            vis = new boolean[n];
            time = 0;
    
            List<List<Integer>> ans = new ArrayList<>();
    
            dfs(0,-1,ans,graph);
    
            return ans;
        }
    }

    //Leetcode 1568
    class Solution {
        int[] dis,low;
        int time;
        int rootCalls;
        int[][] dir;
        boolean res;
        private void minDaysAPB_DFS(int i,int j,int par,int n,int m,boolean[][] vis,int[][] grid){
            int idx = i*m+j;
            dis[idx] = low[idx] = time++;
            vis[i][j] = true;
            for(int d=0;d<4;d++){
                int r = i + dir[d][0];
                int c = j + dir[d][1];
    
                if(r>=0 && c>=0 && r<n && c<m && grid[r][c]==1){
                    if(!vis[r][c]){
                        if(par==-1) rootCalls++;
                        minDaysAPB_DFS(r,c,idx,n,m,vis,grid);
    
                        if(dis[idx] <= low[r*m+c]){
                            if(par!=-1) res = true;
                            else if(rootCalls>1) res = true;
                        }
                        low[idx] = Math.min(low[idx],low[r*m+c]);
                    }
                    else if(r*m+c!=par){
                        low[idx] = Math.min(low[idx],dis[r*m+c]);
                    }
                }
            }
            
        }
        private void minDays(int i,int j,int n,int m,boolean[][] vis,int[][] grid){
            dis = new int[n*m];
            low = new int[n*m];
            time = 0;
            rootCalls = 0;
    
            dir = new int[][]{{0,1},{1,0},{0,-1},{-1,0}};
            minDaysAPB_DFS(i,j,-1,n,m,vis,grid);
        }
        public int minDays(int[][] grid) {
            int n = grid.length;
            int m = grid[0].length;
    
            boolean flag = false;
            boolean[][] vis = new boolean[n][m];
            for(int i=0;i<n;i++){
                for(int j=0;j<m;j++){
                    if(grid[i][j]==1 && !vis[i][j]){
                        if(flag) return 0;
                        minDays(i,j,n,m,vis,grid);
                        flag = true;
                    }
                }
            }
    
            if(time==0) return 0;
    
            if(time==1) return 1;
    
            if(res) return 1;
    
            return 2;
        }
    }

    //Bellman Ford Algorithm - {u,v,w}
    public static void bellmanFord(int src,int[][] edges,int n){
        int[] dp = new int[n];
        Arrays.fill(dp,(int)1e9);
        dp[src] = 0;
        boolean isNegativeCycle = false;
        int[] arr;
        for(int edgeCount=1;edgeCount<=n;edgeCount++){
            arr = dp.clone();

            for(int[] edge:edges){
                int u = edge[0];
                int v = edge[1];
                int w = edge[2];

                if(dp[u]!=(int)1e9 && dp[u] + w < arr[v]){
                    if(edgeCount==n){
                        isNegativeCycle = true;
                        break;
                    }

                    arr[v] = dp[u] + w;
                }
            }

            dp = arr;
        }

        System.out.println("Negative Cycle: "+isNegativeCycle);
    }

    //Leetcode 787
    //Method 2 - Using Bellman Ford Algorithm
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        int[] dp = new int[n];
        Arrays.fill(dp,(int)1e9);
        dp[src] = 0;
        int[] arr;
        boolean res = true;
        for(int stop=1;stop<=k+1;stop++){
            res = true;
            arr = dp.clone();
            for(int[] flight:flights){
                int u = flight[0];
                int v = flight[1];
                int w = flight[2];

                if(dp[u]!=(int)1e9 && (dp[u] + w < arr[v])){
                    arr[v] = dp[u] + w;
                    res = false;
                }
            }
            if(res) break;
            dp = arr;
        }

        return (dp[dst]!=(int)1e9) ? dp[dst] : -1;
    }

    //Floyd Warshall Algorithm
    public void shortest_distance(int[][] matrix)
    {
        int n = matrix.length;
        
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(matrix[i][j]==-1) matrix[i][j] = (int)1e9;
                if(i==j) matrix[i][j] = 0;
            }
        }
        
        for(int k=0;k<n;k++){
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                    matrix[i][j] = Math.min(matrix[i][j],matrix[i][k]+matrix[k][j]);
                }
            }
        }

        /*
        -----How to find a negative cycle-----
        for(int i=0;i<n;i++){
            if(matrix[i][i]<0) System.out.println("Negative Cycle Found");
        }
        */
        
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(matrix[i][j]==(int)1e9) matrix[i][j] = -1;
            }
        }
    }

    //Leetcode 1334
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int[][] matrix = new int[n][n];
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<n;i++){
            map.put(i,0);
            for(int j=0;j<n;j++){
                if(i==j) matrix[i][j] = 0;
                else matrix[i][j] = (int)1e9;
            }
        }

        for(int[] edge:edges){
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];
            matrix[u][v] = w;
            matrix[v][u] = w;
            if(w<=distanceThreshold){
                int cnt = map.get(u);
                map.put(u,++cnt);
                cnt = map.get(v);
                map.put(v,++cnt);
            }
        }

        
        for(int k=0;k<n;k++){
            for(int i=0;i<n;i++){
                for(int j=i+1;j<n;j++){
                    if(matrix[i][k]==(int)1e9 || matrix[k][j]==(int)1e9) continue;
                    if(matrix[i][j] > matrix[i][k] + matrix[k][j]){
                        if(matrix[i][j] > distanceThreshold && matrix[i][k]+matrix[k][j] <= distanceThreshold){
                            int cnt = map.get(i);
                            map.put(i,cnt+1);
                            cnt = map.get(j);
                            map.put(j,cnt+1);

                        }
                        matrix[i][j] = matrix[j][i] = matrix[i][k] + matrix[k][j];
                    }
                }
            }
        }

        int count = (int)1e9;
        int city = -1;
        for(int i=0;i<n;i++){
            if(map.get(i)<count){
                count = map.get(i);
                city = i;
            }else if(map.get(i)==count) city = Math.max(city,i);
        }
        return city;
    }

    //Leetcode 924
    //Method 1 - Using Union-Find
    public int minMalwareSpread01(int[][] graph, int[] initial) {
        int n = graph.length;
        int[] par = new int[n];
        for(int i=1;i<n;i++) par[i] = i;
        int[] size = new int[n];
        Arrays.fill(size,1);

        for(int i=0;i<n;i++){
            for(int j=i+1;j<n;j++){
                if(graph[i][j]==1){
                    int p1 = findPar(i,par);
                    int p2 = findPar(j,par);

                    if(p1!=p2){
                        merge(p1,p2,par,size);
                    }
                }
            }
        }

        Arrays.sort(initial);

        int[] infectedNodes = new int[n];
        for(int i:initial){
            int p = findPar(i,par);
            infectedNodes[p]++;
        }

        int ans = initial[0];
        int max = 0;
        for(int i:initial){
            int p = findPar(i,par);
            if(infectedNodes[p]==1 && size[p]>max){
                max = size[p];
                ans = i;
            }
        }
        return ans;
    }

    //Method 2 - Using DFS
    private int minMalwareSpeedDFS(int u,int n,boolean[] vis,boolean[] infected,int[][] graph){
        if(infected[u]) return 0;
        int count = 1;
        vis[u] = true;
        int c;
        for(int v=0;v<n;v++){
            if(graph[u][v]==1 && !vis[v]){
                c = minMalwareSpeedDFS(v,n,vis,infected,graph);
                if(c==0) {
                    infected[u] = true;
                    return 0;
                }
                count += c;
            }
        }
        return count;
    }
    public int minMalwareSpreadI(int[][] graph, int[] initial) {
        int n = graph.length;

        boolean[] infected = new boolean[n];
        for(int i:initial) infected[i] = true;

        int ans = n;
        int count;
        int max = -1;
        for(int i:initial){
            infected[i] = false;
            boolean[] vis = new boolean[n];
            count = minMalwareSpeedDFS(i,n,vis,infected,graph);
            if(count>max || (count==max && i<ans)){
                max = count;
                ans = i;
            }
            infected[i] = true;
        }
        return ans;
    }

    //Leetcode 928
    private int minMalwareSpeedDFS(int u,int src,int n,boolean[] vis,boolean[] infected,int[][] graph){
        if(infected[u] && u!=src) return 0;
        vis[u] = true;
        int count = 1;
        int c;
        boolean flag = false;
        for(int v=0;v<n;v++){
            if(graph[u][v]==1 && !vis[v]){
                c = minMalwareSpeedDFS(v,src,n,vis,infected,graph);
                if(c==0){
                    infected[u] = true;
                    flag = true;
                }
                count += c;
            }
        }
        return (flag && u!=src) ? 0 : count;
    }
    public int minMalwareSpreadII(int[][] graph, int[] initial) {
        int n = graph.length;
        
        boolean[] infected = new boolean[n];
        for(int i:initial) infected[i] = true;
        int ans = initial[0];
        int max = 0;
        int count;
        for(int u:initial){
            boolean[] vis = new boolean[n];
            count = 0;
            count += minMalwareSpeedDFS(u,u,n,vis,infected,graph);
            if(count>max || (count==max && u<ans)){
                max = count;
                ans = u;
            }
        }
        return ans;
    }

    //Leetcode 399
    private void buildGraph(HashMap<String,HashMap<String,Double>> graph,List<List<String>> equations,double[] values){

        for(int i=0;i<equations.size();i++){
            String u = equations.get(i).get(0);
            String v = equations.get(i).get(1);
            graph.putIfAbsent(u,new HashMap<>());
            graph.get(u).put(v,values[i]);
            graph.putIfAbsent(v,new HashMap<>());
            graph.get(v).put(u,1/values[i]);
        }
    }
    private double getPathWeight(String u,String v,HashSet<String> vis,HashMap<String,HashMap<String,Double>> graph){
        if(!graph.containsKey(u)) return -1.0;

        if(graph.get(u).containsKey(v)) return graph.get(u).get(v);

        vis.add(u);

        for(HashMap.Entry<String,Double> nbr:graph.get(u).entrySet()){
            if(!vis.contains(nbr.getKey())){
                double product = getPathWeight(nbr.getKey(),v,vis,graph);
                if(product!= -1.0){
                    return nbr.getValue()*product;
                }
            }
        }
        return -1.0;
    }
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        HashMap<String,HashMap<String,Double>> graph = new HashMap<>();
        buildGraph(graph,equations,values);

        int size = queries.size();
        double[] result = new double[size];
        for(int i=0;i<size;i++){
            result[i] = getPathWeight(queries.get(i).get(0),queries.get(i).get(1),new HashSet<String>(),graph);
        }
        return result;
    }

    //Mother Vertex
    //https://www.geeksforgeeks.org/problems/mother-vertex/1
    private void motherVertexDFS(int u,boolean[] vis,List<Integer> list,ArrayList<ArrayList<Integer>>adj){
        vis[u] = true;
        for(int v:adj.get(u)){
            if(!vis[v]) motherVertexDFS(v,vis,list,adj);
        }
        
        list.add(u);
    }
    public int findMotherVertex(int V, ArrayList<ArrayList<Integer>>adj)
    {
        boolean[] vis = new boolean[V];
        List<Integer> list = new ArrayList<>();
        
        for(int i=0;i<V;i++){
            if(!vis[i]){
                motherVertexDFS(i,vis,list,adj);
            }
        }
        
        int motherVertex = list.get(V-1);
        vis = new boolean[V];
        list = new ArrayList<>();
        motherVertexDFS(motherVertex,vis,list,adj);
        return (list.size()==V) ? motherVertex : -1;
    }

    //Leetcode 542
    private int[][] updateMatrixBFS(int[][] mat){
        int n = mat.length;
        int m = mat[0].length;
        
        Queue<Integer> q = new LinkedList<>();
        boolean[][] vis = new boolean[n][m];
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(mat[i][j]==0) {
                    q.add(i*m+j);
                    vis[i][j] = true;
                }
            }
        }

        if(q.size()==n*m) return mat;
        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        int level = 0;
        while(q.size()!=0){
            int size = q.size();
            while(size-->0){
                int idx = q.remove();
                int r = idx/m;
                int c = idx%m;

                for(int d=0;d<4;d++){
                    int x = r + dir[d][0];
                    int y = c + dir[d][1];

                    if(x>=0 && y>=0 && x<n && y<m && !vis[x][y]){
                        q.add(x*m+y);
                        vis[x][y] = true;
                        mat[x][y] = level+1;
                    }
                }
            }
            level++;
        }

        return mat;
    }
    public int[][] updateMatrix(int[][] mat) {
        return updateMatrixBFS(mat);
    }

    //Leetcode 127
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if(beginWord.equals(endWord)) return 0;

        HashSet<String> dict = new HashSet<>();
        for(String s:wordList) dict.add(s);

        if(!dict.contains(endWord)) return 0;
        HashSet<String> vis = new HashSet<>();
        Queue<String> q = new LinkedList<>();
        q.add(beginWord);
        int level = 1;
        int size;

        while(q.size()!=0){
            size = q.size();
            while(size-->0){
                String word = q.remove();
                StringBuilder nwsb = new StringBuilder(word);

                for(int i=0;i<word.length();i++){
                    char ch = word.charAt(i);

                    for(int j=0;j<26;j++){
                        if('a'+ j == ch) continue;
                        nwsb.setCharAt(i,(char)('a' + j));
                        String str = nwsb.toString();
                        if(dict.contains(str) && !vis.contains(str)){
                            if(str.equals(endWord)) return level+1;
                            vis.add(str);
                            q.add(str);
                        }
                    }
                    nwsb.setCharAt(i,ch);
                }

            }
            level++;
        }
        return 0;
    }

    //Leetcode 934
    private void shortestBridgeDFS(int i,int j,int n,Queue<Integer> q,int[][] dir,boolean[][] vis,int[][] grid){
        vis[i][j] = true;
        grid[i][j] = 0;
        q.add(i*n+j);

        for(int d=0;d<4;d++){
            int r = i + dir[d][0];
            int c = j + dir[d][1];

            if(r>=0 && c>=0 && r<n && c<n && grid[r][c]==1){
                shortestBridgeDFS(r,c,n,q,dir,vis,grid);
            }
        }

    }
    private int shortestBridgeBFS(int n,Queue<Integer> q,int[][] dir,boolean[][] vis,int[][] grid){

        int level = 0;
        int size;

        while(q.size()!=0){
            size = q.size();
            while(size-->0){
                int idx = q.remove();
                int r = idx/n;
                int c = idx%n;

                for(int d=0;d<4;d++){
                    int x = r + dir[d][0];
                    int y = c + dir[d][1];

                    if(x>=0 && y>=0 && x<n && y<n && !vis[x][y]){
                        if(grid[x][y]==1) return level;
                        q.add(x*n+y);
                        vis[x][y] = true;
                    }
                }
            }
            level++;
        }
        return -1;
    }
    public int shortestBridge(int[][] grid) {
        int n = grid.length;

        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        Queue<Integer> q = new LinkedList<>();
        boolean[][] vis = new boolean[n][n];
        boolean found = false;

        for(int i=0;i<n;i++){
            if(found) break;
            for(int j=0;j<n;j++){
                if(grid[i][j]==1){
                    shortestBridgeDFS(i,j,n,q,dir,vis,grid);
                    found = true;
                    break;
                }
            }
        }

        return shortestBridgeBFS(n,q,dir,vis,grid);
    }

    //Leetcode 1162
    //Method 1 - Using BFS
    private int maxDistanceBFS(int n,Queue<Integer> q,int[][] grid){
        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};

        int level = 0;
        while(q.size()!=0){
            int size = q.size();
            while(size-->0){
                int idx = q.remove();
                int r = idx/n;
                int c = idx%n;
                
                for(int d=0;d<4;d++){
                    int x = r + dir[d][0];
                    int y = c + dir[d][1];

                    if(x>=0 && y>=0 && x<n && y<n && grid[x][y]==0){
                        q.add(x*n+y);
                        grid[x][y] = 1;
                    }
                }
            }
            if(!q.isEmpty()) level++;
        }

        return level;
    }
    public int maxDistance01(int[][] grid) {
        int n = grid.length;
        Queue<Integer> q = new LinkedList<>();

        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(grid[i][j]==1){
                    q.add(i*n+j);
                }
            }
        }

        if(q.isEmpty() || q.size()==n*n) return -1;
        return maxDistanceBFS(n,q,grid);
    }

    //Method 2 - Optimal Solution
    public int maxDistance(int[][] grid) {
        int n = grid.length;
        int x = n+n;

        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(grid[i][j]==1) continue;

                int top = x;
                int left = x;

                if(i-1>=0) top = grid[i-1][j];
                if(j-1>=0) left = grid[i][j-1];

                grid[i][j] = Math.min(top,left) + 1;
            }
        }

        int ans = -(int)1e8;
        for(int i=n-1;i>=0;i--){
            for(int j=n-1;j>=0;j--){
                if(grid[i][j]==1) continue;

                int bottom = x;
                int right = x;

                if(i+1<n) bottom = grid[i+1][j];
                if(j+1<n) right = grid[i][j+1];

                grid[i][j] = Math.min(grid[i][j],Math.min(bottom,right)+1);
                ans = Math.max(ans,grid[i][j]);
            }
        }

        if(ans-1==x+1 || ans==1 || ans==-(int)1e8) return -1;
        return ans-1;
    }

    //Leetcode 773
    private int slidingPuzzleBFS(int n,int m,String start,String goal,int[][] board){
        Queue<String> q = new LinkedList<>();
        HashSet<String> vis = new HashSet<>();
        q.add(start);
        int level = 0;
        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        int size;

        while(!q.isEmpty()){
            size = q.size();
            while(size-->0){
                String s = q.remove();
                int zeroIdx = s.indexOf('0');
                int r = zeroIdx/m;
                int c = zeroIdx%m;

                for(int d=0;d<4;d++){
                    int x = r + dir[d][0];
                    int y = c + dir[d][1];

                    if(x>=0 && y>=0 && x<n && y<m){
                        int swapIdx = x*m+y;
                        StringBuilder sb = new StringBuilder(s);
                        sb.setCharAt(zeroIdx,s.charAt(swapIdx));
                        sb.setCharAt(swapIdx,'0');
                        String t = sb.toString();
                        if(t.equals(goal)) return level+1;
                        if(!vis.contains(t)){
                            vis.add(t);
                            q.add(t);
                        }
                    }
                }
            }
            level++;
        }
        return -1;
    }
    public int slidingPuzzle(int[][] board) {
        int n = 2;
        int m = 3;
        String goal = "123450";
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                sb.append((char)(board[i][j] + '0'));
            }
        }

        String start = sb.toString();
        if(start.equals(goal)) return 0;
        return slidingPuzzleBFS(n,m,start,goal,board);
    }

    //Leetcode 1034
    private void colorBorderDFS(int i,int j,int n,int m,int initialColor,int color,int[][] dir,boolean[][] vis,int[][] grid){
        if(i==0 || j==0 || i==n-1 || j==m-1) grid[i][j] = color;
        vis[i][j] = true;

        for(int d=0;d<4;d++){
            int r = i + dir[d][0];
            int c = j + dir[d][1];

            if(r>=0 && c>=0 && r<n && c<m && !vis[r][c]){
                if(grid[r][c]==initialColor) colorBorderDFS(r,c,n,m,initialColor,color,dir,vis,grid);
                else grid[i][j] = color;
            }
        }
    }
    public int[][] colorBorder(int[][] grid, int i, int j, int color) {
        if(color==grid[i][j]) return grid;
        
        int n = grid.length;
        int m = grid[0].length;
        boolean[][] vis = new boolean[n][m];
        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        colorBorderDFS(i,j,n,m,grid[i][j],color,dir,vis,grid);
        return grid;
    }

    //Doctor Strange
    //https://practice.geeksforgeeks.org/problems/doctor-strange2206/1
    class Complete{
        static int[] dis,low;
        static int time,rootCalls;
        static boolean[] vis,isAP;
            // Function for finding maximum and value pair
        public static void doctorStrangeAP_DFS(int src,int par,ArrayList<Integer>[] graph){
            dis[src] = low[src] = time++;
            vis[src] = true;
            
            for(int nbr:graph[src]){
                if(!vis[nbr]){
                    if(par==-1) rootCalls++;
                    
                    doctorStrangeAP_DFS(nbr,src,graph);
                    
                    if(dis[src] <= low[nbr]){
                        isAP[src] = true;
                    }
                    
                    low[src] = Math.min(low[src],low[nbr]);
                }else if(nbr!=par){
                    low[src] = Math.min(low[src],dis[nbr]);
                }
            }
        }
        public static int doctorStrange (int n, int k, int g[][]) {
            @SuppressWarnings("unchecked")
            ArrayList<Integer>[] graph = new ArrayList[n+1];
            for(int i=0;i<=n;i++){
                graph[i] = new ArrayList<>();
            }
            
            for(int[] edge:g){
                int u = edge[0];
                int v = edge[1];
                graph[u].add(v);
                graph[v].add(u);
            }
            
            low = new int[n+1];
            dis = new int[n+1];
            vis = new boolean[n+1];
            isAP = new boolean[n+1];
            time = 0;
            rootCalls = 0;
            
            for(int i=1;i<=n;i++){
                if(!vis[i]){
                    doctorStrangeAP_DFS(i,-1,graph);
                }
            }
            
            int ans = 0;
            if(isAP[1] && rootCalls>1) ans++;
            for(int i=2;i<=n;i++){
                if(isAP[i]) ans++;
            }
            return ans;
        }
    }

    //Leetcode 959
    private void merge(int idx1,int idx2,int[] par){
        int p1 = findPar(idx1,par);
        int p2 = findPar(idx2,par);
        par[p2] = p1;
    }
    public int regionsBySlashes(String[] grid) {
        int n = grid.length;
        int noOfParts = n*n*4;
        int[] par = new int[noOfParts];
        for(int i=0;i<noOfParts;i++){
            par[i] = i;
        }


        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                char ch = grid[i].charAt(j);
                int idx = i*4*n + 4*j;
                if(ch==' '){
                    merge(idx,idx+1,par);
                    merge(idx+1,idx+2,par);
                    merge(idx+2,idx+3,par);
                }else if(ch=='/'){
                    merge(idx,idx+3,par);
                    merge(idx+1,idx+2,par);
                }else if(ch=='\\'){
                    merge(idx,idx+1,par);
                    merge(idx+2,idx+3,par);
                }
                if(j<n-1) merge(idx+1,idx+7,par);
                if(i<n-1) merge(idx+2,idx+4*n,par);
            }
        }
        int ans = 0;
        for(int i=0;i<noOfParts;i++){
            if(par[i]==i) ans++;
        }
        return ans;
    }

    //Leetcode 734 - Locked
    //Lintcode 856
    public boolean isSentenceSimilarity(String[] words1, String[] words2, List<List<String>> pairs) {
        HashMap<String,HashSet<String>> graph = new HashMap<>();
        for(List<String> pair:pairs){
            String u = pair.get(0);
            String v = pair.get(1);
            graph.putIfAbsent(u,new HashSet<>());

            graph.get(u).add(v);
            graph.putIfAbsent(v,new HashSet<>());
            graph.get(v).add(u);
        }


        for(int i=0;i<words1.length;i++){
            String s1 = words1[i];
            String s2 = words2[i];
            if(s1.equals(s2)) continue;
            if(!graph.containsKey(s1)) return false;
            else if(!graph.get(s1).contains(s2)) return false; 
        }
        return true;
    }


    //Leetcode 737 - Locked
    //https://protegejj.gitbook.io/algorithm-practice/leetcode/graph/737-sentence-similarity-ii
    private boolean areSentencesSimilar(String[] words1,String[] words2,HashMap<String,Integer> strToIdx,int[] par){
        for(int i=0;i<words1.length;i++){
            String s1 = words1[i];
            String s2 = words2[i];

            if(!strToIdx.containsKey(s1) || !strToIdx.containsKey(s2)) return false;
            int p1 = findPar(strToIdx.get(s1),par);
            int p2 = findPar(strToIdx.get(s2),par);

            if(p1!=p2) return false;
        }
        return true;
    }
    public boolean areSentencesSimilar(String[] words1, String[] words2, String[][] pairs){
        if(words1.length!=words2.length) return false;

        int[] par = new int[2*pairs.length];
        for(int i=0;i<2*pairs.length;i++) par[i] = i;
        HashMap<String,Integer> strToIdx = new HashMap<>();
        int idx=0;
        for(String[] pair:pairs){
            String s1 = pair[0];
            String s2 = pair[1];
            if(!strToIdx.containsKey(s1)){
                strToIdx.put(s1,idx++);
            }
            if(!strToIdx.containsKey(s2)){
                strToIdx.put(s2,idx++);
            }

            int p1 = findPar(strToIdx.get(s1),par);
            int p2 = findPar(strToIdx.get(s2),par);

            if(p1!=p2){
                par[p2] = p1;
            }
        }

        return areSentencesSimilar(words1, words2, strToIdx, par);
    }

    //Leetcode 133
    class Solution_CloneGraph{
        class Node {
            public int val;
            public List<Node> neighbors;
            public Node() {
                val = 0;
                neighbors = new ArrayList<Node>();
            }
            public Node(int _val) {
                val = _val;
                neighbors = new ArrayList<Node>();
            }
            public Node(int _val, ArrayList<Node> _neighbors) {
                val = _val;
                neighbors = _neighbors;
            }
        }
        private HashMap<Node,Node> map;
        private Node cloneGraphDFS(Node node){
            Node newNode = new Node(node.val);
            map.put(node,newNode);

            for(Node nbr:node.neighbors){
                if(!map.containsKey(nbr)){
                    newNode.neighbors.add(cloneGraphDFS(nbr));
                }else{
                    newNode.neighbors.add(map.get(nbr));
                }
            }

            return newNode;
        }
        public Node cloneGraph(Node node) {
            if(node==null) return null;
            map = new HashMap<>();
            return cloneGraphDFS(node);
        }
    }

    //Leetcode 417
    private void DFS(int i,int j,int n,int m,int[][] dir,boolean[][] vis,int[][] heights){
        vis[i][j] = true;
        int r,c;
        int height = heights[i][j];
        for(int d=0;d<4;d++){
            r = i + dir[d][0];
            c = j + dir[d][1];
            if(r>=0 && c>=0 && r<n && c<m && !vis[r][c] && height<=heights[r][c])
                DFS(r,c,n,m,dir,vis,heights);
        }
    }
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        int n = heights.length;
        int m = heights[0].length;

        boolean[][] pacific = new boolean[n][m];
        boolean[][] atlantic = new boolean[n][m];
        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        for(int i=0;i<n;i++){
            if(!pacific[i][0]) DFS(i,0,n,m,dir,pacific,heights);
            if(!atlantic[i][m-1]) DFS(i,m-1,n,m,dir,atlantic,heights);
        }

        for(int j=0;j<m;j++){
            if(!pacific[0][j]) DFS(0,j,n,m,dir,pacific,heights);
            if(!atlantic[n-1][j]) DFS(n-1,j,n,m,dir,atlantic,heights);
        }

        List<List<Integer>> ans = new ArrayList<>();
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(pacific[i][j] && atlantic[i][j]){
                    List<Integer> temp = new ArrayList<>();
                    temp.add(i);
                    temp.add(j);
                    ans.add(new ArrayList<>(temp));
                }
            }
        }

        return ans;
    }

    //Leetcode 990
    private void union(int u,int v,int[] par){
        int p1 = findPar(u,par);
        int p2 = findPar(v,par);

        if(p1!=p2){
            par[p1] = p2;
        }
    }
    public boolean equationsPossible(String[] equations) {
        int[] par = new int[26];
        for(int i=0;i<26;i++) par[i] = i;

        for(String eq:equations){
            if(eq.charAt(1)=='='){
                union(eq.charAt(0)-'a',eq.charAt(3)-'a',par);
            }
        }

        for(String eq:equations){
            if(eq.charAt(1)=='!'){
                int p1 = findPar(eq.charAt(0)-'a',par);
                int p2 = findPar(eq.charAt(3)-'a',par);
                if(p1==p2) return false;
            }
        }

        return true;
    }

    //Leetcode 2316
    public long countPairs(int n, int[][] edges) {
        int[] par = new int[n];
        long[] size = new long[n];
        for(int i=0;i<n;i++) {
            par[i] = i;
            size[i] = 1;
        }

        int u,v,p1,p2;
        for(int[] edge:edges){
            u = edge[0];
            v = edge[1];
            p1 = findPar(u,par);
            p2 = findPar(v,par);

            if(p1!=p2){
                par[p1] = p2;
                size[p2] += size[p1];
            }
        }

        long count = 0;
        long remainingNodes = n;
        long s;
        for(int i=0;i<n;i++){
            if(par[i]==i){
                s = size[i];
                count += s * (remainingNodes-s);
                remainingNodes -= s;
            }
        }
        return count;
    }

    //Leetcode 433
    public int minMutation(String startGene, String endGene, String[] bank) {
        HashSet<String> bankSet = new HashSet<>();
        boolean flag = false;
        for(String str:bank){
            if(endGene.equals(str)) flag = true;
            bankSet.add(str);
        }
        if(!flag) return -1;

        HashSet<String> vis = new HashSet<>();
        Queue<String> q = new LinkedList<>();
        q.add(startGene);
        vis.add(startGene);

        int min = 0;
        char[] chars = {'A','C','G','T'};
        while(!q.isEmpty()){
            int size = q.size();
            while(size-->0){
                String gene = q.remove();
                StringBuilder sb = new StringBuilder(gene);
                for(int i=0;i<8;i++){
                    for(int j=0;j<4;j++){
                        sb.setCharAt(i,chars[j]);
                        if(!vis.contains(sb.toString()) && bankSet.contains(sb.toString())){
                            String str = sb.toString();
                            if(str.equals(endGene)) return min+1;
                            q.add(str);
                            vis.add(str);
                        }
                        sb.setCharAt(i,gene.charAt(i));
                    }
                }
            }
            min++;
        }

        return -1;
    }

    //Leetcode 947
    //Method 1 - 27ms
    public int removeStones01(int[][] stones) {
        
        int n = 10001;
        int[] par = new int[2*n];
        for(int i=0;i<2*n;i++) par[i] = i;

        for(int[] stone:stones){
            int x = stone[0];
            int y = stone[1];
            int p1 = findPar(x,par);
            int p2 = findPar(y+n,par);

            if(p1!=p2){
                par[p2] = p1;
            }
        }
        HashSet<Integer> uniqueRoots = new HashSet<>();
        for(int[] stone:stones){
            uniqueRoots.add(findPar(stone[0],par));
        }

        return stones.length - uniqueRoots.size();
    }


    //Method 2 - 4ms
    public int removeStones(int[][] stones) {
        
        int n = 10001;
        int[] par = new int[2*n];
        for(int i=0;i<2*n;i++) par[i] = i;

        for(int[] stone:stones){
            int x = stone[0];
            int y = stone[1];
            int p1 = findPar(x,par);
            int p2 = findPar(y+n,par);

            if(p1!=p2){
                par[p2] = p1;
            }
        }
        HashSet<Integer> uniqueRoots = new HashSet<>();
        for(int[] stone:stones){
            uniqueRoots.add(findPar(stone[0],par));
        }

        return stones.length - uniqueRoots.size();
    }

    //Leetcode 1926
    public int nearestExit(char[][] maze, int[] entrance) {
        int n = maze.length;
        int m = maze[0].length;

        Queue<int[]> q = new LinkedList<>(); 
        q.add(entrance);
        maze[entrance[0]][entrance[1]] = '+';

        int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
        int steps = 0;

        while(!q.isEmpty()){
            int size = q.size();
            while(size-->0){
                int[] loc = q.remove();
                int x = loc[0];
                int y = loc[1];
                for(int d=0;d<4;d++){
                    int r = x + dir[d][0];
                    int c = y + dir[d][1];
                    if(r>=0 && c>=0 && r<n&& c<m && maze[r][c]=='.'){
                        if(r==0 || c==0 || r==n-1 || c==m-1) return steps+1;
                        q.add(new int[]{r,c});
                        maze[r][c] = '+';
                    }
                    
                }
            }
            steps++;
        }

        return -1;
    }

    //Leetcode 1971
    //Method 1 - 51ms
    private boolean validPathDFS(int src,int dest,boolean[] vis,List<Integer>[] graph){
        vis[src] = true;
        if(src==dest){
            return true;
        }

        for(int v:graph[src]){
            if(!vis[v]){
                if(validPathDFS(v,dest,vis,graph))
                    return true;
            }
        }
        return false;
    }
    public boolean validPath01(int n, int[][] edges, int source, int destination) {
        @SuppressWarnings("unchecked")
        List<Integer>[] graph = new ArrayList[n];
        for(int i=0;i<n;i++) graph[i] = new ArrayList<>();

        for(int[] edge:edges){
            int u = edge[0];
            int v = edge[1];
            if(u==source && v==destination) return true;
            graph[u].add(v);
            graph[v].add(u);
        }
        boolean[] vis = new boolean[n];
        return validPathDFS(source,destination,vis,graph);
        
    }

    //Method 2 - 15ms
    public boolean validPath(int n, int[][] edges, int source, int destination) {
        if(source==destination) return true;

        int[] par = new int[n];
        for(int i=0;i<n;i++) par[i] = i;

        for(int[] edge:edges){
            int u = edge[0];
            int v = edge[1];
            int p1 = findPar(u,par);
            int p2 = findPar(v,par);
            if(p1!=p2){
                par[p2] = p1;
            }
        }

        int p1 = findPar(source,par);
        int p2 = findPar(destination,par);
        return p1==p2;
    }

    //Leetcode 841
    class Solution_KeysAndRooms {
        private int count = 0;
        private boolean canVisitAllRooms(int src,int n,boolean[] vis,List<List<Integer>> rooms){
            vis[src] = true;
            count++;
            if(count==n) return true;
            for(int v : rooms.get(src)){
                if(!vis[v]){
                    if(canVisitAllRooms(v,n,vis,rooms)) return true;
                }
            }
    
            return false;
        }
        public boolean canVisitAllRooms(List<List<Integer>> rooms) {
            int n = rooms.size();
            boolean[] vis = new boolean[n];
            return canVisitAllRooms(0,n,vis,rooms);
        }
    }

    //Leetcode 886
    //Method 1 - BFS
    private boolean possibleBipartition(int i,List<List<Integer>> graph,int[] vis){
        Queue<Integer> q = new LinkedList<>();
        q.add(i);
        int colour = 0;
        while(!q.isEmpty()){
            int size = q.size();
            while(size-->0){
                int vtx = q.remove();
                if(vis[vtx]!=-1){
                    if(vis[vtx]!=colour) return false;
                    continue;
                }

                vis[vtx] = colour;
                for(int v:graph.get(vtx)){
                    if(vis[v]==-1) q.add(v);
                }
            }
            colour = (colour+1)%2;
        }
        return true; 
    }
    public boolean possibleBipartitionBFS(int n, int[][] dislikes) {
        int[] vis = new int[n+1];
        Arrays.fill(vis,-1);
        boolean ans = false;
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0;i<=n;i++) graph.add(new ArrayList<>());
        for(int[] edge:dislikes){
            int u = edge[0];
            int v = edge[1];
            graph.get(u).add(v);
            graph.get(v).add(u);
        }
        for(int i=1;i<=n;i++){
            if(vis[i]==-1)
                ans = possibleBipartition(i,graph,vis);
            if(!ans) return false;
        }
        return true;
    }

    //Method 2 - DFS
    private boolean possibleBipartition(int u,int currVal,List<List<Integer>> graph,int[] vis){
        vis[u] = currVal;
        int nextVal = (currVal+1)%2;
        for(int v:graph.get(u)){
            if(vis[v]==-1 && !possibleBipartition(v,nextVal,graph,vis)) return false;
            if(vis[v]!=nextVal) return false;
        }
        return true;
    }
    public boolean possibleBipartition(int n, int[][] dislikes) {
        int[] vis = new int[n+1];
        Arrays.fill(vis,-1);
        boolean ans = false;
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0;i<=n;i++) graph.add(new ArrayList<>());
        for(int[] edge:dislikes){
            int u = edge[0];
            int v = edge[1];
            graph.get(u).add(v);
            graph.get(v).add(u);
        }
        for(int i=1;i<=n;i++){
            if(vis[i]==-1)
                ans = possibleBipartition(i,0,graph,vis);
            if(!ans) return false;
        }
        return true;
    }

    //Leetcode 797
    private void allPathsSourceTarget(int src,int dest,boolean[] vis,List<Integer> res,List<List<Integer>> ans,int[][] graph){
        if(src==dest){
            res.add(src);
            ans.add(new ArrayList<>(res));
            res.remove(res.size()-1);
            return;
        }

        vis[src] = true;
        res.add(src);
        for(int v:graph[src]){
            if(!vis[v])
                allPathsSourceTarget(v,dest,vis,res,ans,graph);
        }
        res.remove(res.size()-1);
        vis[src] = false;
        
    }
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        int n = graph.length;
        boolean[] vis = new boolean[n];
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        allPathsSourceTarget(0,n-1,vis,res,ans,graph);
        return ans;
    }

    //Leetcode 1443
    private int minTime(int u,int par,List<Boolean> hasApple,List<List<Integer>> graph){
        int time = 0;
        for(int v:graph.get(u)){
            if(v==par) continue;

            int recTime = minTime(v,u,hasApple,graph);

            if(recTime>0 || hasApple.get(v))
                time += 2 + recTime;
            
        }
        return time;
    }
    public int minTime(int n, int[][] edges, List<Boolean> hasApple) {
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0;i<n;i++) graph.add(new ArrayList<>());

        for(int[] edge:edges){
            int u = edge[0];
            int v = edge[1];

            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        return minTime(0,-1,hasApple,graph);
    }

    //Leetcode 1519
    private void countSubTrees(int u,int par,int[] result,int[] count,String labels,List<List<Integer>> graph){
        char label = labels.charAt(u);

        int countBefore = count[label-'a'];
        count[label-'a']++;

        for(int v:graph.get(u)){
            if(v!=par){
                countSubTrees(v,u,result,count,labels,graph);
            }
        }

        result[u] = count[label-'a'] - countBefore;
    }
    public int[] countSubTrees(int n, int[][] edges, String labels) {
        if(n==1) return new int[]{1};

        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0;i<n;i++) graph.add(new ArrayList<>());
        for(int[] edge:edges){
            int u = edge[0];
            int v = edge[1];
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        int[] ans = new int[n];
        int[] count = new int[26];
        countSubTrees(0,-1,ans,count,labels,graph);
        return ans;
    }

    //Leetcode 2246
    private int longestPath(int u,String s,int[] ans,List<List<Integer>> graph){
        char label = s.charAt(u);
        int longest = 0;
        int secondLongest = 0;
        for(int v:graph.get(u)){
            int recAns = longestPath(v,s,ans,graph);
            if(s.charAt(v)==label) continue;
            secondLongest = Math.max(secondLongest,recAns);
            if(secondLongest>longest){
                int temp = secondLongest;
                secondLongest = longest;
                longest = temp;
            }
        }

        int max = Math.max(1,Math.max(secondLongest,longest)+1);
        ans[0] = Math.max(ans[0],Math.max(max,secondLongest+longest+1));
        return max;
    }
    public int longestPath(int[] parent, String s) {
        int n = parent.length;
        int[] ans = new int[1];
        List<List<Integer>> graph = new ArrayList<>();
        for(int i=0;i<n;i++) graph.add(new ArrayList<>());

        for(int i=1;i<n;i++){
            graph.get(parent[i]).add(i);
        }

        longestPath(0,s,ans,graph);
        return ans[0];
    }

    //Leetcode 2421
    public int numberOfGoodPaths(int[] vals, int[][] edges) {
        int n = vals.length;
        int[] par = new int[n];
        int[][] valIdxPairs = new int[n][2];
        @SuppressWarnings("unchecked")
        List<Integer>[] graph = new ArrayList[n];
        for(int i=0;i<n;i++) graph[i] = new ArrayList<>();
        for(int[] edge:edges){
            int u = edge[0];
            int v = edge[1];
            graph[u].add(v);
            graph[v].add(u);
        }

        HashMap<Integer,HashMap<Integer,Integer>> componentSize = new HashMap<>();

        for(int i=0;i<n;i++){
            par[i] = i;
            valIdxPairs[i] = new int[]{vals[i],i};
            componentSize.put(i,new HashMap<>());
            componentSize.get(i).put(vals[i],1);
        }

        Arrays.sort(valIdxPairs,(a,b) -> {
            return a[0] - b[0];
        });
        int ans = n;
        for(int [] pair:valIdxPairs){
            int value = pair[0];
            int node = pair[1];

            for(int nbr:graph[node]){
                if(vals[nbr] > value) continue;

                int p1 = findPar(node,par);
                int p2 = findPar(nbr,par);

                if(p1!=p2){
                    ans += componentSize.get(p1).getOrDefault(value,0) * componentSize.get(p2).getOrDefault(value,0);
                    par[p1] = p2;
                    int size = componentSize.get(p1).getOrDefault(value,0) + componentSize.get(p2).getOrDefault(value,0);
                    componentSize.get(p2).put(value,size);
                }
            }
        }

        return ans;
    }

    //Leetcode 2093 - Locked
    //Lintcode 3755
    public int minimumCost(int n, int[][] roads, int discounts) {
        @SuppressWarnings("unchecked")
        List<int[]> [] graph = new ArrayList[n];
        for(int i=0;i<n;i++) graph[i] = new ArrayList<>();

        for(int[] road:roads){
            int u = road[0];
            int v = road[1];
            int w = road[2];
            graph[u].add(new int[]{v,w});
            graph[v].add(new int[]{u,w});
        }

        int[][] distance = new int[n][discounts+1];
        for(int[] dist:distance) Arrays.fill(dist,(int)1e8);

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> {
            return a[1] - b[1];
        });

        pq.add(new int[]{0,0,0});
    
        while(!pq.isEmpty()){
            int[] vtx = pq.remove();
            int node = vtx[0];
            int cost = vtx[1];
            int usedDiscounts = vtx[2];

            if(usedDiscounts > discounts || distance[node][usedDiscounts] <= cost) continue;
            if(node==n-1) return cost;
            
            distance[node][usedDiscounts] = cost;
            for(int[] edge:graph[node]){
                int v = edge[0];
                int w = edge[1];

                pq.add(new int[]{v,cost+w,usedDiscounts});

                if(usedDiscounts < discounts){
                    pq.add(new int[]{v,cost+w/2,usedDiscounts+1});
                }
            }
        }

        return -1;
    }
}
