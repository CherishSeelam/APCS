 // Name: Cherish Seelam 
 // Date: 1/7/24
 // use for-each loops or iterators, not regular for-loops
import java.io.*;
import java.util.*;
public class IteratorLab
{
   public static void main(String[] args)
   {
      System.out.println("Iterator Lab\n");
      int[] rawNumbers = {-9, 4, 2, 5, -10, 6, -4, 24, 20, -28};
      for(int n : rawNumbers )
         System.out.print(n + " ");    
      ArrayList<Integer> numbers = createNumbers(rawNumbers);
      System.out.println("\nArrayList: "+ numbers);      //Implicit Iterator!
      System.out.println("Count negative numbers: " + countNeg(numbers));
      System.out.println("Average: " + average(numbers));
      replaceNeg(numbers);
      System.out.println("Replace negative numbers: " + numbers);
      deleteZero(numbers);
      System.out.println("Delete zeros: " + numbers);
      String[] rawMovies = {"High_Noon", "High_Noon", "Star_Wars", "Tron", "Mary_Poppins", 
               "Dr_No", "Dr_No", "Mary_Poppins", "High_Noon", "Tron"};
      ArrayList<String> movies = createMovies(rawMovies);
      System.out.println("Movies: " + movies);
      System.out.println("Movies: " +  removeDupes(movies));
   }
      // pre: an array of int values 
   	// post: return an ArrayList containing all the values
   public static ArrayList<Integer> createNumbers(int[] rawNumbers) 
   {
      ArrayList<Integer> arrayList = new ArrayList<>();
      for(int n : rawNumbers )
      {
         arrayList.add(n);
      }
      return arrayList;  
   }
      // pre: an array of just Strings  
   	// post: return an ArrayList containing all the Strings
   public static ArrayList<String> createMovies(String[] rawWords) 
   {
      ArrayList<String> myList = new ArrayList<String>();
      for (String str : rawWords)
         myList.add(str);
      return myList;
   }
   
   	// pre: ArrayList a is not empty and contains only Integer objects
   	// post: return the number of negative values in the ArrayList a
   public static int countNeg(ArrayList<Integer> a)
   {
      int count = 0;
      for (int i : a)
      {
         if(i < 0)
         {
            count++;
         }
      }
      return count;  
   }
   	// pre: ArrayList a is not empty and contains only Integer objects
   	// post: return the average of all values in the ArrayList a
   public static double average(ArrayList<Integer> a)
   {
      double total = 0;
      for (int i : a)
      {
         total = total + i;      
      }
      return (total/a.size());   
   }
     	// pre: ArrayList a is not empty and contains only Integer objects
   	// post: replaces all negative values with 0 
   public static void replaceNeg(ArrayList<Integer> a) 
   {
      for (int i = 0; i < a.size(); i++) 
      {
         int element = a.get(i);
         if (element < 0) 
         {
         a.set(i, 0);
         }
      }
   }

     	// pre: ArrayList a is not empty and contains only Integer objects
   	// post: deletes all zeros in the ArrayList a
   public static void deleteZero(ArrayList<Integer> a)
   {
      for (int i = 0; i < a.size(); i++) 
      {
         int element = a.get(i);
         if (element == 0) 
         {
         a.remove(i);
         }
      }   
   }
      // pre: ArrayList a is not empty and contains only String objects
      // post: return a new ArrayList without duplicate movie titles
      //       the parameter a is not modified!
      // strategy: start with an empty array and add movies if not already present
   public static ArrayList<String> removeDupes(ArrayList<String> a)
   {   
      ArrayList<String> myList2 = new ArrayList<String>();
      for (String str : a)
      {
         boolean isDuplicate = false;
         for (String s : myList2)
         {
            if (str.equals(s))
            {
               isDuplicate = true;
               break;
            }
         }
         if (!isDuplicate)
         {
            myList2.add(str);
         }
      }
   return myList2;      
   }
}
