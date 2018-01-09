USE [Craftsman]
GO

/**
 * 微信授权方参数
 */
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[wechat_authorizer_params]') AND type in (N'U'))
DROP TABLE [dbo].[wechat_authorizer_params]
GO

CREATE TABLE [dbo].[wechat_authorizer_params](
	[id] [int] IDENTITY(1, 1) NOT NULL PRIMARY KEY,		--ID，自增长
	[authorizer_appid] [varchar](50) UNIQUE, 			--授权方appid
	[authorizer_appsecret] [nvarchar](50) ,		--授权方appsecret
	[authorizer_access_token] [varchar](200) ,	--授权方接口调用凭据（在授权的公众号具备API权限时，才有此返回值），也简称为令牌
	[token_fresh_time]	[varchar](20) ,		    --Token刷新时间
	[token_fresh_rate] [int] ,	    			--Token刷新频率
	[authorizer_refresh_token] [varchar](200) ,	--接口调用凭据刷新令牌
	[func_info] [ntext] ,						--公众号授权给开发者的权限集列表
	[sid] [varchar](50) NOT NULL,								--自定义授权码，服务号需要拿到此授权码才能进行授权托管，也是公众号自定义唯一标示，用以区分用户访问的那个公众号
	[authorizer_appname] [varchar](50) ,		--授权方名称（服务号名称）
	[authorizer_status] [int] NOT NULL default 1,			--授权状态 1-未授权，2-已授权
	[company_id] [varchar](30) NOT NULL,							--公司ID
	[company_name] [nvarchar](80) ,				--公司名称
	[dept_code] [varchar](30) NOT NULL,							--店铺编码
	[dept_name] [nvarchar](80) ,				--店铺名称
	[dept_telephone] [nvarchar](20) ,			--店铺电话
	[linkman] [nvarchar](20) ,					--联系人
	[telephone] [nvarchar](20) ,				--联系人电话
	[province] [nvarchar](50) ,					--省份
	[city] [nvarchar](50) ,						--城市
	[area] [nvarchar](50) ,						--区域
	[address] [nvarchar](100) ,					--详细地址
	[latitude_longitude] [nvarchar](20) ,		--经纬度
	[msg_signature] [nvarchar](20) ,			--短信签名
	[create_by]	[varchar](20) NOT NULL,					--创建人
	[update_by]	[varchar](20) NOT NULL,					--修改人
	[create_time]	[varchar](20) NOT NULL,				--创建时间
	[update_time]	[varchar](20) NOT NULL,				--修改时间
)
GO

SET ANSI_PADDING OFF
GO