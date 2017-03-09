package variableVisibleTest;
/**
 * 私有构造方法的作用
 * 工厂类、单例模式
 * @author Caonuan
 *
 */
public class PrivateConstructorTest {
	
	public static void main(String[] args) {
		TestClass t = TestClass.creat();
		
	}
}
class TestClass{
	private TestClass(){
		System.out.println("private constructor");
	}
	public static TestClass creat(){
		return new TestClass();
	}
}