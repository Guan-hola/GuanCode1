package cn.tedu.bean;
import java.util.Map;
public class OrderInfo {
	private Order order;//订单信息
	private Map<Product, Integer> map;//该订单中的所有订单项信息
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Map<Product, Integer> getMap() {
		return map;
	}
	public void setMap(Map<Product, Integer> map) {
		this.map = map;
	}
}
