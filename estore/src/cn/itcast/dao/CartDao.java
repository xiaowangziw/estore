package cn.itcast.dao;

import java.sql.Connection;
import java.util.List;

import cn.itcast.domain.Cart;

public interface CartDao {

	/**
	 * 根据指定的用户和商品查询数据
	 * @param uid
	 * @param gid
	 * @return
	 */
	Cart findByUidAndGid(int uid, int gid);

	/**
	 * 添加的方法
	 * @param c
	 */
	void add(Cart c);

	/**
	 * 修改的方法
	 * @param cart
	 */
	void update(Cart cart);

	/**
	 * 查询指定用户的购物车商品数据
	 * @param uid
	 * @return
	 */
	List<Cart> findAll(int uid);
	/**
	 * 根据用户和商品信息，删除购物车数据
	 * @param gid
	 * @param uid
	 */
	void delete(int gid, int uid);

	/**
	 * 清空购物车的方法
	 * @param uid
	 * @param conn
	 */
	void clear(int uid, Connection conn);
	
}
