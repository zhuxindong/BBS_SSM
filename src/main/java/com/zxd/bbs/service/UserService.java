package com.zxd.bbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxd.bbs.dao.UserMapper;
import com.zxd.bbs.pojo.User;

/**
* @author zhuxindong  E-mail:501801307@qq.com
* @date 创建时间：2017年9月9日 下午3:06:56
* @version 1.0
*/

@Service
public class UserService {
	
	
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 查询所有用户
	 * @return
	 */
	public List<User> getAll(){
		
		return userMapper.selectAllWithMsg();
		
	}
	
	/**
	 * 根据用户名查询用户
	 * @param username
	 * @return
	 */
	public List<User> getByUserName(String username){
		
		return userMapper.selectByUserNameWithMsg(username);
		
	}
	
	
	/**
	 * 用户注册
	 * @param user
	 */
	public void register(User user) {
		
		userMapper.insertSelective(user);
		
	}
	
	

}
