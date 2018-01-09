USE [Craftsman]
GO

/**
 * 微信模板消息类型
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[wechat_msg_template_type]') AND type in (N'U'))
DROP TABLE [dbo].[wechat_msg_template_type]
GO

CREATE TABLE [dbo].[wechat_msg_template_type](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,		--ID，自增长
	[template_no] [varchar](30) NOT NULL,				--模板编号
	[template_title] [varchar](30) NOT NULL,			--模板标题
	[create_by]	[varchar](20) NOT NULL,					--创建人
	[update_by]	[varchar](20) NOT NULL,					--修改人
	[create_time]	[varchar](20) NOT NULL,				--创建时间
	[update_time]	[varchar](20) NOT NULL,				--修改时间
)
GO

SET ANSI_PADDING OFF
GO