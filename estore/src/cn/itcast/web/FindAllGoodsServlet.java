package cn.itcast.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.domain.Good;
import cn.itcast.service.GoodService;
import cn.itcast.service.impl.GoodServiceImpl;

public class FindAllGoodsServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		GoodService goodService = new GoodServiceImpl();
		List<Good> gList = goodService.findAll();
		
		request.setAttribute("gList", gList);
		request.getRequestDispatcher("/goods.jsp").forward(request, response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}