package com.tcode.business.goods.dao;

import java.util.List;

import com.tcode.business.goods.model.GoodsPackageDetail;

public interface GoodsPackageDetailDao {

	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<GoodsPackageDetail> loadAll(String packageId) throws Exception;
	
	/**
	 * 根据主键查询套餐详情
	 * @param id
	 * @param itemNo
	 * @return
	 */
	public GoodsPackageDetail loadById(String id, Integer itemNo) throws Exception;
	
	/**
	 * 根据类型删除
	 * @throws Exception
	 */
	public void removeById(GoodsPackageDetail packageDetail) throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param param, start,limit
	 * @return List<packageDetail>
	 * @throws Exception
	 */
	public List<GoodsPackageDetail> loadListPage(GoodsPackageDetail packageDetail, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param GoodsPackageDetail
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(GoodsPackageDetail packageDetail) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(GoodsPackageDetail packageDetail) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(GoodsPackageDetail packageDetail) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(GoodsPackageDetail packageDetail) throws Exception;
}
