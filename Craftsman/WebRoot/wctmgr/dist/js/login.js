$(function(){
	var allVal='';
	//email blur
	$("input[name='username']").blur(function(allVal){
		var usernameVal = $(this).val();
		if(usernameVal==''){
			allVal='用户名不能为空!';
			layerOpen(allVal);
		}
	});
	
	//password blur
	$("input[name='password']").blur(function(allVal){
		var paswVal = $(this).val();
		if(paswVal.length==''){
			allVal='密码不能为空!';
			layerOpen(allVal);
		}
	});
	
	//login button
	$(".login-btn").on('click',function(){
		layer.open({
			type: 2
			,content: '登录中...'
			,time: 2
		});
		$.ajax({
			url:'/bossLogin.atc',
		    type:'POST',
		    data:{
		        'sysUser.userName':$("input[name='username']").val(),
		        'sysUser.password':$("input[name='password']").val()
		    },
		    timeout:6000,    //超时时间
		    dataType:'json',
		    success:function(data,textStatus,jqXHR){
		    	if(data && !data.success)
		    		layerOpen(data.msg);
		    	else if(data.success)
		    		window.location.href = '/boss/bindex.atc';
		    },
		    error:function(xhr,textStatus){
		    	layerOpen(data.msg);
		    }
		});
		 
	});
	
	function layerOpen(allVal){
		layer.open({
			content: allVal,
			btn: '确定'
		});
	}
	
	
});