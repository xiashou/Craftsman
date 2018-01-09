Ext.onReady(function() {
	
	var headRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var itemRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	Ext.define('MoveHeadStore', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'id', type: 'int'},
	        {name: 'deptOut', type: 'string'},
	        {name: 'deptIn', type: 'string'},
	        {name: 'deptOutName', type: 'string'},
	        {name: 'deptInName', type: 'string'},
	        {name: 'total', type: 'float'},
	        {name: 'status', type: 'int'},
	        {name: 'creator', type: 'string'},
	        {name: 'createTime', type: 'string'}
	    ]
	});
	
	var moveHeadStore = Ext.create('Ext.data.Store', {
		model: MoveHeadStore,
		proxy: {
			type: 'ajax',
			url : '/goods/queryGoodsMoveInList.atc',
			reader : {
				root: 'moveHeadList'
			}
		}
	});
    
    var statusRenderer = function(value, cellmeta, record) {
        return value == 1 ? "已收货" : "未收货";
    };
    
    var moveHeadColumns= [headRowNum,
          {header : 'ID',dataIndex: 'id', width: 60, hidden: true},
          {header : '调出门店',dataIndex: 'deptOutName', width: 260},
          {header : '调入门店',dataIndex: 'deptInName', width: 260, hidden: true},
          {header : '总数量',dataIndex: 'total', width: 100, xtype: 'numbercolumn', format:'0.00'},
          {header : '状态',dataIndex: 'status', width: 120, renderer: statusRenderer},
          {header : '创建人',dataIndex: 'creator', width: 120},
          {header : '创建时间',dataIndex: 'createTime', width: 200},
          {xtype: 'actioncolumn',header : '详情',width: 50,align:'center',sortable: false,menuDisabled: true,
				items: [{
				    iconCls: 'application_view_list',
				    tooltip: '详细信息',
				    scope: this,
			        handler: function(grid, rowIndex){
			        	var record = moveHeadGrid.getStore().getAt(rowIndex);
			        	moveItemWindow.show();
			        	moveItemStore.load({
			        		params: {
			        			moveId: record.data.id
			        		}
			        	});
			        }
			    }]
          }
	];
	
	var moveHeadTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	id: 'move',
        	text: '确认收货',
        	iconCls: 'accept', 
        	handler: function(){
        		submitMoveIn();
        	}
        }]
    });
	
	var moveHeadSelModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1,
		mode: 'SINGLE'
	});
	
	var moveHeadGrid = new Ext.grid.GridPanel({
		title: '商品调拨',
		columns : moveHeadColumns,
		store : moveHeadStore, 
		selModel : moveHeadSelModel,
		region : 'center',
		autoScroll : true,
		columnLines : true,
		tbar : moveHeadTbar, 
		stripeRows:true,
		viewConfig : {
			enableTextSelection:true	//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
	    }
	});
	
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [moveHeadGrid]
	});
	
	moveHeadStore.load();
	
	Ext.define('MoveItemStore', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'goodsId', type: 'string'},
	        {name: 'goodsName', type: 'string'},
	        {name: 'inPrice', type: 'float'},
	        {name: 'number', type: 'float'}
	    ]
	});
	
	var moveItemStore = Ext.create('Ext.data.Store', {
		model: MoveItemStore,
		proxy: {
			type: 'ajax',
			url : '/goods/queryGoodsMoveItemById.atc',
			reader : {
				root: 'moveItemList'
			}
		}
	});
	
	var moveItemColumns= [itemRowNum,
          {header : '商品名',dataIndex: 'goodsId', hidden: true},
          {header : '商品',dataIndex: 'goodsName', width: 200},
          {header : '进货价',dataIndex: 'inPrice', width: 120, xtype: 'numbercolumn', format:'0.00', summaryType: 'sum'},
          {header : '数量',dataIndex: 'number', width: 120, xtype: 'numbercolumn', format:'0.00', summaryType: 'sum'}
	];
	
	var moveItemTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	text: '确认收货',
        	iconCls: 'accept', 
        	handler: function(){
        		submitMoveIn();
        	}
        }]
    });
	
	var moveItemGrid = new Ext.grid.GridPanel({
		columns : moveItemColumns,
		store : moveItemStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		tbar : moveItemTbar, 
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
	
	var moveItemWindow = Ext.create('Ext.Window',{
		layout : 'fit',
		title: '商品详情',
		iconCls: 'award_star_gold_2',
		width : 600,
		height : 380,
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
		items: [moveItemGrid]
	});
	
	
	
	function submitMoveIn(){
		var record = moveHeadSelModel.getSelection()[0];
		if(!Ext.isEmpty(record)){
			Ext.MessageBox.show({
				title: '请稍等',
				msg: '正在入库 ...',
				width: 200,
				progress: true,
				closable: false
	        });
			Ext.Ajax.request({
	            method:'POST',
	            url: '/goods/submitGoodsMoveIn.atc',
	            params : {
	            	moveId: record.data.id
				},
				success : function(resp, opts) {
					var result = Ext.decode(resp.responseText);
					Ext.MessageBox.hide();
					if(result.success){
						moveItemWindow.hide();
						Ext.example.msg("提示", "成功入库！");
						moveHeadStore.reload();
					} else {
						Ext.MessageBox.hide();
						Ext.MessageBox.show({
				           title: '提示',
				           msg: result.msg,
				           buttons: Ext.MessageBox.OK,
				           icon: Ext.MessageBox.ERROR
		                });
					}
				},
				failure : function(resp, opts) {
					Ext.MessageBox.hide();
					if(resp.status != 404){
						var result = Ext.decode(resp.responseText);
						Ext.Msg.alert('提示', result.msg);
					} else
						Ext.MessageBox.show({
				           title: '提示',
				           msg: "提交失败，请联系系统管理员！",
				           buttons: Ext.MessageBox.OK,
				           icon: Ext.MessageBox.ERROR
		                });
				}
	        });
		} else {
			Ext.MessageBox.show({
	        	title: '提示',
	        	msg: "您没有选中任何项目！",
	        	buttons: Ext.MessageBox.OK,
	        	icon: Ext.MessageBox.ERROR
			});
		}
		
	}
	
	
});