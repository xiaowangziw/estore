package cn.itcast.service.impl;

import java.util.List;

import cn.itcast.dao.GoodDao;
import cn.itcast.dao.impl.GoodDaoImpl;
import cn.itcast.domain.Good;
import cn.itcast.factory.BeanFactory;
import cn.itcast.service.GoodService;

public class GoodServiceImpl implements GoodService {

	/**
	 * 场景：你入职新公司，原来的老员工，离职
	 * 
	 * 你要接手老员工的工作：GoodDaoImpl
	 * 
	 * 现在GoodDaoImpl，出问题了，GoodDaoImpl其中的注册都被老员工删除了
	 * 
	 * 必须放弃GoodDaoImpl，重新新建一个GoodDaoImpl2，代替GoodDaoImpl。
	 * 
	 * 问题：有多少个类使用到接口实现类，必须全部去修改 如果是200类？那就累死了
	 * 
	 * 程序员守则第一条：懒
	 * 1）程序员不喜欢写重复的代码
	 * 2）程序员不喜欢改重复的代码
	 * 
	 * 到底问题出现在哪里？
	 * new 关键字，在调用构造函数，构造函数调用，必须写清楚名称
	 * 
	 * 程序员自己创建对象，创建对象的权利，都在自己手里，
	 * 
	 * 优化：将创建对象的操作，交给程序（类）自己去完成
	 * 
	 * 1 使用BeanFactory getBean方法，创建对象，通过参数，指定创建对象的类型
	 * 2 使用配置文件将接口和实现类，对应关系描述清楚 （类似 web.xml）
	 * 3 创建对象的权利交给，BeanFactory，对对象的实例化的控制权，反转到了BeanFactory
	 * 
	 * IOC spring 框架， 控制反转
	 * 
	 * 
	 * 
	 * */
	//private GoodDao goodDao = new GoodDaoImpl2();
	private GoodDao goodDao = BeanFactory.getBean("GoodDao");
	
	@Override
	public List<Good> findAll() {
		return goodDao.findAll();
	}

	@Override
	public Good findById(int gid) {
		
		return goodDao.findById(gid);
	}

	@Override
	public void add(Good g) {
		goodDao.add(g);
	}

}
