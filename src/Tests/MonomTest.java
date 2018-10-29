package Tests;
/**
 * This is a simple example of Adding JUnit to the Monom class
 */
import static org.junit.Assert.*;

import org.junit.Test;

import myMath.Monom;

public class MonomTest {
	public final static double EPS = 0.0000001;
	@Test
	public void testMonomDoubleInt() {
		Monom m1 = new Monom(1.22,2);
		if(!(m1.get_power()>=0)) {
            fail("JUnit fail: Somthing is wrong with the Monom construction method");
		}
	}

	@Test
	public void testMonomMonom() {
        Monom m1 = new Monom(1.22, 2);
        Monom m2 = new Monom(m1);
		if(!m1.equals(m2)) {
            fail("JUnit fail: Somthing is wrong with the Monom copy construction or equals method");
		}
	}
	@Test
	public void testMonomString() {
		double coef = -2.21;
		int pow = 12;
		Monom m1 = new Monom(coef, pow);
		String s = m1.toString();
		Monom m2 = new Monom(s);
		if(!m1.equals(m2)) {
			fail("Something is wrong with the String Constructor of the Monom!");
		}
	}
	@Test
	public void testMonomString2() {
		double coef = -2.21;
		int pow = 12;
		Monom m1 = new Monom(coef, pow);
		String[] s = {"x", "x^2", "12.3", "13.1x","5x^8","12*x","-2x^2","-2*x^2","-x^2","-x","22","3", "0","x^1",
				"-3.3*x", "4*x^55", "0*x^5"};
		for(int i=0;i<s.length;i++) {
			Monom m = new Monom(s[i]);
			if(!(m.get_power()>=0)) {
				fail("Something is wrong with the String Constructor of the Monom! fail on: "+s[i]);
			}
		}
	}
	@Test
	public void testMonomString3() {
		double coef = -2.21;
		int pow = 12;
		Monom m1 = new Monom(coef, pow);
		String[] s = {"--ex", "##$$%%xjjhgjh^@2", "12xx.3", "13.1x^-0.3","xx","--x","x^^","x^^2","-x^",
				"-3x^-3","x^-2", "-+","+-","-","-3g5x^6","3gx", " ", "","*x^","*x","*x ^", "x ^2",
				"3x x^2","3x ", "^2","3  x", "3  ", " x"," x^2"," 1"," ^", " -","- ","+ "," +1"," ++",
				" +x"," +2*", " 2^1", "+1","+x","+5x","+5x^2","x3","x^1.1","x^-1","4* x^100"};
		for(int i=0;i<s.length;i++) {
			try {
				Monom m = new Monom(s[i]);
				fail("Something is wrong with the String Constructor of the Monom! fail on: "+s[i]);
			}
			catch (Exception e) {
				// all OK
			}
		}
	}
	@Test
	public void testGet_power() {
		int power = 11;
		Monom m1 = new Monom(1.22,power);
		if(power!= m1.get_power()) {
			fail("JUnit fail: Somthing is wrong with the get_power() method");
		}
	}

	@Test
	public void testGet_coefficient() {
		double cof = -1.003;
		Monom m1 = new Monom(cof,2);
		String err_msg = "JUnit fail: Somthing is wrong with the get_coefficient() method";
		assertEquals(err_msg,cof,m1.get_coefficient(),EPS);
		//if(cof!= m1.get_coefficient()) {fail(err_msg);}
	}

	@Test
	public void testF() {
		double cof = -2.334;
		int pow = 3;
		double x = -1.0023;
		Monom m1 = new Monom(cof,pow);
		double ev = cof*Math.pow(x, pow);
		assertEquals(ev,m1.f(x),EPS);
	}


	@Test
	public void testDerivative() {
		double cof = -2.334;
		int pow = 3;
		Monom m1 = new Monom(cof,pow);
		Monom m2 = new Monom(m1);
		m2.derivative();
		double ev_cof = cof*m1.get_power();
		int ev_pow = m1.get_power()-1;
		Monom m3 = new Monom(ev_cof, ev_pow);
		//assertEquals(m2,m3); // this is not working as for numerical issues (epsilon is required!)
		assertEquals(m2.get_coefficient(),m3.get_coefficient(),EPS);
		assertEquals(m2.get_power(),m3.get_power(),EPS);
	}

	@Test
	public void testAdd() {
		double cof1 = -2.334, cof2 = 1.543;
		int pow = 3;
		Monom m1 = new Monom(cof1,pow);
		Monom m2 = new Monom(cof2,pow);
		m1.add(m2);
		assertEquals(m1.get_coefficient(), cof1+cof2,EPS);
	}
	@Test
	public void testWrongAdd() {
		double cof1 = -2.334, cof2 = 1.543;
		int pow = 3;
		boolean fail = false;
		Monom m1 = new Monom(cof1,pow);
		Monom m2 = new Monom(cof2,pow+1);
		try {
			m1.add(m2);
		}
		catch(RuntimeException e) {
			fail = true;
		}
		assertTrue(fail);
	}
	@Test
	public void testMultiply() {
		double cof1 = -2.334, cof2 = 1.543;
		int pow1 = 4, pow2 = 11;
		Monom m1 = new Monom(cof1,pow1);
		Monom m2 = new Monom(cof2,pow2);
		m1.multiply(m2);
		assertEquals(m1.get_coefficient(), cof1*cof2,EPS);
		assertEquals(m1.get_power(), pow1+pow2,EPS);
	}

	@Test
	public void testEqualsMonom() {
		Monom m1 = new Monom(1.22,0);
		Monom m2 = new Monom(m1);
		if(!m1.equals(m2)) {
			fail("JUnit fail: Somthing is wrong with the Monom copy construction or equals method");
		}
		m1 = new Monom(0,2);
		m2 = new Monom(m1);
		if(!m1.equals(m2)) {
			fail("JUnit fail: Somthing is wrong with the Monom copy construction or equals method");
		}
	}

}
