package cn.itcast.dao;

import java.util.List;

import cn.itcast.domain.Good;

public interface GoodDao {

	/**
	 * 查询所有的商品的方法
	 * @return
	 */
	List<Good> findAll();

	/**
	 * 查询指定商品的方法
	 * @param gid
	 * @return
	 */
	Good findById(int gid);

	/**
	 * 商品上传的方法
	 * @param g
	 */
	void add(Good g);

}

