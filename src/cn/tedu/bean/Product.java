package cn.tedu.bean;

import java.io.Serializable;

public class Product implements Serializable {
	private String id;
	private String name;
	private double price;
	private String category;
	private String imgurl;
	private int	pnum;
	private String description;
	/* 重写hashcode方法 */
	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}
	/* 重写equals方法 */
	@Override
	public boolean equals(Object obj) {
		//1.如果obj为null, 直接返回false
		if(obj == null){
			return false;
		}
		//2.如果obj == this, 直接返回true
		if(obj == this){
			return true;
		}
		
		//3.obj指向的对象的类型不是Product类型
		if(!(obj instanceof Product)){
			return false;
		}
		
		//4.比较两个对象的id值是否相等
		Product other = (Product) obj;
		if(id != null && id.equals(other.getId())){
			return true;
		}
		return false;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public int getPnum() {
		return pnum;
	}
	public void setPnum(int pnum) {
		this.pnum = pnum;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price
				+ ", category=" + category + ", imgurl=" + imgurl + ", pnum="
				+ pnum + ", description=" + description + "]";
	}
}
