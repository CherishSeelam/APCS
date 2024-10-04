//name: Cherish Seelam  
//date: 1/28/24
import java.util.*;
import java.io.*;
public class CustomersInQueue 
{
    public static final double CHANCE_OF_CUSTOMER = 0.8;

    public static void outfileServiceAreasAndQueue(PrintWriter outfile, int min, Customer[] atServiceWindow, Queue<Customer> queue) 
    {
        outfile.print(min + ": ");
        for (Customer c : atServiceWindow)
            if (c != null)
                outfile.print(c.toString() + " ");
        outfile.println("" + queue);
    }

    public static double calculateAverage(int totalMinutes, int customers) 
    {
        return (int) (1.0 * totalMinutes / customers * 10) / 10.0;
    }

    public static PrintWriter setUpFile() 
    {
        PrintWriter outfile = null;
        try 
        {
            outfile = new PrintWriter(new FileWriter("customersSimulation.txt"));
        } 
        catch (IOException e) 
        {
            System.out.println("File not created");
            System.exit(0);
        }
        return outfile;
    }

    public static void main(String[] args) 
    {
        PrintWriter outfile = setUpFile();

        System.out.println("Customers in a Queue Simulation! ");
        Scanner kb = new Scanner(System.in);
        System.out.print("How many service areas? ");
        int sa = kb.nextInt();
        System.out.print("How long, in minutes, should the simulation run? ");
        int time = kb.nextInt();

        serveTheCustomers(time, sa, outfile);  //run the simulation
    }

    public static void serveTheCustomers(int time, int number_of_serviceAreas, PrintWriter outfile) 
    {
       Random rand = new Random();
       Queue<Customer> queue = new LinkedList<>();
       Customer[] serviceAreas = new Customer[number_of_serviceAreas];
       int totalWaitTime = 0;
       int totalCustomersServed = 0;
       int longestWaitTime = 0;
       int longestQueueLength = 0;
   
       int min;
       for (min = 0; min < time || !queue.isEmpty(); min++) 
       {
           if (min < time && rand.nextDouble() < CHANCE_OF_CUSTOMER) 
           {
               queue.add(new Customer(min, rand.nextInt(7) + 3));
           }
   
           for (int i = 0; i < serviceAreas.length; i++) 
           {
               if (serviceAreas[i] != null) 
               {
                   serviceAreas[i].decrementServiceTime();
                   if (serviceAreas[i].getServiceTime() == 0) 
                   {
                       int waitTime = min - serviceAreas[i].getEnterTime();
                       totalWaitTime += waitTime;
                       longestWaitTime = Math.max(longestWaitTime, waitTime);
                       totalCustomersServed++;
                       serviceAreas[i] = null;
                   }
               }
               if (serviceAreas[i] == null && !queue.isEmpty()) 
               {
                   serviceAreas[i] = queue.remove();
               }
           }
           longestQueueLength = Math.max(longestQueueLength, queue.size());
           outfileServiceAreasAndQueue(outfile, min, serviceAreas, queue);
       }
   
       boolean serviceAreasEmpty = true;
       for (Customer c : serviceAreas) 
       {
           if (c != null) 
           {
               serviceAreasEmpty = false;
               break;
           }
       }
   
       while (!serviceAreasEmpty) 
       {
           for (int i = 0; i < serviceAreas.length; i++) 
           {
               if (serviceAreas[i] != null) 
               {
                   serviceAreas[i].decrementServiceTime();
                   if (serviceAreas[i].getServiceTime() == 0) 
                   {
                       int waitTime = min - serviceAreas[i].getEnterTime();
                       totalWaitTime += waitTime;
                       longestWaitTime = Math.max(longestWaitTime, waitTime);
                       //totalCustomersServed++;
                       serviceAreas[i] = null;
                   }
               }
           }
           outfileServiceAreasAndQueue(outfile, min, serviceAreas, queue);
           min++;
           serviceAreasEmpty = true;
           for (Customer c : serviceAreas) 
           {
               if (c != null) 
               {
                   serviceAreasEmpty = false;
                   break;
               }
           }
       }
       System.out.println("Total customers served = " + totalCustomersServed);
       System.out.println("Average wait time = " + calculateAverage(totalWaitTime, totalCustomersServed));
       System.out.println("Longest wait time = " + longestWaitTime);
       System.out.println("Longest queue = " + longestQueueLength );
       outfile.close();
    }

    static class Customer 
    {
        private int enterTime;
        private int serviceTime;

        public Customer(int enterTime, int serviceTime) 
        {
            this.enterTime = enterTime;
            this.serviceTime = serviceTime;
        }

        public int getEnterTime() 
        {
            return enterTime;
        }

        public int getServiceTime() 
        {
            return serviceTime;
        }

        public void decrementServiceTime() 
        {
            serviceTime--;
        }
        
        public String toString() 
        {
            return serviceTime + "";
        }
    }
}

/******************************************************
 Customers in a Queue Simulation! 
 How many service areas? 4
 How long, in minutes, should the simulation run? 60
 Total customers served = 33
 Average wait time = 9.6
 Longest wait time = 16
 Longest queue = 11

****************************************************/