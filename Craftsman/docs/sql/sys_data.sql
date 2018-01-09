
INSERT INTO sys_dept(id, dept_name, parent_id, dept_code, dept_type, create_time) VALUES ('1', '智能工匠汽车营销管理系统', '0', 'XXXX', 0, '');
 
SET IDENTITY_INSERT [sys_user] ON
INSERT INTO sys_user([user_id], [user_name], password, dept_id, real_name, role_id) 
VALUES(1, 'xiashou', 'c9feb348e716778b4159f22da8560bc0', '1', '夏', 1);
INSERT INTO sys_user([user_id], [user_name], password, dept_id, real_name, role_id) 
VALUES(9, 'developer', 'c9feb348e716778b4159f22da8560bc0', '1', 'developer', 2);
SET IDENTITY_INSERT [sys_user] OFF

SET IDENTITY_INSERT [sys_role] ON
INSERT INTO sys_role(role_id, role_name, dept_id, role_type, locked) VALUES(1, '开发人员', '1', '0', 0);
INSERT INTO sys_role(role_id, role_name, dept_id, role_type, locked) VALUES(2, '开发人员', '1', '1', 0);
INSERT INTO sys_role(role_id, role_name, dept_id, role_type, locked) VALUES(101, '系统代理角色', '1', '0', 0);
SET IDENTITY_INSERT [sys_role] OFF

INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'01', N'工匠管理平台', N'0', N'', 1, 0, N'', 1, N'0')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'0101', N'系统设置', N'01', N'', 1, 0, N'cog', 10, N'0')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010101', N'组织架构管理', N'0101', N'/sys/initSysDept.atc', 0, 1, N'chart_organisation', 1, N'0')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010102', N'用户管理与绑定', N'0101', N'/sys/initSysUser.atc', 0, 1, N'user', 2, N'0')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010103', N'角色管理与授权', N'0101', N'/sys/initSysRole.atc', 0, 1, N'group_link', 3, N'0')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010104', N'菜单资源管理', N'0101', N'/sys/initSysMenu.atc', 0, 1, N'layout', 4, N'0')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010105', N'按钮资源管理', N'0101', N'/sys/initSysButton.atc', 0, 1, N'tab', 5, N'0')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010106', N'全局参数管理', N'0101', N'/sys/initSysParam.atc', 0, 1, N'map', 6, N'0')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010107', N'操作指引设置', N'0101', N'/sys/initSysHelp.atc', 0, 1, N'help', 7, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'0102', N'基础信息', N'01', N'', 1, 0, N'brick', 8, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010201', N'省市简称代码', N'0102', N'/base/initBaseArea.atc', 0, 1, N'', 1, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010202', N'车牌区域简称', N'0102', N'/base/initAreaShort.atc', 0, 1, N'', 2, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010203', N'车牌区域代码', N'0102', N'/base/initAreaCode.atc', 0, 1, N'', 3, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010204', N'车辆基础信息', N'0102', N'/base/initCarBaseInfo.atc', 0, 1, N'', 4, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'0103', N'店铺管理', N'01', N'', 1, 0, N'application_view_tile', 1, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010301', N'项目消费', N'0103', N'/order/initNormalOrder.atc', 0, 1, N'', 1, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010302', N'套餐购买', N'0103', N'/order/initPackageOrder.atc', 0, 1, N'', 2, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010304', N'客户退货', N'0103', N'/order/initReturnOrder.atc', 0, 1, N'', 4, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010305', N'客户充值', N'0103', N'/order/initMemberRecharge.atc', 0, 1, N'', 5, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010306', N'服务设置', N'0103', N'/goods/initHourGoods.atc', 0, 1, N'', 6, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010307', N'套餐设置', N'0103', N'/goods/initPackageGoods.atc', 0, 1, N'', 7, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010309', N'卡券设置', N'0103', N'/goods/initCouponsGoods.atc', 0, 1, N'', 9, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010310', N'保险理赔', N'0103', N'/order/initInsuranceOrder.atc', 0, 1, N'', 10, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010311', N'套餐扣次', N'0103', N'/order/initDeductionOrder.atc', 0, 1, N'', 5, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010312', N'开单管理', N'0103', N'/order/initDnfOrderManage.atc', 0, 1, N'', 5, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010313', N'订单删除记录', N'0103', N'/report/initDeleteOrderReport.atc', 0, 1, N'', 11, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'0105', N'库存管理', N'01', N'', 1, 0, N'creditcards', 3, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010501', N'商品入库', N'0105', N'/goods/initInStockGoods.atc', 0, 1, N'', 1, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010502', N'库存调拨', N'0105', N'/goods/initMoveGoods.atc', 0, 1, N'', 2, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010503', N'库存盘点', N'0105', N'/goods/initCheckGoods.atc', 0, 1, N'', 3, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010504', N'库存查询', N'0105', N'/goods/initStockGoods.atc', 0, 1, N'', 4, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010505', N'商品资料', N'0105', N'/goods/initMaterialGoods.atc', 0, 1, N'', 5, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010506', N'入库记录', N'0105', N'/goods/initInStockGoodsReport.atc', 0, 1, N'', 6, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010507', N'出库记录', N'0105', N'/report/initOutGoodsReport.atc', 0, 1, N'', 7, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010508', N'供应商管理', N'0105', N'/shop/initSupplier.atc', 0, 1, N'', 10, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'0106', N'客户管理', N'01', N'', 1, 0, N'user', 4, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010601', N'客户信息', N'0106', N'/member/initMemberInfo.atc', 0, 1, N'', 1, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010602', N'消费记录', N'0106', N'/order/initSaleOrderManage.atc', 0, 1, N'', 2, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010603', N'会员等级', N'0106', N'/member/initMemberGrade.atc', 0, 1, N'', 3, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010604', N'充值设置', N'0106', N'/shop/initRechargeSetting.atc', 0, 1, N'', 4, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010605', N'套餐/卡片管理', N'0106', N'/member/initMemberStock.atc', 0, 1, N'', 5, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'0107', N'财务管理', N'01', N'', 1, 0, N'money_dollar', 5, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010701', N'资产管理', N'0107', N'/fico/initAssets.atc', 0, 1, N'', 1, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010702', N'资金支出', N'0107', N'', 1, 0, N'', 2, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'01070201', N'资金支出', N'010702', N'/fico/initExpenditure.atc', 0, 1, N'', 1, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'01070202', N'支出类型', N'010702', N'/fico/initExptype.atc', 0, 1, N'', 2, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010703', N'应收管理', N'0107', N'/fico/initReceivable.atc', 0, 1, N'', 3, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010704', N'应付管理', N'0107', N'/fico/initPayable.atc', 0, 1, N'', 4, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010705', N'员工业绩提成', N'0107', N'/report/initCommissionReport.atc', 0, 1, N'', 5, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010706', N'会员充值统计', N'0107', N'/report/initRechargeReport.atc', 0, 1, N'', 6, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010707', N'营业流水统计', N'0107', N'/report/initWaterOrderReport.atc', 0, 1, N'', 7, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'0108', N'商城管理', N'01', N'', 0, 1, N'cart', 6, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'0109', N'店铺设置', N'01', N'', 1, 0, N'cup', 7, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010901', N'员工管理', N'0109', N'', 1, 0, N'', 1, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'01090101', N'员工基本信息', N'010901', N'/shop/initEmployee.atc', 0, 1, N'', 1, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'01090102', N'员工组管理', N'010901', N'/shop/initEmployeeGroup.atc', 0, 1, N'', 2, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010903', N'参数配置', N'0109', N'/shop/initParam.atc', 0, 1, N'', 5, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010904', N'系统设置', N'0109', N'/shop/initSetting.atc', 0, 1, N'', 6, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010905', N'提成设置', N'0109', N'/shop/initCommission.atc', 0, 1, N'', 3, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010906', N'提醒设置', N'0109', N'', 1, 0, N'', 4, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'01090601', N'模板设置', N'010906', N'/msg/initMsgTemplate.atc', 0, 1, N'', 1, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'01090602', N'短信管理', N'010906', N'/msg/initMsgSendRecord.atc', 0, 1, N'', 2, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'0110', N'数据分析', N'01', N'', 1, 1, N'chart_curve', 9, N'1')


INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Add', '新建', '010101');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Edit', '修改', '010101');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Del', '删除', '010101');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Add', '新建', '010102');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Edit', '修改', '010102');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Del', '删除', '010102');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Bind', '绑定', '010102');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Add', '新建', '010103');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Edit', '修改', '010103');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Auth', '授权', '010103');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Del', '删除', '010103');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Add', '新建', '010104');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Edit', '修改', '010104');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Del', '删除', '010104');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Add', '新建', '010105');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Edit', '修改', '010105');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Del', '删除', '010105');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Add', '新建', '010106');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Edit', '修改', '010106');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Del', '删除', '010106');

INSERT INTO sys_param(param_key, param_value, remark) values('SYS_TITLE','工匠汽车营销管理系统','系统标题');

INSERT INTO [dbo].[shop_param] ([dept_code], [param1], [param2], [param3]) values('XXXX', '2', '100', '100');

INSERT INTO [dbo].[shop_setting] ([dept_code], [shop_name], [logo], [phone]) values('XXXX', '智能工匠', '', '0755-1234567');

SET IDENTITY_INSERT [dbo].[sys_sequence] ON
INSERT [dbo].[sys_sequence] ([id], [field_name], [max_id], [pattern], [remark]) VALUES (1000, N'VIPNO', N'0', N'000000', N'会员号')
INSERT [dbo].[sys_sequence] ([id], [field_name], [max_id], [pattern], [remark]) VALUES (1001, N'ORDERNO', N'0', N'1000000000', N'订单号')
INSERT [dbo].[sys_sequence] ([id], [field_name], [max_id], [pattern], [remark]) VALUES (1002, N'MATERIALID', N'0', N'30000000', N'实物商品')
INSERT [dbo].[sys_sequence] ([id], [field_name], [max_id], [pattern], [remark]) VALUES (1003, N'HOURID', N'0', N'10000000', N'工时商品')
INSERT [dbo].[sys_sequence] ([id], [field_name], [max_id], [pattern], [remark]) VALUES (1004, N'PACKAGEID', N'0', N'80000000', N'套餐商品')
INSERT [dbo].[sys_sequence] ([id], [field_name], [max_id], [pattern], [remark]) VALUES (1005, N'COUPONSID', N'0', N'100000', N'卡券商品')
SET IDENTITY_INSERT [dbo].[sys_sequence] OFF

INSERT [dbo].[shop_param] ([dept_code], [param1], [param2], [param3], [param4], [param5], [param6], [param7], [param8], [param9], [param10]) VALUES (N'XXXX', N'2', N'100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL)




INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Add', '新建', '010306');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Edit', '修改', '010306');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Del', '删除', '010306');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('AddHour', '新建服务', '010306');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('EditHour', '修改服务', '010306');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('DelHour', '删除服务', '010306');


INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Add', '新建', '010307');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Del', '删除', '010307');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('AddRow', '新增明细', '010307');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Save', '保存', '010307');



INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Add', '新建类型', '010507');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Edit', '修改类型', '010507');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Del', '删除类型', '010507');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('AddMaterial', '新建', '010507');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('EditMaterial', '修改', '010507');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('DelMaterial', '删除', '010507');




