package volatileTest;

/**
 * 用synchronized模拟volatile的读写、自增
 * 
 * @author Caonuan
 *
 */
public class VolatileTest {
	class VolatileSimulation {
		int value;

		public synchronized void set(int i) {
			value = i;
		}

		public synchronized int get() {
			return value;
		}

		public void increasement() {
			int t = get();
			t++;
			set(t);
		}
	}
}
