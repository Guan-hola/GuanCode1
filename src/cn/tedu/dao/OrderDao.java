package cn.tedu.dao;

import java.sql.Connection;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.tedu.bean.Order;
import cn.tedu.bean.OrderItem;
import cn.tedu.bean.SaleInfo;
public interface OrderDao extends Dao{
	
	/**
	 * 添加订单信息(orders表)
	 * @param order 订单信息
	 */
	void addOrder(Order order);
	
	/**
	 * 添加订单项信息(orderitem)
	 * @param orderItem
	 */
	void addOrderItem(OrderItem orderItem);
	
	/**
	 * 根据用户id查询当前用户的所有订单
	 * @param userId 用户id
	 * @return	List<Order>
	 */
	List<Order> findOrderByUserId(int userId);
	
	/**
	 * 根据订单id查询所有的订单项信息
	 * @param id
	 * @return
	 */
	List<OrderItem> findOrderItemByOrderId(String id);

	Order findOrderByOid(String oid);

	/**
	 * 根据订单号删除所有订单项
	 * @param oid
	 */
	void deleteOrderItemsByOid(String oid);

	/**
	 * 根据订单号删除订单
	 * @param oid
	 */
	void deleteOrderByOid(String oid);

	/**
	 * 根据oid修改订单支付状态
	 * @param oid
	 * @param paystate
	 */
	void updatePayStateByOid(String oid, int paystate);

	/**
	 * 查询全部的销售榜单
	 * @return 销售榜单列表
	 */
	List<SaleInfo> findSaleInfos();


}
