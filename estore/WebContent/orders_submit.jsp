<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="Generator" content="ECSHOP v2.7.3" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content="" />
<meta name="Description" content="" />
<title>提交订单</title>
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

/***
 * 
 三级联动优化（心法）：
  1 重复的代码抽取，存入一个方法中
  2 变化的数据，作为方法的参数传入进去
 
 */
 //ele表示select标签，需要添加数据的三级联动标签
 function getData(value,ele){
	//清理原来的数据
	 ele.length = 1
	//获取ajax核心对象
		var xhr = getXHR();
		
		//准备发送请求
		xhr.open("get","${root}/getData?pid="+value,true);
		
		//正式发送
		xhr.send();
		//设置回调函数
		//onreadystatechange设置的函数，会在xhr.readyState属性变化的时候执行
		
		xhr.onreadystatechange = function(){
			//获取响应的数据
			if(xhr.readyState == 4 && xhr.status == 200){
				//获取响应的数据
				var data = xhr.responseText;
				//将数据转换成js对象
				var arr = JSON.parse(data);
				
				for (var i = 0; i < arr.length; i++) {
					//创建option标签
					var opt = document.createElement("option");
					//设置文本数据,获取地区的名称
					opt.innerHTML = arr[i].name;
					//设置标签value值
					opt.setAttribute("value",arr[i].id);
					
					ele.appendChild(opt);
				}
			}
		};
}


	//完成省一级地区数据加载（页面加载完成数据就要出现）
	
	window.onload = function(){
		//准备发送请求，获取数据库中的数据
		//第一种测试：alert（）弹窗，会，将代码停止在某处，相当于debug测试
		//第二种测试：js中也可以try catch，抓取异常（对象调用属性，单词写错，没有效果）；
		var pro = document.getElementById("province");
		getData(0,pro);
	};
	
	//value：当前被选中的option中value值
function getCity(value){
	var city = document.getElementById("city");
	//在清理市的数据的时候，同时要清理县
	var district = document.getElementById("district");
	//清理原来的数据
	district.length = 1;
	
	getData(value,city);
	
}
	function getDistrict(value){
		var district = document.getElementById("district");
		getData(value,district);
	}
	
	
	
	

</script>
</head>
<body>
	<%@include file="inc/header.jsp"%>
	<div class="block clearfix"><div class="AreaR">
	<div class="block box"><div class="blank"></div>
		<div id="ur_here">
			当前位置: <a href="index.jsp">首页</a><code>&gt;</code>购物流程
		</div>
	</div><div class="blank"></div><div class="box"><div class="box_1">
	<div class="userCenterBox boxCenterList clearfix" style="_height:1%;">
	<form action="${root }/addOrder" method="post">
		<!---------收货人信息开始---------->
		<h5><span>收货人信息</span></h5>
		<table width="100%" align="center" border="0" cellpadding="5"
			cellspacing="1" bgcolor="#dddddd">
			<tr>
				<td bgcolor="#ffffff" align="right" width="120px">区域信息：</td>
				<td bgcolor="#ffffff">
				<!-- 希望直接获取到三级联动的选中结果文本
					解决方案：1）要将数据存在另一个地方  2）数据不需要给用户显示
					
					使用隐藏域，来保存数据，需要在select标签中change事件，给隐藏域设置数据
					
					需要获取的数据：当前select标签中，被选中的option标签 
					
					1 this.options 获取当前下拉框所有的option
					2 this.selectedIndex  获取当前被选中的option的索引号
					3 this.options[this.selectedIndex]  获取所有option中选中的哪个标签
					4 this.options[this.selectedIndex].innerHTML 获取标签中文本内容
				 -->
					<!-- 省 -->
					<select id="province" onchange="getCity(this.value);document.getElementById('ph').value=this.options[this.selectedIndex].innerHTML;">
						<option value="">-- 请选择省 --</option>
					</select>&nbsp;&nbsp;&nbsp;
					<input id="ph" type="hidden" name="province">
					<!-- 市 -->
					<select id="city" onchange="getDistrict(this.value);document.getElementById('ch').value=this.options[this.selectedIndex].innerHTML;">
						<option value="">-- 请选择市 --</option>
					</select>&nbsp;&nbsp;&nbsp;
					<input id="ch" type="hidden" name="city">
					<!-- 县(区) -->
					<select id="district" onchange="document.getElementById('dh').value=this.options[this.selectedIndex].innerHTML;">
						<option value="">-- 请选择县(区) --</option>
					</select>
					<input id="dh" type="hidden" name="district">
				</td>
			</tr>
			<tr>
				<td bgcolor="#ffffff" align="right">详细地址：</td>
				<td bgcolor="#ffffff">
					<input style="width:347px;" name="detailAddress"/>
				</td>
			</tr>
			<tr>
				<td bgcolor="#ffffff" align="right">邮政编码：</td>
				<td bgcolor="#ffffff"><input name="zipcode"/></td>
			</tr>
			<tr>
				<td bgcolor="#ffffff" align="right">收货人姓名：</td>
				<td bgcolor="#ffffff"><input name="name"/></td>
			</tr>
			<tr>
				<td bgcolor="#ffffff" align="right">联系电话：</td>
				<td bgcolor="#ffffff"><input name="telephone"/></td>
			</tr>
		</table>
		<!---------收货人信息结束---------->
		
		<!----------商品列表开始----------->
		<div class="blank"></div>
		<h5><span>商品列表</span></h5>
		<table width="100%" border="0" cellpadding="5" cellspacing="1"
			bgcolor="#dddddd">
			<tr>
				<th width="30%" align="center">商品名称</th>
				<th width="22%" align="center">市场价格</th>
				<th width="22%" align="center">商品价格</th>
				<th width="15%" align="center">购买数量</th>
				<th align="center">小计</th>
			</tr>
			<c:forEach items="${cList }" var="cart">
			<tr>
				<td>
					<a href="javascript:;" class="f6" align="center">${cart.good.name }</a>
				</td>
				<td align="center">${cart.good.marketprice }元</td>
				<td align="center">${cart.good.estoreprice }元</td>
				<td align="center">${cart.buynum }</td>
				<td align="center">${cart.buynum * cart.good.estoreprice }元</td>
			</tr>
			<c:set var="totalprice" value="${ totalprice + cart.buynum * cart.good.estoreprice}" />
			</c:forEach>
			<tr>
				<td colspan="5" style="text-align:right;padding-right:10px;font-size:25px;">
					商品总价&nbsp;<font color="red">&yen;${totalprice }</font>元
					<input type="submit" value="提交订单" class="btn" />
				</td>
			</tr>
		</table>
		<!----------商品列表结束----------->
	</form>
	</div></div></div></div></div>
	<%@include file="inc/footer.jsp"%>
</body>
</html>