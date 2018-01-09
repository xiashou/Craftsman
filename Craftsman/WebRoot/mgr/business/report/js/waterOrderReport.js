/**
 * 营业流水报表
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
		fields: ['orderId', 'saleDate', 'memId', 'name', 'mobile', 'carNumber', 'orderType', 'payType', 'aprice', 'creator', 'disprice', 'cost', 'profit'],
		proxy: {
		    type: 'ajax',
			url : '/order/queryWaterHeadList.atc',
			reader : {
			    root: 'hList',
			    totalProperty: 'totalCount'
			}
		}
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
	
	var payStore = Ext.create('Ext.data.ArrayStore', {
		fields : [ 'value', 'text' ],
		data : [ [ 'pcash', '现金' ], [ 'pbalance', '余额' ], [ 'pcard', '刷卡' ], [ 'ptransfer', '转账' ], [ 'pbill', '挂账' ], [ 'pwechat', '微信' ], [ 'palipay', '支付宝' ]]
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
	
	var payCombo = Ext.create('Ext.form.ComboBox', {
		store: payStore,
        enforceMaxLength: true,
        width: 160,
        listConfig: {
            minWidth: null
        },
        valueField: 'value',
        displayField: 'text',
        queryMode: 'local',
        emptyText: '支付方式',
        editable : false,
        multiSelect: true,
        forceSelection: true
	});
	
	var EmployeeStore = Ext.create('Ext.data.Store', {
        fields: ['id', 'name'],
        proxy: {
            type: 'ajax',
            url : '/shop/queryEmployeeByDept.atc',
            reader : {
                root: 'employeeList'
            }
        }
	});
	EmployeeStore.load();
	
	var orderTypeCombo = new Ext.form.ComboBox({
		id: 'orderType',
		triggerAction : 'all',
		mode : 'local',
		queryMode: 'local',
		store: parent.GOrderTypeStore,
		emptyText: '订单类型',
		valueField : 'value',
		displayField : 'text',
		editable : true,
		width: 140
	});
	
	var orderTypeRenderer = function(value, cellmeta, record) {
		var type = parent.GOrderTypeStore.findRecord("value", value);
		if(type) {
			record.data.orderType = type.data.text;
			return type.data.text;
		} else
			return value;
	}
	
	var employeeRenderer = function(value, cellmeta, record) {
		var index = EmployeeStore.find("id", value);
        var record = EmployeeStore.getAt(index);
        var displayText = "";
        if (record == null)
        	displayText = value;
        else
        	displayText = record.data.name;
        return displayText;
	}
	
	var orderColumns= [orderRowNum,
  	            {header : '订单号',dataIndex: 'orderId', width: 100},
  	            {header : '会员ID',dataIndex: 'memId', width: 100, hidden: true},
  	            {header : '订单类型',dataIndex: 'orderType', width: 100, renderer: orderTypeRenderer},
  	            {header : '销售时间',dataIndex: 'saleDate', width: 140},
  	            {header : '会员',dataIndex: 'name', width: 100},
  	            {header : '车牌',dataIndex: 'carNumber', width: 100},
  	            {header : '手机号码',dataIndex: 'mobile', width: 140},
  	            {header : '付款方式',dataIndex: 'payType', width: 140},
  	            {header : '录单人',dataIndex: 'creator', width: 80, renderer: employeeRenderer},
  	            {header : '实收价格',dataIndex: 'aprice', width: 100, renderer: Ext.util.Format.numberRenderer("0.00"),summaryType: 'sum'},
  	            {header : '优惠价格',dataIndex: 'disprice', width: 100, renderer: Ext.util.Format.numberRenderer("0.00"),summaryType: 'sum'},
  	            {header : '成本',dataIndex: 'cost', width: 100, renderer: Ext.util.Format.numberRenderer("0.00"),summaryType: 'sum'},
  	            {header : '毛利',dataIndex: 'profit', width: 100, renderer: Ext.util.Format.numberRenderer("0.00"),summaryType: 'sum'}
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
        },orderTypeCombo,MemberCombo,payCombo,{
//        	xtype: 'numberfield',
//        	id: 'startPrice',
//        	emptyText: '金额开始',
//        	width: 120,
//        },{
//        	xtype: 'numberfield',
//        	id: 'endPrice',
//        	emptyText: '金额结束',
//        	width: 120,
//        },{
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
		title: '营业流水',
		iconCls: 'money',
		columns : orderColumns,
		store : orderStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		tbar : orderTbar, 
		bbar : bbar,
		stripeRows:true,
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
	
//	var detailStore = Ext.create('Ext.data.Store', {
//		fields: ['orderId', 'itemNo', 'saleDate', 'name', 'carId', 'carNumber', 'goodsId', 'goodsName', 'number', 'price'],
//		proxy:{
//            type: 'ajax',
//            url : '/order/queryOrderByGoodsIdPage.atc',
//            reader : {
//                root: 'orderList',
//                totalProperty: 'totalCount'
//            }
//        }
//    });
//	var detailRowNum = new Ext.grid.RowNumberer({
//		header : 'NO',
//		resizable: true,
//		width : 40
//	});
//	
//	var detailColumns= [detailRowNum,
//	            {header : '订单号',dataIndex: 'orderId',menuDisabled: true},
//	 	        {header : '行项目',dataIndex: 'itemNo',menuDisabled: true, hidden:true},
//	 	        {header : '销售日期',dataIndex: 'saleDate',menuDisabled: true, width:'16%'},
//	 	        {header : '姓名',dataIndex: 'name',menuDisabled: true},
//	 	        {header : '车辆ID',dataIndex: 'carId',menuDisabled: true, hidden:true},
//	 	        {header : '车牌号',dataIndex: 'carNumber',menuDisabled: true},
//	 	        {header : '商品ID',dataIndex: 'goodsId',menuDisabled: true, hidden:true},
//	 	        {header : '项目/商品',dataIndex: 'goodsName',menuDisabled: true},
//	 	        {header : '次数/数量',dataIndex: 'number',menuDisabled: true},
//	 	        {header : '价格',dataIndex: 'price',menuDisabled: true,renderer: Ext.util.Format.numberRenderer("0.00")}
//	 	];
//	
//	var detailPagesizeCombo = new Ext.form.ComboBox({
//		name : 'detailPagesize',
//		triggerAction : 'all',
//		mode : 'local',
//		store : new Ext.data.ArrayStore({
//			fields : [ 'value', 'text' ],
//			data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
//					[ 100, '100条/页' ], [ 200, '200条/页' ]]
//		}),
//		valueField : 'value',
//		displayField : 'text',
//		value : '10',
//		editable : false,
//		width : 85
//	});
//
//	var detailNumber = parseInt(detailPagesizeCombo.getValue());
//	
//	// 改变每页显示条数reload数据
//	detailPagesizeCombo.on("select", function(comboBox) {
//		detailBbar.pageSize = parseInt(comboBox.getValue());
//		number = parseInt(comboBox.getValue());
//		detailStore.pageSize = parseInt(comboBox.getValue());
//		var params = getDetailParams();
//		params.start = 0;
//		params.limit = detailBbar.pageSize;
//		detailStore.reload({
//			params : params
//		});
//	});
//	
//	var detailBbar = new Ext.PagingToolbar({
//		pageSize : detailNumber,
//		store : detailStore,
//		displayInfo : true,
//		displayMsg : '显示{0}条到{1}条,共{2}条',
//		emptyMsg : "没有符合条件的记录",
//		items : [ '-', '&nbsp;&nbsp;', detailPagesizeCombo ]
//	});
//		
//	var detailGrid = Ext.create('Ext.grid.GridPanel', {
//		minHeight: 220,
//		columns : detailColumns,
//		store : detailStore, 
//		bbar : detailBbar,
//		region : 'center',
//		autoScroll : true,
//		columnLines : true,
//		frame : false,
//		stripeRows: true,
//		forceFit: true,
//		viewConfig : {
//			enableTextSelection:true	//设置单元格可以选择
//		},
//		loadMask : {
//			msg : '正在加载表格数据,请稍等...'
//	    }
//	});
//	
//	var detailWindow = Ext.create('Ext.Window',{
//		layout : 'fit',
//		title: '详细信息',
//		iconCls: 'award_star_bronze_3',
//		width : 730,
//		height : 440,
//		resizable : false,
//		draggable : true,
//		closeAction : 'hide',
//		modal : false,
//		collapsible : true,
//		titleCollapse : true,
//		maximizable : false,
//		buttonAlign : 'right',
//		border : false,
//		animCollapse : true,
//		constrain : true,
//		items: [detailGrid]
//	});
//	
//	detailWindow.on('show', function(){
//		detailStore.load();
//	});
	
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [orderGrid]
	});
	
	orderStore.on('beforeload', function() {
		Ext.apply(orderStore.proxy.extraParams, getParams());
	});
	
//	detailStore.on('beforeload', function(){
//		Ext.apply(detailStore.proxy.extraParams, getDetailParams());
//	});
	
	orderStore.load();
	
//	detailStore.on('load', function(){
//		if(!Ext.isEmpty(detailStore)){
//			var obj = {}, count = 0;
//			Ext.each(detailStore.data.items, function(detail){
//				if(!obj[detail.data.carId]){
//					obj[detail.data.carId] = detail;
//					count++;
//				}
//			});
//			detailWindow.setTitle("详细信息（总车辆数：" + count + " 台）");
//		}
//	});
	
	
	function getParams(){
		return {
			'orderHead.startDate': parent.dateFormat(Ext.getCmp("startDate").getValue()),
			'orderHead.endDate': parent.dateFormat(Ext.getCmp("endDate").getValue()),
			'orderHead.orderType': orderTypeCombo.getValue(),
			'orderHead.memId': MemberCombo.getValue(),
			'orderHead.pbalance': (!Ext.isEmpty(payCombo.getValue()) && payCombo.getValue().indexOf("pbalance") >= 0) ? 1.0 : 0.0,
			'orderHead.pcash': (!Ext.isEmpty(payCombo.getValue()) && payCombo.getValue().indexOf("pcash") >= 0) ? 1.0 : 0.0,
			'orderHead.pcard': (!Ext.isEmpty(payCombo.getValue()) && payCombo.getValue().indexOf("pcard") >= 0) ? 1.0 : 0.0,
			'orderHead.ptransfer': (!Ext.isEmpty(payCombo.getValue()) && payCombo.getValue().indexOf("ptransfer") >= 0) ? 1.0 : 0.0,
			'orderHead.pwechat': (!Ext.isEmpty(payCombo.getValue()) && payCombo.getValue().indexOf("pwechat") >= 0) ? 1.0 : 0.0,
			'orderHead.palipay': (!Ext.isEmpty(payCombo.getValue()) && payCombo.getValue().indexOf("palipay") >= 0) ? 1.0 : 0.0,
			'orderHead.pbill': (!Ext.isEmpty(payCombo.getValue()) && payCombo.getValue().indexOf("pbill") >= 0) ? 1.0 : 0.0,
		};
	}
	
//	function getDetailParams(){
//		return {
//			'orderItem.startDate': parent.dateFormat(Ext.getCmp("startDate").getValue()),
//			'orderItem.endDate': parent.dateFormat(Ext.getCmp("endDate").getValue()),
//			'orderItem.goodsId': goodsId
//		};
//	}
	
});