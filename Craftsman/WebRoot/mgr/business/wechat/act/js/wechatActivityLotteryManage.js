Ext.onReady(function() {
	
	Ext.QuickTips.init();
	var activityCode;
	var labelWidth = 80;
	
	// 定义自动当前页行号
	var wechatActivityLotteryRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 30
	});
	
	var wechatActivityLotteryDetailRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 30
	});
	
	var activityTypeStore = Ext.create('Ext.data.Store', {
	    fields: ['id', 'name'],
	    data : [
	        //{"id":0, "name":"砸金蛋"},
	        {"id":1, "name":"大转盘"}
	        //...
	    ]
	});

	var activityTypeComboBox = Ext.create('Ext.form.ComboBox', {
		name: 'wechatActivityLottery.activityType',
		labelWidth: labelWidth, 
		labelAlign:'right',
		fieldLabel: '活动类型',
	    store: activityTypeStore,
	    queryMode: 'local',
	    displayField: 'name',
	    valueField: 'id',
	    emptyText:'请选择类型...',
	    editable : false,
	    allowBlank: false,
	    renderTo: Ext.getBody()
	});
	
	var activityStatusStore = Ext.create('Ext.data.Store', {
	    fields: ['id', 'name'],
	    data : [
	        {"id":0, "name":"无效"},
	        {"id":1, "name":"有效"}
	        //...
	    ]
	});

	var activityStatusComboBox = Ext.create('Ext.form.ComboBox', {
		name: 'wechatActivityLottery.activityStatus',
		labelWidth: labelWidth, 
		labelAlign:'right',
		fieldLabel: '活动状态',
	    store: activityStatusStore,
	    queryMode: 'local',
	    displayField: 'name',
	    valueField: 'id',
	    emptyText:'请选择活动状态...',
	    editable : false,
	    allowBlank: false,
	    renderTo: Ext.getBody()
	});
	
	var freeAddTypeStore = Ext.create('Ext.data.Store', {
	    fields: ['id', 'name'],
	    data : [
	        {"id":0, "name":"无"},
	        {"id":1, "name":"消费"},
	        {"id":2, "name":"时间"}
	        //...
	    ]
	});

	var freeAddTypeComboBox = Ext.create('Ext.form.ComboBox', {
		name: 'wechatActivityLottery.freeAddType',
		labelWidth: labelWidth, 
		labelAlign:'right',
		fieldLabel: '赠送类型',
	    store: freeAddTypeStore,
	    queryMode: 'local',
	    displayField: 'name',
	    valueField: 'id',
	    emptyText:'请选择赠送类型...',
	    editable : false,
	    allowBlank: false,
	    renderTo: Ext.getBody()
	});
	
	var paidConsumeTypeStore = Ext.create('Ext.data.Store', {
	    fields: ['id', 'name'],
	    data : [
	        {"id":0, "name":"无"},
	        {"id":1, "name":"积分"},
	        {"id":2, "name":"余额"}
	        //...
	    ]
	});

	var paidConsumeTypeComboBox = Ext.create('Ext.form.ComboBox', {
		name: 'wechatActivityLottery.paidConsumeType',
		labelWidth: labelWidth, 
		labelAlign:'right',
		fieldLabel: '消耗类型',
	    store: paidConsumeTypeStore,
	    queryMode: 'local',
	    displayField: 'name',
	    valueField: 'id',
	    emptyText:'请选择消耗类型...',
	    editable : false,
	    allowBlank: false,
	    renderTo: Ext.getBody()
	});
	
	var prizeLevelStore = Ext.create('Ext.data.Store', {
	    fields: ['id', 'name'],
	    data : [
	        {"id":0, "name":"特等奖"},
	        {"id":1, "name":"一等奖"},
	        {"id":2, "name":"二等奖"},
	        {"id":3, "name":"三等奖"},
	        {"id":4, "name":"四等奖"},
	        {"id":5, "name":"五等奖"},
	        {"id":6, "name":"六等奖"},
	        {"id":7, "name":"七等奖"},
	        {"id":8, "name":"八等奖"},
	        {"id":9, "name":"九等奖"},
	        {"id":10, "name":"十等奖"}
	        //...
	    ]
	});

	var prizeLevelComboBox = Ext.create('Ext.form.ComboBox', {
		name: 'wechatActivityLotteryItem.prizeLevel',
		labelWidth: labelWidth, 
		labelAlign:'right',
		fieldLabel: '奖品等级',
	    store: prizeLevelStore,
	    queryMode: 'local',
	    displayField: 'name',
	    valueField: 'id',
	    emptyText:'请选择奖品等级...',
	    editable : false,
	    allowBlank: false,
	    //renderTo: Ext.getBody()
	});
	
	var prizeTypeStore = Ext.create('Ext.data.Store', {
	    fields: ['id', 'name'],
	    data : [
	        {"id":0, "name":"实物奖品"},
	        //{"id":1, "name":"积分"},
	        //{"id":2, "name":"套餐"}
	        //...
	    ]
	});

	var prizeTypeComboBox = Ext.create('Ext.form.ComboBox', {
		name: 'wechatActivityLotteryItem.prizeType',
		labelWidth: labelWidth, 
		labelAlign:'right',
		fieldLabel: '奖品类型',
	    store: prizeTypeStore,
	    queryMode: 'local',
	    displayField: 'name',
	    valueField: 'id',
	    emptyText:'请选择奖品类型...',
	    editable : false,
	    allowBlank: false,
	    //renderTo: Ext.getBody()
	});
	
	
	var wechatActivityLotteryStore = Ext.create('Ext.data.Store', {
		  fields: ['id', 'activityCode', 'activityTitle', 'activityType', 'activityStatus', 'freeAddType'
		           , 'freeAddNum', 'freeNum', 'paidConsumeType', 'paidConsumeLimit', 'bDate'
		           , 'eDate', 'activityDescription', 'remark', 'companyId', 'companyName'
		           , 'deptCode', 'deptName', 'createBy', 'updateBy', 'createTime'
		           , 'updateTime'],
		  proxy: {
		      type: 'ajax',
			  url : '/wechat/act/queryAllWechatActivityLottery.atc',
			  reader : {
			      root: 'wechatActivityLotteryList'
			  }
		  }
	});
	
	var wechatActivityLotteryDetailStore = Ext.create('Ext.data.Store', {
		fields: ['id', 'activityCode', 'prizeLevel', 'levelName', 'prizeNum', 'lotteryPrizeNum', 'prizeDesc', 'prizeType'
		         , 'prizeWin', 'prizeRate', 'prizeCode', 'createBy', 'updateBy', 'createTime', 'updateTime'],
		proxy: {
			type: 'ajax',
			url : '/wechat/act/queryWechatActivityLotteryDetailByActiveCode.atc',
			reader : {
				root: 'wechatActivityLotteryItemList'
			}
		}
	});
	
	
	var wechatActivityLotteryForm = new Ext.form.FormPanel({
				id : 'wechatActivityLotteryForm',
				layout: 'anchor',
				defaults: {
		            anchor: '100%',
		            layout: 'hbox',
		            xtype:'fieldcontainer'
		        },
				bodyPadding: '5 10 0 0',
				border : false,
				items : [{
					    defaults: {flex: 1, xtype:'textfield', labelWidth:labelWidth, labelAlign:'right'},
					    items: [
					           {id:'id', name:'wechatActivityLottery.id', xtype:'hiddenfield'}
					    ]
					},{
					    defaults: {flex: 1, xtype:'textfield', labelWidth:labelWidth, labelAlign:'right'},
					    items: [
					           {id:'activityCode', name: 'wechatActivityLottery.activityCode', fieldLabel: '活动编码', emptyText:'系统自动生成', readOnly:true, fieldStyle:'background-color: #F0F0F0;' },
					           {id:'activityTitle', name: 'wechatActivityLottery.activityTitle', allowBlank: false, fieldLabel: '活动标题', blankText:'活动标题不能为空！' },
					    ]
					},{
					    defaults: {flex: 1, xtype:'textfield', labelWidth:labelWidth, labelAlign:'right'},
					    items: [
					           activityTypeComboBox,
					           activityStatusComboBox,
					           //freeAddTypeComboBox
					    ]
					},{
					    defaults: {flex: 1, xtype:'numberfield', labelWidth:labelWidth, labelAlign:'right'},
					    items: [
					           //{id:'freeAddNum', name: 'wechatActivityLottery.freeAddNum', allowBlank: false, fieldLabel: '赠送次数', blankText:'赠送次数不能为空！'},
					           //{id:'freeNum', name: 'wechatActivityLottery.freeNum', allowBlank: false, fieldLabel: '抽奖次数', blankText:'抽奖次数不能为空！' },
					           //paidConsumeTypeComboBox,
					    ]
					},{
					    defaults: {flex: 1, xtype:'numberfield', labelWidth:labelWidth, labelAlign:'right'},
					    items: [
					           //{id:'paidConsumeLimit', name: 'wechatActivityLottery.paidConsumeLimit', allowBlank: false, fieldLabel: '消耗额度', blankText:'消耗额度不能为空！'},
					           {id:'bDate', name: 'wechatActivityLottery.bDate',xtype:'datefield',editable : false, allowBlank: false, fieldLabel: '开始日期', blankText:'开始日期不能为空！' },
					           {id:'eDate', name: 'wechatActivityLottery.eDate',xtype:'datefield',editable : false, allowBlank: false, fieldLabel: '结束日期', blankText:'结束日期不能为空！' }
					    ]
					},{
					    defaults: {flex: 1, xtype:'textareafield', labelWidth:labelWidth, labelAlign:'right'},
					    items: [
					           {id:'activityDescription', name: 'wechatActivityLottery.activityDescription', allowBlank: false, fieldLabel: '活动说明', blankText:'活动说明不能为空！'},
					    ]
					},{
					    defaults: {flex: 1, xtype:'textareafield', labelWidth:labelWidth, labelAlign:'right'},
					    items: [
					           {id:'remark', name: 'wechatActivityLottery.remark', fieldLabel: '活动备注'},
					    ]
					},{
					    defaults: {flex: 1, xtype:'textfield', labelWidth:labelWidth, labelAlign:'right'},
					    items: [
					           {id:'companyId', name: 'wechatActivityLottery.companyId', fieldLabel: '公司ID', blankText:'公司ID不能为空！', readOnly:true, fieldStyle:'background-color: #F0F0F0;' },
					           {id:'companyName', name: 'wechatActivityLottery.companyName', fieldLabel: '公司名称' , readOnly:true, fieldStyle:'background-color: #F0F0F0;' },
					           {id:'deptCode', name: 'wechatActivityLottery.deptCode', fieldLabel: '部门编码', blankText:'部门编码不能为空！' , readOnly:true, fieldStyle:'background-color: #F0F0F0;'}
					    ]
					},{
					    defaults: {flex: 1, xtype:'textfield', labelWidth:labelWidth, labelAlign:'right'},
					    items: [
					           {id:'deptName', name: 'wechatActivityLottery.deptName', fieldLabel: '部门名称' , readOnly:true, fieldStyle:'background-color: #F0F0F0;' },
					           {id:'createBy', name: 'wechatActivityLottery.createBy', fieldLabel: '创建人' , readOnly:true, fieldStyle:'background-color: #F0F0F0;' },
					           {id:'updateBy', name: 'wechatActivityLottery.updateBy', fieldLabel: '修改人' , readOnly:true, fieldStyle:'background-color: #F0F0F0;' },
					    ]
					},{
					    defaults: {flex: 1, xtype:'textfield', labelWidth:labelWidth, labelAlign:'right'},
					    items: [
					           {id:'createTime', name: 'wechatActivityLottery.createTime', fieldLabel: '创建时间' , readOnly:true, fieldStyle:'background-color: #F0F0F0;' },
					           {id:'updateTime', name: 'wechatActivityLottery.updateTime', fieldLabel: '修改时间' , readOnly:true, fieldStyle:'background-color: #F0F0F0;' },
					    ]
					}
					
				],
				buttons:[{
					text : '保 存',
					iconCls : 'accept',
					handler : function() {
						if (wechatActivityLotteryForm.form.isValid()) {
							wechatActivityLotteryForm.form.submit({
								waitTitle : '提示',
								method : 'POST',
								waitMsg : '正在处理数据,请稍候...',
								success : function(form, action) {
									wechatActivityLotteryWindow.hide();
									Ext.example.msg('提示', action.result.msg);
									wechatActivityLotteryStore.reload();
								},
								failure : function(form, action) {
									var msg = action.result.msg;
									Ext.MessageBox.alert('提示', msg);
								}
							});
						}
					}
				},{
					text : '关 闭 ',
					iconCls : 'stop',
					handler : function() {
						wechatActivityLotteryWindow.hide();
					}
				}]
			});
	
	var wechatActivityLotteryDetailForm = new Ext.form.FormPanel({
		id : 'wechatActivityLotteryDetailForm',
		layout: 'anchor',
		defaults: {
            anchor: '100%',
            layout: 'hbox',
            xtype:'fieldcontainer'
        },
		bodyPadding: '5 10 0 0',
		border : false,
		items : [
			{
			    defaults: {flex: 1, xtype:'textfield', labelWidth:80, labelAlign:'right'},
			    items: [
			           {id:'id', name:'wechatActivityLotteryItem.id', xtype:'hiddenfield'},
			    ]
			},{
				defaults: {flex: 1, xtype:'textfield', labelWidth:80, labelAlign:'right'},
				items: [
				        {id:'d_activityCode', name: 'wechatActivityLotteryItem.activityCode',fieldLabel: '活动编码', readOnly:true, fieldStyle:'background-color: #F0F0F0;' },
				        prizeLevelComboBox
				]
			},{
				defaults: {flex: 1, xtype:'textfield', labelWidth:80, labelAlign:'right'},
				items: [
				        {id:'levelName',xtype:'textfield', name: 'wechatActivityLotteryItem.levelName',fieldLabel: '奖项名称', allowBlank: false},
				        {id:'prizeDesc', name: 'wechatActivityLotteryItem.prizeDesc',fieldLabel: '奖品描述', allowBlank: false},
				]
			},{
				defaults: {flex: 1, xtype:'numberfield', labelWidth:80, labelAlign:'right'},
				items: [
				        {id:'prizeNum', name: 'wechatActivityLotteryItem.prizeNum', fieldLabel: '奖品总数量' , allowBlank: false},
				        {id:'lotteryPrizeNum', name: 'wechatActivityLotteryItem.lotteryPrizeNum', fieldLabel: '已中奖数量' , allowBlank: false}
				]
			},{
				defaults: {flex: 1, xtype:'numberfield', labelWidth:80, labelAlign:'right'},
				items: [
				        prizeTypeComboBox,
				        {id:'prizeWin', name: 'wechatActivityLotteryItem.prizeWin',fieldLabel: '获得数量', allowBlank: false},
				]
			},{
				defaults: {flex: 1, xtype:'numberfield', labelWidth:80, labelAlign:'right'},
				items: [
				        {id:'prizeRate', name: 'wechatActivityLotteryItem.prizeRate', fieldLabel: '中奖概率（%）' , allowBlank: false},
				        {id:'prizeCode', name: 'wechatActivityLotteryItem.prizeCode',fieldLabel: '奖品编码', readOnly:true, fieldStyle:'background-color: #F0F0F0;' },
				]
			},{
			    defaults: {flex: 1, xtype:'textfield', labelWidth:labelWidth, labelAlign:'right'},
			    items: [
			           {id:'createBy', name: 'wechatActivityLotteryItem.createBy', fieldLabel: '创建人' , readOnly:true, fieldStyle:'background-color: #F0F0F0;' },
			           {id:'updateBy', name: 'wechatActivityLotteryItem.updateBy', fieldLabel: '修改人' , readOnly:true, fieldStyle:'background-color: #F0F0F0;' },
			    ]
			},{
			    defaults: {flex: 1, xtype:'textfield', labelWidth:labelWidth, labelAlign:'right'},
			    items: [
			           {id:'createTime', name: 'wechatActivityLotteryItem.createTime', fieldLabel: '创建时间' , readOnly:true, fieldStyle:'background-color: #F0F0F0;' },
			           {id:'updateTime', name: 'wechatActivityLotteryItem.updateTime', fieldLabel: '修改时间' , readOnly:true, fieldStyle:'background-color: #F0F0F0;' },
			    ]
			}
		],
		buttons:[{
			text : '保 存',
			iconCls : 'accept',
			handler : function() {
				if (wechatActivityLotteryDetailForm.form.isValid()) {
					//Ext.getCmp("d_activityCode").setValue(activityCode);
					wechatActivityLotteryDetailForm.form.submit({
						waitTitle : '提示',
						method : 'POST',
						waitMsg : '正在处理数据,请稍候...',
						success : function(form, action) {
							wechatActivityLotteryDetailWindow.hide();
							Ext.example.msg('提示', action.result.msg);
							wechatActivityLotteryDetailStore.reload();
						},
						failure : function(form, action) {
							var msg = action.result.msg;
							Ext.MessageBox.alert('提示', msg);
						}
					});
				}
			}
		},{
			text : '关 闭 ',
			iconCls : 'stop',
			handler : function() {
				wechatActivityLotteryDetailWindow.hide();
			}
		}]
	});
	
	var wechatActivityLotteryWindow = new Ext.Window({
		title : '修改', // 窗口标题
		layout : 'fit',
		width : 700,
		height : 460,
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
		items: [wechatActivityLotteryForm]
	});
	
	var wechatActivityLotteryDetailWindow = new Ext.Window({
		title : '修改',
		layout : 'fit',
		width : 580,
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
		items: [wechatActivityLotteryDetailForm]
	});
	
	var typeRenderer = function(v){
		var record = parent.GRechargeTypeStore.findRecord("value", v);
		return (Ext.isEmpty(record)) ? "" : record.data.text;
	};
	
  	var wechatActivityLotteryColumns= [wechatActivityLotteryRowNum,
	            {header : 'ID',dataIndex: 'id', width: 60, hidden: true},
	            {header : '活动编码',dataIndex: 'activityCode', width: 100},
	            {header : '活动标题',dataIndex: 'activityTitle', width: 150},
	            {header : '活动类型',dataIndex: 'activityType', width: 80, renderer: function(v){if(v==0) return '砸金蛋';if(v==1) return '大转盘';}},
	            {header : '活动状态',dataIndex: 'activityStatus', width: 80, renderer: function(v){return (v == 0) ? '无效': '有效'}},
	            //{header : '赠送类型',dataIndex: 'freeAddType', width: 80},
	            //{header : '赠送次数',dataIndex: 'freeAddNum', width: 80},
	            //{header : '抽奖次数',dataIndex: 'freeNum', width: 80},
	            //{header : '有偿消耗类型',dataIndex: 'paidConsumeType', width: 100},
	            //{header : '有偿消耗额度',dataIndex: 'paidConsumeLimit', width: 100},
	            {header : '活动开始日期',dataIndex: 'bDate', width: 110},
	            {header : '活动结束日期',dataIndex: 'eDate', width: 110},
	            {header : '活动说明',dataIndex: 'activityDescription', width: 150},
	            {header : '备注',dataIndex: 'remark', width: 150},
	            {header : '公司ID',dataIndex: 'companyId', width: 100},
	            {header : '公司名称',dataIndex: 'companyName', width: 150},
	            {header : '部门编码',dataIndex: 'deptCode', width: 150},
	            {header : '部门名称',dataIndex: 'deptName', width: 150},
	            {header : '创建人',dataIndex: 'createBy', width: 80},
	            {header : '修改人',dataIndex: 'updateBy', width: 80},
	            {header : '创建时间',dataIndex: 'createTime', width: 150},
	            {header : '修改时间',dataIndex: 'updateTime', width: 150}
	            
	  	];
  	
  	var wechatActivityLotteryDetailColumns= [wechatActivityLotteryDetailRowNum,
 	            {header : 'ID',dataIndex: 'id', hidden: true},
 	            {header : '活动编码',dataIndex: 'activityCode', width: 100},
 	            {header : '奖品等级',dataIndex: 'prizeLevel', width: 80, renderer: function(v){if(v==0) return '特等奖';if(v==1) return '一等奖';if(v==2) return '二等奖';if(v==3) return '三等奖';if(v==4) return '四等奖';if(v==5) return '五等奖';if(v==6) return '六等奖';if(v==7) return '七等奖';if(v==8) return '八等奖';if(v==9) return '九等奖';if(v==10) return '十等奖';}},
 	            {header : '奖项名称',dataIndex: 'levelName', width: 80},
 	            {header : '奖项数量',dataIndex: 'prizeNum', width: 80},
 	            {header : '已中奖数量',dataIndex: 'lotteryPrizeNum', width: 80},
 	            {header : '奖品描述',dataIndex: 'prizeDesc', width: 80},
 	            {header : '奖项类型',dataIndex: 'prizeType', width: 80, renderer: function(v){if(v==0) return '实物奖品';if(v==1) return '积分';if(v==2) return '套餐';}},
 	            {header : '中奖数量',dataIndex: 'prizeWin', width: 80},
 	            {header : '中奖概率',dataIndex: 'prizeRate', width: 80},
 	            {header : '奖品编码',dataIndex: 'prizeCode', width: 80},
 	            {header : '创建人',dataIndex: 'createBy', width: 80},
	            {header : '修改人',dataIndex: 'updateBy', width: 80},
	            {header : '创建时间',dataIndex: 'createTime', width: 150},
	            {header : '修改时间',dataIndex: 'updateTime', width: 150}
 	  	];
  	
	var wechatActivityLotteryTbar = Ext.create('Ext.toolbar.Toolbar', {
			region : 'north',
			border : false,
	        items: [{
	        	xtype: 'button',
	        	id: 'insert',
	        	text: '新建',
	        	iconCls: 'add',
	        	//hidden : !parent.haveActionMenu('Add'),
	        	handler: function() {
	        		wechatActivityLotteryDetailWindow.hide();
	        		wechatActivityLotteryWindow.setTitle('新建');
	        		wechatActivityLotteryForm.getForm().reset();
	        		wechatActivityLotteryForm.getForm().url = '/wechat/act/insertWechatActivityLottery.atc';
	        		wechatActivityLotteryWindow.show();
	            }
	        },{
	        	xtype: 'button',
	        	id: 'update',
	        	text: '修改',
	        	iconCls: 'pencil',
	        	//hidden : !parent.haveActionMenu('Edit'),
	        	handler: function() {
	        		initWechatActivityLotteryEdit();
	            }
	        },{
	        	xtype: 'button',
	        	id: 'delete',
	        	text: '删除',
	        	iconCls: 'delete', 
	        	//hidden : !parent.haveActionMenu('Del'),
	        	handler: function(){
	        		initWechatActivityLotteryDelete();
	            }
	        }]
	    });
	
	var wechatActivityLotteryDetailTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	id: 'detailInsert',
        	text: '新建',
        	iconCls: 'add',
        	//hidden : !parent.haveActionMenu('AddDetail'),
        	handler: function() {
        		initDetailInsert();
            }
        },{
        	xtype: 'button',
        	id: 'hourUpdate',
        	text: '修改',
        	iconCls: 'pencil',
        	//hidden : !parent.haveActionMenu('EditDetail'),
        	handler: function() {
        		initDetailEdit();
            }
        },{
        	xtype: 'button',
        	id: 'hourDelete',
        	text: '删除',
        	iconCls: 'delete', 
        	//hidden : !parent.haveActionMenu('DelDetail'),
        	handler: function(){
        		initDetailDelete();
            }
        }]
    });
	
	var wechatActivityLotterySelModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1,
		mode: 'SINGLE'
	});
	
	var wechatActivityLotteryDetailSelModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1,
		mode: 'SINGLE'
	});
	
	var wechatActivityLotteryGrid = new Ext.grid.GridPanel({
			columns : wechatActivityLotteryColumns,
			store : wechatActivityLotteryStore, 
			region : 'center',
			autoScroll : true,
			columnLines : true,
			selModel : wechatActivityLotterySelModel,
			tbar : wechatActivityLotteryTbar, 
			stripeRows:true,
			//forceFit: true,
			viewConfig : {
				enableTextSelection:true	//设置单元格可以选择
			},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			},
			listeners:{
		        itemclick: function(grid, record, item, index, e) {
		        	activityCode = record.data.activityCode;
		        	wechatActivityLotteryDetailStore.load({
						params: {
							'wechatActivityLottery.activityCode' : record.data.activityCode
						}
					});
		        }
		    }
		});
	
	var wechatActivityLotteryDetailGrid = new Ext.grid.GridPanel({
		columns : wechatActivityLotteryDetailColumns,
		store : wechatActivityLotteryDetailStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		selModel : wechatActivityLotteryDetailSelModel,
		tbar : wechatActivityLotteryDetailTbar, 
		stripeRows:true,
		//forceFit: true,
		viewConfig : {
			enableTextSelection:true	//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
	});
	
	//页面初始布局
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [{
			title : '抽奖活动',
			iconCls : 'tag_blue',
			tools : [{
				type:'refresh',
				handler : function() {
					wechatActivityLotteryDetailStore.reload();
				}
			}],
			collapsible : true,
			height : '40%',
			split : true,
			region : 'north',
			layout : 'fit',
			border : false,
			items : [ wechatActivityLotteryGrid ]
		}, {
			title : '奖品等级',
			iconCls : 'bricks',
			tools : [{
				type:'refresh',
				handler : function() {
					wechatActivityLotteryDetailStore.reload();
				}
			}],
			region : 'center',
			split : true,
			layout : 'fit',
			border : false,
			items : [ wechatActivityLotteryDetailGrid ]
		}]
	});
	
	wechatActivityLotteryStore.load();
	
	function initWechatActivityLotteryEdit() {
		var record = wechatActivityLotterySelModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '你没有选中任何项目！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
		    });
			return;
		}
		wechatActivityLotteryDetailWindow.hide();
		wechatActivityLotterySelModel.deselectAll();
		wechatActivityLotteryWindow.setTitle('修改');
		wechatActivityLotteryForm.getForm().reset();
		wechatActivityLotteryForm.loadRecord(record);
		wechatActivityLotteryForm.getForm().url = '/wechat/act/updateWechatActivityLottery.atc';
		wechatActivityLotteryWindow.show();
	}
	
	function initWechatActivityLotteryDelete() {
		var record = wechatActivityLotterySelModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '你没有选中任何项目！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
		    });
			return;
		}
		Ext.Msg.confirm('请确认', '删除这个抽奖活动吗?', function(btn, text) {
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : '/wechat/act/deleteWechatActivityLottery.atc',
					params : {
						'wechatActivityLottery.id' : record.raw.id
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							wechatActivityLotteryWindow.hide();
							Ext.example.msg('提示', result.msg);
							wechatActivityLotteryStore.reload();
							wechatActivityLotteryDetailStore.reload();
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
	
	function initDetailInsert() {
		var record = wechatActivityLotterySelModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '你没有选中任何活动！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
		    });
			return;
		}
		wechatActivityLotteryWindow.hide();
		wechatActivityLotteryDetailWindow.setTitle('新建');
		wechatActivityLotteryDetailForm.getForm().reset();
		Ext.getCmp("d_activityCode").setValue(activityCode);
		wechatActivityLotteryDetailForm.getForm().url = '/wechat/act/insertWechatActivityLotteryItem.atc';
		wechatActivityLotteryDetailWindow.show();
	}
	
	function initDetailEdit() {
		var record = wechatActivityLotteryDetailSelModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '你没有选中任何项目！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
		    });
			return;
		}
		wechatActivityLotteryWindow.hide();
		wechatActivityLotteryDetailSelModel.deselectAll();
		wechatActivityLotteryDetailWindow.setTitle('修改');
		wechatActivityLotteryDetailForm.getForm().reset();
		wechatActivityLotteryDetailForm.loadRecord(record);
		wechatActivityLotteryDetailForm.getForm().url = '/wechat/act/updateWechatActivityLotteryItem.atc';
		wechatActivityLotteryDetailWindow.show();
	}
	
	function initDetailDelete() {
		var record = wechatActivityLotteryDetailSelModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
		           title: '提示',
		           msg: '你没有选中任何项目！',
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
		    });
			return;
		} 
		Ext.Msg.confirm('请确认', '确定要删除这个奖项吗?', function(btn, text) {
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : '/wechat/act/deleteWechatActivityLotteryItem.atc',
					params : {
						'wechatActivityLotteryItem.id' : record.data.id
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							wechatActivityLotteryWindow.hide();
							Ext.example.msg('提示', result.msg);
							wechatActivityLotteryDetailSelModel.deselectAll();
							wechatActivityLotteryDetailStore.reload();
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
});