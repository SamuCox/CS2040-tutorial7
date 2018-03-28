import java.util.*;

class TreeNode {
   private int _item;
   private TreeNode _left, _right;

   public TreeNode(int item) { this(item, null, null); }
   public TreeNode(int item, TreeNode left, TreeNode right) {
      _item = item; _left = left; _right = right;
   }

   @Override public String toString() { return "" + _item; }
   public int getItem() { return _item; }
   public TreeNode getLeft() { return _left; }
   public TreeNode getRight() { return _right; }

   public void setLeft(TreeNode left) { _left = left; }
   public void setRight(TreeNode right) { _right = right; }
}


class BinaryTree {
   public int _numNodes;
   public TreeNode _root;

   private String toPreOrder(TreeNode localRoot) {
      return (localRoot == null ? "" : " " + localRoot +
         toPreOrder(localRoot.getLeft()) +
         toPreOrder(localRoot.getRight()) );
   }
   private String toInOrder(TreeNode localRoot) {
      return (localRoot == null ? "" :
         toInOrder(localRoot.getLeft()) + " " + localRoot +
         toInOrder(localRoot.getRight()) );
   }
   private String toPostOrder(TreeNode localRoot) {
      return (localRoot == null ? "" :
         toPostOrder(localRoot.getLeft()) +
         toPostOrder(localRoot.getRight()) + " " + localRoot);
   }
   @Override public String toString() {
      return "[" + _numNodes + ":\n" +
         "Pre: " + toPreOrder(_root) + "\n" +
         "In:  " + toInOrder(_root) + "\n" +
         "Post:" + toPostOrder(_root) + "\n]";
   } 

   public static BinaryTree fromPreorderInorder(int[] pre, int[] in) {
      BinaryTree tree = new BinaryTree();
      if (pre.length == 0)
         return tree;
      
      tree._numNodes = pre.length;
      tree._root = fromPreorderInorder(pre, in, 0, pre.length - 1,
         0, in.length - 1);
      
      return tree;
   }
   private static TreeNode fromPreorderInorder(int[] pre, int[] in,
   int preLow, int preHigh, int inLow, int inHigh) {
      
      if (inLow > inHigh) return null; // base case : no root
   
      int inRootIdx = -1; // find where local root is in inorder traversal
      for (int inIdx = inLow; inIdx <= inHigh; inIdx++) {
         if (in[inIdx] == pre[preLow]) {
            inRootIdx = inIdx;
            break;
         }
      }
      int leftSize = inRootIdx - inLow;
      return new TreeNode(pre[preLow], // item, left child, right child
         fromPreorderInorder(pre, in, preLow + 1, preLow + leftSize,
            inLow, inRootIdx - 1),
         fromPreorderInorder(pre, in, preLow + leftSize + 1, preHigh,
            inRootIdx + 1, inHigh)
      );
   }



   public void ceilIterative(int target) {
      if (_root == null) {
         System.out.println("No ceiling of " + target);
         return; 
      }
      // Solved iteratively:
      TreeNode localRoot = _root, result = null;
      while (localRoot != null) {
         if (target == localRoot.getItem()) { // found ceiling
            result = localRoot;
            break;
         }
         if (target > localRoot.getItem()) { // kill root and left subtree
            localRoot = localRoot.getRight();
            continue; 
         }
         result = localRoot; // candidate for ceiling, examine left subtree
         localRoot = localRoot.getLeft(); 
      }
      if (result == null) System.out.println("No ceiling of " + target);
      else System.out.println("Ceiling of " + target + " is " + result);
   }



   // Solved recursively:
   public void ceilRecursive(int target) {
      if (_root == null) {
         System.out.println("No ceiling of " + target);
         return; 
      }
      
      TreeNode result = ceilRecursive(_root, target);

      if (result == null) System.out.println("No ceiling of " + target);
      else System.out.println("Ceiling of " + target + " is " + result);
   }

   public TreeNode ceilRecursive(TreeNode localRoot, int target) {
      if (localRoot == null) return null; // no ceil in this subtree
      if (target == localRoot.getItem()) return localRoot; // found ceil
      
      if (target > localRoot.getItem()) // kill root and left subtree
         return ceilRecursive(localRoot.getRight(), target);

      TreeNode leftCeil = ceilRecursive(localRoot.getLeft(), target);
      if (leftCeil == null) return localRoot; // candidate confirmed
         return leftCeil; // left subtree contains the ceil
   }
}


class tut7q1 {

   public static void main(String[] args) {

      //int[] pre = new int[]{7, 2, 5, 6, 8, 0, 4, 3, 9, 1};
      //int[] in = new int[]{5, 8, 6, 2, 0, 7, 9, 3, 1, 4};
      int[] pre = new int[]{10, 6, 4, 8, 18, 15,21, 22};
      int[] in = new int[]{4, 6, 8, 10, 15, 18, 21, 22};
      BinaryTree tree = BinaryTree.fromPreorderInorder(pre, in);
      System.out.println(tree);

      System.out.println("From iterative solution:");
      tree.ceilIterative(100);

      System.out.println("From recursive solution:");
      tree.ceilRecursive(6);
   }
}


