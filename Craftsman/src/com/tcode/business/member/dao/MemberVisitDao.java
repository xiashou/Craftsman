package com.tcode.business.member.dao;

import java.util.List;

import com.tcode.business.member.model.MemberVisit;

public interface MemberVisitDao {
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public MemberVisit loadById(Integer id) throws Exception;
	
	/**
	 * 分页查找客户回访列表
	 * @return
	 * @throws Exception
	 */
	public List<MemberVisit> loadVisitListByPage(MemberVisit visit, int start, int limit) throws Exception;
	
	/**
	 * 查总数
	 * @param visit
	 * @return
	 * @throws Exception
	 */
	public Integer loadListCount(MemberVisit visit) throws Exception;

	/**
	 * 获取保养到期回访列表
	 * @return
	 * @throws Exception
	 */
	public List<MemberVisit> loadVisitList1(String startDate, String endDate) throws Exception;
	
	/**
	 * 获取保险到期回访列表
	 * @return
	 * @throws Exception
	 */
	public List<MemberVisit> loadVisitList2(String startDate, String endDate) throws Exception;
	
	/**
	 * 获取年检到期回访列表
	 * @return
	 * @throws Exception
	 */
	public List<MemberVisit> loadVisitList3(String startDate, String endDate) throws Exception;
	
	/**
	 * 客户流失回访列表
	 * @param companyId
	 * @param lastDate
	 * @return
	 * @throws Exception
	 */
	public List<MemberVisit> loadVisitList4(String lastDate) throws Exception;
	
	/**
	 * 客户剩余服务到期回访列表
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<MemberVisit> loadVisitList5(String startDate, String endDate) throws Exception;
	
	/**
	 * 客户余额不足列表
	 * @return
	 * @throws Exception
	 */
	public List<MemberVisit> loadVisitList6() throws Exception;
	
	/**
	 * 根据店铺和类型查询回访总数
	 * @return
	 * @throws Exception
	 */
	public Integer loadVisitCount(String deptCode, Integer typeId) throws Exception;
	
	/**
	 * 更新保养回访数据
	 * @param now
	 * @param endDate
	 * @throws Exception
	 */
	public void editVisit1(String now, String endDate) throws Exception;
	
	/**
	 * 更新保险回访数据
	 * @param now
	 * @param endDate
	 * @throws Exception
	 */
	public void editVisit2(String now, String endDate) throws Exception;
	
	/**
	 * 更新年检回访数据
	 * @param now
	 * @param endDate
	 * @throws Exception
	 */
	public void editVisit3(String now, String endDate) throws Exception;
	
	/**
	 * 更新客户流失回访数据
	 * @param now
	 * @param endDate
	 * @throws Exception
	 */
	public void editVisit4(String now, String endDate) throws Exception;
	
	/**
	 * 更新剩余服务回访数据
	 * @param now
	 * @throws Exception
	 */
	public void editVisit5(String now) throws Exception;
	
	/**
	 * 更新余额不足回访数据
	 * @param now
	 * @throws Exception
	 */
	public void editVisit6(String now) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(MemberVisit visit) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(MemberVisit visit) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(MemberVisit visit) throws Exception;
	
	/**
	 * 删除指定时间之前的回访
	 * @param oldDate
	 * @throws Exception
	 */
	public void removeOldVisit(String oldDate) throws Exception;
}
