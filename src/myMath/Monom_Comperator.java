package myMath;

import java.util.Comparator;

/**
 * This Class will define how to compare between two Monoms.
 * receives two monoms and returns -1 if the first monom is greater than the second one, and 1 otherwise.
*/
public class Monom_Comperator implements Comparator<Monom> {


	// ******** add your code below *********

    /**
     * This function will receive two monoms and decides how to compare between them. returns -1 if the first monom is greater
     * than the second one, and 1 otherwise.
     * @param arg0 the first Monom.
     * @param arg1 the second Monom.
     * @return integer, -1 if the first monom is greater
     * than the second one, and 1 otherwise.
     */
	@Override
	public int compare(Monom arg0, Monom arg1) {
		if(arg0.get_power() > arg1.get_power()) {
			return -1;
		}
		return 1;
	}
}
