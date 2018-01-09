Ext.onReady(function() {
	
	Ext.QuickTips.init();
	var typeId;
	
	// 定义自动当前页行号
	var materialTypeRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 30
	});

	// 定义自动当前页行号
	var materialRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 30
	});
	
	var materialTypeStore = Ext.create('Ext.data.Store', {
		  fields: ['id', 'deptCode', 'typeName', 'sortNo'],
		  proxy: {
		      type: 'ajax',
		      url : '/goods/queryAllMaterialType.atc',
		      reader : {
		    	  root: 'materialTypeList',
		    	  totalProperty: 'totalCount'
		      }
		  }
	});

	var materialStore = Ext.create('Ext.data.Store', {
        fields: ['id', 'code', 'name', 'typeId', 'price', 'inPrice', 'spec', 'color', 'size', 'unit', 'suitModel', 'remark'],
        proxy: {
            type: 'ajax',
            url : '/goods/queryGoodsMaterialByType.atc',
            reader : {
                root: 'materialList',
                totalProperty: 'totalCount'
            }
        }
	});
	
	var materialTypeForm = new Ext.form.FormPanel({
				id : 'materialTypeForm',
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
					           {id:'id', name:'materialType.id', xtype:'hiddenfield'},
					           {id:'typeName', name: 'materialType.typeName',allowBlank: false, fieldLabel: '类型名称', blankText:'类型名称不能为空!' }
					    ]
					},{
					    defaults: {flex: 1, xtype:'numberfield', labelWidth:70, labelAlign:'right'},
					    items: [
					           {id:'sortNo', name: 'materialType.sortNo', allowBlank: true, fieldLabel: '排序' }
					    ]
					}
				],
				buttons:[{
					text : '保 存',
					iconCls : 'accept',
					handler : function() {
						if (materialTypeForm.form.isValid()) {
							materialTypeForm.form.submit({
								waitTitle : '提示',
								method : 'POST',
								waitMsg : '正在处理数据,请稍候...',
								success : function(form, action) {
									materialTypeWindow.hide();
									Ext.example.msg('提示', action.result.msg);
									materialTypeStore.reload();
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
						materialTypeWindow.hide();
					}
				}]
			});
	
	var materialForm = new Ext.form.FormPanel({
		id : 'materialForm',
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
			           {id:'materialId', name:'material.id', xtype:'hiddenfield'},
			           {id:'typeId', name:'material.typeId', xtype:'hiddenfield'},
			           {id:'inPrice', name:'material.inPrice', xtype:'hiddenfield'},
			           {id:'materialDeptCode', name:'material.deptCode',xtype:'hiddenfield', value: parent.globalDeptCode},
			           {id:'name', name: 'material.name',allowBlank: false, fieldLabel: '商品名称', blankText:'商品名称不能为空!'  }
			    ]
			},{
			    defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'right'},
			    items: [
			           {id:'code', name: 'material.code', allowBlank: true, fieldLabel: '编码' }
			    ]
			},{
			    defaults: {flex: 1, xtype:'numberfield', labelWidth:70, labelAlign:'right'},
			    items: [
			           {id:'price', name: 'material.price', allowBlank: true, fieldLabel: '零售价' }
			    ]
			},{
			    defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'right'},
			    items: [
			           {id:'spec', name: 'material.spec', allowBlank: true, fieldLabel: '规格' }
			    ]
			},{
			    defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'right'},
			    items: [
			           {id:'color', name: 'material.color', allowBlank: true, fieldLabel: '颜色' }
			    ]
			},{
			    defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'right'},
			    items: [
			           {id:'size', name: 'material.size', allowBlank: true, fieldLabel: '尺码' }
			    ]
			},{
			    defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'right'},
			    items: [
			           {id:'unit', name: 'material.unit', allowBlank: true, fieldLabel: '单位' }
			    ]
			},{
			    defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'right'},
			    items: [
			           {id:'suitModel', name: 'material.suitModel', allowBlank: true, fieldLabel: '适用车型' }
			    ]
			},{
			    defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'right'},
			    items: [
			           {id:'remark', name: 'material.remark', allowBlank: true, fieldLabel: '备注' }
			    ]
			}
		],
		buttons:[{
			text : '保 存',
			iconCls : 'accept',
			handler : function() {
				if (materialForm.form.isValid()) {
					Ext.getCmp("typeId").setValue(typeId);
					materialForm.form.submit({
						waitTitle : '提示',
						method : 'POST',
						waitMsg : '正在处理数据,请稍候...',
						success : function(form, action) {
							materialWindow.hide();
							Ext.example.msg('提示', action.result.msg);
							materialStore.reload();
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
				materialWindow.hide();
			}
		}]
	});
	
	var materialTypeWindow = new Ext.Window({
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
		items: [materialTypeForm]
	});
	
	var materialWindow = new Ext.Window({
		title : '修改', // 窗口标题
		layout : 'fit',
		width : 400,
		height : 400,
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
		items: [materialForm]
	});
	
  	var materialTypeColumns= [materialTypeRowNum,
	            {header : 'ID',dataIndex: 'id', width: 60, hidden: true},
	            {header : '类型名称',dataIndex: 'typeName', width: 150},
	            {header : '排序',dataIndex: 'sortNo', width: 60}
	  	];
  	
  	var materialColumns= [materialRowNum,
            {header : 'ID',dataIndex: 'id', hidden: true},
            {header : '门店代码',dataIndex: 'deptCode', hidden: true},
            {header : '类型',dataIndex: 'typeId', width: 60, renderer: function(v){return materialTypeStore.findRecord("id", v).data.typeName;}},
            {header : '商品名称',dataIndex: 'name', width: 100},
            {header : '编码',dataIndex: 'code', width: 100},
            {header : '零售价',dataIndex: 'price', width: 60, renderer: Ext.util.Format.numberRenderer("0.00")},
            {header : '规格',dataIndex: 'spec', width: 60},
            {header : '颜色',dataIndex: 'color', width: 60},
            {header : '尺码',dataIndex: 'size', width: 60},
            {header : '单位',dataIndex: 'unit', width: 60},
            {header : '适用车型',dataIndex: 'suitModel', width: 60},
            {header : '备注',dataIndex: 'remark', width: 60}
  	];
  	
	var materialTypeTbar = Ext.create('Ext.toolbar.Toolbar', {
			region : 'north',
			border : false,
	        items: [{
	        	xtype: 'button',
	        	id: 'insert',
	        	text: '新建',
	        	iconCls: 'add',
	        	hidden : !parent.haveActionMenu('Add'),
	        	handler: function() {
	        		materialWindow.hide();
	        		materialTypeWindow.setTitle('新建');
	        		materialTypeForm.getForm().reset();
	        		materialTypeForm.getForm().url = '/goods/insertMaterialType.atc';
	        		materialTypeWindow.show();
	            }
	        },{
	        	xtype: 'button',
	        	id: 'update',
	        	text: '修改',
	        	iconCls: 'pencil',
	        	hidden : !parent.haveActionMenu('Edit'),
	        	handler: function() {
	        		initMaterialTypeEdit();
	            }
	        },{
	        	xtype: 'button',
	        	id: 'delete',
	        	text: '删除',
	        	iconCls: 'delete', 
	        	disabled: true,
//	        	hidden : !parent.haveActionMenu('Del'),
	        	hidden : true,
	        	handler: function(){
	        		initMaterialTypeDelete();
	            }
	        }]
	    });
	
	var materialTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	id: 'materialInsert',
        	text: '新建',
        	iconCls: 'add',
        	hidden : !parent.haveActionMenu('AddMaterial'),
        	handler: function() {
        		initMaterialInsert();
            }
        },{
        	xtype: 'button',
        	id: 'materialUpdate',
        	text: '修改',
        	iconCls: 'pencil',
        	hidden : !parent.haveActionMenu('EditMaterial'),
        	handler: function() {
        		initMaterialEdit();
            }
        },{
        	xtype: 'button',
        	id: 'materialDelete',
        	text: '删除',
        	iconCls: 'delete', 
        	disabled: true,
//        	hidden : !parent.haveActionMenu('DelMaterial'),
        	hidden : true,
        	handler: function(){
        		initMaterialDelete();
            }
        },{
        	xtype: 'button',
        	id: 'setPrice',
        	text: '调整零售价',
        	iconCls: 'tag_blue_edit', 
        	hidden : !parent.haveActionMenu('SetPrice'),
        	handler: function(){
        		priceWindow.show();
        	}
        },{
        	xtype: 'textfield',
        	id: 'keyword',
        	emptyText : '输入编码或者名称',
        },{
        	xtype: 'button',
        	id: 'search',
        	text: ' 查 询 ',
        	iconCls: 'preview', 
        	handler: function(){
        		if(!Ext.isEmpty(typeId)){
        			materialStore.load({
						params: {
							'typeId' : typeId,
							'keyword' : Ext.getCmp("keyword").getValue()
						}
					});
        		} else {
        			Ext.MessageBox.show({
        	        	title: '提示',
        	        	msg: "请先选择商品类型！",
        	        	buttons: Ext.MessageBox.OK,
        	        	icon: Ext.MessageBox.INFO
        			});
        		}
            }
        },'->',{
        	xtype: 'button',
        	id: 'import',
        	text: '批量导入',
        	iconCls: 'page_excel',
        	hidden : !parent.haveActionMenu('Import'),
        	handler: function(){
        		if(Ext.isEmpty(typeId)){
        			Ext.MessageBox.show({
				           title: '提示',
				           msg: '请先选择类型！',
				           buttons: Ext.MessageBox.OK,
				           icon: Ext.MessageBox.INFO
                    });
					return;
				}
        		Ext.getCmp("importTypeId").setValue(typeId);
        		importWindow.show();
        	}
        }]
    });
	
	var materialTypeSelModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1,
		mode: 'SINGLE'
	});
	
	var materialSelModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1,
		mode: 'SINGLE'
	});
	
	var materialTypeGrid = new Ext.grid.GridPanel({
			columns : materialTypeColumns,
			store : materialTypeStore, 
			region : 'center',
			autoScroll : true,
			columnLines : true,
			selModel : materialTypeSelModel,
			tbar : materialTypeTbar, 
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
					materialStore.load({
						params: {
							'typeId' : record.raw.id
						}
					});
					typeId = record.raw.id;
		        }
		    }
		});
	
	var materialGrid = new Ext.grid.GridPanel({
		columns : materialColumns,
		store : materialStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		selModel : materialSelModel,
		tbar : materialTbar, 
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
			title : '类型',
			iconCls : 'tag_blue',
			tools : [{
				type:'refresh',
				handler : function() {
					materialTypeStore.reload();
				}
			}],
			collapsible : true,
			width : '20%',
			split : true,
			region : 'west',
			layout : 'fit',
			border : false,
			items : [ materialTypeGrid ]
		}, {
			title : '商品',
			iconCls : 'bricks',
			tools : [{
				type:'refresh',
				handler : function() {
					materialStore.reload();
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
			items : [ materialGrid ]
		}]
	});
	
	
	
	var materialPriceRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 30
	});
	
	var materialPriceColumns= [materialPriceRowNum,
          {header : 'ID',dataIndex: 'id', hidden: true},
          {header : '商品名称',dataIndex: 'name', width: 100},
          {header : '编码',dataIndex: 'code', width: 100},
          {header : '最后一次进货价',dataIndex: 'inPrice', width: 60, renderer: Ext.util.Format.numberRenderer("0.00")},
          {header : '当前零售价',dataIndex: 'price', width: 60, renderer: Ext.util.Format.numberRenderer("0.00"),editor: {xtype: 'numberfield', allowBlank: false, minValue: 0}}
	];
	
	var materialPriceCellEditing = new Ext.grid.plugin.CellEditing({
        clicksToEdit: 1
    });
	
	var materialPriceTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'textfield',
        	id: 'param',
        	fieldLabel : '调整基数',
        	labelWidth: 70,
        	width: 140,
        	value: '130'
        },{
        	xtype: 'displayfield',
        	value: '%'
        },{
        	xtype: 'button',
        	text: ' 设 置 ',
        	iconCls: 'tag_blue_edit', 
//        	hidden : !parent.haveActionMenu('setPrice'),
        	handler: function(){
        		var i = 0;
        		materialStore.each(function(rec){
	        		var param = Ext.getCmp('param').getValue();
	        		if(!Ext.isEmpty(rec.get('inPrice')))
	        			materialStore.getAt(i).set('price', Number(Number(param) * Number(rec.get('inPrice')) / 100).toFixed(2)); 
	        		i++;
	        	});
        	}
        },'->',{
        	xtype: 'button',
        	text: '全部保存',
        	iconCls: 'accept', 
//        	hidden : !parent.haveActionMenu('setPrice'),
        	handler: function(){
        		var jsonArray = [];  
                Ext.each(materialStore.data.items, function(item){  
                    jsonArray.push(item.data);  
                });  
                Ext.Ajax.request({  
                    method:'POST',
                    url: '/goods/saveGoodsMaterialPrice.atc',
                    params : {
						jsonStr : Ext.encode(jsonArray)
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							priceWindow.hide();
							Ext.example.msg('提示', result.msg);
							materialStore.loadPage(materialStore.currentPage);
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
    });
	
	var materialPriceSelModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1,
		mode: 'SINGLE'
	});
	
	var materialPriceGrid = new Ext.grid.GridPanel({
		columns : materialPriceColumns,
		store : materialStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		selModel : materialPriceSelModel,
		tbar : materialPriceTbar, 
		stripeRows:true,
		forceFit: true,
		plugins: [materialPriceCellEditing],
		viewConfig : {
			enableTextSelection:true	//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
	});
	
	var priceWindow = new Ext.Window({
		layout : 'fit',
		width : 680,
		height : 480,
		resizable : false,
		draggable : true,
		closeAction : 'hide',
		title : '调整零售价',
		iconCls: 'tag_blue_edit', 
		modal : false,
		collapsible : true,
		titleCollapse : true,
		maximizable : false,
		buttonAlign : 'right',
		border : false,
		animCollapse : true,
		constrain : true,
		items : [materialPriceGrid],
	});
	
	
	var importForm = new Ext.form.FormPanel({
		id : 'importForm',
		name : 'importForm',
		defaultType : 'textfield',
		labelAlign : 'right',
		labelWidth : 99,
		padding: '5 5 5 5',
		frame : false,
		items : [{
				fieldLabel : '请选择导入文件',
				id : 'upload',
				name : 'upload',
				xtype:'filefield',
				buttonText:'浏览...',
				allowBlank : true,
				anchor : '99%'
			},{
				xtype: 'hiddenfield',
				id: 'importTypeId',
				name: 'typeId',
				allowBlank : true
			},{
				xtype: 'displayfield',
				fieldLabel : '下载导入模板',
				allowBlank : true,
				anchor : '99%',
				value:'<a href="/mgr/common/template/商品资料模板.xls">商品资料模板.xls</a>'
			}]
	});
	
	var importWindow = new Ext.Window({
		layout : 'fit',
		width : 380,
		height : 140,
		resizable : false,
		draggable : true,
		closeAction : 'hide',
		title : '导入Excel',
		modal : false,
		collapsible : true,
		titleCollapse : true,
		maximizable : false,
		buttonAlign : 'right',
		border : false,
		animCollapse : true,
		constrain : true,
		items : [importForm],
		buttons : [{
			text : '导入',
			iconCls : 'page_white_excel',
			handler : function() {
				var upload = Ext.getCmp('upload').getValue();
				if (Ext.isEmpty(upload)) {
					Ext.MessageBox.show({
				           title: '提示',
				           msg: '请先选择您要导入的Excel文件...',
				           buttons: Ext.MessageBox.OK,
				           icon: Ext.MessageBox.INFO
					});
					return;
				}
				Ext.MessageBox.show({
					title: '请稍等',
					msg: '商品信息导入中 ...',
					width: 200,
					progress: true,
					closable: false
		        });
				importForm.form.submit({
					url : '/goods/importGoodsMaterial.atc',
					waitTitle : '提示',
					method : 'POST',
					waitMsg : '正在处理数据,请稍候...',
					success : function(form, action) {
						Ext.MessageBox.hide();
						Ext.example.msg('提示', action.result.msg);
						importWindow.hide();
						materialStore.load({
							params: {
								'typeId' : typeId
							}
						});
					},
					failure : function(form, action) {
						Ext.MessageBox.hide();
						var msg = action.result.msg;
                        Ext.MessageBox.show({
 				           title: '提示',
 				           msg: msg,
 				           buttons: Ext.MessageBox.OK,
 				           icon: Ext.MessageBox.ERROR
                        });
					}
				});
			}
		}, {
			text : '关闭',
			id : 'btnReset',
			iconCls : 'stop',
			handler : function() {
				importWindow.hide();
			}
		}]
	});
	
	materialTypeStore.load();
	
	function initMaterialTypeEdit() {
		var record = materialTypeSelModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '你没有选中任何项目！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
		    });
			return;
		}
		materialWindow.hide();
		materialTypeSelModel.deselectAll();
		materialTypeWindow.setTitle('修改');
		materialTypeForm.getForm().reset();
		materialTypeForm.loadRecord(record);
		materialTypeForm.getForm().url = '/goods/updateMaterialType.atc';
		materialTypeWindow.show();
	}
	
	function initMaterialTypeDelete() {
		var record = materialTypeSelModel.getSelection()[0];
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
					url : '/goods/deleteMaterialType.atc',
					params : {
						'materialType.id' : record.raw.id
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							materialTypeWindow.hide();
							Ext.example.msg('提示', result.msg);
							materialTypeStore.reload();
							materialStore.reload();
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
	
	function initMaterialInsert() {
		var record = materialTypeSelModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '你没有选中任何项目！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
		    });
			return;
		}
		materialTypeWindow.hide();
		materialWindow.setTitle('新建');
		materialForm.getForm().reset();
		materialForm.getForm().url = '/goods/insertGoodsMaterial.atc';
		materialWindow.show();
	}
	
	function initMaterialEdit() {
		var record = materialSelModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '你没有选中任何项目！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
		    });
			return;
		}
		materialTypeWindow.hide();
		materialSelModel.deselectAll();
		materialWindow.setTitle('修改');
		materialForm.getForm().reset();
		materialForm.loadRecord(record);
		Ext.getCmp("materialId").setValue(record.raw.id);
		materialForm.getForm().url = '/goods/updateGoodsMaterial.atc';
		materialWindow.show();
	}
	
	function initMaterialDelete() {
		var record = materialSelModel.getSelection()[0];
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
					url : '/goods/deleteGoodsMaterial.atc',
					params : {
						'material.id' : record.raw.id
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							materialTypeWindow.hide();
							Ext.example.msg('提示', result.msg);
							materialSelModel.deselectAll();
							materialStore.reload();
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