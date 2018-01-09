package com.tcode.business.member.action;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.member.model.Member;
import com.tcode.business.member.model.MemberCar;
import com.tcode.business.member.model.MemberGrade;
import com.tcode.business.member.service.MemberCarService;
import com.tcode.business.member.service.MemberGradeService;
import com.tcode.business.member.service.MemberService;
import com.tcode.business.msg.model.MsgCharging;
import com.tcode.business.msg.service.MsgChargingService;
import com.tcode.common.action.BaseAction;
import com.tcode.common.idgenerator.IdGenerator;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("memberAction")
public class MemberAction extends BaseAction {

	private static final long serialVersionUID = -779069155194990033L;
	private static Logger log = Logger.getLogger("SLog");
	
	private MemberService memberService;
	private MemberCarService memberCarService;
	private MemberGradeService memberGradeService;
	private MsgChargingService msgChargingService;
	
	private List<Member> memberList;
	private Member member;
	private MemberCar car;
	private File upload;
	private String uploadFileName;
	
	private String vipNo;
	private Integer memId;
	private String keyword;
	private Integer[] idStr;
	
	/**
	 * 分页查询会员
	 * @return
	 */
	public String queryMemberPage() {
		try {
			if(Utils.isEmpty(member))
				member = new Member();
			member.setCompanyId(this.getDept().getCompanyId());
			this.setTotalCount(memberService.getListCount(member));
			memberList = memberService.getListPage(member, this.getStart(), this.getLimit());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据关键字查询会员
	 * @return
	 */
	public String queryMemberByKeyword() {
		try {
			this.setTotalCount(memberService.getMemberByKeywordCount(this.getDept().getCompanyId(), keyword));
			memberList = memberService.getMemberByKeywordPage(this.getDept().getCompanyId(), keyword, this.getStart(), this.getLimit());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据会员卡号查询
	 * @return
	 */
	public String queryMemberByVipNo() {
		try {
			if(!Utils.isEmpty(vipNo)){
				memberList = memberService.getMemberByVipNo(vipNo);
				this.setResult(true, "");
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
			this.setResult(false, "查询失败！");
		}
		return SUCCESS;
	}
	
	/**
	 * 根据会员ID号查询
	 * @return
	 */
	public String queryMemberByMemId() {
		try {
			if(!Utils.isEmpty(memId)){
				memberList = new ArrayList<Member>();
				memberList.add(memberService.getMemberById(memId));
				this.setResult(true, "");
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
			this.setResult(false, "查询失败！");
		}
		return SUCCESS;
	}
	
	/**
	 * 首页创建车主
	 * @return
	 */
	public String createMember() {
		try {
			if(!Utils.isEmpty(member) && !Utils.isEmpty(car)){
				Member existMember = memberService.getMemberByMobile(this.getDept().getCompanyId(), member.getMobile());
				if(!Utils.isEmpty(existMember)){
					this.setResult(false, "手机号码已存在！");
					return SUCCESS;
				} else {
					MemberCar existCar = memberCarService.getCarByNumber(this.getDept().getCompanyId(), car.getCarShort(), car.getCarCode(), car.getCarNumber());
					if(!Utils.isEmpty(existCar)){
						this.setResult(false, "车牌号码已存在!");
						return SUCCESS;
					} else {
						member.setVipNo("V" + IdGenerator.getVipNoGenerator());
						member.setCompanyId(this.getDept().getCompanyId());
						member.setDeptCode(this.getDept().getDeptCode());
						member.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
						member.setSource(this.getDept().getDeptName());
						member.setPoint(0);
						memberService.insert(member);
						if(!Utils.isEmpty(member.getMemId())){
							car.setMemberId(member.getMemId());
							memberCarService.insert(car);
							this.setResult(true, member.getVipNo());
						}
					}
				}
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
			this.setResult(false, Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 上传图片
	 * @return
	 */
	public String uploadMemberImage() {
		try {
			String realpath = ServletActionContext.getServletContext().getRealPath("/upload/member");
	        if (!Utils.isEmpty(upload) && !Utils.isEmpty(member)) {
	        	String suffix;
	        	if(uploadFileName != null && !"".equals(uploadFileName) && uploadFileName.indexOf(".") > 0)
					suffix = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1).toLowerCase();
				else
					suffix = "";
	        	if("jpg".equals(suffix) || "png".equals(suffix) || "gif".equals(suffix) || "bmp".equals(suffix)){
	        		String fileName = member.getMemId() + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "." + suffix;
	        		File savefile = new File(new File(realpath), fileName);
	        		if (!savefile.getParentFile().exists())
	        			savefile.getParentFile().mkdirs();
	        		FileUtils.copyFile(upload, savefile);
	        		member = memberService.getMemberById(member.getMemId());
	        		if(!Utils.isEmpty(member))
	        			member.setPicture(fileName);
	        		memberService.update(member);
	        		this.setResult(true, "上传成功！");
	        	} else
	        		this.setResult(false, "请上传正确的图片格式！");
	        }
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
			this.setResult(false, "上传失败！");
		}
		
		return SUCCESS;
	}
	
	/**
	 * 批量导入会员信息
	 * @return
	 */
	public String importMember() {
		try {
			Workbook workbook = null;
			if (this.uploadFileName.toLowerCase().endsWith("xls"))
				workbook = new HSSFWorkbook(new FileInputStream(this.upload));
			if (this.uploadFileName.toLowerCase().endsWith("xlsx"))
				workbook = new XSSFWorkbook(new FileInputStream(this.upload));
			Sheet sheet = workbook.getSheetAt(0);
			this.memberList = new ArrayList<Member>();
			Member member = null;
			for (int i = 1; i < sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				member = new Member();
				if (row.getCell(0) != null) {
					row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
					member.setName(row.getCell(0).getStringCellValue().trim().replaceAll("	", ""));
				}
				if (!Utils.isEmpty(row.getCell(1))) {
					row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
					Member existMember = memberService.getMemberByMobile(this.getDept().getCompanyId(), row.getCell(1).getStringCellValue().replaceAll("	", ""));
					if(!Utils.isEmpty(existMember))
						continue;
					else
						member.setMobile(row.getCell(1).getStringCellValue().replaceAll("	", ""));
				} else {
					System.out.println(sheet.getLastRowNum());
					this.setResult(false, "第" + (i+1) + "行手机号码不能为空！");
					return SUCCESS;
				}
				if (row.getCell(2) != null) {
					row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
					if("男".equals(row.getCell(2).getStringCellValue().trim()))
						member.setSex("1");
					else if("女".equals(row.getCell(2).getStringCellValue().trim()))
						member.setSex("2");
					else
						member.setSex("0");
				}
				if (row.getCell(3) != null) {
					row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
					Pattern pattern = Pattern.compile("[0-9]*");  
				    if(!pattern.matcher(row.getCell(3).getStringCellValue()).matches()) {
				    	this.setResult(false, "第" + (i+1) + "行 " + row.getCell(3).getStringCellValue() + " 会员等级须为整数！");
						return SUCCESS;
				    }
					MemberGrade memberGrade = memberGradeService.getMemberGradeByGrade(this.getDept().getCompanyId(), Integer.parseInt(row.getCell(3).getStringCellValue()));
					if(Utils.isEmpty(memberGrade)){
						this.setResult(false, "第" + (i+1) + "行 " + row.getCell(3).getStringCellValue() + " 会员等级不存在！");
						return SUCCESS;
					}
					member.setGrade(memberGrade.getGrade());
				}
				if (row.getCell(4) != null){
					row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
					if(!Utils.isEmpty(row.getCell(4).getStringCellValue()))
						member.setBalance(Double.parseDouble(row.getCell(4).getStringCellValue()));
					else
						member.setBalance(0.0);
				}
				if (row.getCell(5) != null) {
					row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
					member.setPoint(Integer.parseInt(row.getCell(5).getStringCellValue()));
				}
				if (row.getCell(6) != null){
					row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
					member.setSource(row.getCell(6).getStringCellValue());
				}
				if (row.getCell(7) != null) {
					row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
					member.setRemark(row.getCell(7).getStringCellValue());
				}
				if (row.getCell(8) != null) {
					row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
					if(row.getCell(8).getStringCellValue().trim().length() < 7 || row.getCell(8).getStringCellValue().trim().length() > 8){
						this.setResult(false, "第" + (i+1) + "行 " + row.getCell(8).getStringCellValue() + " 车牌号码错误！");
						return SUCCESS;
					}
					member.setCarShort(row.getCell(8).getStringCellValue().substring(0, 1));
					member.setCarCode(row.getCell(8).getStringCellValue().substring(1, 2));
					member.setCarNumber(row.getCell(8).getStringCellValue().substring(2));
				}
				member.setDeptCode(this.getDept().getDeptCode());
				member.setCompanyId(this.getDept().getCompanyId());
				member.setSource(this.getDept().getDeptName());
				member.setVipNo("V" + IdGenerator.getVipNoGenerator());
				member.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
				this.memberList.add(member);
			}
			int count = this.memberService.insertMoreMember(memberList);
			this.setResult(true, "成功添加 " + count + " 条记录！");
		} catch (IllegalArgumentException e) {
			log.error("导入失败！格式错误！" + Utils.getErrorMessage(e));
			this.setResult(false, "导入失败！格式错误！");
		} catch (Exception e) {
			this.setResult(false, "添加失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	
	/**
	 * 添加
	 * @return
	 */
	public String insertMember() {
		try {
			if(!Utils.isEmpty(member)){
				member.setVipNo("V" + IdGenerator.getVipNoGenerator());
				member.setDeptCode(this.getDept().getDeptCode());
				member.setCompanyId(this.getDept().getCompanyId());
				member.setSource(this.getDept().getDeptName());
				member.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
				member.setPoint(0);
				memberService.insert(member);
				vipNo = member.getVipNo();
				this.setResult(true, "新建成功！");
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
			this.setResult(false, Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String updateMember() {
		try {
			if(!Utils.isEmpty(member) && !Utils.isEmpty(member.getMemId())){
				member.setDeptCode(this.getDept().getDeptCode());
				member.setCompanyId(this.getDept().getCompanyId());
				memberService.update(member);
				vipNo = member.getVipNo();
				this.setResult(true, "修改成功!");
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
			this.setResult(false, Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String deleteMember() {
		try {
			if(!Utils.isEmpty(member) && !Utils.isEmpty(member.getMemId())){
				memberService.delete(member);
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
			this.setResult(false, Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String deleteMoreMember() {
		try {
			if (!Utils.isEmpty(idStr)) {
				memberService.deleteByIds(idStr);
				this.setResult(true, "删除成功!");
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
			this.setResult(false, Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 修改会员备注
	 * @return
	 */
	public String updateMemberRemark() {
		try {
			if(!Utils.isEmpty(member) && !Utils.isEmpty(member.getMemId())){
				String remark = member.getRemark();
				member = memberService.getMemberById(member.getMemId());
				member.setRemark(remark);
				memberService.update(member);
				this.setResult(true, "修改成功!");
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
			this.setResult(false, Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 发送短信
	 * @return
	 */
	public String sendMemberSMS() {
		try {
			List<MsgCharging> msgList = msgChargingService.getByDeptCode(this.getDept().getDeptCode());
			MsgCharging msgCharging = null;
			if(msgList.size() == 1) {
				msgCharging = msgList.get(0);
				if(!Utils.isEmpty(msgCharging)){
					if(msgCharging.getRemainingNum() > 0) {
						if(Utils.isEmpty(member))
							member = new Member();
						member.setCompanyId(this.getDept().getCompanyId());
						int result = memberService.sendMemberSms(member, idStr, keyword);
						if(result > 0) {
							msgCharging.setRemainingNum(msgCharging.getRemainingNum() - result);
							msgCharging.setSendNum(msgCharging.getSendNum() + result);
							msgCharging.setUpdateBy("sys");
							msgChargingService.update(msgCharging);
							this.setResult(true, "成功发送" + result + "条信息!");
						} else
							this.setResult(false, "发送失败！");
					} else
						this.setResult(false, "短信条数不足，请联系管理员充值！");
				} else
					this.setResult(false, "没有开通短信服务，请联系管理员！");
			} else
				this.setResult(false, "没有开通短信服务，请联系管理员！");
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
			this.setResult(false, Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public MemberService getMemberService() {
		return memberService;
	}
	@Resource
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	public MemberCarService getMemberCarService() {
		return memberCarService;
	}
	@Resource
	public void setMemberCarService(MemberCarService memberCarService) {
		this.memberCarService = memberCarService;
	}
	public MemberGradeService getMemberGradeService() {
		return memberGradeService;
	}
	@Resource
	public void setMemberGradeService(MemberGradeService memberGradeService) {
		this.memberGradeService = memberGradeService;
	}
	public MsgChargingService getMsgChargingService() {
		return msgChargingService;
	}
	@Resource
	public void setMsgChargingService(MsgChargingService msgChargingService) {
		this.msgChargingService = msgChargingService;
	}

	public MemberCar getCar() {
		return car;
	}
	public void setCar(MemberCar car) {
		this.car = car;
	}
	public List<Member> getMemberList() {
		return memberList;
	}
	public void setMemberList(List<Member> memberList) {
		this.memberList = memberList;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public String getVipNo() {
		return vipNo;
	}
	public void setVipNo(String vipNo) {
		this.vipNo = vipNo;
	}
	public Integer getMemId() {
		return memId;
	}
	public void setMemId(Integer memId) {
		this.memId = memId;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public Integer[] getIdStr() {
		return idStr;
	}
	public void setIdStr(Integer[] idStr) {
		this.idStr = idStr;
	}
	
	
}
