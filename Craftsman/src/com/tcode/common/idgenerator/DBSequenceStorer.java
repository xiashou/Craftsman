package com.tcode.common.idgenerator;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.tcode.core.id.SequenceStorer;
import com.tcode.core.id.StoreSequenceException;
import com.tcode.core.util.Utils;
import com.tcode.system.model.SysSequence;
import com.tcode.system.service.SysSequenceService;

/**
 * ID数据库逻辑存储器
 * @author Xiashou
 * @since 2016/07/06
 */
@Component
public class DBSequenceStorer implements SequenceStorer {
	
	private static Logger log = Logger.getLogger("SLog");
	
	private static SysSequenceService sysSequenceService;
	
	public SysSequenceService getSysSequenceService() {
		return sysSequenceService;
	}
	@Resource
	public void setSysSequenceService(SysSequenceService sysSequenceService) {
		DBSequenceStorer.sysSequenceService = sysSequenceService;
	}
	
	/**
	 * 返回当前最大序列号
	 */
	public long load(String pIdColumnName) throws StoreSequenceException {
		try {
			SysSequence sequence = new SysSequence();
			sequence = sysSequenceService.getSequenceByName(pIdColumnName);
			Long maxvalue = Long.valueOf(sequence.getMaxId());
			return maxvalue.longValue();
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return 0L;
	}
	
	/**
	 * 写入当前生成的最大序列号值
	 */
	public void  updateMaxValueByFieldName(long pMaxId, String pIdColumnName) throws StoreSequenceException {
		try {
			SysSequence sequence = new SysSequence();
			sequence = sysSequenceService.getSequenceByName(pIdColumnName);
			sequence.setMaxId(String.valueOf(pMaxId));
			sysSequenceService.update(sequence);
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
	}

}
