package cn.itcast.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.dao.PCDDao;
import cn.itcast.domain.PCD;
import cn.itcast.utils.DBUtils;

public class PCDDaoImpl implements PCDDao {

	private QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
	
	@Override
	public List<PCD> getData(String pid) {
		String sql = "select * from province_city_district where pid = ?";
		try {
			return qr.query(sql, new BeanListHandler<PCD>(PCD.class), pid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("获取省市县数据失败");
		}
	}

}
