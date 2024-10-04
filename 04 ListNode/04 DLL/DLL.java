// Name: Cherish Seelam
// Date: 12/12/2023

//  DoubleLinkedList, circular, with a dummy head node
//  implements some of the List and LinkedList interfaces: 
//	 	  size(), add(i, o), remove(i);  addFirst(o), addLast(o); 
//  This class also overrides toString().
//  the list is zero-indexed.
//  Uses DLNode.

class DLL  
{
   private int size;
   private DLNode head; //points to a dummy node--very useful--don't mess with it
   
   public DLL()  
   {
      head = new DLNode(null, head, head);
      size = 0;
   } 
     
   /* two accessor methods  */
   public int size()
   {
      return size;
   }
   public DLNode getHead()
   {
      return head;
   }
   
   /* appends obj to end of list; increases size;
   	  @return true  */
   public boolean add(Object obj)
   {
      addLast(obj);
      return true;   
   }
   
   /* inserts obj at position index (the list is zero-indexed).  
      increments size. 
      no need for a special case when size == 0.
	   */
   public void add(int index, Object obj) throws IndexOutOfBoundsException  //this the way the real LinkedList is coded
   {
      if( index > size || index < 0 )
         throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
      /* enter your code below  */
      
      DLNode toStore = new DLNode(obj, null, null);
      if(head.getNext() == head || size == 0) 
      { 
			head.setNext(toStore);
			head.setPrev(toStore);
			toStore.setNext(head);
			toStore.setPrev(head);
			size = 1;
			return;
      }
      DLNode dl = head.getNext(); 
		for(int i = 0; i < index; i++) 
      {
			dl = dl.getNext();
		}
		size++;
		toStore.setNext(dl);         
		toStore.setPrev(dl.getPrev());
		dl.getPrev().setNext(toStore);
		dl.setPrev(toStore);                            
   }
  
    /* return obj at position index (zero-indexed). 
    */
   public Object get(int index) throws IndexOutOfBoundsException
   { 
      if(index >= size || index < 0)
         throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
      /* enter your code below  */
      if(index == 0) 
      {
			return getFirst();
		}
      DLNode curr = head.getNext(); 
      for(int i = 0; i < index; i++)
      { 
         curr = curr.getNext(); 
      }
      return curr.getValue(); 
   }
   
   /* replaces obj at position index (zero-indexed). 
        returns the obj that was replaced.
        */
   public Object set(int index, Object obj) throws IndexOutOfBoundsException
   {
      if(index >= size || index < 0)
         throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
      /* enter your code below  */
      DLNode curr = head.getNext(); 
      for(int i = 0; i < index; i++)
      {
         curr = curr.getNext();
      }
      Object store = curr.getValue(); 
      curr.setValue(obj); 
      return store;
   }
   
   /*  removes the node from position index (zero-indexed).  decrements size.
       @return the object in the node that was removed. 
        */
   public Object remove(int index) throws IndexOutOfBoundsException
   {
      if(index >= size || index < 0)
         throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
      /* enter your code below  */
      DLNode curr = head.getNext(); 
      for(int i = 0; i < index; i++)
      { 
         curr = curr.getNext(); 
      }
      curr.getPrev().setNext(curr.getNext());
      curr.getNext().setPrev(curr.getPrev());
      size--;
      return curr.getValue(); 
   }
  	/* inserts obj to front of list, increases size.
	    */ 
   public void addFirst(Object obj)
   {
      add(0, obj);
   }
   
   /* appends obj to end of list, increases size.
       */
   public void addLast(Object obj)
   {
      add(size, obj);
   }
   
   /* returns the first element in this list  
      */
   public Object getFirst()
   {
      if(head.getNext() == head || size == 0)
      {
			return null;
      }
      return head.getNext().getValue();
   }
   
   /* returns the last element in this list  
     */
   public Object getLast()
   {
      return get(size - 1);
   }
   
   /* returns and removes the first element in this list, or
      returns null if the list is empty  
      */
   public Object removeFirst()
   {
      if(size == 0) //check if the list is empty
      {   
         return null; //return null
      }
      return remove(0);
   }
   
   /* returns and removes the last element in this list, or
      returns null if the list is empty  
      */
   public Object removeLast()
   {
      if(size == 0) //check if the list is empty
      {   
         return null; //return null
      }
      return remove(size-1);
   }
   
   /*  returns a String with the values in the list in a 
       friendly format, for example   [Apple, Banana, Cucumber]
       The values are enclosed in [], separated by one comma and one space.
       An empty list returns [].
    */
   public String toString()
   {
      String s = "[";
		DLNode p = head.getNext();
		for(int i = 0; i < size; i++) 
      {
			s += p.getValue();
			p = p.getNext();
			if(i != size-1)
				s += ", ";
		}
		s += "]";
		return s;
   }
}