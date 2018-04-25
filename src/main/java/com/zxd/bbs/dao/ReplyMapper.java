package com.zxd.bbs.dao;

import com.zxd.bbs.pojo.Reply;

/**
* @author zhuxindong  E-mail:501801307@qq.com
* @date 创建时间：2017年9月11日 下午5:42:11
* @version 1.0
*/

public interface ReplyMapper {

	/**
	 * 插入回复
	 * @Title: insert  
	 * @Description: TODO
	 * @return int  
	 * @param reply
	 * @return
	 */
	int insert(Reply reply);
	
	/**
	 * 根据id删除帖子
	 * @Title: deleteById  
	 * @Description: TODO
	 * @return int  
	 * @param id
	 * @return
	 */
	int deleteById(Integer id);
	
}
