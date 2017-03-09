package multiThreadTest;

import org.junit.Test;

public class TimeTest {
	public static void main(String[] args) {
		int[][] array=new int[1000][1000];
		for(int i=0;i<1000;i++){
			for(int j=0;j<1000;j++){
				array[i][j]=(int) (Math.random()*1000);
			}
		}
		long beginTime=System.currentTimeMillis();
		for(int i=0;i<1000;i++){
			for(int j=0;j<1000;j++){
				array[i][j]+=1;
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println(endTime-beginTime);
	}
	
	@Test
	public void maxInteger(){
		System.out.println(Integer.MAX_VALUE);
	}
}
