package com.tcode.business.member.dao;

import java.util.List;

import com.tcode.business.member.model.MemberCar;

public interface MemberCarDao {

	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<MemberCar> loadAll() throws Exception;

	/**
	 * 根据id查找
	 * @param id
	 * @return MemberCar
	 * @throws Exception
	 */
	public MemberCar loadById(Integer id) throws Exception;
	
	/**
	 * 根据会员ID查找
	 * @param memberId
	 * @return List<MemberCar>
	 * @throws Exception
	 */
	public List<MemberCar> loadByMemberId(Integer memberId) throws Exception;
	
	/**
	 * 根据车牌号码查找
	 * @param carShort,code,number
	 * @return List<MemberCar>
	 * @throws Exception
	 */
	public MemberCar loadByCarNumber(String companyId, String carShort, String code, String number) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(MemberCar car) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(MemberCar car) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(MemberCar car) throws Exception;
	
	/**
	 * 根据会员ID批量删除
	 * @param ids
	 * @throws Exception
	 */
	public Integer removeByMemIds(String ids) throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param param, start, limit
	 * @return List<MemberCar>
	 * @throws Exception
	 */
	public List<MemberCar> loadListPage(MemberCar car, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param param
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(MemberCar car) throws Exception;
	
	/**
	 * 统计店铺车辆总数
	 * @param deptCode
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadCarCountByDept(String deptCode) throws Exception;
	
	/**
	 * 根据当前时间获取满足相差指定时间服务（保险、保养、年检）的车辆
	 * @param apartTime
	 * @param type 类型 7-保险 6-保养 9-年检
	 * @return
	 * @throws Exception
	 */
	public List<MemberCar> loadListByApartNextTime(int apartTime, int type) throws Exception;
}
