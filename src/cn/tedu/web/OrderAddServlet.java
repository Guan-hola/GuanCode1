package cn.tedu.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.bean.Order;
import cn.tedu.bean.OrderItem;
import cn.tedu.bean.Product;
import cn.tedu.bean.User;
import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.OrderService;
/**
 * 增加一个订单
 */
public class OrderAddServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 添加订单只能是登陆之后的用户才可以进行的操作
		 * 后期需要加一个过滤器进行拦截, 如果用户登陆了就可以下订单
		 * 如果没有登陆则需要跳转到登陆页面, 登陆之后再下订单
		 */
		//1.获取当前登陆用户
		User user = (User) request.getSession().getAttribute("user");
		
		//2.获取订单信息, 封装进一个Order Bean中
		Order order = new Order();
		order.setId(UUID.randomUUID().toString());
		
		order.setReceiverinfo(request.getParameter("receiverinfo"));
		order.setPaystate(0);//支付状态 0表示未支付
		order.setUser_id(user.getId());
		
		
		//3.获取当前订单中的所有订单项信息
		/*
		 * 一个订单中可以包含多个商品, 而一个商品对应一个订单项
		 * 所以一个订单中可以对应多个订单项, 当前订单中所有的订单项
		 * 可以用一个list集合来保存
		 * 从购物map中取出要购买的商品信息, 存入OrderItem中
		 * 再将多个OrderItem存入list集合中
		 */
		List<OrderItem> list = new ArrayList<OrderItem>();
		Map<Product, Integer> cartmap = (Map<Product, Integer>) 
				request.getSession().getAttribute("cartmap");
		//4.从购物map中取出要购买的商品信息, 存入OrderItem中
		double totalMoney = 0;
		for(Map.Entry<Product, Integer> entry : cartmap.entrySet()){
			OrderItem item = new OrderItem();
			item.setOrder_id(order.getId());//订单编号
			item.setProduct_id(entry.getKey().getId());//商品编号
			item.setBuynum(entry.getValue());//购买数量
			
			/*
			 * 订单金额 = 所有商品金额的总和 
			 * 商品订单金额 = 单价 * 数量
			 */
			totalMoney += entry.getKey().getPrice() * entry.getValue();
			order.setMoney(totalMoney);//手动计算订单金额
			
			//5.再将多个OrderItem存入list集合中
			list.add(item);
		}
		
		//6.调用service层的方法, 实现添加订单功能
		OrderService service = BasicFactory.getFactory().getInstance(OrderService.class);
		try {
			service.addOrder(order, list);
			//7.将购物车中的商品清空
			cartmap.clear();//清空map中的所有数据
			
			//8.订单添加成功, 跳转到订单列表页面
			response.sendRedirect(request.getContextPath()+"/servlet/OrderListServlet");
			//response.sendRedirect(request.getContextPath()+"/index.jsp");
		} catch (MsgException e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/cart.jsp").forward(request, response);
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
