Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	var msgSendRecordRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var msgSendRecordStore = Ext.create('Ext.data.Store', {
		autoLoad: true,
		fields: ['id', 'deptCode', 'vipNo', 'mobile', 'content', 'status', 'createTime', 'updateTime', 'remark', 'missionID'],
		proxy: {
			type: 'ajax',
			url : '/msg/queryMsgSendRecordPage.atc',
			reader : {
			    root: 'msgSendRecordList',
			    totalProperty: 'totalCount'
			}
		}
	});
	
	var contentRender = function(value, metaData){
		if(value)
			metaData.tdAttr = "data-qtip=\"" + value + "\""; 
		return value;
  	};
	
	var msgSendRecordColumns= [msgSendRecordRowNum,
            {header : 'ID',dataIndex: 'id', width: 60, hidden: true},
            {header : '部门编码',dataIndex: 'deptCode', width: 50, hidden: true},
            {header : '会员号',dataIndex: 'vipNo', width: 65},
            {header : '手机号码',dataIndex: 'mobile', width: 90},
            {header : '短信内容',dataIndex: 'content', width: 200, renderer: contentRender},
            {header : '状态',dataIndex: 'status', width: 50, renderer: function(v){return (v == 0) ? '成功': '失败'}},
            {header : '发送时间',dataIndex: 'updateTime', width: 130},
            {header : '创建时间',dataIndex: 'createTime', width: 130},
            {header : '备注',dataIndex: 'remark', width: 80},
            {header : '任务ID',dataIndex: 'missionID', width: 50}
  	];
	
	var msgSendRecordTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
//        	xtype: 'textfield',
//        	id: 'vipNo',
//        	emptyText : '会员卡号',
//        },{
//        	xtype: 'textfield',
//        	id: 'name',
//        	emptyText : '姓名',
//        },{
//        	xtype: 'textfield',
//        	id: 'mobile',
//        	emptyText : '手机号码',
//        },{
//        	xtype: 'textfield',
//        	id: 'grade',
//        	emptyText : '等级',
//        },{
        	xtype: 'button',
        	id: 'search',
        	text: ' 查 询 ',
        	iconCls: 'preview', 
        	handler: function(){
        		msgSendRecordStore.reload({
        			params : {
        				start : 0,
        				limit : bbar.pageSize
        			}
        		});
            }
        },'->',{
        	xtype: 'displayfield',
        	id: 'sendCount',
        	fieldLabel: '成功发送', 
        	labelWidth: 60,
        	width: 130
        },{
        	xtype: 'displayfield',
        	id: 'remainCount',
        	fieldLabel: '剩余条数', 
        	labelWidth: 60,
        	width: 130
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
		msgSendRecordStore.pageSize = parseInt(comboBox.getValue());
		msgSendRecordStore.reload({
			params : {
				start : 0,
				limit : bbar.pageSize
			}
		});
	});
	
	var bbar = new Ext.PagingToolbar({
		pageSize : number,
		store : msgSendRecordStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesizeCombo ]
	});
	
	var msgSendRecordSelModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1,
		mode: 'SINGLE'
	});
	
	var msgSendRecordGrid = new Ext.grid.GridPanel({
		title: '短信发送记录',
		columns : msgSendRecordColumns,
		store : msgSendRecordStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		selModel : msgSendRecordSelModel,
		tbar : msgSendRecordTbar, 
		bbar : bbar,
		stripeRows:true,
		forceFit: true,
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
		items : [msgSendRecordGrid]
	});
	
	function loadMsgCharging() {
		Ext.Ajax.request({
			url : '/msg/queryMsgChargingByDept.atc',
			success : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
				if(result && !Ext.isEmpty(result)){
					Ext.getCmp('sendCount').setValue(result.sendNum + "条");
					Ext.getCmp('remainCount').setValue(result.remainingNum + "条");
				}
			},
			failure : function(resp, opts) {
                Ext.Msg.alert('提示', "店铺参数配置信息加载出错！");
			}
		});
	}
	
	loadMsgCharging();
	
});