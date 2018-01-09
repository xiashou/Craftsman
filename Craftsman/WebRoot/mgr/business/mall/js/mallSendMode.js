Ext.onReady(function() {

	Ext.QuickTips.init();

	var rowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable : true,
		width : 40
	});

	var sendModeForm = new Ext.form.FormPanel({
		id : 'sendModeForm',
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
				id : 'modeName',
				name : 'sendMode.modeName',
				allowBlank : false,
				fieldLabel : '名称',
				blankText : '品牌名称不能为空!'
			},{
				id : 'id',
				name : 'sendMode.id',
				xtype: 'hiddenfield'
			} ]
		} ],
		buttons : [ {
			text : '保 存',
			iconCls : 'accept',
			handler : function() {
				if (sendModeForm.form.isValid()) {
					sendModeForm.form.submit({
						waitTitle : '提示',
						method : 'POST',
						waitMsg : '正在处理数据,请稍候...',
						success : function(form, action) {
							sendModeWindow.hide();
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
			text : '关 闭 ',
			iconCls : 'stop',
			handler : function() {
				sendModeWindow.hide();
			}
		} ]
	});

	var sendModeWindow = new Ext.Window({
		title : '修改', // 窗口标题
		layout : 'fit',
		width : 300,
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
		items : [ sendModeForm ]
	});

	var store = Ext.create('Ext.data.Store', {
		pageSize : 20,
		fields : [ 'id', 'modeName'],
		proxy : {
			type : 'ajax',
			url : '/mall/queryMallSendModeByPage.atc',
			reader : {
				root : 'sendModeList',
				totalProperty : 'totalCount'
			}
		}
	});

	var columns = [ rowNum, {
		header : 'ID',
		dataIndex : 'id',
		hidden : true
	}, {
		header : '名称',
		dataIndex : 'modeName',
		width : 200
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
				sendModeWindow.setTitle('新建');
				sendModeForm.getForm().reset();
				sendModeForm.getForm().url = '/mall/insertMallSendMode.atc';
				sendModeWindow.show();
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
		sendModeWindow.setTitle('修改');
		sendModeForm.loadRecord(record);
		sendModeForm.getForm().url = '/mall/updateMallSendMode.atc';
		sendModeWindow.show();
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
		Ext.Msg.confirm('请确认', '确定要删除这个配送方式吗?', function(btn, text) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url : '/mall/deleteMallSendMode.atc',
					params : {
						'sendMode.id' : record.data.id
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if (result.success) {
							sendModeWindow.hide();
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