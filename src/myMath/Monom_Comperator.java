package myMath;

import java.util.Comparator;

public class Monom_Comperator implements Comparator<Monom> {


	// ******** add your code below *********
	@Override
	public int compare(Monom arg0, Monom arg1) {
		if(arg0.get_power() > arg1.get_power()) {
			return -1;
		}
		return 1;
	}
}
