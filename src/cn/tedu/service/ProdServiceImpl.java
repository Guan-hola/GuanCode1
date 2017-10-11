package cn.tedu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.bean.Product;
import cn.tedu.dao.ProdDao;
import cn.tedu.factory.BasicFactory;
@Service
public class ProdServiceImpl implements ProdService {
	@Autowired
	private ProdDao dao;
	public List<Product> findAll() {
		return dao.findAll();
	}
	public boolean updatePnum(String pid, int pnum) {
		return dao.updatePnum(pid, pnum);
	}
	public boolean delProd(String pid) {
		return dao.delProd(pid);
	}
	public List<Product> findAllByCondition(String _name, String _category,
			double _minprice, double _maxprice) {
		return dao.findAllByCondition(_name, _category, _minprice, _maxprice);
	}
	public List<Product> findAllBySearch(String search) {
		return dao.findAllBySearch(search);
	}
	public Product findProdById(String pid) {
		return dao.findProdById(pid);
	}
	
	
}
