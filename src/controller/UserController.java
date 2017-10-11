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
			model.addAttribute("msg", "��֤�벻��Ϊ��!");
			return "regist";
		}
		String code = (String) session.getAttribute("code");
		if (!valistr.equalsIgnoreCase(code)) {
			model.addAttribute("msg", "��֤�벻��ȷ");
			return "regist";
		}
		try {
			// 4.����JavaBean�е�checkData����У������
			user.checkData();

			// ��password md5���ܺ��ٴ������ݿ�
			user.setPassword(WebUtils.md5(user.getPassword()));

			// 5.ʵ��ע��(���û���Ϣ��������ݿ�)
			userMapper.registUser(user);
		} catch (MsgException e) {
			/* ��ȡ�׳��쳣��Ϣ, ����request��, ��ת��regist.jsp */
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
		if (user != null) {// �û���������ȷ
			// 4.ʵ�ּ�ס�û�������
			if ("true".equals(remname)) {
				Cookie cookie = new Cookie("remname", URLEncoder.encode(
						username, "utf-8"));
				cookie.setPath(request.getContextPath() + "/");
				cookie.setMaxAge(3600 * 24 * 30);
				response.addCookie(cookie);
			} else {// ȡ����ס�û���(ɾ��Cookie)
				Cookie cookie = new Cookie("remname", "");
				cookie.setPath(request.getContextPath() + "/");
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}

			if ("true".equals(autologin)) {// ʵ��30���Զ���½
				// ���û��������뱣���Cookie��
				Cookie c = new Cookie("autologin", URLEncoder.encode(username,
						"utf-8") + ":" + WebUtils.md5(password));
				c.setMaxAge(60 * 60 * 24 * 30);// ����Cookie30��
				c.setPath(request.getContextPath() + "/");
				// ��Cookie���͸������
				response.addCookie(c);
			} else {// ȡ��30���Զ���½
				Cookie c = new Cookie("autologin", "");
				c.setMaxAge(0);// ����Ϊ0����ɾ��
				c.setPath(request.getContextPath() + "/");
				response.addCookie(c);
			}

			// 6.���е�½
			request.getSession().setAttribute("user", user);

			// 7.��½�ɹ��ض�����ҳ
			return "index";
		} else {// �û������벻��ȷ
			request.setAttribute("msg", "�û��������벻��ȷ!");
			return "login";
		}

	}
}
