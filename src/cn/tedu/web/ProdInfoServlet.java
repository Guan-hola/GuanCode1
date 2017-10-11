package cn.tedu.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.bean.Product;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProdService;
/**
 * 查询指定id的商品信息
 */
public class ProdInfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取所要查询的商品的id
		String pid = request.getParameter("pid");
		
		//2.调用service层的方法根据id查询指定的商品信息
		ProdService service = BasicFactory.getFactory().getInstance(ProdService.class);
		Product prod = service.findProdById(pid);
		
		//3.将商品对象存入request域中
		request.setAttribute("prod", prod);
		
		//4.通过转发将商品信息带到商品的详情页面(prod_info.jsp)
		request.getRequestDispatcher("/prod_info.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
