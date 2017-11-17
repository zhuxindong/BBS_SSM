package com.zxd.bbs.dao;

import com.zxd.bbs.pojo.Reply;

/**
* @author zhuxindong  E-mail:501801307@qq.com
* @date 创建时间：2017年9月11日 下午5:42:11
* @version 1.0
*/

public interface ReplyMapper {

	
	int insert(Reply reply);
	
	int deleteById(Integer id);
	
}
