package executorAndFutureTest;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 而CompletionService的实现是维护一个保存Future对象的BlockingQueue。只有当这个Future对象状态是结束的时候，
 * 才会加入到这个Queue中，take()方法其实就是Producer-Consumer中的Consumer。它会从Queue中取出Future对象，
 * 如果Queue是空的，就会阻塞在那里，直到有完成的Future对象加入到Queue中。
 * 解决了Future.get()会阻塞到call()运行结束的问题
 * @author Caonuan
 *
 */
public class CompletionServiceTest {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(10);
		CompletionService<String> service = new ExecutorCompletionService<>(
				executor);
		for (int i = 0; i < 10; i++) {
			final int ii = i;
			service.submit(new Callable<String>() {

				@Override
				public String call() throws Exception {
					double random = Math.random();
					Thread.currentThread();
					Thread.sleep((int) (random * 1000));
					return "success:" + ii + ":" + random;
				}
			});
		}
		for (int i = 0; i < 10; i++) {
			try {
				System.out.println(service.take().get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		System.out.println("end");
		executor.shutdown();
	}
}
