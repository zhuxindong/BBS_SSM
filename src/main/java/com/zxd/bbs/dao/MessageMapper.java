package com.zxd.bbs.dao;

import java.util.List;

import com.zxd.bbs.pojo.Message;

/**
* @author zhuxindong  E-mail:501801307@qq.com
* @date 创建时间：2017年9月9日 上午9:01:48
* @version 1.0
*/

public interface MessageMapper {
	
	/**
	 * 查询所有帖子，同时查询帖子的主人和回复
	 * @return
	 */
	List<Message> selectAllWithUserAndReply();
	
	/**
	 * 根据用户名查询帖子，同时查询帖子的主人和回复
	 * @Title: selectByUserNameWithUserAndReply  
	 * @Description: 根据用户名查询帖子，同时查询帖子的主人和回复
	 * @return List<Message>  
	 * @param username
	 * @return
	 */
	List<Message> selectByUserNameWithUserAndReply(String username);
	
	/**
	 * 根据id查询帖子，同时查询帖子的主人和回复
	 * @Title: selectByIdWithUserAndReply  
	 * @Description: 根据id查询帖子，同时查询帖子的主人和回复
	 * @return List<Message>  
	 * @param id
	 * @return
	 */
	List<Message> selectByIdWithUserAndReply(int id);
	
	/**
	 * 插入帖子
	 * @Title: insert  
	 * @Description: TODO
	 * @return int  
	 * @param message
	 * @return
	 */
	int insert(Message message);
	
	/**
	 * 根据帖子的id删除帖子
	 * @param id
	 * @return
	 */
	int deleteById(int id);
	
	

}
