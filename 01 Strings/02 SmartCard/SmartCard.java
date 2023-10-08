//name: Cherish Seelam   date: 9/12/2023

import java.text.DecimalFormat;
import java.lang.Math;

public class SmartCard 
{
   // instantiate the constants
   public final static DecimalFormat df = new DecimalFormat("$0.00");
   public final static double MIN_FARE = 0.5;
   // declare the private fields
   private double balance = 0.0;
   private Station BoardedAt = null;
   private boolean IsBoarded = false;
   private String station;
   // write the one-arg constructor
   public SmartCard (double money)
   {
      balance = money;   
   }
   // write four getter methods 
   public double getBalance()
   {
      return balance;
   }
   public String getFormattedBalance()
   {
      return df.format(balance);
   }
   public boolean getIsBoarded()
   {
      return IsBoarded;
   }
   public Station getBoardedAt()
   {
      return BoardedAt;
   }
    
   // write the instance methods as described in the handout
   public void board (Station s)
   {

     // System.out.println(BoardedAt == s);
      if (IsBoarded == true)
      {
         System.out.println("Error: already boarded?!");
      }
      else if (balance < 0.5)
      {
         System.out.println("Insufficient funds to board. Please add more money.");
      }
      else
      {
         station = s.getName();
         IsBoarded = true;
         BoardedAt = s;  
      }
   }
   public double cost (Station s)
   {
      return 0.5 + (Math.abs(s.getZone() - BoardedAt.getZone()) * 0.75);
   } 
   public void exit (Station s)
   {
      if (IsBoarded == false)
      {
         System.out.println(" Error: Did not board?!");
      }
      else if (cost(s) > balance)
      {
         System.out.println(" Insufficient funds to exit. Please add more money.");
      }
      else
      {
         balance = balance - cost(s);
         IsBoarded = false;
         System.out.println("From " + station + " to " + s.getName() + " costs $" + cost(s) + ". SmartCard has " + getFormattedBalance());
      }
      BoardedAt = null;
   }   
   public void addMoney(double d)
   {
      balance = balance + d;
   } 

} 
