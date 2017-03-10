package containerTest.concurrentContainerTest;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 利用FutureTask实现闭锁。get()会阻塞到call()函数运行结束。
 * FutureTask f 可以通过Thread t=new Thread(f);t.start();来提前开始计算
 * 下面代码中3s就会输出“1”
 * @author Caonuan
 *
 */
public class FutureTaskTest {

	public static void main(String[] args) {
		FutureTask<Integer> f = new FutureTask<>(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				Thread.currentThread().sleep(3000);
				return 1;
			}
		});
		Thread t=new Thread(f);
		t.start();
		try {
			//System.out.println(f2.get());
			Thread.currentThread().sleep(1500);
			System.out.println("begin get()");
			System.out.println(f.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
	}
}
