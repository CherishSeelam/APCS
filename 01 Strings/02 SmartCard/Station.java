public class Station
{
   private int myZone;
   private String myName;
   public Station()
   {
      myZone = 0;
      myName = null;
   }
   public Station (String name, int zone)
   {
      myName = name;
      myZone = zone;
   }
   public int getZone()
   {
      return myZone;
   }
   public String getName()
   {
      return myName;
   }
   public String toString()
   {
      return myName + ", zone" + myZone;
   }
}