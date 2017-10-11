package cn.tedu.service;

import java.util.List;

import cn.tedu.anno.Tran;
import cn.tedu.bean.Order;
import cn.tedu.bean.OrderInfo;
import cn.tedu.bean.OrderItem;
import cn.tedu.bean.SaleInfo;
import cn.tedu.exception.MsgException;

public interface OrderService extends Service{
	
	/**
	 * 增加订单
	 * @param order 订单信息
	 * @param list	订单项信息
	 * @throws MsgException 
	 */
	@Tran
	void addOrder(Order order, List<OrderItem> list) throws MsgException;
	
	/**
	 * 根据用户id查询所有的订单信息
	 * @param id
	 * @return
	 */
	List<OrderInfo> findOrderByUserId(int id);

	/**
	 * 根据订单id删除订单相关的信息（orders，orderitem，还原商品库存products）
	 * @param oid
	 * @throws MsgException 要删除的订单不存在或订单已支付
	 */
	void deleteOrderByOid(String oid) throws MsgException;

	/**
	 * 通过oid查询订单
	 * @param oid
	 * @return order
	 */
	Order findOrderByOid(String oid);

	/**
	 * 修改订单支付状态
	 * @param oid
	 * @param i
	 */
	void UpdatePayStateByOid(String oid, int paystate);

	/**
	 * 查询全部的销售榜单
	 * @return 销售榜单列表
	 */
	List<SaleInfo> findSaleInfos();

}
