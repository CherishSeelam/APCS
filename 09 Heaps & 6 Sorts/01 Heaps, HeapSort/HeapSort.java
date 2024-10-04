// Name: Cherish Seelam
// Date: 4/3/24               

public class HeapSort
{	
   public static int N;   //9 or 100

   public static void main(String[] args)
   {
      /* Phase 2 by itself: Given a heap, sort it. Do this part first. */
      /*
      N = 9;  
      double heap[] = {-1,99,80,85,17,30,84,2,16,1};  // size of array = N+1
      
      display(heap);
      sort(heap);
      display(heap);
      System.out.println(isSorted(heap));
      /*
      /* Phases 1 and 2:  Generate 100 random numbers, make a heap, sort it.  */
       N = 100; //4;
       double[] heap = new double[N + 1];  // size of array = N+1
       heap = createRandom(heap);
       //double[] heap = {-1.0, 7.2, 3.4, 6.4, 9.9};  //a special case because 9.9 is already at the end
       display(heap);
       makeHeap(heap, N);
       display(heap); 
       sort(heap);
       display(heap);
       System.out.println( isSorted(heap) );
   }
   
	//******* methods in Phase 2 by itself ******************************************
   public static void display(double[] array)
   {
      for(int k = 1; k < array.length; k++)
         System.out.print(array[k] + "    ");
      System.out.println("\n");	
   }
   
   public static void sort(double[] array)
   {
      /* enter your code here */
      for(int i = N/2; i > 0; i--)
      {
         heapDown(array, i, N);
      }

      for(int i = N; i > 1; i--)
      {
         swap(array, 1, i);
         heapDown(array, 1, i - 1);
      }
      if(array[1] > array[2])   //just an extra swap, if needed.
         swap(array, 1, 2);
   }
  
   public static void swap(double[] array, int a, int b)
   {
      double temp = array[a];
      array[a] = array[b];
      array[b] = temp;
   }
   
   //it's a max-heap. Parents are larger than each child.
   public static void heapDown(double[] array, int k, int lastIndex)
   {
      int i = 2 * k;
      double temp = array[i];
      while(i <= lastIndex)
      {
         if(i < N && array[i] < array[i + 1])
         {
            i++;
         }
         if(temp >= array[i])
         {
            break;
         }
         array[i/2] = array[i];
         i *= 2;
      }
      array[i/2] = temp;
   }
   
   public static boolean isSorted(double[] arr)
   {
      for(int i = 1; i < arr.length - 1; i++)
      {
         if(arr[i] > arr[i+1])
         {
            return false;
         }
      }
      return true;
   }
   
   //****** methods in Phase 1 *******************************************

  	//Generate 100 random numbers (between 1 and 100, formatted to 2 decimal places)
   //Post-condition:  array[0] == -1, the rest of the array is random
   public static double[] createRandom(double[] array)
   {  
      array[0] = -1;   //because heaps don't use index 0
      for(int i = 1; i <= N; i++)
      {
         array[i] = Math.round((Math.random() * 100) * 100.0) / 100.0;
      }
      return array;
   }
   
   //turn the random array into a heap
   //Post-condition:  array[0] == -1, the rest of the array is in heap-order
   public static void makeHeap(double[] array, int lastIndex)
   {
      for(int i = lastIndex/2; i > 0; i--)
      {
         heapDown(array, i, lastIndex);
      }
   }
}

