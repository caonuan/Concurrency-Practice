package containerTest.concurrentContainerTest;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * HashMap的快速失败： 1.利用iterator/foreach遍历HashMap的entrySet或keySet时，增加或删除元素都会引发快速失败
 * 2.iterator遍历到最后时，对hm进行删除或增加元素不会引发快速失败（不同于Vector）
 * 3.synchronized是保证多线程之间的线程安全性，并不会阻止ConcurrentModificationException错误
 * @author Caonuan
 *
 */
public class HashMapConcurrentModificationExceptionTest {
	public static void main(String[] args) {

		HashMap<Integer, Integer> hm = new HashMap<>();
		Iterator<Integer> it = null;
		for (int i = 0; i < 5; i++)
			hm.put(i, i);
		// 测试foreach遍历时删除元素会不会报错
		try {
			for (int i : hm.keySet()) {
				if (i == 2)
					hm.remove(2);
			}
		} catch (ConcurrentModificationException e) {
			System.out.println("remove in foreach by keySet makes exception");
		}
		try {
			for (Entry<Integer, Integer> entry : hm.entrySet()) {
				if (entry.getKey() == 3)
					hm.remove(3);
			}
		} catch (ConcurrentModificationException e) {
			System.out.println("remove in foreach by entrySet makes exception");
		}
		sys(hm);
		// 测试iterator中的快速失败
		try {
			// synchronized是保证多线程之间的线程安全性，并不会阻止ConcurrentModificationException错误
			synchronized (hm) {
				Iterator<Entry<Integer, Integer>> eit = hm.entrySet().iterator();
				while (eit.hasNext()) {
					Entry<Integer, Integer> aa = eit.next();
					// 当判断为aa.getKey() == 2时（it.next到最后时）不会报错
					if (aa.getKey() == 0)
						hm.remove(aa.getKey());
				}
			}

		} catch (ConcurrentModificationException e) {
			System.out.println("remove in iterator makes exception");
		}

		// 测试iterator遍历时添加元素会不会报错
		try {
			it = hm.keySet().iterator();
			while (it.hasNext()) {
				// 当判断为it.next() == 2 时（it.next到最后时）不会报错
				if (it.next() == 1)
					hm.put(5, 5);
			}
		} catch (ConcurrentModificationException e) {
			System.out.println("put in iterator makes exception");
		}
		sys(hm);
	}

	public static void sys(HashMap<Integer, Integer> hm) {
		Object[] i = hm.values().toArray();
		for (Object object : i) {
			System.out.print((int) object + "\t");
		}
		System.out.println();
	}
}
