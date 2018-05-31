Ext.onReady(function() {
	
	Ext.QuickTips.init();
	var selectedIds = new Array();
	var memId;
	
	var memberRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var memberStore = Ext.create('Ext.data.Store', {
		pageSize : 20,
		fields: ['memId','companyId', 'deptCode', 'vipNo', 'name', 'mobile', 'sex', 'grade', 'gradeName', 'appId', 'wechatNo', 'picture', 'carList', 
		           'wechatNick', 'wehcatHead', 'source', 'balance', 'point', 'birthday', 'createTime', 'sales', 'remark'],
		proxy: {
			type: 'ajax',
			url : '/member/queryMemberPage.atc',
			reader : {
			    root: 'memberList',
			    totalProperty: 'totalCount'
			}
		}
	});
	
	var gradeStore = Ext.create('Ext.data.Store', {
		autoLoad: true,
        fields: ['id', 'grade', 'name'],
        proxy: {
            type: 'ajax',
            url : '/member/queryGradeByDeptCode.atc',
            reader : {
                root: 'gradeList'
            }
        }
    });
	
	var picRenderer = function(value, metaData, record) {
		if(value){
  			metaData.tdAttr = "data-qtip=\"<img src='/upload/member/" + value + "' style='width:350px'/>\""; 
  			return '<img src="/upload/member/' + value + '" style="height:30px" onerror="this.src=\'/resources/images/noImage.jpg\'" />';
  		} else
  			return '<img src="/resources/images/noImage.jpg" style="height:30px" />';
    };
    
    var carsRenderer = function(value, cellmeta, record) {
    	var result = "";
    	if(Ext.isArray(value)){
    		Ext.Array.forEach(value,function(car, index, array){
    			result += car.carShort + car.carCode + car.carNumber + "<br>";
    	    }); 
    	}
//    	record.data.carList = result;
    	return result;
    };
	
    var brandStore = Ext.create('Ext.data.Store', {
        pageSize: 10,
    	autoLoad: true,
		fields : [{name: 'id'}, {name: 'shortCode'}, {name: 'brandName'}],  
		proxy:{
            type: 'ajax',
            url : '/base/queryCarBrandPage.atc',
            reader : {
                root: 'carBrandList',
                totalProperty: 'totalCount'
            }
        }
    });
    
    var brandCombo2 = Ext.create('Ext.form.ComboBox', {
		store: brandStore,
        fieldLabel: '车辆品牌',
        labelWidth: 70,
        labelAlign:'right',
        name: 'car.carBrand',
        displayField: 'brandName',
        valueField: 'id',
        queryParam: 'carBrand.shortCode',	//搜索参数字段
        emptyText: '输入车辆品牌首字母组合',
        typeAhead: false,
        hideTrigger:true,
        minChars: 1,
        listConfig: {
            loadingText: '正在查找...',
            emptyText: '<div style="padding: 5px;">没有找到对应的品牌！</div>',
            getInnerTpl: function() {
                return '<span>{shortCode} -> {brandName}</span>';
            }
        },
        pageSize: 10
	});
    
	var memberColumns= [memberRowNum,
            {header : 'ID',dataIndex: 'memId', width: 60, hidden: true},
            {header : '图片',dataIndex: 'picture', width: 70, align:'center', renderer: picRenderer},
            {header : '会员卡号',dataIndex: 'vipNo', width: 80},
            {header : '微信号',dataIndex: 'wechatNo', hidden: true},
            {header : '姓名',dataIndex: 'name', width: 90},
            {header : '手机号码',dataIndex: 'mobile', width: 110},
            {header : '车辆',dataIndex: 'carList', width: 80, renderer: carsRenderer},
            {header : '详情',xtype: 'actioncolumn',menuDisabled: true, sortable: false,width: 40, align: 'center',
            	items: [{
            		iconCls: 'application_view_list',
            		tooltip: '车辆详情',
            		scope: this,
                    handler: function(grid, rowIndex){
                    	var record = memberStore.getAt(rowIndex);
                    	memId = record.data.memId;
                    	carWindow.hide();
                    	carWindow.show();
                    }
            	}]
            },
            {header : '性别',dataIndex: 'sex', width: 50, renderer: function(v){return (v == 1) ? '男': (v==2 ? '女' : '未知');}},
            {header : '等级',dataIndex: 'grade', width: 60, hidden: true},
            {header : '等级',dataIndex: 'gradeName', width: 80},
            {header : '余额',dataIndex: 'balance', width: 60},
            {header : '积分',dataIndex: 'point', width: 60},
            {header : '生日',dataIndex: 'birthday', width: 80},
            {header : '来源',dataIndex: 'source', width: 80},
            {header : '销售顾问',dataIndex: 'sales', width: 60},
            {header : '备注',dataIndex: 'remark', width: 100},
            {header : '创建日期',dataIndex: 'createTime', width: 130}
            
  	];
	
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
        name: 'orderItem.memId',
        valueField: 'memId',
        displayField: 'vipNo',
        fieldLabel: '会员',
        labelWidth: 70,
		labelAlign: 'right',
        typeAhead: false,
        hideTrigger:true,
        width: 190,
        queryParam: 'keyword',	//搜索参数字段
        minChars: 2,
        emptyText: '输入车牌号码或手机号码后4位',
        pageSize: 10,
        listConfig: {
            loadingText: '查找中...',
            emptyText: '<div style="padding: 5px;">没有找到相关车主！</div>',
            getInnerTpl: function() {
                return '<div>{fullCarNumber}/{name}/{mobile}</div>';
            }
		}
	});
	
	var gradeCombo = Ext.create('Ext.form.ComboBox', {
		store: gradeStore,
        enforceMaxLength: true,
        width: 120,
        listConfig: {
            minWidth: null
        },
        fieldLabel: '等级',
        labelWidth: 70,
		labelAlign: 'right',
        valueField: 'grade',
        displayField: 'name',
        queryMode: 'local',
        editable : true
	});
	
	var salesStore = Ext.create('Ext.data.Store', {
		autoLoad: true,
        fields: ['id', 'name'],
        proxy: {
            type: 'ajax',
            url : '/shop/queryEmployeeByDept.atc',
            reader : {
                root: 'employeeList'
            }
        }
	});
	
	var salesCombo2 = Ext.create('Ext.form.ComboBox', {
		id: 'sales',
		flex: 1,
		triggerAction : 'all',
		fieldLabel: '销售顾问',
		labelWidth:70, 
		labelAlign:'right',
		mode : 'local',
		store: salesStore,
		valueField : 'name',
		displayField : 'name',
		editable : true
	});
	
	var sourceStore = Ext.create('Ext.data.Store', {
		autoLoad: true,
        fields: ['deptName'],
        proxy: {
            type: 'ajax',
            url : '/sys/querySysDeptByCompany.atc',
            reader : {
                root: 'deptList'
            }
        }
	});
	
	var sourceCombo = Ext.create('Ext.form.ComboBox', {
		id: 'source',
		name: 'member.sales',
		flex: 1,
		triggerAction : 'all',
		fieldLabel: '来源门店',
		labelWidth:70, 
		labelAlign:'right',
		mode : 'local',
		store: sourceStore,
		emptyText: '默认为本店',
		valueField : 'deptName',
		displayField : 'deptName',
		editable : true
	});
	
	var searchForm = new Ext.form.FormPanel({
		id : 'searchForm',
		layout: 'anchor',
		defaults: {
            anchor: '100%',
            xtype: 'fieldcontainer',
	        layout: 'hbox'
        },
		bodyPadding: '5 10 0 0',
		border : false,
        items : [{
		    	 defaults: {flex: 1,xtype:'textfield',labelWidth:70,labelAlign:'right'},
		    	 items: [MemberCombo] 
		     },{
				 defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'right'},
				 items: [
				         {id:'s_name', name: 'member.birthday', fieldLabel: '姓名'},
				         gradeCombo
				 ]
		     },{
		    	 defaults: {flex: 1, xtype:'numberfield', labelWidth:70, labelAlign:'right'},
		    	 items: [
		    	         {id: 's_startBalance', fieldLabel: '余额从'},
				         {id: 's_endBalance', fieldLabel: '余额到'}
		    	 ]
		     },{
		    	 defaults: {flex: 1, xtype:'numberfield', labelWidth:70, labelAlign:'right'},
		    	 items: [brandCombo2]
		     },{
		    	 defaults: {flex: 1, labelWidth:70, labelAlign:'right'},
		    	 items: [salesCombo2, sourceCombo]
			}
		],
		buttons:[{
			text: ' 查 询 ',
        	iconCls: 'preview',
			handler : function() {
        		memberStore.load({
        			params:{
        				start : 0,
        				limit : bbar.pageSize
        			}
	    		});
        		searchWindow.hide();
			}
		},{
			text : ' 重 置 ',
			iconCls : 'arrow_rotate_anticlockwise',
			handler : function() {
				searchForm.getForm().reset();
			}
		},{
			text : '关 闭 ',
			iconCls : 'stop',
			handler : function() {
				searchWindow.hide();
			}
		}]
	});
	
	var searchWindow = new Ext.Window({
		title : '查找会员', 
		layout : 'fit',
		width : 450,
		height : 250,
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
		items : [searchForm]
	});
	
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
				value:'<a href="/mgr/common/template/会员导入模板.xls">会员导入模板.xls</a>'
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
					msg: '会员信息导入中 ...',
					width: 200,
					progress: true,
					closable: false
		        });
				importForm.form.submit({
					url : '/member/importMember.atc',
					waitTitle : '提示',
					method : 'POST',
					waitMsg : '正在处理数据,请稍候...',
					success : function(form, action) {
						Ext.MessageBox.hide();
						importWindow.hide();
						Ext.example.msg("提示", action.result.msg + "");
						memberStore.load({
							params:{
								start : 0,
								limit : bbar.pageSize
							}
		        		});					
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
	
	var memberTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
//                MemberCombo, {
//        	xtype: 'textfield',
//        	id: 'memName',
//        	emptyText: '姓名',
//        	width: 120
//        },gradeCombo, {
        	xtype: 'button',
        	id: 'search',
        	text: ' 查 询 ',
        	iconCls: 'preview', 
        	handler: function(){
//        		memberStore.load({
//        			params : getParams()
//        		});
        		searchWindow.show();
            }
        },{
        	xtype: 'button',
        	id: 'Add',
        	text: ' 新 建 ',
        	iconCls: 'add', 
        	hidden : !parent.haveActionMenu('Add'),
        	handler: function(){
        		areaShortCombo.setVisible(true);
        		areaCodeCombo.setVisible(true);
        		brandCombo.setVisible(true);
        		memberForm.getForm().url = '/member/createMember.atc';
        		Ext.getCmp("number").setVisible(true);
        		memberForm.getForm().reset();
        		memberWindow.show();
        	}
        },{
        	xtype: 'button',
        	id: 'Edit',
        	text: ' 修 改 ',
        	iconCls: 'pencil', 
        	hidden : !parent.haveActionMenu('Edit'),
        	handler: function(){
        		initEdit();
        	}
        },{
        	xtype: 'button',
        	id: 'Del',
        	text: ' 删 除 ',
        	iconCls: 'delete', 
        	hidden : !parent.haveActionMenu('Del'),
        	handler: function(){
        		initDelete();
        	}
        },{
        	xtype: 'button',
        	id: 'Send',
        	text: '短信群发',
        	iconCls: 'phone', 
        	hidden : !parent.haveActionMenu('Send'),
        	handler: function(){
        		smsWindow.show();
        	}
        },'->',{
        	xtype: 'button',
        	id: 'import',
        	text: '批量导入',
//        	hidden : !parent.haveActionMenu('Import'),
        	iconCls: 'page_excel', 
        	handler: function(){
        		importWindow.show();
        	}
        },{
        	xtype: 'button',
        	text:'导出Excel',
        	iconCls : 'page_white_excel',
        	handler: function() {
        		memberGrid.downloadExcelXml();
        	}
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
		memberStore.pageSize = parseInt(comboBox.getValue());
		var params = getParams();
		params.start = 0;
		params.limit = bbar.pageSize;
		memberStore.reload({
			params:{
				start : 0,
				limit : bbar.pageSize
			}
		});
	});
	
	var bbar = new Ext.PagingToolbar({
		pageSize : number,
		store : memberStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesizeCombo ]
	});
	
	var selModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1,
//		mode: 'SINGLE'
		listeners : {
			selectionchange : function(sm, selections) {
				var rows = sm.getSelection();
				selectedIds = new Array();
        		for(var i = 0; i < rows.length; i++){
        			Ext.Array.clean(selectedIds);
        			if(!Ext.Array.contains(selectedIds,rows[i].data.memId)){
        				selectedIds.push(rows[i].data.memId);
        			}
        		}
			}
		}
	});
	
	var memberGrid = new Ext.grid.GridPanel({
		title: '客户信息',
		iconCls: 'user',
		columns : memberColumns,
		store : memberStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		selModel : selModel,
		tbar : memberTbar, 
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
//		},
//		listeners:{
//	        itemclick: function(grid, record, item, index, e) {
//	        	
//	        }
	    }
	});
	
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [memberGrid]
	});
	
	
	
	var salesCombo = Ext.create('Ext.form.ComboBox', {
		id: 'sales',
		name: 'member.sales',
		flex: 1,
		triggerAction : 'all',
		fieldLabel: '销售顾问',
		labelWidth:70, 
		labelAlign:'right',
		mode : 'local',
		store: salesStore,
		valueField : 'name',
		displayField : 'name',
		editable : false
	});
	
	var areaShortCombo = Ext.create('Ext.form.ComboBox', {
		id: 'areaShortCombo',
		name: 'car.carShort',
		flex: 4,
		allowBlank: false, 
		fieldLabel: '车牌号码',
		labelWidth: 70,
		labelAlign: 'right',
		triggerAction : 'all',
		mode : 'local',
		queryMode: 'local',
		store: parent.GAreaShortStore,
		valueField : 'areaShort',
		displayField : 'areaShort',
		value: parent.II,
		editable : false,
		width : 85
	});
	
	var areaCodeCombo = Ext.create('Ext.form.ComboBox', {
		id: 'areaCodeCombo',
		name: 'car.carCode',
		flex: 2,
		allowBlank: false, 
		triggerAction : 'all',
		mode : 'local',
		queryMode: 'local',
		store: parent.GAreaCodeStore,
		valueField : 'areaCode',
		displayField : 'areaCode',
		value: parent.JJ,
		editable : false,
		width : 85
	});
	
    var brandCombo = Ext.create('Ext.form.ComboBox', {
		store: brandStore,
        fieldLabel: '品牌',
        labelWidth: 70,
        labelAlign:'right',
        name: 'car.carBrand',
        displayField: 'brandName',
        valueField: 'id',
        queryParam: 'carBrand.shortCode',	//搜索参数字段
        typeAhead: false,
        hideTrigger:true,
        minChars: 1,
        listConfig: {
            loadingText: '正在查找...',
            emptyText: '<div style="padding: 5px;">没有找到对应的品牌！</div>',
            getInnerTpl: function() {
                return '<span>{shortCode} -> {brandName}</span>';
            }
        },
        pageSize: 10
	});
    
    var gradeFormCombo = Ext.create('Ext.form.ComboBox', {
    	id: 'grade',
		name: 'member.grade',
		fieldLabel: '等级',
        labelWidth: 70,
        labelAlign:'right',
		store: gradeStore,
		allowBlank: false,
        enforceMaxLength: true,
        width: 120,
        listConfig: {
            minWidth: null
        },
        valueField: 'grade',
        displayField: 'name',
        queryMode: 'local',
        editable : true
	});
	
	var memberForm = new Ext.form.FormPanel({
		id : 'memberForm',
		layout: 'anchor',
		defaults: {
            anchor: '100%',
            xtype: 'fieldcontainer',
	        layout: 'hbox'
        },
		bodyPadding: '5 10 0 0',
		border : false,
		items : [{
		         defaults: {flex: 1,xtype:'textfield',labelWidth:70,labelAlign:'right'},
		         items: [
						areaShortCombo, areaCodeCombo,
	                    {id:'number', name: 'car.carNumber', maxLength: 5, flex: 4},
						{id:'memId', name:'member.memId',xtype:'hiddenfield'},
						{id:'companyId', name:'member.companyId',xtype:'hiddenfield'},
						{id:'deptCode', name:'member.deptCode',xtype:'hiddenfield'},
				        {id:'vipNo', name:'member.vipNo',xtype:'hiddenfield'},
				        {id:'point', name:'member.point',xtype:'hiddenfield'},
				        {id:'source', name:'member.source',xtype:'hiddenfield'},
				        {id:'wehcatHead', name:'member.wehcatHead',xtype:'hiddenfield'},
				        {id:'wechatNo', name:'member.wechatNo',xtype:'hiddenfield'},
				        {id:'wechatNick', name:'member.wechatNick',xtype:'hiddenfield'},
				        {id:'appId', name:'member.appId',xtype:'hiddenfield'}
		         ]
		     },brandCombo,{
		         defaults: {flex: 1,xtype:'textfield',labelWidth:70,labelAlign:'right'},
		         items: [
	                    {id:'name', name: 'member.name', allowBlank: false, fieldLabel: '姓名', blankText:'名称不能为空!'},
	                    {id:'mobile', name: 'member.mobile', maxLength: 11, fieldLabel: '电话号码'}
		         ] 
		     },{
		    	 defaults: {flex: 1,xtype:'textfield',labelWidth:70,labelAlign:'right'},
		    	 items: [
		    	         gradeFormCombo,
		    	         {id:'balance', name: 'member.balance', fieldLabel: '余额'}
		    	 ] 
		     },{
					defaults: {flex: 1, xtype:'datefield', labelWidth:70, labelAlign:'right'},
					items: [
					        {id:'birthday', name: 'member.birthday', fieldLabel: '生日', format: 'Y/m/d'},
					        {id:'createTime', name: 'member.createTime', xtype:'textfield', fieldLabel: '加入时间', format: 'Y/m/d', readOnly: true}
					]
		     },{
				 defaults: {flex: 1,xtype:'radiogroup',labelWidth:70,labelAlign:'right'},
				 items: [
				        {id:'sex', fieldLabel: '性别', columns:2, defaults: {name: 'member.sex'},
				        	items: [{inputValue: '1', boxLabel: '男', checked: true}, {inputValue: '2', boxLabel: '女'}]
				        },salesCombo
				 ]
		     },{
				defaults: {flex: 1, xtype:'textarea', labelWidth:70, labelAlign:'right'},
				items: [
				        {id: 'remark', name: 'member.remark', rows: 3, fieldLabel: '备注'}
				]
			}
		],
		buttons:[{
			text : '确 定',
			iconCls : 'accept',
			handler : function() {
				if (memberForm.form.isValid()) {
					if(isNaN(brandCombo.getValue())){
						Ext.MessageBox.show({
 				           	title: '提示',
 				           	msg: '请输入品牌首字母选择汽车品牌！',
 				           	buttons: Ext.MessageBox.OK,
 				           	icon: Ext.MessageBox.ERROR
                        });
					} else {
						memberForm.form.submit({
							waitTitle : '提示',
							method : 'POST',
							waitMsg : '正在处理数据,请稍候...',
							success : function(form, action) {
								memberForm.getForm().reset();
								memberWindow.hide();
								memberStore.reload();
								Ext.example.msg("提示", "会员" + action.result.msg + "");
//								global.openTabByMenuId('010301', "vip=" + action.result.msg);
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
			}
		},{
			text : '关 闭 ', // 按钮文本
			iconCls : 'stop', // 按钮图标
			handler : function() { // 按钮响应函数
				memberWindow.hide();
			}
		}]
	});
	
	var memberWindow = new Ext.Window({
		title : '创建车主', 
		layout : 'fit',
		width : 450,
		height : 330,
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
		items : [memberForm]
	});
	
	memberStore.on('beforeload', function() {
		Ext.apply(memberStore.proxy.extraParams, getParams());
	});
	
	memberStore.load({
		params:{
			start : 0,
			limit : bbar.pageSize
		}
	});
	
	
	
	
	var carStore = Ext.create('Ext.data.Store', {
		fields: ['id', 'memberId', 'carShort', 'carCode', 'carNumber', 'carBrand', 'brandName', 'carSeries', 'carModel', 'carFrame', 'carEngine', 
		           'carKilometers', 'carInspection', 'carMaintain', 'carInsurance', 'carInsuCompany', 'carIllegal', 'carMobile'],
		proxy: {
			type: 'ajax',
			url : '/member/queryCarByMemberId.atc',
			reader : {
			    root: 'carList'
			}
		}
	});
	
	var carRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var numberRenderer = function(value, metaData, record) {
		return record.data.carShort + record.data.carCode + record.data.carNumber;
    };
	
	var carColumns= [carRowNum,
	            {header : 'ID',dataIndex: 'id', hidden: true},
	            {header : '会员ID',dataIndex: 'memberId', hidden: true},
	            {header : '车牌号',dataIndex: 'carShort', hidden: true},
	            {header : '车牌号',dataIndex: 'carCode', hidden: true},
	            {header : '车牌号',dataIndex: 'carNumber', renderer: numberRenderer},
	            {header : '品牌',dataIndex: 'carBrand', hidden: true},
	            {header : '品牌',dataIndex: 'brandName',width: 80},
	            {header : '系列',dataIndex: 'carSeries', width: 80},
	            {header : '型号',dataIndex: 'carModel', width: 160},
	            {header : '手机号',dataIndex: 'carMobile'},
	            {header : '车架号',dataIndex: 'carFrame'},
	            {header : '发动机号',dataIndex: 'carEngine'},
	            {header : '进厂公里数',dataIndex: 'carKilometers'},
	            {header : '年检时间',dataIndex: 'carInspection'},
	            {header : '下次保养日期',dataIndex: 'carMaintain'},
	            {header : '保险时间',dataIndex: 'carInsurance'},
	            {header : '保险公司',dataIndex: 'carInsuCompany'},
	            {header : '违章',dataIndex: 'carIllegal'}
	  	];
	
	var carTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	id: 'AddCar',
        	text: ' 新 增 ',
        	iconCls: 'add', 
        	hidden : !parent.haveActionMenu('AddCar'),
        	handler: function(){
//        		LoadAreaInfo(parent.EE);
				carForm.getForm().reset();
				carFormWindow.hide();
				carFormWindow.setTitle("添加车辆");
				Ext.getCmp("memberId").setValue(memId);
				carForm.getForm().url = "/member/insertCar.atc";
        		carFormWindow.show();
        	}
        },{
        	xtype: 'button',
        	id: 'EditCar',
        	text: ' 修 改 ',
        	iconCls: 'pencil', 
        	hidden : !parent.haveActionMenu('EditCar'),
        	handler: function(){
        		initEditCar();
        	}
        },{
        	xtype: 'button',
        	id: 'DelCar',
        	text: ' 删 除 ',
        	iconCls: 'delete', 
        	hidden : !parent.haveActionMenu('DelCar'),
        	handler: function(){
        		initDeleteCar();
        	}
        }]
    });
	
	var selCarModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1,
		mode: 'SINGLE'
	});
	
	var carGrid = new Ext.grid.GridPanel({
		columns : carColumns,
		store : carStore, 
		region : 'center',
		selModel: selCarModel,
		autoScroll : true,
		columnLines : true,
		tbar : carTbar, 
		stripeRows:true,
		forceFit: true,
		viewConfig : {
			enableTextSelection:true	//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
	    }
	});
	
	var carWindow = new Ext.Window({
		title : '车辆详情', 
		layout : 'fit',
		width : '95%',
		height : 430,
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
		items : [carGrid]
	});
	
	carWindow.on('show', function(){
		carStore.load({
			params : {
				memberId : memId
			}
		});
	});
	
	var areaShortCombo2 = Ext.create('Ext.form.ComboBox', {
		id: 'areaShortCombo2',
		name: 'car.carShort',
		flex: 4,
		allowBlank: false, 
		fieldLabel: '车牌号码',
		labelWidth: 70,
		labelAlign: 'right',
		triggerAction : 'all',
		mode : 'local',
		queryMode: 'local',
		store: parent.GAreaShortStore,
		valueField : 'areaShort',
		displayField : 'areaShort',
		value: parent.II,
		editable : false,
		width : 85
	});
	
	var areaCodeCombo2 = Ext.create('Ext.form.ComboBox', {
		id: 'areaCodeCombo2',
		name: 'car.carCode',
		flex: 2,
		allowBlank: false, 
		triggerAction : 'all',
		mode : 'local',
		queryMode: 'local',
		store: parent.GAreaCodeStore,
		valueField : 'areaCode',
		displayField : 'areaCode',
		value: parent.JJ,
		editable : false,
		width : 85
	});
	
	
	var BrandStore = Ext.create('Ext.data.Store', {
		pageSize: 10,
		autoLoad: true,
		fields : [{name: 'id'}, {name: 'shortCode'}, {name: 'brandName'}],  
		proxy:{
            type: 'ajax',
            url : '/base/queryCarBrandPage.atc',
            reader : {
                root: 'carBrandList',
                totalProperty: 'totalCount'
            }
        }
    });
	
	var BrandCombo = Ext.create('Ext.form.ComboBox', {
		id: 'BrandCombo',
		fieldLabel: '品牌',
		flex: 1,
		store: BrandStore,
        labelWidth: 70,
        emptyText:'请选择...', 
        labelAlign:'right',
        name: 'car.carBrand',
        displayField: 'brandName',
        valueField: 'id',
        queryParam: 'carBrand.shortCode',	//搜索参数字段
        typeAhead: false,
        hideTrigger:true,
        minChars: 1,
        listConfig: {
            loadingText: '正在查找...',
            emptyText: '<div style="padding: 5px;">没有找到对应的品牌！</div>',
            getInnerTpl: function() {
                return '<span>{shortCode} -> {brandName}</span>';
            }
        }
	});
	
	var carForm = Ext.create('Ext.form.Panel', {
		id : 'carForm',
		layout: 'anchor',
		defaults: {
            anchor: '100%',
            xtype: 'fieldcontainer',
	        layout: 'hbox'
        },
		bodyPadding: '5 10 0 0',
		border : false,
		items : [{
		         defaults: {flex: 1,xtype:'textfield',labelWidth:70,labelAlign:'right'},
		         items: [
						areaShortCombo2, areaCodeCombo2,
						{id:'id', name: 'car.id', xtype:'hiddenfield'},
						{id:'memberId', name: 'car.memberId', xtype:'hiddenfield'},
	                    {id:'carNumber', name: 'car.carNumber', maxLength: 6, flex: 4, allowBlank: false, blankText:'车牌号不能为空!'}
		         ]
		     },{
		         items: [BrandCombo]
		     },{
		         defaults: {flex: 1,xtype:'textfield',labelWidth:70,labelAlign:'right'},
		         items: [
	                    {id:'carSeries', name: 'car.carSeries', fieldLabel: '系列'},
	                    {id:'carModel', name: 'car.carModel', fieldLabel: '型号'}
		         ] 
		     },{
		         defaults: {flex: 1,xtype:'textfield',labelWidth:70,labelAlign:'right'},
		         items: [
	                    {id:'carEngine', name: 'car.carEngine', fieldLabel: '发动机号'},
	                    {id:'carFrame', name: 'car.carFrame', fieldLabel: '车架号'}
		         ] 
		     },{
		    	 defaults: {flex: 1, xtype:'textfield',labelWidth:70,labelAlign:'right'},
		    	 items: [
		    	         {id:'carKilometers', name: 'car.carKilometers', fieldLabel: '进厂公里'},
		    	         {id:'carInspection', name: 'car.carInspection', xtype:'datefield', fieldLabel: '年检时间', format: 'Y/m/d'}
		    	 ]
		     },{
		    	 defaults: {flex: 1,xtype:'datefield',labelWidth:70,labelAlign:'right'},
		    	 items: [
		    	         {id:'carMaintain', name: 'car.carMaintain', fieldLabel: '保养日期', format: 'Y/m/d'},
		    	         {id:'carInsurance', name: 'car.carInsurance', fieldLabel: '保险时间', format: 'Y/m/d'}
		    	 ]
		     },{
		         defaults: {flex: 1,xtype:'textfield',labelWidth:70,labelAlign:'right'},
		         items: [
	                    {id:'carInsuCompany', name: 'car.carInsuCompany', fieldLabel: '保险公司'},
	                    {id:'carMobile', name: 'car.carMobile', fieldLabel: '手机号码'}
		         ]
		     }
		],
		buttons:[{
			text : '保 存',
			iconCls : 'accept',
			handler : function() {
				if (carForm.form.isValid()) {
					if(isNaN(BrandCombo.getValue())){
						Ext.MessageBox.show({
 				           	title: '提示',
 				           	msg: '请输入品牌首字母选择汽车品牌！',
 				           	buttons: Ext.MessageBox.OK,
 				           	icon: Ext.MessageBox.ERROR
                        });
					} else {
						carForm.form.submit({
							waitTitle : '提示',
							method : 'POST',
							waitMsg : '正在处理数据,请稍候...',
							success : function(form, action) {
								carFormWindow.hide();
								carForm.getForm().reset();
								Ext.example.msg("提示", action.result.msg);
								carStore.reload();
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
			}
		},{
			text : '关 闭 ',
			iconCls : 'stop',
			handler : function() {
				CarWindow.hide();
			}
		}]
	});
	
	var carFormWindow = new Ext.Window({
		layout : 'fit',
		width : 450,
		height : 330,
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
		items: [carForm]
	});
	
	
	
	var smsForm = new Ext.form.FormPanel({
		id : 'smsForm',
		layout: 'anchor',
		defaults: {
            anchor: '100%',
            xtype: 'fieldcontainer',
	        layout: 'hbox'
        },
		bodyPadding: '5 10 0 0',
		border : false,
		items : [{
			 	defaults: {flex: 1,xtype:'radiogroup',labelWidth:70,labelAlign:'right'},
				items: [
				        {fieldLabel: '发送方式', columns:3, id: 'sendType',defaults:{name: 'sendType'},
				        	items: [{inputValue: '1', boxLabel: '筛选会员', checked: true}, {inputValue: '2', boxLabel: '选中会员'}, {inputValue: '0', boxLabel: '所有会员'}],
				        	listeners:{
				                //通过change触发
				                change: function(g , newValue , oldValue){
				                	if(newValue.sendType == 1){
				                		salesCombo.setDisabled(false);
				                		Ext.getCmp("sendSex").setDisabled(false);
				                		Ext.getCmp("startBalance").setDisabled(false);
				                		Ext.getCmp("endBalance").setDisabled(false);
				                		Ext.getCmp("startPoint").setDisabled(false);
				                		Ext.getCmp("endPoint").setDisabled(false);
				                		Ext.getCmp("startBirth").setDisabled(false);
				                		Ext.getCmp("endBirth").setDisabled(false);
				                		Ext.getCmp("startCreate").setDisabled(false);
				                		Ext.getCmp("endCreate").setDisabled(false);
				                	} else {
				                		if(newValue.sendType == 2 && selectedIds == '')
				                			Ext.example.msg("提示", "当前没有选中任何会员！");
				                		salesCombo.setDisabled(true);
				                		Ext.getCmp("sendSex").setDisabled(true);
				                		Ext.getCmp("startBalance").setDisabled(true);
				                		Ext.getCmp("endBalance").setDisabled(true);
				                		Ext.getCmp("startPoint").setDisabled(true);
				                		Ext.getCmp("endPoint").setDisabled(true);
				                		Ext.getCmp("startBirth").setDisabled(true);
				                		Ext.getCmp("endBirth").setDisabled(true);
				                		Ext.getCmp("startCreate").setDisabled(true);
				                		Ext.getCmp("endCreate").setDisabled(true);
				                	}
				                }
				            }
				        }
				]
			},{
				defaults: {flex: 1,xtype:'radiogroup',labelWidth:70,labelAlign:'right'},
				items: [salesCombo,
				        {id: 'sendSex',fieldLabel: '性别', columns:3, defaults: {name: 'member.sex'},
				        	items: [{inputValue: '1', boxLabel: '男'}, {inputValue: '2', boxLabel: '女'}, {inputValue: '0', boxLabel: '未知'}]
				        }
				]
			},{
				defaults: {flex: 1,xtype:'numberfield',labelWidth:70,labelAlign:'right'},
				items: [
				        {id: 'startBalance',name: 'member.startBalance', fieldLabel: '余额从'},
				        {id: 'endBalance',name: 'member.endBalance', fieldLabel: '余额到'}
				 ] 
			},{
				defaults: {flex: 1,xtype:'numberfield',labelWidth:70,labelAlign:'right'},
				items: [
				        {id: 'startPoint',name: 'member.startPoint', fieldLabel: '积分从'},
				        {id: 'endPoint',name: 'member.endPoint', fieldLabel: '积分到'}
				 ] 
			},{
				defaults: {flex: 1, xtype:'datefield', labelWidth:70, labelAlign:'right'},
				items: [
				        {id: 'startBirth',name: 'member.startBirth', fieldLabel: '生日开始', format: 'Y/m/d'},
				        {id: 'endBirth',name: 'member.endBirth', fieldLabel: '到', format: 'Y/m/d'}
				]
			},{
				defaults: {flex: 1, xtype:'datefield', labelWidth:70, labelAlign:'right'},
				items: [
				        {id: 'startCreate',name: 'member.startCreate', fieldLabel: '加入时间', format: 'Y/m/d'},
				        {id: 'endCreate',name: 'member.endCreate', fieldLabel: '到', format: 'Y/m/d'}
				]
		    },{
				defaults: {flex: 1, xtype:'textarea', labelWidth:70, labelAlign:'right'},
				items: [
				        {name: 'keyword', rows: 4, fieldLabel: '短信内容',
				        	listeners: {
								change(textarea, newValue, oldValue) {
					            	Ext.getCmp("smsnumber").setValue("（共" + getByteLen(newValue) + "字），短信内容需带上店铺签名！");
					            }
				        	}
				        }
				]
		    },{
		    	defaults: {flex: 1, xtype:'displayfield', labelWidth:70, labelAlign:'right'},
		    	items: [
		    	        {id: 'smsnumber', fieldLabel: '短信字数', value: ''}
		    	]
			}
		],
		buttons:[{
			text : '发 送',
			iconCls : 'accept',
			handler : function() {
				smsForm.form.submit({
					waitTitle : '提示',
					method : 'POST',
					url: '/member/sendMemberSMS.atc',
					waitMsg : '正在处理数据,请稍候...',
					params : {
						idStr : selectedIds,
					},
					success : function(form, action) {
						smsForm.getForm().reset();
						smsWindow.hide();
						Ext.example.msg("提示", action.result.msg + "");
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
		},{
			text : '关 闭 ', // 按钮文本
			iconCls : 'stop', // 按钮图标
			handler : function() { // 按钮响应函数
				smsWindow.hide();
			}
		}]
	});
	
	var smsWindow = new Ext.Window({
		title : '短信群发', 
		layout : 'fit',
		width : 550,
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
		items : [smsForm]
	});
	
	
	function initSendSms(){
		smsWindow.show();
	}
	
	function getByteLen(val) {
        var len = 0;
        for (var i = 0; i < val.length; i++) {
           var length = val.charCodeAt(i);
           if(length>=0&&length<=128) {
                len += 1;
           } else {
                len += 1;
           }
        }
        return len;
    }
	
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
		memberWindow.setTitle('修改');
		areaShortCombo.setVisible(false);
		areaCodeCombo.setVisible(false);
		brandCombo.setVisible(false);
		Ext.getCmp("number").setVisible(false);
		memberForm.getForm().url = '/member/updateMember.atc';
		memberForm.loadRecord(record);
		memberWindow.show();
	}
	
	function initEditCar() {
		var record = selCarModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
	        	title: '提示',
	        	msg: '你没有选中任何项目！',
	        	buttons: Ext.MessageBox.OK,
	        	icon: Ext.MessageBox.INFO
		    });
			return;
		}
	   	BrandCombo.setValue(record.data.carBrand);
		selModel.deselectAll();
		carFormWindow.setTitle('修改');
		carForm.getForm().url = '/member/updateCar.atc';
		carForm.loadRecord(record);
		carFormWindow.show();
	}
	
	function initDelete() {
		if(selectedIds == ''){
			Ext.MessageBox.show({
				title: '提示',
				msg: '没有选中任何记录！',
				buttons: Ext.MessageBox.OK,
				icon: Ext.MessageBox.INFO
            });
		} else {
			Ext.Msg.confirm('请确认', '确认删除这' + selModel.getSelection().length + '个会员吗?', function(btn, text) {
				if(btn == 'yes'){
					Ext.Ajax.request({
						url : '/member/deleteMoreMember.atc',
						params : {
							idStr : selectedIds,
						},
						success : function(resp, opts) {
							var result = Ext.decode(resp.responseText);
							if(result.success){
								Ext.example.msg('提示', result.msg);
								memberStore.loadPage(memberStore.currentPage);
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
	}
	
	function initDeleteCar() {
		var record = selCarModel.getSelection()[0];
		if(Ext.isEmpty(record)) {
			Ext.MessageBox.show({
	        	title: '提示',
	        	msg: '你没有选中任何项目！',
	        	buttons: Ext.MessageBox.OK,
	        	icon: Ext.MessageBox.INFO
		    });
			return;
		}
		Ext.Msg.confirm('请确认', '确认删除这台车辆吗?', function(btn, text) {
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : '/member/deleteCar.atc',
					params : {
						'car.id' : record.data.id,
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							Ext.example.msg('提示', result.msg);
							carStore.reload();
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
	
	function getParams(){
		return {
			'member.memId': Ext.isNumber(MemberCombo.getValue())?MemberCombo.getValue():'',
			'member.name': Ext.getCmp("s_name").getValue(),
			'member.grade': gradeCombo.getValue(),
			'member.sales': salesCombo2.getValue(),
			'member.source': sourceCombo.getValue(),
			'member.carBrand': brandCombo2.getValue(),
			'member.startBalance': Ext.getCmp("s_startBalance").getValue(),
			'member.endBalance': Ext.getCmp("s_endBalance").getValue()
		};
	}
	
});