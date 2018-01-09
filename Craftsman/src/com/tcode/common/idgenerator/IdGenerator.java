package com.tcode.common.idgenerator;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.tcode.core.id.SequenceStorer;
import com.tcode.core.id.fomater.DefaultSequenceFormater;
import com.tcode.core.id.generator.DefaultIDGenerator;
import com.tcode.core.id.sequence.DefaultSequenceGenerator;
import com.tcode.core.util.Utils;
import com.tcode.system.model.SysSequence;
import com.tcode.system.service.SysDeptService;
import com.tcode.system.service.SysSequenceService;

/**
 * ID生成器
 * @author Xiashou
 * @since 2016/07/06
 */
@Component
public class IdGenerator {
	
	private static Logger log = Logger.getLogger("SLog");
	private static int catche = 1;
	
	private static SysSequenceService sysSequenceService;
	private static SysDeptService sysDeptService;
	
	
	/**
	 * 字段名称
	 */
	private String fieldname;
	
	public IdGenerator(String pFieldName){
		setFieldname(pFieldName);
	}
	
	public IdGenerator(){
	}
	
	/**
	 * 获取ID生成器实例
	 * @return DefaultIDGenerator
	 */
	public DefaultIDGenerator getDefaultIDGenerator() {
		DefaultIDGenerator idGenerator = new DefaultIDGenerator(); 
		try {
			SysSequence sequence = new SysSequence();
			sequence = sysSequenceService.getSequenceByName(getFieldname());
			DefaultSequenceFormater sequenceFormater = new DefaultSequenceFormater(); 
			sequenceFormater.setPattern(sequence.getPattern());
			DefaultSequenceGenerator sequenceGenerator = new DefaultSequenceGenerator(getFieldname());
			SequenceStorer sequenceStorer = new DBSequenceStorer();
			sequenceGenerator.setSequenceStorer(sequenceStorer);
			sequenceGenerator.setCache(catche);
			idGenerator.setSequenceFormater(sequenceFormater);
			idGenerator.setSequenceGenerator(sequenceGenerator);
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return idGenerator;
	}
	
	/**
     * 会员卡号生成器(自定义)
     * @return
     */
	public static String getVipNoGenerator() {
		try {
			SysSequence sequence = new SysSequence();
			sequence = sysSequenceService.getSequenceByName("VIPNO");
			String maxId = sequence.getMaxId();
			int currentId = Integer.valueOf(maxId).intValue() + 1;
			String vipNo = (String.valueOf(currentId).toString()).replaceAll("4", "5");
			currentId = Integer.valueOf(vipNo).intValue();
			sequence.setMaxId(vipNo);
			if(currentId > 0 && currentId < 10){
				vipNo = "00000" + String.valueOf(vipNo);
			}else if(10 <= currentId && currentId <= 99){
				vipNo = "0000" + String.valueOf(currentId);
			}else if (100 <= currentId && currentId <= 999) {
				vipNo = "000" + String.valueOf(currentId);
			}else if (1000 <= currentId && currentId <= 9999) {
				vipNo = "00" + String.valueOf(currentId);
			}else if (10000 <= currentId && currentId <= 99999) {
				vipNo = "0" + String.valueOf(currentId);
			}else if (100000 <= currentId && currentId <= 999999) {
				vipNo = String.valueOf(currentId);
			}else if(currentId > 999999){
				log.error("生成卡号越界了.请和您的系统管理员联系!");
			}else{
				log.error("生成卡号发生未知错误,请和开发人员联系!");
			}
			sysSequenceService.update(sequence);
			return vipNo;
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return null;
	}
	
    
//	public static String getMenuIdGenerator(String pParentid){
//		String maxSubMenuId = (String)g4Dao.queryForObject("IdGenerator.getMaxSubMenuId", pParentid);
//		String menuId = null;
//		if(Utils.isEmpty(maxSubMenuId)){
//			menuId = "01";
//		}else{
//			int length = maxSubMenuId.length();
//			String temp = maxSubMenuId.substring(length-2, length);
//			int intMenuId = Integer.valueOf(temp).intValue() + 1;
//			if(intMenuId > 0 && intMenuId < 10){
//				menuId = "0" + String.valueOf(intMenuId);
//			}else if(10 <= intMenuId && intMenuId <= 99){
//				menuId = String.valueOf(intMenuId);
//			}else if(intMenuId > 99){
//				log.error("生成菜单编号越界了.同级兄弟节点编号为[01-99]\n请和您的系统管理员联系!");
//			}else{
//				log.error("生成菜单编号发生未知错误,请和开发人员联系!");
//			}
//		}
//		return pParentid + menuId;
//	}
	
    /**
     * 部门编号ID生成器(自定义)
     * @param pParentid 菜单编号的参考编号
     * @return
     */
	public static String getDeptIdGenerator(String parentId) throws Exception {
		String maxSubDeptId = sysDeptService.getMaxIdByParentId(parentId);
		String deptid = null;
		if(Utils.isEmpty(maxSubDeptId)){
			deptid = String.valueOf(Integer.valueOf(deptid).intValue() + 1);
		}else{
			int length = maxSubDeptId.length();
			String temp = maxSubDeptId.substring(length - 6, length);
			int intDeptId = Integer.valueOf(temp).intValue() + 1;
			if(intDeptId > 0 && intDeptId < 10){
				deptid = "00000" + String.valueOf(intDeptId);
			}else if(10 <= intDeptId && intDeptId <= 99){
				deptid = "0000" + String.valueOf(intDeptId);
			}else if (100 <= intDeptId && intDeptId <= 999) {
				deptid = "000" + String.valueOf(intDeptId);
			}else if (1000 <= intDeptId && intDeptId <= 9999) {
				deptid = "00" + String.valueOf(intDeptId);
			}else if (10000 <= intDeptId && intDeptId <= 99999) {
				deptid = "0" + String.valueOf(intDeptId);
			}else if (100000 <= intDeptId && intDeptId <= 999999) {
				deptid = String.valueOf(intDeptId);
			}else if(intDeptId > 999999){
				log.error("生成部门编号越界了.请和您的系统管理员联系!");
			}else{
				log.error("生成部门编号发生未知错误,请和开发人员联系!");
			}
		}
		return parentId + deptid;
	}
	
	public String getFieldname() {
		return fieldname;
	}
	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}
	public SysSequenceService getSysSequenceService() {
		return sysSequenceService;
	}
	@Resource
	public void setSysSequenceService(SysSequenceService sysSequenceService) {
		IdGenerator.sysSequenceService = sysSequenceService;
	}
	public SysDeptService getSysDeptService() {
		return sysDeptService;
	}
	@Resource
	public void setSysDeptService(SysDeptService sysDeptService) {
		IdGenerator.sysDeptService = sysDeptService;
	}

}
