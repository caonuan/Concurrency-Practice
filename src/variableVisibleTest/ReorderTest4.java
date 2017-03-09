package variableVisibleTest;

/**
 * 重排序测试 成功
 * 
 * @author Caonuan
 *
 */
public class ReorderTest4 {
	static private int a = 0;
	static private boolean flag = false;

	public static void main(String[] args) throws InterruptedException {
		for (;;) {
			Thread t1 = new Thread(new Runnable() {
				@Override
				public void run() {
					a = 1;
					flag = true;
				}
			});
			Thread t2 = new Thread(new Runnable() {

				@Override
				public void run() {
					if (flag)
						if (a == 0)
							System.out.println(a);
				}
			});
			t1.start();
			t2.start();
			t1.join();
			t2.join();
			a = 0;
			flag = false;
		}
	}
}
