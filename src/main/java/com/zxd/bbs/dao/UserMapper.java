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
	
	/**
	 * 根据用户名查询用户，同时查出帖子
	 * @Title: selectByUserNameWithMsg  
	 * @Description: TODO
	 * @return List<User>  
	 * @param username
	 * @return
	 */
	List<User> selectByUserNameWithMsg(String username);
	
	/**
	 * 根据id查询帖子
	 * @Title: selectByIdWithMsg  
	 * @Description: TODO
	 * @return List<User>  
	 * @param id
	 * @return
	 */
	List<User> selectByIdWithMsg(long id);
	
	/**
	 * 有选择 的插入
	 * @Title: insertSelective  
	 * @Description: TODO
	 * @return int  
	 * @param user
	 * @return
	 */
	int insertSelective(User user);
	
	/**
	 * 根据用户名修改
	 * @Title: updateByUserNameSelective  
	 * @Description: TODO
	 * @return int  
	 * @param user
	 * @return
	 */
	int updateByUserNameSelective(User user);
	
}
