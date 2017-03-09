package variableVisibleTest;

/**
 * 重排序测试2
 * 失败
 * @author Caonuan
 *
 */
public class ReorderTest2 {
	static class ReorderExample {
		private int a = 0;
		private boolean flag = false;

		public void writer() {
			a = 1; // 1
			flag = true; // 2
		}

		public void reader() {
			if (flag) { // 3
				if (a != 1) // 4
					System.out.println(a);
			}
		}
	}

	static class WriteThread extends Thread {
		private ReorderExample re;

		public WriteThread(ReorderExample re) {
			this.re = re;
		}

		@Override
		public void run() {
			re.writer();
		}
	}

	static class ReadThread extends Thread {
		private ReorderExample re;

		public ReadThread(ReorderExample re) {
			this.re = re;
		}

		@Override
		public void run() {
			re.reader();
		}
	}

	public static void main(String[] args) {
		for (;;) {
			ReorderExample re = new ReorderExample();
			Thread wt=new WriteThread(re);
			Thread rt=new ReadThread(re);
			wt.start();
			rt.start();
		}
	}
}
