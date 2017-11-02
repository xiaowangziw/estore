package cn.itcast.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import cn.itcast.service.UserService;
import cn.itcast.service.impl.UserServiceImpl;

public class CheckServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//获取请求参数，校验
		String username = request.getParameter("username");
		PrintWriter writer = response.getWriter();
		if(StringUtils.isBlank(username)){
			//数据为空，不合法
			writer.write("-3");
			return;
		}
		//调用Service方法查询
		UserService userService = new UserServiceImpl();
		int info = userService.findByName(username);
		//直接将数据返回给浏览器
		writer.write(info+"");
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}