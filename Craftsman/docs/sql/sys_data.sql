
INSERT INTO sys_dept(id, dept_name, parent_id, dept_code, dept_type, create_time) VALUES ('1', '���ܹ�������Ӫ������ϵͳ', '0', 'XXXX', 0, '');
 
SET IDENTITY_INSERT [sys_user] ON
INSERT INTO sys_user([user_id], [user_name], password, dept_id, real_name, role_id) 
VALUES(1, 'xiashou', 'c9feb348e716778b4159f22da8560bc0', '1', '��', 1);
INSERT INTO sys_user([user_id], [user_name], password, dept_id, real_name, role_id) 
VALUES(9, 'developer', 'c9feb348e716778b4159f22da8560bc0', '1', 'developer', 2);
SET IDENTITY_INSERT [sys_user] OFF

SET IDENTITY_INSERT [sys_role] ON
INSERT INTO sys_role(role_id, role_name, dept_id, role_type, locked) VALUES(1, '������Ա', '1', '0', 0);
INSERT INTO sys_role(role_id, role_name, dept_id, role_type, locked) VALUES(2, '������Ա', '1', '1', 0);
INSERT INTO sys_role(role_id, role_name, dept_id, role_type, locked) VALUES(101, 'ϵͳ�����ɫ', '1', '0', 0);
SET IDENTITY_INSERT [sys_role] OFF

INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'01', N'��������ƽ̨', N'0', N'', 1, 0, N'', 1, N'0')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'0101', N'ϵͳ����', N'01', N'', 1, 0, N'cog', 10, N'0')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010101', N'��֯�ܹ�����', N'0101', N'/sys/initSysDept.atc', 0, 1, N'chart_organisation', 1, N'0')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010102', N'�û��������', N'0101', N'/sys/initSysUser.atc', 0, 1, N'user', 2, N'0')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010103', N'��ɫ��������Ȩ', N'0101', N'/sys/initSysRole.atc', 0, 1, N'group_link', 3, N'0')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010104', N'�˵���Դ����', N'0101', N'/sys/initSysMenu.atc', 0, 1, N'layout', 4, N'0')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010105', N'��ť��Դ����', N'0101', N'/sys/initSysButton.atc', 0, 1, N'tab', 5, N'0')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010106', N'ȫ�ֲ�������', N'0101', N'/sys/initSysParam.atc', 0, 1, N'map', 6, N'0')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010107', N'����ָ������', N'0101', N'/sys/initSysHelp.atc', 0, 1, N'help', 7, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'0102', N'������Ϣ', N'01', N'', 1, 0, N'brick', 8, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010201', N'ʡ�м�ƴ���', N'0102', N'/base/initBaseArea.atc', 0, 1, N'', 1, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010202', N'����������', N'0102', N'/base/initAreaShort.atc', 0, 1, N'', 2, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010203', N'�����������', N'0102', N'/base/initAreaCode.atc', 0, 1, N'', 3, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010204', N'����������Ϣ', N'0102', N'/base/initCarBaseInfo.atc', 0, 1, N'', 4, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'0103', N'���̹���', N'01', N'', 1, 0, N'application_view_tile', 1, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010301', N'��Ŀ����', N'0103', N'/order/initNormalOrder.atc', 0, 1, N'', 1, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010302', N'�ײ͹���', N'0103', N'/order/initPackageOrder.atc', 0, 1, N'', 2, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010304', N'�ͻ��˻�', N'0103', N'/order/initReturnOrder.atc', 0, 1, N'', 4, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010305', N'�ͻ���ֵ', N'0103', N'/order/initMemberRecharge.atc', 0, 1, N'', 5, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010306', N'��������', N'0103', N'/goods/initHourGoods.atc', 0, 1, N'', 6, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010307', N'�ײ�����', N'0103', N'/goods/initPackageGoods.atc', 0, 1, N'', 7, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010309', N'��ȯ����', N'0103', N'/goods/initCouponsGoods.atc', 0, 1, N'', 9, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010310', N'��������', N'0103', N'/order/initInsuranceOrder.atc', 0, 1, N'', 10, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010311', N'�ײͿ۴�', N'0103', N'/order/initDeductionOrder.atc', 0, 1, N'', 5, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010312', N'��������', N'0103', N'/order/initDnfOrderManage.atc', 0, 1, N'', 5, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010313', N'����ɾ����¼', N'0103', N'/report/initDeleteOrderReport.atc', 0, 1, N'', 11, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'0105', N'������', N'01', N'', 1, 0, N'creditcards', 3, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010501', N'��Ʒ���', N'0105', N'/goods/initInStockGoods.atc', 0, 1, N'', 1, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010502', N'������', N'0105', N'/goods/initMoveGoods.atc', 0, 1, N'', 2, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010503', N'����̵�', N'0105', N'/goods/initCheckGoods.atc', 0, 1, N'', 3, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010504', N'����ѯ', N'0105', N'/goods/initStockGoods.atc', 0, 1, N'', 4, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010505', N'��Ʒ����', N'0105', N'/goods/initMaterialGoods.atc', 0, 1, N'', 5, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010506', N'����¼', N'0105', N'/goods/initInStockGoodsReport.atc', 0, 1, N'', 6, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010507', N'�����¼', N'0105', N'/report/initOutGoodsReport.atc', 0, 1, N'', 7, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010508', N'��Ӧ�̹���', N'0105', N'/shop/initSupplier.atc', 0, 1, N'', 10, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'0106', N'�ͻ�����', N'01', N'', 1, 0, N'user', 4, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010601', N'�ͻ���Ϣ', N'0106', N'/member/initMemberInfo.atc', 0, 1, N'', 1, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010602', N'���Ѽ�¼', N'0106', N'/order/initSaleOrderManage.atc', 0, 1, N'', 2, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010603', N'��Ա�ȼ�', N'0106', N'/member/initMemberGrade.atc', 0, 1, N'', 3, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010604', N'��ֵ����', N'0106', N'/shop/initRechargeSetting.atc', 0, 1, N'', 4, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010605', N'�ײ�/��Ƭ����', N'0106', N'/member/initMemberStock.atc', 0, 1, N'', 5, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'0107', N'�������', N'01', N'', 1, 0, N'money_dollar', 5, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010701', N'�ʲ�����', N'0107', N'/fico/initAssets.atc', 0, 1, N'', 1, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010702', N'�ʽ�֧��', N'0107', N'', 1, 0, N'', 2, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'01070201', N'�ʽ�֧��', N'010702', N'/fico/initExpenditure.atc', 0, 1, N'', 1, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'01070202', N'֧������', N'010702', N'/fico/initExptype.atc', 0, 1, N'', 2, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010703', N'Ӧ�չ���', N'0107', N'/fico/initReceivable.atc', 0, 1, N'', 3, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010704', N'Ӧ������', N'0107', N'/fico/initPayable.atc', 0, 1, N'', 4, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010705', N'Ա��ҵ�����', N'0107', N'/report/initCommissionReport.atc', 0, 1, N'', 5, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010706', N'��Ա��ֵͳ��', N'0107', N'/report/initRechargeReport.atc', 0, 1, N'', 6, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010707', N'Ӫҵ��ˮͳ��', N'0107', N'/report/initWaterOrderReport.atc', 0, 1, N'', 7, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'0108', N'�̳ǹ���', N'01', N'', 0, 1, N'cart', 6, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'0109', N'��������', N'01', N'', 1, 0, N'cup', 7, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010901', N'Ա������', N'0109', N'', 1, 0, N'', 1, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'01090101', N'Ա��������Ϣ', N'010901', N'/shop/initEmployee.atc', 0, 1, N'', 1, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'01090102', N'Ա�������', N'010901', N'/shop/initEmployeeGroup.atc', 0, 1, N'', 2, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010903', N'��������', N'0109', N'/shop/initParam.atc', 0, 1, N'', 5, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010904', N'ϵͳ����', N'0109', N'/shop/initSetting.atc', 0, 1, N'', 6, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010905', N'�������', N'0109', N'/shop/initCommission.atc', 0, 1, N'', 3, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'010906', N'��������', N'0109', N'', 1, 0, N'', 4, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'01090601', N'ģ������', N'010906', N'/msg/initMsgTemplate.atc', 0, 1, N'', 1, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'01090602', N'���Ź���', N'010906', N'/msg/initMsgSendRecord.atc', 0, 1, N'', 2, N'1')
INSERT [dbo].[sys_menu] ([id], [menu_name], [parent_id], [menu_url], [expanded], [leaf], [icon_cls], [sort_no], [menu_type]) VALUES (N'0110', N'���ݷ���', N'01', N'', 1, 1, N'chart_curve', 9, N'1')


INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Add', '�½�', '010101');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Edit', '�޸�', '010101');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Del', 'ɾ��', '010101');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Add', '�½�', '010102');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Edit', '�޸�', '010102');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Del', 'ɾ��', '010102');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Bind', '��', '010102');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Add', '�½�', '010103');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Edit', '�޸�', '010103');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Auth', '��Ȩ', '010103');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Del', 'ɾ��', '010103');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Add', '�½�', '010104');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Edit', '�޸�', '010104');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Del', 'ɾ��', '010104');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Add', '�½�', '010105');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Edit', '�޸�', '010105');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Del', 'ɾ��', '010105');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Add', '�½�', '010106');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Edit', '�޸�', '010106');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Del', 'ɾ��', '010106');

INSERT INTO sys_param(param_key, param_value, remark) values('SYS_TITLE','��������Ӫ������ϵͳ','ϵͳ����');

INSERT INTO [dbo].[shop_param] ([dept_code], [param1], [param2], [param3]) values('XXXX', '2', '100', '100');

INSERT INTO [dbo].[shop_setting] ([dept_code], [shop_name], [logo], [phone]) values('XXXX', '���ܹ���', '', '0755-1234567');

SET IDENTITY_INSERT [dbo].[sys_sequence] ON
INSERT [dbo].[sys_sequence] ([id], [field_name], [max_id], [pattern], [remark]) VALUES (1000, N'VIPNO', N'0', N'000000', N'��Ա��')
INSERT [dbo].[sys_sequence] ([id], [field_name], [max_id], [pattern], [remark]) VALUES (1001, N'ORDERNO', N'0', N'1000000000', N'������')
INSERT [dbo].[sys_sequence] ([id], [field_name], [max_id], [pattern], [remark]) VALUES (1002, N'MATERIALID', N'0', N'30000000', N'ʵ����Ʒ')
INSERT [dbo].[sys_sequence] ([id], [field_name], [max_id], [pattern], [remark]) VALUES (1003, N'HOURID', N'0', N'10000000', N'��ʱ��Ʒ')
INSERT [dbo].[sys_sequence] ([id], [field_name], [max_id], [pattern], [remark]) VALUES (1004, N'PACKAGEID', N'0', N'80000000', N'�ײ���Ʒ')
INSERT [dbo].[sys_sequence] ([id], [field_name], [max_id], [pattern], [remark]) VALUES (1005, N'COUPONSID', N'0', N'100000', N'��ȯ��Ʒ')
SET IDENTITY_INSERT [dbo].[sys_sequence] OFF

INSERT [dbo].[shop_param] ([dept_code], [param1], [param2], [param3], [param4], [param5], [param6], [param7], [param8], [param9], [param10]) VALUES (N'XXXX', N'2', N'100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL)




INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Add', '�½�', '010306');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Edit', '�޸�', '010306');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Del', 'ɾ��', '010306');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('AddHour', '�½�����', '010306');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('EditHour', '�޸ķ���', '010306');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('DelHour', 'ɾ������', '010306');


INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Add', '�½�', '010307');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Del', 'ɾ��', '010307');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('AddRow', '������ϸ', '010307');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Save', '����', '010307');



INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Add', '�½�����', '010507');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Edit', '�޸�����', '010507');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('Del', 'ɾ������', '010507');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('AddMaterial', '�½�', '010507');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('EditMaterial', '�޸�', '010507');
INSERT INTO sys_button(btn_key, btn_name, menu_id) VALUES('DelMaterial', 'ɾ��', '010507');




