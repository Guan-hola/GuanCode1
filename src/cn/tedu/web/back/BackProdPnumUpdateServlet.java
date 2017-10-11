package cn.tedu.web.back;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProdService;

public class BackProdPnumUpdateServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取商品的id和需要修改为的库存数量
		String pid = request.getParameter("pid");
		int pnum = Integer.parseInt(request.getParameter("pnum"));
		
		//2.调用service层的方法修改指定商品的库存数量
		ProdService service = BasicFactory.getFactory().getInstance(ProdService.class);
		boolean result = service.updatePnum(pid, pnum);
		
		//3.做出响应
		//true:表示修改成功!
		response.getWriter().write(result+"");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
