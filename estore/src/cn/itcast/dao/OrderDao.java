package cn.itcast.dao;

import java.sql.Connection;
import java.util.List;

import cn.itcast.domain.Order;
import cn.itcast.domain.OrderItems;

public interface OrderDao {

	/**
	 * 添加订单的方法
	 * @param o
	 * @param conn
	 */
	void addOrder(Order o, Connection conn);

	/**
	 * 添加订单明细
	 * @param oiList
	 * @param conn
	 */
	void addOrderItems(List<OrderItems> oiList, Connection conn);

	/**
	 * 获取指定用户所有订单
	 * @param uid
	 * @return
	 */
	List<Order> findAll(int uid);

	/**
	 * 根据指定id，获取订单数据
	 * @param oid
	 * @return
	 */
	Order findById(String oid);

	/**
	 * 根据指定的id获取订单的明细
	 * @param oid
	 * @return
	 */
	List<OrderItems> findOrderItemsByOid(String oid);

	/**
	 * 根据状态获取订单
	 * @param i
	 * @return
	 */
	List<Order> findByStatus(int i);

	/**
	 * 修改订单状态
	 * @param order
	 */
	void updateOrder(Order order);

}
