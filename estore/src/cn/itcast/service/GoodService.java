package cn.itcast.service;

import java.util.List;

import cn.itcast.domain.Good;

public interface GoodService {

	/**
	 * 查询所有的商品
	 * @return
	 */
	List<Good> findAll();

	/**
	 * 查询指定id的商品
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
