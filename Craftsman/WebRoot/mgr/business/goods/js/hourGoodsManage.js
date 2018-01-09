Ext.require(['Ext.ux.TreePicker']);
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	var typeId;
	
	// 定义自动当前页行号
	var hourTypeRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 30
	});

	// 定义自动当前页行号
	var hourRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 30
	});
	
	var hourTypeStore = Ext.create('Ext.data.Store', {
		  fields: ['id', 'deptCode', 'typeName', 'sortNo'],
		  proxy: {
		      type: 'ajax',
			  url : '/goods/queryAllHourType.atc',
			  reader : {
			      root: 'hourTypeList',
			      totalProperty: 'totalCount'
			  }
		  }
	});
	
	var hourStore = Ext.create('Ext.data.Store', {
        fields: ['id', 'deptCode', 'typeId', 'name', 'price', 'remindId'],
        proxy: {
            type: 'ajax',
            url : '/goods/queryGoodsHourByType.atc',
            reader : {
                root: 'hourList',
                totalProperty: 'totalCount'
            }
        }
	});
	
	var hourTypeForm = new Ext.form.FormPanel({
				id : 'hourTypeForm',
				layout: 'anchor',
				defaults: {
		            anchor: '100%',
		            layout: 'hbox',
		            xtype:'fieldcontainer'
		        },
				bodyPadding: '5 10 0 0',
				border : false,
				items : [
					{
					    defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'right'},
					    items: [
					           {id:'id', name:'hourType.id', xtype:'hiddenfield'},
					           {id:'typeName', name: 'hourType.typeName',allowBlank: false, fieldLabel: '类型名称', blankText:'类型名称不能为空!' }
					    ]
					},{
					    defaults: {flex: 1, xtype:'numberfield', labelWidth:70, labelAlign:'right'},
					    items: [
					           {id:'sortNo', name: 'hourType.sortNo', allowBlank: true, fieldLabel: '排序' }
					    ]
					}
				],
				buttons:[{
					text : '保 存', // 按钮文本
					iconCls : 'accept', // 按钮图标
					handler : function() { // 按钮响应函数
						if (hourTypeForm.form.isValid()) {
							hourTypeForm.form.submit({
								waitTitle : '提示',
								method : 'POST',
								waitMsg : '正在处理数据,请稍候...',
								success : function(form, action) {
									hourTypeWindow.hide();
									Ext.example.msg('提示', action.result.msg);
									hourTypeStore.reload();
								},
								failure : function(form, action) {
									var msg = action.result.msg;
									Ext.MessageBox.alert('提示', msg);
								}
							});
						}
					}
				},{
					text : '关 闭 ', // 按钮文本
					iconCls : 'stop', // 按钮图标
					handler : function() { // 按钮响应函数
						hourTypeWindow.hide();
					}
				}]
			});
	
	var hourForm = new Ext.form.FormPanel({
		id : 'hourForm',
		layout: 'anchor',
		defaults: {
            anchor: '100%',
            layout: 'hbox',
            xtype:'fieldcontainer'
        },
		bodyPadding: '5 10 0 0',
		border : false,
		items : [
			{
			    defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'right'},
			    items: [
			           {id:'hourId', name:'hour.id', xtype:'hiddenfield'},
			           {id:'typeId', name:'hour.typeId', xtype:'hiddenfield'},
			           {id:'name', name: 'hour.name',allowBlank: false, fieldLabel: '商品名称', blankText:'商品名称不能为空!'  }
			    ]
			},{
			    defaults: {flex: 1, xtype:'numberfield', labelWidth:70, labelAlign:'right'},
			    items: [
			           {id:'price', name: 'hour.price', allowBlank: true, fieldLabel: '价格' }
			    ]
			}
		],
		buttons:[{
			text : '保 存', // 按钮文本
			iconCls : 'accept', // 按钮图标
			handler : function() { // 按钮响应函数
				if (hourForm.form.isValid()) {
					Ext.getCmp("typeId").setValue(typeId);
					hourForm.form.submit({
						waitTitle : '提示',
						method : 'POST',
						waitMsg : '正在处理数据,请稍候...',
						success : function(form, action) {
							hourWindow.hide();
							Ext.example.msg('提示', action.result.msg);
							hourStore.reload();
						},
						failure : function(form, action) {
							var msg = action.result.msg;
							Ext.MessageBox.alert('提示', msg);
						}
					});
				}
			}
		},{
			text : '关 闭 ', // 按钮文本
			iconCls : 'stop', // 按钮图标
			handler : function() { // 按钮响应函数
				hourWindow.hide();
			}
		}]
	});
	
	var hourTypeWindow = new Ext.Window({
		title : '修改', // 窗口标题
		layout : 'fit',
		width : 400,
		height : 150,
		resizable : false,
		draggable : true,
		closeAction : 'hide',
		modal : false,
		collapsible : true,
		titleCollapse : true,
		maximizable : false,
		buttonAlign : 'right',
		border : false,
		animCollapse : true,
		constrain : true,
		items: [hourTypeForm]
	});
	
	var hourWindow = new Ext.Window({
		title : '修改', // 窗口标题
		layout : 'fit',
		width : 400,
		height : 150,
		resizable : false,
		draggable : true,
		closeAction : 'hide',
		modal : false,
		collapsible : true,
		titleCollapse : true,
		maximizable : false,
		buttonAlign : 'right',
		border : false,
		animCollapse : true,
		constrain : true,
		items: [hourForm]
	});
	
  	var hourTypeColumns= [hourTypeRowNum,
	        {header : 'ID',dataIndex: 'id', width: 60, hidden: true},
	        {header : '类型名称',dataIndex: 'typeName', width: 120},
	        {header : '排序',dataIndex: 'sortNo', width: 80}
	];
  	
  	var hourColumns= [hourRowNum,
            {header : 'ID',dataIndex: 'id', hidden: true},
            {header : '类型',dataIndex: 'typeId', width: 100, renderer: function(v){return hourTypeStore.findRecord("id", v).data.typeName;}},
            {header : '商品名称',dataIndex: 'name', width: 160},
            {header : '价格',dataIndex: 'price', width: 100}
  	];
  	
	var hourTypeTbar = Ext.create('Ext.toolbar.Toolbar', {
			region : 'north',
			border : false,
	        items: [{
	        	xtype: 'button',
	        	id: 'insert',
	        	text: '新建',
	        	iconCls: 'add',
	        	hidden : !parent.haveActionMenu('Add'),
	        	handler: function() {
	        		hourWindow.hide();
	        		hourTypeWindow.setTitle('新建');
	        		hourTypeForm.getForm().reset();
	        		hourTypeForm.getForm().url = '/goods/insertHourType.atc';
	        		hourTypeWindow.show();
	            }
	        },{
	        	xtype: 'button',
	        	id: 'update',
	        	text: '修改',
	        	iconCls: 'pencil',
	        	hidden : !parent.haveActionMenu('Edit'),
	        	handler: function() {
	        		initHourTypeEdit();
	            }
	        },{
	        	xtype: 'button',
	        	id: 'delete',
	        	text: '删除',
	        	iconCls: 'delete', 
	        	hidden : !parent.haveActionMenu('Del'),
	        	handler: function(){
	        		initHourTypeDelete();
	            }
	        }]
	    });
	
	var hourTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	id: 'hourInsert',
        	text: '新建',
        	iconCls: 'add',
        	hidden : !parent.haveActionMenu('AddHour'),
        	handler: function() {
        		initHourInsert();
            }
        },{
        	xtype: 'button',
        	id: 'hourUpdate',
        	text: '修改',
        	iconCls: 'pencil',
        	hidden : !parent.haveActionMenu('EditHour'),
        	handler: function() {
        		initHourEdit();
            }
        },{
        	xtype: 'button',
        	id: 'hourDelete',
        	text: '删除',
        	iconCls: 'delete', 
        	hidden : !parent.haveActionMenu('DelHour'),
        	handler: function(){
        		initHourDelete();
            }
        }]
    });
	
	var hourTypeSelModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1,
		mode: 'SINGLE'
	});
	
	var hourSelModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1,
		mode: 'SINGLE'
	});
	
	var hourTypeGrid = new Ext.grid.GridPanel({
			columns : hourTypeColumns,
			store : hourTypeStore, 
			region : 'center',
			autoScroll : true,
			columnLines : true,
			selModel : hourTypeSelModel,
			tbar : hourTypeTbar, 
			stripeRows:true,
			forceFit: true,
			viewConfig : {
				enableTextSelection:true	//设置单元格可以选择
			},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			},
			listeners:{
		        itemclick: function(grid, record, item, index, e) {
					hourStore.load({
						params: {
							'typeId' : record.raw.id
						}
					});
					typeId = record.raw.id;
		        }
		    }
		});
	
	var hourGrid = new Ext.grid.GridPanel({
		columns : hourColumns,
		store : hourStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		selModel : hourSelModel,
		tbar : hourTbar, 
		stripeRows:true,
//		forceFit: true,
		viewConfig : {
			enableTextSelection:true	//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
	});
	
	//页面初始布局
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [{
			title : '类型',
			iconCls : 'tag_blue',
			tools : [{
				type:'refresh',
				handler : function() {
					hourTypeStore.reload();
				}
			}],
			collapsible : true,
			width : '20%',
			split : true,
			region : 'west',
			layout : 'fit',
			border : false,
			items : [ hourTypeGrid ]
		}, {
			title : '商品',
			iconCls : 'bricks',
			tools : [{
				type:'refresh',
				handler : function() {
					hourStore.reload();
				}
			},{
	            type:'help',
	            tooltip: '操作指引',
	            callback: function(panel, tool, event) {
	            	parent.global.openSystemHelpTab();
	            }
	        }],
			region : 'center',
			width : '80%',
			split : true,
			layout : 'fit',
			border : false,
			items : [ hourGrid ]
		}]
	});
	
	hourTypeStore.load();
	
	function initHourTypeEdit() {
		var record = hourTypeSelModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '你没有选中任何项目！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
		    });
			return;
		}
		hourWindow.hide();
		hourTypeSelModel.deselectAll();
		hourTypeWindow.setTitle('修改');
		hourTypeForm.getForm().reset();
		hourTypeForm.loadRecord(record);
		hourTypeForm.getForm().url = '/goods/updateHourType.atc';
		hourTypeWindow.show();
	}
	
	function initHourTypeDelete() {
		var record = hourTypeSelModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '你没有选中任何项目！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
		    });
			return;
		} 
		Ext.Msg.confirm('请确认', '删除这个类型，该类型下所有商品将被删除，确定?', function(btn, text) {
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : '/goods/deleteHourType.atc',
					params : {
						'hourType.id' : record.raw.id
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							hourTypeWindow.hide();
							Ext.example.msg('提示', result.msg);
							hourTypeStore.reload();
							hourStore.reload();
						} else 
							Ext.MessageBox.show({
     				           title: '提示',
     				           msg: result.msg,
     				           buttons: Ext.MessageBox.OK,
     				           icon: Ext.MessageBox.ERROR
                            });
					},
					failure : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
                        Ext.MessageBox.show({
 				           title: '提示',
 				           msg: result.msg,
 				           buttons: Ext.MessageBox.OK,
 				           icon: Ext.MessageBox.ERROR
                        });
					}
				});
			}
		});
	}
	
	function initHourInsert() {
		var record = hourTypeSelModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '你没有选中任何项目！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
		    });
			return;
		}
		hourTypeWindow.hide();
		hourWindow.setTitle('新建');
		hourForm.getForm().reset();
		hourForm.getForm().url = '/goods/insertGoodsHour.atc';
		hourWindow.show();
	}
	
	function initHourEdit() {
		var record = hourSelModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '你没有选中任何项目！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
		    });
			return;
		}
		hourTypeWindow.hide();
		hourSelModel.deselectAll();
		hourWindow.setTitle('修改');
		hourForm.getForm().reset();
		hourForm.loadRecord(record);
		Ext.getCmp("hourId").setValue(record.raw.id);
		hourForm.getForm().url = '/goods/updateGoodsHour.atc';
		hourWindow.show();
	}
	
	function initHourDelete() {
		var record = hourSelModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '你没有选中任何项目！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
		    });
			return;
		} 
		Ext.Msg.confirm('请确认', '确定要删除这个选项吗?', function(btn, text) {
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : '/goods/deleteGoodsHour.atc',
					params : {
						'hour.id' : record.raw.id
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							hourTypeWindow.hide();
							Ext.example.msg('提示', result.msg);
							hourSelModel.deselectAll();
							hourStore.reload();
						} else 
							Ext.MessageBox.show({
     				           title: '提示',
     				           msg: result.msg,
     				           buttons: Ext.MessageBox.OK,
     				           icon: Ext.MessageBox.ERROR
                            });
					},
					failure : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
                        Ext.MessageBox.show({
 				           title: '提示',
 				           msg: result.msg,
 				           buttons: Ext.MessageBox.OK,
 				           icon: Ext.MessageBox.ERROR
                        });
					}
				});
			}
		});
	}
});