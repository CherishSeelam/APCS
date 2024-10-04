// name: Cherish Seelam
// date: 3/9/24

import java.io.*;
import java.util.*;

public class AuthorsNovelsMap
{
   public static void main(String[] args) throws IOException
   {
      Scanner keyboard = new Scanner(System.in);
      System.out.print("\nEnter input file name: ");
      String inFileName = keyboard.nextLine().trim()+".txt";
      Scanner inputFile = new Scanner(new File(inFileName));
      //System.out.print("\nEnter output file name: ");
      //String outFileName = keyboard.nextLine().trim();
   
      AuthorsMap authors = readAndMakeTheList(inputFile);
      PrintWriter outputFile = new PrintWriter(new FileWriter("authorsNovelsOut.txt"));
      outputFile.println( authors.toString() );
      inputFile.close(); 						
      outputFile.close();
      System.out.println("File created.");
   }
   
   public static AuthorsMap readAndMakeTheList(Scanner inputFile)
   {
      AuthorsMap theAuthors = new AuthorsMap();
      while(inputFile.hasNextLine())
      {
         theAuthors.readOneLine(inputFile.nextLine());
      }
      return theAuthors;
   }
}

class AuthorsMap extends TreeMap<String, Set<String>>
{
  /**   when you extend a class, the constructor is optional  **/

    
   /** extracts the author and book from oneLine.
       calls addAuthorOrNovel      
      */
   public void readOneLine(String oneLine) 
   {
      String[] parts = oneLine.split(",");
      addAuthorOrNovel(parts[0], parts[1]);  
   }
   
   /**  either inserts a new Author mapping, or updates a previous Author mapping
        */
   public void addAuthorOrNovel(String name, String book)
   {
      name = name.toUpperCase();
      if(!this.containsKey(name))
      {
        Set<String> books = new TreeSet<String>();
        books.add(book);
        this.put(name, books);
      }
      else
      {
        Set<String> books = this.get(name);
        books.add(book);
      }
   }
   
   public String toString()
   {
      String output = "";
      for (String author : this.keySet()) 
      {
         output += author + ":";
         Set<String> books = this.get(author);
         for (String book : books) 
         {
            output += " " + book + ",";
         }
         output = output.substring(0, output.length() - 1) + "\n";
      }
      return output;
   }
}
   

/**********************  SAMPLE RUN  ********************************
   /******** Output file for infile2:
   
   DOSTOEVSKI: Crime and Punishment, The Possessed, The Brothers Karamazov, The Grand Inquisitor
   FLAUBERT: Madame Bovary, A Simple Heart, Memoirs of a Madman, Sentimental Education
   STENDHAL: The Red and the Black
   TOLSTOY: Anna Karenina, War and Peace, The Death of Ivan Illyich, The Kreutzer Sonata
   
    **********************************/