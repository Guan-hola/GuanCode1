package cn.tedu.utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;



import cn.tedu.bean.Product;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtils {
	private static ComboPooledDataSource pool = new ComboPooledDataSource();
	private JDBCUtils(){}
	
	/**
	 * 获取一个连接池实例
	 * @return DataSource
	 */
	public static DataSource getPool(){
		return pool;
	}
	/**
	 * 从连接池中获取一个连接
	 * @return Connection
	 */
	public static Connection getConn(){
		try {
			return pool.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/* query查询方法 */
	public static <T> T query(String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = TranManager.getConn();
			ps = conn.prepareStatement(sql);
			if(params != null){//设置sql参数
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i+1, params[i]);//注意i+1
				}
			}
			rs = ps.executeQuery();
			return rsh.handle(rs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			close(null, ps, rs);
		}
	}
	
	/* update增删改方法 */
	public static int update(String sql, Object... params) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = TranManager.getConn();
			ps = conn.prepareStatement(sql);
			if(params != null){
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i+1, params[i]);
				}
			}
			return ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			close(null, ps, rs);
		}
	}
	
	
	/**
	 * 释放资源
	 * @param conn 连接对象
	 * @param stat 传输器对象
	 * @param rs 结果集对象
	 */
	public static void close(Connection conn, Statement stat, ResultSet rs){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				rs = null;
			}
		}
		if(stat != null){
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				stat = null;
			}
		}
		if(conn != null){
			try {
				/*
				 * 如果连接是通过c3p0连接池获取的, conn.close
				 * 只是将连接还回连接池中, 并不关闭连接!! 
				 */
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				conn = null;
			}
		}
	}
	
	
}
