package prog09;
import java.util.Random;

public class SortTest<E extends Comparable<E>> {
  public void test (Sorter<E> sorter, E[] array) {
	long start = System.nanoTime();
	
    E[] copy = array.clone();
    sorter.sort(copy);
    System.out.println();
    long end = System.nanoTime();
    double runTime = (double) (end - start)/1000;
    
    int i;
    if(array.length < 100){
        System.out.println(sorter);
  	  for(i=0; i < array.length; i++ ){
  		  System.out.print(array[i]);
  	  }
    }
 
    System.out.println();
    System.out.print("Took " + runTime + " ms");
  }
  
  public static void main (String [] args) {
    Integer[] array = { 3, 1, 4, 1, 5, 9, 2, 6 };

    if (args.length > 0) {
      // Print out command line argument if there is one.
      System.out.println("args[0] = " + args[0]);

      // Create a random object to call random.nextInt() on.
      Random random = new Random(0);

      // Make array.length equal to args[0] and fill it with random
      // integers:
      int i;
      Integer[] array1 = new Integer[Integer.parseInt(args[0])];
      for(i=0; i < array1.length; i++){
    	  array1[i] = random.nextInt();
    	  
    	  SortTest<Integer> tester = new SortTest<Integer>();
    	    tester.test(new InsertionSort<Integer>(), array1);   
    	    tester.test(new HeapSort<Integer>(), array1);
    	    tester.test(new QuickSort<Integer>(), array1);
    	    tester.test(new MergeSort<Integer>(), array1);
    	  
    	  
    }
    }else{
     
    
    SortTest<Integer> tester = new SortTest<Integer>();
    tester.test(new InsertionSort<Integer>(), array);   
    tester.test(new HeapSort<Integer>(), array);
    tester.test(new QuickSort<Integer>(), array);
    tester.test(new MergeSort<Integer>(), array);
    }
    
      
    
    }
}


class InsertionSort<E extends Comparable<E>>
  implements Sorter<E> {
  public void sort (E[] array) {
	 
    for (int n = 0; n < array.length; n++) {
      E data = array[n];
      int i = n;
      while(array[i].compareTo(data) > 0){
    	  array[i] = array [i-1];
    	  i--;
      }
      // while array[i-1] > data move array[i-1] to array[i] and
      // decrement i
     // array[i] = data;

    }
  }
}


class HeapSort<E extends Comparable<E>>
  implements Sorter<E> {
  
  private E[] array;
  
  private void swap (int i, int j) {
    E data = array[i];
    array[i] = array[j];
    array[j] = data;
  }
  
  public void sort (E[] array) {
    this.array = array;
    for (int i = parent(array.length - 1); i >= 0; i--)
      swapDown(i, array.length - 1);
    
    for (int n = array.length - 1; n >= 0; n--) {
      swap(0, n);
      swapDown(0, n - 1);
    }
  }
  
  public void swapDown (int root, int end) {
    // Calculate the left child of root.
	  int left = left(root);
	  while(left <= end){
		  int right = right(root);
		  int bigger; 
		  if(right <= end && array[left].compareTo(array[right]) < 0){
			  bigger = right;
		  }
		  else{
			  bigger = left;
		  }
		  if(array[root].compareTo(array[bigger]) >= 0)
			  return;
		  swap(root, bigger);
		  root = bigger;
		  left = left(root);

	  }
    
    // while the left child is still in the array
    //   calculate the right child
    //   if the right child is in the array and 
    //      it is bigger than than the left child
    //     bigger child is right child
    //   else
    //     bigger child is left child
    //   if the root is not less than the bigger child
    //     return
    //   swap the root with the bigger child
    //   update root and calculate left child
  }
 
  private int left (int i)  { return 2 * i + 1; }
  private int right (int i) { return 2 * i + 2; }
  private int parent (int i) { return (i - 1) / 2; }
 
}


class QuickSort<E extends Comparable<E>>
  implements Sorter<E> {
  
  private E[] array;
  private void swap (int i, int j) {
    E data = array[i];
    array[i] = array[j];
    array[j] = data;
  }
  
  public void sort (E[] array) {
    this.array = array;
    sort(0, array.length-1);
  }
  
  private void sort(int left, int right) {
    if (left >= right)
      return;
    
    swap(left, (left + right) / 2);
    
    E pivot = array[left];
    int a = left + 1;
    int b = right;
    while (a <= b) {
    	if(array[a].compareTo(pivot) <= 0){
    		a++;
    	}
    	else if(array[b].compareTo(pivot) > 0){
    		b--;
    	}
    	else{
    		swap(a, b);
    	}
      // Move a forward if array[a] <= pivot
      // Move b backward if array[b] > pivot
      // Otherwise swap array[a] and array[b]
    }
    
    swap(left, b);
    
    sort(left, b-1);
    sort(b+1, right);
  }
}


class MergeSort<E extends Comparable<E>>
  implements Sorter<E> {
  
  private E[] array, array2;
  
  public void sort (E[] array) {
    this.array = array;
    array2 = array.clone();
  }
  
  private void sort(int left, int right) {
	  
    if (left >= right)
      return;
    
    int middle = (left + right) / 2;
    sort(left, middle);
    sort(middle+1, right);
    
    int i = left;
    int a = left;
    int b = middle+1;
    while (a <= middle || b <= right) {
    	if(a <= middle && b <= right){
    	if(array[a].compareTo(array[b]) >= 0){
    		array2[i] = array[b];
    		b++;
    	}
    	else{
    		array2[i] = array[a];
    		a++;
    	}
    	}else{
    		if(a > middle){
    			array2[i] = array[b];
    			b++;
    		}
    		else{
    			array2[i] = array[a];
    			a++;
    		}
    	}
    	i++;
    	
      // If both a <= middle and b <= right
      // copy the smaller of array[a] or array[b] to array2[i]
      // Otherwise just copy the remaining elements to array2
    }
    
    System.arraycopy(array2, left, array, left, right - left + 1);

  }
     
}





