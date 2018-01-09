/**
 * 订单删除记录
 */
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	var rowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var store = Ext.create('Ext.data.Store', {
		pageSize : 20,
		fields: ['orderId', 'carId', 'memId', 'name', 'mobile', 'carNumber', 'orderType', 'aprice', 'deleteUser', 'deleteTime'],
		proxy: {
		    type: 'ajax',
			url : '/order/queryDeleteHeadList.atc',
			reader : {
			    root: 'hList',
			    totalProperty: 'totalCount'
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
	
	var columns= [rowNum,
	        {header : '订单号',dataIndex: 'orderId', width: 120},
	  	    {header : '会员ID',dataIndex: 'memId', width: 100, hidden: true},
	  	    {header : '车辆ID',dataIndex: 'carId', width: 100, hidden: true},
	  	    {header : '订单类型',dataIndex: 'orderType', width: 80, renderer: orderTypeRenderer},
	  	    {header : '会员',dataIndex: 'name', width: 180},
	  	    {header : '车牌',dataIndex: 'carNumber', width: 100},
	  	    {header : '手机号码',dataIndex: 'mobile', width: 180},
	  	    {header : '实收金额',dataIndex: 'aprice', width: 110, renderer: Ext.util.Format.numberRenderer("0.00")},
            {header : '删除人',dataIndex: 'deleteUser', width: 100},
            {header : '删除时间',dataIndex: 'deleteTime', width: 180},
            {header : '详情',xtype: 'actioncolumn',menuDisabled: true, sortable: false,width: 80, align: 'center',
            	items: [{
            		iconCls: 'application_view_list',
            		tooltip: '查看详情',
            		scope: this,
                    handler: function(grid, rowIndex){
                    	var rec = grid.getStore().getAt(rowIndex);
                    	parent.global.openOrderViewTab(rec.get("orderId"), rec.get("carId"));
                    }
            	}]
            }
  	];
	
	var tbar = Ext.create('Ext.toolbar.Toolbar', {
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
        },{
        	xtype: 'button',
        	id: 'delete',
        	text: ' 查 询 ',
        	iconCls: 'preview', 
        	handler: function(){
        		store.load({
        			params : getParams()
        		});
        	}
        },'->',{
        	xtype: 'button',
        	text:'导出Excel',
        	iconCls : 'page_white_excel',
        	handler: function() {
        		grid.downloadExcelXml();
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
	
	var grid = new Ext.grid.GridPanel({
		title: '删除记录',
		columns : columns,
		store : store, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		tbar : tbar, 
		bbar : bbar,
		stripeRows:true,
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
		items : [grid]
	});
	
	store.on('beforeload', function() {
		Ext.apply(store.proxy.extraParams, getParams());
	});
	
	store.load();
	
	function getParams(){
		return {
			'orderHead.startDate': parent.dateFormat(Ext.getCmp("startDate").getValue()),
			'orderHead.endDate': parent.dateFormat(Ext.getCmp("endDate").getValue())
		};
	}
	
});