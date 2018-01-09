Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	// 定义自动当前页行号
	var rowNum = new Ext.grid.RowNumberer({
				header : 'NO',
				resizable: true,
				width : 40
			});
	
	var treeStore = Ext.create('Ext.data.TreeStore', {
		id:'treeStore',
        proxy: {
            type: 'ajax',
            reader: 'json',
            url: '/sys/queryNormalTree.atc?parentId=0'
        }
    });
	
	var menuTree = new Ext.tree.TreePanel({
	    store: treeStore,
	    height: '100%',
        border : false,
        rootVisible: false,
        listeners:{
	    	itemclick: function(tree, record, item, index) {
	    		Ext.getCmp('id').setValue(record.data.id);
	    		Ext.getCmp('name').setValue(record.data.text);
	    		Ext.getCmp('display').setValue(record.data.text);
	    		Ext.Ajax.request({
        			url : '/sys/querySysHelpById.atc',
        			params : {
        				id : record.data.id
        			},
        			success : function(resp, opts) {
        				var result = Ext.decode(resp.responseText);
        				if(result && !Ext.isEmpty(result) && !Ext.isEmpty(result.id))
        					Ext.getCmp("text").setValue(result.text);
        				else
        					Ext.getCmp("text").setValue('');
        			},
        			failure : function(resp, opts) {
        				Ext.Msg.alert('提示', "获取出错！");
        			}
        		});
	    	}
	    }
	});
	
	var tbar = Ext.create('Ext.toolbar.Toolbar', {
			region : 'north',
			border : false,
	        items: [{
	        	xtype: 'button',
	        	id: 'insert',
	        	text: ' 保 存 ',
	        	iconCls: 'accept',
	        	handler: function() {
	        		if(Ext.isEmpty(Ext.getCmp('id').getValue())){
	        			Ext.MessageBox.show({
 				           	title: '提示',
 				           	msg: "没有选中任何菜单！",
 				           	buttons: Ext.MessageBox.OK,
 				           	icon: Ext.MessageBox.ERROR
                        });
	        			return;
	        		}
	        		helpForm.form.submit({
						url : '/sys/insertSysHelp.atc',
						waitTitle : '提示',
						method : 'POST',
						waitMsg : '正在处理数据,请稍候...',
						success : function(form, action) {
							Ext.example.msg('提示', '保存成功！');
//							helpForm.form.reset();
						},
						failure : function(form, action) {
							var msg = action.result.msg;
							Ext.MessageBox.alert('提示', '保存失败！');
							helpForm.form.reset();
						}
					});
	            }
	        },{
	        	xtype: 'displayfield',
	        	id: 'display',
	        	width: 200
	        }]
	    });
	
	var helpForm = new Ext.form.FormPanel({
		id : 'helpForm',
		layout: 'anchor',
		title:'操作指引',
		iconCls: 'help',
		tbar: tbar,
		border : true,
		items : [{
			xtype: 'fieldcontainer',
	        layout: 'hbox',
	        defaults: {flex: 1,xtype:'htmleditor',padding: '5 5 5 5'},
	        items: [
	               {id:'id', name: 'sysHelp.id', xtype: 'hidden'},
	               {id:'name', name: 'sysHelp.name', xtype: 'hidden'},
                   {id:'text', name: 'sysHelp.text', height: document.body.scrollHeight - 100,
					    enableAlignments: true,
	                    enableColors: true,
	                    enableFont: true,
	                    enableFontSize: true,
	                    enableFormat: true,
	                    enableLinks: true,
	                    enableLists: true,
	                    fontFamilies : ['宋体','黑体','隶书','微软雅黑','Arial','Courier New','Tahoma','Verdana'],
	                    plugins: Ext.create('Ext.ux.custom.ImageHtmlEditor')
					}
	        ]
		}]
	});
	
	// 布局模型
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [{
			title : '菜单资源',
			iconCls : 'layout',
			tools : [{
				id : 'refresh',
				handler : function() {
			        treeStore.reload();
				}
			}],
			collapsible : true,
			width : 240,
			minSize : 160,
			maxSize : 240,
			split : true,
			region : 'west',
			autoScroll : true,
			items : [ menuTree ]
		}, {
			region : 'center',
			layout : 'fit',
			border : false,
			items : [ helpForm ]
		}]
	});
	
	
});