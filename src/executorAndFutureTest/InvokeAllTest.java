package executorAndFutureTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * ExecutorService的.invokeAll(Collection<? extends Callable<String>> tasks, long
 * timeout, TimeUnit unit)方法。
 * 可以将一个callable集合全部开启，然后返回一个相同的future集合。第二个参数为任务的总运行时间。超过这个时间还没执行完的任务会被cancel。
 * 
 * @author Caonuan
 *
 */
public class InvokeAllTest {
	public static void main(String[] args) {
		List<Callable<String>> taskList = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			final int ii = i;
			taskList.add(new Callable<String>() {
				@Override
				public String call() throws Exception {
					Thread.currentThread().sleep(200 * ii);
					return (new String() + ii);
				}
			});
		}
		ExecutorService e = Executors.newCachedThreadPool();
		List<Future<String>> fs = null;
		try {
			fs = e.invokeAll(taskList, 1000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		for (Future<String> f : fs) {
			try {
				System.out.println(f.get());
			} catch (InterruptedException e1) {
				System.out.println("Interrupted");
			} catch (ExecutionException e1) {
				System.out.println("executionException");
			} catch (CancellationException e1) {
				System.out.println("cancellationException");
			}
		}
		e.shutdown();
	}

	@Test
	public void exceptionTest() {
		try {
			throw new NullPointerException("123");
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getStackTrace());
			System.out.println(e.getCause());
			System.out.println(e.toString());
		}
	}
}
