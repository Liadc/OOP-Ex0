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

    /**
     * This function will calculate a variable X (double) using the polynom's values, and returns the answer as double.
     * @param x the variable to perform all monom's calculations on.
     * @return the calculated value answer.
     */
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

    /**
     * This function will receive another Polynom and add it to the current polynom.
     * @param p1 polynom to add to the current one.
     */
    @Override
    public void add(Polynom_able p1) {
        Iterator<Monom> pointer = p1.iteretor();
        while (pointer.hasNext()) {
            Monom p = pointer.next();
            this.add(p);
        }
        this.fixzero(); //never used, just to make sure.
    }

    /**
     * This function will receive a Monom and add it to the current polynom. if the power of the Monom is equal to another
     * monom inside the current polynom, it will add the coefficient, otherwise it will add it as another new Monom.
     * @param m1 Monom to add to the current polynom.
     */
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

    /**
     * This function will receive another polynom p1 and substract it from the current polynom.
     * (multiply by -1, then perform add method).
     * @param p1 polynom to substract
     */
    @Override
    public void substract(Polynom_able p1) {
        Polynom_able minus1 = new Polynom("-1");
        Polynom_able p2 = p1.copy();
        p2.multiply(minus1);
        this.add(p2);
    }

    /**
     * This function will receive another polynom and perform multiplication of the current one with the other one.
     * The method will change the current polynom to the result of the multiplication.
     * @param p1 polynom to multiply the current polynom with.
     */
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

    /**
     * This function will check whether the current polynom has any monoms inside it. returns True if it does,
     * false otherwise.
     * @return Boolean, true if current polynom has at least one monom, false otherwise.
     */
    @Override
    public boolean isZero() {
        // TODO Auto-generated method stub
        //this.fixzero();
        if (this.get_size() == 0) return true;
        return false;
    }

    /**
     * This function will generate a derivative from the current polynom, and returns the derivative polynom.
     * @return Polynom_able, the derivative of the current polynom.
     */
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

    /**
     * This function will receive two boundaries x1 and x0 (double), and an EPS, and finds the root of the current
     * polynom within the area between x0 and x1 with eps=epsilon error margin. Assuming x1>x0, and f(x0)*f(x1) <0.
     * @param x0 starting point. (double)
     * @param x1 end point. (double)
     * @param eps step (positive) value. the error margin accepted must be equals or less than this value.
     * @return the x value where f(x) is 0, with EPS (epsilon) error margin.
     */
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
        else throw new RuntimeException("f(x0)*f(x1) >0");
    }

    /**
     * This function will receive two boundaries x1 and x0 (double), and an EPS, and calculates the area between
     * the polynom to the X-axis (only positive area). assuming x1 > x0.
     * Compute Riemann's Integral over this Polynom starting from x0, till x1 using eps size steps,
     * see: https://en.wikipedia.org/wiki/Riemann_integral
     * @param x0 double, starting point.
     * @param x1 double, end point.
     * @param eps double, the size steps for each Riemann's sum rectangle.
     * @return the approximated area above the x-axis below this Polynom and between the [x0,x1] range.
     */
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

    /**
     * This function receives another polynom p1 and returns true iff the current polynom has the same Monoms as the current one.
     * @param p1 polynom to equals the current polynom to.
     * @return true iff the current polynom has the same Monom as the current one, false otherwise.
     */
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

    /**
     * This function will return an iterator of the current polynom, so we can iterate through the Monoms.
     * @return Iterator<Monom>, the iterator of the ArrayList.
     */
    @Override
    public Iterator<Monom> iteretor() {
        // TODO Auto-generated method stub
        return poly.iterator();
    }

    /**
     * Overriding toString function: will return a String representing the current polynom. in the form of a1*x^b1+a2*x^b2...
     * @return String, representing the current polynom. in the form of a1*x^b1+a2*x^b2...
     */
    @Override
    public String toString() {
        String a = "";
        Iterator<Monom> iterator = this.iteretor();
        while (iterator.hasNext()) {
            Monom b = iterator.next();
            a = a + b;
            if (iterator.hasNext())
                a += "+";
        }
        return a.replaceAll("\\+-", "-");
    }

    /**
     * This method will clear all monoms from the polynom.
     */
    private void clear() {
        poly.clear();
    }

    /**
     * This method will sort the polynom using Monom-Comperator class, starting with the highest power first.
     */
    private void sort() {
        poly.sort(comp);
    }

    private static Comparator<Monom> comp = new Monom_Comperator();

    /**
     * This function will return how many Monoms are currently inside the polynom.
     * @return Integer, the number of monoms inside the polynom.
     */
    public int get_size() {
        return poly.size();
    }

    /**
     * This function will search for a monom which is equal to 0 and removes it from the polynom. (0, 0*x, 0*x^4 ...)
     */
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