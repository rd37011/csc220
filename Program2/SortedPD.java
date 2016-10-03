package prog02.copy;

import java.io.*;

/**
 *
 * @author Ryan Diaz
 */
public class SortedPD extends ArrayBasedPD {

	/** Find an entry in the directory.
    @param name The name to be found
    @return A FindOutput object containing the result of the search.
	 */
	protected FindOutput find (String name) {
		int first = 0; 
		int last = size-1;
		int cmp;
		
		
		while(first <= last){
		int middle = (last+first) / 2;
		cmp = name.compareTo(theDirectory[middle].getName());
				if(cmp < 0){
					last = middle - 1;
				}
		        if(cmp == 0){        	
		        	return new FindOutput (true, middle);
		        }
		        if(cmp > 0){
		        	first = middle + 1;
		        }
		        if(last<first)
		        {
		        	return new FindOutput(false, first);
		        }
		}
		return new FindOutput(false, first);
	}

	/** Add an entry to the directory.
      @param name The name of the new person
      @param number The number of the new person
	 */
	
protected void add(String name, String number) {
	
	
	
	FindOutput fo = find(name);
	DirectoryEntry entry = theDirectory[fo.index];	
    int insertionIndex = fo.index;   
    if (size >= theDirectory.length) {
  		reallocate();
    for(int i = size; i > insertionIndex; i--){		
    	theDirectory[i] = theDirectory[i-1];
    	}
    
	theDirectory[insertionIndex] = new DirectoryEntry(name, number);

	}
    size++;
}

/** Remove an entry from the directory.
@param name The name of the person to be removed
@return The current number. If not in directory, null is
        returned

*/
public String removeEntry (String name) {

	FindOutput fo = find(name);
	if (!fo.found)
		return null;	
	DirectoryEntry entry = theDirectory[fo.index];
	int i = fo.index;
	while(i < size){
	theDirectory[i] = theDirectory[i+1];
	i++;
	}
	size--;
	modified = true;
	return entry.getNumber();
 }
}