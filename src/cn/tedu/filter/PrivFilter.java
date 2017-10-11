package cn.tedu.filter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import cn.tedu.bean.User;

public class PrivFilter implements Filter{
	private List<String> userList;
	private List<String> adminList;
	public void init(FilterConfig fc) throws ServletException {
		userList=new ArrayList<String>();
		adminList=new ArrayList<String>();
		try {
			String path=fc.getServletContext().getRealPath("/WEB-INF/user.txt");
			BufferedReader reader=new BufferedReader(new FileReader(path));
			String line=null;
			while((line=reader.readLine())!=null){
				userList.add(line);
			}
			path=fc.getServletContext().getRealPath("/WEB-INF/admin.txt");
			reader=new BufferedReader(new FileReader(path));
			while((line=reader.readLine())!=null){
				adminList.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();throw new RuntimeException(e);
		}
	}
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		String uri=req.getRequestURI();
		
		if(userList.contains(uri)||adminList.contains(uri)){//��ҪȨ��
			//�жϵ�ǰ�û��Ƿ�����Ӧ��Ȩ��
			Object userObj=req.getSession().getAttribute("user");
			if(userObj==null){//δ��¼
				req.getRequestDispatcher("/login.jsp").forward(request,response);
			}else{//�ѵ�¼,��ȡ�û���Ϣ
				String role=((User)userObj).getRole();
				//System.out.println(((User)userObj).getUsername()+":"+role);
				if("user".equals(role)&&userList.contains(uri)){
					chain.doFilter(request, response);
				}else if("admin".equals(role)&&adminList.contains(uri)){
					chain.doFilter(request, response);
				}else{//��ʾ������Ȩ���ʸ���Դ
					response.getWriter().write("����Ȩ���ʸ���Դ");
				}
			}
		}else{//����ҪȨ��
			chain.doFilter(request, response);
		}
	}
	public void destroy() {
		
	}
}
