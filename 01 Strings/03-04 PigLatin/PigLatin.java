// Name:   
// Date: 
import java.util.*;
import java.io.*;
public class PigLatin
{
   public static void main(String[] args) 
   {
      //part_1_using_pig();
      part_2_using_piglatenizeFile();
      
     /*   extension only    */
     
       String pigLatin = pig("What!?");
       System.out.println(pigLatin + "\t\t" + pigReverse(pigLatin));   //Yahwta!?
       pigLatin = pig("{(Hello!)}");
       System.out.println("\n" + pigLatin + "\t\t" + pigReverse(pigLatin)); //{(Yaholle!)}
       pigLatin = pig("\"McDonald???\"");
       System.out.println("\n" + pigLatin + "  " + pigReverse(pigLatin));//"YaDcmdlano???"
   }

   public static void part_1_using_pig()
   {
      Scanner sc = new Scanner(System.in); //input from the keyboard
      while(true)
      {
         System.out.print("\nWhat word? ");
         String s = sc.next();     //reads up to white space
         if(s.equals("-1"))
         {
            System.out.println("Goodbye!"); 
            System.exit(0);
         }
         String p = pig(s);
         System.out.println( p );
      }		
   }

   public static final String punct = ",./;:'\"?<>[]{}|`~!@#$%^&*()";
   public static final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
   public static final String vowels = "AEIOUaeiou";
   public static String pig(String s)
   {
      if(s.length() == 0)
      {
         return "";
      }
      
      String firstPunct = "";
      String rLastPunct = "";
      String lastPunct= "";
      //remove and store the beginning punctuation
      for (int i = 0; i < s.length(); i++)
      { 
         if (punct.contains(""+s.charAt(i)))
         {
            firstPunct = firstPunct + s.charAt(i);
         }
         else
         {
            break;
         }
      }
      
      //System.out.println(firstPunct);
     
      //remove and store the ending punctuation 
      for(int i = s.length(); i > 0; i--)
      {
         if(punct.contains(""+s.charAt(i-1)))
         {
            rLastPunct = rLastPunct + s.charAt(i-1);
         }
         else
         {
            break;
         }      
      }
      for (int i = rLastPunct.length(); i > 0; i--)
      {
         lastPunct = lastPunct + rLastPunct.charAt(i-1); 
      }
      s = s.replace(firstPunct, "");
      s = s.replace(lastPunct, ""); 
     // System.out.println(lastPunct); 
     // String firstPuncts = s.substring(0,firstLetter);
     // String lastPuncts = s.substring(lastLetter + 1);     
         
      //START HERE with the basic case:
      //     find the index of the first vowel
      //     y is a vowel if it is not the first letter
      int firstVowelIndex = -1;
      boolean vowelCheck = false;      
      for (int i = 0; i < s.length(); i++) 
      {
         char currentChar = s.charAt(i);
         if (i == 0 && (currentChar == 'Y' || currentChar == 'y')) 
         {
            firstVowelIndex = i;
            vowelCheck = true;
            break;
                
         } 
         else if (vowels.indexOf(currentChar) != -1) 
         {
            firstVowelIndex = i;
            vowelCheck= true;
            break;
         }
      }
      //if no vowel has been found
      if (vowelCheck == false)
      {
         return "**** NO VOWEL ****";
      }
      if (firstVowelIndex == 0 && (s.charAt(0) != 'y'))
      {
         return firstPunct + s + "way" + lastPunct;
      }
      if (Character.isUpperCase(s.charAt(0)))
      {
         if (s.length() <= 1 )
         {
            return firstPunct + s + "way" + lastPunct;
         }
         else if (s.contains("Qu"))
         {
            if(s.startsWith("Qu"))
            {
               return  firstPunct + s.substring(2,3).toUpperCase() + s.substring(3)+ "quay" + lastPunct;
            }
            else if (s.indexOf("Qu") >= firstVowelIndex)
            {
               return firstPunct+ s.substring(firstVowelIndex)+ s.substring(0, firstVowelIndex) + "way" + lastPunct;  
            }
            else
            {
             return firstPunct + s.substring(firstVowelIndex+1)+ s.substring(0, firstVowelIndex+1) + "ay" + lastPunct;  
            }
         }
         else
         {       
            return firstPunct + s.substring(firstVowelIndex, firstVowelIndex + 1).toUpperCase()+ s.substring(firstVowelIndex + 1) + s.substring(0, 1).toLowerCase()+ s.substring(1, firstVowelIndex) + "ay" + lastPunct;
         }  
      }
      if (s.contains("y"))
      {
         if(s.startsWith("y"))
         {
            return firstPunct + s.substring(1)+ s.substring(0,1) + "ay" + lastPunct;
         }
         else if (s.indexOf("y") < firstVowelIndex)
         {
  //          System.out.println("printed");
            return firstPunct + s.substring(s.indexOf("y")) + s.substring(0,s.indexOf("y")) + "ay" + lastPunct;
         }
         else
         {
             return firstPunct + s.substring(firstVowelIndex)+ s.substring(0, firstVowelIndex) + "ay" + lastPunct;  
         }  
      }
      else if (s.contains("qu"))
      {
         if(s.startsWith("qu"))
         {
            return firstPunct + s.substring(2)+ "quay" + lastPunct;
         }
         else if (s.indexOf("qu") >= firstVowelIndex)
         {
            return firstPunct + s.substring(firstVowelIndex)+ s.substring(0, firstVowelIndex) + "way" + lastPunct;  
         }
         else
         {
             return firstPunct + s.substring(firstVowelIndex+1)+ s.substring(0, firstVowelIndex+1) + "ay" + lastPunct;  
         }  
      }
      //is the first letter capitalized?
     

      //return the piglatinized word 

       else
       {
         return  firstPunct + s.substring(firstVowelIndex) + s.substring(0, firstVowelIndex) + "ay" + lastPunct;
       }
            

   }
   public static void part_2_using_piglatenizeFile()  
   {
      Scanner sc = new Scanner(System.in);
      System.out.print("input filename including .txt: ");
      String fileNameIn = sc.next();
      System.out.print("output filename including .txt: ");
      String fileNameOut = sc.next();
      piglatenizeFile( fileNameIn, fileNameOut );
      System.out.println("Piglatin done!");
   }

/****************************** 
*  piglatinizes each word in each line of the input file
*    precondition:  both fileNames include .txt
*    postcondition:  output a piglatinized .txt file 
******************************/
   public static void piglatenizeFile(String fileNameIn, String fileNameOut) 
   {
      Scanner infile = null;
      try
      {
         infile = new Scanner(new File(fileNameIn));  
      }
      catch(IOException e)
      {
         System.out.println("oops");
         System.exit(0);   
      }
   
      PrintWriter outfile = null;
      try
      {
         outfile = new PrintWriter(new FileWriter(fileNameOut));
      }
      catch(IOException e)
      {
         System.out.println("File not created");
         System.exit(0);
      }
   	//process each word in each line
      
      while (infile.hasNext())
      {
         String OutFinal = "";
         String line = infile.nextLine();
         String[] wordSplit = line.split(" ");
         
         for (String element : wordSplit)
         {
            OutFinal = OutFinal + pig(element) + " ";
         }
         
         boolean hasNext = infile.hasNext();
         
         if (!hasNext)
         {
            outfile.println(OutFinal.trim());
         }
         if (hasNext)
         {
            outfile.println(OutFinal.trim()); 
         }
      }      
      
   
      outfile.close();
      infile.close();
   }
   
   /** EXTENSION: Output each PigLatin word in reverse, preserving before-and-after 
       punctuation.  
   */
   public static String pigReverse(String s)
   {

      if(s.length() == 0)
         return "";
         
      String firstPunct = "";
      String rLastPunct = "";
      String lastPunct= "";
      //remove and store the beginning punctuation
      for (int i = 0; i < s.length(); i++)
      { 
         if (punct.contains(""+s.charAt(i)))
         {           
            firstPunct = firstPunct + s.charAt(i);            
         }
         else
         {
            break;
         }
      }
      
     
      //remove and store the ending punctuation 
      for(int i = s.length(); i > 0; i--)
      {
         if(punct.contains(""+s.charAt(i-1)))
         {
            rLastPunct = rLastPunct + s.charAt(i-1);
         }
         else
         {
            break;
         }      
      }
      for (int i = rLastPunct.length(); i > 0; i--)
      {
         lastPunct = lastPunct + rLastPunct.charAt(i-1); 
      }

      String q = "", h = "";
      q = s.replace(lastPunct, "");

      q = q.replace(firstPunct, ""); 


      for(int i = q.length(); i > 0; i-- )
      {
         h = h + q.charAt(i-1);
      }

      return firstPunct + h.substring(0,1).toUpperCase()+h.substring(1,h.length()-1)+ h.substring(h.length()-1).toLowerCase() + lastPunct ;  //just to compile   
   }
}
