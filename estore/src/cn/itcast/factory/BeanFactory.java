package cn.itcast.factory;

import java.util.ArrayList;
import java.util.ResourceBundle;

import cn.itcast.dao.GoodDao;

public class BeanFactory {

	public static <T>T getBean(String inter) {
		System.out.println("工厂类反射实例化对象启动。。。。。。");
		//专门读取properties文件
		ResourceBundle bundle = ResourceBundle.getBundle("bean");
		//获取类全名   
		String className = bundle.getString(inter);
		//先获取当前类的Class
		try {
			//泛型的优化：Class对象，可以设置泛型
			Class<T> clazz = (Class<T>) Class.forName(className);
			//创建字节码对应的对象实例
			return clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
