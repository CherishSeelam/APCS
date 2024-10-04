// Name:Cherish Seelam
// Date: 11/21/23

public class Widget implements Comparable<Widget>
{
   //fields
   private int myCubits;
   private int myHands;
   //constructors
   public Widget()
   {
      myCubits = 0;
      myHands = 0;
   }
   
   public Widget(int cubits, int hands)
   {
      myCubits = cubits;
      myHands = hands;
   }
   
   public Widget(Widget widget)
   {
      myCubits = widget.getCubits();
      myHands = widget.getHands();
   }  
   //accessors and modifiers
   public int getCubits()
   {
      return myCubits;
   }
   
   public int getHands()
   {
      return myHands;
   }
   
   public void setCubits(int cubits)
   {
      myCubits = cubits;
   }
   
   public void setHands(int hands)
   {
      myHands = hands;
   }
   
   //compareTo(Widget) and equals(Widget)
   public int compareTo(Widget widget)
   {
      if(myCubits > widget.myCubits)
      {
         return 1;
      } 
      if(myCubits < widget.myCubits)
      {
         return -1;
      }
      if(myHands > widget.myHands)
      {
         return 1;
      }
      if(myHands < widget.myHands)
      {
         return -1;
      }
      return 0;  
   }
   
   public boolean equals(Widget widget)
   {
      return compareTo(widget) == 0;
   }
   //toString
   public String toString()
   {
      return myCubits + " cubits " + myHands + " hands";
   }
}