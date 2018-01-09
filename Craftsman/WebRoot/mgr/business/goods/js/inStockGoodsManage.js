Ext.onReady(function() {
	
	var inStockRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	Ext.define('InStockModel', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'goodsId', type: 'string'},
	        {name: 'goodsName', type: 'string'},
	        {name: 'number', type: 'string'},
	        {name: 'inPrice', type: 'float'},
	        {name: 'supplier', type: 'string'},
	        {name: 'settlement', type: 'int'},
	        {name: 'settdate', type: 'string'},
	        {name: 'purchdate', type: 'string'}
	    ]
	});
	
	Ext.define('GoodsMaterial', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'id', type: 'string'},
	        {name: 'code', type: 'string'},
	        {name: 'name', type: 'string'},
	        {name: 'typeId', type: 'int'},
	        {name: 'typeName', type: 'string'},
	        {name: 'price', type: 'float'},
	        {name: 'inPrice', type: 'float'},
	        {name: 'spec', type: 'string'},
	        {name: 'color', type: 'string'},
	        {name: 'size', type: 'string'},
	        {name: 'unit', type: 'string'},
	        {name: 'suitModel', type: 'string'},
	        {name: 'remark', type: 'string'}
	    ]
	});
	
	var supplierStore = Ext.create('Ext.data.Store', {
		autoLoad: true,
		fields: ['id', 'name'],
		proxy: {
			type: 'ajax',
			url : '/shop/querySupplierByKeyword.atc',
			reader : {
				root: 'supplierList'
			}
		}
	});
	
	var supplierCombo = Ext.create('Ext.form.ComboBox', {
		id: 'supplierCombo',
		triggerAction : 'all',
		mode : 'local',
		store: supplierStore,
		valueField : 'id',
		displayField : 'name',
		typeAhead: false,
		hideLabel: true,
		queryParam: 'keyword',	//搜索参数字段
		minChars: 0,
		editable : true,
		listeners: {
        	select: function(cb, records) {
        		var rowRecord = inStockGrid.getSelectionModel().getLastSelected();
        		inStockCellEditing.startEdit(rowRecord, 5);
            }
        }
	});
	
	var supplierCombo2 = Ext.create('Ext.form.ComboBox', {
		triggerAction : 'all',
		mode : 'local',
		store: supplierStore,
		valueField : 'id',
		displayField : 'name',
		emptyText: '供应商',
		typeAhead: false,
		hideLabel: true,
		queryParam: 'keyword',	//搜索参数字段
		minChars: 0,
		editable : true
	});
	
//	var settlementCombo = Ext.create('Ext.form.ComboBox', {
//		name : 'settlement',
//		triggerAction : 'all',
//		mode : 'local',
//		store : new Ext.data.ArrayStore({
//			fields : [ 'value', 'text' ],
//			data : [ [ 1, '是' ], [ 0, '否' ] ]
//		}),
//		valueField : 'value',
//		displayField : 'text',
//		value : 1,
//		editable : false,
//		listeners: {
//        	select: function(cb, records){
//        		var rowRecord = inStockGrid.getSelectionModel().getLastSelected();
//        		inStockCellEditing.startEdit(rowRecord, 6);
//            }
//        }
//	});
//	
//	var settlementCombo2 = Ext.create('Ext.form.ComboBox', {
//		name : 'settlement',
//		triggerAction : 'all',
//		mode : 'local',
//		width: 100,
//		store : new Ext.data.ArrayStore({
//			fields : [ 'value', 'text' ],
//			data : [ [ 1, '是' ], [ 0, '否' ] ]
//		}),
//		valueField : 'value',
//		displayField : 'text',
//		emptyText: '是否结算',
//		editable : true
//	});
	
	var inStockStore = Ext.create('Ext.data.Store', {
		  fields: ['id', 'goodsId', 'goodsName', 'number', 'inPrice', 'supplier', 'settlement', 'settdate', 'purchdate']
	});
	
	var GoodsMaterialStore = Ext.create('Ext.data.Store', {
		autoDestroy: true,
		model: GoodsMaterial,
		proxy:{
            type: 'ajax',
            url : '/goods/queryGoodsMaterialByKeyword.atc',
            reader : {
                root: 'materialList'
            }
        }
	});
	
//	var GoodsStore = Ext.create('Ext.data.Store', {
//		model: GoodsMaterial,
//		proxy:{
//            type: 'ajax',
//            url : '/goods/queryGoodsMaterialByDept.atc',
//            reader : {
//                root: 'materialList'
//            }
//        }
//	});
	
//	var GoodsMaterialCombo = Ext.create('Ext.form.ComboBox', {
//		store: GoodsMaterialStore,
//		valueField: 'id',
//		displayField: 'name',
//		typeAhead: false,
//        hideLabel: true,
//        hideTrigger:true,
//        queryParam: 'keyword',	//搜索参数字段
//        minChars: 1,
//        emptyText: '请输入 商品名称/编码',
//        listConfig: {
//            loadingText: '查找中...',
//            emptyText: '<div style="padding: 5px;">没有找到相关商品！</div>',
//            getInnerTpl: function() {
//                return '<div class="search">{typeName} / {name} / {code} / {price} / {spec}</div>';
//            }
//        },
//        listeners: {
//        	select: function(cb, records){
//        		var rec = records[0];
//        		var rowRecord = inStockGrid.getSelectionModel().getLastSelected();
//        		rowRecord.data.goodsName = rec.data.name;
//        		inStockCellEditing.startEdit(rowRecord, 2);
//            }
//        }
//	});
//	
//	var GoodsMaterialRenderer = function(value, cellmeta, record) {
//        var index = GoodsStore.find("id", value);
//        var record = GoodsStore.getAt(index);
//        var displayText = "";
//        if (record == null)
//        	displayText = value;
//        else
//        	displayText = record.data.name + "/" + record.data.code;
//        return displayText;
//    };
    
    var supplierRenderer = function(value, cellmeta, record) {
        var index = supplierStore.find("id", value);
        var record = supplierStore.getAt(index);
        var displayText = "";
        if (record == null)
        	displayText = value;
        else
        	displayText = record.data.name;
        return displayText;
    };
    
	var inStockColumns= [inStockRowNum,
  	            {header : 'ID',dataIndex: 'id', width: 60, hidden: true},
  	            {header : '商品',dataIndex: 'goodsId', hidden: true},
  	            {header : '名称',dataIndex: 'goodsName', width: 400},
  	            {header : '数量',dataIndex: 'number', width: 120, editor: {xtype:'numberfield', minValue: 1, listeners: {
  	            	specialkey : function(field, e) {
						if (e.getKey() == Ext.EventObject.ENTER) {
							var rowRecord = inStockGrid.getSelectionModel().getLastSelected();
	      	          		inStockCellEditing.startEdit(rowRecord, 3);
						}
					}
  	            }}, renderer: Ext.util.Format.numberRenderer("0.00")},
  	            {header : '进货价',dataIndex: 'inPrice', width: 120, editor: {xtype:'numberfield', listeners: {
  	            	specialkey : function(field, e) {
						if (e.getKey() == Ext.EventObject.ENTER) {
							var rowRecord = inStockGrid.getSelectionModel().getLastSelected();
	      	          		inStockCellEditing.startEdit(rowRecord, 4);
						}
					}
  	            }}, 
  	            xtype: 'numbercolumn', renderer: Ext.util.Format.numberRenderer("0.00")},
  	            {header : '供应商',dataIndex: 'supplier', width: 200, editor: supplierCombo, renderer: supplierRenderer},
//  	            {header : '是否结算',dataIndex: 'settlement', width: 100, editor: settlementCombo, renderer: function(v){return v == 1 ? '是' : '否'}},
//  	            {header : '结算时间',dataIndex: 'settdate', width: 120, editor:{xtype:'datefield', format: 'Y/m/d', listeners: {
//      	          	select: function(cb, records){
//      	          		var rowRecord = inStockGrid.getSelectionModel().getLastSelected();
//      	          		inStockCellEditing.startEdit(rowRecord, 7);
//      	            }
//  	            }}, renderer: Ext.util.Format.dateRenderer('Y/m/d')},
//  	            {header : '采购时间',dataIndex: 'purchdate', width: 120, editor:{xtype:'datefield', format: 'Y/m/d',listeners: {
//      	          	select: function(cb, records){
//      	          		insertInStockRow();
//      	            }
//  	            }}, renderer: Ext.util.Format.dateRenderer('Y/m/d')},
  	            {xtype: 'actioncolumn',header : '操作', width: 50,align:'center',sortable: false, menuDisabled: true,
      				items: [{
      				    iconCls: 'delete',
      				    tooltip: '删除',
      				    scope: this,
      			        handler: function(grid, rowIndex){
      			        	inStockGrid.getStore().removeAt(rowIndex);
      			        }
      			    }]
  	            }
  	  	];
	
	var importForm = new Ext.form.FormPanel({
		id : 'importForm',
		name : 'importForm',
		defaultType : 'textfield',
		labelAlign : 'right',
		labelWidth : 99,
		padding: '5 5 5 5',
		frame : false,
		items : [{
				fieldLabel : '请选择导入文件',
				id : 'upload',
				name : 'upload',
				xtype:'filefield',
				buttonText:'浏览...',
				allowBlank : true,
				anchor : '99%'
			},{
				xtype: 'displayfield',
				fieldLabel : '下载导入模板',
				allowBlank : true,
				anchor : '99%',
				value:'<a href="/mgr/common/template/商品入库模板.xls">商品入库模板.xls</a>'
			}]
	});
	
	var importWindow = new Ext.Window({
		layout : 'fit',
		width : 380,
		height : 140,
		resizable : false,
		draggable : true,
		closeAction : 'hide',
		title : '导入Excel',
		modal : false,
		collapsible : true,
		titleCollapse : true,
		maximizable : false,
		buttonAlign : 'right',
		border : false,
		animCollapse : true,
		constrain : true,
		items : [importForm],
		buttons : [{
			text : '导入',
			iconCls : 'page_white_excel',
			handler : function() {
				var upload = Ext.getCmp('upload').getValue();
				if (Ext.isEmpty(upload)) {
					Ext.MessageBox.show({
				           title: '提示',
				           msg: '请先选择您要导入的Excel文件...',
				           buttons: Ext.MessageBox.OK,
				           icon: Ext.MessageBox.INFO
					});
					return;
				}
				Ext.MessageBox.show({
					title: '请稍等',
					msg: '入库信息导入中 ...',
					width: 200,
					progress: true,
					closable: false
		        });
				importForm.form.submit({
					url : '/goods/analysisGoodsInStock.atc',
					waitTitle : '提示',
					method : 'POST',
					waitMsg : '正在处理数据,请稍候...',
					success : function(form, action) {
						Ext.MessageBox.hide();
						importWindow.hide();
						if(Ext.isArray(action.result.inStockList)){
							inStockStore.loadData(action.result.inStockList);
						}
					},
					failure : function(form, action) {
						Ext.MessageBox.hide();
						var msg = action.result.msg;
                        Ext.MessageBox.show({
 				           title: '提示',
 				           msg: msg,
 				           buttons: Ext.MessageBox.OK,
 				           icon: Ext.MessageBox.ERROR
                        });
					}
				});
			}
		}, {
			text : '关闭',
			iconCls : 'stop',
			handler : function() {
				importWindow.hide();
			}
		}]
	});
	
	var inStockCellEditing = new Ext.grid.plugin.CellEditing({
        clicksToEdit: 1
    });
	
	var inStockTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	text: '添加商品',
        	iconCls: 'add',
        	hidden : !parent.haveActionMenu('Add'),
        	handler: function() {
//        		insertInStockRow();
        		choiceGoodsWindow.show();
        	}
        },{
        	xtype: 'button',
        	text: '全部删除',
        	iconCls: 'delete',
        	handler: function() {
        		inStockStore.removeAll();
        	}
        },{
        	xtype: 'button',
        	id: 'delete',
        	text: '全部保存',
        	iconCls: 'accept', 
        	hidden : !parent.haveActionMenu('Save'),
        	handler: function(){
        		saveInStock();
            }
        },'->',{
        	xtype: 'button',
        	id: 'import',
        	text: '批量导入',
        	hidden : !parent.haveActionMenu('Import'),
        	iconCls: 'page_excel',
        	handler: function(){
        		importWindow.show();
        	}
        }]
    });
	
	var inStockGrid = new Ext.grid.GridPanel({
		title: '商品入库',
		columns : inStockColumns,
		store : inStockStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		tbar : inStockTbar, 
		stripeRows:true,
		plugins: [inStockCellEditing],
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
		items : [inStockGrid]
	});
	
//	GoodsStore.load();
	
	///////////////////////////////////////////选择商品
	var storeRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 30
	});
	
	var ProductTypeStore = Ext.create('Ext.data.Store', {
		fields: ['id', 'typeName'],
        proxy: {
            type: 'ajax',
            url : '/goods/queryMaterialTypeByDeptCode.atc',
            reader : {
                root: 'materialTypeList'
            }
        }
	});
	ProductTypeStore.load();
	
	var ProductTypeCombo = Ext.create('Ext.form.field.ComboBox', {
		store: ProductTypeStore,
		valueField: 'id',
		displayField: 'typeName',
		width: 120,
        editable : true,
        emptyText:'商品类型', 
        triggerAction: 'all',
        lastQuery: ''
	});
	
	var storeStore = Ext.create('Ext.data.Store', {
		pageSize: 20,
		fields: ['id', 'deptCode', 'code', 'name', 'typeName', 'price', 'inPrice', 'spec', 'color', 'size', 'unit', 'suitModel'],
		proxy: {
			type: 'ajax',
			url : '/goods/queryGoodsMaterialByKeyword.atc',
			reader : {
				root: 'materialList',
				totalProperty: 'totalCount'
			}
		}
	});
	
	var storeColumns= [storeRowNum,
       {header : 'ID',dataIndex: 'id', width: 60, hidden: true},
       {header : '编码',dataIndex: 'code', width: 100},
       {header : '名称',dataIndex: 'name', width: 180},
       {header : '类型',dataIndex: 'typeName', width: 120},
       {header : '零售价',dataIndex: 'price', width: 80, xtype: 'numbercolumn', format:'0.00'},
       {header : '进货价',dataIndex: 'inPrice', width: 80, xtype: 'numbercolumn', format:'0.00'},
       {header : '规格',dataIndex: 'spec', width: 80, hidden: true},
       {header : '颜色',dataIndex: 'color', width: 80, hidden: true},
       {header : '尺寸',dataIndex: 'size', width: 80, hidden: true},
       {header : '单位',dataIndex: 'unit', width: 60},
       {header : '适用车型',dataIndex: 'suitModel', width: 150}
	];
	
	var storeTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	text: '选择',
        	iconCls: 'accept',
        	handler: function() {
        		choiceInStoreGoods();
            }
        },ProductTypeCombo,{
        	xtype: 'textfield',
        	id: 'keyword',
        	emptyText : '名称/编码/拼音',
        },{
        	xtype: 'button',
        	text: ' 查 询 ',
        	iconCls: 'preview', 
        	handler: function(){
        		storeStore.load({
        			params : storeGetParams()
        		});
            }
        },'->',supplierCombo2]
	});
	
	var storeSelModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1
	});
	
	var storeGrid = new Ext.grid.GridPanel({
		region : 'center',
		columns : storeColumns,
		store : storeStore, 
		autoScroll : true,
		columnLines : true,
		frame : false,
		selModel : storeSelModel,
		tbar : storeTbar, 
//		bbar : storeBbar,
		stripeRows:true,
		forceFit: true,
		viewConfig : {
			enableTextSelection: true
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	var choiceGoodsWindow = new Ext.Window({
		title : '选择商品',
		layout : 'border',
		width : 930,
		height : document.body.clientHeight - 100,
		resizable : true,
		draggable : true,
		closeAction : 'hide',
		iconCls : 'application_side_tree',
		modal : true,
		collapsible : true,
		maximizable : true,
		constrain : true,
		border : false,
		items: [storeGrid]
	});
	
	choiceGoodsWindow.on('show', function() {
		storeStore.load();
	});
	
	storeStore.on('beforeload', function() {
		Ext.apply(storeStore.proxy.extraParams, storeGetParams());
	});
	
	function storeGetParams(){
		return {
			keyword: Ext.getCmp("keyword").getValue(),
			typeId: ProductTypeCombo.getValue()
		};
	}
	
	function choiceInStoreGoods(){
		var storeSelect = storeSelModel.getSelection();
        var supplier = supplierCombo2.getValue();
//		var settlement = settlementCombo2.getValue();
        Ext.each(storeSelect, function(item){
        	if(!Ext.isEmpty(item.data.id)){
        		var record = new InStockModel({
        			goodsId: item.data.id,
        			goodsName: item.data.name + "/" + item.data.code,
        			number: 1,
        			inPrice: item.data.inPrice,
        			supplier: Ext.isEmpty(supplier) ? '' : supplier,
        			settlement: 0,
        			settdate:'',
        			purchdate:'',
        		});
        		inStockStore.insert(inStockStore.getCount(), record);
        	}
        });  
        choiceGoodsWindow.hide();
	}
	
//	function insertInStockRow(){
//		var rowRecord = inStockGrid.getSelectionModel().getLastSelected();
//       	if(!Ext.isEmpty(rowRecord) && Ext.isEmpty(rowRecord.data.supplier)){
//        	Ext.MessageBox.show({
//	           title: '提示',
//	           msg: '供应商不能为空！',
//	           buttons: Ext.MessageBox.OK,
//	           icon: Ext.MessageBox.INFO
//        	});
//        	return;
//      	}
//		var record = new InStockModel({
//			goodsId: '',
//			goodsName: '',
//			number:'',
//			inPrice:'',
//			position:'',
//			supplier: !Ext.isEmpty(rowRecord) ? rowRecord.data.supplier : '',
//			settlement: 0,
//			settdate:'',
//			purchdate:'',
//		});
//		inStockStore.insert(inStockStore.getCount(), record);
//		inStockCellEditing.startEditByPosition({
//			row: inStockStore.getCount() - 1,
//			column: 1
//		});
//	}
	
	function batchSetting() {
		var supplier = supplierCombo2.getValue();
//		var settlement = settlementCombo2.getValue();
		var storeSelect = storeSelModel.getSelection();
		Ext.each(storeSelect, function(inStock){
			if(!Ext.isEmpty(supplier))
				inStock.data.supplier = supplier;
//			if(!Ext.isEmpty(settlement))
//				inStock.data.settlement = settlement;
			inStock.commit();
        });
	}
	
	function saveInStock() {
		var jsonArray = [];
        var eMsg = "";
        Ext.each(inStockStore.data.items, function(item){
        	if(!Ext.isEmpty(item.data.goodsId)){
        		if(!Ext.isEmpty(item.data.settdate))
        			item.data.settdate = Ext.Date.format(new Date(item.data.settdate),'Y/m/d');
        		if(!Ext.isEmpty(item.data.purchdate))
        			item.data.purchdate = Ext.Date.format(new Date(item.data.purchdate),'Y/m/d');
//        		if(Ext.isEmpty(item.data.settlement))
//        			item.data.settlement = 0;
        		jsonArray.push(item.data);
        	}
        	if(Ext.isEmpty(item.data.supplier)){
        		eMsg += item.data.goodsName + " 供应商不能为空！";
        	}
        	if(Ext.isEmpty(item.data.number) || Number(item.data.number) <= 0){
        		eMsg += item.data.goodsName + " 进货数量不能为空！";
        	}
        	if(Ext.isEmpty(item.data.inPrice) || Number(item.data.inPrice) == 0){
        		eMsg += item.data.goodsName + " 进货价不能为0！";
        	}
        });  
        if(!Ext.isEmpty(eMsg)){
        	Ext.MessageBox.show({
 	           title: '提示',
 	           msg: eMsg + "",
 	           buttons: Ext.MessageBox.OK,
 	           icon: Ext.MessageBox.INFO
         	});
         	return;
        }
        Ext.MessageBox.show({
			title: '请稍等',
			msg: '正在生成入库记录 ...',
			width: 200,
			progress: true,
			closable: false
        });
        Ext.Ajax.request({
            method:'POST',
            url: '/goods/saveGoodsInStock.atc',
            params : {
				jsonStr : Ext.encode(jsonArray)
			},
			success : function(resp, opts) {
				Ext.MessageBox.hide();
				var result = Ext.decode(resp.responseText);
				if(result.success){
					if(result.inNumber && !Ext.isEmpty(result.inNumber)){
						Ext.MessageBox.show({
				            title: '结果',
				            msg: result.msg  + "",
				            buttons: Ext.MessageBox.YESNO,
				            closable: false,
				            buttonText:{
				                yes: "打印入库单", 
				                no: "继续操作" 
				            },
				            fn: function(btn) {
				            	if(btn == 'yes'){
				            		inStockStore.removeAll();
				            		//打印订单
				            		parent.global.openInStockPrintTab(result.inNumber);
				        		} else if(btn == 'no'){
				        			inStockStore.removeAll();
				        		}
				            }
				        });
					} else {
						Ext.example.msg('提示', result.msg);
						inStockStore.removeAll();
					}
				} else 
					Ext.MessageBox.show({
			           title: '提示',
			           msg: result.msg,
			           buttons: Ext.MessageBox.OK,
			           icon: Ext.MessageBox.ERROR
	                });
			},
			failure : function(resp, opts) {
				Ext.MessageBox.hide();
				var result = Ext.decode(resp.responseText);
                Ext.Msg.alert('提示', result.msg);
			}
        });
	}
	
});