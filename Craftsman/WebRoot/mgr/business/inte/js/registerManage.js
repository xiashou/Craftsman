Ext.require(['Ext.ux.DataTip']);
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	var LCIPOperatestate = Ext.create('Ext.data.ArrayStore', {
		fields : [ 'value', 'text' ],
		data : [ [ 1, '营业' ], [ 2, '停业' ], [ 3, '整改' ], [ 4, '停业整顿' ], [ 5, '歇业' ], [ 6, '注销' ], [ 9, '其他' ] ]
	});
	var LCIPOperatestateCombo = Ext.create('Ext.form.ComboBox', {
		id: 'operatestate',
		name: 'register.operatestate',
		triggerAction : 'all',
		mode : 'local',
		queryMode: 'local',
		store: LCIPOperatestate,
		valueField : 'value',
		displayField : 'text',
		fieldLabel: '企业经营状态',
		labelWidth:120,
		labelAlign:'right',
		editable : false
	});
	
	var LCIPlevel = Ext.create('Ext.data.ArrayStore', {
		fields : [ 'value', 'text' ],
		data : [ [ '01', '一类维修经营业务' ], [ '02', '二类维修经营业务' ], [ '03', '三类维修经营业务' ], [ '99', '其他' ] ]
	});
	var LCIPlevelCombo = Ext.create('Ext.form.ComboBox', {
		id: 'levels',
		name: 'register.levels',
		triggerAction : 'all',
		mode : 'local',
		queryMode: 'local',
		store: LCIPlevel,
		valueField : 'value',
		displayField : 'text',
		fieldLabel: '企业经营业务类别',
		labelWidth:120,
		labelAlign:'right',
		editable : false
	});
	
	var LCIPBusinessType = Ext.create('Ext.data.ArrayStore', {
		fields : [ 'value', 'text' ],
		data : [ [ '100', '内资' ], [ '110', '国有全资' ], [ '120', '集体全资' ], [ '130', '股份合作' ], [ '140', '联 营' ],
			[ '141', '国有联营' ], [ '142', '集体联营' ], [ '143', '国有与集体联营' ], [ '149', '其他联营' ], [ '150', '有限责任(公司)' ],
			[ '159', '其他有限责任(公司)' ], [ '160', '股份有限(公司)' ], [ '170', '私有' ], [ '171', '私有独资' ], [ '172', '私有合伙' ],
			[ '173', '私营有限责任(公司)' ], [ '174', '私营股份有限(公司)' ], [ '175', '个体经营' ], [ '179', '其他私有' ], [ '190', '其他内资' ],
			[ '200', '港、澳、台投资' ], [ '210', '内地和港、澳或台合资' ], [ '220', '内地和港、澳或台合作' ], [ '230', '港、澳或台独资' ],
			[ '240', '港、澳或台投资股份有限(公司)' ], [ '290', '其他港澳台投资' ], [ '300', '国外投资' ], [ '310', '中外合资' ],
			[ '320', '中外合作' ], [ '330', '外 资' ], [ '340', '国外投资股份有限(公司)' ], [ '390', '其他国外投资' ], [ '900', '其 他' ]]
	});
	var LCIPBusinessTypeCombo = Ext.create('Ext.form.ComboBox', {
		id: 'businesstype',
		name: 'register.businesstype',
		triggerAction : 'all',
		mode : 'local',
		queryMode: 'local',
		store: LCIPBusinessType,
		valueField : 'value',
		displayField : 'text',
		fieldLabel: '企业经济类型',
		labelWidth:120,
		labelAlign:'right',
		editable : false
	});
	
	Ext.define('Register', {
	    extend: 'Ext.data.Model',
	    fields: [
	    	{name: 'deptCode', type: 'string'},
	        {name: 'cmpyCompanycode', type: 'string'},
	        {name: 'companycode', type: 'string'},
	        {name: 'companyloginname', type: 'string'},
	        {name: 'companypassword', type: 'string'},
	        {name: 'name', type: 'string'},
	        {name: 'industryid', type: 'string'},
	        {name: 'orgnumber', type: 'string'},
	        {name: 'areaid', type: 'string'},
	        {name: 'address', type: 'string'},
	        {name: 'linkzip', type: 'string'},
	        {name: 'emails', type: 'string'},
	        {name: 'telphone', type: 'string'},
	        {name: 'businesstype', type: 'string'},
	        {name: 'levels', type: 'string'},
	        {name: 'linkman', type: 'string'},
	        {name: 'linktel', type: 'string'},
	        {name: 'legalname', type: 'string'},
	        {name: 'legaltel', type: 'string'},
	        {name: 'businessrange', type: 'string'},
	        {name: 'certificatefirsttime', type: 'string'},
	        {name: 'certificatestarttime', type: 'string'},
	        {name: 'certificateendtime', type: 'string'},
	        {name: 'operatestate', type: 'string'},
	        {name: 'summary', type: 'string'},
	        {name: 'majorBrand', type: 'string'},
	        {name: 'warranty', type: 'string'},
	        {name: 'service', type: 'string'},
	        {name: 'promotion', type: 'string'},
	        {name: 'superCompany', type: 'string'},
	        {name: 'superIndustryId', type: 'string'},
	        {name: 'industryOpenDate', type: 'string'},
	        {name: 'industryDieDate', type: 'string'}
	    ]
	});
	
	var registerForm = new Ext.form.FormPanel({
		id : 'registerForm',
		layout: 'anchor',
		defaults: {
            anchor: '100%',
            xtype:'textfield'
        },
		bodyPadding: '5 10 0 0',
		border : false,
		plugins: {
            ptype: 'datatip'
        },
		items : [{
			xtype: 'fieldcontainer',
		         layout: 'hbox',
		         defaults: {flex: 1,xtype:'textfield',labelWidth:120,labelAlign:'right'},
		         items: [
	                   {id:'deptCode', name: 'register.deptCode',fieldLabel: '门店代码', readOnly:true, fieldStyle:'background-color: #F0F0F0;'},
	                   {xtype:'displayfield',fieldLabel: '',value:''}
		         ]
		     },{
		    	 xtype: 'fieldcontainer',
		         layout: 'hbox',
		         defaults: {flex: 1,xtype:'textfield', labelWidth:120,labelAlign:'right'},
		         items: [
		        	 {id:'cmpyCompanycode', name: 'register.cmpyCompanycode',fieldLabel: '企业唯一标识',allowBlank : false,blankText : '企业唯一标识不能为空!'},
	                 {id:'companycode', name: 'register.companycode',fieldLabel: '企业编码', readOnly:true, fieldStyle:'background-color: #F0F0F0;'}
		         ]
		     },{
		    	 xtype: 'fieldcontainer',
		    	 layout: 'hbox',
		    	 defaults: {flex: 1,xtype:'textfield', labelWidth:120,labelAlign:'right'},
		    	 items: [
		    		 {id:'companyloginname', name: 'register.companyloginname',fieldLabel: '用户名',allowBlank : false,blankText : '用户名不能为空!'},
		    		 {id:'companypassword', name: 'register.companypassword',fieldLabel: '密码',allowBlank : false,blankText : '密码不能为空!'}
		    	 ]
		     },{
		    	 xtype: 'fieldcontainer',
		    	 layout: 'hbox',
		    	 defaults: {flex: 1,xtype:'textfield', labelWidth:120,labelAlign:'right'},
		    	 items: [
		    		 {id:'name', name: 'register.name',fieldLabel: '企业名称'},
		    		 {id:'industryid', name: 'register.industryid',fieldLabel: '经营许可证号'}
		    	 ]
		     },{
		    	 xtype: 'fieldcontainer',
		    	 layout: 'hbox',
		    	 defaults: {flex: 1,xtype:'textfield', labelWidth:120,labelAlign:'right'},
		    	 items: [
		    		 {id:'orgnumber', name: 'register.orgnumber',fieldLabel: '用户名'},
		    		 {id:'areaid', name: 'register.areaid',fieldLabel: '密码'}
		    	 ]
		     },{
		    	 xtype: 'fieldcontainer',
		    	 layout: 'hbox',
		    	 defaults: {flex: 1,xtype:'textfield', labelWidth:120,labelAlign:'right'},
		    	 items: [
		    		 {id:'address', name: 'register.address',fieldLabel: '企业地址'}
		    	 ]
		     },{
		    	 xtype: 'fieldcontainer',
		    	 layout: 'hbox',
		    	 defaults: {flex: 1,xtype:'textfield', labelWidth:120,labelAlign:'right'},
		    	 items: [
		    		 {id:'linkzip', name: 'register.linkzip',fieldLabel: '邮政编码'},
		    		 {id:'emails', name: 'register.emails',fieldLabel: '企业邮箱'}
		    	 ]
		     },{
		    	xtype: 'fieldcontainer',
		    	layout: 'hbox',
		    	defaults: {flex: 1,xtype:'textfield', labelWidth:120,labelAlign:'right'},
		    	items: [
		    		{id:'telphone', name: 'register.telphone',fieldLabel: '电话号码'},
//		    		{id:'businesstype', name: 'register.businesstype',fieldLabel: '企业经济类型'}
		    		LCIPBusinessTypeCombo
		    	]
		     },{
		    	 xtype: 'fieldcontainer',
		    	 layout: 'hbox',
		    	 defaults: {flex: 1,xtype:'textfield', labelWidth:120,labelAlign:'right'},
		    	 items: [
//		    		 {id:'levels', name: 'register.levels',fieldLabel: '经营业务类别'},
		    		 LCIPlevelCombo,
		    		 {id:'businessrange', name: 'register.businessrange',fieldLabel: '企业经营范围'}
		    	 ]
		     },{
		    	 xtype: 'fieldcontainer',
		    	 layout: 'hbox',
		    	 defaults: {flex: 1,xtype:'textfield', labelWidth:120,labelAlign:'right'},
		    	 items: [
		    		 {id:'linkman', name: 'register.linkman',fieldLabel: '联系人姓名'},
		    		 {id:'linktel', name: 'register.linktel',fieldLabel: '联系人联系方式'}
		    	 ]
		     },{
		    	 xtype: 'fieldcontainer',
		    	 layout: 'hbox',
		    	 defaults: {flex: 1,xtype:'textfield', labelWidth:120,labelAlign:'right'},
		    	 items: [
		    		 {id:'legalname', name: 'register.legalname',fieldLabel: '负责人姓名'},
		    		 {id:'legaltel', name: 'register.legaltel',fieldLabel: '负责人联系方式'}
		    	 ]
		     },{
		    	 xtype: 'fieldcontainer',
		    	 layout: 'hbox',
		    	 defaults: {flex: 1,xtype:'datefield', labelWidth:120,labelAlign:'right', format: 'Y-m-d'},
		    	 items: [
		    		 {id:'certificatefirsttime', name: 'register.certificatefirsttime',fieldLabel: '营业执照发证日期'},
//		    		 {id:'operatestate', xtype:'textfield', name: 'register.operatestate',fieldLabel: '企业经营状态'}
		    		 LCIPOperatestateCombo
		    	 ]
		     },{
		    	 xtype: 'fieldcontainer',
		    	 layout: 'hbox',
		    	 defaults: {flex: 1,xtype:'datefield', labelWidth:120,labelAlign:'right', format: 'Y-m-d'},
		    	 items: [
		    		 {id:'certificatestarttime', name: 'register.certificatestarttime',fieldLabel: '营业期限开始时间'},
		    		 {id:'certificateendtime', name: 'register.certificateendtime',fieldLabel: '营业期限结束时间'}
		    	 ]
		     },{
		    	 xtype: 'fieldcontainer',
		    	 layout: 'hbox',
		    	 defaults: {flex: 1,xtype:'textfield', labelWidth:120,labelAlign:'right'},
		    	 items: [
		    		 {id:'summary', name: 'register.summary',fieldLabel: '企业概述'}
		    	 ]
		     },{
		    	 xtype: 'fieldcontainer',
		    	 layout: 'hbox',
		    	 defaults: {flex: 1,xtype:'textfield', labelWidth:120,labelAlign:'right'},
		    	 items: [
		    		 {id:'majorBrand', name: 'register.majorBrand',fieldLabel: '主修品牌'},
		    		 {id:'warranty', name: 'register.warranty',fieldLabel: '质保信息'}
		    	 ]
		     },{
		    	 xtype: 'fieldcontainer',
		    	 layout: 'hbox',
		    	 defaults: {flex: 1,xtype:'textfield', labelWidth:120,labelAlign:'right'},
		    	 items: [
		    		 {id:'service', name: 'register.service',fieldLabel: '优势服务'},
		    		 {id:'promotion', name: 'register.promotion',fieldLabel: '近期活动'}
		    	 ]
		     },{
		    	 xtype: 'fieldcontainer',
		    	 layout: 'hbox',
		    	 defaults: {flex: 1,xtype:'textfield', labelWidth:120,labelAlign:'right'},
		    	 items: [
		    		 {id:'superCompany', name: 'register.superCompany',fieldLabel: '上级企业名称'},
		    		 {id:'superIndustryId', name: 'register.superIndustryId',fieldLabel: '经营许可证号'}
		    	 ]
		     },{
		    	 xtype: 'fieldcontainer',
		    	 layout: 'hbox',
		    	 defaults: {flex: 1,xtype:'datefield', labelWidth:120,labelAlign:'right', format: 'Y-m-d'},
		    	 items: [
		    		 {id:'industryOpenDate', name: 'register.industryOpenDate',fieldLabel: '道路经营许可证起始日期'},
		    		 {id:'industryDieDate', name: 'register.industryDieDate',fieldLabel: '道路经营许可证结束日期'}
		    	 ]
		     }
		],
		buttons:[{
			text : '保 存',
			iconCls : 'accept',
			handler : function() {
				if (registerForm.form.isValid()) {
					registerForm.form.submit({
						url : '/inte/jx/saveRegister.atc',
						waitTitle : '提示',
						method : 'POST',
						waitMsg : '正在处理数据,请稍候...',
						success : function(form, action) {
							Ext.example.msg('提示', action.result.msg);
						},
						failure : function(form, action) {
							var msg = action.result.msg;
							Ext.MessageBox.alert('提示', msg);
						}
					});
				}
			}
//		},{
//			text : '同 步',
//			iconCls : 'arrow_refresh_small',
//			handler : function() {
//				if (registerForm.form.isValid()) {
//					registerForm.form.submit({
//						url : '/inte/jx/syncRegister.atc',
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
		}]
	});
    
    var registerWindow = new Ext.Window({
		title : '企业注册备案', 
		layout : 'fit',
		width : 680,
		height : 640,
		resizable : false,
		draggable : false,
		closable : false,
		modal : false,
		collapsible : true,
		titleCollapse : true,
		maximizable : false,
		buttonAlign : 'right',
		border : false,
		animCollapse : true,
		constrain : true,
		tools:[{
            type:'help',
            tooltip: '操作指引',
            callback: function(panel, tool, event) {
            	parent.global.openSystemHelpTab();
            }
        }],
		items : [registerForm]
	});
    
    function completeRegister() {
		Ext.Ajax.request({
			url : '/inte/jx/queryRegisterByDept.atc',
			success : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
				if(result && !Ext.isEmpty(result)){
					register = new Register({
				        deptCode: result.deptCode,
				        cmpyCompanycode: result.cmpyCompanycode,
				        companycode: result.companycode,
				        companyloginname: result.companyloginname,
				        companypassword: result.companypassword,
				        name: result.name,
				        industryid: result.industryid,
				        orgnumber: result.orgnumber,
				        areaid: result.areaid,
				        address: result.address,
				        linkzip: result.linkzip,
				        emails: result.emails,
				        telphone: result.telphone,
				        businesstype: result.businesstype,
				        levels: result.levels,
				        linkman: result.linkman,
				        linktel: result.linktel,
				        legalname: result.legalname,
				        legaltel: result.legaltel,
				        businessrange: result.businessrange,
				        certificatefirsttime: result.certificatefirsttime,
				        certificatestarttime: result.certificatestarttime,
				        certificateendtime: result.certificateendtime,
				        operatestate: result.operatestate,
				        summary: result.summary,
				        majorBrand: result.majorBrand,
				        warranty: result.warranty,
				        service: result.service,
				        promotion: result.promotion,
				        superCompany: result.superCompany,
				        superIndustryId: result.superIndustryId,
				        industryOpenDate: result.industryOpenDate,
				        industryDieDate: result.industryDieDate,
					});
					registerForm.loadRecord(register);
				}
			},
			failure : function(resp, opts) {
                Ext.Msg.alert('提示', "信息加载出错！");
			}
		});
	}
    
    completeRegister();
    registerWindow.show();
	
});