package cn.itcast.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.domain.Cart;
import cn.itcast.domain.User;
import cn.itcast.service.CartService;
import cn.itcast.service.impl.CartServiceImpl;

public class FindAllCartsServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//校验登陆
		User loginUser = (User)request.getSession().getAttribute("loginUser");
		if(loginUser == null){
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		//获取数据（uid）
		int uid = loginUser.getId(); 
		//调用Service方法获取数据
		CartService cartService = new CartServiceImpl();
		List<Cart> cList = cartService.findAll(uid);
		
		//转发数据到cart.jsp页面，展示数据
		//request.setAttribute("cList", cList);
		request.getSession().setAttribute("cList", cList);
		request.getRequestDispatcher("/cart.jsp").forward(request, response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}