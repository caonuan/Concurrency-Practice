package synchronizedTest;

/**
 * 线程在获取锁后别的线程是否可以访问该对象？ 结果:可以。而且其他线程如果获取该对象的锁直接操作会造成线程不安全
 * 其次：获得主类的锁和获得主类下属性的锁是两个事情。获得主类的锁不代表获得其下所有属性的锁
 * 当两个线程都尝试获取Test.class或者Test.a时才能保证线程安全
 * @author Caonuan
 *
 */
public class SynchronizedTest2 {
	public static void main(String[] args) throws InterruptedException {
		Thread thread1 = new Test();
		thread1.start();
		Thread.sleep(1);
		//尝试是否可以获取已经被Test线程获取锁的a
		System.out.println("thread1.start() for 1 second:"+Test.a);
		//此处可以进行三种尝试，不获取锁、获取a的锁、获取Test.class的锁
		synchronized (Test.class) {
			for (int i = 0; i < 1000; i++) {
				Test.a++;
			}
		}
		thread1.join();
		Thread.sleep(10);
		System.out.println(Test.a);
	}

	static class Test extends Thread {
		static Integer a = 0;

		@Override
		public void run() {
			//尝试获得Test.class的锁和Test.a的锁的区别
			synchronized (Test.class) {
				for (int i = 0; i < 10000000; i++) {
					a++;
				}
				System.out.println("the 10000000 times add finish:"+a);
			}
		}

	}
}
