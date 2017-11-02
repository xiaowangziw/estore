package cn.itcast.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.domain.User;
import cn.itcast.service.CartService;
import cn.itcast.service.impl.CartServiceImpl;

public class DeleteCartServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//校验登录
		User loginUser = (User)request.getSession().getAttribute("loginUser");
		if(loginUser == null){
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			//return 必须存在，不然代码往下执行，在获取用户id的代码中，会爆出空指针异常
			return;
		}
		//获取参数（gid uid ）
		int gid = Integer.parseInt(request.getParameter("gid"));
		int uid = loginUser.getId();
		//调用Service方法，删除数据
		CartService cartService = new CartServiceImpl();
		
		cartService.delete(gid , uid );
		//调用findAllCartsServlet，重新获取数据库数据，展示，删除的效果
		response.sendRedirect(request.getContextPath()+"/findAllCarts");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}