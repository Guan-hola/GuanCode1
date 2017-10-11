package cn.tedu.factory;

import java.io.FileInputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Properties;

import cn.tedu.dao.Dao;
import cn.tedu.service.Service;
import cn.tedu.utils.TranManager;
import cn.tedu.anno.Tran;
/**
 * 通用的工厂类
 */
public class BasicFactory {
	private static BasicFactory factory = new BasicFactory();
	private static Properties prop = new Properties();
	public static BasicFactory getFactory() {
		return factory;
	}

	private BasicFactory() {
	}

	static {
		// 读取配置文件中配置的信息
		try {
			String confPath = BasicFactory.class.getClassLoader()
					.getResource("conf.properties").getPath();
			prop.load(new FileInputStream(confPath));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/*
	 * 根据conf.properties配置文件中的配置信息 创建UserDao的实例 UserDao=cn.tedu.dao.UserDaoImpl
	 */
	public <T> T getInstance(Class<T> clazz) {
		try {
			// 1.读取配置文件中配置的信息(UserDao的实现类)
			// cn.tedu.dao.UserDaoImpl
			String className = prop.getProperty(clazz.getSimpleName());

			// 2.根据类的全限定名称获得该类的Class对象
			Class clz = Class.forName(className);

			//4.区分t是Service层还是Dao层
			if(Service.class.isAssignableFrom(clz)){
				//Service层
				// 3.利用反射技术根据该类Class对象创建该类的实例
				final T t = (T) clz.newInstance();
				//创建代理对象
				@SuppressWarnings("unchecked")
				T proxy =(T) Proxy.newProxyInstance(clz.getClassLoader(), clz.getInterfaces(), new InvocationHandler() {
					
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						//判断method是否使用事务的注解@Tran
						Object result = null;
						if(method.isAnnotationPresent(Tran.class)){
							//使用了事务
							try {
								TranManager.startTran();
								//执行委托类对象（真实对象）的方法
								result=method.invoke(t, args);
								TranManager.submitTran();
								
							} catch (InvocationTargetException e) {
								e.printStackTrace();
								TranManager.rollBack();
								System.out.println("已经rollback");
								throw e.getTargetException();
							} finally{
								TranManager.releaseTran();
							}
							
						}else{//未使用事务
							try {
								result=method.invoke(t, args);
							} catch (InvocationTargetException e) {
								e.printStackTrace();
								throw e.getTargetException();
							} finally{
								TranManager.releaseTran();
							}
						}
						return result;
					}
				});
				return proxy;
			}else if(Dao.class.isAssignableFrom(clz)){
				//Dao层
				return (T)clz.newInstance();
			}else{
				//都不是
				System.out.println("别捣乱");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
}
