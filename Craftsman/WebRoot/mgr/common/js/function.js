/**
 *  登录超时
 */
window.logout = function() {
	Ext.MessageBox.show({
	    title: '提示',
	    msg: '会话已超时,请重新登录系统！',
	    closable: false,
	    buttons: Ext.MessageBox.OK,
	    animEl: Ext.getBody(),
	    icon: Ext.MessageBox.INFO,
	    fn: function(btn) {
			top.window.location.href = '/login.atc';
	    }
    });  
};

/**
 *  出现错误
 */
window.showError = function(errorMsg) {
	Ext.MessageBox.show({
	    title: '发生错误，请联系系统管理员。',
	    msg: errorMsg,
	    maxWidth: 800,
	    closable: false,
	    buttons: Ext.MessageBox.OK,
	    animEl: Ext.getBody(),
	    icon: Ext.MessageBox.ERROR
    });  
};

/**
 *  没有操作权限
 */
window.noLegal = function() {
	Ext.MessageBox.show({
	    title: '提示',
	    msg: '没有权限执行此操作，请联系管理员！',
	    closable: false,
	    buttons: Ext.MessageBox.OK,
	    animEl: Ext.getBody(),
	    icon: Ext.MessageBox.INFO
    });  
};

/**
 *  是否拥有按钮权限
 */
window.haveActionMenu = function(btn) {
	if (rButtons && rButtons.length > 0)
		return Ext.Array.contains(rButtons, btn);
	else if(BB < 10)
		return true;
	return false;
};

/**
 * 显示右下角消息提示框
 */
window.showMessageTip = function(btn) {
	Ext.create('Ext.ux.Notification', {
		title: '消息',
		position: 'br',
		iconCls: 'comments',
		autoCloseDelay: 10000,
		spacing: 20,
		html: '您收到1条消息！顶顶顶顶顶顶<br>顶顶顶顶<a href="#">查看详情</a>'
	}).show();
};

/**
 * 日期格式化
 */
window.dateFormat = function(value) {
	if(null != value)
        return Ext.Date.format(new Date(value),'Y/m/d');
    else
        return null;
}

/**
 * 日期时间格式化
 */
window.dateTimeFormat = function(value) {
	if(null != value)
		return Ext.Date.format(new Date(value),'Y/m/d H:i:s');
	else
		return null;
}

