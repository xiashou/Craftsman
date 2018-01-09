Ext.onReady(function() {
	
	var moveRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	Ext.define('MoveStore', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'goodsId', type: 'string'},
	        {name: 'goodsName', type: 'int'},
	        {name: 'number', type: 'float'},
	        {name: 'inPrice', type: 'float'}
	    ]
	});
	
	var StockStore = Ext.create('Ext.data.Store', {
		fields: ['id', 'code', 'name', 'number', 'inPrice'],
		proxy: {
            type: 'ajax',
            url : '/goods/queryGoodsMaterialStockByKeyword.atc',
            reader : {
                root: 'materialList',
                totalProperty: 'totalCount'
            }
        }
    });
	
	var StockCombo = Ext.create('Ext.form.ComboBox', {
        store: StockStore,
        labelWidth : 40,
        valueField: 'id',
        displayField: 'name',
        typeAhead: false,
        hideTrigger:true,
        width: 300,
        queryParam: 'keyword',	//搜索参数字段
        minChars: 1,
        pageSize: 10,
        listConfig: {
            loadingText: '查找中...',
            emptyText: '<div style="padding: 5px;">没有找到相关商品！</div>',
            getInnerTpl: function() {
                return '<div class="search">{code}/{name}/{price}/{color}/ 库存:{number}件</div>';
            }
        },
        listeners : {
        	select: function(cb, records) {
            	var record = records[0];
            	var rowRecord = moveGrid.getSelectionModel().getLastSelected();
            	rowRecord.data.inPrice = record.data.inPrice;
            	rowRecord.data.stockNum = record.data.number;
            	rowRecord.data.goodsName = record.data.name;
            	rowRecord.commit();
            	moveCellEditing.startEdit(rowRecord, 3);
			}
		}
	});
	
	var deptStore = Ext.create('Ext.data.Store', {
		autoLoad: true,
		  fields: ['deptCode', 'deptName'],
		  proxy: {
		      type: 'ajax',
			  url : '/sys/querySysDeptByCompany.atc',
			  reader : {
			      root: 'deptList'
			  }
		  }
	});
	
	var moveStore = Ext.create('Ext.data.Store', {
		model: MoveStore,
	});
	
	var GoodsRenderer = function(value, cellmeta, record) {
        var index = StockStore.find("id", value);
        var record = StockStore.getAt(index);
        var displayText = "";
        if (record == null)
        	displayText = value;
        else
        	displayText = record.data.code + "/" + record.data.name + "/" + record.data.number + "件";
        return displayText;
    };
	
	var moveColumns= [moveRowNum,
            {header : 'ID',dataIndex: 'id', width: 60, hidden: true},
            {header : '商品名',dataIndex: 'goodsName', hidden: true},
            {header : '库存数',dataIndex: 'stockNum', hidden: true},
            {header : '商品',dataIndex: 'goodsId', width: 400, editor: StockCombo, renderer: GoodsRenderer},
            {header : '进货价',dataIndex: 'inPrice', width: 120, xtype: 'numbercolumn', format:'0.00'},
            {header : '数量',dataIndex: 'number', width: 120, xtype: 'numbercolumn', format:'0.00',
            	editor: {xtype: 'numberfield', allowBlank: false, minValue: 0, listeners: {
  	            	specialkey : function(field, e) {
						if (e.getKey() == Ext.EventObject.ENTER) {
							var rowRecord = moveGrid.getSelectionModel().getLastSelected();
							if(field.getValue() > rowRecord.data.stockNum){
								Ext.MessageBox.show({
						           title: '提示',
						           msg: "调出数量不能大于库存数！",
						           buttons: Ext.MessageBox.OK,
						           icon: Ext.MessageBox.ERROR
				                });
								moveCellEditing.startEdit(rowRecord, 3);
							} else 
								insertMove();
						}
					}
  	            }}},
            {xtype: 'actioncolumn',header : '操作',width: 50,align:'center',sortable: false,menuDisabled: true,
  				items: [{
  				    iconCls: 'delete',
  				    tooltip: '删除',
  				    scope: this,
  			        handler: function(grid, rowIndex){
  			        	moveGrid.getStore().removeAt(rowIndex);
  			        }
  			    }]
            }
  	];
	
	var shopCombo = Ext.create('Ext.form.ComboBox', {
		id: 'shopCombo',
		anchor: '80%',
		store: deptStore,
		allowBlank: false,
        enforceMaxLength: true,
        fieldLabel: '请选择接收门店',
        labelWidth: 95,
        listConfig: {
            minWidth: null
        },
        valueField: 'deptCode',
        displayField: 'deptName',
        queryMode: 'local',
        editable : false,
        forceSelection: true
	});
	
	var moveCellEditing = new Ext.grid.plugin.CellEditing({
        clicksToEdit: 1
    });
	
	var moveTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	text: '新增一行',
        	iconCls: 'add',
        	handler: function() {
        		insertMove();
        	}
        },'-',shopCombo,{
        	xtype: 'button',
        	id: 'move',
        	text: '确认调拨',
        	iconCls: 'accept', 
        	handler: function(){
        		submitMove();
        	}
        }]
    });
	
	var moveGrid = new Ext.grid.GridPanel({
		title: '商品调拨',
		columns : moveColumns,
		store : moveStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		tbar : moveTbar, 
		stripeRows:true,
		plugins: [moveCellEditing],
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
		items : [moveGrid]
	});
	
	function insertMove(){
		var record = new MoveStore({
			goodsId: '',
			name: '',
			inPrice:'',
			number: 1
		});
		moveStore.insert(moveStore.getCount(), record);
		moveCellEditing.startEditByPosition({
			row: moveStore.getCount() - 1,
			column: 1
		});
	}
	
	function submitMove(){
		if(Ext.isEmpty(shopCombo.getValue())){
			Ext.MessageBox.show({
	        	title: '提示',
	        	msg: "请选择调入门店！",
	        	buttons: Ext.MessageBox.OK,
	        	icon: Ext.MessageBox.ERROR
			});
			return;
		} else if(moveStore.getCount() == 0){
			Ext.MessageBox.show({
	        	title: '提示',
	        	msg: "请添加商品！",
	        	buttons: Ext.MessageBox.OK,
	        	icon: Ext.MessageBox.ERROR
			});
			return;
		} else {
			var goodsArray = [];		//产品
			var total = 0.0;
			Ext.each(moveStore.data.items, function(item){
	        	if(!Ext.isEmpty(item.data.goodsId))
	        		goodsArray.push(item.data);
	        	if(!Ext.isEmpty(item.data.number))
	        		total += item.data.number;
	        });
			Ext.MessageBox.show({
				title: '请稍等',
				msg: '正在生成调拨记录 ...',
				width: 200,
				progress: true,
				closable: false
	        });
			Ext.Ajax.request({
	            method:'POST',
	            url: '/goods/submitGoodsMoveOut.atc',
	            params : {
	            	deptIn: shopCombo.getValue(),
	            	total : total,
	            	goodsString : Ext.encode(goodsArray)
				},
				success : function(resp, opts) {
					var result = Ext.decode(resp.responseText);
					Ext.MessageBox.hide();
					if(result.success){
						moveStore.removeAll();
						Ext.example.msg("提示", "调拨成功！");
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
		}
	}
	
	insertMove();
	
});