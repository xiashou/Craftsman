package com.tcode.business.member.service;

import java.util.List;

import com.tcode.business.member.model.Member;

public interface MemberService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return Member
	 * @throws Exception
	 */
	public Member getMemberById(Integer id) throws Exception;
	
	/**
	 * 根据手机号码查找
	 * @param id
	 * @return Member
	 * @throws Exception
	 */
	public Member getMemberByMobile(String companyId, String mobile) throws Exception;
	
	/**
	 * 根据手机号码查找
	 * @param id
	 * @return Member
	 * @throws Exception
	 */
	public Member getByMobile( String mobile) throws Exception;
	
	/**
	 * 根据手机号和门店编码码查找
	 * @param deptCode
	 * @param mobile
	 * @return Member
	 * @throws Exception
	 */
	public Member getByMobileAndDeptCode(String deptCode, String mobile) throws Exception;
	
	/**
	 * 根据手机号和公司ID查找
	 * @param companyId
	 * @param mobile
	 * @return Member
	 * @throws Exception
	 */
	public Member getByMobileAndCompanyId(String companyId, String mobile) throws Exception;
	
	/**
	 * 根据会员卡号查找
	 * @param vipNo
	 * @return Member
	 * @throws Exception
	 */
	public List<Member> getMemberByVipNo(String vipNo) throws Exception;
	
	/**
	 * 根据关键字查找会员
	 * @param keyword
	 * @return List<Member>
	 * @throws Exception
	 */
	public List<Member> getMemberByKeywordPage(String companyId, String keyword, int start, int limit) throws Exception;
	
	/**
	 * 根据关键字查找会员总数
	 * @param keyword
	 * @return Integer
	 * @throws Exception
	 */
	public Integer getMemberByKeywordCount(String companyId, String keyword) throws Exception;
	
	/**
	 * 批量添加会员
	 * @param memberList
	 * @return
	 * @throws Exception
	 */
	public Integer insertMoreMember(List<Member> memberList) throws Exception;
	
	/**
	 * 添加
	 * @param Member
	 * @throws Exception
	 */
	public void insert(Member member) throws Exception;
	
	/**
	 * 修改
	 * @param Member
	 * @throws Exception
	 */
	public void update(Member member) throws Exception;
	
	/**
	 * 更新会员相册
	 * @param Member
	 * @throws Exception
	 */
	public void updateMemberAlbum(Integer memId, Integer imageId) throws Exception;
	
	/**
	 * 删除相册图片
	 * @param memId
	 * @param imageId
	 * @throws Exception
	 */
	public void deleteMemberAlbum(Integer memId, Integer imageId) throws Exception;
	
	/**
	 * 删除
	 * @param Member
	 * @throws Exception
	 */
	public void delete(Member member) throws Exception;
	
	/**
	 * 批量删除(先删除车辆，在删除会员， 不可恢复)
	 * @param ids
	 * @throws Exception
	 */
	public void deleteByIds(Integer[] idStr) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param button, start, limit
	 * @return List<Member>
	 * @throws Exception
	 */
	public List<Member> getListPage(Member member, int start, int limit) throws Exception;
	
	/**
	 * 批量发送短信
	 * @param member
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	public Integer sendMemberSms(Member member, Integer[] ids, String msg) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param button
	 * @return List<Member>
	 * @throws Exception
	 */
	public Integer getListCount(Member member) throws Exception;
	
	/**
	 * 统计店铺会员总数
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public Integer getMemberCountByDept(String deptCode) throws Exception;
	
	/**
	 * 统计店铺会员总余额
	 * @param deptCode
	 * @return Double
	 * @throws Exception
	 */
	public Double getMemberBalanceSumByDept(String deptCode) throws Exception;
	
	/**
	 * 统计店铺会员总积分
	 * @param deptCode
	 * @return Double
	 * @throws Exception
	 */
	public Integer getMemberPointSumByDept(String deptCode) throws Exception;
	
	/**
	 * 根据当前年月（MM/dd）得到会员信息
	 * @param MMdd
	 * @return
	 * @throws Exception
	 */
	public List<Member> getListByMMdd(String MMdd)  throws Exception;
	
	/**
	 * 根据appId和openId获取会员信息
	 * @param appId
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public List<Member> getByAppIdOpenId(String appId, String openId) throws Exception;
	
	/**
	 * 会员增长量
	 * @param companyId
	 * @param date
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public Integer getDayGrowth(String companyId, String date, String deptCode) throws Exception;
}
