import java.io.*;
import java.util.*;

public class Stack_driver
{
   public static void main(String[] args)
   {
      Stack stk = new Stack();
      System.out.println(stk.toString());
      System.out.println(stk.isEmpty());  
      stk.push("Alright");
      System.out.println(stk.toString());
      System.out.println(stk.isEmpty());
      stk.push("Uptown Funk");
      System.out.println(stk.toString());
      stk.push("Shallow");
      System.out.println(stk.toString());
   
      System.out.println(stk.peek());
      System.out.println(stk.toString());
      
      System.out.println(stk.pop());
      System.out.println(stk.toString());
      System.out.println(stk.pop());
      System.out.println(stk.toString());
      System.out.println(stk.pop());
      System.out.println(stk.toString());
      System.out.println(stk.isEmpty());
      
      Stack stk2 = new Stack();   //empty stack
      // System.out.println(stk2.peek());    //throws Exception
      System.out.println(stk2.pop());  //throws Exception
   }
}

/********************************************
 []
 true
 [Alright]
 false
 [Alright, Uptown Funk]
 [Alright, Uptown Funk, Shallow]
 Shallow
 [Alright, Uptown Funk, Shallow]
 Shallow
 [Alright, Uptown Funk]
 Uptown Funk
 [Alright]
 Alright
 []
 true
 java.lang.NullPointerException: Cannot invoke "ListNode.getValue()" because "this.top" is null
 	at Stack.pop(Stack.java:25)
 	at Stack_driver.main(Stack_driver.java:34)
 
 ************************************/