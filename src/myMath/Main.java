package myMath;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String test1 = "3*x^4+1*x^2";//a*x^b
		String test2 = "0";//a*x

		Polynom_able a = new Polynom(test1);
		Polynom_able b = new Polynom(test2);
		System.out.println(a);
		System.out.println(b);
		System.out.println(a.f(5));
		System.out.println(b.f(6));
	}

}
