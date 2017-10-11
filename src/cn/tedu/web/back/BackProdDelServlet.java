package cn.tedu.web.back;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProdService;
/**
 * 处理删除商品请求
 */
public class BackProdDelServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取商品的id
		String pid = request.getParameter("pid");
		
		//2.调用service层的方法根据商品id删除指定的商品
		ProdService service = BasicFactory.getFactory().getInstance(ProdService.class);
		boolean result = service.delProd(pid);
		
		//3.做出响应
		response.getWriter().write(result+"");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
