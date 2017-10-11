package cn.tedu.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.bean.Order;
import cn.tedu.factory.BasicFactory;
import cn.tedu.service.OrderService;
import cn.tedu.utils.PaymentUtil;
import cn.tedu.utils.PropUtils;

public class PayServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.接收订单id
		String oid=request.getParameter("oid");
		
		//2.准备第三方支付平台参数
		String p0_Cmd="Buy";//业务类型，固定值Buy
		String p1_MerId=PropUtils.getProperties("p1_MerId");//商户编号，唯一身份标识
		String p2_Order=oid;//商户订单号
							/*OrderService os=BasicFactory.getFactory().getInstance(OrderService.class);
							Order order=os.findOrderByOid(oid);
							String p3_Amt=order.getMoney();*/
		String p3_Amt="0.01";//支付金额，元，精确到分，为空则无法直连
		String p4_Cur="CNY";//交易币种，固定值CNY
		String p5_Pid="";//商品名称，用于支付时显示在易宝支付网关左侧的订单产品信息.中文注意转码
		String p6_Pcat="";//商品种类
		String p7_Pdesc="";//商品描述
		String p8_Url=PropUtils.getProperties("responseURL");//商户接收支付成功数据的地址，易宝会发送两次成功通知，要带参数
		String p9_SAF="0";//送货地址，“1”需要用户将送货地址留在易宝，“0”不需要，默认为“0”
		String  pa_MP="";//商户拓展信息
		String pd_FrpId=request.getParameter("pd_FrpId");//支付编码通道
		String pr_NeedResponse="1";//应答机制，固定值为“1”
		//调用工具类生成数据签名
		String hmac=PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, PropUtils.getProperties("keyValue"));//签名数据
		
		//3.将以上参数保存进request
		request.setAttribute("pd_FrpId", pd_FrpId);
		request.setAttribute("p0_Cmd", p0_Cmd);
		request.setAttribute("p1_MerId", p1_MerId);
		request.setAttribute("p2_Order", p2_Order);
		request.setAttribute("p3_Amt", p3_Amt);
		request.setAttribute("p4_Cur", p4_Cur);
		request.setAttribute("p5_Pid", p5_Pid);
		request.setAttribute("p6_Pcat", p6_Pcat);
		request.setAttribute("p7_Pdesc", p7_Pdesc);
		request.setAttribute("p8_Url", p8_Url);
		request.setAttribute("p9_SAF", p9_SAF);
		request.setAttribute("pa_MP", pa_MP);
		request.setAttribute("pr_NeedResponse", pr_NeedResponse);
		request.setAttribute("hmac", hmac);
		
		request.getRequestDispatcher("/confirm.jsp").forward(request,response);
		 
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
