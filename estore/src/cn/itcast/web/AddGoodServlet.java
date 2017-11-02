package cn.itcast.web;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.itcast.domain.Good;
import cn.itcast.service.GoodService;
import cn.itcast.service.impl.GoodServiceImpl;
import cn.itcast.utils.DirUtils;
import cn.itcast.utils.UUIDUtils;

public class AddGoodServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//保证文件上传
		/**
		 * fileupload  apache公司
		 * 
		 * DiskFileItemFactory  工厂类，帮助ServletFileUpload将请求解析，结果是数据封装成FileItem
		 * ServletFileUpload   解析请求的核心对象
		 * FileItem   封装数据的容器对象
		 * */
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		//处理中文文件名乱码
		upload.setHeaderEncoding("utf-8");
		Map<String,String> map = new HashMap<>();
		try {
			List<FileItem> items = upload.parseRequest(request);
			//遍历循环，判断当前的FileItem，其中的内容是否是普通表单项
			for (FileItem item : items) {
				if (item.isFormField()) {
				    String name = item.getFieldName();
				    String value = item.getString("utf-8");
				    map.put(name,value);
				}else{
					//获取当前上传文件的文件名
					 String fileName = item.getName();
					 
					 //将文件上传的数据，输出到指定文件位置
					 //获取upload文件的位置
					 //C:/apache-tomcat-7.0.75/webapps/estore/upload
					 //C:/apache-tomcat-7.0.75/picture
					 //String realPath = getServletContext().getRealPath("/upload");
					 
					 String realPath = "C:/apache-tomcat-7.0.75/picture";
					 
					 //处理重名
					 fileName = UUIDUtils.getUUID() + fileName;
					 //目录打散
					 String dir = DirUtils.getDir(fileName);
					 //保证目录打散的文件夹存在
					 new File(realPath,dir).mkdirs();
					 try {
						item.write(new File(realPath+dir,fileName));
					} catch (Exception e) {
						e.printStackTrace();
					}
					 map.put("imgurl", "/picture"+dir+"/"+fileName);
				}
			}
			
			
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		Good g = new Good();
		
		//封装数据到good对象
		try {
			BeanUtils.populate(g, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(g);
		//调用service方法，插入数据
		GoodService goodService = new GoodServiceImpl();
		goodService.add(g);
		//重新调用查询全部的servlet，查看效果
		response.sendRedirect(request.getContextPath()+"/goods_admin.jsp");
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}