Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	// 定义自动当前页行号
	var rowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var areaStore = Ext.create('Ext.data.Store', {
		fields: ['areaId', 'areaName'],
		proxy: {
			type: 'ajax',
			url : '/base/queryProvinceBaseArea.atc',
			reader : {
				root: 'areaList'
			}
		}
	});
	areaStore.load();
	
	var deptTypeCombo = Ext.create('Ext.form.ComboBox', {
		triggerAction : 'all',
		mode : 'local',
		store: parent.GDeptTypeStore,
		valueField : 'value',
		displayField : 'text',
		editable : false
	});
	
	var store = Ext.create('Ext.data.Store', {
		pageSize : 500,
		fields: ['id', 'parentId', 'deptName', 'deptCode', 'deptType', 'areaId', 'createTime', 'enable'],
		proxy : {
			type : 'ajax',
			url : '/sys/querySysDeptPage.atc',
			reader : {
				root : 'deptList'
			}
		}
	});
	
	var statusRender = function(v){
		return v ? '<span style="color:green;">正常</span>' : '<span style="color:red;">禁用</span>';
	}
	
	var typeRender = function(v){
		return Ext.isEmpty(parent.GDeptTypeStore.findRecord("value", v)) ? '无' : parent.GDeptTypeStore.findRecord("value", v).data.text;
	}
  	
	var columns = [ rowNum, {
		text : '名称',
		width : 280,
		dataIndex : 'deptName',
		editor: {xtype: 'textfield'},
      	items: {
      		xtype: 'textfield',
      		flex : 1,
      		margin: 2,
      		enableKeyEvents: true,
      		listeners: {
      			keyup: function() {
      				store.clearFilter();
      				if (this.value) {
      					store.filter({
      						property     : 'deptName',
      						value         : this.value,
      						anyMatch      : true,
      						caseSensitive : false
      					});
      				}
      			},
      			buffer: 500
      		}
      	}
	}, {
		text : '编码',
		dataIndex : 'id',
		width : 180
	}, {
		text : '上级编码',
		dataIndex : 'parentId',
		width : 180
	}, {
		text : '类型',
		dataIndex : 'deptType',
		width : 180,
		renderer : typeRender
	}, {
		text : '业务对照码',
		dataIndex : 'deptCode',
		width : 100,
		editor: {xtype: 'textfield'},
      	items: {
      		xtype: 'textfield',
      		flex : 1,
      		margin: 2,
      		enableKeyEvents: true,
      		listeners: {
      			keyup: function() {
      				store.clearFilter();
      				if (this.value) {
      					store.filter({
      						property     : 'deptCode',
      						value         : this.value,
      						anyMatch      : true,
      						caseSensitive : false
      					});
      				}
      			},
      			buffer: 500
      		}
      	}
	}, {
		text : '是否可用',
		dataIndex : 'enable',
		width : 100,
		renderer : statusRender
	}, {
		text : '创建时间',
		dataIndex : 'createTime',
		width : 180
	} ]; 
		
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
		value : 500,
		editable : false,
		width : 85
	});

	var number = parseInt(pagesizeCombo.getValue());

	// 改变每页显示条数reload数据
	pagesizeCombo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(comboBox.getValue());
		number = parseInt(comboBox.getValue());
		store.pageSize = parseInt(comboBox.getValue());
		store.reload({
			params : {
				start : 0,
				limit : bbar.pageSize
			}
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
		title: '门店列表',
		iconCls: 'table_relationship',
		columns : columns,
		store : store, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		frame : false,
//		tbar : tbar, 
		bbar : bbar,
		stripeRows:true,
		viewConfig : {
			enableTextSelection:true	//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
		listeners:{
			itemdblclick: function(grid, record, item, index, e) {
				if(parent.haveActionMenu('Edit'))
					initEdit();
	        }
	    }
	});
	
	// 布局模型
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [{
			region : 'center',
			layout : 'fit',
			border : false,
			items : [ grid ]
		}]
	});
	
	store.load({
		params:{
			'dept.deptType':3
		}
	});
	
});