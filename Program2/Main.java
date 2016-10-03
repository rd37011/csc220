package prog02.copy;

/**
 *
 * @author vjm
 */
public class Main {

	/** Processes user's commands on a phone directory.
      @param fn The file containing the phone directory.
      @param ui The UserInterface object to use
             to talk to the user.
      @param pd The PhoneDirectory object to use
             to process the phone directory.
	 */
	public static void processCommands(String fn, UserInterface ui, PhoneDirectory pd) {
		pd.loadData(fn);

		String[] commands = {
				"Add/Change Entry",
				"Look Up Entry",
				"Remove Entry",
				"Save Directory",
		"Exit"};

		String name, number, oldNumber;

		while (true) {
			int c = ui.getCommand(commands);
			switch (c) {
			case -1:
				ui.sendMessage("You clicked the red x, restarting.");
				break;
			case 0:
				//Ask for the name. 
				name = ui.getInfo("Enter name: ");
				
				//Check if name equals null or nothing. 
				if(name == null || name.length() == 0){break;}
				
				//Ask for the number.
				number = ui.getInfo("Enter the number: ");
				
				// !!! Check for cancel.  Blank is o.k.
				if(number == null){
					break;
				}
				
				// Call addOrChangeEntry
				oldNumber = pd.addOrChangeEntry(name, number);
				
				// Report the result
				if(oldNumber == null){
					ui.sendMessage("Added new entry: Name: " + name + " Number: " + number);	
			   }else{
				    ui.sendMessage("Replaced number of " + name + ", old number: " + oldNumber + " New number: " + number);
			   }
				break;
				
                        case 1:
			    name = ui.getInfo("Enter name: ");
			    if(name == null || name.length() == 0){break;}
			    number = pd.lookupEntry(name);
			    if(number == null){
				ui.sendMessage(name + " is not listed in the directory.");
				break;
			    }
			    ui.sendMessage("The number for "+ name + " is " + number);
			    break;
                        case 2:
			    name = ui.getInfo("Enter name: ");
			    if(name == null || name.length() == 0){break;}
			    number = pd.removeEntry(name);
			    if(number == null){
				ui.sendMessage(name + " is not listed in the directory.");
				break;
			    }
                            ui.sendMessage("Deleted entry with name "+ name +" and number "+ number);
			    break;
                        case 3:
			    pd.save();
			    break;
                        case 4:
                            pd.save();
			    return;
                        }
                }
        }
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		String fn = "csc220.txt";
		PhoneDirectory pd = new SortedPD();
		UserInterface ui = new GUI();
		processCommands(fn, ui, pd);
	}
}
