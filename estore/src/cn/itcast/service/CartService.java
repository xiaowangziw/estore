package cn.itcast.service;

import java.util.List;

import cn.itcast.domain.Cart;

public interface CartService {

	
	/**
	 * 添加商品到购物车
	 * @param uid
	 * @param gid
	 */
	void add(int uid, int gid);

	/**
	 * 查询指定用户，所有的购物车中商品
	 * @param uid
	 * @return
	 */
	List<Cart> findAll(int uid);

	/**
	 * 修改购物车商品购买数量的方法
	 * @param c
	 */
	void update(Cart c);
	
	/**
	 * 删除购物车数据的方法
	 * @param gid
	 * @param uid
	 */
	void delete(int gid, int uid);

}
