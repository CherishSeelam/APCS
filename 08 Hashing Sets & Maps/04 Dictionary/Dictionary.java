// Name: Cherish Seelam
// Date: 3/11/24

import java.io.*;
import java.util.*;

public class Dictionary
{
   public static void main(String[] args) 
   {
      Scanner infile = null;
      PrintWriter outputFile = null;
      try
      {
         infile = new Scanner(new File("spanglish.txt"));
         outputFile = new PrintWriter(new FileWriter("dictionaryOutput.txt"));
      }
      catch(Exception e)
      {
         System.out.println( e );
      }
      
      Map<String, Set<String>> eng2spn = makeDictionary( infile );
      outputFile.println("ENGLISH TO SPANISH");
      outputFile.println(display(eng2spn));
   
      Map<String, Set<String>>spn2eng = reverse(eng2spn);
      outputFile.println("SPANISH TO ENGLISH");
      outputFile.println(display(spn2eng));
      outputFile.close();
      
      System.out.println("File created.");
   }
   
   public static Map<String, Set<String>> makeDictionary(Scanner infile)
   {
      Map<String, Set<String>> dict = new TreeMap<String, Set<String>>(); 
      while(infile.hasNext()) 
      {
         String englishWord = infile.next();
         String spanishWord = infile.next();
         add(dict, englishWord, spanishWord);
      }
      return dict;
   }
   
   public static void add(Map<String, Set<String>> dictionary, String word, String translation)
   {
       Set<String> translations;
       if (dictionary.containsKey(word)) 
       {
         translations = dictionary.get(word);
       } 
       else 
       {
         translations = new TreeSet<>();
       }
       translations.add(translation);
       dictionary.put(word, translations);
   }
   
   public static String display(Map<String, Set<String>> m)
   {
      String str = "";
      for(String s : m.keySet())
      {
         str += s + " " + m.get(s) + "\n";
      }
      return str;
   }
   
   public static Map<String, Set<String>> reverse(Map<String, Set<String>> dictionary)
   {
       Map<String, Set<String>> reversed = new TreeMap<>();
       for (Map.Entry<String, Set<String>> entry : dictionary.entrySet()) 
       {
           for (String trans : entry.getValue()) 
           {
               add(reversed, trans, entry.getKey());
           }
       }
       return reversed;
   }
}


   /********************
	FILE INPUT:
   	holiday
		fiesta
		holiday
		vacaciones
		party
		fiesta
		celebration
		fiesta
     <etc.>
  *********************************** 
	FILE OUTPUT:
		ENGLISH TO SPANISH
			banana [banana]
			celebration [fiesta]
			computer [computadora, ordenador]
			double [doblar, doble, duplicar]
			father [padre]
			feast [fiesta]
			good [bueno]
			hand [mano]
			hello [hola]
			holiday [fiesta, vacaciones]
			party [fiesta]
			plaza [plaza]
			priest [padre]
			program [programa, programar]
			sleep [dormir]
			son [hijo]
			sun [sol]
			vacation [vacaciones]

		SPANISH TO ENGLISH
			banana [banana]
			bueno [good]
			computadora [computer]
			doblar [double]
			doble [double]
			dormir [sleep]
			duplicar [double]
			fiesta [celebration, feast, holiday, party]
			hijo [son]
			hola [hello]
			mano [hand]
			ordenador [computer]
			padre [father, priest]
			plaza [plaza]
			programa [program]
			programar [program]
			sol [sun]
			vacaciones [holiday, vacation]

**********************/