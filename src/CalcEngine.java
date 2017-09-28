import java.util.Stack;
//THE BRACETS OR THE POWER BUTTON DOES NOT WORK 
// THE CALCULATOR WORKS WITH ADDING / SUBTRACTING / MULTIPLYING / DIVIDING / DECIMAL POINTS.
public class CalcEngine
{
    char operator;
    String displayString;

    /**
     * Create a CalcEngine instance. Initialise its state so that it is ready 
     * for use.
     */
    public CalcEngine()
    {
        operator =' ';
        displayString=null;
    }
    
    public String getDisplayString()
    {
    	return displayString+" ";
    }
    
    //adds button pressed to infix equation in calculator window 
    // if button is not equals or clear
    public void buttonPressed(String c)
    {
    	if (displayString == null){
    		displayString = c;
    	}
    	else{
    		displayString = displayString+ c;
    	}
    }
    
    //calls methon to convert to postfix and to evaluate the postfix equation
    public void equalsPressed()
    {
    	displayString = convertToPostfix(displayString);
    	displayString = evaluate(displayString).toString();
    	
    }
    
    /**
     * Tests if the character is an operator
     */
    private static boolean isOperator(char c)
    {
    	return c == '+' || 
				c == '-' ||
				c == '*' || 
				c == '/'  ;
			//	c == '^' || 
			//	c == '(' || 
			//	c == ')'
    }
 
    // decides which operations are to be performed for the equation according to the BOMBDAS rules of priority
    public static int precendance(String op) 
    {
    	if (op.equals("+") || op.equals("-"))
    	{
    		return 1;
    	}
		else if (op.equals("*") || op.equals("/"))
		{
			return 2;
		}
    	//not working
	/*	else if (op.equals("^"))
		{
			return 3;
		}

		else if (op.equals("("))
		{
			return 4;
		}
	 */
		else 
		{
			return 0;
		}
	}

    // converts and infix output equation to a postfix equation.
    static String convertToPostfix(String infix) 
    {
		Stack<String> postfixStack = new Stack<String>();
		String postfix = "";

		String[] tokens = infix.split("(?<=[^\\.\\d])|(?=[^\\\\d])");
		//(?<=[^\.a-zA-Z\d]) is a positive lookbehind. It matches the place between two characters, if the preceding string matches the sub-regex contained within (?<=...).
		//[^\.a-zA-Z\d] is a negated character class. It matches a single character that is not contained within [^...]., matches invisable breaks between characters 
		//\. matches the character ..
		//a-z matches any lowercase character between a and z.
		//A-Z is the same, but for uppercase.
		//\d is the equivalent of [0-9], so it matches any digit.

		for (String token : tokens)// iterates through tokens array
		{ 
			switch (token)
			{
			case "(":
				postfixStack.push(token); // pushes the parsed toked onto the stack
				break;
			case ")":
				String t = postfixStack.pop(); // poping the string t of the stack 
				do {
					postfix += t;  // assign postfix to t 
					postfix += " "; // postfix = empty string
					t = postfixStack.pop();  // poping the postfix off the stack
				} 
				while (!t.equals("(")); // when string t not equal to bracets
				break;  // break from loop
			case "+":
			case "-":
			case "/": 	 
			case "*":
			// not working case "^":
				if (postfixStack.empty()) // if stack is empty
				{ 
					postfixStack.push(token); // push tokens onto the stack
				} 
				else 
				{
					while (!postfixStack.empty() && precendance(token) < precendance(postfixStack.peek())) //  else postfix stack is empty and precendence get token is less then the peak value of postfix with precedence
					{ 
						postfix += postfixStack.pop(); // postfix = postfix + postfixStack and poping the item of the stack
						postfix += " "; // postfix = postfix + empty string
					}
					postfixStack.push(token);  //pushes a token onto the postfix stack
				}
				break; // breaks out from the loop
			default: // by default it takes the postfix and adds to postfix token and adds to an empty string 
				postfix += token;
				postfix += " ";
			}
		}
		while (!postfixStack.empty())// when postfix is not empty postfix + postfix stack pop and then add to an empty string
		{ 
			postfix += postfixStack.pop();
			postfix += " ";
		}
		return postfix; // return postfix
	}

    // gives the result  to the equation
    private Double evaluate(String postfix)
    {
    	String p = postfix;
    	
    	// parses the post string and create a new array of operands
    	String[] tokens = p.split("\\s+");
    	Stack<Double> operands =new Stack<Double>();
    	
    	// iterates through tokens array
    	for (String token : tokens)
    	{
    		// Pushes any multiple digit numbers on to the stack.
    		if(token.length()!=1)
    		{
    			operands.push(Double.parseDouble(token));
    		}
    		
    		// Pushes any single digit operands on to the stack.
    		else if(!isOperator(token.charAt(0)))
    		{
    			operands.push(Double.parseDouble(token));
    		}
    		
    		// Pushes both of the digitits and pops the old things from the stack
    		else if(isOperator(token.charAt(0)) && token.charAt(0)!= '(')
    		{
    			double op1 = operands.pop();
    			double op2 = operands.pop();
    			double result = equals(op1,op2,token.charAt(0));
    			operands.push(result);
    		}
    		
    	}
    	return operands.pop();
    }

    // defines the operations for certain operators and returns result of operation.
    public double equals(double op1, double op2, char operator)
    {
    	double result = 0;
    	
        if (operator == '+') 
        {
			result = op1 + op2;
		}
	    else if (operator == '-')
	    {
	    	result = op2 - op1;
		}
		else if (operator == '*') 
		{
			result = op2 * op1;
		}
		else if (operator == '/') 
		{
			result = op2 / op1;
		}
        // not implemented
		/*else if (operator == '^') 
		{
			double power = op2;
			
			for(int i=1;i<op1;i++)
			{
				power = power*op2;
			}
			result = power;
		}
		*/
        return result;
    }
    
    public void clear()
    {
        displayString = null;
    }

    /**
     * Return the title of this calculation engine.
     */
    
    public String getTitle()
    {
        return("Calculator");
    }

    /**
     * Return the author of this engine. This string is displayed as it is,
     * so it should say something like "Written by H. Simpson".
     */
    
    public String getAuthor()
    {
        return("Kacper Woloszyn");
    }

    /**
     * Return the version number of this engine. This string is displayed as 
     * it is, so it should say something like "Version 1.1".
     */
    public String getVersion()
    {
        return("Ver. 1.0");
    }

}