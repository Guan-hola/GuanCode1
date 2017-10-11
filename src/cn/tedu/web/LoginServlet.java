package cn.tedu.web;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.bean.User;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.UserService;
import cn.tedu.utils.WebUtils;

/**
 * 处理登陆请求
 */
public class LoginServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.处理请求参数乱码
		// request.setCharacterEncoding("utf-8");

		// 2.获取请求参数(获取用户的登陆信息)
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String remname = request.getParameter("remname");

		// 3.调用service层的方法进行登陆
		UserService service = BasicFactory.getFactory().getInstance(
				UserService.class);
		User user = service.loginUser(username, WebUtils.md5(password));

		if (user != null) {// 用户名密码正确
			// 4.实现记住用户名功能
			if ("true".equals(remname)) {
				Cookie cookie = new Cookie("remname", URLEncoder.encode(
						username, "utf-8"));
				cookie.setPath(request.getContextPath() + "/");
				cookie.setMaxAge(3600 * 24 * 30);
				response.addCookie(cookie);
			} else {// 取消记住用户名(删除Cookie)
				Cookie cookie = new Cookie("remname", "");
				cookie.setPath(request.getContextPath() + "/");
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}

			// 5.判断是否需要30天内自动登陆
			String autologin = request.getParameter("autologin");
			if ("true".equals(autologin)) {// 实现30天自动登陆
				// 将用户名和密码保存进Cookie中
				Cookie c = new Cookie("autologin", URLEncoder.encode(username, "utf-8") + ":" + WebUtils.md5(password));
				c.setMaxAge(60 * 60 * 24 * 30);// 保存Cookie30天
				c.setPath(request.getContextPath() + "/");
				// 将Cookie发送给浏览器
				response.addCookie(c);
			} else {// 取消30天自动登陆
				Cookie c = new Cookie("autologin", "");
				c.setMaxAge(0);// 设置为0立即删除
				c.setPath(request.getContextPath() + "/");
				response.addCookie(c);
			}

			// 6.进行登陆
			request.getSession().setAttribute("user", user);

			// 7.登陆成功重定向主页
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		} else {// 用户名密码不正确
			request.setAttribute("msg", "用户名或密码不正确!");
			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
