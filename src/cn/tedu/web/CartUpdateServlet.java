package cn.tedu.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.bean.Product;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProdService;

/**
 * 将商品及购买数量加入购物车 删除购物车中指定的商品
 */
public class CartUpdateServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.获取所要加入购物车的商品id及购买数量
		String pid = request.getParameter("pid");
		int buyNum = Integer.parseInt(request.getParameter("buyNum"));

		// 2.调用service层的方法根据id查询商品信息
		ProdService service = BasicFactory.getFactory().getInstance(
				ProdService.class);
		Product prod = service.findProdById(pid);

		// 3.获取session中保存商品的购物车map
		Map<Product, Integer> map = (Map<Product, Integer>) request
				.getSession().getAttribute("cartmap");

		// 4.将商品及购买数量加入购物车
		/*
		 * 在将商品加入购物车时, 如果该商品在购物车中已经存在了, 那么购买数量应该是 "之前的购买数量"+"现在要加入的购买数量"
		 * 如果是第一次将商品加入购物车, 直接将商品和对应的购买数量加入即可!
		 */
		//如果商品的购买数量小于0 则在购物车中删除该商品
		if(buyNum < 0){
			map.remove(prod);
		}else{
			map.put(prod, map.containsKey(prod) ? 
					map.get(prod) + buyNum : buyNum);
		}

		// 5.重定向到购物车列表页面
		response.sendRedirect(request.getContextPath() + "/cart.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
