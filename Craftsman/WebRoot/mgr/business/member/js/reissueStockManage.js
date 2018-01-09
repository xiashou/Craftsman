Ext.require(['Ext.ux.DataTip']);
/**
 * 套餐补发
 */
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	var reissueRowNum = new Ext.grid.RowNumberer({
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
	        {name: 'goodsType', type: 'string'},
	        {name: 'typeId', type: 'int'},
	        {name: 'endDate', type: 'string'},
	        {name: 'memId', type: 'string'}
	    ]
	});
	
	var reissueStore = Ext.create('Ext.data.Store', {
		  fields: ['goodsId', 'goodsName', 'number', 'goodsType', 'typeId', 'endDate', 'memId']
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
	
	var MemberCombo = Ext.create('Ext.form.ComboBox', {
        store: MemberStore,
        fieldLabel: '会员',
        labelWidth:40,
        labelAlign:'right',
        allowBlank: false,
        valueField: 'memId',
        displayField: 'mobile',
        typeAhead: false,
        hideTrigger:true,
        width: 290,
        queryParam: 'keyword',	//搜索参数字段
        minChars: 2,
        emptyText: '请输入会员车牌号或手机号后4位',
        pageSize: 10,
        listConfig: {
            loadingText: '查找中...',
            emptyText: '<div style="padding: 5px;">没有找到相关车主！</div>',
            getInnerTpl: function() {
                return '<div>{fullCarNumber}/{name}/{mobile}</div>';
            }
		}
	});
	
	Ext.define('GoodsHour', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'id', type: 'string'},
	        {name: 'typeId', type: 'string'},
	        {name: 'name', type: 'string'},
	        {name: 'price', type: 'float'}
	    ]
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
	
	var GoodsCombo = Ext.create('Ext.form.ComboBox', {
		store: GoodsStore,
		valueField: 'id',
		displayField: 'name',
        allowBlank: false,
		typeAhead: false,
        hideTrigger:true,
        queryParam: 'keyword',	//搜索参数字段
        minChars: 1,
        emptyText: '请输入 商品名称/编码',
        pageSize: 10,
        listConfig: {
            loadingText: '查找中...',
            emptyText: '<div style="padding: 5px;">没有找到相关商品！</div>',
            getInnerTpl: function() {
                return '<div class="search">{name}</div>';
            }
        },
        listeners: {
        	select: function(cb, records){
        		var rowRecord = reissueGrid.getSelectionModel().getLastSelected();
        		if(!Ext.isEmpty(records[0])){
        			rowRecord.data.goodsName = records[0].data.name;
        			rowRecord.data.typeId = records[0].data.typeId;
        			if(!Ext.isEmpty(records[0].data.id)){
        				if(records[0].data.id.substring(0, 1) == '1')
        					rowRecord.data.goodsType = 1;
        				else if(records[0].data.id.substring(0, 1) == '3')
        					rowRecord.data.goodsType = 2;
        			}
        		}
        		reissueCellEditing.startEdit(rowRecord, 2);
//            },
//            blur: function(cb){
//        		if(Ext.isEmpty(cb.getValue()) || cb.getValue() == 0) {
//        			cb.setRawValue("");
//        			Ext.MessageBox.alert('提示', '商品无效，请重新选择！');
//        			cb.focus();
//        		}
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
	
	var reissueColumns= [reissueRowNum,
            {header : '商品',dataIndex: 'goodsId', width: 320, editor: GoodsCombo, renderer: GoodsRenderer},
            {header : '商品名称',dataIndex: 'goodsName', hidden: true},
            {header : '商品类型',dataIndex: 'goodsType', hidden: true},
            {header : '类型ID',dataIndex: 'typeId', hidden: true},
            {header : '数量',dataIndex: 'number', width: 100, editor: {xtype:'numberfield', minValue: 1, listeners: {
            	specialkey : function(field, e) {
					if (e.getKey() == Ext.EventObject.ENTER) {
						var rowRecord = reissueGrid.getSelectionModel().getLastSelected();
						reissueCellEditing.startEdit(rowRecord, 3);
					}
				}
            }}, renderer: Ext.util.Format.numberRenderer("0.00")},
            {header : '到期日期',dataIndex: 'endDate', width: 120, editor:{xtype:'datefield', format: 'Y/m/d', listeners: {
   	          	select: function(cb, records){
   	          		insertRow();
   	            }
            }}, renderer: Ext.util.Format.dateRenderer('Y/m/d')},
            {xtype: 'actioncolumn',header : '操作', width: 50,align:'center',sortable: false, menuDisabled: true,
   				items: [{
   				    iconCls: 'delete',
   				    tooltip: '删除',
   				    scope: this,
   			        handler: function(grid, rowIndex){
   			        	reissueGrid.getStore().removeAt(rowIndex);
   			        }
   			    }]
            }
  	];
	
	var reissueCellEditing = new Ext.grid.plugin.CellEditing({
        clicksToEdit: 1
    });
	
	var typeCombo = new Ext.form.ComboBox({
		id: 'type',
		triggerAction : 'all',
		mode : 'local',
		store: Ext.create('Ext.data.ArrayStore', {
			fields : [ 'value', 'text' ],
			data : [ [ 1, '补发' ], [ 2, '赠送' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		emptyText: '类型',
		value: 1,
		editable : false,
		width : 80
	});
	
	var reissueTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	text: '新增一行',
        	iconCls: 'add',
//        	hidden : !parent.haveActionMenu('Add'),
        	handler: function() {
        		insertRow();
        	}
        },typeCombo,MemberCombo,{
        	xtype: 'button',
        	id: 'delete',
        	text: ' 确 定 ',
        	iconCls: 'accept', 
//        	hidden : !parent.haveActionMenu('Save'),
        	handler: function(){
                var jsonArray = [];
                var eMsg = "";
                Ext.each(reissueStore.data.items, function(item){
                	if(!Ext.isEmpty(item.data.goodsId)){
                		if(!Ext.isEmpty(item.data.endDate))
                			item.data.endDate = Ext.Date.format(new Date(item.data.endDate),'Y/m/d');
                		jsonArray.push(item.data);  
                	}
                	if(isNaN(item.data.goodsId)){
                		eMsg += item.data.goodsId + "商品不存在！";
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
                if(Ext.isEmpty(MemberCombo.getValue())){
                	Ext.MessageBox.show({
          	           title: '提示',
          	           msg: "请选择会员！",
          	           buttons: Ext.MessageBox.OK,
          	           icon: Ext.MessageBox.INFO
                  	});
                  	return;
                }
                Ext.MessageBox.show({
					title: '请稍等',
					msg: '正在处理 ...',
					width: 200,
					closable: false
		        });
                Ext.Ajax.request({
                    method:'POST',
                    url: '/member/reissueMemberStock.atc',
                    params : {
                    	memId : MemberCombo.getValue(),
                    	type  : typeCombo.getValue(),
						jsonStr : Ext.encode(jsonArray)
					},
					success : function(resp, opts) {
						Ext.MessageBox.hide();
						var result = Ext.decode(resp.responseText);
						if(result.success){
							Ext.example.msg('提示', result.msg);
							reissueStore.removeAll();
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
        }]
    });
	
	var reissueGrid = new Ext.grid.GridPanel({
		title: '套餐补发/赠送',
		columns : reissueColumns,
		store : reissueStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		tbar : reissueTbar, 
		stripeRows:true,
//		forceFit: true,
		plugins: [reissueCellEditing],
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
		items : [reissueGrid]
	});
	
	function insertRow(){
		var rowRecord = reissueGrid.getSelectionModel().getLastSelected();
       	if(!Ext.isEmpty(rowRecord) && Ext.isEmpty(rowRecord.data.goodsId)){
        	Ext.MessageBox.show({
	           title: '提示',
	           msg: '商品不能为空！',
	           buttons: Ext.MessageBox.OK,
	           icon: Ext.MessageBox.INFO
        	});
        	return;
      	}
		var record = new InStockModel({
			goodsId: '',
			goodsName: '',
			goodsType: '',
			typeId: 0,
			number: 1.0,
			endDate: '',
		});
		reissueStore.insert(reissueStore.getCount(), record);
		reissueCellEditing.startEditByPosition({
			row: reissueStore.getCount() - 1,
			column: 1
		});
	}
	
	insertRow();
	
//	var reissueForm = new Ext.form.FormPanel({
//		id : 'reissueForm',
//		layout: 'anchor',
//		defaults: {
//            anchor: '100%',
//            xtype:'textfield'
//        },
//		bodyPadding: '5 10 0 0',
//		border : false,
//		plugins: {
//            ptype: 'datatip'
//        },
//		items : [{
//			xtype: 'fieldcontainer',
//		         layout: 'hbox',
//		         defaults: {flex: 1,xtype:'numberfield',labelWidth:100,labelAlign:'right'},
//		         items: [MemberCombo]
//		     },{
//		    	 xtype: 'fieldcontainer',
//		         layout: 'hbox',
//		         defaults: {flex: 1,xtype:'numberfield', labelWidth:100,labelAlign:'right'},
//		         items: [GoodsCombo]
//		     },{
//		    	 xtype: 'fieldcontainer',
//		         layout: 'hbox',
//		         defaults: {flex: 1,xtype:'numberfield', labelWidth:70,labelAlign:'right'},
//		         items: [
//	                    {id:'number', name: 'memberStock.number',fieldLabel: '数量', allowBlank: false, tooltip:'设置补发数量'},
//	                    {id:'goodsType', name: 'memberStock.goodsType', xtype:'hiddenfield'},
//	                    {id:'typeId', name: 'memberStock.typeId', xtype:'hiddenfield'},
//	                    {id:'goodsName', name: 'memberStock.goodsName', xtype:'hiddenfield'}
//		         ]
//		     },{
//		    	 xtype: 'fieldcontainer',
//		    	 layout: 'hbox',
//		    	 defaults: {flex: 1,xtype:'datefield', labelWidth:70,labelAlign:'right'},
//		    	 items: [
//		    	         {flex: 9,id:'endDate', name: 'memberStock.endDate',fieldLabel: '到期日期', format: 'Y/m/d', tooltip:'设置到期日期'},
//		    	 ]
//		     }
//		],
//		buttons:[{
//			text : '确 认',
//			iconCls : 'accept',
//			handler : function() {
//				if (reissueForm.form.isValid()) {
//					reissueForm.form.submit({
//						url : '/member/reissueMemberStock.atc',
//						waitTitle : '提示',
//						method : 'POST',
//						waitMsg : '正在处理数据,请稍候...',
//						success : function(form, action) {
//							Ext.example.msg('提示', action.result.msg);
//						},
//						failure : function(form, action) {
//							var msg = action.result.msg;
//							Ext.MessageBox.alert('提示', msg);
//						}
//					});
//				}
//			}
//		}]
//	});
//    
//    var reissueWindow = new Ext.Window({
//		title : '套餐补发', 
//		layout : 'fit',
//		width : 380,
//		height : 230,
//		y: 150,
//		resizable : false,
//		draggable : false,
//		closable : false,
//		modal : false,
//		collapsible : true,
//		titleCollapse : true,
//		maximizable : false,
//		buttonAlign : 'right',
//		border : false,
//		animCollapse : true,
//		constrain : true,
//		items : [reissueForm]
//	});
    
    
//    reissueWindow.show();
	
});