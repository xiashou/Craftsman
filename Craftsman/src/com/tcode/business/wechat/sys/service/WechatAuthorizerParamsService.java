package com.tcode.business.wechat.sys.service;

import java.util.List;

import com.tcode.business.wechat.sys.model.WechatAuthorizerParams;
import com.tcode.business.wechat.sys.vo.WechatAuthorizerParamsVo;

public interface WechatAuthorizerParamsService {
	
	/**
	 * 添加
	 * @param wechatAuthorizerParams
	 * @throws Exception
	 */
	public void insert(WechatAuthorizerParams wechatAuthorizerParams) throws Exception;
	
	/**
	 * 修改
	 * @param wechatAuthorizerParams
	 * @throws Exception
	 */
	public void update(WechatAuthorizerParams wechatAuthorizerParams) throws Exception;
	
	/**
	 * 删除
	 * @param wechatAuthorizerParams
	 * @throws Exception
	 */
	public void delete(WechatAuthorizerParams wechatAuthorizerParams) throws Exception;
	
	/**
	 * 根据id查找
	 * @param id
	 * @return WechatAuthorizerParams
	 * @throws Exception
	 */
	public WechatAuthorizerParams getById(Integer id) throws Exception;
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<WechatAuthorizerParams> getAll() throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param wechatAuthorizerParams
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<WechatAuthorizerParams> getListPage(WechatAuthorizerParamsVo wechatAuthorizerParamsVo, int start, int limit) throws Exception;
	
	/**
	 * 
	 * 根据条件查找条目数
	 * @param wechatAuthorizerParams
	 * @return
	 * @throws Exception
	 */
	public Integer getListCount(WechatAuthorizerParamsVo wechatAuthorizerParamsVo) throws Exception;
	
	/**
	 * 根据自定义授权码sid获取授权方参数信息
	 * @param sid 自定义授权码
	 * @return
	 * @throws Exception
	 */
	public List<WechatAuthorizerParams> getBySId(String sid) throws Exception;
	
	/**
	 * 根据授权状态获取授权方参数信息
	 * @param authorizerStatus
	 * @return
	 * @throws Exception
	 */
	public List<WechatAuthorizerParams> getByAuthorizerStatus(int authorizerStatus) throws Exception;
	
	/**
	 * 根据授权方appid获取授权方参数信息
	 * @param authorizerAppId
	 * @return
	 * @throws Exception
	 */
	public List<WechatAuthorizerParams> getByAuthorizerAppId(String authorizerAppId) throws Exception;
	
	/**
	 * 根据授权方门店编码获取授权方参数信息
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<WechatAuthorizerParams> getByDeptCode(String deptCode) throws Exception;
	
	/**
	 * 根据授权方公司ID获取授权方参数信息
	 * @param companyId
	 * @return
	 * @throws Exception
	 */
	public List<WechatAuthorizerParams> getByCompanyId(String companyId) throws Exception;
	
	/**
	 * 根据自定授权码获取授权方参数信息
	 * @param sid
	 * @return
	 * @throws Exception
	 */
	public List<WechatAuthorizerParams> loadBySid(String sid) throws Exception;

}
