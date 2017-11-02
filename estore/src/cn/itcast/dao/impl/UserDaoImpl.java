package cn.itcast.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.itcast.dao.UserDao;
import cn.itcast.domain.User;
import cn.itcast.utils.DBUtils;

public class UserDaoImpl implements UserDao {

	private QueryRunner qr = new QueryRunner(DBUtils.getDataSource());
	@Override
	public User findByName(String username) {
		String sql = "select * from user where username = ?";
		try {
			return qr.query(sql, new BeanHandler<User>(User.class), username);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("查询指定用户失败");
		}
	}

	@Override
	public void register(User user) {
		String sql = "insert into user values(null,?,?,?,?)";
		List<Object> list = new ArrayList<>();
		
		list.add(user.getNickname());
		list.add(user.getUsername());
		
		list.add(user.getPassword());
		list.add(user.getRole());
		
		try {
			qr.update(sql, list.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("注册用户失败");
		}

	}

	@Override
	public User login(String username, String password2) {
		String sql = "select * from user where username = ? and password = ?";
		try {
			return qr.query(sql, new BeanHandler<User>(User.class), username,password2);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("登陆用户失败");
		}
		
	}

}
