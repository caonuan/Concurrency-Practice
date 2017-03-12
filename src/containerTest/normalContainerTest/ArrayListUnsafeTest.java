package containerTest.normalContainerTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

/**
 * 验证ArrayList的线程不安全性：ArrayList<E>.add(E e)操作主要为elementData[size++] = e;
 * 可能出现如下情况：
 * 1.线程1调用elementDate[size]=e,做size++时读取了size值，线程2也调用了elementDate[size]=e,
 * 做size++时也读取了与线程1相同的size的值，size位置重复赋值，最后size=size+1 
 * 2.线程1做调用elementDate[size]后线程2调用elementDate[size]调用size++时，size已经被线程2设置为了size+1，因此最后size成为了size+3
 * 3.ArrayList的自动扩容受到影响
 * 
 * Collections.synchronnizedList(List)可获取线程安全的list
 * @author Caonuan
 *
 */
public class ArrayListUnsafeTest {
	public static void main(String[] args) throws InterruptedException {
		List<Integer> list = new ArrayList<>();
		// List<Integer> list = Collections.synchronizedList(new ArrayList<Integer>()); //线程安全的list
		AtomicInteger ai = new AtomicInteger(0);
		Thread thread1 = new WriteThread(list, ai);
		Thread thread2 = new WriteThread(list, ai);
		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();
		System.out.println(list.size());
		for (int i = 0; i < list.size(); i++) {
			System.out.print(i + "-" + list.get(i) + ",");
		}
	}

	static class WriteThread extends Thread {
		List<Integer> list;
		AtomicInteger ai = new AtomicInteger(0);

		public WriteThread(List<Integer> list, AtomicInteger ai) {
			super();
			this.list = list;
			this.ai = ai;
		}

		@Override
		public void run() {
			while (ai.get() < 100) {
				list.add(ai.getAndIncrement());
			}
		}
	}

	/**
	 * http://blog.csdn.net/jiaochunyu1992/article/details/51177373
	 */
	public class ArrayListInThread implements Runnable {
		List<String> list1 = new ArrayList<String>();// not thread safe
		// List<String> list1 = Collections.synchronizedList(new  ArrayList<String>());// thread safe

		@Override
		public void run() {
			try {
				Thread.sleep((int) (Math.random() * 2));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			list1.add(Thread.currentThread().getName());
		}

	}

	@Test
	public void tmain() throws InterruptedException {
		ThreadGroup group = new ThreadGroup("mygroup");
		ArrayListInThread t = new ArrayListInThread();
		for (int i = 0; i < 10000; i++) {
			Thread th = new Thread(group, t, String.valueOf(i));
			th.start();
		}

		while (group.activeCount() > 0) {
			Thread.sleep(10);
		}
		System.out.println();
		System.out.println(t.list1.size()); // it should be 10000 if thread safe
											// collection is used.
	}

	@Test
	public void arrayListAutoGrow() {
		int a = 10;
		for (int i = 0; i < 10; i++) {
			a = a + (a >> 1);
			System.out.print(a + ",");
		}
	}
}
