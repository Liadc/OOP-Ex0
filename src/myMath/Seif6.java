package myMath;


class Seif6 {
	public static void main(String[] args) {
		Polynom p = new Polynom("0.2x^4-1.5x^3+3.0x^2-x-5");
		p.show2D(-2,6);
		Polynom negP = new Polynom("-1");
		negP.multiply(p);

		//calculating area under X axis and over 'p' given polynom from -2 to 6:
		System.out.println(negP.area(-2,6,0.01));
	}
}