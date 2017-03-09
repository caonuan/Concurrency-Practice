package variableVisibleTest;

/**
 * 重排序测试
 * 失败
 * @author Caonuan
 *
 */
public class ReorderTest1 {

	static class Data {
		private boolean ready;
		private int number;
	}

	private static class ReaderThread extends Thread {
		private Data data;

		public ReaderThread(Data data) {
			this.data = data;
		}

		@Override
		public void run() {
			Long bt=System.currentTimeMillis();
			while (!data.ready&&(System.currentTimeMillis()-bt<=10)) {
				Thread.yield();
			}
			int i = data.number;
			if (i != 42)
				System.out.println(i);
		}
	}

	public static void main(String[] args) {
		for (;;) {
			Data data = new Data();
			new ReaderThread(data).start();
			data.number = 42;
			data.ready = true;
		}
	}
}
