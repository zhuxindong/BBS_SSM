package com.zxd.bbs.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zxd.bbs.pojo.Msg;
import com.zxd.bbs.pojo.User;
import com.zxd.bbs.pojo.UserToken;
import com.zxd.bbs.service.UserService;
import com.zxd.bbs.util.MD5Util;

/**
 * @author zhuxindong E-mail:501801307@qq.com
 * @date 创建时间：2017年9月9日 下午3:12:11
 * @version 1.0
 */

@Controller
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	private static final String TRUE = "true";
	private static final String USER = "user";

	@Autowired
	UserService userService;

	/**
	 * 检查用户名是否已经被注册
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping("/checkregistered")
	@ResponseBody
	public Msg checkRegisteredWithJosn(@RequestParam(value = "username") String username) {

		List<User> users = userService.getByUserName(username);

		if (users.size() > 0) {
			return Msg.success().add("resinfo", "true");
		}

		return Msg.success().add("resinfo", "false");

	}

	/**
	 * 注册新用户
	 * 
	 * @param userToken
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/register")
	@ResponseBody
	public Msg userRegister(UserToken userToken, HttpServletRequest request, HttpServletResponse response) {

		if (!userToken.getPassword().equals(userToken.getRpassword())) {
			return Msg.success().add("resinfo", "两次密码不一致");
		}

		User user = new User();

		user.setUsername(userToken.getUsername());
		user.setPassword(MD5Util.getMD5(userToken.getPassword()));
		user.setName(userToken.getName());
		user.setSex(userToken.getSex());

		userService.register(user);

		logger.info("一名新用户注册，用户名：" + userToken.getUsername());

		request.getSession().setAttribute("user", user);

		/**
		 * 是否3天内自动登录
		 */
		if (userToken.getRememberMe() != null && userToken.getRememberMe().equals(TRUE)) {
			Cookie cookieUsername = new Cookie("username", userToken.getUsername());
			Cookie cookiePssswordMD5 = new Cookie("passwordMD5", MD5Util.getMD5(userToken.getPassword()));
			cookieUsername.setMaxAge(60 * 60 * 24 * 3);
			cookiePssswordMD5.setMaxAge(60 * 60 * 24 * 3);

			response.addCookie(cookieUsername);
			response.addCookie(cookiePssswordMD5);

		}

		return Msg.success().add("resinfo", "注册成功");

	}

	/**
	 * 获取用户信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/userinfo")
	@ResponseBody
	public Msg getUserInfo(HttpServletRequest request, HttpServletResponse response) {

		User user = null;
		/**
		 * 先看看session里面有没有user信息
		 */
		if (request.getSession().getAttribute(USER) != null) {
			user = (User) request.getSession().getAttribute(USER);
			user.setPassword(null);
			return Msg.success().add("userinfo", user);
		}

		/**
		 * 再看cookie里面，如果设置了自动登录，那就直接登录
		 */
		Cookie[] cookies = request.getCookies();
		/**
		 * 如果没有cookie，直接返回未登录信息
		 */
		if (cookies == null) {
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
			} else if ("passwordMD5".equals(cookie.getName())) {
				cookiePssswordMD5 = cookie;
			}
		}
		/**
		 * 如果cookie成功获取到，那么判断用户名密码是否正确
		 */
		if (cookieUsername != null && cookiePssswordMD5 != null) {

			user = userService.getByUserName(cookieUsername.getValue()).get(0);
			if ((user != null) && (user.getPassword().equals(cookiePssswordMD5.getValue()))) {

				/**
				 * 如果用户名密码都正确，就加到session里面
				 */
				request.getSession().setAttribute("user", user);
				return Msg.success().add("userinfo", user);
			}

			/**
			 * 如果cookie中的用户名密码错误，那么让cookie失效
			 */
			cookieUsername.setMaxAge(0);
			cookiePssswordMD5.setMaxAge(0);
			response.addCookie(cookieUsername);
			response.addCookie(cookiePssswordMD5);

			return Msg.success().add("resinfo", "登录已过期或未登录");

		}

		return Msg.success().add("resinfo", "登录已过期或未登录");
	}

	/**
	 * 用户登录
	 * 
	 * @param userToken
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/login")
	@ResponseBody
	public Msg userLogin(UserToken userToken, HttpServletRequest request, HttpServletResponse response) {

		User user = null;

		try {
			user = userService.getByUserName(userToken.getUsername()).get(0);
		} catch (Exception e) {

			return Msg.success().add("resinfo", "用户名不存在");
		}

		/**
		 * 用户名不存在，返回相应信息
		 */
		if (user == null) {

			return Msg.success().add("resinfo", "用户名不存在");
		}

		/**
		 * 如果用户名密码正确，返回用户信息
		 */
		if (user.getPassword().equals(MD5Util.getMD5(userToken.getPassword()))) {

			/**
			 * 加入到session
			 */

			request.getSession().setAttribute("user", user);

			/**
			 * 是否3天内自动登录
			 */
			if (userToken.getRememberMe() != null && userToken.getRememberMe().equals(TRUE)) {
				Cookie cookieUsername = new Cookie("username", userToken.getUsername());
				Cookie cookiePssswordMD5 = new Cookie("passwordMD5", MD5Util.getMD5(userToken.getPassword()));
				cookieUsername.setMaxAge(60 * 60 * 24 * 3);
				cookiePssswordMD5.setMaxAge(60 * 60 * 24 * 3);

				response.addCookie(cookieUsername);
				response.addCookie(cookiePssswordMD5);
			}

			return Msg.success().add("userinfo", user);

		}

		return Msg.success().add("resinfo", "密码错误");

	}

	@RequestMapping("/logout")
	@ResponseBody
	public Msg userLogout(HttpServletRequest request, HttpServletResponse response) {

		/**
		 * 先把session里面的用户信息移除
		 */
		request.getSession().removeAttribute("user");

		/**
		 * 再移除cookie
		 */
		Cookie[] cookies = request.getCookies();
		/**
		 * 如果没有cookie，直接返回未登录信息
		 */
		if (cookies == null) {
			return Msg.success().add("resinfo", "退出成功");
		}

		Cookie cookieUsername = null;
		Cookie cookiePssswordMD5 = null;
		/**
		 * 遍历获取cookie
		 */
		for (Cookie cookie : cookies) {
			if ("username".equals(cookie.getName())) {
				cookieUsername = cookie;
			} else if ("passwordMD5".equals(cookie.getName())) {
				cookiePssswordMD5 = cookie;
			}
		}

		if (cookieUsername != null) {
			cookieUsername.setMaxAge(0);
			response.addCookie(cookieUsername);
		}
		if (cookiePssswordMD5 != null) {
			cookiePssswordMD5.setMaxAge(0);
			response.addCookie(cookiePssswordMD5);
		}

		return Msg.success().add("resinfo", "退出成功");
	}

	/**
	 * 修改用户的个性签名
	 * 
	 * @Title: setUserDesc
	 * @Description: TODO
	 * @return Msg
	 * @return
	 */
	@RequestMapping(value = "/desc", method = RequestMethod.PUT)
	@ResponseBody
	public Msg setUserDesc(@RequestParam(value = "mydesc") String mydesc, HttpServletRequest request) {

		User user = null;

		try {

			user = (User) request.getSession().getAttribute("user");

		} catch (Exception e) {
			logger.error("用户登录验证异常");
			logger.error(e.toString());
			return Msg.success().add("resinfo", "用户登录验证异常");
		}

		if (user == null) {
			logger.error("用户登录验证异常");
			return Msg.success().add("resinfo", "用户登录验证异常");
		}

		user.setDescription(mydesc);

		userService.setUserDesc(user);

		request.getSession().setAttribute("user", user);

		return Msg.success().add("resinfo", "修改个性签名成功");

	}

}
