package cn.tedu.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.tedu.bean.Product;
import cn.tedu.utils.BeanHandler;
import cn.tedu.utils.BeanListHandler;
import cn.tedu.utils.JDBCUtils;
@Repository
public class ProdDaoImpl implements ProdDao {
	public List<Product> findAll() {
		try {
			return JDBCUtils.query("select * from products",
					new BeanListHandler<Product>(Product.class));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public boolean updatePnum(String pid, int pnum) {
		try {
			return JDBCUtils.update("update products set pnum=? where id=?",
					pnum, pid) > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public boolean delProd(String pid) {
		String sql = "delete from products where id=?";
		try {
			return JDBCUtils.update(sql, pid) > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Product> findAllByCondition(String _name, String _category,
			double _minprice, double _maxprice) {
		try {
			String sql = "select * from products where name like ? and category like ? and price>=? and price<=?";
			return JDBCUtils.query(sql, new BeanListHandler<Product>(Product.class), 
					"%" + _name + "%", "%" + _category + "%", 
					_minprice, _maxprice);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Product> findAllBySearch(String search) {
		try {
			String sql = "select * from products where name like ? or category like ? or description like ?";
			return JDBCUtils.query(sql, new BeanListHandler<Product>(Product.class), "%"+search+"%", "%"+search+"%", "%"+search+"%");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Product findProdById(String pid) {
		String sql = "select * from products where id=?";
		try {
			return JDBCUtils.query(sql, new BeanHandler<Product>(Product.class), pid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void changePnum(String product_id, int buynum) {
		String sql = "update products set pnum=pnum+? where id=?";
		try {
			JDBCUtils.update(sql, buynum,product_id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


}
