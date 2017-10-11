package cn.tedu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.bean.User;
import cn.tedu.dao.UserDao;
import cn.tedu.exception.MsgException;
import cn.tedu.factory.BasicFactory;
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao dao;
	
	/**
	 * 实现注册
	 * @param user
	 * @throws MsgException 
	 */
	public void registUser(User user) throws MsgException {
		//1.检查用户名是否存在
		boolean result = dao.findUserByUsername(user.getUsername());
		if(result){//用户名已存在
			throw new MsgException("用户名已存在");
		}
		
		//2.实现注册(保存数据到数据库)
		dao.addUser(user);
	}
	
	/**
	 * 实现登陆
	 * @param username 用户名
	 * @param password 密码
	 * @return User对象
	 */
	public User loginUser(String username, String password) {
		return dao.findUserByUsernameAndPassword(username, password);
	}
	
	/**
	 * 根据用户名查询用户是否存在
	 * @param username 用户名
	 * @return true表示用户名已存在, false表示不存在
	 */
	public boolean hasUser(String username) {
		return dao.findUserByUsername(username);
	}
	
}
