class SuperMario {
   public static final int FREE = 0, MARIO = 1, PRINCESS = 2, ENEMY = 3;
   private int[][] _grid;
   public SuperMario(int[][] grid) { _grid = grid; }

   public int findMinDist() {} // (b)

   public static void main(String[] args) {
      SuperMario mario = new SuperMario(new int[][]{
         {FREE, FREE, {FREE, MARIO, {FREE, FREE, {FREE, FREE, {FREE, ENEMY,
         FREE, FREE}, ENEMY, FREE}, ENEMY, FREE}, FREE, FREE}, PRINCESS, FREE}
         });
      if (mario.canSavePrincess())
         System.out.println("YEAAAH, mario saved the princess after " +
                             mario.findMinDist() + " moves");
      else
         System.out.println("NOooooooo, couldn't save the princess =(");
   }

   /***************************************************************************
   Part a
   ***************************************************************************/
   private static final int[][] DIRS = { // possible edges for each vertex
      {0, -1}, {0, 1}, {-1, 0}, {1, 0}};
   public boolean canSavePrincess() {
      int marioRow = 0, marioCol = 0; // find Mario
      row:
      for (int row = 0; row < _grid.length; row++) {
         for (int col = 0; col < _grid[row].length; col++) {
            if (_grid[row][col] == MARIO) {
               marioRow = row; marioCol = col;
               break row; // break out of 2x for loops instead of innermost
            }
         } 
      }

      boolean[][] vis = new boolean[_grid.length][_grid[0].length];
      Queue<Point> toVisit = new LinkedList<Point>();
      vis[marioRow][marioCol] = true; // remember to shrink problem
      toVisit.offer(new Point(marioRow, marioCol)); // start with Mario

      while (!toVisit.isEmpty()) {
         Point currCell = toVisit.poll();
         int currRow = currCell.getRow(), currCol = currCell.getCol();

         for (int[] nbr : DIRS) {
            int nbrRow = currRow + nbr[0], nbrCol = currCol + nbr[1];
            if (nbrRow < 0 || nbrRow >= _grid.length ||
                  nbrCol < 0 || nbrCol >= _grid[0].length)
               continue; // no edge
            if (vis[nbrRow][nbrCol]) continue; // done alr., not my prob
            if (_grid[nbrRow][nbrCol] == ENEMY) continue; // unreachable
            if (_grid[nbrRow][nbrCol] == PRINCESS) return true; // found!!
            vis[nbrRow][nbrCol] = true; // shrink problem
            toVisit.offer(new Point(nbrRow, nbrCol)); // try this direction
         } 
      }
      return false; // error 404 princess not found =X
   }

   /***************************************************************************
   Part b
   ***************************************************************************/
   public int findMinDist() {
      int marioRow = 0, marioCol = 0; // find Mario
      row: for (int row = 0; row < _grid.length; row++) {
         for (int col = 0; col < _grid[row].length; col++) {
            if (_grid[row][col] == MARIO) {
               marioRow = row; marioCol = col;
               break row; // break out of 2x for loops instead of innermost
            }
         } 
      }
      
      boolean[][] vis = new boolean[_grid.length][_grid[0].length];
      Queue<Point> toVisit = new LinkedList<Point>();
      vis[marioRow][marioCol] = true; // remember to shrink problem
      toVisit.offer(new Point(marioRow, marioCol)); // start with Mario
      
      while (!toVisit.isEmpty()) {
         Point currCell = toVisit.poll();
         int currRow = currCell.getRow(), currCol = currCell.getCol(),
            currDist = currCell.getDistFromSource();
      
         for (int[] nbr : DIRS) {
            int nbrRow = currRow + nbr[0], nbrCol = currCol + nbr[1];
            if (nbrRow < 0 || nbrRow >= _grid.length ||
                  nbrCol < 0 || nbrCol >= _grid[0].length)
               continue; // no edge
            if (vis[nbrRow][nbrCol]) continue; // done alr., not my prob
            if (_grid[nbrRow][nbrCol] == ENEMY) continue; // unreachable
            if (_grid[nbrRow][nbrCol] == PRINCESS) return 1 + currDist;
            vis[nbrRow][nbrCol] = true; // shrink problem, try moving on
            toVisit.offer(new Point(nbrRow, nbrCol, 1 + currDist));
         } 
      }
      return -1; // error 404 princess not found =X
   }

}

class Point {
   private int _row, _col, _distFromSource;
   public Point(int row, int col) { this(row, col, 0); } // for (a)
   public Point(int row, int col, int distFromSource) { // for (b)
      _row = row; _col = col; _distFromSource = distFromSource;
   }
   public int getRow() { return _row; }
   public int getCol() { return _col; }
   public int getDistFromSource() { return _distFromSource; }
   public String toString () {
      return "(" + _row + "," + _col + "," + _distFromSource + ")";
   }
}
