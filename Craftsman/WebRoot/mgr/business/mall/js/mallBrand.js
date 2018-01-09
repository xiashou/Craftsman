Ext.onReady(function() {

	Ext.QuickTips.init();

	// 定义自动当前页行号
	var rowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable : true,
		width : 40
	});

	var mallBrandForm = new Ext.form.FormPanel({
		id : 'mallBrandForm',
		layout : 'anchor',
		defaults : {
			anchor : '100%',
			layout : 'hbox',
			xtype : 'fieldcontainer'
		},
		bodyPadding : '5 10 0 0',
		border : false,
		items : [ {
			defaults : {
				flex : 1,
				xtype : 'textfield',
				labelWidth : 70,
				labelAlign : 'right'
			},
			items : [ {
				id : 'brandName',
				name : 'mallBrand.brandName',
				allowBlank : false,
				fieldLabel : '名称',
				blankText : '品牌名称不能为空!'
			},{
				id : 'id',
				name : 'mallBrand.id',
				xtype: 'hiddenfield'
			} ]
		},{
			defaults : {
				flex : 1,
				xtype : 'filefield',
				labelWidth : 70,
				labelAlign : 'right'
			},
			items : [ {
				fieldLabel : 'Logo',
				id : 'upload',
				name: 'upload',
				buttonText: '浏览...',
				anchor : '99%'
			} ]
		} ],
		buttons : [ {
			text : '保 存', // 按钮文本
			iconCls : 'accept', // 按钮图标
			handler : function() { // 按钮响应函数
				if (mallBrandForm.form.isValid()) {
					mallBrandForm.form.submit({
						waitTitle : '提示',
						method : 'POST',
						waitMsg : '正在处理数据,请稍候...',
						success : function(form, action) {
							mallBrandWindow.hide();
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
		}, {
			text : '关 闭 ', // 按钮文本
			iconCls : 'stop', // 按钮图标
			handler : function() { // 按钮响应函数
				mallBrandWindow.hide();
			}
		} ]
	});

	var mallBrandWindow = new Ext.Window({
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
		items : [ mallBrandForm ]
	});

	var store = Ext.create('Ext.data.Store', {
		pageSize : 20,
		fields : [ 'id', 'brandName', 'shortCode', 'logo' ],
		proxy : {
			type : 'ajax',
			url : '/mall/queryMallBrandByPage.atc',
			reader : {
				root : 'brandList',
				totalProperty : 'totalCount'
			}
		}
	});
	
	var logoRenderer = function(value, metaData, record) {
		if(value){
  			metaData.tdAttr = "data-qtip=\"<img src='/upload/mall/brand/" + value + "' style='width:350px'/>\""; 
  			return '<img src="/upload/mall/brand/' + value + '" style="height:30px" onerror="this.src=\'/resources/images/noImage.jpg\'" />';
  		} else
  			return '<img src="/resources/images/noImage.jpg" style="height:30px" />';
    };

	var columns = [ rowNum, {
		header : 'ID',
		dataIndex : 'id',
		hidden : true
	}, {
		header : '名称',
		dataIndex : 'brandName',
		width : 200
	}, {
		header : 'Logo',
		dataIndex : 'logo',
		width : 80,
		renderer: logoRenderer
	} ];

	var tbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
		items : [ {
			xtype : 'button',
			id : 'insert',
			text : '新建',
			iconCls : 'add',
//			hidden : !parent.haveActionMenu('Add'),
			handler : function() {
				mallBrandWindow.setTitle('新建');
				mallBrandForm.getForm().reset();
				mallBrandForm.getForm().url = '/mall/insertMallBrand.atc';
				mallBrandWindow.show();
			}
		}, {
			xtype : 'button',
			id : 'update',
			text : '修改',
			iconCls : 'pencil',
//			hidden : !parent.haveActionMenu('Edit'),
			handler : function() {
				initEdit();
			}
		}, {
			xtype : 'button',
			id : 'delete',
			text : '删除',
			iconCls : 'delete',
//			hidden : !parent.haveActionMenu('Del'),
			handler : function() {
				initDelete();
			}
		} ]
	});

	var pagesizeCombo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
					[ 100, '100条/页' ], [ 200, '200条/页' ], [ 500, '500条/页' ],
					[ 1000000, '更多条' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		editable : false,
		width : 85
	});

	var number = parseInt(pagesizeCombo.getValue());

	// 改变每页显示条数reload数据
	pagesizeCombo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(comboBox.getValue());
		number = parseInt(comboBox.getValue());
		store.pageSize = parseInt(comboBox.getValue());
		store.reload({
			params : {
				start : 0,
				limit : bbar.pageSize
			}
		});
	});

	var bbar = new Ext.PagingToolbar({
		pageSize : number,
		store : store,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesizeCombo ]
	});

	var selModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox : 1,
		width: 40, 
		mode : 'SINGLE'
	});

	var grid = new Ext.grid.GridPanel({
		columns : columns,
		store : store,
		region : 'center',
		autoScroll : true,
		columnLines : true,
		selModel : selModel,
		frame : false,
		tbar : tbar,
		bbar : bbar,
		stripeRows : true,
		viewConfig : {
			enableTextSelection : true
		//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
		listeners : {
			itemdblclick : function(grid, record, item, index, e) {
				if (parent.haveActionMenu('Edit'))
					initEdit();
			}
		}
	});

	// 布局模型
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [ {
			region : 'center',
			layout : 'fit',
			border : false,
			items : [ grid ]
		} ]
	});

	store.load({
		params : {
			start : 0,
			limit : bbar.pageSize
		}
	});

	function initEdit() {
		var record = selModel.getSelection()[0];
		if (Ext.isEmpty(record)) {
			Ext.MessageBox.show({
				title : '提示',
				msg : '你没有选中任何项目！',
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.INFO
			});
			return;
		}
		selModel.deselectAll();
		mallBrandWindow.setTitle('修改');
		mallBrandForm.loadRecord(record);
		mallBrandForm.getForm().url = '/mall/updateMallBrand.atc';
		mallBrandWindow.show();
	}

	function initDelete() {
		var record = selModel.getSelection()[0];
		if (Ext.isEmpty(record)) {
			Ext.MessageBox.show({
				title : '提示',
				msg : '你没有选中任何项目！',
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.INFO
			});
			return;
		}
		Ext.Msg.confirm('请确认', '确定要删除这个品牌吗?', function(btn, text) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url : '/mall/deleteMallBrand.atc',
					params : {
						'mallBrand.id' : record.data.id
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if (result.success) {
							mallBrandWindow.hide();
							Ext.example.msg('提示', result.msg);
							selModel.deselectAll();
							store.reload();
						} else
							Ext.MessageBox.show({
								title : '提示',
								msg : result.msg,
								buttons : Ext.MessageBox.OK,
								icon : Ext.MessageBox.ERROR
							});
					},
					failure : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						Ext.MessageBox.show({
							title : '提示',
							msg : result.msg,
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.ERROR
						});
					}
				});
			}
		});
	}

});