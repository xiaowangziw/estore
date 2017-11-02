package cn.itcast.service;

import java.util.List;

import cn.itcast.domain.PCD;

public interface PCDService {

	/**
	 * 获取三级联动数据的方法
	 * @param pid
	 * @return
	 */
	List<PCD> getData(String pid);

}
