// Name:
// Date: 

interface BST_Generic_interface<E>
{
   public int size();
   public TreeNode<E> getRoot() ;
   public boolean contains(E obj);
   public void add(E obj);         //does not balance
   public void addBalanced(E obj); //AVL
   public void remove(E obj);      //does not balance
   public E min();
   public E max();
   public String display();
   public String toString();
}

/*******************
Copy your BST code.  Implement generics.
If you skipped remove() and/or addBalanced(), just leave the method bodies empty.
**********************/
public class BST_Generic<E extends Comparable<E>> implements BST_Generic_interface<E>
{
      /*  copy your BST code  here  */
   private TreeNode<E> root;
   private int size;
   public BST_Generic()
   {
      root = null;
      size = 0;
   }
   public int size()
   {
      return size;  
   }
   public TreeNode<E> getRoot()   //accessor method
   {
      return root;
   }
   /***************************************
   @param s -- one string to be inserted
   ****************************************/
   public void add(E s) 
   {
      size++;
      root = add(root, s);
   }
   private TreeNode add(TreeNode<E> t, E s) //recursive helper method
   {      
      if (t == null) 
      {
         return new TreeNode(s);
      } 
      else if (s.compareTo(t.getValue()) <= 0) 
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
   
   public boolean contains(E obj)
   {
      return contains(root, obj);   
   }
   
   private boolean contains(TreeNode<E> t, E x) //recursive helper method
   {
      if (t == null) 
      {
         return false;
      } 
      else if (x.equals(t.getValue())) 
      {
         return true;
      } 
      else if (x.compareTo(t.getValue()) < 0) 
      {
         return contains(t.getLeft(), x);
      } 
      else 
      {
         return contains(t.getRight(), x);
      }
   }
   
   public E min()
   {
      return min(root);   
   }
   
   private E min(TreeNode<E> t)  //use iteration
   {
      if (t == null) 
      {
         return null;
      }
      while (t.getLeft() != null)
      {
         t = t.getLeft();
      }
      return t.getValue(); 
   }
   
   public E max()
   {
     return max(root);   
   }
   
   private E max(TreeNode<E> t)  //recursive helper method
   {
      if (t == null) 
      {
         return null;
      } 
      else if (t.getRight() == null) 
      {
         return t.getValue();
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
   
   private String toString(TreeNode<E> t)  //an in-order traversal.  Use recursion.
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
   public void remove(E target)
   {
      root = remove(root, target);
      size--;
   }

   private TreeNode remove(TreeNode<E> t, E target)
   {
      if (t == null)
      {
         return null;
      }
      else if (target.compareTo(t.getValue()) < 0) 
      {
         t.setLeft(remove(t.getLeft(), target)); 
      }
      else if (target.compareTo(t.getValue()) > 0) 
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
            E min = min(t.getRight()); 
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

   public void addBalanced(E value)  
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
   private TreeNode balanceTree(TreeNode<E> root)   
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
   private TreeNode addBalanced(TreeNode<E> t, E strObj)
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
      if (strObj.compareTo(t.getValue()) < 0)
      {
         t.setLeft(addBalanced(t.getLeft(), strObj));
      }
      else if (strObj.compareTo(t.getValue()) > 0)
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

/*******************
  Copy your TreeNode code.  Implement generics.
**********************/
class TreeNode<E>
{
   private E value; 
   private TreeNode<E> left, right;
   
   public TreeNode(E initValue)
   { 
      value = initValue; 
      left = null; 
      right = null; 
   }
   
   public TreeNode(E initValue, TreeNode initLeft, TreeNode initRight)
   { 
      value = initValue; 
      left = initLeft; 
      right = initRight; 
   }
   
   public E getValue()
   { 
      return value; 
   }
   
   public TreeNode<E> getLeft() 
   { 
      return left; 
   }
   
   public TreeNode<E> getRight() 
   { 
      return right; 
   }
   
   public void setValue(E theNewValue) 
   { 
      value = theNewValue; 
   }
   
   public void setLeft(TreeNode<E> theNewLeft) 
   { 
      left = theNewLeft;
   }
   
   public void setRight(TreeNode<E> theNewRight)
   { 
      right = theNewRight;
   }


}