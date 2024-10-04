 //Name: Cherish Seelam
 //Date: 4/9/24
 
import java.util.*;

/* implement the API for java.util.PriorityQueue
 *     a min-heap of objects in an ArrayList<E> in a resource class
 * test this class with HeapPriorityQueue_Driver.java.
 * test this class with LunchRoom.java.
 * add(E) and remove()  must work in O(log n) time
 */
public class HeapPriorityQueue<E extends Comparable<E>> 
{
   private ArrayList<E> myHeap;
   
   public HeapPriorityQueue()
   {
      myHeap = new ArrayList<E>();
      myHeap.add(null);
   }
   
   public ArrayList<E> getHeap()   //for Codepost
   {
      return myHeap;
   }
   
   public int lastIndex()
   {
      return myHeap.size() - 1;   
   }
   
   public boolean isEmpty()
   {
      return myHeap.size() == 1;   
   }
   
   public boolean add(E obj)
   {
      myHeap.add(obj);
      heapUp(lastIndex());
      return true;
   }
   
   public E remove()
   {
      if (isEmpty()) 
      {
         return null;
      }
      E min = myHeap.get(1);
      if (myHeap.size() > 2) 
      {
         E last = myHeap.remove(lastIndex());
         myHeap.set(1, last);
         heapDown(1, lastIndex());
      } 
      else 
      {
         myHeap.remove(1);
      }
      return min;
   }
   
   public E peek()
   {
      if (isEmpty()) 
      {
         return null;
      }
      return myHeap.get(1);
   }
   
   //  it's a min-heap of objects in an ArrayList<E> in a resource class
   public void heapUp(int k)
   {
      while (k > 1 && myHeap.get(k/2).compareTo(myHeap.get(k)) > 0) 
      {
         swap(k, k/2);
         k = k/2;
      }    
   }
   
   private void swap(int a, int b)
   {
      E temp = myHeap.get(a);
      myHeap.set(a, myHeap.get(b));
      myHeap.set(b, temp);
   }
   
  //  it's a min-heap of objects in an ArrayList<E> in a resource class
   public void heapDown(int k, int lastIndex)
  { 
      int i;
      while (2*k <= lastIndex) 
      {
         i = 2 * k;
         if (i < lastIndex && myHeap.get(i).compareTo(myHeap.get(i+1)) > 0) 
         {
            i++;
         }
         if (myHeap.get(k).compareTo(myHeap.get(i)) <= 0) 
         {
            return;
         }
         swap(k, i);
         k = i;
      } 
  }
   
   public String toString()
   {
      return myHeap.toString();
   }  
}
