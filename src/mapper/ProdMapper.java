package mapper;

import java.util.List;

import cn.tedu.bean.Product;

public interface ProdMapper {
	/**
	 * 查询所有商品信息, 返回所有商品组成的list集合
	 * @return List<Product>
	 */
	List<Product> findAll();
	/**
	 * 修改指定商品的库存数量
	 * @param pid	商品id
	 * @param pnum	商品的库存数量
	 * @return boolean true 修改成功!
	 */
	boolean updatePnum(String pid, int pnum);
	/**
	 * 根据商品的id删除指定的商品
	 * @param pid	商品的id
	 * @return	boolean true表示删除成功!
	 */
	boolean delProd(String pid);
	
	/**
	 * 根据条件查询所有符合条件的商品集合
	 * @param _name	商品的名称
	 * @param _category	商品的分类
	 * @param _minprice	商品的最低价格
	 * @param _maxprice 商品的最高价格
	 * @return List<Product>
	 */
	List<Product> findAllByCondition(String _name, String _category,
			double _minprice, double _maxprice);
	
	/**
	 * 根据条件查询素所有符合条件的商品
	 * @param search 查询条件(商品名称/商品分类/商品描述)
	 * @return List<Product>
	 */
	List<Product> findAllBySearch(String search);
	/**
	 * 根据商品的id查询指定的商品
	 * @param pid	商品id
	 * @return	Product
	 */
	Product findProdById(String pid);
	
	/**
	 * 根据商品id恢复库存
	 * @param product_id
	 * @param buynum
	 */
	void changePnum(String product_id, int buynum);
	
}
