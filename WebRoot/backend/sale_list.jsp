<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
<style type="text/css">
body {
	background: #F5F5F5;
	text-align: center;
}

table {
	text-align: center;
	margin: 0px auto;
}

th {
	background-color: silver;
}
</style>
</head>
<body>
	<h1>销售榜单</h1>
	<a href="${app}/servlet/DownloadServlet">销售榜单下载</a>
	<hr>

	<table bordercolor="black" border="1" width="95%" cellspacing="0px" cellpadding="5px">
		<tr>
			<th>商品id</th>
			<th>商品名称</th>
			<th>销售总量</th>
		</tr>
	
		<c:forEach items="${requestScope.list }" var="SaleInfo">
			<tr>
				<td>${SaleInfo.prod_id }</td>
				<td>${SaleInfo.prod_name }</td>
				<td>${SaleInfo.sale_num }</td>
			</tr>
		</c:forEach>
			

	</table>
</body>
</html>
