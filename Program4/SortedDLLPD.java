package prog04;

/** This is an implementation of the prog02.PhoneDirectory interface that uses
 *   a doubly linked list to store the data.
 *   @author vjm
 */
public class SortedDLLPD extends DLLBasedPD {
  /** Add an entry to the directory.
      @param name The name of the new person.
      @param number The number of the new person.
  */
  protected void add (String name, String number) {
    // EXERCISE
    // Call find to find where to put the entry.
	  FindOutput fo = find(name);
	  DLLEntry next = fo.entry;
	  DLLEntry entry = new DLLEntry(name, number);

    // How does find tell you that the new entry should go at the end?
    // Test for this condition and call super.add which handles it.
	  if(next == null){
		  super.add(name, number);
	  }
    // How can you tell if the new entry should go at the front?
	// Test for this condition and write code to handle it.
	  else if(next == head){
		  entry.setNext(next);
		  next.setPrevious(entry);
		  entry.setPrevious(null);
		  head = entry;  
	  }
    // Set next to the entry that will be next after the new entry is
    // inserted.   
    // Set previous to the entry that will be previous.
    // Insert the new DLLEntry between next and previous.
	  else{
		  DLLEntry previous = next.getPrevious();
		  entry.setNext(next);
		  next.setPrevious(entry);
		  previous.setNext(entry);
		  entry.setPrevious(previous);
	  }


  }

  /** Find an entry in the directory.
      @param name The name to be found.
      @return A FindOutput object describing the result.
  */
  protected FindOutput find (String name) {
    // EXERCISE
	  
	  for(DLLEntry i = head; !(i == null); i = i.getNext()){
		// If this is the entry you want
		  if(name.compareTo(i.getName()) == 0){
			// Return the appropriate FindOutput object.
			  return new FindOutput(true, i);
		  }	
		  if(i == head && name.compareTo(i.getName()) < 0){
			  return new FindOutput(false, i);
		  }
		   // Modify find so it also stops when it gets to an entry after the
		   // one you want.
		  if(name.compareTo(i.getName()) < 0 && name.compareTo(i.getPrevious().getName()) == 0){
			  return new FindOutput(false, i); 
		  }
	  }

    return new FindOutput(false, null); //Name not found.
  }
}
