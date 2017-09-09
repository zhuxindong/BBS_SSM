package com.zxd.bbs.dao;

import java.util.List;

import com.zxd.bbs.pojo.User;

/**
* @author zhuxindong  E-mail:501801307@qq.com
* @date 创建时间：2017年9月8日 下午3:04:27
* @version 1.0
*/

public interface UserMapper {
	
	/**
	 * 查询所有用户，同时查询用户的帖子和打分情况
	 * @param user
	 * @return
	 */
	List<User> selectAllWithMsg();
	
	List<User> selectByUserNameWithMsg(String username);
	
	List<User> selectByIdWithMsg(long id);
	
	int insertSelective(User user);
	
	int updateByUserNameSelective(User user);
	
}
