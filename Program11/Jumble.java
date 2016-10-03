package prog11;

import java.io.File;
import java.util.Scanner;

import prog02.UserInterface;
import prog02.GUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Jumble {
  /**
   * Sort the letters in a word.
   * @param word Input word to be sorted, like "computer".
   * @return Sorted version of word, like "cemoptru".
   */
  public static String sort (String word) {
    char[] sorted = new char[word.length()];
    for (int n = 0; n < word.length(); n++) {
      char c = word.charAt(n);
      int i = n;

      while (i > 0 && c < sorted[i-1]) {
        sorted[i] = sorted[i-1];
        i--;
      }

      sorted[i] = c;
    }

    return new String(sorted, 0, word.length());
  }

  public static void main (String[] args) {
    UserInterface ui = new GUI();
    //Map<String,String> map = new TreeMap<String,String>();
    // Map<String,String> map = new DummyList<String,String>();
     //Map<String,String> map = new SkipList<String,String>();
    //Map<String, List<String>> map = new ChainedHashTable<String, List<String>>();
    Map<String, List<String>> map = new OpenHashTable<String, List<String>>();

    Scanner in = null;
    do {
      try {
        in = new Scanner(new File(ui.getInfo("Enter word file.")));
      } catch (Exception e) {
        System.out.println(e);
        System.out.println("Try again.");
      }
    } while (in == null);
	    
    int n = 0;
    while (in.hasNextLine()) {
      String word = in.nextLine();
      if (n++ % 1000 == 0)
	      System.out.println(word + " sorted is " + sort(word));
      
      // EXERCISE: Insert an entry for word into map.
      // What is the key?  What is the value?
      
      
      
      String word1 = sort(word);
      if (!map.containsKey(word1)){
    	  List<String> list = new ArrayList<String>();
    	  list.add(word);
    	  map.put(word1, list);
      }else {
    	  List<String> list2 = map.get(word1);
    	  list2.add(word);
      }

    }

    while (true) {
      String jumble = ui.getInfo("Enter jumble:");
      if (jumble == null){
    	  break;
    	  
      }

      // EXERCISE:  Look up the jumble in the map.
      // What key do you use?
      jumble = sort(jumble);
      List<String> word = map.get(jumble);

      if (word == null)
        ui.sendMessage("No match for " + jumble);
      else
        ui.sendMessage(jumble + " unjumbled is " + word);
    }
    
    String key2 = null;
    while (true){
    	int length = -1; 
    String jumble = sort(ui.getInfo("Enter word jumble:"));
    while ( length < 0){
   	 	String lengthStr = ui.getInfo("Please enter length of the first pun word:");
   		  try {
   			  length = Integer.parseInt(lengthStr);
   		  
   	  if (length < 0)
   		  ui.sendMessage("The number is not positive.");
   		  }catch(Exception e){
   			  ui.sendMessage("It is not a number.");
   		  }
    }
   	  
   	  for ( String key1 : map.keySet()){
   		  if (length == key1.length()){
   			   key2 = check (key1, jumble);
   			   
   			   if (key2 != null && map.containsKey(key2)){
   				List<String> word = map.get(key1);
   				List<String> poop = map.get(key2);
   				ui.sendMessage(jumble + " can be unjumbled as" + word + " " + poop);
   				
   			   }
   				   
   		  }
   	  }
   		  
   	  
   	  
    }
  }
  public static String check (String key1, String clue){
	  String key2 = "";
	  int i = 0;
	  int j = 0;
	  for (j = 0; j < clue.length(); j++){
	  //while ( i < key1.length() && j < clue.length() ){
		  if(i >= key1.length()){
			  key2 += clue.charAt(j);
		  }else{
		  
		  if (key1.charAt(i) == clue.charAt(j))
			  i++;
		  else if (clue.charAt(j) < key1.charAt(i)) 
			  key2 += clue.charAt(j);
		  else
			  return null;
		  }
		 // i++;
		  //j++;
		 
	  }
	  
	  if (key2.length() != (clue.length() - key1.length()))
		  return null;
	  return key2;
  }
}

        
    

