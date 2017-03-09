package concurrentContainerTest;

import java.util.concurrent.Semaphore;

public class SemaphoreTest {

	public static void main(String[] args) {
		Semaphore s=new Semaphore(1);
		s.release();
		System.out.println(s.availablePermits());
	}
}
