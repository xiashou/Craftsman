package com.tcode.business.wechat.sys.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.wechat.sys.dao.WechatMsgTemplateDao;
import com.tcode.business.wechat.sys.model.WechatMsgTemplate;
import com.tcode.business.wechat.sys.vo.WechatMsgTemplateVo;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("wechatMsgTemplateDao")
public class WechatMsgTemplateDaoImpl extends BaseDao<WechatMsgTemplate, Serializable> implements WechatMsgTemplateDao {

	@Override
	public List<WechatMsgTemplate> loadListPage(WechatMsgTemplateVo wechatMsgTemplateVo, int start, int limit)
			throws Exception {
		List<WechatMsgTemplate> list = null;
		DetachedCriteria criteria = connectionCriteria(wechatMsgTemplateVo);
		criteria.addOrder(Order.desc("updateTime"));
		list = (List<WechatMsgTemplate>) super.loadListForPage(criteria, start, limit);
		return list;
	}
	
	@Override
	public Integer loadListCount(WechatMsgTemplateVo wechatMsgTemplateVo) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(wechatMsgTemplateVo);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(WechatMsgTemplateVo wechatMsgTemplateVo){
		DetachedCriteria criteria = DetachedCriteria.forClass(WechatMsgTemplate.class);  
		if(!Utils.isEmpty(wechatMsgTemplateVo)){
			if(!Utils.isEmpty(wechatMsgTemplateVo.getId()) && wechatMsgTemplateVo.getId() != 0)
				criteria.add(Restrictions.eq("id", wechatMsgTemplateVo.getId()));
			if(!Utils.isEmpty(wechatMsgTemplateVo.getDeptCode()))
				criteria.add(Restrictions.eq("deptCode", wechatMsgTemplateVo.getDeptCode()));
		}
		return criteria;
	}
	
	@Override
	public List<WechatMsgTemplate> loadBydepNoAndTemplateNo(String deptCode, String templateNo) throws Exception {
		return super.loadList("from WechatMsgTemplate w where w.deptCode = ? and w.templateNo = ?", deptCode, templateNo);
	}
	
	@Override
	public List<WechatMsgTemplate> loadByCompanyIdAndTemplateNo(String companyId, String templateNo) throws Exception {
		return super.loadList("from WechatMsgTemplate w where w.companyId = ? and w.templateNo = ?", companyId, templateNo);
	}
	
	@Override
	public List<WechatMsgTemplate> loadBydepNo(String deptCode) throws Exception {
		return super.loadList("from WechatMsgTemplate w where w.deptCode = ? ", deptCode);
	}

}
