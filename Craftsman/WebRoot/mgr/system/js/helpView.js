Ext.onReady(function() {
	
	// 布局模型
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [{
			region : 'center',
			layout : 'fit',
			border : false,
			id: 'title',
			title:'操作指引',
			items : [{
				id: 'content',
				padding: 10,
				autoScroll: true,
				html: '1'
            }]
		}]
	});
	
	if(!Ext.isEmpty(menuId)){
		Ext.Ajax.request({
			url : '/sys/querySysHelpById.atc',
			params:{
				id: menuId
			},
			success : function(resp, opts) {
				var result = Ext.decode(resp.responseText);
				if(result && !Ext.isEmpty(result)){
					
					if(!Ext.isEmpty(result.name))
						Ext.getCmp("title").setTitle(result.name + " 操作指引");
					else
						Ext.getCmp("title").setTitle("操作指引");
					if(!Ext.isEmpty(result.text))
						Ext.getCmp("content").body.update(result.text);
					else
						Ext.getCmp("content").body.update('操作指引正在更新中。。。');
				}
			},
			failure : function(resp, opts) {
	            Ext.Msg.alert('提示', "加载操作指引出错！");
			}
		});
	}
	
	
});