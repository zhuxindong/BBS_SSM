package com.zxd.bbs.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zxd.bbs.dao.UserMapper;
import com.zxd.bbs.pojo.Msg;
import com.zxd.bbs.pojo.User;
import com.zxd.bbs.pojo.UserToken;
import com.zxd.bbs.service.UserService;
import com.zxd.bbs.util.MD5Util;

/**
* @author zhuxindong  E-mail:501801307@qq.com
* @date 创建时间：2017年9月9日 下午3:12:11
* @version 1.0
*/

@Controller
public class UserController {

	@Autowired
	UserService userService;
	
	
	/**
	 * 检查用户名是否已经被注册
	 * @param username
	 * @return
	 */
	@RequestMapping("/checkregistered")
	@ResponseBody
	public Msg checkRegisteredWithJosn(@RequestParam(value="username") String username){
		
		List<User> users = userService.getByUserName(username);
		
		if (users.size() > 0) {
			return Msg.success().add("registered", "true");
		}
		
		return Msg.success().add("registered", "false");
		
	}
	
	
	
	@RequestMapping("/register")
	public Msg userRegister(UserToken userToken, HttpServletRequest request,HttpServletResponse response) {
		if (userToken.getPassword().equals(userToken.getRpassword())) {
			return Msg.fail().add("errinfo", "两次密码不一致");
		}
		
		User user = new User();
		
		user.setUsername(userToken.getUsername());
		user.setPassword(MD5Util.getMD5(userToken.getPassword()));
		user.setName(userToken.getName());
		user.setSex(userToken.getSex());
		
		userService.register(user);
		
		request.getSession().setAttribute("user", user);
		
		/**
		 * 是否3天内自动登录
		 */
		if (userToken.getRememberMe().equals("true")) {
			Cookie cookieUsername = new Cookie("username", userToken.getUsername());
			Cookie cookiePssswordMD5 = new Cookie("passwordMD5", MD5Util.getMD5(userToken.getPassword()));
			cookieUsername.setMaxAge(60*60*24*3);
			
			response.addCookie(cookieUsername);
			response.addCookie(cookiePssswordMD5);
		}
		
		return Msg.success();
		
		
	}
	
}
