//  Name: Cherish Seelam   
//  Date: 3/11/24

import java.io.*;
import java.util.*;

public class AuthorsNovels
{
   public static void main(String[] args) throws IOException
   {
      /*   test the AuthorEntry object  */
      AuthorEntry a = new AuthorEntry("Aaa");
      System.out.println("name: " + a.getName());
      System.out.println("novels: " + a.getNovels());
      System.out.println("toString(): " + a);
      AuthorEntry b = new AuthorEntry("bbb", "y");
      System.out.println("name: " + b.getName());
      b.addNovel("z");
      b.addNovel("y");
      b.addNovel("x");
      System.out.println("novels: " + b.getNovels());
      System.out.println("toString(): " + b);
      System.out.println(b.compareTo(a));   // 1
      System.out.println(a.compareTo(b));   // -1
      System.out.println("AAA".compareTo(a.getName())); // 0
      
      
      /*  start the lab  */
      Scanner keyboard = new Scanner(System.in);
      System.out.print("\nEnter input file name: ");
      String inFileName = keyboard.nextLine().trim()+".txt";
      Scanner inputFile = new Scanner(new File(inFileName));
      //System.out.print("\nEnter output file name: ");
      //String outFileName = keyboard.nextLine().trim();
      AuthorList authors = readAndMakeTheList(inputFile);
      String outFileName = "authorsNovelsOut.txt";
      PrintWriter outputFile = new PrintWriter(new FileWriter(outFileName));
      outputFile.println( authors.toString() );
      inputFile.close(); 						
      outputFile.close();
      System.out.println("Done.");
   }
   
   public static AuthorList readAndMakeTheList(Scanner inputFile)
   {
      AuthorList theList = new AuthorList();
      while(inputFile.hasNextLine())
      {
         theList.readOneLine(inputFile.nextLine());
      }
      return theList;
   }
}

class AuthorList extends ArrayList<AuthorEntry>
{
    /**   you get an ArrayList for free   **/
   public AuthorList()
   {
      super();
   }
     /** extracts the author and book from oneLine.
         calls addAuthorEntry      
      */
   public void readOneLine(String oneLine) 
   { 
      String[] parts = oneLine.split(",");
      addAuthorEntry(parts[0], parts[1]);   
   }
   
    /** use a listIterator.  Needs to call .previous() 
          either inserts a new AuthorEntry object, or 
          adds a novel to a previous AuthorEntry object,
          in alphabetic order
    */
   public void addAuthorEntry(String name, String book)
   {
      for (AuthorEntry author : this) 
      {
         if (author.getName().equals(name)) 
         {
            author.addNovel(book);
            return;
         }
      }
      this.add(new AuthorEntry(name, book));
   }
   
   public String toString()
   { 
      String result = "";
      for (AuthorEntry author : this) 
      {
         result += author.toString() + "\n";
      }
      return result;  
   }
}

class AuthorEntry implements Comparable<AuthorEntry>
{
   //fields
   private String name;
   private ArrayList<String> novels;
   
   //two constructors. argument n may be in lowercase. 
   public AuthorEntry(String n)
   {
      name = n.toUpperCase();
      novels = new ArrayList<>();
   }
   public AuthorEntry(String n, String book)
   {
      name = n.toUpperCase();
      novels = new ArrayList<>();
      novels.add(book);
   }
   
   /**  appends book to novels, but only if it is not already in that list.    
       */
   public void addNovel(String book)
   {
      if (!novels.contains(book)) 
      {
         novels.add(book);
      }
   }
   
   /** two standard accessor methods  */
   public String getName() 
   {
      return name;
   }

   public ArrayList<String> getNovels() 
   {
      return novels;
   }
        
   /**  pre:  name is not an empty string.  novels might be an empty ArrayList.
       uses:  either a for-each loop or an iterator
       post:  returns a string representation of this AuthorEntry in the format as 
              shown on each line of the output file.  
     */
   public String toString()
   {
      String result = this.name + ": ";
      for (String novel : this.novels) {
         result += novel + ", ";
      }
      // Remove the trailing comma and space
      if (result.endsWith(", ")) {
         result = result.substring(0, result.length() - 2);
      }
      return result;
   }
   @Override
   public int compareTo(AuthorEntry other) 
   {
      return this.name.compareTo(other.name);
   }
}


/***************************************
     Extension    
     use this header to implement AuthorEntry a different way
***************************************************/
// class AuthorEntryExt extends ArrayList<String> implements Comparable<AuthorEntryExt>
// {
// }


/**********************  SAMPLE RUN  ********************************
 name: AAA
 novels: []
 toString(): AAA
 name: BBB
 novels: [y, z, x]
 toString(): BBB: y, z, x
 1
 -1
 0
 
 
 Enter input file name: infile2
 Done.
 
 **********************************************************/
   /******** Output file for infile2:
   
   DOSTOEVSKI: Crime and Punishment, The Possessed, The Brothers Karamazov, The Grand Inquisitor
   FLAUBERT: Madame Bovary, A Simple Heart, Memoirs of a Madman, Sentimental Education
   STENDHAL: The Red and the Black
   TOLSTOY: Anna Karenina, War and Peace, The Death of Ivan Illyich, The Kreutzer Sonata
   
    */