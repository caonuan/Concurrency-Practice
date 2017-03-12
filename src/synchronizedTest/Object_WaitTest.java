package synchronizedTest;

/**
 * 测试Object.wait()
 * 线程先运行，然后thisTime对象被wait(),线程停止运行。在其他线程调用了thisTime.notify()后，继续获得执行机会
 * @author Caonuan
 *
 */
public class Object_WaitTest {
	static class TestClass extends Thread {
		public Long thisTime;

		@Override
		public void run() {
			thisTime = System.currentTimeMillis();
			for (int i = 0;i<4;) {
				if (System.currentTimeMillis() - thisTime >= 500) {
					i++;
					System.out.println(thisTime);
					thisTime = System.currentTimeMillis();
				}
				if (i == 2) {
					synchronized (thisTime) {
						try {
							System.out.println("thisTime into wait()");
							thisTime.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		TestClass t1 = new TestClass();
		t1.start();
		Thread.currentThread();
		Thread.sleep(2000);
		synchronized (t1.thisTime) {
			System.out.println("main thread notify() thisTime");
			t1.thisTime.notify();
		}
	}

}
