/**
 * 会员充值
 */
Ext.require([
    'Ext.ux.DataTip',
    'Ext.ux.form.SearchField'
]);
Ext.onReady(function() {
	Ext.QuickTips.init();
	
	//定义、接收会员号参数
	var vipNo, carId;
	if(typeof(vip) != 'undefined') vipNo = vip;
	if(typeof(car) != 'undefined') carId = car;

	//会员基本信息,会员等级基本信息
	var memberRecord, memberGrade;	
	//订单类型4会员充值, 显示应收, 实际应收, 实收, 库存报警值, 积分抵现值, 抵现金额, 积分规则
	var orderType = "4", receivable = 0, rece = 0, actual = 0, stockWarn = 0, offsetParam = 0, poffset = 0, pointRule = 0;	
	
	var lastProject = "", orderNo = "", selectMode = 0;
	
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
		    						'member.id': member.data.id,
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
				        {id:'memberBirthday', name: 'member.birthday', fieldLabel: '生日', format: 'Y-m-d',maxValue: new Date()},
				        {id:'memberCreateTime', name: 'member.createTime', xtype:'textfield', fieldLabel: '加入时间', format: 'Y-m-d', readOnly: true}
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
		          'carEngine', 'carKilometers', 'carInspection', 'carMaintain', 'carInsurance', 'carInsuCompany', 'cariLLegal'],  
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
			carId = cRecord.data.id;
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
                	flex: 1, id: 'carKilometers', name: 'car.carKilometers', xtype: 'numberfield', fieldLabel: '进厂公里', labelWidth: 60,
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
                	flex: 1, id: 'carNextkilo', name: 'car.carNextkilo',  xtype: 'numberfield', fieldLabel: '下次保养公里', labelWidth: 90, value: 5000
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
            		flex: 1, xtype: 'textfield', padding: '0 5 0 0'
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
		autoLoad: true,
		fields : [{name: 'id'}, {name: 'shortCode'}, {name: 'brandName'}],  
		proxy:{
            type: 'ajax',
            url : '/base/queryAllCarBrand.atc',
            reader : {
                root: 'carBrandList'
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
        valueField: 'id',
        displayField: 'brandName',
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
		    	 defaults: {flex: 1, xtype:'textfield',labelWidth:70,labelAlign:'right'},
		    	 items: [
		    	         {id:'kilometers', name: 'car.carKilometers', fieldLabel: '进厂公里'},
		    	         {id:'inspection', name: 'car.carInspection', xtype:'datefield', fieldLabel: '年检时间', format: 'Y-m-d'}
		    	 ]
		     },{
		    	 defaults: {flex: 1,xtype:'datefield',labelWidth:70,labelAlign:'right'},
		    	 items: [
		    	         {id:'maintain', name: 'car.carMaintain', fieldLabel: '保养日期', format: 'Y-m-d'},
		    	         {id:'insurance', name: 'car.carInsurance', fieldLabel: '保险时间', format: 'Y-m-d'}
		    	 ]
		     },{
		         defaults: {flex: 1,xtype:'textfield',labelWidth:70,labelAlign:'right'},
		         items: [
	                    {id:'insuCompany', name: 'car.carInsuCompany', fieldLabel: '保险公司'}
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
		items: [CarForm]
	});
	
	//////////////////////////////////////////////////////////////////////////////// 会员剩余服务
	
	var MemberStockStore = Ext.create('Ext.data.Store', {
		fields: ['memId', 'goodsId', 'goodsName', 'goodsType', 'number', 'typeId', 'source', 'sourceName', 'unitPrice'],
		proxy:{
            type: 'ajax',
            url : '/member/queryMemberStockByMemId.atc',
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
        {header : '到期日期',dataIndex: 'endDate',width: 100, menuDisabled: true}
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
//	    },
//	    listeners:{
//			itemdblclick: function(grid, record, item, index, e) {
//				serviceToOrder(record);
//				MemberStockWindow.hide();
//	        }
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
		fields: ['id', 'vipNo', 'name', 'mobile', 'carId', 'carShort', 'carCode', 'carNumber', 'fullCarNumber'],
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
            	carId = record.data.carId;
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
		region: 'north',
		height : 40,
		defaults: {
		    anchor: '100%',
		    layout: {
		        type: 'hbox'
		    }
		},
		items : [{
			xtype: 'container',
        	layout: 'hbox',
        	margin: '10 10 0 10',
        	defaults:{
            	flex: 1, xtype: 'textfield', padding: '0 5 0 0'
            },
        	items: [{
        		border : false,
        		minWidth: 120,
				xtype: 'toolbar',
				style:{
					padding: '0',
					marginLeft: '20'
				},
				items: [{
				    xtype:'splitbutton',
				    text: '客户充值',
				    iconCls: 'money',
				    menu: [
				           {text: '项目消费', value:'normal', handler: OrderTypeChange},
				           {text: '套餐购买', value:'package', handler: OrderTypeChange},
				           {text: '套餐扣次', value:'deduction', handler: OrderTypeChange},
				           {text: '客户退货', value:'return', handler: OrderTypeChange},
				           {text: '会员充值', value:'recharge', handler: OrderTypeChange}
				    ]
				}]
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
        fields: ['id', 'name'],
        proxy: {
            type: 'ajax',
            url : '/shop/queryAllEmployeeByDept.atc',
            reader : {
                root: 'employeeList'
            }
        }
	});
	EmployeeStore.load();
	
	Ext.define('RechargeModel', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'id', type: 'int'},
	        {name: 'name', type: 'string'},
	        {name: 'price', type: 'string'},
	        {name: 'mode', type: 'string'}
	    ]
	});
	
	var RechargeStore = Ext.create('Ext.data.Store', {
		fields: ['id', 'name', 'price', 'mode'],
		proxy: {
			type: 'ajax',
			url : '/shop/queryRechargeByDept.atc',
			reader : {
				root: 'rList'
			}
		}
	});
	RechargeStore.load();
	
	var RechargeDetailStore = Ext.create('Ext.data.Store', {
		fields: ['rechargeId', 'itemNo', 'value', 'type', 'number', 'dname', 'dprice', 'days'],
		proxy: {
			type: 'ajax',
			url : '/shop/queryRechargeDetailById.atc',
			reader : {
				root: 'dList'
			}
		}
	});
	
	var RechargeCombo = Ext.create('Ext.form.ComboBox', {
		id: 'type',
		columnWidth : .4,
		allowBlank: false, 
		labelWidth: 100,
		fieldLabel: '充值套餐',
		margin: '20 0 0 0',
		labelStyle : 'font-size: 18px;',
		fieldStyle : 'font-size: 14px;',
		labelAlign:'left',
		mode : 'local',
		store: RechargeStore,
		valueField : 'id',
		displayField : 'name',
//		emptyText: '请选择...',
		editable : false,
        listeners : {
        	select: function(cb, records) {
        		var record = records[0];
        		Ext.getCmp("recharge").setValue(record.data.price);
        		RechargePanel.getComponent("detailItem").removeAll(false);
        		selectMode = record.data.mode;
//        		if(Number(record.data.mode) > 0){
        		completeRechargeDetail(record.data.id, record.data.mode);
//        		}
			}
		}
	});
	
	//根据充值套餐，查找明细并展示
	function completeRechargeDetail(rechargeId, mode) {
		RechargeDetailStore.load({
			params: {
				'recharge.id' : rechargeId
			},
			callback: function(records, options, success){
				if(success){
					Ext.each(records, function(detail){
						if(detail.index == 0)
							RechargePanel.getComponent("detailItem").add([
							      {fieldLabel: '套餐内容',boxLabel:detail.data.dname, inputValue:detail.data.itemNo,checked:true }
							]);
						else {
							if(mode > 0)
								RechargePanel.getComponent("detailItem").add([{boxLabel:detail.data.dname, inputValue:detail.data.itemNo }]);
							else
								RechargePanel.getComponent("detailItem").add([{boxLabel:detail.data.dname, inputValue:detail.data.itemNo, checked:true }]);
						}
			        }); 
				}
	        }
		});
	}
	
	var RechargePanel = Ext.create('Ext.form.FormPanel',{
		region: 'center',
		title: '会员充值',
		iconCls: 'money',
		bodyPadding: 10,
		defaults: {
            labelWidth: 60
        },
        items : [{
        	layout : 'column',
			border : false,
			items : [{
				columnWidth : .3,
				id: 'currentBalance',
				margin: '20 0 0 0',
				xtype: 'displayfield',
				fieldLabel: '当前余额',
				labelStyle : 'font-size: 18px;',
				fieldStyle : 'font-size: 20px; color:red;'
			}]
        },{
        	layout : 'column',
        	border : false,
        	items : [RechargeCombo]
        },{
        	layout : 'column',
        	border : false,
    		columnWidth : .9,
    		id:'detailItem',
    		itemId : 'detailItem',
			margin: '20 0 0 0',
			defaultType: 'checkbox',
			defaults: {
				labelStyle : 'font-size: 18px;',
				padding: '0 10 0 0'
			}
        },{
        	layout : 'column',
			border : false,
			items : [{
				columnWidth : .3,
				id: 'recharge',
				margin: '20 0 0 0',
				xtype: 'numberfield',
				fieldLabel: '充值金额',
				labelStyle : 'font-size: 18px;',
				fieldStyle : 'font-size: 18px;',
				listeners: {
					change : function(){
						completeReceivable();
					}
				}
			}]
        }]
	});
	
//	//////////////////////////////////////服务赠送
//	Ext.define('GiveProjectModel', {
//	    extend: 'Ext.data.Model',
//	    fields: [
//	        {name: 'typeId', type: 'string'},
//	        {name: 'goodsId', type: 'string'},
//	        {name: 'goodsName', type: 'string'},
//	        {name: 'unitPrice', type: 'float'},
//	        {name: 'number', type: 'int'}
//	    ]
//	});
//	Ext.define('GiveProjectSecondItem', {
//	    extend: 'Ext.data.Model',
//	    fields: [
//	        {name: 'id', type: 'string'},
//	        {name: 'name', type: 'string'},
//	        {name: 'typeId', type: 'string'},
//	        {name: 'price', type: 'float'}
//	    ]
//	});
//	var GiveProjectStore = new Ext.data.Store({
//        autoDestroy: true,
//        model: GiveProjectModel
//    });
//	var GiveProjectItemStore = new Ext.data.Store({
//		autoLoad: false,
//		fields : ['id', 'typeName', 'sortNo'],  
//		proxy:{
//            type: 'ajax',
//            url : '/goods/queryHourTypeByDeptCode.atc',
//            reader : {
//                root: 'hourTypeList'
//            }
//        },
//        sorters: [{
//            property: 'sortNo',
//            direction:'ASC'
//        }]
//	});
//	var GiveProjectSecondItemStore = new Ext.data.Store({
//		autoLoad: false,
//		model: GiveProjectSecondItem,
//		proxy:{
//            type: 'ajax',
//            url : '/goods/queryGoodsHourByType.atc',
//            reader : {
//                root: 'hourList'
//            }
//        }
//	});
//	var GiveProjectItemCombo = Ext.create('Ext.form.field.ComboBox', {
//		store: GiveProjectItemStore,
//		valueField: 'id',
//		displayField: 'typeName',
//        editable : false,
//        emptyText:'请选择...', 
//        triggerAction: 'all',
//        lastQuery: '',
//        listeners: {
//        	select: function(cb, records){
//        		var record = records[0];
//        		GiveProjectSecondItemStore.load({
//        			params: {
//        				typeId: Number(record.data.id)
//                    }
//        		});
//        		var rowRecord = GiveProjectGrid.getSelectionModel().getLastSelected();
//        		GiveProjectCellEditing.startEdit(rowRecord, 2);
//            }
//        }
//	});
//	var GiveProjectSecondItemCombo = Ext.create('Ext.form.ComboBox', {
//		store: GiveProjectSecondItemStore,
//		valueField: 'id',
//		displayField: 'name',
//		queryMode: 'local',
//        editable : true,
//        selectOnFocus : true,
//        emptyText:'请选择...', 
//        typeAhead: true,
//        triggerAction: 'all',
//        lastQuery: '',
//        listeners: {
//        	select: function(cb, records){
//                var record = records[0];
//                if(!Ext.isEmpty(record)){
//                	var rowRecord = GiveProjectGrid.getSelectionModel().getLastSelected();
//                	rowRecord.data.unitPrice = record.data.price;
//                	rowRecord.data.goodsName = record.data.name;
//                	GiveProjectCellEditing.startEdit(rowRecord, 4);
//                }
//        	}
//        }
//	});
//	var GiveProjectCellEditing = new Ext.grid.plugin.CellEditing({
//        clicksToEdit: 1
//    });
//	var GiveProjectRowNum = new Ext.grid.RowNumberer({
//		header : 'NO',
//		resizable: true,
//		width : 40
//	});
//	var GiveProjectTbar = Ext.create('Ext.toolbar.Toolbar', {
//		region : 'north',
//		border : false,
//        items: [{
//        	xtype: 'button',
//        	text: '添加项目',
//        	iconCls: 'add',
//        	handler: function() {
//        		insertGiveProject();
//            }
//        },{
//        	xtype: 'button',
//        	text: '确认',
//        	iconCls: 'accept',
//        	handler: function() {
//        		GiveProjectWindow.hide();
//            }
//        }]
//    });
//	var GiveProjectItemRenderer = function(value, cellmeta, record) {
//        var index = GiveProjectItemStore.find("id", value);
//        var record = GiveProjectItemStore.getAt(index);
//        var displayText = "";
//        if (record == null)
//        	displayText = value;
//        else
//        	displayText = record.data.typeName;
//        return displayText;
//    };
//    var GiveProjectSecondItemRenderer = function(value, cellmeta, record) {
//        var index = GiveProjectSecondItemStore.find("id", value);
//        var record = GiveProjectSecondItemStore.getAt(index);
//        var displayText = "";
//        if (record == null)
//        	displayText = value;
//        else
//        	displayText = record.data.name;
//        return displayText;
//    };
//	var GiveProjectColumns= [GiveProjectRowNum,
//	        {header: '大项',dataIndex: 'typeId',menuDisabled: true, editor: GiveProjectItemCombo, renderer: GiveProjectItemRenderer},
//	        {header : '小项',dataIndex: 'goodsId',menuDisabled: true, width: '18%', editor: GiveProjectSecondItemCombo, renderer: GiveProjectSecondItemRenderer },
//	        {header : '工时费',dataIndex: 'unitPrice',menuDisabled: true, xtype: 'numbercolumn', renderer: Ext.util.Format.numberRenderer("0.00")},
//	        {header : '次数',dataIndex: 'number',menuDisabled: true, editor: {xtype: 'numberfield', allowBlank: false, minValue: 0, maxValue: 100000}},
//	        {xtype: 'actioncolumn',header : '操作',width: 50,align:'center',sortable: false,menuDisabled: true,
//                items: [{
//                    iconCls: 'delete',
//                    tooltip: '删除',
//                    scope: this,
//                    handler: function(grid, rowIndex){
//                    	GiveProjectGrid.getStore().removeAt(rowIndex);
//                    }
//                }]
//            }
//	];
//	var GiveProjectGrid = Ext.create('Ext.grid.GridPanel', {
//		minHeight: 220,
//		columns : GiveProjectColumns,
//		store : GiveProjectStore, 
//		region : 'center',
//		autoScroll : true,
//		columnLines : true,
//		frame : false,
//		tbar : GiveProjectTbar, 
//		stripeRows: true,
//		forceFit: true,
//		plugins: [GiveProjectCellEditing],
//		viewConfig : {
//			enableTextSelection:true	//设置单元格可以选择
//		},
//		loadMask : {
//			msg : '正在加载表格数据,请稍等...'
//	    }
//	});
//	
//	var GiveProjectWindow = Ext.create('Ext.Window',{
//		layout : 'fit',
//		title: '服务赠送',
//		iconCls: 'award_star_gold_2',
//		width : 600,
//		height : 380,
//		resizable : false,
//		draggable : true,
//		closeAction : 'hide',
//		modal : false,
//		collapsible : true,
//		titleCollapse : true,
//		maximizable : false,
//		buttonAlign : 'right',
//		border : false,
//		animCollapse : true,
//		constrain : true,
//		items: [GiveProjectGrid]
//	});
//	
//	GiveProjectWindow.on('show', function(){
//		GiveProjectItemStore.load();
//		if(GiveProjectStore.getCount() == 0)
//			insertGiveProject();
//	});
//	
//	/////////////////////////////////////// 商品赠送
//	
//	Ext.define('GiveProductModel', {
//	    extend: 'Ext.data.Model',
//	    fields: [
//	        {name: 'typeId', type: 'string'},
//	        {name: 'goodsId', type: 'string'},
//	        {name: 'goodsName', type: 'string'},
//	        {name: 'unitPrice', type: 'float'},
//	        {name: 'number', type: 'int'}
//	    ]
//	});
//	var GiveProductStore = new Ext.data.Store({
//        autoDestroy: true,
//        model: GiveProductModel
//    });
//	
//	var GiveProductTypeStore = Ext.create('Ext.data.Store', {
//		fields: ['id', 'typeName'],
//        proxy: {
//            type: 'ajax',
//            url : '/goods/queryMaterialTypeByDeptCode.atc',
//            reader : {
//                root: 'materialTypeList'
//            }
//        }
//	});
//	var GiveStockStore = Ext.create('Ext.data.Store', {
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
//	var GiveProductTypeCombo = Ext.create('Ext.form.field.ComboBox', {
//		store: GiveProductTypeStore,
//		valueField: 'id',
//		displayField: 'typeName',
//        editable : false,
//        emptyText:'请选择...', 
//        triggerAction: 'all',
//        lastQuery: '',
//        listeners: {
//        	select: function(cb, records){
//        		var record = records[0];
//        		var rowRecord = GiveProductGrid.getSelectionModel().getLastSelected();
//        		rowRecord.data.goodsName = record.data.name;
//        		GiveProductCellEditing.startEdit(rowRecord, 2);
//        		GiveStockStore.getProxy().url = "/goods/queryGoodsMaterialStockByKeyword.atc?typeId=" + record.data.id;
//            }
//        }
//	});
//	var GiveStockCombo = Ext.create('Ext.form.ComboBox', {
//        store: GiveStockStore,
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
//                return '<div>{code}/{name}/{price}/ {number}件</div>';
//            }
//        },
//        listeners : {
//        	select: function(cb, records) {
//            	var record = records[0];
//            	var rowRecord = GiveProductGrid.getSelectionModel().getLastSelected();
//            	rowRecord.data.unitPrice = record.data.price;
//            	rowRecord.data.goodsName = record.data.name;
//            	rowRecord.commit();
//            	GiveProductCellEditing.startEdit(rowRecord, 4);
//			}
//		}
//	});
//	GiveProductStore = new Ext.data.Store({
//        autoDestroy: true,
//        model: GiveProductModel
//    });
//	var GiveProductCellEditing = new Ext.grid.plugin.CellEditing({
//        clicksToEdit: 1
//    });
//	var GiveProductRowNum = new Ext.grid.RowNumberer({
//		header : 'NO',
//		resizable: true,
//		width : 40
//	});
//	var GiveProductTbar = Ext.create('Ext.toolbar.Toolbar', {
//		region : 'north',
//		border : false,
//        items: [{
//        	xtype: 'button',
//        	text: '添加产品',
//        	iconCls: 'add',
//        	handler: function() {
//        		insertGiveProduct();
//            }
//        },{
//        	xtype: 'button',
//        	text: '确认',
//        	iconCls: 'accept',
//        	handler: function() {
//        		GiveProductWindow.hide();
//            }
//        }]
//    });
//	var GiveProductTypeRenderer = function(value, cellmeta, record) {
//        var index = GiveProductTypeStore.find("id", value);
//        var record = GiveProductTypeStore.getAt(index);
//        var displayText = "";
//        if (record == null)
//        	displayText = value;
//        else
//        	displayText = record.data.typeName;
//        return displayText;
//    };
//    var GiveProductRenderer = function(value, cellmeta, record) {
//        var index = GiveStockStore.find("id", value);
//        var record = GiveStockStore.getAt(index);
//        var displayText = "";
//        if (record == null)
//        	displayText = value;
//        else {
//        	if(Number(record.data.number) > stockWarn)
//        		displayText = record.data.code + "/" + record.data.name;
//        	else
//        		displayText = "<font color=red>" + record.data.code + "/" + record.data.name + "（库存" + record.data.number + "件）</font>";
//        }
//        return displayText;
//    };
//	var GiveProductColumns= [GiveProductRowNum,
//	        {header : '类型',dataIndex: 'typeId',menuDisabled: true,width: '16%', editor: GiveProductTypeCombo, renderer: GiveProductTypeRenderer},
//	        {header : '产品',dataIndex: 'goodsId',menuDisabled: true,width: '45%', editor: GiveStockCombo, renderer: GiveProductRenderer },
//	        {header : '单价（元）',dataIndex: 'unitPrice',menuDisabled: true,xtype: 'numbercolumn', renderer: Ext.util.Format.numberRenderer("0.00")},
//	        {header : '数量',dataIndex: 'number',menuDisabled: true, editor: {xtype: 'numberfield', allowBlank: false, minValue: 0}},
//	        {xtype: 'actioncolumn',header : '操作',width: 50,align:'center',sortable: false, menuDisabled: true,
//                items: [{
//                    iconCls: 'delete',
//                    tooltip: '删除',
//                    scope: this,
//                    handler: function(grid, rowIndex){
//                    	GiveProductGrid.getStore().removeAt(rowIndex);
//                    }
//                }]
//            }
//	];
//	
//	var GiveProductGrid = Ext.create('Ext.grid.GridPanel', {
//		minHeight: 220,
//		columns : GiveProductColumns,
//		store : GiveProductStore, 
//		region : 'center',
//		autoScroll : true,
//		columnLines : true,
//		frame : false,
//		tbar : GiveProductTbar, 
//		stripeRows: true,
//		forceFit: true,
//		plugins: [GiveProductCellEditing],
//		viewConfig : {
//			enableTextSelection:true	//设置单元格可以选择
//		},
//		loadMask : {
//			msg : '正在加载表格数据,请稍等...'
//	    }
//	});
//	
//	var GiveProductWindow = Ext.create('Ext.Window',{
//		layout : 'fit',
//		title: '商品赠送',
//		iconCls: 'mouse',
//		width : 600,
//		height : 380,
//		resizable : false,
//		draggable : true,
//		closeAction : 'hide',
//		modal : false,
//		collapsible : true,
//		titleCollapse : true,
//		maximizable : false,
//		buttonAlign : 'right',
//		border : false,
//		animCollapse : true,
//		constrain : true,
//		items: [GiveProductGrid]
//	});
//	
//	GiveProductWindow.on('show', function(){
//		GiveProductTypeStore.load();
//		if(GiveProductStore.getCount() == 0)
//			insertGiveProduct();
//	});
//	
//	/////////////////////////////////////// 卡券赠送
//	
//	var GiveCouponsRowNum = new Ext.grid.RowNumberer({
//		header : 'NO',
//		resizable: true,
//		width : 40
//	});
//	
//	var GiveCouponsStore = Ext.create('Ext.data.Store', {
//        fields: ['id', 'name', 'price', 'applyId', 'minimum', 'goodsName'],
//        proxy: {
//            type: 'ajax',
//            url : '/goods/queryCouponsByDept.atc',
//            reader : {
//                root: 'couponsList'
//            }
//        }
//	});
//	
//	var GiveCouponsSelModel = Ext.create('Ext.selection.CheckboxModel', {
//		injectCheckbox: 1
////		mode: 'SINGLE'
//	});
//	
//	var GiveCouponsTbar = Ext.create('Ext.toolbar.Toolbar', {
//		region : 'north',
//		border : false,
//        items: [{
//        	xtype: 'button',
//        	text: '确 认',
//        	iconCls: 'accept',
//        	handler: function() {
//        		GiveCouponsWindow.hide();
//            }
//        }]
//    });
//	
//	var GiveCouponsColumns= [GiveCouponsRowNum,
//                {header : 'ID',dataIndex: 'id', hidden: true},
// 	            {header : '名称',dataIndex: 'name'},
// 	            {header : '抵扣金额',dataIndex: 'price', renderer: Ext.util.Format.numberRenderer("0.00")},
// 	            {header : '最低消费',dataIndex: 'minimum', renderer: Ext.util.Format.numberRenderer("0.00")},
// 	            {header : '适用服务',dataIndex: 'applyId', hidden: true},
// 	            {header : '适用服务',dataIndex: 'goodsName'}
//     	];
//	
//	var GiveCouponsGrid = Ext.create('Ext.grid.GridPanel', {
//		minHeight: 220,
//		columns : GiveCouponsColumns,
//		store : GiveCouponsStore, 
//		selModel : GiveCouponsSelModel,
//		region : 'center',
//		autoScroll : true,
//		columnLines : true,
//		frame : false,
//		tbar : GiveCouponsTbar, 
//		stripeRows: true,
//		forceFit: true,
//		viewConfig : {
//			enableTextSelection:true	//设置单元格可以选择
//		},
//		loadMask : {
//			msg : '正在加载表格数据,请稍等...'
//	    }
//	});
//	
//	var GiveCouponsWindow = Ext.create('Ext.Window',{
//		layout : 'fit',
//		title: '卡券赠送',
//		iconCls: 'creditcards',
//		width : 600,
//		height : 380,
//		resizable : false,
//		draggable : true,
//		closeAction : 'hide',
//		modal : false,
//		collapsible : true,
//		titleCollapse : true,
//		maximizable : false,
//		buttonAlign : 'right',
//		border : false,
//		animCollapse : true,
//		constrain : true,
//		items: [GiveCouponsGrid]
//	});
//	
//	GiveCouponsWindow.on('show', function(){
//		GiveCouponsStore.load();
//	});
	
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
		region: 'south',
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
//				columnWidth : .45,
//				minWidth: 280,
//				border : false,
//				xtype: 'toolbar',
//				style:{
//					padding: '10 0 0 0',
//					marginLeft: '20'
//				},
//				items: [{
//				    xtype:'button',
//				    text: '服务赠送',
//				    iconCls: 'award_star_gold_2',
//				    handler : function() {
//				    	GiveProjectWindow.show();
//				    }
//				},{
//					xtype:'button',
//					text: '商品赠送',
//					iconCls: 'mouse',
//					handler : function() {
//						GiveProductWindow.show();
//				    }
//				},{
//				    xtype:'button',
//				    text: '卡券赠送',
//				    iconCls: 'creditcards',
//				    handler : function() {
//						GiveCouponsWindow.show();
//				    }
//				}]
//			},{
//				columnWidth : .20,
//				id : 'deductible',
//				xtype: 'numberfield',
//				fieldLabel: '积分',
//				padding: '5 0 0 0',
//				labelWidth: 40,
//				labelAlign: 'right',
//				width : 180,
//				minValue: 100,
//				maxText: '积分不足',
//				value: 0,
//				disabled: true,
//				listeners: {
//                	change: function(field, newValue, oldValue){
//                		if(field.isValid()){
//                			var point = Number(memberRecord.data.point);
//                			if(Number(newValue) <= point){
//                				poffset = (Number(newValue) / Number(offsetParam)).toFixed(2);
//                				Ext.getCmp("poffset").setValue("可抵现" + poffset + "元");
//                			}
//                			completeReceivable();
//                		}
//                    }
//                }
//			}, {
//				columnWidth : .13,
//				id: 'poffset',
//				xtype: 'displayfield',
//				minWidth : 90,
//				value: '可抵现0.00元',
//				padding: '5 0 0 0',
//				margin: '0 10 0 5'
//			}, {
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
					hidden: true,
					anchor : '100%',
					listeners: {
	                	change: function(field, newValue, oldValue){
	                		if(!isNaN(field.value)) {
	                			receivable = newValue;
//	                			if(memberRecord){
//	                				if(receivable > poffset && Number(memberRecord.data.point) > 0)
//	                					Ext.getCmp("deductible").setDisabled(false);
//	                				else
//	                					Ext.getCmp("deductible").setDisabled(true);
//	                			}
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
				items:[pcash, pcard, ptransfer, pwechat, palipay, pbill]
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
				minWidth: 250,
				border : false,
				bbar: ['->',{
				    xtype:'button',
				    text: '立即开单',
				    padding: '5',
				    scale: 'medium',
				    iconCls: 'page_white_text'
				},{
				    xtype:'button',
				    padding: '5',
				    scale: 'medium',
//				    style:'background-image:-webkit-linear-gradient(top,#d3a203,#dba907)',
				    text: '组合消费',
				    iconCls: 'accept',
				    handler : function() {
				    	submitOrder();
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
        layout:'border',
        tools:[{
            type:'help',
            tooltip: '操作指引',
            callback: function(panel, tool, event) {
            	parent.global.openSystemHelpTab();
            }
        }],
        items:[OrderHead, RechargePanel, OrderSubmit]
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
	
	//如果有会员卡号参数传过来则，完善右边会员信息并插入一条项目初始数据，
	//否则会员文本框获得焦点
	if(!Ext.isEmpty(vipNo)){
		completeMemberInfo(vipNo);
		MemberCombo.setValue(vipNo);
	} else {
		MemberCombo.focus();
	}
	
	/////////////////////////////////////////////////////////////  方法部分  //////////////////////////////////////////////////
	
	//更改订单类型
	function OrderTypeChange(item){
		if(item.value == 'package'){
			parent.global.openTabByMenuId('010302', "vip=" + (vipNo ? vipNo : '') + "&car=" + carId);
		} else if(item.value == 'return') {
			parent.global.openTabByMenuId('010304', "vip=" + (vipNo ? vipNo : '') + "&car=" + carId);
		} else if(item.value == 'recharge') {
			parent.global.openTabByMenuId('010305', "vip=" + (vipNo ? vipNo : '') + "&car=" + carId);
		} else if(item.value == 'deduction') {
			parent.global.openTabByMenuId('010311', "vip=" + (vipNo ? vipNo : '') + "&car=" + carId);
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
	
//	//添加一行赠送服务
//	function insertGiveProject(){
//		var record = new GiveProjectModel({
//			typeId: '',
//			goodsId: '',
//			unitPrice: 0,
//			number: 1
//		});
//		GiveProjectStore.insert(GiveProjectStore.getCount(), record);
//		GiveProjectCellEditing.startEditByPosition({
//			row: GiveProjectStore.getCount() - 1,
//			column: 1
//		});
//	}
//	
//	//添加一行赠送商品
//	function insertGiveProduct(){
//		var record = new GiveProductModel({
//			typeId: '',
//			goodsId: '',
//			unitPrice: 0,
//			number: 1
//		});
//		GiveProductStore.insert(GiveProductStore.getCount(), record);
//		GiveProductCellEditing.startEditByPosition({
//			row: GiveProductStore.getCount() - 1,
//			column: 1
//		});
//	}
	
	//根据会员卡号加载会员信息
	function completeMemberInfo(vipNoNo){
		Ext.Ajax.request({
			url : '/member/queryMemberByVipNo.atc',
			params : {
				vipNo: vipNoNo
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
							Ext.getCmp("currentBalance").setValue(memberRecord.data.balance);
							if(!Ext.isEmpty(record.picture))
								headImg.setSrc("/upload/member/" + record.picture);
							vipNo = record.vipNo;
//							//若积分大于0，设置兑换框最大值
//							if(Number(record.point) > 0)
//								Ext.getCmp("deductible").setMaxValue(record.point);
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
	
	//计算应收
	function completeReceivable(){
		receivable = 0
		receivable = Number(Ext.getCmp("recharge").getValue()).toFixed(2);
		receivable = receivable - poffset;
		rece = receivable;
		Ext.getCmp("receivable").setValue(receivable.toFixed(2));
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
			var payButtons = new Array("pcash","pcard","ptransfer","pwechat","palipay","pbill");
			Ext.each(payButtons, function(button) {
				Ext.getCmp(button).setValue(0);
				var btnText = Ext.getCmp("payRow").getComponent(button).text;
				btnText = btnText.replace("<font color='#FFCC00'>", "").replace("</font>", "");
				Ext.getCmp("payRow").getComponent(button).setText(btnText);
			});
			Ext.getCmp("payRow").getComponent(btn.itemId).setText("<font color='#FFCC00'>" + btn.text + "</font>");
			Ext.getCmp(btn.itemId).setValue(receivable);
			submitOrder();
		}
    }
	
	//计算消费所得积分
	function completeSalePoint(){
    	return (pointRule == 0) ? 0 : Math.round(Number(actual) / pointRule);
	}
	
	//提交订单
	function submitOrder(){
		if(Number(actual) < Number(receivable)){
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
		}
		
		var detailItem = "", count = 0;
		var details = Ext.getCmp('detailItem').items;
        for(var i = 0; i < details.length; i++) {
        	if(details.get(i).checked){
        		detailItem += details.get(i).inputValue + ",";
            	count++;
            }
        }
		if(selectMode > 0) {
	        if(count > selectMode) {
	        	Ext.MessageBox.show({
	        		title: '提示',
		            msg: '套餐内容只能选' + selectMode + ' 项！',
		            buttons: Ext.MessageBox.OK,
		            icon: Ext.MessageBox.INFO
			    });
	        	return false;
	        } else if(count < selectMode) {
	        	Ext.Msg.confirm('请确认', '可以选' + selectMode + ' 项，确定只选' + count + '项吗？', function(btn, text) {
	        		if(btn == 'no'){
	        			return false;
	        		}
	        	});
	        }
		}
		
		var saleDate = parent.dateFormat(Ext.getCmp("saleDate").getValue()) + ' ' + Ext.getCmp("saleTime").getRawValue();
		
        Ext.MessageBox.show({
			title: '请稍等',
			msg: '正在提交订单 ...',
			width: 200,
			progress: true,
			closable: false
        });
        Ext.Ajax.request({
            method:'POST',
            timout: 600000,
            url: '/order/submitRechargeOrder.atc',
            params : {
            	'orderHead.orderType' : orderType,
            	'orderHead.memId' : memberRecord.data.memId,
            	'orderHead.vipNo' : memberRecord.data.vipNo,
            	'orderHead.carId' : CarCombo.getValue(),
            	'orderHead.carNumber' : Ext.getCmp("cNumber").getValue(),
            	'orderHead.saleDate' : saleDate,
            	'orderHead.oprice' : rece,
            	'orderHead.aprice' : Ext.getCmp("receivable").getValue(),
            	'orderHead.point' : completeSalePoint(),
            	'orderHead.poffset' : 0,
            	'orderHead.pbalance' : Ext.isEmpty(Ext.getCmp("pbalance").getValue()) ? 0 : Ext.getCmp("pbalance").getValue(),
            	'orderHead.pcash' : Ext.isEmpty(Ext.getCmp("pcash").getValue()) ? 0 : Ext.getCmp("pcash").getValue(),
            	'orderHead.pcard' : Ext.isEmpty(Ext.getCmp("pcard").getValue()) ? 0 : Ext.getCmp("pcard").getValue(),
            	'orderHead.ptransfer' : Ext.isEmpty(Ext.getCmp("ptransfer").getValue()) ? 0 : Ext.getCmp("ptransfer").getValue(),
            	'orderHead.pbill' : Ext.isEmpty(Ext.getCmp("pbill").getValue()) ? 0 : Ext.getCmp("pbill").getValue(),
            	'orderHead.pwechat' : Ext.isEmpty(Ext.getCmp("pwechat").getValue()) ? 0 : Ext.getCmp("pwechat").getValue(),
            	'orderHead.palipay' : Ext.isEmpty(Ext.getCmp("palipay").getValue()) ? 0 : Ext.getCmp("palipay").getValue(),
            	'orderHead.status' : 1,
            	'orderHead.creator' : Ext.isEmpty(EmployeeSingleCombo.getValue()) ? parent.GG : EmployeeSingleCombo.getValue(),
            	'orderHead.remark' : Ext.getCmp("orderRemark").getValue(),
            	rechargeId : RechargeCombo.getValue(),
            	detailItem : detailItem
			},
			success : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
				Ext.MessageBox.hide();
				if(result.success){
					orderNo = result.msg;
					Ext.MessageBox.show({
			            title: '结果',
			            msg: result.msg  + " 消费记录创建成功！",
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
			        			location.reload();
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
	
	function printOrder(btn, text){
		if(!isNaN(text)){
			submitOrder(text);
		} else {
			Ext.MessageBox.show({
	           title: '提示',
	           msg: "定金输入不合法！",
	           buttons: Ext.MessageBox.OK,
	           icon: Ext.MessageBox.ERROR
            });
			return;
		}
    }
	
});