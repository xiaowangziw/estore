package cn.itcast.dao;

import java.util.List;

import cn.itcast.domain.PCD;

public interface PCDDao {

	/**
	 * 获取三级联动的方法
	 * @param pid
	 * @return
	 */
	List<PCD> getData(String pid);

}
