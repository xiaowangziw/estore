package cn.itcast.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PicServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//获取请求参数
		String imgurl = request.getParameter("imgurl");
		// C:/apache-tomcat-7.0.75  /picture/11/14/a1f37e332f4d49898b92301da86efc4b1.jpg
		//加载图片
		File file = new File("C:/apache-tomcat-7.0.75"+imgurl);
		
		//使用IO技术读取文件
		FileInputStream in = new FileInputStream(file);
		
		//获取一个输出流
		ServletOutputStream out = response.getOutputStream();
		//标准的IO代码
		
		int len = -1;
		byte[] buf = new byte[8192];
		while((len = in.read(buf))!=-1){
			out.write(buf, 0, len);
		}
		in.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}