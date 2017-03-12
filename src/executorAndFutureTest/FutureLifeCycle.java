package executorAndFutureTest;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * futureTask.cancel(boolean ifInterrupt)方法试图取消任务进行。其中boolean变量决定是否发送中断给正在执行的任务。
 * Callable接口本身无法接受中断，call本身也无法处理中断。处理方法：利用Thread.currentThread().interrupted()判断。
 * 在本例中，cancel()调用后，已经在执行的线程会继续执行完毕，但是一旦调用get()方法就会抛出CancellationException，
 * 而且get()方法默认抛出的两个异常都与这个异常无关，所以如果不特意catch这个异常，调用cancel()的线程会直接报错终止运行。
 * 对没有开始的任务，cancel()后调用get()也会出现同样的结果。
 * @author Caonuan
 *
 */
public class FutureLifeCycle {
	public static void main(String[] args) {
		Callable<String> c = new Callable<String>() {
			@Override
			public String call() throws Exception {
				long ct = System.currentTimeMillis();
				long ct2 = System.currentTimeMillis();
				//运行到1s时输出一下。
				while (System.currentTimeMillis() - ct2 < 3000
						&& !Thread.currentThread().isInterrupted()) {
					if (System.currentTimeMillis() - ct > 1000) {
						ct = System.currentTimeMillis();
						System.out.println("1s");
					}
				}
				System.out.println("executed");
				return null;
			}
		};
		FutureTask<String> f = new FutureTask<>(c);
		Thread t = new Thread(f);
		t.start();
		System.out.println("run");
		//在运行到1.5秒时cancel() FutureTask
		try {
			Thread.currentThread();
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("cancel");
		//判断是否取消任务成功
		System.out.println("isCancelled:\t" + f.cancel(true));
		//尝试get()受到cancel的Future
		try {
			f.get();
		} catch (InterruptedException | ExecutionException
				| CancellationException e) {
			e.printStackTrace();
		}
		try {
			Thread.currentThread();
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		//测试没有开始的Future进程cancel()后会怎么样
		FutureTask<String> f2 = new FutureTask<>(new Callable<String>() {
			@Override
			public String call() throws Exception {
				return "success";
			}
		});
		f2.cancel(false);
		System.out.println(
				"is the un-started thread cancelled?:\t" + f2.isCancelled());
		try {
			System.out.println(f2.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		System.out.println("program still running to the end.");
	}
}