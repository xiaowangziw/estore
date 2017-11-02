package cn.itcast.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils2 {

	public static String getPwd(String password){
		
		try {
			//获取加密对象
			MessageDigest instance = MessageDigest.getInstance("md5");
			//调用加密对象的方法,加密做完
			byte[] bs = instance.digest(password.getBytes());
			/**
			 * 向mysql方法优化： 1） 将所有的数据，转换成正数，数据不变2）将所有的数据转换成16进制格式
			 * 
			 *   byte b  1001 1111
			 *   int  b  0000 0000 0000 0000 0000 0000 1001 1111
			 * & int 255 0000 0000 0000 0000 0000 0000 1111 1111 
			 * -----------------------------------------------------
			 *           0000 0000 0000 0000 0000 0000 1001 1111
			 * 
			 * 
			 * */
			String str = "";
			for (byte b : bs) {
				int temp = b & 255;
				if(temp>=0 && temp < 16){
					str = str + "0" +Integer.toHexString(temp);
				}else{
					str = str + Integer.toHexString(temp);
				}
			}
			System.out.println(str);
			return str;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static void main(String[] args) {
		MD5Utils2.getPwd("abc");
		//mysql ：      900150983cd24fb0d6963f7d28e17f72
		//MD5Utils2:90 150983cd24fb0d6963f7d28e17f72
		//MD5Utils2:900150983cd24fb0d6963f7d28e17f72
	}
}
