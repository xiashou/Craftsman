USE [Craftsman]
GO

/**
 * 短信模板
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[msg_template]') AND type in (N'U'))
DROP TABLE [dbo].[msg_template]
GO

CREATE TABLE [dbo].[msg_template](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,		--ID，自增长
	[template_type_no] [int] NOT NULL,					--模板类型编码
	[dept_code] [varchar](30) NOT NULL, 				--门店代码
	[content] [varchar](500) NOT NULL,					--模板内容
	[remaining_days]	[int] NOT NULL,					--剩余天数
	[send_rate]	[int] NOT NULL,							--发送频率
	[create_time]	[varchar](20) NOT NULL,				--创建时间
	[update_time]	[varchar](20) NOT NULL,				--修改时间
)
GO

SET ANSI_PADDING OFF
GO