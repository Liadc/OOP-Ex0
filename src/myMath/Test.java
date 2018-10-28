package myMath;


import java.util.Scanner;

class Test {
	public static void main(String[] args) {
		
		//TODO: CASE OF -x when entering a polynom
		System.out.println("Hello, Please choose 2 Polynom's (Ex: a_1*x^b_1 + a_2*x^b_2 ... a_n*x^b_n - \nFor an empty Polynom please enter 0");
		Scanner sc = new Scanner(System.in);
		Polynom_able pol1;
		Polynom_able pol2;
		System.out.println("Polynom num 1: ");
		String polstr = sc.nextLine();
		pol1 = new Polynom(polstr);
		System.out.println("Polynom num 2: ");
		polstr = sc.nextLine();
		pol2 = new Polynom(polstr);
		System.out.println("What whould u like to do with p1 & p2?");
		String choice = "";
		while(!choice.equals("Exit")) {
			System.out.println("1 - add - Adds to Pol1 = Pol1 + Pol2");
			System.out.println("2 - substract - substract From Pol1, Pol1 = Pol1 - Pol2");
			System.out.println("3 - multiply - Multiply Pol1 with Pol2, Pol1 = Pol1 * Pol2");
			System.out.println("4 - equals - Return true iff Pol1 equals to Pol2.");
			System.out.println("5 - isZero() - return true if Pol1 is equal to Zero");
			System.out.println("6 - root by giving x0,x1,eps where's 0x<01 return the cut's x of the function with x-axis. Ex: 1,2,3");
			System.out.println("7 - Polynom_able derivative()");
			System.out.println("8 - area(double x0,double x1, double eps)");
			System.out.println("9 - TESTMODE - Polynom_able Kitzon(yes or no, if yes return vaule(one of them) if not return false - TESTMODE");
			System.out.println("Exit - for Exiting the Program");
			System.out.println("Current Pol1: " + pol1);
			System.out.println("Current Pol2: " + pol2);
			choice = sc.nextLine();
			switch (choice) {
			case "1":
				System.out.println("Add");
				pol1.add(pol2);
				break;
			case "2":
				System.out.println("Substract");
				pol1.substract(pol2);
				System.out.println(pol1);
				break;
			case "3":
				System.out.println("Multiply");
				pol1.multiply(pol2);
				System.out.println(pol1);
				break;
			case "4":
				System.out.println("Equals?:");
				System.out.println(pol1.equals(pol2));
				break;
			case "5":
				System.out.println("isZero");
				System.out.println(pol1.isZero());
				break;
				
			case "6":
				System.out.println("Root - Please choose x0,x1,eps(Ex: 1,2,3) Make sure f(x0)*f(x1) < 0");
				String paramroot = sc.nextLine();
				String paramsroot[] = paramroot.split(",");
				double[] inputsroot = {Double.parseDouble(paramsroot[0]),Double.parseDouble(paramsroot[1]),Double.parseDouble(paramsroot[2])};
				System.out.println("Root of Pol1:");
				System.out.println(pol1.root(inputsroot[0], inputsroot[1], inputsroot[2]));
				break;
				
				
			case "7":
				System.out.println("The derivative of Pol1 is:");
				System.out.println(pol1.copy().derivative());
				break;
				
			case "8":
				System.out.println("Area - Please choose x0,x1,eps(Ex: 1,2,3)");
				String paramarea = sc.nextLine();
				String paramsarea[] = paramarea.split(",");
				double[] inputsarea = {Double.parseDouble(paramsarea[0]),Double.parseDouble(paramsarea[1]),Double.parseDouble(paramsarea[2])};
				System.out.println("Roots of Pol1:");
				System.out.println(pol1.area(inputsarea[0], inputsarea[1], inputsarea[2]));
				break;
				
			case "9":
				System.out.println("Kitzon - Please choose x0,x1(Ex: 1,2)");
				String param = sc.nextLine();
				String params[] = param.split(",");
				double[] inputs = {Double.parseDouble(params[0]),Double.parseDouble(params[1]),0.01};
				double xcut = pol1.copy().derivative().root(inputs[0], inputs[1], inputs[2]);
				System.out.println("around the point:  " + "(" + xcut + "," + pol1.f(xcut) + ") might have Kitzon");
				break;
				
			case "Exit":
				choice = "Exit";
			default:
				break;
			}
		}
		System.out.println("Good Bye..");
		
		sc.close();
	}
}
