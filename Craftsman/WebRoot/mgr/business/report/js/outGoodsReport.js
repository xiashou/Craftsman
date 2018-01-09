/**
 * 出库记录
 */
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	var goodsId;
	
	var orderRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 30
	});
	
	var orderStore = Ext.create('Ext.data.Store', {
		pageSize : 20,
		fields: ['typeId', 'goodsId', 'goodsName', 'spec', 'color', 'name', 'mobile', 'number', 'price', 'aprice'],
		proxy: {
		    type: 'ajax',
			url : '/order/queryOutGoodsOrderList.atc',
			reader : {
			    root: 'orderList',
			    totalProperty: 'totalCount'
			}
		}
	});
	
	var goodsTypeStore = Ext.create('Ext.data.Store', {
		autoLoad: true,
		fields: ['id', 'typeName'],
		proxy: {
		    type: 'ajax',
		    url : '/goods/queryMaterialTypeByDeptCode.atc',
			reader : {
			    root: 'materialTypeList'
			}
		}
	});
	
	var goodsTypeCombo = new Ext.form.ComboBox({
		id: 'goodsType',
		triggerAction : 'all',
		mode : 'local',
		store: goodsTypeStore,
		emptyText: '商品类型',
		valueField : 'id',
		displayField : 'typeName',
		editable : true,
		width: 140
	});
	
	var goodsTypeRenderer = function(value, cellmeta, record) {
        var index = goodsTypeStore.find("id", value);
        var rec = goodsTypeStore.getAt(index);
        var displayText = "";
        if (rec == null)
        	displayText = value;
        else {
        	displayText = rec.data.typeName;
        	//这里直接把typeId设成名称  以便导出时显示名称而不是ID
        	record.data.typeId = rec.data.typeName;
        	//record.commit();
        }
        return displayText;
    };
	
	var orderColumns= [orderRowNum,
  	            {header : '类型',dataIndex: 'typeId', width: 120, renderer: goodsTypeRenderer},
  	            {header : '商品',dataIndex: 'goodsId', width: 100, hidden: true},
  	            {header : '商品',dataIndex: 'goodsName', width: 240},
  	            {header : '规格',dataIndex: 'spec', width: 100},
  	            {header : '颜色',dataIndex: 'color', width: 100},
  	            {header : '尺码',dataIndex: 'name', width: 100},
  	            {header : '单位',dataIndex: 'mobile', width: 100},
  	            {header : '成本',dataIndex: 'aprice', width: 120, summaryType: 'sum', renderer: Ext.util.Format.numberRenderer("0.00")},
  	            {header : '销售量',dataIndex: 'number', width: 120, summaryType: 'sum', renderer: Ext.util.Format.numberRenderer("0.00")},
  	            {header : '销售总金额',dataIndex: 'price', width: 120, summaryType: 'sum', renderer: Ext.util.Format.numberRenderer("0.00")},
  	            {xtype: 'actioncolumn',header : '详情',width: 80, align:'center',sortable: false,menuDisabled: true,
  	                items: [{
  	                    iconCls: 'application_view_list',
  	                    tooltip: '查看详情',
  	                    scope: this,
  	                    handler: function(grid, rowIndex){
  	                    	var record = orderStore.getAt(rowIndex);
  	                    	goodsId = record.data.goodsId;
  	                    	detailWindow.hide();
  	                    	detailWindow.show();
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
        },goodsTypeCombo,{
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
		title: '商品出库',
		iconCls: 'money',
		columns : orderColumns,
		store : orderStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		tbar : orderTbar, 
		bbar : bbar,
		stripeRows:true,
		features: [{
            ftype: 'summary',
            dock: 'bottom'
        }],
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
	
	var detailStore = Ext.create('Ext.data.Store', {
		fields: ['orderId', 'itemNo', 'saleDate', 'name', 'carId', 'carNumber', 'goodsId', 'goodsName', 'number', 'price'],
		proxy:{
            type: 'ajax',
            url : '/order/queryOrderByGoodsIdPage.atc',
            reader : {
                root: 'orderList',
                totalProperty: 'totalCount'
            }
        }
    });
	var detailRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var detailColumns= [detailRowNum,
	            {header : '订单号',dataIndex: 'orderId',menuDisabled: true},
	 	        {header : '行项目',dataIndex: 'itemNo',menuDisabled: true, hidden:true},
	 	        {header : '销售日期',dataIndex: 'saleDate',menuDisabled: true, width:'16%'},
	 	        {header : '姓名',dataIndex: 'name',menuDisabled: true},
	 	        {header : '车辆ID',dataIndex: 'carId',menuDisabled: true, hidden:true},
	 	        {header : '车牌号',dataIndex: 'carNumber',menuDisabled: true},
	 	        {header : '商品ID',dataIndex: 'goodsId',menuDisabled: true, hidden:true},
	 	        {header : '项目/商品',dataIndex: 'goodsName',menuDisabled: true},
	 	        {header : '次数/数量',dataIndex: 'number',menuDisabled: true},
	 	        {header : '价格',dataIndex: 'price',menuDisabled: true,renderer: Ext.util.Format.numberRenderer("0.00")}
	 	];
	
	var detailPagesizeCombo = new Ext.form.ComboBox({
		name : 'detailPagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
					[ 100, '100条/页' ], [ 200, '200条/页' ]]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '10',
		editable : false,
		width : 85
	});

	var detailNumber = parseInt(detailPagesizeCombo.getValue());
	
	// 改变每页显示条数reload数据
	detailPagesizeCombo.on("select", function(comboBox) {
		detailBbar.pageSize = parseInt(comboBox.getValue());
		number = parseInt(comboBox.getValue());
		detailStore.pageSize = parseInt(comboBox.getValue());
		var params = getDetailParams();
		params.start = 0;
		params.limit = detailBbar.pageSize;
		detailStore.reload({
			params : params
		});
	});
	
	var detailBbar = new Ext.PagingToolbar({
		pageSize : detailNumber,
		store : detailStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', detailPagesizeCombo ]
	});
		
	var detailGrid = Ext.create('Ext.grid.GridPanel', {
		minHeight: 220,
		columns : detailColumns,
		store : detailStore, 
		bbar : detailBbar,
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
	
	var detailWindow = Ext.create('Ext.Window',{
		layout : 'fit',
		title: '详细信息',
		iconCls: 'award_star_bronze_3',
		width : 730,
		height : 440,
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
		items: [detailGrid]
	});
	
	detailWindow.on('show', function(){
		detailStore.load();
	});
	
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [orderGrid]
	});
	
	orderStore.on('beforeload', function() {
		Ext.apply(orderStore.proxy.extraParams, getParams());
	});
	
	detailStore.on('beforeload', function(){
		Ext.apply(detailStore.proxy.extraParams, getDetailParams());
	});
	
	orderStore.load();
	
	detailStore.on('load', function(){
		if(!Ext.isEmpty(detailStore)){
			var obj = {}, count = 0;
			Ext.each(detailStore.data.items, function(detail){
				if(!obj[detail.data.carId]){
					obj[detail.data.carId] = detail;
					count++;
				}
			});
			detailWindow.setTitle("详细信息（总车辆数：" + count + " 台）");
		}
	});
	
	function getParams(){
		return {
			'orderItem.startDate': parent.dateFormat(Ext.getCmp("startDate").getValue()),
			'orderItem.endDate': parent.dateFormat(Ext.getCmp("endDate").getValue()),
			'orderItem.goodsType': goodsTypeCombo.getValue(),
			'orderItem.goodsName': Ext.getCmp("goodsName").getValue()
		};
	}
	
	function getDetailParams(){
		return {
			'orderItem.startDate': parent.dateFormat(Ext.getCmp("startDate").getValue()),
			'orderItem.endDate': parent.dateFormat(Ext.getCmp("endDate").getValue()),
			'orderItem.goodsId': goodsId
		};
	}
	
});