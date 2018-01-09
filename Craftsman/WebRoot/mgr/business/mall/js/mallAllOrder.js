Ext.onReady(function() {

	Ext.QuickTips.init();

	// 定义自动当前页行号
	var rowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable : true,
		width : 40
	});
	
	var store = Ext.create('Ext.data.Store', {
		pageSize : 20,
		fields : [ 'orderId', 'orderType', 'appId', 'memId', 'saleDate', 'oprice', 'aprice', 'point', 'status', 'payType', 'poffset', 'consignee', 'contact', 'address', 'remark' ],
		proxy : {
			type : 'ajax',
			url : '/mall/queryMallOrderPage.atc',
			reader : {
				root : 'orderList',
				totalProperty: 'totalCount'
			}
		}
	});
	
	var columns = [ rowNum, {
		header : '订单号',
		dataIndex : 'orderId',
		width : 180
	}, {
		header : '订单类型',
		dataIndex : 'orderType',
		width : 80
	}, {
		header : '会员号',
		dataIndex : 'memId',
		width : 100
	}, {
		header : '销售时间',
		dataIndex : 'saleDate',
		width : 140
	}, {
		header : '应收',
		dataIndex : 'oprice',
		width : 100
	}, {
		header : '实收',
		dataIndex : 'aprice',
		width : 100
	}, {
		header : '所获积分',
		dataIndex : 'point',
		width : 100
	}, {
		header : '支付类型',
		dataIndex : 'payType',
		width : 100
	}, {
		header : '状态',
		dataIndex : 'status',
		width : 100
	}, {
		header : '收货人姓名',
		dataIndex : 'consignee',
		width : 120
	}, {
		header : '收货人电话',
		dataIndex : 'contact',
		width : 120
	}, {
		header : '收货地址',
		dataIndex : 'address',
		width : 200
	}, {
		header : '客户留言',
		dataIndex : 'remark',
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
			handler : function() {
				mallBannerWindow.setTitle('新建');
				mallBannerForm.getForm().reset();
				mallBannerForm.getForm().url = '/mall/insertMallBanner.atc';
				mallBannerWindow.show();
			}
		}, {
			xtype : 'button',
			id : 'update',
			text : '修改',
			iconCls : 'pencil',
			handler : function() {
				initEdit();
			}
		}, {
			xtype : 'button',
			id : 'delete',
			text : '删除',
			iconCls : 'delete',
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
	
	pagesizeCombo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(comboBox.getValue());
		number = parseInt(comboBox.getValue());
		store.pageSize = parseInt(comboBox.getValue());
		var params = getParams();
		params.start = 0;
		params.limit = bbar.pageSize;
		store.reload({
			params : params
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
		title:'订单管理',
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
	
	store.on('beforeload', function() {
		Ext.apply(store.proxy.extraParams, getParams());
	});

	store.load();
	
	function getParams(){
		return {
//			'order.typeId' : typeId
		};
	}

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
		mallBannerWindow.setTitle('修改');
		mallBannerForm.loadRecord(record);
		mallBannerForm.getForm().url = '/mall/updateMallBanner.atc';
		mallBannerWindow.show();
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
					url : '/mall/deleteMallBanner.atc',
					params : {
						'banner.id' : record.data.id
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if (result.success) {
							mallBannerWindow.hide();
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