package cyclicBarrierTest;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 线程复用
 * @author Caonuan
 *
 */
public class ThreadReuseTest {
	static class Worker implements Callable<Integer> {
		CyclicBarrier barrier;
		private int i;

		public Worker(CyclicBarrier barrier) {
			super();
			this.barrier = barrier;
		}

		@Override
		public Integer call() throws Exception {
			barrier.await();
			return ++i;
		}
	}

	public static void main(String[] args) throws InterruptedException, BrokenBarrierException, ExecutionException {
		ExecutorService executors = Executors.newFixedThreadPool(4);
		CyclicBarrier barrier = new CyclicBarrier(4);
		Worker worker1 = new Worker(barrier);
		Worker worker2 = new Worker(barrier);
		Worker worker3 = new Worker(barrier);
		for (int i = 0; i < 10; i++) {
			Future<Integer> result1= executors.submit(worker1);
			Future<Integer> result2= executors.submit(worker2);
			Future<Integer> result3= executors.submit(worker3);
			barrier.await();
			System.out.println(result1.get()+"\t"+result2.get()+"\t"+result3.get()+"\t");
		}
	}

}
