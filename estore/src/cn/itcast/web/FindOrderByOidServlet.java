package cn.itcast.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.domain.Order;
import cn.itcast.domain.User;
import cn.itcast.service.OrderService;
import cn.itcast.service.impl.OrderServiceImpl;

public class FindOrderByOidServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//校验登录（虽然最终查询不要用户信息，但是查询当前用户指定订单信息，还是需要严格登陆校验）
		User loginUser = (User)request.getSession().getAttribute("loginUser");
		if(loginUser == null){
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		
		//获取参数（oid）
		String oid = request.getParameter("oid");
		//调用service，查询数据
		OrderService orderService = new OrderServiceImpl();
		
		Order order = orderService.findById(oid);
		
		//将数据转发orders_detail.jsp，在页面显示
		request.setAttribute("order", order);
		request.getRequestDispatcher("/orders_detail.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}