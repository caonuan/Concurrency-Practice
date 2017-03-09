package variableVisibleTest;

/**
 * 内部类测试，内部类可自由访问外部类成员，变量名一样时，各自持有自己的该变量
 * 多态中，Super s=new Son();，s的重写方法调用是Son的方法，s的Super、Son中同名属性调用时是Super的属性
 * @author Caonuan
 *
 */
public class ThisEscapeTest1 {
	static int i = 1;
	static {
		System.out.println(i);
	}
	{
		System.out.println(i);
		
	}

	static class Test {
		static int i;
		int j=1;
		static {
			System.out.println(i);
		}
		{
			System.out.println(i);
		}

	}

	public static void main(String[] args) {
		Test.i=2;
		new ThisEscapeTest1();
		System.out.println(Test.i);
		Test t= new Test2();
		System.out.println(t.j);
	}

	static class Test2 extends Test{
		static int j=2;
	}
}
