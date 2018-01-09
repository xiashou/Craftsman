package com.tcode.business.wechat.sys.dao;

import java.util.List;

import com.tcode.business.wechat.sys.model.WechatAuthorizerParams;
import com.tcode.business.wechat.sys.vo.WechatAuthorizerParamsVo;

public interface WechatAuthorizerParamsDao {
	
	/**
	 * 添加
	 * @param wechatAuthorizerParams
	 * @throws Exception
	 */
	public void save(WechatAuthorizerParams wechatAuthorizerParams) throws Exception;
	
	/**
	 * 修改
	 * @param wechatAuthorizerParams
	 * @throws Exception
	 */
	public void edit(WechatAuthorizerParams wechatAuthorizerParams) throws Exception;
	
	/**
	 * 删除
	 * @param wechatAuthorizerParams
	 * @throws Exception
	 */
	public void remove(WechatAuthorizerParams wechatAuthorizerParams) throws Exception;
	
	/**
	 * 根据id查找
	 * @param id
	 * @return WechatAuthorizerParams
	 * @throws Exception
	 */
	public WechatAuthorizerParams loadById(Integer id) throws Exception;
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<WechatAuthorizerParams> loadAll() throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param wechatAuthorizerParams
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<WechatAuthorizerParams> loadListPage(WechatAuthorizerParamsVo wechatAuthorizerParamsVo, int start, int limit) throws Exception;
	
	/**
	 * 
	 * 根据条件查找条目数
	 * @param wechatAuthorizerParams
	 * @return
	 * @throws Exception
	 */
	public Integer loadListCount(WechatAuthorizerParamsVo wechatAuthorizerParamsVo) throws Exception;
	
	/**
	 * 根据自定义授权码sid获取授权方参数信息
	 * @param sid 自定义授权码
	 * @return
	 * @throws Exception
	 */
	public List<WechatAuthorizerParams> loadBySId(String sid) throws Exception;
	
	/**
	 * 根据授权状态获取授权方参数信息
	 * @param authorizerStatus
	 * @return
	 * @throws Exception
	 */
	public List<WechatAuthorizerParams> loadByAuthorizerStatus(int authorizerStatus) throws Exception;
	
	/**
	 * 根据授权方appid获取授权方参数信息
	 * @param authorizerAppId
	 * @return
	 * @throws Exception
	 */
	public List<WechatAuthorizerParams> loadByAuthorizerAppId(String authorizerAppId) throws Exception;
	
	/**
	 * 根据授权方门店编码获取授权方参数信息
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<WechatAuthorizerParams> loadByDeptCode(String deptCode) throws Exception;
	
	/**
	 * 根据授权方公司ID获取授权方参数信息
	 * @param companyId
	 * @return
	 * @throws Exception
	 */
	public List<WechatAuthorizerParams> loadByCompanyId(String companyId) throws Exception;
	
	/**
	 * 根据自定授权码获取授权方参数信息
	 * @param sid
	 * @return
	 * @throws Exception
	 */
	public List<WechatAuthorizerParams> loadBySid(String sid) throws Exception;

}
