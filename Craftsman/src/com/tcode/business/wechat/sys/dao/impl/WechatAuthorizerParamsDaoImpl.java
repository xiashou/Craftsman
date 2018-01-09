package com.tcode.business.wechat.sys.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.wechat.sys.dao.WechatAuthorizerParamsDao;
import com.tcode.business.wechat.sys.model.WechatAuthorizerParams;
import com.tcode.business.wechat.sys.vo.WechatAuthorizerParamsVo;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("wechatAuthorizerParamsDao")
public class WechatAuthorizerParamsDaoImpl extends BaseDao<WechatAuthorizerParams, Serializable>
		implements WechatAuthorizerParamsDao {

	@Override
	public List<WechatAuthorizerParams> loadListPage(WechatAuthorizerParamsVo wechatAuthorizerParamsVo, int start,
			int limit) throws Exception {
		List<WechatAuthorizerParams> list = null;
		DetachedCriteria criteria = connectionCriteria(wechatAuthorizerParamsVo);
		criteria.addOrder(Order.desc("createTime"));
		list = (List<WechatAuthorizerParams>) super.loadListForPage(criteria, start, limit);
		return list;
	}
	
	@Override
	public Integer loadListCount(WechatAuthorizerParamsVo wechatAuthorizerParamsVo) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(wechatAuthorizerParamsVo);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(WechatAuthorizerParamsVo wechatAuthorizerParamsVo){
		DetachedCriteria criteria = DetachedCriteria.forClass(WechatAuthorizerParams.class);  
		if(!Utils.isEmpty(wechatAuthorizerParamsVo)){
			if(!Utils.isEmpty(wechatAuthorizerParamsVo.getId()))
				criteria.add(Restrictions.eq("id", wechatAuthorizerParamsVo.getId()));
		}
		return criteria;
	}
	
	@Override
	public WechatAuthorizerParams loadById(Integer id) throws Exception {
		return super.loadById(WechatAuthorizerParams.class, id);
	}
	
	@Override
	public List<WechatAuthorizerParams> loadBySId(String sid) throws Exception {
		return super.loadList("from WechatAuthorizerParams w where w.sid = ?", sid);
	}

	@Override
	public List<WechatAuthorizerParams> loadByAuthorizerStatus(int authorizerStatus) throws Exception {
		return super.loadList("from WechatAuthorizerParams w where w.authorizerStatus = ?", authorizerStatus);
	}
	
	@Override
	public List<WechatAuthorizerParams> loadByAuthorizerAppId(String authorizerAppId) throws Exception {
		return super.loadList("from WechatAuthorizerParams w where w.authorizerAppId = ?", authorizerAppId);
	}
	
	@Override
	public List<WechatAuthorizerParams> loadByDeptCode(String deptCode) throws Exception {
		return super.loadList("from WechatAuthorizerParams w where w.deptCode = ?", deptCode);
	}
	
	@Override
	public List<WechatAuthorizerParams> loadByCompanyId(String companyId) throws Exception {
		return super.loadList("from WechatAuthorizerParams w where w.companyId = ?", companyId);
	}
	
	@Override
	public List<WechatAuthorizerParams> loadBySid(String sid) throws Exception {
		return super.loadList("from WechatAuthorizerParams w where w.sid = ?", sid);
	}

}
