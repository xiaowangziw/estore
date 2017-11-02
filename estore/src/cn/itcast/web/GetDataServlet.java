package cn.itcast.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.domain.PCD;
import cn.itcast.service.PCDService;
import cn.itcast.service.impl.PCDServiceImpl;
import flexjson.JSONSerializer;

public class GetDataServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//获取请求参数
		//准备直接使用String类数据，作为sql语句的参数（注：原来需要int）
		String pid = request.getParameter("pid");
		
		//调用Service方法获取地区数据
		PCDService pcdService = new PCDServiceImpl();
		List<PCD> list = pcdService.getData(pid);
		//flexjson技术，使用的对象JSONSerializer
		//将数据转换为json格式的字符串（为了后期发出响应——方便页面使用JSON.parse(),将数据转换成js对象）
		
		JSONSerializer jsonSerializer = new JSONSerializer();
		String serialize = jsonSerializer.serialize(list);
		//将数据发出响应，给浏览器操作
		response.getWriter().write(serialize);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}