package com.tcode.business.member.dao;

import java.util.List;

import com.tcode.business.member.model.MemberStock;

public interface MemberStockDao {

	/**
	 * 根据会员ID查询名下所有库存
	 * @param memId
	 * @return
	 * @throws Exception
	 */
	public List<MemberStock> loadByMemId(Integer memId) throws Exception;
	
	/**
	 * 根据会员ID查询名下所有库存详细信息
	 * @param memId
	 * @return
	 * @throws Exception
	 */
	public List<MemberStock> loadDetailByMemId(Integer memId) throws Exception;
	
	/**
	 * 查询店铺会员库存
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<MemberStock> loadListByDept(String deptCode, Integer memId) throws Exception;
	
	/**
	 * 查询店铺会员库存总数
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public Integer loadListCountByDept(String deptCode, Integer memId) throws Exception;
	
	/**
	 * 根据ID查找
	 * @param memId
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public MemberStock loadById(Integer memId, String goodsId, String endDate, String source) throws Exception;
	
	/**
	 * 添加或者扣减库存数
	 * @throws Exception
	 */
	public void editStockNumber(Integer memId, String goodsId, String endDate, Double number, String source) throws Exception;
	
	/**
	 * 存储过程更新会员库存
	 * @param memStock
	 * @throws Exception
	 */
	public void editStockByProc(MemberStock memStock) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(MemberStock memberStock) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(MemberStock memberStock) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(MemberStock memberStock) throws Exception;
	
	/**
	 * 根据ID查找对应的库存
	 * @param memId
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public double loadGoodsNum(Integer memId, String goodsId) throws Exception;
}
