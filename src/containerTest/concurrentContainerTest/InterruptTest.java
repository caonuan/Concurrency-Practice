package containerTest.concurrentContainerTest;

/**
 * jdk 1.6:
 * public void interrupt()中断线程。
 * 1.如果当前线程没有中断它自己（这在任何情况下都是允许的），
 * 则该线程的 checkAccess 方法就会被调用，这可能抛出 SecurityException。
 * 2.如果线程在调用 Object 类的 wait()、wait(long) 或wait(long, int) 方法，或者该类的 join()、join(long)、
 * join(long, int)、sleep(long) 或 sleep(long, int) 方法过程中受阻，则其中断状态将被清除，
 * 它还将收到一个 InterruptedException。
 * 3.如果该线程在可中断的通道上的 I/O 操作中受阻，则该通道将被关闭，
 * 该线程的中断状态将被设置并且该线程将收到一个ClosedByInterruptException。
 * 4.如果该线程在一个 Selector中受阻，则该线程的中断状态将被设置，它将立即从选择操作返回，并可能带有一个非零值，就好像调用了选择器的 wakeup 方法一样。
 * 5.如果以前的条件都没有保存，则该线程的中断状态将被设置。 中断一个不处于活动状态的线程不需要任何作用。
 * 
 *  抛出： SecurityException - 如果当前线程无法修改该线程
 * @author Caonuan
 *
 */
public class InterruptTest {
	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					this.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for (;;) {
					System.out.println(System.currentTimeMillis());
					try {
						this.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		};
		t.start();
		Thread.sleep(1000);
		t.interrupt();
		
	}
}
