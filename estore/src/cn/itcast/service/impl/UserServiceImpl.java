package cn.itcast.service.impl;

import cn.itcast.dao.UserDao;
import cn.itcast.dao.impl.UserDaoImpl;
import cn.itcast.domain.User;
import cn.itcast.service.UserService;
import cn.itcast.utils.MD5Utils;

public class UserServiceImpl implements UserService {

	private UserDao userDao = new UserDaoImpl();
	@Override
	public int register(User user) {
		try {
			//查询数据是否存在
			User findByName = userDao.findByName(user.getUsername());
			if(findByName == null){
				//可以注册，加密，设置角色
				user.setPassword(MD5Utils.getPassword(user.getPassword()));
				user.setRole("user");
				
				userDao.register(user);
				return 1;
			}else{
				return -1;
			}
		} catch (Exception e) {
			//显示堆栈错误信息，方便查错
			e.printStackTrace();
			return -2;
		}
	}
	@Override
	public int findByName(String username) {
		try {
			//查询数据是否存在
			User findByName = userDao.findByName(username);
			if(findByName == null){
				return 1;//表示可以使用
			}else{
				return -1;//已经存在
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -2;
		}
	}
	@Override
	public User login(String username, String password) {
		// TODO 这个是一个书签，开发到这里，一会儿继续
		// 用户明文密码进行加密
		String password2 = MD5Utils.getPassword(password);
		// 调用dao登录用户
		
		return  userDao.login(username,password2);
		
	}
	

}
