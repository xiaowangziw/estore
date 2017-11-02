package cn.itcast.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.dao.OrderDao;
import cn.itcast.domain.Order;
import cn.itcast.domain.OrderItems;
import cn.itcast.utils.DBUtils;

public class OrderDaoImpl implements OrderDao {

	private QueryRunner qrr = new QueryRunner();
	@Override
	public void addOrder(Order o, Connection conn) {
		String sql = "insert into orders values(?,?,?,?,?,?)";
		
		List<Object> list = new ArrayList<>();
		
		list.add(o.getId());
		list.add(o.getUid());
		list.add(o.getTotalprice());
		
		list.add(o.getAddress());
		list.add(o.getStatus());
		list.add(o.getCreatetime());
		
		try {
			qrr.update(conn, sql, list.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("添加订单失败");
		}

	}

	@Override
	public void addOrderItems(List<OrderItems> oiList, Connection conn) {
		String sql = "insert into orderitems values(?,?,?)";
		for (OrderItems oi : oiList) {
			try {
				qrr.update(conn, sql, oi.getOid(),oi.getGid(),oi.getBuynum());
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("添加订单明细失败");
			}
		}
		

	}
	private QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
	@Override
	public List<Order> findAll(int uid) {
		String sql = "select * from orders where uid = ?";
		try {
			return qr.query(sql, new BeanListHandler<Order>(Order.class), uid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("查询指定用户订单数据失败");
		}
	}

	@Override
	public Order findById(String oid) {
		String sql = "select * from orders where id = ?";
		try {
			return qr.query(sql, new BeanHandler<Order>(Order.class), oid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("查询指定订单数据失败");
		}

	}

	@Override
	public List<OrderItems> findOrderItemsByOid(String oid) {
		String sql = "select * from orderitems where oid = ?";
		try {
			return qr.query(sql, new BeanListHandler<OrderItems>(OrderItems.class), oid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("查询指定订单明细数据失败");
		}
	}

	@Override
	public List<Order> findByStatus(int i) {
		String sql = "select * from orders where status = ?";
		try {
			return qr.query(sql, new BeanListHandler<Order>(Order.class), i);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("查询指定状态订单数据失败");
		}
	}

	@Override
	public void updateOrder(Order order) {
		String sql = "update orders set status = ? where id = ?";
		try {
			qr.update(sql, order.getStatus(),order.getId());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("修改指定状态订单失败");
		}
		
	}

}
