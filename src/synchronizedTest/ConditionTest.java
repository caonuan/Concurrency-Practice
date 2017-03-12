package synchronizedTest;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

/**
 * Condition测试。
 * 模拟生产者-消费者模式
 * @author Caonuan
 * @param <T> 泛型
 */
public class ConditionTest<T> {
	Lock lock = new ReentrantLock();
	Condition rc = lock.newCondition(); //读condition
	Condition wc = lock.newCondition(); //写condition
	//T[] ss=new T[3];
	T[] ss = (T[]) new Object[3]; //缓冲池
	int count = 0; //缓冲池中个数
	int rptr = 0, wptr = 0; //读指针，写指针

	public void put(T s) {
		lock.lock();
		while (count == ss.length) { //队满
			try {
				System.out.println("enter into wc.await()");
				wc.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ss[wptr == ss.length ? 0 : wptr++] = s; //写入
		count++;
		rc.signal(); //唤醒读线程
		lock.unlock();
	}

	public T get() {
		lock.lock();
		while (count == 0) { //队空
			try {
				rc.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		T t = ss[rptr == ss.length ? 0 : rptr++]; //取出
		count--;
		wc.signal();	//唤醒写线程。当写线程进入等待，没有这行代码则写线程永远不会被唤醒
		lock.unlock();
		genericTest();	//泛型数组测试，与Condition测试无关
		return t;
	}

	public static void main(String[] args) {
		final ConditionTest<String> c = new ConditionTest<>();
		//生产者
		Thread wt = new Thread(new Runnable() {
			@Override
			public void run() {
				for (;;) {
					try {
						Thread.currentThread();
						Thread.sleep(1100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					c.put(new Long(System.currentTimeMillis()).toString());
				}
			}
		});
		//消费者
		Thread rt = new Thread(new Runnable() {
			@Override
			public void run() {
				for (;;) {
					try {
						Thread.currentThread();
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(c.get() + ";" + c.count);
				}
			}
		});
		//测试condition，设置count=ss.length，写线程直接进入等待，在注释掉读线程中的signal()，观察现象;
		c.count = 3;
		wt.start();
		//保证put()线程进入了等待
		try {
			Thread.currentThread();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		rt.start();
	}

	@Test
	/**
	 * 泛型测试
	 */
	public void stringTest() {
		Object[] ss = new Object[100];
		System.out.println(ss.length);

		ss[0] = new String("123");
		String s = (String) ss[0];
		System.out.println(s);
	}

	@Test
	/**
	 * 输出转换的数组
	 */
	public void genericTest() {
		T[] t = genericTranslate();
		for (T t2 : t) {
			System.out.print(t2.toString()+"\t");
		}
		System.out.println();
	}

	/**
	 * Object[]转换为String[]
	 */
	public T[] genericTranslate() {
		String[] sa = new String[ss.length];
		for (int i = 0; i < ss.length; i++) {
			sa[i] = String.valueOf(ss[i]);
		}
		return (T[]) sa;
	}

	/*@Test
	//泛型数组
	public void genericArray() {
		List<String>[] lsa = new List<String>[10]; // Not really allowed.  
		Object o = lsa;
		Object[] oa = (Object[]) o;
		List<Integer> li = new ArrayList<Integer>();
		li.add(new Integer(3));
		oa[1] = li; // Unsound, but passes run time store check  
		String s = lsa[1].get(0); // Run-time error: ClassCastException. 
	}*/
}
