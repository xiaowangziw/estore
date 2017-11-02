package cn.itcast.listener;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cn.itcast.service.OrderService;
import cn.itcast.service.impl.OrderServiceImpl;

/**
 * @author 王嘉男
 *
 * @time 2017年6月25日
 * 设置一个监听器，在项目启动的时候，开启订单的扫描
 */
public class ScannerListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//设置定时器
		Timer timer = new Timer();
		//设置一个定时任务
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("==============扫描订单启动=============");
				//设置扫描订单
				OrderService orderService = new OrderServiceImpl();
				//这个方法需不需要参数，通过具体的sql语句操作确认，发现不需要参数
				orderService.scanner();
				System.out.println("==============扫描订单完成=============");
			}
		}, 0, 1000*60*30);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
