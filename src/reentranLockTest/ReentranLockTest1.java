package reentranLockTest;

import java.util.concurrent.locks.ReentrantLock;

public class ReentranLockTest1 {
	static class ReentranLockWorker extends Thread{
		private ReentrantLock lock;

		public ReentranLockWorker(ReentrantLock lock) {
			super();
			this.lock = lock;
		}
		
		@Override
		public void run() {
			lock.lock();
			try {
				sleep(1000);
				System.out.println(this.getName()+"work");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
			boolean getLock=false;
			while(getLock!=true){
				getLock= lock.tryLock();
			};
			//lock.lock();
			//lock.tryLock();
			try {
				sleep(1000);
				System.out.println(this.getName()+"rest");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}
	public static void main(String[] args) throws InterruptedException {
		//设置公平可重入锁
		ReentrantLock lock = new ReentrantLock(true);
		int N = 4;
		Thread[] threads = new Thread[N];
		for (int i =0;i<N;i++){
			threads[i]=new ReentranLockWorker(lock);
			threads[i].start();
			Thread.sleep(100);
		}
	}
}
