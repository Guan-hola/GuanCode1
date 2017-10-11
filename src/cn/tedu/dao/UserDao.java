package cn.tedu.dao;
import cn.tedu.bean.User;
public interface UserDao extends Dao{
	
	/**
	 * 根据用户名查询用户是否存在
	 * @param username 用户名
	 * @return true 表示用户名已存在 false表示用户名不存在
	 */
	public boolean findUserByUsername(String username);
	/**
	 * 将用户注册信息保存进数据库中
	 * @param user User对象
	 */
	public void addUser(User user);
	
	/**
	 * 根据用户名和密码查询用户
	 * @param username 用户名
	 * @param password 密码
	 * @return User对象
	 */
	public User findUserByUsernameAndPassword(String username, String password);
}
