Ext.onReady(function() {
	Ext.QuickTips.init();
	
	var checkTotal = 0, checkDate = new Date(), already = 0, wait = 0, difference = 0; 
	
	var stockRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var diffNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	Ext.define('Type',{
		extend : 'Ext.data.Model',
		fields   : [
            {type:'int',name:'id'},
            {type:'string',name:'typeName'}
		]
	});
	
	var stockStore = Ext.create('Ext.data.Store', {
		  fields: ['goodsId', 'typeId', 'typeName', 'code', 'name', 'number', 'actual'],
		  proxy: {
		      type: 'ajax',
			  url : '/goods/queryGoodsStockByDept.atc',
			  reader : {
			      root: 'stockList'
			  }
		  },
		  listeners: {
			  load : function(store, records, successful, operation){
	        	    if(successful){
		        		checkTotal = store.getCount();
		        		wait = checkTotal;
		        		Ext.getCmp("checkTotal").setValue(checkTotal);
		        		Ext.getCmp("wait").setValue(wait);
//		        		Ext.each(records, function(item){
//		        			item.data.actual = item.data.number;
//		        			item.commit();
//		        		});
	        	    }
	        	}
		  }
	});
	
	stockStore.on('update', function(){
		completeCheckRow();
	});
	
//	stockStore.on('load', function(){
//		
//	});
	
	var diffStore = Ext.create('Ext.data.Store', {
		  fields: ['goodsId', 'typeId', 'typeName', 'code', 'name', 'number', 'actual']
	});
	
	var typeStore = Ext.create('Ext.data.Store', {
		model: 'Type',
        proxy: {
            type: 'ajax',
            url : '/goods/queryMaterialTypeByDeptCode.atc',
            reader : {
                root: 'materialTypeList'
            }
        },
        listeners:{
        	load : function(store, records, successful, operation){
        	    if(successful){
	        		var allType = Ext.create('Type',{
						id: 0,
						typeName: '所有类型'
					});
					typeStore.insert(0, allType);
        	    }
        	}
        }
	});
	typeStore.load();
	var typeCombo = Ext.create('Ext.form.field.ComboBox', {
		store: typeStore,
		valueField: 'id',
		displayField: 'typeName',
		value: 0,
		width: '100%',
        editable : false,
        emptyText:'请选择...', 
        triggerAction: 'all',
        lastQuery: '',
        listeners: {
        	select: function(cb, records){
        		var record = records[0];
        		stockStore.clearFilter();
        		if(record.data.id != 0) {
        			stockStore.filter({
        				property      : 'typeName',
        				value         : record.data.typeName,
        				anyMatch      : true,
        				caseSensitive : false
        			});
        		}
            }
        }
	});
	
	var stockColumns = [stockRowNum,
      	            {header : 'ID',dataIndex: 'goodsId', hidden: true},
      	            {header : '类型',dataIndex: 'typeName', width: 100, items : typeCombo},
      	            {header : '编码',dataIndex: 'code', width: 120,
      	            	items : {
	      	  	            xtype: 'textfield',
	      	  	            flex : 1,
	      	  	            margin: 2,
	      	  	            enableKeyEvents: true,
	      	  	            listeners: {
	      	  	                keyup: function() {
	      	  	                	stockStore.clearFilter();
	      	  	                    if (this.value) {
	      	  	                    	stockStore.filter({
	      	  	                            property      : 'code',
	      	  	                            value         : this.value,
	      	  	                            anyMatch      : true,
	      	  	                            caseSensitive : false
	      	  	                        });
	      	  	                    }
	      	  	                },
	      	  	                buffer: 500
	      	  	            }
      	            	}},
      	            {header : '名称',dataIndex: 'name', width: 200,
      	            	items : {
	      	  	            xtype: 'textfield',
	      	  	            flex : 1,
	      	  	            margin: 2,
	      	  	            enableKeyEvents: true,
	      	  	            listeners: {
	      	  	                keyup: function() {
	      	  	                	stockStore.clearFilter();
	      	  	                    if (this.value) {
	      	  	                    	stockStore.filter({
	      	  	                            property      : 'name',
	      	  	                            value         : this.value,
	      	  	                            anyMatch      : true,
	      	  	                            caseSensitive : false
	      	  	                        });
	      	  	                    }
	      	  	                },
	      	  	                buffer: 500
	      	  	            }
      	            	}},
      	            {header : '数量',dataIndex: 'number', width: 80, summaryType: 'sum'},
      	            {header : '实盘数',dataIndex: 'actual', width: 80, summaryType: 'sum',editor: {xtype: 'numberfield', allowBlank: false, minValue: 0, maxValue: 100000}}
      	  	];
	
	var diffColumns = [diffNum,
                    {header : 'ID',dataIndex: 'goodsId', hidden: true},
                    {header : '类型',dataIndex: 'typeName', width: 80},
                    {header : '编码',dataIndex: 'code', width: 100},
                    {header : '名称',dataIndex: 'name', width: 180},
                    {header : '数量',dataIndex: 'number', width: 80, summaryType: 'sum'},
                    {header : '实盘数',dataIndex: 'actual', width: 80, summaryType: 'sum'},
                    {header : '调整', width:40, align:"center",xtype: 'actioncolumn',
                    	items: [{
                            iconCls: 'accept',
                            tooltip: '调整库存为实盘数',
                            handler: function(grid, rowIndex, colIndex) {
                            	var stock = diffGrid.getStore().getAt(rowIndex);
                            	Ext.Ajax.request({
                                    method:'POST',
                                    url: '/goods/updateGoodsStock.atc',
                                    params : {
                						'stock.goodsId': stock.data.goodsId,
                						'stock.number': stock.data.actual
                					},
                					success : function(resp, opts) {
                						var result = Ext.decode(resp.responseText);
                						if(result.success){
                							Ext.example.msg('提示', result.msg);
                							diffGrid.getStore().removeAt(rowIndex);
                							if(diffGrid.getStore().getCount() == 0){
                                        		Ext.getCmp("finish").setDisabled(false);
                                        	}
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
                                        Ext.Msg.alert('提示', result.msg);
                					}
                                });
                            	
                            }
                        }]
                    },
                    {header : '删除', width:40, align:"center",xtype: 'actioncolumn',
                    	items: [{
                            iconCls: 'delete',
                            tooltip: '删除记录',
                            handler: function(grid, rowIndex){
                            	diffGrid.getStore().removeAt(rowIndex);
                            	if(diffGrid.getStore().getCount() == 0){
                            		Ext.getCmp("finish").setDisabled(false);
                            	}
                            }
                        }]
                    }
           ];
	
	var stockTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	id: 'start',
        	text: '开始盘点',
        	hidden : !parent.haveActionMenu('Check'),
        	iconCls: 'table_lightning',
        	handler: function(){
        		Ext.Msg.confirm('请确认', '盘点期间所有库存冻结，确定开始盘点吗?', function(btn, text) {
        			if(btn == 'yes'){
        				stockStore.load();
//        				typeStore.load();
        				Ext.getCmp("start").setDisabled(true);
        				Ext.getCmp("end").setDisabled(false);
        			}
        		});
        	}
        },{
        	xtype: 'button',
        	id: 'end',
        	text: '盘点结束',
        	iconCls: 'table_save',
        	disabled: true,
        	handler: function(){
        		if(wait > 0)
        			Ext.Msg.confirm('请确认', '当前还有未盘商品，确定结束吗?', function(btn, text) {
            			if(btn == 'yes'){
        					Ext.getCmp("end").setDisabled(true);
        					Ext.getCmp("diff").setDisabled(false);
        					Ext.MessageBox.alert('提示', "库存存在差异，请继续处理差异！", function(){toDiffStore();});
            			}
            		});
        		else
        			if(difference == 0){
        				Ext.MessageBox.show({
      			           title: '提示',
      			           msg: "盘点完成！",
      			           buttons: Ext.MessageBox.OK,
      			           icon: Ext.MessageBox.YES
      	                });
        				location.reload();
    				} else {
    					Ext.getCmp("end").setDisabled(true);
    					Ext.getCmp("diff").setDisabled(false);
    					Ext.MessageBox.alert('提示', "库存存在差异，请继续处理差异！", function(){toDiffStore();});
//    					Ext.MessageBox.show({
//      			           title: '提示',
//      			           msg: "库存存在差异，请继续处理差异！",
//      			           buttons: Ext.MessageBox.OK,
//      			           icon: Ext.MessageBox.ERROR
//      	                });
//     					Ext.getCmp("end").setDisabled(true);
//     					Ext.getCmp("diff").setDisabled(false);
//     					toDiffStore();
    				}
        	}
        },{
        	xtype: 'button',
        	id: 'diff',
        	text: '差异处理',
        	iconCls: 'table_row_delete',
        	disabled: true,
        	handler: function(){
        		toDiffStore();
        	}
        }]
    });
	
	var checkCellEditing = new Ext.grid.plugin.CellEditing({
        clicksToEdit: 1
    });
	
	var stockGrid = new Ext.grid.GridPanel({
		title: '库存盘点',
		columns : stockColumns,
		store : stockStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		frame : false,
		tbar : stockTbar, 
		stripeRows: true,
		forceFit: true,
		plugins: [checkCellEditing],
		tools:[{
            type:'help',
            tooltip: '操作指引',
            callback: function(panel, tool, event) {
            	parent.global.openSystemHelpTab();
            }
        }],
		features: [{
            ftype: 'summary',
            dock: 'bottom'
        }],
		viewConfig : {
			enableTextSelection:true	//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
//		},
//		listeners: {
//			render: function(){
//				Ext.each(stockStore.data.items, function(item){
//        			item.data.actual = item.data.number;
//        			item.commit();
//        		});
//	        }
		}
	});
	
	var diffGrid = new Ext.grid.GridPanel({
		columns : diffColumns,
		store : diffStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		frame : false,
		stripeRows: true,
		forceFit: true,
		viewConfig : {
			enableTextSelection:true	//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	var checkPanel = Ext.create('Ext.form.Panel', {
        autoHeight: true,
        bodyPadding: '0 5 0 5',
        defaults: {
            anchor: '100%'
        },
        items : [{
			xtype: 'fieldset',
			title: '全盘信息',
			collapsible: false,
			items:[{
                defaults:{
                	labelAlign: 'left',
                	padding: '20 10 10 10',
                	labelStyle : 'font-size: 20px;',
    				fieldStyle : 'font-size: 20px;',
                },
                items: [{
                		flex: 1, id:'birthday', xtype: 'displayfield', fieldLabel : '盘点日期',
                		format: 'Y/m/d',
    					renderer: Ext.util.Format.dateRenderer('Y/m/d'),
    					value: checkDate
                }]
			},{
				defaults:{
					labelAlign: 'left',
					padding: '10 10 10 10',
					labelStyle : 'font-size: 20px;',
					fieldStyle : 'font-size: 20px;',
				},
				items: [{
					flex: 1, id:'checkTotal', xtype: 'displayfield', fieldLabel : '盘点总数', value: checkTotal
				}]
			},{
				xtype: 'container',
                layout: 'hbox',
                defaults:{
					labelAlign: 'left',
					padding: '10 10 10 10',
					labelStyle : 'font-size: 20px;',
					fieldStyle : 'font-size: 20px;',
				},
                items: [{
                	flex: 1, id:'already', xtype: 'displayfield', fieldLabel : '已盘数量', value: already
                }, {
                	flex: 1, id:'wait', xtype: 'displayfield', fieldLabel : '未盘数量', value: wait
                }]
			},{
				defaults:{
					labelAlign: 'left',
					padding: '10 10 10 10',
					labelStyle : 'font-size: 20px;',
					fieldStyle : 'font-size: 20px;',
				},
				items: [{
					flex: 1, id:'difference', xtype: 'displayfield', fieldLabel : '盘点差异', value: difference
				}]
			}]
//        },{
//        	xtype: 'fieldset',
//			title: '分类信息',
//			collapsible: false,
//			items:[{
//				xtype: 'container',
//                layout: 'hbox',
//                defaults: {
//                	labelAlign: 'left',
//                	padding: '20 10 10 10',
//                	labelStyle : 'font-size: 20px;',
//    				fieldStyle : 'font-size: 20px;',
//                },
//                items: [{
//                	flex: 1, id:'as', xtype: 'displayfield', labelWidth:150, fieldLabel : '美容仓    已盘数', value: 100
//                }, {
//                	flex: 1, id:'sfff', xtype: 'displayfield', fieldLabel : '未盘数', value: 20
//                }]
//			},{
//				xtype: 'container',
//                layout: 'hbox',
//                defaults:{
//                	labelAlign: 'left',
//                	padding: '20 10 10 10',
//                	labelStyle : 'font-size: 20px;',
//    				fieldStyle : 'font-size: 20px;',
//                },
//                items: [{
//                	flex: 1, id:'rr', xtype: 'displayfield', labelWidth:150, fieldLabel : '装饰仓    已盘数', value: 100
//                }, {
//                	flex: 1, id:'tt', xtype: 'displayfield', fieldLabel : '未盘数', value: 20
//                }]
//			},{
//				xtype: 'container',
//				layout: 'hbox',
//				defaults:{
//					labelAlign: 'left',
//					padding: '20 10 10 10',
//					labelStyle : 'font-size: 20px;',
//					fieldStyle : 'font-size: 20px;',
//				},
//				items: [{
//					flex: 1, id:'qq', xtype: 'displayfield', labelWidth:150, fieldLabel : '改装仓    已盘数', value: 100
//				}, {
//					flex: 1, id:'aa', xtype: 'displayfield', fieldLabel : '未盘数', value: 20
//				}]
//			}]
        }]
	});
	
	var checkInfo = Ext.create('Ext.panel.Panel',{
		region: 'east',
        id: 'inventoryInfo',
        title: '盘点信息',
        iconCls:'application_view_columns',
        split: true,
        width: '34%',
        border: false,
        autoScroll: true,
        collapsible: true,
        animCollapse: true,
        items:[checkPanel]
	});
	
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [stockGrid, checkInfo]
	});
	
	var diffWindow = new Ext.Window({
		layout : 'fit',
		title : '差异处理',
		iconCls: 'table_row_delete',
		width : 800,
		height : 450,
		resizable : false,
		draggable : true,
		closeAction : 'hide',
		modal : false,
		collapsible : true,
		titleCollapse : true,
		maximizable : false,
		buttonAlign : 'center',
		border : false,
		animCollapse : true,
		constrain : true,
		items: [diffGrid],
		buttons : [{
			text : '完成盘点',
			id: 'finish',
			iconCls : 'accept',
			disabled: true,
			handler : function() {
				location.reload();
			}
		}]
	});
	
	function completeCheckInfo(){
		Ext.getCmp("checkTotal").setValue(checkTotal);
		Ext.getCmp("already").setValue(already);
		Ext.getCmp("wait").setValue(wait);
		Ext.getCmp("difference").setValue(difference);
	}
	
	function completeCheckRow(){
		already = 0, wait = 0, difference = 0;
		Ext.each(stockStore.data.items, function(item){
			if(!Ext.isEmpty(item.data.actual))
				already++;
			wait = checkTotal - already;
			if(!Ext.isEmpty(item.data.actual) && (Number(item.data.number) != Number(item.data.actual)))
				difference++;
		});
		completeCheckInfo();
	}
	
	function toDiffStore(){
		var i = 0;
		Ext.each(stockStore.data.items, function(item){
			if(item.data.number != item.data.actual){
				if(Ext.isEmpty(item.data.actual))
					item.data.actual = 0;
				diffStore.insert(i, item);
				i++;
			}
		});
		diffWindow.show();
	}
	
	
});