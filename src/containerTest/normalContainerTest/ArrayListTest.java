package containerTest.normalContainerTest;

import java.util.LinkedList;
/**
 * 看样子是用来测试list的线程不安全性的
 * @author Caonuan
 *
 */
public class ArrayListTest {

	public static void main(String[] args) {
		LinkedList list = new LinkedList<Integer>();
		Test2 test1 = new Test2(list);
		Test2 test2 = new Test2(list);
		test1.start();
		test2.start();
	}

	static class Test2 extends Thread {
		private LinkedList<Integer> list;

		public Test2(LinkedList<Integer> list) {
			this.list = list;
		}

		@Override
		public void run() {
			for (int i = 0; i < 10; i++) {
				list.addFirst(Math.round(10));
				System.out.println(this.getName() + ":" + list.size());
			}
		}
	}
}
