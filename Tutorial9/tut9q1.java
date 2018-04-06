import java.util.*;

class Graph {
   private int[][] _matrix;
   private boolean _directed;
   public Graph(int numVertices, boolean directed) {
      _matrix = new int[numVertices][numVertices];
      _directed = directed;
   }
   public void addEdge(int from, int to) {
      if (!_directed) _matrix[to][from] = 1;
      _matrix[from][to] = 1;
   }
   public void printDFS() {
      boolean[] vis = new boolean[_matrix.length];
      for (int currVert = 0; currVert < _matrix.length; currVert++) {
         if (vis[currVert]) continue; // comp. alr. done, not my problem
         printDFS(currVert, vis); // DFS this component
   }
      System.out.println();
   }
   private void printDFS(int from, boolean[] vis) {
      vis[from] = true;
      System.out.print(from + " "); // this vertex is done
      for (int to = 0; to < _matrix[from].length; to++) {
         if (_matrix[from][to] == 0) continue; // no edge
         if (vis[to]) continue; // done already, not my problem
         printDFS(to, vis); // DFS my unvisited neighbours
      } 
   }

   public void printBFS() {
      boolean[] vis = new boolean[_matrix.length];
      for (int currVert = 0; currVert < _matrix.length; currVert++) {
         if (vis[currVert]) continue; // comp. alr. done, not my problem
         printBFS(currVert, vis); // BFS this component
      }
      System.out.println();
   }
   
   private void printBFS(int startVert, boolean[] vis) {
      Queue<Integer> toVisit = new LinkedList<Integer>();
      vis[startVert] = true; toVisit.offer(startVert);
      while (!toVisit.isEmpty()) {
         int from = toVisit.poll();
         System.out.print(from + " ");
         for (int to = 0; to < _matrix[from].length; to++) {
            if (_matrix[from][to] == 0) continue; // no edge
            if (vis[to]) continue; // done already, not my problem
            vis[to] = true; toVisit.offer(to); // BFS unvisited nghbrs.
         } 
      }
   }

   public void printTopoOrder() { // _directed == true, no cycles
      int[] inDegrs = new int[_matrix.length]; // build list of in-degrs
      for (int from = 0; from < _matrix.length; from++)
         for (int to = 0; to < _matrix[from].length; to++)
            if (_matrix[from][to] != 0) inDegrs[to]++;
      // find vertices with no pre-reqs
      Queue<Integer> noPreReqs = new LinkedList<Integer>();
      for (int currVert = 0; currVert < _matrix.length; currVert++)
         if (inDegrs[currVert] == 0) noPreReqs.offer(currVert);
      while (!noPreReqs.isEmpty()) {
         int from = noPreReqs.poll();
         System.out.print(from + " ");
         for (int to = 0; to < _matrix[from].length; to++) {
            if (_matrix[from][to] == 0) continue; // no edge
            if (--inDegrs[to] == 0) // 1 less pre-requisite
               noPreReqs.offer(to); // found anth. vert with 0 pre-reqs
         }
      }
      System.out.println();
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("[ " + _matrix.length + " vertices :\n");
      for (int row = 0; row < _matrix.length; row++) {
         sb.append("\t" + row + " : [" + _matrix[row][0]);
         for (int col = 1; col < _matrix[row].length; col++)
            sb.append("," + _matrix[row][col]);
         sb.append("]\n");
      }
      return sb + "]";
   }

   public boolean containsCycle() {
      if (_directed) return containsCycleDir();
      return containsCycleUndir();
   }
   
   /***************************************************************************
   Part a
   ***************************************************************************/
   private boolean containsCycleUndir() {
      boolean[] vis = new boolean[_matrix.length];
      
      for (int currVert = 0; currVert < _matrix.length; currVert++) {
         if (vis[currVert]) continue; // comp. alr. done, not my problem
         if (findCycleDFS(currVert, -1, vis)) // DFS this comp., dont skip
            return true; // found cycle in this CC
      }
      return false; // no cycle in all CCs
   }

   private boolean findCycleDFS(int from, int parent, boolean[] vis) {
      vis[from] = true;
      for (int to = 0; to < _matrix[from].length; to++) {
         if (to == parent) continue; // dont go back to where you came from
         if (_matrix[from][to] == 0) continue; // no edge
         if (vis[to]) return true;             // we visited here before -> cycle
            if (findCycleDFS(to, from, vis)) // DFS all nghbrs. except parent
            return true; // has cycle further down
      }
      return false; // no cycle from here down
   }

   /***************************************************************************
   Part b
   ***************************************************************************/
   private static final int DOWNWARDS = 1, COMPLETED = 2;
   private boolean containsCycleDir() {
      int[] vis = new int[_matrix.length];

      for (int vert = 0; vert < _matrix.length; vert ++) {
         if (vis[vert] == COMPLETED) continue; // strongly connected component done, not my prob.
         if (findDirCycleDFS(vert, vis)) // DFS this SCC
            return true; // found cycle in this SCC
      }
      return false; // no cycle in all SCCs
   }
   private boolean findDirCycleDFS(int from, int[] vis) {
      vis[from] = DOWNWARDS; // if visit again before returning -> cycle

      for (int to = 0; to < _matrix[from].length; to++) {
         if (_matrix[from][to] == 0) continue; // no edge
         if (vis[to] == COMPLETED) continue; // no cycle here onwards
         if (vis[to] == DOWNWARDS) return true; // we're back! -> cycle
   if (findDirCycleDFS(to, vis)) // DFS all nghbrs.
      return true; // has cycle further down
   }
    
   vis[from] = COMPLETED; // came back up -> no cycle from here down
    
   return false;
   }
}
