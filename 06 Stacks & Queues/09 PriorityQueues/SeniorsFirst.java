// name: Cherish Seelam   date: 2/4/24

import java.util.*;
import java.io.*;
public class SeniorsFirst
{
   public static final int CUSTOMERS_PER_MINUTE = 2;  
              
   public static void main(String[] args)
   {     
      PrintWriter outfile = setUpFile();      
      
      System.out.println("Seniors First Simulation! ");
      Scanner kb = new Scanner(System.in);
      System.out.print("How many cashiers? ");
      int number_of_cashiers = kb.nextInt();
      System.out.print("How long, in minutes, should the simulation run? ");
      int time = kb.nextInt();
      
      waitTimes(time, number_of_cashiers, outfile);  //run the simulation
   } 
    
   public static PrintWriter setUpFile()
   {
      PrintWriter outfile = null; 
      try
      {
         outfile = new PrintWriter(new FileWriter("customerWaitTimes.txt"));
      }
      catch(IOException e)
      {
         System.out.println("File not created");
         System.exit(0);
      }
      return outfile;
   }
  
   public static void outfileCashiersAndQueues(PrintWriter outfile, int min, ArrayList<PriorityQueue<Customer>> cashier)
   { 
      outfile.println("minute " + min + ": ");
      for( Queue<Customer> q : cashier )
      {
         outfile.print("          ");
         for( Customer c : q )
            outfile.print( c.toString()+" ");
         outfile.println();
      }
   }
  
   public static double calculateAverage(int totalMinutes, int customers)
   {
      return (int)(1.0 * totalMinutes/customers * 100)/100.0;
   }
   
   public static void waitTimes(int time, int number_of_cashiers, PrintWriter outfile)
   {
      int customers = 0;
      int customersCheckedOut = 0;
      String[] classes = new String[]{"Senior", "Junior", "Sophomore", "Freshman"};
      int[] served = new int[]{0,0,0,0};
      int[] longestWait = new int[]{0,0,0,0};
      int[] totalWait = new int[]{0,0,0,0};
   
      ArrayList<PriorityQueue<Customer>> cashiers = new ArrayList<>();
      for(int i=0; i<number_of_cashiers; i++)
         cashiers.add( new PriorityQueue<Customer>() );
      
      for(int min = 0; min < time; min++)
      {
         int newCustomers = (int)(Math.random() * CUSTOMERS_PER_MINUTE) + 1;
         for(int i = 0; i < newCustomers; i++)
         {
            Customer c = new Customer((int)(Math.random() * 10) + 1);
            c.setEnterTime((int)(Math.random() * (min + 1)));
            int shortest = 0;
            int minSize = cashiers.get(0).size();
            for(int j = 1; j < number_of_cashiers; j++)
            {
               if(cashiers.get(j).size() < minSize)
               {
                  shortest = j;
                  minSize = cashiers.get(j).size();
               }
            }
            cashiers.get(shortest).add(c);
            customers++;
         }
         
         for(int i = 0; i < number_of_cashiers; i++)
         {
            if(!cashiers.get(i).isEmpty())
            {
               Customer c = cashiers.get(i).peek();
               c.decrementServiceTime();
               if(c.getServiceTime() == 0)
               {
                  c = cashiers.get(i).remove();
                  customersCheckedOut++;
                  String grade = c.getGrade();
                  served[Arrays.asList(classes).indexOf(grade)]++;
                  int waitTime = min - c.getEnterTime();
                  totalWait[Arrays.asList(classes).indexOf(grade)] += waitTime;
                  if(waitTime > longestWait[Arrays.asList(classes).indexOf(grade)])
                  {
                     longestWait[Arrays.asList(classes).indexOf(grade)] = waitTime;
                  }
               }
            }
         }         
         outfileCashiersAndQueues(outfile, min, cashiers);
      }      
      System.out.println("Customer\tTotal\tLongest\tAverage Wait");
      for(int i = 0; i < 4; i++)
      {
         System.out.println(classes[i] + "\t\t" + served[i] + "\t" + longestWait[i] + "\t" + calculateAverage(totalWait[i], served[i]));
      }
      
      outfile.close();	
   }

     /*  report the results to the screen in table form, like this:
         Customer		Total		Longest		Average Wait
         Senior			23			10			4.434782608695652
         Junior			18			40			7.666666666666667
         Sophomor			14			28			13.285714285714286
         Freshman			1			2			2.0
         */  
   
   /*  copy your Customer class and modify it for priority queues  */
   static class Customer implements Comparable<Customer>
   {
      public int enterTime;
      private int serviceTime;
      private String grade;
      public Customer(int serviceTime) 
      {
         this.enterTime = (int)(Math.random() * 60);
         this.serviceTime = serviceTime;
         String[] grades = {"Senior", "Junior", "Sophomore", "Freshman"};
         int index = (int)(Math.random() * 4);
         for(int i = 0; i < grades.length; i++)
         {
            if(grades[i].equalsIgnoreCase(grades[index]))
            {
               this.grade = grades[i];
               break;
            }
         }      
      }
    
      public int getEnterTime() 
      {
          return enterTime;
      }
    
      public int getServiceTime() 
      {
          return serviceTime;
      }
      
      public void setEnterTime(int i)
      {
         enterTime = i;
      } 
    
      public void decrementServiceTime() 
      {
          serviceTime--;
      }
      
      public String getGrade()
      {
          return grade;
      }
    
      public String toString() 
      {
          return serviceTime + "-" + grade;
      }
    
      public int compareTo(Customer other)
      {
          int gradeCompare = this.grade.compareTo(other.grade);
          if(gradeCompare == 0)
          {
             return Integer.compare(this.enterTime, other.enterTime);
          }
          else
          {
             return gradeCompare;
          }
      }
   }
}

/******************************************************
to the screen:
   Seniors First Simulation! 
   How many cashiers? 6
   How long, in minutes, should the simulation run? 120
   Customer		Total		Longest		Average Wait
   Senior			59			10			3.3
   Junior			62			27			5.12
   Sophomor			42			40			13.73
   Freshman			16			81			17.12


to the file:

minute 0: 
          3-Fr 
          1-Fr 
          
          
minute 1: 
          2-Fr 
          
          1-Ju 
          1-So 
minute 2: 
          5-Ju 2-Fr 
          2-Se
          
minute 3: 
          4-Ju 2-Fr 
          1-Se 
          1-Ju 
          4-So 
minute 4: 
          3-Ju 2-Fr 
          2-Fr 
          3-Fr 
          3-So 
minute 5: 
          2-Ju 2-Fr 
          5-Ju 2-Fr 
          1-Se 3-Fr 
          2-So 
minute 6: 
          1-Ju 2-Fr 2-Ju 
          4-Ju 2-Fr 
          3-Fr 
          1-So 3-So 
minute 7: 
          2-Ju 2-Fr 
          3-Ju 2-Fr 6-Fr 
          5-Ju 3-Fr 
          3-So 
minute 8: 
          1-Ju 2-Fr 6-So 
          2-Ju 2-Fr 6-Fr 
          4-Ju 3-Fr 
          2-So 5-So 
minute 9: 
          6-So 2-Fr 
          1-Ju 2-Fr 6-Fr 
          3-Ju 3-Fr 3-Fr 
          5-Se 5-So 2-So 
minute 10: 
          5-Ju 2-Fr 6-So 2-Fr 
          2-Fr 6-Fr 
          2-Ju 3-Fr 3-Fr 
          4-Se 5-So 2-So 
minute 11: 
          4-Ju 2-Fr 6-So 2-Fr 
          4-So 2-So 2-Fr 6-Fr 
          1-Ju 3-Fr 3-Fr 
          3-Se 5-So 2-So 
minute 12: 
          3-Ju 2-Fr 6-So 2-Fr 
          3-So 2-So 2-Fr 6-Fr 
          3-Fr 4-Fr 3-Fr 
          2-Se 4-Ju 2-So 5-So 
minute 13: 
          2-Ju 2-So 6-So 2-Fr 2-Fr 
          2-So 2-So 2-Fr 6-Fr 
          5-Ju 3-Fr 3-Fr 4-Fr 
          1-Se 4-Ju 2-So 5-So 
minute 14: 
          1-Ju 2-So 6-So 2-Fr 2-Fr 
          1-So 2-So 2-Fr 6-Fr 3-So 
          4-Ju 4-Ju 3-Fr 4-Fr 3-Fr 
          4-Ju 5-So 2-So 
minute 15: 
          6-So 2-So 2-Fr 2-Fr 
          2-So 3-So 2-Fr 6-Fr 
          3-Ju 4-Ju 3-Fr 4-Fr 3-Fr 
          1-Se 4-Ju 2-So 3-So 5-So 
minute 16: 
          5-So 2-So 2-Fr 2-Fr 2-So 
          1-So 3-So 2-Fr 6-Fr 5-So 
          2-Ju 4-Ju 3-Fr 4-Fr 3-Fr 
          4-Ju 5-So 2-So 3-So 
minute 17: 
          4-So 2-So 2-Fr 2-Fr 2-So 4-Fr 
          3-So 5-So 2-Fr 6-Fr 
          1-Ju 4-Ju 3-Fr 4-Fr 3-Fr 
          3-Ju 2-Ju 2-So 3-So 5-So 
minute 18: 
          3-So 2-So 2-Fr 2-Fr 2-So 4-Fr 
          5-Ju 3-So 2-So 6-Fr 5-So 2-Fr 
          4-Ju 3-Fr 3-Fr 4-Fr 
          2-Ju 2-Ju 2-So 3-So 5-So 
minute 19: 
          2-So 2-So 2-Fr 2-Fr 2-So 4-Fr 
          4-Ju 3-So 2-So 6-Fr 5-So 2-Fr 
          3-Ju 2-So 3-Fr 4-Fr 3-Fr 6-Fr 
          1-Ju 2-Ju 2-So 3-So 5-So 
minute 20: 
          1-So 2-So 4-So 2-Fr 2-So 4-Fr 2-Fr 
          3-Ju 3-So 2-So 6-Fr 5-So 2-Fr 
          2-Ju 2-So 3-Fr 4-Fr 3-Fr 6-Fr 
          4-Se 2-Ju 1-Ju 3-So 5-So 2-So 
minute 21: 
          2-So 2-So 4-So 2-Fr 2-Fr 4-Fr 
          2-Ju 3-So 4-Ju 6-Fr 5-So 2-Fr 2-So 
          1-Ju 2-So 3-Fr 4-Fr 3-Fr 6-Fr 4-Fr 
          3-Se 2-Ju 1-Ju 3-So 5-So 2-So 
minute 22: 
          1-So 2-So 4-So 2-Fr 2-Fr 4-Fr 5-So 
          1-Ju 3-So 4-Ju 6-Fr 5-So 2-Fr 2-So 
          2-So 3-Fr 3-Fr 4-Fr 4-Fr 6-Fr 
          2-Se 2-Ju 2-Se 3-So 5-So 2-So 1-Ju 
minute 23: 
          2-So 2-Fr 4-So 2-Fr 3-Fr 4-Fr 5-So 
          4-Ju 3-So 2-So 6-Fr 5-So 2-Fr 
          1-So 3-Fr 3-So 4-Fr 4-Fr 6-Fr 3-Fr 
          1-Se 2-Ju 2-Se 3-So 5-So 2-So 1-Ju 
minute 24: 
          4-Ju 2-So 4-So 2-Fr 3-Fr 4-Fr 5-So 2-Fr 
          3-Ju 3-So 2-So 6-Fr 5-So 2-Fr 6-Fr 
          3-So 3-Fr 3-Fr 4-Fr 4-Fr 6-Fr 
          2-Se 2-Ju 1-Ju 3-So 5-So 2-So 
minute 25: 
          3-Ju 2-So 4-So 2-Fr 3-Fr 4-Fr 5-So 2-Fr 
          2-Ju 3-So 2-So 6-Fr 5-So 2-Fr 6-Fr 
          2-So 3-Fr 4-So 4-Fr 4-Fr 6-Fr 3-Fr 
          1-Se 2-Ju 4-Se 3-So 5-So 2-So 1-Ju 
minute 26: 
          2-Ju 2-So 4-So 2-Fr 3-Fr 4-Fr 5-So 2-Fr 
          2-Se 2-Ju 2-So 3-So 5-So 2-Fr 6-Fr 6-Fr 
          1-So 5-So 4-So 3-Fr 4-Fr 6-Fr 3-Fr 4-Fr 
          4-Se 2-Ju 1-Ju 3-So 5-So 2-So 
minute 27: 
          1-Ju 2-So 4-So 2-Fr 3-Fr 4-Fr 5-So 2-Fr 
          1-Se 2-Ju 2-So 3-So 5-So 2-Fr 6-Fr 6-Fr 
          4-So 5-So 3-Fr 3-Fr 4-Fr 6-Fr 4-Fr 
          3-Se 2-Ju 6-Se 3-So 5-So 2-So 1-Ju 4-So 
minute 28: 
          2-So 2-Fr 4-So 2-Fr 3-Fr 4-Fr 5-So 2-Fr 
          2-Ju 3-So 2-So 6-Fr 5-So 2-Fr 6-Fr 
          3-So 5-So 3-Fr 4-So 4-Fr 6-Fr 4-Fr 3-Fr 
          2-Se 2-Ju 6-Se 3-So 5-So 2-So 1-Ju 4-So 
minute 29: 
          3-Ju 2-So 4-So 2-Fr 3-Fr 4-Fr 5-So 2-Fr 2-Fr 
          1-Ju 3-So 2-So 5-So 5-So 2-Fr 6-Fr 6-Fr 
          2-So 5-So 3-Fr 4-So 4-Fr 6-Fr 4-Fr 3-Fr 
          1-Se 2-Ju 6-Se 3-So 5-So 2-So 1-Ju 4-So 
minute 30: 
          2-Ju 2-So 4-So 2-Fr 3-Fr 4-Fr 5-So 2-Fr 2-Fr 
          3-So 5-So 2-So 5-So 5-Fr 2-Fr 6-Fr 6-Fr 
          2-Se 2-So 3-Fr 5-So 4-Fr 6-Fr 4-Fr 3-Fr 4-So 
          6-Se 2-Ju 1-Ju 3-So 5-So 2-So 4-So 
minute 31: 
          1-Ju 2-So 4-So 2-Fr 3-Fr 4-Fr 5-So 2-Fr 2-Fr 
          2-So 5-So 2-So 5-So 5-Fr 2-Fr 6-Fr 6-Fr 6-Fr 
          1-Se 2-So 3-Fr 5-So 4-Fr 6-Fr 4-Fr 3-Fr 4-So 
          5-Se 2-Ju 1-Ju 3-So 5-So 2-So 4-So 4-So 
minute 32: 
          2-So 2-Fr 4-So 2-Fr 3-Fr 4-Fr 5-So 2-Fr 4-Fr 
          1-So 5-So 2-So 5-So 5-Fr 2-Fr 6-Fr 6-Fr 6-Fr 
          2-So 5-So 3-Fr 4-So 4-Fr 6-Fr 4-Fr 3-Fr 
          4-Se 2-Ju 1-Ju 2-Ju 5-So 2-So 4-So 4-So 3-So 
minute 33: 
          3-Se 2-So 4-So 2-Fr 2-Fr 4-Fr 5-So 2-Fr 4-Fr 3-Fr 
          5-So 5-So 2-So 6-Fr 5-Fr 2-Fr 6-Fr 6-Fr 
          1-So 5-So 3-Fr 4-So 4-Fr 6-Fr 4-Fr 3-Fr 6-Fr 
          3-Se 2-Ju 1-Ju 2-Ju 5-So 2-So 4-So 4-So 3-So 
minute 34: 
          2-Se 2-So 4-So 2-Fr 2-Fr 4-Fr 5-So 2-Fr 4-Fr 3-Fr 
          4-So 5-So 2-So 6-Fr 5-Fr 2-Fr 6-Fr 6-Fr 2-Fr 3-Fr 
          5-So 4-So 3-Fr 3-Fr 4-Fr 6-Fr 4-Fr 6-Fr 
          2-Se 2-Ju 1-Ju 2-Ju 5-So 2-So 4-So 4-So 3-So 
minute 35: 
          1-Se 2-So 4-So 2-Fr 2-Fr 4-Fr 5-So 2-Fr 4-Fr 3-Fr 
          3-So 5-So 2-So 6-Fr 5-Fr 2-Fr 6-Fr 6-Fr 2-Fr 3-Fr 
          1-Se 3-Se 3-Fr 4-So 5-So 6-Fr 4-Fr 6-Fr 3-Fr 4-Fr 
          1-Se 2-Ju 1-Ju 2-Ju 5-So 2-So 4-So 4-So 3-So 
minute 36: 
          2-So 2-So 4-So 2-Fr 2-Fr 4-Fr 5-So 2-Fr 4-Fr 3-Fr 
          2-So 5-So 2-So 6-Fr 5-Fr 2-Fr 6-Fr 6-Fr 2-Fr 3-Fr 
          3-Se 5-So 3-Fr 4-So 4-Fr 6-Fr 4-Fr 6-Fr 3-Fr 
          6-Se 2-Ju 1-Ju 2-Ju 5-So 2-So 4-So 4-So 3-So 
minute 37: 
          1-So 2-So 4-So 2-Fr 2-Fr 4-Fr 5-So 2-Fr 4-Fr 3-Fr 
          1-So 5-So 2-So 6-Fr 5-Fr 2-Fr 6-Fr 6-Fr 2-Fr 3-Fr 
          2-Se 5-Ju 3-Fr 4-So 5-So 6-Fr 4-Fr 6-Fr 3-Fr 4-Fr 
          5-Se 2-Ju 1-Ju 2-Ju 5-So 2-So 4-So 4-So 3-So 6-Fr 
minute 38: 
          4-So 2-So 5-So 2-Fr 6-So 4-Fr 2-Fr 2-Fr 4-Fr 3-Fr 
          2-So 5-So 2-Fr 6-Fr 2-So 5-Fr 6-Fr 6-Fr 2-Fr 3-Fr 
          1-Se 5-Ju 3-Fr 4-So 5-So 6-Fr 4-Fr 6-Fr 3-Fr 4-Fr 
          4-Se 2-Ju 1-Ju 2-Ju 5-So 2-So 4-So 4-So 3-So 6-Fr 
minute 39: 
          3-Ju 4-So 5-So 2-Fr 2-So 4-Fr 2-Fr 2-Fr 4-Fr 3-Fr 6-So 
          3-Se 2-So 2-Fr 6-Fr 5-So 5-Fr 6-Fr 6-Fr 2-Fr 3-Fr 2-So 
          5-Ju 5-So 3-Fr 4-So 4-Fr 6-Fr 4-Fr 6-Fr 3-Fr 
          3-Se 2-Ju 1-Ju 2-Ju 5-So 2-So 4-So 4-So 3-So 6-Fr 
minute 40: 
          2-Ju 4-So 5-So 2-Fr 2-So 4-Fr 2-Fr 2-Fr 4-Fr 3-Fr 6-So 
          2-Se 2-So 2-Fr 6-Fr 5-So 5-Fr 6-Fr 6-Fr 2-Fr 3-Fr 2-So 
          4-Ju 5-So 3-Fr 4-So 2-So 6-Fr 4-Fr 6-Fr 3-Fr 4-Fr 3-So 
          2-Se 2-Ju 1-Ju 2-Ju 5-So 2-So 4-So 4-So 3-So 6-Fr 
minute 41: 
          1-Ju 4-So 5-So 2-Fr 2-So 3-So 2-Fr 2-Fr 4-Fr 3-Fr 6-So 4-Fr 
          1-Se 2-So 2-Fr 6-Fr 5-So 5-Fr 6-Fr 6-Fr 2-Fr 3-Fr 2-So 
          3-Ju 5-So 3-Fr 4-So 2-So 6-Fr 4-Fr 6-Fr 3-Fr 4-Fr 3-So 
          1-Se 2-Ju 1-Ju 2-Ju 4-Ju 2-So 4-So 4-So 3-So 6-Fr 5-So 
minute 42: 
          4-So 2-So 5-So 2-Fr 6-So 3-So 2-Fr 2-Fr 4-Fr 3-Fr 4-Fr 
          2-So 5-So 2-So 6-Fr 2-So 2-Fr 6-Fr 6-Fr 2-Fr 3-Fr 5-Fr 
          2-Ju 5-So 4-So 4-So 2-So 3-Fr 4-Fr 6-Fr 3-Fr 4-Fr 3-So 6-Fr 
          1-Ju 2-Ju 2-So 2-Ju 4-Ju 5-So 4-So 4-So 3-So 6-Fr 
minute 43: 
          3-So 2-So 5-So 2-Fr 6-So 3-So 2-Fr 2-Fr 4-Fr 3-Fr 4-Fr 6-Fr 
          1-So 5-So 2-So 6-Fr 2-So 2-Fr 6-Fr 6-Fr 2-Fr 3-Fr 5-Fr 
          1-Ju 5-So 4-So 4-So 2-So 3-Fr 4-Fr 6-Fr 3-Fr 4-Fr 3-So 6-Fr 
          4-Se 1-Ju 2-So 2-Ju 2-Ju 5-So 4-So 4-So 3-So 6-Fr 4-Ju 
minute 44: 
          2-So 2-So 5-So 2-Fr 6-So 3-So 2-Fr 2-Fr 4-Fr 3-Fr 4-Fr 6-Fr 
          5-Ju 5-So 1-So 6-Fr 2-So 2-So 6-Fr 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 
          5-So 4-So 4-So 3-Fr 2-So 3-Fr 4-Fr 6-Fr 6-Fr 4-Fr 3-So 
          3-Se 1-Ju 3-Se 2-Ju 2-Ju 2-So 4-So 4-So 3-So 6-Fr 4-Ju 5-So 
minute 45: 
          1-So 2-So 5-So 2-Fr 6-So 3-So 2-Fr 2-Fr 4-Fr 3-Fr 4-Fr 6-Fr 4-Fr 
          4-Ju 5-So 1-So 6-Fr 2-So 2-So 6-Fr 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 
          3-Se 4-So 5-So 3-Fr 2-So 4-So 4-Fr 6-Fr 6-Fr 4-Fr 3-So 3-Fr 
          2-Se 1-Ju 3-Se 2-Ju 2-Ju 2-So 4-So 4-So 3-So 6-Fr 4-Ju 5-So 
minute 46: 
          5-So 2-So 3-So 2-Fr 6-So 6-Fr 2-Fr 2-Fr 4-Fr 3-Fr 4-Fr 4-Fr 
          4-Se 5-So 4-Ju 6-Fr 2-So 1-So 6-Fr 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 2-So 
          2-Se 4-So 5-So 3-Fr 2-So 4-So 4-Fr 6-Fr 6-Fr 4-Fr 3-So 3-Fr 2-Fr 
          1-Se 1-Ju 3-Se 2-Ju 2-Ju 2-So 4-So 4-So 3-So 6-Fr 4-Ju 5-So 
minute 47: 
          3-Se 2-So 5-So 2-Fr 6-So 3-So 2-Fr 2-Fr 4-Fr 3-Fr 4-Fr 4-Fr 6-Fr 
          3-Se 5-So 4-Ju 6-Fr 2-So 1-So 6-Fr 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 2-So 
          1-Se 4-So 5-So 3-Fr 2-So 4-So 4-Fr 6-Fr 6-Fr 4-Fr 3-So 3-Fr 2-Fr 
          3-Se 1-Ju 2-So 2-Ju 2-Ju 5-So 4-So 4-So 3-So 6-Fr 4-Ju 6-So 
minute 48: 
          2-Se 2-So 5-So 2-Fr 6-So 3-So 5-So 2-Fr 4-Fr 3-Fr 4-Fr 4-Fr 6-Fr 2-Fr 
          2-Se 5-So 4-Ju 6-Fr 2-So 1-So 6-Fr 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 2-So 
          5-So 4-So 4-So 3-Fr 2-So 3-Fr 4-Fr 6-Fr 6-Fr 4-Fr 3-So 2-Fr 
          2-Se 1-Ju 2-So 2-Ju 2-Ju 5-So 4-So 4-So 3-So 6-Fr 4-Ju 6-So 5-Fr 
minute 49: 
          1-Se 2-So 5-So 2-Fr 6-So 3-So 5-So 2-Fr 4-Fr 3-Fr 4-Fr 4-Fr 6-Fr 2-Fr 
          1-Se 5-So 4-Ju 6-Fr 2-So 1-So 6-Fr 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 2-So 6-Fr 
          4-So 4-So 4-So 3-Fr 2-So 3-Fr 4-Fr 6-Fr 6-Fr 4-Fr 3-So 2-Fr 5-Fr 
          1-Se 1-Ju 2-So 2-Ju 2-Ju 5-So 4-So 4-So 3-So 6-Fr 4-Ju 6-So 5-Fr 
minute 50: 
          5-So 2-So 3-So 2-Fr 6-So 2-Fr 5-So 2-Fr 4-Fr 3-Fr 4-Fr 4-Fr 6-Fr 
          4-Ju 5-So 1-So 6-Fr 2-So 2-So 6-Fr 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 6-Fr 
          3-So 4-So 4-So 3-Fr 2-So 3-Fr 4-Fr 6-Fr 6-Fr 4-Fr 3-So 2-Fr 5-Fr 3-Fr 
          1-Ju 2-Ju 2-So 2-Ju 4-Ju 5-So 4-So 4-So 3-So 6-Fr 4-Fr 6-So 5-Fr 
minute 51: 
          4-So 2-So 3-So 2-Fr 6-So 2-Fr 5-So 2-Fr 4-Fr 3-Fr 4-Fr 4-Fr 6-Fr 6-Fr 
          3-Ju 5-So 1-So 6-Fr 2-So 2-So 4-So 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 6-Fr 6-Fr 
          2-So 4-So 4-So 3-Fr 2-So 3-Fr 4-Fr 6-Fr 6-Fr 4-Fr 3-So 2-Fr 5-Fr 3-Fr 
          2-Ju 2-Ju 2-So 3-So 4-Ju 5-So 4-So 4-So 5-Fr 6-Fr 4-Fr 6-So 
minute 52: 
          3-So 2-So 3-So 2-Fr 6-So 2-Fr 5-So 2-Fr 4-Fr 3-Fr 4-Fr 4-Fr 6-Fr 6-Fr 
          2-Ju 5-So 1-So 6-Fr 2-So 2-So 4-So 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 6-Fr 6-Fr 
          1-So 4-So 4-So 3-Fr 2-So 3-Fr 4-Fr 6-Fr 6-Fr 4-Fr 3-So 2-Fr 5-Fr 3-Fr 
          2-Se 2-Ju 2-Ju 3-So 4-Ju 5-So 2-So 4-So 5-Fr 6-Fr 4-Fr 6-So 3-So 4-So 
minute 53: 
          3-Se 2-So 3-So 2-Fr 6-So 2-Fr 3-So 2-Fr 4-Fr 3-Fr 4-Fr 4-Fr 6-Fr 6-Fr 5-So 
          1-Ju 5-So 4-Ju 6-Fr 2-So 2-So 1-So 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 6-Fr 6-Fr 4-So 
          4-So 2-So 4-So 3-Fr 3-So 3-Fr 4-Fr 6-Fr 6-Fr 4-Fr 3-Fr 2-Fr 5-Fr 
          1-Se 2-Ju 2-Ju 3-So 4-Ju 5-So 2-So 4-So 5-Fr 6-Fr 4-Fr 6-So 3-So 4-So 
minute 54: 
          2-Se 2-So 3-So 2-Fr 6-So 2-Fr 3-So 2-Fr 4-Fr 3-Fr 4-Fr 4-Fr 6-Fr 6-Fr 5-So 
          4-Ju 5-So 1-So 6-Fr 2-So 2-So 4-So 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 6-Fr 6-Fr 
          2-Ju 2-So 4-So 3-Fr 3-So 3-Fr 4-So 6-Fr 6-Fr 4-Fr 3-Fr 2-Fr 5-Fr 4-Fr 5-So 
          2-Ju 2-Ju 2-So 3-So 4-Ju 5-So 4-So 4-So 5-Fr 6-Fr 4-Fr 6-So 3-So 
minute 55: 
          1-Se 2-So 3-So 2-Fr 6-So 2-Fr 3-So 2-Fr 4-Fr 3-Fr 4-Fr 4-Fr 6-Fr 6-Fr 5-So 
          3-Ju 5-So 1-So 6-Fr 2-So 2-So 4-So 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 6-Fr 6-Fr 4-Fr 
          1-Ju 2-So 4-So 3-Fr 3-So 3-Fr 4-So 6-Fr 6-Fr 4-Fr 3-Fr 2-Fr 5-Fr 4-Fr 5-So 
          5-Se 2-Ju 2-Ju 3-So 4-Ju 5-So 2-So 4-So 5-Fr 6-Fr 4-Fr 6-So 3-So 4-So 
minute 56: 
          3-So 2-So 3-So 2-So 6-So 2-Fr 5-So 2-Fr 4-Fr 3-Fr 4-Fr 4-Fr 6-Fr 6-Fr 2-Fr 
          2-Ju 5-So 1-So 6-Fr 2-So 2-So 4-So 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 6-Fr 6-Fr 4-Fr 
          4-So 2-So 4-So 3-Fr 3-So 3-Fr 5-So 6-Fr 6-Fr 4-Fr 3-Fr 2-Fr 5-Fr 4-Fr 
          4-Se 2-Ju 2-Ju 3-So 4-Ju 5-So 4-Ju 4-So 5-Fr 6-Fr 4-Fr 6-So 3-So 4-So 2-So 
minute 57: 
          2-Se 3-So 3-So 2-So 6-So 2-Fr 5-So 2-So 4-Fr 3-Fr 4-Fr 4-Fr 6-Fr 6-Fr 2-Fr 2-Fr 
          1-Ju 5-So 1-So 6-Fr 2-So 2-So 4-So 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 6-Fr 6-Fr 4-Fr 
          3-So 2-So 4-So 3-Fr 3-So 3-Fr 5-So 6-Fr 6-Fr 4-Fr 3-Fr 2-Fr 5-Fr 4-Fr 5-So 
          3-Se 2-Ju 2-Ju 3-So 4-Ju 5-So 4-Ju 4-So 5-Fr 6-Fr 4-Fr 6-So 3-So 4-So 2-So 
minute 58: 
          1-Se 3-So 3-So 2-So 6-So 2-Fr 5-So 2-So 4-Fr 3-Fr 4-Fr 4-Fr 6-Fr 6-Fr 2-Fr 2-Fr 
          3-Se 1-Ju 1-So 5-So 2-So 2-So 4-So 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 6-Fr 6-Fr 4-Fr 6-Fr 
          4-Ju 3-So 4-So 2-So 3-So 3-Fr 5-So 3-Fr 6-Fr 4-Fr 3-Fr 2-Fr 5-Fr 4-Fr 5-So 6-Fr 
          2-Se 2-Ju 2-Ju 3-So 4-Ju 5-So 4-Ju 4-So 5-Fr 6-Fr 4-Fr 6-So 3-So 4-So 2-So 
minute 59: 
          2-Se 3-So 3-So 2-So 6-So 2-Fr 5-So 2-So 4-Fr 3-Fr 4-Fr 4-Fr 6-Fr 6-Fr 2-Fr 2-Fr 
          2-Se 1-Ju 1-So 5-So 2-So 2-So 4-So 6-Fr 2-Fr 3-Fr 5-Fr 2-Fr 6-Fr 6-Fr 4-Fr 6-Fr 
          3-Ju 3-So 4-So 2-So 3-So 3-Fr 5-So 3-Fr 6-Fr 4-Fr 3-Fr 2-Fr 5-Fr 4-Fr 5-So 6-Fr 
          1-Se 2-Ju 2-Ju 3-So 4-Ju 5-So 4-Ju 4-So 5-Fr 6-Fr 4-Fr 6-So 3-So 4-So 2-So 5-Fr 

****************************************************/