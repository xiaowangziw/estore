package cn.itcast.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.domain.Good;
import cn.itcast.service.GoodService;
import cn.itcast.service.impl.GoodServiceImpl;

public class FindGoodByIdServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取参数
		String parameter = request.getParameter("gid");
		Integer gid = null;
		try {
			 gid = Integer.parseInt(parameter);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath());
			return ;
		}
		//调用Service方法，获取数据
		GoodService goodService = new GoodServiceImpl();
		Good good = goodService.findById(gid);
		
		//转发到页面，进行展示数据
		request.setAttribute("good", good);
		request.getRequestDispatcher("/goods_detail.jsp").forward(request, response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}