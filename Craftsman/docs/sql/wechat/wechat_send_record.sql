USE [Craftsman]
GO

/**
 * 微信模板消息发送记录
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[wechat_send_record]') AND type in (N'U'))
DROP TABLE [dbo].[wechat_send_record]
GO

CREATE TABLE [dbo].[wechat_send_record](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,		--ID，自增长
	[company_id] [varchar](30) NOT NULL,				--公司ID
	[company_name] [nvarchar](80) ,						--公司名称
	[dept_code] [varchar](30) NOT NULL,					--店铺编码
	[dept_name] [nvarchar](80) ,						--店铺名称
	[vip_no]	[varchar](10) NOT NULL,					--会员卡号
	[template_code] [varchar](30) NOT NULL,				--模板编码
	[openid] [varchar](50) NOT NULL, 					--openId
	[appid] [varchar](50) NOT NULL, 					--appId
	[content] [varchar](500) NOT NULL,					--消息内容
	[order_id] [varchar](20),							--订单ID
	[status] [int] NOT NULL,							--发送状态0-成功，1-失败
	[remark] [varchar](20) ,							--备注
	[create_time]	[varchar](20) NOT NULL,				--创建时间
	[update_time]	[varchar](20) NOT NULL,				--修改时间/发送时间
)
GO

SET ANSI_PADDING OFF
GO