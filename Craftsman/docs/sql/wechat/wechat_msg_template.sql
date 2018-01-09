USE [Craftsman]
GO

/**
 * 微信模板消息
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[wechat_msg_template]') AND type in (N'U'))
DROP TABLE [dbo].[wechat_msg_template]
GO

CREATE TABLE [dbo].[wechat_msg_template](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,		--ID，自增长
	[template_no] [varchar](30) NOT NULL,				--模板编号
	[template_title] [varchar](30) NOT NULL,			--模板标题
	[template_id] [varchar](100) ,						--模板ID
	[company_id] [varchar](30) NOT NULL,					--公司ID
	[company_name] [nvarchar](80) ,						--公司名称
	[dept_code] [varchar](30) NOT NULL,					--店铺编码
	[dept_name] [nvarchar](80) ,						--店铺名称
	[create_by]	[varchar](20) NOT NULL,					--创建人
	[update_by]	[varchar](20) NOT NULL,					--修改人
	[create_time]	[varchar](20) NOT NULL,				--创建时间
	[update_time]	[varchar](20) NOT NULL,				--修改时间
	[send_Rate]		[int] NOT NULL,							--发送频率
	[send_bdays] [int] NOT NULL,						--开始发送时间
)
GO

ALTER TABLE [dbo].[wechat_msg_template] ADD
	CONSTRAINT [template_no_and_dept_no] UNIQUE NONCLUSTERED ([template_no] ASC, [dept_code] ASC) 
	WITH (PAD_INDEX = OFF,
		IGNORE_DUP_KEY = OFF,
		ALLOW_ROW_LOCKS = ON,
		ALLOW_PAGE_LOCKS = ON)
	ON [PRIMARY]
GO

SET ANSI_PADDING OFF
GO