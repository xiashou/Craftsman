Ext.require(['Ext.ux.TreePicker']);
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	var gradeStore = Ext.create('Ext.data.Store', {
        fields: ['id', 'grade', 'name', 'needPoint', 'discount', 'orderDiscount'],
        proxy: {
            type: 'ajax',
            url : '/member/queryGradeByDeptCode.atc',
            reader : {
                root: 'gradeList'
            }
        }
    });
	
	var gradePriceStore = Ext.create('Ext.data.Store', {
        fields: ['goodsId', 'gradeId', 'goodsType', 'goodsName', 'price', 'mprice', 'oprice', 'mcount', 'ocount'],
        proxy: {
            type: 'ajax',
            url : '/member/queryGradePriceByDeptCode.atc',
            reader : {
                root: 'gradePriceList'
            }
        }
    });
	
	var gradeForm = Ext.create('Ext.form.Panel', {
		id : 'gradeForm',
		layout: 'anchor',
		defaults: {
            anchor: '100%',
            xtype: 'fieldcontainer',
	        layout: 'hbox'
        },
		bodyPadding: '5 10 0 0',
		border : false,
		items : [{
		         defaults: {flex: 1,xtype:'numberfield',labelWidth:70,labelAlign:'right'},
		         items: [
						{id:'id', name: 'grade.id', xtype:'hiddenfield'},
						{id:'grade', name: 'grade.grade',fieldLabel: '等级', allowBlank: false, blankText:'等级不能为空!'},
						{id:'needPoint', name: 'grade.needPoint', fieldLabel: '所需积分'},
		         ]
		     },{
		         defaults: {flex: 1,xtype:'textfield',labelWidth:70,labelAlign:'right'},
		         items: [
		                {id:'name', name: 'grade.name', fieldLabel: '等级名', allowBlank: false, blankText:'等级名不能为空!'}
		         ] 
		     },{
		    	 defaults: {flex: 0.5, xtype:'textfield',labelWidth:70,labelAlign:'right'},
		    	 items: [
		    	        {id:'discount', name: 'grade.discount', fieldLabel: '折扣'},
		    	        {id:'orderDiscount', name: 'grade.orderDiscount', fieldLabel: '预约折扣'}
		    	 ]
		     }
		],
		buttons:[{
			text : '保 存',
			iconCls : 'accept',
			handler : function() {
				if (gradeForm.form.isValid()) {
					gradeForm.form.submit({
						waitTitle : '提示',
						method : 'POST',
						waitMsg : '正在处理数据,请稍候...',
						success : function(form, action) {
							gradeWindow.hide();
							gradeForm.getForm().reset();
							gradeStore.reload();
							Ext.example.msg("提示", action.result.msg);
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
				}
			}
		},{
			text : '关 闭 ',
			iconCls : 'stop',
			handler : function() {
				gradeWindow.hide();
			}
		}]
	});
	
	var gradeWindow = new Ext.Window({
		layout : 'fit',
		width : 450,
		height : 200,
		x: 20,
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
		items: [gradeForm]
	});
	

	var gradeToolbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	text: '新建',
        	iconCls: 'add',
        	hidden: !parent.haveActionMenu('Add'),
        	handler: function(){
        		gradeWindow.setTitle('新建会员等级');
        		gradeForm.getForm().reset();
        		gradeForm.getForm().url = '/member/insertGrade.atc';
        		gradeWindow.show();
            }
        },{
        	xtype: 'button',
        	text: '修改',
        	iconCls: 'pencil',
        	hidden: !parent.haveActionMenu('Edit'),
        	handler: function() {
        		initEdit();
            }
        },{
        	xtype: 'button',
        	id:'delete',
        	text: '删除',
        	iconCls: 'delete',
        	hidden : !parent.haveActionMenu('Del'),
        	handler: function(){
        		initDelete();
            }
        }]
    });
	
	var gradePriceToolbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'textfield',
        	id: 'mcount',
        	width: 60
        },{
        	xtype: 'button',
        	text: '一键设置会员折扣',
        	iconCls: 'coins_delete',
        	handler: function(){
        		var i = 0;
	        	gradePriceStore.each(function(rec){
	        		var mcount = Ext.getCmp('mcount').getValue();
	        		gradePriceStore.getAt(i).set('mcount', mcount); 
	        		gradePriceStore.getAt(i).set('mprice', Number(Number(mcount) * Number(rec.get('price') * 0.1)).toFixed(2));
	        		i++;
	        	});
        	}
        },{
        	xtype: 'textfield',
        	id: 'ocount',
        	width: 60
        },{
        	xtype: 'button',
        	text: '一键设置预约折扣',
        	iconCls: 'coins_delete',
        	handler: function(){
        		var i = 0;
        		var ocount = Ext.getCmp('ocount').getValue();
	        	gradePriceStore.each(function(rec){
	        		gradePriceStore.getAt(i).set('ocount', ocount); 
	        		gradePriceStore.getAt(i).set('oprice', Number(Number(ocount) * Number(rec.get('price') * 0.1)).toFixed(2)); 
	        		i++;
	        	});
        	}
        },{
        	xtype: 'button',
        	text: '全部保存',
        	iconCls: 'accept',
        	hidden : !parent.haveActionMenu('Save'),
        	handler: function(){
                var jsonArray = [];  
                Ext.each(gradePriceStore.data.items, function(item){  
                    jsonArray.push(item.data);
                });  
                Ext.Ajax.request({  
                    method:'POST',
                    url: '/member/saveGradePrice.atc',
                    params : {
						jsonStr : Ext.encode(jsonArray)
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							Ext.example.msg('提示', result.msg);
							gradePriceStore.loadPage(gradePriceStore.currentPage);
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
	
//	Ext.define('Supcount', {
//	     extend: 'Ext.data.Model',
//	     fields: [
//	         {name: 'text', type: 'string'},
//	         {name: 'value',  type: 'string'}
//	     ]
//	 });
//	var countStore = new Ext.data.Store({
//		model: Supcount,
//		data : [
//	         {value: '1', text: '是'},
//	         {value: '0', text: '否'}
//	     ]
//	});
	
//	var supMcountCombo = new Ext.form.field.ComboBox({
//		store: countStore,
//		valueField: 'value',
//		displayField: 'text',
//		queryMode: 'local',
//		editable : false
//	});
//	var supMcountRenderer = function(value, cellmeta, record) {
//        var index = countStore.find("value", value);
//        var record = countStore.getAt(index);
//        var displayText = "";
//        if (record == null)
//        	displayText = value;
//        else
//        	displayText = record.data.text;
//        return displayText;
//    };
//    var supOcountCombo = new Ext.form.field.ComboBox({
//		store: countStore,
//		valueField: 'value',
//		displayField: 'text',
//		queryMode: 'local',
//		editable : false
//	});
//	var supOcountRenderer = function(value, cellmeta, record) {
//        var index = countStore.find("value", value);
//        var record = countStore.getAt(index);
//        var displayText = "";
//        if (record == null)
//        	displayText = value;
//        else
//        	displayText = record.data.text;
//        return displayText;
//    };
	var gradeColumns= [
	       {header : 'ID',dataIndex: 'id', width: 60, hidden: true},
	       {header : 'dept',dataIndex: 'deptCode', width: 60, hidden: true},
	       {header : '会员等级',dataIndex: 'grade', width: 60},
	       {header : '等级名',dataIndex: 'name', width: 80},
	       {header : '所需积分',dataIndex: 'needPoint', width: 60},
	       {header : '会员折扣',dataIndex: 'discount', width: 60},
	       {header : '预约折扣',dataIndex: 'orderDiscount', width: 60}
	];
	
	var gradePriceColumns= [
	       {header : 'ID',dataIndex: 'goodsId', width: 60, hidden: true},
	       {header : '会员等级', dataIndex: 'gradeId', width: 60, hidden: true},
	       {header : '类型',dataIndex: 'goodsType', width: 60},
	       {header : '名称',dataIndex: 'goodsName', width: 60},
	       {header : '工时费',dataIndex: 'price', width: 60, renderer: function(v){return Number(v).toFixed(2); }},
	       {header : '会员折扣',dataIndex: 'mcount', width: 60, editor: {xtype: 'numberfield', allowBlank: false, minValue: 0}},
	       {header : '会员价',dataIndex: 'mprice', width: 60, editor: {xtype: 'numberfield', allowBlank: false, minValue: 0},renderer: Ext.util.Format.numberRenderer("0.00")},
	       {header : '预约折扣',dataIndex: 'ocount', width: 60, editor: {xtype: 'numberfield', allowBlank: false, minValue: 0}},
	       {header : '预约价',dataIndex: 'oprice', width: 60, editor: {xtype: 'numberfield', allowBlank: false, minValue: 0},renderer: Ext.util.Format.numberRenderer("0.00")}
	];
	
	var selModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1,
		mode: 'SINGLE'
	});
	
	var gradeGrid = new Ext.grid.GridPanel({
		columns : gradeColumns,
		autoScroll : true,
		columnLines : true,
		selModel: selModel,
		frame : false,
		region : 'center',
		store : gradeStore,
		tbar : gradeToolbar, 
		stripeRows:true, 
		forceFit : true,
		viewConfig : {
			enableTextSelection:true
			// forceFit : true
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
		listeners:{
	        itemclick: function(grid, record, item, index, e) {
	        	gradePriceStore.load({
	        		params:{
	        			'gradePrice.gradeId': record.data.grade
	        		}
	        	});
	        	Ext.getCmp('mcount').setValue(record.data.discount);
	        	Ext.getCmp('ocount').setValue(record.data.orderDiscount);
	        }
	    }
	});
	
	var gradePriceCellEditing = new Ext.grid.plugin.CellEditing({
        clicksToEdit: 1
    });
	
	var gradePriceGrid = new Ext.grid.GridPanel({
		columns : gradePriceColumns,
		title: '等级详情',
		autoScroll : true,
		columnLines : true,
//		selModel: selModel,
		frame : false,
		region : 'center',
		store : gradePriceStore,
		tbar : gradePriceToolbar, 
		stripeRows:true, 
		forceFit : true,
		plugins: [gradePriceCellEditing],
		viewConfig : {
			enableTextSelection:true
			// forceFit : true
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
	    }
	});
	
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [{
			title : '会员等级',
			iconCls : 'group',
			tools : [{
				id : 'refresh',
				handler : function() {
			        gradeStore.reload();
				}
			},{
	            type:'help',
	            tooltip: '操作指引',
	            callback: function(panel, tool, event) {
	            	parent.global.openSystemHelpTab();
	            }
	        }],
			collapsible : true,
			width : 480,
			minSize : 160,
			maxSize : 580,
			split : true,
			region : 'west',
			autoScroll : true,
			items : [ gradeGrid ]
		}, {
			region : 'center',
			layout : 'fit',
			border : false,
			items : [gradePriceGrid]
		}]
	});
	
	gradeStore.load();
	//加载完成后计算折扣
	gradePriceStore.on('load', function(){
    	var i = 0;
    	gradePriceStore.each(function(rec){
    		if(Number(rec.data.mprice) == Number(rec.get('price')))
    			gradePriceStore.getAt(i).set('mcount', 0);
    		else
    			gradePriceStore.getAt(i).set('mcount', Number(Number(rec.data.mprice) / (Number(rec.get('price')) == 0 ? 1 : Number(rec.get('price'))) * 10).toFixed(2));
    		if(Number(rec.data.oprice) == Number(rec.get('price')))
    			gradePriceStore.getAt(i).set('ocount', 0);
    		else
    			gradePriceStore.getAt(i).set('ocount', Number(Number(rec.data.oprice) / (Number(rec.get('price')) == 0 ? 1 : Number(rec.get('price'))) * 10).toFixed(2)); 
    		i++;
    	});
	});
	
	
	function initEdit() {
		var record = selModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '你没有选中任何项目！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
		    });
			return;
		} 
		selModel.deselectAll();
		gradeWindow.setTitle('修改会员等级');
		gradeForm.getForm().url = '/member/updateGrade.atc';
		gradeForm.loadRecord(record);
		gradeWindow.show();
	}
	
	function initDelete() {
		var record = selModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '你没有选中任何项目！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
		    });
			return;
		} 
		Ext.Msg.confirm('请确认', '确认删除这个等级吗?', function(btn, text) {
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : '/member/deleteGrade.atc',
					params : {
						'grade.id' : record.raw.id
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							Ext.example.msg('提示', result.msg);
							gradeStore.reload();
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
		});        			
	}

});