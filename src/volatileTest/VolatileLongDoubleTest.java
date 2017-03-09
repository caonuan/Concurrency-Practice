package volatileTest;

import org.junit.Test;

/**
 * 验证非volatile的long和long的多线程无最低安全性 
 * 失败
 * @author Caonuan
 *
 */
public class VolatileLongDoubleTest {
	static long d = 1l;
	static long r1;
	static long t = 1l;

	static class rd extends Thread {
		@Override
		public void run() {
			for (;;) {
				t = d;
				if (t != 1l && t != -1l)
					System.out.println(Long.toBinaryString(t));
			}
		}
	}

	static class wt2 extends Thread {
		@Override
		public void run() {
			for (;;)
				d = 1l;
		}
	}

	static class wt1 extends Thread {
		@Override
		public void run() {
			for (;;)
				d = -1l;
		}

	}

	public static void main(String[] args) throws InterruptedException {
		Thread t3 = new rd();
		Thread t1 = new wt1();
		Thread t2 = new wt2();
		t3.start();
		t1.start();
		t2.start();
	}

	@Test
	public void test() {
		System.out.println(Long.toBinaryString(1l));
		System.out.println(Long.toBinaryString(-1l));
		System.out.println(Long.toBinaryString(0l));
		System.out.println(Long.toBinaryString(-4294967296l));
		System.out.println(Long.toBinaryString(4294967295l));
	}
}
