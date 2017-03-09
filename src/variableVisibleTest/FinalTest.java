package variableVisibleTest;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试final类是否可以修改属性
 * 可以
 * @author Caonuan
 *
 */
public class FinalTest {

	public static void main(String[] args) {
		final f1 f=new f1();
		f.setA(1);
		System.out.println(f.getA());
		
		final Map map = new HashMap<>();
		map.put(1, 1);
		System.out.println(map.get(1));
	}
	
	static class f1 {
		private int a;

		public int getA() {
			return a;
		}
		public void setA(int a) {
			this.a = a;
		}

	}
}
