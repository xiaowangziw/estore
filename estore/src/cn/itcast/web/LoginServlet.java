package cn.itcast.web;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import cn.itcast.domain.User;
import cn.itcast.service.UserService;
import cn.itcast.service.impl.UserServiceImpl;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//获取参数并且校验
		String username = request.getParameter("username");
		if(StringUtils.isBlank(username)){
			
			request.setAttribute("msg", "用户名不能为空");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
			
		}
		String password = request.getParameter("password");
		if(StringUtils.isBlank(password)){
			
			request.setAttribute("msg", "密码不能为空");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
			
		}
		//调用Service方法，登录用户
		UserService userService = new UserServiceImpl();
		User loginUser = userService.login(username , password);
		
		//根据返回值，不同处理
		if(loginUser == null){
			request.setAttribute("msg", "用户名或者密码错误");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}else{
			//======================记住用户名修改=====================
			String remember = request.getParameter("remember");
			if("on".equals(remember)){
				//需要记住
				Cookie cookie = new Cookie("username", URLEncoder.encode(username, "utf-8") );
				cookie.setMaxAge(60*60*24*7);
				cookie.setPath("/");
				response.addCookie(cookie);
				//为什么cookie不能存中文？
				//规定
			}else{
				//不需要记住
				Cookie cookie = new Cookie("username", "");
				cookie.setMaxAge(0);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
			
			//======================记住用户名修改=====================
			//登陆成功，将用户的信息存入session中,跳转主页
			request.getSession().setAttribute("loginUser", loginUser);
			//=================跳转之前浏览的页面修改======================
			
			String url = (String)request.getSession().getAttribute("url");
			if(url != null){
				response.sendRedirect(url);
				return;
			}
			
			//=================跳转之前浏览的页面修改======================
			
			response.sendRedirect(request.getContextPath());//request.getContextPath———— /estore
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}