/**
 * 员工提成报表
 * @author Think_XIA
 */
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	var date = new Date();
	date.setDate(1);
	
	var loadMarsk = new Ext.LoadMask(Ext.getBody(), {    
	    msg:'正在计算提成，请稍候......',    
	    removeMask:true // 完成后移除    
	});
	
	var rowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});

	var employeeStore = Ext.create('Ext.data.Store', {
		fields : [ 'id', 'name', 'comm1', 'comm2', 'comm3', 'comm4', 'comm5', 'comm6'],
		proxy : {
			type : 'ajax',
			url : '/shop/queryEmployeeCommission.atc',
			reader : {
				root : 'employeeList'
			}
		}
	});
	
	var sumRenderer = function(value, cellmeta, record) {
		return Number(record.data.comm1 + record.data.comm2 + record.data.comm3 + record.data.comm4 + record.data.comm5 + record.data.comm6).toFixed(2);
    };
	
	var columns = [ rowNum, {
		text : 'ID',
		dataIndex : 'id',
		hidden: true
	}, {
		text : '姓名',
		width : 100,
		dataIndex : 'name'
	}, {
		text : '施工提成',
		width : 120,
		sortable : true,
		dataIndex : 'comm1',
		summaryType : 'sum',
		renderer : Ext.util.Format.numberRenderer("0.00")
	}, {
		text : '销售提成',
		width : 120,
		sortable : true,
		dataIndex : 'comm2',
		summaryType : 'sum',
		renderer : Ext.util.Format.numberRenderer("0.00")
	}, {
		text : '辅助销售提成',
		width : 120,
		sortable : true,
		dataIndex : 'comm3',
		summaryType : 'sum',
		renderer : Ext.util.Format.numberRenderer("0.00")
	}, {
		text : '接单提成',
		width : 120,
		sortable : true,
		dataIndex : 'comm4',
		summaryType : 'sum',
		renderer : Ext.util.Format.numberRenderer("0.00")
	}, {
		text : '顾问提成',
		width : 120,
		sortable : true,
		dataIndex : 'comm5',
		summaryType : 'sum',
		renderer : Ext.util.Format.numberRenderer("0.00")
	}, {
		text : '组长提成',
		width : 120,
		sortable : true,
		dataIndex : 'comm6',
		summaryType : 'sum',
		renderer : Ext.util.Format.numberRenderer("0.00")
	}, {
		text : '合计',
		width : 120,
		sortable : true,
		dataIndex : 'comm7',
		summaryType : 'sum',
		renderer : sumRenderer
	} ];

	var tbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
		items : [ {
			xtype : 'datefield',
			id : 'startDate',
			emptyText : '开始日期',
			width : 140,
			format : 'Y/m/d',
			value : date
		}, {
			xtype : 'datefield',
			id : 'endDate',
			emptyText : '结束日期',
			width : 140,
			format : 'Y/m/d'
		}, {
			xtype : 'button',
			id : 'search',
			text : ' 查 询 ',
			iconCls : 'preview',
			handler : function() {
				reckonCommission();
			}
		}, '->', {
			xtype : 'button',
			text : '导出Excel',
			iconCls : 'page_white_excel',
			handler : function() {
				commissionGrid.downloadExcelXml();
			}
		} ]
	});
	
	var commissionGrid = new Ext.grid.GridPanel({
		title : '提成',
		iconCls : 'wrench',
		columns : columns,
		store : employeeStore,
		region : 'center',
		autoScroll : true,
		columnLines : true,
		tbar : tbar,
		stripeRows : true,
		tools : [ {
			type : 'help',
			tooltip : '操作指引',
			callback : function(panel, tool, event) {
				parent.global.openSystemHelpTab();
			}
		} ],
		features : [ {
			ftype : 'summary',
			dock : 'bottom'
		} ],
		viewConfig : {
			enableTextSelection : true
		},
		loadMask : {
			msg : '正在计算员工提成,请稍等...'
		}
	});
	
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [commissionGrid]
	});
	
	function reckonCommission() {
		employeeStore.load({
			params : getParams()
		});
	}
	
	function getParams(){
		return {
			'orderItem.startDate': parent.dateFormat(Ext.getCmp("startDate").getValue()),
			'orderItem.endDate': parent.dateFormat(Ext.getCmp("endDate").getValue())
		};
	}
	
    
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	var leaderRatio, orderRatio, salesRatio;	//提成参数
//	
//	var date = new Date();
//	date.setDate(1);
//	
//	var loadMarsk = new Ext.LoadMask(Ext.getBody(), {    
//	    msg:'正在计算提成，请稍候......',    
//	    removeMask:true // 完成后移除    
//	});
//	
//	var rowNum = new Ext.grid.RowNumberer({
//		header : 'NO',
//		resizable: true,
//		width : 40
//	});
//	var rowNum2 = new Ext.grid.RowNumberer({
//		header : 'NO',
//		resizable: true,
//		width : 40
//	});
//	var rowNum3 = new Ext.grid.RowNumberer({
//		header : 'NO',
//		resizable: true,
//		width : 40
//	});
//	
//	Ext.define('CommissionModel', {
//	    extend: 'Ext.data.Model',
//	    fields: [
//	        {name: 'orderId', type: 'string'},
//	        {name: 'saleDate', type: 'string'},
//	        {name: 'goodsId', type: 'string'},
//	        {name: 'goodsType', type: 'string'},
//	        {name: 'goodsName', type: 'string'},
//	        {name: 'unitPrice', type: 'float'},
//	        {name: 'number', type: 'float'},
//	        {name: 'price', type: 'float'},
//	        {name: 'source', type: 'string'},
//	        {name: 'cPrice', type: 'float'},
//	        {name: 'performer', type: 'string'},
//	        {name: 'seller', type: 'string'}
//	    ]
//	});
//	
//	Ext.define('CommissionModel2', {
//	    extend: 'Ext.data.Model',
//	    fields: [
//	        {name: 'orderId', type: 'string'},
//	        {name: 'orderType', type: 'string'},
//	        {name: 'saleDate', type: 'string'},
//	        {name: 'carNumber', type: 'string'},
//	        {name: 'aprice', type: 'float'},
//	        {name: 'orderRatio', type: 'string'},
//	        {name: 'cPrice', type: 'float'},
//	        {name: 'creator', type: 'string'}
//	    ]
//	});
//	
//	Ext.define('CommissionModel3', {
//	    extend: 'Ext.data.Model',
//	    fields: [
//	        {name: 'orderId', type: 'string'},
//	        {name: 'orderType', type: 'string'},
//	        {name: 'saleDate', type: 'string'},
//	        {name: 'carNumber', type: 'string'},
//	        {name: 'aprice', type: 'float'},
//	        {name: 'orderRatio', type: 'string'},
//	        {name: 'cPrice', type: 'float'},
//	        {name: 'creator', type: 'string'},
//	        {name: 'name', type: 'string'}
//	    ]
//	});
//	
//	var employeeStore = Ext.create('Ext.data.Store', {
//		  fields: ['id', 'name', 'position', 'entryDate', 'enable'],
//		  proxy: {
//		      type: 'ajax',
//			  url : '/shop/queryEmployeeByDept.atc',
//			  reader : {
//			      root: 'employeeList'
//			  }
//		  }
//	});
//	
//	var employeeGroupStore = Ext.create('Ext.data.Store', {
//		  fields: ['id', 'name', 'groupLeader', 'groupMember', 'enable'],
//		  proxy: {
//		      type: 'ajax',
//			  url : '/shop/queryEmployeeGroupByDept.atc',
//			  reader : {
//			      root: 'employeeList'
//			  }
//		  }
//	});
//	
//	var commissionStore = new Ext.data.Store({
//		model: CommissionModel,
//		proxy: {
//		      type: 'ajax',
//			  url : '/order/queryBuildCommOrder.atc',
//			  reader : {
//			      root: 'orderList'
//			  }
//		}
//    });
//	
//	var commissionStore2 = new Ext.data.Store({
//		model: CommissionModel2,
//		proxy: {
//			type: 'ajax',
//			url : '/order/queryTakeCommHead.atc',
//			reader : {
//			    root: 'hList'
//			}
//		}
//    });
//	
//	var commissionStore3 = new Ext.data.Store({
//		model: CommissionModel3,
//		proxy: {
//			type: 'ajax',
//			url : '/order/querySalesCommHead.atc',
//			reader : {
//			    root: 'hList'
//			}
//		}
//    });
//	
//	var columns = [rowNum, {
//        text     : '订单号',
//        sortable : false,
//        dataIndex: 'orderId'
//    }, {
//        text     : '销售时间',
//        sortable : true,
//        width: 130,
//        dataIndex: 'saleDate'
//    }, {
//    	text     : '项目/商品',
//    	sortable : true,
//    	dataIndex: 'goodsName'
//    }, {
//    	text     : '数量',
//    	sortable : true,
//    	width: 60,
//    	summaryType: 'sum',
//    	dataIndex: 'number'
//    }, {
//    	text     : '工时费/单价',
//    	sortable : true,
//    	dataIndex: 'unitPrice',
//    	summaryType: 'sum',
//    	renderer: Ext.util.Format.numberRenderer("0.00")
//    }, {
//    	text     : '毛利',
//    	sortable : true,
//    	dataIndex: 'price',
//    	summaryType: 'sum',
//    	renderer: Ext.util.Format.numberRenderer("0.00")
//    }, {
//    	text     : '提成方式',
//    	sortable : true,
//    	dataIndex: 'source'
//    }, {
//    	text     : '提成金额',
//    	sortable : true,
//    	dataIndex: 'cPrice',
//    	summaryType: 'sum',
//    	renderer: Ext.util.Format.numberRenderer("0.00")
//    }];
//	
//	var orderTypeRenderer = function(value, cellmeta, record) {
//		var type = parent.GOrderTypeStore.findRecord("value", value);
//		if(type) {
//			record.data.orderType = type.data.text;
//			return type.data.text;
//		} else
//			return value;
//	}
//	
//	var columns2 = [rowNum2, {
//        text     : '订单号',
//        sortable : false,
//        dataIndex: 'orderId'
//    }, {
//    	text     : '订单类型',
//    	sortable : false,
//    	dataIndex: 'orderType',
//    	renderer: orderTypeRenderer
//    }, {
//        text     : '销售时间',
//        sortable : true,
//        width: 130,
//        dataIndex: 'saleDate'
//    }, {
//    	text     : '车牌',
//    	sortable : true,
//    	width: 130,
//    	dataIndex: 'carNumber'
//    }, {
//    	text     : '实收',
//    	sortable : true,
//    	dataIndex: 'aprice',
//    	summaryType: 'sum',
//    	renderer: Ext.util.Format.numberRenderer("0.00")
//    }, {
//    	text     : '提成方式',
//    	sortable : true,
//    	dataIndex: 'orderRatio'
//    }, {
//    	text     : '提成金额',
//    	sortable : true,
//    	dataIndex: 'cPrice',
////    	summaryType: 'sum',
//    	renderer: Ext.util.Format.numberRenderer("0.00"),
//    	summaryType: function(records){
//         	var i = 0, length = records.length, total = 0, record;
//            for (; i < length; ++i) {
//            	record = records[i];
//            	total += Number(Ext.isEmpty(record.get("cPrice")) ? 0 : record.get("cPrice")).toFixed(2);
//            }
//            return total;
//        }
//    }];
//	
//	var columns3 = [rowNum3, {
//        text     : '订单号',
//        sortable : false,
//        dataIndex: 'orderId'
//    }, {
//    	text     : '订单类型',
//    	sortable : false,
//    	dataIndex: 'orderType',
//    	renderer: orderTypeRenderer
//    }, {
//        text     : '销售时间',
//        sortable : true,
//        width: 130,
//        dataIndex: 'saleDate'
//    }, {
//    	text     : '车牌',
//    	sortable : true,
//    	width: 130,
//    	dataIndex: 'carNumber'
//    }, {
//    	text     : '实收',
//    	sortable : true,
//    	dataIndex: 'aprice',
//    	summaryType: 'sum',
//    	renderer: Ext.util.Format.numberRenderer("0.00")
//    }, {
//    	text     : '提成方式',
//    	sortable : true,
//    	dataIndex: 'salesRatio'
//    }, {
//    	text     : '提成金额',
//    	sortable : true,
//    	dataIndex: 'cPrice',
////    	summaryType: 'sum',
//    	renderer: Ext.util.Format.numberRenderer("0.00"),
//    	summaryType: function(records){
//         	var i = 0, length = records.length, total = 0, record;
//            for (; i < length; ++i) {
//            	record = records[i];
//            	total += Number(Ext.isEmpty(record.get("cPrice")) ? 0 : record.get("cPrice")).toFixed(2);
//            }
//            return total;
//        }
//    }];
//	
//	var orderTypeCombo = new Ext.form.ComboBox({
//		triggerAction : 'all',
//		mode : 'local',
//		store: parent.GOrderTypeStore,
//		emptyText: '订单类型',
//		valueField : 'value',
//		displayField : 'text',
//		editable : true,
//		width: 140
//	});
//	
//	var tbar = Ext.create('Ext.toolbar.Toolbar', {
//		region : 'north',
//		border : false,
//        items: [{
//        	xtype: 'datefield',
//        	id: 'startDate',
//        	emptyText: '开始日期',
//        	width: 140,
//        	format:'Y/m/d',
//        	value: date
//        },{
//        	xtype: 'datefield',
//        	id: 'endDate',
//        	emptyText: '结束日期',
//        	width: 140,
//        	format:'Y/m/d'
//        },orderTypeCombo,{
//        	xtype: 'textfield',
//        	id: 'goodsName',
//        	emptyText: '项目/商品',
//        	width: 160,
//        },{
//        	xtype: 'button',
//        	id: 'search',
//        	text: ' 查 询 ',
//        	iconCls: 'preview', 
//        	handler: function(){
//        		loadMarsk.show();
//        		showCommission();
//            }
//        },'->',{
//        	xtype: 'button',
//        	text:'导出Excel',
//        	iconCls : 'page_white_excel',
//        	handler: function() {
//        		commissionGrid.downloadExcelXml();
//        	}
//        }]
//    });
//	
//	var orderTypeCombo2 = new Ext.form.ComboBox({
//		triggerAction : 'all',
//		mode : 'local',
//		store: parent.GOrderTypeStore,
//		emptyText: '订单类型',
//		valueField : 'value',
//		displayField : 'text',
//		editable : true,
//		width: 140
//	});
//	
//	var tbar2 = Ext.create('Ext.toolbar.Toolbar', {
//		region : 'north',
//		border : false,
//        items: [{
//        	xtype: 'datefield',
//        	emptyText: '开始日期',
//        	width: 140,
//        	id: 'startDate2',
//        	format:'Y/m/d',
//        	value: date
//        },{
//        	xtype: 'datefield',
//        	emptyText: '结束日期',
//        	width: 140,
//        	id: 'endDate2',
//        	format:'Y/m/d'
//        },orderTypeCombo2,{
//        	xtype: 'button',
//        	text: ' 查 询 ',
//        	iconCls: 'preview', 
//        	handler: function(){
//        		loadMarsk.show();
//        		showCommission2();
//            }
//        },'->',{
//        	xtype: 'button',
//        	text:'导出Excel',
//        	iconCls : 'page_white_excel',
//        	handler: function() {
//        		commissionGrid2.downloadExcelXml();
//        	}
//        }]
//    });
//	
//	var tbar3 = Ext.create('Ext.toolbar.Toolbar', {
//		region : 'north',
//		border : false,
//        items: [{
//        	xtype: 'datefield',
//        	emptyText: '开始日期',
//        	width: 140,
//        	id: 'startDate3',
//        	format:'Y/m/d',
//        	value: date
//        },{
//        	xtype: 'datefield',
//        	emptyText: '结束日期',
//        	width: 140,
//        	id: 'endDate3',
//        	format:'Y/m/d'
//        },{
//        	xtype: 'button',
//        	text: ' 查 询 ',
//        	iconCls: 'preview', 
//        	handler: function(){
//        		loadMarsk.show();
//        		showCommission3();
//            }
//        },'->',{
//        	xtype: 'button',
//        	text:'导出Excel',
//        	iconCls : 'page_white_excel',
//        	handler: function() {
//        		commissionGrid3.downloadExcelXml();
//        	}
//        }]
//    });
//	
//	var commissionGrid = new Ext.grid.GridPanel({
//		title: '施工提成',
//		iconCls: 'wrench',
//		columns : [],
//		region : 'center',
//		autoScroll : true,
//		columnLines : true,
//		tbar : tbar, 
//		stripeRows:true,
//		tools:[{
//            type:'help',
//            tooltip: '操作指引',
//            callback: function(panel, tool, event) {
//            	parent.global.openSystemHelpTab();
//            }
//        }],
//        features: [{
//            ftype: 'summary',
//            dock: 'bottom'
//        }],
//		viewConfig : {
//			enableTextSelection:true	//设置单元格可以选择
//		},
//		loadMask : {
//			msg : '正在计算员工提成,请稍等...'
//		}
//	});
//	
//	var commissionGrid2 = new Ext.grid.GridPanel({
//		title: '接单提成',
//		iconCls: 'page_white_paint',
//		columns : [],
//		region : 'center',
//		autoScroll : true,
//		columnLines : true,
//		tbar : tbar2, 
//		stripeRows:true,
//		tools:[{
//            type:'help',
//            tooltip: '操作指引',
//            callback: function(panel, tool, event) {
//                // show help here
//            }
//        }],
//        features: [{
//            ftype: 'summary',
//            dock: 'bottom'
//        }],
//		viewConfig : {
//			enableTextSelection:true	//设置单元格可以选择
//		},
//		loadMask : {
//			msg : '正在计算员工提成,请稍等...'
//		}
//	});
//	
//	var commissionGrid3 = new Ext.grid.GridPanel({
//		title: '顾问提成',
//		iconCls: 'phone',
//		columns : [],
//		region : 'center',
//		autoScroll : true,
//		columnLines : true,
//		tbar : tbar3, 
//		stripeRows:true,
//        features: [{
//            ftype: 'summary',
//            dock: 'bottom'
//        }],
//		viewConfig : {
//			enableTextSelection:true	//设置单元格可以选择
//		},
//		loadMask : {
//			msg : '正在计算员工提成,请稍等...'
//		}
//	});
//	
//	var mainTab = Ext.create('Ext.TabPanel', {
//		region : 'center',
//		margins : 0,
//		deferredRender : false,
//		activeTab : 0,
//		items : [commissionGrid, commissionGrid2, commissionGrid3]
//	});
//	
//	var viewport = new Ext.Viewport({
//		layout : 'border',
//		items : [mainTab]
//	});
//	
//	//门店参数配置信息
//	Ext.Ajax.request({
//		async: false,   //ASYNC 是否异步( TRUE 异步 , FALSE 同步)
//		url : '/shop/queryParamById.atc',
//		success : function(resp, opts) {
//			var result = Ext.decode(resp.responseText);
//			if(result && !Ext.isEmpty(result)){
//				if(!Ext.isEmpty(result.param4) && !isNaN(result.param4))
//					leaderRatio = result.param4;		//组长提成比例
//				if(!Ext.isEmpty(result.param5))
//					orderRatio = result.param5;			//接单提成
//				if(!Ext.isEmpty(result.param6))
//					salesRatio = result.param6;			//顾问提成
//			}
//		},
//		failure : function(resp, opts) {
//            Ext.Msg.alert('提示', "店铺参数配置信息加载出错！");
//		}
//	});
//	
//	employeeStore.load();
//	employeeStore.on('load', function(){
//		employeeStore.each(function(rec){
//			columns.push({text:rec.get("name"),
//				columns:[
//					{text: "提成比例", dataIndex: "ratio" + rec.get("id")},
//				    {text: "提成金额", dataIndex: "comm" + rec.get("id"), renderer: Ext.util.Format.numberRenderer("0.00"),
//			        	 summaryType: function(records){
//			             	var i = 0, length = records.length, total = 0, record;
//			                for (; i < length; ++i) {
//			                	record = records[i];
//			                	total += Number(Ext.isEmpty(record.get("comm" + rec.get("id"))) ? 0 : record.get("comm" + rec.get("id"))).toFixed(2) * 1;
//			                }
//			                return total;
//			             }
//			         }
//				]
//			}); 
//			columns2.push({text:rec.get("name"), dataIndex: "comm" + rec.get("name"), renderer: Ext.util.Format.numberRenderer("0.00"),
//				summaryType: function(records){
//	             	var i = 0, length = records.length, total = 0, record;
//	                for (; i < length; ++i) {
//	                	record = records[i];
//	                	total += Number(Ext.isEmpty(record.get("comm" + rec.get("name"))) ? 0 : record.get("comm" + rec.get("name"))).toFixed(2) * 1;
//	                }
//	                return total;
//	            }
//			});
//			columns3.push({text:rec.get("name"), dataIndex: "comm" + rec.get("name"), renderer: Ext.util.Format.numberRenderer("0.00"),
//				summaryType: function(records){
//	             	var i = 0, length = records.length, total = 0, record;
//	                for (; i < length; ++i) {
//	                	record = records[i];
//	                	total += Number(Ext.isEmpty(record.get("comm" + rec.get("name"))) ? 0 : record.get("comm" + rec.get("name"))).toFixed(2) * 1;
//	                }
//	                return total;
//	            }
//			});
//    	});
//		commissionGrid.reconfigure(commissionStore, columns);
//		commissionGrid2.reconfigure(commissionStore2, columns2);
//		commissionGrid3.reconfigure(commissionStore3, columns3);
//	});
//	
//	commissionStore.on('load', function(){
//		var i = 0;
//		commissionStore.each(function(rec){
//			//计算每列提成金额，按百分比或按固定金额
//			if(rec.data.source.indexOf('%') > 0) {
//				//项目按照公式计算，商品按照实收价格计算
//				if(rec.data.goodsType == '1' || rec.data.goodsType == '3')
//					commissionStore.getAt(i).set('cPrice', Number(Number(rec.data.source.replace('%', '')) * Number(rec.data.unitPrice * 0.01)).toFixed(2));
//				else if(rec.data.goodsType == '2' || rec.data.goodsType == '4')
//					commissionStore.getAt(i).set('cPrice', Number(Number(rec.data.source.replace('%', '')) * Number(rec.data.price * 0.01)).toFixed(2));
//			} else {
//  		  		commissionStore.getAt(i).set('cPrice', Number(rec.data.source).toFixed(2));
//  		  		commissionStore.getAt(i).set('source', Number(rec.data.source).toFixed(2));
//  		  	}
//			
//			//将施工方和销售方合并放入数组中
//			var ids = new Array();
//			if(!Ext.isEmpty(rec.data.performer)){
//				//单个人或组时特殊处理
//				if(rec.data.performer.length == 2)
//					ids.push(rec.data.performer.substring(0, rec.data.performer.length - 1));
//				else
//					ids = rec.data.performer.substring(0, rec.data.performer.length - 1).split(",");
//			} else if(!Ext.isEmpty(rec.data.seller)){
//				if(rec.data.seller.length == 2)
//					ids.push(rec.data.seller.substring(0, rec.data.seller.length - 1));
//				else
//					ids = rec.data.seller.substring(0, rec.data.seller.length - 1).split(",");
//			}
//			
//			var emps = new Array();		//除组长外所有平摊提成的员工
//			var isLeader = false;		//是否有组长
//			for (index in ids){
//				var emp = employeeStore.findRecord("id", ids[index].trim());
//				//判断是否为员工，是员工则直接放入数组中
//				if(!Ext.isEmpty(emp))
//					emps.push(ids[index].trim());
//				else {
//					//为组时，把所有组员（不包括组长）放入员工数组中
//					var group = employeeGroupStore.findRecord("id", ids[index].trim());
//					if(!Ext.isEmpty(group)){
//						var eids;
//						if(!Ext.isEmpty(group.data.groupMember)){
//							eids = group.data.groupMember.split(",");
//							for (eindex in eids){
//								emps.push(eids[eindex].trim());
//							}
//						}
//						//组长提成按设定的特殊比例
//						if(!Ext.isEmpty(group.data.groupLeader)){
//							commissionStore.getAt(i).set('ratio' + group.data.groupLeader, leaderRatio + "%");
//							commissionStore.getAt(i).set('comm'  + group.data.groupLeader, Number(Number(rec.get("cPrice")) * leaderRatio / 100).toFixed(2));
//							isLeader = true;
//						}
//					}
//				}
//			}
//			//设置所有均摊提成的员工
//			for(index in emps){
//				//只有一个员工时，拿全部提成
//				if(emps.length == 1){
//					commissionStore.getAt(i).set('ratio' + emps[0], "100%");
//					commissionStore.getAt(i).set('comm'  + emps[0], Number(rec.get("cPrice")).toFixed(2));
//				//多个员工时，均摊除去组长部分的提成
//				} else {
//					var c = isLeader ? Math.round((100 - leaderRatio) / emps.length) : Math.round(100 / emps.length);
//					commissionStore.getAt(i).set('ratio' + emps[index], c + "%");
//					commissionStore.getAt(i).set('comm'  + emps[index], Number(Number(rec.get("cPrice")) * c / 100).toFixed(2));
//				}
//			}
//			rec.commit();
//			i++;
////			console.log(Number(i/commissionStore.getCount()).toFixed(1));
//			Ext.MessageBox.updateProgress(Number(i/commissionStore.getCount()).toFixed(1), '已完成 '+Math.round(i/commissionStore.getCount() * 100)+'%');
//    	});
//		loadMarsk.hide();
//	});
//	
//	commissionStore2.on('load', function(){
//		var i = 0;
//		commissionStore2.each(function(rec){
//			rec.data.orderRatio = orderRatio;
//			if(orderRatio.indexOf('%') > 0)
//				rec.data.cPrice = Number(Number(rec.data.aprice) * Number(orderRatio.replace("%", "")) / 100).toFixed(2);
//			else
//				rec.data.cPrice = orderRatio;
//			
//			if(!Ext.isEmpty(rec.data.creator))
//				commissionStore2.getAt(i).set('comm'  + rec.data.creator, rec.data.cPrice);
//			rec.commit();
//			i++;
//		});
//		loadMarsk.hide();
//	});
//	
//	commissionStore3.on('load', function(){
//		var i = 0;
//		commissionStore3.each(function(rec){
//			rec.data.salesRatio = salesRatio;
//			if(salesRatio.indexOf('%') > 0)
//				rec.data.cPrice = Number(Number(rec.data.aprice) * Number(salesRatio.replace("%", "")) / 100).toFixed(2);
//			else
//				rec.data.cPrice = salesRatio;
//			
//			if(!Ext.isEmpty(rec.data.name))
//				commissionStore3.getAt(i).set('comm'  + rec.data.name, rec.data.cPrice);
//			rec.commit();
//			i++;
//		});
//		loadMarsk.hide();
//	});
//	
//	function showCommission() {
//		employeeGroupStore.load({
//			callback: function(records){
//				commissionStore.load({
//					params : getParams()
//				});
//	        }
//		});
//	}
//	
//	function showCommission2() {
//		commissionStore2.load({
//			params : getParams2()
//		});
//	}
//	
//	function showCommission3() {
//		commissionStore3.load({
//			params : getParams3()
//		});
//	}
//	
//	function getParams(){
//		return {
//			'orderItem.goodsName': Ext.getCmp("goodsName").getValue(),
//			'orderItem.startDate': parent.dateFormat(Ext.getCmp("startDate").getValue()),
//			'orderItem.endDate': parent.dateFormat(Ext.getCmp("endDate").getValue()),
//			'orderItem.orderType': orderTypeCombo.getValue()
//		};
//	}
//	
//	function getParams2(){
//		return {
//			'orderHead.startDate': parent.dateFormat(Ext.getCmp("startDate2").getValue()),
//			'orderHead.endDate': parent.dateFormat(Ext.getCmp("endDate2").getValue()),
//			'orderHead.orderType': orderTypeCombo2.getValue()
//		};
//	}
//	
//	function getParams3(){
//		return {
//			'orderHead.startDate': parent.dateFormat(Ext.getCmp("startDate3").getValue()),
//			'orderHead.endDate': parent.dateFormat(Ext.getCmp("endDate3").getValue())
//		};
//	}
	
});