package concurrentContainerTest;

import java.util.ConcurrentModificationException;
import java.util.Vector;

/**
 * 集合捕获ConcurrentModificationException异常
 * 容器在迭代过程中如果不考虑并发修改（即在迭代过程中容器内容被删除或增加），表现出的行为为ConcurrentModificationException异常
 * 不仅Vocter会在foreach遍历时通过remove()方法抛出此异常，hashmap也会出现相同情况
 * ConcurrentModificationException是“善意的提醒”，remove()语句最终将会被成功执行。
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
		// 测试遍历时添加元素会不会报错
		try {
			for (Integer integer : v) {
				if (integer == 4)
					v.add(5);
			}
		} catch (ConcurrentModificationException e) {
			System.out.println("add ConcurrentModificationException");
		}
		// 测试遍历时删除元素会不会报错
		try {
			for (Integer integer : v) {
				if (integer == 5)
					v.remove(4);//移除5
			}
		} catch (ConcurrentModificationException e) {
			System.out.println("remove ConcurrentModificationException");
		}
		for (Integer integer : v) {
			System.out.print(integer + "\t");
		}
	}

}
