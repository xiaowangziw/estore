package cn.itcast.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import cn.itcast.domain.User;
import cn.itcast.service.UserService;
import cn.itcast.service.impl.UserServiceImpl;

public class RegisterServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//数据校验（两次密码一致，验证码正确，数据不为空）
		String username = request.getParameter("username");
		if(StringUtils.isBlank(username)){
			request.setAttribute("msg", "用户名不能为空");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}
		
		String nickname = request.getParameter("nickname");
		if(StringUtils.isBlank(nickname)){
			request.setAttribute("msg", "昵称不能为空");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}
		
		String password = request.getParameter("password");
		if(StringUtils.isBlank(password)){
			request.setAttribute("msg", "密码不能为空");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}
		
		String confirm_password = request.getParameter("confirm_password");
		if(StringUtils.isBlank(confirm_password)){
			request.setAttribute("msg", "确认密码不能为空");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}
		
		String captcha = request.getParameter("captcha");
		if(StringUtils.isBlank(captcha)){
			request.setAttribute("msg", "验证码不能为空");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}
		
		if(!password.equals(confirm_password)){
			request.setAttribute("msg", "两次密码不一致");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}
		
		String code = (String)request.getSession().getAttribute("code");
		if(!code.equalsIgnoreCase(captcha)){
			request.setAttribute("msg", "验证码错误");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}
		//封装数据到User对象
		User user = new User();
		try {
			BeanUtils.populate(user, request.getParameterMap());
			//IllegalAccessException : 如果当前成员变量是private，需要对其进行操作的是，取消java语言访问检查
		} catch (Exception e) {
			e.printStackTrace();
		}
		//调用Service方法注册用户
		UserService userService = new UserServiceImpl();
		int info = userService.register(user);
		//根据返回值，不同处理
		if(info == 1){
			//成功
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}else if(info == -1){
			//已经存在
			request.setAttribute("msg", "用户名已经存在");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}else {
			//类似这种，提示的信息，找谁来写客服，公关。
			request.setAttribute("msg", "服务器忙");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}