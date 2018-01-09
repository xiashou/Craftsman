/**
 * 员工设置
 * @author Think_XIA
 */
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	var leaderRatio, orderRatio, salesRatio;	//提成参数
	
	var date = new Date();
	date.setDate(1);
	
	var loadMarsk = new Ext.LoadMask(Ext.getBody(), {    
	    msg:'正在计算提成，请稍候......',    
	    removeMask:true // 完成后移除    
	});
	
	var rowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	var rowNum2 = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	var rowNum3 = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	Ext.define('CommissionModel', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'orderId', type: 'string'},
	        {name: 'itemNo', type: 'string'},
	        {name: 'saleDate', type: 'string'},
	        {name: 'goodsId', type: 'string'},
	        {name: 'goodsType', type: 'string'},
	        {name: 'goodsName', type: 'string'},
	        {name: 'unitPrice', type: 'float'},
	        {name: 'number', type: 'float'},
	        {name: 'price', type: 'float'},
	        {name: 'source', type: 'string'},
	        {name: 'cprice', type: 'float'},
	        {name: 'performer', type: 'string'},
	        {name: 'seller', type: 'string'},
	        {name: 'middleman', type: 'string'}
	    ]
	});
	
//	var employeeStore = Ext.create('Ext.data.Store', {
//		  fields: ['id', 'name', 'position', 'entryDate', 'enable'],
//		  proxy: {
//		      type: 'ajax',
//			  url : '/shop/queryEmployeeByDept.atc',
//			  reader : {
//			      root: 'employeeList'
//			  }
//		  }
//	});
	
	var employeeStore = Ext.create('Ext.data.Store', {
		fields: ['id', 'name', 'isGroup'],
        proxy: {
            type: 'ajax',
            url : '/shop/queryAllEmployeeByDept.atc',
            reader : {
                root: 'employeeList'
            }
        }
	});
	
	var PerformerPanel = Ext.create('Ext.form.Panel', {
    	autoHeight: true,
		layout: 'anchor',
        defaults: {
            anchor: '100%',
        },
        bodyPadding: 10,
        items: [{
        	xtype: 'fieldset',
			itemId: 'PerformerSet',
			title: '员工',
			collapsible: false,
			defaults: {
				labelWidth: 89,
				anchor: '100%',
				layout: {
					type: 'hbox',
					defaultMargins: {top: 0, right: 5, bottom: 0, left: 0}
				}
			},
            items: [{
				xtype: 'container',
				id:'PerformerSet',
				itemId: 'PerformerContainer',
				layout: 'column',
				defaults: {
                	columnWidth: 1/3,
                	xtype: 'checkbox'
                }
            }]
        },{
        	xtype: 'fieldset',
			itemId: 'PerformerGroupSet',
			title: '员工组',
			collapsible: false,
			defaults: {
				labelWidth: 89,
				anchor: '100%',
				layout: {
					type: 'hbox',
					defaultMargins: {top: 0, right: 5, bottom: 0, left: 0}
				}
			},
            items: [{
				xtype: 'container',
				id: 'PerformerGroupSet',
				itemId: 'PerformerGroupContainer',
				layout: 'column',
				defaults: {
                	columnWidth: 1/3,
                	xtype: 'checkbox'
                }
            }]
        }],
        buttons:[{
        	text : '确 定',
			iconCls : 'accept',
			handler : function() {
				var employee = Ext.getCmp('PerformerSet').items;
				var group = Ext.getCmp('PerformerGroupSet').items;
                var value = '', display = '';
                for(var i = 0; i < employee.length; i++){
                    if(employee.get(i).checked){
                    	value += employee.get(i).inputValue + ",";
                    	display += employee.get(i).boxLabel + ",";
                    }
                }
                for(var i = 0; i < group.length; i++){
                    if(group.get(i).checked){
                    	value += group.get(i).inputValue + ",";
                    	display += group.get(i).boxLabel + ",";
                    }
                }
                var rowRecord = commissionGrid.getSelectionModel().getLastSelected();
                rowRecord.data.performer = value;
                rowRecord.commit();
                PerformerWindow.hide();
			}
        }]
	});
    var SellerPanel = Ext.create('Ext.form.Panel', {
    	autoHeight: true,
		layout: 'anchor',
        defaults: {
            anchor: '100%',
        },
        bodyPadding: 10,
        items: [{
        	xtype: 'fieldset',
			itemId: 'SellerSet',
			title: '员工',
			collapsible: false,
			defaults: {
				labelWidth: 89,
				anchor: '100%',
				layout: {
					type: 'hbox',
					defaultMargins: {top: 0, right: 5, bottom: 0, left: 0}
				}
			},
            items: [{
				xtype: 'container',
				id:'SellerSet',
				itemId: 'SellerContainer',
				layout: 'column',
                defaults: {
                	columnWidth: 1/3,
                	xtype: 'checkbox'
                }, 
            }]
        },{
        	xtype: 'fieldset',
			itemId: 'SellerGroupSet',
			title: '员工组',
			collapsible: false,
            items: [{
				xtype: 'container',
				id: 'SellerGroupSet',
				itemId: 'SellerGroupContainer',
				layout: 'column',
                defaults: {
                	columnWidth: 1/3,
                	margin: 5,
                }, 
                defaultType: 'checkbox'
            }]
        }],
        buttons:[{
        	text : '确 定',
			iconCls : 'accept',
			handler : function() {
				var employee = Ext.getCmp('SellerSet').items;
				var group = Ext.getCmp('SellerGroupSet').items;
                var value = '', display = '';
                for(var i = 0; i < employee.length; i++){
                    if(employee.get(i).checked){
                    	value += employee.get(i).inputValue + ",";
                    	display += employee.get(i).boxLabel + ",";
                    }
                }
                for(var i = 0; i < group.length; i++){
                    if(group.get(i).checked){
                    	value += group.get(i).inputValue + ",";
                    	display += group.get(i).boxLabel + ",";
                    }
                }
                var rowRecord = commissionGrid.getSelectionModel().getLastSelected();
            	rowRecord.data.seller = value;
            	rowRecord.commit();
            	SellerWindow.hide();
			}
        }]
	});
    var MiddlePanel = Ext.create('Ext.form.Panel', {
    	autoHeight: true,
		layout: 'anchor',
        defaults: {
            anchor: '100%',
        },
        bodyPadding: 10,
        items: [{
        	xtype: 'fieldset',
			itemId: 'MiddleSet',
			title: '员工',
			collapsible: false,
			defaults: {
				labelWidth: 89,
				anchor: '100%',
				layout: {
					type: 'hbox',
					defaultMargins: {top: 0, right: 5, bottom: 0, left: 0}
				}
			},
            items: [{
				xtype: 'container',
				id:'MiddleSet',
				itemId: 'MiddleContainer',
				layout: 'column',
                defaults: {
                	columnWidth: 1/3,
                	xtype: 'checkbox'
                }, 
            }]
        },{
        	xtype: 'fieldset',
			itemId: 'MiddleGroupSet',
			title: '员工组',
			collapsible: false,
            items: [{
				xtype: 'container',
				id: 'MiddleGroupSet',
				itemId: 'MiddleGroupContainer',
				layout: 'column',
                defaults: {
                	columnWidth: 1/3,
                	margin: 5,
                }, 
                defaultType: 'checkbox'
            }]
        }],
        buttons:[{
        	text : '确 定',
			iconCls : 'accept',
			handler : function() {
				var employee = Ext.getCmp('MiddleSet').items;
				var group = Ext.getCmp('MiddleGroupSet').items;
                var value = '', display = '';
                for(var i = 0; i < employee.length; i++){
                    if(employee.get(i).checked){
                    	value += employee.get(i).inputValue + ",";
                    	display += employee.get(i).boxLabel + ",";
                    }
                }
                for(var i = 0; i < group.length; i++){
                    if(group.get(i).checked){
                    	value += group.get(i).inputValue + ",";
                    	display += group.get(i).boxLabel + ",";
                    }
                }
                var rowRecord = commissionGrid.getSelectionModel().getLastSelected();
            	rowRecord.data.middleman = value;
            	rowRecord.commit();
            	MiddleWindow.hide();
			}
        }]
	});
	var PerformerWindow = Ext.create('Ext.Window',{
		layout : 'fit',
		title: '选择施工方',
		width : 420,
		height : 400,
		resizable : false,
		draggable : true,
		closeAction : 'hide',
		modal : false,
		autoScroll: true,
		collapsible : true,
		titleCollapse : true,
		maximizable : false,
		buttonAlign : 'right',
		border : false,
		animCollapse : true,
		constrain : true,
		items : [PerformerPanel]
	});
	var SellerWindow = Ext.create('Ext.Window',{
		layout : 'fit',
		title: '选择销售方',
		width : 420,
		height : 400,
		resizable : false,
		draggable : true,
		closeAction : 'hide',
		modal : false,
		autoScroll: true,
		collapsible : true,
		titleCollapse : true,
		maximizable : false,
		buttonAlign : 'right',
		border : false,
		animCollapse : true,
		constrain : true,
		items : [SellerPanel]
	});
	var MiddleWindow = Ext.create('Ext.Window',{
		layout : 'fit',
		title: '选择辅助销售',
		width : 420,
		height : 400,
		resizable : false,
		draggable : true,
		closeAction : 'hide',
		modal : false,
		autoScroll: true,
		collapsible : true,
		titleCollapse : true,
		maximizable : false,
		buttonAlign : 'right',
		border : false,
		animCollapse : true,
		constrain : true,
		items : [MiddlePanel]
	});
	employeeStore.on('load', function(){
		if(!Ext.isEmpty(employeeStore)){
			Ext.each(employeeStore.data.items, function(employee){
				if(!employee.data.isGroup){
					SellerPanel.getComponent("SellerSet").getComponent("SellerContainer").add([{
						boxLabel: employee.data.name, inputValue: employee.data.id
					}]);
					PerformerPanel.getComponent("PerformerSet").getComponent("PerformerContainer").add([{
						boxLabel: employee.data.name, inputValue: employee.data.id
					}]);
					MiddlePanel.getComponent("MiddleSet").getComponent("MiddleContainer").add([{
						boxLabel: employee.data.name, inputValue: employee.data.id
					}]);
				} else {
					SellerPanel.getComponent("SellerGroupSet").getComponent("SellerGroupContainer").add([{
						boxLabel: employee.data.name, inputValue: employee.data.id
					}]);
					PerformerPanel.getComponent("PerformerGroupSet").getComponent("PerformerGroupContainer").add([{
						boxLabel: employee.data.name, inputValue: employee.data.id
					}]);
					MiddlePanel.getComponent("MiddleGroupSet").getComponent("MiddleGroupContainer").add([{
						boxLabel: employee.data.name, inputValue: employee.data.id
					}]);
				}
			});
		}
	});
	employeeStore.load();
	
//	var employeeGroupStore = Ext.create('Ext.data.Store', {
//		  fields: ['id', 'name', 'groupLeader', 'groupMember', 'enable'],
//		  proxy: {
//		      type: 'ajax',
//			  url : '/shop/queryEmployeeGroupByDept.atc',
//			  reader : {
//			      root: 'employeeList'
//			  }
//		  }
//	});
	
	var commissionStore = new Ext.data.Store({
		model: CommissionModel,
		proxy: {
		      type: 'ajax',
			  url : '/order/queryBuildCommOrder.atc',
			  reader : {
			      root: 'orderList'
			  }
		}
    });
	
	var cpriceRenderer = function(value, cellmeta, record) {
		var cprice = 0;
		if(record.data.source.indexOf('%') > 0)
			cprice =  Number(Number(record.data.source.replace('%', '')) * Number(record.data.price * 0.01)).toFixed(2);
		else
			cprice = Number(record.data.source).toFixed(2);
		return Ext.util.Format.number(cprice, "0.00");
	}
	
	var employeeRenderer = function(value, cellmeta, record) {
		if(!Ext.isEmpty(value)){
			if(value.indexOf(",") > 0){
				var names = "";
				var ids = value.split(",");
				for (index in ids){
					if(!Ext.isEmpty(ids[index].trim()))
						names += employeeStore.findRecord("id", ids[index].trim()).data.name + ',';
				}
				return names;
			} else 
				return employeeStore.findRecord("id", value.trim()).data.name;
		} else
			return "";
    };
	
	var columns = [rowNum, {
        text     : '订单号',
        sortable : false,
        dataIndex: 'orderId'
    }, {
        text     : '行项目',
        dataIndex: 'itemNo',
        hidden	 : true
    }, {
    	text     : '销售时间',
    	sortable : true,
    	width: 130,
    	dataIndex: 'saleDate'
    }, {
    	text     : '项目/商品',
    	sortable : true,
    	width: 200,
    	dataIndex: 'goodsName'
    }, {
    	text     : '数量',
    	sortable : true,
    	width: 60,
    	summaryType: 'sum',
    	dataIndex: 'number'
    }, {
    	text     : '工时费/单价',
    	sortable : true,
    	dataIndex: 'unitPrice',
    	summaryType: 'sum',
    	renderer: Ext.util.Format.numberRenderer("0.00")
    }, {
    	text     : '毛利',
    	sortable : true,
    	dataIndex: 'price',
    	summaryType: 'sum',
    	renderer: Ext.util.Format.numberRenderer("0.00")
    }, {
    	text     : '提成方式',
    	sortable : true,
    	dataIndex: 'source'
    }, {
    	text     : '提成金额',
    	sortable : true,
    	dataIndex: 'cprice',
    	summaryType: 'sum',
    	renderer: cpriceRenderer
    }, {
    	text     : '施工方',
    	width	 : 190,
    	dataIndex: 'performer',
    	renderer : employeeRenderer,
    	editor: {xtype: 'textfield', allowBlank: false,
            listeners: {
            	focus: function(field, newValue, oldValue){
            		SellerWindow.hide();
            		MiddleWindow.hide();
            		PerformerWindow.show();
                }
            }
    	}
    }, {
    	text     : '销售方',
    	width	 : 190,
    	dataIndex: 'seller',
    	renderer : employeeRenderer,
    	editor: {xtype: 'textfield', allowBlank: false,
            listeners: {
            	focus: function(field, newValue, oldValue){
            		PerformerWindow.hide();
            		MiddleWindow.hide();
            		SellerWindow.show();
                }
            }
    	}
    }, {
    	text     : '辅助销售',
    	width	 : 190,
    	dataIndex: 'middleman',
    	renderer : employeeRenderer,
    	editor: {xtype: 'textfield', allowBlank: false,
            listeners: {
            	focus: function(field, newValue, oldValue){
            		SellerWindow.hide();
            		PerformerWindow.hide();
            		MiddleWindow.show();
                }
            }
    	}
    }];
	
	var orderTypeCombo = new Ext.form.ComboBox({
		triggerAction : 'all',
		mode : 'local',
		store: parent.GOrderTypeStore,
		emptyText: '订单类型',
		valueField : 'value',
		displayField : 'text',
		editable : true,
		width: 140
	});
	
	var tbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'datefield',
        	id: 'startDate',
        	emptyText: '开始日期',
        	width: 140,
        	format:'Y/m/d',
        	value: date
        },{
        	xtype: 'datefield',
        	id: 'endDate',
        	emptyText: '结束日期',
        	width: 140,
        	format:'Y/m/d'
        },orderTypeCombo,{
        	xtype: 'textfield',
        	id: 'goodsName',
        	emptyText: '项目/商品',
        	width: 160,
        },{
        	xtype: 'button',
        	id: 'search',
        	text: ' 查 询 ',
        	iconCls: 'preview', 
        	handler: function(){
        		showCommission();
            }
        },{
        	xtype: 'button',
        	id: 'save',
        	text: ' 保 存 ',
        	iconCls: 'accept', 
        	handler: function(){
        		saveCommissioner();
        	}
        },'->',{
        	xtype: 'button',
        	text:'导出Excel',
        	iconCls : 'page_white_excel',
        	handler: function() {
        		commissionGrid.downloadExcelXml();
        	}
        }]
    });
	
	var cellEditing = new Ext.grid.plugin.CellEditing({
        clicksToEdit: 1
    });
	
	var selModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1
	});
	
	var commissionGrid = new Ext.grid.GridPanel({
		title: '员工设置',
		iconCls: 'wrench',
		columns : columns,
		store : commissionStore,
		region : 'center',
		autoScroll : true,
		columnLines : true,
		tbar : tbar, 
		selModel: selModel,
		stripeRows:true,
		tools:[{
            type:'help',
            tooltip: '操作指引',
            callback: function(panel, tool, event) {
            	parent.global.openSystemHelpTab();
            }
        }],
        plugins: [cellEditing],
        features: [{
            ftype: 'summary',
            dock: 'bottom'
        }],
		viewConfig : {
			enableTextSelection:true	//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载,请稍等...'
		}
	});
	
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [commissionGrid]
	});
	
	function showCommission() {
		commissionStore.load({
			params : getParams()
		});
	}
	
	function getParams(){
		return {
			'orderItem.goodsName': Ext.getCmp("goodsName").getValue(),
			'orderItem.startDate': parent.dateFormat(Ext.getCmp("startDate").getValue()),
			'orderItem.endDate': parent.dateFormat(Ext.getCmp("endDate").getValue()),
			'orderItem.orderType': orderTypeCombo.getValue()
		};
	}
	
	function saveCommissioner() {
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
		var storeSelect = selModel.getSelection();
		var jsonArray = [];
        Ext.each(storeSelect, function(item){
        	if(!Ext.isEmpty(item.data.goodsId)){
        		jsonArray.push(item.data);
        	}
        });
		Ext.Ajax.request({
            method:'POST',
            url: '/shop/saveCommissioner.atc',
            params : {
				jsonStr : Ext.encode(jsonArray)
			},
			success : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
				if(result.success){
					Ext.example.msg('提示', result.msg);
					commissionStore.reload();
				} else 
					Ext.MessageBox.show({
			           title: '提示',
			           msg: result.msg,
			           buttons: Ext.MessageBox.OK,
			           icon: Ext.MessageBox.ERROR
	                });
			},
			failure : function(resp, opts) {
				Ext.MessageBox.hide();
				var result = Ext.decode(resp.responseText);
                Ext.Msg.alert('提示', result.msg);
			}
        });
	}
	
	
});