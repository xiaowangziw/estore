package cn.itcast.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import cn.itcast.dao.CartDao;
import cn.itcast.dao.GoodDao;
import cn.itcast.dao.OrderDao;
import cn.itcast.dao.impl.CartDaoImpl;
import cn.itcast.dao.impl.GoodDaoImpl;
import cn.itcast.dao.impl.OrderDaoImpl;
import cn.itcast.domain.Good;
import cn.itcast.domain.Order;
import cn.itcast.domain.OrderItems;
import cn.itcast.service.OrderService;
import cn.itcast.utils.DBUtils;

public class OrderServiceImpl implements OrderService {

	private OrderDao orderDao = new OrderDaoImpl();
	private CartDao cartDao = new CartDaoImpl();
	@Override
	public void addOrder(Order o) {
		// 获取链接
		Connection conn = null;
		try {
			conn = DBUtils.getConnection();
			
			// 开启事务
			conn.setAutoCommit(false);
			// 添加订单
			orderDao.addOrder(o,conn);
			// 添加订单明细
			orderDao.addOrderItems(o.getOiList(),conn);
			// 清空购物车
			cartDao.clear(o.getUid(),conn);
			
			// 提交事务
			conn.commit();
			
		} catch (Exception e) {
			// 如果异常，回滚
			e.printStackTrace();
			try {
				if(conn != null){
					conn.rollback();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			//释放资源
			DbUtils.closeQuietly(conn);
		}
		

	}
	@Override
	public List<Order> findAll(int uid) {
		
		return orderDao.findAll(uid);
	}
	
	private GoodDao goodDao = new GoodDaoImpl();
	@Override
	public Order findById(String oid) {
		//先获取指定订单数据
		Order order = orderDao.findById(oid);
		//根据订单，获取其中明细数据orderItems
		List<OrderItems> oiList = orderDao.findOrderItemsByOid(oid);
		//根据指定的订单明细，获取相关的商品信息
		for (OrderItems oi : oiList) {
			Good good = goodDao.findById(oi.getGid());
			oi.setGood(good);
		}
		order.setOiList(oiList);
		return order;
	}
	@Override
	public void scanner() {
		//select * from orders where status = 1;
		//将所有未支付的订单，获取出来
		List<Order> oList = orderDao.findByStatus(1);
		for (Order order : oList) {
			//当前时间毫秒值 -订单创建时间的毫秒值，如果超过半个小时，设置过期
			if(new Date().getTime() - order.getCreatetime().getTime() >= 1000*60*30){
				order.setStatus(3);
				orderDao.updateOrder(order);
			}
		}
	}

}
