package cn.tedu.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.tedu.bean.Order;
import cn.tedu.bean.OrderItem;
import cn.tedu.bean.SaleInfo;
import cn.tedu.utils.BeanHandler;
import cn.tedu.utils.BeanListHandler;
import cn.tedu.utils.JDBCUtils;
@Repository
public class OrderDaoImpl implements OrderDao {

	public void addOrder(Order order) {
		String sql = "insert into orders values(?,?,?,?,null,?)";
		try {
			JDBCUtils.update(sql, order.getId(), order.getMoney(),
					order.getReceiverinfo(), order.getPaystate(),
					order.getUser_id());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void addOrderItem(OrderItem orderItem) {
		String sql = "insert into orderitem values(?,?,?)";
		try {
			JDBCUtils.update(sql, orderItem.getOrder_id(),
					orderItem.getProduct_id(),
					orderItem.getBuynum());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	public List<Order> findOrderByUserId(int userId) {
		String sql = "select * from orders where user_id=?";
		try {
			return JDBCUtils.query(sql, new BeanListHandler<Order>(Order.class), userId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<OrderItem> findOrderItemByOrderId(String orderId) {
		try {
		String sql = "select * from orderitem where order_id=?";
		return JDBCUtils.query(sql, new BeanListHandler<OrderItem>(OrderItem.class), orderId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Order findOrderByOid(String oid) {
		
		try {
			String sql="select * from orders where id=?";
			return JDBCUtils.query(sql, new BeanHandler<Order>(Order.class), oid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void deleteOrderItemsByOid(String oid) {
		try {
			String sql="delete from orderitem where order_id=?";
			JDBCUtils.update(sql, oid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void deleteOrderByOid(String oid) {
		try {
			String sql="delete from orders where id=?";
			JDBCUtils.update(sql, oid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void updatePayStateByOid(String oid, int paystate) {
		try {
			String sql="update orders set paystate=? where id=?";
			JDBCUtils.update(sql, paystate,oid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<SaleInfo> findSaleInfos() {
		try {
			String sql="select pd.id prod_id, pd.name prod_name,sum( oi.buynum ) sale_num  "
								+"from orderitem oi, products pd, orders os "
								+"where oi.product_id=pd.id "
								+"and oi.order_id=os.id "
								+"and os.paystate=1 "
								+"group by prod_id "
								+"order by sale_num desc ";
			return JDBCUtils.query(sql, new BeanListHandler<SaleInfo>(SaleInfo.class));
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<SaleInfo>();
		}
	}


}
