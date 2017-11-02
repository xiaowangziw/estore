package cn.itcast.service.impl;

import java.util.List;

import cn.itcast.dao.CartDao;
import cn.itcast.dao.GoodDao;
import cn.itcast.dao.impl.CartDaoImpl;
import cn.itcast.dao.impl.GoodDaoImpl;
import cn.itcast.domain.Cart;
import cn.itcast.factory.BeanFactory;
import cn.itcast.service.CartService;

public class CartServiceImpl implements CartService {

	private CartDao cartDao = new CartDaoImpl();
	
	@Override
	public void add(int uid, int gid) {
		
		//查询当前用户（uid）是否购买过指定（gid）的商品
		Cart cart = cartDao.findByUidAndGid(uid,gid);
		if(cart == null){
			//没买过，创建新的记录
			Cart c = new Cart();
			c.setUid(uid);
			c.setGid(gid);
			c.setBuynum(1);
			
			cartDao.add(c);
			
		}else{
			//买过了，修改购买数量加一
			cart.setBuynum(cart.getBuynum() + 1);
			cartDao.update(cart);
		}

	}
	//private GoodDao goodDao = new GoodDaoImpl2();
	private GoodDao goodDao = (GoodDao) BeanFactory.getBean("GoodDao");
	@Override
	public List<Cart> findAll(int uid) {
		//先根据用户id获取购物车数据
		List<Cart> cList = cartDao.findAll(uid);
		//将对应的商品数据，封装起来
		for (Cart cart : cList) {
			cart.setGood(goodDao.findById(cart.getGid()));
		}
		return cList;
	}
	@Override
	public void update(Cart c) {
		
		cartDao.update(c);
	}
	@Override
	public void delete(int gid, int uid) {
		cartDao.delete(gid , uid);
	}
}
