USE [Craftsman]
GO

/**
 * 短信任务列表
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[msg_mission]') AND type in (N'U'))
DROP TABLE [dbo].[msg_mission]
GO

CREATE TABLE [dbo].[msg_mission](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,		--ID，自增长
	[template_type_no] [int] NOT NULL,					--模板类型编码
	[dept_code] [varchar](30) NOT NULL, 				--门店代码
	[vip_no]	[varchar](10) NOT NULL,					--会员卡号
	[order_id] [varchar](20) NOT NULL,					--订单ID
	[create_time]	[varchar](20) NOT NULL,				--创建时间
	[update_time]	[varchar](20) NOT NULL,				--修改时间
)
GO

SET ANSI_PADDING OFF
GO