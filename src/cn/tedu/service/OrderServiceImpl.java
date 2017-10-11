package cn.tedu.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.bean.Order;
import cn.tedu.bean.OrderInfo;
import cn.tedu.bean.OrderItem;
import cn.tedu.bean.Product;
import cn.tedu.bean.SaleInfo;
import cn.tedu.dao.OrderDao;
import cn.tedu.dao.ProdDao;
import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;
import cn.tedu.utils.JDBCUtils;
import cn.tedu.utils.TranManager;
@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderDao order_dao;
	@Autowired
	private ProdDao prod_dao;
	public void addOrder(Order order, List<OrderItem> list) throws MsgException {
		//--------------------此事务方案不能解决产生垃圾订单的问题，因为事务的开启，提交，回滚和dao层的四个方法中使用的数据库连接对象不是同一个，需要将conn传递到每个dao层方法--------------------//


			//调用dao层的方法添加订单信息
			order_dao.addOrder(order);
			for(OrderItem orderItem : list){
				//检查购买数量(orderItem.buyNum)是否小于等于库存数量(Product.pnum)
				//获取购买数量
				int buyNum = orderItem.getBuynum();
				
				//获取库存数量
				//>>获取商品id
				String pid = orderItem.getProduct_id();
				//>>查询商品信息
				Product prod = prod_dao.findProdById(pid);
				int pnum = prod.getPnum();
				if(buyNum>pnum){//如果购买数量大于库存数量
					throw new MsgException("库存数量不足, 订单添加失败! id:"+pid+",name:"+prod.getName()+"剩余库存:"+prod.getPnum());
				}
				
				//调用dao层的方法添加订单项信息
				order_dao.addOrderItem(orderItem);
				
				//将购买数量从库存数量中扣除
				prod_dao.updatePnum( pid, prod.getPnum()-buyNum);
			}
		
		
		
		
	}
	
	public List<OrderInfo> findOrderByUserId(int userId)  {
		//1.通过Userid查询当前用户的所有订单信息
		List<Order> orderList = order_dao.findOrderByUserId(userId);
		
		if(orderList == null ){
			return null;
		}
		
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		for (Order order : orderList) {
			//2.遍历每一个订单, 通过订单id查询当前订单中包含的所有订单项信息
			List<OrderItem> orderItemList = order_dao
					.findOrderItemByOrderId(order.getId());
			
			//3.遍历每一个订单项, 通过订单项获取商品信息及商品的购买数量
			Map<Product, Integer> map = new HashMap<Product, Integer>();
			
			if(orderItemList!=null){
				for(OrderItem orderItem : orderItemList){
					//3.1.获取商品id, 通过商品id查询商品信息, 返回Product对象
					Product prod = prod_dao.findProdById(orderItem.getProduct_id());
					//3.2.获取购买数量
					int buyNum = orderItem.getBuynum();
					//3.3.将商品信息和购买数量存入map中
					map.put(prod, buyNum);
				}
			}
			
			//4.将订单信息和所有的订单项信息存入OrderInfo中
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setOrder(order);
			orderInfo.setMap(map);
			
			//5.将一个完整的订单信息存入List集合中
			orderInfoList.add(orderInfo);
		}
		return orderInfoList;
	}

	public void deleteOrderByOid(String oid) throws MsgException {
		//1.根据订单id查询订单信息
		System.out.println(oid);
		Order order = order_dao.findOrderByOid(oid);
		//2.判断是否存在
		if(order==null){
			throw new MsgException("当前订单不存在，已删除");
		}
		//3.判断是否支付，支付则不可删除
		if(order.getPaystate()!=0){
			throw new MsgException("当前订单已支付，不可删除");
		}
		//4.根据oid查询当前订单的所有条目orderItemLis'，并获取buyNum
		List<OrderItem> orderItemList= order_dao.findOrderItemByOrderId(oid);
		
		//5.遍历itemList
		for(OrderItem item:orderItemList){
			//6.恢复商品库存
			prod_dao.changePnum(item.getProduct_id(),item.getBuynum());
		}
		
		order_dao.deleteOrderItemsByOid(oid);
		
		order_dao.deleteOrderByOid(oid);
		
		
	}

	public Order findOrderByOid(String oid) {
		// TODO Auto-generated method stub
		return order_dao.findOrderByOid(oid);
	}

	public void UpdatePayStateByOid(String oid, int paystate) {
		order_dao.updatePayStateByOid(oid,paystate);
		
	}

	public List<SaleInfo> findSaleInfos() {
		return order_dao.findSaleInfos();
		
	}

}
