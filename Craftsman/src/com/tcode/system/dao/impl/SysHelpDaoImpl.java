package com.tcode.system.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.tcode.common.dao.BaseDao;
import com.tcode.system.dao.SysHelpDao;
import com.tcode.system.model.SysHelp;

@Component("sysHelpDao")
public class SysHelpDaoImpl extends BaseDao<SysHelp, Serializable> implements SysHelpDao {

	@Override
	public SysHelp loadById(String id) throws Exception {
		return super.loadById(SysHelp.class, id);
	}

}
