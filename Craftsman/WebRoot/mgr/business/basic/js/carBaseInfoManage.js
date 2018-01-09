Ext.require(['Ext.ux.TreePicker']);
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	var brandId, seriesId;
	
	// 定义自动当前页行号
	var brandRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var seriesRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var modelRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var brandForm = new Ext.form.FormPanel({
				id : 'brandForm',
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
					           {id:'id', name:'carBrand.id', xtype:'hiddenfield'},
					           {id:'shortCode', name: 'carBrand.shortCode',allowBlank: false, fieldLabel: '首字母', blankText:'首字母不能为空!' }
					    ]
					},{
					    defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'right'},
					    items: [
					           {id:'brandName', name: 'carBrand.brandName', allowBlank: false, fieldLabel: '品牌名称', blankText:'品牌名称不能为空!' }
					    ]
					},{
						defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'right'},
						items: [
						        {fieldLabel : '上传照片',id : 'upload',name : 'upload',xtype:'filefield',buttonText:'浏览...',anchor : '99%'},
						        {xtype: 'hiddenfield',id: 'picture',name: 'carBrand.logo'}
						]
					}
				],
				buttons:[{
					text : '保 存', // 按钮文本
					iconCls : 'accept', // 按钮图标
					handler : function() { // 按钮响应函数
						if (brandForm.form.isValid()) {
							brandForm.form.submit({
								waitTitle : '提示',
								method : 'POST',
								waitMsg : '正在处理数据,请稍候...',
								success : function(form, action) {
									brandWindow.hide();
									Ext.example.msg('提示', action.result.msg);
									brandStore.reload();
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
						brandWindow.hide();
					}
				}]
			});
	
	var seriesForm = new Ext.form.FormPanel({
		id : 'seriesForm',
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
			           {id:'carSeriesId', name:'carSeries.id', xtype:'hiddenfield'},
			           {id:'brandId', name:'carSeries.brandId', xtype:'hiddenfield'},
			           {id:'seriesName', name: 'carSeries.seriesName',allowBlank: false, fieldLabel: '名称', blankText:'名称不能为空!' }
			    ]
			}
		],
		buttons:[{
			text : '保 存', // 按钮文本
			iconCls : 'accept', // 按钮图标
			handler : function() { // 按钮响应函数
				if (seriesForm.form.isValid()) {
					Ext.getCmp("brandId").setValue(brandId);
					seriesForm.form.submit({
						waitTitle : '提示',
						method : 'POST',
						waitMsg : '正在处理数据,请稍候...',
						success : function(form, action) {
							seriesWindow.hide();
							Ext.example.msg('提示', action.result.msg);
							seriesStore.reload();
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
				seriesWindow.hide();
			}
		}]
	});
	
	var modelForm = new Ext.form.FormPanel({
		id : 'modelForm',
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
			           {id:'carModelId', name:'carModel.id', xtype:'hiddenfield'},
			           {id:'seriesId', name:'carModel.seriesId', xtype:'hiddenfield'},
			           {id:'modelName', name: 'carModel.modelName',allowBlank: false, fieldLabel: '名称', blankText:'名称不能为空!' }
			    ]
			}
		],
		buttons:[{
			text : '保 存', // 按钮文本
			iconCls : 'accept', // 按钮图标
			handler : function() { // 按钮响应函数
				if (modelForm.form.isValid()) {
					Ext.getCmp("seriesId").setValue(seriesId);
					modelForm.form.submit({
						waitTitle : '提示',
						method : 'POST',
						waitMsg : '正在处理数据,请稍候...',
						success : function(form, action) {
							modelWindow.hide();
							Ext.example.msg('提示', action.result.msg);
							modelStore.reload();
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
				modelWindow.hide();
			}
		}]
	});
	
	var brandWindow = new Ext.Window({
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
		items: [brandForm]
	});
	
	var seriesWindow = new Ext.Window({
		title : '修改', // 窗口标题
		layout : 'fit',
		width : 400,
		height : 120,
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
		items: [seriesForm]
	});
	
	var modelWindow = new Ext.Window({
		title : '修改', // 窗口标题
		layout : 'fit',
		width : 400,
		height : 120,
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
		items: [modelForm]
	});
	
	var brandStore = Ext.create('Ext.data.Store', {
		  fields: ['id', 'shortCode', 'brandName', 'logo'],
		  proxy: {
		      type: 'ajax',
		  url : '/base/queryAllCarBrand.atc',
		  reader : {
		      root: 'carBrandList',
		      totalProperty: 'totalCount'
		  }
    	}
    });
	
	var seriesStore = Ext.create('Ext.data.Store', {
        fields: ['id', 'brandId', 'seriesName'],
        proxy: {
            type: 'ajax',
            url : '/base/queryCarSeriesByBrandId.atc',
            reader : {
                root: 'carSeriesList',
                totalProperty: 'totalCount'
            }
        }
	});
	
	var modelStore = Ext.create('Ext.data.Store', {
        fields: ['id', 'seriesId', 'modelName'],
        proxy: {
            type: 'ajax',
            url : '/base/queryCarModelBySeriesId.atc',
            reader : {
                root: 'carModelList',
                totalProperty: 'totalCount'
            }
        }
	});
	
	var logoRender = function(value, metaData, record) {
		if(value){
  			metaData.tdAttr = "data-qtip=\"<img src='/upload/brand/" + value + "' style='width:100px'/>\""; 
  			return '<img src="/upload/brand/' + value + '" style="height:30px" onerror="this.src=\'/resources/images/noImage.jpg\'" />';
  		} else
  			return '<img src="/resources/images/noImage.jpg" style="height:30px" />';
    };
  	
  	var brandColumns= [brandRowNum,
	            {header : 'ID',dataIndex: 'id', width: 60, hidden: true},
	            {header : 'Logo',dataIndex: 'logo', width: 40, renderer: logoRender},
	            {header : '首字母',dataIndex: 'shortCode', width: 60,editor: {xtype: 'textfield'},
	    			items : {
	    	            xtype: 'textfield', flex : 1, margin: 2, enableKeyEvents: true,
	    	            listeners: {
	    	                keyup: function() {
	    	                	carBrandStore.clearFilter();
	    	                    if (this.value) {
	    	                    	carBrandStore.filter({
	    	                            property      : 'shortCode',
	    	                            value         : this.value,
	    	                            anyMatch      : true,
	    	                            caseSensitive : false
	    	                        });
	    	                    }
	    	                },
	    	                buffer: 500
	    	            }
	    	        }},
	            {header : '品牌名称',dataIndex: 'brandName', width: 150,editor: {xtype: 'textfield'},
		    			items : {
		    	            xtype: 'textfield', flex : 1, margin: 2, enableKeyEvents: true,
		    	            listeners: {
		    	                keyup: function() {
		    	                	carBrandStore.clearFilter();
		    	                    if (this.value) {
		    	                    	carBrandStore.filter({
		    	                            property      : 'brandName',
		    	                            value         : this.value,
		    	                            anyMatch      : true,
		    	                            caseSensitive : false
		    	                        });
		    	                    }
		    	                },
		    	                buffer: 500
		    	            }
		    	        }}
	  	];
  	
  	var seriesColumns= [seriesRowNum,
  	 	            {header : 'ID',dataIndex: 'id', width: 60, hidden: true},
  	 	            {header : '品牌',dataIndex: 'brandId', width: 60, renderer: function(v){return brandStore.findRecord("id", v).data.brandName;}},
  	 	            {header : '系列名称',dataIndex: 'seriesName', width: 150,editor: {xtype: 'textfield'},
  	 		    			items : {
  	 		    	            xtype: 'textfield', flex : 1, margin: 2, enableKeyEvents: true,
  	 		    	            listeners: {
  	 		    	                keyup: function() {
  	 		    	                	seriesStore.clearFilter();
  	 		    	                    if (this.value) {
  	 		    	                    	seriesStore.filter({
  	 		    	                            property      : 'seriesName',
  	 		    	                            value         : this.value,
  	 		    	                            anyMatch      : true,
  	 		    	                            caseSensitive : false
  	 		    	                        });
  	 		    	                    }
  	 		    	                },
  	 		    	                buffer: 500
  	 		    	            }
  	 		    	        }}
  	 	  	];
  	
  	var modelColumns= [modelRowNum,
  	  	 	            {header : 'ID',dataIndex: 'id', width: 60, hidden: true},
  	  	 	            {header : '系列',dataIndex: 'seriesId', width: 60, renderer: function(v){return seriesStore.findRecord("id", v).data.seriesName;}},
  	  	 	            {header : '型号名称',dataIndex: 'modelName', width: 150,editor: {xtype: 'textfield'},
  	  	 		    			items : {
  	  	 		    	            xtype: 'textfield', flex : 1, margin: 2, enableKeyEvents: true,
  	  	 		    	            listeners: {
  	  	 		    	                keyup: function() {
  	  	 		    	                	modelStore.clearFilter();
  	  	 		    	                    if (this.value) {
  	  	 		    	                    	modelStore.filter({
  	  	 		    	                            property      : 'modelName',
  	  	 		    	                            value         : this.value,
  	  	 		    	                            anyMatch      : true,
  	  	 		    	                            caseSensitive : false
  	  	 		    	                        });
  	  	 		    	                    }
  	  	 		    	                },
  	  	 		    	                buffer: 500
  	  	 		    	            }
  	  	 		    	        }}
  	  	 	  	];
	
	var brandTbar = Ext.create('Ext.toolbar.Toolbar', {
			region : 'north',
			border : false,
	        items: [{
	        	xtype: 'button',
	        	id: 'insert',
	        	text: '新建',
	        	iconCls: 'add',
	        	hidden : !parent.haveActionMenu('AddBrand'),
	        	handler: function() {
	        		seriesWindow.hide();
	        		modelWindow.hide();
	        		brandWindow.setTitle('新建');
	        		brandForm.getForm().reset();
	        		brandForm.getForm().url = '/base/insertCarBrand.atc';
	        		brandWindow.show();
	            }
	        },{
	        	xtype: 'button',
	        	id: 'update',
	        	text: '修改',
	        	iconCls: 'pencil',
	        	hidden : !parent.haveActionMenu('EditBrand'),
	        	handler: function() {
	        		initBrandEdit();
	            }
	        },{
	        	xtype: 'button',
	        	id: 'delete',
	        	text: '删除',
	        	iconCls: 'delete', 
	        	hidden : !parent.haveActionMenu('DelBrand'),
	        	handler: function(){
	        		initBrandDelete();
	            }
	        }]
	    });
	
	var seriesTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	text: '新建',
        	iconCls: 'add',
        	hidden : !parent.haveActionMenu('AddSeries'),
        	handler: function() {
        		brandWindow.hide();
        		modelWindow.hide();
        		seriesWindow.setTitle('新建');
        		seriesForm.getForm().reset();
        		seriesForm.getForm().url = '/base/insertCarSeries.atc';
        		seriesWindow.show();
            }
        },{
        	xtype: 'button',
        	text: '修改',
        	iconCls: 'pencil',
        	hidden : !parent.haveActionMenu('EditSeries'),
        	handler: function() {
        		initSeriesEdit();
            }
        },{
        	xtype: 'button',
        	text: '删除',
        	iconCls: 'delete', 
        	hidden : !parent.haveActionMenu('DelSeries'),
        	handler: function(){
        		initSeriesDelete();
            }
        }]
    });
	
	var modelTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	text: '新建',
        	iconCls: 'add',
        	hidden : !parent.haveActionMenu('AddModel'),
        	handler: function() {
        		seriesWindow.hide();
        		brandWindow.hide();
        		modelWindow.setTitle('新建');
        		modelForm.getForm().reset();
        		modelForm.getForm().url = '/base/insertCarModel.atc';
        		modelWindow.show();
            }
        },{
        	xtype: 'button',
        	text: '修改',
        	iconCls: 'pencil',
        	hidden : !parent.haveActionMenu('EditModel'),
        	handler: function() {
        		initModelEdit();
            }
        },{
        	xtype: 'button',
        	text: '删除',
        	iconCls: 'delete', 
        	hidden : !parent.haveActionMenu('DelModel'),
        	handler: function(){
        		initModelDelete();
            }
        }]
    });
	
	var brandSelModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1,
		mode: 'SINGLE'
	});
	
	var seriesSelModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1,
		mode: 'SINGLE'
	});
	
	var modelSelModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1,
		mode: 'SINGLE'
	});
	
	var brandGrid = new Ext.grid.GridPanel({
			columns : brandColumns,
			store : brandStore, 
			region : 'center',
			autoScroll : true,
			columnLines : true,
			selModel : brandSelModel,
			tbar : brandTbar, 
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
					seriesStore.load({
						params: {
							brandId : record.raw.id
						}
					});
					brandId = record.raw.id;
		        }
		    }
		});
	
	var seriesGrid = new Ext.grid.GridPanel({
		columns : seriesColumns,
		store : seriesStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		selModel : seriesSelModel,
		tbar : seriesTbar, 
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
				modelStore.load({
					params: {
						'carModel.seriesId' : record.raw.id
					}
				});
				seriesId = record.raw.id;
	        }
	    }
	});
	
	var modelGrid = new Ext.grid.GridPanel({
		columns : modelColumns,
		store : modelStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		selModel : modelSelModel,
		tbar : modelTbar, 
		stripeRows:true,
		forceFit: true,
		viewConfig : {
			enableTextSelection:true	//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
//		},
//		listeners:{
//			itemdblclick: function(grid, record, item, index, e) {
//				if(parent.haveActionMenu('Edit'))
//					initEdit();
//	        }
	    }
	});
	
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [{
			title : '品牌',
			iconCls : 'car',
			tools : [{
				type:'refresh',
				handler : function() {
					brandStore.reload();
				}
			}],
//			collapsible : true,
			width : '30%',
			split : true,
			region : 'west',
			layout : 'fit',
			border : false,
			items : [ brandGrid ]
		}, {
			title : '系列',
			iconCls : 'car',
			tools : [{
				type:'refresh',
				handler : function() {
					seriesStore.reload();
				}
			}],
			region : 'center',
			width : '34%',
			split : true,
			layout : 'fit',
			border : false,
			items : [ seriesGrid ]
		}, {
			title : '型号',
			iconCls : 'car',
			tools : [{
				type:'refresh',
				handler : function() {
					modelStore.reload();
				}
			}],
			region : 'east',
			width : '36%',
			split : true,
			layout : 'fit',
			border : false,
			items : [ modelGrid ]
		}]
	});
	
	brandStore.load();
	
	function initBrandEdit() {
		var record = brandSelModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '你没有选中任何项目！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
		    });
			return;
		}
		seriesWindow.hide();
		modelWindow.hide();
		brandSelModel.deselectAll();
		brandWindow.setTitle('修改');
		brandForm.getForm().reset();
		brandForm.loadRecord(record);
		brandForm.getForm().url = '/base/updateCarBrand.atc';
		brandWindow.show();
	}
	
	function initBrandDelete() {
		var record = brandSelModel.getSelection()[0];
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
					url : '/base/deleteCarBrand.atc',
					params : {
						'carBrand.id' : record.raw.id
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							brandWindow.hide();
							Ext.example.msg('提示', result.msg);
							brandSelModel.deselectAll();
							brandStore.reload();
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
	
	function initSeriesEdit() {
		var record = seriesSelModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '你没有选中任何项目！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
		    });
			return;
		}
		brandWindow.hide();
		modelWindow.hide();
		seriesSelModel.deselectAll();
		seriesWindow.setTitle('修改');
		seriesForm.getForm().reset();
		seriesForm.loadRecord(record);
		Ext.getCmp("carSeriesId").setValue(record.raw.id);
		seriesForm.getForm().url = '/base/updateCarSeries.atc';
		seriesWindow.show();
	}
	
	function initSeriesDelete() {
		var record = seriesSelModel.getSelection()[0];
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
					url : '/base/deleteCarSeries.atc',
					params : {
						'carSeries.id' : record.raw.id
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							seriesWindow.hide();
							Ext.example.msg('提示', result.msg);
							seriesSelModel.deselectAll();
							seriesStore.reload();
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
	
	function initModelEdit() {
		var record = modelSelModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '你没有选中任何项目！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
		    });
			return;
		}
		seriesWindow.hide();
		brandWindow.hide();
		modelSelModel.deselectAll();
		modelWindow.setTitle('修改');
		modelForm.getForm().reset();
		modelForm.loadRecord(record);
		Ext.getCmp("carModelId").setValue(record.raw.id);
		modelForm.getForm().url = '/base/updateCarModel.atc';
		modelWindow.show();
	}
	
	function initModelDelete() {
		var record = modelSelModel.getSelection()[0];
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
					url : '/base/deleteCarModel.atc',
					params : {
						'carModel.id' : record.raw.id
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							modelWindow.hide();
							Ext.example.msg('提示', result.msg);
							modelSelModel.deselectAll();
							modelStore.reload();
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