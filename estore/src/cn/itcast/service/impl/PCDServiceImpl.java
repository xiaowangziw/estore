package cn.itcast.service.impl;

import java.util.List;

import cn.itcast.dao.PCDDao;
import cn.itcast.dao.impl.PCDDaoImpl;
import cn.itcast.domain.PCD;
import cn.itcast.service.PCDService;

public class PCDServiceImpl implements PCDService {

	private PCDDao pcdDao = new PCDDaoImpl();
	@Override
	public List<PCD> getData(String pid) {
		
		return pcdDao.getData(pid);
	}

}
