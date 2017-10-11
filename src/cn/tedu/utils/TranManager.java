package cn.tedu.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class TranManager {
	
	private static ThreadLocal<Connection> tl=new ThreadLocal<Connection>(){
		protected Connection initialValue(){//-------ÿһ��tl.get()�ò�������ʱ�������������������һ������
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
	
	//�ͷ�����
	public static void releaseTran(){
		try {
			tl.get().close();
			//�ӵ�ǰ�̵߳�map��ɾ����Ӧ�����ݿ����Ӷ���
			//ɾ����Ŀ���ǣ���ֹ��һ���û�ʹ�ø��߳�ʱ���������ѹر�
			tl.remove();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
