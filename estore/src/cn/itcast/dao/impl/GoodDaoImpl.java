package cn.itcast.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.dao.GoodDao;
import cn.itcast.domain.Good;
import cn.itcast.utils.DBUtils;

public class GoodDaoImpl implements GoodDao {

	private QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
	@Override
	public List<Good> findAll() {
		System.out.println("GoodDaoImpl....执行.....");
		String sql = "select * from goods";
		try {
			return qr.query(sql, new BeanListHandler<Good>(Good.class));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("查询所有的商品数据失败");
		}
	}
	@Override
	public Good findById(int gid) {
		String sql = "select * from goods where id = ?";
		try {
			return qr.query(sql, new BeanHandler<Good>(Good.class), gid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("查询指定的商品数据失败");
		}
	}
	@Override
	public void add(Good g) {
		String sql ="insert into goods values(null,?,?,?,?,?,?,?)";
		
		List<Object> list = new ArrayList<>();
		list.add(g.getName());
		list.add(g.getMarketprice());
		list.add(g.getEstoreprice());
		
		list.add(g.getCategory());
		list.add(g.getNum());
		list.add(g.getImgurl());
		list.add(g.getDescription());
		
		try {
			qr.update(sql, list.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("上传商品数据失败");
		}
		
		
	}

}
