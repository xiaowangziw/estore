package cn.itcast.service;

import cn.itcast.domain.User;

public interface UserService {

	/**
	 * 用户注册的方法
	 * @param user
	 * @return
	 */
	int register(User user);

	/**
	 * 根据用户名查询数据的方法
	 * @param username
	 * @return
	 */
	int findByName(String username);

	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	User login(String username, String password);

}
