package cn.itcast.service;

import java.util.List;

import cn.itcast.domain.Order;

public interface OrderService {

	/**
	 * 添加订单的方法
	 * @param o
	 */
	void addOrder(Order o);

	/**
	 * 查询指定用户的所有订单
	 * @param uid
	 * @return
	 */
	List<Order> findAll(int uid);

	/**
	 * 获取指定的订单信息
	 * @param oid
	 * @return
	 */
	Order findById(String oid);

	/**
	 * 扫描订单的方法
	 */
	void scanner();

}
