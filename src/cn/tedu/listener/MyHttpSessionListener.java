package cn.tedu.listener;

import java.util.HashMap;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import cn.tedu.bean.Product;
/*
 * 在session对象创建之后立即将cartmap(Map集合)存入session中
 */
public class MyHttpSessionListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent se) {
		se.getSession().setAttribute("cartmap", 
				new HashMap<Product, Integer>());
	}
	public void sessionDestroyed(HttpSessionEvent se) {
	}

}
