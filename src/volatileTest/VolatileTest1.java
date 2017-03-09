package volatileTest;
/**
 * volatile不保证复合操作的原子性。只有单纯的get(),set()方法是原子的。
 * @author Caonuan
 *
 */
public class VolatileTest1 {
	static volatile int a=0;
	static public void addOne(){
		a+=1;
	}
	public static void main(String[] args) {
		for(int i=0;i<1000;i++){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					VolatileTest1.addOne();
				}
			}).start();;
		}
		System.out.println(VolatileTest1.a);
	}
}
