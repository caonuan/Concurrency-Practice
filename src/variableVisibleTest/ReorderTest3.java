package variableVisibleTest;

/**
 * 重排序测试3
 * 成功
 * @author Caonuan
 *
 */
public class ReorderTest3 {
	static int x = 0, y = 0;
	static int a = 0, b = 0;

	public static void main(String[] args) throws InterruptedException {
		for (;;) {
			Thread one = new Thread(new Runnable() {
				@Override
				public void run() {
					a = 1;
					x = b;
				}
			});

			Thread other = new Thread(new Runnable() {
				@Override
				public void run() {
					b = 1;
					y = a;
				}
			});
			one.start();
			other.start();
			one.join();
			other.join();
			// System.out.println("(" + x + "," + y + ")");
			if (x == 0 && y == 0)
				System.out.println(x + "\t" + y);

			x = 0;
			y = 0;
			a = 0;
			b = 0;
		}
	}
}