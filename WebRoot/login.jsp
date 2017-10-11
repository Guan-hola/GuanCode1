<%@page import="java.net.URLDecoder"%>
<%@ page language="java" import="java.util.*, java.net.URLDecoder" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${app}/css/login.css"/>
		<title>EasyMall欢迎您登陆</title>
		<script type="text/javascript">
			/* 使用js代码对用户名进行url解码 */
			window.onload = function(){
				var oInp = document.getElementsByName("username")[0];
				oInp.value = decodeURI(oInp.value);
			}
			
		</script>
	</head>
	<body>
		<h1>欢迎登陆EasyMall</h1>
		<form action="${app}/servlet/LoginServlet" method="POST">
			<table>
				<tr>
					<td colspan="2" style="color:red;text-align:center">
						${ msg }
					</td>
				</tr>
				<tr>
					<%-- //-- jsp片段, 其中可以书写多行java语句
						//1.获取请求中的所有Cookie信息
						Cookie[] cs = request.getCookies();
						Cookie remnameCookie = null;
						if( cs != null){
							for(Cookie c : cs){
								if("remname".equals(c.getName())){
									remnameCookie = c;
								}
							}
						}
						String username = "";
						if(remnameCookie != null){
							username = remnameCookie.getValue();
							//对username进行url解码
							username = URLDecoder.decode(username, "utf-8");
						}
					--%>
					<td class="tdx">用户名：</td>
					<td><input type="text" name="username" value="${ cookie.remname.value }"/></td>
				</tr>
				<tr>
					<td class="tdx">密&nbsp;&nbsp; 码：</td>
					<td><input type="password" name="password"/></td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="checkbox" name="remname" value="true"
							${ empty cookie.remname ? "" : "checked='checked'" }
						/>记住用户名
						<input type="checkbox" name="autologin" value="true"/>30天内自动登陆
					</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align:center">
						<input type="submit" value="登 陆"/>
					</td>
				</tr>
			</table>
		</form>		
	</body>
</html>
