papackage prog05;

import java.util.Stack;
import prog02.UserInterface;
import prog02.GUI;

public class Parser {
  static final String OPERATORS = "()+-*/u^";
  static final int[] PRECEDENCE = { -1, -1, 1, 1, 2, 2, 3, 4  };
  String input = null;
  int index = 0;
  Stack<Character> operatorStack = new Stack<Character>();
  Stack<Double> numberStack = new Stack<Double>();
  static UserInterface ui = new GUI();

  boolean atEndOfInput () {
    while (index < input.length() &&
           Character.isWhitespace(input.charAt(index)))
      index++;
    return index == input.length();
  }

  boolean isNumber () {
    return Character.isDigit(input.charAt(index));
  }

  double getNumber () {
    int index2 = index;
    while (index2 < input.length() &&
           (Character.isDigit(input.charAt(index2)) ||
            input.charAt(index2) == '.'))
      index2++;
    double d = 0;
    try {
      d = Double.parseDouble(input.substring(index, index2));
    } catch (Exception e) {
      System.out.println(e);
    }
    index = index2;
    return d;
  }

  char getOperator () {
	  
    char op = input.charAt(index++);
    if (OPERATORS.indexOf(op) == -1)
      System.out.println("Operator " + op + " not recognized.");
    return op;
  }

  
  String numberStackToString () {
	  
    String s = "numberStack: ";
    Stack<Double> helperStack = new Stack<Double>();

    while (!numberStack.empty()){
		
    	helperStack.push(numberStack.pop());
	}
    while (!helperStack.empty()){
		
    	s += helperStack.peek() + " "; 
    	numberStack.push(helperStack.pop());
    }
    return s;
  }

  String operatorStackToString () {
    String s = "operatorStack: ";
    Stack<Character> helperStack = new Stack<Character>();
    
    while (!operatorStack.empty()){
		
    	helperStack.push(operatorStack.pop());
    }
    
    while(!helperStack.empty()){
		
    	s += helperStack.peek() + " "; 
    	operatorStack.push(helperStack.pop());
    }
    return s;
  }

  void displayStacks () {
    ui.sendMessage(numberStackToString() + "\n" +
                   operatorStackToString());
  }

  double evaluate (String expr) {
    input = expr;
    index = 0;
    boolean previousNum = false;
    char last = 0;
    
    while (!operatorStack.empty()) operatorStack.pop();
    while (!numberStack.empty()) numberStack.pop();

    while (!atEndOfInput()) {
		
      if (isNumber()) {
		  
        numberStack.push(getNumber());
    	previousNum = true;
        displayStacks();

      } else {
		  
      		if (operatorStack.size() == 0){ 
			
      			char op = getOperator();
      			if (op == '-' && !previousNum) {
					
      				operatorStack.push('u');
      				displayStacks();
      				last = 'u';
      			} else {
					
      				operatorStack.push(op);
      				displayStacks();
      				last = op;
      			}

      			previousNum = false;
      		} else {
				
      			char op = getOperator();
      			if (op == '-' && (previousNum || last == ')')){
					
      				processOperator(op);
      				last = op;
      				displayStacks();
      			} else if (op == '-' && last != 'u') {
					
      				processOperator('u');
      				last = 'u';
      				displayStacks();
      			} else {
					
      				processOperator(op);
      				last = op;
      				displayStacks();
      			}

      			previousNum = false;
      		}
      	}
    }
    while (!operatorStack.empty()) {
		
    	evaluateOperator();
    }

    return numberStack.peek();
  }

  
  
  int precedence (char op) {
    return PRECEDENCE[OPERATORS.indexOf(op)];
  }
  
  double evaluateOperator (double a, char op, double b){
	  
	  double com = 0.0;
	  
	  switch (op){
	  
	  case '+':
		  com = a + b;
		  break;
	  case '-':
		  com = a - b;
		  break;
	  case '*':
		  com = a*b;
		  break;
	  case '/':
		  com = a / b;
		  break;
	  case '^':
		  com = Math.pow(a, b);
		  break;
	  }
	  
	  return com;
  }

  void evaluateOperator () {
	  if (operatorStack.peek() == 'u') {
		  double b = numberStack.pop();
		  b = -b;
		  numberStack.push(b);
		  operatorStack.pop();
		  displayStacks();
		  
	  } else {
		  
		  char op = operatorStack.pop();
		  double b = numberStack.pop();
		  double a = numberStack.pop();	
		  numberStack.push(evaluateOperator(a, op, b));
		  displayStacks();
	  }
	
  }
  
  void processOperator (char op) {
	  if (op == '('){
		  operatorStack.push(op);
	  } else if (op == ')'){
		  while (operatorStack.peek() != '('){
			  while (operatorStack.peek() != '(' && precedence(operatorStack.peek()) >= precedence(op)){
				  evaluateOperator();
			  }
		  }
		  operatorStack.pop();
		  
	  } else {
		  while (!operatorStack.empty() && precedence(operatorStack.peek()) >= precedence(op)){
			  evaluateOperator();
		  }
		  operatorStack.push(op);
	  }
  }
  
  
  public static void main (String[] args) {
    Parser parser = new Parser();

    while (true) {
      String line = ui.getInfo("Enter arithmetic expression or cancel.");
      if (line == null)
        return;

      try {
        double result = parser.evaluate(line);
        ui.sendMessage(line + " = " + result);
      } catch (Exception e) {
        System.out.println(e);
      }
    }
  }
}
