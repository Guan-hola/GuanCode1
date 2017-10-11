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
	 * ʵ��ע��
	 * @param user
	 * @throws MsgException 
	 */
	public void registUser(User user) throws MsgException {
		//1.����û����Ƿ����
		boolean result = dao.findUserByUsername(user.getUsername());
		if(result){//�û����Ѵ���
			throw new MsgException("�û����Ѵ���");
		}
		
		//2.ʵ��ע��(�������ݵ����ݿ�)
		dao.addUser(user);
	}
	
	/**
	 * ʵ�ֵ�½
	 * @param username �û���
	 * @param password ����
	 * @return User����
	 */
	public User loginUser(String username, String password) {
		return dao.findUserByUsernameAndPassword(username, password);
	}
	
	/**
	 * �����û�����ѯ�û��Ƿ����
	 * @param username �û���
	 * @return true��ʾ�û����Ѵ���, false��ʾ������
	 */
	public boolean hasUser(String username) {
		return dao.findUserByUsername(username);
	}
	
}
