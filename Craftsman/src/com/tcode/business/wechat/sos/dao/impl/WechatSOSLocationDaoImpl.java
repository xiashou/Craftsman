package com.tcode.business.wechat.sos.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.wechat.sos.dao.WechatSOSLocationDao;
import com.tcode.business.wechat.sos.model.WechatSOSLocation;
import com.tcode.business.wechat.sos.vo.WechatSOSLocationVo;
import com.tcode.common.dao.BaseDao;

@Component("wechatSOSLocationDao")
public class WechatSOSLocationDaoImpl extends BaseDao<WechatSOSLocation, Serializable> implements WechatSOSLocationDao {


	@Override
	public List<WechatSOSLocation> loadByTemplateCodeAndOpenIdForApp(String openId, String appId, String deptCode)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WechatSOSLocation> loadListPage(WechatSOSLocationVo wechatSOSLocationVo, int start, int limit)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer loadListCount(WechatSOSLocationVo wechatSOSLocationVo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
