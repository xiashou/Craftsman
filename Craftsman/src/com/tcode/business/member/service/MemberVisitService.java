package com.tcode.business.member.service;

import java.util.List;

import com.tcode.business.member.model.MemberVisit;

public interface MemberVisitService {
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public MemberVisit getById(Integer id) throws Exception;
	
	/**
	 * 分页查找客户回访列表
	 * @return
	 * @throws Exception
	 */
	public List<MemberVisit> getVisitListByPage(MemberVisit visit, int start, int limit) throws Exception;
	
	/**
	 * 查总数
	 * @return
	 * @throws Exception
	 */
	public Integer getVisitListCount(MemberVisit visit) throws Exception;

	/**
	 * 获取保养到期回访列表
	 * @return
	 * @throws Exception
	 */
	public List<MemberVisit> getVisitList1(String startDate, String endDate) throws Exception;
	
	/**
	 * 获取保险到期回访列表
	 * @return
	 * @throws Exception
	 */
	public List<MemberVisit> getVisitList2(String startDate, String endDate) throws Exception;
	
	/**
	 * 获取年检到期回访列表
	 * @return
	 * @throws Exception
	 */
	public List<MemberVisit> getVisitList3(String startDate, String endDate) throws Exception;
	
	/**
	 * 客户流失回访列表
	 * @param lastDate 为60天之前的日期
	 * @return
	 * @throws Exception
	 */
	public List<MemberVisit> getVisitList4(String lastDate) throws Exception;
	
	/**
	 * 客户剩余服务到期回访列表
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<MemberVisit> getVisitList5(String startDate, String endDate) throws Exception;
	
	/**
	 * 客户余额不足回访列表
	 * @return
	 * @throws Exception
	 */
	public List<MemberVisit> getVisitList6() throws Exception;
	
	/**
	 * 根据店铺和类型查询回访总数
	 * @return
	 * @throws Exception
	 */
	public Integer getVisitCount(String deptCode, Integer typeId) throws Exception;
	
	/**
	 * 更新保养回访数据
	 * @param now
	 * @param endDate
	 * @throws Exception
	 */
	public void updateVisit1(String now, String endDate) throws Exception;
	
	/**
	 * 更新保险回访数据
	 * @param now
	 * @param endDate
	 * @throws Exception
	 */
	public void updateVisit2(String now, String endDate) throws Exception;
	
	/**
	 * 更新年检回访数据
	 * @param now
	 * @param endDate
	 * @throws Exception
	 */
	public void updateVisit3(String now, String endDate) throws Exception;
	
	/**
	 * 更新客户流失回访数据
	 * @param now
	 * @param endDate
	 * @throws Exception
	 */
	public void updateVisit4(String now, String endDate) throws Exception;
	
	/**
	 * 更新剩余服务回访数据
	 * @param now
	 * @throws Exception
	 */
	public void updateVisit5(String now) throws Exception;
	
	/**
	 * 更新余额不足回访数据
	 * @param now
	 * @throws Exception
	 */
	public void updateVisit6(String now) throws Exception;
	
	/**
	 * 删除指定时间之前的回访
	 * @param oldDate
	 * @throws Exception
	 */
	public void deleteOldVisit(String oldDate) throws Exception;
	
	/**
	 * 添加
	 * @param MemberVisit
	 * @throws Exception
	 */
	public void insert(MemberVisit visit) throws Exception;
	
	/**
	 * 修改
	 * @param MemberVisit
	 * @throws Exception
	 */
	public void update(MemberVisit visit) throws Exception;
	
	/**
	 * 删除
	 * @param MemberVisit
	 * @throws Exception
	 */
	public void delete(MemberVisit visit) throws Exception;
}
