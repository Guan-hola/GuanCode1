package cn.tedu.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 * ServletContext生命周期监听
 * ServletContext对象创建时立即执行contextInitialized
 * ServletContext对象销毁时立即执行contextDestroyed
 * 
 */
public class MyServletContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		//可以通过事件对象(sce)获取当前事件源
		ServletContext context = sce.getServletContext();
		//context.getContextPath() 相当于 request.getContextPath()
		context.setAttribute("app", context.getContextPath());
		System.out.println("当前WEB应用的虚拟路径已被存入ServletContext域中...");
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}

}
