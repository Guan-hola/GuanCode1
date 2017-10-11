package mapper;

import cn.tedu.bean.User;
import cn.tedu.exception.MsgException;

public interface UserMapper {
	/**
	 * 实现注册
	 * @param user
	 * @throws MsgException 
	 */
	public void registUser(User user) throws MsgException;
	
	/**
	 * 实现登陆
	 * @param username 用户名
	 * @param password 密码
	 * @return User对象
	 */
	
	public User loginUser(String username, String password);
	/**
	 * 根据用户名查询用户是否存在
	 * @param username 用户名
	 * @return true表示用户名已存在, false表示不存在
	 */
	public boolean hasUser(String username);

}
