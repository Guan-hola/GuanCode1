package controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.tedu.bean.User;
import cn.tedu.exception.MsgException;
import cn.tedu.utils.WebUtils;

@Controller
public class UserController {
	@Autowired
	UserMapper userMapper;

	@RequestMapping("RegistServlet")
	public String requestAction(String valistr, HttpSession session,
			HttpServletResponse response, User user, Model model) {
		if (WebUtils.isNull(valistr)) {
			model.addAttribute("msg", "验证码不能为空!");
			return "regist";
		}
		String code = (String) session.getAttribute("code");
		if (!valistr.equalsIgnoreCase(code)) {
			model.addAttribute("msg", "验证码不正确");
			return "regist";
		}
		try {
			// 4.调用JavaBean中的checkData方法校验数据
			user.checkData();

			// 对password md5加密后再存入数据库
			user.setPassword(WebUtils.md5(user.getPassword()));

			// 5.实现注册(将用户信息保存进数据库)
			userMapper.registUser(user);
		} catch (MsgException e) {
			/* 获取抛出异常信息, 存入request域, 并转发regist.jsp */
			model.addAttribute("msg", e.getMessage());
			return "regist";
		}

		return null;
	}

	@RequestMapping("LoginServlet")
	public String loginAction(String autologin, String username,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response, String password, String remname,
			Model model) throws IOException, ServletException {

		User user = userMapper.loginUser(username, password);
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

			if ("true".equals(autologin)) {// 实现30天自动登陆
				// 将用户名和密码保存进Cookie中
				Cookie c = new Cookie("autologin", URLEncoder.encode(username,
						"utf-8") + ":" + WebUtils.md5(password));
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
			return "index";
		} else {// 用户名密码不正确
			request.setAttribute("msg", "用户名或密码不正确!");
			return "login";
		}

	}
}
