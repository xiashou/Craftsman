/**
 * 充值记录
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	
	var orderRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var orderStore = Ext.create('Ext.data.Store', {
		  fields: ['orderNo', 'memId', 'goodsId', 'goodsName', 'number', 'name', 'sex', 'mobile', 'createTime'],
		  proxy: {
		      type: 'ajax',
			  url : '/report/queryReptRechargeListPage.atc',
			  reader : {
			      root: 'list',
			      totalProperty: 'totalCount'
			  }
		  }
	});
	
	var orderColumns= [orderRowNum,
	      	            {header : '订单号',dataIndex: 'orderNo', width: 120},
	      	            {header : '创建时间',dataIndex: 'createTime', width: 160},
	      	            {header : '姓名',dataIndex: 'name', width: 120},
	      	            {header : '称谓',dataIndex: 'sex', width: 70, renderer: function(v){return (v == 1) ? '先生': (v==2 ? '女士' : '未知');}},
	      	            {header : '手机号码',dataIndex: 'mobile', width: 130},
	      	            {header : '类型',dataIndex: 'goodsId', width: 130, renderer: function(v){return (v == 'B') ? '充值': '赠送';}},
	      	            {header : '商品',dataIndex: 'goodsName', width: 100},
	      	            {header : '金额/数量',dataIndex: 'number', width: 120}
	      	  	];
	
	var orderTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	id: 'delete',
        	text: ' 查 询 ',
        	iconCls: 'preview', 
        	handler: function(){
        		
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
		orderStore.reload({
			params : {
				start : 0,
				limit : bbar.pageSize
			}
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
		title: '客户充值',
		iconCls: 'money',
		columns : orderColumns,
		store : orderStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		tbar : orderTbar, 
		bbar : bbar,
		stripeRows:true,
//		forceFit: true,
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
		},
		listeners:{
	        itemclick: function(grid, record, item, index, e) {
	        	
	        }
	    }
	});
	
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [orderGrid]
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
	
	function printOrderByNo(orderId){
		//如果存在之前打印的选项卡则先关闭
		if(parent.mainTab.getComponent('1000000'))
			parent.global.closeTab(parent.mainTab.getComponent('1000000'));
		
		//打开打印选项卡然后关闭录单选项卡
		parent.global.openTab("1000000", "订单打印", "/order/initOrderPrint.atc?orderNo=" + orderId.trim(), null);
	}
	
});