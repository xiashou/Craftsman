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
		fields : [ 'orderId', 'orderType', 'appId', 'memId', 'openId', 'nickName', 'saleDate', 'oprice', 'aprice', 'point', 
		           'status', 'payType', 'poffset', 'consignee', 'contact', 'address', 'remark', 'expressNo' ],
		proxy : {
			type : 'ajax',
			url : '/mall/queryMallOrderPendingPage.atc',
			reader : {
				root : 'orderList',
				totalProperty: 'totalCount'
			}
		}
	});
	
	var orderTypeRenderer = function(value) {
  		var type = parent.MOrderTypeStore.findRecord("value", value);
		if(type) 
			return type.data.text;
		else
			return value;
  	}
	
	var payTypeRenderer = function(value) {
		if(!Ext.isEmpty(value)){
			var type = parent.MPayTypeStore.findRecord("value", value);
			if(type) 
				return type.data.text;
			else
				return value;
		}
  	}
	
	var orderStatusRenderer = function(value) {
		if(!Ext.isEmpty(value)){
			var css = "";
			if(value == 1)
				css = "<font color=orange>";
			else if(value == 2)
				css = "<font color=red>";
			else if(value == 3)
				css = "<font color=blue>";
			else if(value == 4)
				css = "<font color=green>";
			var status = parent.MOrderStatusStore.findRecord("value", value);
			if(status) 
				return css + status.data.text + "</font>";
			else
				return value;
		}
  	}
	
	var columns = [ rowNum, {
		header : '发货',xtype: 'actioncolumn',menuDisabled: true, sortable: false,width: 60, align: 'center',
    	items: [{
    		iconCls: 'lorry_go',
    		tooltip: '发货',
    		scope: this,
            handler: function(grid, rowIndex){
            	var record = grid.getStore().getAt(rowIndex);
            	if(record.data.status == 2){
            		initSend(record.data.orderId);
            	}
            },
            getClass: function(metadata, r){
            	if(r.record.data.status == 2)
            		return 'lorry_go';
            	else
            		return 'lorry_delete_black';
            }
    	}]
    }, {
		header : '订单号',
		dataIndex : 'orderId',
		width : 160
//	}, {
//		header : '订单类型',
//		dataIndex : 'orderType',
//		width : 80,
//		renderer: orderTypeRenderer
	}, {
		header : '会员昵称',
		dataIndex : 'nickName',
		width : 100
	}, {
		header : '销售时间',
		dataIndex : 'saleDate',
		width : 140
	}, {
		header : '应收',
		dataIndex : 'oprice',
		width : 100,
		renderer: Ext.util.Format.numberRenderer("0.00")
	}, {
		header : '实收',
		dataIndex : 'aprice',
		width : 100,
		renderer: Ext.util.Format.numberRenderer("0.00")
	}, {
		header : '所获积分',
		dataIndex : 'point',
		width : 100
	}, {
		header : '支付类型',
		dataIndex : 'payType',
		width : 100,
		renderer: payTypeRenderer
	}, {
		header : '状态',
		dataIndex : 'status',
		width : 80,
		renderer: orderStatusRenderer
	}, {
		header : '收货人姓名',
		dataIndex : 'consignee',
		width : 100
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
	}, {
		header : '快递单号',
		dataIndex : 'expressNo',
		width : 160
	} ];

	var tbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
		items : [ {
//			xtype : 'button',
//			text : '查 询',
//			iconCls : 'preview',
//			handler : function() {
//			}
//		}, {
//			xtype : 'button',
//			text : '修 改',
//			iconCls : 'pencil',
//			handler : function() {
//			}
//		}, {
			xtype : 'button',
			id : 'delete',
			text : '删 除',
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
		title:'待处理订单',
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
	
	function initSend(orderId) {
		
		Ext.MessageBox.prompt(orderId + ' 订单发货', '请输入快递单号:', function(btn, text){
			if (btn == 'ok') {
				Ext.Ajax.request({
					url : '/mall/sendMallOrderGoods.atc',
					params : {
						'order.orderId' : orderId,
						'order.expressNo' : text
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if (result.success) {
							Ext.example.msg('提示', result.msg);
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
		} else if(record.data.status != 1 && record.data.status != 0){
			Ext.MessageBox.show({
				title : '提示',
				msg : '只能删除未付款和已取消的订单！',
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.INFO
			});
			return;
		}
		Ext.Msg.confirm('请确认', '确定要删除这个订单吗?', function(btn, text) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url : '/mall/deleteMallOrder.atc',
					params : {
						'order.orderId' : record.data.orderId,
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if (result.success) {
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