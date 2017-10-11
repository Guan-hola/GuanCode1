package cn.tedu.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.OrderService;

public class OrderDelServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取要删除的订单号
		String oid = request.getParameter("oid");
		
		//2.创建业务层对象
		OrderService orderService=BasicFactory.getFactory().getInstance(OrderService.class);
		
		//3.调用service层方法
		try {
			orderService.deleteOrderByOid(oid);
			//4.删除成功
			response.getWriter().write("订单删除成功，2秒后跳转");
		} catch (MsgException e) {
			//4.删除失败
			response.getWriter().write("订单删除失败，2秒后跳转");
		}
		//5.设置自动跳转
		response.setHeader("refresh", "2;url="+request.getContextPath()+"/servlet/OrderListServlet");
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
