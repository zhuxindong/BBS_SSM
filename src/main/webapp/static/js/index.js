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
		type: 'POST',
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







function get_userinfo() {
		$.ajax({
			url: 'userinfo',		
			success:function(result){

				console.log(result.extend.resinfo);

				if (result.extend.resinfo=='登录已过期或未登录') {
					$('#chgpassword').hide();
					$('#logout').hide();
					

					$('#nav_name').text('您还未登录');
					$('#main_name').text('您还未登录');
					$('#main-desc').hide();
				}
				



			}
		});
		
		
	};
