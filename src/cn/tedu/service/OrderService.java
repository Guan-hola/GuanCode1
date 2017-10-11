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
	 * ���Ӷ���
	 * @param order ������Ϣ
	 * @param list	��������Ϣ
	 * @throws MsgException 
	 */
	@Tran
	void addOrder(Order order, List<OrderItem> list) throws MsgException;
	
	/**
	 * �����û�id��ѯ���еĶ�����Ϣ
	 * @param id
	 * @return
	 */
	List<OrderInfo> findOrderByUserId(int id);

	/**
	 * ���ݶ���idɾ��������ص���Ϣ��orders��orderitem����ԭ��Ʒ���products��
	 * @param oid
	 * @throws MsgException Ҫɾ���Ķ��������ڻ򶩵���֧��
	 */
	void deleteOrderByOid(String oid) throws MsgException;

	/**
	 * ͨ��oid��ѯ����
	 * @param oid
	 * @return order
	 */
	Order findOrderByOid(String oid);

	/**
	 * �޸Ķ���֧��״̬
	 * @param oid
	 * @param i
	 */
	void UpdatePayStateByOid(String oid, int paystate);

	/**
	 * ��ѯȫ�������۰�
	 * @return ���۰��б�
	 */
	List<SaleInfo> findSaleInfos();

}
