//name: ___Cherish Seelam________________________  date: ______1/3/24________________
import java.util.*;

public class Generics_Demo
{
   public static void main(String[] args)
   {
   /*************  non-generic data structures **********************/
   
      List myList = new ArrayList();          //old style, non-generic
      myList.add("this is a string");         //put a String in the arrayList
      // String str = myList.get(0);           //error: ____________________________________________
      //Object obj = myList.get(0);
      //_________________________________________//Fix it by changing the type of the reference.
      //String str = (String)myList.get(0);
      //________________________________________ //Fix it by casting. It now acts like a String.
      //String str = (String)obj;
      //________________________________________ //another way to cast.
      //String str = String.valueOf(obj);
      //_________________________________________//Stack Overflow's way to change obj to a String
      //String str = obj.toString();
      //________________________________________ //every Object has a toString()
      //System.out.println( str );
   
      List theList = new ArrayList();         //old style, non-generic
      theList.add(3);                         //put an int in the arrayList    
      //int x = theList.get(0);          //error: _____________________________________
      //Object obj = theList.get(0);
      //__________________________________//Fix it by changing the type of the reference.
      //int x = (Integer)obj;
      //__________________________________//Fix it by casting.
      //int x = Integer.valueOf().toString();
      //_________________________________ //Stack Overflow's way to change obj to an Integer
      //int x = Integer.parseInt(obj.toString());
      //__________________________________//every Object has a toString()
     
      // int square = x * x;              // it behaves like an Integer
      // System.out.println( square );
      
      
    /*************  generic data structures  **********************/  
     
      List<String> stringList = new ArrayList<>();  // ArrayList<E>
      stringList.add("this is a string");
      String str2 = stringList.get(0);       //it "remembers" the data is a String
      String str3 = str2.substring(1,2);     //all String methods are available without casting
      System.out.println( str3 );
      
      LinkedList<Integer> ints = new LinkedList<>();  // LinkedList<E>
      ints.add(3);
      Integer y = ints.getFirst();          //it "remembers" the data is an Integer
      Integer square = y * y;               //no need to cast
      System.out.println( square );
   
   /*************  ListNode  **********************/   
   
      ListNode<Integer> s = new ListNode<>(4, null);   //uses the generic ListNode<E>, see below
      ListNode<Integer> t = new ListNode<>(5, s);
      Integer num =(Integer) t.getNext().getValue();            //what type does getNext() return?
      Integer sq = num * num;                    
      System.out.println( sq );
   }

   static class ListNode<E>     //write the generic ListNode<E> class
   {
   /*  two private fields  */
      private E value;   
      private ListNode<E> next;
      
   /* one two-arg constructor  */
      
      public ListNode(E v, ListNode<E> n)
      {
         value=v;
         next=n;
      }
            
            
         
      /*  2 accessor methods   */       
         
       public Object getValue()
      {
         return value;
      }
      public ListNode getNext()
      {
         return next;
      }  
            
         
         
         
        
         
      /*  2 modifier methods  */         
       
      public void setValue(E newv)
      {
         value=newv;
      }
      public void setNext(ListNode<E> newn)
      {
         next=newn;
      }
      
      
   
   
   
    
   }
}
   
   
 /***************************
   ----jGRASP exec: java Generics_Demo
 h
 9
 16
 
 ***************************/