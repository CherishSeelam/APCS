// Name: Cherish Seelam  
// Date: 12/3/23
public class Stack 
{
    private ListNode top;

    public Stack() 
    {
        top = null;
    }

    public void push(Object item) 
    {
        ListNode newNode = new ListNode(item, top);
        top = newNode;
    }

    public Object pop() 
    {
        Object item = null;
        try 
        {
            item = top.getValue();
            top = top.getNext();
        } 
        catch (NullPointerException e) 
        {
            System.out.println("Stack is empty.");
        }
        return item;
    }

    public Object peek() 
    {
      Object item = null;
      try 
      {
         item = top.getValue();
      } 
      catch (NullPointerException e) 
      {
         System.out.println("Stack is empty.");
      }
      return item;
    }

    public boolean isEmpty() 
    {
      if (top == null) 
      {
        return true;
      } 
      else 
      {
        return false;
      }
    }

    public String toString() 
    {
      Stack temp = new Stack();      
      String str = "[";
      while (top != null) 
      {
         temp.push(this.pop());
      }
      while (!temp.isEmpty()) 
      {
         Object item = temp.pop();
         this.push(item);
         str += item;
         if (!temp.isEmpty()) 
         {
            str = str + ", ";
         }
      }
      str = str + "]";
      return str;
   }
}
