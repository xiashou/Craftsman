/**
 * 登陆页面
 * @author Xiashou
 * @since 2015/04/03
 */
Ext.Ajax.timeout = 60000;
Ext.Ajax.defaultHeaders = {'Request-By': 'TSINGMA'};
Ext.onReady(function() {
	
	var LoginForm = new Ext.form.Panel({
		title : "身份认证",
		xtype : 'form',
		frame : false,
		border: false,
		id : 'LoginForm',
		defaults : {
			width : 300
		},
		bodyStyle : 'padding: 30 0 0 50',
		defaultType : 'textfield',
		labelWidth : 40,
		items : [{
					id : 'userName',
					name : 'sysUser.userName',
					fieldLabel : '帐&nbsp;号',
					labelWidth : 50,
					maxLength : 30,
					blankText : '帐号不能为空,请输入!',
					maxLengthText : '账号的最大长度为30个字符',
					allowBlank : false,
					listeners : {
						specialkey : function(field, e) {
							if (e.getKey() == Ext.EventObject.ENTER) {
								Ext.getCmp('password').focus();
							}
						}
					}
				}, {
					id : 'password',
					name : 'sysUser.password',
					fieldLabel : '密&nbsp;码',
					inputType : 'password',
					labelWidth : 50,
					maxLength : 20,
					blankText : '密码不能为空,请输入!',
					maxLengthText : '密码的最大长度为20个字符',
					allowBlank : false,
					listeners : {
						specialkey : function(field, e) {
							if (e.getKey() == Ext.EventObject.ENTER) {
								login();
							}
						}
					}
				}],
		buttons : [{
		            text : '重&nbsp;置',  
		            iconCls : 'arrow_rotate_anticlockwise',
		            handler : function() {
		            	LoginForm.getForm().reset();
		            }
		        },{
					text : '登&nbsp;录',
					iconCls : 'accept',
					handler : function() {
						login();
					}
				}]
    });
	
	var panel = new Ext.Panel({
		autoTabs : true,
		border : false,
		items : [{
					contentEl : 'hello-tabs'
				},{
					xtype : 'tabpanel',
					id : 'loginTabs',
					activeTab : 0,
					height : 180,
					border : false,
					items : [LoginForm]
				}]
	});

	var win = new Ext.Window({
		title : '深圳市智能工匠技术有限公司 <a href="http://www.miibeian.gov.cn/" target="_blank">粤ICP备16085903号</a>',
		renderTo : Ext.getBody(),
		layout : 'fit',
		width : 460,
		height : 300,
		closeAction : 'hide',
		plain : true,
		modal : true,
		collapsible : true,
		titleCollapse : true,
		maximizable : false,
		draggable : false,
		closable : false,
		resizable : false,
		animateTarget: document.body,
		items : panel
	});

	win.on('show', function() {
		setTimeout(function() {
			var userName = Ext.getCmp('userName');
			var password = Ext.getCmp('password');
			var c_userName = getCookie('login.userName');
			userName.setValue(c_userName);
			if (Ext.isEmpty(c_userName))
				userName.focus();
			else
				password.focus();
		}, 200);
	}, this);

	win.show();
	
	/**
	 * 提交登陆请求
	 */
	function login() {
		if (LoginForm.getForm().isValid()) {
			LoginForm.getForm().submit({
				url : '/userLogin.atc',
				waitTitle : '提示',
				method : 'POST',
				success : function(form, action) {
					Ext.MessageBox.show({
						title : '请等待',
						msg : '正在验证您的身份,请稍候.....',
						width : 250,
						wait : true,
						waitConfig : {
							interval : 50
						}
					});
					var userName = Ext.getCmp('userName');
					setCookie("login.userName", userName.getValue(), 240);
					window.location.href = '/index.atc';
				},
				failure : function(form, action) {
					Ext.MessageBox.show({
				           title: '提示',
				           msg: action.result.msg != '' ? action.result.msg : '与服务器失去连接！',
				           buttons: Ext.MessageBox.OK,
				           icon: Ext.MessageBox.ERROR
				    });
					Ext.getCmp('password').setValue('').focus();
				}
			});
		}
	}
	
	/**
	 * 设置Cookie
	 * @param {} name
	 * @param {} value
	 */
	function setCookie(name, value, minuts) {
		var argv = setCookie.arguments;
		var argc = setCookie.arguments.length;
	    var expiration = new Date((new Date()).getTime() + minuts * 60000 * 60);
		document.cookie = name + "=" + escape(value) + "; expires=" + expiration.toGMTString();
	}

	/**
	 * 获取Cookie
	 * @param {} Name
	 * @return {}
	 */
	function getCookie(Name) {
		var search = Name + "="
		if (document.cookie.length > 0) {
			offset = document.cookie.indexOf(search)
			if (offset != -1) {
				offset += search.length
				end = document.cookie.indexOf(";", offset)
				if (end == -1)
					end = document.cookie.length
				return unescape(document.cookie.substring(offset, end))
			} else
				return ""
		}
	}

	/**
	 * 从缓存中清除Cookie
	 * @param {} name
	 */
	function clearCookie(name) {
		var expdate = new Date();
		expdate.setTime(expdate.getTime() - (86400 * 1000 * 1));
		setCookie(name, "", expdate);
	}
	
});