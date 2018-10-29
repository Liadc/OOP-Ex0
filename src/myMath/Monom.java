
package myMath;

/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Timor & Liad
 *
 */
public class Monom implements function{

	private double _coefficient;
	private int _power;


	/**	
	 * a Constructor with double as coefficient and integer as power.
	 * @param a - Coefficient of the monom
	 * @param b - Power of the monom.
	 */
	public Monom(double a, int b){
		if(b>=0) {
			this.set_coefficient(a);
			this.set_power(b);
		}
		else {
			throw new RuntimeException();
		}
	}

    /**
     * Copy constructor for Monom. create a new Monom with same values of the other one.
     * @param m1 Monom to perform a copy on.
     */
	public Monom(Monom m1) {
		this(m1.get_coefficient(), m1.get_power());
	}

    /**
     * a String constructor for the Monom. creates a new Monom from a String of the form: a*x^b where a is real number and b is non-negative.
     * @param s String, assuming monom of the form: a*x^b where a is real number and b is non-negative.
     */
	public Monom(String s) {
	    this._power =0;
	    this._coefficient=0;
	    if(!isValidMonom(s)){
            throw new RuntimeException("Monom with illegal syntax: "+s);
        }
        else {
            s = s.toLowerCase();
            if (s.contains("x")) {
                if (s.contains("^")) {
                    String power = s.substring(s.indexOf("^") + 1);
                    if (power.length() > 0 && power.matches("\\d+")) { //contains at one or more digits in POWER.
                        this.set_power(Integer.parseInt(power));
                    } else {
                        throw new RuntimeException("Illegal power in Monom");
                    }
                    if (s.contains("*")) {
                        String coef = s.substring(0, s.indexOf("*"));
                        if (coef.length() > 0 && isDouble(coef)) {
                            this.set_coefficient(Double.parseDouble(coef));
                        } else {
                            throw new RuntimeException();
                        }
                    } else {
                        String beforeX = s.substring(0, s.indexOf("x"));
                        if (beforeX.length() > 0 && isDouble(beforeX)) {
                            this.set_coefficient(Double.parseDouble(beforeX));
                        } else {
                            if (beforeX.equals("-"))
                                this.set_coefficient(-1);
                            else if (beforeX.length() == 0) {
                                this.set_coefficient(1);
                            } else {
                                throw new RuntimeException();
                            }
                        }
                    }
                } else { //does not contains ^
                    if (s.contains("*")) {
                        String coef = s.substring(0, s.indexOf("*"));
                        if (coef.length() > 0 && isDouble(coef)) {
                            this.set_coefficient(Double.parseDouble(coef));
                        } else {
                            throw new RuntimeException();
                        }
                    } else {
                        String beforeX = s.substring(0, s.indexOf("x"));
                        if (beforeX.length() > 0 && isDouble(beforeX)) {
                            this.set_coefficient(Double.parseDouble(beforeX));
                        } else {
                            if (beforeX.equals("-"))
                                this.set_coefficient(-1);
                            else if (beforeX.length() == 0) {
                                this.set_coefficient(1);
                            } else {
                                throw new RuntimeException();
                            }
                        }
                    }
                }
            } else { //does not contain x
                if(isDouble(s)){
                    this.set_coefficient(Double.parseDouble(s));
                }
                else{
                    throw new RuntimeException("Illegal syntax for monom");
                }
            }
        }
	}//end function


	// ***************** add your code below **********************

    /**
     * This method will add a new Monom to the current Monom iff the powers are equal. otherwise, will throw RuntimeException.
     * @param m1 Monom, the other Monom to add.
     */
	public void add(Monom m1) {
		if(this._power == m1._power) {
			this._coefficient += m1._coefficient;
		}
		else{
            throw new RuntimeException("Cannot add monoms, not the same power!");
        }
	}

    /**
     * This method will multiply a Monom with the current monom.
     * @param m1 Monom, multiply on the current monom.
     */
	public void multiply(Monom m1) {
		this._coefficient = this._coefficient*m1._coefficient;
		this._power += m1._power;
	}

    /**
     * This method will perform derivative on the current monom and change it's values.
     */
	public void derivative() {
		if(this._power>0) {
			this._coefficient = this._coefficient * this._power;
			this._power--;	
		} else {
			this._coefficient = 0;
		}
	}

    /**
     * This method will get a double number X and perform f(X) using the Monom values: coefficient as coefficient of X,
     * and power as power of X, and return the result.
     * @param x double, the variable to perform f(X) on.
     * @return double, result representing f(x) answer.
     */
	@Override
	public double f(double x) {
		return this._coefficient*Math.pow(x, this._power);
	}

    /**
     * a Getter method to get the Monom coefficient.
     * @return double, the monom Coefficient.
     */
	public double get_coefficient() {
		return _coefficient;
	}
    /**
     * a Getter method to get the Monom power.
     * @return double, the monom power.
     */
	public int get_power() {
		return _power;
	}

    /**
     * overriding the toString method, to return a String with the form of: a*x^b where a is the coefficient value and
     * b is the power value.
     * @return String with the form of: a*x^b where a is the coefficient value and b is the power value.
     */
	@Override
	public String toString() {
		String checker = "";
		if(this._power>0) {
			checker = this._coefficient + "*x^" + this._power;
		}else {
			checker = ""+this._coefficient;
		}

		return checker;
	}

    /**
     * a method to check whether this Monom is valid (its power is non negative integer) return True if it's valid, false otherwise.
     * @return Boolean, true if this monom is valid, false otherwise.
     */
	public boolean isValidMonom(){
        return (this.get_power() >= 0);
    }

    /**
     * This method gets other Monom and checks if the other monom has the same value as the current monom.
     * Will return True if they are equal, false otherwise.
     * @param m1 Monom, the one we compare current monom to.
     * @return Boolean, return True if they are equal, false otherwise.
     */
	public boolean equals(Monom m1) {
		return (this._power==m1._power)&&(this._coefficient==m1._coefficient);
	}

	//****************** Private Methods and Data *****************

    /**
     * a private method, gets a String and checks if it's a valid syntax Monom string.
     * This method uses Java Regex, and checks if the monomStr matches the regex. if it does, it's a valid syntax and return true,
     * otherwise, it's an invalid syntax for the monom, and returns false.
     * @param monomStr the String to check if it's a valid syntax string representing a monom.
     * @return Boolean, true if string is valid, false otherwise.
     */
    private boolean isValidMonom(String monomStr) {
        if (monomStr.length() > 0) {
            return monomStr.matches("([-]?)(\\d*\\.?\\d*)?(\\*)?([xX](\\^\\d+)?)?"); //RegEx for valid syntax Monom, with monom parts separated to pattern groups.
        }
        return false;
    }

    /**
     * This method will get a String 'str' and checks if it can be parsed into Double. returns true if it can,
     * return false otherwise.
     * @param str String, checks if this string is a double.
     * @return Boolean, true if this string can be parsed to Double, false otherwise.
     */
    private static boolean isDouble(String str) {
	    try {
	        Double.parseDouble(str);
	        return true;
        }
        catch (Exception e){
	        return false;
        }
    }

    /**
     * a Setter method to set the power for the monom.
     * @param p Integer, (non negative). the power to set the value to.
     */
	private void set_power(int p) {
		this._power = p;
	}

    /**
     * a Setter method to set the coefficient for the monom.
     * @param a Double, the coefficient to set the value to.
     */
    private void set_coefficient(double a){
        this._coefficient = a;
    }
}
