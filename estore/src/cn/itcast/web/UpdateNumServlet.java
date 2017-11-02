package cn.itcast.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.domain.Cart;
import cn.itcast.domain.User;
import cn.itcast.service.CartService;
import cn.itcast.service.impl.CartServiceImpl;

public class UpdateNumServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//校验登录
		User loginUser = (User)request.getSession().getAttribute("loginUser");
		if(loginUser == null){
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		//获取数据并且封装到Cart对象
		int uid = loginUser.getId();
		int buynum = Integer.parseInt( request.getParameter("buynum"));
		int gid = Integer.parseInt( request.getParameter("gid"));
		
		Cart c = new Cart();
		
		c.setBuynum(buynum);
		c.setGid(gid);
		c.setUid(uid);
		
		//调用Service，修改数据
		CartService cartService = new CartServiceImpl();
		cartService.update(c);
		
		//调用findAllCartsServlet，重新获取数据，展示修改后的效果
		response.sendRedirect(request.getContextPath()+"/findAllCarts");
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}