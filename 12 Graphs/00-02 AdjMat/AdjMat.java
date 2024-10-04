//Name: Cherish Seelam
//Date: 5/13/24

/* Resource classes and interfaces
 * for use with Graph0 AdjMat_0_Driver,
 *              Graph1 WarshallDriver,
 *          and Graph2 FloydDriver
 */

import java.util.*;
import java.io.*;

interface AdjacencyMatrix
{
   public int[][] getGrid();
   public int[][] readGrid(String fileName);
   public boolean isNeighbor(int from, int to);
   public int countEdges();
   public List<Integer> getNeighbors(int source);
   public String showAllNeighbors();
   public String toString();  //returns the grid as a String
}

interface WithNames
{
   public void readNames(String fileName);
   public Map<String, Integer> getNamesAndNumbers();
   public String toStringNamesAndNumbers();  // each line contains number-name, ex: 0-Pendleton
   public boolean isNeighbor(String from, String to);
}
  
interface Warshall
{    
   public int countReachables();
   public boolean isReachable(String from, String to);  
   public List<String> getReachables(String from);
   public String toStringReachability(); //displays the reachability matrix with 2 spaces in front of each value
   public void allPairsReachability();   // Warshall's Algorithm. fills the reachability matrix                                  
}

interface Floyd
{
   public int getCost(int from, int to);
   public int getCost(String from, String to);
   public void allPairsWeighted();  //Floyd's Algorithm
}

/***********************  the graph  ******************/
public class AdjMat implements AdjacencyMatrix//, WithNames//, Warshall//, Floyd
{
   private int[][] grid = null;   //adjacency matrix representation
   private Map<String, Integer> namesAndNumbers = null;    // maps name to number
   private  ArrayList<String> nameList = null;  //reverses the map, index-->name
   private int[][] reachability = null; //reachability matrix for Warshall, cost matrix for Floyd
 
 /*  write constructors, accessor methods, and instance methods   */  
   public AdjMat(String fileName)
   {
      grid = readGrid(fileName);
   }
   
   public AdjMat(String matrixFile, String fileNames)
   {
      grid = readGrid(matrixFile);
      namesAndNumbers = new TreeMap<String, Integer>();
      nameList = new ArrayList<String>();
      readNames(fileNames);
      reachability = null;
   }
      
   public int[][] getGrid()
   {
      return grid;
   }
   
   public int[][] readGrid(String fileName)
   {
      try 
      {
         Scanner infile = new Scanner(new File(fileName));
         int size = infile.nextInt();
         grid = new int[size][size];
         for (int i = 0; i < size; i++) 
         {
            for (int j = 0; j < size; j++) 
            {
               grid[i][j] = infile.nextInt();
            }
         }
         infile.close();
      } 
      catch (FileNotFoundException e) 
      {
         System.out.println("File not found.");
      }
      return grid;
   }
   
   public boolean isNeighbor(int from, int to)
   {
      if (grid[from][to] != 0) 
      {
         return true;
      } 
      else 
      {
        return false;
      }
   }
   
   public int countEdges()
   {
      int count = 0;
      for (int[] row : grid) 
      {
         for (int cell : row) 
         {
            if (cell != 0) 
            {
               count++;
            }
         }
      }
      return count;
   }
   
   public List<Integer> getNeighbors(int source)
   {
      List<Integer> neighbors = new ArrayList<>();
         for (int i = 0; i < grid[source].length; i++) 
         {
            if (grid[source][i] != 0) 
            {
               neighbors.add(i);
            }
         }
         return neighbors;
   }
   
   public String showAllNeighbors()
   {
      String ret = "";
      for(int i = 0; i < grid.length; i++) 
      {
         ret = ret + i + ": " + getNeighbors(i).toString() + "\n";
      }
   
      return ret;
   }
   
   public String toString()
   {
      String str = "";
      for (int i = 0; i < grid.length; i++)
      {
         for (int j = 0; j < grid.length; j++)
         {
            str = str + grid[i][j] + " ";
         }
         str = str + "\n";
      }
      return str;
   } 
 
   /**************  implement the WithNames interface ************/
   public void readNames(String fileName)
   {
      try 
      {
        Scanner infile = new Scanner(new File(fileName));
        int next = infile.nextInt();
        infile.nextLine();
        for (int i = 0; i < next; i++) 
        {
            String city = infile.nextLine().trim();
            namesAndNumbers.put(city, i);
            nameList.add(city);
        }
        infile.close(); 
      } 
      catch (FileNotFoundException e) 
      {
         System.out.println("File not found: " + fileName);
      }
   }
   
   public Map<String, Integer> getNamesAndNumbers()
   {
      return namesAndNumbers;
   }
   
   public String toStringNamesAndNumbers()  // each line contains number-name, ex: 0-Pendleton
   {
      String str = "";
      for(int i = 0; i < nameList.size(); i ++)
      {
         str = str + i + "-" + nameList.get(i) + "\n";
      }
      return str;
   }
   
   public boolean isNeighbor(String from, String to)
   {
      int intFrom = namesAndNumbers.get(from);
      int intTo = namesAndNumbers.get(to);  
      if (grid[intFrom][intTo] != 0) 
      {
         return true;
      } 
      else 
      {
         return false;
      }  
   }
      
   /************  implement the Warshall interface ************/
   public int countReachables()
   {
      int count = 0;
      for(int i = 0; i < reachability.length; i++)
      {
         for(int j = 0; j < reachability[i].length; j++)
         {
            if(reachability[i][j] == 1)
            {
               count++;
            }
         }
      }
      return count;   
   }
      
   public boolean isReachable(String from, String to)
   {
      int f = namesAndNumbers.get(from);
      int t = namesAndNumbers.get(to);  
      if (reachability[f][t] == 1) 
      {
         return true;
      } 
      else 
      {
         return false;
      }  
   }
    
   public List<String> getReachables(String from)
   {
      List<String> r = new ArrayList<String>();
      int start = namesAndNumbers.get(from);
      for(int i = 0; i < reachability[start].length; i++)
      {
         if(reachability[start][i] == 1)
         {
            r.add(nameList.get(i));
         }
      }
      return r;
   }
   
   public String toStringReachability() //displays the reachability matrix with 2 spaces in front of each value
   {
      String result = "";
      for(int i = 0; i < reachability.length; i++)
      {
         for(int j = 0; j < reachability[i].length; j++)
         {
            if(reachability[i][j] < 10)
            {
               result += " " + reachability[i][j] + " ";
            }
            else
            {
               result += reachability[i][j] + " ";
            }
         }
         result = result + "\n";
      }
      return result;
   }
   
   public void allPairsReachability()   // Warshall's Algorithm. fills the reachability matrix  
   { 
      reachability = new int[grid.length][grid.length];
      for(int i = 0; i < grid.length; i++)
      {
         for(int j = 0; j < grid[i].length; j++)
         {
            reachability[i][j] = grid[i][j];
         }
      }
      for(int k = 0; k < reachability.length; k++)
      {
         for(int i = 0; i < reachability.length; i++)
         {
            for(int j = 0; j < reachability.length; j++)
            {
               if(reachability[i][j] != 0 || (reachability[i][k] != 0 && reachability[k][j] != 0))
               {
                  reachability[i][j] = 1;
               }
               else
               {
                  reachability[i][j] = 0;            
               }
            }
         }
      }
   }
        
   /*************  implement the Floyd interface  *********/
   public int getCost(int from, int to)
   {
      return grid[from][to];
   }
   
   public int getCost(String from, String to)
   {
      int start = namesAndNumbers.get(from);
      int end = namesAndNumbers.get(to);
      return grid[start][end];
   }
   
   public void allPairsWeighted()
   {
      for(int k = 0; k < grid.length; k++)
      {
         for(int i = 0; i < grid.length; i++)
         {
            for(int j = 0; j < grid.length; j++)
            {
               if(grid[i][j] > (grid[i][k] + grid[k][j])) 
               {
                  grid[i][j] = grid[i][k] + grid[k][j];
               }
            }
         }
      }
   } 
}