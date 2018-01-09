Ext.require([
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.ux.DataView.DragSelector',
    'Ext.ux.DataView.LabelEditor'
]);
Ext.onReady(function() {
	
	Ext.QuickTips.init();
	
	var imageIds, memId;
	if(typeof(ids) != 'undefined') imageIds = ids;
	if(typeof(mId) != 'undefined') memId = mId;
	
	var imageId;
	
	var store = Ext.create('Ext.data.Store', {
        fields: ['imageId', 'name', 'path', 'size'],
        proxy: {
            type: 'ajax',
            url : '/base/queryImageByIds.atc',
            reader : {
                root: 'imageList'
            }
        }
    });
	
//	// 翻页排序时带上查询条件
//	store.on('beforeload', function() {
//		store.getProxy().extraParams = {ids: imageIds, memId: memId};
//	});
	
    var uploadForm = new Ext.form.FormPanel({
		id : 'uploadForm',
		name : 'uploadForm',
		defaultType : 'textfield',
		labelAlign : 'right',
		labelWidth : 99,
		frame : false,
		fileUpload : true,
		border: false,
		bodyPadding: '5 5 0',
		items : [{
			xtype: 'hiddenfield', id:'memId', name: 'memId', value: memId
		},{
			xtype:'filefield',fieldLabel:'请选择文件',buttonText:'浏览...',labelAlign:'right',
			id:'uploadFile',name:'upload',allowBlank:true,anchor:'99%'
		}]
	});
    
    var uploadWindow = new Ext.Window({
		title : '上传文件', // 窗口标题
		layout : 'fit',
		width : 380,
		height : 120,
		x: 300,
		y: 100,
		resizable : false,
		draggable : true,
		closeAction : 'hide',
		title : '上传文件',
		modal : false,
		collapsible : true,
		titleCollapse : true,
		maximizable : false,
		buttonAlign : 'right',
		border : false,
		animCollapse : true,
		constrain : true,
		items : [uploadForm], // 嵌入的表单面板
		buttons : [{ // 窗口底部按钮配置
			text : '上 传', // 按钮文本
			iconCls : 'upload', // 按钮图标
			handler : function() { // 按钮响应函数
				var file = Ext.getCmp('uploadFile').getValue();
				if (Ext.isEmpty(file)) {
					Ext.Msg.alert('提示', '请先选择您要上传的文件...');
					return;
				}
				if(Ext.isEmpty(Ext.getCmp("memId").getValue())){
					Ext.Msg.alert('提示', '参数错误！');
					return;
				}
				uploadForm.form.submit({
					url : '/base/uploadImage.atc',
					waitTitle : '提示',
					method : 'POST',
					waitMsg : '正在处理数据,请稍候...',
					success : function(form, action) {
						Ext.example.msg('提示', '上传成功！');
						uploadForm.form.reset();
						uploadWindow.hide();
						store.reload({
							params: {
								memId: memId
							}
						});
					},
					failure : function(form, action) {
						var msg = action.result.msg;
						Ext.MessageBox.alert('提示', '上传失败:' + msg);
						uploadForm.form.reset();
					}
				});
			}
		}, {	// 窗口底部按钮配置
				text : '重 置', // 按钮文本
				iconCls : 'arrow_rotate_anticlockwise', // 按钮图标
				handler : function() { // 按钮响应函数
					uploadForm.form.reset();
				}
		}]
	});
    
	var toolbar = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		border : true,
        items: [{
        	xtype: 'button',
        	text: '上传',
        	iconCls: 'add', 
        	handler: function(){
        		if(store.getTotalCount() < 4){
        			uploadWindow.show();
        		} else 
        			Ext.Msg.alert('提示', '最多上传4张照片！');
            }
//        },{
//        	xtype: 'button',
//        	text: '查询',
//        	iconCls: 'page_find', 
//        	handler: function(){
//        		searchWindow.show();
//            }
        },{
        	xtype: 'button',
        	text: '删除',
        	iconCls: 'delete', 
        	handler: function(){
        		deletePicture(imageId);
            }
        }]
    });
	
    var chooseAction = Ext.create('Ext.Action', {
        iconCls: 'delete',
        text: '删除',
        handler: function(widget, event) {
        	deletePicture(imageId);
        }
    });

    var contextMenu = Ext.create('Ext.menu.Menu', {
        items: [chooseAction]
    });
	
	var panel = Ext.create('Ext.Panel', {
        id: 'images-view',
        frame: false,
		autoScroll : true,
		region : 'center',
		border : false,
        items: Ext.create('Ext.view.View', {
        	id: 'imageView',
            store: store,
            tpl: [
                '<tpl for=".">',
                    '<div class="thumb-wrap" id="{imageId}">',
                    '<div class="thumb qtip-target">',
                    //'<a href="web{imgPath}" data-lightbox="imageView" data-title="{orgname}<br/>{width}×{height} / {size} KB">',
                    '<img src="{path}" data-qtip="<img src={path} height=300px/>" /></div>',
                    '<span class="x-editable">{name}</span>',
                    '</div>',
                '</tpl>'
            ],
            //width: bodyWidth - 10,
            //一定要有一个高度，否则trackOver失效
            autoScroll: true,
            height: '100%',
            autoHeight: true,
//            multiSelect: true,
//            trackOver: true,
            overItemCls: 'x-item-over',
            itemSelector: 'div.thumb-wrap',
            emptyText: '<div style="padding:10px;width:100%;text-align:center">没有找到照片！</div>',
            plugins: [
                Ext.create('Ext.ux.DataView.DragSelector', {}),
                Ext.create('Ext.ux.DataView.LabelEditor', {dataIndex: 'name' + 'suffix'})
            ],
            prepareData: function(data) {
                Ext.apply(data, {
                	dateString: Ext.util.Format.substr(data.datetime, 0, 19)
                });
                return data;
            },
            listeners: {
            	//鼠标选中监听，把选中的id放入数组
                selectionchange: function(view, item){
                	if(item.length > 0){
                		imageId = item[0].data.imageId;
                	}
//                	if(item.length > 0){
//                		selectedItems = new Array();
//                		for(var i = 0; i < item.length; i++){
//                			Ext.Array.clean(selectedItems);
//                			if(!Ext.Array.contains(selectedItems,item[i].data.id)){
//                				selectedItems.push(item[i].data.id);
//                			}
//                		}
//                	}
                },
                itemdblclick: function (view, record, item, index, e){
                	picPath = record.data.path;
                	e.stopEvent();
                	deletePicture(record.data.imageId);
                },
                itemcontextmenu: function(view, record, item, index, e) {
                	picPath = record.data.path;
                    e.stopEvent();
                    contextMenu.showAt(e.getXY());
                    return false;
                }
            }
        })
    });
	
	// 布局模型
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [ toolbar, panel]
	});
	
	store.load({
		params: {
			memId: memId
		}
	});
	
	function deletePicture(id){
		
		Ext.Msg.confirm('请确认', '确认删除这张照片吗?', function(btn, text) {
			if(btn == 'yes'){
				Ext.Ajax.request({
					url : '/base/deleteImage.atc',
					params : {
						'image.imageId' : id,
						memId:  memId
					},
					success : function(resp, opts) {
						var result = Ext.decode(resp.responseText);
						if(result.success){
							Ext.example.msg('提示', result.msg);
							store.load({
								params: {
									memId: memId
								}
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
		                Ext.Msg.alert('提示', result.msg);
					}
				});
			}
		});  
		
	}


});