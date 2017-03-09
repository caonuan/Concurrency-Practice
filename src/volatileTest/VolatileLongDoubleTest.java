package volatileTest;

import org.junit.Test;

/**
 * 验证非volatile的long和double的多线程无最低安全性 
 * 结果：在x86下是无最低安全性的，在64位系统下long和double默认就是一个64位的
 * @author Caonuan
 *
 */
public class VolatileLongDoubleTest {
	static long d = 1l;
	static long t = 1l;
	static Thread t3 = new rd();
	static Thread t1 = new wt1();
	static Thread t2 = new wt2();

	static class rd extends Thread {
		@Override
		public void run() {
			for (;;) {
				t = d;
				if (t != 1l && t != -1l){
					System.out.println(Long.toBinaryString(t));
					t1.stop();
					t2.stop();
					break;
				}
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
		t3.start();
		t1.start();
		t2.start();
	}

	@Test
	public void test() {
		System.out.println("1l:"+Long.toBinaryString(1l));
		System.out.println("-1l:"+Long.toBinaryString(-1l));
		System.out.println("0l:"+Long.toBinaryString(0l));
		System.out.println("-4294967295l:"+Long.toBinaryString(-4294967296l));
		System.out.println("4294967295l:"+Long.toBinaryString(4294967295l));
	}
}
