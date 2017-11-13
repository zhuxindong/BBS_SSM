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


// 根据传入的页码号，分页查询
function to_page(argument) {
	// body...
	$.ajax({
		url: 'messages',
		type: 'GET',
		data: {'pn': argument},
		success:function(result){

			//构建分页条
			build_page_nav(result);



			//渲染帖子列表
			build_message_list(result);
			

			


		}
	});	
}







// 构建分页条信息
function build_page_nav(argument) {
	// 清空原来的区域
	$('#page_nav_area').empty();

	// 根节点
	var page_ul = $('<ul></ul>').addClass('pagination');

	// 首页
	var firstPage_li = $('<li></li>').append($('<a></a>').append('首页').attr('href','#'));
	firstPage_li.click(function(event) {
		/* Act on the event */
		to_page(1);
	});


	// 尾页
	var lastPage_li = $('<li></li>').append($('<a></a>').append('末页').attr('href','#'));
	lastPage_li.click(function(event) {
		/* Act on the event */
		to_page(argument.extend.pageinfo.pages);
	});



	// 是否有前一页
	if (argument.extend.pageinfo.hasPreviousPage == true) {
		var prePage_li = $('<li></li>').append($('<a></a>').append('&laquo;').attr('href','#'));

		prePage_li.click(function(event) {
			/* Act on the event */
			to_page(argument.extend.pageinfo.pageNum-1);
		});

	}
	
	// 是否有后一页
	if (argument.extend.pageinfo.hasNextPage == true) {
		var nextPage_li = $('<li></li>').append($('<a></a>').append('&raquo;').attr('href','#'));

		nextPage_li.click(function(event) {
			/* Act on the event */
			to_page(argument.extend.pageinfo.pageNum+1);
		});

	}
	

	page_ul.append(firstPage_li).append(prePage_li);


	// 循环构建分页按钮信息
	$.each(argument.extend.pageinfo.navigatepageNums, function(index, val) {
		 /* iterate through array or object */
		 var num_li = $('<li></li>').append($('<a></a>').append(val).attr('href','#'));

		 // 判断是否为当前页
		 if (argument.extend.pageinfo.pageNum == val) {num_li.addClass('active');}
		 // 绑定点击事件
		 num_li.click(function(event) {
		 	/* Act on the event */
		 	to_page(val);
		 });

		 page_ul.append(num_li);

	});

	page_ul.append(nextPage_li).append(lastPage_li);

	var page_nav = $('<nav></nav>').append(page_ul);

	page_nav.appendTo('#page_nav_area');

}




// 渲染帖子信息
function build_message_list(argument) {
	// 先清空

	$('#app-messages').empty();

	//获取帖子列表
	var msg=argument.extend.pageinfo.list;

	// 通过遍历来构建帖子
	$.each(msg, function(index, val) {
		 /* iterate through array or object */

		 // 一个帖子的根元素div
		 var root_div=$('<div style="display: none;"></div>').addClass('panel panel-default').attr('id','msg'+val.id);

		 //帖子的表头信息
		 var head_div=$('<div></div>').addClass('panel-heading').append(val.user.name)   //加入帖子主人,下面是加入创建时间
		 														.append($('<font></font>').append(val.createtime).css({'float':"right",'margin-right':"5px"}));





         // 发表评论的根元素,
         var reply_div=$('<div style="float: right; margin-top: 10px;"></div>').append($('<button class="btn btn-info" type="button" data-toggle="collapse" aria-expanded="false"'
         																				+'aria-controls="collapseExample" style="float: right;">'
         																				+'评论</button>').attr('data-target','#rep_msg_'+val.id)); //加入按钮
																	         	// .append($('<div class="collapse" style="float: right; ">'
																	         	// +'<div id="reply-page">'
																	         	// +'<form action="" method="post"></form> </div></div>').attr('id','rep_msg_'+val.id));//加入输入框
		// 评论输入框的textarea
		var reply_textarea_div=$('<textarea  class="form-control" style="min-height: 82px;"></textarea>').attr('id','reply_text_'+val.id);										         	
		// 评论输入框的回复按钮
		var reply_button_div=$('<button type="button" class="btn">发表</button>').attr('onclick','publishreply('+val.id+')');
		// 评论输入框的form
		var reply_form_div=$('<form action="" method="post"></form>').append(reply_textarea_div).append(reply_button_div);
		var reply_page_div=$('<div id="reply-page"></div>').append(reply_form_div);
		var reply_hiden_div=$('<div class="collapse" style="float: right; "></div>').append(reply_page_div).attr('id','rep_msg_'+val.id);
		reply_div.append(reply_hiden_div);

         //帖子的内容信息
         var content_div=$('<div></div>').addClass('panel-body').append(val.content).append(reply_div);




		 //将构建好的元素加入根元素
		 root_div.append(head_div).append(content_div);


		 // 将根元素加入页面
		 root_div.appendTo('#app-messages');
		 root_div.fadeIn(300);
		 
		
	});
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
						window.location.href="index.html";
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
					window.location.href="index.html";
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