package cn.itcast.domain;

public class ExampleA {

	/**
	 * <T> :当前这个方法是一个泛型方法
	 * T ：返回值是什么类型我不知道，大概长这样
	 * T x ：传入的参数是什么类型我不知道，大概长这样
	 * */
	public <T>T aa(T x){
		System.out.println(x.getClass().getName());
		
		return x;
	}
	
	public static void main(String[] args) {
		ExampleA exampleA = new ExampleA();
		
		String aa = exampleA.aa(" ");
		Integer aa2 = exampleA.aa(5);
		Character aa3 = exampleA.aa('a');
		ExampleA aa4 = exampleA.aa(exampleA);
		
	}
}
