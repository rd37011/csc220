package prog03;

public class LinearFib implements Fib {
	public double fib (int n){
		double a = 0;
		double b = 1;
		double c = a+b;
		if(n == 0)
			return 0;
		for(int i = 1; i < n; i++){
			c = a + b;
			a = b;
			b = c;
			
		}
			return c; 

}
		

	
	
	public double o (int n){
		return n;
	}
}
