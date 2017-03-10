package atomicClassTest;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 将AtomicInteger的原子自增操作与int变量的synchroinzed自增操作时间比较，看谁比较快
 * 结论：AtomicInteger.incrementAndGet()会比synchronized在外面慢，比synchronized在循环里快
 * 原因：AtomicIngeger.incrementAndGet()实际上是循环执行compareAndSet(expect,newValue)，
 * 会比每次获取锁再自增快，但compareAndSet()可能失败，所以比一次获取synchronized慢
 * 
 * @author Caonuan
 *
 */
public class AtomicIntVelocityTest {

	public static void main(String[] args) {
		Long sbt = System.nanoTime();
		Thread thread3 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 1000000; i++)
					IntTest.increment();
			}
		});
		Thread thread4 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 1000000; i++)
					IntTest.increment();
			}
		});
		thread3.start();
		thread4.start();
		try {
			thread3.join();
			thread4.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Long set = System.nanoTime();
		System.out.println("synchronized在循环里面：\t" + (set - sbt) + "\t" + IntTest.i);
		Long sbt_2 = System.nanoTime();
		Thread thread5 = new Thread(new Runnable() {

			@Override
			public void run() {
				IntTest.increment_2();
			}
		});
		Thread thread6 = new Thread(new Runnable() {

			@Override
			public void run() {
				IntTest.increment_2();
			}
		});
		thread5.start();
		thread6.start();
		try {
			thread5.join();
			thread6.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		Long set_2 = System.nanoTime();
		System.out.println(
				"synchronized在循环外面：\t" + (set_2 - sbt_2) + "\t" + IntTest.j);
		Long abt = System.nanoTime();
		AtomicInteger ai = new AtomicInteger(0);
		Thread thread1 = new AtomicIntTest(ai);
		Thread thread2 = new AtomicIntTest(ai);
		thread1.start();
		thread2.start();
		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Long aet = System.nanoTime();
		System.out.println("AtomicIngeter：\t\t" + (aet - abt) + "\t" + ai);

	}

	static class AtomicIntTest extends Thread {
		AtomicInteger ai;

		public AtomicIntTest(AtomicInteger ai) {
			super();
			this.ai = ai;
		}

		@Override
		public void run() {
			for (int i = 0; i < 1000000; i++)
				ai.incrementAndGet();
		}
	}

	static class IntTest {
		static int i = 0;
		static int j = 0;

		static synchronized public void increment() {
			i++;
		}

		static synchronized public void increment_2() {
			for (int i = 0; i < 1000000; i++)
				j++;
		}
	}
}
