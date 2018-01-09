Ext.onReady(function() {
	
	Ext.QuickTips.init();
	var rechargeId;
	
	// 定义自动当前页行号
	var rechargeRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 30
	});
	
	var rechargeDetailRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 30
	});
	
	Ext.define('ProjectModel', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'itemNo', type: 'string'},
	        {name: 'goodsId', type: 'string'},
	        {name: 'goodsType', type: 'int'},
	        {name: 'goodsName', type: 'string'},
	        {name: 'unitPrice', type: 'float'},
	        {name: 'discount', type: 'float'},
	        {name: 'price', type: 'float'},	//bool
	        {name: 'number', type: 'int'},
	        {name: 'performer', type: 'string'},
	        {name: 'seller', type: 'string'},
	        {name: 'source', type: 'string'},
	        {name: 'isDeduction', type: 'int'}
	    ]
	});
	var ProjectStore = new Ext.data.Store({
        model: ProjectModel
    });
	var ProjectItemStore = new Ext.data.Store({
		autoLoad: true,
		fields : ['id', 'typeName', 'sortNo'],  
		proxy:{
            type: 'ajax',
            url : '/goods/queryHourTypeByDeptCode.atc',
            reader : {
                root: 'hourTypeList'
            }
        },
        sorters: [{
            property: 'sortNo',
            direction:'ASC'
        }]
	});
	var ProjectItemCombo = Ext.create('Ext.form.ComboBox', {
		store: ProjectItemStore,
		valueField: 'id',
		displayField: 'typeName',
		name: 'recharge.name',
		labelWidth: 80, 
		labelAlign:'right',
		fieldLabel: '服务类型',
        editable : false,
        emptyText:'请选择类型...', 
        triggerAction: 'all',
        lastQuery: '',
        listeners: {
        	select: function(cb, records){
        		var record = records[0];
        		ProjectSecondItemStore.load({
        			params: {
        				typeId: Number(record.data.id)
        			},
        			callback: function(records){
        				if(!Ext.isEmpty(records)){
        					ProjectSecondItemCombo.select(records[0]);
        					Ext.getCmp("value").setValue(records[0].data.id);
        	    			Ext.getCmp("dname").setValue(records[0].data.name);
        				}
                    }
        		});
            }
        }
	});
    
    Ext.define('ProjectSecondItem', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'id', type: 'string'},
	        {name: 'name', type: 'string'},
	        {name: 'typeId', type: 'string'},
	        {name: 'price', type: 'float'}
	    ]
	});
	var ProjectSecondItemStore = new Ext.data.Store({
		autoLoad: false,
		model: ProjectSecondItem,
		proxy:{
            type: 'ajax',
            url : '/goods/queryGoodsHourByType.atc',
            reader : {
                root: 'hourList'
            }
        }
	});
	var ProjectSecondItemCombo = Ext.create('Ext.form.ComboBox', {
		store: ProjectSecondItemStore,
		valueField: 'id',
		displayField: 'name',
		name: 'recharge.name',
		queryMode: 'local',
        editable : false,
        emptyText:'请选择...', 
        triggerAction: 'all',
        lastQuery: '',
        listeners: {
        	select: function(cb, records){
        		Ext.getCmp("value").setValue(cb.getValue());
    			Ext.getCmp("dname").setValue(records[0].data.name);
        	}
        }
	});
	
	var balanceItem = {fieldLabel: '赠送金额', xtype:'numberfield', minValue:0, listeners : {
		change: function(field, newValue, oldValue) {
			Ext.getCmp("value").setValue(newValue);
			Ext.getCmp("dname").setValue("金额");
		}
	}};
	
	var pointItem = {fieldLabel: '赠送积分', xtype:'numberfield', minValue:0, listeners : {
		change: function(field, newValue, oldValue) {
			Ext.getCmp("value").setValue(newValue);
			Ext.getCmp("dname").setValue("金额");
		}
	}};
	
	var ProductTypeStore = Ext.create('Ext.data.Store', {
		fields: ['id', 'typeName'],
        proxy: {
            type: 'ajax',
            url : '/goods/queryMaterialTypeByDeptCode.atc',
            reader : {
                root: 'materialTypeList'
            }
        }
	});
	ProductTypeStore.load();
	var StockStore = Ext.create('Ext.data.Store', {
		fields: ['id', 'code', 'name', 'number', 'price'],
		proxy: {
            type: 'ajax',
            url : '/goods/queryGoodsMaterialStockByKeyword.atc',
            reader : {
                root: 'materialList',
                totalProperty: 'totalCount'
            }
        }
    });
	var ProductTypeCombo = Ext.create('Ext.form.ComboBox', {
		store: ProductTypeStore,
		labelWidth: 80, 
		labelAlign:'right',
		fieldLabel: '商品类型',
		valueField: 'id',
		displayField: 'typeName',
		name: 'recharge.name',
        editable : false,
        emptyText:'请选择...', 
        triggerAction: 'all',
        lastQuery: '',
        listeners: {
        	select: function(cb, records){
        		var record = records[0];
        		StockStore.getProxy().url = "/goods/queryGoodsMaterialStockByKeyword.atc?typeId=" + record.data.id;
            }
        }
	});
	var StockCombo = Ext.create('Ext.form.ComboBox', {
        store: StockStore,
        labelWidth : 40,
        valueField: 'id',
        displayField: 'name',
        name: 'recharge.name',
        typeAhead: false,
        hideTrigger:true,
        width: 300,
        queryParam: 'keyword',	//搜索参数字段
        minChars: 1,
        pageSize: 10,
        listConfig: {
            loadingText: '查找中...',
            emptyText: '<div style="padding: 5px;">没有找到相关商品！</div>',
            getInnerTpl: function() {
                return '<div>{name}/{price}元/库存:{number}件</div>';
            }
        },
        listeners : {
        	select: function(cb, records) {
            	var record = records[0];
            	Ext.getCmp("value").setValue(cb.getValue());
    			Ext.getCmp("dname").setValue(record.data.name);
			}
		}
	});
	
	var GiveCouponsStore = Ext.create('Ext.data.Store', {
		autoLoad: true,
        fields: ['id', 'name', 'price', 'applyId', 'minimum', 'goodsName'],
        proxy: {
            type: 'ajax',
            url : '/goods/queryCouponsByDept.atc',
            reader : {
                root: 'couponsList'
            }
        }
	});
	var GiveCouponsCombo = Ext.create('Ext.form.ComboBox', {
		store: GiveCouponsStore,
		labelWidth: 80, 
		labelAlign:'right',
		fieldLabel: '选择卡券',
		valueField: 'id',
		displayField: 'name',
		name: 'recharge.name',
        editable : false,
        emptyText:'请选择...', 
        triggerAction: 'all',
        lastQuery: '',
        listeners: {
        	select: function(cb, records){
    			Ext.getCmp("value").setValue(cb.getValue());
    			Ext.getCmp("dname").setValue(records[0].data.name);
            }
        }
	});
	
	var gradeStore = Ext.create('Ext.data.Store', {
		autoLoad: true,
        fields: ['id', 'grade', 'name', 'needPoint', 'discount', 'orderDiscount'],
        proxy: {
            type: 'ajax',
            url : '/member/queryGradeByDeptCode.atc',
            reader : {
                root: 'gradeList'
            }
        }
    });
	
	var gradeCombo = Ext.create('Ext.form.ComboBox', {
		store: gradeStore,
		labelWidth: 80, 
		labelAlign:'right',
		fieldLabel: '选择等级',
		valueField: 'grade',
		displayField: 'name',
		name: 'recharge.name',
        editable : false,
        emptyText:'请选择...', 
        triggerAction: 'all',
        lastQuery: '',
        listeners: {
        	select: function(cb, records){
        		Ext.getCmp("value").setValue(cb.getValue());
    			Ext.getCmp("dname").setValue(records[0].data.name);
            }
        }
	});
	
	var rechargeStore = Ext.create('Ext.data.Store', {
		  fields: ['id', 'deptCode', 'name', 'price', 'mode'],
		  proxy: {
		      type: 'ajax',
			  url : '/shop/queryRechargeByDept.atc',
			  reader : {
			      root: 'rList'
			  }
		  }
	});
	
	var rechargeDetailStore = Ext.create('Ext.data.Store', {
		fields: ['rechargeId', 'itemNo', 'value', 'type', 'number', 'dname', 'dprice', 'days'],
		proxy: {
			type: 'ajax',
			url : '/shop/queryRechargeDetailById.atc',
			reader : {
				root: 'dList'
			}
		}
	});
	
	var rechargeTypeCombo = Ext.create('Ext.form.ComboBox', {
		id: 'type',
		name: 'rechargeDetail.type',
		flex: 2,
		allowBlank: false, 
		fieldLabel: '赠送类型',
		labelWidth: 80,
		labelAlign:'right',
		mode : 'local',
		store: parent.GRechargeTypeStore,
		valueField : 'value',
		displayField : 'text',
		editable : false,
		value: 1,
        listeners : {
        	select: function(cb, records) {
            	if(cb.getValue() == 2) {	//服务
            		rechargeDetailForm.getComponent("dnameItem").removeAll(false);
            		rechargeDetailForm.getComponent("dnameItem").add([ProjectItemCombo, ProjectSecondItemCombo]);
            		Ext.getCmp("number").setDisabled(false);
            		rechargeDetailForm.updateLayout();
            	} else if(cb.getValue() == 3) {	//商品
            		rechargeDetailForm.getComponent("dnameItem").removeAll(false);
            		rechargeDetailForm.getComponent("dnameItem").add([ProductTypeCombo, StockCombo]);
            		Ext.getCmp("number").setDisabled(false);
            		rechargeDetailForm.updateLayout();
            	} else if(cb.getValue() == 4) {	//卡券
            		rechargeDetailForm.getComponent("dnameItem").removeAll(false);
            		rechargeDetailForm.getComponent("dnameItem").add([GiveCouponsCombo]);
            		Ext.getCmp("number").setDisabled(false);
            		rechargeDetailForm.updateLayout();
            	} else if(cb.getValue() == 5) {	//积分
            		rechargeDetailForm.getComponent("dnameItem").removeAll(false);
            		rechargeDetailForm.getComponent("dnameItem").add([pointItem]);
            		Ext.getCmp("number").setDisabled(true);
            		rechargeDetailForm.updateLayout();
            	} else if(cb.getValue() == 6) {	//会员等级
            		rechargeDetailForm.getComponent("dnameItem").removeAll(false);
            		rechargeDetailForm.getComponent("dnameItem").add([gradeCombo]);
            		Ext.getCmp("number").setDisabled(true);
            		rechargeDetailForm.updateLayout();
            	} else {	//金额
            		rechargeDetailForm.getComponent("dnameItem").removeAll(false);
            		rechargeDetailForm.getComponent("dnameItem").add([balanceItem]);
            		Ext.getCmp("number").setDisabled(true);
            		rechargeDetailForm.updateLayout();
            	}
			}
		}
	});
	
	var rechargeForm = new Ext.form.FormPanel({
				id : 'rechargeForm',
				layout: 'anchor',
				defaults: {
		            anchor: '100%',
		            layout: 'hbox',
		            xtype:'fieldcontainer'
		        },
				bodyPadding: '5 10 0 0',
				border : false,
				items : [{
					    defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'right'},
					    items: [
					           {id:'id', name:'recharge.id', xtype:'hiddenfield'},
					           {id:'name', name: 'recharge.name',allowBlank: false, fieldLabel: '名称', blankText:'名称不能为空!' }
					    ]
					},{
					    defaults: {flex: 1, xtype:'numberfield', labelWidth:70, labelAlign:'right'},
					    items: [
					           {flex: 6,id:'price', name: 'recharge.price', allowBlank: false, fieldLabel: '金额', blankText:'金额不能为空!' },
					           {flex: 6,id:'mode', name: 'recharge.mode', allowBlank: true, labelWidth:110, fieldLabel: '赠送方式多个选', value:0, tooltip:'0则全部赠送' },
					           {flex: 1,xtype:'displayfield', value:' 个'}
					    ]
					}
				],
				buttons:[{
					text : '保 存',
					iconCls : 'accept',
					handler : function() {
						if (rechargeForm.form.isValid()) {
							rechargeForm.form.submit({
								waitTitle : '提示',
								method : 'POST',
								waitMsg : '正在处理数据,请稍候...',
								success : function(form, action) {
									rechargeWindow.hide();
									Ext.example.msg('提示', action.result.msg);
									rechargeStore.reload();
								},
								failure : function(form, action) {
									var msg = action.result.msg;
									Ext.MessageBox.alert('提示', msg);
								}
							});
						}
					}
				},{
					text : '关 闭 ',
					iconCls : 'stop',
					handler : function() {
						rechargeWindow.hide();
					}
				}]
			});
	
	var rechargeDetailForm = new Ext.form.FormPanel({
		id : 'rechargeDetailForm',
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
			    defaults: {flex: 1, xtype:'textfield', labelWidth:80, labelAlign:'right'},
			    items: [
			           {id:'rechargeId', name:'rechargeDetail.rechargeId', xtype:'hiddenfield'},
			           {id:'itemNo', name:'rechargeDetail.itemNo', xtype:'hiddenfield'},
			           {id:'value', name: 'rechargeDetail.value', xtype:'hiddenfield'},
			           {id:'dname', name: 'rechargeDetail.dname', xtype:'hiddenfield'},
			           rechargeTypeCombo,
			           {xtype: 'displayfield', value:''}
			    ]
			},{
				itemId: 'dnameItem',
			    defaults: {flex: 1, xtype:'textfield', labelWidth:80, labelAlign:'right'},
			    items: [balanceItem]
			},{
				defaults: {flex: 1, xtype:'numberfield', labelWidth:80, labelAlign:'right'},
				items: [
				        {id:'number', name: 'rechargeDetail.number',fieldLabel: '次数/个数', disabled: true},
				        {xtype:'displayfield', vlaue: ''}
				]
			},{
				defaults: {flex: 1, xtype:'numberfield', labelWidth:80, labelAlign:'right'},
				items: [
				        {id:'days', name: 'rechargeDetail.days',fieldLabel: '有效天数'},
				        {id:'dprice', name: 'rechargeDetail.dprice', fieldLabel: '价值' }
				]
			}
		],
		buttons:[{
			text : '保 存',
			iconCls : 'accept',
			handler : function() {
				if (rechargeDetailForm.form.isValid()) {
					Ext.getCmp("rechargeId").setValue(rechargeId);
					Ext.getCmp("itemNo").setValue((rechargeDetailStore.getCount() + 1) * 10);
					rechargeDetailForm.form.submit({
						waitTitle : '提示',
						method : 'POST',
						waitMsg : '正在处理数据,请稍候...',
						success : function(form, action) {
							rechargeDetailWindow.hide();
							Ext.example.msg('提示', action.result.msg);
							rechargeDetailStore.reload();
						},
						failure : function(form, action) {
							var msg = action.result.msg;
							Ext.MessageBox.alert('提示', msg);
						}
					});
				}
			}
		},{
			text : '关 闭 ',
			iconCls : 'stop',
			handler : function() {
				rechargeDetailWindow.hide();
			}
		}]
	});
	
	var rechargeWindow = new Ext.Window({
		title : '修改', // 窗口标题
		layout : 'fit',
		width : 400,
		height : 160,
		x: 100,
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
		items: [rechargeForm]
	});
	
	var rechargeDetailWindow = new Ext.Window({
		title : '修改',
		layout : 'fit',
		width : 430,
		height : 210,
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
		items: [rechargeDetailForm]
	});
	
	var typeRenderer = function(v){
		var record = parent.GRechargeTypeStore.findRecord("value", v);
		return (Ext.isEmpty(record)) ? "" : record.data.text;
	};
	
  	var rechargeColumns= [rechargeRowNum,
	            {header : 'ID',dataIndex: 'id', width: 60, hidden: true},
	            {header : '名称',dataIndex: 'name', width: 150},
	            {header : '金额',dataIndex: 'price', width: 60, renderer: Ext.util.Format.numberRenderer("0.00")},
	            {header : '方式',dataIndex: 'mode', width: 60, renderer: function(v){return '多选' + v;}}
	  	];
  	
  	var rechargeDetailColumns= [rechargeDetailRowNum,
 	            {header : 'ID',dataIndex: 'rechargeId', hidden: true},
 	            {header : '项目号',dataIndex: 'itemNo', hidden: true},
 	            {header : '赠送内容ID',dataIndex: 'value', width: 120, hidden: true},
 	            {header : '类型',dataIndex: 'type', width: 60, renderer: typeRenderer},
 	            {header : '名称',dataIndex: 'dname', width: 100},
 	            {header : '次数',dataIndex: 'number', width: 80},
 	            {header : '有效天数',dataIndex: 'days', width: 80},
 	            {header : '价值',dataIndex: 'dprice', width: 80, renderer: Ext.util.Format.numberRenderer("0.00")},
 	  	];
  	
	var rechargeTbar = Ext.create('Ext.toolbar.Toolbar', {
			region : 'north',
			border : false,
	        items: [{
	        	xtype: 'button',
	        	id: 'insert',
	        	text: '新建',
	        	iconCls: 'add',
	        	hidden : !parent.haveActionMenu('Add'),
	        	handler: function() {
	        		rechargeDetailWindow.hide();
	        		rechargeWindow.setTitle('新建');
	        		rechargeForm.getForm().reset();
	        		rechargeForm.getForm().url = '/shop/insertRecharge.atc';
	        		rechargeWindow.show();
	            }
	        },{
	        	xtype: 'button',
	        	id: 'update',
	        	text: '修改',
	        	iconCls: 'pencil',
	        	hidden : !parent.haveActionMenu('Edit'),
	        	handler: function() {
	        		initRechargeEdit();
	            }
	        },{
	        	xtype: 'button',
	        	id: 'delete',
	        	text: '删除',
	        	iconCls: 'delete', 
	        	hidden : !parent.haveActionMenu('Del'),
	        	handler: function(){
	        		initRechargeDelete();
	            }
	        }]
	    });
	
	var rechargeDetailTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	id: 'detailInsert',
        	text: '新建',
        	iconCls: 'add',
        	hidden : !parent.haveActionMenu('AddDetail'),
        	handler: function() {
        		initDetailInsert();
            }
        },{
        	xtype: 'button',
        	id: 'hourUpdate',
        	text: '修改',
        	iconCls: 'pencil',
        	hidden : !parent.haveActionMenu('EditDetail'),
        	handler: function() {
        		initDetailEdit();
            }
        },{
        	xtype: 'button',
        	id: 'hourDelete',
        	text: '删除',
        	iconCls: 'delete', 
        	hidden : !parent.haveActionMenu('DelDetail'),
        	handler: function(){
        		initDetailDelete();
            }
        }]
    });
	
	var rechargeSelModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1,
		mode: 'SINGLE'
	});
	
	var rechargeDetailSelModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1,
		mode: 'SINGLE'
	});
	
	var rechargeGrid = new Ext.grid.GridPanel({
			columns : rechargeColumns,
			store : rechargeStore, 
			region : 'center',
			autoScroll : true,
			columnLines : true,
			selModel : rechargeSelModel,
			tbar : rechargeTbar, 
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
		        	rechargeId = record.data.id;
		        	rechargeDetailStore.load({
						params: {
							'recharge.id' : record.data.id
						}
					});
		        }
		    }
		});
	
	var rechargeDetailGrid = new Ext.grid.GridPanel({
		columns : rechargeDetailColumns,
		store : rechargeDetailStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		selModel : rechargeDetailSelModel,
		tbar : rechargeDetailTbar, 
		stripeRows:true,
		forceFit: true,
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
			title : '充值活动',
			iconCls : 'tag_blue',
			tools : [{
				type:'refresh',
				handler : function() {
					rechargeDetailStore.reload();
				}
			}],
			collapsible : true,
			width : '40%',
			split : true,
			region : 'west',
			layout : 'fit',
			border : false,
			items : [ rechargeGrid ]
		}, {
			title : '赠送商品',
			iconCls : 'bricks',
			tools : [{
				type:'refresh',
				handler : function() {
					rechargeDetailStore.reload();
				}
			},{
	            type:'help',
	            tooltip: '操作指引',
	            callback: function(panel, tool, event) {
	            	parent.global.openSystemHelpTab();
	            }
	        }],
			region : 'center',
			split : true,
			layout : 'fit',
			border : false,
			items : [ rechargeDetailGrid ]
		}]
	});
	
	rechargeStore.load();
	
	function initRechargeEdit() {
		var record = rechargeSelModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '你没有选中任何项目！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
		    });
			return;
		}
		rechargeDetailWindow.hide();
		rechargeSelModel.deselectAll();
		rechargeWindow.setTitle('修改');
		rechargeForm.getForm().reset();
		rechargeForm.loadRecord(record);
		rechargeForm.getForm().url = '/shop/updateRecharge.atc';
		rechargeWindow.show();
	}
	
	function initRechargeDelete() {
		var record = rechargeSelModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '你没有选中任何项目！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
		    });
			return;
		}
		Ext.Msg.confirm('请确认', '删除这个充值活动吗?', function(btn, text) {
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : '/shop/deleteRecharge.atc',
					params : {
						'recharge.id' : record.raw.id
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							rechargeWindow.hide();
							Ext.example.msg('提示', result.msg);
							rechargeStore.reload();
							rechargeDetailStore.reload();
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
	
	function initDetailInsert() {
		var record = rechargeSelModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '你没有选中任何活动！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
		    });
			return;
		}
		rechargeWindow.hide();
		rechargeDetailWindow.setTitle('新建');
		rechargeDetailForm.getForm().reset();
		rechargeDetailForm.getForm().url = '/shop/insertRechargeDetail.atc';
		rechargeDetailWindow.show();
	}
	
	function initDetailEdit() {
		var record = rechargeDetailSelModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '你没有选中任何项目！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
		    });
			return;
		}
		rechargeWindow.hide();
		rechargeDetailSelModel.deselectAll();
		rechargeDetailWindow.setTitle('修改');
		rechargeDetailForm.getForm().reset();
		rechargeDetailForm.loadRecord(record);
		rechargeDetailForm.getForm().url = '/shop/updateRechargeDetail.atc';
		rechargeDetailWindow.show();
	}
	
	function initDetailDelete() {
		var record = rechargeDetailSelModel.getSelection()[0];
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
					url : '/shop/deleteRechargeDetail.atc',
					params : {
						'rechargeDetail.rechargeId' : record.data.rechargeId,
						'rechargeDetail.itemNo': record.data.itemNo
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							rechargeWindow.hide();
							Ext.example.msg('提示', result.msg);
							rechargeDetailSelModel.deselectAll();
							rechargeDetailStore.reload();
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