// Name: Cherish Seelam
// Date: 1/24/24
//uses PostfixEval

import java.util.*;
public class InfixPostfixEval
{
   public static final String LEFT  = "([{<";
   public static final String RIGHT = ")]}>";
   public static final String operators = "+ - * / % ^ !";
   
   public static void main(String[] args)
   {
      System.out.println("Infix  \t-->\tPostfix\t\t-->\tEvaluate");
      /*build your list of Infix expressions here  */
      List<String> infixExp = new ArrayList<>();
      infixExp.add("5 - 1 - 1");
      infixExp.add("5 - 1 + 1");
      infixExp.add("12 / 6 / 2");
      infixExp.add("3 + 4 * 5");
      infixExp.add("3 * 4 + 5");
      infixExp.add("1.3 + 2.7 + -6 * 6");
      infixExp.add("( 33 + -43 ) * ( -55 + 65 )");
      infixExp.add("8 + 1 * 2 - 9 / 3");
      infixExp.add("3 * ( 4 * 5 + 6 )");
      infixExp.add("3 + ( 4 - 5 - 6 * 2 )");
      infixExp.add("2 + 7 % 3");
      infixExp.add("( 2 + 7 ) % 3"); 
        
      for( String infix : infixExp )
      {
         try{
            String pf = infixToPostfix(infix);  //get this conversion to work first
            System.out.println(infix + "\t\t\t" + pf );  
      //      System.out.println(infix + "\t\t\t" + pf + "\t\t\t" + PostfixEval_teacher.eval(pf));  //PostfixEval must work!
         }
         catch(Exception e)
         {
            System.out.println(e.toString());
         } 
      }
   }
   
   public static String infixToPostfix(String infix)
   {
      List<String> nums = new ArrayList<String>(Arrays.asList(infix.split(" ")));
            /* enter your code here  */
      Stack<String> stack = new Stack<String>();
      String postfix = "";
      for(String item : nums)
      {
         if(!operators.contains(item) && !LEFT.contains(item) && !RIGHT.contains(item))  
         {
            postfix += item + " ";
         }
         else if(LEFT.contains(item))
         {
            stack.push(item);
         }
         else if(RIGHT.contains(item))
         {
            while(!(stack.peek().equals(LEFT.substring(RIGHT.indexOf(item), RIGHT.indexOf(item)+1))))
            {
               postfix += stack.pop() + " ";
            }
            stack.pop();
         }
         else
         {
            while(!stack.isEmpty() && precedence(item) <= precedence(stack.peek()))
            {
               postfix += stack.pop() + " ";
            }
            stack.push(item);
         }
      }
      while(!stack.isEmpty())
      {
         postfix += stack.pop() + " ";
      }
      return postfix.trim();
   }  
   
   //enter your precedence method below
      public static int precedence(String str)
   {
      if(str.equals("+") || str.equals("-"))
      {
         return 1;
      }
      else if(str.equals("*") || str.equals("/") || str.equals("%"))
      {
         return 2;
      }
      else if(str.equals("^"))
      {
         return 3;
      }
      else if(str.equals("!"))
      {
         return 4;
      }
      else
      {
         return -1;
      }
   }
   
}


/********************************************

Infix  	-->	Postfix		-->	Evaluate
 5 - 1 - 1			5 1 - 1 -			3.0
 5 - 1 + 1			5 1 - 1 +			5.0
 12 / 6 / 2			12 6 / 2 /			1.0
 3 + 4 * 5			3 4 5 * +			23.0
 3 * 4 + 5			3 4 * 5 +			17.0
 1.3 + 2.7 + -6 * 6			1.3 2.7 + -6 6 * +			-32.0
 ( 33 + -43 ) * ( -55 + 65 )			33 -43 + -55 65 + *			-100.0
 8 + 1 * 2 - 9 / 3			8 1 2 * + 9 3 / -			7.0
 3 * ( 4 * 5 + 6 )			3 4 5 * 6 + *			78.0
 3 + ( 4 - 5 - 6 * 2 )			3 4 5 - 6 2 * - +			-10.0
 2 + 7 % 3			2 7 3 % +			3.0
 ( 2 + 7 ) % 3			2 7 + 3 %			0.0
      
***********************************************/
