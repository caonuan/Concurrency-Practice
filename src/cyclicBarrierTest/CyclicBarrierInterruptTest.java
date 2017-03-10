package cyclicBarrierTest;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * cyclicBarrier其中一个被栅栏挡住的线程被interrupt()强制唤醒后会出现的结果。被中断的线程报错 interruptException
 * 别的线程报错 brokenBarrierException
 * @author Caonuan
 *
 */
public class CyclicBarrierInterruptTest {
	public static void main(String[] args) {
		final CyclicBarrier b = new CyclicBarrier(3);
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					b.await();
				} catch (BrokenBarrierException e) {
					System.out.println("t brokenBarrierException");
				} catch (InterruptedException e) {
					System.out.println("t interruptException");
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					b.await();
				} catch (BrokenBarrierException e) {
					System.out.println("t2 brokenBarrierException");
				} catch (InterruptedException e) {
					System.out.println("t2 interruptException");
				}
			}
		});
		t.start();
		t2.start();
		t.interrupt();
	}
}
