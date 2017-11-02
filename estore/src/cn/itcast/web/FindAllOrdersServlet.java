package cn.itcast.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.domain.Order;
import cn.itcast.domain.User;
import cn.itcast.service.OrderService;
import cn.itcast.service.impl.OrderServiceImpl;

public class FindAllOrdersServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 目标：获取当前用户的所有订单
		// 校验登录
		User loginUser = (User) request.getSession().getAttribute("loginUser");
		if (loginUser == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			// return 必须存在，不然代码往下执行，在获取用户id的代码中，会爆出空指针异常
			return;
		}
		
		int uid = loginUser.getId();
		
		//调用service方法，查询用户订单
		OrderService orderService = new OrderServiceImpl();
		List<Order> oList = orderService.findAll(uid);
		
		//将数据转发到页面orders.jsp
		request.setAttribute("oList", oList);
		request.getRequestDispatcher("/orders.jsp").forward(request, response);
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}