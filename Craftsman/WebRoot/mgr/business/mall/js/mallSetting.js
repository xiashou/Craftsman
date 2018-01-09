Ext.require(['Ext.ux.DataTip']);
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	Ext.define('Setting', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'title', type: 'string'},
	        {name: 'appId', type: 'string'},
	        {name: 'mchId', type: 'string'},
	        {name: 'appSecret', type: 'string'},
	        {name: 'pointRule', type: 'int'}
	    ]
	});
	
	var settingForm = new Ext.form.FormPanel({
		id : 'settingForm',
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
		         defaults: {flex: 1,xtype:'textfield',labelWidth:70,labelAlign:'right'},
		         items: [
	                   {id:'title', name: 'setting.title',fieldLabel: '商城名称',maxLength: 6, allowBlank : false,blankText : '商城名称不能为空!'}
		         ]
		     },{
		    	 xtype: 'fieldcontainer',
		         layout: 'hbox',
		         defaults: {flex: 1,xtype:'textfield', labelWidth:70,labelAlign:'right'},
		         items: [
	                    {id:'appId', name: 'setting.appId',fieldLabel: '公众号ID', readOnly: true, fieldStyle:'background-color: #F0F0F0;'}
		         ]
		     },{
		    	 xtype: 'fieldcontainer',
		         layout: 'hbox',
		         defaults: {flex: 1,xtype:'textfield', labelWidth:70,labelAlign:'right'},
		         items: [
	                    {id:'mchId', name: 'setting.mchId',fieldLabel: '商户号',allowBlank : false,blankText : '商户号不能为空!'}
		         ]
		     },{
		    	 xtype: 'fieldcontainer',
		    	 layout: 'hbox',
		    	 defaults: {flex: 1,xtype:'textfield', labelWidth:70, labelAlign:'right'},
		    	 items: [
		    	         {id:'appSecret', name: 'setting.appSecret',fieldLabel: 'API密钥',allowBlank : false,blankText : 'API密钥不能为空!'}
		    	 ]
		     },{
		    	 xtype: 'fieldcontainer',
		    	 layout: 'hbox',
		    	 defaults: {flex: 1,xtype:'numberfield', labelWidth:70, labelAlign:'right'},
		    	 items: [
		    	         {id:'pointRule', name: 'setting.pointRule',fieldLabel: '积分规则',allowBlank : false,blankText : '积分规则不能为空!', value: 0, tooltip:'设置消费多少金额积1分'}
		    	 ]
		     }
		],
		buttons:[{
			text : '修 改',
			iconCls : 'accept',
			handler : function() {
				if (settingForm.form.isValid()) {
					settingForm.form.submit({
						url : '/mall/updateMallSetting.atc',
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
			}
		}]
	});
    
    var settingWindow = new Ext.Window({
		title : '商城设置', 
		layout : 'fit',
		width : 400,
		height : 250,
		y: 150,
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
		items : [settingForm]
	});
    
    function completeShopSetting() {
		Ext.Ajax.request({
			url : '/mall/queryMallSettingByAppId.atc',
			success : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
				if(result && !Ext.isEmpty(result)){
					setting = new Setting({
						title: result.title,
						appId: result.appId,
						mchId: result.mchId,
						appSecret: result.appSecret,
						pointRule: result.pointRule
					});
					settingForm.loadRecord(setting);
				}
			},
			failure : function(resp, opts) {
                Ext.Msg.alert('提示', "系统设置信息加载出错！");
			}
		});
	}
    
    completeShopSetting();
    settingWindow.show();
	
});