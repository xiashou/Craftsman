package com.tcode.business.member.service;

import java.util.List;

import com.tcode.business.member.model.MemberStock;

public interface MemberStockService {

	/**
	 * 根据ID查找
	 * @param memId
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public MemberStock getById(Integer memId, String goodsId, String endDate, String source) throws Exception;
	
	/**
	 * 根据会员ID查找该会员库存
	 * @param memId
	 * @return
	 * @throws Exception
	 */
	public List<MemberStock> getListByMemId(Integer memId, String deptCode) throws Exception;
	
	/**
	 * 查询门店会员库存
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<MemberStock> getListByDept(String deptCode, Integer memId) throws Exception;
	
	/**
	 * 查询门店会员库存总数
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public Integer getListCountByDept(String deptCode, Integer memId) throws Exception;
	
	/**
	 * 根据会员ID查找该会员详细库存
	 * @param memId
	 * @return
	 * @throws Exception
	 */
	public List<MemberStock> getListDetailByMemId(Integer memId) throws Exception;
	
	/**
	 * 更新会员库存
	 * @param memId
	 * @param goodsId
	 * @param number
	 * @throws Exception
	 */
	public void updateStockNumber(MemberStock memberStock) throws Exception;
	
	/**
	 * 存储过程更新会员库存
	 * @param memberStock
	 * @throws Exception
	 */
	public void updateStockByProc(MemberStock memberStock) throws Exception;
	
	/**
	 * 存储过程批量更新会员库存
	 * @param memberStock
	 * @throws Exception
	 */
	public void updateMoreStockByProc(List<MemberStock> msList) throws Exception;
	
	/**
	 * 批量更新会员库存
	 * @param List<MemberStock>
	 * @throws Exception
	 */
	public void updateMoreStockNumber(List<MemberStock> msList) throws Exception;
	
	/**
	 * 添加
	 * @param MemberStock
	 * @throws Exception
	 */
	public void insert(MemberStock memberStock) throws Exception;
	
	/**
	 * 修改
	 * @param MemberStock
	 * @throws Exception
	 */
	public void update(MemberStock memberStock) throws Exception;
	
	/**
	 * 删除
	 * @param MemberStock
	 * @throws Exception
	 */
	public void delete(MemberStock memberStock) throws Exception;
	
	/**
	 * 根据ID查找对应的库存
	 * @param memId
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public double getGoodsNum(Integer memId, String goodsId) throws Exception;
}
