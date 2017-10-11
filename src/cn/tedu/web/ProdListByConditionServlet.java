package cn.tedu.web;

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
 * 根据条件查询所有符合条件的商品
 * 如果没有查询条件, 按照默认值查询所有商品
 */
public class ProdListByConditionServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 1.获取查询条件(商品的名称(name), 商品的分类(category))
		 * 		商品的最低价格(minprice)及最高价格(maxprice)
		 */
		String name = request.getParameter("name");
		String category = request.getParameter("category");
		String minprice = request.getParameter("minprice");
		String maxprice = request.getParameter("maxprice");
		
		//2.对查询条件进行处理
		String _name = "";
		String _category = "";
		double _minprice = 0;
		double _maxprice = Double.MAX_VALUE;
		if(name != null && !"".equals(name)){
			_name = name;
		}
		if(category != null && !"".equals(category)){
			_category = category;
		}
		String reg = "^(0(\\.[0-9]+)?)|([1-9][0-9]*(\\.[0-9]+)?)$";//校验小数
		//"0(\\.[0-9]+)?" --> 0.1 0.01 0.11
		//"[1-9][0-9]*(\\.[0-9]+)?"  1.1 1.01 1.11
		if(minprice != null && !"".equals(minprice)){
			//"0(\\.[0-9]+)?" --> 0.1 0.01 0.11
			//"[1-9][0-9]*(\\.[0-9]+)?"  1.1 1.01 1.11
			if(minprice.matches(reg)){
				_minprice = Double.parseDouble(minprice);
			}
		}
		if(maxprice != null && !"".equals(maxprice)){
			//maxprice必须是一个正整数或者小数
			if(maxprice.matches(reg) && Double.parseDouble(maxprice)>=_minprice){
				_maxprice = Double.parseDouble(maxprice);
			}
		}
		
		//3.调用service层的方法根据条件查询所有符合条件的商品
		ProdService service = BasicFactory.getFactory().getInstance(ProdService.class);
		List<Product> list = service.findAllByCondition(_name, _category, _minprice, _maxprice);
		
		//4.将保存所有商品的list集合存入request域
		request.setAttribute("list", list);
		
		//5.通过转发将所有商品带到前台的商品列表页面
		request.getRequestDispatcher("/prod_list.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
