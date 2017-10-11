package cn.tedu.web.back;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.bean.Product;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProdService;
/**
 * 负责查询所有的商品信息并显示在网页上
 */
public class BackProdListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 1.调用Service层的方法(findAll)查询所有商品, 
		 * 返回所有商品对象组成list集合 */
		ProdService service = BasicFactory.getFactory().getInstance(ProdService.class);
		List<Product> list =  service.findAll();
		
		/* 2.将所有商品的list集合存入request域 */
		request.setAttribute("list", list);
		
		/* 3.将所有商品的list集合通过转发带到prod_list.jsp进行展示 */
		request.getRequestDispatcher("/backend/prod_list.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
