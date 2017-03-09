package executorAndFutureTest;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * FutureTask如何提前开始任务。通过新建一个Thread(FutureTask),再通过thread.start()开始任务。
 * 不能通过futureTask.run()方法，实测会阻塞到call()执行完毕才执行随后的内容。 get()方法可以反复调用。
 * 
 * @author Caonuan
 */
public class FuturePreLoad {
	public static void main(String[] args) {
		Callable<String> c = new Callable<String>() {
			@Override
			public String call() throws Exception {
				long ct = System.currentTimeMillis();
				long ct2 = System.currentTimeMillis();
				while (System.currentTimeMillis() - ct2 < 5000) {
					if (System.currentTimeMillis() - ct > 1000) {
						ct = System.currentTimeMillis();
						System.out.println("1s");
					}
				}
				return "success";
			}
		};
		FutureTask<String> f = new FutureTask<>(c);
		Thread t = new Thread(f);
		t.start();
		// f.run(); //不行，主线程会阻塞到call()执行完毕
		System.out.println("run");
		try {
			Thread.currentThread().sleep(2500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("get");
		try {
			System.out.println(f.get());
			System.out.println(f.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

	}
}
