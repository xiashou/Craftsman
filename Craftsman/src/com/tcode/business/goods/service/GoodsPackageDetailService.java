package com.tcode.business.goods.service;

import java.util.List;

import com.tcode.business.goods.model.GoodsHour;
import com.tcode.business.goods.model.GoodsPackageDetail;

public interface GoodsPackageDetailService {

	/**
	 * 根据套餐ID查询所有套餐内内容
	 * @return
	 * @throws Exception
	 */
	public List<GoodsPackageDetail> getGoodsPackageDetailById(String packageId) throws Exception;
	
	
	/**
	 * 添加
	 * @param GoodsPackageDetail
	 * @throws Exception
	 */
	public void insert(GoodsPackageDetail goodsPackageDetail) throws Exception;
	
	/**
	 * 根据主键查询套餐详情
	 * @param id
	 * @param itemNo
	 * @return
	 * @throws Exception
	 */
	public GoodsPackageDetail getPackageDetailById(String id, Integer itemNo) throws Exception;
	
	/**
	 * 修改
	 * @param GoodsPackageDetail
	 * @throws Exception
	 */
	public void update(GoodsPackageDetail goodsPackageDetail) throws Exception;
	
	/**
	 * 删除
	 * @param GoodsPackageDetail
	 * @throws Exception
	 */
	public void delete(GoodsPackageDetail goodsPackageDetail) throws Exception;
	
	/**
	 * 根据类型删除
	 * @param GoodsPackageDetail
	 * @throws Exception
	 */
	public void deleteById(GoodsPackageDetail goodsPackageDetail) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param button, start, limit
	 * @return List<GoodHour>
	 * @throws Exception
	 */
	public List<GoodsPackageDetail> getListPage(GoodsPackageDetail goodsPackageDetail, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param button
	 * @return List<GoodHour>
	 * @throws Exception
	 */
	public Integer getListCount(GoodsPackageDetail goodsPackageDetail) throws Exception;
	
	/**
	 * 根据关键字 查找(带类型名)
	 * @param deptCode
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public List<GoodsHour> getGoodsByKeyword(String deptCode, String companyId, String keyword) throws Exception;
}
