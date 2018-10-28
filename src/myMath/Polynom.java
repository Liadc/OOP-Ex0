package myMath;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import myMath.Monom;

/**
 * This class represents a Polynom with add, multiply functionality, it also supports the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 *
 * @author Liad & Timor
 */
public class Polynom implements Polynom_able {

    private List<Monom> poly;

    /**
     * a Constructor for Polynom class. will initiate a polynom with the Zero monom.
     */
    public Polynom() {
        poly = new ArrayList<>();
        poly.add(new Monom(0, 0));
    }

    /**
     * a Constructor for Polynom class using a String. will ignore spaces, and can use x and capital X for a monom.
     * if the string is an invalid syntax for a polynom, will throw Runtime Exception.
     */
    public Polynom(String str) {
        poly = new ArrayList<>();
        try {
            str = str.replaceAll("\\s+", ""); //replaces all white-spaces withing the monomStr to "".
            int i = 0;
            str = str.replaceAll("-", "\\+-"); //replacing - for +- to be able to split with +
            str = str.replaceAll("X", "x");
            if (str.charAt(0) == '+') i = 1;//for specific when the poly starts with -
            String[] parts = str.split("\\+");
            for (; i < parts.length; i++) {
                this.add(new Monom(parts[i]));
            }
            this.sort();
            this.fixzero();
        } catch (Exception e) {
            throw new RuntimeException("illegal syntax for polynom");
        }
    }

    /**
     * a Copy-constructor performing a deep-copy of an other Polynom and initiate a new one with the same Monoms.
     * @param p1 Polynom_able, the other Polynom to perform deep-copy on.
     */
    public Polynom(Polynom_able p1){
        this.poly = new ArrayList<>();
        Iterator<Monom> p1Itr = p1.iteretor();
        while(p1Itr.hasNext()){
            Monom toAdd = new Monom(p1Itr.next());
            this.add(toAdd);
        }
    }

    /**
     * a copy Method performing a deep-copy of the CURRENT polynom, and initiate a new one with the same
     * Monoms, then returns it.
     * @return Polynom, a deep-copy of the current one.
     */
    @Override
    public Polynom_able copy() {
        Polynom_able out = new Polynom();
        Iterator<Monom> runner = this.iteretor();
        while (runner.hasNext()) {
            Monom copy = runner.next();
            out.add(new Monom(copy.get_coefficient(), copy.get_power()));
        }
        return out;
    }

    /**
     * This function returns an Integer represents the number of monoms inside this polynom.
     * @return Integer, represents the number of monoms inside this polynom.
     */
    public int size() {
		return this.poly.size();
	}

    @Override
    public double f(double x) {
        double sum = 0;
        Iterator<Monom> pointer = this.iteretor();
        while (pointer.hasNext()) {
            Monom p = pointer.next();
            sum += p.f(x);
        }
        return sum;
    }

    @Override
    public void add(Polynom_able p1) {
        Iterator<Monom> pointer = p1.iteretor();
        while (pointer.hasNext()) {
            Monom p = pointer.next();
            this.add(p);
        }
        this.fixzero(); //never used, just to make sure.
    }

    @Override
    public void add(Monom m1) {
        boolean check = true;
        Iterator<Monom> it = this.iteretor();
        while (it.hasNext()) {
            Monom p = it.next();
            if (p.get_power() == m1.get_power()) {
                p.add(m1);
                check = false;
            }
        }
        if (check) {
            poly.add(new Monom(m1));
            poly.sort(comp);
        }
        fixzero();
    }

    @Override
    public void substract(Polynom_able p1) {
        Polynom_able minus1 = new Polynom("-1");
        Polynom_able p2 = p1.copy();
        p2.multiply(minus1);
        this.add(p2);
    }

    @Override
    public void multiply(Polynom_able p1) {
        Polynom_able polycopy = this.copy();
        Iterator<Monom> copyp = polycopy.iteretor();
        Iterator<Monom> pointerp1 = p1.iteretor();
        this.clear();        //poly or this
        while (copyp.hasNext()) {
            Monom monomcopy = copyp.next();
            pointerp1 = p1.iteretor();
            while (pointerp1.hasNext()) {
                Monom p1p = pointerp1.next();
                Monom keep = new Monom(monomcopy.get_coefficient(), monomcopy.get_power());
                keep.multiply(p1p);
                this.add(keep);
            }
        }
        this.fixzero();
    }

    @Override
    public boolean isZero() {
        // TODO Auto-generated method stub
        //this.fixzero();
        if (this.get_size() == 0) return true; //WORK ON IT
        return false;
    }

    @Override
    public Polynom_able derivative() {
        // TODO Auto-generated method stub
        Polynom_able out = new Polynom();
        Iterator<Monom> runner = this.iteretor();
        Monom m1;
        while (runner.hasNext()) {
            m1 = new Monom(runner.next());
            m1.derivative();
            out.add(m1);
        }
        return out;
    }

    @Override
    public double root(double x0, double x1, double eps) {
        if (this.f(x0) * this.f(x1) < 0) {
            double mid = x0;
            while ((x1 - x0) >= eps) {
                mid = (x0 + x1) / 2;
                if (this.f(mid) == 0) {
                    return mid;
                } else {
                    if (this.f(x0) * this.f(mid) < 0) {
                        x1 = mid;
                    } else {
                        x0 = mid;
                    }
                }
            }
            return mid;
        }
        throw new RuntimeException("f(x0)*f(x1) >0");
    }

    @Override
    public double area(double x0, double x1, double eps) {
        double totalwidth = (x1 - x0);
        double deltax = totalwidth / eps;
        double sum = 0;
        for (int i = 0; i < eps; i++) {
            double p = this.f(x0 + i * deltax);
            if (p > 0) {
                p = deltax * p;
                sum = sum + p;
            }
        }
        return sum;
    }

    @Override
    public boolean equals(Polynom_able p1) {
        // TODO Auto-generated method stub
        if (this.get_size() == p1.get_size()) {
            Iterator<Monom> runner1 = this.iteretor();
            Iterator<Monom> runner2 = p1.iteretor();
            while (runner1.hasNext()) {
                Monom m1 = runner1.next();
                Monom m2 = runner2.next();
                if (!m1.equals(m2)) {
                    return false;        //-4.0*x^3-5.0*x^2 == -4.0*x^3-5.0*x^2+0.0(cuz the fixzero)
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public Iterator<Monom> iteretor() {
        // TODO Auto-generated method stub
        return poly.iterator();
    }

    @Override
    public String toString() {
        String a = "";
        Iterator<Monom> iterator = this.iteretor();
        while (iterator.hasNext()) {
            Monom b = iterator.next();
            a = a + b;
            if (iterator.hasNext()) a += "+";
        }
        return a.replaceAll("\\+-", "-");
    }


    private void clear() {
        poly.clear();
    }

    private void sort() {
        poly.sort(comp);
    }

    private static Comparator<Monom> comp = new Monom_Comperator();

    public int get_size() {
        return poly.size();
    }

    private void fixzero() {
        Iterator<Monom> runner = this.iteretor();
        while (runner.hasNext()) {
            Monom m1 = runner.next();
            if (m1.get_coefficient() == 0) {
                runner.remove();
            }
        }
    }
}