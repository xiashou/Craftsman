/**
 * 开单管理/未完成订单
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
		fields: ['orderId', 'orderType', 'itemNo', 'saleDate', 'carId', 'name', 'mobile', 'carNumber', 'goodsType', 'goodsName', 
		           'discount', 'price', 'status', 'remark'],
		proxy: {
		    type: 'ajax',
			url : '/order/queryOrderListByDept.atc',
			reader : {
			    root: 'orderList',
			    totalProperty: 'totalCount'
			}
		}
	});
	
	orderStore.getProxy().extraParams = {'orderItem.status': 2};
	
	var orderTypeCombo = new Ext.form.ComboBox({
		id: 'orderType',
		triggerAction : 'all',
		mode : 'local',
		store: parent.GOrderTypeStore,
		valueField : 'value',
		displayField : 'text',
		emptyText: '订单类型',
		editable : true,
		width : 140
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
        width: 190,
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
		if(itemType){
			record.data.goodsType = itemType.data.text;
			return itemType.data.text;
		} else
			return value;
    };
	
	var orderColumns= [orderRowNum,
  	            {header : '订单号',dataIndex: 'orderId', width: 80},
  	            {header : '订单类型',dataIndex: 'orderType', width: 60, renderer: orderTypeRenderer},
  	            {header : '销售日期',dataIndex: 'saleDate', width: 100},
  	            {header : '车主',dataIndex: 'name', width: 80},
  	            {header : '手机号码',dataIndex: 'mobile', width: 80},
  	            {header : '车牌号',dataIndex: 'carNumber', width: 70},
  	            {header : '类型',dataIndex: 'goodsType', width: 60, renderer: goodsTypeRenderer},
  	            {header : '项目',dataIndex: 'goodsName', width: 120},
  	            {header : '折扣',dataIndex: 'discount', width: 50,renderer: Ext.util.Format.numberRenderer("0.00")},
  	            {header : '价格（元）',dataIndex: 'price', width: 80, xtype: 'numbercolumn', format:'0.00'},
  	            {header : '状态',dataIndex: 'status', width: 60, renderer: statusRenderer},
  	            {header : '备注',dataIndex: 'remark', width: 80},
  	            {header : "通知取车", xtype: 'actioncolumn', menuDisabled: true, sortable: false, width: 60, align: 'center',
      				items: [{
      	          		iconCls: 'bell',
      	                tooltip: '通知取车',
      	                handler: function(grid, rowIndex, colIndex) {
      	                	var rec = grid.getStore().getAt(rowIndex);
      	                	if(!Ext.isEmpty(rec) && (rec.data.status == 2 || rec.data.status == '未完成')){
      	                		Ext.Ajax.request({
      	                			url : '/order/noticeTakeOrderCar.atc',
      	                			params : {
      	                				orderNo : rec.get("orderId").trim()
      	                			},
      	                			success : function(resp, opts) {
      	                				var result = Ext.decode(resp.responseText);
      	                				if(result.success){
      	                					Ext.example.msg('提示', result.msg);
      	                					orderStore.reload();
      	                				}
      	                			},
      	                			failure : function(resp, opts) {
      	                				Ext.Msg.alert('提示', "通知短信发送出错！");
      	                			}
      	                		});
      	                	}
      	                },
      	                getClass: function(metadata, r){
      	                	if(r.record.raw.status == 2)
      	                		return 'bell';
      	                	else
      	                		return 'bell2';
      	                }
      	          	}]
  	            },
  	            {header : "结算", xtype: 'actioncolumn', menuDisabled: true, sortable: false, width: 40, align: 'center',
      				items: [{
      	          		iconCls: 'money',
      	                tooltip: '结算订单',
      	                handler: function(grid, rowIndex, colIndex) {
      	                	var rec = grid.getStore().getAt(rowIndex);
      	                	parent.global.openSettlementTab(rec.get("orderId"), rec.get("carId"));
      	                }
      	          	}]
  	            },
  	            {text : '删除', xtype: 'actioncolumn', menuDisabled: true, sortable: false, width: 40, align: 'center',
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
        	width: 140,
        	format:'Y/m/d'
        },{
        	xtype: 'datefield',
        	id: 'endDate',
        	emptyText: '销售日期结束',
        	width: 140,
        	format:'Y/m/d'
        },orderTypeCombo,MemberCombo,{
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
		title: '未完成订单',
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
					Ext.Ajax.request({
						url : '/order/deleteOrderByNo.atc',
						async: false,
						params : {
							orderNo : orderId,
						},
						success : function(resp, opts) {
							var result = Ext.decode(resp.responseText);
							if(result.success){
								Ext.example.msg("提示", result.msg + "");
								orderStore.reload();
							}
						},
						failure : function(resp, opts) {
							var result = Ext.decode(resp.responseText);
			                Ext.Msg.alert('提示', "获取项目价格信息失败！");
						}
					});
				}
			}
		});
	}
	
	function getParams(){
		return {
			'orderItem.startDate': parent.dateFormat(Ext.getCmp("startDate").getValue()),
			'orderItem.endDate': parent.dateFormat(Ext.getCmp("endDate").getValue()),
			'orderItem.orderType': orderTypeCombo.getValue(),
			'orderItem.memId': MemberCombo.getValue(),
			'orderItem.goodsName': Ext.getCmp("goodsName").getValue()
		};
	}
	
});