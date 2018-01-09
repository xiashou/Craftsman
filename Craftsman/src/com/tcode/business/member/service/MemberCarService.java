package com.tcode.business.member.service;

import java.util.List;

import com.tcode.business.member.model.MemberCar;

public interface MemberCarService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return MemberCar
	 * @throws Exception
	 */
	public MemberCar getMemberCarById(Integer id) throws Exception;
	
	/**
	 * 根据会员ID查询车辆
	 * @param memberId
	 * @return List<MemberCar>
	 * @throws Exception
	 */
	public List<MemberCar> getMemberCarByMemberId(Integer memberId) throws Exception;
	
	/**
	 * 根据车牌号查找车辆
	 * @param id
	 * @return MemberCar
	 * @throws Exception
	 */
	public MemberCar getCarByNumber(String companyId, String carShort, String code, String number) throws Exception;
	
	/**
	 * 添加
	 * @param MemberCar
	 * @throws Exception
	 */
	public void insert(MemberCar car) throws Exception;
	
	/**
	 * 修改
	 * @param MemberCar
	 * @throws Exception
	 */
	public void update(MemberCar car) throws Exception;
	
	/**
	 * 删除
	 * @param MemberCar
	 * @throws Exception
	 */
	public void delete(MemberCar car) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param button, start, limit
	 * @return List<MemberCar>
	 * @throws Exception
	 */
	public List<MemberCar> getListPage(MemberCar car, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param button
	 * @return List<MemberCar>
	 * @throws Exception
	 */
	public Integer getListCount(MemberCar car) throws Exception;
	
	/**
	 * 统计店铺车辆总数
	 * @param deptCode
	 * @return Integer
	 * @throws Exception
	 */
	public Integer getCarCountByDept(String deptCode) throws Exception;
	
	/**
	 * 根据当前时间获取满足相差指定时间服务（保险、保养、年检）的车辆
	 * @param apartTime
	 * @param type 类型 7-保险 6-保养 9-年检
	 * @return
	 * @throws Exception
	 */
	public List<MemberCar> getListByApartNextTime(int apartTime, int type) throws Exception;
}
