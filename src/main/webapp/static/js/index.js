$(document).ready(function(){
	// 让用户头像呈现为正方形。。。
    $('#user_picture').css('height',$('#user_picture').css('width'));
    $('#user_picture img').css('width',$('#user_picture').css('width'));
    to_page(1);
})


// 把时间戳变为普通日期
function getLocalTime(nS) {     
   return new Date(parseInt(nS) * 1000).toLocaleString().replace(/:\d{1,2}$/,' ');     
} 


// 根据传入的页码号，更新message这个全局变量
function to_page(argument) {
	// body...
	$.ajax({
		url: 'messages',
		type: 'POST',
		data: {'pn': argument},
		success:function(result){

			var vm_messages = new Vue({

			el: "#app-messages",
			data:{
				'vue_msg':result.extend.pageinfo.list,
			}

			})
		}
	});	
}



// 用vue渲染列表，并绑定数据
// var vm_messages = new Vue({

// 	el: "#app-messages",
// 	data:{
// 		'vue_msg':messages.extend.pageinfo.list
// 	}

// })