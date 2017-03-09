package volatileTest;

/**
 * 验证long和double在多线程中没有最低安全性。 这份代码时网上的，是错误的代码。可能出现1！=-1&&-1！=1
 * 
 * @author Caonuan
 *
 */
public class VolatileLongDoubleTest2 {
	protected long l = -1l;

	public static void main(String[] args) {
		System.out.println(toBinary(-1l));
		System.out.println(toBinary(1l));
		VolatileLongDoubleTest2 t = new VolatileLongDoubleTest2();
		Worker w1 = new Worker(t);
		Worker2 w2 = new Worker2(t);
		w1.setDaemon(true);
		w2.setDaemon(true);
		w1.start();
		w2.start();
		while (true) {
			if (t.l != -1l && t.l != 1l) {
				System.out.println(toBinary(t.l));
				System.out.println("l的写不是原子操作");
				break;
			}
		}
	}

	private static String toBinary(long l) {
		StringBuilder sb = new StringBuilder(Long.toBinaryString(l));
		while (sb.length() < 64) {
			sb.insert(0, "0");
		}
		return sb.toString();
	}
}

class Worker extends Thread {

	public Worker(VolatileLongDoubleTest2 t) {
		this.t = t;
	}

	private VolatileLongDoubleTest2 t;

	@Override
	public void run() {
		while (true) {
			t.l = -1l;
		}
	}
}

class Worker2 extends Thread {

	public Worker2(VolatileLongDoubleTest2 t) {
		this.t = t;
	}

	private VolatileLongDoubleTest2 t;

	@Override
	public void run() {
		while (true) {
			t.l = 1l;
		}
	}
}
