package concurrentContainerTest;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Vector;
/**
 * 集合捕获ConcurrentModificationException异常
 * 不仅Vocter会在foreach遍历时通过remove()方法抛出此异常，hashmap也会出现相同情况
 * ConcurrentModificationException是“善意的提醒”，remove()语句最终将会被成功执行。
 * @author Caonuan
 *
 */

import org.junit.Test;

public class VectorTest {
	public static void main(String[] args) {
		Vector<Integer> v = new Vector<Integer>();
		for (int i = 0; i < 1000; i++)
			v.add(new Integer(i));
		v.remove(0);
		for (Integer integer : v) {
			v.remove(3);
		}
	}

	@Test
	public void hashMapTest() {
		HashMap hm = new HashMap<>();
		for (int i = 0; i < 1000; i++)
			hm.put(i, i);
		try {
			synchronized (hm) { // synchronized是保证多线程之间的线程安全性，并不会阻止ConcurrentModificationException错误
				for (Object i : hm.keySet()) {
					if ((int) i == 32)
						hm.remove(32);
				}
			}
		} catch (ConcurrentModificationException e) {
		}
		System.out.println(hm.get(32));
		Object[] i=  hm.values().toArray();
		System.out.println((int)i[1]);
	}
}
