
package prog03;
import prog02.UserInterface;
import prog02.GUI;

/**
 *
 * @author vjm
 */
public class Main {
  /** Use this variable to store the result of each call to fib. */
  public static double fibN;

  /** Determine the time in microseconds it takes to calculate the
      n'th Fibonacci number.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @return the time for the call to fib(n) in microseconds
  */
  public static double time (Fib fib, int n) {
    // Get the starting time in nanoseconds.
    long start = System.nanoTime();

    // Calculate the n'th Fibonacci number.
    // Store the result in fibN.
    fibN = fib.fib(n);

    // Get the ending time in nanoseconds.  Use long, as for start.
    long end = System.nanoTime();

    // Uncomment the following for a quick test.
    // System.out.println("start " + start + " end " + end);

    // Return the difference between the end time and the
    // start time divided by 1000.0 to convert to microseconds.
    return(end - start)/1000.0;
  }

  /** Determine the average time in microseconds it takes to calculate
      the n'th Fibonacci number.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @param ncalls the number of calls to average over
      @return the average time per call
  */
  public static double averageTime (Fib fib, int n, long ncalls) {
    double totalTime = 0;

    // Add up the total call time for calling time (above) ncalls times.
    // Use long instead int in your declaration of the counter variable.
    for(int i = 0; i < ncalls; i++){
    	totalTime += time(fib, n);
    }


    // Return the average time.
    return totalTime / ncalls;
  }

  /** Determine the time in microseconds it takes to to calculate
      the n'th Fibonacci number accurate to three significant figures.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @return the time it it takes to compute the n'th Fibonacci number
  */
  public static double accurateTime (Fib fib, int n) {
    // Get the time using the time method above.
	 
	     double time = time(fib, n);
	   

    // Since it is not very accurate, it might be zero.  If so set it to 0.1
       if(time == 0){
    	   time = 0.1;
       }


    // Calculate the number of calls to average over that will give
    // three figures of accuracy.  You will need to "cast" it to long
    // by putting   (long)   in front of the formula.
       long ncalls = (long)((1000 * 1000)/(time * time));

    // If ncalls is 0, increase it to 1.
       if(ncalls == 0){
    	   ncalls = 1;
       }


    // Get the accurate time using averageTime.
       return averageTime(fib, n, ncalls);
  }

  static void labExperiments () {
    // Create (Exponential time) Fib object and test it.
    Fib linFib = new ExponentialFib();
    System.out.println(linFib);
    for (int i = 0; i < 11; i++)
      System.out.println(i + " " + linFib.fib(i));
    
    // Determine running time for n1 = 20 and print it out.
    int n1 = 20;
    double time1 = averageTime(linFib, n1, 1000);
    System.out.println("n1 " + n1 + " time1 " + time1);
    long ncalls = (long)((1000 * 1000)/(time1 * time1));
    System.out.println("ncalls "+ ncalls);
    time1 = averageTime(linFib, n1, ncalls);
    System.out.println("n1 "+ n1 + " time 1 "+ time1);
    
    // Calculate constant:  time = constant times O(n).
    double c = time1 / linFib.o(n1);
    System.out.println("c " + c);
    
    // Estimate running time for n2=30.
    int n2 = 30;
    double time2est = c * linFib.o(n2);
    ncalls = (long)((1000*1000)/(time2est * time2est));
    System.out.println("n2 " + n2 + " estimated time " + time2est);
    System.out.println("ncalls "+ ncalls);
    
    
    // Calculate actual running time for n2=30.
    double time2 = averageTime(linFib, n2, 100);
    ncalls = (long)((100 * 100)/(time2 * time2));
    System.out.println("n2 " + n2 + " actual time " + time2);
    
    //Estimating running time for n3 = 100.
    System.out.println();
    int n3 = 100;
    double time3est = c * linFib.o(n3);
    ncalls = (long)((1000*1000)/(time3est * time3est));
    System.out.println("n3 " + n3 + " estimated time " + time3est);
    
  }

  private static UserInterface ui = new GUI();
  
  

  static void hwExperiments (Fib fib) {
	  
	 
    double c = -1;// -1 indicates that no experiments have been run yet.
    double constant = 0;
    int number = 0;
    String n = "0";
    
    while (true) {
    	n = ui.getInfo("Enter n: ");
    	if(n == null || n.equals("")){
    		return;
    	}
    	number = Integer.parseInt(n);
    	while(number == 0 || number % 1 != 0){
    	try{
    		number = Integer.parseInt(n);
    	}
    	catch(NumberFormatException e){
    		ui.sendMessage("java.lang.NumberFormatException:" + " For input string: " + n + "." + "Try again.");
    		n = ui.getInfo("Enter n: ");
    	}
    	}
    	

      // If this not the first experiment, estimate the running time for
      // that n using the value of the constant from the last time.
    	double estTime = 0;
    	double actualTime = 0;
        if(c == -1){
    		actualTime  = time(fib, number);
            constant = actualTime / fib.fib(number);
            ui.sendMessage("fib("+number+")=" + fib.fib(number) + " in " + actualTime + " ms" );
            c = 1;
           
            
    	}
   	
    	// Now, calculate the (accurate) running time for that n.
        // Calculate the constant c.
    	else if(c == 1){
    		estTime = constant * fib.fib(number);
    		ui.sendMessage("Estimated time for fib("+number+")" + " is " + estTime + " ms");
    		if(estTime/3600000. < 1){
    			actualTime  = accurateTime(fib, number);
                constant = actualTime / fib.fib(number);
                ui.sendMessage("fib("+number+")=" + fib.fib(number) + " in " + actualTime + " ms" );
    			continue;
    		}
    		 if(estTime/3600000. > 1){
    	          	ui.sendMessage("This experiment may take longer than an hour, possibly trapping you in here!!    Would you like to continue with the experiment?");
    	          	String[] command2 = {
    	      				"Yes, let's  do it",
    	      				"No, get me out of here"
    	      				}; 
    	            int com2 = ui.getCommand(command2);
    	          	switch(com2){
    	          	case 0:
    	          		actualTime  = time(fib, number);
    	                constant = actualTime / fib.fib(number);
    	                ui.sendMessage("fib("+number+")=" + fib.fib(number) + " in " + actualTime + " ms" );
    	          	case 1:
    	          		return;
    	          	}
    	          return;
    	          }	
    		 ui.sendMessage("Would you like to perform another experiment?");
    	      	String[] command2 = {
    	  				"Yes, let's  do it",
    	  				"No, get me out of here"
    	  				}; 
    	        int com2 = ui.getCommand(command2);
    	      	switch(com2){
    	      	case 0:
    	      		break;
    	      	case 1:
    	      		return;
    	      	}
        return;
    	}

      // Ask the user before doing another experiment.  Otherwise return.

    }
  }

  static void hwExperiments () {
    // In a loop, ask the user which implementation of Fib or exit,
    // and call hwExperiments (above) with a new Fib of that type.
	  
	   String[] commands = {
				"ConstantFib",
				"ExponentialFib",
				"LinearFib",
				"LogFib",
		"Exit"}; 
	while(true){  
	  int com = ui.getCommand(commands); 
	  switch(com){
	  case -1:
		  break;
	  case 0:
		  Fib ConstantFib = new ConstantFib();
		  hwExperiments(ConstantFib);
		  break;
	  case 1:
		  Fib ExponentialFib = new ExponentialFib();
		  hwExperiments(ExponentialFib);
		  break;
	  case 2: 
		  Fib LinearFib = new LinearFib();
		  hwExperiments(LinearFib);
		  break;
	  case 3:
		  Fib LogFib = new LogFib();
		  hwExperiments(LogFib);
		  break;
	  case 4: 
		  return;
	  
	  }
	  
	  }

  }

  /**
   * @param args the command line arguments
   */
  public static void main (String[] args) {
    labExperiments();
    hwExperiments();
  }
}
