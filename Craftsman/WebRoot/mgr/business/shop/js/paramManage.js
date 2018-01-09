Ext.require(['Ext.ux.DataTip']);
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	Ext.define('Param', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'param1', type: 'string'},
	        {name: 'param2', type: 'string'},
	        {name: 'param3', type: 'string'},
	        {name: 'param4', type: 'string'},
	        {name: 'param5', type: 'string'},
	        {name: 'param6', type: 'string'},
	        {name: 'param7', type: 'string'},
	        {name: 'param8', type: 'string'},
	        {name: 'param9', type: 'string'},
	        {name: 'param10', type: 'string'}
	    ]
	});
	
	var paramForm = new Ext.form.FormPanel({
		id : 'paramForm',
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
		         defaults: {flex: 1,xtype:'numberfield',labelWidth:100,labelAlign:'right'},
		         items: [
	                   {id:'param1', name: 'param.param1',fieldLabel: '库存报警值',tooltip: '商品库存低于或等于此值将报警!'}
		         ]
		     },{
		    	 xtype: 'fieldcontainer',
		         layout: 'hbox',
		         defaults: {flex: 1,xtype:'numberfield', labelWidth:100,labelAlign:'right'},
		         items: [
	                    {id:'param2', name: 'param.param2',fieldLabel: '积分抵现', tooltip:'设置多少积分抵现1元'}
		         ]
		     },{
		    	 xtype: 'fieldcontainer',
		         layout: 'hbox',
		         defaults: {flex: 1,xtype:'numberfield', labelWidth:100,labelAlign:'right'},
		         items: [
	                    {id:'param3', name: 'param.param3',fieldLabel: '积分规则', tooltip:'设置消费多少金额积1分'}
		         ]
//		     },{
//		    	 xtype: 'fieldcontainer',
//		    	 layout: 'hbox',
//		    	 defaults: {flex: 1,xtype:'numberfield', labelWidth:100,labelAlign:'right'},
//		    	 items: [
//		    	         {flex: 9,id:'param4', name: 'param.param4',fieldLabel: '组长提成比例', tooltip:'设置组长占提成金额的百分比'},
//		    	         {xtype:'displayfield',value:'&nbsp;%'}
//		    	 ]
//		     },{
//		    	 xtype: 'fieldcontainer',
//		         layout: 'hbox',
//		         defaults: {flex: 1,xtype:'textfield', labelWidth:100, labelAlign:'right'},
//		         items: [
//	                    {id:'param5', name: 'param.param5',fieldLabel: '接单提成', tooltip:'设置百分比或者固定金额'}
//		         ]
//		     },{
//		    	 xtype: 'fieldcontainer',
//		    	 layout: 'hbox',
//		    	 defaults: {flex: 1,xtype:'textfield', labelWidth:100, labelAlign:'right'},
//		    	 items: [
//		    	         {id:'param6', name: 'param.param6',fieldLabel: '销售顾问提成', tooltip:'设置百分比或者固定金额'}
//		    	 ]
		     }
		],
		buttons:[{
			text : '修 改',
			iconCls : 'accept',
			hidden: !parent.haveActionMenu('Edit'),
			handler : function() {
				if (paramForm.form.isValid()) {
					paramForm.form.submit({
						url : '/shop/updateParam.atc',
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
    
    var paramWindow = new Ext.Window({
		title : '参数配置', 
		layout : 'fit',
		width : 380,
		height : 260,
		y: 180,
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
		items : [paramForm]
	});
    
    function completeShopParam() {
		Ext.Ajax.request({
			url : '/shop/queryParamById.atc',
			success : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
				if(result && !Ext.isEmpty(result)){
					param = new Param({
						param1: result.param1,
						param2: result.param2,
						param3: result.param3,
						param4: result.param4,
						param5: result.param5,
						param6: result.param6,
						param7: result.param7,
						param8: result.param8,
						param9: result.param9,
						param10: result.param10,
					});
					paramForm.loadRecord(param);
				}
			},
			failure : function(resp, opts) {
                Ext.Msg.alert('提示', "店铺参数配置信息加载出错！");
			}
		});
	}
    
    completeShopParam();
    paramWindow.show();
	
});