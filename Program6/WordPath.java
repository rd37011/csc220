package prog06;

import prog02.UserInterface;


import prog02.ConsoleUI;
import prog02.GUI;
import java.io.*;
import java.util.*;

public class WordPath {
	
	
	 List<Node> nodes = new ArrayList<Node>();
		
	 
		public static void main(String[] args){
			UserInterface ui = new GUI();
			WordPath game = new WordPath(); 
			
			
			String fileName = ui.getInfo("Please enter the dictionary file name: ");
			game.loadDictionary(fileName);
			
			
			String start = "";
			String target = "";
			Node match = null;
			Node match2 = null;
			
	 		while(match == null || match2 == null){
	 			
				start = ui.getInfo("Please enter your starting word: ");
				target = ui.getInfo("Please enter your target word: ");
				
				if(start  == null || target == null ){
					break;
				}
				
				match = game.find(start);
				match2 = game.find(target);
				
				if(match.equals(null) || match2.equals(null)){
					
					ui.sendMessage("Your word was not found in the dictionary. Please try again.");
				}
			}
			
			String[] commands = { "Human plays.", "Computer plays." };
			int c = ui.getCommand(commands);
			switch(c){
				case 0:
					game.play(start, target);
					break;
				case 1:
					game.solve(start, target);
					break;
			}
				
		}

		
		public void play(String startWord, String targetWord){
			// TODO Auto-generated method stub
			
			UserInterface ui = new GUI();
			String start = startWord;
			String target = targetWord;
			String next = "";
			while(true){
				
				ui.sendMessage("The current word is: " + start + "\nThe target word is: " + target);
				next = ui.getInfo("Enter your next word: ");
				
				if(next == null)				
					break;
				
				if(oneDegree(next, start)){
					start = next;
					
			    }else{
					ui.sendMessage("Warning: Your word differs by more than one letter!");
			    }
				
				if(start.equals(target)){
						start = next;
						if(start.equals(target)){
							ui.sendMessage("You win!!");
							start = "";
							target = "";
							return;
						}
					}
				}
		}

		public Node find(String word){
			Node finder;
			Iterator<Node> i = nodes.iterator();
			
			while(i.hasNext()){
				finder = i.next();
				
				if(finder.word.equals(word)){
					return finder;
				}
			}
			return null;
		}

		public void solve(String start, String target){
			// TODO Auto-generated method stub
			
			UserInterface ui = new GUI();
			List<String> outputList = new ArrayList<String>() ;
			
			Queue<Node> nodeQueue = new ArrayQueue<Node>();
			Node startNode = find(start);
			nodeQueue.offer(startNode);
			Node nextNode; 
			
			
			while(!nodeQueue.isEmpty()){
				Node currentNode = nodeQueue.poll();
				
				for(Node theNode: nodes){
					if(!theNode.word.equals(startNode.word) && theNode.previous == null && oneDegree(theNode.word, currentNode.word)){
						nextNode = theNode;					
						nextNode.previous = currentNode;
						nodeQueue.offer(nextNode);
						
						if(nextNode.word.equals(target)){
							ui.sendMessage("You win!!");
							
							Node outNode = theNode;
							
							while(outNode != null){
								
								outputList.add(outNode.word);
								outNode = outNode.previous;
							}
							String s = "";
							for(int i = outputList.size() -1; i >= 0; i--){
								if(i == 1){
									ui.sendMessage("Got to " + outputList.get(i-1) + " from " + outputList.get(i));
								}
								s += outputList.get(i) + "\n";
							}
							ui.sendMessage(s);
							return;
						}
						
					}
					
				}
			}
		}
		

		
		
	private static class Node{
		String word;
		
		Node previous;
		
		public Node (String word, Node previous){
			this.word = word;
			this.previous = previous;
		}
		
		public Node(Node theNode){
			this.previous = theNode.previous;
			this.word = theNode.word;
			
		}
				
		public Node getPrevious(){
			return this.previous;
		}
		
		public String getWord(){
			return this.word;
		}
		
		
			
		}

	static boolean oneDegree(String a, String b){
		
		if(a.length() != b.length())
			return false;
		int difference = 0;
		
		     for(int i = 0; i < a.length(); i++) { // go from first to last character index the words
		    	 if(a.charAt(i) != b.charAt(i)){
		    		 difference++;
		    	 }
		     }
		      return difference == 1;
		}           
	
	


	public void loadDictionary (String fileName) {
	    // Remember the source name.
	    try {
	      // Create a Scanner for the file.
	      Scanner in = new Scanner(new File(fileName));
	      Node currentNode = null;
	      
	      while (in.hasNextLine()) {
	        String word = in.nextLine();
	        currentNode = new Node(word, null);
	        nodes.add(currentNode);
	      }
	      // Close the file.
	      in.close();
	    } catch (FileNotFoundException ex) {
	      // Do nothing: no data to load.
	      return;
	    } catch (Exception ex) {
	      System.err.println("Load of directory failed.");
	      ex.printStackTrace();
	      System.exit(1);
	    }
	}
}
