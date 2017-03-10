package concurrentContainerTest;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Vector;

/**
 * 集合捕获ConcurrentModificationException异常
 * 容器在迭代过程中如果不考虑并发修改（即在迭代过程中容器内容被删除或增加），表现出的行为为ConcurrentModificationException异常
 * ConcurrentModificationException是“善意的提醒”，remove()语句最终将会被成功执行。\n
 * Vector的快速失败不同于HashMap，原因在于next()和hasNext()的判断机制不一样。看源码（jdk1.7)可以得知。
 *
 * @author Caonuan
 *
 */

public class VectorConcurrentModificationExceptionTest {
	public static void main(String[] args) {
		Vector<Integer> v = new Vector<Integer>();
		for (int i = 0; i < 5; i++)
			v.add(new Integer(i));
		v.remove(0);
		System.out.println("v.get(0):" + v.get(0));
		sys(v);
		// 测试foreach遍历时添加元素会不会报错
		System.out.println("测试foreach遍历时添加元素会不会报错");
		try {
			for (Integer integer : v) {
				if (integer == 4)
					v.add(5);
			}
		} catch (ConcurrentModificationException e) {
			System.out.println("add in foreach ConcurrentModificationException");
		}
		sys(v);
		// 测试foreach遍历时删除元素会不会报错
		System.out.println(" 测试foreach遍历时删除元素会不会报错");
		try {
			for (Integer integer : v) {
				if (integer == 5)
					v.remove(4);// 移除5
			}
		} catch (ConcurrentModificationException e) {
			System.out.println("remove in foreach ConcurrentModificationException");
		}
		sys(v);
		// 测试iterator遍历时到达iterator最后时删除元素会不会报错
		System.out.println("测试iterator遍历时到达iterator最后时删除元素会不会报错");
		try {
			Iterator<Integer> it = v.iterator();
			while (it.hasNext()) {
				if (it.next() == 4);
					v.remove(3);
			}
		} catch (ConcurrentModificationException e) {
			System.out.println("remove in iterator ConcurrentModificationException");
		}
		sys(v);
	}

	public static void sys(Vector<Integer> v) {
		for (Integer integer : v) {
			System.out.print(integer + "\t");
		}
		System.out.println();
	}
}
