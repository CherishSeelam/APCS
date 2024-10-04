// Name: 
// Date: 

interface BSTinterface
{
   public int size();
   public TreeNode getRoot() ;
   public boolean contains(String obj);
   public void add(String obj);          //does not balance
   public void addBalanced(String obj);  //AVL
   public void remove(String obj);       //does not re-balance
   //public void removeBalanced(String obj); //extension
   public String min();
   public String max();
   public String display();
   public String toString();
}

public class BST implements BSTinterface
{
   /*  copy your BST code  here  */
   private TreeNode root;
   private int size;
   public BST()
   {
      root = null;
      size = 0;
   }
   public int size()
   {
      return size;  
   }
   public TreeNode getRoot()   //accessor method
   {
      return root;
   }
   /***************************************
   @param s -- one string to be inserted
   ****************************************/
   public void add(String s) 
   {
      size++;
      root = add(root, s);
   }
   private TreeNode add(TreeNode t, String s) //recursive helper method
   {      
      if (t == null) 
      {
         return new TreeNode(s);
      } 
      else if (s.compareTo((String)t.getValue()) <= 0) 
      {
         t.setLeft(add(t.getLeft(), s));
      } 
      else 
      {
         t.setRight(add(t.getRight(), s));
      }
      return t;
   }
     /*************************
      Copy the display() method from TreeLab. 
      **********************/
   public String display()
   {
      return display(root, 0);
   }
   private String display(TreeNode t, int level) //recursive helper method
   {
      String toRet = "";
      if(t == null)
         return "";
      toRet += display(t.getRight(), level + 1); //recurse right
      for(int k = 0; k < level; k++)
         toRet += "\t";
      toRet += t.getValue() + "\n";
      toRet += display(t.getLeft(), level + 1); //recurse left
      return toRet;
   }
   
   public boolean contains( String obj)
   {
      return contains(root, obj);   
   }
   
   private boolean contains(TreeNode t, String x) //recursive helper method
   {
      if (t == null) 
      {
         return false;
      } 
      else if (x.equals(t.getValue())) 
      {
         return true;
      } 
      else if (x.compareTo((String)t.getValue()) < 0) 
      {
         return contains(t.getLeft(), x);
      } 
      else 
      {
         return contains(t.getRight(), x);
      }
   }
   
   public String min()
   {
      return min(root);   
   }
   
   private String min(TreeNode t)  //use iteration
   {
      if (t == null) 
      {
         return null;
      }
      while (t.getLeft() != null)
      {
         t = t.getLeft();
      }
      return (String)t.getValue(); 
   }
   
   public String max()
   {
     return max(root);   
   }
   
   private String max(TreeNode t)  //recursive helper method
   {
      if (t == null) 
      {
         return null;
      } 
      else if (t.getRight() == null) 
      {
         return (String)t.getValue();
      } 
      else 
      {
         return max(t.getRight());
      }  
   }
   
   public String toString()
   {
      return toString(root);   
   }
   
   private String toString(TreeNode t)  //an in-order traversal.  Use recursion.
   {
      if (t == null) 
      {
         return "";
      } 
      else 
      {
         return toString(t.getLeft()) + "" + t.getValue() + " " + toString(t.getRight());
      }
   }

   /*  precondition:  target must be in the tree.
                      implies that tree cannot be null.
   */
   public void remove(String target)
   {
      root = remove(root, target);
      size--;
   }

   private TreeNode remove(TreeNode t, String target)
   {
      if (t == null)
      {
         return null;
      }
      else if (target.compareTo((String)t.getValue()) < 0) 
      {
         t.setLeft(remove(t.getLeft(), target)); 
      }
      else if (target.compareTo((String)t.getValue()) > 0) 
      {
         t.setRight(remove(t.getRight(), target)); 
      }
      else
      {
         if (t.getLeft() == null && t.getRight() == null) 
         {
            return null; 
         }
         else if (t.getLeft() == null) 
         {
            return t.getRight();
         }
         else if (t.getRight() == null) 
         {
            return t.getLeft(); 
         }
         else 
         {
            String min = min(t.getRight()); 
            t.setValue(min); 
            t.setRight(remove(t.getRight(), min)); 
         }
      }
      return t; 
   }

   /*  start the addBalanced methods */
   private int calcBalance(TreeNode current) //height to right minus 
   {                                         //height to left
      if (current == null) 
      {
         return 0;
      }
      return height(current.getRight()) - height(current.getLeft());
   }

   private int height(TreeNode current)   //from TreeLab
   {
      if(current == null) // base case: empty tree has height -1
      {
         return -1;
      }
      return 1 + Math.max(height(current.getLeft()), height(current.getRight()));
   }

   public void addBalanced(String value)  
   { 
   /*
      // first approach
      add(value);
      root = balanceTree( root );
   */
      
   /*
      second approach */
      root = addBalanced(root, value);
   }
   
   // first approach
   private TreeNode balanceTree( TreeNode root )   
   {
      int balance = calcBalance(root);
      if (balance > 1) 
      {
         if (calcBalance(root.getRight()) < 0) 
         {
            root.setRight(rotateRight(root.getRight()));
         }
         root = rotateLeft(root);
      } 
      else if (balance < -1) 
      {
         if (calcBalance(root.getLeft()) > 0) 
         {
            root.setLeft(rotateLeft(root.getLeft()));
         }
         root = rotateRight(root);
      }
      return root; 
   }
   
   //helper method for second approach
   private TreeNode addBalanced(TreeNode t, String strObj)
   {
      /*
      if (t == null)
      {
         return new TreeNode(strObj);
      }
      if (strObj.compareTo((String) t.getValue()) > 0)
      {
         t.setRight(addBalanced(t.getRight(), strObj));
         if (height(t.getLeft()) < height(t.getRight()))
         {
            if(height(t.getRight().getLeft()) > height(t.getRight().getRight()))
            {
               t = doubleLeft(t);
            }
            else
            {
               t = rotateLeft(t);
            }
         }
      }
      else
      {
         t.setLeft(addBalanced((String)t.getLeft()));
         if(height(t.getLeft()) > height(t.getRight()))
         {
            if(height(t.getLeft().getRight()) > height(t.getLeft().getLeft()))
            {
               t = doubleRight(t);
            }
            else
            {
               t = rotateRight(t);
            }
         }
      }
      return t;
      */
      if (t == null)
      {
         return new TreeNode(strObj);
      }
      if (strObj.compareTo((String) t.getValue()) < 0)
      {
         t.setLeft(addBalanced(t.getLeft(), strObj));
      }
      else if (strObj.compareTo((String) t.getValue()) > 0)
      {
         t.setRight(addBalanced(t.getRight(), strObj));
      }
      return balanceTree(t);
   }
   
   /*  write the four rotation methods   */
   private TreeNode rotateRight(TreeNode t) 
   {
      TreeNode x = t.getLeft();
      TreeNode t2 = x.getRight();
      x.setRight(t);
      t.setLeft(t2);
      return x;
   }
   
   private TreeNode rotateLeft(TreeNode t) 
   {
      TreeNode y = t.getRight();
      TreeNode t2 = y.getLeft();
      y.setLeft(t);
      t.setRight(t2);
      return y;
   }
   
   private TreeNode doubleLeft(TreeNode y) 
   {
      y.setRight(rotateRight(y.getRight()));
      return rotateLeft(y);
   }
   
   private TreeNode doubleRight(TreeNode x) 
   {
      x.setLeft(rotateLeft(x.getLeft()));
      return rotateRight(x);
   }     
}