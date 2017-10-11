package cn.tedu.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class TranManager {
	
	private static ThreadLocal<Connection> tl=new ThreadLocal<Connection>(){
		protected Connection initialValue(){//-------每一次tl.get()得不到数据时，调用这个方法，创建一个对象
			return JDBCUtils.getConn();
		};
	};
	
	private TranManager(){}
	
	public static Connection getConn(){
		
		return tl.get();
	}
	
	public static void startTran(){
		try {
			tl.get().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void submitTran(){
		try {
			tl.get().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public  static void rollBack(){
		try {
			tl.get().rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//释放事务
	public static void releaseTran(){
		try {
			tl.get().close();
			//从当前线程的map中删除对应的数据库连接对象
			//删除的目的是：防止下一个用户使用该线程时遇到连接已关闭
			tl.remove();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
