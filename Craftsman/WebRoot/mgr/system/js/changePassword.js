Ext.require(['Ext.ux.DataTip']);
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	var passwordForm = new Ext.form.FormPanel({
		id : 'passwordForm',
		layout: 'anchor',
		defaults: {
            anchor: '100%',
            xtype:'textfield'
        },
		bodyPadding: '5 10 0 0',
		border : false,
		plugins: {
            ptype: 'datatip'
        },
		items : [{
			xtype: 'fieldcontainer',
		         layout: 'hbox',
		         defaults: {flex: 1,xtype:'textfield',labelWidth:80,labelAlign:'right'},
		         items: [
	                   {id:'password', name: 'sysUser.password',inputType : 'password',fieldLabel: '原密码',allowBlank : false}
		         ]
		     },{
		    	 xtype: 'fieldcontainer',
		         layout: 'hbox',
		         defaults: {flex: 1,xtype:'textfield', labelWidth:80,labelAlign:'right'},
		         items: [
	                    {id:'newPassword', name: 'newPassword',inputType : 'password',fieldLabel: '新密码',allowBlank : false}
		         ]
		     },{
		    	 xtype: 'fieldcontainer',
		         layout: 'hbox',
		         defaults: {flex: 1,xtype:'textfield', labelWidth:80,labelAlign:'right'},
		         items: [
	                    {id:'confirm', name: 'confirm',inputType : 'password',fieldLabel: '确认密码',allowBlank : false}
		         ]
		     }
		],
		buttons:[{
			text : '修 改',
			iconCls : 'accept',
			handler : function() {
				if(Ext.getCmp("confirm").getValue() == Ext.getCmp("newPassword").getValue()){
					if(passwordForm.form.isValid()) {
						passwordForm.form.submit({
							url : '/sys/changeSysUserPassword.atc',
							waitTitle : '提示',
							method : 'POST',
							waitMsg : '正在处理数据,请稍候...',
							success : function(form, action) {
								Ext.example.msg('提示', action.result.msg);
							},
							failure : function(form, action) {
								var msg = action.result.msg;
								Ext.MessageBox.alert('提示', msg);
							}
						});
					}
				} else {
					Ext.MessageBox.alert('提示', "两次密码不一致！");
				}
			}
		}]
	});
    
    var passwordWindow = new Ext.Window({
		title : '修改密码', 
		layout : 'fit',
		width : 380,
		height : 180,
		y: 250,
		resizable : false,
		draggable : false,
		closable : false,
		modal : false,
		collapsible : true,
		titleCollapse : true,
		maximizable : false,
		buttonAlign : 'right',
		border : false,
		animCollapse : true,
		constrain : true,
		tools:[{
            type:'help',
            tooltip: '操作指引',
            callback: function(panel, tool, event) {
            	parent.global.openSystemHelpTab();
            }
        }],
		items : [passwordForm]
	});
    
    passwordWindow.show();
	
});