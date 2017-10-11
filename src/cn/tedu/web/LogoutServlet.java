package cn.tedu.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 处理用户的注销请求
 */
public class LogoutServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//杀死session
		request.getSession().invalidate();
		
		//如果用户点击退出按钮, 默认是取消自动登陆, 即删除自动登陆Cookie
		Cookie c = new Cookie("autologin", "");
		c.setMaxAge(0);// 设置为0立即删除
		c.setPath(request.getContextPath() + "/");
		response.addCookie(c);
		
		//退出之后跳转主页
		response.sendRedirect(request.getContextPath()+"/index.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
