package cn.tedu.web.back;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.bean.SaleInfo;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.OrderService;

public class DownloadServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.创建业务层对象
		OrderService os=BasicFactory.getFactory().getInstance(OrderService.class);
		
		List<SaleInfo> saleInfos = os.findSaleInfos();
		
		StringBuffer buf=new StringBuffer("商品id,商品名称,销售数量\n");
		for(SaleInfo si:saleInfos){
			String prod_id = si.getProd_id();
			String prod_name = si.getProd_name();
			int sale_sum = si.getSale_num();
			
			buf.append(prod_id).append(",").append(prod_name).append(",").append(sale_sum).append("\n");
		}
		//准备文件名称
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fname="SaleList"+sdf.format(date)+".csv";
		
		//6.告知浏览器以附件下载方式接受
		response.setHeader("Content-Disposition", "attachment;filename="+fname);
		response.setContentType("text/html;charset=gbk");
		//7.使用response输出流将文件传给浏览器
		response.getWriter().write(buf.toString());
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
