package cn.itcast.dao;

import cn.itcast.domain.User;

public interface UserDao {

	/**
	 * 查询用户的方法（根据用户名）
	 * @param username
	 * @return
	 */
	User findByName(String username);

	/**
	 * 注册的方法
	 * @param user
	 */
	void register(User user);

	/**
	 * 用户登录的方法
	 * @param username
	 * @param password2
	 * @return
	 */
	User login(String username, String password2);

}
