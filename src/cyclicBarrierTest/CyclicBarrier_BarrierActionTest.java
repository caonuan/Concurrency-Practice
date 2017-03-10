package cyclicBarrierTest;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * barrierAction线程，在CyclicBarrier构造方法中定义，当栅栏到达阈值时会先调用barrierAction线程
 * @author Caonuan
 *
 */
public class CyclicBarrier_BarrierActionTest {
	public static void main(String[] args) {
		Thread[] tl=new Thread[3];
		final CyclicBarrier b = new CyclicBarrier(3, new Runnable() {

			@Override
			public void run() {
				System.out.println("barrierAction");
			}
		});
		for (int i = 0; i < 3; i++) {
			final int ii = i;
			tl[i]=new Thread() {
				@Override
				public void run() {
					System.out.println(ii+"-before");
					try {
						b.await();
					} catch (InterruptedException | BrokenBarrierException e) {
					}
					System.out.println(ii+"-after");
					
				}
			};
			tl[i].start();
		}
	/*	try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		tl[1].interrupt();*/
	}
}
