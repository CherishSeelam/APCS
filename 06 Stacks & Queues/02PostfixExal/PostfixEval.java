// Name: Cherish Seelam
// Date: 1/21/24

import java.util.*;

public class PostfixEval
{
   public static final String operators = "+ - * / % ^ !";
   
   public static void main(String[] args)
   {
      System.out.println("Postfix  -->  Evaluate");
      ArrayList<String> postfixExp = new ArrayList<String>();
      /*  build your list of expressions here  */
      postfixExp.add("3 4 5 * +");
      postfixExp.add("3 4 * 5 +");
      postfixExp.add("10 20 + -6 6 * +");
      postfixExp.add("3 4 + 5 6 + *"); 
      postfixExp.add("3 4 5 + * 2 - 5 /");
      postfixExp.add("8 1 2 * + 9 3 / -");   
      postfixExp.add("2 3 ^");
      postfixExp.add("20 3 %");
      postfixExp.add("21 3 %");
      postfixExp.add("22 3 %");
      postfixExp.add("23 3 %");
      postfixExp.add("5 !");
      postfixExp.add("1 1 1 1 1 + + + + !");
      for( String pf : postfixExp )
      {
         System.out.println(pf + "\t\t" + eval(pf));
      }
   }
   
   public static double eval(String pf)
   {
      List<String> postfixParts = new ArrayList<String>(Arrays.asList(pf.split(" ")));
      /*  enter your code here  */
      Stack<String> stack = new Stack<String>();
      for(String str : postfixParts)
      {
         if(!isOperator(str))
         {
            stack.push(str);
         } 
         else if(str.equals("!"))
         {
            double n1 = Double.parseDouble(stack.pop());
            stack.push("" + factorial(n1));
         }
         else
         {
            double n1 = Double.parseDouble(stack.pop());
            double n2 = Double.parseDouble(stack.pop());
            stack.push("" + eval(n1, n2, str));
         } 
      }
      return Double.parseDouble(stack.pop());
   }
   
   public static double eval(double a, double b, String op)
   {
      double x = 0;
      if(op.equals("+"))
      {
         x = b + a;
      }
      if(op.equals("-"))
      {
         x = b - a;
      }
      if(op.equals("/"))
      {
         x = b / a;
      }
      if(op.equals("*"))
      {
         x = b * a;
      }
      if(op.equals("%"))
      {
         x = b % a;
      }
      if(op.equals("^"))
      {
         x = Math.pow(b, a);
      }
      return x;
   }
   
   public static int factorial(double j)
   {
      double fact = 1;
      for (double i = j; i > 1 ; i--) 
      {
         fact = fact * i;
      }
      return (int) fact;
   }
   
   public static boolean isOperator(String op)
   {
      if(op.equals("+") || op.equals("-") || op.equals("/") || op.equals("*") || op.equals("^") || op.equals("%") || op.equals("!"))
      {
         return true;
      }
      return false;
   }
}

/**********************************************
Postfix  -->  Evaluate
 3 4 5 * +		23
 3 4 * 5 +		17
 10 20 + -6 6 * +		-6
 3 4 + 5 6 + *		77
 3 4 5 + * 2 - 5 /		5
 8 1 2 * + 9 3 / -		7
 2 3 ^		8
 20 3 %		2
 21 3 %		0
 22 3 %		1
 23 3 %		2
 5 !		120
 1 1 1 1 1 + + + + !		120
 
 
 *************************************/