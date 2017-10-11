<%@page import="cn.tedu.bean.User"%>
<%@ page language="java" import="java.util.*" isELIgnored="false" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
	<link href="${app}/css/prodList.css" rel="stylesheet" type="text/css">
</head>
<body>
	<!-- 将头部包含进来 -->
	<%@include file="/_head.jsp" %>
	
	<div id="content">
		<div id="search_div">
			<form method="post" action="${app}/servlet/ProdListByConditionServlet">
				<span class="input_span">商品名：<input type="text" name="name" value="${ param.name }"/></span>
				<span class="input_span">商品种类：<input type="text" name="category" value="${ param.category }"/></span>
				<span class="input_span">商品价格区间：
					<input type="text" name="minprice" value="${ param.minprice }"/> - 
					<input type="text" name="maxprice" value="${ param.maxprice }"/></span>
				<input type="submit" value="查 询">
				<input type="reset" id="reset" value="重置">
			</form>
		</div>
		<div id="prod_content">
			<!-- 模版数据 -->
			
		<c:forEach items="${ list }" var="prod">
			<div class="prod_div">
				<a href="${app}/servlet/ProdInfoServlet?pid=${prod.id}">
					<img src="${app}/servlet/ProdImgServlet?imgurl=${prod.imgurl}"></img>
				</a>	
				<div id="prod_name_div">
					<a href="${app}/servlet/ProdInfoServlet?pid=${prod.id}">
						${ prod.name }
					</a>
				</div>
				<div id="prod_price_div">
					${ prod.price }
				</div>
				<div>
					<div id="gotocart_div">
						<a href="${app}/servlet/CartUpdateServlet?pid=${prod.id}&buyNum=1">加入购物车</a>
					</div>					
					<div id="say_div">
						库存${prod.pnum }件
					</div>					
				</div>
			</div>
		</c:forEach>
			<div style="clear: both"></div>
		</div>
	</div>
	<!-- 将尾部包含进来 -->
	<%@include file="/_foot.jsp" %>
</body>
</html>
