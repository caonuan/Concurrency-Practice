package executorAndFutureTest;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.junit.Test;

/**
 * ExecutorService的shutdown()方法：executor在接收Runable时，将其注册为future保存，
 * future在线程池中不会自动销毁，需要调用executor.shutdown()才会销毁，程序才会正常结束。
 * future不在线程池中时，会随着封装的代码的结束而销毁。（另外发现单纯的FutureTask需要先调用run()之后才能调用get().
 * @author Caonuan
 *
 */
public class ExecutorServiceShutdownTest {
	public static void main(String[] args) {
		ExecutorService e1 = Executors.newFixedThreadPool(1);
		ExecutorService e2 = Executors.newFixedThreadPool(1);
		Future<?> f1 = e1.submit(new Runnable() {
			@Override
			public void run() {
			}
		});
		Future<String> f2 = e2.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				return "e2,end";
			}
		});
		try {
			System.out.println("e1 result:" + f1.get());
			System.out.println("e2 result:" + f2.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		System.out.println("e1shutdown?:" + e1.isShutdown());
		System.out.println("e2shutdown?:" + e2.isShutdown());
		System.out.println("begin shutdown the executors------");
		e1.shutdown();
		e2.shutdown();
		System.out.println("e1shutdown?:" + e1.isShutdown());
		System.out.println("e2shutdown?:" + e2.isShutdown());
	}
	@Test
	public void futureIfAutoShutdownTest(){
		FutureTask<String> f = new FutureTask<String>(new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(1000);
				return "f";
			}
		});
		f.run();
		try {
			System.out.println(f.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}
