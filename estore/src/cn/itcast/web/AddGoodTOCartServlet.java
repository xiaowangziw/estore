package cn.itcast.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.domain.User;
import cn.itcast.service.CartService;
import cn.itcast.service.impl.CartServiceImpl;

public class AddGoodTOCartServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//校验登录
		User loginUser = (User)request.getSession().getAttribute("loginUser");
		if(loginUser == null){
			//=============修改跳转之前浏览页面代码=====================
			//查看商品详细的具体地址
			String header = request.getHeader("Referer");
			
			//将数据存入session中,方便后期使用
			request.getSession().setAttribute("url", header);
			//=============修改跳转之前浏览页面代码=====================
			//未登录的，返回登陆页面
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			//没有return继续往下执行，当前response已经被commit，无法再次提交
			return;
		}
		//获取参数（uid  gid）
		int uid = loginUser.getId();
		int gid = Integer.parseInt(request.getParameter("gid"));
		
		//调用Service方法，添加数据
		CartService cartService = new CartServiceImpl();
		
		cartService.add(uid , gid );
		
		//重定向到中间页面buyorcart.jsp,让用户选择继续购物，还是购物车结算
		response.sendRedirect(request.getContextPath()+"/buyorcart.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}