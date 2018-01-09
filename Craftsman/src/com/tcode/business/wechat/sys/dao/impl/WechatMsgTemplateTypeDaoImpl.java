package com.tcode.business.wechat.sys.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.wechat.sys.dao.WechatMsgTemplateTypeDao;
import com.tcode.business.wechat.sys.model.WechatMsgTemplate;
import com.tcode.business.wechat.sys.model.WechatMsgTemplateType;
import com.tcode.business.wechat.sys.vo.WechatMsgTemplateTypeVo;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("wechatMsgTemplateTypeDao")
public class WechatMsgTemplateTypeDaoImpl extends BaseDao<WechatMsgTemplateType, Serializable> implements WechatMsgTemplateTypeDao {

	@Override
	public List<WechatMsgTemplateType> loadListPage(WechatMsgTemplateTypeVo wechatMsgTemplateTypeVo, int start, int limit)
			throws Exception {
		List<WechatMsgTemplateType> list = null;
		DetachedCriteria criteria = connectionCriteria(wechatMsgTemplateTypeVo);
		criteria.addOrder(Order.asc("updateTime"));
		list = (List<WechatMsgTemplateType>) super.loadListForPage(criteria, start, limit);
		return list;
	}
	
	@Override
	public Integer loadListCount(WechatMsgTemplateTypeVo wechatMsgTemplateTypeVo) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(wechatMsgTemplateTypeVo);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(WechatMsgTemplateTypeVo wechatMsgTemplateTypeVo){
		DetachedCriteria criteria = DetachedCriteria.forClass(WechatMsgTemplateType.class);  
		if(!Utils.isEmpty(wechatMsgTemplateTypeVo)){
			if(!Utils.isEmpty(wechatMsgTemplateTypeVo.getId()) && wechatMsgTemplateTypeVo.getId() != 0)
				criteria.add(Restrictions.eq("id", wechatMsgTemplateTypeVo.getId()));
		}
		return criteria;
	}
	
	@Override
	public List<WechatMsgTemplateType> loadByTemplateNo(String templateNo) throws Exception {
		return super.loadList("from WechatMsgTemplateType w where w.templateNo = ?", templateNo);
	}
}
