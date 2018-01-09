USE [Craftsman]
GO

/**
 * 短信计数计费
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[msg_charging]') AND type in (N'U'))
DROP TABLE [dbo].[msg_charging]
GO

CREATE TABLE [dbo].[msg_charging](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,		--ID，自增长
	[dept_code] [varchar](30) NOT NULL, 				--门店代码
	[send_num] [int] NOT NULL,							--已发送条数
	[remaining_num]	[int] NOT NULL,						--剩余条数
	[create_by]	[varchar](20) NOT NULL,					--创建人
	[update_by]	[varchar](20) NOT NULL,					--修改人
	[create_time]	[varchar](20) NOT NULL,				--创建时间
	[update_time]	[varchar](20) NOT NULL,				--修改时间
)
GO

SET ANSI_PADDING OFF
GO