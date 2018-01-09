/**
 * 客户回访管理
 * @author Think_XIA
 */
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	var loadMarsk = new Ext.LoadMask(Ext.getBody(), {    
	    msg:'正在计算提成，请稍候......',    
	    removeMask:true // 完成后移除    
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
	
	var rowNum1 = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	var rowNum2 = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	var rowNum3 = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	var rowNum4 = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	var rowNum5 = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	var rowNum6 = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	Ext.define('VisitModel1', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'id', type: 'int'},
	        {name: 'typeId', type: 'int'},
	        {name: 'memId', type: 'int'},
	        {name: 'name', type: 'string'},
	        {name: 'mobile', type: 'string'},
	        {name: 'carId', type: 'int'},
	        {name: 'carNumber', type: 'string'},
	        {name: 'endDate', type: 'string'},
	        {name: 'result', type: 'string'},
	        {name: 'visitor', type: 'string'},
	        {name: 'visitTime', type: 'string'}
	    ]
	});
	
	Ext.define('VisitModel2', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'id', type: 'int'},
	        {name: 'typeId', type: 'int'},
	        {name: 'memId', type: 'int'},
	        {name: 'name', type: 'string'},
	        {name: 'mobile', type: 'string'},
	        {name: 'carId', type: 'int'},
	        {name: 'carNumber', type: 'string'},
	        {name: 'endDate', type: 'string'},
	        {name: 'result', type: 'string'},
	        {name: 'visitor', type: 'string'},
	        {name: 'visitTime', type: 'string'}
	    ]
	});
	
	Ext.define('VisitModel3', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'id', type: 'int'},
	        {name: 'typeId', type: 'int'},
	        {name: 'memId', type: 'int'},
	        {name: 'name', type: 'string'},
	        {name: 'mobile', type: 'string'},
	        {name: 'carId', type: 'int'},
	        {name: 'carNumber', type: 'string'},
	        {name: 'endDate', type: 'string'},
	        {name: 'result', type: 'string'},
	        {name: 'visitor', type: 'string'},
	        {name: 'visitTime', type: 'string'}
	    ]
	});
	
	Ext.define('VisitModel4', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'id', type: 'int'},
	        {name: 'typeId', type: 'int'},
	        {name: 'memId', type: 'int'},
	        {name: 'name', type: 'string'},
	        {name: 'mobile', type: 'string'},
	        {name: 'lastSale', type: 'string'},
	        {name: 'result', type: 'string'},
	        {name: 'visitor', type: 'string'},
	        {name: 'visitTime', type: 'string'}
	    ]
	});
	
	Ext.define('VisitModel5', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'id', type: 'int'},
	        {name: 'typeId', type: 'int'},
	        {name: 'memId', type: 'int'},
	        {name: 'name', type: 'string'},
	        {name: 'mobile', type: 'string'},
	        {name: 'goodsId', type: 'string'},
	        {name: 'goodsName', type: 'string'},
	        {name: 'endDate', type: 'string'},
	        {name: 'result', type: 'string'},
	        {name: 'visitor', type: 'string'},
	        {name: 'visitTime', type: 'string'}
	    ]
	});
	
	Ext.define('VisitModel6', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'id', type: 'int'},
	        {name: 'typeId', type: 'int'},
	        {name: 'memId', type: 'int'},
	        {name: 'name', type: 'string'},
	        {name: 'mobile', type: 'string'},
	        {name: 'balance', type: 'string'},
	        {name: 'result', type: 'string'},
	        {name: 'visitor', type: 'string'},
	        {name: 'visitTime', type: 'string'}
	    ]
	});
	
	var store1 = new Ext.data.Store({
		model: VisitModel1,
		proxy: {
		      type: 'ajax',
			  url : '/member/queryVisitListByPage.atc',
			  reader : {
			      root: 'visitList',
			      totalProperty: 'totalCount'
			  }
		}
    });
	
	var columns1 = [rowNum1, {
        text     : 'ID',
        hidden	 : true,
        dataIndex: 'id'
    }, {
    	text     : 'ID',
    	hidden	 : true,
    	dataIndex: 'typeId'
    }, {
        text     : '会员ID',
        hidden	 : true,
        dataIndex: 'memId'
    }, {
    	text     : '车辆ID',
    	hidden	 : true,
    	dataIndex: 'carId'
    }, {
    	text     : '会员',
    	width	 : 160,
    	dataIndex: 'name'
    }, {
    	text     : '电话',
    	sortable : true,
    	width	 : 120,
    	dataIndex: 'mobile'
    }, {
    	text     : '车牌号码',
    	width	 : 100,
    	dataIndex: 'carNumber'
    }, {
    	text     : '保养到期',
    	sortable : true,
    	width	 : 140,
    	dataIndex: 'endDate'
    }, {
    	text     : '回访结果',
    	width	 : 220,
    	dataIndex: 'result'
    }, {
    	text     : '回访账号',
    	sortable : true,
    	width	 : 140,
    	dataIndex: 'visitor',
    }, {
    	text     : '回访时间',
    	sortable : true,
    	width	 : 160,
    	dataIndex: 'visitTime',
    }, {
    	header : '回访',xtype: 'actioncolumn',menuDisabled: true, sortable: false,width: 60, align: 'center',
    	items: [{
    		iconCls: 'phone_sound',
    		tooltip: '回访',
    		scope: this,
            handler: function(grid, rowIndex){
            	var record = grid.getStore().getAt(rowIndex);
            	visitForm.loadRecord(record);
            	visitWindow.show();
            }
    	}]
    }];
	
	var MemberCombo1 = Ext.create('Ext.form.ComboBox', {
        store: MemberStore,
        allowBlank: false,
        valueField: 'memId',
        displayField: 'mobile',
        typeAhead: false,
        hideTrigger:true,
        width: 260,
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
	
	var tbar1 = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [MemberCombo1,{
        	xtype: 'button',
        	text: ' 查 询 ',
        	iconCls: 'preview', 
        	handler: function(){
        		store1.load({
        			params : getParams1()
        		});
            }
        }]
    });
	
	var pagesizeCombo1 = new Ext.form.ComboBox({
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

	var number1 = parseInt(pagesizeCombo1.getValue());
	
	pagesizeCombo1.on("select", function(comboBox) {
		bbar1.pageSize = parseInt(comboBox.getValue());
		number1 = parseInt(comboBox.getValue());
		store1.pageSize = parseInt(comboBox.getValue());
		var params = getParams1();
		params.start = 0;
		params.limit = bbar1.pageSize;
		store1.reload({
			params : params
		});
	});
	
	var bbar1 = new Ext.PagingToolbar({
		pageSize : number1,
		store : store1,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesizeCombo1 ]
	});
	
	var visitGrid1 = new Ext.grid.GridPanel({
		title: '保养到期',
		iconCls: 'tag_blue',
		columns : columns1,
		store : store1,
		region : 'center',
		autoScroll : true,
		columnLines : true,
		tbar : tbar1, 
		bbar : bbar1,
		stripeRows:true,
		tools:[{
            type:'help',
            tooltip: '操作指引',
            callback: function(panel, tool, event) {
            	parent.global.openSystemHelpTab();
            }
        }],
		viewConfig : {
			enableTextSelection:true
		},
		loadMask : {
			msg : '正在加载,请稍等...'
		}
	});
	
	
/////////////////////////////////////////////////////////1111111111111111
/////////////////////////////////////////////////////////2222222222222222
	
	var store2 = new Ext.data.Store({
		model: VisitModel2,
		proxy: {
		      type: 'ajax',
			  url : '/member/queryVisitListByPage.atc',
			  reader : {
			      root: 'visitList',
			      totalProperty: 'totalCount'
			  }
		}
    });
	
	var columns2 = [rowNum2, {
        text     : 'ID',
        hidden	 : true,
        dataIndex: 'id'
    }, {
    	text     : 'ID',
    	hidden	 : true,
    	dataIndex: 'typeId'
    }, {
        text     : '会员ID',
        hidden	 : true,
        dataIndex: 'memId'
    }, {
    	text     : '车辆ID',
    	hidden	 : true,
    	dataIndex: 'carId'
    }, {
    	text     : '会员',
    	width	 : 160,
    	dataIndex: 'name'
    }, {
    	text     : '电话',
    	sortable : true,
    	width	 : 120,
    	dataIndex: 'mobile'
    }, {
    	text     : '车牌号码',
    	width	 : 100,
    	dataIndex: 'carNumber'
    }, {
    	text     : '保险到期',
    	sortable : true,
    	width	 : 140,
    	dataIndex: 'endDate'
    }, {
    	text     : '回访结果',
    	width	 : 220,
    	dataIndex: 'result'
    }, {
    	text     : '回访账号',
    	sortable : true,
    	width	 : 140,
    	dataIndex: 'visitor',
    }, {
    	text     : '回访时间',
    	sortable : true,
    	width	 : 160,
    	dataIndex: 'visitTime',
    }, {
    	header : '回访',xtype: 'actioncolumn',menuDisabled: true, sortable: false,width: 60, align: 'center',
    	items: [{
    		iconCls: 'phone_sound',
    		tooltip: '回访',
    		scope: this,
            handler: function(grid, rowIndex){
            	var record = grid.getStore().getAt(rowIndex);
            	visitForm.loadRecord(record);
            	visitWindow.show();
            }
    	}]
    }];
	
	var MemberCombo2 = Ext.create('Ext.form.ComboBox', {
        store: MemberStore,
        allowBlank: false,
        valueField: 'memId',
        displayField: 'mobile',
        typeAhead: false,
        hideTrigger:true,
        width: 260,
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
	
	var tbar2 = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [MemberCombo2,{
        	xtype: 'button',
        	text: ' 查 询 ',
        	iconCls: 'preview', 
        	handler: function(){
        		store2.load({
        			params : getParams2()
        		});
            }
        }]
    });
	
	var pagesizeCombo2 = new Ext.form.ComboBox({
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

	var number2 = parseInt(pagesizeCombo2.getValue());
	
	pagesizeCombo2.on("select", function(comboBox) {
		bbar2.pageSize = parseInt(comboBox.getValue());
		number2 = parseInt(comboBox.getValue());
		store2.pageSize = parseInt(comboBox.getValue());
		var params = getParams2();
		params.start = 0;
		params.limit = bbar2.pageSize;
		store2.reload({
			params : params
		});
	});
	
	var bbar2 = new Ext.PagingToolbar({
		pageSize : number2,
		store : store2,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesizeCombo2 ]
	});
	
	var visitGrid2 = new Ext.grid.GridPanel({
		title: '保险到期',
		iconCls: 'tag_blue',
		columns : columns2,
		store : store2,
		region : 'center',
		autoScroll : true,
		columnLines : true,
		tbar : tbar2, 
		bbar : bbar2,
		stripeRows:true,
		viewConfig : {
			enableTextSelection:true
		},
		loadMask : {
			msg : '正在加载,请稍等...'
		}
	});
	
	////////////////////////////////////////// 2222222222222222
	////////////////////////////////////////// 3333333333333333
	
	var store3 = new Ext.data.Store({
		model: VisitModel3,
		proxy: {
		      type: 'ajax',
			  url : '/member/queryVisitListByPage.atc',
			  reader : {
			      root: 'visitList',
			      totalProperty: 'totalCount'
			  }
		}
    });
	
	var columns3 = [rowNum3, {
        text     : 'ID',
        hidden	 : true,
        dataIndex: 'id'
    }, {
    	text     : 'ID',
    	hidden	 : true,
    	dataIndex: 'typeId'
    }, {
        text     : '会员ID',
        hidden	 : true,
        dataIndex: 'memId'
    }, {
    	text     : '车辆ID',
    	hidden	 : true,
    	dataIndex: 'carId'
    }, {
    	text     : '会员',
    	width	 : 160,
    	dataIndex: 'name'
    }, {
    	text     : '电话',
    	sortable : true,
    	width	 : 120,
    	dataIndex: 'mobile'
    }, {
    	text     : '车牌号码',
    	width	 : 100,
    	dataIndex: 'carNumber'
    }, {
    	text     : '年检到期',
    	sortable : true,
    	width	 : 140,
    	dataIndex: 'endDate'
    }, {
    	text     : '回访结果',
    	width	 : 220,
    	dataIndex: 'result'
    }, {
    	text     : '回访账号',
    	sortable : true,
    	width	 : 140,
    	dataIndex: 'visitor',
    }, {
    	text     : '回访时间',
    	sortable : true,
    	width	 : 160,
    	dataIndex: 'visitTime',
    }, {
    	header : '回访',xtype: 'actioncolumn',menuDisabled: true, sortable: false,width: 60, align: 'center',
    	items: [{
    		iconCls: 'phone_sound',
    		tooltip: '回访',
    		scope: this,
            handler: function(grid, rowIndex){
            	var record = grid.getStore().getAt(rowIndex);
            	visitForm.loadRecord(record);
            	visitWindow.show();
            }
    	}]
    }];
	
	var MemberCombo3 = Ext.create('Ext.form.ComboBox', {
        store: MemberStore,
        allowBlank: false,
        valueField: 'memId',
        displayField: 'mobile',
        typeAhead: false,
        hideTrigger:true,
        width: 260,
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
	
	var tbar3 = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [MemberCombo3,{
        	xtype: 'button',
        	text: ' 查 询 ',
        	iconCls: 'preview', 
        	handler: function(){
        		store3.load({
        			params : getParams3()
        		});
            }
        }]
    });
	
	var pagesizeCombo3 = new Ext.form.ComboBox({
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

	var number3 = parseInt(pagesizeCombo3.getValue());
	
	pagesizeCombo3.on("select", function(comboBox) {
		bbar3.pageSize = parseInt(comboBox.getValue());
		number3 = parseInt(comboBox.getValue());
		store3.pageSize = parseInt(comboBox.getValue());
		var params = getParams3();
		params.start = 0;
		params.limit = bbar3.pageSize;
		store3.reload({
			params : params
		});
	});
	
	var bbar3 = new Ext.PagingToolbar({
		pageSize : number3,
		store : store3,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesizeCombo3 ]
	});
	
	var visitGrid3 = new Ext.grid.GridPanel({
		title: '年检到期',
		iconCls: 'tag_blue',
		columns : columns3,
		store : store3,
		region : 'center',
		autoScroll : true,
		columnLines : true,
		tbar : tbar3, 
		bbar : bbar3,
		stripeRows:true,
		viewConfig : {
			enableTextSelection:true
		},
		loadMask : {
			msg : '正在加载,请稍等...'
		}
	});
	
	////////////////////////////////////////33333333333333333333333
	////////////////////////////////////////44444444444444444444444
	
	var store4 = new Ext.data.Store({
		model: VisitModel4,
		proxy: {
		      type: 'ajax',
			  url : '/member/queryVisitListByPage.atc',
			  reader : {
			      root: 'visitList',
			      totalProperty: 'totalCount'
			  }
		}
    });
	
	var columns4 = [rowNum4, {
        text     : 'ID',
        hidden	 : true,
        dataIndex: 'id'
    }, {
    	text     : 'ID',
    	hidden	 : true,
    	dataIndex: 'typeId'
    }, {
        text     : '会员ID',
        hidden	 : true,
        dataIndex: 'memId'
    }, {
    	text     : '车辆ID',
    	hidden	 : true,
    	dataIndex: 'carId'
    }, {
    	text     : '会员',
    	width	 : 160,
    	dataIndex: 'name'
    }, {
    	text     : '电话',
    	sortable : true,
    	width	 : 120,
    	dataIndex: 'mobile'
//    }, {
//    	text     : '车牌号码',
//    	width	 : 100,
//    	dataIndex: 'carNumber'
    }, {
    	text     : '最后消费日期',
    	sortable : true,
    	width	 : 140,
    	dataIndex: 'lastSale'
    }, {
    	text     : '回访结果',
    	width	 : 220,
    	dataIndex: 'result'
    }, {
    	text     : '回访账号',
    	sortable : true,
    	width	 : 140,
    	dataIndex: 'visitor',
    }, {
    	text     : '回访时间',
    	sortable : true,
    	width	 : 160,
    	dataIndex: 'visitTime',
    }, {
    	header : '回访',xtype: 'actioncolumn',menuDisabled: true, sortable: false,width: 60, align: 'center',
    	items: [{
    		iconCls: 'phone_sound',
    		tooltip: '回访',
    		scope: this,
            handler: function(grid, rowIndex){
            	var record = grid.getStore().getAt(rowIndex);
            	visitForm.loadRecord(record);
            	visitWindow.show();
            }
    	}]
    }];
	
	var MemberCombo4 = Ext.create('Ext.form.ComboBox', {
        store: MemberStore,
        allowBlank: false,
        valueField: 'memId',
        displayField: 'mobile',
        typeAhead: false,
        hideTrigger:true,
        width: 260,
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
	
	var tbar4 = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [MemberCombo4,{
        	xtype: 'button',
        	text: ' 查 询 ',
        	iconCls: 'preview', 
        	handler: function(){
        		store4.load({
        			params : getParams4()
        		});
            }
        }]
    });
	
	var pagesizeCombo4 = new Ext.form.ComboBox({
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

	var number4 = parseInt(pagesizeCombo4.getValue());
	
	pagesizeCombo4.on("select", function(comboBox) {
		bbar4.pageSize = parseInt(comboBox.getValue());
		number4 = parseInt(comboBox.getValue());
		store4.pageSize = parseInt(comboBox.getValue());
		var params = getParams4();
		params.start = 0;
		params.limit = bbar4.pageSize;
		store4.reload({
			params : params
		});
	});
	
	var bbar4 = new Ext.PagingToolbar({
		pageSize : number4,
		store : store4,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesizeCombo4 ]
	});
	
	var visitGrid4 = new Ext.grid.GridPanel({
		title: '客户流失',
		iconCls: 'tag_blue',
		columns : columns4,
		store : store4,
		region : 'center',
		autoScroll : true,
		columnLines : true,
		tbar : tbar4, 
		bbar : bbar4,
		stripeRows:true,
		viewConfig : {
			enableTextSelection:true
		},
		loadMask : {
			msg : '正在加载,请稍等...'
		}
	});
	
	////////////////////////////////////////44444444444444444444444
	////////////////////////////////////////55555555555555555555555
	
	var store5 = new Ext.data.Store({
		model: VisitModel5,
		proxy: {
		      type: 'ajax',
			  url : '/member/queryVisitListByPage.atc',
			  reader : {
			      root: 'visitList',
			      totalProperty: 'totalCount'
			  }
		}
    });
	
	var columns5 = [rowNum5, {
        text     : 'ID',
        hidden	 : true,
        dataIndex: 'id'
    }, {
    	text     : 'ID',
    	hidden	 : true,
    	dataIndex: 'typeId'
    }, {
        text     : '会员ID',
        hidden	 : true,
        dataIndex: 'memId'
    }, {
    	text     : '车辆ID',
    	hidden	 : true,
    	dataIndex: 'carId'
    }, {
    	text     : '会员',
    	width	 : 160,
    	dataIndex: 'name'
    }, {
    	text     : '电话',
    	sortable : true,
    	width	 : 120,
    	dataIndex: 'mobile'
    }, {
    	text     : '名称',
    	width	 : 180,
    	dataIndex: 'goodsName'
    }, {
    	text     : '到期日期',
    	sortable : true,
    	width	 : 140,
    	dataIndex: 'endDate'
    }, {
    	text     : '回访结果',
    	width	 : 220,
    	dataIndex: 'result'
    }, {
    	text     : '回访账号',
    	sortable : true,
    	width	 : 140,
    	dataIndex: 'visitor',
    }, {
    	text     : '回访时间',
    	sortable : true,
    	width	 : 160,
    	dataIndex: 'visitTime',
    }, {
    	header : '回访',xtype: 'actioncolumn',menuDisabled: true, sortable: false,width: 60, align: 'center',
    	items: [{
    		iconCls: 'phone_sound',
    		tooltip: '回访',
    		scope: this,
            handler: function(grid, rowIndex){
            	var record = grid.getStore().getAt(rowIndex);
            	visitForm.loadRecord(record);
            	visitWindow.show();
            }
    	}]
    }];
	
	var MemberCombo5 = Ext.create('Ext.form.ComboBox', {
        store: MemberStore,
        allowBlank: false,
        valueField: 'memId',
        displayField: 'mobile',
        typeAhead: false,
        hideTrigger:true,
        width: 260,
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
	
	var tbar5 = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [MemberCombo5,{
        	xtype: 'button',
        	text: ' 查 询 ',
        	iconCls: 'preview', 
        	handler: function(){
        		store5.load({
        			params : getParams5()
        		});
            }
        }]
    });
	
	var pagesizeCombo5 = new Ext.form.ComboBox({
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

	var number5 = parseInt(pagesizeCombo5.getValue());
	
	pagesizeCombo5.on("select", function(comboBox) {
		bbar5.pageSize = parseInt(comboBox.getValue());
		number5 = parseInt(comboBox.getValue());
		store5.pageSize = parseInt(comboBox.getValue());
		var params = getParams5();
		params.start = 0;
		params.limit = bbar5.pageSize;
		store5.reload({
			params : params
		});
	});
	
	var bbar5 = new Ext.PagingToolbar({
		pageSize : number5,
		store : store5,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesizeCombo5 ]
	});
	
	var visitGrid5 = new Ext.grid.GridPanel({
		title: '服务到期',
		iconCls: 'tag_blue',
		columns : columns5,
		store : store5,
		region : 'center',
		autoScroll : true,
		columnLines : true,
		tbar : tbar5, 
		bbar : bbar5,
		stripeRows:true,
		viewConfig : {
			enableTextSelection:true
		},
		loadMask : {
			msg : '正在加载,请稍等...'
		}
	});
	
	////////////////////////////////////////55555555555555555555555	
	////////////////////////////////////////66666666666666666666666
	
	var store6 = new Ext.data.Store({
		model: VisitModel6,
		proxy: {
		      type: 'ajax',
			  url : '/member/queryVisitListByPage.atc',
			  reader : {
			      root: 'visitList',
			      totalProperty: 'totalCount'
			  }
		}
    });
	
	var columns6 = [rowNum6, {
        text     : 'ID',
        hidden	 : true,
        dataIndex: 'id'
    }, {
    	text     : 'ID',
    	hidden	 : true,
    	dataIndex: 'typeId'
    }, {
        text     : '会员ID',
        hidden	 : true,
        dataIndex: 'memId'
    }, {
    	text     : '车辆ID',
    	hidden	 : true,
    	dataIndex: 'carId'
    }, {
    	text     : '会员',
    	width	 : 160,
    	dataIndex: 'name'
    }, {
    	text     : '电话',
    	sortable : true,
    	width	 : 120,
    	dataIndex: 'mobile'
    }, {
    	text     : '余额',
    	width	 : 120,
    	dataIndex: 'balance',
    	renderer: Ext.util.Format.numberRenderer("0.00")
//    }, {
//    	text     : '到期日期',
//    	sortable : true,
//    	width	 : 140,
//    	dataIndex: 'endDate'
    }, {
    	text     : '回访结果',
    	width	 : 220,
    	dataIndex: 'result'
    }, {
    	text     : '回访账号',
    	sortable : true,
    	width	 : 140,
    	dataIndex: 'visitor',
    }, {
    	text     : '回访时间',
    	sortable : true,
    	width	 : 160,
    	dataIndex: 'visitTime',
    }, {
    	header : '回访',xtype: 'actioncolumn',menuDisabled: true, sortable: false,width: 60, align: 'center',
    	items: [{
    		iconCls: 'phone_sound',
    		tooltip: '回访',
    		scope: this,
            handler: function(grid, rowIndex){
            	var record = grid.getStore().getAt(rowIndex);
            	visitForm.loadRecord(record);
            	visitWindow.show();
            }
    	}]
    }];
	
	var MemberCombo6 = Ext.create('Ext.form.ComboBox', {
        store: MemberStore,
        allowBlank: false,
        valueField: 'memId',
        displayField: 'mobile',
        typeAhead: false,
        hideTrigger:true,
        width: 260,
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
	
	var tbar6 = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [MemberCombo6,{
        	xtype: 'button',
        	text: ' 查 询 ',
        	iconCls: 'preview', 
        	handler: function(){
        		store6.load({
        			params : getParams6()
        		});
            }
        }]
    });
	
	var pagesizeCombo6 = new Ext.form.ComboBox({
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

	var number6 = parseInt(pagesizeCombo6.getValue());
	
	pagesizeCombo6.on("select", function(comboBox) {
		bbar6.pageSize = parseInt(comboBox.getValue());
		number6 = parseInt(comboBox.getValue());
		store6.pageSize = parseInt(comboBox.getValue());
		var params = getParams6();
		params.start = 0;
		params.limit = bbar6.pageSize;
		store6.reload({
			params : params
		});
	});
	
	var bbar6 = new Ext.PagingToolbar({
		pageSize : number6,
		store : store6,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesizeCombo6 ]
	});
	
	var visitGrid6 = new Ext.grid.GridPanel({
		title: '余额不足',
		iconCls: 'tag_blue',
		columns : columns6,
		store : store6,
		region : 'center',
		autoScroll : true,
		columnLines : true,
		tbar : tbar6, 
		bbar : bbar6,
		stripeRows:true,
		viewConfig : {
			enableTextSelection:true
		},
		loadMask : {
			msg : '正在加载,请稍等...'
		}
	});
	
	////////////////////////////////////////66666666666666666666666
	
	var mainTab = Ext.create('Ext.TabPanel', {
		region : 'center',
		margins : 0,
		deferredRender : false,
		activeTab : 0,
		items : [visitGrid1, visitGrid2, visitGrid3, visitGrid4, visitGrid5, visitGrid6]
	});
	
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [mainTab]
	});
	
	
	var visitForm = new Ext.form.FormPanel({
		id : 'visitForm',
		layout: 'anchor',
		defaults: {
            anchor: '100%',
            xtype: 'fieldcontainer',
	        layout: 'hbox'
        },
		bodyPadding: '5 10 0 0',
		border : false,
        items : [{
				 defaults: {flex: 1, xtype:'hiddenfield'},
				 items: [
				         {id:'id', name: 'visit.id'},
				         {id:'typeId', name: 'visit.typeId'}
				 ]
		     },{
		    	 defaults: {flex: 1, xtype:'textarea', labelWidth:70, labelAlign:'right'},
				 items: [
				        {id: 'result', name: 'visit.result', rows: 3, fieldLabel: '回访结果'}
				 ]
			}
		],
		buttons:[{
			text: ' 确 定 ',
        	iconCls: 'accept',
			handler : function() {
				visitForm.form.submit({
					waitTitle : '提示',
					method : 'POST',
					url: '/member/updateVisitRecord.atc',
					waitMsg : '正在处理数据,请稍候...',
					success : function(form, action) {
						var typeId = Ext.getCmp("typeId").getValue();
						if(typeId == 1)
							store1.reload();
						else if(typeId == 2)
							store2.reload();
						else if(typeId == 3)
							store3.reload();
						else if(typeId == 4)
							store4.reload();
						else if(typeId == 5)
							store5.reload();
						else
							store6.reload();
						visitForm.getForm().reset();
						visitWindow.hide();
						Ext.example.msg("提示", "回访成功！");
					},
					failure : function(form, action) {
						var msg = action.result.msg;
						Ext.MessageBox.show({
 				           	title: '提示',
 				           	msg: msg,
 				           	buttons: Ext.MessageBox.OK,
 				           	icon: Ext.MessageBox.ERROR
                        });
					}
				});
        		visitWindow.hide();
			}
		}]
	});
	
	var visitWindow = new Ext.Window({
		title : '回访', 
		layout : 'fit',
		width : 450,
		height : 150,
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
		items : [visitForm]
	});
	
	store1.on('beforeload', function() {
		Ext.apply(store1.proxy.extraParams, getParams1());
	});
	store2.on('beforeload', function() {
		Ext.apply(store2.proxy.extraParams, getParams2());
	});
	store3.on('beforeload', function() {
		Ext.apply(store3.proxy.extraParams, getParams3());
	});
	store4.on('beforeload', function() {
		Ext.apply(store4.proxy.extraParams, getParams4());
	});
	store5.on('beforeload', function() {
		Ext.apply(store5.proxy.extraParams, getParams5());
	});
	store6.on('beforeload', function() {
		Ext.apply(store6.proxy.extraParams, getParams6());
	});
	
	function getParams1(){
		return {
			'visit.typeId': 1,
			'visit.memId' : MemberCombo1.getValue()
		};
	}
	function getParams2(){
		return {
			'visit.typeId': 2,
			'visit.memId' : MemberCombo2.getValue()
		};
	}
	function getParams3(){
		return {
			'visit.typeId': 3,
			'visit.memId' : MemberCombo3.getValue()
		};
	}
	function getParams4(){
		return {
			'visit.typeId': 4,
			'visit.memId' : MemberCombo4.getValue()
		};
	}
	function getParams5(){
		return {
			'visit.typeId': 5,
			'visit.memId' : MemberCombo5.getValue()
		};
	}
	function getParams6(){
		return {
			'visit.typeId': 6,
			'visit.memId' : MemberCombo6.getValue()
		};
	}
	
	store1.load({
		params: {
			start: 0,
			limit: bbar1.pageSize
		}
	});
	
});