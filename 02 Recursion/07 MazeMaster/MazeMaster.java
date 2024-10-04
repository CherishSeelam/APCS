// Name: Cherish Seelam
// Date: 10/19/23
 
import java.util.*;
import java.io.*;
 
public class MazeMaster
{
   public static void main(String[] args)
   {
      Scanner sc = new Scanner(System.in);
      System.out.print("Enter the maze's filename (no .txt): ");
      Maze m = new Maze(sc.next()+".txt");   //append the .txt here
      // Maze m = new Maze();    //extension
      m.display();      
      System.out.println("Options: ");
      System.out.println("1: Mark all dots.");
      System.out.println("2: Mark all dots and display the number of recursive calls.");
      System.out.println("3: Mark only the correct path.");
      System.out.println("4: Mark only the correct path. If no path exists, return false.");
      System.out.println("5: Mark only the correct path and display the number of steps.\n\tIf no path exists, return false.");
      System.out.println("6: Mark only the correct path and list the steps.\n\tIf no path exists, return false.");
      System.out.print("Please make a selection: ");
      m.solve(sc.nextInt());
      m.display();      //display solved maze
   } 
}
 
class Maze
{
   //constants
   private final char WALL = 'W';
   private final char DOT = '.';
   private final char START = 'S';
   private final char EXIT = 'E';
   private final char TEMP = 'o';
   private final char PATH = '*';
   //instance fields
   private char[][] maze;
   private int startRow, startCol;
  
   //constructors
	
	/* 
	 * EXTENSION 
	 * This is a no-arg constructor that generates a random maze
    * Do not comment it out.  Do not delete it.
	 */
   public Maze()
   {
   
   }
	
	/** 
	 * Constructor.
    * Creates a "deep copy" of the array.
    * The autograder uses this constructor.
	 */
   public Maze(char[][] m)  
   {
      maze = m;
      for(int r = 0; r < maze.length; r++)
      {
         for(int c = 0; c < maze[0].length; c++)
         { 
            if(maze[r][c] == START)    //location of start location
            {
               startRow = r;
               startCol = c;
            }
         }
      }
   } 
	
 	/* 
	 * Write this one-arg constructor.
    * the filename already has ".txt"
    * Use a try-catch block.
	 * Use next(), not nextLine() 
    * Search the maze and save the location of 'S' 
	 */
   public Maze(String filename)    
   {
      Scanner infile = null;
      try
      {
         infile = new Scanner(new File(filename));
      }
      catch (Exception e)
      {
         System.out.println("File not found");
      }
      /* enter your code here */
      int row = infile.nextInt();
      int column = infile.nextInt();
      this.maze = new char[row][column];
      infile.nextLine();
      for (int i = 0 ; i < row; i++)
      {
         String s = infile.next();
         for (int j = 0; j < column; j++)
         {
            maze[i][j] = s.charAt(j);
            if (maze[i][j] == START)
            {
               startRow = i;
               startCol = j;
            }
         }
         
      }
   }
   
   public char[][] getMaze()
   {
      return maze;
   }
   
   public void display()
   {
      if(maze==null) 
         return;
      for(int a = 0; a<maze.length; a++)
      {
         for(int b = 0; b<maze[0].length; b++)
         {
            System.out.print(maze[a][b]);
         }
         System.out.println();
      }
      System.out.println();
   }
   
   public void solve(int n)
   {
      switch(n)
      {
         case 1:
            markAll(startRow, startCol);
            break;
         case 2:
            int count = markAllAndCountRecursions(startRow, startCol);
            System.out.println("Number of recursions = " + count);
            break;
         case 3:
            markTheCorrectPath(startRow, startCol);
            break;
         case 4: 
            if( markTheCorrectPath(startRow, startCol) )
               System.out.println("Path found!"); 
            else           //use mazeNoPath
               System.out.println("No path exists."); 
            break;
         case 5:
            if( markCorrectPathAndCountSteps(startRow, startCol, 0) )
               System.out.println("Path found!"); 
            else           //use mazeNoPath
               System.out.println("No path exists."); 
            break;
         case 6: 
            if( markCorrectPathAndListSteps(startRow, startCol, "") )
               System.out.println("Path found!"); 
            else        //use mazeNoPath 
               System.out.println("No path exists.");
            break;
         default:
            System.out.println("File not found");  
      }
   }
   
	/* 
	 * From handout, #1.
	 * Fill the maze, mark every step.
	 * This is a lot like AreaFill.
	 */ 
   public void markAll(int r, int c)
   {
      if (r >= 0 && c >= 0 && r < maze.length && c < maze[0].length)
      {
         if (maze[r][c] == DOT)
         {
            maze[r][c] = PATH;
            markAll(r - 1, c);
            markAll(r + 1, c);
            markAll(r, c - 1);
            markAll(r, c + 1);
         
         }
         else if (maze[r][c] == START)
         {
            markAll(r - 1, c);
            markAll(r + 1, c);
            markAll(r, c - 1);
            markAll(r, c + 1);   
         }
      }
      
   }
 
	/* 
	 * From handout, #2.
	 * Fill the maze, mark and count every recursive call as you go.
	 * Like AreaFill's counting without a static variable.
	 */ 
   public int markAllAndCountRecursions(int r, int c)
   {
      if(r < 0 || c < 0 || r >= maze.length || c >= maze[0].length)
      {
         return 1;
      }
      else if(maze[r][c] == EXIT || maze[r][c] == WALL)
      {
         return 1;
      }
      else if(maze[r][c] == PATH)
      {   
         return 1;
      }   
      else if(maze[r][c] == START)
      {    
         return 1 + markAllAndCountRecursions(r - 1, c) + markAllAndCountRecursions(r + 1, c) + markAllAndCountRecursions(r, c - 1) + markAllAndCountRecursions(r, c + 1);
      }
      else if(maze[r][c] == DOT) 
      {
         maze[r][c] = PATH;   
         return 1 + markAllAndCountRecursions(r - 1, c) + markAllAndCountRecursions(r + 1, c) + markAllAndCountRecursions(r, c - 1) + markAllAndCountRecursions(r, c + 1);
      }   
      else      
      { 
         maze[r][c] = PATH;
         return 1 + markAllAndCountRecursions(r - 1, c) + markAllAndCountRecursions(r + 1, c) + markAllAndCountRecursions(r, c - 1) + markAllAndCountRecursions(r, c + 1);
      }
   }
 
/* 
 * From handout, #3 and #4
 * Solve the maze, OR the booleans, and mark the path through it with an asterisk
 *  Recur until you find E, then mark the path, return true.
 *  If no path is found, return false.
 */	
   public boolean markTheCorrectPath(int r, int c)
   {
      if(r < 0 || c < 0 || r >= maze.length || c >= maze[0].length)      
      {
         return false;
      }
      if (maze[r][c] == EXIT)
      {
         return true;
      }
      else if (maze[r][c] == WALL)
      {
         return false;
      }
      else if (maze[r][c] == PATH)
      {
         return false;
      }
      else
      {
         maze[r][c] = PATH;
         if (markTheCorrectPath(r + 1, c) == true || markTheCorrectPath(r - 1, c) == true|| markTheCorrectPath(r, c + 1) == true || markTheCorrectPath(r, c - 1) == true)
         {
            return true;
         }
         else
         {
            maze[r][c] = DOT;
            return false;
         }   
      }
         
      
   }
 
   /* 
	 * From handout, #5.
	 * Solve the maze, mark the path, count the steps. 	 
	 * Mark only the correct path and display the number of steps.
	 * If no path exists, return false.
	 */ 	
   public boolean markCorrectPathAndCountSteps(int r, int c, int count)
   {
      if(r < 0 || c < 0 || r >= maze.length || c >= maze[0].length)      
      {
         return false;
      }
      if (maze[r][c] == EXIT)
      {
         return true;
      }
      else if (maze[r][c] == WALL)
      {
         return false;
      }
      else if (maze[r][c] == PATH)
      {
         return false;
      }
      else
      {
         maze[r][c] = PATH;
         if (markCorrectPathAndCountSteps(r + 1, c, count + 1) == true || markCorrectPathAndCountSteps(r - 1, c, count + 1) == true|| markCorrectPathAndCountSteps(r, c + 1, count + 1) == true || markCorrectPathAndCountSteps(r, c - 1, count + 1) == true)
         {
            return true;
         }
         else
         {
            maze[r][c] = DOT;
            return false;
         }   
      }
      
   }
   
 /*   #6.	Solve the maze, mark the path, list the (r,c) steps.
    */
   public boolean markCorrectPathAndListSteps(int r, int c, String steps)
   {
      if(r < 0 || c < 0 || r >= maze.length || c >= maze[0].length)      
      {
         return false;
      }
      if (maze[r][c] == EXIT)
      {
         System.out.print(steps);
         return true;
      }
      else if (maze[r][c] == WALL)
      {
         return false;
      }
      else if (maze[r][c] == PATH)
      {
         return false;
      }
      else
      {
         maze[r][c] = PATH;
         if(markCorrectPathAndListSteps(r+1, c, "(" + (r +1) + "," + c + ") ") == true || markCorrectPathAndListSteps(r-1, c, "(" + (r-1) + "," + c + ") ") == true || markCorrectPathAndListSteps(r, c+1, "(" + r + "," + (c+1) + ") ") == true || markCorrectPathAndListSteps(r, c-1, "(" + r + "," + (c-1) + ") ") == true)
         {
            System.out.print(steps);
            return true;
         }
         else
         {
            maze[r][c]= DOT;
            return false;
         }
      }
   }
}
 
   /*****************************************
 
      ----jGRASP exec: java MazeMaster_teacher
 Enter the maze's filename (no .txt): maze1
 WWWWWWWW
 W....W.W
 WW.WW..W
 W....W.W
 W.W.WW.E
 S.W.WW.W
 WW.....W
 WWWWWWWW
 
 Options: 
 1: Mark all dots.
 2: Mark all dots and display the number of recursive calls.
 3: Mark only the correct path.
 4: Mark only the correct path. If no path exists, return false.
 5: Mark only the correct path and display the number of steps.
 	If no path exists, return false.
 6: Mark only the correct path and list the steps.
 	If no path exists, return false.
 Please make a selection: 1
 WWWWWWWW
 W****W*W
 WW*WW**W
 W****W*W
 W*W*WW*E
 S*W*WW*W
 WW*****W
 WWWWWWWW
 
 
  ----jGRASP: operation complete.
 
  ----jGRASP exec: java MazeMaster_teacher
 Enter the maze's filename (no .txt): maze1
 WWWWWWWW
 W....W.W
 WW.WW..W
 W....W.W
 W.W.WW.E
 S.W.WW.W
 WW.....W
 WWWWWWWW
 
 Options: 
 1: Mark all dots.
 2: Mark all dots and display the number of recursive calls.
 3: Mark only the correct path.
 4: Mark only the correct path. If no path exists, return false.
 5: Mark only the correct path and display the number of steps.
 	If no path exists, return false.
 6: Mark only the correct path and list the steps.
 	If no path exists, return false.
 Please make a selection: 2
 Number of recursions = 105
 WWWWWWWW
 W****W*W
 WW*WW**W
 W****W*W
 W*W*WW*E
 S*W*WW*W
 WW*****W
 WWWWWWWW
 
 
  ----jGRASP: operation complete.
 
  ----jGRASP exec: java MazeMaster_teacher
 Enter the maze's filename (no .txt): maze1
 WWWWWWWW
 W....W.W
 WW.WW..W
 W....W.W
 W.W.WW.E
 S.W.WW.W
 WW.....W
 WWWWWWWW
 
 Options: 
 1: Mark all dots.
 2: Mark all dots and display the number of recursive calls.
 3: Mark only the correct path.
 4: Mark only the correct path. If no path exists, return false.
 5: Mark only the correct path and display the number of steps.
 	If no path exists, return false.
 6: Mark only the correct path and list the steps.
 	If no path exists, return false.
 Please make a selection: 3
 Path found!
 WWWWWWWW
 W....W.W
 WW.WW..W
 W***.W.W
 W*W*WW*E
 S*W*WW*W
 WW.****W
 WWWWWWWW
 
 
  ----jGRASP: operation complete.
 
  ----jGRASP exec: java MazeMaster_teacher
 Enter the maze's filename (no .txt): mazeNoPath
 WWWWWWWW
 W....W.W
 WW.WW..E
 W..WW.WW
 W.W.W..W
 S.W.WW.W
 WWW....W
 WWWWWWWW
 
 Options: 
 1: Mark all dots.
 2: Mark all dots and display the number of recursive calls.
 3: Mark only the correct path.
 4: Mark only the correct path. If no path exists, return false.
 5: Mark only the correct path and display the number of steps.
 	If no path exists, return false.
 6: Mark only the correct path and list the steps.
 	If no path exists, return false.
 Please make a selection: 4
 No path exists.
 WWWWWWWW
 W....W.W
 WW.WW..E
 W..WW.WW
 W.W.W..W
 S.W.WW.W
 WWW....W
 WWWWWWWW
 
 
  ----jGRASP: operation complete.
 
  ----jGRASP exec: java MazeMaster_teacher
 Enter the maze's filename (no .txt): maze1
 WWWWWWWW
 W....W.W
 WW.WW..W
 W....W.W
 W.W.WW.E
 S.W.WW.W
 WW.....W
 WWWWWWWW
 
 Options: 
 1: Mark all dots.
 2: Mark all dots and display the number of recursive calls.
 3: Mark only the correct path.
 4: Mark only the correct path. If no path exists, return false.
 5: Mark only the correct path and display the number of steps.
 	If no path exists, return false.
 6: Mark only the correct path and list the steps.
 	If no path exists, return false.
 Please make a selection: 5
 Number of steps = 14
 Path found!
 WWWWWWWW
 W....W.W
 WW.WW..W
 W***.W.W
 W*W*WW*E
 S*W*WW*W
 WW.****W
 WWWWWWWW
 
 
  ----jGRASP: operation complete.
 
  ----jGRASP exec: java MazeMaster_teacher
 Enter the maze's filename (no .txt): maze1
 WWWWWWWW
 W....W.W
 WW.WW..W
 W....W.W
 W.W.WW.E
 S.W.WW.W
 WW.....W
 WWWWWWWW
 
 Options: 
 1: Mark all dots.
 2: Mark all dots and display the number of recursive calls.
 3: Mark only the correct path.
 4: Mark only the correct path. If no path exists, return false.
 5: Mark only the correct path and display the number of steps.
 	If no path exists, return false.
 6: Mark only the correct path and list the steps.
 	If no path exists, return false.
 Please make a selection: 6
 (5,0) (5,1) (4,1) (3,1) (3,2) (3,3) (4,3) (5,3) (6,3) (6,4) (6,5) (6,6) (5,6) (4,6) (4,7)
 Path found!
 WWWWWWWW
 W....W.W
 WW.WW..W
 W***.W.W
 W*W*WW*E
 S*W*WW*W
 WW.****W
 WWWWWWWW
 
 
  ----jGRASP: operation complete.
 **************************************/