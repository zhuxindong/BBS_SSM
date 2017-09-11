package com.zxd.bbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxd.bbs.dao.MessageMapper;
import com.zxd.bbs.pojo.Message;

/**
* @author zhuxindong  E-mail:501801307@qq.com
* @date 创建时间：2017年9月11日 下午5:54:01
* @version 1.0
*/

@Service
public class MessageService {
	
	
	@Autowired
	private MessageMapper messageMapper;
	
	
	/**
	 * 查询所有帖子
	 * @return
	 */
	public List<Message> getAll(){
		
		return messageMapper.selectAllWithUserAndReply();
		
	}
	
	
	
	

}
