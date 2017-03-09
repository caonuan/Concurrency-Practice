package multiThreadTest;

import java.util.LinkedList;

import org.junit.Test;

public class Test1 {

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

	@Test
	public void stringTest() {
		String s = "akjdsnfkjgnwkjergkj<img alt=\"\" src=\"pblog/userfiles/images/IMG_1406.jpg\"/></p>";
		int searchIndex = 0;
		do {
			searchIndex = s.indexOf("<img", searchIndex);
			if (searchIndex != -1) {
				s = s.substring(0, searchIndex + 4) + " style=\"max-width:100%;\""
						+ s.substring(searchIndex + 4, s.length());
				//System.out.println(s);
			}
			searchIndex += 1;
		} while (searchIndex != 0);
		// System.out.println(searchIndex);
		s=s.replaceAll("pblog/userfiles", "/pblog/userfiles");
		System.out.println(s);
	}
}
