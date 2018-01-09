/**
 * 消费记录
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	
	var orderRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 30
	});
	
	var orderStore = Ext.create('Ext.data.Store', {
		pageSize : 20,
		fields: ['orderId', 'orderType', 'itemNo', 'saleDate', 'mobile', 'carNumber', 'goodsType', 'goodsName', 'number',
		           'performerName', 'discount', 'price', 'aprice', 'mainten', 'status', 'remark'],
		proxy: {
		    type: 'ajax',
		    url : '/order/queryOrderListByDept.atc',
			reader : {
			    root: 'orderList',
			    totalProperty: 'totalCount'
			}
		}
	});
	
//	var saleDateRenderer = function(value, cellmeta, record) {
//		return Ext.isEmpty(value) ? "" : value.substring(0, 10);
//	}
	
	var orderTypeCombo = new Ext.form.ComboBox({
		id: 'orderType',
		triggerAction : 'all',
		mode : 'local',
		store: parent.GOrderTypeStore,
		valueField : 'value',
		displayField : 'text',
		emptyText: '订单类型',
		editable : true,
		width : 100
	});
	
	var MemberStore = Ext.create('Ext.data.Store', {
		fields: ['memId', 'vipNo', 'name', 'mobile', 'carShort', 'carCode', 'carNumber', 'fullCarNumber'],
		proxy:{
            type: 'ajax',
            url : '/member/queryMemberByKeyword.atc',
            reader : {
                root: 'memberList',
                totalProperty: 'totalCount'
            }
        }
    });
	
	var MemberCombo = Ext.create('Ext.form.ComboBox', {
        store: MemberStore,
        name: 'orderItem.memId',
        allowBlank: false,
        valueField: 'memId',
        displayField: 'vipNo',
        typeAhead: false,
        hideTrigger:true,
        width: 220,
        queryParam: 'keyword',	//搜索参数字段
        minChars: 2,
        emptyText: '会员',
        pageSize: 10,
        listConfig: {
            loadingText: '查找中...',
            emptyText: '<div style="padding: 5px;">没有找到相关车主！</div>',
            getInnerTpl: function() {
                return '<div>{fullCarNumber}/{name}/{mobile}</div>';
            }
		}
	});
	
	var orderTypeRenderer = function(value, cellmeta, record) {
		var type = parent.GOrderTypeStore.findRecord("value", value);
		if(type) {
			record.data.orderType = type.data.text;
			return type.data.text;
		} else
			return value;
	}
	
	var statusRenderer = function(value, cellmeta, record) {
		var status = parent.GOrderStatusStore.findRecord("value", value);
		if(status) {
			record.data.status = status.data.text;
			return status.data.text;
		} else
			return status;
	}
	
	var goodsTypeRenderer = function(value, cellmeta, record) {
		var itemType = parent.GOrderItemTypeStore.findRecord("value", value);
		if(itemType) {
			record.data.goodsType = itemType.data.text;
			return itemType.data.text;
		} else
			return value;
    };
    
    var priceRenderer = function(value, cellmeta, record) {
		if(value == 0 && record.data.orderType == '会员充值') {
			return record.data.aprice;
		} else
			return value;
    };
	
	var orderColumns= [orderRowNum,
      	            {header : '订单号',dataIndex: 'orderId', width: 80,summaryType: function(){return '合计'}},
      	            {header : '订单类型',dataIndex: 'orderType', width: 60, renderer: orderTypeRenderer},
      	            {header : '销售日期',dataIndex: 'saleDate', width: 100},
      	            {header : '手机号码',dataIndex: 'mobile', width: 80},
      	            {header : '车牌号',dataIndex: 'carNumber', width: 70,
      	            	editor: {xtype: 'textfield'},
      	            	items: {
      	            		xtype: 'textfield',
      	            		flex : 1,
      	            		margin: 2,
      	            		enableKeyEvents: true,
      	            		listeners: {
      	            			keyup: function() {
      	            				orderStore.clearFilter();
      	            				if (this.value) {
      	            					orderStore.filter({
      	            						property     : 'carNumber',
      	            						value         : this.value,
      	            						anyMatch      : true,
      	            						caseSensitive : false
      	            					});
      	            				}
      	            			},
      	            			buffer: 500
      	            		}
      	            }},
      	            {header : '类型',dataIndex: 'goodsType', width: 60, renderer: goodsTypeRenderer},
      	            {header : '项目/商品',dataIndex: 'goodsName', width: 120},
      	            {header : '数量',dataIndex: 'number', width: 60,summaryType: 'sum'},
      	            {header : '施工方/销售方',dataIndex: 'performerName', width: 100},
      	            {header : '折扣',dataIndex: 'discount', width: 50,renderer: Ext.util.Format.numberRenderer("0.00")},
      	            {header : '价格（元）',dataIndex: 'price', width: 80, xtype: 'numbercolumn', format:'0.00',summaryType: 'sum', renderer: priceRenderer},
      	            {header : '进厂公里数',dataIndex: 'mainten', width: 60},
      	            //{header : '价格（元）',dataIndex: 'aprice', width: 80, xtype: 'numbercolumn', format:'0.00',summaryType: 'sum'},
      	            {header : '状态',dataIndex: 'status', width: 60, renderer: statusRenderer},
      	            {header : '备注',dataIndex: 'remark', width: 80},
      	            {header : '打印',xtype: 'actioncolumn',menuDisabled: true, sortable: false,width: 30, align: 'center',
	                    items: [{
	                        getClass: function(v, meta, rec) {
	                            if (rec.get('status') < 1) {
	                                return 'printer';
	                            } else {
	                                return 'printer';
	                            }
	                        },
	                        getTip: function(v, meta, rec) {
	                            if (rec.get('change') < 0) {
	                                return '打印订单';
	                            } else {
	                                return '打印订单';
	                            }
	                        },
	                        handler: function(grid, rowIndex, colIndex) {
	                            var rec = grid.getStore().getAt(rowIndex),
	                                action = (rec.get('change') < 0 ? 'Hold' : 'Buy');
	                            printOrderByNo(rec.get("orderId"));
	                        }
	                    }]
      	            },
      	            {header : '删除',xtype: 'actioncolumn',menuDisabled: true, sortable: false,width: 30, align: 'center',
      	            	items: [{
      	            		iconCls: 'cross',
	                        tooltip: '删除订单',
	                        handler: function(grid, rowIndex, colIndex) {
	                            var rec = grid.getStore().getAt(rowIndex);
	                            deleteOrderByNo(rec.get("orderId"));
	                        }
      	            	}]
      	            }
      	  	];
	
	var orderTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'datefield',
        	id: 'startDate',
        	emptyText: '销售日期开始',
        	width: 120,
        	format:'Y/m/d'
        },{
        	xtype: 'datefield',
        	id: 'endDate',
        	emptyText: '销售日期结束',
        	width: 120,
        	format:'Y/m/d'
        },orderTypeCombo,{
        	xtype: 'textfield',
        	id: 'orderId',
        	emptyText: '订单号',
        	width: 120
        },MemberCombo,{
        	xtype: 'textfield',
        	id: 'goodsName',
        	emptyText: '商品名称',
        	width: 140
        },{
        	xtype: 'button',
        	id: 'delete',
        	text: ' 查 询 ',
        	iconCls: 'preview', 
        	handler: function(){
        		orderStore.load({
        			params : getParams()
        		});
            }
        },'->',{
        	xtype: 'button',
        	text:'导出Excel',
        	iconCls : 'page_white_excel',
        	handler: function() {
        		orderGrid.downloadExcelXml();
        	}
        }]
    });
	
	var pagesizeCombo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
					[ 100, '100条/页' ], [ 200, '200条/页' ], [ 500, '500条/页' ], [ 1000000, '更多条' ] ]
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
		orderStore.pageSize = parseInt(comboBox.getValue());
		var params = getParams();
		params.start = 0;
		params.limit = bbar.pageSize;
		orderStore.reload({
			params : params
		});
	});
	
	var bbar = new Ext.PagingToolbar({
		pageSize : number,
		store : orderStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesizeCombo ]
	});
	
	var orderGrid = new Ext.grid.GridPanel({
		title: '消费记录',
		iconCls: 'money',
		columns : orderColumns,
		store : orderStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		tbar : orderTbar, 
		bbar : bbar,
		stripeRows:true,
		forceFit: true,
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
		}
	});
	
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [orderGrid]
	});
	
	orderStore.on('beforeload', function() {
		Ext.apply(orderStore.proxy.extraParams, getParams());
	});
	
	orderStore.load({
		params:{
			start : 0,
			limit : bbar.pageSize
		}
	});
	
	//删除订单
	function deleteOrderByNo(orderId){
		Ext.Msg.confirm('请确认', '确定要删除整张订单吗?', function(btn, text) {
			if(btn == 'yes'){
				if(!Ext.isEmpty(orderId)){
					Ext.MessageBox.show({
						title: '请稍等',
						msg: '正在删除订单 ...',
						width: 200,
						closable: false
			        });
					Ext.Ajax.request({
						url : '/order/deleteOrderByNo.atc',
						async: false,
						params : {
							orderNo : orderId,
						},
						success : function(resp, opts) {
							Ext.MessageBox.hide();
							var result = Ext.decode(resp.responseText);
							if(result.success){
								Ext.example.msg("提示", result.msg + "");
								orderStore.reload();
							}
						},
						failure : function(resp, opts) {
							Ext.MessageBox.hide();
							var result = Ext.decode(resp.responseText);
			                Ext.Msg.alert('提示', "获取项目价格信息失败！");
						}
					});
				}
			}
		});
	}
	
	function printOrderByNo(orderId){
		//如果存在之前打印的选项卡则先关闭
		if(parent.mainTab.getComponent('1000000'))
			parent.global.closeTab(parent.mainTab.getComponent('1000000'));
		
		//打开打印选项卡然后关闭录单选项卡
		parent.global.openTab("1000000", "订单打印", "/order/initOrderPrint.atc?orderNo=" + orderId.trim(), null);
	}
	
	function getParams(){
		return {
			'orderItem.startDate': parent.dateFormat(Ext.getCmp("startDate").getValue()),
			'orderItem.endDate': parent.dateFormat(Ext.getCmp("endDate").getValue()),
			'orderItem.orderType': orderTypeCombo.getValue(),
			'orderItem.orderId': Ext.getCmp("orderId").getValue(),
			'orderItem.memId': MemberCombo.getValue(),
			'orderItem.goodsName': Ext.getCmp("goodsName").getValue()
		};
	}
	
	
});