USE [Craftsman]
GO

/**
 * 短信模板类型
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[msg_template_type]') AND type in (N'U'))
DROP TABLE [dbo].[msg_template_type]
GO

CREATE TABLE [dbo].[msg_template_type](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,				--ID，自增长
	[template_type_no]	[int] NOT NULL,							--模板类型编码
	[template_type_name] [varchar](30) NOT NULL, 				--模板类型名称
	[property]	[int] DEFAULT 1 NOT NULL,						--模板性质1-系统自带，2-客户新增
)
GO

SET IDENTITY_INSERT [dbo].[msg_template_type] ON
GO

INSERT INTO [dbo].[msg_template_type] ([id], [template_type_no], [template_type_name]) VALUES ('1', '1', N'项目消费');
INSERT INTO [dbo].[msg_template_type] ([id], [template_type_no], [template_type_name]) VALUES ('2', '2', N'套餐购买');
INSERT INTO [dbo].[msg_template_type] ([id], [template_type_no], [template_type_name]) VALUES ('3', '3', N'客户充值');
INSERT INTO [dbo].[msg_template_type] ([id], [template_type_no], [template_type_name]) VALUES ('4', '4', N'下单成功');
INSERT INTO [dbo].[msg_template_type] ([id], [template_type_no], [template_type_name]) VALUES ('5', '5', N'违章处理');
INSERT INTO [dbo].[msg_template_type] ([id], [template_type_no], [template_type_name]) VALUES ('6', '6', N'保养到期提醒');
INSERT INTO [dbo].[msg_template_type] ([id], [template_type_no], [template_type_name]) VALUES ('7', '7', N'保险到期提醒');
INSERT INTO [dbo].[msg_template_type] ([id], [template_type_no], [template_type_name]) VALUES ('8', '8', N'年审到期提醒');
INSERT INTO [dbo].[msg_template_type] ([id], [template_type_no], [template_type_name]) VALUES ('9', '9', N'年检到期提醒');
INSERT INTO [dbo].[msg_template_type] ([id], [template_type_no], [template_type_name]) VALUES ('10', '10', N'渡晶到期提醒');
INSERT INTO [dbo].[msg_template_type] ([id], [template_type_no], [template_type_name]) VALUES ('11', '11', N'提车通知');
INSERT INTO [dbo].[msg_template_type] ([id], [template_type_no], [template_type_name]) VALUES ('12', '12', N'生日提醒');
GO

SET ANSI_PADDING OFF
GO