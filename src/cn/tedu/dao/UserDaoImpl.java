package cn.tedu.dao;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import cn.tedu.bean.User;
import cn.tedu.utils.BeanHandler;
import cn.tedu.utils.JDBCUtils;
@Repository
public class UserDaoImpl implements UserDao {
	
	public boolean findUserByUsername(String username) {
		try {
			User user = JDBCUtils.query("select * from user where username=?", new BeanHandler<User>(User.class), username);
			return user != null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	public void addUser(User user) {
		try {
			JDBCUtils.update("insert into user values(null, ?,?,?,?,?)", 
					user.getUsername(),
					user.getPassword(),
					user.getNickname(),
					user.getEmail());
					user.getRole();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	public User findUserByUsernameAndPassword(String username, String password) {
		try {
			User user = JDBCUtils.query("select * from user where username=? and password=?", new BeanHandler<User>(User.class), username, password);
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
