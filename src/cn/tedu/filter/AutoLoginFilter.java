package cn.tedu.filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import cn.tedu.bean.User;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.UserService;

public class AutoLoginFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("AutoLoginFilter.doFilter().......");
		/* 自动登陆的必要条件: 
		 * 1.用户必须是未登陆状态
		 * 2.用户必须勾选了30天自动登陆, 即Cookie中包含自动登陆Cookie
		 * 3.自动登陆Cookie中的用户名和密码必须要正确
		 * 4.无论是否自动登陆都必须要放行过滤器
		 */
		
		//1.判断用户是否为未登录状态
		HttpServletRequest req = (HttpServletRequest) request;
		if(req.getSession().getAttribute("user") == null){
			//表明用户为未登录状态
			//2.判断Cookie中是否包含自动登陆Cookie
			Cookie[] cs = req.getCookies();
			if( cs != null){
				for (Cookie c : cs) {
					if("autologin".equals(c.getName())){
						//3.判断自动登陆Cookie中的用户名和密码是否正确
						String value = c.getValue();//"admin:123"
						String username = value.split(":")[0];
						//对用户名进行url解码
						username = URLDecoder.decode(username,"utf-8");
						String password = value.split(":")[1];
						UserService service = BasicFactory.getFactory().getInstance(UserService.class);
						User user = service.loginUser(username, password);
						if(user != null){//用户名和密码正确
							//帮用户做登陆操作
							req.getSession().setAttribute("user", user);
						}
					}
					
				}
			}
		}
		//4.无论是否自动登陆, 都需要放行过滤器执行后面的操作!
		chain.doFilter(request, response);
	}

	public void destroy() {
	}

}
