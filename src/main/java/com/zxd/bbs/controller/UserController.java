package com.zxd.bbs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zxd.bbs.pojo.Msg;
import com.zxd.bbs.pojo.User;
import com.zxd.bbs.service.UserService;

/**
* @author zhuxindong  E-mail:501801307@qq.com
* @date 创建时间：2017年9月9日 下午3:12:11
* @version 1.0
*/

@Controller
public class UserController {

	@Autowired
	UserService userService;
	
	
	@RequestMapping("/checkregistered")
	@ResponseBody
	public Msg checkRegisteredWithJosn(@RequestParam(value="username") String username){
		
		List<User> users = userService.getByUserName(username);
		
		if (users.size() > 0) {
			return Msg.success().add("registered", "true");
		}
		
		return Msg.success().add("registered", "false");
		
	}
	
	
	
	
}
