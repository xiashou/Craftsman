package com.tcode.business.msg.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.msg.dao.MsgTemplateDao;
import com.tcode.business.msg.model.MsgTemplate;
import com.tcode.common.dao.BaseDao;

@Component("msgTemplateDao")
public class MsgTemplateDaoImpl extends BaseDao<MsgTemplate, Serializable> implements MsgTemplateDao {

	@Override
	public List<MsgTemplate> loadByTemplateTypeNo(String deptCode, int templateTypeNo) throws Exception {
		return super.loadList("from MsgTemplate m where m.deptCode = ? and m.templateTypeNo = ?", deptCode, templateTypeNo);
	}

}
