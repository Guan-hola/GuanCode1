<%@ page language="java" import="java.util.*" isELIgnored="false" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
<title>商品管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<style type="text/css">
	body{
		font-family: "微软雅黑";
		background-color: #EDEDED;
	}
	h2{
		text-align: center;
	}
	table{ 
		margin: 0 auto; 
		/* width: 96%; */
		text-align: center;
		border-collapse:collapse;
	}
	td, th{ padding: 7px;}
	th{
		background-color: #DCDCDC;
	}
	th.ths{
		width: 100px;
	} 
	input.pnum{
		width:80px;
		height:25px;
		font-size: 18px;
		text-align:center;
	}
	
</style>

<!--引入jquery的js库-->
<script src="${app}/js/jquery-1.4.2.js"></script>
<script type="text/javascript">
	/* 文档就绪事件函数 */
	$(function(){
		/* 修改商品库存数量 */
		$(".pnum").blur(function(){
			/* 
			 1.当输入框失去焦点时没有做任何修改, 不应该向服务器发送请求
			 2.当输入的数值不是合法数值时, 要提示"您输入的数值不合法"
			 3.当提示完"您输入的数值不合法"后, 将输入框的值还原上一次的值
			 */
			var _this = this;
			//获取商品的库存数量(旧的数量)
			var oldPnum = $(this).attr("oldPnum");
			//获取商品的库存数量(新的数量)
			var newPnum = $(this).val();
			
			//获取商品的id
			var pid = $(this).attr("id");
			
			//问题1:如果修改之前的数量和修改之后的数量一致, 就不做任何修改
			if(oldPnum == newPnum){
				return;
			}
			
			//console.log("old: "+oldPnum);
			//console.log("new: "+newPnum);
			
			//问题2:校验数值是否合法(必须是正整数(>=0))//0199
			var reg = /^0$|^[1-9][0-9]*$/;
			if(!reg.test(newPnum)){//不是合法数值
				alert("您输入的数值不合法!");
				//如果数值不合法, 把输入框的值还原为上次输入的合法数值
				$(this).val(oldPnum);
				return;
			}
			
			//使用ajax请求异步访问服务器, 修改商品库存数量
			$.post("${app}/servlet/BackProdPnumUpdateServlet", {"pnum" : newPnum,"pid" : pid}, function(result){
				//result: true 表示修改成功, false: 修改失败
				if("true" == result){
					alert("修改成功!");
					//修改成功后要及时更新旧的库存数量
					$(_this).attr("oldPnum", newPnum);
				}else{
					alert("修改失败!");
				}
			});
			
		});
		
		
		
		/* 删除商品 
		 * 给所有的删除超链接添加点击事件, 点击删除
		 * 执行删除当前商品的操作!
		 */
		$(".del").click(function(){
			//添加确认对话框, 确认是否真的删除商品
			if(!window.confirm("您确定要删除该商品吗?")){
				return;
			}
		
			//1.获取所要删除商品的id
			var pid = $(this).parents("tr").find(".pnum").attr("id");
			//alert(pid);
			
			var _this = this;
			
			//2.利用ajax异步访问服务器来删除指定id的商品
			$.post("${app}/servlet/BackProdDelServlet", {"pid":pid}, function(result){
				//result:true表示删除成功
				if("true" == result){
					alert("删除成功!");
					//同时将商品信息从页面中删除
					$(_this).parents("tr").remove();
				}else{
					alert("删除失败!");
				}
			});
			
			
		});
	
	});
	
</script>
</head>
<body>
	<h2>商品管理</h2>
	<table border="1">
		<tr>
			<th>商品图片</th>
			<th width="200px">商品ID</th>
			<th class="ths">商品名称</th>
			<th class="ths">商品种类</th>
			<th class="ths">商品单价</th>
			<th class="ths">库存数量</th>
			<th>描述信息</th>
			<th width="50px">操 作</th>
		</tr>

		<!-- 模版数据 -->
	
	
<%-- 通过jstl+el获取所有商品组成的list集合, 遍历显示 --%>
<c:forEach items="${ list }" var="prod">
		<tr>
			<td>
				<img width="120px" height="120px" src="${app}/servlet/ProdImgServlet?imgurl=${ prod.imgurl }" alt="" >
			</td>
			<td>${ prod.id }</td>
			<td>${ prod.name }</td>
			<td>${ prod.category }</td>
			<td>${ prod.price }</td>
			<td>
				<input type="text" id="${ prod.id }" class="pnum" oldPnum="${ prod.pnum }" value="${ prod.pnum }"/>
			</td>
			<td>${ prod.description }</td>
			<td><a class="del" href="javascript:void(0)">删 除</a></td>
		</tr>
</c:forEach>	


	</table>
</body>
</html>
