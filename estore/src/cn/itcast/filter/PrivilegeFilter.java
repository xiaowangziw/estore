package cn.itcast.filter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.domain.User;

public class PrivilegeFilter implements Filter {
	// 准备两个集合分别保存用户和管理员权限数据
	private List<String> userList = new ArrayList<>();
	private List<String> adminList = new ArrayList<>();

	@Override
	// 加载权限管理配置文件数据
	public void init(FilterConfig config) throws ServletException {
		System.out.println("。。。。。。。。。。。。。。。。权限数据加载启动。。。。。。。。。。。。。。。。。");
		try {
			// 获取当前权限管理配置文件的位置
			String realPath = config.getServletContext().getRealPath("/WEB-INF/classes/user.txt");
			// 使用输入流读取
			BufferedReader reader = new BufferedReader(new FileReader(new File(realPath)));
			// 标准IO
			String line = null;
			while ((line = reader.readLine()) != null) {
				// 将数据存入集合容器中
				userList.add(line);
			}
			System.out.println(userList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// 获取当前权限管理配置文件的位置
			String realPath = config.getServletContext().getRealPath("/WEB-INF/classes/admin.txt");
			// 使用输入流读取
			BufferedReader reader = new BufferedReader(new FileReader(new File(realPath)));
			// 标准IO
			String line = null;
			while ((line = reader.readLine()) != null) {
				// 将数据存入集合容器中
				adminList.add(line);
			}
			System.out.println(adminList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("。。。。。。。。。。。。。。。。权限数据加载完成。。。。。。。。。。。。。。。。。");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		// 将请求的路径和权限数据比较
		String requestURI = req.getRequestURI();
		requestURI = requestURI.substring(req.getContextPath().length());
		System.out.println(requestURI);
		
		boolean isUser = userList.contains(requestURI);
		boolean isAdmin = adminList.contains(requestURI);
		if(isUser || isAdmin){
			//请求只要出现在其中一个集合中，就是需要管理的
			
			//当前用户必须登录
			User loginUser = (User)req.getSession().getAttribute("loginUser");
			if(loginUser == null){
				res.sendRedirect(req.getContextPath()+"/login.jsp");
				return;
			}else{
				//针对用户的角色和具有的权限，进行匹配
				//当前用户是普通用户，而且请求的权限是普通用户权限，放行
				if(loginUser.getRole().equals("user") && isUser){
					chain.doFilter(req, res);
				}else if(loginUser.getRole().equals("admin") && isAdmin){
					chain.doFilter(req, res);
				}else{
					throw new RuntimeException("权限不足，请联系超级管理员");
				}
			}
		}else{
			//不需要管理
			chain.doFilter(req, res);
		}
		
	}

	@Override
	public void destroy() {
	}

}
