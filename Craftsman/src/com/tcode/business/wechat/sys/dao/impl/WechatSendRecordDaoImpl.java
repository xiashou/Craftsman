package com.tcode.business.wechat.sys.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.wechat.sys.dao.WechatSendRecordDao;
import com.tcode.business.wechat.sys.model.WechatSendRecord;
import com.tcode.business.wechat.sys.vo.WechatSendRecordVo;
import com.tcode.common.dao.BaseDao;

@Component("wechatSendRecoredDao")
public class WechatSendRecordDaoImpl extends BaseDao<WechatSendRecord, Serializable> implements WechatSendRecordDao {

	@Override
	public void save(WechatSendRecord wechatSendRecord) throws Exception {
		super.save(wechatSendRecord);
	}

	@Override
	public void edit(WechatSendRecord wechatSendRecord) throws Exception {
		super.edit(wechatSendRecord);
	}

	@Override
	public List<WechatSendRecord> loadByTemplateCodeAndOpenIdForApp(String templateCode, String openId, String appId, String deptCode)
			throws Exception {
		super.getHibernateTemplate().setMaxResults(1) ;
		List<WechatSendRecord>  list = super.loadList("from WechatSendRecord w where w.templateCode = ? AND openId = ? AND appId = ? AND w.deptCode = ? order by w.updateTime desc", templateCode, openId, appId, deptCode);
		super.getHibernateTemplate().setMaxResults(0) ;
		return list;
	}

	@Override
	public List<WechatSendRecord> loadListPage(WechatSendRecordVo wechatSendRecordVo, int start, int limit)
			throws Exception {
		return null;
	}

	@Override
	public Integer loadListCount(WechatSendRecordVo wechatSendRecordVo) throws Exception {
		return null;
	}

}
