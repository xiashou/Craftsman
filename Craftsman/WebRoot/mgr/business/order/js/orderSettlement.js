/**
 * 订单结算
 */
Ext.require([
    'Ext.ux.DataTip',
    'Ext.ux.form.SearchField'
]);
Ext.onReady(function() {
	Ext.QuickTips.init();
	
	//定义、接收会员号参数
	var orderId, vipNo, carId;
	if(typeof(orderNo) != 'undefined') orderId = orderNo;
//	if(typeof(vip) != 'undefined') vipNo = vip;
	if(typeof(car) != 'undefined') carId = car;

	//会员基本信息,会员等级基本信息
	var memberRecord, memberGrade;	
	//订单类型1普通订单, 显示应收, 实际应收, 实收, 库存报警值, 积分抵现值, 抵现金额, 积分规则, 定金, 订单状态
	var orderType = "1", receivable = 0, rece = 0, actual = 0, stockWarn = 0, offsetParam = 0, poffset = 0, pointRule = 0, pdeposit = 0, status;	
	
	var lastProject = "";
	
	Ext.define('Member', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'memId', type: 'int'},
	        {name: 'vipNo', type: 'string'},
	        {name: 'deptCode', type: 'string'},
	        {name: 'name', type: 'string'},
	        {name: 'sex', type: 'string'},
	        {name: 'mobile', type: 'string'},
	        {name: 'grade', type: 'string'},
	        {name: 'point', type: 'int'},
	        {name: 'wechatNo', type: 'string'},
	        {name: 'wehcatHead', type: 'string'},
	        {name: 'wechatNick', type: 'string'},
	        {name: 'source', type: 'string'},
	        {name: 'balance', type: 'float'},
	        {name: 'birthday', type: 'string'},
	        {name: 'createTime', type: 'string'},
	        {name: 'sales', type: 'string'},
	        {name: 'remark', type: 'string'},
	        {name: 'appId', type: 'string'},
	        {name: 'picture', type: 'string'}
	    ]
	});
	
	Ext.define('MemberGrade', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'id', type: 'int'},
	        {name: 'deptCode', type: 'string'},
	        {name: 'name', type: 'string'},
	        {name: 'grade', type: 'string'},
	        {name: 'needPoint', type: 'string'},
	        {name: 'discount', type: 'float'},
	        {name: 'orderDiscount', type: 'float'}
	    ]
	});
	
	var EmployeeSingleStore = Ext.create('Ext.data.Store', {
        fields: ['id', 'name'],
        proxy: {
            type: 'ajax',
            url : '/shop/queryEmployeeByDept.atc',
            reader : {
                root: 'employeeList'
            }
        }
	});
	EmployeeSingleStore.load();
	
	var headImg = Ext.create('Ext.Img', {
		flex: 1,
		width : 104,  
    	height : 104,
    	name: 'avatar',
    	border: 1,
    	margin: '5 5 0 0',
    	style: {
    	    borderColor: '#666',
    	    borderStyle: 'solid'
    	}
	});
	headImg.setSrc("/resources/images/noImage.jpg");
	
	//////////////////////////////////////////////////////////////////////////////////  会员基本信息
	var InfoPanel = Ext.create('Ext.form.Panel', {
        autoHeight: true,
        bodyPadding: '0 5 0 5',
        defaults: {
            anchor: '100%'
        },
        items : [{
			xtype: 'fieldset',
			title: '基本信息',
			collapsible: false,
			defaults: {
			    labelWidth: 89,
			    anchor: '100%',
			    layout: {
			        type: 'hbox',
			        defaultMargins: {top: 0, right: 5, bottom: 0, left: 0}
			    }
			},
			items:[{
				xtype: 'container',
                layout: 'hbox',
                margin: '0 0 0 0',
                items: [{
                	flex: 3,
	                layout:'anchor',
	                margin: '0 0 2 5',
	                defaults:{
	                	labelWidth: 40,
	                	xtype: 'displayfield',
	                },
	                items:[{
	                	xtype: 'container',
	                    layout: 'hbox',
	                    margin: '0 0 2 0',
	                    items: [{
	                    	flex: 3, id: 'vipNo', xtype: 'displayfield', fieldLabel: '卡号', labelWidth: 40, value: vipNo
	                    }, {
	                    	flex: 4, id:'cNumber', xtype: 'displayfield', fieldLabel: '车牌号', labelWidth: 50
	                    }]
	                },{
	                	xtype: 'container',
	                    layout: 'hbox',
	                    margin: '0 0 2 0',
	                    items: [{
	                    	flex: 2, id: 'name', xtype: 'displayfield', fieldLabel: '姓名', labelWidth: 40
	                    }, {
	                    	flex: 1, id: 'sex', xtype: 'displayfield', renderer: function(v){return (v == '1' ? '先生' : (v == '2' ? '女士' : '未知'));}
	                    }, {
	                    	flex: 4, id:'sales', xtype: 'displayfield', fieldLabel: '销售顾问', labelWidth: 60
	                    }]
	                },{
	                	xtype: 'container',
	                	layout: 'hbox',
	                	margin: '0 0 2 0',
	                	items: [{
	                		flex: 3, id: 'grade', xtype: 'displayfield', fieldLabel: '等级', labelWidth: 40
	                	}, {
	                		flex: 4, id: 'point', xtype: 'displayfield', fieldLabel: '积分', labelWidth: 40
	                	}]
	                },{
	                	xtype: 'container',
	                	layout: 'hbox',
	                	margin: '0 0 2 0',
	                	items: [{
	                		flex: 3, id :'balance', xtype: 'displayfield', fieldLabel: '余额', labelWidth: 40
	                	}, {
	                		flex: 4, id: 'createTime', xtype: 'displayfield', fieldLabel: '加入时间', labelWidth: 60,
	                		renderer: function(v){return (v.length > 10 ? v.substring(0, 10) : '');}
	                	}]
	                },{
	    				xtype: 'container',
	                    layout: 'hbox',
	                    margin: '0 0 0 0',
	                    items: [{
	                    	flex: 3, id:'birthday', xtype: 'displayfield', fieldLabel: '生日', labelWidth: 40
	                    }, {
	                    	flex: 4, id:'mobile', xtype: 'displayfield', fieldLabel: '手机号码', labelWidth: 60
	                    }]
	                }]
                }, headImg]
			},{
				xtype: 'container',
                layout: 'hbox',
                margin: '0 0 5 5',
                items: [{
                	id: 'remark', xtype: 'textarea', rows: 2, fieldLabel: '备注', width: '80%', labelWidth: 40
                },Ext.create('Ext.toolbar.Toolbar', {
 		    	   items: ['->',{
 		    		  xtype: 'button',
		    		  text:'保存',
		    		  handler: function(){
		    			  Ext.Ajax.request({
		    					url : '/member/updateMemberRemark.atc',
		    					params : {
		    						'member.memId': memberRecord.data.memId,
		    						'member.remark': Ext.getCmp("remark").getValue()
		    					},
		    					success : function(resp, opts) {
		    						var result = Ext.decode(resp.responseText);
		    						if(result.success){
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
 		    	   }]
 		       })]
			}, Ext.create('Ext.toolbar.Toolbar', {
 		    	   items: [{
 		    		   xtype: 'button',
		    		   text:'修改资料',
		    		   handler: function(){
		    			   Ext.getCmp("memberId").setValue(memberRecord.data.memId);
		    			   Ext.getCmp("memberVipNo").setValue(memberRecord.data.vipNo);
		    			   Ext.getCmp("memberDeptCode").setValue(memberRecord.data.deptCode);
		    			   Ext.getCmp("memberPoint").setValue(memberRecord.data.point);
		    			   Ext.getCmp("memberName").setValue(memberRecord.data.name);
		    			   Ext.getCmp('memberSex').setValue({'member.sex' : memberRecord.data.sex});
		    			   Ext.getCmp("memberGrade").setValue(memberRecord.data.grade);
		    			   Ext.getCmp("memberBalance").setValue(memberRecord.data.balance);
		    			   Ext.getCmp("memberBirthday").setValue(memberRecord.data.birthday);
		    			   Ext.getCmp("memberCreateTime").setValue(memberRecord.data.createTime);
		    			   Ext.getCmp("memberMobile").setValue(memberRecord.data.mobile);
		    			   Ext.getCmp("memberSales").setValue(memberRecord.data.sales);
		    			   Ext.getCmp("memberRemark").setValue(memberRecord.data.remark);
		    			   Ext.getCmp("wechatNo").setValue(memberRecord.data.wechatNo);
		    			   Ext.getCmp("wehcatHead").setValue(memberRecord.data.wehcatHead);
		    			   Ext.getCmp("wechatNick").setValue(memberRecord.data.wechatNick);
		    			   Ext.getCmp("memberAlbum").setValue(memberRecord.data.album);
		    			   Ext.getCmp("memberSource").setValue(memberRecord.data.source);
		    			   Ext.getCmp("memberAppId").setValue(memberRecord.data.appId);
		    			   Ext.getCmp("memberPicture").setValue(memberRecord.data.picture); 
		    			   
		    			   InfoForm.getForm().url = "/member/updateMember.atc";
		    			   InfoWindow.setTitle(memberRecord.data.vipNo + " 资料修改");
		    			   InfoWindow.show();
		    		   }
 		    	   },{
 		    		   xtype: 'button',
 		    		   text: '上传头像',
 		    		   handler: function(){
 		    			  Ext.getCmp("picture").setValue(memberRecord.data.memId);
 		    			  ImportWindow.show();
 		    		   }
 		    	  },{
		    		   xtype: 'button',
		    		   text: '会员相册',
		    		   handler: function(){
		    			   if(!Ext.isEmpty(memberRecord)){
		    				   AlbumWindow.update('<iframe id="iframe" style="overflow:auto;width:100%; height:100%;" src="/base/initBaseImage.atc?mId=' + memberRecord.data.memId + '" frameborder="0"></iframe>');
		    				   AlbumWindow.setTitle('会员相册');
		    				   AlbumWindow.show();
		    			   } else {
		    				   Ext.MessageBox.show({
						           title: '提示',
						           msg: '请先选择会员！',
						           buttons: Ext.MessageBox.OK,
						           icon: Ext.MessageBox.INFO
		    				   });
		    			   }
		    		   }
 		    	   }]
 		       })
			]
        }]
	});
	
	var AlbumWindow = new Ext.Window({
		layout : 'fit',
		width : 580,
		height : 400,
		resizable : false,
		draggable : true,
		closeAction : 'hide',
		modal : false,
		collapsible : true,
		titleCollapse : true,
		maximizable : true,
		border : false,
		animCollapse : true,
		constrain : true
	});
	
	var ImportForm = new Ext.form.FormPanel({
		id : 'ImportForm',
		name : 'ImportForm',
		defaultType : 'textfield',
		labelAlign : 'right',
		labelWidth : 99,
		padding: '5 5 5 5',
		frame : false,
		items : [{
				fieldLabel : '上传照片',
				labelWidth: 80,
				labelAlign: 'right',
				id : 'upload',
				name : 'upload',
				xtype:'filefield',
				buttonText:'浏览...',
				allowBlank : false,
				anchor : '99%'
			},{
				xtype: 'hiddenfield',
				id: 'picture',
				name: 'member.memId',
			}]
	});
	
	var ImportWindow = new Ext.Window({
		layout : 'fit',
		width : 380,
		height : 120,
		resizable : false,
		draggable : true,
		closeAction : 'hide',
		title : '上传照片',
		modal : false,
		collapsible : true,
		titleCollapse : true,
		maximizable : false,
		buttonAlign : 'right',
		border : false,
		animCollapse : true,
		constrain : true,
		items : [ImportForm],
		buttons : [{
			text : '上 传',
			iconCls : 'image',
			handler : function() {
				var upload = Ext.getCmp('upload').getValue();
				if (Ext.isEmpty(upload)) {
					Ext.MessageBox.show({
				           title: '提示',
				           msg: '请先选择您要上传的图片...',
				           buttons: Ext.MessageBox.OK,
				           icon: Ext.MessageBox.INFO
					});
					return;
				}
				Ext.MessageBox.show({
					title: '请稍等',
					msg: '图片上传中 ...',
					width: 200,
					progress: true,
					closable: false
		        });
				ImportForm.form.submit({
					url : '/member/uploadMemberImage.atc',
					waitTitle : '提示',
					method : 'POST',
					waitMsg : '正在处理数据,请稍候...',
					success : function(form, action) {
						Ext.MessageBox.hide();
						ImportWindow.hide();
						Ext.example.msg("提示", action.result.msg);
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
			text : '关 闭',
			iconCls : 'stop',
			handler : function() {
				ImportWindow.hide();
			}
		}]
	});
	
	var SalesCombo = Ext.create('Ext.form.ComboBox', {
		id: 'memberSales',
		name: 'member.sales',
		flex: 1,
		triggerAction : 'all',
		fieldLabel: '销售顾问',
		labelWidth:70, 
		labelAlign:'right',
		mode : 'local',
		store: EmployeeSingleStore,
		valueField : 'name',
		displayField : 'name',
		editable : false
	});
	
	var InfoForm = Ext.create('Ext.form.Panel', {
		id : 'InfoForm',
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
			    defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'right'},
			    items: [
			           {id:'memberId', name:'member.memId',xtype:'hiddenfield'},
			           {id:'memberVipNo', name:'member.vipNo',xtype:'hiddenfield'},
			           {id:'memberDeptCode', name:'member.deptCode',xtype:'hiddenfield'},
			           {id:'memberPoint', name:'member.point',xtype:'hiddenfield'},
			           {id:'wechatNo', name:'member.wechatNo',xtype:'hiddenfield'},
			           {id:'wehcatHead', name:'member.wehcatHead',xtype:'hiddenfield'},
			           {id:'wechatNick', name:'member.wechatNick',xtype:'hiddenfield'},
			           {id:'memberAlbum', name:'member.album',xtype:'hiddenfield'},
			           {id:'memberSource', name:'member.source',xtype:'hiddenfield'},
			           {id:'memberAppId', name:'member.appId',xtype:'hiddenfield'},
			           {id:'memberPicture', name:'member.picture',xtype:'hiddenfield'},
			           {id:'memberName', name:'member.name',fieldLabel: '姓名', allowBlank: false, blankText:'姓名不能为空!'},
			           {id:'memberSex', xtype:'radiogroup', defaults: {name: 'member.sex', labelWidth: 40},fieldLabel: '性别',
				        	items: [{inputValue: 1,boxLabel: '先生', checked: true}, {inputValue: 2,boxLabel: '女士'}]
				        }
			    ]
			},{
			    defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'right'},
			    items: [
			           {id:'memberGrade', name: 'member.grade', fieldLabel: '等级'},
			           {id:'memberBalance', name: 'member.balance', fieldLabel: '余额', readOnly: true}
			    ]
			},{
				defaults: {flex: 1, xtype:'datefield', labelWidth:70, labelAlign:'right'},
				items: [
				        {id:'memberBirthday', name: 'member.birthday', fieldLabel: '生日', format: 'Y/m/d'},
				        {id:'memberCreateTime', name: 'member.createTime', xtype:'textfield', fieldLabel: '加入时间', format: 'Y/m/d', readOnly: true}
				]
			},{
				defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'right'},
				items: [
				        {id:'memberMobile', name: 'member.mobile', maxLength: 11, fieldLabel: '手机号码'},
				        SalesCombo
				]
			},{
				defaults: {flex: 1, xtype:'textarea', labelWidth:70, labelAlign:'right'},
				items: [
				        {id: 'memberRemark', name: 'member.remark', rows: 3, fieldLabel: '备注'}
				]
			}
		],
		buttons:[{
			text : '保 存',
			iconCls : 'accept',
			handler : function() {
				if (InfoForm.form.isValid()) {
					InfoForm.form.submit({
						waitTitle : '提示',
						method : 'POST',
						waitMsg : '正在处理数据,请稍候...',
						success : function(form, action) {
							InfoWindow.hide();
							Ext.example.msg('提示', action.result.msg);
							//成功后重新加载会员面板
							completeMemberInfo(action.result.vipNo);
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
				InfoWindow.hide();
			}
		}]
	});
	
	var InfoWindow = new Ext.Window({
		layout : 'fit',
		width : 500,
		height : 280,
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
		items: [InfoForm]
	});
	
	///////////////////////////////////////////////////////////////////////////////// 会员车辆信息
	
	var CarStore = Ext.create('Ext.data.Store', {
		fields : ['id', 'memberId', 'carShort', 'carCode', 'carNumber', 'carBrand', 'carSeries', 'carModel', 'carFrame', 'showName', 
		          'carEngine', 'carKilometers', 'carNextkilo', 'carInspection', 'carMaintain', 'carInsurance', 'carInsuCompany', 'cariLLegal', 'carMobile'],  
		proxy:{
            type: 'ajax',
            url : '/member/queryCarByMemberId.atc',
            reader : {
                root: 'carList'
            }
        }
	});
	
	var CarCombo = Ext.create('Ext.form.ComboBox', {
		id: 'CarCombo',
		name: 'car.id',
		flex: 8,
		store: CarStore,
		allowBlank: false,
        enforceMaxLength: true,
        fieldLabel: '选择车辆',
        labelWidth: 65,
        listConfig: {
            minWidth: null
        },
        valueField: 'id',
        displayField: 'showName',
        queryMode: 'local',
        editable : false,
        forceSelection: true,
        listeners: {
            select: function(cb, records) {
            	var record = records[0];
            	CarsPanel.loadRecord(record);
            }
        }
	});
	
	CarStore.on('load', function(){
		var cRecord;
		if(!Ext.isEmpty(carId)){
			cRecord = CarStore.findRecord("id", carId);
			CarCombo.setValue(cRecord);
			CarsPanel.loadRecord(cRecord);
		} else {
			cRecord = CarStore.getAt(0);
			CarCombo.select(cRecord);
			CarsPanel.loadRecord(cRecord);
		}
		Ext.getCmp("cNumber").setValue(cRecord.data.carShort + cRecord.data.carCode + cRecord.data.carNumber);
		MemberCombo.setRawValue(cRecord.data.carShort + cRecord.data.carCode + cRecord.data.carNumber);
		Ext.getCmp("carCount").setValue(CarStore.getCount() + "辆");
	});
	
	var areaShortStore = Ext.create('Ext.data.Store', {
		fields : [{name: 'id'}, {name: 'areaShort'}],  
		proxy:{
            type: 'ajax',
            url : '/base/queryAllAreaShort.atc',
            reader : {
                root: 'areaShortList'
            }
        }
	});
	
	var areaCodeStore = Ext.create('Ext.data.Store', {
		fields : [{name: 'id'}, {name: 'areaCode'}],  
		proxy:{
            type: 'ajax',
            url : '/base/queryAllAreaCode.atc',
            reader : {
                root: 'areaCodeList'
            }
        }
	});
	
	var CarsPanel = Ext.create('Ext.form.Panel', {
		autoHeight: true,
        bodyPadding: '0 5 0 5',
        defaults: {
            anchor: '100%',
            labelWidth: 100
        },
        items : [{
			xtype: 'fieldset',
			title: '车辆信息',
			collapsible: false,
			defaults: {
			    labelWidth: 89,
			    anchor: '100%',
			    layout: {
			        type: 'hbox',
			        defaultMargins: {top: 0, right: 5, bottom: 0, left: 0}
			    }
			},
//			items: [CarCombo, {
			items:[{
				xtype: 'container',
            	layout: 'hbox',
            	margin: '0 0 5 5',
            	defaults:{
                	flex: 1, xtype: 'displayfield'
                },
            	items: [CarCombo, {
            		flex: 2, id:'carCount',padding:'0 0 0 10'
            	}]
			},{
            	xtype: 'container',
                layout: 'hbox',
                margin: '0 0 5 5',
                defaults:{
                	xtype: 'textfield', padding: '0 5 0 0'
                },
                items: [{
                	flex: 7, id: 'carKilometers', name: 'car.carKilometers', xtype: 'numberfield', fieldLabel: '进厂公里', labelWidth: 60,
                }, {
                	id:'carBrand', name:'car.carBrand', xtype:'hiddenfield'
                }, {
                	id:'carCode', name:'car.carCode', xtype:'hiddenfield'
                }, {
                	id:'carShort', name:'car.carShort', xtype:'hiddenfield'
                }, {
                	id:'carNumber', name:'car.carNumber', xtype:'hiddenfield'
                }, {
                	id:'memberId2', name:'car.memberId', xtype:'hiddenfield'
                }, {
                	flex: 1, xtype: 'displayfield',hideLabel: true, value: 'km', padding: '0'
                }, {
                	flex: 7, id: 'carNextkilo', name: 'car.carNextkilo',  xtype: 'numberfield', fieldLabel: '下次保养公里', labelWidth: 90, value: 5000
                }, {
                	flex: 1, xtype: 'displayfield',hideLabel: true, value: 'km', padding: '0'
                }]
            },{
            	xtype: 'container',
            	layout: 'hbox',
            	margin: '0 0 5 5',
            	defaults:{
                	flex: 1, xtype: 'textfield', padding: '0 5 0 0'
                },
                items: [{
            		id: 'carMaintain', name: 'car.carMaintain', xtype: 'datefield', format: 'Y/m/d', fieldLabel: '保养时间', labelWidth: 60
            	}, {
            		id: 'carEngine', name: 'car.carEngine', fieldLabel: '发动机号', labelWidth: 60
            	}]
            },{
            	xtype: 'container',
            	layout: 'hbox',
            	margin: '0 0 5 5',
            	defaults:{
                	flex: 1, xtype: 'datefield', format: 'Y/m/d', padding: '0 5 0 0'
                },
                items: [{
            		id: 'carFrame', name: 'car.carFrame', fieldLabel: '车架号', labelWidth: 60
            	}, {
            		id: 'carInspection', xtype: 'datefield', format: 'Y/m/d', name: 'car.carInspection', fieldLabel: '年检时间', labelWidth: 60
            	}]
            },{
            	xtype: 'container',
            	layout: 'hbox',
            	margin: '0 0 5 5',
            	defaults:{
                	flex: 1, xtype: 'datefield', format: 'Y/m/d', padding: '0 5 0 0'
                },
            	items: [{
            		id: 'carInsurance', name: 'car.carInsurance', fieldLabel: '保险时间', labelWidth: 60
            	},{
            		id:'carInsuCompany', name: 'car.carInsuCompany', xtype: 'textfield', fieldLabel: '保险公司', labelWidth: 60
            	}]
            },{
            	xtype: 'container',
            	layout: 'hbox',
            	margin: '0 0 5 5',
            	items: [Ext.create('Ext.toolbar.Toolbar', {
 		    	   items: [{
 		    		   xtype: 'button',
 		    		   text:'保存',
 		    		   id: 'saveCar',
 		    		   handler: function() {
 		    			  Ext.getCmp("memberId2").setValue(memberRecord.data.memId);
 		    			  CarsPanel.getForm().url = "/member/updateCar.atc";
 		    			  if (CarsPanel.form.isValid()) {
 		    				 CarsPanel.form.submit({
 		 						waitTitle : '提示',
 		 						method : 'POST',
 		 						waitMsg : '正在处理数据,请稍候...',
 		 						success : function(form, action) {
 		 							CarWindow.hide();
 		 							CarForm.getForm().reset();
 		 							Ext.example.msg("提示", action.result.msg);
 		 							var car = action.result.car;
 		 							if(car){
 		 								completeMemberCarCombo(car.memberId);
 		 								Ext.getCmp("carEngine").setValue(car.carEngine);
 		 			    			    Ext.getCmp("carFrame").setValue(car.carFrame);
 		 			    			    Ext.getCmp("carKilometers").setValue(car.carKilometers);
 		 			    			    Ext.getCmp("carNextkilo").setValue(car.carNextkilo);
 		 			    			    Ext.getCmp("carInspection").setValue(car.carInspection);
 		 			    			    Ext.getCmp("carMaintain").setValue(car.carMaintain);
 		 			    			    Ext.getCmp("carInsurance").setValue(car.carInsurance);
 		 			    			    Ext.getCmp("carInsuCompany").setValue(car.carInsuCompany);
 		 							}
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
 		    		   xtype: 'button',
		    		   text:'修改信息',
		    		   handler: function(){
		    			   var carRecord = CarStore.findRecord("id", CarCombo.getValue());
		    			   CarForm.loadRecord(carRecord);
		    			   areaShortCombo.setValue(carRecord.data.carShort);
		    			   areaCodeCombo.setValue(carRecord.data.carCode);
		    			   BrandCombo.setValue(carRecord.data.carBrand);
		    			   Ext.getCmp("carId").setValue(carRecord.data.id);
		    			   Ext.getCmp("number").setValue(carRecord.data.carNumber);
		    			   Ext.getCmp("carMemberId").setValue(carRecord.data.memberId);
		    			   Ext.getCmp("series").setValue(carRecord.data.carSeries);
		    			   Ext.getCmp("model").setValue(carRecord.data.carModel);
		    			   Ext.getCmp("engine").setValue(carRecord.data.carEngine);
		    			   Ext.getCmp("frame").setValue(carRecord.data.carFrame);
		    			   Ext.getCmp("kilometers").setValue(carRecord.data.carKilometers);
		    			   Ext.getCmp("nextkilo").setValue(carRecord.data.carNextkilo);
		    			   Ext.getCmp("inspection").setValue(carRecord.data.carInspection);
		    			   Ext.getCmp("maintain").setValue(carRecord.data.carMaintain);
		    			   Ext.getCmp("insurance").setValue(carRecord.data.carInsurance);
		    			   Ext.getCmp("insuCompany").setValue(carRecord.data.carInsuCompany);
		    			   Ext.getCmp("carMobile").setValue(carRecord.data.carMobile);
		    			   CarForm.getForm().url = "/member/updateCar.atc";
		    			   CarWindow.setTitle(carRecord.data.carShort + carRecord.data.carCode + carRecord.data.carNumber + " 资料修改");
		    			   CarWindow.show();
		    		   }
 		    	   }]
 		       })]
            }]
        }]
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
		store: areaShortStore,
		valueField : 'areaShort',
		displayField : 'areaShort',
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
		store: areaCodeStore,
		valueField : 'areaCode',
		displayField : 'areaCode',
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
	
	var CarForm = Ext.create('Ext.form.Panel', {
		id : 'CarForm',
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
						{id:'carId', name: 'car.id', xtype:'hiddenfield'},
						{id:'carMemberId', name: 'car.memberId', xtype:'hiddenfield'},
	                    {id:'number', name: 'car.carNumber', maxLength: 5, flex: 4, allowBlank: false, blankText:'账号不能为空!'}
		         ]
		     },{
		         items: [BrandCombo]
		     },{
		         defaults: {flex: 1,xtype:'textfield',labelWidth:70,labelAlign:'right'},
		         items: [
	                    {id:'series', name: 'car.carSeries', fieldLabel: '系列'},
	                    {id:'model', name: 'car.carModel', fieldLabel: '型号'}
		         ] 
		     },{
		         defaults: {flex: 1,xtype:'textfield',labelWidth:70,labelAlign:'right'},
		         items: [
	                    {id:'engine', name: 'car.carEngine', fieldLabel: '发动机号'},
	                    {id:'frame', name: 'car.carFrame', fieldLabel: '车架号'}
		         ] 
		     },{
		    	 defaults: {flex: 1, xtype:'numberfield',labelWidth:70,labelAlign:'right'},
		    	 items: [
		    	         {id:'kilometers', name: 'car.carKilometers', fieldLabel: '进厂公里'},
		    	         {id:'nextkilo', name: 'car.carNextkilo', labelWidth:90, fieldLabel: '下次保养公里', value: 5000}
		    	 ]
		     },{
		    	 defaults: {flex: 1,xtype:'datefield',labelWidth:70,labelAlign:'right'},
		    	 items: [
		    	         {id:'maintain', name: 'car.carMaintain', fieldLabel: '保养日期', format: 'Y/m/d'},
		    	         {id:'inspection', name: 'car.carInspection', fieldLabel: '年检时间', format: 'Y/m/d'},
		    	 ]
		     },{
		         defaults: {flex: 1,xtype:'textfield',labelWidth:70,labelAlign:'right'},
		         items: [
		                {id:'insurance', name: 'car.carInsurance', xtype:'datefield', fieldLabel: '保险时间', format: 'Y/m/d'},
	                    {id:'insuCompany', name: 'car.carInsuCompany', fieldLabel: '保险公司'},
		         ]
		     },{
		    	 defaults: {flex: 1,xtype:'textfield',labelWidth:70,labelAlign:'right'},
		    	 items: [
		    	         {id:'carMobile', name: 'car.carMobile', fieldLabel: '手机号码'},
		    	         {xtype:'displayfield',value:''}
		    	 ]
		     }
		],
		buttons:[{
			text : '保 存',
			iconCls : 'accept',
			handler : function() {
				if (CarForm.form.isValid()) {
					if(isNaN(BrandCombo.getValue())){
						Ext.MessageBox.show({
 				           	title: '提示',
 				           	msg: '请输入品牌首字母选择汽车品牌！',
 				           	buttons: Ext.MessageBox.OK,
 				           	icon: Ext.MessageBox.ERROR
                        });
					} else {
						CarForm.form.submit({
							waitTitle : '提示',
							method : 'POST',
							waitMsg : '正在处理数据,请稍候...',
							success : function(form, action) {
								CarWindow.hide();
								CarForm.getForm().reset();
								Ext.example.msg("提示", action.result.msg);
								var car = action.result.car;
								if(car){
									completeMemberCarCombo(car.memberId);
									Ext.getCmp("carEngine").setValue(car.carEngine);
				    			    Ext.getCmp("carFrame").setValue(car.carFrame);
				    			    Ext.getCmp("carKilometers").setValue(car.carKilometers);
				    			    Ext.getCmp("carNextkilo").setValue(car.carNextkilo);
				    			    Ext.getCmp("carInspection").setValue(car.carInspection);
				    			    Ext.getCmp("carMaintain").setValue(car.carMaintain);
				    			    Ext.getCmp("carInsurance").setValue(car.carInsurance);
				    			    Ext.getCmp("carInsuCompany").setValue(car.carInsuCompany);
				    			    Ext.getCmp("carMobile").setValue(car.carMobile);
								}
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
	
	var CarWindow = new Ext.Window({
		layout : 'fit',
		width : 500,
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
		items: [CarForm]
	});
	
	//////////////////////////////////////////////////////////////////////////////// 会员剩余服务
	
	var MemberStockStore = Ext.create('Ext.data.Store', {
		fields: ['memId', 'goodsId', 'goodsName', 'goodsType', 'number', 'typeId', 'source', 'endDate', 'sourceName', 'unitPrice'],
		proxy:{
            type: 'ajax',
            url : '/member/queryMemberStockDetailByMemId.atc',
            reader : {
                root: 'msList'
            }
        }
    });
	var MemberStockRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var goodsTypeRenderer = function(value, cellmeta, record) {
		var itemType = parent.GOrderItemTypeStore.findRecord("value", value);
		if(itemType)
			return itemType.data.text;
		else
			return value;
    };
	
	var MemberStockColumns= [MemberStockRowNum,
        {header : '会员ID',dataIndex: 'memId',hidden: true},
        {header : '类型ID',dataIndex: 'typeId',hidden: true},
        {header : '商品ID',dataIndex: 'goodsId',hidden: true},
        {header : '类型',dataIndex: 'goodsType', renderer: goodsTypeRenderer},
        {header : '单价',dataIndex: 'unitPrice',hidden: true},
        {header : '商品',dataIndex: 'goodsName',width: 160, menuDisabled: true},
        {header : '数量',dataIndex: 'number',width:60, menuDisabled: true},
        {header : '到期日期',dataIndex: 'endDate',width: 100, menuDisabled: true},
        {xtype: 'actioncolumn',header : '使用',width: 50,align:'center',sortable: false, menuDisabled: true,
             items: [{
                 iconCls: 'accept',
                 tooltip: '使用',
                 scope: this,
                 handler: function(grid, rowIndex){
                	 serviceToOrder(MemberStockStore.getAt(rowIndex));
                	 MemberStockWindow.hide();
                 }
             }]
         }
 	];
		
	var MemberStockGrid = Ext.create('Ext.grid.GridPanel', {
		minHeight: 220,
		columns : MemberStockColumns,
		store : MemberStockStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		frame : false,
		stripeRows: true,
		forceFit: true,
		viewConfig : {
			enableTextSelection:true	//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
	    },
	    listeners:{
			itemdblclick: function(grid, record, item, index, e) {
				serviceToOrder(record);
				MemberStockWindow.hide();
	        }
	    }
	});
	
	var MemberStockWindow = Ext.create('Ext.Window',{
		layout : 'fit',
		title: '会员剩余服务',
		iconCls: 'award_star_silver_1',
		width : 540,
		height : 340,
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
		items: [MemberStockGrid]
	});
	
	MemberStockWindow.on('show', function(){
		MemberStockStore.load({
			params:{
				memId: memberRecord.data.memId
			}
		});
	});
	
	var SurplusForm = Ext.create('Ext.form.Panel', {
		autoHeight: true,
		bodyPadding: '0 5 0 5',
		defaults: {
			anchor: '100%',
			labelWidth: 100
		},
		items : [{
			xtype: 'fieldset',
			itemId: 'SurplusSet',
			title: '剩余服务',
			collapsible: false,
			defaults: {
				labelWidth: 89,
				anchor: '100%',
				layout: {
					type: 'hbox',
					defaultMargins: {top: 0, right: 5, bottom: 0, left: 0}
				}
			},
			items: [{
				xtype: 'container',
				itemId: 'SurplusContainer',
				layout: 'column',
                margin: '0 0 5 5',
                defaults: {
                	columnWidth: 1/3, 
                	labelWidth: 90,
                	xtype: 'displayfield',
                }
            }]
		}]
	});
	
	///////////////////////////////////////////////历史消费记录
	
	var HistorySalesStore = Ext.create('Ext.data.Store', {
		fields: ['orderId', 'itemNo', 'saleDate', 'name', 'carId', 'carNumber', 'goodsId', 'goodsName', 'number', 'price'],
		proxy:{
            type: 'ajax',
            url : '/order/queryOrderByMemIdPage.atc',
            reader : {
                root: 'orderList',
                totalProperty: 'totalCount'
            }
        }
    });
	var HistorySalesRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var HistorySalesColumns= [HistorySalesRowNum,
	            {header : '订单号',dataIndex: 'orderId',menuDisabled: true},
	 	        {header : '行项目',dataIndex: 'itemNo',menuDisabled: true, hidden:true},
	 	        {header : '销售日期',dataIndex: 'saleDate',menuDisabled: true, width:'16%'},
	 	        {header : '姓名',dataIndex: 'name',menuDisabled: true},
	 	        {header : '车辆ID',dataIndex: 'carId',menuDisabled: true, hidden:true},
	 	        {header : '车牌号',dataIndex: 'carNumber',menuDisabled: true},
	 	        {header : '商品ID',dataIndex: 'goodsId',menuDisabled: true, hidden:true},
	 	        {header : '项目/商品',dataIndex: 'goodsName',menuDisabled: true},
	 	        {header : '次数/数量',dataIndex: 'number',menuDisabled: true},
	 	        {header : '价格',dataIndex: 'price',menuDisabled: true,renderer: Ext.util.Format.numberRenderer("0.00")}
	 	];
	
	var HistorySalesPagesizeCombo = new Ext.form.ComboBox({
		name : 'HistorySalesPagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
					[ 100, '100条/页' ], [ 200, '200条/页' ]]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '10',
		editable : false,
		width : 85
	});

	var HistorySalesNumber = parseInt(HistorySalesPagesizeCombo.getValue());
	
	// 改变每页显示条数reload数据
	HistorySalesPagesizeCombo.on("select", function(comboBox) {
		HistorySalesBbar.pageSize = parseInt(comboBox.getValue());
		number = parseInt(comboBox.getValue());
		HistorySalesStore.pageSize = parseInt(comboBox.getValue());
		HistorySalesStore.reload({
			params : {
				start : 0,
				limit : HistorySalesBbar.pageSize,
				memId : memberRecord.data.memId
			}
		});
	});
	
	var HistorySalesBbar = new Ext.PagingToolbar({
		pageSize : HistorySalesNumber,
		store : HistorySalesStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', HistorySalesPagesizeCombo ]
	});
		
	var HistorySalesGrid = Ext.create('Ext.grid.GridPanel', {
		minHeight: 220,
		columns : HistorySalesColumns,
		store : HistorySalesStore, 
		bbar : HistorySalesBbar,
		region : 'center',
		autoScroll : true,
		columnLines : true,
		frame : false,
		stripeRows: true,
		forceFit: true,
		viewConfig : {
			enableTextSelection:true	//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
	    }
	});
	
	var HistorySalesWindow = Ext.create('Ext.Window',{
		layout : 'fit',
		title: '历史消费',
		iconCls: 'award_star_bronze_3',
		width : 730,
		height : 440,
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
		items: [HistorySalesGrid]
	});
	HistorySalesStore.on('refresh', function(){
		HistorySalesStore.getProxy().extraParams = {'memId': memberRecord.data.memId};
	});
	HistorySalesWindow.on('show', function(){
		HistorySalesStore.load({
			params : {
				start : 0,
				limit : HistorySalesBbar.pageSize,
				memId : memberRecord.data.memId
			}
		});
	});
	
	
	//////////////////////////////////////////////////////////////   订单部分     //////////////////////////////////////////////////////////////
	
	var MemberStore = Ext.create('Ext.data.Store', {
		fields: ['id', 'vipNo', 'name', 'mobile', 'carShort', 'carCode', 'carNumber', 'fullCarNumber'],
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
		flex: 3,
        store: MemberStore,
        labelWidth : 40,
        allowBlank: false,
        fieldLabel : '会员',
        labelAlign: 'right',
        valueField: 'vipNo',
        displayField: 'fullCarNumber',
        typeAhead: false,
        hideTrigger:true,
        width: '100%',
        queryParam: 'keyword',	//搜索参数字段
        minChars: 2,
        emptyText: '请输入 车牌/手机号',
        pageSize: 10,
        listConfig: {
            loadingText: '查找中...',
            emptyText: '<div style="padding: 5px;">没有找到相关车主！</div>',
            getInnerTpl: function() {
                return '<div>{fullCarNumber}/{name}/{mobile}</div>';
            }
        },
        listeners : {
        	select: function(cb, records) {
            	var record = records[0];
            	completeMemberInfo(record.data.vipNo);
			}
		}
	});
	
	var EmployeeSingleCombo = Ext.create('Ext.form.ComboBox', {
		flex: 2,
		labelWidth: 60, 
		labelAlign:'right',
		store: EmployeeSingleStore,
        enforceMaxLength: true,
        fieldLabel: '开单人',
        width: '100%',
        listConfig: {
            minWidth: null
        },
        valueField: 'name',
        displayField: 'name',
        queryMode: 'local',
        editable : false,
        forceSelection: true
	});
	
	//订单抬头
	var OrderHead = Ext.create('Ext.form.FormPanel', {
		height : 30,
		margin: '10 10 0 10',
		defaults: {
		    anchor: '100%',
		    layout: {
		        type: 'hbox'
		    }
		},
		items : [{
			xtype: 'container',
        	layout: 'hbox',
        	margin: '0 0 5 5',
        	defaults:{
            	flex: 1, xtype: 'textfield', padding: '0 5 0 0'
            },
        	items: [{
					xtype: 'textfield',
					id: 'orderId',
					flex: 2,
					fieldLabel : '订单号',
					labelWidth : 50,
					labelAlign: 'right',
					style:{
						padding: '0',
						marginLeft: '5'
					},
					readOnly: true
//				items: [{
//				    xtype:'splitbutton',
//				    text: '项目消费',
//				    iconCls: 'application_view_columns',
//				    menu: [
//				           {text: '套餐购买', value:'package', handler: OrderTypeChange},
//				           {text: '客户退货', value:'return', handler: OrderTypeChange},
//				           {text: '会员充值', value:'recharge', handler: OrderTypeChange}
//				    ]
//				}]
        	}, MemberCombo, EmployeeSingleCombo, {
        			flex: 2,
					fieldLabel : '消费日期',
					id: 'saleDate',
					width: 170,
					labelWidth : 70,
					labelAlign: 'right',
					xtype : 'datefield',
					format: 'Y/m/d',
					maxValue: new Date(),
					value: new Date()
        	},{
        		flex: .8,
        		id: 'saleTime',
        		xtype: 'timefield',
				format: 'H:i',
				editable: false,
				value: new Date()
        	}]
		}]
	});
	
	var EmployeeStore = Ext.create('Ext.data.Store', {
		fields: ['id', 'name', 'isGroup'],
        proxy: {
            type: 'ajax',
            url : '/shop/queryAllEmployeeByDept.atc',
            reader : {
                root: 'employeeList'
            }
        }
	});
	var EmployeeRenderer = function(value, cellmeta, record) {
		if(!Ext.isEmpty(value)){
			if(value.indexOf(",") > 0){
				var names = "";
				var ids = value.split(",");
				for (index in ids){
					if(!Ext.isEmpty(ids[index].trim()))
						names += EmployeeStore.findRecord("id", ids[index].trim()).data.name + ',';
				}
				return names;
			} else 
				return EmployeeStore.findRecord("id", value.trim()).data.name;
		} else
			return "";
    };
    var PerformerPanel = Ext.create('Ext.form.Panel', {
    	autoHeight: true,
		layout: 'anchor',
        defaults: {
            anchor: '100%',
        },
        bodyPadding: 10,
        items: [{
        	xtype: 'fieldset',
			itemId: 'PerformerSet',
			title: '员工',
			collapsible: false,
			defaults: {
				labelWidth: 89,
				anchor: '100%',
				layout: {
					type: 'hbox',
					defaultMargins: {top: 0, right: 5, bottom: 0, left: 0}
				}
			},
            items: [{
				xtype: 'container',
				id:'PerformerSet',
				itemId: 'PerformerContainer',
				layout: 'column',
				defaults: {
                	columnWidth: 1/3,
                	xtype: 'checkbox'
                }
            }]
        },{
        	xtype: 'fieldset',
			itemId: 'PerformerGroupSet',
			title: '员工组',
			collapsible: false,
			defaults: {
				labelWidth: 89,
				anchor: '100%',
				layout: {
					type: 'hbox',
					defaultMargins: {top: 0, right: 5, bottom: 0, left: 0}
				}
			},
            items: [{
				xtype: 'container',
				id: 'PerformerGroupSet',
				itemId: 'PerformerGroupContainer',
				layout: 'column',
				defaults: {
                	columnWidth: 1/3,
                	xtype: 'checkbox'
                }
            }]
        }],
        buttons:[{
        	text : '确 定',
			iconCls : 'accept',
			handler : function() {
				var employee = Ext.getCmp('PerformerSet').items;
				var group = Ext.getCmp('PerformerGroupSet').items;
                var value = '', display = '';
                for(var i = 0; i < employee.length; i++){
                    if(employee.get(i).checked){
                    	value += employee.get(i).inputValue + ",";
                    	display += employee.get(i).boxLabel + ",";
                    }
                }
                for(var i = 0; i < group.length; i++){
                    if(group.get(i).checked){
                    	value += group.get(i).inputValue + ",";
                    	display += group.get(i).boxLabel + ",";
                    }
                }
                var rowRecord = ProjectGrid.getSelectionModel().getLastSelected();
                rowRecord.data.performer = value;
                rowRecord.commit();
                PerformerWindow.hide();
			}
        }]
	});
    var SellerPanel = Ext.create('Ext.form.Panel', {
    	autoHeight: true,
		layout: 'anchor',
        defaults: {
            anchor: '100%',
        },
        bodyPadding: 10,
        items: [{
        	xtype: 'fieldset',
			itemId: 'SellerSet',
			title: '员工',
			collapsible: false,
			defaults: {
				labelWidth: 89,
				anchor: '100%',
				layout: {
					type: 'hbox',
					defaultMargins: {top: 0, right: 5, bottom: 0, left: 0}
				}
			},
            items: [{
				xtype: 'container',
				id:'SellerSet',
				itemId: 'SellerContainer',
				layout: 'column',
                defaults: {
                	columnWidth: 1/3,
                	xtype: 'checkbox'
                }, 
            }]
        },{
        	xtype: 'fieldset',
			itemId: 'SellerGroupSet',
			title: '员工组',
			collapsible: false,
            items: [{
				xtype: 'container',
				id: 'SellerGroupSet',
				itemId: 'SellerGroupContainer',
				layout: 'column',
                defaults: {
                	columnWidth: 1/3,
                	margin: 5,
                }, 
                defaultType: 'checkbox'
            }]
        }],
        buttons:[{
        	text : '确 定',
			iconCls : 'accept',
			handler : function() {
				var employee = Ext.getCmp('SellerSet').items;
				var group = Ext.getCmp('SellerGroupSet').items;
                var value = '', display = '';
                for(var i = 0; i < employee.length; i++){
                    if(employee.get(i).checked){
                    	value += employee.get(i).inputValue + ",";
                    	display += employee.get(i).boxLabel + ",";
                    }
                }
                for(var i = 0; i < group.length; i++){
                    if(group.get(i).checked){
                    	value += group.get(i).inputValue + ",";
                    	display += group.get(i).boxLabel + ",";
                    }
                }
                var rowRecord = ProductGrid.getSelectionModel().getLastSelected();
                rowRecord.data.seller = value;
                rowRecord.commit();
                SellerWindow.hide();
			}
        }]
	});
	var PerformerWindow = Ext.create('Ext.Window',{
		layout : 'fit',
		title: '选择施工方',
		width : 400,
		height : 280,
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
		items : [PerformerPanel]
	});
	var SellerWindow = Ext.create('Ext.Window',{
		layout : 'fit',
		title: '选择销售方',
		width : 400,
		height : 280,
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
		items : [SellerPanel]
	});
	EmployeeStore.on('load', function(){
		if(!Ext.isEmpty(EmployeeStore)){
			Ext.each(EmployeeStore.data.items, function(employee){
				if(!employee.data.isGroup){
					SellerPanel.getComponent("SellerSet").getComponent("SellerContainer").add([{
						boxLabel: employee.data.name, inputValue: employee.data.id
					}]);
					PerformerPanel.getComponent("PerformerSet").getComponent("PerformerContainer").add([{
						boxLabel: employee.data.name, inputValue: employee.data.id
					}]);
				} else {
					SellerPanel.getComponent("SellerGroupSet").getComponent("SellerGroupContainer").add([{
						boxLabel: employee.data.name, inputValue: employee.data.id
					}]);
					PerformerPanel.getComponent("PerformerGroupSet").getComponent("PerformerGroupContainer").add([{
						boxLabel: employee.data.name, inputValue: employee.data.id
					}]);
					
				}
			});
		}
	});
	EmployeeStore.load();
	Ext.define('ProjectModel', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'typeId', type: 'string'},
	        {name: 'goodsId', type: 'string'},
	        {name: 'goodsType', type: 'int'},
	        {name: 'goodsName', type: 'string'},
	        {name: 'unitPrice', type: 'float'},
	        {name: 'discount', type: 'float'},
	        {name: 'price', type: 'float'},	//bool
	        {name: 'number', type: 'int'},
	        {name: 'performer', type: 'string'},
	        {name: 'endDate', type: 'string'},
	        {name: 'source', type: 'string'},
	        {name: 'isDeduction', type: 'int'}
	    ]
	});
	Ext.define('ProductModel', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'typeId', type: 'string'},
	        {name: 'goodsId', type: 'string'},
	        {name: 'goodsType', type: 'int'},
	        {name: 'goodsName', type: 'string'},
	        {name: 'unitPrice', type: 'float'},
	        {name: 'number', type: 'int'},
	        {name: 'discount', type: 'float'},
	        {name: 'price', type: 'float'},
	        {name: 'seller', type: 'string'},
	        {name: 'endDate', type: 'string'},
	        {name: 'source', type: 'string'},
	        {name: 'isDeduction', type: 'int'}
	    ]
	});
	var ProjectStore = new Ext.data.Store({
        autoDestroy: true,
        model: ProjectModel,
        proxy: {
			type: 'ajax',
			url : '/order/queryOrderProjectByOrderId.atc',
			reader : {
			    root: 'orderList'
			}
		}
    });
	var ProjectItemStore = new Ext.data.Store({
		fields : ['id', 'typeName', 'sortNo'],  
		proxy:{
            type: 'ajax',
            url : '/goods/queryHourTypeByDeptCode.atc',
            reader : {
                root: 'hourTypeList'
            }
        },
        sorters: [{
            property: 'sortNo',
            direction:'ASC'
        }]
	});
	ProjectItemStore.load();
	var ProjectItemCombo = Ext.create('Ext.form.field.ComboBox', {
		store: ProjectItemStore,
		valueField: 'id',
		displayField: 'typeName',
        editable : false,
        emptyText:'请选择...', 
        triggerAction: 'all',
        lastQuery: '',
        listeners: {
        	select: function(cb, records){
        		var record = records[0];
        		lastProject = record.data.id;
        		ProjectSecondItemStore.load({
        			params: {
        				typeId: Number(lastProject)
        			},
        			callback: function(records){
        				if(!Ext.isEmpty(records)){
        					ProjectSecondItemCombo.select(records[0]);
        					var rowRecord = ProjectGrid.getSelectionModel().getLastSelected();
        					insertProject();
        					completeProjectRow(rowRecord, records[0]);
        				}
                    }
        		});
        		var rowRecord = ProjectGrid.getSelectionModel().getLastSelected();
        		ProjectCellEditing.startEdit(rowRecord, 2);
            }
        }
	});
	ProjectItemStore.on('load', function(){
		var cRecord = ProjectItemStore.getAt(0);
		ProjectItemCombo.select(cRecord);
		lastProject = cRecord.data.id;
		ProjectSecondItemStore.load({
			params: {
				typeId: Number(lastProject)
			},
			callback: function(records){
				if(!Ext.isEmpty(records)){
					ProjectSecondItemCombo.select(records[0]);
					var rowRecord = ProjectGrid.getSelectionModel().getLastSelected();
					if(rowRecord){
						insertProject();
						completeProjectRow(rowRecord, records[0]);
					}
				}
            }
		});
		var rowRecord = ProjectGrid.getSelectionModel().getLastSelected();
		ProjectCellEditing.startEdit(rowRecord, 2);
	});
	var ProjectItemRenderer = function(value, cellmeta, record) {
        var index = ProjectItemStore.find("id", value);
        var record = ProjectItemStore.getAt(index);
        var displayText = "";
        if (record == null)
        	displayText = value;
        else
        	displayText = record.data.typeName;
        return displayText;
    };
	Ext.define('ProjectSecondItem', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'id', type: 'string'},
	        {name: 'name', type: 'string'},
	        {name: 'typeId', type: 'string'},
	        {name: 'price', type: 'float'}
	    ]
	});
	var ProjectSecondItemStore = new Ext.data.Store({
		autoLoad: false,
		model: ProjectSecondItem,
		proxy:{
            type: 'ajax',
            url : '/goods/queryGoodsHourByType.atc',
            reader : {
                root: 'hourList'
            }
        }
	});
	var ProjectSecondItemCombo = Ext.create('Ext.form.ComboBox', {
		store: ProjectSecondItemStore,
		valueField: 'id',
		displayField: 'name',
		queryMode: 'local',
        editable : true,
        selectOnFocus : true,
        emptyText:'请选择...', 
        typeAhead: true,
        triggerAction: 'all',
        lastQuery: '',
        listeners: {
        	select: function(cb, records){
                var record = records[0];
                if(!Ext.isEmpty(record)){
                	var rowRecord = ProjectGrid.getSelectionModel().getLastSelected();
                	completeProjectRow(rowRecord, record);
                	insertProject();
                	ProjectCellEditing.startEdit(rowRecord, 5);
                }
            },
            change: function(combox, newValue, oldValue){
            	var rowRecord = ProjectGrid.getSelectionModel().getLastSelected();
            	var rec = ProjectSecondItemStore.findRecord("name", newValue, 0, false, false, true);
            	if(Ext.isEmpty(rec) && rowRecord){
            		rowRecord.data.unitPrice = 0;
            		rowRecord.data.price = 0;
            		rowRecord.data.goodsName = newValue;
            		rowRecord.commit();
            	}
        	}
        }
	});
	var ProjectSecondItemRenderer = function(value, cellmeta, record) {
		return record.data.goodsName;
    };
	var ProjectCellEditing = new Ext.grid.plugin.CellEditing({
        clicksToEdit: 1
    });
	var ProjectRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	var ProjectTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	id: 'insert',
        	text: '添加项目',
        	iconCls: 'add',
        	handler: function() {
        		insertProject();
            }
        }]
    });
	var ProjectColumns= [ProjectRowNum,
	        {dataIndex: 'isDeduction', hidden: true},
	        {header: '大项',dataIndex: 'typeId', menuDisabled: true, editor: ProjectItemCombo, renderer: ProjectItemRenderer},
	        {header : '小项',dataIndex: 'goodsId', menuDisabled: true, width: '20%', editor: ProjectSecondItemCombo, renderer: ProjectSecondItemRenderer },
	        {header : '工时费',dataIndex: 'unitPrice',menuDisabled: true, xtype: 'numbercolumn', renderer: Ext.util.Format.numberRenderer("0.00"),
	        	editor: {xtype: 'numberfield', allowBlank: false, minValue: 0,
	        		listeners: {
                    	change: function(field, newValue, oldValue){
                    		var rowRecord = ProjectGrid.getSelectionModel().getLastSelected();
                    		rowRecord.data.price = Number(Number(newValue) * Number(rowRecord.data.discount == '0' ? '10' : rowRecord.data.discount) * 0.1).toFixed(2);
                    		rowRecord.commit();
                        }
                    }
	        	}
	        },
	        {header : '折扣',dataIndex: 'discount',menuDisabled: true,
            	editor: {xtype: 'numberfield', allowBlank: false, minValue: 0, maxValue: 10,
                    listeners: {
                    	change: function(field, newValue, oldValue){
                    		var rowRecord = ProjectGrid.getSelectionModel().getLastSelected();
                    		rowRecord.data.price = Number(Number(rowRecord.data.unitPrice) * Number(newValue) * 0.1).toFixed(2);
                    		rowRecord.commit();
                        }
                    }
            	}
            },
	        {header : '价格（元）', dataIndex: 'price', menuDisabled: true, xtype: 'numbercolumn', format:'0.00',
	        	editor: {xtype: 'numberfield', allowBlank: false, minValue: 0, listeners: {
  	            	specialkey : function(field, e) {
						if (e.getKey() == Ext.EventObject.ENTER) {
							var rowRecord = ProjectGrid.getSelectionModel().getLastSelected();
							ProjectCellEditing.startEdit(rowRecord, 6);
						}
					}
  	            }},
	        	summaryType: function(records){
                    var i = 0, length = records.length, total = 0, record;
                    for (; i < length; ++i) {
                        record = records[i];
                        total += Number(record.get('price')).toFixed(2) * 1;
                    }
                    return total;
                }
		    },
	        {header : '项目施工方',dataIndex: 'performer',menuDisabled: true,renderer: EmployeeRenderer,
		    	editor: {xtype: 'textfield', allowBlank: false,
                    listeners: {
                    	focus: function(field, newValue, oldValue){
                    		SellerWindow.hide();
                    		PerformerWindow.show();
                        }
                    }
            	}
		    },
	        {xtype: 'actioncolumn',header : '操作',width: 50,align:'center',sortable: false,menuDisabled: true,
                items: [{
                    iconCls: 'delete',
                    tooltip: '删除',
                    scope: this,
                    handler: function(grid, rowIndex){
                    	ProjectGrid.getStore().removeAt(rowIndex);
                    	completeReceivable();
                    }
                }]
            }
	];
	var ProjectGrid = Ext.create('Ext.grid.GridPanel', {
		minHeight: 220,
		title: '项目列表',
		columns : ProjectColumns,
		store : ProjectStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		frame : false,
		tbar : ProjectTbar, 
		stripeRows: true,
		forceFit: true,
		plugins: [ProjectCellEditing],
        features: [{
            ftype: 'summary',
            dock: 'bottom'
        }],
		viewConfig : {
			enableTextSelection:true,	//设置单元格可以选择
			getRowClass:changeRowClass
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
	    },
	    listeners: { 
	    	beforeedit: function (editor, e, eOpts) {
	    		//使用剩余服务中的项目是 不可编辑
	    		if(Number(e.record.data.goodsType) > 2 && (e.field == 'price' || e.field == 'goodsId' || e.field == 'typeId' || e.field == 'unitPrice' || e.field == 'discount'))
	    			return false;
	            return true;
        	}
        }
	});
	ProjectStore.on('update', function(){
		completeReceivable();
	});
	
	///////////////////////////////////////////////////////////////// 产品部分
	
	
	
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
		width: 130,
        editable : true,
        emptyText:'商品类型', 
        triggerAction: 'all',
        lastQuery: ''
	});
	
	
	var storeRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 30
	});
	
	var storeStore = Ext.create('Ext.data.Store', {
		fields: ['id', 'code', 'name', 'typeId', 'typeName', 'number', 'price', 'color', 'suitModel'],
		proxy: {
			type: 'ajax',
			url : '/goods/queryGoodsMaterialStockByKeyword.atc',
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
       {header : '库存数量',dataIndex: 'number', width: 80},
       {header : '零售价',dataIndex: 'price', width: 80, xtype: 'numbercolumn', format:'0.00'},
       {header : '颜色',dataIndex: 'color', width: 80, hidden: true},
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
        },'->',{
        	xtype: 'textfield',
        	id: 'choiceSellerDisplay',
        	emptyText : '产品销售方',
        	listeners: {
            	focus: function(field, newValue, oldValue){
            		PerformerWindow.hide();
            		SellerWindow.show();
                }
            }
        },{
        	xtype: 'hiddenfield',
        	id: 'choiceSeller',
        	emptyText : '产品销售方'
        }]
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
		stripeRows:true,
		forceFit: true,
		viewConfig : {
			enableTextSelection: true,
			getRowClass:changeStockRowClass
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
			typeId : ProductTypeCombo.getValue(),
			keyword: Ext.getCmp("keyword").getValue()
		};
	}
	
	function choiceInStoreGoods(){
		var storeSelect = storeSelModel.getSelection();
        var saller = Ext.getCmp("choiceSeller").getValue();
        Ext.each(storeSelect, function(item){
        	if(!Ext.isEmpty(item.data.id)){
        		var record = new ProductModel({
    				typeId: item.data.typeId,
    				goodsId: item.data.id,
    				goodsName: item.data.code + "/" + item.data.name,
    				goodsType: 2,
    				unitPrice: item.data.price,
    				number: 1,
    				discount: Ext.isEmpty(memberGrade) ? 0 : memberGrade.data.discount,
    				price: Ext.isEmpty(memberGrade) ? item.data.price : (item.data.price * (memberGrade.data.discount == 0?1:memberGrade.data.discount*0.1)),
    				seller: saller,
    				source: '',
    				endDate: '',
    				isDeduction: 0
    			});
        		ProductStore.insert(ProductStore.getCount(), record);
        	}
        });  
        choiceGoodsWindow.hide();
        completeReceivable();
	}
	
	//小于报警库存时， 改变grid行背景色
	function changeStockRowClass(record, rowIndex, rowParams, store){
	    if(Number(record.data.number) <= stockWarn)
	    	return 'x-grid-font-red';
	}
	
	
//	var StockStore = Ext.create('Ext.data.Store', {
//		fields: ['id', 'code', 'name', 'number', 'price'],
//		proxy: {
//            type: 'ajax',
//            url : '/goods/queryGoodsMaterialStockByKeyword.atc',
//            reader : {
//                root: 'materialList',
//                totalProperty: 'totalCount'
//            }
//        }
//    });
//	var ProductTypeCombo = Ext.create('Ext.form.field.ComboBox', {
//		store: ProductTypeStore,
//		valueField: 'id',
//		displayField: 'typeName',
//        editable : false,
//        emptyText:'请选择...', 
//        triggerAction: 'all',
//        lastQuery: '',
//        listeners: {
//        	select: function(cb, records){
//        		var record = records[0];
//        		var rowRecord = ProductGrid.getSelectionModel().getLastSelected();
//        		rowRecord.data.goodsName = record.data.name;
//        		ProductCellEditing.startEdit(rowRecord, 2);
//        		StockStore.getProxy().url = "/goods/queryGoodsMaterialStockByKeyword.atc?typeId=" + record.data.id;
//            }
//        }
//	});
//	var StockCombo = Ext.create('Ext.form.ComboBox', {
//        store: StockStore,
//        labelWidth : 40,
//        valueField: 'id',
//        displayField: 'name',
//        typeAhead: false,
//        hideTrigger:true,
//        width: 300,
//        queryParam: 'keyword',	//搜索参数字段
//        minChars: 1,
//        pageSize: 10,
//        listConfig: {
//            loadingText: '查找中...',
//            emptyText: '<div style="padding: 5px;">没有找到相关商品！</div>',
//            getInnerTpl: function() {
//                return '<div class="search">{code}/{name}/{price}/ 库存:{number}件</div>';
//            }
//        },
//        listeners : {
//        	select: function(cb, records) {
//            	var record = records[0];
//            	var rowRecord = ProductGrid.getSelectionModel().getLastSelected();
//            	rowRecord.data.unitPrice = record.data.price;
//            	rowRecord.data.price = record.data.price;
//            	rowRecord.data.goodsName = record.data.name;
//            	rowRecord.commit();
//            	insertProduct();
//            	ProductCellEditing.startEdit(rowRecord, 4);
//			}
//		}
//	});
	ProductStore = new Ext.data.Store({
        autoDestroy: true,
        model: ProductModel,
        proxy: {
			type: 'ajax',
			url : '/order/queryOrderProductByOrderId.atc',
			reader : {
			    root: 'orderList'
			}
		}
    });
	var ProductCellEditing = new Ext.grid.plugin.CellEditing({
        clicksToEdit: 1
    });
	var ProductRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	var ProductTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	text: '添加产品',
        	iconCls: 'add',
        	handler: function() {
        		choiceGoodsWindow.show();
//        		insertProduct();
            }
        },{
        	xtype: 'button',
        	text: '全部删除',
        	iconCls: 'delete',
        	handler: function() {
        		ProductStore.removeAll();
        	}
        }]
    });
	var ProductTypeRenderer = function(value, cellmeta, record) {
        var index = ProductTypeStore.find("id", value);
        var record = ProductTypeStore.getAt(index);
        var displayText = "";
        if (record == null)
        	displayText = value;
        else
        	displayText = record.data.typeName;
        return displayText;
    };
	var ProductColumns= [ProductRowNum,
	        {dataIndex: 'isDeduction', hidden: true},
	        {header : '类型',dataIndex: 'typeId',menuDisabled: true,width: '12%', renderer: ProductTypeRenderer},
	        {header : '产品',dataIndex: 'goodsId', hidden: true},
	        {header : '产品',dataIndex: 'goodsName',menuDisabled: true,width: '30%'},
	        {header : '单价（元）',dataIndex: 'unitPrice',menuDisabled: true,editor: {xtype: 'numberfield'}, renderer: Ext.util.Format.numberRenderer("0.00"),
	        	listeners: {
                	change: function(field, newValue, oldValue){
                		var rowRecord = ProductGrid.getSelectionModel().getLastSelected();
                		rowRecord.data.price = Number(rowRecord.data.number) * Number(newValue) * Number(rowRecord.data.discount == '0' ? '10' : rowRecord.data.discount) * 0.1;
                		rowRecord.commit();
                	}
            }},
	        {header : '数量',dataIndex: 'number',menuDisabled: true,summaryType: 'sum', editor: {xtype: 'numberfield', allowBlank: false, minValue: 0, maxValue: 100000,
                listeners: {
                	change: function(field, newValue, oldValue){
                		var rowRecord = ProductGrid.getSelectionModel().getLastSelected();
                		rowRecord.data.price = Number(rowRecord.data.unitPrice) * Number(newValue) * Number(rowRecord.data.discount == '0' ? '10' : rowRecord.data.discount) * 0.1;
                		rowRecord.commit();
                    }
                }
            }},
	        {header : '折扣',dataIndex: 'discount',menuDisabled: true,
            	editor: {
            		xtype: 'numberfield',
            		allowBlank: false,
                    minValue: 0,
                    maxValue: 10,
                    listeners: {
                    	change: function(field, newValue, oldValue){
                    		var rowRecord = ProductGrid.getSelectionModel().getLastSelected();
                    		rowRecord.data.price = Number(rowRecord.data.unitPrice) * Number(rowRecord.data.number) * Number(newValue) * 0.1;
                    		rowRecord.commit();
                        }
                    }
            }},
	        {header : '总价（元）',dataIndex: 'price',menuDisabled: true,xtype: 'numbercolumn', format:'0.00', summaryType: 'sum',
	        	editor: {xtype: 'numberfield', allowBlank: false, minValue: 0,
	        		listeners: {
                    	change: function(field, newValue, oldValue){
                    		var rowRecord = ProjectGrid.getSelectionModel().getLastSelected();
                    		rowRecord.commit();
                        }
                    }
	        	},
	        	summaryType: function(records){
                    var i = 0, length = records.length, total = 0, record;
                    for (; i < length; ++i) {
                        record = records[i];
                        total += Number(record.get('price')).toFixed(2) * 1;
                        //total += record.get('unitPrice') * record.get('number') * (record.get('discount') == 0 ? 10 : record.get('discount')) * 0.1;
                    }
                    return total;
                }
		    },
		    {header : '产品销售方',dataIndex: 'seller',menuDisabled: true, renderer: EmployeeRenderer,
		    	editor: {xtype: 'textfield', allowBlank: false,
                    listeners: {
                    	focus: function(field, newValue, oldValue){
                    		PerformerWindow.hide();
                    		SellerWindow.show();
                        }
                    }
            	}
		    },
		    {dataIndex: 'number', hidden: true},
	        {xtype: 'actioncolumn',header : '操作',width: 50,align:'center',sortable: false,menuDisabled: true,
                items: [{
                    iconCls: 'delete',
                    tooltip: '删除',
                    scope: this,
                    handler: function(grid, rowIndex){
                    	ProductGrid.getStore().removeAt(rowIndex);
                    	completeReceivable();
                    }
                }]
            }
	];
	var ProductGrid = Ext.create('Ext.grid.GridPanel', {
		minHeight: 210,
		title: '产品消耗列表',
		columns : ProductColumns,
		store : ProductStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		frame : false,
		tbar : ProductTbar, 
		stripeRows: true,
		forceFit: true,
		plugins: [ProductCellEditing],
        features: [{
            ftype: 'summary',
            dock: 'bottom'
        }],
		viewConfig : {
			enableTextSelection: true,	//设置单元格可以选择
			getRowClass:changeRowClass
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
	    },
	    listeners: { 
	    	beforeedit: function (editor, e, eOpts) {
	    		//使用剩余服务中的项目是 不可编辑
	    		if(Number(e.record.data.goodsType) > 2)
	    			return false;
	            return true;
        	}
        }
	});
	ProductStore.on('update', function(){
		completeReceivable();
	});
	
	
	//////////////////////////////////////服务赠送
	Ext.define('GiveProjectModel', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'typeId', type: 'string'},
	        {name: 'goodsId', type: 'string'},
	        {name: 'goodsName', type: 'string'},
	        {name: 'unitPrice', type: 'float'},
	        {name: 'number', type: 'int'}
	    ]
	});
	Ext.define('GiveProjectSecondItem', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'id', type: 'string'},
	        {name: 'name', type: 'string'},
	        {name: 'typeId', type: 'string'},
	        {name: 'price', type: 'float'}
	    ]
	});
	var GiveProjectStore = new Ext.data.Store({
        autoDestroy: true,
        model: GiveProjectModel
    });
	var GiveProjectItemStore = new Ext.data.Store({
		autoLoad: false,
		fields : ['id', 'typeName', 'sortNo'],  
		proxy:{
            type: 'ajax',
            url : '/goods/queryHourTypeByDeptCode.atc',
            reader : {
                root: 'hourTypeList'
            }
        },
        sorters: [{
            property: 'sortNo',
            direction:'ASC'
        }]
	});
	var GiveProjectSecondItemStore = new Ext.data.Store({
		autoLoad: false,
		model: GiveProjectSecondItem,
		proxy:{
            type: 'ajax',
            url : '/goods/queryGoodsHourByType.atc',
            reader : {
                root: 'hourList'
            }
        }
	});
	var GiveProjectItemCombo = Ext.create('Ext.form.field.ComboBox', {
		store: GiveProjectItemStore,
		valueField: 'id',
		displayField: 'typeName',
        editable : false,
        emptyText:'请选择...', 
        triggerAction: 'all',
        lastQuery: '',
        listeners: {
        	select: function(cb, records){
        		var record = records[0];
        		GiveProjectSecondItemStore.load({
        			params: {
        				typeId: Number(record.data.id)
                    }
        		});
        		var rowRecord = GiveProjectGrid.getSelectionModel().getLastSelected();
        		GiveProjectCellEditing.startEdit(rowRecord, 2);
            }
        }
	});
	var GiveProjectSecondItemCombo = Ext.create('Ext.form.ComboBox', {
		store: GiveProjectSecondItemStore,
		valueField: 'id',
		displayField: 'name',
		queryMode: 'local',
        editable : true,
        selectOnFocus : true,
        emptyText:'请选择...', 
        typeAhead: true,
        triggerAction: 'all',
        lastQuery: '',
        listeners: {
        	select: function(cb, records){
                var record = records[0];
                if(!Ext.isEmpty(record)){
                	var rowRecord = GiveProjectGrid.getSelectionModel().getLastSelected();
                	rowRecord.data.unitPrice = record.data.price;
                	rowRecord.data.goodsName = record.data.name;
                	GiveProjectCellEditing.startEdit(rowRecord, 4);
                }
        	}
        }
	});
	var GiveProjectCellEditing = new Ext.grid.plugin.CellEditing({
        clicksToEdit: 1
    });
	var GiveProjectRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	var GiveProjectTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	text: '添加项目',
        	iconCls: 'add',
        	handler: function() {
        		insertGiveProject();
            }
        },{
        	xtype: 'button',
        	text: '确认',
        	iconCls: 'accept',
        	handler: function() {
        		GiveProjectWindow.hide();
            }
        }]
    });
	var GiveProjectItemRenderer = function(value, cellmeta, record) {
        var index = GiveProjectItemStore.find("id", value);
        var record = GiveProjectItemStore.getAt(index);
        var displayText = "";
        if (record == null)
        	displayText = value;
        else
        	displayText = record.data.typeName;
        return displayText;
    };
    var GiveProjectSecondItemRenderer = function(value, cellmeta, record) {
        var index = GiveProjectSecondItemStore.find("id", value);
        var record = GiveProjectSecondItemStore.getAt(index);
        var displayText = "";
        if (record == null)
        	displayText = value;
        else
        	displayText = record.data.name;
        return displayText;
    };
	var GiveProjectColumns= [GiveProjectRowNum,
	        {header: '大项',dataIndex: 'typeId',menuDisabled: true, editor: GiveProjectItemCombo, renderer: GiveProjectItemRenderer},
	        {header : '小项',dataIndex: 'goodsId',menuDisabled: true, width: '18%', editor: GiveProjectSecondItemCombo, renderer: GiveProjectSecondItemRenderer },
	        {header : '工时费',dataIndex: 'unitPrice',menuDisabled: true, xtype: 'numbercolumn', renderer: Ext.util.Format.numberRenderer("0.00")},
	        {header : '次数',dataIndex: 'number',menuDisabled: true, editor: {xtype: 'numberfield', allowBlank: false, minValue: 0, maxValue: 100000}},
	        {xtype: 'actioncolumn',header : '操作',width: 50,align:'center',sortable: false,menuDisabled: true,
                items: [{
                    iconCls: 'delete',
                    tooltip: '删除',
                    scope: this,
                    handler: function(grid, rowIndex){
                    	GiveProjectGrid.getStore().removeAt(rowIndex);
                    }
                }]
            }
	];
	var GiveProjectGrid = Ext.create('Ext.grid.GridPanel', {
		minHeight: 220,
		columns : GiveProjectColumns,
		store : GiveProjectStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		frame : false,
		tbar : GiveProjectTbar, 
		stripeRows: true,
		forceFit: true,
		plugins: [GiveProjectCellEditing],
		viewConfig : {
			enableTextSelection:true	//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
	    }
	});
	
	var GiveProjectWindow = Ext.create('Ext.Window',{
		layout : 'fit',
		title: '服务赠送',
		iconCls: 'award_star_gold_2',
		width : 600,
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
		items: [GiveProjectGrid]
	});
	
	GiveProjectWindow.on('show', function(){
		GiveProjectItemStore.load();
		if(GiveProjectStore.getCount() == 0)
			insertGiveProject();
	});
	
	/////////////////////////////////////// 商品赠送
	
	Ext.define('GiveProductModel', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'typeId', type: 'string'},
	        {name: 'goodsId', type: 'string'},
	        {name: 'goodsName', type: 'string'},
	        {name: 'unitPrice', type: 'float'},
	        {name: 'number', type: 'int'}
	    ]
	});
	var GiveProductStore = new Ext.data.Store({
        autoDestroy: true,
        model: GiveProductModel
    });
	
	var GiveProductTypeStore = Ext.create('Ext.data.Store', {
		fields: ['id', 'typeName'],
        proxy: {
            type: 'ajax',
            url : '/goods/queryMaterialTypeByDeptCode.atc',
            reader : {
                root: 'materialTypeList'
            }
        }
	});
	var GiveStockStore = Ext.create('Ext.data.Store', {
		fields: ['id', 'code', 'name', 'number', 'price'],
		proxy: {
            type: 'ajax',
            url : '/goods/queryGoodsMaterialStockByKeyword.atc',
            reader : {
                root: 'materialList',
                totalProperty: 'totalCount'
            }
        }
    });
	var GiveProductTypeCombo = Ext.create('Ext.form.field.ComboBox', {
		store: GiveProductTypeStore,
		valueField: 'id',
		displayField: 'typeName',
        editable : false,
        emptyText:'请选择...', 
        triggerAction: 'all',
        lastQuery: '',
        listeners: {
        	select: function(cb, records){
        		var record = records[0];
        		var rowRecord = GiveProductGrid.getSelectionModel().getLastSelected();
        		rowRecord.data.goodsName = record.data.name;
        		GiveProductCellEditing.startEdit(rowRecord, 2);
        		GiveStockStore.getProxy().url = "/goods/queryGoodsMaterialStockByKeyword.atc?typeId=" + record.data.id;
            }
        }
	});
	var GiveStockCombo = Ext.create('Ext.form.ComboBox', {
        store: GiveStockStore,
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
                return '<div>{code}/{name}/{price}/ {number}件</div>';
            }
        },
        listeners : {
        	select: function(cb, records) {
            	var record = records[0];
            	var rowRecord = GiveProductGrid.getSelectionModel().getLastSelected();
            	rowRecord.data.unitPrice = record.data.price;
            	rowRecord.data.goodsName = record.data.name;
            	rowRecord.commit();
            	GiveProductCellEditing.startEdit(rowRecord, 4);
			}
		}
	});
	GiveProductStore = new Ext.data.Store({
        autoDestroy: true,
        model: GiveProductModel
    });
	var GiveProductCellEditing = new Ext.grid.plugin.CellEditing({
        clicksToEdit: 1
    });
	var GiveProductRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	var GiveProductTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	text: '添加产品',
        	iconCls: 'add',
        	handler: function() {
        		insertGiveProduct();
            }
        },{
        	xtype: 'button',
        	text: '确认',
        	iconCls: 'accept',
        	handler: function() {
        		GiveProductWindow.hide();
            }
        }]
    });
	var GiveProductTypeRenderer = function(value, cellmeta, record) {
        var index = GiveProductTypeStore.find("id", value);
        var record = GiveProductTypeStore.getAt(index);
        var displayText = "";
        if (record == null)
        	displayText = value;
        else
        	displayText = record.data.typeName;
        return displayText;
    };
    var GiveProductRenderer = function(value, cellmeta, record) {
        var index = GiveStockStore.find("id", value);
        var record = GiveStockStore.getAt(index);
        var displayText = "";
        if (record == null)
        	displayText = value;
        else {
        	if(Number(record.data.number) > stockWarn)
        		displayText = record.data.code + "/" + record.data.name;
        	else
        		displayText = "<font color=red>" + record.data.code + "/" + record.data.name + "（库存" + record.data.number + "件）</font>";
        }
        return displayText;
    };
	var GiveProductColumns= [GiveProductRowNum,
	        {header : '类型',dataIndex: 'typeId',menuDisabled: true,width: '16%', editor: GiveProductTypeCombo, renderer: GiveProductTypeRenderer},
	        {header : '产品',dataIndex: 'goodsId',menuDisabled: true,width: '45%', editor: GiveStockCombo, renderer: GiveProductRenderer },
	        {header : '单价（元）',dataIndex: 'unitPrice',menuDisabled: true,xtype: 'numbercolumn', renderer: Ext.util.Format.numberRenderer("0.00")},
	        {header : '数量',dataIndex: 'number',menuDisabled: true, editor: {xtype: 'numberfield', allowBlank: false, minValue: 0}},
	        {xtype: 'actioncolumn',header : '操作',width: 50,align:'center',sortable: false, menuDisabled: true,
                items: [{
                    iconCls: 'delete',
                    tooltip: '删除',
                    scope: this,
                    handler: function(grid, rowIndex){
                    	GiveProductGrid.getStore().removeAt(rowIndex);
                    }
                }]
            }
	];
	
	var GiveProductGrid = Ext.create('Ext.grid.GridPanel', {
		minHeight: 220,
		columns : GiveProductColumns,
		store : GiveProductStore, 
		region : 'center',
		autoScroll : true,
		columnLines : true,
		frame : false,
		tbar : GiveProductTbar, 
		stripeRows: true,
		forceFit: true,
		plugins: [GiveProductCellEditing],
		viewConfig : {
			enableTextSelection:true	//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
	    }
	});
	
	var GiveProductWindow = Ext.create('Ext.Window',{
		layout : 'fit',
		title: '商品赠送',
		iconCls: 'mouse',
		width : 600,
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
		items: [GiveProductGrid]
	});
	
	GiveProductWindow.on('show', function(){
		GiveProductTypeStore.load();
		if(GiveProductStore.getCount() == 0)
			insertGiveProduct();
	});
	
	/////////////////////////////////////// 卡券赠送
	
	var GiveCouponsRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 40
	});
	
	var GiveCouponsStore = Ext.create('Ext.data.Store', {
        fields: ['id', 'name', 'price', 'applyId', 'minimum', 'goodsName'],
        proxy: {
            type: 'ajax',
            url : '/goods/queryCouponsByDept.atc',
            reader : {
                root: 'couponsList'
            }
        }
	});
	
	var GiveCouponsSelModel = Ext.create('Ext.selection.CheckboxModel', {
		injectCheckbox: 1
//		mode: 'SINGLE'
	});
	
	var GiveCouponsTbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : false,
        items: [{
        	xtype: 'button',
        	text: '确 认',
        	iconCls: 'accept',
        	handler: function() {
        		GiveCouponsWindow.hide();
            }
        }]
    });
	
	var GiveCouponsColumns= [GiveCouponsRowNum,
                {header : 'ID',dataIndex: 'id', hidden: true},
 	            {header : '名称',dataIndex: 'name'},
 	            {header : '抵扣金额',dataIndex: 'price', renderer: Ext.util.Format.numberRenderer("0.00")},
 	            {header : '最低消费',dataIndex: 'minimum', renderer: Ext.util.Format.numberRenderer("0.00")},
 	            {header : '适用服务',dataIndex: 'applyId', hidden: true},
 	            {header : '适用服务',dataIndex: 'goodsName'}
     	];
	
	var GiveCouponsGrid = Ext.create('Ext.grid.GridPanel', {
		minHeight: 220,
		columns : GiveCouponsColumns,
		store : GiveCouponsStore, 
		selModel : GiveCouponsSelModel,
		region : 'center',
		autoScroll : true,
		columnLines : true,
		frame : false,
		tbar : GiveCouponsTbar, 
		stripeRows: true,
		forceFit: true,
		viewConfig : {
			enableTextSelection:true	//设置单元格可以选择
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
	    }
	});
	
	var GiveCouponsWindow = Ext.create('Ext.Window',{
		layout : 'fit',
		title: '卡券赠送',
		iconCls: 'creditcards',
		width : 600,
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
		items: [GiveCouponsGrid]
	});
	
	GiveCouponsWindow.on('show', function(){
		GiveCouponsStore.load();
	});
	
	/////////////////////////////////////////////////// 支付方式
    var pbalance = Ext.create('Ext.button.Split', {
        text: '余 额',
        itemId: 'pbalance',
        margin: '5 5 0 0',
        handler: onButtonPay,
        menu : {
            items: [{
            	xtype: 'numberfield', id: 'pbalance', minValue: 0, fieldLabel: '金额', labelWidth: 30, width: 150,
            	listeners: {
                	change: function(field, newValue, oldValue){
                		if(Ext.isEmpty(memberRecord)){
                			Ext.MessageBox.show({
              		           title: '提示',
              		           msg: '请先选择会员！',
              		           buttons: Ext.MessageBox.OK,
              		           icon: Ext.MessageBox.INFO
                 			});
                			return false;
                		}
                		completeActual(field);
                    }
                }
            }]
        }
    });
	var pcash = Ext.create('Ext.button.Split', {
        text: '现 金',
        itemId: 'pcash',
        margin: '5 5 0 0',
        handler: onButtonPay,
        menu : {
            items: [{
            	xtype: 'numberfield', id: 'pcash', minValue: 0, fieldLabel: '金额', labelWidth: 30, width: 150,
            	listeners: {
                	change: function(field, newValue, oldValue){
                		completeActual(field);
                    }
                }
            }]
        }
    });
	var pcard = Ext.create('Ext.button.Split', {
        text: '刷 卡',
        itemId: 'pcard',
        margin: '5 5 0 0',
        handler: onButtonPay,
        menu : {
            items: [{
            	xtype: 'numberfield', id: 'pcard', minValue: 0, fieldLabel: '金额', labelWidth: 30, width: 150,
            	listeners: {
                	change: function(field, newValue, oldValue){
                		completeActual(field);
                    }
                }
            }]
        }
    });
	var ptransfer = Ext.create('Ext.button.Split', {
        text: '转 账',
        itemId: 'ptransfer',
        margin: '5 5 0 0',
        handler: onButtonPay,
        menu : {
            items: [{
            	xtype: 'numberfield', id: 'ptransfer', minValue: 0, fieldLabel: '金额', labelWidth: 30, width: 150,
            	listeners: {
                	change: function(field, newValue, oldValue){
                		completeActual(field);
                    }
                }
            }]
        }
    });
	var pwechat = Ext.create('Ext.button.Split', {
        text: '微 信',
        itemId: 'pwechat',
        margin: '5 5 0 0',
        handler: onButtonPay,
        menu : {
            items: [{
            	xtype: 'numberfield', id: 'pwechat', minValue: 0, fieldLabel: '金额', labelWidth: 30, width: 150,
            	listeners: {
                	change: function(field, newValue, oldValue){
                		completeActual(field);
                    }
                }
            }]
        }
    });
	var palipay = Ext.create('Ext.button.Split', {
        text: '支付宝',
        itemId: 'palipay',
        margin: '5 5 0 0',
        handler: onButtonPay,
        menu : {
            items: [{
            	xtype: 'numberfield', id: 'palipay', minValue: 0, fieldLabel: '金额', labelWidth: 30, width: 150,
            	listeners: {
                	change: function(field, newValue, oldValue){
                		completeActual(field);
                    }
                }
            }]
        }
    });
	var pbill = Ext.create('Ext.button.Split', {
        text: '挂 账',
        itemId: 'pbill',
        margin: '5 5 0 0',
        handler: onButtonPay,
        menu : {
            items: [{
            	xtype: 'numberfield', id: 'pbill', minValue: 0, fieldLabel: '金额', labelWidth: 30, width: 150,
            	listeners: {
                	change: function(field, newValue, oldValue){
                		completeActual(field);
                    }
                }
            }]
        }
    });
	
	//订单提交
	var OrderSubmit = Ext.create('Ext.form.FormPanel',{
		title: '订单支付',
		iconCls: 'money_dollar',
		bodyPadding: '0 10 0 10',
		defaults: {
            anchor: '100%',
            labelWidth: 100
        },
		items : [{
			layout : 'column',
			border : false,
			items : [{
				columnWidth : .30,
				minWidth: 280,
				border : false,
				xtype: 'toolbar',
				style:{
					padding: '10 0 0 0',
					marginLeft: '20'
				},
				items: [{
				    xtype:'button',
				    text: '服务赠送',
				    iconCls: 'award_star_gold_2',
				    handler : function() {
				    	GiveProjectWindow.show();
				    }
				},{
					xtype:'button',
					text: '商品赠送',
					iconCls: 'mouse',
					handler : function() {
						GiveProductWindow.show();
				    }
				},{
				    xtype:'button',
				    text: '卡券赠送',
				    iconCls: 'creditcards',
				    handler : function() {
						GiveCouponsWindow.show();
				    }
				}]
			},{
				columnWidth : .20,
				id : 'estimate',
				xtype: 'datefield',
				fieldLabel: '预计交车时间',
				padding: '5 0 0 0',
				labelWidth: 100,
				labelAlign: 'right',
				format: 'Y/m/d',
				width : 200
			},{
				columnWidth : .15,
				id : 'deductible',
				xtype: 'numberfield',
				fieldLabel: '积分',
				padding: '5 0 0 0',
				labelWidth: 40,
				labelAlign: 'right',
				width : 180,
				minValue: 100,
				maxText: '积分不足',
				value: 0,
				disabled: true,
				listeners: {
                	change: function(field, newValue, oldValue){
                		if(field.isValid()){
                			var point = Number(memberRecord.data.point);
                			if(Number(newValue) <= point){
                				poffset = (Number(newValue) / Number(offsetParam)).toFixed(2);
                				Ext.getCmp("poffset").setValue("可抵现" + poffset + "元");
                			}
                			completeReceivable();
                		}
                    }
                }
			}, {
				columnWidth : .13,
				id: 'poffset',
				xtype: 'displayfield',
				minWidth : 90,
				value: '可抵现0.00元',
				padding: '5 0 0 0',
				margin: '0 10 0 5'
			}, {
				columnWidth : .22,
				border : false,
				width : 200,
				padding: '5 0 0 0',
				style :{float: 'right'},
				items : [{
					id : 'receivable',
					xtype: 'textfield',
					fieldLabel : '应收',
					labelAlign: 'right',
					labelStyle : 'font-size: 16px;',
					fieldStyle : 'font-size: 16px; color:red;',
					labelWidth: 60,
					format:'0.00',
					value: '0.00',
					anchor : '100%',
					listeners: {
	                	change: function(field, newValue, oldValue){
	                		if(!isNaN(field.value)) {
	                			receivable = newValue;
	                			if(memberRecord){
	                				if(receivable > poffset && Number(memberRecord.data.point) > 0)
	                					Ext.getCmp("deductible").setDisabled(false);
	                				else
	                					Ext.getCmp("deductible").setDisabled(true);
	                			}
	                		} else {
	                			Ext.MessageBox.show({
	                				title: '提示',
	                				msg: '请输入有效数字！',
	                				buttons: Ext.MessageBox.OK,
	                				icon: Ext.MessageBox.ERROR
	                			});
	                			completeReceivable();
	                		}
	                    },
	                    blur: function(field){
	                    	if(!isNaN(field.value)) {
	                    		field.setValue(Number(field.value).toFixed(2));
	                    	}
	                    }
	                }
				}]
			}]
		},{
			layout : 'column',
			items: [{
				columnWidth : .78,
				id: 'payRow',
				items:[pbalance, pcash, pcard, ptransfer, pwechat, palipay, pbill]
			},{
				columnWidth : .22,
				border : false,
				id: 'actual',
				xtype: 'textfield',
				labelWidth: 60,
				fieldLabel : '实收',
				labelAlign: 'right',
				labelStyle : 'font-size: 16px;',
				fieldStyle : 'font-size: 16px; color:red; border: 0;',
				readOnly: true,
				value: '0.00'
			}]
		},{
			layout : {type : 'hbox',align : 'middle'},
			items: [{
				labelWidth: 40,
				id: 'orderRemark',
				xtype: 'textarea',
				rows: 2,
				width:'50%',
				margin: '10 0 0 0',
				fieldLabel: '备注'
			}, {xtype:'tbspacer',flex:1}, {
				minWidth: 350,
				border : false,
				bbar: ['->',{
					xtype: 'displayfield',
					id: 'deposit',
					fieldLabel: '定金',
					labelAlign: 'right',
					labelWidth: 40,
					margin: '0 20 0 0',
					labelStyle : 'font-size: 16px;',
					fieldStyle : 'font-size: 16px; color:red; border: 0;'
				},{
				    xtype:'button',
				    text: '保存',
				    padding: '5',
				    scale: 'medium',
				    iconCls: 'disk',
				    handler : function() {
				    	submitOrder(2);
				    }
				},{
				    xtype:'button',
				    padding: '5',
				    scale: 'medium',
//				    style:'background-image:-webkit-linear-gradient(top,#d3a203,#dba907)',
				    text: '组合消费',
				    iconCls: 'accept',
				    handler : function() {
				    	submitOrder(1);
				    }
				}]
			}]
		}]
	});
	
	/////////////////////////////////////////////////////////////  板块布局   //////////////////////////////////////////////////
	
	var MemberDockedItems = [{
        xtype: 'toolbar',
        dock: 'bottom',
        items: [
//            { xtype: 'button', iconCls:'money', text: '会员充值' },
            { xtype: 'button', iconCls:'award_star_bronze_3', text: '历史消费',handler:function(){HistorySalesWindow.show();} },
//            { xtype: 'button', iconCls:'table_relationship', text: '绑定微信' },
            { xtype: 'button', iconCls: 'car', id: 'addCar', text:'添加车辆',
	    		   handler: function() {
	    			   if(!Ext.isEmpty(memberRecord)){
	    				   LoadAreaInfo(parent.EE);
	    				   CarForm.getForm().reset();
	    				   CarWindow.setTitle("添加车辆");
	    				   Ext.getCmp("carMemberId").setValue(memberRecord.data.memId);
	    				   CarForm.getForm().url = "/member/insertCar.atc";
	    				   CarWindow.show();
	    			   } else {
	    				   Ext.MessageBox.show({
	    					   title: '提示',
					           msg: "请先选择会员!",
					           buttons: Ext.MessageBox.OK,
					           icon: Ext.MessageBox.INFO
	    				   });
	    			   }
	    		   }
     }
        ]
    }];
	
	//订单信息
	var OrderInfo = Ext.create('Ext.panel.Panel',{
		region: 'center',
        id: 'orderInfo',
        title: '订单信息',
        iconCls:'application_view_columns',
        border: false,
        autoScroll: true,
        animCollapse: true,
        tools:[{
            type:'help',
            tooltip: '操作指引',
            callback: function(panel, tool, event) {
            	parent.global.openSystemHelpTab();
            }
        }],
        items: [{
        	layout:'anchor',
            items:[OrderHead, ProjectGrid, ProductGrid, OrderSubmit]
        }]
	});
	
	//会员信息
	var MemberInfo = Ext.create('Ext.panel.Panel',{
		region: 'east',
        id: 'memberInfo',
        title: '会员信息',
        iconCls:'user',
        split: true,
        width: '32%',
        minWidth: 400,
        border: false,
        autoScroll: true,
        collapsible: true,
        animCollapse: true,
        items:[InfoPanel, CarsPanel, SurplusForm],
        dockedItems: MemberDockedItems,
        //鉴定会员面板展开与收缩，重新布局订单支付模块
        listeners: {
        	expand: function(panel){
        		OrderSubmit.updateLayout();
            },
            collapse: function(panel){
        		OrderSubmit.updateLayout();
            }
        }
	});
	
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [OrderInfo, MemberInfo] 
	});
	
	
	/////////////////////////////////////////////////////////////  页面初始化  //////////////////////////////////////////////////
	
	//加载门店参数配置信息
	completeShopParam();
	
	if(!Ext.isEmpty(orderId)){
		Ext.getCmp("orderId").setValue(orderId);
		Ext.MessageBox.show({
			title: '请稍等',
			msg: '正在加载订单 ...',
			width: 200,
			progress: false,
			closable: false
        });
		Ext.Ajax.request({
			async: false,   //ASYNC 是否异步( TRUE 异步 , FALSE 同步)
			url : '/order/queryOrderHeadById.atc',
			params : {orderNo: orderId},
			success : function(resp, opts) {
				var orderHead = Ext.decode(resp.responseText);
				if(orderHead && !Ext.isEmpty(orderHead)){
					pdeposit = Number(orderHead.pdeposit);
					MemberCombo.setRawValue(orderHead.carNumber);
					MemberCombo.setReadOnly(true);
					CarCombo.setValue(orderHead.carId);
					EmployeeSingleCombo.setRawValue(orderHead.creator);
					//Ext.getCmp("saleDate").setValue(orderHead.saleDate.substring(0, 10));
					Ext.getCmp("saleTime").setValue(orderHead.saleDate.substring(11, 16));
					Ext.getCmp("deposit").setValue(pdeposit.toFixed(2));
					status = orderHead.status;
					
					completeProject(orderId);
					completeProduct(orderId);
					completeMemberInfo(orderHead.vipNo);
//					carId = orderHead.carId;
				}
				
				Ext.MessageBox.hide();
			},
			failure : function(resp, opts) {
				Ext.MessageBox.hide();
                Ext.Msg.alert('提示', "获取订单信息失败！");
			}
		});
	}
	
	
	
	
	
//	//如果有会员卡号参数传过来则，完善右边会员信息并插入一条项目初始数据，
//	//否则会员文本框获得焦点
//	if(!Ext.isEmpty(vipNo)){
//		completeMemberInfo(vipNo);
//		MemberCombo.setValue(vipNo);
//		//初始插入一条项目
//		var record = new ProjectModel({
//			typeId: '',
//			goodsId: '',
//			goodsType: 1,
//			unitPrice: 0,
//			discount: 0,
//			price: 0,
//			number: 1,
//			performer: '',
//			source: '',
//			isDeduction: 0
//		});
//		ProjectStore.insert(ProjectStore.getCount(), record);
//		ProjectCellEditing.startEditByPosition({
//			row: ProjectStore.getCount() - 1,
//			column: 1
//		});
//	} else {
//		MemberCombo.focus();
//	}
	
	/////////////////////////////////////////////////////////////  方法部分  //////////////////////////////////////////////////
	
	//更改订单类型
	function OrderTypeChange(item){
		if(item.value == 'package'){
			parent.global.openTabByMenuId('010302', "vip=" + (vipNo ? vipNo : '') + "&car=" + carId);
		} else if(item.value == 'return') {
			parent.global.openTabByMenuId('010304', "vip=" + (vipNo ? vipNo : '') + "&car=" + carId);
		} else if(item.value == 'recharge') {
			parent.global.openTabByMenuId('010305', "vip=" + (vipNo ? vipNo : '') + "&car=" + carId);
		} else {
			parent.global.openTabByMenuId('010301', "vip=" + (vipNo ? vipNo : '') + "&car=" + carId);
		}
    }
	
	//门店参数配置信息
	function completeShopParam() {
		Ext.Ajax.request({
			async: false,   //ASYNC 是否异步( TRUE 异步 , FALSE 同步)
			url : '/shop/queryParamById.atc',
			success : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
				if(result && !Ext.isEmpty(result)){
					if(!Ext.isEmpty(result.param1) && !isNaN(result.param1))
						stockWarn = result.param1;		//库存提醒
					if(!Ext.isEmpty(result.param2) && !isNaN(result.param2))
						offsetParam = result.param2;	//积分兑换
					if(!Ext.isEmpty(result.param3) && !isNaN(result.param3))
						pointRule = result.param3;		//积分规则
				}
			},
			failure : function(resp, opts) {
                Ext.Msg.alert('提示', "店铺参数配置信息加载出错！");
			}
		});
	}
	
	//加载项目列表
	function completeProject(orderNo) {
		ProjectStore.load({
			params:{
				orderNo: orderNo
			},
			callback: function(records){
				completeReceivable();
            }
		});
	}
	
	function completeProduct(orderNo) {
		ProductStore.load({
			params:{
				orderNo: orderNo
			},
			callback: function(records){
				completeReceivable();
            }
		});
	}
	
	//添加一行项目
	function insertProject(projectRow){
		if(Ext.isEmpty(projectRow)){
			if(!Ext.isEmpty(memberRecord)){
				var record = new ProjectModel({
					typeId: lastProject != "" ? lastProject : "",
					goodsId: '',
					goodsType: 1,
					unitPrice: 0,
					discount: Ext.isEmpty(memberGrade) ? 0 : memberGrade.data.discount,
					price: 0,
					number: 1,
					performer: '',
					source: '',
					isDeduction: 0
				});
				ProjectStore.insert(ProjectStore.getCount(), record);
				ProjectCellEditing.startEditByPosition({
					row: ProjectStore.getCount() - 1,
					column: 2
				});
			} else 
				Ext.MessageBox.show({
					title: '提示',
					msg: '请先选择会员！',
					buttons: Ext.MessageBox.OK,
					icon: Ext.MessageBox.INFO
				});
		} else {
			ProjectStore.insert(ProjectStore.getCount(), projectRow);
			ProjectCellEditing.startEditByPosition({
				row: ProjectStore.getCount() - 1,
				column: 2
			});
		}
	}
	
	//添加一行产品
	function insertProduct(productRow){
		if(Ext.isEmpty(productRow)){
			var record = new ProductModel({
				typeId: '',
				goodsId: '',
				goodsType: 2,
				price: 0,
				number: 1,
				discount: 0,
				price: 0,
				seller: '',
				source: '',
				isDeduction: 0
			});
			ProductStore.insert(ProductStore.getCount(), record);
			ProductCellEditing.startEditByPosition({
				row: ProductStore.getCount() - 1, 
				column: 1
			});
		} else {
			ProductStore.insert(ProductStore.getCount(), productRow);
			ProductCellEditing.startEditByPosition({
				row: ProductStore.getCount() - 1, 
				column: 1
			});
		}
		
	}
	
	//添加一行赠送服务
	function insertGiveProject(){
		var record = new GiveProjectModel({
			typeId: '',
			goodsId: '',
			unitPrice: 0,
			number: 1
		});
		GiveProjectStore.insert(GiveProjectStore.getCount(), record);
		GiveProjectCellEditing.startEditByPosition({
			row: GiveProjectStore.getCount() - 1,
			column: 1
		});
	}
	
	//添加一行赠送商品
	function insertGiveProduct(){
		var record = new GiveProductModel({
			typeId: '',
			goodsId: '',
			unitPrice: 0,
			number: 1
		});
		GiveProductStore.insert(GiveProductStore.getCount(), record);
		GiveProductCellEditing.startEditByPosition({
			row: GiveProductStore.getCount() - 1,
			column: 1
		});
	}
	
	//根据会员卡号加载会员信息
	function completeMemberInfo(vipNo){
		Ext.Ajax.request({
			url : '/member/queryMemberByVipNo.atc',
			params : {
				vipNo: vipNo
			},
			success : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
				if(result.success){
					if(Ext.isArray(result.memberList)){
						if(result.memberList.length == 1){
							var record = result.memberList[0];
							memberRecord = new Member({
						        memId: record.memId,
						        vipNo: record.vipNo,
						        deptCode: record.deptCode,
						        name: record.name,
						        mobile: record.mobile,
						        sex: record.sex,
						        grade: record.grade,
						        point: record.point,
						        wechatNo: record.wechatNo,
						        wehcatHead: record.wechatHead,
						        wechatNick: record.wechatNick,
						        source: record.source,
						        balance: record.balance,
						        birthday: record.birthday,
						        createTime: record.createTime,
						        sales: record.sales,
						        appId: record.appId,
						        remark: record.remark,
						        album: record.album
						    });
							InfoPanel.loadRecord(memberRecord);
							completeMemberCarCombo(record.memId);
							completeMemberService(record.memId);
							completeMemberGrade(record.grade);
							if(!Ext.isEmpty(record.picture))
								headImg.setSrc("/upload/member/" + record.picture);
							//若积分大于0，设置兑换框最大值
							if(Number(record.point) > 0)
								Ext.getCmp("deductible").setMaxValue(record.point);
						} else if(result.memberList.length == 0)
							Ext.MessageBox.show({
						    	title: '提示',
						        msg: '没有找到该会员！',
						        buttons: Ext.MessageBox.OK,
						        icon: Ext.MessageBox.ERROR
		                    });
						else
							Ext.MessageBox.show({
						           title: '提示',
						           msg: '会员号码重复，请联系管理员！',
						           buttons: Ext.MessageBox.OK,
						           icon: Ext.MessageBox.ERROR
		                    });
					} else 
						Ext.MessageBox.show({
					           title: '提示',
					           msg: '获取会员信息错误！',
					           buttons: Ext.MessageBox.OK,
					           icon: Ext.MessageBox.ERROR
	                    });
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
	
	//根据会员ID加载车辆下拉框
	function completeMemberCarCombo(memberId){
		CarStore.load({
			params:{
				memberId: memberId
			}
		});
	}
	
	//加载会员剩余服务信息
	function completeMemberService(memberId) {
		SurplusForm.getComponent("SurplusSet").getComponent("SurplusContainer").removeAll();
		Ext.Ajax.request({
			url : '/member/queryMemberStockByMemId.atc',
			params : {
				memId : memberId
			},
			success : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
				if(result.success){
					if(Ext.isArray(result.msList) && !Ext.isEmpty(result.msList)){
						Ext.each(result.msList, function(memberStock){
							SurplusForm.getComponent("SurplusSet").getComponent("SurplusContainer").add([
                          	   {fieldLabel: memberStock.goodsName, value: memberStock.number + memberStock.unit}
                          	]);
				        });  
					}
					SurplusForm.getComponent("SurplusSet").getComponent("SurplusContainer").add([
                  	   Ext.create('Ext.toolbar.Toolbar', {
                  		   style:{float: 'right'},
                      	   items: ['->',{
                      		   xtype: 'button',
                      		   text:'查看详情',
                      		   handler: function(){
                      			   MemberStockWindow.show();
                      		   }
                      	   }]
                         })
                  	]);
				}		
			},
			failure : function(resp, opts) {
                Ext.Msg.alert('提示', "会员等级信息加载出错！");
			}
		});
	}
	
	//加载会员等级信息
	function completeMemberGrade(grade) {
		Ext.Ajax.request({
			url : '/member/queryGradeByGrade.atc',
			params : {
				'grade.grade' : grade
			},
			success : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
				if(result.success){
					if(Ext.isArray(result.gradeList)){
						if(result.gradeList.length == 1){
							var grade = result.gradeList[0];
							if(!Ext.isEmpty(grade)){
								memberGrade = new MemberGrade({
									id: grade.id,
									deptCode: grade.deptCode,
									name: grade.name,
									grade: grade.grade,
									needPoint: grade.needPoint,
									discount: grade.discount,
									orderDiscount: grade.orderDiscount
								});
								Ext.getCmp("grade").setValue(grade.name);
							} else
								Ext.example.msg("提示", "没有配置会员等级信息！");
						}
					}
				}		
			},
			failure : function(resp, opts) {
                Ext.Msg.alert('提示', "会员等级信息加载出错！");
			}
		});
	}
	
	//加载区域信息
	function LoadAreaInfo(areaId){
		Ext.Ajax.request({
			url : '/base/queryBaseAreaById.atc',
			params : {
				'area.areaId' : areaId
			},
			success : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
				areaShortCombo.setValue(result.area.areaShort);
				areaCodeCombo.setValue(result.area.areaCode);
			},
			failure : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
                Ext.Msg.alert('提示', result.msg);
			}
		});
	}
	
	//根据小项完善当前行信息
	function completeProjectRow(row, record){
		if(!Ext.isEmpty(record)){
			row.data.unitPrice = record.data.price;
			row.data.goodsName = record.data.name;
			Ext.Ajax.request({
				url : '/member/queryGradePriceById.atc',
				async: false,
				params : {
					'gradePrice.goodsId' : record.data.id,
					'gradePrice.gradeId' : memberRecord.data.grade
				},
				success : function(resp, opts) {
					var result = Ext.decode(resp.responseText);
					if(result.success){
						if(Ext.isArray(result.gradePriceList)){
							var gradePrice = result.gradePriceList[0];
							if(!Ext.isEmpty(gradePrice)){
								row.data.price = Number(gradePrice.mprice).toFixed(2);
							} else {
								if(!Ext.isEmpty(memberGrade)){
									row.data.discount = memberGrade.data.discount;
									row.data.price = (Number((memberGrade.data.discount == 0) ? 10 : memberGrade.data.discount) * Number(record.data.price) * 0.1).toFixed(2);
								} else {
									row.data.discount = 0;
									row.data.price = Number(record.data.price).toFixed(2);
								}
							}
						}
						row.commit();
					}
				},
				failure : function(resp, opts) {
					var result = Ext.decode(resp.responseText);
	                Ext.Msg.alert('提示', "获取项目价格信息失败！");
				}
			});
		}
	}
	
	//计算应收，扣次类别不计入应收
	function completeReceivable(){
		receivable = 0
		ProjectStore.each(function(record){
			if(record.data.isDeduction != 1)
				receivable += Number(record.data.price);
		});
		ProductStore.each(function(record){
			if(record.data.isDeduction != 1)
				receivable += Number(record.data.price)
		});
		receivable = Number(receivable - poffset).toFixed(2);
		rece = receivable;
		if(pdeposit != 0)
			receivable = Number(receivable - pdeposit).toFixed(2);
		Ext.getCmp("receivable").setValue(receivable);
	}
	
	//计算实收
	function completeActual(field){
		if(receivable == 0){
			Ext.MessageBox.show({
		           title: '提示',
		           msg: "当前没有任何消费项目！",
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
			});
			return;
		}
		actual = 0;
		actual = Number(Ext.getCmp("pbalance").getValue()) + Number(Ext.getCmp("pcash").getValue()) + Number(Ext.getCmp("pcard").getValue()) +
				Number(Ext.getCmp("ptransfer").getValue()) + Number(Ext.getCmp("pwechat").getValue()) + Number(Ext.getCmp("palipay").getValue()) + 
				Number(Ext.getCmp("pbill").getValue());
		Ext.getCmp("actual").setValue(actual.toFixed(2));
		
		var btn = Ext.getCmp("payRow").getComponent(field.id);
		if(btn){
			if(field.value > 0){
				if(btn.text.indexOf("font") < 0){
					btn.setText("<font color='#FFCC00'>" + btn.text + "</font>");
				}
			} else {
				btn.setText(btn.text.replace("<font color='#FFCC00'>", "").replace("</font>", ""));
			}
		}
	}
	
	//支付按钮点击
	function onButtonPay(btn) {
		if(receivable != 0){
			var payButtons = new Array("pbalance","pcash","pcard","ptransfer","pwechat","palipay","pbill");
			Ext.each(payButtons, function(button){
				Ext.getCmp(button).setValue(0);
				var btnText = Ext.getCmp("payRow").getComponent(button).text;
				btnText = btnText.replace("<font color='#FFCC00'>", "").replace("</font>", "");
				Ext.getCmp("payRow").getComponent(button).setText(btnText);
			});
			Ext.getCmp("payRow").getComponent(btn.itemId).setText("<font color='#FFCC00'>" + btn.text + "</font>");
			Ext.getCmp(btn.itemId).setValue(receivable);
			submitOrder(1);
		}
    }
	
	//会员使用剩余服务
	function serviceToOrder(record){
		if(!Ext.isEmpty(record)){
			if(Ext.isEmpty(record.data.endDate) || new Date(record.data.endDate) >= new Date()){
				//使用剩余赠送项目或套餐项目
				if(record.data.goodsType == 1 || record.data.goodsType == 3){
					if(ProjectSecondItemStore.find('id', record.data.goodsId) == -1)
						ProjectSecondItemStore.add({id: record.data.goodsId, name: record.data.goodsName});
					var projectRow = new ProjectModel({
						typeId: record.data.typeId,
						goodsId: record.data.goodsId,
						goodsName: record.data.goodsName,
						goodsType: record.data.goodsType,
						unitPrice: record.data.unitPrice,
						discount: 0,
						price: 0,
						number: 1,
						performer: '',
						endDate: record.data.endDate,
						source: record.data.source,
						isDeduction: 1
					});
					insertProject(projectRow);
				//使用剩余赠送商品或套餐商品
				} else if(record.data.goodsType == 2 || record.data.goodsType == 4) {
					if(Ext.isEmpty(record.data.typeId))
						StockStore.getProxy().url = "/goods/queryGoodsMaterialStockByKeyword.atc";
					else
						StockStore.getProxy().url = "/goods/queryGoodsMaterialStockByKeyword.atc?typeId=" + record.data.typeId;
					StockStore.load({
	        			params: {
	        				keyword: record.data.goodsName
	        			},
	        			callback: function(records){
	        				if(!Ext.isEmpty(records)){
	        					var productRow = new ProductModel({
	        						typeId: record.data.typeId,
	        						goodsId: record.data.goodsId,
	        						goodsName: record.data.goodsName,
	        						goodsType: record.data.goodsType,
	        						number: 1,
	        						unitPrice: record.data.unitPrice,
	        						discount: memberGrade.data.discount,
	        						price: 0,
	        						seller: '',
	        						endDate: record.data.endDate,
	        						source: record.data.source,
	        						isDeduction: 1
	        					});
	        					insertProduct(productRow);
	        				} else
	        					Ext.MessageBox.show({
									title: '提示',
									msg: "没有找到相关商品信息!",
									buttons: Ext.MessageBox.OK,
									icon: Ext.MessageBox.INFO
								});
	                    }
	        		});
				//使用优惠券
				} else {
					Ext.Ajax.request({
			            method:'POST',
			            url: '/goods/queryCouponsById.atc',
			            params : {
			            	'coupons.id' : record.data.goodsId
						},
						success : function(resp, opts) {
							var result = Ext.decode(resp.responseText);
							if(result.success){
								if(Ext.isArray(result.couponsList)){
									var coupons = result.couponsList[0];
									if(!Ext.isEmpty(coupons)){
										if(!Ext.isEmpty(coupons.minimum)){
											if(receivable < coupons.minimum){
												Ext.MessageBox.show({
													title: '提示',
													msg: "消费金额不能低于 " + coupons.minimum + " 元!",
													buttons: Ext.MessageBox.OK,
													icon: Ext.MessageBox.INFO
												});
												return false;
											}
										} else if(coupons.applyId) {
											var isApply = false;
											Ext.each(ProjectStore.data.items, function(item){
									        	if(coupons.applyId.indexOf(item.data.goodsId) >= 0){
									        		isApply = true;
									        	}
									        });
									        Ext.each(ProductStore.data.items, function(item){
									        	if(coupons.applyId.indexOf(item.data.goodsId) >= 0){
									        		isApply = true;
									        	}
									        });
									        if(!isApply) {
									        	Ext.MessageBox.show({
													title: '提示',
													msg: "没有可适用的项目或商品!",
													buttons: Ext.MessageBox.OK,
													icon: Ext.MessageBox.INFO
												});
												return false;
									        }
										}
										if(ProjectSecondItemStore.find('id', coupons.id) == -1)
											ProjectSecondItemStore.add({id: coupons.id, name: coupons.name});
										var projectRow = new ProjectModel({
											typeId: "",
											goodsId: coupons.id,
											goodsName: coupons.name,
											goodsType: 5,
											unitPrice: 0,
											discount: 0,
											price: coupons.price * -1,
											number: 1,
											performer: '',
											source: '',
											endDate: '9999/12/30',
											isDeduction: 2
										});
										insertProject(projectRow);
										completeReceivable();
									}
								} else
									Ext.MessageBox.show({
										title: '提示',
										msg: "没有相关卡券信息!",
										buttons: Ext.MessageBox.OK,
										icon: Ext.MessageBox.INFO
									});
							}
						},
						failure : function(resp, opts) {
							var result = Ext.decode(resp.responseText);
			                Ext.Msg.alert('提示', result.msg);
						}
			        });
				}
			} else
				Ext.MessageBox.show({
					title: '提示',
					msg: "商品已过期!",
					buttons: Ext.MessageBox.OK,
					icon: Ext.MessageBox.INFO
				});
		}
	}
	
	//改变grid行背景色，扣次类别则变色
	function changeRowClass(record, rowIndex, rowParams, store){
	    if (record.get("isDeduction") == 1) {
	        return 'x-grid-record-yellow';
	    } else if(record.get("isDeduction") == 2) {
	    	return 'x-grid-record-green';
	    }
	}
	
	//根据类型获取单位描述
	function getUnit(type){
		if(type == 1){
			return '次';
		} else if(type == 2) {
			return '个';
		} else if(type == 3) {
			return '次';
		} else if(type == 4) {
			return '个';
		} else if(type == 5) {
			return '张';
		}
	}
	
	//计算消费所得积分
	function completeSalePoint(){
    	return (pointRule == 0) ? 0 : Math.round(Number(actual) / pointRule);
	}
	
	//提交订单 type 提交方式(1:消费; 2:保存)
	function submitOrder(type){
		if(Number(actual) < Number(receivable) && type == 1){
			Ext.MessageBox.show({
	           title: '提示',
	           msg: "实收金额不能小于应收金额！",
	           buttons: Ext.MessageBox.OK,
	           icon: Ext.MessageBox.INFO
			});
			return false;
		} else if(Ext.isEmpty(memberRecord)){
			Ext.MessageBox.show({
				title: '提示',
				msg: "会员不能为空！",
				buttons: Ext.MessageBox.OK,
				icon: Ext.MessageBox.INFO
			});
			MemberCombo.focus();
			return false;
		} else if(Ext.isEmpty(CarCombo.getValue())){
			Ext.MessageBox.show({
				title: '提示',
				msg: "没有选择车辆！",
				buttons: Ext.MessageBox.OK,
				icon: Ext.MessageBox.INFO
			});
			CarCombo.focus();
			return false;
		} else if(Number(Ext.getCmp("pbalance").getValue()) > 0 && Number(Ext.getCmp("pbalance").getValue()) > Number(memberRecord.data.balance) && type == 1){
			Ext.MessageBox.show({
	           title: '提示',
	           msg: "余额不足！",
	           buttons: Ext.MessageBox.OK,
	           icon: Ext.MessageBox.INFO
			});
			return false;
		} else if(!Ext.getCmp("deductible").isValid() && poffset > 0 && type == 1){
			Ext.MessageBox.show({
		           title: '提示',
		           msg: "抵现积分不合法！",
		           buttons: Ext.MessageBox.OK,
		           icon: Ext.MessageBox.INFO
			});
			Ext.getCmp("deductible").focus();
			return false;
		}
		
		Ext.MessageBox.show({
			title: '请稍等',
			msg: '正在提交订单 ...',
			width: 200,
			progress: true,
			closable: false
        });
		
		var saleDate = parent.dateFormat(Ext.getCmp("saleDate").getValue()) + ' ' + Ext.getCmp("saleTime").getRawValue();
		
        var projectArray = [];		//项目
        var productArray = [];		//产品
        var giveProjectArray = [];	//服务赠送
        var giveProductArray = [];	//商品赠送
        var giveCouponsArray = [];	//卡券赠送
        
        Ext.each(ProjectStore.data.items, function(item){
        	if(!Ext.isEmpty(item.data.goodsId)){
        		if(isNaN(item.data.goodsId))
        			item.data.goodsId = '00000000';
        		projectArray.push(item.data);
        	}
        });
        Ext.each(ProductStore.data.items, function(item){
        	if(!Ext.isEmpty(item.data.goodsId))
        		productArray.push(item.data);
        });  
        if(Ext.isEmpty(projectArray) && Ext.isEmpty(productArray)){
        	Ext.MessageBox.show({
	        	title: '提示',
	        	msg: "没有任何消费项目！",
	        	buttons: Ext.MessageBox.OK,
	        	icon: Ext.MessageBox.INFO
			});
			return false;
        }
        if(GiveProjectStore.getCount() > 0){
        	Ext.each(GiveProjectStore.data.items, function(item){
            	if(!Ext.isEmpty(item.data.goodsId))
            		giveProjectArray.push(item.data);
            });  
        }
        if(GiveProductStore.getCount() > 0) {
        	Ext.each(GiveProductStore.data.items, function(item){
            	if(!Ext.isEmpty(item.data.goodsId))
            		giveProductArray.push(item.data);
            });  
        }
        if(GiveCouponsStore.getCount() > 0) {
        	Ext.each(GiveCouponsSelModel.getSelection(), function(item){
        		if(!Ext.isEmpty(item.data.id))
        			giveCouponsArray.push(item.data);
        	});  
        }
        
        Ext.Ajax.request({
            method:'POST',
            timout: 600000,
            url: '/order/submitOrder.atc',
            params : {
            	'orderHead.orderId' : orderId,
            	'orderHead.orderType' : orderType,
            	'orderHead.memId' : memberRecord.data.memId,
            	'orderHead.vipNo' : memberRecord.data.vipNo,
            	'orderHead.carId' : CarCombo.getValue(),
            	'orderHead.carNumber' : Ext.getCmp("cNumber").getValue(),
            	'orderHead.saleDate' : saleDate,
            	'orderHead.oprice' : rece,
            	'orderHead.aprice' : Number(Ext.getCmp("actual").getValue()) + Number(pdeposit),
            	'orderHead.point' : completeSalePoint(),
            	'orderHead.poffset' : Number(Ext.getCmp("deductible").getValue()),
            	'orderHead.pdeposit' : pdeposit,		//定金
            	'orderHead.pbalance' : Ext.isEmpty(Ext.getCmp("pbalance").getValue()) ? 0 : Ext.getCmp("pbalance").getValue(),
            	'orderHead.pcash' : Ext.isEmpty(Ext.getCmp("pcash").getValue()) ? 0 : Ext.getCmp("pcash").getValue(),
            	'orderHead.pcard' : Ext.isEmpty(Ext.getCmp("pcard").getValue()) ? 0 : Ext.getCmp("pcard").getValue(),
            	'orderHead.ptransfer' : Ext.isEmpty(Ext.getCmp("ptransfer").getValue()) ? 0 : Ext.getCmp("ptransfer").getValue(),
            	'orderHead.pbill' : Ext.isEmpty(Ext.getCmp("pbill").getValue()) ? 0 : Ext.getCmp("pbill").getValue(),
            	'orderHead.pwechat' : Ext.isEmpty(Ext.getCmp("pwechat").getValue()) ? 0 : Ext.getCmp("pwechat").getValue(),
            	'orderHead.palipay' : Ext.isEmpty(Ext.getCmp("palipay").getValue()) ? 0 : Ext.getCmp("palipay").getValue(),
            	'orderHead.status' : type == 1 ? 1 : status,
            	'orderHead.estimate' : Ext.isEmpty(Ext.getCmp("estimate").getValue()) ? "" : parent.dateFormat(Ext.getCmp("estimate").getValue()),
            	'orderHead.mainten' : Ext.isEmpty(Ext.getCmp("carKilometers").getValue()) ? "" : Ext.getCmp("carKilometers").getValue(),
            	'orderHead.creator' : Ext.isEmpty(EmployeeSingleCombo.getValue()) ? parent.GG : EmployeeSingleCombo.getValue(),
            	'orderHead.remark' : Ext.getCmp("orderRemark").getValue(),
            	projectString : Ext.encode(projectArray),
            	productString : Ext.encode(productArray),
            	giveProjectString : Ext.encode(giveProjectArray),
            	giveProductString : Ext.encode(giveProductArray),
            	giveCouponsString : Ext.encode(giveCouponsArray)
			},
			success : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
				Ext.MessageBox.hide();
				if(result.success){
					orderNo = result.msg;
					Ext.MessageBox.show({
			            title: '结果',
			            msg: result.msg  + (type == 1 ? " 消费成功！" : " 保存成功！"),
			            buttons: Ext.MessageBox.YESNO,
			            buttonText:{
			                yes: "打印订单", 
			                no: "继续开单" 
			            },
			            fn: function(btn) {
			            	if(btn == 'yes'){
			            		//打印订单
			            		parent.global.openPrintTab(orderNo);
			        		} else if(btn == 'no'){
			        			if(type == 1){
			        				if(parent.mainTab.getComponent('1000001'))
			        					parent.global.closeTab(parent.mainTab.getComponent('1000001'));
			        			}
			        		}
			            }
			        });
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
				var result = Ext.decode(resp.responseText);
				Ext.MessageBox.hide();
                Ext.Msg.alert('提示', result.msg);
			}
        });
	}
	
});