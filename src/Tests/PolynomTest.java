package Tests;
/**
 * This Junit class - is not complete and should contains additional testing functionality - should be written by the OOP students.
 */
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import myMath.Monom;
import myMath.Polynom;

public class PolynomTest {

	private static long SEED = 1*2*3*5*7*11*13*17*19+1, D=100, SIZE_oF_POLY=5, NUMBER_OF_POLYS=10;
	private Polynom _p0;
	private Polynom _p_1;
	private Monom _m_1;
	private ArrayList<Polynom> _polys;
	private Random _rand;

	@Before
	public void setUp() throws Exception {

		init_rand();
		init_polynoms();
	}
	private void init_rand() {
		init_rand(SEED);
	}
	private void init_rand(long seed) {
		_rand = new Random(seed);
	}
	private void init_polynoms() {
		_p0 = new Polynom();
		_m_1 = new Monom(-1,0);
		_p_1 = new Polynom(); _p_1.add(new Monom(_m_1)); // -1
		
		_polys = new ArrayList<Polynom>();
		for(int a=0;a<NUMBER_OF_POLYS;a++) {
			Polynom p = new Polynom();
			for(int i=0;i<SIZE_oF_POLY;i++) {
				Monom curr = rand_monom();
				p.add(curr);
			}
			_polys.add(p);
		}
	}
	private Monom rand_monom() {
		Monom ans = null;
		double cof = _rand.nextDouble()*D - D/2;
		int pow = _rand.nextInt(10);
		ans = new Monom(cof, pow);
		return ans;
	}
	@Test
	public void testPolynom() {
		Polynom ot = _polys.get(0);
		Polynom p = new Polynom(ot);
		if(!p.equals(ot)) {
			fail("ERR - the polynoms should be the same (equals)");
		}
	}
	@Test
	public void testPolynomString() {
		Polynom p0 = _polys.get(0);
		String s = p0.toString();
		Polynom p1 = new Polynom(s);
		if(!p0.equals(p1)) {
			fail("ERR - related to the Polynom(String) init: the polynoms should be the same (equals)");
		}
	}

	@Test
	public void testPolynomString2() {
		String s = "-2.34x^4 + 2.3x^2 + 12";
		Polynom p1 = new Polynom(s);
		if(p1.size()!=3) {
			fail("ERR - related to the Polynom(String) init: the polynoms should be the same (equals)");
		}
	}
	@Test
	public void testAddMonom() {
		Polynom p1 = new Polynom(_polys.get(0));
		Monom m1 = new Monom(_polys.get(1).iteretor().next());
		p1.add(new Monom(m1));
		m1.multiply(_m_1);
		p1.add(new Monom(m1));
		if(!p1.equals( _polys.get(0))) {
			fail("ERR - the polynoms should be the same (equals)");
		}
	}

	@Test
	public void testAddPolynom() {
		Polynom p1 = new Polynom(_polys.get(0));
		Polynom p2 = new Polynom(_polys.get(1));
		Polynom p3 = new Polynom(_polys.get(1));
		p3.multiply(_p_1);
		p1.add(p2);
		p1.add(p3);
		//assertEquals(p1, _polys.get(0));
		if(!p1.equals( _polys.get(0))) {
			fail("ERR - the polynoms should be the same");
		}
	}
/**
	@Test
	public void testMultiplyMonom() {
		fail("Not yet implemented");
	}

	@Test
	public void testMultiplyPolynom() {
		fail("Not yet implemented");
	}

	@Test
	public void testEqualsPolynom() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsZero() {
		fail("Not yet implemented");
	}

	@Test
	public void testF() {
		fail("Not yet implemented");
	}

	@Test
	public void testRoot() {
		fail("Not yet implemented");
	}
**/
}
