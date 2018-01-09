package com.tcode.business.msg.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.msg.dao.MsgTemplateDao;
import com.tcode.business.msg.model.MsgTemplate;
import com.tcode.business.msg.service.MsgTemplateService;
import com.tcode.core.util.Utils;

@Component("msgTemplateService")
public class MsgTemplateServiceImpl implements MsgTemplateService{
	
	private MsgTemplateDao  msgTemplateDao;
	
	@Override
	public void insert(MsgTemplate msgTemplate) throws Exception {
		msgTemplate.setCreateTime(Utils.getSysTime());
		msgTemplate.setUpdateTime(Utils.getSysTime());
		msgTemplateDao.save(msgTemplate);
	}
	
	@Override
	public void update(MsgTemplate msgTemplate) throws Exception {
		msgTemplate.setUpdateTime(Utils.getSysTime());
		msgTemplateDao.edit(msgTemplate);
	}
	
	@Override
	public List<MsgTemplate> getByTemplateTypeNo(String deptCode, int templateTypeNo) throws Exception {
		return msgTemplateDao.loadByTemplateTypeNo(deptCode, templateTypeNo);
	}

	
	public MsgTemplateDao getMsgTemplateDao() {
		return msgTemplateDao;
	}

	@Resource
	public void setMsgTemplateDao(MsgTemplateDao msgTemplateDao) {
		this.msgTemplateDao = msgTemplateDao;
	}

}
