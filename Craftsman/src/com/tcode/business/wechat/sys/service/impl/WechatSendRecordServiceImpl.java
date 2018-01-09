package com.tcode.business.wechat.sys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.wechat.sys.dao.WechatSendRecordDao;
import com.tcode.business.wechat.sys.model.WechatSendRecord;
import com.tcode.business.wechat.sys.service.WechatSendRecordService;
import com.tcode.business.wechat.sys.vo.WechatSendRecordVo;
import com.tcode.core.util.Utils;

@Component("WechatSendRecordService")
public class WechatSendRecordServiceImpl implements WechatSendRecordService {
	
	private WechatSendRecordDao wechatSendRecoredDao;

	@Override
	public void insert(WechatSendRecord wechatSendRecord) throws Exception {
		String currentTime = Utils.getSysTime();
		wechatSendRecord.setCreateTime(currentTime);
		wechatSendRecord.setUpdateTime(currentTime);
		wechatSendRecoredDao.save(wechatSendRecord);
	}

	@Override
	public void update(WechatSendRecord wechatSendRecord) throws Exception {
		String currentTime = Utils.getSysTime();
		wechatSendRecord.setUpdateTime(currentTime);
		wechatSendRecoredDao.edit(wechatSendRecord);
	}

	@Override
	public List<WechatSendRecord> getByTemplateCodeAndOpenIdForApp(String templateCode, String openId, String appId,
			String deptCode) throws Exception {
		return wechatSendRecoredDao.loadByTemplateCodeAndOpenIdForApp(templateCode, openId, appId, deptCode);
	}

	@Override
	public List<WechatSendRecord> getListPage(WechatSendRecordVo wechatSendRecordVo, int start, int limit)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getListCount(WechatSendRecordVo wechatSendRecordVo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public WechatSendRecordDao getWechatSendRecoredDao() {
		return wechatSendRecoredDao;
	}

	@Resource
	public void setWechatSendRecoredDao(WechatSendRecordDao wechatSendRecoredDao) {
		this.wechatSendRecoredDao = wechatSendRecoredDao;
	}

}
