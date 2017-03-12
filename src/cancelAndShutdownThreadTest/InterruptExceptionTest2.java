package cancelAndShutdownThreadTest;

/**
 * 测试单独调用一个interrupt()方法的作用
 * 没用
 * @author Caonuan
 *
 */
public class InterruptExceptionTest2 {

	public static void main(String[] args) {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					System.out.println(i);
					if (i == 5)
						Thread.currentThread().interrupt();
				}
			}
		});
		t.start();
	}
}
