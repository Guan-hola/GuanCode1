package cn.tedu.dao;

import java.sql.Connection;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.tedu.bean.Order;
import cn.tedu.bean.OrderItem;
import cn.tedu.bean.SaleInfo;
public interface OrderDao extends Dao{
	
	/**
	 * ��Ӷ�����Ϣ(orders��)
	 * @param order ������Ϣ
	 */
	void addOrder(Order order);
	
	/**
	 * ��Ӷ�������Ϣ(orderitem)
	 * @param orderItem
	 */
	void addOrderItem(OrderItem orderItem);
	
	/**
	 * �����û�id��ѯ��ǰ�û������ж���
	 * @param userId �û�id
	 * @return	List<Order>
	 */
	List<Order> findOrderByUserId(int userId);
	
	/**
	 * ���ݶ���id��ѯ���еĶ�������Ϣ
	 * @param id
	 * @return
	 */
	List<OrderItem> findOrderItemByOrderId(String id);

	Order findOrderByOid(String oid);

	/**
	 * ���ݶ�����ɾ�����ж�����
	 * @param oid
	 */
	void deleteOrderItemsByOid(String oid);

	/**
	 * ���ݶ�����ɾ������
	 * @param oid
	 */
	void deleteOrderByOid(String oid);

	/**
	 * ����oid�޸Ķ���֧��״̬
	 * @param oid
	 * @param paystate
	 */
	void updatePayStateByOid(String oid, int paystate);

	/**
	 * ��ѯȫ�������۰�
	 * @return ���۰��б�
	 */
	List<SaleInfo> findSaleInfos();


}
