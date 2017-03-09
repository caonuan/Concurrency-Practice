package cyclicBarrierTest;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CyclicBarrierTest1 {
	static class Worker implements Callable<String>{
		CyclicBarrier barrier ;
		
		public Worker(CyclicBarrier barrier) {
			super();
			this.barrier = barrier;
		}

		@Override
		public String call() throws Exception {
			try {
				Thread.sleep((long) (Math.random()*10000));
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			try {
				System.out.println("当前等待线程数量："+barrier.getNumberWaiting());
				barrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
			return "success";
		}
	}
	
	public static void main(String[] args) {
		ExecutorService executors = Executors.newFixedThreadPool(4);
		CyclicBarrier barrier = new CyclicBarrier(4);
		Future<String> f =executors.submit(new Worker(barrier));
		executors.submit(new Worker(barrier));
		executors.submit(new Worker(barrier));
		executors.submit(new Worker(barrier));
		try {
			System.out.println(f.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		System.out.println("全部运行完成");
	}
	
}
