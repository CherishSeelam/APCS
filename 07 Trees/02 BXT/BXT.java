// Name: Cherish Seelam
// Date: 2/12/24 
/*  Represents a binary expression tree.
 *  The BXT builds itself from postorder expressions. It can
 *  evaluate and print itself.  Also prints inorder and postorder strings. 
 */
 
import java.util.*;

public class BXT
{
   public static final String operators = "+ - * / % ^ !";
   private TreeNode root;   
   
   public BXT()
   {
      root = null;
   }
   public TreeNode getRoot()   
   {
      return root;
   }
    
   public void buildTree(String str)
   {
      Stack<TreeNode> stack = new Stack<>();
      String[] tokens = str.split(" ");
      for(String token : tokens)
      {
         if(isOperator(token))
         {
            TreeNode right = stack.pop();
            TreeNode left = stack.pop();
            stack.push(new TreeNode(token, left, right));
         }
         else
         {
            stack.push(new TreeNode(token, null, null));
         }
      }
      root = stack.pop();     	
   }
   
   public double evaluateTree()
   {
      return evaluateNode(root);
   }
   
   private double evaluateNode(TreeNode t)  //recursive
   {
      if(t == null)
      {
         return 0;
      }
      if(t.getLeft() == null && t.getRight() == null)
      {
         return Double.parseDouble(t.getValue().toString());
      }
      double left = evaluateNode(t.getLeft());
      double right = evaluateNode(t.getRight()); 
      return computeTerm((String)t.getValue(), left, right);
   }
   
   private double computeTerm(String s, double a, double b)
   {
      if (s.equals("+")) 
      {
        return a + b;
      } 
      else if (s.equals("-")) 
      {
        return a - b;
      }
      else if (s.equals("*")) 
      {
        return a * b;
      } 
      else if (s.equals("/")) 
      {
        return a / b;
      } 
      else if (s.equals("%")) 
      {
        return a % b;
      } 
      else if (s.equals("^")) 
      {
        return Math.pow(a, b);
      } 
      else 
      {
        return 0;
      }
   }
   
   private boolean isOperator(String s)
   {
      return operators.contains(s);
   }
   
   public String display()
   {
      return display(root, 0);
   }
   
   private String display(TreeNode t, int level)
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
    
   public String inorderTraverse()
   {
      return inorderTraverse(root);
   }
   
   private String inorderTraverse(TreeNode t)
   {
      if(t == null)
      {
         return "";
      }
      return inorderTraverse(t.getLeft()) + " " + t.getValue() + "" + inorderTraverse(t.getRight());
   }
   
   public String preorderTraverse()
   {
      return preorderTraverse(root);
   }
   
   private String preorderTraverse(TreeNode root)
   {
      if(root == null)
      {
         return "";
      }
      return root.getValue() + " " + preorderTraverse(root.getLeft()) + "" + preorderTraverse(root.getRight());
   }
   
  /* extension */
   // public String inorderTraverseWithParentheses()
   // {
      // return inorderTraverseWithParentheses(root);
   // }
//    
   // private String inorderTraverseWithParentheses(TreeNode t)
   // {
      // return "";
   // }
}