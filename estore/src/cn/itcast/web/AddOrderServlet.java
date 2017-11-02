package cn.itcast.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.domain.Cart;
import cn.itcast.domain.Order;
import cn.itcast.domain.OrderItems;
import cn.itcast.domain.User;
import cn.itcast.service.OrderService;
import cn.itcast.service.impl.OrderServiceImpl;
import cn.itcast.utils.UUIDUtils;

public class AddOrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//校验登录
		User loginUser = (User)request.getSession().getAttribute("loginUser");
		if(loginUser == null){
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		//获取数据（目标：将Order订单对象封装好  ）
		String oid = UUIDUtils.getUUID();
		int uid = loginUser.getId();
		//double 数据长度  8个字节
		/**
		 * 电商网站，一年总流水，阿里巴巴，需要一个更加强大的数据类型，进行计算和保存数据
		 * BigDecimal，可以记录任意长度数据，不能使用计算符号
		 * 
		 * */
		double totalprice = 0;
		//计算总金额，从购物车数据中获取和计算
		List<Cart> cList = (List<Cart>) request.getSession().getAttribute("cList");
		//准备一个集合，封装订单明细数据
		List<OrderItems> oiList = new ArrayList<>();
		
		for (Cart cart : cList) {
			totalprice = totalprice + cart.getBuynum() * cart.getGood().getEstoreprice();
			
			OrderItems oi = new OrderItems();
			oi.setBuynum(cart.getBuynum());
			oi.setGid(cart.getGid());
			oi.setOid(oid);
			
			oiList.add(oi);
		}
		//支付状态
		int status = 1;
		//创建时间
		Date createtime = new Date();
		//详细地址
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String district = request.getParameter("district");
		
		String detailAddress = request.getParameter("detailAddress");
		String zipcode = request.getParameter("zipcode");
		String name = request.getParameter("name");
		String telephone = request.getParameter("telephone");
		
		String address = province+"（省/市）"+ city+"（市/区）"+district+"（县/镇）"+detailAddress+"邮编："+zipcode+"姓名："+name+"电话："+telephone;
		
		Order o = new Order();
		o.setAddress(address);
		o.setCreatetime(createtime);
		o.setId(oid);
		o.setStatus(status);
		o.setTotalprice(totalprice);
		o.setUid(uid);
		o.setOiList(oiList);
		System.out.println(o);
		//调用service添加订单
		OrderService orderService = new OrderServiceImpl();
		orderService.addOrder(o);
		
		//跳转orders.jsp页面
		//response.sendRedirect(request.getContextPath()+"/orders.jsp");
		response.sendRedirect(request.getContextPath()+"/findAllOrders");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}