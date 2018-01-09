Ext.require(['Ext.ux.TreePicker']);
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	var currentForm;
	
	Ext.define('MsgTemplate', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'id', type: 'int'},//主键
	        {name: 'templateTypeNo', type: 'int'},//模板类型ID
	        {name: 'deptCode', type: 'string'},//门店代码
	        {name: 'content', type: 'string'},//模板内容
	        {name: 'remainingDays', type: 'int'},//剩余天数
	        {name: 'sendRate', type: 'int'},//发送频率
	        {name: 'createTime', type: 'string'},//创建时间
	        {name: 'updateTime', type: 'string'}//修改时间
	    ]
	});
	
	// 定义自动当前页行号
	var msgTemplateTypeRowNum = new Ext.grid.RowNumberer({
		header : 'NO',
		resizable: true,
		width : 30
	});

	var msgTemplateTypeStore = Ext.create('Ext.data.ArrayStore', {
		  fields: ['id', 'name'],
		  data : [ [ 1, '项目消费' ], [ 2, '套餐购买' ], [ 3, '客户充值' ], [4, '下单成功'], [5, '违章处理'],
		           [ 6, '保养到期提醒' ], [ 7, '保险到期提醒' ], [9, '年检到期提醒'], [10, '镀晶到期提醒'],
		  		   [11, '提车通知'], [12, '生日提醒']
		  ]
	});
	
	var toolbar = Ext.widget('toolbar', {
        items : [{
                text: '保 存',
                iconCls: 'accept'
            },{
                text: 'Format',
                iconCls: 'add16'
            }
        ]
    });
	
	var textStore = Ext.create('Ext.data.ArrayStore', {
		fields : [ 'value', 'text' ],
		data : [ [ 1, '称呼' ], [ 2, '项目名称' ], [ 3, '消费金额' ], [ 4, '所得积分' ], [ 5, '当前余额' ],
		         [6, '车牌'], [7, '剩余天数', [8, '消费时间'], [9, '公里数'], [10, '有效期'], [11, '支付方式']]]
	});
	
	var previewStore = Ext.create('Ext.data.ArrayStore', {
		fields : [ 'value', 'preview' ],
		data : [ [ 1, '林先生' ], [ 2, '蜡水洗车' ], [ 3, '30' ], [ 4, '3' ], [ 5, '150' ],
		         [6, '粤B888888'],[7, '30'],[8, '2015年3月20日'], [9, '88888KM'], [10, '2017年9月20日'], 
		         [11, '余额，现金']]
	});
	
	var msgTemplateFormPanel = Ext.create('Ext.form.FormPanel', {
		id : 'msgTemplateFormPanel',
		layout: 'anchor',
		defaults: {
            anchor: '100%',
            layout: 'hbox',
            xtype:'fieldcontainer',
            maxWidth: 600
        },
        bodyPadding: 10,
		border : false,
		items : [{
	        xtype: 'fieldset',
			title: '配置',
			collapsible: false,
			layout: 'column',
			defaults: {
            	columnWidth: 1/4,
            	layout: {
					defaultMargins: {top: 0, right: 5, bottom: 5, left: 0}
				}
            },
			items:[{
				xtype:'displayfield',
				value:'{1}:称呼'
			},{
				xtype:'displayfield',
				value:'{2}:项目名'
			},{
				xtype:'displayfield',
				value:'{3}:消费金额'
			},{
				xtype:'displayfield',
				value:'{4}:所得积分'
			},{
				xtype:'displayfield',
				value:'{5}:当前余额'
			},{
				xtype:'displayfield',
				value:'{6}:车牌'
			},{
				xtype:'displayfield',
				value:'{7}:剩余天数'
			},{
				xtype:'displayfield',
				value:'{8}:消费时间'
			},{
				xtype:'displayfield',
				value:'{9}:公里数'
			},{
				xtype:'displayfield',
				value:'{10}:有效期'
			},{
				xtype:'displayfield',
				value:'{11}:支付方式'
			}]
		},{
			xtype: 'fieldset',
			itemId: 'frequency',
			hidden : true,
			title: '频率配置',
			collapsible: false,
			layout: 'anchor',
			defaults: {
	            anchor: '100%',
	            layout: 'hbox',
	            xtype:'fieldcontainer'
	        },
			bodyPadding: '10 10 0 0',
			items : [{
				defaults: {flex: 1, xtype:'textfield',  labelSeparator: '', margin: '0 10 0 0'},
			    items: [{
			    	id: 'remainingDays',
			    	flex: 6,
			    	labelWidth:150,
					fieldLabel: '在距离提醒时间剩余'
			    },{
			    	id: 'sendRate',
			    	flex: 4,
			    	labelWidth:60,
			    	fieldLabel: '天时，每'
			    },{
			    	flex: 3,
			    	xtype:'displayfield',
			    	value:'天主动进行一次提醒',
			    }]
			}]
		},{
			xtype: 'fieldset',
			title: '模板编辑',
			collapsible: false,
			layout: 'anchor',
			defaults: {
	            anchor: '100%',
	            layout: 'hbox',
	            xtype:'fieldcontainer'
	        },
			bodyPadding: '10 10 0 0',
			items : [{
				defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'top',labelStyle: 'font-weight:bold', margin: '0 10 0 0'},
			    items: [{
			    	id:'id',
			    	xtype:'hiddenfield',
			    	fieldLabel: '主键'
			    },{
			    	id:'templateTypeNo',
			    	xtype:'hiddenfield',
			    	fieldLabel: '模板类型ID'
			    },{
			    	id:'content',
			    	xtype:'textarea',
					fieldLabel: '消费模板',
					rows: 6,
					value:'',
					listeners: {
						change(textarea, newValue, oldValue) {
			            	previewSMS('saleTemp', newValue);
			            }
			        }
			    }]
			},{
				defaults: {flex: 1, xtype:'textfield', labelWidth:70, labelAlign:'top',labelStyle: 'font-weight:bold', margin: '0 10 0 0'},
				items: [{
			    	xtype:'displayfield',
			    	id: 'saleTemp',
					fieldLabel: '预览（共97字）',
					value:'',
			    }]
		    }]
		},{
			xtype: 'displayfield',
			value: '温馨提示：<br>1、短信内容只能包含一个【】括号，且括号里面为店铺签名。<br>2、短信是否发送成功可到店铺设置-提醒设置-短信管理中查看。<br>3、当短信内容超过70字，将自动拆分成2条发送。<br>4、店铺设置-提醒设置-短信管理中充值短信条数。'
		}]
	});
	
  	var msgTemplateTypeColumns= [msgTemplateTypeRowNum,
	            {header : 'ID',dataIndex: 'id', width: 60, hidden: true},
	            {header : '名称',dataIndex: 'name', width: 150}
	  	];
  	
	var msgTemplateTypeGrid = new Ext.grid.GridPanel({
			columns : msgTemplateTypeColumns,
			store : msgTemplateTypeStore, 
			region : 'center',
			autoScroll : true,
			columnLines : true,
			stripeRows:true,
			forceFit: true,
			viewConfig : {
				enableTextSelection:true	//设置单元格可以选择
			},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			},
			listeners:{
		        itemclick: function(grid, record, item, index, e) {
		        	var msgTemplateTypeNo = record.data.id;
		        	
		        	var frequency = msgTemplateFormPanel.getComponent("frequency");
		        	if(frequency){
		        		if(6 <= msgTemplateTypeNo && msgTemplateTypeNo <= 10)
		        			frequency.setVisible(true);
		        		else
		        			frequency.setVisible(false);
		        	}
		        	
		        	msgTemplateFormPanel.getForm().reset();
		        	initMsgTemplateInfo(msgTemplateTypeNo);//初始化FormPanel内容
		        }
		    }
		});
	
	//页面初始布局
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [{
			title : '类型',
			iconCls : 'tag_blue',
			tools : [{
				type:'refresh',
				handler : function() {
					msgTemplateTypeStore.reload();
				}
			}],
			collapsible : true,
			width : '25%',
			split : true,
			region : 'west',
			layout : 'fit',
			border : false,
			items : [ msgTemplateTypeGrid ]
		}, {
			title : '模板设置',
			iconCls : 'bricks',
			tools:[{
	            type:'help',
	            tooltip: '操作指引',
	            callback: function(panel, tool, event) {
	            	parent.global.openSystemHelpTab();
	            }
	        }],
			region : 'center',
			itemId: 'rightForm',
			split : true,
			layout : 'fit',
			border : false,
			items: msgTemplateFormPanel,
			tbar:[{
				text: '保 存',
                iconCls: 'accept',
                handler: function(){
	    			  Ext.Ajax.request({
	    					url : '/msg/insertOrUpdateMsgTemplate.atc',
	    					params : {
	    						'msgTemplate.id': Ext.getCmp("id").getValue()==''? 0:Ext.getCmp("id").getValue(),
	    						'msgTemplate.templateTypeNo': Ext.getCmp("templateTypeNo").getValue()==''? 0:Ext.getCmp("templateTypeNo").getValue(),
	    						'msgTemplate.content': Ext.getCmp("content").getValue(),
	    						'msgTemplate.remainingDays': Ext.getCmp("remainingDays").getValue()==''? 0:Ext.getCmp("remainingDays").getValue(),
	    						'msgTemplate.sendRate': Ext.getCmp("sendRate").getValue()==''? 0:Ext.getCmp("sendRate").getValue()
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
		}]
	});
	
	currentForm = msgTemplateFormPanel;
	
	initMsgTemplateInfo(1);//初始化FormPanel内容
	
	/**
	 * 短信预览
	 * targetId 预览容器ID
	 * smsStr 短信模板
	 */
	function previewSMS(targetId, smsStr){
		Ext.each(previewStore.data.items, function(item){
			if(smsStr.indexOf("{" + item.data.value + "}") > 0){
				smsStr = smsStr.replace("{" + item.data.value + "}", item.data.preview);
			}
		});
		Ext.getCmp(targetId).setValue(smsStr);
		Ext.getCmp(targetId).setFieldLabel("预览（共" + getByteLen(smsStr) + "字）");
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
	
	//点击模板类型列表时，根据模板类型ID获取对应店铺模板信息
	function initMsgTemplateInfo(templateTypeNo){
		Ext.getCmp("templateTypeNo").setValue(templateTypeNo);//设置模板类型ID
		Ext.Ajax.request({
			url : '/msg/queryMsgTemplateByTemplateTypeNo.atc',
			params : {
				templateTypeNo: templateTypeNo
			},
			success : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
				if(result.success){
					if(Ext.isArray(result.msgTemplateList)){
						if(result.msgTemplateList.length == 1){
							var record = result.msgTemplateList[0];
							msgTemplateRecord = new MsgTemplate({
								id: record.id,
								templateTypeNo: record.templateTypeNo,
						        content: record.content,
						        remainingDays: record.remainingDays,
						        sendRate: record.sendRate
						    });
							msgTemplateFormPanel.loadRecord(msgTemplateRecord);
						} else if(result.msgTemplateList.length == 0)
							Ext.MessageBox.show({
						    	title: '提示',
						        msg: '没有找到对应短信模板信息！',
						        buttons: Ext.MessageBox.OK,
						        icon: Ext.MessageBox.ERROR
		                    });
						else
							Ext.MessageBox.show({
						           title: '提示',
						           msg: '短信模板信息重复，请联系管理员！',
						           buttons: Ext.MessageBox.OK,
						           icon: Ext.MessageBox.ERROR
		                    });
					} else 
						Ext.MessageBox.show({
					           title: '提示',
					           msg: '获取短信模板错误！',
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
});

