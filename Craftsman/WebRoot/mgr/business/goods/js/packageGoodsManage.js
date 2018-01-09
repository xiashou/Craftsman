Ext.onReady(function() {
	
	var packageId;
	
	var packageHeadRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 30
	});
	
	//套餐基本信息
	var packageHeadStore = Ext.create('Ext.data.Store', {
		  fields: ['id', 'name','price', 'range', 'expire', 'startDate','endDate','explain','status','createTime'],
		  proxy: {
		      type: 'ajax',
			  url : '/goods/queryAllPackageGoods.atc',
			  reader : {
			      root: 'packageList',
			      totalProperty: 'totalCount'
			  }
		  }
	});
	
	//套餐列表表头
	var packageHeadColumns= [packageHeadRowNum,
	      	            {header : 'ID',dataIndex: 'id', width: 60, hidden: true},
	      	            {header : '套餐名称',dataIndex: 'name', width: 150},
	      	            {header : '创建日期',dataIndex: 'createTime', width: 120}
	      	  	];
	
	//套餐列表工具栏按钮
	var packageHeadTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	id: 'insert',
        	text: '新建',
        	iconCls: 'add',
        	hidden : !parent.haveActionMenu('Add'),
        	handler: function() {
        		packageHeadForm.getForm().reset();
        		packageDetailStore.removeAll();
        		packageHeadSelModel.deselectAll();
            }
        },{
        	xtype: 'button',
        	id: 'delete',
        	text: '删除',
        	iconCls: 'delete', 
        	hidden : !parent.haveActionMenu('Del'),
        	handler: function(){
        		initPackageGoodsDelete();
            }
        }]
    });
	
	//套餐列表表头样式(选择框勾选)
	var packageHeadSelModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1,
		mode: 'SINGLE'
	});
	
	//套餐列表GRID
	var packageHeadGrid = new Ext.grid.GridPanel({
		columns : packageHeadColumns,
		store : packageHeadStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		selModel : packageHeadSelModel,
		tbar : packageHeadTbar, 
		stripeRows:true,
		forceFit: true,
		viewConfig : {
			enableTextSelection:true	//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
		listeners:{
			//点击每行监听事件
	        itemclick: function(grid, record, item, index, e) {
	        	//加载对应套餐详情
	        	packageHeadForm.loadRecord(record);
	        	packageDetailStore.load({
					params: {
						'packageId' : record.raw.id
					}
				});
				packageId = record.raw.id;
	        }
	    }
	});
	
	//自定义状态下拉框
	var statusCombo = Ext.create('Ext.form.ComboBox', {
		id: 'status',
		anchor: '80%',
		store: Ext.create('Ext.data.Store', {
			fields: ['id', 'name'],
			data:[
			      {id: '1', name: '启用'},
			      {id: '0', name: '禁用'}
			]
		}),
		allowBlank: false,
        enforceMaxLength: true,
        fieldLabel: '状态',
        labelAlign:'right',
        labelWidth: 95,
        listConfig: {
            minWidth: null
        },
        valueField: 'id',
        displayField: 'name',
        value: '1',
        queryMode: 'local',
        editable : false,
        forceSelection: true
	});
	
	var rangeCombo = Ext.create('Ext.form.ComboBox', {
		id: 'range',
		name: 'goodsPackage.range',
		anchor: '80%',
		store: Ext.create('Ext.data.Store', {
			fields: ['value', 'text'],
			data:[
			      {value: 1, text: '仅本店'},
			      {value: 2, text: '连锁通用'}
			]
		}),
		allowBlank: false,
        enforceMaxLength: true,
        fieldLabel: '适用范围',
        labelAlign:'right',
        labelWidth: 95,
        listConfig: {
            minWidth: null
        },
        valueField: 'value',
        displayField: 'text',
        value: '1',
        queryMode: 'local',
        editable : false,
        forceSelection: true
	});
	
	//套餐详情表单
	var packageHeadForm = Ext.create('Ext.form.FormPanel', {
		id : 'packageHeadForm',
		region : 'north',
		margins : '3 3 3 3',
		border : true,
		layout: 'anchor',
		buttonAlign : 'center',
		height : 165,
		defaults: {
            anchor: '100%',
            layout: 'hbox',
            xtype:'fieldcontainer'
        },
		bodyPadding: '5 10 0 0',
		border : false,
		items : [
			{
			    defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'right'},
			    items: [
		               {id:'id', name:'goodsPackage.id', xtype:'hiddenfield'},
		               {id:'createTime', name:'goodsPackage.createTime', xtype:'hiddenfield'},
			           {id:'name', name: 'goodsPackage.name',xtype:'textfield', allowBlank: false, fieldLabel: '套餐名称', blankText:'商品名称不能为空!'  },
			           {id:'price', name: 'goodsPackage.price',xtype:'numberfield', allowBlank: false, fieldLabel: '零售价', blankText:'商品名称不能为空!'  },
			           rangeCombo
			    ]
			},{
			    defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'right'},
			    items: [
			           {id:'expire', name: 'goodsPackage.expire', xtype:'numberfield', fieldLabel: '有效天数' },
			           statusCombo,{xtype:'displayfield'}
			    ]
			},{
				defaults: {flex: 1, xtype:'textarea', labelWidth:70, labelAlign:'right',rows: 3},
				items: [
				        {id:'explain', name: 'goodsPackage.explain', allowBlank: true, fieldLabel: '说明' },
				]
			}
		],
		buttons:[{
			text : '保 存', // 按钮文本
			iconCls : 'accept', // 按钮图标
			handler : function() { // 按钮响应函数
				if (packageHeadForm.form.isValid()) {
					//保存确定ajax提交保存数据
					 Ext.Ajax.request({
	    					url : '/goods/savePackageGoods.atc',
	    					params : {
	    						'goodsPackage.id': Ext.getCmp("id").getValue(),
	    						'goodsPackage.name': Ext.getCmp("name").getValue(),
	    						'goodsPackage.price': Ext.getCmp("price").getValue(),
	    						'goodsPackage.range': rangeCombo.getValue(),
//	    						'goodsPackage.startDate': parent.dateFormat(Ext.getCmp("startDate").getValue()),
//	    						'goodsPackage.endDate': parent.dateFormat(Ext.getCmp("endDate").getValue()),
	    						'goodsPackage.expire': Ext.getCmp("expire").getValue(),
	    						'goodsPackage.createTime': parent.dateTimeFormat(Ext.getCmp("createTime").getValue()),
	    						'goodsPackage.status': Ext.getCmp("status").getValue(),
	    						'goodsPackage.explain': Ext.getCmp("explain").getValue()
	    					},
	    					success : function(resp, opts) {
	    						var result = Ext.decode(resp.responseText);
	    						if(result.success){
	    							packageHeadStore.reload();
	    							Ext.example.msg('提示', result.msg);
	    						}
	    					},
	    					failure : function(resp, opts) {
	    						var result = Ext.decode(resp.responseText);
	    		                Ext.MessageBox.show({
	    					           title: '提示',
	    					           msg: result.msg,
	    					           buttons: Ext.MessageBox.OK,
	    					           icon: Ext.MessageBox.ERROR
	    		                });
	    					}
	    			  });
				}
			}
		},{
			text : '重 置 ',
			iconCls : 'arrow_rotate_anticlockwise',
			handler : function() {
				packageHeadForm.getForm().reset();
			}
		}]
	});
	
	Ext.define('PackageDetailModel', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'gpId', type: 'string'},
	        {name: 'itemNo', type: 'string'},
	        {name: 'goodsId', type: 'string'},
	        {name: 'goodsType', type: 'int'},
	        {name: 'goodsName', type: 'string'},
	        {name: 'number', type: 'string'}
	    ]
	});
	
	Ext.define('GoodsHour', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'id', type: 'string'},
	        {name: 'typeId', type: 'string'},
	        {name: 'name', type: 'string'},
	        {name: 'suitModel', type: 'string'},
	        {name: 'price', type: 'float'}
	    ]
	});
	
	//套餐内容行样式
	var packageDetailRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 15
	});
	
	//套餐内容来源
	var packageDetailStore = Ext.create('Ext.data.Store', {
		  fields: ['gpId', 'itemNo', 'goodsId', 'number', 'goodsName', 'goodsType', 'typeId'],
		  proxy: {
		      type: 'ajax',
			  url : '/goods/queryPackageDetailById.atc',
			  reader : {
			      root: 'packageDetailList',
                  totalProperty: 'totalCount'
			  }
		  }
	});
	
	var packageDetailCellEditing = new Ext.grid.plugin.CellEditing({
        clicksToEdit: 1
    });
	
	var GoodsStore = new Ext.data.Store({
		autoDestroy: true,
		model: GoodsHour,
		proxy:{
            type: 'ajax',
            url : '/goods/queryGoodsByKeyword.atc',
            reader : {
                root: 'goodsList'
            }
        }
	});
	
	var goodsEditing = new Ext.grid.plugin.CellEditing({
        clicksToEdit: 1
    });
	
	var GoodsCombo = Ext.create('Ext.form.ComboBox', {
		store: GoodsStore,
		valueField: 'name',
		displayField: 'name',
		typeAhead: false,
        hideLabel: true,
        hideTrigger:true,
        queryParam: 'keyword',	//搜索参数字段
        minChars: 1,
        emptyText: '请输入 商品名称/编码',
        listConfig: {
            loadingText: '查找中...',
            emptyText: '<div style="padding: 5px;">没有找到相关商品！</div>',
            getInnerTpl: function() {
                return '<div class="search">{name}/{suitModel}</div>';
            }
        },
        listeners: {
        	select: function(cb, records){
        		var rowRecord = packageDetailGrid.getSelectionModel().getLastSelected();
        		if(rangeCombo.getValue() == 2 && records[0].data.id.substring(0, 1) == "1"){
    				cb.setRawValue("");
    				cb.focus();
    				Ext.MessageBox.alert('提示', '适用范围是连锁通用时内容只能为商品！');
        		} else {
        			rowRecord.data.goodsId = records[0].data.id;
        			rowRecord.data.typeId = records[0].data.typeId;
        			goodsEditing.startEdit(rowRecord, 2);
        		}
            },
            blur: function(cb){
            	var rowRecord = packageDetailGrid.getSelectionModel().getLastSelected();
    			if(!Ext.isEmpty(rowRecord.data.goodsId)){
    				if(rowRecord.data.goodsId == cb.getValue()) {
    					cb.setValue("");
    					cb.setRawValue("");
    					Ext.MessageBox.alert('提示', '商品无效，请重新选择！');
    					cb.focus();
    				}
    			} else
    				Ext.MessageBox.alert('提示', '商品无效，请重新选择！');
            }
        }
	});
	

	var GoodsRenderer = function(value, cellmeta, record) {
        var index = GoodsStore.find("id", value);
        var record = GoodsStore.getAt(index);
        var displayText = "";
        if (record == null)
        	displayText = value;
        else
        	displayText = record.data.name;
        return displayText;
    };
	
	var packageDetailColumns= [packageDetailRowNum,
		{header : '套餐ID,主键',dataIndex: 'gpId', width: 60, hidden: true},
		{header : '行项目,主键',dataIndex: 'itemNo', width: 60, hidden: true},
		{header : '商品ID',dataIndex: 'goodsId', width: 60, hidden: true},
		{header : '类型ID',dataIndex: 'typeId', width: 60, hidden: true},
		{header : '商品名称',dataIndex: 'goodsName', width: 150, editor: GoodsCombo},
		{header : '次数/数量',dataIndex: 'number', width: 60, editor: {xtype: 'numberfield', allowBlank: false}},
		{xtype: 'actioncolumn',header : '操作',width: 50,align:'center',sortable: false,menuDisabled: true,
			items: [{
			    iconCls: 'delete',
			    tooltip: '删除',
			    scope: this,
		        handler: function(grid, rowIndex){
		        	packageDetailGrid.getStore().removeAt(rowIndex);
		        }
		    }]
	}];
	
	var packageDetailTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	text: '新增一行',
        	iconCls: 'add',
        	hidden : !parent.haveActionMenu('AddRow'),
        	handler: function() {
        		addPackageDetail();
            }
        },'->',{
        	xtype: 'button',
        	text: '保存全部',
        	iconCls: 'accept',
        	hidden : !parent.haveActionMenu('Save'),
        	handler: function() {
                var jsonArray = [];  
                Ext.each(packageDetailStore.data.items, function(item){
                	if(!Ext.isEmpty(item.data.goodsId))
                		jsonArray.push(item.data);
                });  
                Ext.Ajax.request({  
                    method:'POST',
                    url: '/goods/savePackageDetail.atc',
                    params : {
						jsonStr : Ext.encode(jsonArray),
						packageId : packageId
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							Ext.example.msg('提示', result.msg);
						} else 
							Ext.MessageBox.show({
					           title: '提示',
					           msg: result.msg,
					           buttons: Ext.MessageBox.OK,
					           icon: Ext.MessageBox.ERROR
			                });
					},
					failure : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
                        Ext.Msg.alert('提示', result.msg);
					}
                });
            }
        }]
    });
	
	var packageDetailGrid = Ext.create('Ext.grid.GridPanel', {
		columns : packageDetailColumns,
		store : packageDetailStore, 
		title : '套餐内容',
		iconCls : 'package_green',
		region : 'center',
		margins : '3 3 3 3',
		border : false,
		autoScroll : true,
		columnLines : true,
		tbar : packageDetailTbar, 
		plugins: [goodsEditing],
		stripeRows:true,
		forceFit: true,
		viewConfig : {
			enableTextSelection:true	//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	var packageDetail = Ext.create('Ext.panel.Panel',{
		region: 'center',
        id: 'packageDetail',
        title: '套餐详情',
        iconCls: 'package_add',
        split: true,
        border: false,
        autoScroll: true,
        collapsible: true,
        animCollapse: true,
        items:[packageHeadForm, packageDetailGrid]
	});
	
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [{
			title : '套餐列表',
			iconCls : 'package',
			tools : [{
				type:'refresh',
				handler : function() {
					packageHeadStore.reload();
				}
			},{
	            type:'help',
	            tooltip: '操作指引',
	            callback: function(panel, tool, event) {
	            	parent.global.openSystemHelpTab();
	            }
	        }],
			collapsible : true,
			width : '30%',
			split : true,
			region : 'west',
			layout : 'fit',
			border : false,
			items : [ packageHeadGrid ]
		}, packageDetail]
	});
	packageHeadStore.reload();
	
	function initPackageGoodsDelete() {
		var record = packageHeadSelModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '你没有选中任何项目！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
		    });
			return;
		} 
		Ext.Msg.confirm('请确认', '确定要删除这个选项吗?', function(btn, text) {
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : '/goods/deletePackageGoods.atc',
					params : {
						'goodsPackage.id' : record.raw.id
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							Ext.example.msg('提示', result.msg);
							packageHeadStore.reload();
							packageHeadForm.getForm().reset();
							packageDetailStore.removeAll();
						} else 
							Ext.MessageBox.show({
     				           title: '提示',
     				           msg: result.msg,
     				           buttons: Ext.MessageBox.OK,
     				           icon: Ext.MessageBox.ERROR
                            });
					},
					failure : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
                        Ext.MessageBox.show({
 				           title: '提示',
 				           msg: result.msg,
 				           buttons: Ext.MessageBox.OK,
 				           icon: Ext.MessageBox.ERROR
                        });
					}
				});
			}
		});
	}
	
	//添加一行项目
	function addPackageDetail(){
		var packageHead = packageHeadSelModel.getSelection()[0];
		if(packageHead == null){
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '请选择套餐！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
			});
		}else{
			var record = new PackageDetailModel({
    			gpId: packageHead.data.id,
    			itemNo: '',
    			goodsId: '',
    			goodsName: '',
    			number: '1'
    		});
    		packageDetailStore.insert(packageDetailStore.getCount(), record);
//    		packageDetailCellEditing.startEditByPosition({
//    			row: packageDetailStore.getCount() - 1,
//    			column: 1
//    		});
		}
	}
});