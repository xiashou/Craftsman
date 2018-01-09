Ext.require(['Ext.ux.DataTip']);
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	Ext.define('Setting', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'shopName', type: 'string'},
	        {name: 'logo', type: 'string'},
	        {name: 'phone', type: 'string'},
	        {name: 'address', type: 'string'},
	        {name: 'shopRemark', type: 'string'},
	        {name: 'xlocation', type: 'string'},
	        {name: 'ylocation', type: 'string'}
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
	                   {id:'shopName', name: 'setting.shopName',fieldLabel: '店铺名称'}
		         ]
		     },{
		    	 xtype: 'fieldcontainer',
		         layout: 'hbox',
		         defaults: {flex: 1,xtype:'filefield', labelWidth:70,labelAlign:'right'},
		         items: [
	                    {id:'upload', name: 'upload',fieldLabel: 'Logo',buttonText:'浏览...'}
		         ]
		     },{
		    	 xtype: 'fieldcontainer',
		         layout: 'hbox',
		         defaults: {flex: 1,xtype:'textfield', labelWidth:70,labelAlign:'right'},
		         items: [
	                    {id:'phone', name: 'setting.phone',fieldLabel: '联系电话'}
		         ]
		     },{
		    	 xtype: 'fieldcontainer',
		    	 layout: 'hbox',
		    	 defaults: {flex: 1,xtype:'textarea', rows: 3, labelWidth:70, labelAlign:'right'},
		    	 items: [
		    	         {id:'address', name: 'setting.address',fieldLabel: '店铺地址'}
		    	 ]
		     },{
		    	 xtype: 'fieldcontainer',
		    	 layout: 'hbox',
		    	 defaults: {flex: 1,xtype:'textarea', rows: 3, labelWidth:70, labelAlign:'right'},
		    	 items: [
		    	         {id:'shopRemark', name: 'setting.shopRemark',fieldLabel: '店注'}
		    	 ]
		     },{
		    	 xtype: 'fieldcontainer',
		    	 layout: 'hbox',
		    	 defaults: {flex: 1,xtype:'textfield', labelWidth:70, labelAlign:'right'},
		    	 items: [
		    	         {id:'xlocation', name: 'setting.xlocation', fieldLabel: '位置x'},
		    	         {id:'ylocation', name: 'setting.ylocation', fieldLabel: '位置y'}
		    	 ]
		     },{
		    	 xtype: 'fieldcontainer',
		    	 layout: 'hbox',
		    	 defaults: {flex: 1,xtype:'displayfield', labelWidth:70, labelAlign:'right'},
		    	 items: [
		    	         {fieldLabel: ' ', labelSeparator: '',  value:'拽拖右方地图红色标记选择店铺地址，单击标记确认地址'}
		    	 ]
		     }
		],
		buttons:[{
			text : '修 改',
			iconCls : 'accept',
			hidden: !parent.haveActionMenu('Edit'),
			handler : function() {
				if (settingForm.form.isValid()) {
					settingForm.form.submit({
						url : '/shop/updateSetting.atc',
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
		title : '系统设置（重新登录后生效）', 
		layout : 'fit',
		width : 440,
		height : 380,
		x: (document.body.clientWidth - 1140) / 2,
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
    
    var mapWindow = new Ext.Window({
		title : '选择位置（点击红色标记确定位置）',
		layout : 'fit',
		width : 500,
		height : 500,
		x: document.body.clientWidth / 2 + 100,
		y: 90,
		margin: '0 0 0 0',
		resizable : false,
		draggable : false,
		closable : false,
		modal : false,
		collapsible : false,
		titleCollapse : true,
		maximizable : false,
		buttonAlign : 'right',
		border : false,
		animCollapse : true,
		constrain : true,
		//html: '<iframe src="/wct/mart/map.jsp" width="100%" height="100%" frameborder="0"></iframe>'
	});
    
    function completeShopSetting() {
		Ext.Ajax.request({
			url : '/shop/querySettingByDept.atc',
			success : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
				if(result && !Ext.isEmpty(result)){
					setting = new Setting({
						shopName: result.shopName,
						logo: result.logo,
						phone: result.phone,
						address: result.address,
						shopRemark: result.shopRemark,
						xlocation: result.xlocation,
						ylocation: result.ylocation
					});
					mapWindow.update('<iframe src="/mgr/business/shop/map.jsp" width="100%" height="100%" frameborder="0"></iframe>');
					Ext.getCmp("upload").setRawValue(result.logo);
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
    mapWindow.show();
	
});