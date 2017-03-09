package synchronizedTest;

/**
 * 对于一个静态属性（SC.sA),SC.sA锁和sC.sA锁（sC是SC的实例）是不同的锁，即使他们代表的是同一个东西。
 * 同样，SC.class锁和sC锁也不能保证sA的线程安全
 * @author Caonuan
 *
 */
public class StaticSynchronized extends Thread {
	private static SC sC = new SC();
	private boolean ifLockClass = false;
	//静态内部类
	static class SC {
		static Integer sA = 0;
	}
	//构造线程实例时决定是锁类还是锁实例
	public StaticSynchronized(boolean ifLockClass) {
		this.ifLockClass = ifLockClass;
	}

	@Override
	public void run() {
		//另一个测试：synchronized (ifLockClass ? Class1.class : class1) {
		synchronized (ifLockClass ? SC.sA : sC.sA) {
			for (int i = 0; i < 10000000; i++) {
				if (ifLockClass) {
					SC.sA++;
				} else {
					sC.sA++;
				}
			}
		}
	}

	public static void sys() {
		System.out.println(SC.sA);
	}

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new StaticSynchronized(true);
		Thread t2 = new StaticSynchronized(false);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		sys();
	}
}
