<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册</title>
<%@include file="inc/common_head.jsp"%>
<script type="text/javascript">
	function getXHR(){
		var xmlhttp;
		if (window.XMLHttpRequest)
		{// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
		}
		else
		{// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		}
		return xmlhttp;
	}
	//设置一个全局的变量，保证后期，可以存储数据
var flag = false;
	function check(value){
		//获取当前输入框中用户名，发送ajax请求，给服务器
		var xmlhttp = getXHR();
		//true：异步请求——what is 异步请求？
		//异步：所有的请求可以插队执行   同步：所有的请求排队
		xmlhttp.open("get","${root}/check?username="+value,true);
		xmlhttp.send();
		
		xmlhttp.onreadystatechange = function(){
			if(xmlhttp.readyState == 4 && xmlhttp.status==200){
				//获取响应的数据
				var data = xmlhttp.responseText;
				//基于响应的任务
				var msg = document.getElementById("username_notice");
				var registForm = document.getElementById("registForm");
				if(data == 1){
					msg.innerHTML = "<font color='green'>可以使用</font>";
					flag = true;
				}else if(data == -1){
					msg.innerHTML = "重复";
					//任何事件，都用来，启动js函数的，需要执行的任务和结果，都定义在函数中。
					flag = false;
				}else if(data == -3){
					msg.innerHTML = "不能为空";
					flag = false;
				}else{
					msg.innerHTML = "服务器忙";
					flag = false;
				}
			}
		};
		
	}
	/*
	问题原因：
		页面加载完成表单本身就设置了onsubmit    onsubmit="return register();"
		在启动离焦事件只有，又一次对表单的onsubmit属性进行设置    registForm.onsubmit = function(){return true;};
	
		后一次覆盖前一次操作结果；
		
	解决方案：
	         别人写的代码，不要改，但是，自己的js也要执行
	         
	         alert(register());发现，执行的结果是一个boolean类型的值
	                      设置一个全局变量保存自己的js函数执行结果
		
	                      
	心得：在工作的时候，别人的代码，不要改，不要删，只能在原来基础上添加。                    
	*/
	/* 
	this有三种含义：表示当前标签对象，表示函数的调用这，表示循环过程中被循环到的当前元素（jquery知识点）
	
	onblur="is_registered(this.value);check();"
	相当于：
	ele.onblur = function(){
		
		is_registered(this.value);
		check();
	}; */
</script>
</head>
<body>
	<%@include file="inc/header.jsp"%>
	<div class="block block1">
		<div class="blank"></div>
		<div class="usBox">
			<div class="usBox_1">
				<div class="login_tab">
					<ul>
						<li onclick="location.href='login.jsp';">
							<a href="javascript:;">用户登录</a>
						</li>
						<li class="active">用户注册</li>
					</ul>
				</div>
				<form id="registForm" action="${root }/register" method="post" name="formUser"
					onsubmit="return (register() && flag);">
					<table width="100%" border="0" align="left" cellpadding="5"
						cellspacing="3">
						<!-- 表格的标题标签 -->
						<caption>${msg }</caption>
						<tr>
							<td width="25%" align="right">用户名</td>
							<td width="65%"><input name="username" type="text"
								id="username" onblur="is_registered(this.value);check(this.value);"
								class="inputBg" /> <span id="username_notice"
								style="color:#FF0000"> *</span></td>
						</tr>
						<tr>
							<td align="right">昵称</td>
							<td><input name="nickname" type="text"
								id="nickname" onblur="check_nickname(this.value);"
								class="inputBg" /> <span id="nickname_notice"
								style="color:#FF0000"> *</span></td>
						</tr>
						<tr>
							<td align="right">密码</td>
							<td><input name="password" type="password" id="password1"
								onblur="check_password(this.value);"
								onkeyup="checkIntensity(this.value)" class="inputBg" />
								<span style="color:#FF0000"
								id="password_notice"> *</span></td>
						</tr>
						<tr>
							<td align="right">密码强度</td>
							<td>
								<table width="145" border="0" cellspacing="0" cellpadding="1">
									<tr align="center">
										<td width="33%" style="border-bottom:2px solid #ccc;" id="pwd_lower">弱</td>
										<td width="33%" style="border-bottom:2px solid #ccc;" id="pwd_middle">中</td>
										<td width="33%" style="border-bottom:2px solid #ccc;" id="pwd_high">强</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="right">确认密码</td>
							<td><input name="confirm_password" type="password" 
								id="conform_password"
								onblur="check_conform_password(this.value);" class="inputBg" />
								<span style="color:#FF0000"
								id="conform_password_notice"> *</span></td>
						</tr>
						<tr>
							<td align="right">验证码</td>
							<td><input type="text" size="8" name="captcha" id="captcha"
								class="inputBg" onblur="check_captcha(this.value);" /> <span style="color:#FF0000"
								id="captcha_notice"> *</span></td>
						</tr>
						<tr>
							<td align="right"></td>
							<td><img src="validatecode.jsp"
								style="vertical-align:middle;cursor:pointer;width:130px;height:35px;margin-top:-2px;"
								onClick="src='validatecode.jsp?'+Math.random()" /></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><label> <input name="agreement" type="checkbox"
									value="1" checked="checked" /> 我已看过并接受《<a
									href="javascript:;" style="color:blue" target="_blank">用户协议</a>》
							</label></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td align="left">
								<input name="Submit" type="submit" value="" class="us_Submit_reg">
							</td>
						</tr>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
					</table>
				</form>
				<div class="blank"></div>
			</div>
		</div>
	</div>
	<%@include file="inc/footer.jsp"%>
</body>
</html>