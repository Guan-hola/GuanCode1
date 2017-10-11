package cn.tedu.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class BeanListHandler<T> implements ResultSetHandler<List<T>>{
	private Class clazz;
	public BeanListHandler(Class clazz){
		/* 接受T类型的Class对象, 是为了便于下面剖析这个类, 
		 * 从而得出该类型中由哪些属性和方法 */
		//Account.class
		this.clazz = clazz;//保存在类的内部, 方便下面的方法使用
		
	}
	
	/* 将查询结果集中的每一行记录封装成一个Bean(T)对象, 将多个Bean对象组成一个List集合并返回 */
	public List<T> handle(ResultSet rs) throws Exception {
		List<T> list = new ArrayList<T>();
		while(rs.next()){
			//1.通过该类型的Class对象, 创建该类型的实例
			//Account acc = new Account();
			T t = (T) clazz.newInstance();
			
			//2.剖析T得出该类型中包含哪些公共的方法和属性
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			
			//2.1.根据beanInfo获取T类型中包含的属性描述器数组
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			
			//2.2.遍历属性描述器数组, 获取每一个属性描述器, 
				//再获取该属性描述器中包含的属性及相关的set和get方法
			for (PropertyDescriptor pd : pds) {
				//pd: id setId() getId() --> PropertyDescriptor
				//2.3.获取当前描述器中的属性名
				String name = pd.getName();//属性名: id	name money
				
				//2.4.获取当前描述器中属性相关的set方法
				Method method = pd.getWriteMethod();//setId() setName() setMoney()
				
				//2.5.获取结果集中第一行记录中的每一列的值
				//根据name(属性名)获取rs中指定列的值
				//acc.setId(rs.getObject("id"))
				try {
					//2.6.将结果集中的数据封装到Bean对象(t)中
					Object value=null;
					//如何判断当前属性类型
					
					//if(pd.getPropertyType()==Integer.TYPE){
					if(pd.getPropertyType()==int.class){//int  ==  Integer.TYPE
						value=rs.getInt(name);
					}else{
						value=rs.getObject(name);
					}
					method.invoke(t, value);
				} catch (Exception e) {
					continue;
				}
			}
			list.add(t);
		}
		return list.size() == 0 ? null : list;
	}
	
}
