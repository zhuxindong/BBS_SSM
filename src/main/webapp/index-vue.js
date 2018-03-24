//全局的用户信息
var userinfo;


$(document).ready(function(){
	// 让用户头像呈现为正方形。。。
    $('#user_picture').css('height',$('#user_picture').css('width'));
    $('#user_picture img').css('width',$('#user_picture').css('width'));
    to_page(1);

    get_userinfo();
})


// 把时间戳变为普通日期
function getLocalTime(nS) {     
   return new Date(parseInt(nS) * 1000).toLocaleString().replace(/:\d{1,2}$/,' ');     
} 





// 获取用户的登录信息
function get_userinfo() {
		$.ajax({
			url: 'userinfo',		
			success:function(result){

				if (result.extend.resinfo=='登录已过期或未登录') {
					$('#chgpassword').hide();
					$('#logout').hide();


					$('#nav_name').text('您还未登录');
					$('#main_name').text('您还未登录');
					$('#main-desc').hide();
				}
				else{
					$('#logindiv').hide();	
					
					userinfo = result.extend.userinfo;

					$('#nav_name').text(userinfo.name);
					$('#main_name').text(userinfo.name);

					
				}
				



			}
		});
	};



// 异步查询用户是否可用
$('#rusername').keyup(function() {
	/* Act on the event */

	if (checkminggan('#rusername','#checkResult','#reglogbtn')==false) {

		$.ajax({
		url: 'checkregistered',
		type: 'POST',
		data: {'username': $(this).val()},
		success:function(result) {
			// body...
			if (result.extend.resinfo=='false') {
				$('#checkResult').html('<font color="green">可以使用</font>');
				$('#reglogbtn').removeAttr('disabled');
			}
			else{
				$('#checkResult').html('<font color="red">用户名已被注册</font>');
				$('#reglogbtn').attr('disabled','disabled');
			}
		}

	});


	}

	

});


// 检查敏感信息
function checkminggan(input,output,btn) {
	var checkinput=$(input).val();
	if ((checkinput.search("<") != -1) || (checkinput.search(">") != -1) ) {
		$(output).html("<font color='red'>含有敏感字符</font>");
		$(btn).attr('disabled','disabled');
		return true;
	}
	else{
		$(output).html(" ");
		$(btn).removeAttr('disabled');
		return false;
	}

}


// 检查注册时两次输入的密码是否一致
$('#rrpassword').keyup(function() {
	// body...
	if ($('#rpassword').val()==$('#rrpassword').val()) {
		$('#checkrpwd').html(' ');
		$('#reglogbtn').removeAttr('disabled');
	}
	else{
		$('#checkrpwd').html("<font color='red'>两次输入的密码不一致</font>");
		$('#reglogbtn').attr('disabled','disabled');
	}


});

$('#rname').keyup(function() {
	// body...
	checkminggan('#rname','#checkmg','#reglogbtn');
})


// 注册按钮的方法
$('#reglogbtn').click(function(event) {
	
	// 表单不能为空的判断
	if ($('#rusername').val().replace(/(^\s*)|(\s*$)/g, '')=='') {
		$('#checkResult').html('<font color="red">不能为空</font>');
	}else if ($('#rpassword').val().replace(/(^\s*)|(\s*$)/g, '')=='') {
		$('#checkpwd').html('<font color="red">不能为空</font>');
	}else if ($('#rrpassword').val().replace(/(^\s*)|(\s*$)/g, '')=='') {
		$('#checkrpwd').html('<font color="red">不能为空</font>');
	}else if ($('#rname').val().replace(/(^\s*)|(\s*$)/g, '')=='') {
		$('#checkmg').html('<font color="red">不能为空</font>');
	}else{
		// 两次输入密码要一致的判断
		if ($('#rpassword').val()==$('#rrpassword').val()) {
			$('#checkrpwd').html(' ');
			$('#reglogbtn').removeAttr('disabled');


			$.ajax({
				url: 'register',
				type: 'POST',
				data: $('#regform').serialize(),
				success:function(result){
					$('#regModal').modal('hide');
					window.location.href="index.html";
				}
			});



		}
		else{
			$('#checkrpwd').html("<font color='red'>两次输入的密码不一致</font>");
			$('#reglogbtn').attr('disabled','disabled');
		}
		
	}
	
});


//2秒内禁用按钮，防止表单重复提交
 	function btnactive()
 	{
 		document.getElementById("logbtn").removeAttribute('disabled');
 		document.getElementById("tryagain").innerHTML="";
 	}



//用户登录
$('#logbtn').click(function() {
	
	// 表单不能为空的判断
	if ($('#lusername').val().replace(/(^\s*)|(\s*$)/g, '')=='') {
		$('#lusername_msg').html('<font color="red">不能为空</font>');
	}else if ($('#lpassword').val().replace(/(^\s*)|(\s*$)/g, '')=='') {
		$('#lpassword_msg').html('<font color="red">不能为空</font>');
	}else{

		
		$.ajax({
				url: 'login',
				type: 'POST',
				data: $('#logform').serialize(),
				success:function(result){

					if (result.extend.resinfo=='用户名不存在') {
						$('#logResult').html('<font color="red">用户名不存在</font>');
					}else if(result.extend.resinfo=='密码错误'){
						$('#logResult').html('<font color="red">密码错误</font>');
					}else{
						$('#loginModal').modal('hide');
						window.location.href="index-vue.html";
					}
					
				}
			});


	}
});


//退出登录
$('#logout').click(function() {
	
	$.ajax({
				url: 'logout',
				success:function(result){
					console.log(result);
					window.location.href="index-vue.html";
				}
			});

});



// 发布动态
$('#publishmessagebtn').click(function(event) {
	/* Act on the event */
	//如果用户未登录，那么弹出登录框
	if (userinfo==null || userinfo==undefined ||userinfo.id<0) {
		$('#loginModal').modal({
			backdrop:'static'
		});
		return;
	}

	//校验输入框是否为空
	if ($('#saysomething').val()=="") {
		alert('写点什么吧，不要为空哦！');
		return;
	}


	//先渲染了再说
	$("#app-messages").prepend('<div class="panel panel-default" style="display: none;">'+
						  '<div class="panel-heading">'+userinfo.name +'<font style="float: right; margin-right: 5px;">刚刚</font></div>'+
						  '<div class="panel-body" id="shownewmessage">'+
						    $('#saysomething').val()+
						  '</div>'+
						'</div>');

	// 过渡动画
	$("#app-messages div").show(500);

	
	$.ajax({
		url: 'messages',
		type: 'POST',
		data: {'content': $('#saysomething').val()},
		success:function(result){

			// console.log(result);
		}
	});
	
	



});



//删除帖子
function delmsg(id) {

	var msg_id="#msg"+id;

	
	
	$.ajax({
		url: 'messages/'+id,
		type: 'POST',
		data: {_method: 'DELETE'},
		success:function (result) {

			//动画渲染
			$(msg_id).hide(500);

		}
	});

}



// 根据传入的页码号，分页查询
function to_page(argument) {
	// body...
	$.ajax({
		url: 'messages',
		type: 'GET',
		data: {'pn': argument},
		success:function(result){


			vue_msglist.$data.pages=result.extend.pageinfo;

		}
	});	
}



//vue渲染帖子信息和导航条
var vue_msglist = new Vue({
	el: "#app-messages",
	data:{
		pages:{"pageNum":1,"pageSize":10,"size":10,"startRow":1,"endRow":10,"total":52,"pages":6,"list":[{"id":2543,"content":"修复了删除动态的bug","createtime":"2017-11-17 11:10:14","user":{"id":37,"username":"201403080433 ","password":null,"name":"朱鑫栋","sex":null,"description":null,"messages":null,"score":null},"replies":[]},{"id":2541,"content":"发布动态功能完成","createtime":"2017-11-13 18:43:47","user":{"id":37,"username":"201403080433 ","password":null,"name":"朱鑫栋","sex":null,"description":null,"messages":null,"score":null},"replies":[]},{"id":2533,"content":"mybatis 插入的","createtime":"2017-11-03 18:42:20","user":{"id":37,"username":"201403080433 ","password":null,"name":"朱鑫栋","sex":null,"description":null,"messages":null,"score":null},"replies":[]},{"id":2532,"content":"mybatis 插入的","createtime":"2017-11-03 18:40:13","user":{"id":37,"username":"201403080433 ","password":null,"name":"朱鑫栋","sex":null,"description":null,"messages":null,"score":null},"replies":[]},{"id":2531,"content":"wqeqweqwe","createtime":"2017-10-23 10:39:40","user":{"id":63,"username":"CC123","password":null,"name":"cc123","sex":null,"description":null,"messages":null,"score":null},"replies":[]},{"id":2530,"content":"修复tomcat崩溃的bug，修复评论异常的bug，优化查询效率","createtime":"2017-07-14 15:04:28","user":{"id":37,"username":"201403080433 ","password":null,"name":"朱鑫栋","sex":null,"description":null,"messages":null,"score":null},"replies":[{"id":39,"content":"可以评论了","createtime":"2017-07-14 15:04:36","user":{"id":37,"username":"201403080433 ","password":null,"name":"朱鑫栋","sex":null,"description":null,"messages":null,"score":null},"message":null}]},{"id":2526,"content":"可以注册","createtime":"2017-06-23 22:23:29","user":{"id":57,"username":"123","password":null,"name":"123","sex":null,"description":null,"messages":null,"score":null},"replies":[{"id":40,"content":"gg","createtime":"2017-07-21 21:59:47","user":{"id":37,"username":"201403080433 ","password":null,"name":"朱鑫栋","sex":null,"description":null,"messages":null,"score":null},"message":null}]},{"id":2525,"content":"性能优化，全部开启懒加载，采用迫切左外连接查询","createtime":"2017-06-23 22:22:40","user":{"id":37,"username":"201403080433 ","password":null,"name":"朱鑫栋","sex":null,"description":null,"messages":null,"score":null},"replies":[]},{"id":2522,"content":"修复了不能注册的bug","createtime":"2017-06-21 20:23:34","user":{"id":37,"username":"201403080433 ","password":null,"name":"朱鑫栋","sex":null,"description":null,"messages":null,"score":null},"replies":[]},{"id":1119,"content":"为什么要js分页呢","createtime":"2017-06-17 22:36:08","user":{"id":48,"username":"asd","password":null,"name":"asd","sex":null,"description":null,"messages":null,"score":null},"replies":[{"id":32,"content":"123","createtime":"2017-06-18 14:10:37","user":{"id":37,"username":"201403080433 ","password":null,"name":"朱鑫栋","sex":null,"description":null,"messages":null,"score":null},"message":null}]}],"prePage":0,"nextPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2,3,4,5,6],"navigateFirstPage":1,"navigateLastPage":6,"lastPage":6,"firstPage":1},
	},
	methods:{
		topage:function(pn){
			to_page(pn);
		}
	}

})



