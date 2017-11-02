package cn.itcast.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.dao.CartDao;
import cn.itcast.domain.Cart;
import cn.itcast.utils.DBUtils;

public class CartDaoImpl implements CartDao {

	private QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
	@Override
	public Cart findByUidAndGid(int uid, int gid) {
		String sql = "select * from cart where uid = ? and gid = ?";
		try {
			return qr.query(sql, new BeanHandler<Cart>(Cart.class), uid,gid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("查询用户的购物车商品数据失败");
		}
	}

	@Override
	public void add(Cart c) {
		String sql = "insert into cart values(?,?,?)";
		try {
			qr.update(sql, c.getUid(),c.getGid(),c.getBuynum());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("添加购物车商品数据失败");
		}

	}

	@Override
	public void update(Cart cart) {
		/**
		 * 修改数据，增加数据大小，直接设置一个确定的数据
		 * 修改数据，增加数据大小，设置，让sql语句执行，自己增长
		 * 
		 * 
		 * 在对数据库进行数据操作的时候，尽量使用，幂等操作
		 * 优酷：阿里ons系统讲解，幂等操作，沈询
		 * 
		 * 幂等操作： 无论这个sql语句执行多少次，结果都是一致的
		 * 
		 * */
		String sql = "update cart set buynum = ? where uid = ? and gid = ?";
		try {
			qr.update(sql, cart.getBuynum(),cart.getUid(),cart.getGid());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("修改购物车商品数据失败");
		}

	}

	@Override
	public List<Cart> findAll(int uid) {
		String sql = "select * from cart where uid = ?";
		try {
			return qr.query(sql, new BeanListHandler<Cart>(Cart.class), uid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("查看指定用户的购物车商品数据失败");
		}
	}
	@Override
	public void delete(int gid, int uid) {
		
		String sql = "delete from cart where gid = ? and uid = ?";
		try {
			qr.update(sql, gid,uid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("根据用户id和商品id删除购物车数据失败");
		}
		
	}
	private QueryRunner qrr = new QueryRunner();
	@Override
	public void clear(int uid, Connection conn) {
		String sql = "delete from cart where uid = ?";
		try {
			qrr.update(conn, sql, uid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("清空指定用户的购物车数据失败");
		}
		
	}
	
}
