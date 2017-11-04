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
			return Msg.success().add("resinfo", "true");
		}
		
		return Msg.success().add("resinfo", "false");
		
	}
	
	
	
	@RequestMapping("/register")
	@ResponseBody
	public Msg userRegister(UserToken userToken,
							HttpServletRequest request, 
							HttpServletResponse response) {
		System.out.println(userToken);
		
		if (!userToken.getPassword().equals(userToken.getRpassword())) {
			return Msg.success().add("resinfo", "两次密码不一致");
		}
		
		User user = new User();
		
		user.setUsername(userToken.getUsername());
		user.setPassword(MD5Util.getMD5(userToken.getPassword()));
		user.setName(userToken.getName());
		user.setSex(userToken.getSex());
		
		userService.register(user);
		System.out.println("插入数据库成功");
		
		request.getSession().setAttribute("user", user);
		
		/**
		 * 是否3天内自动登录
		 */
		if (userToken.getRememberMe()!=null && userToken.getRememberMe().equals("true")) {
			Cookie cookieUsername = new Cookie("username", userToken.getUsername());
			Cookie cookiePssswordMD5 = new Cookie("passwordMD5", MD5Util.getMD5(userToken.getPassword()));
			cookieUsername.setMaxAge(60*60*24*3);
			cookiePssswordMD5.setMaxAge(60*60*24*3);
			
			response.addCookie(cookieUsername);
			response.addCookie(cookiePssswordMD5);
			
			System.out.println(" cookie设置成功");
		}
		
		return Msg.success().add("resinfo", "注册成功");

	}
	
	
	@RequestMapping("/userinfo")
	@ResponseBody
	public Msg getUserInfo(HttpServletRequest request,
						   HttpServletResponse response) {
		
		User user = null;
		/**
		 * 先看看session里面有没有user信息
		 */
		if (request.getSession().getAttribute("user") != null) {
			user = (User) request.getSession().getAttribute("user");
			user.setPassword(null);
			return Msg.success().add("userinfo", user);
		}
		
		/**
		 * 再看cookie里面，如果设置了自动登录，那就直接登录
		 */
		Cookie[] cookies = request.getCookies();
		if (cookies==null) {
			return Msg.success().add("resinfo", "登录已过期或未登录");
		}
		
		
		Cookie cookieUsername = null;
		Cookie cookiePssswordMD5 = null;
		/**
		 * 遍历获取cookie
		 */
		for (Cookie cookie : cookies) {
			if ("username".equals(cookie.getName())) {
				cookieUsername = cookie;
			}else if ("passwordMD5".equals(cookie.getName())) {
				cookiePssswordMD5 = cookie;
			}		
		}
		/**
		 * 如果cookie成功获取到，那么判断用户名密码是否正确
		 */
		if (cookieUsername!=null && cookiePssswordMD5!=null) {
			
			user = userService.getByUserName(cookieUsername.getValue()).get(0);
			if ((user != null) && (user.getPassword().equals(cookiePssswordMD5.getValue()))) {
				
				/**
				 * 加到session里面
				 */
				request.getSession().setAttribute("user", user);
				return Msg.success().add("userinfo", user);
			}
			
			/**
			 * 让cookie失效
			 */
			cookieUsername.setMaxAge(0);
			cookiePssswordMD5.setMaxAge(0);
			response.addCookie(cookieUsername);
			response.addCookie(cookiePssswordMD5);
			
			return Msg.success().add("resinfo","登录已过期或未登录");
			
		}
		
		
		
		
		return Msg.success().add("resinfo", "登录已过期或未登录");
		
	}
	
	
	
}
