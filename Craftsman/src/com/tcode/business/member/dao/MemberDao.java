package com.tcode.business.member.dao;

import java.util.List;

import com.tcode.business.member.model.Member;

public interface MemberDao {

	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<Member> loadAll() throws Exception;

	/**
	 * 根据id查找
	 * @param id
	 * @return Member
	 * @throws Exception
	 */
	public Member loadById(Integer id) throws Exception;
	
	/**
	 * 根据手机号码查找
	 * @param id
	 * @return Member
	 * @throws Exception
	 */
	public Member loadByMobile(String companyId, String mobile) throws Exception;
	
	/**
	 * 根据手机号码查找
	 * @param id
	 * @return Member
	 * @throws Exception
	 */
	public Member loadByMobile( String mobile) throws Exception;
	
	/**
	 * 根据手机号和门店编码码查找
	 * @param deptCode
	 * @param mobile
	 * @return Member
	 * @throws Exception
	 */
	public Member loadByMobileAndDeptCode(String deptCode, String mobile) throws Exception;
	
	/**
	 * 根据手机号和公司ID查找
	 * @param companyId
	 * @param mobile
	 * @return Member
	 * @throws Exception
	 */
	public Member loadByMobileAndCompanyId(String companyId, String mobile) throws Exception;
	
	/**
	 * 根据会员卡号查找
	 * @param vipNo
	 * @return Member
	 * @throws Exception
	 */
	public List<Member> loadByVipNo(String vipNo) throws Exception;
	
	/**
	 * 根据关键字查找会员
	 * @param keyword
	 * @return List<Member>
	 * @throws Exception
	 */
	public List<Member> loadByKeywordPage(String companyId, String keyword, int start, int limit) throws Exception;
	
	/**
	 * 根据关键字查找会员总数
	 * @param keyword
	 * @return List<Member>
	 * @throws Exception
	 */
	public Integer loadByKeywordCount(String companyId, String keyword) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(Member member) throws Exception;
	
	/**
	 * 添加或修改
	 * @throws Exception
	 */
	public void saveOrUpdate(Member member) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(Member member) throws Exception;
	
	/**
	 * 更新会员积分
	 * @throws Exception
	 */
	public void editMemberPoint(Integer memId, Integer point) throws Exception;
	
	/**
	 * 更新会员余额
	 * @throws Exception
	 */
	public void editMemberBalance(Integer memId, Double balance) throws Exception;
	
	/**
	 * 更改会员等级
	 * @param memId
	 * @param grade
	 * @throws Exception
	 */
	public void editMemberGrade(Integer memId, Integer grade) throws Exception;
	
	/**
	 * 更新会员相册
	 * @throws Exception
	 */
	public void editMemberAlbum(Integer memId, Integer imageId) throws Exception;
	
	/**
	 * 删除会员相册中照片
	 * @throws Exception
	 */
	public void removeMemberAlbum(Integer memId, Integer imageId) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(Member member) throws Exception;
	
	/**
	 * 批量删除
	 * @param ids
	 * @throws Exception
	 */
	public Integer removeByIds(String ids) throws Exception;
	
	/**
	 * 根据id数组精确查找会员
	 * @param ids
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<Member> loadListByIdsPage(Integer[] ids, int start, int limit) throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param param, start,limit
	 * @return List<Member>
	 * @throws Exception
	 */
	public List<Member> loadListPage(Member member, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param param
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(Member member) throws Exception;
	
	/**
	 * 统计店铺会员总数
	 * @param deptCode
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadMemberCountByDept(String deptCode) throws Exception;
	
	/**
	 * 统计店铺会员总余额
	 * @param deptCode
	 * @return Double
	 * @throws Exception
	 */
	public Double loadMemberBalanceSumByDept(String deptCode) throws Exception;
	
	/**
	 * 统计店铺会员总积分
	 * @param deptCode
	 * @return Double
	 * @throws Exception
	 */
	public Integer loadMemberPointSumByDept(String deptCode) throws Exception;
	
	/**
	 * 根据当前年月（MM/dd）得到会员信息
	 * @param MMdd
	 * @return
	 * @throws Exception
	 */
	public List<Member> loadListByMMdd(String MMdd)  throws Exception;
	
	/**
	 * 根据appId和openId获取会员信息
	 * @param appId
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public List<Member> loadByAppIdOpenId(String appId, String openId) throws Exception;
	
	/**
	 * 会员增长量
	 * @param companyId
	 * @param date
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public Integer loadDayGrowth(String companyId, String date, String deptCode) throws Exception;
}
