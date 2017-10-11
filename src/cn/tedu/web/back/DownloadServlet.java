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
		//1.����ҵ������
		OrderService os=BasicFactory.getFactory().getInstance(OrderService.class);
		
		List<SaleInfo> saleInfos = os.findSaleInfos();
		
		StringBuffer buf=new StringBuffer("��Ʒid,��Ʒ����,��������\n");
		for(SaleInfo si:saleInfos){
			String prod_id = si.getProd_id();
			String prod_name = si.getProd_name();
			int sale_sum = si.getSale_num();
			
			buf.append(prod_id).append(",").append(prod_name).append(",").append(sale_sum).append("\n");
		}
		//׼���ļ�����
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fname="SaleList"+sdf.format(date)+".csv";
		
		//6.��֪������Ը������ط�ʽ����
		response.setHeader("Content-Disposition", "attachment;filename="+fname);
		response.setContentType("text/html;charset=gbk");
		//7.ʹ��response��������ļ����������
		response.getWriter().write(buf.toString());
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
