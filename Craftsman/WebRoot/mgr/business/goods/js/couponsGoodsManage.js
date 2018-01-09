Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	// 定义自动当前页行号
	var rowNum = new Ext.grid.RowNumberer({
				header : 'NO',
				resizable: true,
				width : 40
			});
	
	Ext.define('GoodsHour', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'id', type: 'string'},
	        {name: 'name', type: 'string'},
	        {name: 'price', type: 'float'}
	    ]
	});
	
	var GoodsStore = new Ext.data.Store({
		autoLoad: true,
		model: GoodsHour,
		proxy:{
            type: 'ajax',
            url : '/goods/queryAllGoodsHour.atc',
            reader : {
                root: 'hourList'
            }
        }
	});
	
	var GoodsCombo = Ext.create('Ext.form.ComboBox', {
		store: GoodsStore,
		fieldLabel: '适用服务',
		name: 'coupons.applyId',
		labelWidth:70, 
		labelAlign:'right',
		valueField: 'id',
		displayField: 'name',
		queryMode: 'local',
        editable : true,
        selectOnFocus : true,
//        emptyText:'请选择...', 
        multiSelect: true,
        triggerAction: 'all',
        lastQuery: '',
        listeners: {
        	select: function(cb, records){
                var record = records[0];
                if(!Ext.isEmpty(record)){
                	var rowRecord = ProjectGrid.getSelectionModel().getLastSelected();
                	completeProjectRow(rowRecord, record);
                	ProjectCellEditing.startEdit(rowRecord, 5);
                }
            },
            change: function(combox, newValue, oldValue){
            	var rowRecord = ProjectGrid.getSelectionModel().getLastSelected();
            	var rec = ProjectSecondItemStore.findRecord("name", newValue, 0, false, false, true);
            	if(Ext.isEmpty(rec)){
            		rowRecord.data.unitPrice = 0;
            		rowRecord.data.price = 0;
            		rowRecord.data.goodsName = newValue;
            		rowRecord.commit();
            	}
        	}
        }
	});
	
	var couponsForm = new Ext.form.FormPanel({
				id : 'couponsForm',
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
					           {id:'id', name:'coupons.id', xtype:'hiddenfield'},
					           {id:'name', name: 'coupons.name', allowBlank: false, fieldLabel: '名称', blankText:'名称不能为空!' }
					    ]
					},{
					    defaults: {flex: 1, xtype:'numberfield', labelWidth:70, labelAlign:'right'},
					    items: [
					            {id:'price', name: 'coupons.price', fieldLabel: '抵扣金额'},
					            {id:'minimum', name: 'coupons.minimum', fieldLabel: '最低消费'}
					    ]
					},{
						defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'right'},
						items: [
//						        {id:'applyId', name: 'coupons.applyId', fieldLabel: '适用服务'}
								GoodsCombo
						]
					}
				],
				buttons:[{
					text : '保 存',
					iconCls : 'accept',
					handler : function() {
						if (couponsForm.form.isValid()) {
							couponsForm.form.submit({
								waitTitle : '提示',
								method : 'POST',
								waitMsg : '正在处理数据,请稍候...',
								success : function(form, action) {
									couponsWindow.hide();
									Ext.example.msg('提示', action.result.msg);
									store.reload();
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
						couponsWindow.hide();
					}
				}]
			});
	
	var couponsWindow = new Ext.Window({
				title : '修改', // 窗口标题
				layout : 'fit',
				width : 400,
				height : 180,
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
				items: [couponsForm]
			});
	
	var store = Ext.create('Ext.data.Store', {
		  		  pageSize : 20,
		          fields: ['id', 'name', 'price', 'applyId', 'minimum', 'goodsName'],
		          proxy: {
		              type: 'ajax',
		              url : '/goods/queryCouponsByDept.atc',
		              reader : {
		                  root: 'couponsList'
		              }
		          }
		    });
  	
  	var columns= [rowNum,
	            {header : 'ID',dataIndex: 'id', width: 100, hidden: true},
	            {header : '名称',dataIndex: 'name', width: 200},
	            {header : '抵扣金额',dataIndex: 'price', width: 200, renderer: Ext.util.Format.numberRenderer("0.00")},
	            {header : '最低消费',dataIndex: 'minimum', width: 200, renderer: Ext.util.Format.numberRenderer("0.00")},
	            {header : '适用服务',dataIndex: 'applyId', width: 200, hidden: true},
	            {header : '适用服务',dataIndex: 'goodsName', width: 200}
	            
	  	]; 
	
	var tbar = Ext.create('Ext.toolbar.Toolbar', {
			region : 'north',
			border : false,
	        items: [{
	        	xtype: 'button',
	        	id: 'insert',
	        	text: '新建',
	        	iconCls: 'add',
	        	hidden : !parent.haveActionMenu('Add'),
	        	handler: function() {
	        		couponsWindow.setTitle('新建');
	        		couponsForm.getForm().reset();
	        		couponsForm.getForm().url = '/goods/insertCoupons.atc';
	        		couponsWindow.show();
	            }
	        },{
	        	xtype: 'button',
	        	id: 'update',
	        	text: '修改',
	        	iconCls: 'pencil',
	        	hidden : !parent.haveActionMenu('Edit'),
	        	handler: function() {
	        		initEdit();
	            }
	        },{
	        	xtype: 'button',
	        	id: 'delete',
	        	text: '删除',
	        	iconCls: 'delete', 
	        	hidden : !parent.haveActionMenu('Del'),
	        	handler: function(){
	        		initDelete();
	            }
	        }]
	    });
	
	
	var selModel = Ext.create('Ext.selection.CheckboxModel', {
			injectCheckbox: 1,
			mode: 'SINGLE'
		});
	
	var grid = new Ext.grid.GridPanel({
			columns : columns,
			store : store, 
			region : 'center',
			title: '卡券设置',
			tools:[{
	            type:'help',
	            tooltip: '操作指引',
	            callback: function(panel, tool, event) {
	            	parent.global.openSystemHelpTab();
	            }
	        }],
			autoScroll : true,
			columnLines : true,
			selModel : selModel,
			frame : false,
			tbar : tbar, 
			stripeRows:true,
			viewConfig : {
				enableTextSelection:true	//设置单元格可以选择
			},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			},
			listeners:{
				itemdblclick: function(grid, record, item, index, e) {
					if(parent.haveActionMenu('Edit'))
						initEdit();
		        }
		    }
		});
	
	// 布局模型
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [{
			region : 'center',
			layout : 'fit',
			border : false,
			items : [ grid ]
		}]
	});
	
	store.load();
	
	function initEdit() {
		var record = selModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '你没有选中任何项目！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
		    });
			return;
		} 
		selModel.deselectAll();
		couponsWindow.setTitle('修改');
		couponsForm.loadRecord(record);
		couponsForm.getForm().url = '/goods/updateCoupons.atc';
		couponsWindow.show();
	}
	
	function initDelete() {
		var record = selModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '你没有选中任何项目！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
		    });
			return;
		} 
		Ext.Msg.confirm('请确认', '确定要删除这个卡券吗?', function(btn, text) {
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : '/goods/deleteCoupons.atc',
					params : {
						'coupons.id' : record.raw.id
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							couponsWindow.hide();
							Ext.example.msg('提示', result.msg);
							selModel.deselectAll();
							store.reload();
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