package concurrentContainerTest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
/**
 * 生产者与消费者线程，一个2s生产一个，一个1s消费一个
 * @author Caonuan
 *
 */
public class BlockingQueueTest {
	static BlockingQueue<Integer> bq = new LinkedBlockingDeque<>(1);
	static int i = 0;

	public static void main(String[] args) {
		Thread t1 = new Thread() {
			@Override
			public void run() {
				try {
					for (;;){
						bq.put(new Integer(i++));
						this.sleep(2000);
					}
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		};
		Thread t2 = new Thread() {
			@Override
			public void run() {
				try {
					for (;;) {
						System.out.println(bq.take());
						this.sleep(1000);
					}
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		};

		t1.start();
		t2.start();
	}
}
