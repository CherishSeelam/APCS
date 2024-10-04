// Name: Cherish Seelam
// Date: 2/20/24

interface BSTinterface
{
   public int size();
   public TreeNode getRoot();
   public boolean contains(String obj);
   public void add(String obj);           //does not balance
   //public void addBalanced(String obj);  //AVL
   public void remove(String obj);    
   //public void removeBalanced(String obj); //extra lab
   public String min();
   public String max();
   public String display();
   public String toString();
}

/*******************
BST. Implement the remove() method.
Test it with BST_Remove_Driver.java
**********************/
public class BST implements BSTinterface
{
   /*  copy your BST code here  */
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
   
}