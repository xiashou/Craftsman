USE [Craftsman]
GO

/**
 * 短信发送记录
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[msg_send_record]') AND type in (N'U'))
DROP TABLE [dbo].[msg_send_record]
GO

CREATE TABLE [dbo].[msg_send_record](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,		--ID，自增长
	[dept_code] [varchar](30) NOT NULL, 				--门店代码
	[vip_no]	[varchar](10) NOT NULL,					--会员卡号
	[mission_id] [int] ,								--任务ID
	[mobile] [varchar](20) NOT NULL,					--手机号码
	[content] [varchar](500) NOT NULL,					--短信内容
	[order_id] [varchar](20) NOT NULL,					--订单ID
	[status] [int] NOT NULL,							--发送状态0-成功，1-失败
	[remark] [varchar](20) ,							--备注
	[create_time]	[varchar](20) NOT NULL,				--创建时间
	[update_time]	[varchar](20) NOT NULL,				--修改时间/发送时间
)
GO

SET ANSI_PADDING OFF
GO