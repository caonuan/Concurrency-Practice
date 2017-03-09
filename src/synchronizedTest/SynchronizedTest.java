package synchronizedTest;

import org.junit.Test;

/**
 * synchronized简单测试，没什么意义
 * @author Caonuan
 *
 */
public class SynchronizedTest {
	static Data data;

	public static void main(String[] args) {
		data = new Data();
		Thread thread1 = new Test1(data);
		Thread thread2 = new Test1(data);
		thread1.start();
		thread2.start();
		Data data1=new Data();
		Thread thread3= new Test1(data1);
		thread3.start();
		
		for (int i = 0; i <= 2; i++) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			data.judge();
			System.out.println(data1.a);
		}

	}

	static class Test1 extends Thread {
		private Data data;

		public Test1(Data data) {
			super();
			this.data = data;
		}

		@Override
		public void run() {
			do {
				synchronized (data) {
					data.a++;
					data.b++;
				}
			} while (true);
		}
	}

	static class Data {
		int a;
		int b;

		public void judge() {
			synchronized (data) {
				System.out.println(
						"a=" + data.a + ",b=" + data.b + "a==b?:"+(data.a == data.b));
			}
		}
	}

	@Test
	/**
	 * Integer是值传递
	 */
	public void test1() {
		Integer i = 0;
		Integer b = i;
		i++;
		System.out.println(b+"\t"+i+"\t"+(i==b));
		b++;
		System.out.println(b+"\t"+i+"\t"+(i==b));
	}
}
